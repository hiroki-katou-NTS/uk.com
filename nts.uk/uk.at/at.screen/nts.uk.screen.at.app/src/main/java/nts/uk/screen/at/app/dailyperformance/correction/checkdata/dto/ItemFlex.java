package nts.uk.screen.at.app.dailyperformance.correction.checkdata.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.shared.dom.attendance.util.item.ItemValue;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ItemFlex {
	// フレックス不足時間
	private ItemValue value18;
	private ItemValue value21;
	private ItemValue value189;
	private ItemValue value190;
	private ItemValue value191;
}
