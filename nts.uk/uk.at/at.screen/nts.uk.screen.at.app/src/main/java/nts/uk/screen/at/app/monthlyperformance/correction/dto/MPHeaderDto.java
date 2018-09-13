package nts.uk.screen.at.app.monthlyperformance.correction.dto;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.uk.ctx.at.shared.app.find.scherec.monthlyattditem.ControlOfMonthlyDto;
import nts.uk.ctx.at.shared.dom.scherec.dailyattendanceitem.enums.DailyAttendanceAtr;
import nts.uk.screen.at.app.dailyperformance.correction.dto.Constraint;
import nts.uk.screen.at.app.monthlyperformance.correction.param.PAttendanceItem;
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

	private static final String ADD_CHARACTER = "A";
	private static final String PX = "px";
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
		
		lstHeader.add(new MPHeaderDto("ID", "id", "String", "30px", "", true, "Label", true, true));
		//G_1   状態
		//lstHeader.add(new MPHeaderDto(TextResource.localize("KMW003_21"), "state", "String", "30px", "", false, "FlexImage", true, true));
		lstHeader.add(new MPHeaderDto("状<br>態", "state", "String", "30px", "", false, "FlexImage", true, true));
		String name = TextResource.localize("KMW003_22");
		String newName = name.replace("\n", "<br>");
		//G_2 アラーム/エラー	
		lstHeader.add(new MPHeaderDto(newName, "error", "String", "60px", "", false, "Label", true, true));
		//G_3 社員コード
		lstHeader.add(new MPHeaderDto(TextResource.localize("KMW003_23"), "employeeCode", "String", "85px", "", false, "Label", true, true));
		//G_4 社員名
		lstHeader.add(new MPHeaderDto(TextResource.localize("KMW003_24"), "employeeName", "String", "102px", "", false, "Label", true, true));
		//G_5 個人プロフィール
		lstHeader.add(new MPHeaderDto("", "picture-person", "String", "10px", "", false, "Image", true, true));		
		//G_6 本人確認
		lstHeader.add(new MPHeaderDto(TextResource.localize("KMW003_25"), "identify", "boolean", "35px", "", false, "Checkbox", true, true));
		//G_7 
		lstHeader.add(new MPHeaderDto(TextResource.localize("KMW003_26"), "approval", "boolean", "35px", "", false, "Checkbox", true, true));
		//G_8 日別確認
		lstHeader.add(new MPHeaderDto(TextResource.localize("KMW003_27"), "dailyconfirm", "String", "64px", "", false, "Label", true, true));
		//G_9 日別実績の修正
		lstHeader.add(new MPHeaderDto(TextResource.localize("KMW003_28"), "dailyperformace", "String", "85px", "", false, "Button", true, true));
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
	public static MPHeaderDto createSimpleHeader(PAttendanceItem item, ControlOfMonthlyDto ctrOfMonthlyDto) {
		String key = mergeString(ADD_CHARACTER, String.valueOf(item.getId()));
		String width = String.valueOf(item.getColumnWidth() == null ? 100 : item.getColumnWidth()) + PX;
		MPHeaderDto dto = new MPHeaderDto("", key, "String", width, "", false, "", false, false);
		int attendanceAtr = item.getAttendanceAtr();

		if (attendanceAtr == 4) {
			// dto.setNtsControl("TextEditorNumberSeparated");
			dto.setConstraint(new Constraint("Currency", false, ""));
		} else if (attendanceAtr == 1) {
			// dto.setNtsControl("TextEditorTimeShortHM");
			dto.setConstraint(new Constraint("Clock", false, ""));
		} else if (attendanceAtr == 2) {
			dto.setConstraint(new Constraint("Integer", false, ""));
		} else if (attendanceAtr == 3) {
			dto.setConstraint(new Constraint("HalfInt", false, ""));
		}
//		else if (attendanceAtr == DailyAttendanceAtr.TimeOfDay.value) {
//			dto.setConstraint(new Constraint("TimeWithDay", false, ""));
//		}
		// Set header text
		if (null != item.getLineBreakPosition() && item.getLineBreakPosition() > 0) {
			dto.headerText = item.getName() != null ? item.getName().substring(0, item.getLineBreakPosition()) + "<br/>"
					+ item.getName().substring(item.getLineBreakPosition(), item.getName().length()) : "";
		} else {
			dto.headerText = item.getName() != null ? item.getName() : "";
		}
		if (null != ctrOfMonthlyDto) {
			dto.setColor(ctrOfMonthlyDto.getHeaderBgColorOfMonthlyPer());
		}
		return dto;
	}
	private static String mergeString(String... x) {
		return StringUtils.join(x);
	}
}
