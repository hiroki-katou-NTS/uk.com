package nts.uk.screen.at.app.dailymodify.query;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.shared.dom.attendance.util.item.ItemValue;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DailyModifyQuery {

	private String employeeId;
	
	private GeneralDate baseDate;
	
	private List<ItemValue> itemValues;
}
