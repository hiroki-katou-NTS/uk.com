package nts.uk.ctx.at.schedule.app.command.budget.schedulevertical.verticalsetting;

import lombok.AllArgsConstructor;
import lombok.Data;
import nts.uk.ctx.at.schedule.dom.budget.schedulevertical.verticalsetting.VerticalCalSet;
import nts.uk.shr.com.context.AppContexts;

/**
 * 
 * @author tanlv
 *
 */

@Data
@AllArgsConstructor
public class VerticalSettingCommand {
	/* 会社ID */
    private String companyId;
    
    /*コード*/
    private String verticalCalCd;
    
    /*名称*/
    private String verticalCalName;
    
    /*単位*/
    private int unit;
    
    /*利用区分*/
    private int useAtr;
    
    /*応援集計区分*/
    private int assistanceTabulationAtr;

	public VerticalCalSet toDomain() {
		return VerticalCalSet.createFromJavaType(AppContexts.user().companyId(), this.verticalCalCd,
				this.verticalCalName, this.unit, this.useAtr, this.assistanceTabulationAtr);
	}
}
