package nts.uk.ctx.at.function.dom.annualworkschedule;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.dom.AggregateRoot;
import nts.uk.ctx.at.function.dom.annualworkschedule.enums.ValueOuputFormat;
import nts.uk.ctx.at.function.dom.annualworkschedule.primitivevalue.ItemOutTblBookCode;
import nts.uk.ctx.at.function.dom.annualworkschedule.primitivevalue.ItemOutTblBookHeadingName;

/**
* 帳表に出力する項目
*/
@AllArgsConstructor
@Getter
public class ItemOutTblBook extends AggregateRoot {
	/**
	* 会社ID
	*/
	private String cid;

	/**
	* コード
	*/
	private ItemOutTblBookCode cd;

	/**
	* コード
	*/
	private String setOutCd;

	/**
	* 並び順
	*/
	private int sortBy;

	/**
	* 見出し名称
	*/
	private ItemOutTblBookHeadingName headingName;

	/**
	* 使用区分
	*/
	private int useClass;

	/**
	* 値の出力形式
	*/
	private ValueOuputFormat valOutFormat;

	public static ItemOutTblBook createFromJavaType(String cid, String cd, String setOutCd, int sortBy, String headingName, int useClass,
			int valOutFormat) {
		return new ItemOutTblBook(cid, new ItemOutTblBookCode(cd), setOutCd, sortBy,
				new ItemOutTblBookHeadingName(headingName), useClass,
				EnumAdaptor.valueOf(valOutFormat, ValueOuputFormat.class));
	}
}
