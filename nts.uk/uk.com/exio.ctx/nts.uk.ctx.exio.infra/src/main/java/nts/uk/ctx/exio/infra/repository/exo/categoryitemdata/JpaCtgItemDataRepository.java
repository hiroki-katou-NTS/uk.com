package nts.uk.ctx.exio.infra.repository.exo.categoryitemdata;

import java.util.Optional;
import java.util.List;

import javax.ejb.Stateless;

import nts.uk.ctx.exio.infra.entity.exo.categoryitemdata.OiomtCtgItemData;
import nts.uk.ctx.exio.infra.entity.exo.categoryitemdata.OiomtCtgItemDataPk;
import nts.uk.ctx.exio.dom.exo.categoryitemdata.CtgItemDataRepository;
import nts.uk.ctx.exio.dom.exo.categoryitemdata.CtgItemData;
import nts.arc.layer.infra.data.JpaRepository;

@Stateless
public class JpaCtgItemDataRepository extends JpaRepository implements CtgItemDataRepository
{

    private static final String SELECT_ALL_QUERY_STRING = "SELECT f FROM OiomtCtgItemData f";
    private static final String SELECT_BY_KEY_STRING = SELECT_ALL_QUERY_STRING + " WHERE ";

    @Override
    public List<CtgItemData> getAllCtgItemData(){
        return this.queryProxy().query(SELECT_ALL_QUERY_STRING, OiomtCtgItemData.class)
                .getList(item -> item.toDomain());
    }

    @Override
    public Optional<CtgItemData> getCtgItemDataById(){
        return this.queryProxy().query(SELECT_BY_KEY_STRING, OiomtCtgItemData.class)
        .getSingle(c->c.toDomain());
    }

    @Override
    public void add(CtgItemData domain){
        this.commandProxy().insert(OiomtCtgItemData.toEntity(domain));
    }

    @Override
    public void update(CtgItemData domain){
        this.commandProxy().update(OiomtCtgItemData.toEntity(domain));
    }

    @Override
    public void remove(){
        this.commandProxy().remove(OiomtCtgItemData.class, new OiomtCtgItemDataPk()); 
    }
}
