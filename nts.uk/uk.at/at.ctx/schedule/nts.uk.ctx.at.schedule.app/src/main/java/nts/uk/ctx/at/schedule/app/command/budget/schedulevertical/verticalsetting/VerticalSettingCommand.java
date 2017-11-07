package nts.uk.ctx.at.schedule.app.command.budget.schedulevertical.verticalsetting;

import java.util.List;
import java.util.stream.Collectors;

import lombok.AllArgsConstructor;
import lombok.Data;
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
			VerticalCalItem domain = VerticalCalItem.createFromJavatype(companyId, x.getVerticalCalCd(), x.getItemId(), x.getItemName(), 
					x.getCalculateAtr(),
					x.getDisplayAtr(),
					x.getCumulativeAtr(),	
					x.getAttributes(),
					x.getRounding(),
					x.getDispOrder(),
					x.getFormPeople() != null ? x.getFormPeople().toDomainFormPeople(companyId, x.getVerticalCalCd(), x.getItemId()) : null,
					x.getFormulaAmount() !=null ? x.getFormulaAmount().toDomainFormAmount(companyId, companyId, x.getItemId()) : null,
					x.getFormulaNumerical() !=null ? x.getFormulaNumerical().toDomainNumerical(companyId, companyId, x.getItemId()) : null);
					
			domain.validate();
			domain.validate(x.getDispOrder());
			return domain;
			}).collect(Collectors.toList());
		
		return VerticalCalSet.createFromJavaType(AppContexts.user().companyId(), this.verticalCalCd,
				this.verticalCalName, this.unit, this.useAtr, this.assistanceTabulationAtr, items);
	}
}
