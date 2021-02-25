package nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.agreement.management.limitrule;

import org.apache.commons.lang3.tuple.Pair;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.agreement.management.onemonth.AgreementOneMonthTime;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.agreement.management.onemonth.OneMonthErrorAlarmTime;

/** ３６協定複数月平均 */
@Getter
@AllArgsConstructor
public class AgreementMultiMonthAvg {

	/** 複数月平均 */
	private OneMonthErrorAlarmTime multiMonthAvg;
	
	public AgreementMultiMonthAvg() {
		this.multiMonthAvg = new OneMonthErrorAlarmTime();
	}
	
	/** エラー時間を超えているか */
	public Pair<Boolean, AgreementOneMonthTime> isErrorTimeOver(AgreementOneMonthTime applyTime) {
		
		/** ＠エラーアラーム時間.エラー時間を超えているか(申請時間) */
		return multiMonthAvg.isErrorTimeOver(applyTime);
	}
	
	/** アラーム時間を計算する */
	public AgreementOneMonthTime calcAlarmTime(AgreementOneMonthTime applyTime) {
		
		/** ＠エラーアラーム時間.アラーム時間を計算する(申請時間) */
		return multiMonthAvg.calcAlarmTime(applyTime);
	}
}
