package nts.uk.ctx.at.schedule.infra.entity.schedule.workschedule;

import java.util.Optional;

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
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTime;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.TimevacationUseTimeOfDaily;
import nts.uk.ctx.at.shared.dom.worktype.specialholidayframe.SpecialHdFrameNo;
import nts.uk.shr.infra.data.entity.ContractUkJpaEntity;

/**
 * 
 * @author tutk
 *
 */
@Entity
@NoArgsConstructor
@Table(name="KSCDT_SCH_COME_LATE")
@Getter
public class KscdtSchComeLate extends ContractUkJpaEntity {
	
	@EmbeddedId
	public KscdtSchComeLatePK pk;

	/** 会社ID **/
	@Column(name = "CID")
	public String cid;
	
	/** 時間年休使用時間 **/
	@Column(name = "USE_HOURLY_HD_PAID")
	public int useHourlyHdPaid;
	
	/** 時間代休使用時間 */
	@Column(name = "USE_HOURLY_HD_COM")
	public int useHourlyHdCom;
	
	/** 超過有休使用時間 */
	@Column(name = "USE_HOURLY_HD_60H")
	public int useHourlyHd60h;
	
	/** 特別休暇枠NO**/
	@Column(name = "USE_HOURLY_HD_SP_NO")
	public Integer useHourlyHdSpNO;
	
	/** 特別休暇使用時間 */
	@Column(name = "USE_HOURLY_HD_SP_TIME")
	public int useHourlyHdSpTime;
	
	/** 子の看護休暇使用時間 */
	@Column(name = "USE_HOURLY_HD_CHILDCARE")
	public int useHourlyHdChildCare;
	
	/** 介護休暇使用時間 */
	@Column(name = "USE_HOURLY_HD_NURSECARE")
	public int useHourlyHdNurseCare;
	
	
	@ManyToOne(fetch = FetchType.LAZY)
	@PrimaryKeyJoinColumns({ @PrimaryKeyJoinColumn(name = "SID", referencedColumnName = "SID"),
			@PrimaryKeyJoinColumn(name = "YMD", referencedColumnName = "YMD") })
	public KscdtSchTime kscdtSchTime;


	@Override
	protected Object getKey() {
		return pk;
	}


	public KscdtSchComeLate(KscdtSchComeLatePK pk, String cid, int useHourlyHdPaid, int useHourlyHdCom,
			int useHourlyHd60h, Integer useHourlyHdSpNO, int useHourlyHdSpTime, int useHourlyHdChildCare,
			int useHourlyHdNurseCare) {
		super();
		this.pk = pk;
		this.cid = cid;
		this.useHourlyHdPaid = useHourlyHdPaid;
		this.useHourlyHdCom = useHourlyHdCom;
		this.useHourlyHd60h = useHourlyHd60h;
		this.useHourlyHdSpNO = useHourlyHdSpNO;
		this.useHourlyHdSpTime = useHourlyHdSpTime;
		this.useHourlyHdChildCare = useHourlyHdChildCare;
		this.useHourlyHdNurseCare = useHourlyHdNurseCare;
	}
	
	public static KscdtSchComeLate toEntity(String sid, GeneralDate ymd,String cid,int workNo,TimevacationUseTimeOfDaily timePaidUseTime) {
		KscdtSchComeLate entity =  new KscdtSchComeLate(
				new KscdtSchComeLatePK(sid, ymd, workNo), 
				cid, 
				timePaidUseTime.getTimeAnnualLeaveUseTime().v(),
				timePaidUseTime.getTimeCompensatoryLeaveUseTime().v(),
				timePaidUseTime.getSixtyHourExcessHolidayUseTime().v(),
				timePaidUseTime.getSpecialHolidayFrameNo().isPresent()? timePaidUseTime.getSpecialHolidayFrameNo().get().v():null,
				timePaidUseTime.getTimeSpecialHolidayUseTime().v(),
				timePaidUseTime.getTimeChildCareHolidayUseTime().v(),
				timePaidUseTime.getTimeCareHolidayUseTime().v());
		return entity;
	}
	
	public TimevacationUseTimeOfDaily toDomain() {
		TimevacationUseTimeOfDaily domain = new TimevacationUseTimeOfDaily(
				new AttendanceTime(this.useHourlyHdPaid), 
				new AttendanceTime(this.useHourlyHdCom), 
				new AttendanceTime(this.useHourlyHd60h),
				new AttendanceTime(this.useHourlyHdSpTime), 
				this.useHourlyHdSpNO!= null ? Optional.of(new SpecialHdFrameNo(this.useHourlyHdSpNO)):Optional.empty(), 
				new AttendanceTime(this.useHourlyHdChildCare), 
				new AttendanceTime(this.useHourlyHdNurseCare));
			return domain;
	}
	
}
