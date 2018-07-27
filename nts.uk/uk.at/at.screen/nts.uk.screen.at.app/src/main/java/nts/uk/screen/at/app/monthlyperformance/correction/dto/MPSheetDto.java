
package nts.uk.screen.at.app.monthlyperformance.correction.dto;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class MPSheetDto {

	//sheet no
	private String name;
	//sheet name
	private String text;
	private List<String> columns;

	public MPSheetDto(String name, String text) {
		super();
		this.name = name;
		this.text = text;
		this.columns = new ArrayList<>();
	}

	public void addColumn(String columnName) {
		this.columns.add(columnName);
	}
	
	public boolean isExistColumn(String key){
		for(String column: this.columns){
			if(column.equals(key)){
				return true;
			}
		}
		return false;
	}
}
