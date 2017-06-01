package nts.uk.ctx.at.record.infra.entity.stamp;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.record.infra.entity.worklocation.KwlmtWorkLocation;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

@Entity
@Table(name = "KWKDT_STAMP")
@AllArgsConstructor
@NoArgsConstructor
public class KwkdtStamp extends UkJpaEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/* 主キー */
	@EmbeddedId
	public KwkdtStampPK kwkdtStampPK;
	
	@OneToOne
	public KwlmtWorkLocation kwlmtWorkLocation;

	@Basic(optional = false)
	@Column(name = "WORK_TIME_CD")
	public String workTimeCd;

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
	@Column(name = "STAMP_REASON")
	public int stampReason;

	@Override
	protected Object getKey() {
		return kwkdtStampPK;
	}

}
