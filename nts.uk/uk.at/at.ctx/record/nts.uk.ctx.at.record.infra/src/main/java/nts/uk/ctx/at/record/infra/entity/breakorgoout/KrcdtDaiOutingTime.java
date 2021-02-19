package nts.uk.ctx.at.record.infra.entity.breakorgoout;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.breakouting.OutingTimeSheet;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.common.TimeActualStamp;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.common.timestamp.WorkStamp;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

/**
 * 
 * @author nampt 日別実績の外出時間帯
 *
 */
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "KRCDT_DAI_OUTING_TIME_TS")
public class KrcdtDaiOutingTime extends UkJpaEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	@EmbeddedId
	public KrcdtDaiOutingTimePK krcdtDaiOutingTimePK;

	@Column(name = "OUT_STAMP_TIME")
	public Integer outStampTime;

	@Column(name = "OUT_STAMP_PLACE_CODE")
	public String outStampPlaceCode;

	@Column(name = "OUT_STAMP_SOURCE_INFO")
	public Integer outStampSourceInfo;

	@Column(name = "OUT_ACTUAL_TIME")
	public Integer outActualTime;

	@Column(name = "OUT_ACTUAL_PLACE_CODE")
	public String outActualPlaceCode;

	@Column(name = "OUT_ACTUAL_SOURCE_INFO")
	public Integer outActualSourceInfo;

	@Column(name = "OUT_NUMBER_STAMP")
	public Integer outNumberStamp;

	@Column(name = "BACK_STAMP_TIME")
	public Integer backStampTime;

	@Column(name = "BACK_STAMP_PLACE_CODE")
	public String backStampPlaceCode;

	@Column(name = "BACK_STAMP_SOURCE_INFO")
	public Integer backStampSourceInfo;

	@Column(name = "BACK_ACTUAL_TIME")
	public Integer backActualTime;

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
		TimeActualStamp gooutactualStamp = (outingTime.getGoOut() != null && outingTime.getGoOut().isPresent()) ? outingTime.getGoOut().get() : null;
		TimeActualStamp backactualStamp = (outingTime.getComeBack() != null && outingTime.getComeBack().isPresent()) ? outingTime.getComeBack().get() : null;
		WorkStamp outStamp = gooutactualStamp == null ? null : gooutactualStamp.getStamp().orElseGet(null);
		WorkStamp backStamp = backactualStamp == null ? null : backactualStamp.getStamp().orElseGet(null);
		WorkStamp outActual = gooutactualStamp == null ? null : gooutactualStamp.getActualStamp().orElse(null);
		WorkStamp backActual = backactualStamp == null ? null : backactualStamp.getActualStamp().orElse(null);
		return new KrcdtDaiOutingTime(new KrcdtDaiOutingTimePK(employeeId, date, outingTime.getOutingFrameNo().v()),
				outStamp == null ? null : !outStamp.getTimeDay().getTimeWithDay().isPresent() ? null : outStamp.getTimeDay().getTimeWithDay().get().valueAsMinutes(),
				outStamp == null || !outStamp.getLocationCode().isPresent() ? null : outStamp.getLocationCode().get().v(),
				outStamp == null ? null : outStamp.getTimeDay().getReasonTimeChange().getTimeChangeMeans().value,
				outActual == null || !outActual.getTimeDay().getTimeWithDay().isPresent()? null
						: outActual.getTimeDay().getTimeWithDay().get().valueAsMinutes(),
				outActual == null || !outActual.getLocationCode().isPresent() ? null :  outActual.getLocationCode().get().v(),
				outActual == null ? null : outActual.getTimeDay().getReasonTimeChange().getTimeChangeMeans().value,
					gooutactualStamp == null ? null : gooutactualStamp.getNumberOfReflectionStamp(),
				backStamp == null ? null : !backStamp.getTimeDay().getTimeWithDay().isPresent()? null
						: backStamp.getTimeDay().getTimeWithDay().get().valueAsMinutes(),
				backStamp == null || !backStamp.getLocationCode().isPresent() ? null : backStamp.getLocationCode().get().v(),
				backStamp == null ? null : backStamp.getTimeDay().getReasonTimeChange().getTimeChangeMeans().value,
				backActual == null || !backActual.getTimeDay().getTimeWithDay().isPresent()? null
						: backActual.getTimeDay().getTimeWithDay().get().valueAsMinutes(),
				backActual == null || !backActual.getLocationCode().isPresent() ? null :  backActual.getLocationCode().get().v(),
				backActual == null ? null : backActual.getTimeDay().getReasonTimeChange().getTimeChangeMeans().value,
				backactualStamp == null ? null : backactualStamp.getNumberOfReflectionStamp(),
				outingTime.getOutingTimeCalculation().valueAsMinutes(), outingTime.getOutingTime().valueAsMinutes(),
				outingTime.getReasonForGoOut().value);
	}
}
