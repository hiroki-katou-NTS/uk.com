package nts.uk.ctx.exio.app.find.exi.item;

import lombok.Value;
import nts.uk.ctx.exio.app.find.exi.condset.AcScreenCondSetDto;
import nts.uk.ctx.exio.app.find.exi.dataformat.ChrDataFormatSetDto;
import nts.uk.ctx.exio.app.find.exi.dataformat.DateDataFormSetDto;
import nts.uk.ctx.exio.app.find.exi.dataformat.InsTimeDatFmSetDto;
import nts.uk.ctx.exio.app.find.exi.dataformat.NumDataFormatSetDto;
import nts.uk.ctx.exio.dom.exi.item.StdAcceptItem;

/**
 * 受入項目（定型）
 */

@Value
public class StdAcceptItemDto {

	/**
	 * 条件設定コード
	 */
	private String conditionSettingCode;

	/**
	 * 受入項目番号
	 */
	private int acceptItemNumber;

	/**
	 * CSV項目番号
	 */
	private int csvItemNumber;

	/**
	 * CSV項目名
	 */
	private String csvItemName;

	/**
	 * 項目型
	 */
	private int itemType;

//	private NumDataFormatSetDto numberFormatSetting;
//
//	private ChrDataFormatSetDto charFormatSetting;
//
//	private DateDataFormSetDto dateFormatSetting;
//
//	private InsTimeDatFmSetDto instTimeFormatSetting;

	private AcScreenCondSetDto screenConditionSetting;
	
	public StdAcceptItemDto(String conditionSettingCode, int acceptItemNumber, int csvItemNumber, String csvItemName,
			int itemType, AcScreenCondSetDto screenConditionSetting) {
		super();
		this.conditionSettingCode = conditionSettingCode;
		this.acceptItemNumber = acceptItemNumber;
		this.csvItemNumber = csvItemNumber;
		this.csvItemName = csvItemName;
		this.itemType = itemType;
		this.screenConditionSetting = screenConditionSetting;
	}

	public static StdAcceptItemDto fromDomain(StdAcceptItem domain) {
		return new StdAcceptItemDto(domain.getConditionSetCd().v(), domain.getAcceptItemNumber(), domain.getCsvItemNumber(), domain.getCsvItemName(), domain.getItemType().value, null);
	}

}
