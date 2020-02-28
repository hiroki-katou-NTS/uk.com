package nts.uk.ctx.at.schedule.dom.employeeinfo.medicalworkstyle;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.arc.layer.dom.objecttype.DomainAggregate;
import nts.uk.ctx.at.shared.dom.common.CompanyId;

/**
 * @author ThanhNX 看護区分
 */
@AllArgsConstructor 
public class NurseClassification implements DomainAggregate {

	/**
	 * 	会社ID
	 */
	@Getter
	private final CompanyId companyId;

	/**
	 * 看護区分コード
	 */
	@Getter
	private final NurseClassifiCode nurseClassifiCode;

	/**
	 * 看護区分名称	
	 */
	@Getter
	private NurseClassifiName nurseClassifiName;

	/**
	 * 	免許区分	
	 */
	@Getter
	private LicenseClassification license;

	/**
	 * 	事務的業務従事者か
	 ****「する」の場合は事務的業務従事者として働く****
	 */
	@Getter
	private boolean officeWorker;
	
}
