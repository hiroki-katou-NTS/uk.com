package nts.uk.ctx.at.function.ac.outputitemsofworkstatustable;

import nts.arc.time.GeneralDate;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.function.dom.adapter.outputitemsofworkstatustable.AttendanceItemDtoValue;
import nts.uk.ctx.at.function.dom.adapter.outputitemsofworkstatustable.AttendanceItemServiceAdapter;
import nts.uk.ctx.at.function.dom.adapter.outputitemsofworkstatustable.AttendanceResultDto;
import nts.uk.ctx.at.record.pub.dailyattendanceitem.AttendanceItemService;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Stateless
@TransactionAttribute(TransactionAttributeType.SUPPORTS)
public class AttendanceItemServiceAdapterImpl implements AttendanceItemServiceAdapter {
    @Inject
    private AttendanceItemService service;

    @Override
    public AttendanceResultDto getValueOf(String employeeId, GeneralDate workingDate, Collection<Integer> itemIds) {
        return null;
    }

}
