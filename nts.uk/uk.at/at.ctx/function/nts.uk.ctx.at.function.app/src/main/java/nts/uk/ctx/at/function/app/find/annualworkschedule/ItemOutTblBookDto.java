package nts.uk.ctx.at.function.app.find.annualworkschedule;

import lombok.AllArgsConstructor;
import lombok.Value;
import nts.uk.ctx.at.function.dom.annualworkschedule.ItemOutTblBook;

/**
* 帳表に出力する項目
*/
@AllArgsConstructor
@Value
public class ItemOutTblBookDto {
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

	public static ItemOutTblBookDto fromDomain(ItemOutTblBook domain) {
		return new ItemOutTblBookDto(domain.getCid(), domain.getCode(), domain.getSortBy(), domain.getUseClass(), domain.getValOutFormat(), domain.getHeadingName());
	}
}
