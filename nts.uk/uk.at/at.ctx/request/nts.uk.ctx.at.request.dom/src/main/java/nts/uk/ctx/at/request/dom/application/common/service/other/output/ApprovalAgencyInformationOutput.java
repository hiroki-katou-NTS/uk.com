package nts.uk.ctx.at.request.dom.application.common.service.other.output;

import java.util.List;

import lombok.Value;

@Value
public class ApprovalAgencyInformationOutput {
	/** 承認者の代行情報リスト*/
	List<ObjApproverRepresenterOutput> listApproverAndRepresenterSID;
	
	/** 承認代行者リスト*/
	List<String> listRepresenterSID;

	/**   
	 * true：承認者リストに全員パス設定した, false：そうではない
	 */
	boolean flag;
}
