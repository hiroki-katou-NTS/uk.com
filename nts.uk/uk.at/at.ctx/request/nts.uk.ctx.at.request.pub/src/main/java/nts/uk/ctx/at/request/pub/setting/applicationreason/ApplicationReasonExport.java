package nts.uk.ctx.at.request.pub.setting.applicationreason;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author ThanhNX
 *
 *         申請定型理由 export
 */
@Data
@AllArgsConstructor
public class ApplicationReasonExport {

	/**
	 * 会社Iｄ
	 */
	public String companyId;
	/**
	 * 申請種類
	 */
	public Integer appType;

	/** 理由ID */
	public String reasonID;
	/**
	 * 表示順
	 */
	public int dispOrder;

	/** 定型理由 */
	public String reasonTemp;
	/**
	 * 既定
	 */
	public Integer defaultFlg;
}
