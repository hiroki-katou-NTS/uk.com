package nts.uk.cnv.infra.td.entity.snapshot.column;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.val;
import nts.arc.layer.infra.data.entity.JpaEntity;
import nts.arc.layer.infra.data.jdbc.map.JpaEntityMapper;
import nts.uk.cnv.dom.td.schema.tabledesign.column.ColumnDesign;
import nts.uk.cnv.dom.td.schema.tabledesign.column.DataType;
import nts.uk.cnv.dom.td.schema.tabledesign.column.DefineColumnType;
import nts.uk.cnv.infra.td.entity.snapshot.NemTdSnapshotTable;
import nts.uk.cnv.infra.td.entity.snapshot.index.NemTdSnapshotTableIndex;
import nts.uk.cnv.infra.td.entity.snapshot.index.NemTdSnapshotTableIndexColumnsPk;

@Setter
@Getter
@Entity
@NoArgsConstructor
@AllArgsConstructor
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

	@ManyToOne
	@JoinColumns({ 
		@JoinColumn(name = "SNAPSHOT_ID", referencedColumnName = "SNAPSHOT_ID", insertable = false, updatable = false),
		@JoinColumn(name = "TABLE_ID", referencedColumnName = "TABLE_ID", insertable = false, updatable = false),
	})
	public NemTdSnapshotTable columnOfTable;
	
	public NemTdSnapshotColumn(NemTdSnapshotColumnPk pk, String name, String jpName, String dataType, int maxLength,
			int scale, int nullable, String defaultValue, String comment, String check, int dispOrder) {
		super();
		this.pk = pk;
		this.name = name;
		this.jpName = jpName;
		this.dataType = dataType;
		this.maxLength = maxLength;
		this.scale = scale;
		this.nullable = nullable;
		this.defaultValue = defaultValue;
		this.comment = comment;
		this.check = check;
		this.dispOrder = dispOrder;
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
				snapshotId,
				tableId,
				column.getId());
		
		return new NemTdSnapshotColumn(pk, 
				column.getName(), 
				column.getJpName(), 
				column.getType().getDataType().toString(), 
				column.getType().getLength(), 
				column.getType().getScale(), 
				column.getType().isNullable() ? 1 : 0, 
				column.getType().getDefaultValue(), 
				column.getComment(), 
				column.getType().getCheckConstraint(), 
				column.getDispOrder());
	}

}
