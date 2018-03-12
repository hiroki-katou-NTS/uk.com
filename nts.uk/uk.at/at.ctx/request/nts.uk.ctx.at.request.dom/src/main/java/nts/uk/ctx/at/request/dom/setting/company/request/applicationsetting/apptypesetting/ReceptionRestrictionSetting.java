package nts.uk.ctx.at.request.dom.setting.company.request.applicationsetting.apptypesetting;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.dom.DomainObject;
import nts.uk.ctx.at.request.dom.application.ApplicationType;
/**
 * 受付制限設定
 * @author Doan Duy Hung
 *
 */
@Getter
@AllArgsConstructor
public class ReceptionRestrictionSetting extends DomainObject {
	
	/**
	 * 申請種類
	 */
	private ApplicationType appType;
	
	/**
	 * 事前の受付制限
	 */
	private BeforehandRestriction beforehandRestriction;
	
	/**
	 * 事後の受付制限
	 */
	private AfterhandRestriction afterhandRestriction;
	
	public static ReceptionRestrictionSetting toDomain(Integer appType, 
			BeforehandRestriction beforehandRestriction, AfterhandRestriction afterhandRestriction){
		return new ReceptionRestrictionSetting(
				EnumAdaptor.valueOf(appType, ApplicationType.class), 
				beforehandRestriction, 
				afterhandRestriction);
	}
	
}
