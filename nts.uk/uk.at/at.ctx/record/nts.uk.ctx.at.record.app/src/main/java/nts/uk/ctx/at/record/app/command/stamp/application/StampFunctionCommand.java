package nts.uk.ctx.at.record.app.command.stamp.application;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class StampFunctionCommand {
	/** 使用区分 */
	private int usrAtr;
	
	/** 表示項目一覧 */
	private List<Integer> lstDisplayItemId;
}
