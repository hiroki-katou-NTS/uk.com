package nts.uk.ctx.pr.shared.infra.repository.socialinsurance.employeesociainsur.empcomofficehis;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.pr.shared.dom.socialinsurance.employeesociainsur.empcomofficehis.AffOfficeInformation;
import nts.uk.ctx.pr.shared.dom.socialinsurance.employeesociainsur.empcomofficehis.AffOfficeInformationRepository;
import nts.uk.ctx.pr.shared.infra.entity.socialinsurance.employeesociainsur.empcomofficehis.QqbmtAffOfficeInfor;
import nts.uk.ctx.pr.shared.infra.entity.socialinsurance.employeesociainsur.empcomofficehis.QqbmtAffOfficeInforPk;

import javax.ejb.Stateless;
import java.util.List;
import java.util.Optional;

@Stateless
public class JpaAffOfficeInformationRepository extends JpaRepository implements AffOfficeInformationRepository
{

    private static final String SELECT_ALL_QUERY_STRING = "SELECT f FROM QqbmtAffOfficeInfor f";
    private static final String SELECT_BY_KEY_STRING = SELECT_ALL_QUERY_STRING + " WHERE  f.affOfficeInforPk.socialInsuranceOfficeCd =:socialInsuranceOfficeCd AND  f.affOfficeInforPk.hisId =:hisId ";

    @Override
    public List<AffOfficeInformation> getAllAffOfficeInformation(){
        return this.queryProxy().query(SELECT_ALL_QUERY_STRING, QqbmtAffOfficeInfor.class)
                .getList(item -> item.toDomain());
    }

    @Override
    public Optional<AffOfficeInformation> getAffOfficeInformationById(String socialInsuranceOfficeCd, String hisId){
        return this.queryProxy().query(SELECT_BY_KEY_STRING, QqbmtAffOfficeInfor.class)
        .setParameter("socialInsuranceOfficeCd", socialInsuranceOfficeCd)
        .setParameter("hisId", hisId)
        .getSingle(c->c.toDomain());
    }

    @Override
    public void add(AffOfficeInformation domain){
        this.commandProxy().insert(QqbmtAffOfficeInfor.toEntity(domain));
    }

    @Override
    public void update(AffOfficeInformation domain){
        this.commandProxy().update(QqbmtAffOfficeInfor.toEntity(domain));
    }

    @Override
    public void remove(String socialInsuranceOfficeCd, String hisId){
        this.commandProxy().remove(QqbmtAffOfficeInfor.class, new QqbmtAffOfficeInforPk(socialInsuranceOfficeCd, hisId));
    }
}
