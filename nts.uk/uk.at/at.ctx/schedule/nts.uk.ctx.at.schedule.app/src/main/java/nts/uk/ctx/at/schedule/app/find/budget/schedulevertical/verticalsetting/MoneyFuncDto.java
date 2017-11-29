package nts.uk.ctx.at.schedule.app.find.budget.schedulevertical.verticalsetting;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.schedule.dom.budget.schedulevertical.verticalsetting.MoneyFunc;

/**
 * TanLV
 *
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
public class MoneyFuncDto {
	/** 会社ID */
	private String companyId;

	/** コード */
	private String verticalCalCd;

	/** 汎用縦計項目ID */
	private String verticalCalItemId;

	/** 順番 */
	private int dispOrder;

	/** 外部予算実績項目コード */
	private String externalBudgetCd;

	/** 勤怠項目ID */
	private String attendanceItemId;

	/** 予定項目ID */
	private String presetItemId;

	/** 演算子区分 */
	private int operatorAtr;
	
	/**
	 * fromDomain
	 * @param func
	 * @return
	 */
	public static MoneyFuncDto fromDomain(MoneyFunc func){
		return new MoneyFuncDto(
				func.getCompanyId(),
				func.getVerticalCalCd(),
				func.getVerticalCalItemId(),
				func.getDispOrder(),
				func.getExternalBudgetCd(),
				func.getAttendanceItemId(),
				func.getPresetItemId(),
				func.getOperatorAtr().value
				);
	}
}
