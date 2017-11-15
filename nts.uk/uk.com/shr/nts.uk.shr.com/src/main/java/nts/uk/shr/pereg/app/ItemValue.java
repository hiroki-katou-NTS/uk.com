package nts.uk.shr.pereg.app;

import java.math.BigDecimal;

import lombok.RequiredArgsConstructor;
import nts.arc.enums.EnumAdaptor;
import nts.arc.time.GeneralDate;

@RequiredArgsConstructor
public class ItemValue {

	private final String definitionId;
	private final String itemCode;
	private final String value;
	private final int type;
	
	/**
	 * 個人情報項目定義ID
	 * @return 個人情報項目定義ID
	 */
	public String definitionId() {
		return this.definitionId;
	}
	
	/**
	 * 項目定義コード
	 * @return 項目定義コード
	 */
	public String itemCode() {
		return this.itemCode;
	}
	
	@SuppressWarnings("unchecked")
	public <T> T value() {
		Object convertedValue;
		switch (this.itemValueType()) {
		case NUMERIC:
			convertedValue = new BigDecimal(this.value);
			break;
		case STRING:
			convertedValue = this.value;
			break;
		case DATE:
			convertedValue = GeneralDate.fromString(this.value, "yyyyMMdd");
			break;
		default:
			throw new RuntimeException("invalid type: " + this.type);
		}
		
		return (T)convertedValue;
	}
	
	public ItemValueType itemValueType() {
		return EnumAdaptor.valueOf(this.type, ItemValueType.class);
	}
}
