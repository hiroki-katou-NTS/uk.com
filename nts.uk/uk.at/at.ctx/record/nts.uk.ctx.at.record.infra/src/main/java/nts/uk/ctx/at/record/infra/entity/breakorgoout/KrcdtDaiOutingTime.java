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
import nts.uk.shr.infra.data.entity.ContractUkJpaEntity;

/**
 * 
 * @author nampt 日別実績の外出時間帯
 *
 */
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "KRCDT_DAY_TS_GOOUT")
public class KrcdtDaiOutingTime extends ContractUkJpaEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	@EmbeddedId
	public KrcdtDaiOutingTimePK krcdtDaiOutingTimePK;

	@Column(name = "OUT_STAMP_TIME")
	public Integer outStampTime;

	@Column(name = "OUT_STAMP_PLACE_CODE")
	public String outStampPlaceCode;

	@Column(name = "OUT_STAMP_SOURCE_INFO")
	public Integer outStampSourceInfo;

	@Column(name = "BACK_STAMP_TIME")
	public Integer backStampTime;

	@Column(name = "BACK_STAMP_PLACE_CODE")
	public String backStampPlaceCode;

	@Column(name = "BACK_STAMP_SOURCE_INFO")
	public Integer backStampSourceInfo;

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
		WorkStamp outStamp = (outingTime.getGoOut() != null && outingTime.getGoOut().isPresent()) ? outingTime.getGoOut().get() : null;
		WorkStamp backStamp = (outingTime.getComeBack() != null && outingTime.getComeBack().isPresent()) ? outingTime.getComeBack().get() : null;
		
		return new KrcdtDaiOutingTime(new KrcdtDaiOutingTimePK(employeeId, date, outingTime.getOutingFrameNo().v()),
				outStamp == null ? null : !outStamp.getTimeDay().getTimeWithDay().isPresent() ? null : outStamp.getTimeDay().getTimeWithDay().get().valueAsMinutes(),
				outStamp == null || !outStamp.getLocationCode().isPresent() ? null : outStamp.getLocationCode().get().v(),
				outStamp == null ? null : outStamp.getTimeDay().getReasonTimeChange().getTimeChangeMeans().value,
						
				backStamp == null ? null : !backStamp.getTimeDay().getTimeWithDay().isPresent()? null
						: backStamp.getTimeDay().getTimeWithDay().get().valueAsMinutes(),
				backStamp == null || !backStamp.getLocationCode().isPresent() ? null : backStamp.getLocationCode().get().v(),
				backStamp == null ? null : backStamp.getTimeDay().getReasonTimeChange().getTimeChangeMeans().value,
						
				outingTime.getOutingTimeCalculation().valueAsMinutes(), 
				outingTime.getOutingTime().valueAsMinutes(),
				outingTime.getReasonForGoOut().value);
	}
}
