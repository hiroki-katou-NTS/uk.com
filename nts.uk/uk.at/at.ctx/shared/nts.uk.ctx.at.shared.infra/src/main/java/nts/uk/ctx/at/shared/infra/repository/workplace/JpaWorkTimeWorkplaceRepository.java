package nts.uk.ctx.at.shared.infra.repository.workplace;

import java.util.List;
import java.util.Optional;
//import java.util.Optional;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.shared.dom.worktime.workplace.WorkTimeWorkplace;
import nts.uk.ctx.at.shared.dom.worktime.workplace.WorkTimeWorkplaceRepository;
import nts.uk.ctx.at.shared.dom.worktime.worktimeset.WorkTimeSetting;
import nts.uk.ctx.at.shared.infra.entity.workplace.KshmtWorkTimeWorkplace;
import nts.uk.ctx.at.shared.infra.entity.worktime.KshmtWorkTimeSet;
//import nts.uk.ctx.at.shared.infra.entity.workplace.KshmtWorkTimeWorkplacePK;
import nts.uk.ctx.at.shared.infra.repository.worktime.worktimeset.JpaWorkTimeSettingGetMemento;

@Stateless
public class JpaWorkTimeWorkplaceRepository extends JpaRepository implements WorkTimeWorkplaceRepository {

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
        builderString.append(" WHERE a.pk.companyID = 'companyID' ");
        SELECT = builderString.toString();

        builderString = new StringBuilder();
        builderString.append(SELECT);
        builderString.append(" AND a.pk.workplaceID = 'workplaceID' ");
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
        //TODO not update all. need remove trước
        this.commandProxy().updateAll(KshmtWorkTimeWorkplace.toEntity(domain));
    }

    @Override
    public void remove(WorkTimeWorkplace domain) {
        this.commandProxy().removeAll(KshmtWorkTimeWorkplace.toEntity(domain));
    }

    @Override
    public List<WorkTimeWorkplace> getByCId(String companyID) {
        List<KshmtWorkTimeWorkplace> listEntity = this.queryProxy().query(SELECT, KshmtWorkTimeWorkplace.class)
            .setParameter("companyId", companyID)
            .getList();

//        return KshmtWorkTimeWorkplace.toDomain(listEntity);

        //TODO QA thiếu design table
        return null;
    }

    @Override
    public Optional<WorkTimeWorkplace> getByCIdAndWkpId(String companyID, String workplaceID) {
        List<KshmtWorkTimeWorkplace> listEntity = this.queryProxy().query(SELECT, KshmtWorkTimeWorkplace.class)
            .setParameter("companyId", companyID)
            .setParameter("workplaceID", workplaceID)
            .getList();

        WorkTimeWorkplace domain = KshmtWorkTimeWorkplace.toDomain(listEntity);
        return domain == null ? Optional.empty() : Optional.of(domain);
    }

}
