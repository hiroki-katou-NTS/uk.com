package nts.uk.ctx.at.record.dom.adapter.application.setting;

import lombok.AllArgsConstructor;
import lombok.Value;
import nts.uk.ctx.at.shared.dom.remainingnumber.algorithm.ApplicationType;

/**
 * @author ThanhNX
 *
 * 申請定型理由 import
 */
@AllArgsConstructor
@Value
public class ApplicationReasonRc {

	/**
	 * 会社Iｄ
	 */
	public String companyId;
	/**
	 * 申請種類
	 */
	public ApplicationType appType;

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
