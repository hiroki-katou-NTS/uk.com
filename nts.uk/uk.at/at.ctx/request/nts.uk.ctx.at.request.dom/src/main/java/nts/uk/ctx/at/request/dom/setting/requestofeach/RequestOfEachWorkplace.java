package nts.uk.ctx.at.request.dom.setting.requestofeach;

import java.util.List;

import lombok.EqualsAndHashCode;
import lombok.Value;

/**
 * 職場別申請承認設定
 * @author dudt
 *
 */
@Value
@EqualsAndHashCode(callSuper=false)
public class RequestOfEachWorkplace extends RequestOfEachCommon {
	
	/**
	 * 職場Iｄ
	 */
	public String workplaceId;
	
	/**
	 * 申請時の承認者の選択
	 */
	public SelectionFlg selectOfApproversFlg;

	public RequestOfEachWorkplace(List<RequestAppDetailSetting> requestAppDetailSettings, String workplaceId,
			SelectionFlg selectOfApproversFlg) {
		super(requestAppDetailSettings);
		this.workplaceId = workplaceId;
		this.selectOfApproversFlg = selectOfApproversFlg;
	}
}
