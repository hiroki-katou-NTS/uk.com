package nts.uk.ctx.sys.assist.infra.repository.deletedata;

import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.sys.assist.dom.deletedata.CategoryDeletion;
import nts.uk.ctx.sys.assist.dom.deletedata.CategoryDeletionRepository;
import nts.uk.ctx.sys.assist.infra.entity.deletedata.SspdtCategoryDeletion;
import nts.uk.ctx.sys.assist.infra.entity.deletedata.SspdtCategoryDeletionPK;

@Stateless
public class JpaCategoryDeletionRepository extends JpaRepository implements CategoryDeletionRepository {

	private static final String SELECT_ALL_QUERY_STRING = "SELECT f FROM SspdtCategoryDeletion f";
	private static final String SELECT_BY_KEY_STRING = SELECT_ALL_QUERY_STRING
			+ " WHERE  f.sspdtCategoryDeletionPK.delId =:delId AND  f.sspdtCategoryDeletionPK.categoryId =:categoryId ";
	private static final String SELECT_BY_KEY_STRING_LIST = SELECT_ALL_QUERY_STRING
			+ " WHERE  f.sspdtCategoryDeletionPK.delId =:delId";

	@Override
	public List<CategoryDeletion> getAllCategoryDeletion() {
		return this.queryProxy().query(SELECT_ALL_QUERY_STRING, SspdtCategoryDeletion.class)
				.getList(item -> item.toDomain());
	}

	@Override
	public Optional<CategoryDeletion> getCategoryDeletionById(String delId, String categoryId) {
		return this.queryProxy().query(SELECT_BY_KEY_STRING, SspdtCategoryDeletion.class)
				.setParameter("delId", delId).setParameter("categoryId", categoryId)
				.getSingle(c -> c.toDomain());
	}

	@Override
	public void add(CategoryDeletion domain) {
		this.commandProxy().insert(SspdtCategoryDeletion.toEntity(domain));
	}

	@Override
	public void update(CategoryDeletion domain) {
		this.commandProxy().update(SspdtCategoryDeletion.toEntity(domain));
	}

	@Override
	public void remove(String delId, String categoryId) {
		this.commandProxy().remove(SspdtCategoryDeletion.class,
				new SspdtCategoryDeletionPK(delId, categoryId));
	}

	@Override
	public void addAll(List<CategoryDeletion> categories) {
		for (CategoryDeletion categoryDeletion : categories) {
			this.commandProxy().insert(SspdtCategoryDeletion.toEntity(categoryDeletion));
		}
		this.getEntityManager().flush();
	}

	/*
	 * (non-Javadoc)
	 * 
	 */
	@Override
	public List<CategoryDeletion> getCategoryDeletionListById(String delId) {
		return this.queryProxy().query(SELECT_BY_KEY_STRING_LIST, SspdtCategoryDeletion.class)
				.setParameter("delId", delId).getList(c -> c.toDomain());
	}
}
