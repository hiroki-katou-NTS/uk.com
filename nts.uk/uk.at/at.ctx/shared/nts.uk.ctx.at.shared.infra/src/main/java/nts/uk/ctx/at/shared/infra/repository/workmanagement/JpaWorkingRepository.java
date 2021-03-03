package nts.uk.ctx.at.shared.infra.repository.workmanagement;

import lombok.val;
import nts.arc.layer.infra.data.JpaRepository;
import nts.arc.time.GeneralDate;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.shared.dom.scherec.taskmanagement.taskframe.TaskFrameNo;
import nts.uk.ctx.at.shared.dom.scherec.taskmanagement.taskmaster.*;
import nts.uk.ctx.at.shared.dom.workmanagement.aggregateroot.workmaster.Work;
import nts.uk.ctx.at.shared.dom.workmanagement.repo.workmaster.WorkingRepository;
import nts.uk.ctx.at.shared.infra.entity.workmanagement.workmaster.KsrmtTaskChild;
import nts.uk.ctx.at.shared.infra.entity.workmanagement.workmaster.KsrmtTaskChildPk;
import nts.uk.ctx.at.shared.infra.entity.workmanagement.workmaster.KsrmtTaskMaster;
import nts.uk.ctx.at.shared.infra.entity.workmanagement.workmaster.KsrmtTaskMasterPk;
import nts.uk.ctx.at.shared.infra.entity.workmanagement.workmaster.metamode.KsrmtTaskChildPk_;
import nts.uk.ctx.at.shared.infra.entity.workmanagement.workmaster.metamode.KsrmtTaskChild_;
import nts.uk.ctx.at.shared.infra.entity.workmanagement.workmaster.metamode.KsrmtTaskMasterPk_;
import nts.uk.ctx.at.shared.infra.entity.workmanagement.workmaster.metamode.KsrmtTaskMaster_;
import nts.uk.shr.com.color.ColorCode;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.*;
import javax.swing.text.html.parser.Entity;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Stateless
public class JpaWorkingRepository extends JpaRepository implements WorkingRepository {

    @Override
    public void insert(Work work) {
        val entityMaster = KsrmtTaskMaster.toEntity(work);
        val entityChilds = KsrmtTaskChild.toEntitys(work);
        this.commandProxy().insert(entityMaster);
        this.commandProxy().insertAll(entityChilds);
    }

    @Override
    public void update(Work work) {
        val entityMaster = KsrmtTaskMaster.toEntity(work);
        val entityChilds = KsrmtTaskChild.toEntitys(work);
        this.commandProxy().update(entityMaster);
        this.commandProxy().updateAll(entityChilds);
    }

    @Override
    public void delete(String cid, TaskFrameNo taskFrameNo, TaskCode code) {
        this.commandProxy().remove(KsrmtTaskMaster.class, new KsrmtTaskMasterPk(cid, taskFrameNo.v(), code.v()));
        EntityManager em = this.getEntityManager();
        CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
        CriteriaDelete<KsrmtTaskChild> childCriteriaDelete = criteriaBuilder.createCriteriaDelete(KsrmtTaskChild.class);
        Root<KsrmtTaskChild> root = childCriteriaDelete.from(KsrmtTaskChild.class);

        List<Predicate> condition = new ArrayList<>();
        condition.add(criteriaBuilder.equal(root.get(KsrmtTaskChild_.pk).get(KsrmtTaskChildPk_.CID), cid));
        condition.add(criteriaBuilder.equal(root.get(KsrmtTaskChild_.pk).get(KsrmtTaskChildPk_.FRAMENO), taskFrameNo.v()));
        condition.add(criteriaBuilder.equal(root.get(KsrmtTaskChild_.pk).get(KsrmtTaskChildPk_.CD), code.v()));
        childCriteriaDelete.where(condition.toArray(new Predicate[]{}));
        em.createQuery(childCriteriaDelete).executeUpdate();
    }

    @Override
    public List<Work> getListWork(String cid) {
        EntityManager em = this.getEntityManager();
        CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
        CriteriaQuery<KsrmtTaskMaster> criteriaQuery = criteriaBuilder.createQuery(KsrmtTaskMaster.class);
        Root<KsrmtTaskMaster> root = criteriaQuery.from(KsrmtTaskMaster.class);
        criteriaQuery.select(root);
        Predicate predicate = criteriaBuilder.equal(root.get(KsrmtTaskMaster_.pk).get(KsrmtTaskMasterPk_.CID), cid);
        criteriaQuery.where(predicate);
        TypedQuery<KsrmtTaskMaster> query = em.createQuery(criteriaQuery);
        List<KsrmtTaskMaster> rs = query.getResultList();
        return rs.stream().map(e -> new Work(
                new TaskCode(e.getPk().CD),
                new TaskFrameNo(e.getPk().FRAMENO),
                new ExternalCooperationInfo(
                        e.EXTCD1 != null ? Optional.of(new TaskExternalCode(e.EXTCD1)) : Optional.empty(),
                        e.EXTCD2 != null ? Optional.of(new TaskExternalCode(e.EXTCD2)) : Optional.empty(),
                        e.EXTCD3 != null ? Optional.of(new TaskExternalCode(e.EXTCD3)) : Optional.empty(),
                        e.EXTCD4 != null ? Optional.of(new TaskExternalCode(e.EXTCD4)) : Optional.empty(),
                        e.EXTCD5 != null ? Optional.of(new TaskExternalCode(e.EXTCD5)) : Optional.empty()
                ),
                e.getKsrmtTaskChildren().stream().map(i -> new TaskCode(i.pk.CD)).collect(Collectors.toList()),
                new DatePeriod(e.EXPSTARTDATE, e.EXPENDDATE),
                new TaskDisplayInfo(
                        new TaskName(e.NAME),
                        new TaskAbName(e.ABNAME),
                        e.COLOR != null ? Optional.of(new ColorCode(e.COLOR)) : Optional.empty(),
                        e.NOTE != null ? Optional.of(new TaskNote(e.NOTE)) : Optional.empty()

                )

        )).collect(Collectors.toList());
    }

    @Override
    public List<Work> getListWork(String cid, TaskFrameNo taskFrameNo) {
        return null;
    }

    @Override
    public Optional<Work> getOptionalWork(String cid, TaskFrameNo taskFrameNo, TaskCode code) {
        return Optional.empty();
    }

    @Override
    public List<Work> getListWork(String cid, List<TaskFrameNo> taskFrameNos) {
        return null;
    }

    @Override
    public List<Work> getListWork(String cid, GeneralDate referenceDate) {
        return null;
    }

    @Override
    public List<Work> getListWork(String cid, GeneralDate referenceDate, List<TaskFrameNo> taskFrameNos) {
        return null;
    }

    @Override
    public List<Work> getListWork(String cid, GeneralDate referenceDate, TaskFrameNo taskFrameNo, List<TaskCode> codes) {
        return null;
    }

    @Override
    public List<Work> getListWork(String cid, TaskFrameNo taskFrameNo, TaskCode code) {
        return null;
    }

    @Override
    public List<Work> getListWork(String cid, TaskFrameNo taskFrameNo, List<TaskCode> codes) {
        return null;
    }

    @Override
    public boolean checkExit(String cid, TaskFrameNo taskFrameNo, TaskCode code) {
        return false;
    }

}
