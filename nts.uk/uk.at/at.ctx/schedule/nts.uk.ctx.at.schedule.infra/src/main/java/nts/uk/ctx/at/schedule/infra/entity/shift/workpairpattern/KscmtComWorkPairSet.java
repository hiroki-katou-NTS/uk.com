package nts.uk.ctx.at.schedule.infra.entity.shift.workpairpattern;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.uk.shr.infra.data.entity.ContractUkJpaEntity;

/**
 * 勤務ペア設定
 * 
 * @author sonnh1
 *
 */
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "KSCMT_COM_WORK_PAIR_SET")
public class KscmtComWorkPairSet extends ContractUkJpaEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	@EmbeddedId
	public KscmtComWorkPairSetPK kscmtComWorkPairSetPk;

	@Column(name = "WORKTYPE_CD")
	public String workTypeCode;

	@Column(name = "WORKTIME_CD")
	public String workTimeCode;

	@ManyToOne
	@JoinColumns({ @JoinColumn(name = "CID", referencedColumnName = "CID", insertable = false, updatable = false),
			@JoinColumn(name = "GROUP_NO", referencedColumnName = "GROUP_NO", insertable = false, updatable = false),
			@JoinColumn(name = "PATTERN_NO", referencedColumnName = "PATTERN_NO", insertable = false, updatable = false) })
	private KscmtComPatternItem kscmtComPatternItem;

	@Override
	protected Object getKey() {
		return this.kscmtComWorkPairSetPk;
	}

	public KscmtComWorkPairSet(KscmtComWorkPairSetPK kscmtComWorkPairSetPk, String workTypeCode, String workTimeCode) {
		super();
		this.kscmtComWorkPairSetPk = kscmtComWorkPairSetPk;
		this.workTypeCode = workTypeCode;
		this.workTimeCode = workTimeCode;
	}
}
