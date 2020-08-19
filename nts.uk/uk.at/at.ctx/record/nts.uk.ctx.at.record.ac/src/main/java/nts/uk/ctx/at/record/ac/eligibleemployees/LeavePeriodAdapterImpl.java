package nts.uk.ctx.at.record.ac.eligibleemployees;

import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.record.dom.adapter.eligibleemployees.LeavePeriodAdapter;
import nts.uk.ctx.at.shared.dom.employmentrules.organizationmanagement.LeavePeriod;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import java.util.List;

@Stateless
@TransactionAttribute(TransactionAttributeType.SUPPORTS)
public class LeavePeriodAdapterImpl implements LeavePeriodAdapter {
    @Override
    public List<LeavePeriod> GetLeavePeriod(List<String> sids, DatePeriod datePeriod) {
        return null;
    }
}
