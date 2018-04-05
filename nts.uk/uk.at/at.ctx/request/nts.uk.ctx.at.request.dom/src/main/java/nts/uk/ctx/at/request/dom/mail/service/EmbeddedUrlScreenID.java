package nts.uk.ctx.at.request.dom.mail.service;

import lombok.Getter;

public class EmbeddedUrlScreenID {
	// プログラムID
	@Getter
	private String programId;
	// 遷移先ID
	@Getter
	private String destinationId;
	public EmbeddedUrlScreenID(String programId, String destinationId) {
		this.programId = programId;
		this.destinationId = destinationId;
	}
}
