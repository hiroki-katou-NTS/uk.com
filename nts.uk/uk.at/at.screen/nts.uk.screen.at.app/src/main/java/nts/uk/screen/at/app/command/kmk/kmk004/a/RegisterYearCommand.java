package nts.uk.screen.at.app.command.kmk.kmk004.a;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.arc.enums.EnumAdaptor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class RegisterYearCommand {

	private int year;
	// 勤務の種類
	private int workTypeMode;
	private int screenMode;
	private boolean updateMode;
	private String itemId;

	public WorkTypeMode getWorkTypeMode() {
		return EnumAdaptor.valueOf(this.workTypeMode, WorkTypeMode.class);

	}

	public ScreenMode getScreenMode() {
		return EnumAdaptor.valueOf(this.screenMode, ScreenMode.class);

	}
}
