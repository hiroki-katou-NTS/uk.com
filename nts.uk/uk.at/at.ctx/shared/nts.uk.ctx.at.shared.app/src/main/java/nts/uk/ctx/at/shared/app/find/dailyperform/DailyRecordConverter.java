package nts.uk.ctx.at.shared.app.find.dailyperform;

import nts.uk.ctx.at.shared.dom.dailyattdcal.converter.DailyRecordToAttendanceItemConverter;

// 日別のコンバーターを作成する
public interface DailyRecordConverter {
	DailyRecordToAttendanceItemConverter createDailyConverter();
}
