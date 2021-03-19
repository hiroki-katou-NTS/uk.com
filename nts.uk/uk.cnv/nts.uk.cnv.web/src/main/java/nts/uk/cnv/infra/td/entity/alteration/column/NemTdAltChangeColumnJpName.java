package nts.uk.cnv.infra.td.entity.alteration.column;

import java.io.Serializable;

import javax.persistence.Column;
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
import nts.uk.cnv.dom.td.alteration.content.column.ChangeColumnJpName;
import nts.uk.cnv.infra.td.entity.alteration.NemTdAltContentPk;
import nts.uk.cnv.infra.td.entity.alteration.NemTdAlteration;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "NEM_TD_ALT_CHANGE_COLUMN_JPNAME")
public class NemTdAltChangeColumnJpName extends JpaEntity implements Serializable {

	/** serialVersionUID */
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	public NemTdAltContentPk pk;

	@Column(name = "COLUMN_ID")
	public String columnId;

	@Column(name = "JPNAME")
	public String jpName;

	@ManyToOne
    @PrimaryKeyJoinColumns({
    	@PrimaryKeyJoinColumn(name = "ALTERATION_ID", referencedColumnName = "ALTERATION_ID")
    })
	public NemTdAlteration alteration;
	
	public static NemTdAltChangeColumnJpName toEntity(NemTdAltContentPk pk, ChangeColumnJpName d) {
		
		val e = new NemTdAltChangeColumnJpName();
		
		e.pk = pk;
		e.columnId = d.getColumnId();
		e.jpName = d.getJpName();
		
		return e;
	}

	public ChangeColumnJpName toDomain() {
		return new ChangeColumnJpName(this.columnId, this.jpName);
	}

	@Override
	protected Object getKey() {
		return pk;
	}
}
