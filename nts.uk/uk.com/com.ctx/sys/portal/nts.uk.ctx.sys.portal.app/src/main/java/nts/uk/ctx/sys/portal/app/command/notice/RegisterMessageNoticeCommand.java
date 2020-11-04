package nts.uk.ctx.sys.portal.app.command.notice;

import lombok.Getter;
import nts.uk.ctx.sys.portal.app.query.notice.MessageNoticeDto;

/**
 * Create message notice command
 * @author DungDV
 *
 */
@Getter
public class RegisterMessageNoticeCommand {
	
	/** 作成者ID */
	private String creatorID;
	
	/** お知らせメッセージ */
	private MessageNoticeDto messageNotice;
	
}
