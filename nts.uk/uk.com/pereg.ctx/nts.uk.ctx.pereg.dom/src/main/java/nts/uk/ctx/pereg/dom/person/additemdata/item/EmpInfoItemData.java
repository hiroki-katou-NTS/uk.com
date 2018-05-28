package nts.uk.ctx.pereg.dom.person.additemdata.item;

import java.math.BigDecimal;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.dom.AggregateRoot;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.pereg.dom.person.info.dateitem.DateType;
import nts.uk.ctx.pereg.dom.person.info.item.IsRequired;
import nts.uk.ctx.pereg.dom.person.info.item.ItemCode;
import nts.uk.ctx.pereg.dom.person.personinfoctgdata.item.DataState;
import nts.uk.ctx.pereg.dom.person.personinfoctgdata.item.DataStateType;
import nts.uk.shr.pereg.app.find.dto.OptionalItemDataDto;

@NoArgsConstructor
@Getter
@Setter
// 個人情報項目データ
public class EmpInfoItemData extends AggregateRoot {

	private ItemCode itemCode;

	private String perInfoDefId;

	private String recordId;

	private String perInfoCtgId;

	private String perInfoCtgCd;

	private String itemName;

	private IsRequired isRequired;

	private DataState dataState;

	private int dataType;

	private int selectionItemRefType;

	private String itemParentCd;

	private DateType dateType;

	private String SelectionItemRefCd;

	public EmpInfoItemData(ItemCode itemCode, String perInfoDefId, String recordId, String perInfoCtgId,
			String perInfoCtgCd, String itemName, IsRequired isRequired, DataState dataState, int dataType) {
		super();
		this.itemCode = itemCode;
		this.perInfoDefId = perInfoDefId;
		this.recordId = recordId;
		this.perInfoCtgId = perInfoCtgId;
		this.perInfoCtgCd = perInfoCtgCd;
		this.itemName = itemName;
		this.isRequired = isRequired;
		this.dataState = dataState;
		this.dataType = dataType;
	}

	public EmpInfoItemData(String perInfoDefId, String recordId, DataState dataState) {
		super();
		this.perInfoDefId = perInfoDefId;
		this.recordId = recordId;
		this.dataState = dataState;
	}

	public static EmpInfoItemData createFromJavaType(String itemCode, String perInfoDefId, String recordId,
			String perInfoCtgId, String perInfoCtgCd, String itemName, int isRequired, int dataStateType,
			String stringValue, BigDecimal intValue, GeneralDate dateValue, int dataType) {
		// initial dataTypeState
		DataState dataState = createDataState(dataStateType, stringValue, intValue, dateValue);
		IsRequired required = EnumAdaptor.valueOf(isRequired, IsRequired.class);

		return new EmpInfoItemData(new ItemCode(itemCode), perInfoDefId, recordId, perInfoCtgId, perInfoCtgCd, itemName,
				required, dataState, dataType);

	}

	public static EmpInfoItemData createFromJavaType(String perInfoDefId, String recordId, int dataStateType,
			String stringValue, BigDecimal intValue, GeneralDate dateValue, int dataType) {

		return new EmpInfoItemData(new ItemCode(""), perInfoDefId, recordId, "", "", "",
				EnumAdaptor.valueOf(0, IsRequired.class),
				createDataState(dataStateType , stringValue, intValue,
						dateValue),
				dataType);

	}
	
	/**
	 * ORIGINAL version
	 * @return
	 */
	public static EmpInfoItemData createFromJavaType(String perInfoDefId, String recordId, int dataStateType,
			String stringValue, BigDecimal intValue, GeneralDate dateValue) {
		return new EmpInfoItemData(perInfoDefId, recordId, createDataState(dataStateType, stringValue, intValue, dateValue));

	}

	private static DataState createDataState(int dataStateType, String stringValue, BigDecimal intValue,
			GeneralDate dateValue) {
		DataStateType dataStateTypeEnum = EnumAdaptor.valueOf(dataStateType, DataStateType.class);
		switch (dataStateTypeEnum) {
		case String:
			return DataState.createFromStringValue(stringValue);

		case Numeric:
			return DataState.createFromNumberValue(intValue);

		case Date:
			return DataState.createFromDateValue(dateValue);
			
		}
		return null;
	}
	
	public Object getValue() {
		switch (this.dataState.getDataStateType()) {
		case String:
			return this.dataState.getStringValue();
		case Numeric:
			return this.dataState.getNumberValue();
		case Date:
			return this.dataState.getDateValue();
		}
		return null;
	}

	public OptionalItemDataDto genToPeregDto() {
		OptionalItemDataDto dto = new OptionalItemDataDto();
		dto.setItemCode(this.itemCode.v());
		dto.setPerInfoItemDefId(perInfoDefId);
		dto.setRecordId(recordId);
		dto.setPerInfoCtgId(perInfoCtgId);
		dto.setPerInfoCtgCd(perInfoCtgCd);
		dto.setItemName(itemName);
		dto.setDataType(this.dataState.getDataStateType().value);
		switch (dataState.getDataStateType()) {
		case String:
			dto.setValue(dataState.getStringValue());
			break;
		case Numeric:
			dto.setValue(dataState.getNumberValue());
			break;
		case Date:
			dto.setValue(dataState.getDateValue());
			break;
		}
		dto.setRequired(isRequired == IsRequired.REQUIRED);
		return dto;
	}

}
