package nts.uk.ctx.workflow.dom.approvermanagement.workroot.service.unregisterapproval;

import lombok.AllArgsConstructor;
import lombok.Data;
import nts.uk.ctx.workflow.dom.service.output.ErrorFlag;

@Data
@AllArgsConstructor
public class ErrorContent {
    // 承認フェーズ番号
    private int approvalPhaseOrder;

    // エラーフラグ
    private ErrorFlag errorFlag;
}
