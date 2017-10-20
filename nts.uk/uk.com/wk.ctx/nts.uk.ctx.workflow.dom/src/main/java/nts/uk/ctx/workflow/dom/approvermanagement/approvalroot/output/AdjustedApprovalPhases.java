package nts.uk.ctx.workflow.dom.approvermanagement.approvalroot.output;

import java.util.List;

import lombok.Getter;
import lombok.val;
import nts.uk.ctx.workflow.dom.approvermanagement.workroot.ApprovalPhase;

public class AdjustedApprovalPhases {
	@Getter
	public final List<ApprovalPhaseOutput> phases;

	public AdjustedApprovalPhases(List<ApprovalPhaseOutput> phases) {
		this.phases = phases;
	}

	public ErrorFlag checkError(List<ApprovalPhase> masterPhases) {

		ErrorFlag errorFlag;

		for (val phase : this.phases) {

			errorFlag = phase.checkError();
			if (errorFlag.isError()) {
				return errorFlag;
			}

			if (!phase.isSingleApproved()) {
				continue;
			}

			if (!phase.containsConfirmer()) {
				continue;
			}

			// ↓この辺りの処理は仕様がよく分からないので、間違ってるかも＠＠

			// 確定者あるドメインモデル「承認者」から変換した実際の承認者がいるかチェックする
			val masterPhase = masterPhases.stream()
					.filter(mp -> mp.getApprovalPhaseId().equals(phase.getApprovalPhaseId())).findFirst().get();

			if (!phase.containsAny(masterPhase.getApprovers())) {
				return ErrorFlag.NO_CONFIRM_PERSON;
			}
		}

		return ErrorFlag.NO_ERROR;
	}
}
