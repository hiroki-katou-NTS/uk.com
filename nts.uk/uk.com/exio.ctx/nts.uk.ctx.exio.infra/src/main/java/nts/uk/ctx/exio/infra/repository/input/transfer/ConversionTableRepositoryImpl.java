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
import nts.uk.cnv.core.dom.conversiontable.ConversionSource;
import nts.uk.cnv.core.dom.conversiontable.ConversionTable;
import nts.uk.cnv.core.dom.conversiontable.OneColumnConversion;
import nts.uk.ctx.exio.dom.input.transfer.ConversionTableRepository;
import nts.uk.ctx.exio.infra.entity.input.transfer.conversion.ScvmtConversionSources;
import nts.uk.ctx.exio.infra.entity.input.transfer.conversion.ScvmtConversionTable;
import nts.uk.ctx.exio.infra.entity.input.transfer.source.ScvmtErpColumnDesign;

@Stateless
public class ConversionTableRepositoryImpl extends JpaRepository implements ConversionTableRepository {

	@Override
	public ConversionSource getSource(String groupName) {
		String query = "SELECT cs FROM ScvmtConversionSources cs WHERE cs.categoryName = :category";

		return this.queryProxy().query(query, ScvmtConversionSources.class)
			.setParameter("category", groupName)
			.getList(entity -> entity.toDomain(getPkColumns(entity.getSourceTableName())))
			.stream()
			.findFirst()
			.get();
	}
	
	private List<String> getPkColumns(String tableName) {
		String sql = "SELECT cd FROM ScvmtErpColumnDesign cd"
				+ " WHERE cd.scvmtErpColumnDesignPk.tableName = :tableName"
				+ " AND cd.pk = 1";
		return this.queryProxy().query(sql, ScvmtErpColumnDesign.class)
				.setParameter("tableName", tableName)
				.getList(cd -> cd.getName());
	}

	@Override
	public List<ConversionTable> get(String groupName, ConversionSource source, ConversionCodeType cct) {
		String query =
				  "SELECT c FROM ScvmtConversionTable c"
				+ " WHERE c.pk.categoryName = :category";
		List<ScvmtConversionTable> entities = this.queryProxy().query(query, ScvmtConversionTable.class)
			.setParameter("category", groupName)
			.getList();
		
		DatabaseType type = DatabaseType.parse(this.database());
		
		val entitiesByTable = entities.stream()
				.collect(Collectors.groupingBy(e -> e.pk.getTargetTableName()));
		
		List<ConversionTable> results = new ArrayList<>();
		
		for (val entry : entitiesByTable.entrySet()) {
			
			String targetTableName = entry.getKey();
			val entitiesOfTable = entry.getValue();
			
			ConversionInfo info = new ConversionInfo(type, "", "", "" , "", "", "", "", cct);
			List<OneColumnConversion> columns = entitiesOfTable.stream()
					.map(entity -> entity.toDomain(info, info.getJoin(source)))
					.collect(Collectors.toList());
			
			results.add(ScvmtConversionTable.toDomain(targetTableName, info, columns, source));
		}

		return results;
	}

}
