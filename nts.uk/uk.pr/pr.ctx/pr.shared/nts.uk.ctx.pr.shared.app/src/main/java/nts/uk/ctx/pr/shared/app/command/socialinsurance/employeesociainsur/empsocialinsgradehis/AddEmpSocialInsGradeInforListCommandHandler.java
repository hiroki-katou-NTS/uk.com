package nts.uk.ctx.pr.shared.app.command.socialinsurance.employeesociainsur.empsocialinsgradehis;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.error.BusinessException;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.arc.layer.app.command.CommandHandlerWithResult;
import nts.arc.time.GeneralDate;
import nts.gul.text.IdentifierUtil;
import nts.uk.ctx.pr.shared.dom.socialinsurance.employeesociainsur.empsocialinsgradehis.CalculationAtr;
import nts.uk.ctx.pr.shared.dom.socialinsurance.employeesociainsur.empsocialinsgradehis.EmpSocialInsGrade;
import nts.uk.ctx.pr.shared.dom.socialinsurance.employeesociainsur.empsocialinsgradehis.EmpSocialInsGradeHis;
import nts.uk.ctx.pr.shared.dom.socialinsurance.employeesociainsur.empsocialinsgradehis.EmpSocialInsGradeHisInter;
import nts.uk.ctx.pr.shared.dom.socialinsurance.employeesociainsur.empsocialinsgradehis.EmpSocialInsGradeInfo;
import nts.uk.ctx.pr.shared.dom.socialinsurance.employeesociainsur.empsocialinsgradehis.EmpSocialInsGradeRepository;
import nts.uk.ctx.pr.shared.dom.socialinsurance.employeesociainsur.empsocialinsgradehis.EmpSocialInsGradeService;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.history.YearMonthHistoryItem;
import nts.arc.time.calendar.period.YearMonthPeriod;
import nts.uk.shr.pereg.app.command.MyCustomizeException;
import nts.uk.shr.pereg.app.command.PeregAddListCommandHandler;

@Stateless
public class AddEmpSocialInsGradeInforListCommandHandler
		extends CommandHandlerWithResult<List<AddEmpSocialInsGradeInforCommand>, List<MyCustomizeException>>
		implements PeregAddListCommandHandler<AddEmpSocialInsGradeInforCommand> {

	@Inject
	private EmpSocialInsGradeRepository repository;

	@Inject
	private EmpSocialInsGradeService service;

	@Override
	public String targetCategoryCd() {
		
		return "CS00092";
		
	}

	@Override
	public Class<?> commandClass() {
		
		return AddEmpSocialInsGradeInforCommand.class;
		
	}

	@Override
	protected List<MyCustomizeException> handle(CommandHandlerContext<List<AddEmpSocialInsGradeInforCommand>> context) {

		List<AddEmpSocialInsGradeInforCommand> command = context.getCommand();

		String cid = AppContexts.user().companyId();

		Map<String, String> recordIds = new HashMap<>();
		
		List<MyCustomizeException> result = new ArrayList<>();

		List<EmpSocialInsGradeHisInter> domains = new ArrayList<>();

		List<String> sids = command.stream().map(c -> c.getEmployeeId()).collect(Collectors.toList());

		Map<String, EmpSocialInsGrade> histBySidsMap = repository.getBySidsAndCid(cid, sids);

		command.stream().forEach(c -> {
			try {

				String newHistID = IdentifierUtil.randomUniqueId();

				YearMonthHistoryItem dateItem = new YearMonthHistoryItem(newHistID,

						new YearMonthPeriod(
								c.getStartYM() != null ? c.getStartYM().yearMonth() : GeneralDate.min().yearMonth(),

								c.getEndYM() != null ? c.getEndYM().yearMonth() : GeneralDate.max().yearMonth()));

				EmpSocialInsGrade existHist = histBySidsMap.get(c.getEmployeeId());

				EmpSocialInsGradeHis itemToBeAdded = new EmpSocialInsGradeHis(cid, c.getEmployeeId(),
						new ArrayList<>());

				EmpSocialInsGradeInfo info = new EmpSocialInsGradeInfo(newHistID,

						c.getSocInsMonthlyRemune() == null? 0: c.getSocInsMonthlyRemune().intValue(),

						c.getCalculationAtr() == null? CalculationAtr.SCHEDULED.value:  c.getCalculationAtr().intValue(),

						c.getHealInsStandMonthlyRemune() != null ? c.getHealInsStandMonthlyRemune().intValue() : null,

						c.getHealInsGrade() != null ? c.getHealInsGrade().intValue() : null,

						c.getPensionInsStandCompenMonthly() != null ? c.getPensionInsStandCompenMonthly().intValue() : null,

						c.getPensionInsGrade() != null ? c.getPensionInsGrade().intValue() : null);

				if (existHist != null) {

					itemToBeAdded = existHist.getHistory();

				}

				itemToBeAdded.add(dateItem);

				domains.add(new EmpSocialInsGradeHisInter(itemToBeAdded, info , null));

				recordIds.put(c.getEmployeeId(), newHistID);

			} catch (BusinessException e) {
				
				MyCustomizeException ex = new MyCustomizeException(e.getMessageId(), Arrays.asList(c.getEmployeeId()),
						"期間");
				
				result.add(ex);
				
			}

		});

		if (!domains.isEmpty()) {

			service.addAll(domains);

		}

		if (!recordIds.isEmpty()) {

			result.add(new MyCustomizeException("NOERROR", recordIds));

		}

		return result;

	}

}
