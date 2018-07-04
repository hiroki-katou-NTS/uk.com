package nts.uk.ctx.sys.assist.infra.repository.storage;

import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.sys.assist.dom.storage.TargetCategory;
import nts.uk.ctx.sys.assist.dom.storage.TargetCategoryRepository;
import nts.uk.ctx.sys.assist.infra.entity.storage.SspmtTargetCategory;
import nts.uk.ctx.sys.assist.infra.entity.storage.SspmtTargetCategoryPk;

@Stateless
public class JpaTargetCategoryRepository extends JpaRepository implements TargetCategoryRepository {

	private static final String SELECT_ALL_QUERY_STRING = "SELECT f FROM SspmtTargetCategory f";
	private static final String SELECT_BY_KEY_STRING = SELECT_ALL_QUERY_STRING
			+ " WHERE  f.targetCategoryPk.storeProcessingId =:storeProcessingId AND  f.targetCategoryPk.categoryId =:categoryId ";
	private static final String SELECT_BY_KEY_STRING_LIST = SELECT_ALL_QUERY_STRING
			+ " WHERE  f.targetCategoryPk.storeProcessingId =:storeProcessingId ";

	@Override
	public List<TargetCategory> getAllTargetCategory() {
		return this.queryProxy().query(SELECT_ALL_QUERY_STRING, SspmtTargetCategory.class)
				.getList(item -> item.toDomain());
	}

	@Override
	public Optional<TargetCategory> getTargetCategoryById(String storeProcessingId, String categoryId) {
		return this.queryProxy().query(SELECT_BY_KEY_STRING, SspmtTargetCategory.class)
				.setParameter("storeProcessingId", storeProcessingId).setParameter("categoryId", categoryId)
				.getSingle(c -> c.toDomain());
	}

	@Override
	public void add(List<TargetCategory> domain) {
		for (TargetCategory targetCategory : domain) {
			this.commandProxy().insert(SspmtTargetCategory.toEntity(targetCategory));
		}
		
	}

	@Override
	public void update(TargetCategory domain) {
		this.commandProxy().update(SspmtTargetCategory.toEntity(domain));
	}

	@Override
	public void remove(String storeProcessingId, String categoryId) {
		this.commandProxy().remove(SspmtTargetCategory.class, new SspmtTargetCategoryPk(storeProcessingId, categoryId));
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.sys.assist.dom.storage.TargetCategoryRepository#getTargetCategoryListById(java.lang.String)
	 */
	@Override
	public List<TargetCategory> getTargetCategoryListById(String storeProcessingId) {
		return this.queryProxy().query(SELECT_BY_KEY_STRING_LIST, SspmtTargetCategory.class)
				.setParameter("storeProcessingId", storeProcessingId).getList(c -> c.toDomain());
	}
}
