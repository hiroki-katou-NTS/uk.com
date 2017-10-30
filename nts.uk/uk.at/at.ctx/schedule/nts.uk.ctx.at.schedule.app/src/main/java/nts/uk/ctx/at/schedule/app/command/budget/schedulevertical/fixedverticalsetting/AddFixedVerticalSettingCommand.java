package nts.uk.ctx.at.schedule.app.command.budget.schedulevertical.fixedverticalsetting;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
/**
 * 
 * @author phongtq
 *
 */
@Data
@AllArgsConstructor
public class AddFixedVerticalSettingCommand {

	/* 特別休暇コード */
	private int fixedItemAtr;

	List<FixedVerticalSettingCommand> fixedVertical;
}
