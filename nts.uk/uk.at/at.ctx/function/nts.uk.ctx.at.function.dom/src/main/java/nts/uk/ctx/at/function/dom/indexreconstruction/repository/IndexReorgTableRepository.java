package nts.uk.ctx.at.function.dom.indexreconstruction.repository;

import java.math.BigDecimal;
import java.util.Optional;

import nts.uk.ctx.at.function.dom.indexreconstruction.IndexReorgTable;

/**
 * The Interface IndexReorgTableRepository.
 * Repository インデックス再構成テーブル
 */
public interface IndexReorgTableRepository {
	
	/**
	 * Find one.
	 *
	 * @param categoryNo the category no
	 * @param tablePhysName the table phys name
	 * @return the optional
	 */
	Optional<IndexReorgTable> findOne(BigDecimal categoryNo, String tablePhysName);
	
	/**
	 * Adds the.
	 *
	 * @param domain the domain
	 */
	void add(IndexReorgTable domain);

	/**
	 * Delete.
	 *
	 * @param categoryNo the category no
	 * @param tablePhysName the table phys name
	 */
	void delete(BigDecimal categoryNo, String tablePhysName);
}
