package nts.uk.shr.pereg.app;

import java.math.BigDecimal;

import org.apache.commons.lang3.StringUtils;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.arc.enums.EnumAdaptor;
import nts.arc.time.GeneralDate;

@AllArgsConstructor
@NoArgsConstructor
public class ItemValue {

	private String definitionId;
	private String itemCode;
	private String value;
	private int type;
	
	public static ItemValue createItemValue(String definitionId, String itemCode, String value, int dataType,
			Integer selectionRefType, String selectionRefCode) {
		ItemValue itemValue = new ItemValue();
		itemValue.definitionId = definitionId;
		itemValue.itemCode = itemCode;
		itemValue.value = value;
		ItemValueType itemValueType = EnumAdaptor.valueOf(dataType, ItemValueType.class);
		switch (itemValueType) {
		case STRING:
			itemValue.type = 1;
			break;
		case NUMERIC:
		case TIME:
		case TIMEPOINT:
		case NUMBERIC_BUTTON:
			itemValue.type = 2;
			break;
		case DATE:
			itemValue.type = 3;
			break;
		case SELECTION:
		case SELECTION_RADIO:
		case SELECTION_BUTTON:
			switch (selectionRefType) {
			// 1:専用マスタ(DesignatedMaster)
			case 1:
				if (selectionRefCode.equals("M00006")) {
					itemValue.type = 2;
				} else {
					itemValue.type = 1;
				}
				break;
			//2:コード名称(CodeName)
			case 2:
				itemValue.type = 1;
				break;
			// 3:列挙型(Enum)
			case 3:
				itemValue.type = 2;
				break;
			}
			break;
		
		default:
			itemValue.type = 1;
			break;
		}
		return itemValue;
	}

	/**
	 * 個人情報項目定義ID
	 * 
	 * @return 個人情報項目定義ID
	 */
	public String definitionId() {
		return this.definitionId;
	}

	/**
	 * 項目定義コード
	 * 
	 * @return 項目定義コード
	 */
	public String itemCode() {
		return this.itemCode;
	}

	@SuppressWarnings("unchecked")
	public <T> T value() {
		Object convertedValue;
		switch (this.saveDataType()) {
		case NUMERIC:
			// In case of value is empty or null return default value
			if (StringUtils.isEmpty(this.value)) {
				convertedValue = null;
			} else {
				convertedValue = new BigDecimal(this.value);
			}
			break;
		case STRING:
			convertedValue = this.value;
			break;
		case DATE:
			// In case of value is empty or null return null
			if (StringUtils.isEmpty(this.value)) {
				convertedValue = null;
			} else {
				convertedValue = GeneralDate.fromString(this.value, "yyyy/MM/dd");
			}
			break;
		default:
			throw new RuntimeException("invalid type: " + this.type);
		}

		return (T) convertedValue;
	}

	public void setValue(Object obj) {
		if (obj == null){
			this.value = null;
			return;
		}
		switch (this.saveDataType()) {
		case NUMERIC:
			this.value = obj.toString();
			break;
		case STRING:
			this.value = obj.toString();
			break;
		case DATE:
			this.value = ((GeneralDate) obj).toString("yyyy/MM/dd");
			break;
		default:
			throw new RuntimeException("invalid type: " + this.type);
		}
	}

	public SaveDataType saveDataType() {
		return EnumAdaptor.valueOf(this.type, SaveDataType.class);
	}

}
