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

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "NEM_TD_ALT_ADD_TABLE_INDEX_COLUMN")
public class NemTdAltAddTableIndexColumns extends JpaEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	@EmbeddedId
	public NemTdAltAddTableIndexColumnsPk pk;

	@Column(name = "COLUMN_NAME")
	private String columnName;

	@ManyToOne
    @PrimaryKeyJoinColumns({
    	@PrimaryKeyJoinColumn(name = "ALTERATION_ID", referencedColumnName = "ALTERATION_ID"),
    	@PrimaryKeyJoinColumn(name = "SEQ_NO", referencedColumnName = "SEQ_NO"),
    	@PrimaryKeyJoinColumn(name = "NAME", referencedColumnName = "NAME")
    })
	public NemTdAltAddTableIndex addTableIndex;

	@Override
	protected Object getKey() {
		return pk;
	}
}
