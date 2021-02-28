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
    	@PrimaryKeyJoinColumn(name = "BRANCH", referencedColumnName = "BRANCH"),
    	@PrimaryKeyJoinColumn(name = "DATE", referencedColumnName = "DATE"),
    	@PrimaryKeyJoinColumn(name = "NAME", referencedColumnName = "NAME")
    })
	public ScvmtUkIndexDesign indexdesign;

	@Override
	protected Object getKey() {
		return pk;
	}

}
