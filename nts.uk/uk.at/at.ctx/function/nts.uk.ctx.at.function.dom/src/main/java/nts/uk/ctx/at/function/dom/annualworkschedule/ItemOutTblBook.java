package nts.uk.ctx.at.function.dom.annualworkschedule;

import java.util.List;

import lombok.Getter;
import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.dom.DomainObject;
import nts.uk.ctx.at.function.dom.annualworkschedule.enums.ValueOuputFormat;
import nts.uk.ctx.at.function.dom.annualworkschedule.primitivevalue.ItemOutTblBookCode;
import nts.uk.ctx.at.function.dom.annualworkschedule.primitivevalue.ItemOutTblBookHeadingName;

/**
* 帳表に出力する項目
*/
@Getter
public class ItemOutTblBook extends DomainObject {
	/**
	* 会社ID
	*/
	private String cid;

	/**
	* 年間勤務表（36チェックリスト）の出力項目設定.コード
	*/
	private String setOutCd;

	/**
	* コード
	*/
	private ItemOutTblBookCode cd;

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
	private boolean useClassification;
	/**
	* 値の出力形式
	*/
	private ValueOuputFormat valOutFormat;

	private List<CalcFormulaItem> listOperationSetting;

	public static ItemOutTblBook createFromJavaType(String cid, String setOutCd, String cd, int sortBy, String headingName, boolean useClass,
			int valOutFormat, List<CalcFormulaItem> listCalcFormulaItem) {
		return new ItemOutTblBook(cid, setOutCd, new ItemOutTblBookCode(cd), sortBy,
				new ItemOutTblBookHeadingName(headingName), useClass,
				EnumAdaptor.valueOf(valOutFormat, ValueOuputFormat.class), listCalcFormulaItem);
	}

	public ItemOutTblBook(String cid, String setOutCd, ItemOutTblBookCode cd, int sortBy,
			ItemOutTblBookHeadingName headingName, boolean useClassification, ValueOuputFormat valOutFormat,
			List<CalcFormulaItem> listOperationSetting) {
		super();
		this.cid = cid;
		this.setOutCd = setOutCd;
		this.cd = cd;
		this.sortBy = sortBy;
		this.headingName = headingName;
		this.useClassification = useClassification;
		this.valOutFormat = valOutFormat;
		this.listOperationSetting = listOperationSetting;
	}
	
	
}
