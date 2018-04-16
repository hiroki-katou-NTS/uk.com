package nts.uk.ctx.at.record.dom.monthlyprocess.aggr.work;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.Setter;
import nts.uk.ctx.at.record.dom.monthly.AttendanceTimeOfMonthly;
import nts.uk.ctx.at.record.dom.monthly.agreement.AgreementTimeOfManagePeriod;

/*
 * 戻り値：ドメインサービス：月別実績を集計する．集計処理
 */
@Getter
public class AggregateMonthlyRecordValue {

	/** 月別実績の勤怠時間 */
	private List<AttendanceTimeOfMonthly> attendanceTimeList;

	/** 管理時間の36協定時間 */
	private List<AgreementTimeOfManagePeriod> agreementTimeList;
	
	/** エラー有無 */
	@Setter
	private boolean error;
	
	/*
	 * コンストラクタ
	 */
	public AggregateMonthlyRecordValue(){
		
		this.attendanceTimeList = new ArrayList<>();
		this.agreementTimeList = new ArrayList<>();
		this.error = false;
	}
}
