package nts.uk.ctx.at.function.app.find.annualworkschedule;

import java.util.List;

import lombok.Value;
import nts.uk.ctx.at.function.dom.annualworkschedule.CalcFormulaItem;
import nts.uk.ctx.at.function.dom.annualworkschedule.ItemOutTblBook;

/**
* 帳表に出力する項目
*/
@Value
public class ItemOutTblBookDto
{
	private final static String CD_36_AGREEMENT_TIME = "01";
	/**
	* 年間勤務表(36チェックリスト)の出力条件.コード
	*/
	private String setOutCd;

	/**
	* コード
	*/
	private String cd;

	/**
	* 並び順
	*/
	private int sortBy;

	/**
	* 見出し名称
	*/
	private String headingName;

	/**
	* 使用区分
	*/
	private boolean useClass;

	/**
	* 値の出力形式
	*/
	private int valOutFormat;

	/**
	 * 36協定時間
	 */
	private boolean item36AgreementTime;

	List<CalcFormulaItem> listOperationSetting;

	public static ItemOutTblBookDto fromDomain(ItemOutTblBook domain)
	{
		boolean isItem36AgreementTime = CD_36_AGREEMENT_TIME.equals(domain.getCd().v());
		return new ItemOutTblBookDto(domain.getSetOutCd(), domain.getCd().v(), domain.getSortBy(),
				domain.getHeadingName().v(), domain.isUseClassification(), domain.getValOutFormat().value,
				isItem36AgreementTime, domain.getListOperationSetting());
	}

	public ItemOutTblBookDto(String setOutCd, String cd, int sortBy, String headingName, boolean useClass, int valOutFormat, boolean item36AgreementTime,
			List<CalcFormulaItem> listOperationSetting) {
		super();
		this.setOutCd = setOutCd;
		this.cd = cd;
		this.sortBy = sortBy;
		this.headingName = headingName;
		this.useClass = useClass;
		this.valOutFormat = valOutFormat;
		this.item36AgreementTime = item36AgreementTime;
		this.listOperationSetting = listOperationSetting;
	}
}
