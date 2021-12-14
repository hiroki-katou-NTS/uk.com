package nts.uk.ctx.exio.dom.input.domain;

import java.util.Set;

import lombok.Value;
import nts.uk.ctx.exio.dom.input.canonicalize.ImportingMode;

/**
 * 受入グループ
 */
@Value
public class ImportingDomain {

	/** 受入グループID */
	ImportingDomainId domainId;
	
	/** 名称 */
	String name;
	
	/** 利用可能な受入モード */
	Set<ImportingMode> availableModes;
	
	/** トランザクション単位 */
	TransactionUnit transactionUnit;
}
