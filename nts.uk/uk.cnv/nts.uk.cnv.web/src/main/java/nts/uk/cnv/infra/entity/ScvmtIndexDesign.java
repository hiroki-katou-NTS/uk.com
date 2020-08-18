package nts.uk.cnv.infra.entity;

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
@Table(name = "SCVMT_INDEX_DESIGN")
public class ScvmtIndexDesign extends JpaEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	@EmbeddedId
	public ScvmtIndexDesignPk pk;
	
	@ManyToOne
    @PrimaryKeyJoinColumns({
    	@PrimaryKeyJoinColumn(name = "TABLE_ID", referencedColumnName = "TABLE_ID")
    })
	public ScvmtTableDesign tabledesign;

	@Override
	protected Object getKey() {
		return pk;
	}
	
}
