package nts.uk.ctx.at.record.infra.entity.worktime;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.arc.enums.EnumAdaptor;
import nts.uk.ctx.at.record.dom.worklocation.WorkLocationCD;
import nts.uk.ctx.at.record.dom.worktime.TimeActualStamp;
import nts.uk.ctx.at.record.dom.worktime.TimeLeavingWork;
import nts.uk.ctx.at.record.dom.worktime.WorkStamp;
import nts.uk.ctx.at.record.dom.worktime.enums.StampSourceInfo;
import nts.uk.ctx.at.record.dom.worktime.primitivevalue.WorkNo;
import nts.uk.ctx.at.record.infra.entity.workinformation.KrcdtDaiPerWorkInfo;
import nts.uk.shr.com.time.TimeWithDayAttr;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

/**
 * 
 * @author nampt 出退勤
 *
 */
@NoArgsConstructor
@Entity
@Table(name = "KRCDT_TIME_LEAVING_WORK")
public class KrcdtTimeLeavingWork extends UkJpaEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	@EmbeddedId
	public KrcdtTimeLeavingWorkPK krcdtTimeLeavingWorkPK;

	@Column(name = "ATD_ACTUAL_ROUDING_TIME_DAY")
	public BigDecimal attendanceActualRoudingTime;

	@Column(name = "ATD_ACTUAL_TIME")
	public BigDecimal attendanceActualTime;

	@Column(name = "ATD_ACTUAL_PLACE_CODE")
	public String attendanceActualPlaceCode;

	@Column(name = "ATD_ACTUAL_SOURCE_INFO")
	public BigDecimal attendanceActualSourceInfo;

	@Column(name = "ATD_STAMP_ROUDING_TIME_DAY")
	public BigDecimal attendanceStampRoudingTime;

	@Column(name = "ATD_STAMP_TIME")
	public BigDecimal attendanceStampTime;

	@Column(name = "ATD_STAMP_PLACE_CODE")
	public String attendanceStampPlaceCode;

	@Column(name = "ATD_STAMP_SOURCE_INFO")
	public BigDecimal attendanceStampSourceInfo;

	@Column(name = "ATD_NUMBER_STAMP")
	public BigDecimal attendanceNumberStamp;

	@Column(name = "LWK_ACTUAL_ROUDING_TIME_DAY")
	public BigDecimal leaveWorkActualRoundingTime;

	@Column(name = "LWK_ACTUAL_TIME")
	public BigDecimal leaveWorkActualTime;

	@Column(name = "LWK_ACTUAL_PLACE_CODE")
	public String leaveWorkActualPlaceCode;

	@Column(name = "LWK_ACTUAL_SOURCE_INFO")
	public BigDecimal leaveActualSourceInfo;

	@Column(name = "LWK_STAMP_ROUDING_TIME_DAY")
	public BigDecimal leaveWorkStampRoundingTime;

	@Column(name = "LWK_STAMP_TIME")
	public BigDecimal leaveWorkStampTime;

	@Column(name = "LWK_STAMP_PLACE_CODE")
	public String leaveWorkStampPlaceCode;

	@Column(name = "LWK_STAMP_SOURCE_INFO")
	public BigDecimal leaveWorkStampSourceInfo;

	@Column(name = "LWK_NUMBER_STAMP")
	public BigDecimal leaveWorkNumberStamp;

	public TimeLeavingWork toDomain() {
		TimeLeavingWork domain = new TimeLeavingWork(new WorkNo(this.krcdtTimeLeavingWorkPK.workNo),
				new TimeActualStamp(
						new WorkStamp(new TimeWithDayAttr(this.attendanceActualRoudingTime.intValue()), new TimeWithDayAttr(this.attendanceActualTime.intValue()), new WorkLocationCD(this.attendanceActualPlaceCode), EnumAdaptor.valueOf(this.attendanceActualSourceInfo.intValue(), StampSourceInfo.class)),
						new WorkStamp(new TimeWithDayAttr(this.attendanceStampRoudingTime.intValue()), new TimeWithDayAttr(this.attendanceStampTime.intValue()), new WorkLocationCD(this.attendanceStampPlaceCode), EnumAdaptor.valueOf(this.attendanceStampSourceInfo.intValue(), StampSourceInfo.class)),
						this.attendanceNumberStamp.intValue()),
				new TimeActualStamp(
						new WorkStamp(new TimeWithDayAttr(this.leaveWorkActualRoundingTime.intValue()), new TimeWithDayAttr(this.leaveWorkActualTime.intValue()), new WorkLocationCD(this.leaveWorkActualPlaceCode), EnumAdaptor.valueOf(this.leaveActualSourceInfo.intValue(), StampSourceInfo.class)),
						new WorkStamp(new TimeWithDayAttr(this.leaveWorkStampRoundingTime.intValue()), new TimeWithDayAttr(this.leaveWorkStampTime.intValue()), new WorkLocationCD(this.leaveWorkStampPlaceCode), EnumAdaptor.valueOf(this.leaveWorkStampSourceInfo.intValue(), StampSourceInfo.class)),
						this.leaveWorkNumberStamp.intValue()));
		return domain;
	}

	@ManyToOne
	@JoinColumns({ @JoinColumn(name = "SID", referencedColumnName = "SID", insertable = false, updatable = false),
			@JoinColumn(name = "YMD", referencedColumnName = "YMD", insertable = false, updatable = false) })
	public KrcdtDaiLeavingWork daiLeavingWork;
	

	@ManyToOne
	@JoinColumns({ @JoinColumn(name = "SID", referencedColumnName = "SID", insertable = false, updatable = false),
			@JoinColumn(name = "YMD", referencedColumnName = "YMD", insertable = false, updatable = false) })
	public KrcdtDaiTemporaryTime daiTemporaryTime;

	public KrcdtTimeLeavingWork(KrcdtTimeLeavingWorkPK krcdtTimeLeavingWorkPK, BigDecimal attendanceActualRoudingTime,
			BigDecimal attendanceActualTime, String attendanceActualPlaceCode, BigDecimal attendanceActualSourceInfo,
			BigDecimal attendanceStampRoudingTime, BigDecimal attendanceStampTime, String attendanceStampPlaceCode,
			BigDecimal attendanceStampSourceInfo, BigDecimal attendanceNumberStamp,
			BigDecimal leaveWorkActualRoundingTime, BigDecimal leaveWorkActualTime, String leaveWorkActualPlaceCode,
			BigDecimal leaveActualSourceInfo, BigDecimal leaveWorkStampRoundingTime, BigDecimal leaveWorkStampTime,
			String leaveWorkStampPlaceCode, BigDecimal leaveWorkStampSourceInfo, BigDecimal leaveWorkNumberStamp) {
		super();
		this.krcdtTimeLeavingWorkPK = krcdtTimeLeavingWorkPK;
		this.attendanceActualRoudingTime = attendanceActualRoudingTime;
		this.attendanceActualTime = attendanceActualTime;
		this.attendanceActualPlaceCode = attendanceActualPlaceCode;
		this.attendanceActualSourceInfo = attendanceActualSourceInfo;
		this.attendanceStampRoudingTime = attendanceStampRoudingTime;
		this.attendanceStampTime = attendanceStampTime;
		this.attendanceStampPlaceCode = attendanceStampPlaceCode;
		this.attendanceStampSourceInfo = attendanceStampSourceInfo;
		this.attendanceNumberStamp = attendanceNumberStamp;
		this.leaveWorkActualRoundingTime = leaveWorkActualRoundingTime;
		this.leaveWorkActualTime = leaveWorkActualTime;
		this.leaveWorkActualPlaceCode = leaveWorkActualPlaceCode;
		this.leaveActualSourceInfo = leaveActualSourceInfo;
		this.leaveWorkStampRoundingTime = leaveWorkStampRoundingTime;
		this.leaveWorkStampTime = leaveWorkStampTime;
		this.leaveWorkStampPlaceCode = leaveWorkStampPlaceCode;
		this.leaveWorkStampSourceInfo = leaveWorkStampSourceInfo;
		this.leaveWorkNumberStamp = leaveWorkNumberStamp;
	}
	
	public static List<TimeLeavingWork> toDomain(List<KrcdtTimeLeavingWork> krcdtTimeLeavingWorks){
		return krcdtTimeLeavingWorks.stream().map(f -> f.toDomain()).collect(Collectors.toList());
	};

	@Override
	protected Object getKey() {
		return this.krcdtTimeLeavingWorkPK;
	}

}
