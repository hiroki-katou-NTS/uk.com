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
@Table(name = "KSCMT_PAIR_COM")
public class KscmtPairCom extends ContractUkJpaEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	@EmbeddedId
	public KscmtPairComPK kscmtPairComPk;

	@Column(name = "WORKTYPE_CD")
	public String workTypeCode;

	@Column(name = "WORKTIME_CD")
	public String workTimeCode;

	@ManyToOne
	@JoinColumns({ @JoinColumn(name = "CID", referencedColumnName = "CID", insertable = false, updatable = false),
			@JoinColumn(name = "GROUP_NO", referencedColumnName = "GROUP_NO", insertable = false, updatable = false),
			@JoinColumn(name = "PATTERN_NO", referencedColumnName = "PATTERN_NO", insertable = false, updatable = false) })
	private KscmtPairPatrnCom kscmtPairPatrnCom;

	@Override
	protected Object getKey() {
		return this.kscmtPairComPk;
	}

	public KscmtPairCom(KscmtPairComPK kscmtPairComPk, String workTypeCode, String workTimeCode) {
		super();
		this.kscmtPairComPk = kscmtPairComPk;
		this.workTypeCode = workTypeCode;
		this.workTimeCode = workTimeCode;
	}
}
