package nts.uk.cnv.infra.entity.tabledesign;

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
import nts.uk.cnv.core.dom.tabledesign.ColumnDesign;

@Getter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "NEM_TD_SNAPSHOT_COLUMN")
public class NemTdSnapshotColumn extends JpaEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	@EmbeddedId
	public NemTdSnapshotColumnPk pk;

	@Column(name = "NAME")
	public String name;

	@Column(name = "JPNAME")
	private String jpName;

	@Column(name = "DATA_TYPE")
	private String type;

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
	private String checkConstraint;

	@Column(name = "DISPORDER")
	private int dispOrder;

	@ManyToOne
    @PrimaryKeyJoinColumns({
    	@PrimaryKeyJoinColumn(name = "TABLE_ID", referencedColumnName = "TABLE_ID"),
    	@PrimaryKeyJoinColumn(name = "SNAPSHOT_ID", referencedColumnName = "SNAPSHOT_ID")
    })
	public NemTdSnapshotTable tabledesign;

	@Override
	protected Object getKey() {
		return pk;
	}

	public ColumnDesign toDomain() {
		return new ColumnDesign(
				pk.id,
				name,
				type,
				(nullable == 1 ? true : false),
				defaultValue,
				comment,
				dispOrder,
				false
			);
	}

}
