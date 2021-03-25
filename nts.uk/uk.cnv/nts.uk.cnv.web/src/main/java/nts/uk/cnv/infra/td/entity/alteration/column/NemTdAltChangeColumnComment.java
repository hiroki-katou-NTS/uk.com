package nts.uk.cnv.infra.td.entity.alteration.column;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.val;
import nts.uk.cnv.dom.td.alteration.content.AlterationContent;
import nts.uk.cnv.dom.td.alteration.content.column.ChangeColumnComment;
import nts.uk.cnv.infra.td.entity.alteration.NemTdAltContentBase;
import nts.uk.cnv.infra.td.entity.alteration.NemTdAltContentPk;

@SuppressWarnings("serial")
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "NEM_TD_ALT_CHANGE_COLUMN_COMMENT")
public class NemTdAltChangeColumnComment extends NemTdAltContentBase implements Serializable {

	@EmbeddedId
	public NemTdAltContentPk pk;

	@Column(name = "COLUMN_ID")
	public String columnId;

	@Column(name = "COMMENT")
	public String comment;

	public static NemTdAltContentBase toEntity(NemTdAltContentPk contentPk, AlterationContent ac) {
		val domain = (ChangeColumnComment) ac;
		return new NemTdAltChangeColumnComment(
				contentPk,
				domain.getColumnId(),
				domain.getComment());
	}

	public ChangeColumnComment toDomain() {
		return new ChangeColumnComment(this.columnId, this.comment);
	}

	@Override
	protected Object getKey() {
		return pk;
	}
}
