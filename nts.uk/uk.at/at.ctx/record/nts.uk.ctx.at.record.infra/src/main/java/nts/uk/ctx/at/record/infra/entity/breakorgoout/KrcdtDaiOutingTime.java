package nts.uk.ctx.at.record.infra.entity.breakorgoout;

import java.io.Serializable;
<<<<<<< Updated upstream
=======
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
>>>>>>> Stashed changes

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.arc.time.GeneralDate;
<<<<<<< Updated upstream
import nts.uk.ctx.at.record.dom.breakorgoout.OutingTimeSheet;
import nts.uk.ctx.at.record.dom.worktime.WorkStamp;
=======
import nts.uk.ctx.at.record.dom.breakorgoout.OutingTimeOfDailyPerformance;
import nts.uk.ctx.at.record.dom.breakorgoout.OutingTimeSheet;
>>>>>>> Stashed changes
import nts.uk.shr.infra.data.entity.UkJpaEntity;

/**
 * 
 * @author nampt 日別実績の外出時間帯
 *
 */
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "KRCDT_DAI_OUTING_TIME")
public class KrcdtDaiOutingTime extends UkJpaEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	@EmbeddedId
	public KrcdtDaiOutingTimePK krcdtDaiOutingTimePK;

	@Column(name = "OUT_STAMP_TIME")
	public Integer outStampTime;

	@Column(name = "OUT_STAMP_ROUDING_TIME_DAY")
	public Integer outStampRoundingTimeDay;

	@Column(name = "OUT_STAMP_PLACE_CODE")
	public String outStampPlaceCode;

	@Column(name = "OUT_STAMP_SOURCE_INFO")
	public Integer outStampSourceInfo;

	@Column(name = "OUT_ACTUAL_TIME")
	public Integer outActualTime;

	@Column(name = "OUT_ACTUAL_ROUDING_TIME_DAY")
	public Integer outActualRoundingTimeDay;

	@Column(name = "OUT_ACTUAL_PLACE_CODE")
	public String outActualPlaceCode;

	@Column(name = "OUT_ACTUAL_SOURCE_INFO")
	public Integer outActualSourceInfo;

	@Column(name = "OUT_NUMBER_STAMP")
	public Integer outNumberStamp;

	@Column(name = "BACK_STAMP_TIME")
	public Integer backStampTime;

	@Column(name = "BACK_STAMP_ROUDING_TIME_DAY")
	public Integer backStampRoundingTimeDay;

	@Column(name = "BACK_STAMP_PLACE_CODE")
	public String backStampPlaceCode;

	@Column(name = "BACK_STAMP_SOURCE_INFO")
	public Integer backStampSourceInfo;

	@Column(name = "BACK_ACTUAL_TIME")
	public Integer backActualTime;

	@Column(name = "BACK_ACTUAL_ROUDING_TIME_DAY")
	public Integer backActualRoundingTimeDay;

	@Column(name = "BACK_ACTUAL_PLACE_CODE")
	public String backActualPlaceCode;

	@Column(name = "BACK_ACTUAL_SOURCE_INFO")
	public Integer backActualSourceInfo;

	@Column(name = "BACK_NUMBER_STAMP")
	public Integer backNumberStamp;

	@Column(name = "OUTING_TIME_CALCULATION")
	public Integer outingTimeCalculation;

	@Column(name = "OUTING_TIME")
	public Integer outingTime;

	@Column(name = "OUTING_REASON")
	public Integer outingReason;

	@Override
	protected Object getKey() {
		return this.krcdtDaiOutingTimePK;
	}

	public static KrcdtDaiOutingTime toEntity(String employeeId, GeneralDate date, OutingTimeSheet outingTime) {
		WorkStamp outStamp = outingTime.getGoOut().getStamp().orElseGet(null);
		WorkStamp backStamp = outingTime.getComeBack().getStamp().orElseGet(null);
		return new KrcdtDaiOutingTime(new KrcdtDaiOutingTimePK(employeeId, date, outingTime.getOutingFrameNo().v()),
				outStamp == null ? null
						: outStamp.getTimeWithDay() == null ? null : outStamp.getTimeWithDay().valueAsMinutes(),
				outStamp == null ? null
						: outStamp.getAfterRoundingTime() == null ? null
								: outStamp.getAfterRoundingTime().valueAsMinutes(),
				outStamp == null ? null : outStamp.getLocationCode().v(),
				outStamp == null ? null : outStamp.getStampSourceInfo().value,
				outingTime.getGoOut().getActualStamp().getTimeWithDay() == null ? null
						: outingTime.getGoOut().getActualStamp().getTimeWithDay().valueAsMinutes(),
				outingTime.getGoOut().getActualStamp().getAfterRoundingTime() == null ? null
						: outingTime.getGoOut().getActualStamp().getAfterRoundingTime().valueAsMinutes(),
				outingTime.getGoOut().getActualStamp().getLocationCode().v(),
				outingTime.getGoOut().getActualStamp().getStampSourceInfo().value,
				outingTime.getGoOut().getNumberOfReflectionStamp(),
				backStamp == null ? null : backStamp.getTimeWithDay() == null ? null
						: backStamp.getTimeWithDay().valueAsMinutes(),
				backStamp == null ? null : backStamp.getAfterRoundingTime() == null ? null
						: backStamp.getAfterRoundingTime().valueAsMinutes(),
				backStamp == null ? null : backStamp.getLocationCode().v(),
				backStamp == null ? null : backStamp.getStampSourceInfo().value,
				outingTime.getComeBack().getActualStamp().getTimeWithDay() == null ? null
						: outingTime.getComeBack().getActualStamp().getTimeWithDay().valueAsMinutes(),
				outingTime.getComeBack().getActualStamp().getAfterRoundingTime() == null ? null
						: outingTime.getComeBack().getActualStamp().getAfterRoundingTime().valueAsMinutes(),
				outingTime.getComeBack().getActualStamp().getLocationCode().v(),
				outingTime.getComeBack().getActualStamp().getStampSourceInfo().value,
				outingTime.getComeBack().getNumberOfReflectionStamp(),
				outingTime.getOutingTimeCalculation().valueAsMinutes(), outingTime.getOutingTime().valueAsMinutes(),
				outingTime.getReasonForGoOut().value);
	}
}
