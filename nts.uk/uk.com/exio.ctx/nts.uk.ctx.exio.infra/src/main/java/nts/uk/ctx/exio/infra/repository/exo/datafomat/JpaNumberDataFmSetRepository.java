package nts.uk.ctx.exio.infra.repository.exo.datafomat;

import java.util.Optional;
import java.util.List;

import javax.ejb.Stateless;

import nts.uk.ctx.exio.infra.entity.exo.dataformat.OiomtNumberDataFmSet;
import nts.uk.ctx.exio.infra.entity.exo.dataformat.OiomtNumberDataFmSetPk;
import nts.uk.ctx.exio.dom.exo.datafomat.NumberDataFmSetRepository;
import nts.uk.ctx.exio.dom.exo.datafomat.NumberDataFmSet;
import nts.arc.layer.infra.data.JpaRepository;

@Stateless
public class JpaNumberDataFmSetRepository extends JpaRepository implements NumberDataFmSetRepository
{

    private static final String SELECT_ALL_QUERY_STRING = "SELECT f FROM OiomtNumberDataFmSet f";
    private static final String SELECT_BY_KEY_STRING = SELECT_ALL_QUERY_STRING + " WHERE ";

    @Override
    public List<NumberDataFmSet> getAllNumberDataFmSet(){
        return this.queryProxy().query(SELECT_ALL_QUERY_STRING, OiomtNumberDataFmSet.class)
                .getList(item -> item.toDomain());
    }

    @Override
    public Optional<NumberDataFmSet> getNumberDataFmSetById(){
        return this.queryProxy().query(SELECT_BY_KEY_STRING, OiomtNumberDataFmSet.class)
        .getSingle(c->c.toDomain());
    }

    @Override
    public void add(NumberDataFmSet domain){
        this.commandProxy().insert(OiomtNumberDataFmSet.toEntity(domain));
    }

    @Override
    public void update(NumberDataFmSet domain){
        this.commandProxy().update(OiomtNumberDataFmSet.toEntity(domain));
    }

    @Override
    public void remove(){
        this.commandProxy().remove(OiomtNumberDataFmSet.class, new OiomtNumberDataFmSetPk()); 
    }
}
