package nts.uk.ctx.at.shared.dom.scherec.supportmanagement;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.arc.error.BusinessException;
import nts.arc.layer.dom.objecttype.DomainAggregate;
import nts.uk.ctx.at.shared.dom.workrule.organizationmanagement.workplace.TargetOrgIdenInfor;

/**
 * 
 * 応援許可する組織
 * UKDesign.ドメインモデル.NittsuSystem.UniversalK.就業.shared(勤務予定、勤務実績).応援管理.応援許可する組織
 * @author lan_lt
 */
@Getter
@AllArgsConstructor
public class SupportAllowOrganization implements DomainAggregate{

	/** 対象組織情報 **/
	private final TargetOrgIdenInfor targetOrg;
	
	/** 応援可能組織 **/
	private TargetOrgIdenInfor supportableOrganization;
	
	/**
	 * 作る
	 * @param targetOrgInfo 対象組織情報
	 * @param supportableOrganization 応援可能組織
	 * @return
	 */
	public static SupportAllowOrganization create(
				TargetOrgIdenInfor targetOrgInfo
			,	TargetOrgIdenInfor supportableOrganization) {
		
		if( !targetOrgInfo.getUnit().equals( supportableOrganization.getUnit() ))
			throw new BusinessException( "Msg_2299" );
		
		
		if( targetOrgInfo.equals( supportableOrganization ))
			throw new BusinessException( "Msg_2146" );
		
		return new SupportAllowOrganization( targetOrgInfo, supportableOrganization );
	}
	
	/**
	 * 複写する
	 * @param copyDestinationOrg 複写先組織
	 * @return
	 */
	public SupportAllowOrganization copy(TargetOrgIdenInfor copyDestinationOrg) {
		
		return SupportAllowOrganization.create( copyDestinationOrg, this.supportableOrganization );
		
	}
	
}
