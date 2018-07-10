package nts.uk.ctx.at.request.dom.vacation.history.service;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.request.dom.application.common.adapter.record.remainingnumber.AnnualHolidayPlanManaAdapter;
import nts.uk.ctx.at.request.dom.vacation.history.PlanVacationHistory;
import nts.uk.ctx.at.request.dom.vacation.history.VacationHistoryRepository;
import nts.uk.ctx.at.shared.dom.yearholidaygrant.service.Period;

@Stateless
public class PlanVacationRuleExportImpl implements PlanVacationRuleExport{
	@Inject
	private VacationHistoryRepository vacationHisRepos;
	@Inject
	private AnnualHolidayPlanManaAdapter annualAdapter;
	@Override
	public boolean checkMaximumOfPlan(String cid, String employeeId, String workTypeCode, Period dateData) {
		//指定する期間内の計画休暇のルールの履歴を取得する
		//ドメインモデル「計画休暇のルールの履歴」を取得する
		List<PlanVacationHistory> lstVactionPeriod = vacationHisRepos.findByWorkTypeAndPeriod(cid, workTypeCode, dateData);
		if(lstVactionPeriod.isEmpty()) {
			//ドメインモデル「計画休暇のルールの履歴」を全て取得する
			List<PlanVacationHistory> lstVacation = vacationHisRepos.findByWorkTypeCode(cid, workTypeCode);
			if(lstVacation.isEmpty()) {
				return false;
			} else {
				return true;
			}
		} else {
			//申請期間が利用可能な期間外かチェックする
			boolean isOutThePeriod = this.checkOutThePeriod(lstVactionPeriod, dateData);
			if(isOutThePeriod) {
				return true;
			}
			//取得したドメインモデル「計画休暇のルールの履歴」を先頭から最後までループする
			for (PlanVacationHistory data : lstVactionPeriod) {
				if(this.checkMaxPlanSpecification(cid, employeeId, workTypeCode, new Period(data.start(), data.end()), data.getMaxDay().v(), dateData)) {
					return true;
				}
			}
			
			return false;
		}
		
	}
	@Override
	public boolean checkOutThePeriod(List<PlanVacationHistory> lstVactionPeriod, Period dateData) {
		boolean outData = true;
		for(int i = 0; dateData.getStartDate().compareTo(dateData.getEndDate()) + i <= 0; i++){
			GeneralDate loopDate = dateData.getStartDate().addDays(i);
			for (PlanVacationHistory planData : lstVactionPeriod) {
				//ループする日は範囲内かチェックする
				if(loopDate.after(planData.start())
						&& loopDate.before(planData.end())) {
					outData = false;
					break;
				}
			}
			//期間外フラグをチェックする
			if(outData) {
				break;
			}
		}
		return outData;
	}
	@Override
	public boolean checkMaxPlanSpecification(String cid, String employeeId, String workTypeCode, Period checkDate,
			int maxDay, Period appDate) {
		boolean outputData = false;
		//チェック期間による申請期間を編集する
		Period editDate = this.getEditDate(checkDate, appDate);
		//指定する期間の計画年休使用明細を取得する
		List<GeneralDate> lstDateDetail = annualAdapter.lstDetailPeriod(employeeId, workTypeCode, checkDate);
		//使用済の計画年休日数を取得する
		int useDay = this.getUseDays(lstDateDetail, editDate);
		//申請する計画年休日数=申請終了日(編集後)-申請開始日(編集後) + 1日
		//int appDays = application.getStartDate().get().compareTo(application.getEndDate().get())
		int appDays = appDate.getEndDate().compareTo(appDate.getStartDate()) + 1;
		//(使用済の計画年休日数＋申請する計画年休日数)はINPUT．上限日数と比較する
		if(useDay + appDays > maxDay) {
			outputData = true;
		}
		return outputData;
	}
	@Override
	public Period getEditDate(Period checkData, Period appDate) {
		//申請開始日(編集後)=申請開始日、申請終了日(編集後)=申請終了日(初期化)
		Period outputData = new Period(appDate.getStartDate(), appDate.getEndDate());
		//チェック期間、申請期間を比較する
		if(appDate.getStartDate().before(checkData.getStartDate())) {
			outputData.setStartDate(checkData.getStartDate());
		}
		if(appDate.getEndDate().after(checkData.getEndDate())) {
			outputData.setEndDate(checkData.getEndDate());
		}
		return outputData;
	}
	@Override
	public int getUseDays(List<GeneralDate> lstDate, Period dataDate) {
		int outputData = 0;
		if(lstDate.isEmpty()) {
			return outputData;
		}
		for (GeneralDate detailDate : lstDate) {
			//INPUT．申請開始日 <= ループ中の「使用一覧」．使用日 <= INPUT．申請終了日 false
			if(dataDate.getStartDate().after(detailDate)
					|| dataDate.getEndDate().before(detailDate)) {
				outputData += 1;
			}
		}
		return outputData;
	}
	
	

}
