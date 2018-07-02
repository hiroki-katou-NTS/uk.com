package nts.uk.ctx.exio.infra.repository.exo.datafomat;

import java.util.Optional;
import java.util.List;

import javax.ejb.Stateless;

import nts.uk.ctx.exio.infra.entity.exo.dataformat.OiomtChacDataFmSet;
import nts.uk.ctx.exio.infra.entity.exo.dataformat.OiomtChacDataFmSetPk;
import nts.uk.ctx.exio.dom.exo.datafomat.ChacDataFmSetRepository;
import nts.uk.ctx.exio.dom.exo.datafomat.ChacDataFmSet;
import nts.arc.layer.infra.data.JpaRepository;

@Stateless
public class JpaChacDataFmSetRepository extends JpaRepository implements ChacDataFmSetRepository
{

    private static final String SELECT_ALL_QUERY_STRING = "SELECT f FROM OiomtChacDataFmSet f";
    private static final String SELECT_BY_KEY_STRING = SELECT_ALL_QUERY_STRING + " WHERE ";

    @Override
    public List<ChacDataFmSet> getAllChacDataFmSet(){
        return this.queryProxy().query(SELECT_ALL_QUERY_STRING, OiomtChacDataFmSet.class)
                .getList(item -> item.toDomain());
    }

    @Override
    public Optional<ChacDataFmSet> getChacDataFmSetById(){
        return this.queryProxy().query(SELECT_BY_KEY_STRING, OiomtChacDataFmSet.class)
        .getSingle(c->c.toDomain());
    }

    @Override
    public void add(ChacDataFmSet domain){
        this.commandProxy().insert(OiomtChacDataFmSet.toEntity(domain));
    }

    @Override
    public void update(ChacDataFmSet domain){
        this.commandProxy().update(OiomtChacDataFmSet.toEntity(domain));
    }

    @Override
    public void remove(){
        this.commandProxy().remove(OiomtChacDataFmSet.class, new OiomtChacDataFmSetPk()); 
    }
}
