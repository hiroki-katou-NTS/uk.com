package nts.uk.ctx.at.schedule.dom.budget.schedulevertical.verticalsetting;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.arc.enums.EnumAdaptor;
/**
 * 金額計算関数
 * @author phongtq
 *
 */
@AllArgsConstructor
@Getter
public class MoneyFunc {
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
	private OperatorAtr operatorAtr;

	
	public static MoneyFunc createFromJavatype(String companyId, String verticalCalCd, String verticalCalItemId,
			int dispOrder, String externalBudgetCd, String attendanceItemId, String presetItemId, int operatorAtr) {
		return new MoneyFunc(companyId, verticalCalCd, verticalCalItemId, dispOrder, externalBudgetCd, attendanceItemId,
				presetItemId, EnumAdaptor.valueOf(operatorAtr, OperatorAtr.class));
	}
}
