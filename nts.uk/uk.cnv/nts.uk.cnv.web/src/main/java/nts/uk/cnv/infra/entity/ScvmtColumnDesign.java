package nts.uk.cnv.infra.entity;

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
import nts.uk.cnv.dom.databasetype.DataType;
import nts.uk.cnv.dom.tabledesign.ColumnDesign;

@Getter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "SCVMT_COLUMN_DESIGN")
public class ScvmtColumnDesign extends JpaEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	@EmbeddedId
	public ScvmtColumnDesignPk scvmtColumnDesignPk;

	@Column(name = "NAME")
	public String name;
	
	@Column(name = "DATA_TYPE")
	private String dataType;

	@Column(name = "MAX_LENGTH")
	private int maxLength;

	@Column(name = "SCALE")
	private int scale;

	@Column(name = "NULLABLE")
	private int nullable;

	@Column(name = "PRIMARY_KEY")
	private int primaryKey;

	@Column(name = "PRIMARY_KEY_SEQ")
	private int primaryKeySeq;

	@Column(name = "UNIQUE_KEY")
	private int uniqueKey;

	@Column(name = "UNIQUE_KEY_SEQ")
	private int uniqueKeySeq;

	@Column(name = "DEFAULT_VALUE")
	private String defaultValue;

	@Column(name = "COMMENT")
	private String comment;

	@ManyToOne
    @PrimaryKeyJoinColumns({
    	@PrimaryKeyJoinColumn(name = "TABLE_ID", referencedColumnName = "TABLE_ID")
    })
	public ScvmtTableDesign tabledesign;
	
	@Override
	protected Object getKey() {
		return scvmtColumnDesignPk;
	}

	public ColumnDesign toDomain() {
		return new ColumnDesign(
				scvmtColumnDesignPk.id,
				name,
				DataType.valueOf(dataType),
				maxLength,
				scale,
				(nullable == 1),
				(primaryKey == 1),
				primaryKeySeq,
				(uniqueKey == 1),
				uniqueKeySeq,
				defaultValue,
				comment);
	}

}
