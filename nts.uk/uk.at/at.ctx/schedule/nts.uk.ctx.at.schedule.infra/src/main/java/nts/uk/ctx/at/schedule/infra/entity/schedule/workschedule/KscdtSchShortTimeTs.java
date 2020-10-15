package nts.uk.ctx.at.schedule.infra.entity.schedule.workschedule;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.PrimaryKeyJoinColumns;
import javax.persistence.Table;

import lombok.Getter;
import lombok.NoArgsConstructor;
import nts.arc.enums.EnumAdaptor;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTimeOfExistMinus;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.shortworktime.ChildCareAttribute;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.shortworktime.ShortTimeOfDailyAttd;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.shortworktime.ShortWorkTimFrameNo;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.shortworktime.ShortWorkingTimeSheet;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.worktime.ActualWorkingTimeOfDaily;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.worktime.AttendanceTimeOfDailyAttendance;
import nts.uk.shr.com.time.TimeWithDayAttr;
import nts.uk.shr.infra.data.entity.ContractUkJpaEntity;

/**
 * 
 * @author HieuLt
 *
 */
@Entity
@NoArgsConstructor
@Table(name = "KSCDT_SCH_SHORTTIME_TS")
@Getter
public class KscdtSchShortTimeTs extends ContractUkJpaEntity {

	@EmbeddedId
	public KscdtSchShortTimeTsPK pk;

	/** 会社ID **/
	@Column(name = "CID")
	public String cid;
	/** 短時間勤務 開始時刻 **/
	@Column(name = "SHORTTIME_TS_START")
	public int shortTimeTsStart;

	@Column(name = "SHORTTIME_TS_END")
	public int shortTimeTsEnd;

	@ManyToOne(fetch = FetchType.LAZY)
	@PrimaryKeyJoinColumns({ @PrimaryKeyJoinColumn(name = "SID", referencedColumnName = "SID"),
			@PrimaryKeyJoinColumn(name = "YMD", referencedColumnName = "YMD") })
	public KscdtSchBasicInfo kscdtSchBasicInfo;
	
	public static KscdtSchShortTimeTs toEntity(ShortWorkingTimeSheet shortWorkingTimeSheets, String sID, GeneralDate yMD, String cID) {
		KscdtSchShortTimeTsPK pk = new KscdtSchShortTimeTsPK(sID, yMD, shortWorkingTimeSheets.getChildCareAttr().value, shortWorkingTimeSheets.getShortWorkTimeFrameNo().v());
		KscdtSchShortTimeTs kscdtSchShortTimeTs = new KscdtSchShortTimeTs(pk, cID, shortWorkingTimeSheets.getStartTime().v(), shortWorkingTimeSheets.getEndTime().v());
		return kscdtSchShortTimeTs;
	}

	@Override
	protected Object getKey() {
		return this.pk;
	}
	
	public ShortTimeOfDailyAttd toDomain(String sid, GeneralDate ymd, int childCareAtr, int frameNo) {
		
		ShortTimeOfDailyAttd optSortTimeWork = null;
		List<ShortWorkingTimeSheet> shortWorkingTimeSheets = new ArrayList<>();
			ShortWorkingTimeSheet shortWorkingTimeSheet = new ShortWorkingTimeSheet(new ShortWorkTimFrameNo(frameNo), EnumAdaptor.valueOf(childCareAtr, ChildCareAttribute.class), 
					new TimeWithDayAttr(this.shortTimeTsStart), new TimeWithDayAttr(this.shortTimeTsEnd));
			shortWorkingTimeSheets.add(shortWorkingTimeSheet);
			
		optSortTimeWork = new ShortTimeOfDailyAttd(shortWorkingTimeSheets);
		
		return optSortTimeWork;
	}

	public KscdtSchShortTimeTs(KscdtSchShortTimeTsPK pk, String cid, int shortTimeTsStart, int shortTimeTsEnd) {
		super();
		this.pk = pk;
		this.cid = cid;
		this.shortTimeTsStart = shortTimeTsStart;
		this.shortTimeTsEnd = shortTimeTsEnd;
	}
}
