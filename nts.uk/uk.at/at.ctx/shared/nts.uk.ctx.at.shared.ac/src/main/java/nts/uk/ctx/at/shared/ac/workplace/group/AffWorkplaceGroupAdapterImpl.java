package nts.uk.ctx.at.shared.ac.workplace.group;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.shared.dom.workrule.shiftmaster.AffWorkplaceAdapter;
import nts.uk.ctx.bs.employee.pub.workplace.workplacegroup.WorkplaceGroupPublish;

@Stateless
public class AffWorkplaceGroupAdapterImpl implements AffWorkplaceAdapter{
@Inject
private WorkplaceGroupPublish groupPublish;

@Override
public List<String> getWKPID(String CID, String WKPGRPID) {
	// TODO Auto-generated method stub
	return groupPublish.getByWorkplaceGroupID(Arrays.asList(WKPGRPID)).stream().map(x->x.getWorkplaceGroupId()).collect(Collectors.toList());
}
}
