package nts.uk.ctx.at.request.dom.application.common.adapter.workflow.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class AgentPubImport {

	/** 承認者の代行情報リスト */
	List<ApproverRepresenterImport> listApproverAndRepresenterSID;

	/** 承認代行者リスト */
	List<String> listRepresenterSID;

	/**
	 * true：承認者リストに全員パス設定した, false：そうではない
	 */
	boolean flag;
}
