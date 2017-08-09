package nts.uk.ctx.workflow.dom.approvermanagement.workroot;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum ConfirmationRootType {
	/* 日次確認*/
	DAILY_CONFIRMATION(0),
	/* 月次確認*/
	MONTHLY_CONFIRMATION(1);
	public final int value;
}
