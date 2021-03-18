package nts.uk.cnv.infra.td.entity.alteration.index;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Embeddable
public class NemTdAltAddTableIndexColumnsPk implements Serializable {
	private static final long serialVersionUID = 1L;

	@Column(name = "ALTERATION_ID")
	private String alterationId;

	@Column(name = "SEQ_NO")
	private int seqNo;

	/** 主キーの場合は固定で "PK" */
	@Column(name = "SUFFIX")
	public String suffix;

	@Column(name = "COLUMN_ORDER")
	private int columnOrder;
	
	public static NemTdAltAddTableIndexColumnsPk create(NemTdAltAddTableIndexPk parentPk, int columnOrder) {
		return new NemTdAltAddTableIndexColumnsPk(
				parentPk.alterationId,
				parentPk.seqNo,
				parentPk.suffix,
				columnOrder);
	}
}
