package nts.uk.ctx.exio.infra.repository.input.transfer;

import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;

import nemunoki.oruta.shr.tabledefinetype.databasetype.DatabaseType;
import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.cnv.core.dom.conversiontable.ConversionCodeType;
import nts.uk.cnv.core.dom.conversiontable.ConversionInfo;
import nts.uk.cnv.core.dom.conversiontable.ConversionSource;
import nts.uk.cnv.core.dom.conversiontable.ConversionTable;
import nts.uk.cnv.core.dom.conversiontable.OneColumnConversion;
import nts.uk.cnv.core.infra.entity.conversiontable.ScvmtConversionSources;
import nts.uk.cnv.core.infra.entity.conversiontable.ScvmtConversionTable;
import nts.uk.cnv.core.infra.entity.tabledesign.ScvmtErpColumnDesign;
import nts.uk.ctx.exio.dom.input.transfer.ConversionTableRepository;

@Stateless
public class ConversionTableRepositoryImpl extends JpaRepository implements ConversionTableRepository {

	@Override
	public ConversionSource getSource(int groupId) {
		String query = "SELECT cs FROM ScvmtConversionSources cs WHERE cs.categoryName = :category";

		return this.queryProxy().query(query, ScvmtConversionSources.class)
			.setParameter("category", groupId)
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
	public List<ConversionTable> get(int groupId, ConversionSource source, ConversionCodeType cct) {
		String query =
				  "SELECT c FROM ScvmtConversionTable c"
				+ " WHERE c.pk.categoryName = :category";
		List<ScvmtConversionTable> entities = this.queryProxy().query(query, ScvmtConversionTable.class)
			.setParameter("category", groupId)
			.getList();
		
		DatabaseType  type = DatabaseType.parse(this.database());

		ConversionInfo info = new ConversionInfo(type, "", "", "" , "", "", "", "", cct);
		List<OneColumnConversion> columns = entities.stream()
				.map(entity -> entity.toDomain(info, info.getJoin(source)))
				.collect(Collectors.toList());
		
		return entities.stream()
				.map(entity -> entity.toDomain(info, columns, source))
				.collect(Collectors.toList());
	}

}
