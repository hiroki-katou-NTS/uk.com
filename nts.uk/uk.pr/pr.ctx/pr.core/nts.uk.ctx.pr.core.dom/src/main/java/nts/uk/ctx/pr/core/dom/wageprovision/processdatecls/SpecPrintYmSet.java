package nts.uk.ctx.pr.core.dom.wageprovision.processdatecls;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.arc.layer.dom.AggregateRoot;

/**
 * 明細書印字年月設定
 */
@AllArgsConstructor
@Getter
public class SpecPrintYmSet extends AggregateRoot {

	/**
	 * 会社ID
	 */
	private String cid;

	/**
	 * 処理区分NO
	 */
	private int processCateNo;

	/**
	 * 処理年月
	 */
	private int processDate;

	/**
	 * 印字年月
	 */
	private int printDate;

}
