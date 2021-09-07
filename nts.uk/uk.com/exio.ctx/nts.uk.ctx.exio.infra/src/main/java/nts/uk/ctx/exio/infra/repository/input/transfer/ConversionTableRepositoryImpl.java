package nts.uk.ctx.exio.infra.repository.input.transfer;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;

import lombok.val;
import nemunoki.oruta.shr.tabledefinetype.databasetype.DatabaseType;
import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.cnv.core.dom.conversiontable.ConversionCodeType;
import nts.uk.cnv.core.dom.conversiontable.ConversionInfo;
import nts.uk.cnv.core.dom.conversiontable.ConversionRecord;
import nts.uk.cnv.core.dom.conversiontable.ConversionSource;
import nts.uk.cnv.core.dom.conversiontable.ConversionTable;
import nts.uk.cnv.core.dom.conversiontable.OneColumnConversion;
import nts.uk.ctx.exio.dom.input.transfer.ConversionTableRepository;
import nts.uk.ctx.exio.infra.entity.input.transfer.conversion.ScvmtConversionRecord;
import nts.uk.ctx.exio.infra.entity.input.transfer.conversion.ScvmtConversionSources;
import nts.uk.ctx.exio.infra.entity.input.transfer.conversion.ScvmtConversionTable;

@Stateless
public class ConversionTableRepositoryImpl extends JpaRepository implements ConversionTableRepository {

	@Override
	public ConversionSource getSource(String domainName) {
		String query = "SELECT cs FROM ScvmtConversionSources cs WHERE cs.categoryName = :category";

		return this.queryProxy().query(query, ScvmtConversionSources.class)
			.setParameter("category", domainName)
			.getList(entity -> entity.toDomain(getPkColumns(entity.getSourceTableName())))
			.stream()
			.findFirst()
			.get();
	}
	
	@Override
	public List<ConversionRecord> getRecords(String category, String tableName) {
		String query =
				  "SELECT c FROM ScvmtConversionRecord c"
				+ " WHERE c.pk.categoryName = :category"
				+ " AND c.pk.targetTableName = :table";
		List<ScvmtConversionRecord> entity = this.queryProxy().query(query, ScvmtConversionRecord.class)
			.setParameter("category", category)
			.setParameter("table", tableName)
			.getList();

		return entity.stream()
			.map(e -> e.toDomain())
			.collect(Collectors.toList());
	}
	
	private List<String> getPkColumns(String tableName) {
//		String sql = "SELECT cd FROM ScvmtErpColumnDesign cd"
//				+ " WHERE cd.scvmtErpColumnDesignPk.tableName = :tableName"
//				+ " AND cd.pk = 1";
//		return this.queryProxy().query(sql, ScvmtErpColumnDesign.class)
//				.setParameter("tableName", tableName)
//				.getList(cd -> cd.getName());
		
		//TODO fix Update取り込みの際に使用する
		return new ArrayList<>();
	}

	@Override
	public List<ConversionTable> get(String domainName, ConversionSource source, ConversionCodeType cct) {
		String query =
				  "SELECT c FROM ScvmtConversionTable c"
				+ " WHERE c.pk.categoryName = :category";
		List<ScvmtConversionTable> converionTables = this.queryProxy().query(query, ScvmtConversionTable.class)
			.setParameter("category", domainName)
			.getList();
		
		DatabaseType type = DatabaseType.parse(this.database());
		
		List<String> domainTables = converionTables.stream()
				.map(e -> e.pk.getTargetTableName())
				.distinct()
				.collect(Collectors.toList());
		
		List<ConversionTable> results = new ArrayList<>();
		ConversionInfo info = new ConversionInfo(type, "", "", "" , "", "", "", "", cct);
		
		for (String targetTableName : domainTables) {
			val records = getRecords(domainName, targetTableName);
			for(ConversionRecord record : records) {
				List<OneColumnConversion> columns = converionTables.stream()
						.filter(ct -> ct.pk.getTargetTableName().equals(targetTableName) && ct.pk.getRecordNo() == record.getRecordNo())
						.map(ct -> ct.toDomain(info, info.getJoin(source)))
						.collect(Collectors.toList());

				results.add(ScvmtConversionTable.toDomain(targetTableName, info, columns, source, record));
			}
		}

		return results;
	}

}
