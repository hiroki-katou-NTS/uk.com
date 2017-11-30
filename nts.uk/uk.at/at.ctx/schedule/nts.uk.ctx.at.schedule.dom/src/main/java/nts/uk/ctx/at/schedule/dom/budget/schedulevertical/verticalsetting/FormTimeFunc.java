package nts.uk.ctx.at.schedule.dom.budget.schedulevertical.verticalsetting;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.arc.enums.EnumAdaptor;

/**
 * TanLV
 * 時間計算式
 * 
 */
@AllArgsConstructor
@Getter
public class FormTimeFunc {
	/** 会社ID */
    private String companyId;
    
    /** コード*/
    private String verticalCalCd;
    
    /** 汎用縦計項目ID */
    private String verticalCalItemId;
    
    /** 順番 */
    private int dispOrder;
    
    /** 予定項目ID */
    private String presetItemId;
    
    /** 勤怠項目ID */
    private String attendanceItemId;
    
    /** 外部予算実績項目コード */
    private String externalBudgetCd;
    
    /** 演算子区分 */
    private OperatorAtr operatorAtr;
    
    /**
     * Create From JavaType
     * @param companyId
     * @param verticalCalCd
     * @param verticalCalItemId
     * @param dispOrder
     * @param presetItemId
     * @param attendanceItemId
     * @param externalBudgetCd
     * @param operatorAtr
     * @return
     */
	public static FormTimeFunc createFromJavaType(String companyId, String verticalCalCd, String verticalCalItemId, int dispOrder,
			String presetItemId, String attendanceItemId, String externalBudgetCd, int operatorAtr) {
		
		return new FormTimeFunc(companyId, 
				verticalCalCd, 
				verticalCalItemId, 
				dispOrder,
				presetItemId, 
				attendanceItemId, 
				externalBudgetCd,
				EnumAdaptor.valueOf(operatorAtr, OperatorAtr.class)
			);
	}
}
