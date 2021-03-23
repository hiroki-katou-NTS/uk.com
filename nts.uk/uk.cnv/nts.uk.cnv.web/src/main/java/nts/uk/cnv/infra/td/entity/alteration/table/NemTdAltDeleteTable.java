package nts.uk.cnv.infra.td.entity.alteration.table;

import java.io.Serializable;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.PrimaryKeyJoinColumns;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.val;
import nts.arc.layer.infra.data.entity.JpaEntity;
import nts.uk.cnv.dom.td.alteration.content.RemoveTable;
import nts.uk.cnv.infra.td.entity.alteration.NemTdAltContentPk;
import nts.uk.cnv.infra.td.entity.alteration.NemTdAlteration;

@SuppressWarnings("serial")
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "NEM_TD_ALT_DELETE_TABLE")
public class NemTdAltDeleteTable extends JpaEntity implements Serializable {

	@EmbeddedId
	public NemTdAltContentPk pk;

	@ManyToOne
    @PrimaryKeyJoinColumns({
    	@PrimaryKeyJoinColumn(name = "ALTERATION_ID", referencedColumnName = "ALTERATION_ID")
    })
	public NemTdAlteration alteration;
	
	public static NemTdAltDeleteTable toEntity(NemTdAltContentPk pk) {
		val e = new NemTdAltDeleteTable();
		e.pk = pk;
		return e;
	}

	public RemoveTable toDomain() {
		return new RemoveTable();
	}

	@Override
	protected Object getKey() {
		return pk;
	}
}
