package nts.uk.ctx.at.schedule.dom.budget.schedulevertical.verticalsetting;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class VerticalCalSet {
	/* 会社ID */
    private String companyId;
    
    /*コード*/
    private String verticalCalCd;
    
    /*名称*/
    private String verticalCalName;
    
    /*単位*/
    private Unit unit;
    
    /*利用区分*/
    private UseAtr useAtr;
    
    /*応援集計区分*/
    private AssistanceTabulationAtr assistanceTabulationAtr;
}
