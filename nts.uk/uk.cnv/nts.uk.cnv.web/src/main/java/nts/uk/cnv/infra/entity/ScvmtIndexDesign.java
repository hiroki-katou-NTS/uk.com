package nts.uk.cnv.infra.entity;

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

	@Column(name = "TYPE")
	public String type;

	@Column(name = "PARAMS")
	public String params;
	
	@OneToMany(targetEntity = ScvmtIndexColumns.class, mappedBy = "indexdesign", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinTable(name = "SCVMT_COLUMN_DESIGN")
	public List<ScvmtIndexColumns> columns;
	
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
