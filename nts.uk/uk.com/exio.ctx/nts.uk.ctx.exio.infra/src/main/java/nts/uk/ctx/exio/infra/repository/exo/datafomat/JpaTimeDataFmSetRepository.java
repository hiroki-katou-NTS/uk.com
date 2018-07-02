package nts.uk.ctx.exio.infra.repository.exo.datafomat;

import java.util.Optional;
import java.util.List;

import javax.ejb.Stateless;

import nts.uk.ctx.exio.infra.entity.exo.dataformat.OiomtTimeDataFmSetO;
import nts.uk.ctx.exio.infra.entity.exo.dataformat.OiomtTimeDataFmSetPk;
import nts.uk.ctx.exio.dom.exo.datafomat.TimeDataFmSetRepository;
import nts.uk.ctx.exio.dom.exo.datafomat.TimeDataFmSet;
import nts.arc.layer.infra.data.JpaRepository;

@Stateless
public class JpaTimeDataFmSetRepository extends JpaRepository implements TimeDataFmSetRepository
{

    private static final String SELECT_ALL_QUERY_STRING = "SELECT f FROM OiomtTimeDataFmSet f";
    private static final String SELECT_BY_KEY_STRING = SELECT_ALL_QUERY_STRING + " WHERE ";

    @Override
    public List<TimeDataFmSet> getAllTimeDataFmSet(){
        return this.queryProxy().query(SELECT_ALL_QUERY_STRING, OiomtTimeDataFmSetO.class)
                .getList(item -> item.toDomain());
    }

    @Override
    public Optional<TimeDataFmSet> getTimeDataFmSetById(){
        return this.queryProxy().query(SELECT_BY_KEY_STRING, OiomtTimeDataFmSetO.class)
        .getSingle(c->c.toDomain());
    }

    @Override
    public void add(TimeDataFmSet domain){
        this.commandProxy().insert(OiomtTimeDataFmSetO.toEntity(domain));
    }

    @Override
    public void update(TimeDataFmSet domain){
        this.commandProxy().update(OiomtTimeDataFmSetO.toEntity(domain));
    }

    @Override
    public void remove(){
        this.commandProxy().remove(OiomtTimeDataFmSetO.class, new OiomtTimeDataFmSetPk()); 
    }
}
