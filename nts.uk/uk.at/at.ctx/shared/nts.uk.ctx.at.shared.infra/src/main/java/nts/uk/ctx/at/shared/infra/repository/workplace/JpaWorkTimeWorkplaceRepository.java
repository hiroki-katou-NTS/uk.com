package nts.uk.ctx.at.shared.infra.repository.workplace;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.shared.dom.worktime.workplace.WorkTimeWorkplace;
import nts.uk.ctx.at.shared.dom.worktime.workplace.WorkTimeWorkplaceRepository;
import nts.uk.ctx.at.shared.dom.worktime.worktimeset.WorkTimeSetting;
import nts.uk.ctx.at.shared.infra.entity.workplace.KshmtWorkTimeWorkplace;
import nts.uk.ctx.at.shared.infra.entity.workplace.KshmtWorkTimeWorkplacePK;
import nts.uk.ctx.at.shared.infra.entity.worktime.KshmtWorkTimeSet;
import nts.uk.ctx.at.shared.infra.repository.worktime.worktimeset.JpaWorkTimeSettingGetMemento;

import javax.ejb.Stateless;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Stateless
public class JpaWorkTimeWorkplaceRepository extends JpaRepository implements WorkTimeWorkplaceRepository {


    //TODO QA thiếu design table
    private static final String SELECT_WORKTIME_WORKPLACE_BYID = "SELECT a FROM KshmtWorkTimeSet a JOIN KshmtWorkTimeWorkplace b "
        + " ON a.kshmtWorkTimeSetPK.cid = b.kshmtWorkTimeWorkplacePK.companyID "
        + " AND a.kshmtWorkTimeSetPK.worktimeCd = b.kshmtWorkTimeWorkplacePK.workTimeID "
        + " WHERE b.kshmtWorkTimeWorkplacePK.companyID = :companyID "
        + " AND b.kshmtWorkTimeWorkplacePK.workplaceID = :workplaceID "
        + " AND a.abolitionAtr = 1";

    private static final String SELECT;

    private static final String FIND_BY_CID_AND_WKPID;

    static {
        StringBuilder builderString = new StringBuilder();
        builderString.append(" SELECT a FROM KshmtWorkTimeWorkplace a  ");
        builderString.append(" WHERE a.kshmtWorkTimeWorkplacePK.companyID = :companyId ");
        SELECT = builderString.toString();

        builderString = new StringBuilder();
        builderString.append(SELECT);
        builderString.append(" AND a.kshmtWorkTimeWorkplacePK.workplaceID = :workplaceId ");
        FIND_BY_CID_AND_WKPID = builderString.toString();
    }

    @Override
    public List<WorkTimeSetting> getWorkTimeWorkplaceById(String companyID, String workplaceID) {
        List<WorkTimeSetting> getWorkTimeWorkplaceById = this.queryProxy()
            .query(SELECT_WORKTIME_WORKPLACE_BYID, KshmtWorkTimeSet.class)
            .setParameter("companyID", companyID)
            .setParameter("workplaceID", workplaceID)
            .getList(c -> new WorkTimeSetting(new JpaWorkTimeSettingGetMemento(c)));
        return getWorkTimeWorkplaceById;
    }

    @Override
    public void add(WorkTimeWorkplace domain) {
        this.commandProxy().insertAll(KshmtWorkTimeWorkplace.toEntity(domain));
    }

    @Override
    public void update(WorkTimeWorkplace domain) {
        this.commandProxy().updateAll(KshmtWorkTimeWorkplace.toEntity(domain));
    }

    @Override
    public void remove(WorkTimeWorkplace domain) {

        List<KshmtWorkTimeWorkplacePK> pks = KshmtWorkTimeWorkplace.toEntity(domain).stream().map(x ->
            new KshmtWorkTimeWorkplacePK(x.kshmtWorkTimeWorkplacePK.companyID,
                x.kshmtWorkTimeWorkplacePK.workplaceID,
                x.kshmtWorkTimeWorkplacePK.workTimeID)).collect(Collectors.toList());
        this.commandProxy().removeAll(KshmtWorkTimeWorkplace.class,pks);
    }

    @Override
    public List<WorkTimeWorkplace> getByCId(String companyID) {
        List<KshmtWorkTimeWorkplace> listEntity = this.queryProxy().query(SELECT, KshmtWorkTimeWorkplace.class)
            .setParameter("companyId", companyID).getList();

        Map<String,List<KshmtWorkTimeWorkplace>> listMap = listEntity.stream().collect(Collectors.groupingBy(x -> x.kshmtWorkTimeWorkplacePK.workplaceID,Collectors.toList()));

        return listMap.entrySet().stream().map(x -> KshmtWorkTimeWorkplace.toDomain(x.getValue())).collect(Collectors.toList());

    }

    @Override
    public Optional<WorkTimeWorkplace> getByCIdAndWkpId(String companyID, String workplaceID) {
        List<KshmtWorkTimeWorkplace> listEntity = this.queryProxy().query(FIND_BY_CID_AND_WKPID, KshmtWorkTimeWorkplace.class)
            .setParameter("companyId", companyID)
            .setParameter("workplaceId", workplaceID)
            .getList();

        WorkTimeWorkplace domain = KshmtWorkTimeWorkplace.toDomain(listEntity);
        return domain == null ? Optional.empty() : Optional.of(domain);
    }

}
