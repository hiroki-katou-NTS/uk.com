package nts.uk.ctx.sys.assist.infra.repository.storage;

import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.sys.assist.dom.storage.TargetCategory;
import nts.uk.ctx.sys.assist.dom.storage.TargetCategoryRepository;
import nts.uk.ctx.sys.assist.infra.entity.storage.SspdtSaveTargetCtg;
import nts.uk.ctx.sys.assist.infra.entity.storage.SspdtSaveTargetCtgPk;

@Stateless
public class JpaTargetCategoryRepository extends JpaRepository implements TargetCategoryRepository {

	private static final String SELECT_ALL_QUERY_STRING = "SELECT f FROM SspdtSaveTargetCtg f";
	private static final String SELECT_BY_KEY_STRING = SELECT_ALL_QUERY_STRING
			+ " WHERE  f.targetCategoryPk.storeProcessingId =:storeProcessingId AND  f.targetCategoryPk.categoryId =:categoryId ";
	private static final String SELECT_BY_KEY_STRING_LIST = SELECT_ALL_QUERY_STRING
			+ " WHERE  f.targetCategoryPk.storeProcessingId =:storeProcessingId ";

	@Override
	public List<TargetCategory> getAllTargetCategory() {
		return this.queryProxy().query(SELECT_ALL_QUERY_STRING, SspdtSaveTargetCtg.class)
				.getList(item -> item.toDomain());
	}

	@Override
	public Optional<TargetCategory> getTargetCategoryById(String storeProcessingId, String categoryId) {
		return this.queryProxy().query(SELECT_BY_KEY_STRING, SspdtSaveTargetCtg.class)
				.setParameter("storeProcessingId", storeProcessingId).setParameter("categoryId", categoryId)
				.getSingle(c -> c.toDomain());
	}

	@Override
	public void add(List<TargetCategory> domain) {
		for (TargetCategory targetCategory : domain) {
			this.commandProxy().insert(SspdtSaveTargetCtg.toEntity(targetCategory));
		}
		
	}

	@Override
	public void update(TargetCategory domain) {
		this.commandProxy().update(SspdtSaveTargetCtg.toEntity(domain));
	}

	@Override
	public void remove(String storeProcessingId, String categoryId) {
		this.commandProxy().remove(SspdtSaveTargetCtg.class, new SspdtSaveTargetCtgPk(storeProcessingId, categoryId));
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.sys.assist.dom.storage.TargetCategoryRepository#getTargetCategoryListById(java.lang.String)
	 */
	@Override
	public List<TargetCategory> getTargetCategoryListById(String storeProcessingId) {
		return this.queryProxy().query(SELECT_BY_KEY_STRING_LIST, SspdtSaveTargetCtg.class)
				.setParameter("storeProcessingId", storeProcessingId).getList(c -> c.toDomain());
	}
}
