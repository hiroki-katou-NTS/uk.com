package nts.uk.ctx.at.schedule.app.command.budget.schedulevertical.fixedverticalsetting;


import lombok.AllArgsConstructor;
import lombok.Data;
import nts.uk.ctx.at.schedule.dom.budget.schedulevertical.fixedverticalsetting.VerticalTime;
/**
 * 
 * @author phongtq
 *
 */
@Data
@AllArgsConstructor
public class VerticalTimeSettingCommand {
	/** 付与基準日 **/
	private int fixedItemAtr;
	
	/** 表示区分 **/
	public int displayAtr;

	public int verticalTimeNo;
	
	/** 時刻 **/
	public int startClock;
	
	public VerticalTime toDomain(String companyId, int fixedItemAtr){
		return VerticalTime.createFromJavaType(companyId, fixedItemAtr, this.verticalTimeNo, this.displayAtr, this.startClock);
	}
}
