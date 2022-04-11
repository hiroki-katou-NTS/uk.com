package nts.uk.ctx.at.function.dom.employmentinfoterminal.infoterminal.message;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author thanh_nx
 *
 *         知らせメッセージImport
 */
@AllArgsConstructor
@Getter
public class NoticeMessageImport {

	// メッセージ表示順
	private int displayOrder;

	// メッセージの内容
	private String content;
	
}
