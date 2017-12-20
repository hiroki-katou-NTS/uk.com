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
 * 日別実績の休憩時間帯
 *
 */
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "KRCDT_DAI_BREAK_TIME")
public class KrcdtDaiBreakTime extends UkJpaEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	@EmbeddedId
	public KrcdtDaiBreakTimePK krcdtDaiBreakTimePK;
	
	@Column(name = "STR_STAMP_TIME")
	public BigDecimal startStampTime;

	@Column(name = "STR_STAMP_ROUDING_TIME_DAY")
	public BigDecimal startStampRoundingTimeDay;
	
	@Column(name = "STR_STAMP_PLACE_CODE")
	public String startStampPlaceCode;

	@Column(name = "STR_STAMP_SOURCE_INFO")
	public BigDecimal startStampSourceInfo;
	
	@Column(name = "END_STAMP_TIME")
	public BigDecimal endStampTime;

	@Column(name = "END_STAMP_ROUDING_TIME_DAY")
	public BigDecimal endStampRoundingTimeDay;
	
	@Column(name = "END_STAMP_PLACE_CODE")
	public String endStampPlaceCode;

	@Column(name = "END_STAMP_SOURCE_INFO")
	public BigDecimal endStampSourceInfo;
	
	@Override
	protected Object getKey() {
		return this.krcdtDaiBreakTimePK;
	}
}
