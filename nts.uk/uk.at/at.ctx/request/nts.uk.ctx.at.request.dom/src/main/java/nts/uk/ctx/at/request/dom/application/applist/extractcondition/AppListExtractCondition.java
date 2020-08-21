package nts.uk.ctx.at.request.dom.application.applist.extractcondition;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.uk.ctx.at.request.dom.application.ApplicationType;
import nts.uk.ctx.at.request.dom.application.applist.service.ApplicationDisplayOrder;
import nts.uk.ctx.at.request.dom.application.applist.service.ListOfAppTypes;
/**
 * 申請一覧抽出条件
 * @author hoatt
 *
 */
@Getter
@AllArgsConstructor
public class AppListExtractCondition {
	/**
	 * 事後出力
	 */
	private boolean subsequentOutput;
	
	/**
	 * 事前出力
	 */
	private boolean advanceOutput;
	
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
	private List<String> listEmployeeId;
	
	/**
	 * 承認状況＿差戻
	 */
	private boolean remandStatus;
	
	/**
	 * 承認状況＿取消
	 */
	private boolean cancelStatus;
	
	/**
	 * 承認状況＿承認済
	 */
	private boolean approvalStatus;
	
	/**
	 * 承認状況＿代行承認済
	 */
	private boolean agentApprovalStatus;
	
	/**
	 * 承認状況＿否認
	 */
	private boolean denialStatus;
	
	/**
	 * 承認状況＿未承認
	 */
	private boolean unapprovalStatus;
	
	/**
	 * 申請種類
	 */
	private ApplicationType appType;
	
	/**
	 * 申請種類リスト
	 */
	private List<ListOfAppTypes> listOfAppTypes;
}
