package nts.uk.ctx.at.shared.infra.repository.workmanagement;

import lombok.val;
import nts.arc.layer.infra.data.DbConsts;
import nts.arc.layer.infra.data.JpaRepository;
import nts.arc.primitive.PrimitiveValueBase;
import nts.arc.time.GeneralDate;
import nts.arc.time.calendar.period.DatePeriod;
import nts.gul.collection.CollectionUtil;
import nts.uk.ctx.at.shared.dom.scherec.taskmanagement.taskframe.TaskFrameNo;
import nts.uk.ctx.at.shared.dom.scherec.taskmanagement.taskmaster.*;
import nts.uk.ctx.at.shared.dom.workmanagement.aggregateroot.workmaster.Work;
import nts.uk.ctx.at.shared.dom.workmanagement.repo.workmaster.WorkingRepository;
import nts.uk.ctx.at.shared.infra.entity.workmanagement.workmaster.KsrmtTaskChild;
import nts.uk.ctx.at.shared.infra.entity.workmanagement.workmaster.KsrmtTaskMaster;
import nts.uk.ctx.at.shared.infra.entity.workmanagement.workmaster.KsrmtTaskMasterPk;
import nts.uk.ctx.at.shared.infra.entity.workmanagement.workmaster.metamodel.KsrmtTaskChildPk_;
import nts.uk.ctx.at.shared.infra.entity.workmanagement.workmaster.metamodel.KsrmtTaskChild_;
import nts.uk.ctx.at.shared.infra.entity.workmanagement.workmaster.metamodel.KsrmtTaskMasterPk_;
import nts.uk.ctx.at.shared.infra.entity.workmanagement.workmaster.metamodel.KsrmtTaskMaster_;
import nts.uk.shr.com.color.ColorCode;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.*;
import java.util.*;
import java.util.stream.Collectors;


@Stateless
public class JpaWorkingRepository extends JpaRepository implements WorkingRepository {

    @Override
    public void insert(Work work) {
        val entityMaster = KsrmtTaskMaster.toEntity(work);
        val entityChilds = KsrmtTaskChild.toEntittys(work);
        this.commandProxy().insert(entityMaster);
        this.commandProxy().insertAll(entityChilds);
    }

    @Override
    public void update(Work work) {
        val entityMaster = KsrmtTaskMaster.toEntity(work);
        val entityChilds = KsrmtTaskChild.toEntittys(work);
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
        return getListWork(rs);
    }

    @Override
    public List<Work> getListWork(String cid, TaskFrameNo taskFrameNo) {
        EntityManager em = this.getEntityManager();
        CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
        CriteriaQuery<KsrmtTaskMaster> criteriaQuery = criteriaBuilder.createQuery(KsrmtTaskMaster.class);
        Root<KsrmtTaskMaster> root = criteriaQuery.from(KsrmtTaskMaster.class);
        criteriaQuery.select(root);
        List<Predicate> conditions = new ArrayList<>();
        conditions.add(criteriaBuilder.equal(root.get(KsrmtTaskMaster_.pk).get(KsrmtTaskMasterPk_.CID), cid));
        conditions.add(criteriaBuilder.equal(root.get(KsrmtTaskMaster_.pk).get(KsrmtTaskMasterPk_.FRAMENO), taskFrameNo.v()));
        criteriaQuery.where(conditions.toArray(new Predicate[]{}));
        TypedQuery<KsrmtTaskMaster> query = em.createQuery(criteriaQuery);
        List<KsrmtTaskMaster> rs = query.getResultList();
        return getListWork(rs);
    }

    @Override
    public Optional<Work> getOptionalWork(String cid, TaskFrameNo taskFrameNo, TaskCode code) {
        val entityOpt = this.queryProxy().find(new KsrmtTaskMasterPk(
                cid,
                taskFrameNo.v(),
                code.v()
        ), KsrmtTaskMaster.class);
        return entityOpt.map(this::getListWork);
    }

    @Override
    public List<Work> getListWork(String cid, List<TaskFrameNo> taskFrameNos) {
        EntityManager em = this.getEntityManager();
        CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
        CriteriaQuery<KsrmtTaskMaster> criteriaQuery = criteriaBuilder.createQuery(KsrmtTaskMaster.class);
        Root<KsrmtTaskMaster> root = criteriaQuery.from(KsrmtTaskMaster.class);
        criteriaQuery.select(root);

        List<KsrmtTaskMaster> result = new ArrayList<>();

        CollectionUtil.split(taskFrameNos, DbConsts.MAX_CONDITIONS_OF_IN_STATEMENT, frameNos -> {
            // Predicate where clause
            List<Predicate> conditions = new ArrayList<>();
            List<Integer> integerList = frameNos.stream().map(PrimitiveValueBase::v).collect(Collectors.toList());
            conditions.add(criteriaBuilder.equal(root.get(KsrmtTaskMaster_.pk).get(KsrmtTaskMasterPk_.CID), cid));
            conditions.add(root.get(KsrmtTaskMaster_.pk).get(KsrmtTaskMasterPk_.FRAMENO).in(integerList));
            criteriaQuery.where(conditions.toArray(new Predicate[]{}));
            TypedQuery<KsrmtTaskMaster> query = em.createQuery(criteriaQuery);
            result.addAll(query.getResultList());
        });
        return getListWork(result);
    }

    @Override
    public List<Work> getListWork(String cid, GeneralDate referenceDate) {
        EntityManager em = this.getEntityManager();
        CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
        CriteriaQuery<KsrmtTaskMaster> criteriaQuery = criteriaBuilder.createQuery(KsrmtTaskMaster.class);
        Root<KsrmtTaskMaster> root = criteriaQuery.from(KsrmtTaskMaster.class);
        criteriaQuery.select(root);
        List<Predicate> conditions = new ArrayList<>();
        conditions.add(criteriaBuilder.equal(root.get(KsrmtTaskMaster_.pk).get(KsrmtTaskMasterPk_.CID), cid));
        conditions.add(criteriaBuilder.lessThanOrEqualTo(root.get(KsrmtTaskMaster_.EXPSTARTDATE), referenceDate));
        conditions.add(criteriaBuilder.greaterThanOrEqualTo(root.get(KsrmtTaskMaster_.EXPENDDATE), referenceDate));
        criteriaQuery.where(conditions.toArray(new Predicate[]{}));
        TypedQuery<KsrmtTaskMaster> query = em.createQuery(criteriaQuery);
        List<KsrmtTaskMaster> rs = query.getResultList();
        return getListWork(rs);
    }

    @Override
    public List<Work> getListWork(String cid, GeneralDate referenceDate, List<TaskFrameNo> taskFrameNos) {
        EntityManager em = this.getEntityManager();
        CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
        CriteriaQuery<KsrmtTaskMaster> criteriaQuery = criteriaBuilder.createQuery(KsrmtTaskMaster.class);
        Root<KsrmtTaskMaster> root = criteriaQuery.from(KsrmtTaskMaster.class);
        criteriaQuery.select(root);

        List<KsrmtTaskMaster> result = new ArrayList<>();
        CollectionUtil.split(taskFrameNos, DbConsts.MAX_CONDITIONS_OF_IN_STATEMENT, frameNos -> {
            // Predicate where clause
            List<Predicate> conditions = new ArrayList<>();
            conditions.add(criteriaBuilder.equal(root.get(KsrmtTaskMaster_.pk).get(KsrmtTaskMasterPk_.CID), cid));
            conditions.add(criteriaBuilder.lessThanOrEqualTo(root.get(KsrmtTaskMaster_.EXPSTARTDATE), referenceDate));
            conditions.add(criteriaBuilder.greaterThanOrEqualTo(root.get(KsrmtTaskMaster_.EXPENDDATE), referenceDate));

            List<Integer> integerList = frameNos.stream().map(PrimitiveValueBase::v).collect(Collectors.toList());
            conditions.add(root.get(KsrmtTaskMaster_.pk).get(KsrmtTaskMasterPk_.FRAMENO).in(integerList));
            criteriaQuery.where(conditions.toArray(new Predicate[]{}));
            TypedQuery<KsrmtTaskMaster> query = em.createQuery(criteriaQuery);
            result.addAll(query.getResultList());

        });
        List<Work> works = getListWork(result);
        works.sort(Comparator.comparing(Work::getCode));
        return works;
    }

    @Override
    public List<Work> getListWork(String cid, GeneralDate referenceDate, TaskFrameNo taskFrameNo, List<TaskCode> codes) {
        EntityManager em = this.getEntityManager();
        CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
        CriteriaQuery<KsrmtTaskMaster> criteriaQuery = criteriaBuilder.createQuery(KsrmtTaskMaster.class);
        Root<KsrmtTaskMaster> root = criteriaQuery.from(KsrmtTaskMaster.class);
        criteriaQuery.select(root);
        List<KsrmtTaskMaster> result = new ArrayList<>();
        CollectionUtil.split(codes, DbConsts.MAX_CONDITIONS_OF_IN_STATEMENT, code -> {
            // Predicate where clause
            List<Predicate> conditions = new ArrayList<>();
            conditions.add(criteriaBuilder.equal(root.get(KsrmtTaskMaster_.pk).get(KsrmtTaskMasterPk_.CID), cid));
            conditions.add(criteriaBuilder.lessThanOrEqualTo(root.get(KsrmtTaskMaster_.EXPSTARTDATE), referenceDate));
            conditions.add(criteriaBuilder.greaterThanOrEqualTo(root.get(KsrmtTaskMaster_.EXPENDDATE), referenceDate));

            List<String> stringListCodes = code.stream().map(PrimitiveValueBase::v).collect(Collectors.toList());
            conditions.add(criteriaBuilder.equal(root.get(KsrmtTaskMaster_.pk).get(KsrmtTaskMasterPk_.FRAMENO), taskFrameNo.v()));
            conditions.add(root.get(KsrmtTaskMaster_.pk).get(KsrmtTaskMasterPk_.CD).in(stringListCodes));
            criteriaQuery.where(conditions.toArray(new Predicate[]{}));
            TypedQuery<KsrmtTaskMaster> query = em.createQuery(criteriaQuery);
            result.addAll(query.getResultList());

        });
        List<Work> works = getListWork(result);
        works.sort(Comparator.comparing(Work::getCode));
        return works;
    }

    @Override
    public List<Work> getListWork(String cid, TaskFrameNo taskFrameNo, TaskCode code) {
        Optional<Work> optionalWork = this.getOptionalWork(cid, taskFrameNo, code);
        if (!optionalWork.isPresent() || optionalWork.get().getChildWorkList().isEmpty())
            return Collections.emptyList();
        val listCode = optionalWork.get().getChildWorkList();
        return this.getListWork(cid, taskFrameNo, listCode);

    }

    @Override
    public List<Work> getListWork(String cid, TaskFrameNo taskFrameNo, List<TaskCode> codes) {
        EntityManager em = this.getEntityManager();
        CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
        CriteriaQuery<KsrmtTaskMaster> criteriaQuery = criteriaBuilder.createQuery(KsrmtTaskMaster.class);
        Root<KsrmtTaskMaster> root = criteriaQuery.from(KsrmtTaskMaster.class);
        criteriaQuery.select(root);
        List<KsrmtTaskMaster> result = new ArrayList<>();
        CollectionUtil.split(codes, DbConsts.MAX_CONDITIONS_OF_IN_STATEMENT, code -> {
            // Predicate where clause
            List<Predicate> conditions = new ArrayList<>();
            conditions.add(criteriaBuilder.equal(root.get(KsrmtTaskMaster_.pk).get(KsrmtTaskMasterPk_.CID), cid));
            List<String> stringListCodes = code.stream().map(PrimitiveValueBase::v).collect(Collectors.toList());
            conditions.add(criteriaBuilder.equal(root.get(KsrmtTaskMaster_.pk).get(KsrmtTaskMasterPk_.FRAMENO), taskFrameNo.v()));
            conditions.add(root.get(KsrmtTaskMaster_.pk).get(KsrmtTaskMasterPk_.CD).in(stringListCodes));
            criteriaQuery.where(conditions.toArray(new Predicate[]{}));
            TypedQuery<KsrmtTaskMaster> query = em.createQuery(criteriaQuery);
            result.addAll(query.getResultList());

        });
        List<Work> works = getListWork(result);
        works.sort(Comparator.comparing(Work::getCode));
        return works;
    }

    @Override
    public boolean checkExit(String cid, TaskFrameNo taskFrameNo, TaskCode code) {
        val rs = this.getOptionalWork(cid, taskFrameNo, code);
        return rs.isPresent();
    }


    private List<Work> getListWork(List<KsrmtTaskMaster> masterList) {

        return masterList.stream().map(this::getListWork).collect(Collectors.toList());
    }

    private Work getListWork(KsrmtTaskMaster e) {

        return new Work(
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

        );
    }


}
