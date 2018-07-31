package nts.uk.ctx.exio.infra.repository.exo.category;

import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.exio.dom.exo.category.ExOutLinkTable;
import nts.uk.ctx.exio.dom.exo.category.ExCndOutputRepository;
import nts.uk.ctx.exio.infra.entity.exo.category.OiomtExCndOutput;
import nts.uk.ctx.exio.infra.entity.exo.category.OiomtExCndOutputPk;

@Stateless
public class JpaExCndOutputRepository extends JpaRepository implements ExCndOutputRepository
{

    private static final String SELECT_ALL_QUERY_STRING = "SELECT f FROM OiomtExCndOutput f";
    private static final String SELECT_BY_KEY_STRING = SELECT_ALL_QUERY_STRING + " WHERE  f.exCndOutputPk.categoryId =:categoryId ";

    @Override
    public List<ExOutLinkTable> getAllExCndOutput(){
        return this.queryProxy().query(SELECT_ALL_QUERY_STRING, OiomtExCndOutput.class)
                .getList(item -> item.toDomain());
    }

    @Override
    public Optional<ExOutLinkTable> getExCndOutputById(Integer categoryId){
        return this.queryProxy().query(SELECT_BY_KEY_STRING, OiomtExCndOutput.class)
        .setParameter("categoryId", categoryId)
        .getSingle(c->c.toDomain());
    }

    @Override
    public void add(ExOutLinkTable domain){
        this.commandProxy().insert(OiomtExCndOutput.toEntity(domain));
    }

    @Override
    public void update(ExOutLinkTable domain){
        this.commandProxy().update(OiomtExCndOutput.toEntity(domain));
    }

    @Override
    public void remove(int categoryId){
        this.commandProxy().remove(OiomtExCndOutput.class, new OiomtExCndOutputPk(categoryId)); 
    }
}
