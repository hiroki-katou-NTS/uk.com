package nts.uk.file.at.app.export.vacation.set.holiday;

import java.util.List;

import nts.uk.shr.infra.file.report.masterlist.data.MasterData;

public interface HolidayRepository {
	List<MasterData> getAllHoliday(String cid);
}
