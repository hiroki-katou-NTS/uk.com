package nts.uk.ctx.bs.employee.dom.regpersoninfo.personinfoadditemdata.item;

import java.math.BigDecimal;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.dom.AggregateRoot;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.bs.person.dom.person.info.item.IsRequired;

@NoArgsConstructor
@Getter
@Setter
public class EmpInfoItemData extends AggregateRoot {

	private String perInfoDefId;

	private String recordId;

	private String perInfoCtgId;

	private String itemName;

	private IsRequired isRequired;

	private DataState dataState;

	public EmpInfoItemData(String perInfoDefId, String recordId, String perInfoCtgId, String itemName,
			IsRequired isRequired, DataState dataState) {
		super();
		this.perInfoDefId = perInfoDefId;
		this.recordId = recordId;
		this.perInfoCtgId = perInfoCtgId;
		this.itemName = itemName;
		this.dataState = dataState;
	}

	public static EmpInfoItemData createFromJavaType(String perInfoDefId, String recordId, String perInfoCtgId,
			String itemName, int isRequired, int dataStateType, String stringValue, BigDecimal intValue,
			GeneralDate dateValue) {

		return new EmpInfoItemData(perInfoDefId, recordId, perInfoCtgId, itemName,
				EnumAdaptor.valueOf(isRequired, IsRequired.class), createDataState(
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
