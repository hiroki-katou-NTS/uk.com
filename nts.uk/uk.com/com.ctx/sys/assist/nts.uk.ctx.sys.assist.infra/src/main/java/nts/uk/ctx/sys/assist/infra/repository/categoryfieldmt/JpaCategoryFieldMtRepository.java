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
import nts.uk.ctx.sys.assist.infra.entity.categoryfieldmt.SspmtSaveCategoryField;

@Stateless
public class JpaCategoryFieldMtRepository extends JpaRepository implements CategoryFieldMtRepository
{

    private static final String SELECT_ALL_QUERY_STRING = "SELECT f FROM SspmtSaveCategoryField f";
//    private static final String SELECT_BY_KEY_STRING = SELECT_ALL_QUERY_STRING + " WHERE ";
    private static final String SELECT_BY_LIST_KEY_STRING = SELECT_ALL_QUERY_STRING + " WHERE  f.categoryFieldMtPk.categoryId IN :lstCategoryId ";
    private static final String SELECT_BY_CATEGORY_ID_AND_SYSTEM_TYPE = SELECT_ALL_QUERY_STRING +
    		" WHERE f.categoryFieldMtPk.categoryId = :categoryId"
    		+ " AND f.categoryFieldMtPk.systemType = :systemType";

    
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
		List<SspmtSaveCategoryField> entities = new ArrayList<>();
		CollectionUtil.split(categoryIds, DbConsts.MAX_CONDITIONS_OF_IN_STATEMENT, subList -> {
			entities.addAll(this.queryProxy().query(SELECT_BY_LIST_KEY_STRING, SspmtSaveCategoryField.class)
				.setParameter("lstCategoryId", subList)
		        .getList());
		});
		return entities.stream().map(c->c.toDomain()).collect(Collectors.toList());
	}
	
	@Override
	public List<CategoryFieldMt> findByCategoryIdAndSystemType(String categoryId, int systemType) {
		return this.queryProxy().query(SELECT_BY_CATEGORY_ID_AND_SYSTEM_TYPE, SspmtSaveCategoryField.class)
				.setParameter("categoryId", categoryId)
				.setParameter("systemType", systemType)
				.getList(SspmtSaveCategoryField::toDomain);
	}
}
