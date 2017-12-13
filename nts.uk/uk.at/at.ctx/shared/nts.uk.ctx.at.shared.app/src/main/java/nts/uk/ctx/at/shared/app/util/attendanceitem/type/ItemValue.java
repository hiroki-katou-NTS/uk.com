package nts.uk.ctx.at.shared.app.util.attendanceitem.type;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import nts.arc.time.GeneralDate;

@AllArgsConstructor
@RequiredArgsConstructor
public class ItemValue {

	private String value;

	@Getter
	private final ValueType valueType;

	@Getter
	private final String layoutCode;
	
	@Getter
	private final Integer itemId;

	@SuppressWarnings("unchecked")
	public <T> T value() {
		switch (this.valueType) {
		case INTEGER:
			return (T) new Integer(this.value);
		case STRING:
			return (T) this.value;
		case DECIMAL:
			return (T) new BigDecimal(this.value);
		case DATE:
			return (T) GeneralDate.fromString(this.value, "yyyyMMdd");
		default:
			throw new RuntimeException("invalid type: " + this.valueType);
		}
	}
	
	public void value(Object value){
		this.value = value.toString();
	}
}
