package nts.uk.ctx.sys.portal.app.command.notice;

import lombok.Getter;
import nts.arc.time.GeneralDateTime;
import nts.uk.ctx.sys.portal.app.query.notice.MessageNoticeDto;

/**
 * Update message notice command
 * @author DungDV
 *
 */
@Getter
public class UpdateMessageNoticeCommand {
	
	/** 作成者ID */
	private String creatorId;
	
	/** 入力日 */
	private GeneralDateTime inputDate;
	
	/** お知らせメッセージ */
	private MessageNoticeDto messageNotice;
}
