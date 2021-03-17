package nts.uk.cnv.infra.td.entity.uktabledesign;

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
@Table(name = "SCVMT_UK_INDEX_COLUMNS")
public class ScvmtUkIndexColumns extends JpaEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	@EmbeddedId
	public ScvmtUkIndexColumnsPk pk;

	@Column(name = "COLUMN_ID")
	public String columnId;

	@ManyToOne
    @PrimaryKeyJoinColumns({
    	@PrimaryKeyJoinColumn(name = "TABLE_ID", referencedColumnName = "TABLE_ID"),
    	@PrimaryKeyJoinColumn(name = "SNAPSHOT_ID", referencedColumnName = "SNAPSHOT_ID"),
    	@PrimaryKeyJoinColumn(name = "INDEX_ID", referencedColumnName = "INDEX_ID")
    })
	public ScvmtUkIndexDesign indexdesign;

	@Override
	protected Object getKey() {
		return pk;
	}

}
