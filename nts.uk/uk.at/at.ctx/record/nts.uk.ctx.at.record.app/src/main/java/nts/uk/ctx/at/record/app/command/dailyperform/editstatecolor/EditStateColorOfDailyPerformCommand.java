package nts.uk.ctx.at.record.app.command.dailyperform.editstatecolor;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;
import nts.uk.ctx.at.record.dom.editstate.EditStateOfDailyPerformance;
@Data
public class EditStateColorOfDailyPerformCommand {
	private List<EditStateOfDailyPerformance> data = new ArrayList<>();
}
