package nts.uk.ctx.at.schedule.infra.repository.workschedules;

import java.util.Optional;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.arc.time.GeneralDate;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.schedule.dom.schedule.workschedule.WorkSchedule;
import nts.uk.ctx.at.schedule.dom.schedule.workschedule.WorkScheduleRepository;
import nts.uk.ctx.at.schedule.infra.entity.schedule.workschedule.KscdtSchBasicInfo;
import nts.uk.ctx.at.schedule.infra.entity.schedule.workschedule.KscdtSchBasicInfoPK;
import nts.uk.ctx.at.schedule.infra.entity.schedule.workschedule.KscdtSchShortTimeTs;
import nts.uk.shr.com.context.AppContexts;

@Stateless
public class JpaWorkScheduleRepository extends JpaRepository implements WorkScheduleRepository {

	private static final String SELECT_BY_KEY = "SELECT c FROM KscdtSchBasicInfo c WHERE c.pk.sid = :employeeID AND c.pk.ymd = :ymd";
	
	private static final String SELECT_CHECK_UPDATE = "SELECT count (c) FROM KscdtSchBasicInfo c WHERE c.pk.sid = :employeeID AND c.pk.ymd = :ymd";

	@Override
	public Optional<WorkSchedule> get(String employeeID, GeneralDate ymd) {
		Optional<WorkSchedule> workSchedule = this.queryProxy().query(SELECT_BY_KEY, KscdtSchBasicInfo.class)
				.setParameter("employeeID", employeeID).setParameter("ymd", ymd)
				.getSingle(c -> c.toDomain(employeeID, ymd));
		return workSchedule;
	}
	
	@Override
	public boolean checkExits(String employeeID, GeneralDate ymd) {
		return this.queryProxy().query(SELECT_CHECK_UPDATE, Long.class)
				.setParameter("employeeID", employeeID).setParameter("ymd", ymd)
				.getSingle().get() > 0;
	}

	@Override
	public void insert(WorkSchedule workSchedule) {
		String cID = AppContexts.user().companyId();
		this.commandProxy().insert(this.toEntity(workSchedule, cID));
	}

	public KscdtSchBasicInfo toEntity(WorkSchedule workSchedule, String cID) {
		return KscdtSchBasicInfo.toEntity(workSchedule, cID);
	}

	@Override
	public void update(WorkSchedule workSchedule) {
		String cID = AppContexts.user().companyId();
		Optional<KscdtSchBasicInfo> oldData = this.queryProxy().query(SELECT_BY_KEY, KscdtSchBasicInfo.class)
				.setParameter("employeeID", workSchedule.getEmployeeID()).setParameter("ymd", workSchedule.getYmd()).getSingle(c -> c);

		if (oldData.isPresent()) {
			KscdtSchBasicInfo newData = KscdtSchBasicInfo.toEntity(workSchedule, cID);
			oldData.get().confirmedATR = newData.confirmedATR;
			oldData.get().empCd = newData.empCd;
			oldData.get().jobId = newData.jobId;
			oldData.get().wkpId = newData.wkpId;
			oldData.get().clsCd = newData.clsCd;
			oldData.get().busTypeCd = newData.busTypeCd;
			oldData.get().nurseLicense = newData.nurseLicense;
			oldData.get().wktpCd = newData.wktpCd;
			oldData.get().wktmCd = newData.wktmCd;
			oldData.get().goStraightAtr = newData.goStraightAtr;
			oldData.get().backStraightAtr = newData.backStraightAtr;

			// kscdtSchTime
			oldData.get().kscdtSchTime.cid = newData.kscdtSchTime.cid;
			oldData.get().kscdtSchTime.count = newData.kscdtSchTime.count;
			oldData.get().kscdtSchTime.totalTime = newData.kscdtSchTime.totalTime;
			oldData.get().kscdtSchTime.totalTimeAct = newData.kscdtSchTime.totalTimeAct;
			oldData.get().kscdtSchTime.prsWorkTime = newData.kscdtSchTime.prsWorkTime;
			oldData.get().kscdtSchTime.prsWorkTimeAct = newData.kscdtSchTime.prsWorkTimeAct;
			oldData.get().kscdtSchTime.prsPrimeTime = newData.kscdtSchTime.prsPrimeTime;
			oldData.get().kscdtSchTime.prsMidniteTime = newData.kscdtSchTime.prsMidniteTime;
			oldData.get().kscdtSchTime.extBindTimeOtw = newData.kscdtSchTime.extBindTimeOtw;
			oldData.get().kscdtSchTime.extBindTimeHw = newData.kscdtSchTime.extBindTimeHw;
			oldData.get().kscdtSchTime.extVarwkOtwTimeLegal = newData.kscdtSchTime.extVarwkOtwTimeLegal;
			oldData.get().kscdtSchTime.extFlexTime = newData.kscdtSchTime.extFlexTime;
			oldData.get().kscdtSchTime.extFlexTimePreApp = newData.kscdtSchTime.extFlexTimePreApp;
			oldData.get().kscdtSchTime.extMidNiteOtwTime = newData.kscdtSchTime.extMidNiteOtwTime;
			oldData.get().kscdtSchTime.extMidNiteHdwTimeLghd = newData.kscdtSchTime.extMidNiteHdwTimeLghd;
			oldData.get().kscdtSchTime.extMidNiteHdwTimeIlghd = newData.kscdtSchTime.extMidNiteHdwTimeIlghd;
			oldData.get().kscdtSchTime.extMidNiteHdwTimePubhd = newData.kscdtSchTime.extMidNiteHdwTimePubhd;
			oldData.get().kscdtSchTime.extMidNiteTotal = newData.kscdtSchTime.extMidNiteTotal;
			oldData.get().kscdtSchTime.extMidNiteTotalPreApp = newData.kscdtSchTime.extMidNiteTotalPreApp;
			oldData.get().kscdtSchTime.intervalAtdClock = newData.kscdtSchTime.intervalAtdClock;
			oldData.get().kscdtSchTime.intervalTime = newData.kscdtSchTime.intervalTime;
			oldData.get().kscdtSchTime.brkTotalTime = newData.kscdtSchTime.brkTotalTime;
			oldData.get().kscdtSchTime.hdPaidTime = newData.kscdtSchTime.hdPaidTime;
			oldData.get().kscdtSchTime.hdPaidHourlyTime = newData.kscdtSchTime.hdPaidHourlyTime;
			oldData.get().kscdtSchTime.hdComTime = newData.kscdtSchTime.hdComTime;
			oldData.get().kscdtSchTime.hdComHourlyTime = newData.kscdtSchTime.hdComHourlyTime;
			oldData.get().kscdtSchTime.hd60hTime = newData.kscdtSchTime.hd60hTime;
			oldData.get().kscdtSchTime.hd60hHourlyTime = newData.kscdtSchTime.hd60hHourlyTime;
			oldData.get().kscdtSchTime.hdspTime = newData.kscdtSchTime.hdspTime;
			oldData.get().kscdtSchTime.hdspHourlyTime = newData.kscdtSchTime.hdspHourlyTime;
			oldData.get().kscdtSchTime.hdstkTime = newData.kscdtSchTime.hdstkTime;
			oldData.get().kscdtSchTime.hdHourlyTime = newData.kscdtSchTime.hdHourlyTime;
			oldData.get().kscdtSchTime.hdHourlyShortageTime = newData.kscdtSchTime.hdHourlyShortageTime;
			oldData.get().kscdtSchTime.absenceTime = newData.kscdtSchTime.absenceTime;
			oldData.get().kscdtSchTime.vacationAddTime = newData.kscdtSchTime.vacationAddTime;
			oldData.get().kscdtSchTime.staggeredWhTime = newData.kscdtSchTime.staggeredWhTime;
			// List<KscdtSchOvertimeWork> overtimeWorks
			oldData.get().kscdtSchTime.overtimeWorks.stream().forEach(x -> {
				newData.kscdtSchTime.overtimeWorks.stream().forEach(y -> {
					x.cid = y.cid;
					x.overtimeWorkTime = y.overtimeWorkTime;
					x.overtimeWorkTimeTrans = y.overtimeWorkTimeTrans;
					x.overtimeWorkTimePreApp = y.getOvertimeWorkTimePreApp();
				});
			});
			// List<KscdtSchHolidayWork> holidayWorks
			oldData.get().kscdtSchTime.holidayWorks.stream().forEach(x -> {
				newData.kscdtSchTime.holidayWorks.stream().forEach(y -> {
					x.cid = y.cid;
					x.holidayWorkTsStart = y.holidayWorkTsStart;
					x.holidayWorkTsEnd = y.holidayWorkTsEnd;
					x.holidayWorkTime = y.holidayWorkTime;
					x.holidayWorkTimeTrans = y.holidayWorkTimeTrans;
					x.holidayWorkTimePreApp = y.holidayWorkTimePreApp;
				});
			});
			// List<KscdtSchBonusPay> bonusPays
			oldData.get().kscdtSchTime.bonusPays.stream().forEach(x ->{
				newData.kscdtSchTime.bonusPays.stream().forEach(y -> {
					x.cid = y.cid;
					x.premiumTime = y.premiumTime;
					x.premiumTimeWithIn = y.premiumTimeWithIn;
					x.premiumTimeWithOut = y.getPremiumTimeWithOut();
				});
			});
			//List<KscdtSchPremium> premiums
			oldData.get().kscdtSchTime.premiums.stream().forEach(x ->{
				newData.kscdtSchTime.premiums.stream().forEach( y -> {
					x.cid = y.cid;
					x.premiumTime = y.premiumTime;
				});
			});
			//List<KscdtSchShortTime> shortTimes
			oldData.get().kscdtSchTime.shortTimes.stream().forEach( x -> {
				newData.kscdtSchTime.shortTimes.stream().forEach( y -> {
					x.cid = y.cid;
					x.count = y.count;
					x.totalTime = y.totalTime;
					x.totalTimeWithIn = y.totalTimeWithIn;
					x.totalTimeWithOut = y.getTotalTimeWithOut();
				});
			});
			
			//List<KscdtSchEditState> editStates;
			oldData.get().editStates.stream().forEach(x -> {
					newData.editStates.stream().forEach(y ->{
						x.cid = y.cid;
						x.sditState = y.sditState;
					});
			});
			//List<KscdtSchAtdLvwTime> atdLvwTimes;
			oldData.get().atdLvwTimes.stream().forEach(x ->{
				newData.atdLvwTimes.stream().forEach(y ->{
					x.cid = y.cid;
					x.atdClock = y.atdClock;
					x.lwkClock = y.lwkClock;
				});
			});
			//List<KscdtSchShortTimeTs> schShortTimeTs
			for(KscdtSchShortTimeTs ts : newData.schShortTimeTs) {
				oldData.get().schShortTimeTs.forEach(x->{
					if(ts.pk.frameNo == x.pk.frameNo) {
						x.cid = ts.cid;
						x.shortTimeTsStart = ts.shortTimeTsStart;
						x.shortTimeTsEnd = ts.shortTimeTsEnd;
					}
				});
			}
			//List<KscdtSchBreakTs> breakTs;
			oldData.get().breakTs.stream().forEach(x -> {
				newData.breakTs.stream().forEach(y ->{
					x.cid = y.cid;
					x.breakTsStart = y.breakTsStart;
					x.breakTsEnd = y.breakTsEnd;
				});
			});

			this.commandProxy().update(oldData.get());
		}
	}

	@Override
	public void delete(String sid, GeneralDate ymd) {
		Optional<WorkSchedule> optWorkSchedule = this.get(sid, ymd);
		if(optWorkSchedule.isPresent()){
			KscdtSchBasicInfoPK pk = new KscdtSchBasicInfoPK(optWorkSchedule.get().getEmployeeID(), optWorkSchedule.get().getYmd());
			this.commandProxy().remove(KscdtSchBasicInfo.class, pk);
		}	
	}
	
	@Override
	public void delete(String sid, DatePeriod datePeriod) {
		datePeriod.forEach(ymd->{
			Optional<WorkSchedule> optWorkSchedule = this.get(sid, ymd);
			if(optWorkSchedule.isPresent()){
				KscdtSchBasicInfoPK pk = new KscdtSchBasicInfoPK(optWorkSchedule.get().getEmployeeID(), optWorkSchedule.get().getYmd());
				this.commandProxy().remove(KscdtSchBasicInfo.class, pk);
			}
		});
	}

}
