package nts.uk.ctx.bs.employee.app.find.itemvalue;

import find.person.info.item.DataTypeStateDto;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
public class ItemDefinitionValueDto {
	// for label text
	@NonNull
	private String itemName;

	// for label constraint
	@NonNull
	private String itemCode;

	// is required?
	// for render control label
	private boolean required;

	// value of item definition
	@NonNull
	private Object value;

	// containt some infor of item for
	// render control
	private DataTypeStateDto item;
}
