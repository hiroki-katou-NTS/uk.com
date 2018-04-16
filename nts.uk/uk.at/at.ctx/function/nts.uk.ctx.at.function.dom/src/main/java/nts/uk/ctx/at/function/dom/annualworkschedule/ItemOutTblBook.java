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
	private int code;
	/**
	* 並び順
	*/
	private int sortBy;
	/**
	* 使用区分
	*/
	private int useClass;
	/**
	* 値の出力形式
	*/
	private int valOutFormat;
	/**
	* 見出し名称
	*/
	private String headingName;

	public ItemOutTblBook(String cid, int code, int sortBy, int useClass, int valOutFormat, String headingName) {
		super();
		this.cid = cid;
		this.code = code;
		this.sortBy = sortBy;
		this.useClass = useClass;
		this.valOutFormat = valOutFormat;
		this.headingName = headingName;
	}
}
