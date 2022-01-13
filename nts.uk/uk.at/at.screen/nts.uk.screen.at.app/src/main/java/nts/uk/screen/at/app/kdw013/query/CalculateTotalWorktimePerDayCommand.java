package nts.uk.screen.at.app.kdw013.query;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.dailyattendancework.IntegrationOfDaily;

@Getter
@AllArgsConstructor
public class CalculateTotalWorktimePerDayCommand {
	// List<日別勤怠(Work)>
	public List<IntegrationOfDaily> integrationOfDailyLst;
}