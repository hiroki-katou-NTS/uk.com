package nts.uk.screen.at.app.ksu001.getinfoofInitstartup;

import lombok.Value;
import nts.uk.ctx.at.shared.dom.workrule.organizationmanagement.workplace.TargetOrgIdenInfor;

@Value
public class TargetOrgIdenInforDto   {

	/**
	 * 単位
	 */
	public  int unit; //WORKPLACE(0), //WORKPLACE_GROUP(1);
	
	/**
	 * 職場ID
	 */
	public  String workplaceId;
	
	/**
	 * 職場グループID
	 */
	public  String workplaceGroupId;

	public TargetOrgIdenInforDto(TargetOrgIdenInfor targetOrgIdenInfor) {
		super();
		this.unit = targetOrgIdenInfor.getUnit().value;
		this.workplaceId = targetOrgIdenInfor.getWorkplaceId().isPresent() ? targetOrgIdenInfor.getWorkplaceId().get() : null;
		this.workplaceGroupId = targetOrgIdenInfor.getWorkplaceGroupId().isPresent() ? targetOrgIdenInfor.getWorkplaceGroupId().get() : null;
	}
	
	public TargetOrgIdenInforDto(int unit, String workplaceId, String workplaceGroupId) {
		super();
		this.unit = unit;
		this.workplaceId = workplaceId;
		this.workplaceGroupId = workplaceGroupId;
	}
}
