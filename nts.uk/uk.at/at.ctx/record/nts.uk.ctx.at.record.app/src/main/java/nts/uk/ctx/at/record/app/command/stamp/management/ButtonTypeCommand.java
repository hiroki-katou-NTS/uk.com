package nts.uk.ctx.at.record.app.command.stamp.management;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ButtonTypeCommand {
	/** 予約区分 */
	private int reservationArt;
	
	/** 打刻種類 */
	private StampTypeCommand stampType;
}
