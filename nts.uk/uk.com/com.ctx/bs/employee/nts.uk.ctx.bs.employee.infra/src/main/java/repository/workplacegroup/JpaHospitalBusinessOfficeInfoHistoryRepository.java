package repository.workplacegroup;

import entity.workplacegroup.BsymtMedcareNightShiftRuleHist;
import entity.workplacegroup.metamodel.BsymtMedcareNightShiftRuleHistPk_;
import entity.workplacegroup.metamodel.BsymtMedcareNightShiftRuleHist_;
import lombok.val;
import nts.arc.layer.infra.data.JpaRepository;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.bs.employee.dom.workplace.group.hospitalofficeinfo.HospitalBusinessOfficeInfo;
import nts.uk.ctx.bs.employee.dom.workplace.group.hospitalofficeinfo.HospitalBusinessOfficeInfoHistory;
import nts.uk.ctx.bs.employee.dom.workplace.group.hospitalofficeinfo.HospitalBusinessOfficeInfoHistoryRepository;
import nts.uk.shr.com.context.AppContexts;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class JpaHospitalBusinessOfficeInfoHistoryRepository extends JpaRepository implements HospitalBusinessOfficeInfoHistoryRepository {
    @Override
    public Optional<HospitalBusinessOfficeInfo> get(String workplaceGroupId, GeneralDate baseDate) {
        String cid = AppContexts.user().companyId();
        EntityManager entityManager = this.getEntityManager();
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<BsymtMedcareNightShiftRuleHist> criteriaQuery = criteriaBuilder.createQuery(BsymtMedcareNightShiftRuleHist.class);
        Root<BsymtMedcareNightShiftRuleHist> root = criteriaQuery.from(BsymtMedcareNightShiftRuleHist.class);
        criteriaQuery.select(root);
        List<Predicate> conditions = new ArrayList<Predicate>();
        conditions.add(criteriaBuilder.equal(root.get(BsymtMedcareNightShiftRuleHist_.pK).get(BsymtMedcareNightShiftRuleHistPk_.CID), cid));
        conditions.add(criteriaBuilder.equal(root.get(BsymtMedcareNightShiftRuleHist_.pK).get(BsymtMedcareNightShiftRuleHistPk_.WKPGRPID), workplaceGroupId));
        conditions.add(criteriaBuilder.lessThanOrEqualTo(root.get(BsymtMedcareNightShiftRuleHist_.STARTDATE), baseDate));
        conditions.add(criteriaBuilder.greaterThan(root.get(BsymtMedcareNightShiftRuleHist_.ENDDATE), baseDate));
        criteriaQuery.where(conditions.toArray(new Predicate[]{}));
        TypedQuery<BsymtMedcareNightShiftRuleHist> query = entityManager.createQuery(criteriaQuery);
        List<BsymtMedcareNightShiftRuleHist> resultList = query.getResultList();
        if (resultList.isEmpty()) {
            return Optional.empty();
        } else {
            val entity = resultList.get(0);
            return Optional.of(BsymtMedcareNightShiftRuleHist.toDomainInFo(entity));
        }

    }

    @Override
    public Optional<HospitalBusinessOfficeInfoHistory> getHospitalBusinessOfficeInfoHistory(String workplaceGroupId) {
        val cid = AppContexts.user().companyId();
        EntityManager entityManager = this.getEntityManager();
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<BsymtMedcareNightShiftRuleHist> criteriaQuery = criteriaBuilder.createQuery(BsymtMedcareNightShiftRuleHist.class);
        Root<BsymtMedcareNightShiftRuleHist> root = criteriaQuery.from(BsymtMedcareNightShiftRuleHist.class);
        criteriaQuery.select(root);

        List<Predicate> conditions = new ArrayList<>();
        conditions.add(criteriaBuilder.equal(root.get(BsymtMedcareNightShiftRuleHist_.pK).get(BsymtMedcareNightShiftRuleHistPk_.WKPGRPID), workplaceGroupId));
        conditions.add(criteriaBuilder.equal(root.get(BsymtMedcareNightShiftRuleHist_.pK).get(BsymtMedcareNightShiftRuleHistPk_.CID), cid));

        criteriaQuery.where(conditions.toArray(new Predicate[]{}));

        TypedQuery<BsymtMedcareNightShiftRuleHist> query = entityManager.createQuery(criteriaQuery);
        List<BsymtMedcareNightShiftRuleHist> ruleHists = query.getResultList();
        if (ruleHists.isEmpty()) {
            return Optional.empty();
        } else {
            return Optional.of(BsymtMedcareNightShiftRuleHist.toListDomainHist(ruleHists).get(0));
        }

    }

    @Override
    public Optional<HospitalBusinessOfficeInfo> get(String historyId) {
        val cid = AppContexts.user().companyId();
        EntityManager entityManager = this.getEntityManager();
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<BsymtMedcareNightShiftRuleHist> criteriaQuery = criteriaBuilder.createQuery(BsymtMedcareNightShiftRuleHist.class);
        Root<BsymtMedcareNightShiftRuleHist> root = criteriaQuery.from(BsymtMedcareNightShiftRuleHist.class);
        criteriaQuery.select(root);

        List<Predicate> conditions = new ArrayList<>();
        conditions.add(criteriaBuilder.equal(root.get(BsymtMedcareNightShiftRuleHist_.pK).get(BsymtMedcareNightShiftRuleHistPk_.HISTID), historyId));
        conditions.add(criteriaBuilder.equal(root.get(BsymtMedcareNightShiftRuleHist_.pK).get(BsymtMedcareNightShiftRuleHistPk_.CID), cid));

        criteriaQuery.where(conditions.toArray(new Predicate[]{}));

        TypedQuery<BsymtMedcareNightShiftRuleHist> query = entityManager.createQuery(criteriaQuery);
        List<BsymtMedcareNightShiftRuleHist> ruleHists = query.getResultList();
        if (ruleHists.isEmpty()) {
            return Optional.empty();
        } else {
            return Optional.of(BsymtMedcareNightShiftRuleHist.toDomainInFo(ruleHists.get(0)));
        }
    }

    @Override
    public boolean exists(String workplaceGroupId, GeneralDate baseDate) {
        return this.get(workplaceGroupId, baseDate).isPresent();
    }

    @Override
    public void insert(HospitalBusinessOfficeInfo hospitalInfo, HospitalBusinessOfficeInfoHistory hospitalHist) {

    }

    @Override
    public void updateHospitalInfoHistory(HospitalBusinessOfficeInfoHistory hospitalHist) {

    }

    @Override
    public void updateHospitalBusinessOfficeInfo(HospitalBusinessOfficeInfo hospitalBusinessOfficeInfo) {

    }

    @Override
    public void delete(String workplaceGroupId, String historyId) {

    }
}
