package nts.uk.ctx.exio.dom.input.execute;

import lombok.Value;

/**
 * 受入グループのトランザクション戦略
 */
@Value
public class GroupTransactionStrategy {

	/** 受入グループID */
	int groupId;
	
	/** トランザクション単位 */
	TransactionUnit transactionUnit;
}
