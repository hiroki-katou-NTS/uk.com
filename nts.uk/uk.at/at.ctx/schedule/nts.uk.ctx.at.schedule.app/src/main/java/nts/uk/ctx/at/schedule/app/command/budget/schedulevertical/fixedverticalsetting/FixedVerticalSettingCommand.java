package nts.uk.ctx.at.schedule.app.command.budget.schedulevertical.fixedverticalsetting;

import lombok.AllArgsConstructor;
import lombok.Data;
import nts.uk.ctx.at.schedule.dom.budget.schedulevertical.fixedverticalsetting.FixedVertical;
/**
 * 
 * @author phongtq
 *
 */
@Data
@AllArgsConstructor
public class FixedVerticalSettingCommand {

	/* 付与基準日 */
	private int useAtr;

	/* 付与基準日 */
	private int fixedItemAtr;

	public FixedVertical toDomain(String companyId){
		return FixedVertical.createFromJavaType(companyId, this.fixedItemAtr, this.useAtr);
	}
}
