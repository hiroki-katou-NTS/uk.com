package nts.uk.ctx.pr.shared.infra.repository.socialinsurance.employeesociainsur.empbenepenpeninfor;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.pr.shared.dom.socialinsurance.employeesociainsur.empbenepenpeninfor.WelfPenNumInformation;
import nts.uk.ctx.pr.shared.dom.socialinsurance.employeesociainsur.empbenepenpeninfor.WelfPenNumInformationRepository;
import nts.uk.ctx.pr.shared.infra.entity.socialinsurance.employeesociainsur.empbenepenpeninfor.QqsmtEmpPenIns;

import javax.ejb.Stateless;
import java.util.List;
import java.util.Optional;

@Stateless
public class JpaWelfPenNumInformationRepository extends JpaRepository implements WelfPenNumInformationRepository
{

    private static final String SELECT_ALL_QUERY_STRING = "SELECT f FROM QqsmtEmpPenIns f";
    private static final String SELECT_BY_KEY_STRING = SELECT_ALL_QUERY_STRING + " WHERE  f.empPenInsPk.historyId =:historyId ";

    @Override
    public List<WelfPenNumInformation> getAllWelfPenNumInformation(){
        return null;
    }

    @Override
    public Optional<WelfPenNumInformation> getWelfPenNumInformationById(String historyId){
        return this.queryProxy().query(SELECT_BY_KEY_STRING, QqsmtEmpPenIns.class)
        .setParameter("historyId", historyId)
        .getSingle(c->c.toDomain());
    }

   
}
