package nts.uk.ctx.at.function.infra.repository.indexreconstruction;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.PreparedStatement;
import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.ejb.Stateless;
import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;

import nts.arc.layer.infra.data.JpaRepository;
import nts.arc.layer.infra.data.jdbc.NtsResultSet;
import nts.uk.ctx.at.function.dom.indexreconstruction.IndexReorgTable;
import nts.uk.ctx.at.function.dom.indexreconstruction.repository.CalculateFragRate;
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

	private static final String QUERY_SELECT_BY_IDS = QUERY_SELECT_ALL + " WHERE f.pk.categoryNo IN :categoryNos";

	private static final String QUERY_RECONFIG_INDEX = "ALTER INDEX ALL ON %s REORGANIZE;";

	private static final String QUERY_UPDATE_STATIS = "UPDATE STATISTICS %s;";

	/**
	 * Find one.
	 *
	 * @param categoryNo    the category no
	 * @param tablePhysName the table phys name
	 * @return the optional
	 */
	@Override
	public Optional<IndexReorgTable> findOne(BigDecimal categoryNo, String tablePhysName) {
		return this.queryProxy().query(QUERY_SELECT_BY_ID_AND_PHY_NAME, KfnctIndexReorgTable.class)
				.setParameter("categoryNo", categoryNo).setParameter("tablePhysName", tablePhysName)
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
	 * @param categoryNo    the category no
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
		return this.queryProxy().query(QUERY_SELECT_BY_IDS, KfnctIndexReorgTable.class)
				.setParameter("categoryNos", categoryIds).getList(IndexReorgTable::createFromMemento);
	}

	/**
	 * Calculate frag rate. インデックス再構成前の断片化率を計算する
	 *
	 * @param tablePhysName the table phys name
	 * @return the list
	 * @throws IOException
	 * @throws FileNotFoundException
	 */
	@Override
	public List<CalculateFragRate> calculateFragRate(String tablePhysName) {
		String databaseName = "UK4";
		String QUERY_CACULATE_FRAG_RATE_TEST = "SELECT a.object_id, object_name(a.object_id) AS TableName,"
				+ " a.index_id, name AS IndexName, avg_fragmentation_in_percent"
				+ " FROM sys.dm_db_index_physical_stats" + " (DB_ID (?)" + " , OBJECT_ID(?)" + " , NULL" + " , NULL"
				+ " , NULL) AS a" + " INNER JOIN sys.indexes AS b" + " ON a.object_id = b.object_id"
				+ " AND a.index_id = b.index_id;";
		try (PreparedStatement stmt = this.connection().prepareStatement(QUERY_CACULATE_FRAG_RATE_TEST)) {
			// Get current DB name via standalone.xml
			File file = new File(System.getProperty("jboss.server.config.dir") + "/standalone.xml");
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance(
					"com.sun.org.apache.xerces.internal.jaxp.DocumentBuilderFactoryImpl",
					ClassLoader.getSystemClassLoader());
			factory.setAttribute(XMLConstants.ACCESS_EXTERNAL_DTD, "");
			factory.setAttribute(XMLConstants.ACCESS_EXTERNAL_SCHEMA, "");
			Document prop = factory.newDocumentBuilder().parse(file);
			String connectionInfo = prop.getElementsByTagName("connection-url").item(1).getTextContent();
			Pattern p = Pattern.compile(".DatabaseName=(\\w+)$");
			Matcher m = p.matcher(connectionInfo);
			if (m.find()) {
				databaseName = m.group(1);
			}

			stmt.setString(1, databaseName);
			stmt.setString(2, tablePhysName);
			List<CalculateFragRate> res = new NtsResultSet(stmt.executeQuery()).getList(rs -> {
				return CalculateFragRate.builder().tablePhysicalName(rs.getString("TableName"))
						.fragmentationRate(
								rs.getBigDecimal("avg_fragmentation_in_percent").setScale(2, RoundingMode.HALF_EVEN))
						.indexId(rs.getInt("index_id")).indexName(rs.getString("IndexName")).build();
			});
			return res;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * Reconfigures index. インデックス再構成するsql文を実行する
	 * 
	 * @param tablePhysName the table phys name
	 */
	@Override
	public void reconfiguresIndex(String tablePhysName) {
		String query = String.format(QUERY_RECONFIG_INDEX, tablePhysName);
		this.getEntityManager().createNativeQuery(query).executeUpdate();
	}

	/**
	 * Update statis. 統計情報を更新する
	 * 
	 * @param tablePhysName the table phys name
	 */
	@Override
	public void updateStatis(String tablePhysName) {
		String query = String.format(QUERY_UPDATE_STATIS, tablePhysName);
		this.getEntityManager().createNativeQuery(query).executeUpdate();
	}
}
