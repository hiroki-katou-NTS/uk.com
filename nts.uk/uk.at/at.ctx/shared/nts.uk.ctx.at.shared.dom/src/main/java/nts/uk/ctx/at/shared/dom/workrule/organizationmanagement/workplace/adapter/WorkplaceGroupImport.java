package nts.uk.ctx.at.shared.dom.workrule.organizationmanagement.workplace.adapter;


import lombok.Getter;

/**
 * 職場グループImported										
 * @author HieuLt
 *
 */
@Getter
public class WorkplaceGroupImport {
	
	/** 職場グループID **/
	private final String workplaceGroupId;
	
	/** 職場グループコード **/ 
	private final String workplaceGroupCode;
	
	/** 職場グループ名称 **/
	private final String workplaceGroupName;
	
	/** 職場グループ種別**/
	private final int workplaceGroupType;
	//[C-0] 職場グループImported ( 職場グループID, 職場グループコード, 職場グループ名称, 職場グループ種別 )
	public WorkplaceGroupImport(String workplaceGroupId, String workplaceGroupCode,
			String workplaceGroupName, int workplaceGroupType) {
		super();

		this.workplaceGroupId = workplaceGroupId;
		this.workplaceGroupCode = workplaceGroupCode;
		this.workplaceGroupName = workplaceGroupName;
		this.workplaceGroupType = workplaceGroupType;
	}
	

}
