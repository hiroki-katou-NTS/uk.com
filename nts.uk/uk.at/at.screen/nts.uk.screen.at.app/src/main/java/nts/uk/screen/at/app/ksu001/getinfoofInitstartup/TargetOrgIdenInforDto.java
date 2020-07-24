package nts.uk.screen.at.app.ksu001.getinfoofInitstartup;

import lombok.Value;

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

	public TargetOrgIdenInforDto(int unit, String workplaceId, String workplaceGroupId) {
		super();
		this.unit = unit;
		this.workplaceId = workplaceId;
		this.workplaceGroupId = workplaceGroupId;
	}
	
	
	
}
