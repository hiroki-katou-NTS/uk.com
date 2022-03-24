package nts.uk.screen.at.app.query.kfp002;

import lombok.Value;
import nts.uk.ctx.at.function.app.query.anyperiodcorrection.formatsetting.AnyPeriodCorrectionFormatDto;
import nts.uk.ctx.at.shared.app.find.scherec.monthlyattditem.ControlOfMonthlyDto;
import nts.uk.ctx.at.shared.dom.scherec.dailyattendanceitem.adapter.attendanceitemname.AttItemName;
import nts.uk.screen.at.app.monthlyperformance.correction.dto.MonthlyAttendanceItemDto;

import java.util.List;

@Value
public class AnyPeriodCorrectionDisplayDto {
    private AnyPeriodCorrectionFormatDto formatSetting;

    private List<AttItemName> items;

    private List<MonthlyAttendanceItemDto> monthlyItems;

    private List<ControlOfMonthlyDto> monthlyItemControls;
}
