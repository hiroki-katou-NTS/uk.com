package nts.uk.cnv.infra.td.entity.alteration;

import static java.util.stream.Collectors.*;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.PrimaryKeyJoinColumns;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.val;
import nts.arc.layer.infra.data.entity.JpaEntity;
import nts.uk.cnv.dom.td.schema.tabledesign.column.ColumnDesign;
import nts.uk.cnv.dom.td.schema.tabledesign.column.DataType;
import nts.uk.cnv.dom.td.schema.tabledesign.column.DefineColumnType;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "NEM_TD_ALT_ADD_TABLE_COLUMN")
public class NemTdAltAddTableColumn extends JpaEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private NemTdAltAddTableColumnPk pk;

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

	@ManyToOne
    @PrimaryKeyJoinColumns({
    	@PrimaryKeyJoinColumn(name = "ALTERATION_ID", referencedColumnName = "ALTERATION_ID"),
    	@PrimaryKeyJoinColumn(name = "SEQ_NO", referencedColumnName = "SEQ_NO")
    })
	public NemTdAltAddTable addTable;

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
						defaultValue,
						check),
				comment,
				dispOrder);
	}

	public static NemTdAltAddTableColumn toEntity(NemTdAltAddTable parent, ColumnDesign d) {
		
		val e = new NemTdAltAddTableColumn();
		
		e.pk = new NemTdAltAddTableColumnPk(
				parent.pk.alterationId,
				parent.pk.seqNo,
				d.getId());
		e.name = d.getName();
		e.jpName = d.getJpName();
		e.comment = d.getComment();
		e.dispOrder = d.getDispOrder();
		
		val t = d.getType();
		e.dataType = t.getDataType().toString();
		e.maxLength = t.getLength();
		e.scale = t.getScale();
		e.nullable = t.isNullable() ? 1 : 0;
		e.defaultValue = t.getDefaultValue();
		e.check = t.getCheckConstaint();
		
		return e;
	}
	
	public static List<NemTdAltAddTableColumn> toEntity(NemTdAltAddTable parent, List<ColumnDesign> d) {
		return d.stream()
				.map(c -> toEntity(parent, c))
				.collect(toList());
	}
}
