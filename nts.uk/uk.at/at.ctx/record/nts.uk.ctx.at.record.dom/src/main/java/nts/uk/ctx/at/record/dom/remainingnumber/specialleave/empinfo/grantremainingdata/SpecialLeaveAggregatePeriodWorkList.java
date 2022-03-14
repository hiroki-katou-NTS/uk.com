package nts.uk.ctx.at.record.dom.remainingnumber.specialleave.empinfo.grantremainingdata;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.val;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.record.dom.remainingnumber.specialleave.export.SpecialLeaveManagementService;
import nts.uk.ctx.at.shared.dom.remainingnumber.specialleave.service.SpecialHolidayInterimMngData;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.vacation.GrantBeforeAfterAtr;

/**
 * 特別休暇集計期間WORKリスト
 * @author hayata_maekawa
 *
 */
@Getter
@AllArgsConstructor
public class SpecialLeaveAggregatePeriodWorkList {

	private List<SpecialLeaveAggregatePeriodWork> periodWorkList;
	
	
	/**
	 * 残数処理
	 * @param require
	 * @param companyId
	 * @param employeeId
	 * @param specialHolidayInterimMngData
	 * @param specialLeaveCode
	 * @param entryDate
	 * @param baseDate
	 * @param specialLeaveInfo
	 * @return
	 */
	public InPeriodOfSpecialLeaveResultInfor remainNumberProcess(SpecialLeaveManagementService.RequireM5 require,
			String companyId, String employeeId, SpecialHolidayInterimMngData specialHolidayInterimMngData,
			int specialLeaveCode, GeneralDate entryDate, GeneralDate baseDate, SpecialLeaveInfo specialLeaveInfo) {
		
		InPeriodOfSpecialLeaveResultInfor outputData = new InPeriodOfSpecialLeaveResultInfor();
		
		
		// 特別休暇集計期間でループ
		for (val aggregatePeriodWork : this.periodWorkList){

			//残数処理
			outputData = specialLeaveInfo.remainNumberProcess(require, companyId, employeeId, 
					aggregatePeriodWork, specialHolidayInterimMngData, outputData, specialLeaveCode,
					entryDate, baseDate,isNextGrantPeriodAtr(aggregatePeriodWork, entryDate));			
		}
		return outputData;
	}
	
	
	
	/**
	 * 次の期間の付与前後を判断
	 * @param periodWorkList
	 * @param entryDate
	 * @return
	 */
	private GrantBeforeAfterAtr isNextGrantPeriodAtr(SpecialLeaveAggregatePeriodWork periodWork,
			GeneralDate entryDate){
		if(periodWork.getEndDay().isNextPeriodEndAtr()){
			return periodWork.judgeGrantPeriodAtr(entryDate);
		}
		
		return periodWorkList.get(periodWorkList.indexOf(periodWork)+1).judgeGrantPeriodAtr(entryDate);
	}
	
}
