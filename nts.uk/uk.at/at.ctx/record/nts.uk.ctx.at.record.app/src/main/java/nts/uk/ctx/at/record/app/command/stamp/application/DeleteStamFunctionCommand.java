package nts.uk.ctx.at.record.app.command.stamp.application;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DeleteStamFunctionCommand {
	/** 表示項目一覧 */
	private int displayItemId;
}
