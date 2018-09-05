package nts.uk.ctx.sys.assist.dom.salary;

import lombok.Getter;
import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.dom.AggregateRoot;

/**
 * 処理区分基本情報
 */

@Getter
public class ProcessInformation extends AggregateRoot {

	/**
	 * 会社ID
	 */
	private String cid;

	/**
	 * 処理区分NO
	 */
	private int processCateNo;

	/**
	 * 廃止区分
	 */
	private AbolitionAtr deprecatCate;

	/**
	 * 処理区分名称
	 */
	private ProcessDivisionName processDivisionName;

	public ProcessInformation(String cid, int processCateNo, int deprecatCate, String processDivisionName) {
		this.cid = cid;
		this.processCateNo = processCateNo;
		this.deprecatCate = EnumAdaptor.valueOf(deprecatCate, AbolitionAtr.class);
		this.processDivisionName = new ProcessDivisionName(processDivisionName);
	}
}
