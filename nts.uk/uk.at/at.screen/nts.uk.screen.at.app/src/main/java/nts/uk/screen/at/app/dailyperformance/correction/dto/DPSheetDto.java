/**
 * 8:45:16 AM Sep 6, 2017
 */
package nts.uk.screen.at.app.dailyperformance.correction.dto;

import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author hungnm
 *
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DPSheetDto {

	//sheet no
	private String name;
	//sheet name
	private String text;
	private List<String> columns;

	public DPSheetDto(String name, String text) {
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
