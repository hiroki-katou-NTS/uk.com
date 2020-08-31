package nts.uk.screen.at.app.ksu.ksu001q.query;

import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.GeneralDate;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.schedule.dom.workschedule.budgetcontrol.budgetperformance.ExtBudgetActItemCode;
import nts.uk.ctx.at.schedule.dom.workschedule.budgetcontrol.budgetperformance.ExtBudgetDaily;
import nts.uk.ctx.at.schedule.dom.workschedule.budgetcontrol.budgetperformance.ExtBudgetDailyRepository;
import nts.uk.ctx.at.shared.dom.workrule.organizationmanagement.workplace.TargetOrgIdenInfor;

/**
 * 期間中の外部予算日次を取得する
 * UKDesign.ドメインモデル.NittsuSystem.UniversalK.就業.contexts.勤務予定.勤務予定.予算管理.App.
 * 
 * @author thanhlv
 *
 */
@Stateless
public class DailyExternalBudgetQuery {

	/** 日次の外部予算実績Repository */
	@Inject
	private ExtBudgetDailyRepository extBudgetDailyRepository;

	/**
	 * 期間の日次の外部予算実績を取得する
	 * 
	 * @param dailyExternal
	 * @return
	 */
	public List<DailyExternalBudgetDto> getDailyExternalBudget(DailyExternalBudget dailyExternal) {

		TargetOrgIdenInfor targetOrg = ("1").equals(dailyExternal.getUnit())
				? TargetOrgIdenInfor.creatIdentifiWorkplaceGroup(dailyExternal.getId())
				: TargetOrgIdenInfor.creatIdentifiWorkplace(dailyExternal.getId());

		DatePeriod datePeriod = new DatePeriod(GeneralDate.fromString(dailyExternal.getStartDate(), "yyyy/MM/dd"),
				GeneralDate.fromString(dailyExternal.getEndDate(), "yyyy/MM/dd"));

		// 期間の日次の外部予算実績を取得する
		List<ExtBudgetDaily> extBudgetDailies = extBudgetDailyRepository.getDailyExtBudgetResultsForPeriod(targetOrg,
				datePeriod, new ExtBudgetActItemCode(dailyExternal.getItemCode()));

		// 値（項目）
		if (dailyExternal.getUnit().equals("1")) {
			return extBudgetDailies.stream()
					.filter(item -> dailyExternal.getId().equals(item.getTargetOrg().getWorkplaceGroupId().get()))
					.map(x -> {
						DailyExternalBudgetDto budgetDto = new DailyExternalBudgetDto();
						budgetDto.setValue(x.getActualValue().toString());
						budgetDto.setDate(x.getYmd().toString());
						return budgetDto;
					}).collect(Collectors.toList());
		}

		return extBudgetDailies.stream()
				.filter(item -> dailyExternal.getId().equals(item.getTargetOrg().getWorkplaceId().get())).map(x -> {
					DailyExternalBudgetDto budgetDto = new DailyExternalBudgetDto();
					budgetDto.setValue(x.getActualValue().toString());
					budgetDto.setDate(x.getYmd().toString());
					return budgetDto;
				}).collect(Collectors.toList());

	}
}
