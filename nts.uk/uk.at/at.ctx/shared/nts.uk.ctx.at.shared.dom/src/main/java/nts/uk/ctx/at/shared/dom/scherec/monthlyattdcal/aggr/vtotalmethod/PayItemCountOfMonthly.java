package nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.aggr.vtotalmethod;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.Setter;
import lombok.val;
import nts.arc.layer.dom.AggregateRoot;
import nts.uk.ctx.at.shared.dom.worktype.WorkTypeCode;

/**
 * 月別実績の給与項目カウント
 * @author shuichu_ishida
 */
@Getter
@Setter
public class PayItemCountOfMonthly extends AggregateRoot {

	/** 会社ID */
	private String companyId;
	/** 給与出勤日数 */
	private List<WorkTypeCode> payAttendanceDays;
	/** 給与欠勤日数 */
	private List<WorkTypeCode> payAbsenceDays;
	
	/**
	 * コンストラクタ
	 * @param companyId 会社ID
	 */
	public PayItemCountOfMonthly(String companyId){
		
		super();
		this.companyId = companyId;
		this.payAttendanceDays = new ArrayList<>();
		this.payAbsenceDays = new ArrayList<>();
	}
	
	/**
	 * ファクトリー
	 * @param companyId 会社ID
	 * @param payAttendanceDays 給与出勤日数
	 * @param payAbsenceDays 給与欠勤日数
	 * @return 月別実績の給与項目カウント
	 */
	public static PayItemCountOfMonthly of(
			String companyId,
			List<WorkTypeCode> payAttendanceDays,
			List<WorkTypeCode> payAbsenceDays){
		
		val domain = new PayItemCountOfMonthly(companyId);
		domain.payAttendanceDays = payAttendanceDays;
		domain.payAbsenceDays = payAbsenceDays;
		return domain;
	}
	
	/**
	 * 日数の取得処理
	 * @param payItemCountAtr 項目区分
	 * @param workTypeCode 勤務種類コード
	 * @return 日数
	 */
	public Double getDays(PayItemCountAtr payItemCountAtr, WorkTypeCode workTypeCode){
		
		Double returnValue = 0.0;
		
		if (payItemCountAtr == PayItemCountAtr.PAY_ATTENDANCE_DAYS){
			if (this.payAttendanceDays.contains(workTypeCode)) returnValue = 1.0;
		}
		if (payItemCountAtr == PayItemCountAtr.PAY_ABSENCE_DAYS){
			if (this.payAbsenceDays.contains(workTypeCode)) returnValue = 1.0;
		}
		return returnValue;
	}
}
