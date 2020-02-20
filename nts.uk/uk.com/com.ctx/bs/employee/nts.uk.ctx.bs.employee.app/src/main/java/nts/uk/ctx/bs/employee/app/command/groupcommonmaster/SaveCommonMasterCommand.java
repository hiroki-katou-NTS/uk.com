package nts.uk.ctx.bs.employee.app.command.groupcommonmaster;

import java.util.List;

import lombok.Getter;

/**
 * 
 * @author sonnlb
 *
 */
@Getter
public class SaveCommonMasterCommand {
	// 画面モード
	private boolean newMode;

	private String commonMasterId;

	private List<UpdateMasterItemCommand> commonMasterItems;

	private UpdateMasterItemCommand selectedCommonMasterItem;
}
