package nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.flexshortage;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.uk.ctx.at.shared.dom.common.days.AttendanceDaysMonth;

/**
 * フレックス不足の年休補填管理
 * @author HoangNDH
 *
 */
@Getter
@AllArgsConstructor
public class InsufficientFlexHolidayMnt {
	/** 会社ID */
	private String companyId;
	
	/** 補填可能時間 */
	private AttendanceDaysMonth supplementableDays;
	
	public static InsufficientFlexHolidayMnt createFromJavaType(String companyId, double supplementableDays) {
		return new InsufficientFlexHolidayMnt(companyId, new AttendanceDaysMonth(supplementableDays));
	}
	
	/**
	 * 年休補填時間のエラーチェック
	 * @param deductDays 年休控除日数
	 * @return true：正常、false：エラー
	 */
	public boolean checkErrorForSupplementableDays(AttendanceDaysMonth deductDays){
		return (deductDays.v() > this.supplementableDays.v());
	}
}
