package nts.uk.ctx.pr.shared.infra.repository.familyinfo.empfamilysocialins;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.pr.shared.dom.familyinfo.empfamilysocialins.EmpFamilyInsHis;
import nts.uk.ctx.pr.shared.dom.familyinfo.empfamilysocialins.EmpFamilyInsHisRepository;
import nts.uk.ctx.pr.shared.dom.familyinfo.empfamilysocialins.EmpFamilySocialInsCtgRepository;
import nts.uk.ctx.pr.shared.dom.familyinfo.empfamilysocialins.EmpFamilySocialInsRepository;
import nts.uk.ctx.pr.shared.infra.entity.familyinfo.empfamilysocialins.QqsmtEmpFamilyInsHis;

import javax.ejb.Stateless;
import java.util.List;


@Stateless
public class JpaEmpFamilyInsHisRepository extends JpaRepository implements EmpFamilyInsHisRepository, EmpFamilySocialInsRepository, EmpFamilySocialInsCtgRepository {

    private static final String SELECT_ALL_QUERY_STRING = "SELECT f FROM QqsmtEmpFamilyInsHis f";
    private static final String SELECT_BY_EMP_ID = SELECT_ALL_QUERY_STRING + " WHERE  f.empFamilyInsHisPk.empId =:empId ";

    @Override
    public EmpFamilyInsHis getAllEmpFamilyInsHis(String empId){
        List<QqsmtEmpFamilyInsHis> history = this.queryProxy().query(SELECT_BY_EMP_ID, QqsmtEmpFamilyInsHis.class)
                .getList();
        return history == null ? null : QqsmtEmpFamilyInsHis.toDomainEmpFamilyInsHis(history);
    }

}
