package nts.uk.ctx.at.request.app.command.applicationreason;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ApplicationReasonCommand {
	/**
	 * 申請種類
	 */
	private int appType;
	
	/** 理由ID */
	private String reasonID;
	/**
	 * 表示順
	 */
	private int dispOrder;
	
	/** 定型理由 */
	private String reasonTemp;
	/**
	 * 既定
	 */
	private int defaultFlg;
}
