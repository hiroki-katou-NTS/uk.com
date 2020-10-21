package nts.uk.ctx.sys.portal.app.command.notice;

import lombok.Getter;
import nts.arc.time.GeneralDate;

/**
 * Delete message notice command
 * @author DungDV
 *
 */
@Getter
public class DeleteMessageNoticeCommand {
	
	/** 作成者ID */
	private String creatorID;
	
	/**	入力日 */
	private GeneralDate inputDate;
}
