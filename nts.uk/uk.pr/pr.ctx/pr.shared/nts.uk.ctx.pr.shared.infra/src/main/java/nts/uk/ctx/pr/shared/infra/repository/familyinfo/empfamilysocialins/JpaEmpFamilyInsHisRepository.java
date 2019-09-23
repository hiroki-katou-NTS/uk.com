package nts.uk.ctx.pr.shared.infra.repository.familyinfo.empfamilysocialins;

import nts.arc.layer.infra.data.JpaRepository;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.pr.shared.dom.familyinfo.empfamilysocialins.*;
import nts.uk.ctx.pr.shared.infra.entity.familyinfo.empfamilysocialins.QqsmtEmpFamilyInsHis;

import javax.ejb.Stateless;
import java.util.List;
import java.util.Optional;


@Stateless
public class JpaEmpFamilyInsHisRepository extends JpaRepository implements EmpFamilyInsHisRepository, EmpFamilySocialInsRepository, EmpFamilySocialInsCtgRepository {

    private static final String SELECT_ALL_QUERY_STRING = "SELECT f FROM QqsmtEmpFamilyInsHis f";
    private static final String SELECT_BY_EMP_ID = SELECT_ALL_QUERY_STRING + " WHERE  f.empFamilyInsHisPk.empId =:empId ";
    private static final  String SELECT_EM_FAMILY = "SELECT f FROM QqsmtEmpFamilyInsHis f WHERE f.empFamilyInsHisPk.empId =:empId AND f.empFamilyInsHisPk.familyId =:familyId";
    private static final String SELECT_FAMILY_SOCIAL  = "SELECT f FROM QqsmtEmpFamilyInsHis f WHERE f.empFamilyInsHisPk.historyId =:historyId";
    private static final String SELECT_FAMILY_SOCIAL_INS  = "SELECT f FROM QqsmtEmpFamilyInsHis f WHERE f.empFamilyInsHisPk.empId = :empId " +
            "AND f.empFamilyInsHisPk.familyId =:familyId AND f.startDate >= :date AND  f.endDate <= :date";


    @Override
    public EmpFamilyInsHis getAllEmpFamilyInsHis(String empId){
        List<QqsmtEmpFamilyInsHis> history = this.queryProxy().query(SELECT_BY_EMP_ID, QqsmtEmpFamilyInsHis.class)
                .getList();
        return history == null ? null : QqsmtEmpFamilyInsHis.toDomainEmpFamilyInsHis(history);
    }

    @Override
    public Optional<EmpFamilyInsHis> getListEmFamilyHis(String empId, int familyId) {
        List<QqsmtEmpFamilyInsHis> history  =
                this.queryProxy().query(SELECT_EM_FAMILY, QqsmtEmpFamilyInsHis.class)
                .setParameter("empId", empId)
                .setParameter("familyId", familyId)
                .getList();
        return  Optional.of(QqsmtEmpFamilyInsHis.toDomainEmpFamilyInsHis(history)) ;
    }

    @Override
    public Optional<EmpFamilySocialIns> getEmpFamilySocialInsById(String historyId) {
        return this.queryProxy().query(SELECT_FAMILY_SOCIAL, QqsmtEmpFamilyInsHis.class)
                .setParameter("historyId", historyId)
                .getSingle(c->c.toDomains());
    }

}
