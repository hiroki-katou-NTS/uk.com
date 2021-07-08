package nts.uk.screen.at.app.query.kdp.kdp002.b;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.ctx.sys.portal.app.query.notice.MsgNoticesDto;

/**
 * 
 * @author chungnt
 *
 */

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ContentOfNotificationByStampDto {

	/**
	 * Map<お知らせメッセージ、作成者> (List)
	 */
	public List<MsgNoticesDto> msgNotices;
	
}
