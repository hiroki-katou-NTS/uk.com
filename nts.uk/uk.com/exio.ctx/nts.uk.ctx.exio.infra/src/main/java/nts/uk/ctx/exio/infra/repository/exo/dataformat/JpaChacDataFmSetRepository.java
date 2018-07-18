package nts.uk.ctx.exio.infra.repository.exo.dataformat;

import java.util.Optional;
import java.util.List;

import javax.ejb.Stateless;

import lombok.val;
import nts.uk.ctx.exio.dom.exo.dataformat.ChacDataFmSet;
import nts.uk.ctx.exio.dom.exo.dataformat.ChacDataFmSetRepository;
import nts.uk.ctx.exio.infra.entity.exo.dataformat.OiomtChacDataFmSet;
import nts.uk.ctx.exio.infra.entity.exo.dataformat.OiomtChacDataFmSetPk;
import nts.uk.ctx.exio.infra.entity.exo.dataformat.OiomtInTimeDataFmSet;
import nts.uk.ctx.exio.infra.entity.exo.dataformat.OiomtInTimeDataFmSetPk;
import nts.arc.layer.infra.data.JpaRepository;

@Stateless
public class JpaChacDataFmSetRepository extends JpaRepository implements ChacDataFmSetRepository
{

    private static final String SELECT_ALL_QUERY_STRING = "SELECT f FROM OiomtChacDataFmSet f";
    private static final String SELECT_BY_KEY_STRING = SELECT_ALL_QUERY_STRING + " WHERE f.chacDataFmSetPk.cid =:cid";

    @Override
    public List<ChacDataFmSet> getAllChacDataFmSet(){
        return this.queryProxy().query(SELECT_ALL_QUERY_STRING, OiomtChacDataFmSet.class)
                .getList(item -> item.toDomain());
    }

    @Override
    public Optional<ChacDataFmSet> getChacDataFmSetById(String cid){
    	val entity = this.queryProxy().find(new OiomtChacDataFmSetPk(cid), OiomtChacDataFmSet.class);
		if (entity.isPresent()) {
			return Optional.of(entity.get().toDomain());
		}
		return Optional.empty();
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
