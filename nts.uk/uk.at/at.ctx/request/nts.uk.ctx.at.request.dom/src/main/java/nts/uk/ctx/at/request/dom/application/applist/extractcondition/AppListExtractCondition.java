package nts.uk.ctx.at.request.dom.application.applist.extractcondition;

import java.util.List;
import java.util.Optional;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.request.dom.application.applist.service.ApplicationDisplayOrder;
import nts.uk.ctx.at.request.dom.application.applist.service.ListOfAppTypes;
/**
 * 申請一覧抽出条件
 * @author hoatt
 *
 */
@Setter
@Getter
@AllArgsConstructor
public class AppListExtractCondition {
	
	/**
	 * 期間開始日
	 */
	private GeneralDate periodStartDate;
	
	/**
	 * 期間終了日
	 */
	private GeneralDate periodEndDate;
	/**
	 * 事後出力
	 */
	private boolean postOutput;
	
	/**
	 * 事前出力
	 */
	private boolean preOutput;
	
	/**
	 * 申請一覧区分
	 */
	private ApplicationListAtr appListAtr;
	
	/**
	 * 申請表示順
	 */
	private ApplicationDisplayOrder appDisplayOrder;
	
	/**
	 * 表の幅登録
	 */
	private boolean tableWidthRegis;
	
	/**
	 * 社員IDリスト
	 */
	private Optional<List<String>> opListEmployeeID;
	
	/**
	 * 承認状況＿差戻
	 */
	private Optional<Boolean> opRemandStatus;
	
	/**
	 * 承認状況＿取消
	 */
	private Optional<Boolean> opCancelStatus;
	
	/**
	 * 承認状況＿承認済
	 */
	private Optional<Boolean> opApprovalStatus;
	
	/**
	 * 承認状況＿代行承認済
	 */
	private Optional<Boolean> opAgentApprovalStatus;
	
	/**
	 * 承認状況＿否認
	 */
	private Optional<Boolean> opDenialStatus;
	
	/**
	 * 承認状況＿未承認
	 */
	private Optional<Boolean> opUnapprovalStatus;
	
	/**
	 * 申請種類
	 */
	private Optional<List<ListOfAppTypes>> opAppTypeLst;
	
	/**
	 * 申請種類リスト
	 */
	private Optional<List<ListOfAppTypes>> opListOfAppTypes;
	
	public AppListExtractCondition() {
		periodStartDate = null;
		periodEndDate = null;
		postOutput = false;
		preOutput = false;
		appListAtr = null;
		appDisplayOrder = ApplicationDisplayOrder.APPLICANT_ORDER;
		tableWidthRegis = false;
		opListEmployeeID = Optional.empty();
		opRemandStatus = Optional.empty();
		opCancelStatus = Optional.empty();
		opApprovalStatus = Optional.empty();
		opAgentApprovalStatus = Optional.empty();
		opDenialStatus = Optional.empty();
		opUnapprovalStatus = Optional.empty();
		opAppTypeLst = Optional.empty();
		opListOfAppTypes = Optional.empty();
	}
}
