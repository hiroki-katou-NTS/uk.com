package nts.uk.ctx.at.record.infra.entity.stamp;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.uk.shr.infra.data.entity.ContractUkJpaEntity;

@Entity
@Table(name = "KWKDT_STAMP")
@AllArgsConstructor
@NoArgsConstructor
public class KwkdtStamp extends ContractUkJpaEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/* 主キー */
	@EmbeddedId
	public KwkdtStampPK kwkdtStampPK;

//	@JoinColumn(name = "WORK_LOCATION_CD", referencedColumnName = "WORK_LOCATION_CD", insertable = false, updatable = false)
//	@OneToOne(optional = false, cascade = CascadeType.DETACH)
//	public KwlmtWorkLocation kwlmtWorkLocation;

	@Basic(optional = false)
	@Column(name = "SIFT_CD")
	public String siftCd;

	@Basic(optional = false)
	@Column(name = "STAMP_COMBINATION_ATR")
	public int stampCombinationAtr;

	@Basic(optional = false)
	@Column(name = "WORK_LOCATION_CD")
	public String workLocationCd;

	@Basic(optional = false)
	@Column(name = "STAMP_METHOD")
	public int stampMethod;

	@Basic(optional = false)
	@Column(name = "GO_OUT_REASON")
	public int goOutReason;
	@Basic(optional = false)
	@Column(name = "REFLECTED_ATR")
	public Integer reflectedAtr;

	@Override
	protected Object getKey() {
		return kwkdtStampPK;
	}

}
