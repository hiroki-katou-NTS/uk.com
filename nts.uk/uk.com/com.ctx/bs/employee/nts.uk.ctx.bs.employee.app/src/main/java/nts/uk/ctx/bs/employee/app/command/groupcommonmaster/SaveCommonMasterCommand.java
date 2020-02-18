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
	private boolean isNewMode;

	private String commonMasterId;

	private List<UpdateMasterItemCommand> listMasterItem;

	private UpdateMasterItemCommand saveItem;
}
