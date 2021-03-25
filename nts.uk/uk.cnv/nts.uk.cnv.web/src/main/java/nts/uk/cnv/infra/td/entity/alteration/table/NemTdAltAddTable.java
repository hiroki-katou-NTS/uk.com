package nts.uk.cnv.infra.td.entity.alteration.table;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.uk.cnv.dom.td.alteration.content.AddTable;
import nts.uk.cnv.dom.td.schema.tabledesign.TableDesign;
import nts.uk.cnv.dom.td.schema.tabledesign.TableName;
import nts.uk.cnv.dom.td.schema.tabledesign.column.ColumnDesign;
import nts.uk.cnv.dom.td.schema.tabledesign.constraint.TableConstraints;
import nts.uk.cnv.infra.td.entity.alteration.NemTdAltContentBase;
import nts.uk.cnv.infra.td.entity.alteration.NemTdAltContentPk;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "NEM_TD_ALT_ADD_TABLE")
public class NemTdAltAddTable extends NemTdAltContentBase implements Serializable {

	/** serialVersionUID */
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	public NemTdAltContentPk pk;

	@Column(name = "NAME")
	public String name;

	@Column(name = "JPNAME")
	public String jpName;

	public String getTableId() {
		return this.name;
	}

	public AddTable toDomain(List<NemTdAltAddTableColumn> columns, List<NemTdAltAddTableIndex> indexes, List<NemTdAltAddTableIndexColumns> indexColumns) {
		List<ColumnDesign> cols = columns.stream()
				.map(col -> col.toDomain())
				.collect(Collectors.toList());

		Map<String, List<String>> indexColumnsMap = new HashMap<>();
		indexes.stream()
				.forEach(idx -> {
					List<String> ics = indexColumns.stream()
							.filter(ic -> idx.pk.suffix.equals(ic.pk.suffix))
							.map(ic -> ic.columnId)
							.collect(Collectors.toList());
					indexColumnsMap.put(idx.pk.suffix, ics);
				});

		TableConstraints tableConstraints = NemTdAltAddTableIndex.toDomain(indexes, indexColumnsMap);

		return new AddTable(new TableDesign(getTableId(), new TableName(name), jpName, cols, tableConstraints));
	}

	public static NemTdAltContentBase toEntity(NemTdAltContentPk contentPk, AddTable domain) {
		TableDesign td = domain.getTableDesign();
		return new NemTdAltAddTable(
				contentPk,
				td.getName().v(),
				td.getJpName()
			);
	}

	@Override
	protected Object getKey() {
		return pk;
	}
}
