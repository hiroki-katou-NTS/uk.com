package nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.converter;

// 日別のコンバーターを作成する
public interface DailyRecordConverter {
	DailyRecordToAttendanceItemConverter createDailyConverter();
}
