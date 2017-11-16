package nts.uk.ctx.bs.person.dom.person.personinfoctgdata.item;

import java.math.BigDecimal;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.dom.AggregateRoot;
import nts.arc.time.GeneralDate;

@NoArgsConstructor
@Getter
@Setter
public class PersonInfoItemData extends AggregateRoot {

	private String perInfoItemDefId;

	private String recordId;

	private DataState dataState;

	public PersonInfoItemData(String perInfoItemDefId, String recordId, DataState dataState) {
		super();
		this.perInfoItemDefId = perInfoItemDefId;
		this.recordId = recordId;
		this.dataState = dataState;
	}

	public static PersonInfoItemData createFromJavaType(String perInfoItemDefId, String recordId, int dataStateType,
			String stringValue, BigDecimal intValue, GeneralDate dateValue) {

		return new PersonInfoItemData(perInfoItemDefId, recordId, createDataState(
				EnumAdaptor.valueOf(dataStateType, DataStateType.class), stringValue, intValue, dateValue));

	}

	private static DataState createDataState(DataStateType dataStateType, String stringValue, BigDecimal intValue,
			GeneralDate dateValue) {

		DataState resultState = new DataState();

		switch (dataStateType) {
		case String:
			resultState = DataState.createFromStringValue(stringValue);
			break;

		case Numeric:
			resultState = DataState.createFromNumberValue(intValue);
			break;

		case Date:
			resultState = DataState.createFromDateValue(dateValue);
			break;

		}
		return resultState;
	}
}
