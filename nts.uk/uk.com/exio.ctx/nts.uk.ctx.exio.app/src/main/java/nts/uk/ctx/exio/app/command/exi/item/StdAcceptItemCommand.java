package nts.uk.ctx.exio.app.command.exi.item;

import lombok.Value;
import nts.uk.ctx.exio.app.command.exi.condset.AcScreenCondSetCommand;
import nts.uk.ctx.exio.dom.exi.item.StdAcceptItem;

@Value
public class StdAcceptItemCommand {

	/**
	 * 条件設定コード
	 */
	private String conditionSettingCode;

	private String categoryId;

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

	// private NumDataFormatSetDto numberFormatSetting;
	//
	// private ChrDataFormatSetDto charFormatSetting;
	//
	// private DateDataFormSetDto dateFormatSetting;
	//
	// private InsTimeDatFmSetDto instTimeFormatSetting;

	private AcScreenCondSetCommand screenConditionSetting;

	public StdAcceptItemCommand(String conditionSettingCode, String categoryId, int acceptItemNumber, int csvItemNumber,
			String csvItemName, int itemType, int categoryItemNo, AcScreenCondSetCommand screenConditionSetting) {
		super();
		this.conditionSettingCode = conditionSettingCode;
		this.acceptItemNumber = acceptItemNumber;
		this.csvItemNumber = csvItemNumber;
		this.csvItemName = csvItemName;
		this.itemType = itemType;
		this.screenConditionSetting = screenConditionSetting;
		this.categoryItemNo = categoryItemNo;
		this.categoryId = categoryId;
	}

//	public static StdAcceptItem toDomain(String companyId, int systemType, StdAcceptItemCommand dto) {
//		return new StdAcceptItem(companyId, systemType, dto.getConditionSettingCode(), dto.getCategoryId(),
//				dto.acceptItemNumber, dto.categoryItemNo, dto.csvItemNumber, dto.csvItemName, dto.itemType, null, null);
//	}

}
