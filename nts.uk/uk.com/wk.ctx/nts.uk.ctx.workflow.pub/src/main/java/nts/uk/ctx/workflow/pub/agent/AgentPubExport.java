package nts.uk.ctx.workflow.pub.agent;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class AgentPubExport {

	/** 承認者の代行情報リスト */
	List<ApproverRepresenterExport> listApproverAndRepresenterSID;

	/** 承認代行者リスト */
	List<String> listRepresenterSID;

	/**
	 * true：承認者リストに全員パス設定した, false：そうではない
	 */
	boolean flag;
}
