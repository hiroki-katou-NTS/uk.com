package nts.uk.ctx.at.request.dom.application.applist.service;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
/**
 * tam thoi /output cua 4
 * UKDesign.UniversalK.就業.KAF_申請.CMM045_申請一覧・承認一覧.A:申請一覧画面.アルゴリズム.パラメータ.申請件数
 * @author hoatt
 *
 */
@Getter
@Setter
@AllArgsConstructor
public class ApplicationStatus {
	/**
	 * 未承認件数
	 */
	private int unApprovalNumber;
	/**
	 * 承認件数
	 */
	private int approvalNumber;
	/**
	 * 代行承認件数
	 */
	private int approvalAgentNumber;
	/**
	 * 取消件数
	 */
	private int cancelNumber;
	/**
	 * 差戻件数
	 */
	private int remandNumner;
	/**
	 * 否認件数
	 */
	private int denialNumber;
	
	public ApplicationStatus() {
		this.unApprovalNumber = 0;
		this.approvalNumber = 0;
		this.approvalAgentNumber = 0;
		this.cancelNumber = 0;
		this.remandNumner = 0;
		this.denialNumber = 0;
	}
}
