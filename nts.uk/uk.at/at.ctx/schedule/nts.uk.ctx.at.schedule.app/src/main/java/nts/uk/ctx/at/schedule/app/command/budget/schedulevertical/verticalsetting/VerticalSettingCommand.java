package nts.uk.ctx.at.schedule.app.command.budget.schedulevertical.verticalsetting;

import java.util.List;
import java.util.stream.Collectors;

import lombok.AllArgsConstructor;
import lombok.Data;
import nts.arc.enums.EnumAdaptor;
import nts.uk.ctx.at.schedule.dom.budget.schedulevertical.verticalsetting.Attributes;
import nts.uk.ctx.at.schedule.dom.budget.schedulevertical.verticalsetting.CalculateAtr;
import nts.uk.ctx.at.schedule.dom.budget.schedulevertical.verticalsetting.CumulativeAtr;
import nts.uk.ctx.at.schedule.dom.budget.schedulevertical.verticalsetting.DisplayAtr;
import nts.uk.ctx.at.schedule.dom.budget.schedulevertical.verticalsetting.Rounding;
import nts.uk.ctx.at.schedule.dom.budget.schedulevertical.verticalsetting.VerticalCalItem;
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
    
    private List<VerticalCalItemCommand> verticalCalItems;

	public VerticalCalSet toDomain() {
		String companyId = AppContexts.user().companyId();
		
		List<VerticalCalItem> items = this.verticalCalItems.stream().map(x-> {
			return new VerticalCalItem(companyId, x.getVerticalCalCd(), x.getItemId(), x.getItemName(), 
					EnumAdaptor.valueOf(x.getCalculateAtr(), CalculateAtr.class),
					EnumAdaptor.valueOf(x.getDisplayAtr(), DisplayAtr.class),
					EnumAdaptor.valueOf(x.getCumulativeAtr(), CumulativeAtr.class),	
					EnumAdaptor.valueOf(x.getAttributes(), Attributes.class),
					EnumAdaptor.valueOf(x.getRounding(), Rounding.class),
					x.getDispOrder(),
					x.getFormPeople().toDomainFormPeople(companyId, companyId, x.getItemId()),
					x.getFormulaAmount().toDomainFormAmount(companyId, companyId, x.getVerticalCalCd()));
			}).collect(Collectors.toList());
		
		return VerticalCalSet.createFromJavaType(AppContexts.user().companyId(), this.verticalCalCd,
				this.verticalCalName, this.unit, this.useAtr, this.assistanceTabulationAtr, items);
	}
}
