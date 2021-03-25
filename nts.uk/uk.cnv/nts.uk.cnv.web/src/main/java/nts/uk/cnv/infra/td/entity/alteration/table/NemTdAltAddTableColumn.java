package nts.uk.cnv.infra.td.entity.alteration.table;

import static java.util.stream.Collectors.*;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.val;
import nts.arc.layer.infra.data.entity.JpaEntity;
import nts.uk.cnv.dom.td.alteration.content.AddTable;
import nts.uk.cnv.dom.td.schema.tabledesign.column.ColumnDesign;
import nts.uk.cnv.dom.td.schema.tabledesign.column.DataType;
import nts.uk.cnv.dom.td.schema.tabledesign.column.DefineColumnType;
import nts.uk.cnv.infra.td.entity.alteration.NemTdAltContentPk;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "NEM_TD_ALT_ADD_TABLE_COLUMN")
public class NemTdAltAddTableColumn extends JpaEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	@EmbeddedId
	public NemTdAltAddTableColumnPk pk;

	@Column(name = "NAME")
	public String name;

	@Column(name = "JPNAME")
	public String jpName;

	@Column(name = "DATA_TYPE")
	public String dataType;

	@Column(name = "MAX_LENGTH")
	public int maxLength;

	@Column(name = "SCALE")
	public int scale;

	@Column(name = "NULLABLE")
	public int nullable;

	@Column(name = "DEFAULT_VALUE")
	public String defaultValue;

	@Column(name = "COMMENT")
	public String comment;

	@Column(name = "CHECK_CONSTRAINT")
	public String check;

	@Column(name = "DISPORDER")
	public int dispOrder;

	@Override
	protected Object getKey() {
		return pk;
	}

	public ColumnDesign toDomain() {
		return new ColumnDesign(
				pk.id,
				name,
				"",
				new DefineColumnType(
						DataType.valueOf(dataType),
						maxLength,
						scale,
						(nullable == 1),
						(defaultValue == null) ? "" : defaultValue,
						check),
				(comment == null) ? "" : comment,
				dispOrder);
	}

	private static NemTdAltAddTableColumn toEntity(NemTdAltContentPk parent, ColumnDesign domain) {
		val type = domain.getType();
		return new NemTdAltAddTableColumn(
			new NemTdAltAddTableColumnPk(
				parent.alterationId,
				parent.seqNo,
				domain.getId()),
			domain.getName(),
			domain.getJpName(),
			type.getDataType().toString(),
			type.getLength(),
			type.getScale(),
			type.isNullable() ? 1 : 0,
			type.getDefaultValue(),
			domain.getComment(),
			type.getCheckConstaint(),
			domain.getDispOrder()
		);
	}

	public static List<JpaEntity> toEntity(NemTdAltContentPk parent, AddTable domain) {
		return domain.getTableDesign().getColumns().stream()
		.map(d -> toEntity(parent, d))
		.collect(toList());
	}
}
