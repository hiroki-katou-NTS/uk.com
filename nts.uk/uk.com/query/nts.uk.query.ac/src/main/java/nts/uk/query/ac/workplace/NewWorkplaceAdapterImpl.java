package nts.uk.query.ac.workplace;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.bs.employee.pub.workplace.master.WorkplacePub;
import nts.uk.query.model.workplace.WorkplaceAdapter;
import nts.uk.query.model.workplace.WorkplaceInfoImport;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

@Stateless
public class NewWorkplaceAdapterImpl implements WorkplaceAdapter {

    @Inject
    private WorkplacePub workplacePub;

    @Override
    public List<WorkplaceInfoImport> getAllActiveWorkplace(String companyId, GeneralDate baseDate) {
        return workplacePub.getAllActiveWorkplaceInfor(companyId, baseDate)
                .stream()
                .map(w -> new WorkplaceInfoImport(
                        w.getWorkplaceId(),
                        w.getHierarchyCode(),
                        w.getWorkplaceCode(),
                        w.getWorkplaceName(),
                        w.getWorkplaceDisplayName(),
                        w.getWorkplaceGenericName(),
                        w.getWorkplaceExternalCode()))
                .collect(Collectors.toList());
    }

    @Override
    public List<WorkplaceInfoImport> getWorkplaceInfoByWkpIds(String companyId, List<String> listWorkplaceId, GeneralDate baseDate) {
        return workplacePub.getWorkplaceInforByWkpIds(companyId, listWorkplaceId, baseDate)
                .stream()
                .map(w -> new WorkplaceInfoImport(
                        w.getWorkplaceId(),
                        w.getHierarchyCode(),
                        w.getWorkplaceCode(),
                        w.getWorkplaceName(),
                        w.getWorkplaceDisplayName(),
                        w.getWorkplaceGenericName(),
                        w.getWorkplaceExternalCode()))
                .collect(Collectors.toList());
    }

    @Override
    public List<WorkplaceInfoImport> getPastWorkplaceInfo(String companyId, String historyId, List<String> listWorkplaceId) {
        return workplacePub.getPastWorkplaceInfor(companyId, historyId, listWorkplaceId)
                .stream()
                .map(w -> new WorkplaceInfoImport(
                        w.getWorkplaceId(),
                        w.getHierarchyCode(),
                        w.getWorkplaceCode(),
                        w.getWorkplaceName(),
                        w.getWorkplaceDisplayName(),
                        w.getWorkplaceGenericName(),
                        w.getWorkplaceExternalCode()))
                .collect(Collectors.toList());
    }
}
