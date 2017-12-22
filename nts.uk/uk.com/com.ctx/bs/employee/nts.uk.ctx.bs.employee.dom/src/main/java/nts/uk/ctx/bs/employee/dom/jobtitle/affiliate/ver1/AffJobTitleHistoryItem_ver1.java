package nts.uk.ctx.bs.employee.dom.jobtitle.affiliate.ver1;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.arc.layer.dom.AggregateRoot;
import nts.uk.ctx.bs.employee.dom.jobtitle.affiliate.AffJobTitleHistoryItemNote;

/**
 * The Class AffJobHistoryItem.
 */
//  所属職位履歴項目
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class AffJobTitleHistoryItem_ver1 extends AggregateRoot{

	
	/** The history Id. */
	// 履歴ID
	private String historyId;
	
	/** The Employee Id. */
	// 社員ID
	private String employeeId;
	
	/** The job title code. */
	// 職位ID
	private String jobTitleId;
	
	/** The AffJobHistoryItemNote. */
	// 備考
	private AffJobTitleHistoryItemNote note;
	
	public static AffJobTitleHistoryItem_ver1 createFromJavaType(String histId, String employeeId, String jobTitleId, String note){
		return new AffJobTitleHistoryItem_ver1(histId,employeeId, jobTitleId, new AffJobTitleHistoryItemNote(note));
	}
}
