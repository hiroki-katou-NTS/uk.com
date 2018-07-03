package nts.uk.ctx.exio.infra.repository.exo.datafomat;

import java.util.Optional;
import java.util.List;

import javax.ejb.Stateless;

import nts.uk.ctx.exio.infra.entity.exo.dataformat.OiomtAwDataFormatSet;
import nts.uk.ctx.exio.infra.entity.exo.dataformat.OiomtAwDataFormatSetPk;
import nts.uk.ctx.exio.dom.exo.datafomat.AwDataFormatSetRepository;
import nts.uk.ctx.exio.dom.exo.datafomat.AwDataFormatSet;
import nts.arc.layer.infra.data.JpaRepository;

@Stateless
public class JpaAwDataFormatSetRepository extends JpaRepository implements AwDataFormatSetRepository
{

    private static final String SELECT_ALL_QUERY_STRING = "SELECT f FROM OiomtAwDataFormatSet f";
    private static final String SELECT_BY_KEY_STRING = SELECT_ALL_QUERY_STRING + " WHERE ";

    @Override
    public List<AwDataFormatSet> getAllAwDataFormatSet(){
        return this.queryProxy().query(SELECT_ALL_QUERY_STRING, OiomtAwDataFormatSet.class)
                .getList(item -> item.toDomain());
    }

    @Override
    public Optional<AwDataFormatSet> getAwDataFormatSetById(){
        return this.queryProxy().query(SELECT_BY_KEY_STRING, OiomtAwDataFormatSet.class)
        .getSingle(c->c.toDomain());
    }

    @Override
    public void add(AwDataFormatSet domain){
        this.commandProxy().insert(OiomtAwDataFormatSet.toEntity(domain));
    }

    @Override
    public void update(AwDataFormatSet domain){
        this.commandProxy().update(OiomtAwDataFormatSet.toEntity(domain));
    }

    @Override
    public void remove(){
        this.commandProxy().remove(OiomtAwDataFormatSet.class, new OiomtAwDataFormatSetPk()); 
    }
}
