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
@Table(name = "KSCMT_WKP_PATTERN_ITEM")
public class KscmtWkpPatternItem extends ContractUkJpaEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	@EmbeddedId
	public KscmtWkpPatternItemPK kscmtWkpPatternItemPk;

	@Column(name = "PATTERN_NAME")
	public String patternName;

	@ManyToOne
	@JoinColumns({ @JoinColumn(name = "WKP_ID", referencedColumnName = "WKP_ID", insertable = false, updatable = false),
			@JoinColumn(name = "GROUP_NO", referencedColumnName = "GROUP_NO", insertable = false, updatable = false) })
	private KscmtWkpPattern kscmtWkpPattern;

	@OneToMany(targetEntity=KscmtWkpWorkPairSet.class, cascade = CascadeType.ALL, mappedBy = "kscmtWkpPatternItem", orphanRemoval = true)
	@JoinTable(name = "KSCMT_WKP_WORK_PAIR_SET")
	public List<KscmtWkpWorkPairSet> kscmtWkpWorkPairSet;

	@Override
	protected Object getKey() {
		return this.kscmtWkpPatternItemPk;
	}

	public KscmtWkpPatternItem(KscmtWkpPatternItemPK kscmtWkpPatternItemPk, String patternName,
			List<KscmtWkpWorkPairSet> kscmtWkpWorkPairSet) {
		super();
		this.kscmtWkpPatternItemPk = kscmtWkpPatternItemPk;
		this.patternName = patternName;
		this.kscmtWkpWorkPairSet = kscmtWkpWorkPairSet;
	}
}
