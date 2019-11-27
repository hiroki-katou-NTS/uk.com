package nts.uk.ctx.pr.shared.infra.repository.familyinfo.empfamilysocialins;

import nts.arc.layer.infra.data.JpaRepository;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.pr.shared.dom.familyinfo.empfamilysocialins.*;
import nts.uk.ctx.pr.shared.infra.entity.familyinfo.empfamilysocialins.QqsmtEmpFamilyInsHis;
import nts.uk.shr.com.context.AppContexts;

import javax.ejb.Stateless;
import java.util.List;
import java.util.Optional;


@Stateless
public class JpaEmpFamilyInsHisRepository extends JpaRepository implements EmpFamilyInsHisRepository, EmpFamilySocialInsRepository, EmpFamilySocialInsCtgRepository {

    private static final String SELECT_ALL_QUERY_STRING = "SELECT f FROM QqsmtEmpFamilyInsHis f ";
    private static final  String SELECT_EM_FAMILY = "SELECT f FROM QqsmtEmpFamilyInsHis f WHERE f.empFamilyInsHisPk.empId =:empId AND f.empFamilyInsHisPk.familyId =:familyId AND f.empFamilyInsHisPk.cid =:cid";
    private static final String SELECT_FAMILY_SOCIAL  = "SELECT f FROM QqsmtEmpFamilyInsHis f WHERE f.empFamilyInsHisPk.historyId =:historyId AND f.empFamilyInsHisPk.empId =:empId  AND f.empFamilyInsHisPk.familyId =:familyId " +
            "AND f.empFamilyInsHisPk.cid =:cid";

    private static final String SELECT_FAMILY_SOCIAL_CTG = SELECT_FAMILY_SOCIAL + "WHERE f.dependent =:dependent";
    private static final String SELECT_FAMILY_SOCIAL_CTG_LIST = "SELECT f FROM QqsmtEmpFamilyInsHis f WHERE f.empFamilyInsHisPk.cid =:cid AND f.empFamilyInsHisPk.empId IN :empIds  AND f.empFamilyInsHisPk.familyId =:familyId " +
            "AND f.dependent =:dependent AND f.startDate <= :baseDate AND  f.endDate >= :baseDate";

    @Override
    public Optional<EmpFamilyInsHis> getListEmFamilyHis(String empId, int familyId) {
        List<QqsmtEmpFamilyInsHis> history  =
                this.queryProxy().query(SELECT_EM_FAMILY, QqsmtEmpFamilyInsHis.class)
                .setParameter("empId", empId)
                .setParameter("familyId", familyId)
                .setParameter("cid", AppContexts.user().companyId())
                .getList();
        return  history.isEmpty() ?  Optional.empty() : Optional.of(QqsmtEmpFamilyInsHis.toDomainEmpFamilyInsHis(history)) ;
    }

    @Override
    public Optional<EmpFamilySocialIns> getEmpFamilySocialInsById(String empId, String familyId, String historyId) {
        return this.queryProxy().query(SELECT_FAMILY_SOCIAL, QqsmtEmpFamilyInsHis.class)
                .setParameter("historyId", historyId)
                .setParameter("familyId", Integer.parseInt(familyId))
                .setParameter("empId", empId)
                .setParameter("cid", AppContexts.user().companyId())
                .getSingle(c->c.toDomains());
    }

    @Override
    public Optional<EmpFamilySocialInsCtg> getEmpFamilySocialInsCtg(String empId, int familyId, String historyId) {
        return this.queryProxy().query(SELECT_FAMILY_SOCIAL_CTG, QqsmtEmpFamilyInsHis.class)
                .setParameter("historyId", historyId)
                .setParameter("familyId", familyId)
                .setParameter("empId", empId)
                .setParameter("cid", AppContexts.user().companyId())
                .setParameter("dependent", Distinction.USE.value )
                .getSingle(c->c.toDomainEmpFamilySocialInsCtg());
    }

    @Override
    public List<EmpFamilySocialInsCtg> getEmpFamilySocialInsCtg(List<String> empIds, int familyId, GeneralDate date) {
        return this.queryProxy().query(SELECT_FAMILY_SOCIAL_CTG_LIST, QqsmtEmpFamilyInsHis.class)
                .setParameter("familyId", familyId)
                .setParameter("empIds", empIds)
                .setParameter("cid", AppContexts.user().companyId())
                .setParameter("dependent", Distinction.USE.value )
                .setParameter("baseDate", date )
                .getList(c->c.toDomainEmpFamilySocialInsCtg());
    }

}
