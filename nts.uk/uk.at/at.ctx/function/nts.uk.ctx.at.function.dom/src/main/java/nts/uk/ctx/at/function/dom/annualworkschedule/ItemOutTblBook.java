package nts.uk.ctx.at.function.dom.annualworkschedule;

import lombok.Getter;
import nts.arc.layer.dom.AggregateRoot;

/**
* 帳表に出力する項目
*/
@Getter
public class ItemOutTblBook extends AggregateRoot {
	/**
	* 会社ID
	*/
	private String cid;

	/**
	* コード
	*/
	private int cd;

	/**
	* コード
	*/
	private int setOutCd;

	/**
	* コード
	*/
	private int itemOutCd;

	/**
	* 使用区分
	*/
	private int useClass;

	/**
	* 値の出力形式
	*/
	private int valOutFormat;

	public ItemOutTblBook(String cid, int cd, int setOutCd, int itemOutCd, int useClass, int valOutFormat) {
		super();
		this.cid = cid;
		this.cd = cd;
		this.setOutCd = setOutCd;
		this.itemOutCd = itemOutCd;
		this.useClass = useClass;
		this.valOutFormat = valOutFormat;
	}
}
