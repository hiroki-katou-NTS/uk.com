package nts.uk.ctx.at.request.dom.application.common.service.other;

import java.util.Optional;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.request.dom.application.holidayworktime.AppHolidayWork;
import nts.uk.ctx.at.request.dom.application.overtime.AppOverTime;

/**
 * refactor 4
 * UKDesign.UniversalK.就業.KAF_申請.共通アルゴリズム.01-09_事前申請を取得.事前内容の取得.表示する事前申請内容
 * @author Doan Duy Hung
 *
 */
@AllArgsConstructor
@Getter
public class PreAppContentDisplay {
	
	private GeneralDate date;
	// 残業申請
	private Optional<AppOverTime> apOptional = Optional.empty();
	// 休日出勤申請
	private Optional<AppHolidayWork> appHolidayWork = Optional.empty();
}
