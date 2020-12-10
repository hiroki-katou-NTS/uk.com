package nts.uk.ctx.at.function.dom.indexreconstruction.repository;

import java.math.BigDecimal;
import java.util.List;
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
	void delete(int categoryNo, String tablePhysName);
	
	/**
	 * Find all by category ids.
	 *
	 * @param categoryIds the category ids
	 * @return the list
	 */
	List<IndexReorgTable> findAllByCategoryIds(List<BigDecimal> categoryIds);
	
	/**
	 * Calculate frag rate.
	 * インデックス再構成前の断片化率を計算する
	 *
	 * @param tablePhysName the table phys name
	 * @return the list
	 */
	List<CaculateFragRate> calculateFragRate(String tablePhysName);
	
	/**
	 * Reconfigures index.
	 *	インデックス再構成するsql文を実行する
	 * @param tablePhysName the table phys name
	 */
	void reconfiguresIndex(String tablePhysName);
	
	/**
	 * Update statis.
	 *
	 * @param tablePhysName the table phys name
	 */
	void updateStatis(String tablePhysName);
}
