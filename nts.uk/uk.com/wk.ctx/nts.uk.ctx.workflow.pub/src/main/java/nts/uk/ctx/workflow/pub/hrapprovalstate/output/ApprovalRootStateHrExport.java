package nts.uk.ctx.workflow.pub.hrapprovalstate.output;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.uk.ctx.workflow.pub.hrapprovalstate.input.ApprovalStateHrImport;

@Getter
@AllArgsConstructor
public class ApprovalRootStateHrExport {
	/**エラーフラグ*/
	private boolean errorFlg;
	/**人事承認状態データ*/
	private ApprovalStateHrImport apprState;
}
