package nts.uk.ctx.pr.core.infra.repository.wageprovision.speclayout;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.pr.core.dom.wageprovision.speclayout.SpecificationLayout;
import nts.uk.ctx.pr.core.dom.wageprovision.speclayout.SpecificationLayoutRepository;
import nts.uk.ctx.pr.core.infra.entity.wageprovision.speclayout.QpbmtSpecLayout;
import nts.uk.ctx.pr.core.infra.entity.wageprovision.speclayout.QpbmtSpecLayoutPk;

import javax.ejb.Stateless;
import java.util.List;
import java.util.Optional;

@Stateless
public class JpaSpecificationLayoutRepository extends JpaRepository implements SpecificationLayoutRepository
{

    private static final String SELECT_ALL_QUERY_STRING = "SELECT f FROM QpbmtSpecLayout f";
    private static final String SELECT_BY_KEY_STRING = SELECT_ALL_QUERY_STRING + " WHERE  f.specLayoutPk.cid =:cid AND  f.specLayoutPk.specCd =:specCd ";
    private static final String SELECT_SPEC_NAME = "SELECT e.specCode, e.specName FROM QpbmtSpecLayoutHist f INNER JOIN QpbmtSpecLayout e on e.specLayoutPk.specCd = f.specLayoutHistPk.specCd" +
            " Where f.startYearMonth > :startYearMonth AND f.specLayoutHistPk.specCd = :specCd AND f.specLayoutHistPk.cid = :cid";

    @Override
    public List<SpecificationLayout> getAllSpecificationLayout(){
        return this.queryProxy().query(SELECT_ALL_QUERY_STRING, QpbmtSpecLayout.class)
                .getList(item -> item.toDomain());
    }

    @Override
    public List<SpecificationLayout> getSpecCode(String cid, String salaryCd, int startYearMonth) {
        return  this.queryProxy().query(SELECT_SPEC_NAME, QpbmtSpecLayout.class)
                .setParameter("startYearMonth", startYearMonth)
                .setParameter("specCd", salaryCd)
                .setParameter("cid", cid)
                .getList().stream().map(item -> {return item.toDomain();});
    }

    @Override
    public Optional<SpecificationLayout> getSpecificationLayoutById(String cid, String specCd){
        return this.queryProxy().query(SELECT_BY_KEY_STRING, QpbmtSpecLayout.class)
        .setParameter("cid", cid)
        .setParameter("specCd", specCd)
        .getSingle(c->c.toDomain());
    }

    @Override
    public void add(SpecificationLayout domain){
        this.commandProxy().insert(QpbmtSpecLayout.toEntity(domain));
    }

    @Override
    public void update(SpecificationLayout domain){
        this.commandProxy().update(QpbmtSpecLayout.toEntity(domain));
    }

    @Override
    public void remove(String cid, String specCd){
        this.commandProxy().remove(QpbmtSpecLayout.class, new QpbmtSpecLayoutPk(cid, specCd));
    }
}
