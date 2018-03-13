package nts.uk.ctx.exio.app.find.exi.item;

import lombok.Value;
import nts.uk.ctx.exio.app.find.exi.condset.AcScreenCondSetDto;
import nts.uk.ctx.exio.app.find.exi.dataformat.ChrDataFormatSetDto;
import nts.uk.ctx.exio.app.find.exi.dataformat.DateDataFormSetDto;
import nts.uk.ctx.exio.app.find.exi.dataformat.InsTimeDatFmSetDto;
import nts.uk.ctx.exio.app.find.exi.dataformat.NumDataFormatSetDto;
import nts.uk.ctx.exio.app.find.exi.dataformat.TimeDataFmSetDto;
import nts.uk.ctx.exio.dom.exi.dataformat.ChrDataFormatSet;
import nts.uk.ctx.exio.dom.exi.dataformat.DateDataFormSet;
import nts.uk.ctx.exio.dom.exi.dataformat.InsTimeDatFmSet;
import nts.uk.ctx.exio.dom.exi.dataformat.ItemType;
import nts.uk.ctx.exio.dom.exi.dataformat.NumDataFormatSet;
import nts.uk.ctx.exio.dom.exi.dataformat.TimeDataFormatSet;
import nts.uk.ctx.exio.dom.exi.item.StdAcceptItem;

/**
 * 受入項目（定型）
 */

@Value
public class StdAcceptItemDto {

	private int systemType;
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
	
	private int categoryItemNo;

	private NumDataFormatSetDto numberFormatSetting;

	private ChrDataFormatSetDto charFormatSetting;

	private DateDataFormSetDto dateFormatSetting;

	private InsTimeDatFmSetDto instTimeFormatSetting;

	private TimeDataFmSetDto timeFormatSetting;

	private AcScreenCondSetDto screenConditionSetting;

	public static StdAcceptItemDto fromDomain(StdAcceptItem domain) {
		return new StdAcceptItemDto(domain.getSystemType().value, domain.getConditionSetCd().v(), domain.getAcceptItemNumber(),
				domain.getCsvItemNumber(), domain.getCsvItemName(), domain.getItemType().value, domain.getCategoryItemNo(),
				domain.getItemType() == ItemType.NUMERIC && domain.getDataFormatSetting() != null
						? NumDataFormatSetDto.fromDomain((NumDataFormatSet) domain.getDataFormatSetting()) : null,
				domain.getItemType() == ItemType.CHARACTER && domain.getDataFormatSetting() != null
						? ChrDataFormatSetDto.fromDomain((ChrDataFormatSet) domain.getDataFormatSetting()) : null,
				domain.getItemType() == ItemType.DATE && domain.getDataFormatSetting() != null
						? DateDataFormSetDto.fromDomain((DateDataFormSet) domain.getDataFormatSetting()) : null,
				domain.getItemType() == ItemType.INS_TIME && domain.getDataFormatSetting() != null
						? InsTimeDatFmSetDto.fromDomain((InsTimeDatFmSet) domain.getDataFormatSetting()) : null,
				domain.getItemType() == ItemType.TIME && domain.getDataFormatSetting() != null
						? TimeDataFmSetDto.fromDomain((TimeDataFormatSet) domain.getDataFormatSetting()) : null,
				domain.getAcceptScreenConditionSetting().isPresent()
						? AcScreenCondSetDto.fromDomain(domain.getAcceptScreenConditionSetting().get()) : null);
	}

}
