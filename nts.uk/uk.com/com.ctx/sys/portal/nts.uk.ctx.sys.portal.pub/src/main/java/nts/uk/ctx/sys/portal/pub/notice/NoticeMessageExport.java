package nts.uk.ctx.sys.portal.pub.notice;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author thanh_nx
 *
 *         知らせメッセージExport
 */
@AllArgsConstructor
@Getter
public class NoticeMessageExport {

	// メッセージ表示順
	private int displayOrder;

	// メッセージの内容
	private String content;
}
