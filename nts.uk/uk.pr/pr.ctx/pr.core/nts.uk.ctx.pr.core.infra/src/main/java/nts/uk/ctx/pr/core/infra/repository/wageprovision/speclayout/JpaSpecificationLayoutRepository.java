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

    @Override
    public List<SpecificationLayout> getAllSpecificationLayout(){
        return this.queryProxy().query(SELECT_ALL_QUERY_STRING, QpbmtSpecLayout.class)
                .getList(item -> item.toDomain());
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
