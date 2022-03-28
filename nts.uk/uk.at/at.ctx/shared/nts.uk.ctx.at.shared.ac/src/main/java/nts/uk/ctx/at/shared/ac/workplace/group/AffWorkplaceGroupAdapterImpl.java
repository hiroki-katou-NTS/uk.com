package nts.uk.ctx.at.shared.ac.workplace.group;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.shared.dom.workrule.shiftmaster.AffWorkplaceAdapter;
import nts.uk.ctx.bs.employee.pub.workplace.master.WorkplacePub;
import nts.uk.ctx.bs.employee.pub.workplace.workplacegroup.WorkplaceGroupPublish;

@Stateless
public class AffWorkplaceGroupAdapterImpl implements AffWorkplaceAdapter {
    @Inject
    private WorkplaceGroupPublish groupPublish;

    @Inject
    private WorkplacePub workplacePub;

    @Override
    public List<String> getWKPID(String CID, String WKPGRPID) {
        // TODO Auto-generated method stub
        return groupPublish.getByWorkplaceGroupID(Arrays.asList(WKPGRPID)).stream().map(x -> x.getWorkplaceGroupId()).collect(Collectors.toList());
    }

    @Override
    public List<String> getUpperWorkplace(String companyID, String workplaceID, GeneralDate date) {
        return this.workplacePub.getUpperWorkplace(companyID, workplaceID, date);
    }

    @Override
    public List<String> getWorkplaceIdAndUpper(String companyId, GeneralDate baseDate, String workplaceId) {
        return this.workplacePub.getWorkplaceIdAndUpper(companyId, baseDate, workplaceId);
    }
}
