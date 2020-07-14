package nts.uk.ctx.at.shared.dom.workrule.organizationmanagement.workplace.adapter;

import lombok.Builder;
import lombok.Getter;


@Getter
@Builder
public class WorkplaceGroupImport {
	
	/** 会社ID */
	private final String CID;
	
	/** 職場グループID **/
	private String workplaceGroupId;
	
	/** 職場グループコード **/ 
	private String workplaceGroupCode;
	
	/** 職場グループ名称 **/
	private String workplaceGroupName;
	
	/** 職場グループ種別**/
	private int workplaceGroupType;
	
	public WorkplaceGroupImport(String cID, String workplaceGroupId, String workplaceGroupCode,
			String workplaceGroupName, int workplaceGroupType) {
		super();
		CID = cID;
		this.workplaceGroupId = workplaceGroupId;
		this.workplaceGroupCode = workplaceGroupCode;
		this.workplaceGroupName = workplaceGroupName;
		this.workplaceGroupType = workplaceGroupType;
	}
	

}
