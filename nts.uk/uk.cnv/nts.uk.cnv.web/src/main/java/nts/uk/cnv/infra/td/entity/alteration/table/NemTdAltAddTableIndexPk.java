package nts.uk.cnv.infra.td.entity.alteration.table;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.uk.cnv.infra.td.entity.alteration.NemTdAltContentPk;

@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public class NemTdAltAddTableIndexPk implements Serializable {
	private static final long serialVersionUID = 1L;

	@Column(name = "ALTERATION_ID")
	public String alterationId;

	@Column(name = "SEQ_NO")
	public int seqNo;
	
	@Column(name = "TYPE")
	public String type;

	@Column(name = "SUFFIX")
	public String suffix;
	
	private static final String TYPE_PK = "PRIMARY KEY";
	private static final String TYPE_UK = "UNIQUE KEY";
	private static final String TYPE_INDEX = "INDEX";

	public static NemTdAltAddTableIndexPk asPK(NemTdAltContentPk parentPk) {
		return new NemTdAltAddTableIndexPk(parentPk.alterationId, parentPk.seqNo, TYPE_PK, "PK");
	}
	
	public static NemTdAltAddTableIndexPk asUK(NemTdAltContentPk parentPk, String suffix) {
		return new NemTdAltAddTableIndexPk(parentPk.alterationId, parentPk.seqNo, TYPE_UK, suffix);
	}
	
	public static NemTdAltAddTableIndexPk asIndex(NemTdAltContentPk parentPk, String suffix) {
		return new NemTdAltAddTableIndexPk(parentPk.alterationId, parentPk.seqNo, TYPE_INDEX, suffix);
	}
	
	public boolean isPK() {
		return TYPE_PK.equals(type);
	}

	public boolean isUK() {
		return TYPE_UK.equals(type);
	}

	public boolean isIndex() {
		return TYPE_INDEX.equals(type);
	}
}
