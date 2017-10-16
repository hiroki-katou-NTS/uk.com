/**
 * 8:45:16 AM Sep 6, 2017
 */
package nts.uk.screen.at.app.dailyperformance.correction.dto;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

/**
 * @author hungnm
 *
 */
@Getter
@Setter
public class DPSheetDto {

	private String name;
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
}
