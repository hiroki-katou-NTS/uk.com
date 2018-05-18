package nts.uk.ctx.sys.assist.infra.repository.categoryfieldmt;

import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.sys.assist.dom.categoryfieldmt.CategoryFieldMt;
import nts.uk.ctx.sys.assist.dom.categoryfieldmt.CategoryFieldMtRepository;
import nts.uk.ctx.sys.assist.infra.entity.categoryfieldmt.SspmtCategoryFieldMt;

@Stateless
public class JpaCategoryFieldMtRepository extends JpaRepository implements CategoryFieldMtRepository
{

    private static final String SELECT_ALL_QUERY_STRING = "SELECT f FROM SspmtCategoryFieldMt f";
    private static final String SELECT_BY_KEY_STRING = SELECT_ALL_QUERY_STRING + " WHERE ";
    private static final String SELECT_BY_LIST_KEY_STRING = SELECT_ALL_QUERY_STRING + " WHERE  f.categoryPk.categoryId IN :lstCID ";
    

    
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
		return this.queryProxy().query(SELECT_BY_LIST_KEY_STRING, SspmtCategoryFieldMt.class)
				.setParameter("lstCID", categoryIds).setParameter("categoryIds", categoryIds)
		        .getList(c->c.toDomain());
	}
}
