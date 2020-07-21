package nts.uk.screen.at.app.ksu001.getinfoofInitstartup;

import java.util.Optional;

import lombok.Value;

@Value
public class TargetOrgIdenInforDto   {

	/**
	 * 単位
	 */
	public final int unit;
	
	/**
	 * 職場ID
	 */
	public final Optional<String> workplaceId;
	
	/**
	 * 職場グループID
	 */
	public final Optional<String> workplaceGroupId;

	public TargetOrgIdenInforDto(int unit, String workplaceId, String workplaceGroupId) {
		super();
		this.unit = unit;
		this.workplaceId = Optional.ofNullable(workplaceId);
		this.workplaceGroupId = Optional.ofNullable(workplaceGroupId);
	}
	
	
	
}
