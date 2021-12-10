package nts.uk.ctx.at.function.dom.adapter;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class WorkplaceHistoryItemImport {
	
	private String historyId;
	
	private String employeeId;

	private String  workplaceId;

//	private String  normalWorkplaceId;
	
	/** 勤務場所コード */
//	private Optional<String> workLocationCode;

<<<<<<< HEAD
	public WorkplaceHistoryItemImport(String historyId, String employeeId, String workplaceId,
			Optional<String> workLocationCode) {
		this.historyId = historyId;
		this.employeeId = employeeId;
		this.workplaceId = workplaceId;
		this.workLocationCode = workLocationCode;
=======
	public WorkplaceHistoryItemImport(String historyId, String employeeId, String workplaceId, String normalWorkplaceId) {
		this.historyId = historyId;
		this.employeeId = employeeId;
		this.workplaceId = workplaceId;
		this.normalWorkplaceId = normalWorkplaceId;
//		this.workLocationCode = workLocationCode;
>>>>>>> uk/release_bug901
	}
	
	
}
