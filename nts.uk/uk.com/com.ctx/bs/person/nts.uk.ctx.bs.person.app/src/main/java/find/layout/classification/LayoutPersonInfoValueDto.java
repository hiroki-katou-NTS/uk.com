package find.layout.classification;

import find.person.info.item.DataTypeStateDto;
import lombok.Data;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class LayoutPersonInfoValueDto {

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
	private Object value;

	// containt some infor of item for
	// render control
	private DataTypeStateDto item;
}
