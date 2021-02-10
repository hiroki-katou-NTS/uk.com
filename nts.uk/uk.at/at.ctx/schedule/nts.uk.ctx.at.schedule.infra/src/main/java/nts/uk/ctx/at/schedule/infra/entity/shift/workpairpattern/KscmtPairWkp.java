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
import nts.uk.shr.infra.data.entity.UkJpaEntity;

/**
 * 勤務ペア設定
 * 
 * @author sonnh1
 *
 */
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "KSCMT_PAIR_WKP")
public class KscmtPairWkp extends UkJpaEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	@EmbeddedId
	public KscmtWkpWorkPairSetPK kscmtWkpWorkPairSetPk;

	@Column(name = "WORKTYPE_CD")
	public String workTypeCode;

	@Column(name = "WORKTIME_CD")
	public String workTimeCode;

	@ManyToOne
	@JoinColumns({ @JoinColumn(name = "WKP_ID", referencedColumnName = "WKP_ID", insertable = false, updatable = false),
			@JoinColumn(name = "GROUP_NO", referencedColumnName = "GROUP_NO", insertable = false, updatable = false),
			@JoinColumn(name = "PATTERN_NO", referencedColumnName = "PATTERN_NO", insertable = false, updatable = false), })
	private KscmtPairPatrnWkp kscmtWkpPatternItem;

	@Override
	protected Object getKey() {
		return this.kscmtWkpWorkPairSetPk;
	}

	public KscmtPairWkp(KscmtWkpWorkPairSetPK kscmtWkpWorkPairSetPk, String workTypeCode, String workTimeCode) {
		super();
		this.kscmtWkpWorkPairSetPk = kscmtWkpWorkPairSetPk;
		this.workTypeCode = workTypeCode;
		this.workTimeCode = workTimeCode;
	}
}
