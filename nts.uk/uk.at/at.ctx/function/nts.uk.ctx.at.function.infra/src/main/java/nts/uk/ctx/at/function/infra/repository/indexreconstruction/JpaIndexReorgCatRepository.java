package nts.uk.ctx.at.function.infra.repository.indexreconstruction;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.function.dom.indexreconstruction.IndexReorgCat;
import nts.uk.ctx.at.function.dom.indexreconstruction.repository.IndexReorgCatRepository;
import nts.uk.ctx.at.function.infra.entity.indexreconstruction.KfnctIndexReorgCat;
import nts.uk.shr.com.context.AppContexts;

import javax.ejb.Stateless;
import java.util.List;
import java.util.Optional;

/**
 * The Class JpaIndexReorgCatRepository.
 */
@Stateless
public class JpaIndexReorgCatRepository extends JpaRepository implements IndexReorgCatRepository {

	/**
	 * The Constant QUERY_SELECT_ALL.
	 */
	// Select all
	private static final String QUERY_SELECT_ALL = "SELECT f FROM KfnctIndexReorgCat f";

	/**
	 * The Constant QUERY_SELECT_BY_ID.
	 */
	// Select one
	private static final String QUERY_SELECT_BY_ID = QUERY_SELECT_ALL
			+ " WHERE f.pk.categoryNo = :categoryNo";

	/**
	 * Finds all.
	 *
	 * @return the <code>IndexReorgCat</code> domain list
	 */
	@Override
	public List<IndexReorgCat> findAll() {
		return this.queryProxy()
				   .query(QUERY_SELECT_ALL, KfnctIndexReorgCat.class)
				   .getList(IndexReorgCat::createFromMemento);
	}

	/**
	 * Find one.
	 *
	 * @param categoryNo the category no
	 * @return the optional
	 */
	@Override
	public Optional<IndexReorgCat> findOne(int categoryNo) {
		return this.queryProxy()
				   .query(QUERY_SELECT_BY_ID, KfnctIndexReorgCat.class)
				   .setParameter("categoryNo", categoryNo)
				   .getSingle(IndexReorgCat::createFromMemento);
	}

	/**
	 * Adds the.
	 *
	 * @param domain the domain
	 */
	@Override
	public void add(IndexReorgCat domain) {
		// Convert data to entity
		KfnctIndexReorgCat entity = this.toEntity(domain);
		// Insert entity
		this.commandProxy().insert(entity);
	}

	/**
	 * Delete.
	 *
	 * @param categoryNo the category no
	 */
	@Override
	public void delete(int categoryNo) {
		this.commandProxy().remove(KfnctIndexReorgCat.class, categoryNo);
	}

	/**
	 * To entity.
	 *
	 * @param domain the domain
	 * @return the entity インデックス再構成カテゴリ
	 */
	private KfnctIndexReorgCat toEntity(IndexReorgCat domain) {
		String companyId = AppContexts.user().companyId();
		String contractCode = AppContexts.user().contractCode();
		return new KfnctIndexReorgCat(companyId, contractCode, domain);
	}

}
