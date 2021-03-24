package nts.uk.cnv.infra.td.entity.snapshot.column;

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
import nts.arc.layer.infra.data.jdbc.map.JpaEntityMapper;
import nts.uk.cnv.dom.td.schema.tabledesign.column.ColumnDesign;
import nts.uk.cnv.dom.td.schema.tabledesign.column.DataType;
import nts.uk.cnv.dom.td.schema.tabledesign.column.DefineColumnType;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "NEM_TD_SNAPSHOT_COLUMN")
public class NemTdSnapshotColumn extends JpaEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	public static JpaEntityMapper<NemTdSnapshotColumn> MAPPER = new JpaEntityMapper<>(NemTdSnapshotColumn.class);

	@EmbeddedId
	public NemTdSnapshotColumnPk pk;

	@Column(name = "NAME")
	public String name;

	@Column(name = "JPNAME")
	public String jpName;

	@Column(name = "DATA_TYPE")
	private String dataType;

	@Column(name = "MAX_LENGTH")
	private int maxLength;

	@Column(name = "SCALE")
	private int scale;

	@Column(name = "NULLABLE")
	private int nullable;

	@Column(name = "DEFAULT_VALUE")
	private String defaultValue;

	@Column(name = "COMMENT")
	private String comment;

	@Column(name = "CHECK_CONSTRAINT")
	private String check;

	@Column(name = "DISPORDER")
	private int dispOrder;

	@Override
	protected Object getKey() {
		return pk;
	}

	public ColumnDesign toDomain() {
		return new ColumnDesign(
				pk.id,
				name,
				jpName,
				new DefineColumnType(
						DataType.valueOf(dataType),
						maxLength,
						scale,
						(nullable == 1),
						defaultValue,
						check),
				comment,
				dispOrder);
	}
	public static NemTdSnapshotColumn toEntity(String snapshotId, String tableId, ColumnDesign column) {
		val pk = new NemTdSnapshotColumnPk(
				column.getId(),
				snapshotId,
				tableId);
		
		return new NemTdSnapshotColumn(pk, 
				column.getName(), 
				column.getJpName(), 
				column.getType().getDataType().toString(), 
				column.getType().getLength(), 
				column.getType().getScale(), 
				column.getType().isNullable() ? 1 : 0, 
				column.getType().getDefaultValue(), 
				column.getComment(), 
				column.getType().getCheckConstaint(), 
				column.getDispOrder());
	}
}
