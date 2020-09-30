package nts.uk.ctx.at.function.infra.repository.indexreconstruction;

import java.math.BigDecimal;
import java.util.Optional;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.function.dom.indexreconstruction.IndexReorgTable;
import nts.uk.ctx.at.function.dom.indexreconstruction.repository.IndexReorgTableRepository;
import nts.uk.ctx.at.function.infra.entity.indexreconstruction.KfnctIndexReorgTablePk;
import nts.uk.ctx.at.function.infra.entity.indexreconstruction.KfnctIndexReorgTable;

/**
 * The Class JpaIndexReorgTableRepository.
 */
@Stateless
public class JpaIndexReorgTableRepository extends JpaRepository implements IndexReorgTableRepository {

	// Select all
	private static final String QUERY_SELECT_ALL = "SELECT f FROM KfnctIndexReorgTable f";
	
	// Select one
	private static final String QUERY_SELECT_BY_ID = QUERY_SELECT_ALL
			+ " WHERE f.pk.categoryNo = :categoryNo AND f.pk.tablePhysName = :tablePhysName";
		
	/**
	 * Find one.
	 *
	 * @param categoryNo the category no
	 * @param tablePhysName the table phys name
	 * @return the optional
	 */
	@Override
	public Optional<IndexReorgTable> findOne(BigDecimal categoryNo, String tablePhysName) {
		return this.queryProxy()
			.query(QUERY_SELECT_BY_ID, KfnctIndexReorgTable.class)
			.setParameter("categoryNo", categoryNo)
			.setParameter("tablePhysName", tablePhysName)
			.getSingle(IndexReorgTable::createFromMemento);
	}

	/**
	 * Adds the.
	 *
	 * @param domain the domain
	 */
	@Override
	public void add(IndexReorgTable domain) {
		// Convert data to entity
		KfnctIndexReorgTable entity = JpaIndexReorgTableRepository.toEntity(domain);
		// Insert entity
		this.commandProxy().insert(entity);
	}

	/**
	 * Delete.
	 *
	 * @param categoryNo the category no
	 * @param tablePhysName the table phys name
	 */
	@Override
	public void delete(BigDecimal categoryNo, String tablePhysName) {
		KfnctIndexReorgTablePk key = new KfnctIndexReorgTablePk(categoryNo, tablePhysName);
		this.commandProxy().remove(KfnctIndexReorgTable.class, key);
	}

	private static KfnctIndexReorgTable toEntity(IndexReorgTable domain) {
		KfnctIndexReorgTable entity = new KfnctIndexReorgTable();
		domain.setMemento(entity);
		return entity;
	}
}
