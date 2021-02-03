package nts.uk.screen.at.app.query.kdp.kdp003.r;

import lombok.Builder;
import lombok.Data;
import nts.uk.ctx.sys.portal.app.query.notice.MessageNoticeDto;

/**
 * ・Map<お知らせメッセージ, 作成者コード, 作成者>(List)
 * 
 * @author tutt
 *
 */
@Data
@Builder
public class MsgNoticeDto {

	// お知らせメッセージ
	private MessageNoticeDto message;
	
	// 作成者コード
	private String scd;

	// 作成者
	private String bussinessName;

}
