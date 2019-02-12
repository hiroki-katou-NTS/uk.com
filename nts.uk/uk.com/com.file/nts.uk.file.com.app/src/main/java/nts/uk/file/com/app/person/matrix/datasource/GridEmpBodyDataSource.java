package nts.uk.file.com.app.person.matrix.datasource;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.uk.shr.pereg.app.ComboBoxObject;
@Getter
@AllArgsConstructor
public class GridEmpBodyDataSource {
	private String itemCode;
	private String itemParentCode;	
	private Object value;
	private String recordId;
	private List<ComboBoxObject> lstComboBoxValue;
	public void setValue(String textValue) {
		this.value = textValue;
	}
}
