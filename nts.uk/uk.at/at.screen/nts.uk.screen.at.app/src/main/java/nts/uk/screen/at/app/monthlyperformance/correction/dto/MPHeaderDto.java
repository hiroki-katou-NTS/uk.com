package nts.uk.screen.at.app.monthlyperformance.correction.dto;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.uk.screen.at.app.dailyperformance.correction.dto.Constraint;
import nts.uk.shr.com.i18n.TextResource;

/**
 * TODO
 */
@Getter
@Setter
@NoArgsConstructor
public class MPHeaderDto {
	private String headerText;

	private String key;

	private String dataType;

	private String width;

	private String color;

	private boolean hidden;

	private String ntsControl;

	private String ntsType;

	private String onChange;

	private Boolean changedByOther;

	private Boolean changedByYou;

	private List<MPHeaderDto> group;

	private Constraint constraint;

	public MPHeaderDto(String headerText, String key, String dataType, String width, String color, boolean hidden,
			String ntsControl, Boolean changedByOther, Boolean changedByYou) {
		super();
		this.headerText = headerText;
		this.key = key;
		this.dataType = dataType;
		this.width = width;
		this.color = color;
		this.hidden = hidden;
		this.ntsControl = ntsControl;
		this.changedByOther = changedByOther;
		this.changedByYou = changedByYou;
		this.group = new ArrayList<>();
	}
	public static List<MPHeaderDto> GenerateFixedHeader() {
		List<MPHeaderDto> lstHeader = new ArrayList<>();
		
		lstHeader.add(new MPHeaderDto("ID", "id", "String", "30px", "", false, "Label", true, true));
		//G_1   状態
		lstHeader.add(new MPHeaderDto(TextResource.localize("KMW003_21"), "state", "String", "30px", "", false, "FlexImage", true, true));
		//G_2 アラーム/エラー	
		lstHeader.add(new MPHeaderDto(TextResource.localize("KMW003_22"), "error", "String", "60px", "", false, "Label", true, true));
		//G_3 社員コード
		lstHeader.add(new MPHeaderDto(TextResource.localize("KMW003_23"), "employeeCode", "String", "120px", "", false,
				"Label", true, true));
		//G_4 社員名
		lstHeader.add(new MPHeaderDto(TextResource.localize("KMW003_24"), "employeeName", "String", "190px", "", false,
				"Label", true, true));
		//G_5 個人プロフィール
		lstHeader.add(new MPHeaderDto("", "picture-person", "String", "35px", "", false, "Image", true, true));		
		//G_6 本人確認
		lstHeader.add(new MPHeaderDto(TextResource.localize("KMW003_25"), "identify", "boolean", "35px", "", false,
				"Checkbox", true, true));
		//G_8 日別確認
		lstHeader.add(new MPHeaderDto(TextResource.localize("KMW003_27"), "dailyconfirm", "String", "190px", "", false,
				"Label", true, true));
		//G_9 日別実績の修正
		lstHeader.add(new MPHeaderDto(TextResource.localize("KMW003_28"), "dailyperformace", "String", "35px", "", false,
				"Button", true, true));
		return lstHeader;
	}
	public void setHeaderText(MPAttendanceItem param) {
		if (param.getLineBreakPosition() > 0) {
			this.headerText = param.getName() != null ? param.getName().substring(0, param.getLineBreakPosition())
					+ "<br/>" + param.getName().substring(param.getLineBreakPosition(), param.getName().length()) : "";
		} else {
			this.headerText = param.getName() != null ? param.getName() : "";
		}
	}

	public void setHeaderColor(MPAttendanceItemControl param) {
		this.color = param.getHeaderBackgroundColor();
	}
}
