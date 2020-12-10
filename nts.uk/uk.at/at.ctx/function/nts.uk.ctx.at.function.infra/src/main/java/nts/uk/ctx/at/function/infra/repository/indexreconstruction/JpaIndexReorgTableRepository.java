package nts.uk.ctx.at.function.infra.repository.indexreconstruction;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.function.dom.indexreconstruction.IndexReorgTable;
import nts.uk.ctx.at.function.dom.indexreconstruction.repository.CaculateFragRate;
import nts.uk.ctx.at.function.dom.indexreconstruction.repository.IndexReorgTableRepository;
import nts.uk.ctx.at.function.infra.entity.indexreconstruction.KfnctIndexReorgTable;
import nts.uk.ctx.at.function.infra.entity.indexreconstruction.KfnctIndexReorgTablePk;

/**
 * The Class JpaIndexReorgTableRepository.
 */
@Stateless
public class JpaIndexReorgTableRepository extends JpaRepository implements IndexReorgTableRepository {

	// Select all
	private static final String QUERY_SELECT_ALL = "SELECT f FROM KfnctIndexReorgTable f";
	
	// Select one
	private static final String QUERY_SELECT_BY_ID_AND_PHY_NAME = QUERY_SELECT_ALL
			+ " WHERE f.pk.categoryNo = :categoryNo AND f.pk.tablePhysName = :tablePhysName";
	
	private static final String QUERY_SELECT_BY_IDS = QUERY_SELECT_ALL
			+ " WHERE f.pk.categoryNo IN (:categoryNos)";
	
	private static final String QUERY_CACULATE_FRAG_RATE = "SELECT a.object_id, object_name(a.object_id) AS TableName," + 
			" a.index_id, name AS IndedxName, avg_fragmentation_in_percent" + 
			" FROM sys.dm_db_index_physical_stats" + 
			" (DB_ID ('UK4')" + 
			" , OBJECT_ID(:tablePhysName)" + 
			" , NULL" + 
			" , NULL" + 
			" , NULL) AS a" + 
			" INNER JOIN sys.indexes AS b" + 
			" ON a.object_id = b.object_id" + 
			" AND a.index_id = b.index_id;";
	
	private static final String QUERY_RECONFIG_INDEX = "ALTER INDEX ALL ON :tablePhysName REORGANIZE;";
	
	private static final String QUERY_UPDATE_STATIS = "UPDATE STATISTICS :tablePhysName;";
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
			.query(QUERY_SELECT_BY_ID_AND_PHY_NAME, KfnctIndexReorgTable.class)
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
	 *  @param categoryNo the category no
	 * @param tablePhysName the table phys name
	 */
	@Override
	public void delete(int categoryNo, String tablePhysName) {
		KfnctIndexReorgTablePk key = new KfnctIndexReorgTablePk(categoryNo, tablePhysName);
		this.commandProxy().remove(KfnctIndexReorgTable.class, key);
	}

	private static KfnctIndexReorgTable toEntity(IndexReorgTable domain) {
		KfnctIndexReorgTable entity = new KfnctIndexReorgTable();
		domain.setMemento(entity);
		return entity;
	}

	/**
	 * Find all by category ids.
	 *
	 * @param categoryIds the category ids
	 * @return the list
	 */
	@Override
	public List<IndexReorgTable> findAllByCategoryIds(List<BigDecimal> categoryIds) {
		return this.queryProxy()
			.query(QUERY_SELECT_BY_IDS, KfnctIndexReorgTable.class)
			.setParameter("categoryNos", categoryIds)
			.getList(IndexReorgTable::createFromMemento);
	}

	/**
	 * Calculate frag rate.
	 * インデックス再構成前の断片化率を計算する
	 *
	 * @param tablePhysName the table phys name
	 * @return the list
	 */
	@Override
	public List<CaculateFragRate> calculateFragRate(String tablePhysName) {
		return this.queryProxy()
				.query(QUERY_CACULATE_FRAG_RATE)
				.setParameter("tablePhysName", tablePhysName)
				.getList( c -> {
					BigDecimal fragRate = ((BigDecimal) c[4]).setScale(2);
					return CaculateFragRate.builder()
							.tablePhysicalName((String) c[1])
							.indexId((int) c[2])
							.indexName((String) c[3])
							.fragmentationRate(fragRate)
							.build();
				});
	}

	/**
	 * Reconfigures index.
	 * インデックス再構成するsql文を実行する
	 * @param tablePhysName the table phys name
	 */
	@Override
	public void reconfiguresIndex(String tablePhysName) {
		this.queryProxy()
				.query("QUERY_RECONFIG_INDEX")
				.setParameter("tablePhysName", tablePhysName)
				.getQuery();
	}

	/**
	 * Update statis.
	 * 統計情報を更新する
	 * @param tablePhysName the table phys name
	 */
	@Override
	public void updateStatis(String tablePhysName) {
		this.queryProxy()
		.query("QUERY_UPDATE_STATIS")
		.setParameter("tablePhysName", tablePhysName)
		.getQuery();
	}
}
