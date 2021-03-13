package nts.uk.cnv.infra.td.entity.uktabledesign;

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
@Table(name = "SCVMT_UK_INDEX_DESIGN")
public class ScvmtUkIndexDesign extends JpaEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	@EmbeddedId
	public ScvmtUkIndexDesignPk pk;

	@Column(name = "TYPE")
	public String type;

	@Column(name = "IS_CLUSTERED")
	public boolean clustered;

	@OrderBy(value = "pk.id asc")
	@OneToMany(targetEntity = ScvmtUkIndexColumns.class, mappedBy = "indexdesign", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinTable(name = "SCVMT_UK_COLUMN_DESIGN")
	public List<ScvmtUkIndexColumns> columns;

	@ManyToOne
    @PrimaryKeyJoinColumns({
    	@PrimaryKeyJoinColumn(name = "TABLE_ID", referencedColumnName = "TABLE_ID"),
    	@PrimaryKeyJoinColumn(name = "SNAPSHOT_ID", referencedColumnName = "SNAPSHOT_ID"),
    	@PrimaryKeyJoinColumn(name = "EVENT_ID", referencedColumnName = "EVENT_ID")
    })
	public ScvmtUkTableDesign tabledesign;

	@Override
	protected Object getKey() {
		return pk;
	}

}
