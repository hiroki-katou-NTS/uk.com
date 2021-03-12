package nts.uk.ctx.at.shared.infra.repository.scherec.taskmanagement.taskassign.taskassignworkplace;

import lombok.val;
import nts.arc.layer.infra.data.DbConsts;
import nts.arc.layer.infra.data.JpaRepository;
import nts.gul.collection.CollectionUtil;
import nts.uk.ctx.at.shared.dom.scherec.taskmanagement.taskframe.TaskFrameNo;
import nts.uk.ctx.at.shared.dom.scherec.taskmanagement.taskmaster.TaskCode;
import nts.uk.ctx.at.shared.dom.scherec.taskmanagement.taskassign.taskassignworkplace.NarrowingDownTaskByWorkplace;
import nts.uk.ctx.at.shared.dom.scherec.taskmanagement.repo.taskassign.taskassingworkplace.NarrowingByWorkplaceRepository;
import nts.uk.ctx.at.shared.infra.entity.scherec.taskmanagement.taskassign.taskassignworkplace.KsrmtTaskAssignWkp;
import nts.uk.ctx.at.shared.infra.entity.scherec.taskmanagement.taskassign.taskassignworkplace.KsrmtTaskAssignWkpPk;
import nts.uk.ctx.at.shared.infra.entity.scherec.taskmanagement.taskassign.taskassignworkplace.metamodel.KsrmtTaskAssignWkpPk_;
import nts.uk.ctx.at.shared.infra.entity.scherec.taskmanagement.taskassign.taskassignworkplace.metamodel.KsrmtTaskAssignWkp_;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.*;
import java.util.*;
import java.util.stream.Collectors;

@Stateless
public class JpaNarrowingByWorkplaceRepository extends JpaRepository implements NarrowingByWorkplaceRepository {
    @Override
    public void insert(NarrowingDownTaskByWorkplace narrowing) {
        val listEntity = KsrmtTaskAssignWkp.toEntitys(narrowing);
        this.commandProxy().insertAll(listEntity);
    }

    @Override
    public void update(NarrowingDownTaskByWorkplace narrowing) {
        val listEntity = KsrmtTaskAssignWkp.toEntitys(narrowing);
        this.commandProxy().updateAll(listEntity);

    }

    @Override
    public void delete(String cid,String workPlaceId, TaskFrameNo taskFrameNo) {
        EntityManager entityManager = this.getEntityManager();
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaDelete<KsrmtTaskAssignWkp> criteriaQuery = criteriaBuilder.createCriteriaDelete(KsrmtTaskAssignWkp.class);
        Root<KsrmtTaskAssignWkp> root = criteriaQuery.from(KsrmtTaskAssignWkp.class);
        List<Predicate> condition = new ArrayList<>();
        condition.add(criteriaBuilder.equal(root.get(KsrmtTaskAssignWkp_.pk).get(KsrmtTaskAssignWkpPk_.WKPID), workPlaceId));
        condition.add(criteriaBuilder.equal(root.get(KsrmtTaskAssignWkp_.pk).get(KsrmtTaskAssignWkpPk_.TASKCD), taskFrameNo.v()));
        condition.add(criteriaBuilder.equal(root.get(KsrmtTaskAssignWkp_.companyId), cid));
        criteriaQuery.where(condition.toArray(new Predicate[]{}));
        entityManager.createQuery(criteriaQuery).executeUpdate();
    }

    @Override
    public void delete(String cid,String workPlaceId) {
        EntityManager entityManager = this.getEntityManager();
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaDelete<KsrmtTaskAssignWkp> criteriaQuery = criteriaBuilder.createCriteriaDelete(KsrmtTaskAssignWkp.class);
        Root<KsrmtTaskAssignWkp> root = criteriaQuery.from(KsrmtTaskAssignWkp.class);
        List<Predicate> condition = new ArrayList<>();
        condition.add(criteriaBuilder.equal(root.get(KsrmtTaskAssignWkp_.pk).get(KsrmtTaskAssignWkpPk_.WKPID), workPlaceId));
        condition.add(criteriaBuilder.equal(root.get(KsrmtTaskAssignWkp_.companyId), cid));
        criteriaQuery.where(condition.toArray(new Predicate[]{}));
        entityManager.createQuery(criteriaQuery).executeUpdate();
    }

    @Override
    public List<NarrowingDownTaskByWorkplace> getListWorkByCid(String cid) {
        EntityManager entityManager = this.getEntityManager();
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<KsrmtTaskAssignWkp> criteriaQuery = criteriaBuilder.createQuery(KsrmtTaskAssignWkp.class);
        Root<KsrmtTaskAssignWkp> root = criteriaQuery.from(KsrmtTaskAssignWkp.class);
        criteriaQuery.select(root);
        Predicate predicate = criteriaBuilder.equal(root.get(KsrmtTaskAssignWkp_.companyId), cid);
        criteriaQuery.where(predicate);
        TypedQuery<KsrmtTaskAssignWkp> query = entityManager.createQuery(criteriaQuery);
        List<KsrmtTaskAssignWkp> listEntity = query.getResultList();
        return this.toDomains(listEntity);
    }

    @Override
    public Optional<NarrowingDownTaskByWorkplace> getOptionalWork(String workPlaceId, TaskFrameNo taskFrameNo) {
        EntityManager entityManager = this.getEntityManager();
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<KsrmtTaskAssignWkp> criteriaQuery = criteriaBuilder.createQuery(KsrmtTaskAssignWkp.class);
        Root<KsrmtTaskAssignWkp> root = criteriaQuery.from(KsrmtTaskAssignWkp.class);
        criteriaQuery.select(root);
        List<Predicate> condition = new ArrayList<>();
        condition.add(criteriaBuilder.equal(root.get(KsrmtTaskAssignWkp_.pk).get(KsrmtTaskAssignWkpPk_.WKPID), workPlaceId));
        condition.add(criteriaBuilder.equal(root.get(KsrmtTaskAssignWkp_.pk).get(KsrmtTaskAssignWkpPk_.FRAMENO), taskFrameNo.v()));
        criteriaQuery.where(condition.toArray(new Predicate[]{}));
        TypedQuery<KsrmtTaskAssignWkp> query = entityManager.createQuery(criteriaQuery);
        List<KsrmtTaskAssignWkp> listEntity = query.getResultList();
        val listRs = this.toDomains(listEntity);
        if (listRs.isEmpty())
            return Optional.empty();
        return Optional.of(listRs.get(0));
    }

    @Override
    public List<NarrowingDownTaskByWorkplace> getListWork(List<String> workPlaceIds, TaskFrameNo taskFrameNo) {
        EntityManager entityManager = this.getEntityManager();
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<KsrmtTaskAssignWkp> criteriaQuery = criteriaBuilder.createQuery(KsrmtTaskAssignWkp.class);
        Root<KsrmtTaskAssignWkp> root = criteriaQuery.from(KsrmtTaskAssignWkp.class);
        criteriaQuery.select(root);
        List<NarrowingDownTaskByWorkplace> rs = new ArrayList<>();
        CollectionUtil.split(workPlaceIds, DbConsts.MAX_CONDITIONS_OF_IN_STATEMENT, listWpls -> {
            List<Predicate> conditions = new ArrayList<>();
            conditions.add(root.get(KsrmtTaskAssignWkp_.pk).get(KsrmtTaskAssignWkpPk_.WKPID).in(listWpls));
            conditions.add(criteriaBuilder.equal(root.get(KsrmtTaskAssignWkp_.pk).get(KsrmtTaskAssignWkpPk_.FRAMENO), taskFrameNo.v()));
            criteriaQuery.where(conditions.toArray(new Predicate[]{}));
            TypedQuery<KsrmtTaskAssignWkp> query = entityManager.createQuery(criteriaQuery);
            rs.addAll(this.toDomains(query.getResultList()));
        });

        return rs;
    }

    @Override
    public List<NarrowingDownTaskByWorkplace> getListWorkByWpl(String cid,String workPlaceId) {
        EntityManager entityManager = this.getEntityManager();
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<KsrmtTaskAssignWkp> criteriaQuery = criteriaBuilder.createQuery(KsrmtTaskAssignWkp.class);
        Root<KsrmtTaskAssignWkp> root = criteriaQuery.from(KsrmtTaskAssignWkp.class);
        criteriaQuery.select(root);
        List<Predicate> conditions = new ArrayList<>();
        conditions.add(criteriaBuilder.equal(root.get(KsrmtTaskAssignWkp_.pk).get(KsrmtTaskAssignWkpPk_.WKPID), workPlaceId));
        conditions.add(criteriaBuilder.equal(root.get(KsrmtTaskAssignWkp_.companyId), cid));
        criteriaQuery.where(conditions.toArray(new Predicate[]{}));
        TypedQuery<KsrmtTaskAssignWkp> query = entityManager.createQuery(criteriaQuery);
        return this.toDomains(query.getResultList());

    }

    private List<NarrowingDownTaskByWorkplace> toDomains(List<KsrmtTaskAssignWkp> entitys) {
        List<KsrmtTaskAssignWkpPk> listPk = entitys.stream().map(KsrmtTaskAssignWkp::getPk).collect(Collectors.toList());
        List<NarrowingDownTaskByWorkplace> rs = new ArrayList<>();
        Map<String, List<KsrmtTaskAssignWkpPk>> listMap =
                listPk.stream().collect(Collectors.groupingBy(e -> e.WKPID, Collectors.toList()));
        listMap.keySet().forEach(e -> {
            List<KsrmtTaskAssignWkpPk> wkpPkList = listMap.get(e);
            if (wkpPkList.size() > 0) {
                rs.add(new NarrowingDownTaskByWorkplace(
                        e,
                        new TaskFrameNo(wkpPkList.get(0).FRAMENO),
                        wkpPkList.stream().map(j -> new TaskCode(j.TASKCD)).collect(Collectors.toList())
                ));
            }
        });
        rs.sort(Comparator.comparing(NarrowingDownTaskByWorkplace::getTaskFrameNo));
        return rs;
    }
}
