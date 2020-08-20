package nts.uk.ctx.at.record.ac.eligibleemployees;

import lombok.val;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.record.dom.adapter.eligibleemployees.WorkPlaceHistAdapter;
import nts.uk.ctx.at.shared.dom.employmentrules.organizationmanagement.AffiliationPeriodAndWorkplace;
import nts.uk.ctx.at.shared.dom.employmentrules.organizationmanagement.WorkPlaceHist;
import nts.uk.ctx.bs.employee.pub.workplace.master.WorkplacePub;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

@Stateless
@TransactionAttribute(TransactionAttributeType.SUPPORTS)
public class WorkPlaceHistAdapterImpl implements WorkPlaceHistAdapter {
    @Inject
    private WorkplacePub workplacePub;
    @Override
    public List<WorkPlaceHist> GetWorkHistory(List<String> sids, DatePeriod datePeriod) {
        val listRs = workplacePub.getWplByListSidAndPeriod(sids,datePeriod);
        val rs = new ArrayList<WorkPlaceHist>();
        listRs.forEach(e->{
            val subItem = new ArrayList<AffiliationPeriodAndWorkplace>();
            e.getLstWkpIdAndPeriod().forEach(i->subItem.add(new AffiliationPeriodAndWorkplace(i.getDatePeriod(),i.getWorkplaceId())));
            rs.add(new WorkPlaceHist(e.getEmployeeId(),subItem));
        });
        return rs;
    }
}
