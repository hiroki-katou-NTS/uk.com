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
public class AddVerticalTimeSettingCommand {
	
	/* 特別休暇コード */
	private int fixedVerticalNo;
	
	/* 表示区分 */
	public int displayAtr;
	
	/* 時刻 */
	public int startClock;
	
	public VerticalTime toDomain(String companyId){
		return VerticalTime.createFromJavaType(companyId, this.fixedVerticalNo, this.displayAtr, this.startClock);
	}
}
