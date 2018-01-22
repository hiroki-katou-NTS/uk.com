package nts.uk.ctx.at.request.dom.setting.requestofeach;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.Value;
import nts.arc.enums.EnumAdaptor;
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
	/**
	 * create from java type
	 * @param selectOfApproversFlg
	 * @param list
	 * @return
	 * @author yennth
	 */
	public static RequestOfEachCompany createFromJavaType(int selectOfApproversFlg, List<RequestAppDetailSetting> list){
		return new RequestOfEachCompany(list, EnumAdaptor.valueOf(selectOfApproversFlg, SelectionFlg.class));
	}
}
