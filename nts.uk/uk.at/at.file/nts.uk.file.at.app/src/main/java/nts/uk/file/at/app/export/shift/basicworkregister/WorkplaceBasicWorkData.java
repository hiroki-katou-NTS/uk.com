package nts.uk.file.at.app.export.shift.basicworkregister;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class WorkplaceBasicWorkData {
	private String workplaceId;
	private Integer workDayAtr;
	private String workTypeCD;
	private String workTypeName;
	private String workTimeCD;
	private String workTimeName;
	private String workplaceCode;
	private String workplaceName;
	
	private String hierarchyCode;
	
	public static WorkplaceBasicWorkData createFromJavaType(String workplaceId,int workDayAtr, 
			String workTypeCD, String workTypeName, String workTimeCD, String workTimeName){
		return new WorkplaceBasicWorkData(workplaceId,workDayAtr, workTypeCD, workTypeName, workTimeCD, workTimeName);
	}


	public WorkplaceBasicWorkData(String workplaceId, int workDayAtr, String workTypeCD, String workTypeName,
			String workTimeCD, String workTimeName) {
		super();
		this.workplaceId = workplaceId;
		this.workDayAtr = workDayAtr;
		this.workTypeCD = workTypeCD;
		this.workTypeName = workTypeName;
		this.workTimeCD = workTimeCD;
		this.workTimeName = workTimeName;
	}
}
