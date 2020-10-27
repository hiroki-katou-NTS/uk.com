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
import nts.uk.shr.infra.data.entity.ContractUkJpaEntity;

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
public class KscmtPairPatrnCom extends ContractUkJpaEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	@EmbeddedId
	public KscmtPairPatrnComPK kscmtPairPatrnComPk;

	@Column(name = "PATTERN_NAME")
	public String patternName;

	@ManyToOne
	@JoinColumns({ @JoinColumn(name = "CID", referencedColumnName = "CID", insertable = false, updatable = false),
			@JoinColumn(name = "GROUP_NO", referencedColumnName = "GROUP_NO", insertable = false, updatable = false) })
	private KscmtPairGrpCom kscmtPairGrpCom;

	@OneToMany(targetEntity=KscmtPairCom.class, cascade = CascadeType.ALL, mappedBy = "kscmtPairPatrnCom", orphanRemoval = true)
	@JoinTable(name = "KSCMT_PAIR_COM")
	public List<KscmtPairCom> kscmtPairCom;

	@Override
	protected Object getKey() {
		return this.kscmtPairPatrnComPk;
	}

	public KscmtPairPatrnCom(KscmtPairPatrnComPK kscmtPairPatrnComPk, String patternName,
			List<KscmtPairCom> kscmtPairCom) {
		super();
		this.kscmtPairPatrnComPk = kscmtPairPatrnComPk;
		this.patternName = patternName;
		this.kscmtPairCom = kscmtPairCom;
	}
}
