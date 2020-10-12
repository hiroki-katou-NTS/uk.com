package nts.uk.ctx.at.function.infra.repository.indexreconstruction;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.function.dom.indexreconstruction.IndexReorgCat;
import nts.uk.ctx.at.function.dom.indexreconstruction.repository.IndexReorgCatRepository;
import nts.uk.ctx.at.function.infra.entity.indexreconstruction.KfnctIndexReorgCat;
import nts.uk.ctx.at.function.infra.entity.indexreconstruction.KfnctIndexReorgCatPk;

/**
 * The Class JpaIndexReorgCatRepository.
 */
@Stateless
public class JpaIndexReorgCatRepository extends JpaRepository implements IndexReorgCatRepository {
	
	/** The Constant QUERY_SELECT_ALL. */
	// Select all
	private static final String QUERY_SELECT_ALL = "SELECT f FROM KfnctIndexReorgCat f";
	
	/** The Constant QUERY_SELECT_BY_ID. */
	// Select one
	private static final String QUERY_SELECT_BY_ID = QUERY_SELECT_ALL
			+ " WHERE f.pk.categoryNo = :categoryNo";

	/**
	 * Finds all.
	 * 
	 * @return the <code>IndexReorgCat</code> domain list
	 */
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
	public Optional<IndexReorgCat> findOne(BigDecimal categoryNo) {
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
		KfnctIndexReorgCat entity = JpaIndexReorgCatRepository.toEntity(domain);
		// Insert entity
		this.commandProxy().insert(entity);
	}

	/**
	 * Delete.
	 *
	 * @param categoryNo the category no
	 */
	@Override
	public void delete(BigDecimal categoryNo) {
		KfnctIndexReorgCatPk key = new KfnctIndexReorgCatPk(categoryNo);
		this.commandProxy().remove(KfnctIndexReorgCat.class, key);
	}

	/**
	 * To entity.
	 *
	 * @param domain the domain
	 * @return the kfnct index reorg cat
	 */
	private static KfnctIndexReorgCat toEntity(IndexReorgCat domain) {
		KfnctIndexReorgCat entity = new KfnctIndexReorgCat();
		domain.setMemento(entity);
		return entity;
	}
}
