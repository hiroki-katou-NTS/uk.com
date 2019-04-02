package nts.uk.ctx.at.shared.dom.remainingnumber.yearholiday.employeeinfo.remaininggrantdata.checklastgrantnumber;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.shared.dom.remainingnumber.annualleave.empinfo.grantremainingdata.AnnualLeaveGrantRemainingData;
import nts.uk.ctx.at.shared.dom.remainingnumber.yearholiday.employeeinfo.remaininggrantdata.checklastgrantnumber.calcumulativegrant.CalCumulativeGrant;
import nts.uk.ctx.at.shared.dom.remainingnumber.yearholiday.employeeinfo.remaininggrantdata.lastgrantedinfo.LastGrantedInfo;
/**
 * 前回付与数が指定した閾値以上かチェック
 * @author tutk
 *
 */
@Stateless
public class CheckLastgrantNumber {
	@Inject
	private LastGrantedInfo lastGrantedInfo;
	
	@Inject
	private CalCumulativeGrant calCumulativeGrant;
	
	public boolean checkLastgrantNumber(String employeeId,Double numberDayAward,GeneralDate baseDate) {
		
		//前回付与情報を取得
		Optional<AnnualLeaveGrantRemainingData> annualLeaveGrantRemainingData = lastGrantedInfo.getLastGrantedInfo(employeeId,baseDate);
		//実行結果 = 取得失敗
		Double grantedNumber = 0.0;
		//実行結果 = 取得成功
		if(annualLeaveGrantRemainingData.isPresent()) {
			grantedNumber = annualLeaveGrantRemainingData.get().getDetails().getGrantNumber().getDays().v();
			//付与数 < INPUT．閾値
			if(grantedNumber<numberDayAward) {
				grantedNumber =  calCumulativeGrant.getCalCumulativeGrant(annualLeaveGrantRemainingData.get(), numberDayAward).getNumberGrantDay();
			}
		}
		
		//比較結果を返す
		if(numberDayAward<=grantedNumber)
			return true;
		return false;
	}
}
