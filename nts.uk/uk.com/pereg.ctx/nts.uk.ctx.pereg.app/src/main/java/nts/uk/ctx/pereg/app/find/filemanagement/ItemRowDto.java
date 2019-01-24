package nts.uk.ctx.pereg.app.find.filemanagement;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.uk.ctx.pereg.app.find.layoutdef.classification.ActionRole;
import nts.uk.shr.pereg.app.ComboBoxObject;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ItemRowDto {
	 private String itemCode;
	 private String itemName;
	 private Object value;
	 private String textValue;
	 private String recordId;
	 private int itemOrder;
	 private ActionRole actionRole;
	 private List<ComboBoxObject> lstComboBoxValue;

}
