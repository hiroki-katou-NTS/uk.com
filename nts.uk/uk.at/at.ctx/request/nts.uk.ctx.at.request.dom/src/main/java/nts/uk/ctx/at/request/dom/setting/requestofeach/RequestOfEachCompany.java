package nts.uk.ctx.at.request.dom.setting.requestofeach;

import java.util.List;

import lombok.EqualsAndHashCode;
import lombok.Value;
/**
 * 会社別申請承認設定
 * @author dudt
 *
 */
@Value
@EqualsAndHashCode(callSuper=false)
public class RequestOfEachCompany extends RequestOfEachCommon {
	
	/**
	 * 申請時の承認者の選択
	 */
	public SelectionFlg selectOfApproversFlg;

	public RequestOfEachCompany(List<RequestAppDetailSetting> requestAppDetailSettings,
			SelectionFlg selectOfApproversFlg) {
		super(requestAppDetailSettings);
		this.selectOfApproversFlg = selectOfApproversFlg;
	}
}
