package nts.uk.cnv.infra.td.entity.uktabledesign;

import java.io.Serializable;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.PrimaryKeyJoinColumns;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.arc.layer.infra.data.entity.JpaEntity;

@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "SCVMT_UK_INDEX_COLUMNS")
public class ScvmtUkIndexColumns extends JpaEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	@EmbeddedId
	public ScvmtUkIndexColumnsPk pk;

	@ManyToOne
    @PrimaryKeyJoinColumns({
    	@PrimaryKeyJoinColumn(name = "TABLE_ID", referencedColumnName = "TABLE_ID"),
    	@PrimaryKeyJoinColumn(name = "SNAPSHOT_ID", referencedColumnName = "SNAPSHOT_ID"),
    	@PrimaryKeyJoinColumn(name = "EVENT_ID", referencedColumnName = "EVENT_ID"),
    	@PrimaryKeyJoinColumn(name = "NAME", referencedColumnName = "NAME")
    })
	public ScvmtUkIndexDesign indexdesign;

	@Override
	protected Object getKey() {
		return pk;
	}

}
