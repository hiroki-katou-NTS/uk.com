package nts.uk.ctx.at.record.infra.entity.breakorgoout;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

/**
 * 
 * @author nampt
 * 日別実績の外出時間帯
 *
 */
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "KRCDT_DAI_OUTING_TIME")
public class KrcdtDaiOutingTime extends UkJpaEntity implements Serializable  {

	private static final long serialVersionUID = 1L;

	@EmbeddedId
	public KrcdtDaiOutingTimePK krcdtDaiOutingTimePK;
	
	@Column(name = "OUT_STAMP_TIME")
	public BigDecimal outStampTime;

	@Column(name = "OUT_STAMP_ROUDING_TIME_DAY")
	public BigDecimal outStampRoundingTimeDay;
	
	@Column(name = "OUT_STAMP_PLACE_CODE")
	public String outStampPlaceCode;

	@Column(name = "OUT_STAMP_SOURCE_INFO")
	public BigDecimal outStampSourceInfo;
	
	@Column(name = "OUT_ACTUAL_TIME")
	public BigDecimal outActualTime;

	@Column(name = "OUT_ACTUAL_ROUDING_TIME_DAY")
	public BigDecimal outActualRoundingTimeDay;
	
	@Column(name = "OUT_ACTUAL_PLACE_CODE")
	public String outActualPlaceCode;

	@Column(name = "OUT_ACTUAL_SOURCE_INFO")
	public BigDecimal outActualSourceInfo;
	
	@Column(name = "OUT_NUMBER_STAMP")
	public BigDecimal outNumberStamp;
	
	@Column(name = "BACK_STAMP_TIME")
	public BigDecimal backStampTime;

	@Column(name = "BACK_STAMP_ROUDING_TIME_DAY")
	public BigDecimal backStampRoundingTimeDay;
	
	@Column(name = "BACK_STAMP_PLACE_CODE")
	public String backStampPlaceCode;

	@Column(name = "BACK_STAMP_SOURCE_INFO")
	public BigDecimal backStampSourceInfo;
	
	@Column(name = "BACK_ACTUAL_TIME")
	public BigDecimal backActualTime;

	@Column(name = "BACK_ACTUAL_ROUDING_TIME_DAY")
	public BigDecimal backActualRoundingTimeDay;
	
	@Column(name = "BACK_ACTUAL_PLACE_CODE")
	public String backActualPlaceCode;

	@Column(name = "BACK_ACTUAL_SOURCE_INFO")
	public BigDecimal backActualSourceInfo;
	
	@Column(name = "BACK_NUMBER_STAMP")
	public BigDecimal backNumberStamp;
	
	@Column(name = "OUTING_TIME_CALCULATION")
	public BigDecimal outingTimeCalculation;

	@Column(name = "OUTING_TIME")
	public BigDecimal outingTime;
	
	@Column(name = "OUTING_REASON")
	public BigDecimal outingReason;
	
	@Override
	protected Object getKey() {
		return this.krcdtDaiOutingTimePK;
	}
}
