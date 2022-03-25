package nts.uk.query.pub.workflow.workroot.approvalmanagement;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ApprovalRootExport {

	/** システム区分 */
	private Integer sysAtr;
	/** 承認ルート区分 */
	private Integer employmentRootAtr;
	/** 履歴 */
	private List<EmploymentAppHistoryItemExport> historyItems;
	/** 申請種類 */
	private Integer applicationType;
	/** 確認ルート種類 */
	private Integer confirmationRootType;
	/** 届出ID */
	private Integer noticeId;
	/** 各業務エベントID */
	private String busEventId;
}
