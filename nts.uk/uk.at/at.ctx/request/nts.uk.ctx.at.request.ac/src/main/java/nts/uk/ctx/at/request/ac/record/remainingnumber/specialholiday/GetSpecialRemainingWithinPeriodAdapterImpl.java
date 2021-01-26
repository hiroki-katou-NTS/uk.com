package nts.uk.ctx.at.request.ac.record.remainingnumber.specialholiday;

import nts.arc.time.GeneralDate;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.request.dom.adapter.record.remainingnumber.specialholiday.GetSpecialRemainingWithinPeriodAdapter;
import nts.uk.ctx.at.request.dom.adapter.record.remainingnumber.specialholiday.TotalResultOfSpecialLeaveImport;

import javax.ejb.Stateless;
import java.util.List;
import java.util.Optional;

@Stateless
public class GetSpecialRemainingWithinPeriodAdapterImpl implements GetSpecialRemainingWithinPeriodAdapter {
    @Override
    public TotalResultOfSpecialLeaveImport algorithm(String companyId,
                                                     String employeeId,
                                                     DatePeriod aggrPeriod,
                                                     boolean achievementsReferenceOnly,
                                                     GeneralDate criteriaDate,
                                                     Integer specialLeaveCode,
                                                     Optional<Boolean> isOverWriteOpt,
                                                     Optional<List<Object>> forOverWriteList,
                                                     Optional<Object> prevHolidayOver60h) {
        //TODO: wait for new requestList273
        TotalResultOfSpecialLeaveImport result = new TotalResultOfSpecialLeaveImport();
        return result;
    }
}
