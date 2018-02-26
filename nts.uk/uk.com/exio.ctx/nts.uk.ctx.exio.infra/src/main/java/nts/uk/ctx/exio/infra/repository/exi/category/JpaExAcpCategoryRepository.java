package nts.uk.ctx.exio.infra.repository.exi.category;

import java.util.Optional;
import java.util.List;

import javax.ejb.Stateless;

import nts.uk.ctx.exio.infra.entity.exi.category.OiomtExAcpCategory;
import nts.uk.ctx.exio.infra.entity.exi.category.OiomtExAcpCategoryPk;
import nts.uk.ctx.exio.dom.exi.category.ExAcpCategoryRepository;
import nts.uk.ctx.exio.dom.exi.category.ExAcpCategory;
import nts.arc.layer.infra.data.JpaRepository;

@Stateless
public class JpaExAcpCategoryRepository extends JpaRepository implements ExAcpCategoryRepository
{

    private static final String SELECT_ALL_QUERY_STRING = "SELECT f FROM OiomtExAcpCategory f";
    private static final String SELECT_BY_KEY_STRING = SELECT_ALL_QUERY_STRING + " WHERE  f.exAcpCategoryPk.categoryId =:categoryId ";

    @Override
    public List<ExAcpCategory> getAllExAcpCategory(){
        return this.queryProxy().query(SELECT_ALL_QUERY_STRING, OiomtExAcpCategory.class)
                .getList(item -> toDomain(item));
    }

    @Override
    public Optional<ExAcpCategory> getExAcpCategoryById(String categoryId){
        return this.queryProxy().query(SELECT_BY_KEY_STRING, OiomtExAcpCategory.class)
        .setParameter("categoryId", categoryId)
        .getSingle(c->toDomain(c));
    }

    @Override
    public void add(ExAcpCategory domain){
        this.commandProxy().insert(toEntity(domain));
    }

    @Override
    public void update(ExAcpCategory domain){
        OiomtExAcpCategory newExAcpCategory = toEntity(domain);
        OiomtExAcpCategory updateExAcpCategory = this.queryProxy().find(newExAcpCategory.exAcpCategoryPk, OiomtExAcpCategory.class).get();
        if (null == updateExAcpCategory) {
            return;
        }
        updateExAcpCategory.version = newExAcpCategory.version;
        updateExAcpCategory.categoryName = newExAcpCategory.categoryName;
        this.commandProxy().update(updateExAcpCategory);
    }

    @Override
    public void remove(String categoryId){
        this.commandProxy().remove(OiomtExAcpCategoryPk.class, new OiomtExAcpCategoryPk(categoryId)); 
    }

    private static ExAcpCategory toDomain(OiomtExAcpCategory entity) {
        return ExAcpCategory.createFromJavaType(entity.version, entity.exAcpCategoryPk.categoryId, entity.categoryName);
    }

    private OiomtExAcpCategory toEntity(ExAcpCategory domain) {
        return new OiomtExAcpCategory(domain.getVersion(), new OiomtExAcpCategoryPk(domain.getCategoryId()), domain.getCategoryName());
    }

}
