package nts.uk.ctx.at.shared.dom.scherec.workinfo;

import java.util.Optional;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.shared.dom.WorkInformation;

public interface GetWorkInfoSchedule {
	// 予定データから勤務情報を取得する
	public Optional<WorkInformation> getWorkInfoSc(String employeeId, GeneralDate baseDate);
}
