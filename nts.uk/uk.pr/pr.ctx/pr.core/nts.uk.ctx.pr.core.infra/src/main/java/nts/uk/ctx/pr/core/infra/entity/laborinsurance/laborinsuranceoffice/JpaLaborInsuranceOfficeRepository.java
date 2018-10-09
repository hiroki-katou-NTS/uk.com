package nts.uk.ctx.pr.core.infra.repository.労働保険.労働保険事業所;

import java.util.Optional;
import java.util.List;

import javax.ejb.Stateless;

import nts.uk.ctx.pr.core.dom.労働保険.労働保険事業所.LaborInsuranceOffice;
import nts.uk.ctx.pr.core.dom.労働保険.労働保険事業所.LaborInsuranceOfficeRepository;
import nts.arc.layer.infra.data.JpaRepository;

@Stateless
public class JpaLaborInsuranceOfficeRepository extends JpaRepository implements LaborInsuranceOfficeRepository
{

    private static final String SELECT_ALL_QUERY_STRING = "SELECT f FROM QpbmtLaborInsuOffice f";
    private static final String SELECT_BY_KEY_STRING = SELECT_ALL_QUERY_STRING + " WHERE ";

    @Override
    public List<LaborInsuranceOffice> getAllLaborInsuranceOffice(){
        return this.queryProxy().query(SELECT_ALL_QUERY_STRING, QpbmtLaborInsuOffice.class)
                .getList(item -> item.toDomain());
    }

    @Override
    public Optional<LaborInsuranceOffice> getLaborInsuranceOfficeById(){
        return this.queryProxy().query(SELECT_BY_KEY_STRING, QpbmtLaborInsuOffice.class)
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
    public void remove(){
        this.commandProxy().remove(QpbmtLaborInsuOffice.class, new QpbmtLaborInsuOfficePk()); 
    }
}
