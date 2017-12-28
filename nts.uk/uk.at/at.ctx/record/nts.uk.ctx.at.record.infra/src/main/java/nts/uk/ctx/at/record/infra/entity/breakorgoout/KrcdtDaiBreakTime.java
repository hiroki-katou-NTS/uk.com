package nts.uk.ctx.at.record.infra.entity.breakorgoout;

import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.record.dom.breakorgoout.BreakTimeOfDailyPerformance;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

/**
 * 
 * @author nampt 日別実績の休憩時間帯
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
	public int startStampTime;

	@Column(name = "STR_STAMP_ROUDING_TIME_DAY")
	public int startStampRoundingTimeDay;

	@Column(name = "STR_STAMP_PLACE_CODE")
	public String startStampPlaceCode;

	@Column(name = "STR_STAMP_SOURCE_INFO")
	public int startStampSourceInfo;

	@Column(name = "END_STAMP_TIME")
	public int endStampTime;

	@Column(name = "END_STAMP_ROUDING_TIME_DAY")
	public int endStampRoundingTimeDay;

	@Column(name = "END_STAMP_PLACE_CODE")
	public String endStampPlaceCode;

	@Column(name = "END_STAMP_SOURCE_INFO")
	public int endStampSourceInfo;

	@Override
	protected Object getKey() {
		return this.krcdtDaiBreakTimePK;
	}

	public static List<KrcdtDaiBreakTime> toEntity(BreakTimeOfDailyPerformance breakTime) {
		return breakTime.getBreakTimeSheets().stream().map(c -> new KrcdtDaiBreakTime(
				new KrcdtDaiBreakTimePK(breakTime.getEmployeeId(), breakTime.getYmd(), breakTime.getBreakType().value,
						c.getBreakFrameNo().v()),
				c.getStartTime().getTimeWithDay().valueAsMinutes(),
				c.getStartTime().getAfterRoundingTime().valueAsMinutes(), c.getStartTime().getLocationCode().v(),
				c.getStartTime().getStampSourceInfo().value, c.getEndTime().getTimeWithDay().valueAsMinutes(),
				c.getEndTime().getAfterRoundingTime().valueAsMinutes(), c.getEndTime().getLocationCode().v(),
				c.getEndTime().getStampSourceInfo().value)).collect(Collectors.toList());
	}
}
