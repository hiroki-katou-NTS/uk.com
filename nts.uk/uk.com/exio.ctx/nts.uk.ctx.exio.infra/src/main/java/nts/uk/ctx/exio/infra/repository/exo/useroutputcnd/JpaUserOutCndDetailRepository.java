package nts.uk.ctx.exio.infra.repository.exo.useroutputcnd;

import java.util.Optional;
import java.util.List;

import javax.ejb.Stateless;

import nts.uk.ctx.exio.infra.entity.exo.useroutputcnd.OiomtUserOutCndDetail;
import nts.uk.ctx.exio.infra.entity.exo.useroutputcnd.OiomtUserOutCndDetailPk;
import nts.uk.ctx.exio.dom.exo.useroutputcnd.UserOutCndDetailRepository;
import nts.uk.ctx.exio.dom.exo.useroutputcnd.UserOutCndDetail;
import nts.arc.layer.infra.data.JpaRepository;

@Stateless
public class JpaUserOutCndDetailRepository extends JpaRepository implements UserOutCndDetailRepository
{

    private static final String SELECT_ALL_QUERY_STRING = "SELECT f FROM OiomtUserOutCndDetail f";
    private static final String SELECT_BY_KEY_STRING = SELECT_ALL_QUERY_STRING + " WHERE ";

    @Override
    public List<UserOutCndDetail> getAllUserOutCndDetail(){
        return this.queryProxy().query(SELECT_ALL_QUERY_STRING, OiomtUserOutCndDetail.class)
                .getList(item -> item.toDomain());
    }

    @Override
    public Optional<UserOutCndDetail> getUserOutCndDetailById(){
        return this.queryProxy().query(SELECT_BY_KEY_STRING, OiomtUserOutCndDetail.class)
        .getSingle(c->c.toDomain());
    }

    @Override
    public void add(UserOutCndDetail domain){
        this.commandProxy().insert(OiomtUserOutCndDetail.toEntity(domain));
    }

    @Override
    public void update(UserOutCndDetail domain){
        this.commandProxy().update(OiomtUserOutCndDetail.toEntity(domain));
    }

    @Override
    public void remove(){
        this.commandProxy().remove(OiomtUserOutCndDetail.class, new OiomtUserOutCndDetailPk()); 
    }
}
