package nts.uk.file.at.app.export.outputitemsofworkstatustable;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.function.dom.outputitemsofworkstatustable.DisplayContentWorkStatus;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DisplayContentReportData {
    private List<DisplayContentWorkStatus> data;
    private DatePeriod period;
    private int mode;
}
