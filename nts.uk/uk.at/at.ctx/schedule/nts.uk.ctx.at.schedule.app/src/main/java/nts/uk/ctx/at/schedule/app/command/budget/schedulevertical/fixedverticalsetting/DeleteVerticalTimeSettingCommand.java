package nts.uk.ctx.at.schedule.app.command.budget.schedulevertical.fixedverticalsetting;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class DeleteVerticalTimeSettingCommand {
	/* 特別休暇コード */
	private int fixedItemAtr;
	
	private int startClock;
}
