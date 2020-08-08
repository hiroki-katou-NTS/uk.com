package nts.uk.ctx.at.request.dom.application.applist.extractcondition;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.request.dom.application.ApplicationType;
/**
 * 申請一覧抽出条件
 * @author hoatt
 *
 */
@Getter
@AllArgsConstructor
public class AppListExtractCondition {
	/**期間開始日付*/
	private GeneralDate startDate;
	/**期間終了日付*/
	private GeneralDate endDate;
	/**申請一覧区分*/
	private ApplicationListAtr appListAtr;
	/**申請種類*/
	private ApplicationType appType;
	/**承認状況＿未承認*/
	private boolean unapprovalStatus;
	/**承認状況＿承認済*/
	private boolean approvalStatus;
	/**承認状況＿否認*/
	private boolean denialStatus;
	/**承認状況＿代行承認済*/
	private boolean agentApprovalStatus;
	/**承認状況＿差戻*/
	private boolean remandStatus;
	/**承認状況＿取消*/
	private boolean cancelStatus;
	/**申請表示対象*/
	private  ApplicationDisplayAtr appDisplayAtr;
	/**社員IDリスト*/
	private List<String> listEmployeeId;
	/**社員絞込条件*/
	private String empRefineCondition;
	
}
