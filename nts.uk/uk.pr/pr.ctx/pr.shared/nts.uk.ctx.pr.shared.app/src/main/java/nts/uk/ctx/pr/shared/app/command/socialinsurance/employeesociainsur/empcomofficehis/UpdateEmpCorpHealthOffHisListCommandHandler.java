package nts.uk.ctx.pr.shared.app.command.socialinsurance.employeesociainsur.empcomofficehis;

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
import nts.uk.ctx.pr.shared.dom.socialinsurance.employeesociainsur.empcomofficehis.AffOfficeInformation;
import nts.uk.ctx.pr.shared.dom.socialinsurance.employeesociainsur.empcomofficehis.AffOfficeInformationRepository;
import nts.uk.ctx.pr.shared.dom.socialinsurance.employeesociainsur.empcomofficehis.EmpCorpHealthOffHis;
import nts.uk.ctx.pr.shared.dom.socialinsurance.employeesociainsur.empcomofficehis.EmpCorpHealthOffHisInter;
import nts.uk.ctx.pr.shared.dom.socialinsurance.employeesociainsur.empcomofficehis.EmpCorpHealthOffHisRepository;
import nts.uk.ctx.pr.shared.dom.socialinsurance.employeesociainsur.empcomofficehis.EmpCorpHealthOffHisService;
import nts.uk.ctx.pr.shared.dom.socialinsurance.employeesociainsur.empcomofficehis.SocialInsuranceOfficeCode;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.history.DateHistoryItem;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.shr.pereg.app.command.MyCustomizeException;
import nts.uk.shr.pereg.app.command.PeregUpdateListCommandHandler;
@Stateless
public class UpdateEmpCorpHealthOffHisListCommandHandler extends CommandHandlerWithResult<List<UpdateEmpCorpHealthOffHisCommand>, List<MyCustomizeException>>
implements PeregUpdateListCommandHandler<UpdateEmpCorpHealthOffHisCommand>{

	@Inject
	private EmpCorpHealthOffHisRepository empCorpHealthOffHisRepo;

	@Inject
	private EmpCorpHealthOffHisService empCorpHealthOffHisService;
	
	@Inject
	private AffOfficeInformationRepository affOfficeInformationRepository;

	@Override
	public String targetCategoryCd() {
		
		return "CS00075";
		
	}

	@Override
	public Class<?> commandClass() {
		
		return UpdateEmpCorpHealthOffHisCommand.class;
		
	}

	@Override
	protected List<MyCustomizeException> handle(CommandHandlerContext<List<UpdateEmpCorpHealthOffHisCommand>> context) {
		
		List<UpdateEmpCorpHealthOffHisCommand> cmd = context.getCommand();
		
		String cid = AppContexts.user().companyId();
		
		List<String> sidErrorLst = new ArrayList<>();
		
		List<MyCustomizeException> errorExceptionLst = new ArrayList<>();
		
		// sidsPidsMap
		List<String> sids = cmd.stream().map(c -> c.getEmployeeId()).collect(Collectors.toList());
		
		List<EmpCorpHealthOffHisInter> domainIntermediates = new ArrayList<>();
		
		Map<String, List<EmpCorpHealthOffHis>> existHistMap = empCorpHealthOffHisRepo.getByCidAndSidsDesc(cid, sids)
				.stream().collect(Collectors.groupingBy(c -> c.getEmployeeId()));
		 
		List<AffOfficeInformation> updateInfos = new ArrayList<>();
		
		cmd.stream().forEach(c -> {
			
			try {
				
				// In case of date period are exist in the screen,
				if (c.getStartDate() != null) {
					
					List<EmpCorpHealthOffHis> existHistLst = existHistMap.get(c.getEmployeeId());
					
					if (existHistLst != null) {
						
						Optional<DateHistoryItem> itemToBeUpdate = existHistLst.get(0).getPeriod().stream()
								
								.filter(h -> h.identifier().equals(c.getHistId())).findFirst();
						
						if (itemToBeUpdate.isPresent()) {
							
							existHistLst.get(0).changeSpan(itemToBeUpdate.get(), new DatePeriod(c.getStartDate(),
									
									c.getEndDate() != null ? c.getEndDate() : GeneralDate.max()));
							
							AffOfficeInformation updateInfo = new AffOfficeInformation(itemToBeUpdate.get().identifier(), new SocialInsuranceOfficeCode(c.getCode()));
							
							domainIntermediates
									.add(new EmpCorpHealthOffHisInter(existHistLst.get(0), itemToBeUpdate.get(), updateInfo));
						
						} else {
							
							sidErrorLst.add(c.getEmployeeId());
						}
						
					} else {
						
						sidErrorLst.add(c.getEmployeeId());
						
					}
					
				}else {
					
					AffOfficeInformation updateInfo = new AffOfficeInformation(c.getHistId(), new SocialInsuranceOfficeCode(c.getCode()));
					
					updateInfos.add(updateInfo);
					
				}

			} catch (BusinessException e) {
				
				MyCustomizeException ex = new MyCustomizeException(e.getMessageId(), Arrays.asList(c.getEmployeeId()), "期間");
				
				errorExceptionLst.add(ex);
			}

		});
		
		if(!domainIntermediates.isEmpty()) {
			
			this.empCorpHealthOffHisService.updateAllCPS003(domainIntermediates);
			
		}
		
		if(!updateInfos.isEmpty()) {
			
			this.affOfficeInformationRepository.updateAll(updateInfos);
		}
		
		if(sidErrorLst.isEmpty()) {
			
			errorExceptionLst.add(new MyCustomizeException("Invalid", sidErrorLst));
		}
		
		return errorExceptionLst;
	}

}
