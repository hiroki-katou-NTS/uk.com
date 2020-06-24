package nts.uk.ctx.at.shared.dom.workrule.organizationmanagement.workplace.adapter;

import lombok.Builder;
import lombok.Getter;


@Getter
@Builder
public class WorkplaceGroupImport {
	
	/** 職場グループID **/
	private String workplaceId;
	/** 職場グループコード **/ 
	private String workplaceGroupCode;
	/** 職場グループ名称 **/
	private String workplaceName;
	/** 職場グループ種別**/
	private int workplaceGroupType;
	public WorkplaceGroupImport(String workplaceId, String workplaceGroupCode, String workplaceName,
			int workplaceGroupType) {
		super();
		this.workplaceId = workplaceId;
		this.workplaceGroupCode = workplaceGroupCode;
		this.workplaceName = workplaceName;
		this.workplaceGroupType = workplaceGroupType;
	}

}
