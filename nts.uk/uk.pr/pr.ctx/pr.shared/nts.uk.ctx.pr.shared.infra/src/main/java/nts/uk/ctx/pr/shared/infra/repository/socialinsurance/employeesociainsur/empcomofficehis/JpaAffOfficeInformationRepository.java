package nts.uk.ctx.pr.shared.infra.repository.socialinsurance.employeesociainsur.empcomofficehis;

import nts.arc.layer.infra.data.JpaRepository;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.pr.shared.dom.socialinsurance.employeesociainsur.empcomofficehis.AffOfficeInformation;
import nts.uk.ctx.pr.shared.dom.socialinsurance.employeesociainsur.empcomofficehis.AffOfficeInformationRepository;
import nts.uk.ctx.pr.shared.infra.entity.socialinsurance.employeesociainsur.empcomofficehis.QqsmtEmpCorpOffHis;
import nts.uk.shr.com.context.AppContexts;

import javax.ejb.Stateless;
import java.util.List;
import java.util.Optional;


@Stateless
public class JpaAffOfficeInformationRepository extends JpaRepository implements AffOfficeInformationRepository{

    private static final String SELECT_ALL_QUERY_STRING = "SELECT f FROM QqsmtEmpCorpOffHis f";
    private static final String SELECT_BY_KEY_STRING = SELECT_ALL_QUERY_STRING + " WHERE  f.empCorpOffHisPk.employeeId =:employeeId AND f.empCorpOffHisPk.historyId =:hisId AND  f.empCorpOffHisPk.cid =:cid ";

    @Override
    public List<AffOfficeInformation> getAllAffOfficeInformation() {
        return null;
    }


    @Override
    public Optional<AffOfficeInformation> getAffOfficeInformationById(String empId, String hisId) {
        return this.queryProxy().query(SELECT_BY_KEY_STRING, QqsmtEmpCorpOffHis.class)
                .setParameter("employeeId", empId)
                .setParameter("hisId",hisId)
                .setParameter("cid", AppContexts.user().companyId())
                .getSingle(x -> x.toDomain());
    }

    @Override
    public void add(AffOfficeInformation domain) {

    }

    @Override
    public void update(AffOfficeInformation domain) {

    }

    @Override
    public void remove(String socialInsuranceOfficeCd, String hisId) {

    }
}
