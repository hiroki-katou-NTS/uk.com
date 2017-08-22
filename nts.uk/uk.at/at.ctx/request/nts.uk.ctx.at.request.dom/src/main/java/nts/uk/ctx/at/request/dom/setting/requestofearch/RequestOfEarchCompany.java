package nts.uk.ctx.at.request.dom.setting.requestofearch;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.dom.AggregateRoot;
import nts.uk.ctx.at.request.dom.application.common.ApplicationType;
import nts.uk.ctx.at.request.dom.application.common.UseAtr;
/**
 * 会社別申請承認設定
 * @author dudt
 *
 */
@Getter
@AllArgsConstructor
public class RequestOfEarchCompany extends AggregateRoot {
	/**
	 *　会社Iｄ 	
	 */
	public String companyId;
	
	/**
	 * 申請時の承認者の選択
	 */
	public SelectionFlg selectOfApproversFlg;
	
	public static RequestOfEarchCompany createSimpleFromJavaType(String companyId,			
			int selectOfApproversFlg) {
		return new RequestOfEarchCompany(companyId,
				EnumAdaptor.valueOf(selectOfApproversFlg, SelectionFlg.class));
	}
}
