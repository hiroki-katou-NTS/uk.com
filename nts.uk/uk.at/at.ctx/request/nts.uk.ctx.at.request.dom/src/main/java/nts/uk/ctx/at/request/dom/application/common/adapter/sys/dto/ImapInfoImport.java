package nts.uk.ctx.at.request.dom.application.common.adapter.sys.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * refactor 4
 * IMAP情報
 * @author Doan Duy Hung
 *
 */
@AllArgsConstructor
@Getter
public class ImapInfoImport {
	/** The server. */
	// サーバ
	private String server;

	/** The use server. */
	// サーバ使用
	private Integer useServer;

	/** The port. */
	// ポート
	private Integer port;
}
