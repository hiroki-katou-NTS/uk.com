package nts.uk.ctx.exio.infra.repository.exo.category;

import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.exio.dom.exo.category.ExOutCtg;
import nts.uk.ctx.exio.dom.exo.category.ExOutCtgRepository;
import nts.uk.ctx.exio.infra.entity.exo.category.OiomtExOutCtg;
import nts.uk.ctx.exio.infra.entity.exo.category.OiomtExOutCtgPk;

@Stateless
public class JpaExOutCtgRepository extends JpaRepository implements ExOutCtgRepository
{

    private static final String SELECT_ALL_QUERY_STRING = "SELECT f FROM OiomtExOutCtg f";
    private static final String SELECT_BY_KEY_STRING = SELECT_ALL_QUERY_STRING + " WHERE  f.exOutCtgPk.categoryId =:categoryId ";

    @Override
    public List<ExOutCtg> getAllExOutCtg(){
        return this.queryProxy().query(SELECT_ALL_QUERY_STRING, OiomtExOutCtg.class)
                .getList(item -> item.toDomain());
    }

    @Override
    public Optional<ExOutCtg> getExOutCtgById(String categoryId){
        return this.queryProxy().query(SELECT_BY_KEY_STRING, OiomtExOutCtg.class)
        .setParameter("categoryId", categoryId)
        .getSingle(c->c.toDomain());
    }

    @Override
    public void add(ExOutCtg domain){
        this.commandProxy().insert(OiomtExOutCtg.toEntity(domain));
    }

    @Override
    public void update(ExOutCtg domain){
        this.commandProxy().update(OiomtExOutCtg.toEntity(domain));
    }

    @Override
    public void remove(String categoryId){
        this.commandProxy().remove(OiomtExOutCtg.class, new OiomtExOutCtgPk(categoryId)); 
    }
}
