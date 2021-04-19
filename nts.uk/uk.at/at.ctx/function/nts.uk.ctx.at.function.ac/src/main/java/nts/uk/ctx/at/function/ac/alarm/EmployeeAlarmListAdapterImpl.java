package nts.uk.ctx.at.function.ac.alarm;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.function.dom.adapter.alarm.EmployeeAlarmListAdapter;
import nts.uk.ctx.sys.auth.pub.employee.EmployeePublisher;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;
import java.util.List;

@Stateless
@TransactionAttribute(TransactionAttributeType.SUPPORTS)
public class EmployeeAlarmListAdapterImpl implements EmployeeAlarmListAdapter {
    @Inject
    private EmployeePublisher employeePub;

    @Override
    public List<String> getListEmployeeId(String workplaceId, GeneralDate referenceDate) {
        return employeePub.getListEmployeeId(workplaceId, referenceDate);
    }
}
