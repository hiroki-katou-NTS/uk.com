package nts.uk.ctx.pr.shared.app.command.socialinsurance.employeesociainsur.emphealinsurbeneinfo;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.error.BusinessException;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.arc.layer.app.command.CommandHandlerWithResult;
import nts.arc.time.GeneralDate;
import nts.gul.collection.CollectionUtil;
import nts.uk.ctx.pr.shared.dom.socialinsurance.employeesociainsur.emphealinsurbeneinfo.EmpHealInsQualifiInfoService;
import nts.uk.ctx.pr.shared.dom.socialinsurance.employeesociainsur.emphealinsurbeneinfo.EmpHealthInsurBenefits;
import nts.uk.ctx.pr.shared.dom.socialinsurance.employeesociainsur.emphealinsurbeneinfo.EmplHealInsurQualifiInfor;
import nts.uk.ctx.pr.shared.dom.socialinsurance.employeesociainsur.emphealinsurbeneinfo.EmplHealInsurQualifiInforParams;
import nts.uk.ctx.pr.shared.dom.socialinsurance.employeesociainsur.emphealinsurbeneinfo.EmplHealInsurQualifiInforRepository;
import nts.uk.ctx.pr.shared.dom.socialinsurance.employeesociainsur.emphealinsurbeneinfo.HealInsurNumberInfor;
import nts.uk.ctx.pr.shared.dom.socialinsurance.employeesociainsur.emphealinsurbeneinfo.HealInsurNumberInforRepository;
import nts.uk.shr.com.context.AppContexts;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.shr.pereg.app.command.MyCustomizeException;
import nts.uk.shr.pereg.app.command.PeregUpdateListCommandHandler;

@Stateless
public class UpdateEmpHealInsQualifiInfoListCommandHandler
		extends CommandHandlerWithResult<List<UpdateEmpHealInsQualifiInfoCommand>, List<MyCustomizeException>>
		implements PeregUpdateListCommandHandler<UpdateEmpHealInsQualifiInfoCommand> {
	@Inject
	private EmplHealInsurQualifiInforRepository emplHealInsurQualifiInforRepository;

	@Inject
	private EmpHealInsQualifiInfoService empHealInsQualifiInfoService;

	@Inject
	private HealInsurNumberInforRepository healInsurNumberInforRepository;
	@Override
	public String targetCategoryCd() {
		return "CS00082";
	}

	@Override
	public Class<?> commandClass() {
		return UpdateEmpHealInsQualifiInfoCommand.class;
	}

	@Override
	protected List<MyCustomizeException> handle(
			CommandHandlerContext<List<UpdateEmpHealInsQualifiInfoCommand>> context) {

		List<UpdateEmpHealInsQualifiInfoCommand> command = context.getCommand();

		String cid = AppContexts.user().companyId();

		List<String> sidErrorLst = new ArrayList<>();

		List<MyCustomizeException> errorExceptionLst = new ArrayList<>();

		// sidsPidsMap
		List<String> sids = command.stream().map(c -> c.getEmployeeId()).collect(Collectors.toList());

		List<EmplHealInsurQualifiInforParams> domainIntermediates = new ArrayList<>();
		
		List<EmplHealInsurQualifiInfor> listDomain = emplHealInsurQualifiInforRepository.getEmplHealInsurQualifiInforDescCps003(cid, sids);

		Map<String, List<EmplHealInsurQualifiInfor>> histBySidsMap = listDomain.stream().collect(Collectors.groupingBy(c -> c.getEmployeeId()));

		List<HealInsurNumberInfor> healInsurNumberInfors = new ArrayList<>();
		command.stream().forEach(c -> {

			try {

				// In case of date period are exist in the screen,
				if (c.getStartDate() != null) {

					List<EmplHealInsurQualifiInfor> existHistLst = histBySidsMap.get(c.getEmployeeId());

					if (existHistLst != null) {

						Optional<EmpHealthInsurBenefits> itemToBeUpdate = existHistLst.get(0)

								.getMourPeriod().stream()

								.filter(h -> h.identifier().equals(c.getHistoryId())).findFirst();

						if (itemToBeUpdate.isPresent()) {

							existHistLst.get(0).changeSpan(itemToBeUpdate.get(), new DatePeriod(c.getStartDate(),
									
									c.getEndDate() != null ? c.getEndDate() : GeneralDate.max()));

							HealInsurNumberInfor numberInfor = HealInsurNumberInfor.createFromJavaType(c.getHistoryId(),
									
									c.getNurCaseInsNumber(), c.getHealInsNumber());

							domainIntermediates.add(new EmplHealInsurQualifiInforParams(cid, itemToBeUpdate.get(),
									
									numberInfor, existHistLst.get(0)));

						} else {

							sidErrorLst.add(c.getEmployeeId());

						}
					} else {

						sidErrorLst.add(c.getEmployeeId());


					}
				}else {
					
					
					HealInsurNumberInfor numberInfor = HealInsurNumberInfor.createFromJavaType(c.getHistoryId(),
							
							c.getNurCaseInsNumber(), c.getHealInsNumber());
					
					healInsurNumberInfors.add(numberInfor);
				}
				

			} catch (BusinessException e) {

				MyCustomizeException ex = new MyCustomizeException(e.getMessageId(), Arrays.asList(c.getEmployeeId()),
						"期間");

				errorExceptionLst.add(ex);

			}

		});

		if (!domainIntermediates.isEmpty()) {

			List<MyCustomizeException> myExs = empHealInsQualifiInfoService.updateAll(domainIntermediates);

			if (!myExs.isEmpty()) {

				errorExceptionLst.addAll(myExs);

			}

		}
		
		if(!CollectionUtil.isEmpty(healInsurNumberInfors)) {
			
			this.healInsurNumberInforRepository.updateAll(healInsurNumberInfors);
			
		}

			

		if (!sidErrorLst.isEmpty()) {

			errorExceptionLst.add(new MyCustomizeException("Invalid EmpHealInsQualifiInfo", sidErrorLst));

		}

		return errorExceptionLst;
	}

}
