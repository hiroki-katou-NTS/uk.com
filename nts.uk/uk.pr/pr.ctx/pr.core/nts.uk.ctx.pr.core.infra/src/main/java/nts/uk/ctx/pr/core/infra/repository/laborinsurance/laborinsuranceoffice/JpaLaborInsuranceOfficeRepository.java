package nts.uk.ctx.pr.core.infra.repository.laborinsurance.laborinsuranceoffice;

import java.util.Optional;
import java.util.List;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.pr.core.dom.laborinsurance.laborinsuranceoffice.LaborInsuranceOffice;
import nts.uk.ctx.pr.core.dom.laborinsurance.laborinsuranceoffice.LaborInsuranceOfficeRepository;
import nts.uk.ctx.pr.core.infra.entity.laborinsurance.laborinsuranceoffice.QpbmtLaborInsuOffice;
import nts.uk.ctx.pr.core.infra.entity.laborinsurance.laborinsuranceoffice.QpbmtLaborInsuOfficePk;
import nts.uk.shr.com.context.AppContexts;

@Stateless
public class JpaLaborInsuranceOfficeRepository extends JpaRepository implements LaborInsuranceOfficeRepository
{

    private static final String SELECT_ALL_QUERY_STRING = "SELECT f FROM QpbmtLaborInsuOffice f";
    private static final String SELECT_BY_COMPANY = SELECT_ALL_QUERY_STRING + " WHERE f.laborInsuOfficePk.cid =:cid ORDER BY f.laborInsuOfficePk.laborOfficeCode";
    private static final String SELECT_BY_KEY_STRING = SELECT_ALL_QUERY_STRING + " WHERE f.laborInsuOfficePk.cid =:cid AND f.laborInsuOfficePk.laborOfficeCode =:laborOfficeCode";
    @Override
    public List<LaborInsuranceOffice> getLaborInsuranceOfficeByCompany(){
        return this.queryProxy().query(SELECT_BY_COMPANY, QpbmtLaborInsuOffice.class).setParameter("cid", AppContexts.user().companyId())
                .getList(item -> item.toDomain());
    }

    @Override
    public Optional<LaborInsuranceOffice> getLaborInsuranceOfficeById(String laborOfficeCode){
        return this.queryProxy().query(SELECT_BY_KEY_STRING, QpbmtLaborInsuOffice.class).setParameter("cid", AppContexts.user().companyId()).setParameter("laborOfficeCode", laborOfficeCode)
        .getSingle(c->c.toDomain());
    }

    @Override
    public void add(LaborInsuranceOffice domain){
        this.commandProxy().insert(QpbmtLaborInsuOffice.toEntity(domain));
    }

    @Override
    public void update(LaborInsuranceOffice domain){
        this.commandProxy().update(QpbmtLaborInsuOffice.toEntity(domain));
    }

    @Override
    public void remove(String laborOfficeCode){
        this.commandProxy().remove(QpbmtLaborInsuOffice.class, new QpbmtLaborInsuOfficePk(AppContexts.user().companyId(), laborOfficeCode));
    }
}
