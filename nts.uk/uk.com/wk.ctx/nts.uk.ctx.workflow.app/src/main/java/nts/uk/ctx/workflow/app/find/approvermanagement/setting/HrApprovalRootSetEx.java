package nts.uk.ctx.workflow.app.find.approvermanagement.setting;

import lombok.Getter;

import lombok.AllArgsConstructor;

@Getter
@AllArgsConstructor
public class HrApprovalRootSetEx {
	private boolean comMode; // 会社
	private boolean devMode; // 部門
	private boolean empMode; // 個人
}
