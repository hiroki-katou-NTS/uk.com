package nts.uk.ctx.at.shared.infra.repository.scherec.taskmanagement.taskassign.taskassignemployee;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.shared.dom.scherec.taskmanagement.taskassign.taskassignemployee.TaskAssignEmployee;
import nts.uk.ctx.at.shared.dom.scherec.taskmanagement.repo.taskassign.taskassingemployee.TaskAssignEmployeeRepository;
import nts.uk.ctx.at.shared.infra.entity.scherec.taskmanagement.taskassign.taskassignemployee.KsrmtTaskAssignSya;
import nts.uk.ctx.at.shared.infra.entity.scherec.taskmanagement.taskassign.taskassignemployee.KsrmtTaskAssignSyaPk;

import javax.ejb.Stateless;
import java.util.List;

@Stateless
public class JpaTaskAssignEmployeeRepository extends JpaRepository implements TaskAssignEmployeeRepository {

    @Override
    public void insert(TaskAssignEmployee domain) {
        this.commandProxy().insert(KsrmtTaskAssignSya.create(domain));
    }

    @Override
    public void update(TaskAssignEmployee domain) {
        this.queryProxy().find(
                new KsrmtTaskAssignSyaPk(domain.getEmployeeId(), domain.getTaskFrameNo().v(), domain.getTaskCode().v()),
                KsrmtTaskAssignSya.class
        ).ifPresent(entity -> {
//            entity.pk
        });
    }

    @Override
    public void delete(String companyId, int taskFrameNo, String taskCode) {
        String query = "SELECT a FROM KsrmtTaskAssignSya a WHERE a.companyId = :companyId AND a.pk.taskFrameNo = :taskFrameNo AND a.pk.taskCode = :taskCode";
        List<KsrmtTaskAssignSya> entities = this.queryProxy().query(query, KsrmtTaskAssignSya.class)
                .setParameter("companyId", companyId)
                .setParameter("taskFrameNo", taskFrameNo)
                .setParameter("taskCode", taskCode)
                .getList();
        if (!entities.isEmpty()) {
            this.commandProxy().removeAll(entities);
            this.getEntityManager().flush();
        }
    }

    @Override
    public List<TaskAssignEmployee> get(String companyId) {
        String query = "SELECT a FROM KsrmtTaskAssignSya a WHERE a.companyId = :companyId";
        return this.queryProxy().query(query, KsrmtTaskAssignSya.class)
                .setParameter("companyId", companyId)
                .getList(KsrmtTaskAssignSya::toDomain);
    }

    @Override
    public List<TaskAssignEmployee> get(String employeeId, int taskFrameNo) {
        String query = "SELECT a FROM KsrmtTaskAssignSya a WHERE a.pk.employeeId = :employeeId AND a.pk.taskFrameNo = :taskFrameNo";
        return this.queryProxy().query(query, KsrmtTaskAssignSya.class)
                .setParameter("employeeId", employeeId)
                .setParameter("taskFrameNo", taskFrameNo)
                .getList(KsrmtTaskAssignSya::toDomain);
    }

    @Override
    public List<TaskAssignEmployee> get(String companyId, int taskFrameNo, String taskCode) {
        String query = "SELECT a FROM KsrmtTaskAssignSya a WHERE a.companyId = :companyId AND a.pk.taskFrameNo = :taskFrameNo AND a.pk.taskCode = :taskCode";
        return this.queryProxy().query(query, KsrmtTaskAssignSya.class)
                .setParameter("companyId", companyId)
                .setParameter("taskFrameNo", taskFrameNo)
                .setParameter("taskCode", taskCode)
                .getList(KsrmtTaskAssignSya::toDomain);
    }

    @Override
    public List<TaskAssignEmployee> getAll(String companyId, int taskFrameNo) {
        String query = "SELECT a FROM KsrmtTaskAssignSya a WHERE a.companyId = :companyId AND a.pk.taskFrameNo = :taskFrameNo";
        return this.queryProxy().query(query, KsrmtTaskAssignSya.class)
                .setParameter("companyId", companyId)
                .setParameter("taskFrameNo", taskFrameNo)
                .getList(KsrmtTaskAssignSya::toDomain);
    }
}
