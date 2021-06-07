package nts.uk.cnv.infra.repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.cnv.core.dom.tabledesign.ColumnDesign;
import nts.uk.cnv.core.dom.tabledesign.ErpTableDesign;
import nts.uk.cnv.core.infra.entity.conversiontable.ScvmtErpColumnDesign;
import nts.uk.cnv.core.infra.entity.conversiontable.ScvmtErpColumnDesignPk;
import nts.uk.cnv.core.infra.entity.tabledesign.ScvmtErpTableDesign;
import nts.uk.cnv.dom.tabledesign.ErpTableDesignRepository;

@Stateless
public class JpaErpTableDesignRepository extends JpaRepository implements ErpTableDesignRepository {

	@Override
	public void insert(ErpTableDesign tableDesign) {
		this.commandProxy().insert(toEntity(tableDesign));
	}

	@Override
	public void update(ErpTableDesign tableDesign) {
		this.commandProxy().update(toEntity(tableDesign));
	}

	@Override
	public boolean exists(String tableName) {
		String sql = "SELECT td FROM ScvmtErpTableDesign td WHERE td.name = :name";
		Optional<ScvmtErpTableDesign> result = this.queryProxy().query(sql, ScvmtErpTableDesign.class)
				.setParameter("name", tableName)
				.getSingle();
		return result.isPresent();
	}

	private ScvmtErpTableDesign toEntity(ErpTableDesign tableDesign) {
		List<ScvmtErpColumnDesign> columns = tableDesign.getColumns().stream()
				.map(cd -> toEntity(tableDesign.getName(), cd))
				.collect(Collectors.toList());

		return new ScvmtErpTableDesign(tableDesign.getName(), columns);
	}

	private ScvmtErpColumnDesign toEntity(String tableName, ColumnDesign columnDesign) {
		return new ScvmtErpColumnDesign(
					new ScvmtErpColumnDesignPk(tableName, columnDesign.getId()),
					columnDesign.getName(),
					columnDesign.getType(),
					(columnDesign.isNullable() ? 1 : 0),
					columnDesign.getDefaultValue(),
					columnDesign.getComment(),
					columnDesign.getDispOrder(),
					(columnDesign.isPk() ? 1 : 0),
					null
				);
	}

	@Override
	public Optional<ErpTableDesign> find(String tablename) {
		String sql = "SELECT td FROM ScvmtErpTableDesign td WHERE td.name = :name";
		Optional<ScvmtErpTableDesign> parent = this.queryProxy().query(sql, ScvmtErpTableDesign.class)
				.setParameter("name", tablename)
				.getSingle();
		if(!parent.isPresent()) return Optional.empty();

		Optional<ScvmtErpTableDesign> result = this.queryProxy().find(parent.get().getName(), ScvmtErpTableDesign.class);
		if(!parent.isPresent()) return Optional.empty();

		return Optional.of(result.get().toDomain());
	}

	@Override
	public List<String> getAllTableList() {
		String sql = "SELECT td.NAME FROM SCVMT_ERP_TABLE_DESIGN td ORDER BY NAME ASC";
		List<String> tablelist = new ArrayList<>();
		tablelist = this.jdbcProxy().query(sql).getList(rec -> {
			return rec.getString("NAME");
		});

		return tablelist;
	}

	@Override
	public List<String> getPkColumns(String tableName) {
		String sql = "SELECT cd FROM ScvmtErpColumnDesign cd"
				+ " WHERE cd.scvmtErpColumnDesignPk.tableName = :tableName"
				+ " AND cd.pk = 1";
		return this.queryProxy().query(sql, ScvmtErpColumnDesign.class)
				.setParameter("tableName", tableName)
				.getList(cd -> cd.getName());
	}
}
