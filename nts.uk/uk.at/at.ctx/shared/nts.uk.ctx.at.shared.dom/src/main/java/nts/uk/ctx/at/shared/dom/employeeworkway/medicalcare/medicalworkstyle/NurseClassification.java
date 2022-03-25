package nts.uk.ctx.at.shared.dom.employeeworkway.medicalcare.medicalworkstyle;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.arc.error.BusinessException;
import nts.arc.layer.dom.objecttype.DomainAggregate;
import nts.uk.ctx.at.shared.dom.common.CompanyId;

/**
 * @author ThanhNX 看護区分
 * UKDesign.ドメインモデル.NittsuSystem.UniversalK.就業.shared.社員の働き方.医療介護.医療勤務形態.看護区分
 */
@Getter
@AllArgsConstructor 
public class NurseClassification implements DomainAggregate {

	/**
	 * 	会社ID
	 */
	private final CompanyId companyId;

	/**
	 * 看護区分コード
	 */
	private final NurseClassifiCode nurseClassifiCode;

	/**
	 * 看護区分名称	
	 */
	private NurseClassifiName nurseClassifiName;

	/**
	 * 免許区分	
	 */
	private LicenseClassification license;

	/**
	 * 事務的業務従事者か
	 ****「する」の場合は事務的業務従事者として働く****
	 */
	@Getter
	private boolean officeWorker;
	
	/**
	 * 看護管理者か
	 */
	private boolean isNursingManager;
	
	/**
	 * 作る
	 * @param companyId 会社ID
	 * @param nurseClassifiCode 看護区分コード
	 * @param nurseClassifiName 看護区分名称
	 * @param license 免許区分
	 * @param officeWorker 事務的業務従事者か
	 * @param isNursingManager 看護管理者か
	 * @return
	 */
	public static NurseClassification create(CompanyId companyId
			,	NurseClassifiCode nurseClassifiCode
			,	NurseClassifiName nurseClassifiName
			,	LicenseClassification license
			,	boolean officeWorker
			,	boolean isNursingManager
			
			) {
		
		if(license!= LicenseClassification.NURSE && isNursingManager) {
			throw new BusinessException("Msg_2238");
		}
		
		return new NurseClassification(companyId
				,	nurseClassifiCode
				,	nurseClassifiName
				,	license
				,	officeWorker
				,	isNursingManager);
	}
	
}
