package nts.uk.ctx.at.shared.ac.workplace.group;

import nts.uk.ctx.at.shared.dom.adapter.workplace.group.AffWorkplaceGroupImport;
import nts.uk.ctx.at.shared.dom.adapter.workplace.group.SharedAffWorkplaceGroupAdapter;
import nts.uk.ctx.bs.employee.pub.workplace.workplacegroup.AffWorkplaceGroupPub;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.List;
import java.util.stream.Collectors;

@Stateless
public class SharedAffWorkplaceGroupAdapterImpl implements SharedAffWorkplaceGroupAdapter {

    @Inject
    private AffWorkplaceGroupPub affWorkplaceGroupPub;

    @Override
    public List<AffWorkplaceGroupImport> getByListWkpIds(String cid, List<String> wkpIds) {
        return affWorkplaceGroupPub.getByListWkpIds(cid, wkpIds).stream().map(x ->
                new AffWorkplaceGroupImport(x.getWKPGRPID(), x.getWKPID()))
                .collect(Collectors.toList());
    }
}
