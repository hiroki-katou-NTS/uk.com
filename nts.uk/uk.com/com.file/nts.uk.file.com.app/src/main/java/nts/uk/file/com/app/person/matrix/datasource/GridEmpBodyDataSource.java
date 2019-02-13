package nts.uk.file.com.app.person.matrix.datasource;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.uk.shr.pereg.app.ComboBoxObject;
@Getter
@AllArgsConstructor
public class GridEmpBodyDataSource {
	private String recordId;
	private String itemCode;
	private Object value;
	private List<ComboBoxObject> lstComboBoxValue;
	public void setValue(String textValue) {
		this.value = textValue;
	}
}
