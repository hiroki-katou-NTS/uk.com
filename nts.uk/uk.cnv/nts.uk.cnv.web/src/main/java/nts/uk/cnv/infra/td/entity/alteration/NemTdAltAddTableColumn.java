package nts.uk.cnv.infra.td.entity.alteration;

import java.io.Serializable;

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
import nts.arc.layer.infra.data.entity.JpaEntity;
import nts.uk.cnv.dom.td.tabledefinetype.DataType;
import nts.uk.cnv.dom.td.tabledesign.ColumnDesign;
import nts.uk.cnv.dom.td.tabledesign.DefineColumnType;

@Getter
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

}
