package nts.uk.screen.at.app.kdw013.query;

import lombok.Getter;
import lombok.Setter;
import nts.arc.time.GeneralDate;

@Getter
@Setter
public class TotalWorktimeDto {
	// 年月日：年月日
	public GeneralDate date;

	// 作業時間
	public int taskTime;
}