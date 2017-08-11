package nts.uk.ctx.at.request.dom.setting.applicationformreason;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.dom.AggregateRoot;
import nts.uk.ctx.at.request.dom.application.common.ApplicationType;

/**
 * 申請定型理由
 * @author dudt
 *
 */
@Getter
@AllArgsConstructor
public class ApplicationFormReason extends AggregateRoot {
	/**
	 * 会社Iｄ
	 */
	public String companyId;
	/**
	 * 申請種類
	 */
	public ApplicationType appType;
	/**
	 * 表示順
	 */
	public int displayOrder;
	/**
	 * 既定
	 */
	public DefaultFlg defaultFlg;
	//定型理由
	
	public static ApplicationFormReason createSimpleFromJavaType(String companyId,
			int appType,
			int displayOrder,
			int defaultFlg) {
				return new ApplicationFormReason(companyId, 
						EnumAdaptor.valueOf(appType, ApplicationType.class), 
						displayOrder, 
						EnumAdaptor.valueOf(defaultFlg, DefaultFlg.class));
		
	}
}
