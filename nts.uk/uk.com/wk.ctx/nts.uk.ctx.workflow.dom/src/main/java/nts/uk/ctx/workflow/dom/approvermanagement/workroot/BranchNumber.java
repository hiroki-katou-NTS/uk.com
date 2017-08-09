package nts.uk.ctx.workflow.dom.approvermanagement.workroot;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum BranchNumber {
	/* 第１ルート*/
	FIRST_ROOT(0),
	/* 第２ルート*/
	SECOND_ROOT(1),
	/* 第３ルート*/
	THIRD_ROOT(2),
	/* 第４ルート*/
	FOURTH_ROOT(3),
	/* 第５ルート*/
	FIFTH_ROOT(4);
	public final int value;
}
