/**
 * 2:20:39 PM Sep 5, 2017
 */
package nts.uk.screen.at.app.dailyperformance.correction.dto;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.uk.shr.com.i18n.TextResource;

/**
 * @author hungnm
 *
 */
@Getter
@Setter
@NoArgsConstructor
public class DPHeaderDto {

	private String headerText;

	private String key;

	private String dataType;

	private String width;

	private String color;
	
	private String ntsControl;

	private Boolean changedByOther;

	private Boolean changedByYou;
	
	private DPHeaderDto(String headerText, String key, String dataType, String width, String color, String ntsControl,
			Boolean changedByOther, Boolean changedByYou) {
		super();
		this.headerText = headerText;
		this.key = key;
		this.dataType = dataType;
		this.width = width;
		this.color = color;
		this.ntsControl = ntsControl;
		this.changedByOther = changedByOther;
		this.changedByYou = changedByYou;
	}
	
	public static DPHeaderDto createSimpleHeader(String key, String width) {
		return new DPHeaderDto("", key, "String", width, "", "", false, false);
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

	public static List<DPHeaderDto> GenerateFixedHeader() {
		List<DPHeaderDto> lstHeader = new ArrayList<>();
		lstHeader.add(new DPHeaderDto("ID", "id", "String", "30px", "", "Label", true, true));
		lstHeader.add(new DPHeaderDto("状<br/>態", "state", "String", "30px", "", "Label", true, true));
		lstHeader.add(new DPHeaderDto("ER/AL", "error", "String", "60px", "", "Label", true, true));
		lstHeader.add(new DPHeaderDto(TextResource.localize("KDW003_41"), "date", "String", "90px", "", "Label", true, true));
		lstHeader.add(new DPHeaderDto(TextResource.localize("KDW003_42"), "sign", "boolean", "35px", "", "Checkbox", true, true));
		lstHeader.add(new DPHeaderDto(TextResource.localize("KDW003_32"), "employeeCode", "String", "120px", "", "Label", true, true));
		lstHeader.add(new DPHeaderDto(TextResource.localize("KDW003_33"), "employeeName", "String", "190px", "", "Label", true, true));
		lstHeader.add(new DPHeaderDto("", "picture-person", "String", "35px", "", "Image", true, true));
		return lstHeader;
	}
}
