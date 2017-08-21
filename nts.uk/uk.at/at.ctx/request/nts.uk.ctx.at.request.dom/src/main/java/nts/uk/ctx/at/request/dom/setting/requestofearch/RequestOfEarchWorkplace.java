package nts.uk.ctx.at.request.dom.setting.requestofearch;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.dom.AggregateRoot;
import nts.uk.ctx.at.request.dom.application.common.ApplicationType;
import nts.uk.ctx.at.request.dom.application.common.UseAtr;

/**
 * 職場別申請承認設定
 * @author dudt
 *
 */
@Getter
@AllArgsConstructor
public class RequestOfEarchWorkplace extends AggregateRoot{
	/**
	 *　会社Iｄ 	
	 */
	public String companyId;
	/**
	 * 職場Iｄ
	 */
	public String workplaceId;
	
	/**
	 * 申請時の承認者の選択
	 */
	public SelectionFlg selectOfApproversFlg;	
	
	public static RequestOfEarchWorkplace createSimpleFromJavaType(String companyId,
			String wpId,
			int selectOfApproversFlg) {
				return new RequestOfEarchWorkplace(companyId, 
						wpId,						
						EnumAdaptor.valueOf(selectOfApproversFlg, SelectionFlg.class));
		
	}
}
