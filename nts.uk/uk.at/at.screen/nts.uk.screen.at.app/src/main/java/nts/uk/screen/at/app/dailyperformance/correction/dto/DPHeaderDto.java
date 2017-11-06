/**
 * 2:20:39 PM Sep 5, 2017
 */
package nts.uk.screen.at.app.dailyperformance.correction.dto;

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
@AllArgsConstructor
@NoArgsConstructor
public class DPHeaderDto {

	private String headerText;

	private String key;

	private String dataType;

	private String width;

	private String color;

	private boolean changedByOther;

	private boolean changedByYou;

	public DPHeaderDto(String key, String width) {
		super();
		this.key = key;
		this.width = width;
		this.dataType = "string";
	}

	public void setHeaderText(DPAttendanceItem param) {
		if (param.getLineBreakPosition() > 0) {
			this.headerText = param.getName().substring(0, param.getLineBreakPosition()) + "<br/>"
					+ param.getName().substring(param.getLineBreakPosition(), param.getName().length());
		} else {
			this.headerText = param.getName();
		}
	}

	public void setHeaderColor(DPAttendanceItemControl param) {
		this.color = param.getHeaderBackgroundColor();
	}

}
