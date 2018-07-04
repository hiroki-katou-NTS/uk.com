package nts.uk.ctx.exio.infra.repository.exo.outcnddetail;

import javax.ejb.Stateless;
import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.exio.dom.exo.outcnddetail.SearchCodeList;
import nts.uk.ctx.exio.dom.exo.outcnddetail.SearchCodeListRepository;
import nts.uk.ctx.exio.infra.entity.exo.outcnddetail.OiomtSearchCodeList;
import nts.uk.ctx.exio.infra.entity.exo.outcnddetail.OiomtSearchCodeListPk;

@Stateless
public class JpaSearchCodeListRepository extends JpaRepository implements SearchCodeListRepository
{

    private static final String SELECT_ALL_QUERY_STRING = "SELECT f FROM OiomtSearchCodeList f";
    private static final String SELECT_BY_KEY_STRING = SELECT_ALL_QUERY_STRING + " WHERE  f.searchCodeListPk.id =:id";


    @Override
    public void add(SearchCodeList domain){
        this.commandProxy().insert(toEntity(domain));
    }

    @Override
    public void update(SearchCodeList domain){
        this.commandProxy().update(toEntity(domain));
    }

    @Override
    public void remove(String id){
        this.commandProxy().remove(OiomtSearchCodeList.class, new OiomtSearchCodeListPk(id)); 
    }
    
    public OiomtSearchCodeList toEntity(SearchCodeList domain){
    	return new OiomtSearchCodeList(domain.getId(), domain.getCategoryId(), domain.getCategoryItemNo().v(), domain.getSearchCode().v(), domain.getSearchItemName());
    }
    
    public SearchCodeList toDomain(OiomtSearchCodeList entity){
    	return new SearchCodeList(entity.searchCodeListPk.id, entity.categoryId, entity.categoryItemNo, entity.searchCode, entity.searchItemName);
    }
}
