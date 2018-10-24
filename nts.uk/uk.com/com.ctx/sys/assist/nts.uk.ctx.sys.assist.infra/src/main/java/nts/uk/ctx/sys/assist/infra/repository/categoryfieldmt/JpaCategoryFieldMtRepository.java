package nts.uk.ctx.sys.assist.infra.repository.categoryfieldmt;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.DbConsts;
import nts.arc.layer.infra.data.JpaRepository;
import nts.gul.collection.CollectionUtil;
import nts.uk.ctx.sys.assist.dom.categoryfieldmt.CategoryFieldMt;
import nts.uk.ctx.sys.assist.dom.categoryfieldmt.CategoryFieldMtRepository;
import nts.uk.ctx.sys.assist.infra.entity.categoryfieldmt.SspmtCategoryFieldMt;

@Stateless
public class JpaCategoryFieldMtRepository extends JpaRepository implements CategoryFieldMtRepository
{

    private static final String SELECT_ALL_QUERY_STRING = "SELECT f FROM SspmtCategoryFieldMt f";
    private static final String SELECT_BY_KEY_STRING = SELECT_ALL_QUERY_STRING + " WHERE ";
    private static final String SELECT_BY_LIST_KEY_STRING = SELECT_ALL_QUERY_STRING + " WHERE  f.categoryFieldMtPk.categoryId IN :lstCategoryId ";
    

    
    @Override
    public void remove(){
    }




	@Override
	public List<CategoryFieldMt> getAllCategoryFieldMt() {
		return null;
	}




	@Override
	public Optional<CategoryFieldMt> getCategoryFieldMtById() {
		return null;
	}




	@Override
	public void add(CategoryFieldMt domain) {
	}




	@Override
	public void update(CategoryFieldMt domain) {
		
	}




	/* (non-Javadoc)
	 * @see nts.uk.ctx.sys.assist.dom.categoryFieldMaster.CategoryFieldMtRepository#getCategoryFieldMtByListId(java.util.List)
	 */
	@Override
	public List<CategoryFieldMt> getCategoryFieldMtByListId(List<String> categoryIds) {
		List<SspmtCategoryFieldMt> entities = new ArrayList<>();
		CollectionUtil.split(categoryIds, DbConsts.MAX_CONDITIONS_OF_IN_STATEMENT, subList -> {
			entities.addAll(this.queryProxy().query(SELECT_BY_LIST_KEY_STRING, SspmtCategoryFieldMt.class)
				.setParameter("lstCategoryId", subList)
		        .getList());
		});
		return entities.stream().map(c->c.toDomain()).collect(Collectors.toList());
	}
}
