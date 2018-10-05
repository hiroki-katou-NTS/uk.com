package nts.uk.ctx.exio.infra.repository.exo.executionlog;

import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.exio.dom.exo.executionlog.ExOutOpMng;
import nts.uk.ctx.exio.dom.exo.executionlog.ExOutOpMngRepository;
import nts.uk.ctx.exio.infra.entity.exo.executionlog.OiomtExOutOpMng;
import nts.uk.ctx.exio.infra.entity.exo.executionlog.OiomtExOutOpMngPk;

@Stateless
public class JpaExOutOpMngRepository extends JpaRepository implements ExOutOpMngRepository
{

    private static final String SELECT_ALL_QUERY_STRING = "SELECT f FROM OiomtExOutOpMng f";
    private static final String SELECT_BY_KEY_STRING = SELECT_ALL_QUERY_STRING + " WHERE  f.exOutOpMngPk.exOutProId =:exOutProId ";

    @Override
    public List<ExOutOpMng> getAllExOutOpMng(){
        return this.queryProxy().query(SELECT_ALL_QUERY_STRING, OiomtExOutOpMng.class)
                .getList(item -> item.toDomain());
    }

    @Override
    public Optional<ExOutOpMng> getExOutOpMngById(String exOutProId){
        return this.queryProxy().query(SELECT_BY_KEY_STRING, OiomtExOutOpMng.class)
        .setParameter("exOutProId", exOutProId)
        .getSingle(c->c.toDomain());
    }

    @Override
    public void add(ExOutOpMng domain){
        this.commandProxy().insert(OiomtExOutOpMng.toEntity(domain));
    }

    @Override
    public void update(ExOutOpMng domain){
        this.commandProxy().update(OiomtExOutOpMng.toEntity(domain));
    }

    @Override
    public void remove(String exOutProId){
        this.commandProxy().remove(OiomtExOutOpMng.class, new OiomtExOutOpMngPk(exOutProId)); 
    }
}
