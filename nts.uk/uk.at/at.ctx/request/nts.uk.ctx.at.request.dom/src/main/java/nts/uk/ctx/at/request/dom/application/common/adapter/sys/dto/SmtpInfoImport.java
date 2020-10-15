package nts.uk.ctx.at.request.dom.application.common.adapter.sys.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * refactor 4
 * SMTP情報
 * @author Doan Duy Hung
 *
 */
@AllArgsConstructor
@Getter
public class SmtpInfoImport {
	/** The server. */
	// サーバ
	private String server;
	
	/** The port. */
	// ポート
	private Integer port;
}
