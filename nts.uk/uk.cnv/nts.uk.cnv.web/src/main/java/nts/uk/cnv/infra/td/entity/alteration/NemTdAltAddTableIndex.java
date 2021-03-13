package nts.uk.cnv.infra.td.entity.alteration;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinTable;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.PrimaryKeyJoinColumns;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.arc.layer.infra.data.entity.JpaEntity;

@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "NEM_TD_ALT_ADD_TABLE_INDEX")
public class NemTdAltAddTableIndex extends JpaEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	@EmbeddedId
	public NemTdAltAddTableIndexPk pk;

	@Column(name = "TYPE")
	public String type;

	@Column(name = "IS_CLUSTERED")
	public boolean clustered;

	@OrderBy(value = "pk.id asc")
	@OneToMany(targetEntity = NemTdAltAddTableIndexColumns.class, mappedBy = "addTableIndex", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinTable(name = "NEM_TD_ALT_ADD_TABLE_INDEX_COLUMN")
	public List<NemTdAltAddTableIndexColumns> columns;

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

}
