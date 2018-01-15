package nts.uk.ctx.at.schedule.dom.budget.schedulevertical.verticalsetting;

import java.util.List;

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
public class FormTime {
	/** 会社ID */
    private String companyId;
    
    /** コード*/
    private String verticalCalCd;
    
    /** 汎用縦計項目ID */
    private String verticalCalItemId;
    
    /** カテゴリ区分 */
    private CategoryIndicator categoryIndicator;
    
    /** 実績表示区分 */
    private ActualDisplayAtr actualDisplayAtr;
    
    private List<FormTimeFunc> lstFormTimeFunc;

    /**
     * Create From Javatype
     * @param companyId
     * @param verticalCalCd
     * @param verticalCalItemId
     * @param categoryIndicator
     * @param actualDisplayAtr
     * @param lstFormTimeFunc
     * @return
     */
	public static FormTime createFromJavaType(String companyId, String verticalCalCd, String verticalCalItemId,
			int categoryIndicator, int actualDisplayAtr, List<FormTimeFunc> lstFormTimeFunc) {
		
		return new FormTime(companyId, verticalCalCd, verticalCalItemId, 
				EnumAdaptor.valueOf(categoryIndicator, CategoryIndicator.class), 
				EnumAdaptor.valueOf(actualDisplayAtr, ActualDisplayAtr.class),
				lstFormTimeFunc);
	}
}
