package nts.uk.ctx.workflow.dom.approvermanagement.workroot;

import lombok.AllArgsConstructor;

/**
 * 運用モード
 */
@AllArgsConstructor
public enum OperationMode {
	/** 就業担当者が行う */
	PERSON_IN_CHARGE(0),
	
	/** 上長・社員が行う */
	SUPERIORS_EMPLOYEE(1);
	
	public final int value;
}
