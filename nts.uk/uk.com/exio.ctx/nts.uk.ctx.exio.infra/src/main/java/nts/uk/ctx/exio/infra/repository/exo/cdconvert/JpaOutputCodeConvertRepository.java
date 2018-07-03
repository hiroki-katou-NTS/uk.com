package nts.uk.ctx.exio.infra.repository.exo.cdconvert;

import java.util.Optional;
import java.util.List;

import javax.ejb.Stateless;

import nts.uk.ctx.exio.infra.entity.exo.cdconvert.OiomtOutputCodeConvert;
import nts.uk.ctx.exio.infra.entity.exo.cdconvert.OiomtOutputCodeConvertPk;
import nts.uk.ctx.exio.dom.exo.cdconvert.OutputCodeConvertRepository;
import nts.uk.ctx.exio.dom.exo.cdconvert.OutputCodeConvert;
import nts.arc.layer.infra.data.JpaRepository;

@Stateless
public class JpaOutputCodeConvertRepository extends JpaRepository implements OutputCodeConvertRepository
{

    private static final String SELECT_ALL_QUERY_STRING = "SELECT f FROM OiomtOutputCodeConvert f";
    private static final String SELECT_BY_KEY_STRING = SELECT_ALL_QUERY_STRING + " WHERE ";

    @Override
    public List<OutputCodeConvert> getAllOutputCodeConvert(){
        return this.queryProxy().query(SELECT_ALL_QUERY_STRING, OiomtOutputCodeConvert.class)
                .getList(item -> item.toDomain());
    }

    @Override
    public Optional<OutputCodeConvert> getOutputCodeConvertById(){
        return this.queryProxy().query(SELECT_BY_KEY_STRING, OiomtOutputCodeConvert.class)
        .getSingle(c->c.toDomain());
    }

    @Override
    public void add(OutputCodeConvert domain){
        this.commandProxy().insert(OiomtOutputCodeConvert.toEntity(domain));
    }

    @Override
    public void update(OutputCodeConvert domain){
        this.commandProxy().update(OiomtOutputCodeConvert.toEntity(domain));
    }

    @Override
    public void remove(){
        this.commandProxy().remove(OiomtOutputCodeConvert.class, new OiomtOutputCodeConvertPk()); 
    }
}
