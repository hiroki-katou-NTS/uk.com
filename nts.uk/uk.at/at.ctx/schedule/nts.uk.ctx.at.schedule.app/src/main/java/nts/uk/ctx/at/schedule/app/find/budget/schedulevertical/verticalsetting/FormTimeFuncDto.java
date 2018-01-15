package nts.uk.ctx.at.schedule.app.find.budget.schedulevertical.verticalsetting;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.schedule.dom.budget.schedulevertical.verticalsetting.FormTimeFunc;

/**
 * TanLV
 *
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
public class FormTimeFuncDto {
	/* 会社ID */
    private String companyId;
    
    /*コード*/
    private String verticalCalCd;
    
    /* 汎用縦計項目ID */
    private String verticalCalItemId;
    
    /* 予定項目ID */
    private String presetItemId;
    
    /* 勤怠項目ID */
    private String attendanceItemId;
    
    /* 外部予算実績項目コード */
    private String externalBudgetCd;
    
    /* 演算子区分 */
    private int operatorAtr;
    
    /* 順番 */
    private int dispOrder;

    /**
     * FormTimeFuncDto
     * @param domain
     * @return
     */
	public static FormTimeFuncDto fromDomain(FormTimeFunc domain) {
		return new FormTimeFuncDto(
				domain.getCompanyId(),
				domain.getVerticalCalCd(),
				domain.getVerticalCalItemId(),
				domain.getPresetItemId(),
				domain.getAttendanceItemId(),
				domain.getExternalBudgetCd(),
				domain.getOperatorAtr().value,
				domain.getDispOrder()
			);
	}
}
