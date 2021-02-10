package nts.uk.ctx.at.schedule.infra.entity.shift.workpairpattern;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.JoinTable;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

/**
 * 勤務ペアパターン
 * 
 * @author sonnh1
 *
 */
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "KSCMT_PAIR_PATRN_COM")
public class KscmtPairPatrnCom extends UkJpaEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	@EmbeddedId
	public KscmtComPatternItemPK kscmtComPatternItemPk;

	@Column(name = "PATTERN_NAME")
	public String patternName;

	@ManyToOne
	@JoinColumns({ @JoinColumn(name = "CID", referencedColumnName = "CID", insertable = false, updatable = false),
			@JoinColumn(name = "GROUP_NO", referencedColumnName = "GROUP_NO", insertable = false, updatable = false) })
	private KscmtPairGrpCom kscmtComPattern;

	@OneToMany(targetEntity=KscmtPairCom.class, cascade = CascadeType.ALL, mappedBy = "kscmtComPatternItem", orphanRemoval = true)
	@JoinTable(name = "KSCMT_PAIR_COM")
	public List<KscmtPairCom> kscmtComWorkPairSet;

	@Override
	protected Object getKey() {
		return this.kscmtComPatternItemPk;
	}

	public KscmtPairPatrnCom(KscmtComPatternItemPK kscmtComPatternItemPk, String patternName,
			List<KscmtPairCom> kscmtComWorkPairSet) {
		super();
		this.kscmtComPatternItemPk = kscmtComPatternItemPk;
		this.patternName = patternName;
		this.kscmtComWorkPairSet = kscmtComWorkPairSet;
	}
}
