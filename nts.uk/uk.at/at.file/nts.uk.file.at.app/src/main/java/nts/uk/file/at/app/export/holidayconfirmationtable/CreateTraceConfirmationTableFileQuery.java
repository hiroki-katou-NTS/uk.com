package nts.uk.file.at.app.export.holidayconfirmationtable;


import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.arc.enums.EnumAdaptor;
import nts.uk.ctx.at.shared.dom.remainingnumber.specialholidaymng.interim.ManagermentAtr;

import java.util.List;

@AllArgsConstructor
@Getter
public class CreateTraceConfirmationTableFileQuery {
    private ManagermentAtr mngAtr;
    private boolean moreSubstituteHolidaysThanHolidays;
    private boolean moreHolidaysThanSubstituteHolidays;
    private List<String> listEmployeeId;

    public CreateTraceConfirmationTableFileQuery(int mngAtr, boolean moreSubstituteHolidaysThanHolidays,
                                                 boolean moreHolidaysThanSubstituteHolidays, List<String> listEmployeeId) {
        this.mngAtr = EnumAdaptor.valueOf(mngAtr, ManagermentAtr.class);
        this.moreSubstituteHolidaysThanHolidays = moreSubstituteHolidaysThanHolidays;
        this.moreHolidaysThanSubstituteHolidays = moreHolidaysThanSubstituteHolidays;
        this.listEmployeeId = listEmployeeId;

    }
}
