package nts.uk.ctx.at.schedule.infra.repository.workschedules;

import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.schedule.dom.schedule.workschedule.WorkSchedule;
import nts.uk.ctx.at.schedule.dom.schedule.workschedule.WorkScheduleRepository;
import nts.uk.ctx.at.schedule.infra.entity.schedule.workschedule.KscdtSchAtdLvwTime;
import nts.uk.ctx.at.schedule.infra.entity.schedule.workschedule.KscdtSchBasicInfo;
import nts.uk.ctx.at.schedule.infra.entity.schedule.workschedule.KscdtSchBonusPay;
import nts.uk.ctx.at.schedule.infra.entity.schedule.workschedule.KscdtSchBreakTs;
import nts.uk.ctx.at.schedule.infra.entity.schedule.workschedule.KscdtSchEditState;
import nts.uk.ctx.at.schedule.infra.entity.schedule.workschedule.KscdtSchHolidayWork;
import nts.uk.ctx.at.schedule.infra.entity.schedule.workschedule.KscdtSchOvertimeWork;
import nts.uk.ctx.at.schedule.infra.entity.schedule.workschedule.KscdtSchPremium;
import nts.uk.ctx.at.schedule.infra.entity.schedule.workschedule.KscdtSchShortTime;
import nts.uk.ctx.at.schedule.infra.entity.schedule.workschedule.KscdtSchShortTimeTs;
import nts.uk.shr.com.context.AppContexts;

@Stateless
public class JpaWorkScheduleRepository extends JpaRepository implements WorkScheduleRepository {

	private static final String SELECT_BY_KEY = "SELECT c FROM KscdtSchBasicInfo c WHERE c.pk.CID = :CID AND c.pk.YMD = :YMD";

	@Override
	public Optional<WorkSchedule> get(String employeeID, GeneralDate ymd) {
		Optional<WorkSchedule> workSchedule = this.queryProxy().query(SELECT_BY_KEY, KscdtSchBasicInfo.class)
				.setParameter("SID", "employeeID").setParameter("YMD", "ymd")
				.getSingle(c -> c.toDomain(employeeID, ymd));
		return workSchedule;
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
				.setParameter("SID", "employeeID").setParameter("YMD", "ymd").getSingle(c -> c);

		if (oldData.isPresent()) {
			KscdtSchBasicInfo newData = KscdtSchBasicInfo.toEntity(workSchedule, cID);
			KscdtSchBasicInfo dataUpdate = oldData.get();
			dataUpdate.confirmedATR = newData.confirmedATR;
			dataUpdate.empCd = newData.empCd;
			dataUpdate.jobId = newData.jobId;
			dataUpdate.wkpId = newData.wkpId;
			dataUpdate.clsCd = newData.clsCd;
			dataUpdate.busTypeCd = newData.busTypeCd;
			dataUpdate.nurseLicense = newData.nurseLicense;
			dataUpdate.wktpCd = newData.wktpCd;
			dataUpdate.wktmCd = newData.wktmCd;
			dataUpdate.goStraightAtr = newData.goStraightAtr;
			dataUpdate.backStraightAtr = newData.backStraightAtr;

			// kscdtSchTime
			dataUpdate.kscdtSchTime.cid = newData.kscdtSchTime.cid;
			dataUpdate.kscdtSchTime.count = newData.kscdtSchTime.count;
			dataUpdate.kscdtSchTime.totalTime = newData.kscdtSchTime.totalTime;
			dataUpdate.kscdtSchTime.totalTimeAct = newData.kscdtSchTime.totalTimeAct;
			dataUpdate.kscdtSchTime.prsWorkTime = newData.kscdtSchTime.prsWorkTime;
			dataUpdate.kscdtSchTime.prsWorkTimeAct = newData.kscdtSchTime.prsWorkTimeAct;
			dataUpdate.kscdtSchTime.prsPrimeTime = newData.kscdtSchTime.prsPrimeTime;
			dataUpdate.kscdtSchTime.prsMidniteTime = newData.kscdtSchTime.prsMidniteTime;
			dataUpdate.kscdtSchTime.extBindTimeOtw = newData.kscdtSchTime.extBindTimeOtw;
			dataUpdate.kscdtSchTime.extBindTimeHw = newData.kscdtSchTime.extBindTimeHw;
			dataUpdate.kscdtSchTime.extVarwkOtwTimeLegal = newData.kscdtSchTime.extVarwkOtwTimeLegal;
			dataUpdate.kscdtSchTime.extFlexTime = newData.kscdtSchTime.extFlexTime;
			dataUpdate.kscdtSchTime.extFlexTimePreApp = newData.kscdtSchTime.extFlexTimePreApp;
			dataUpdate.kscdtSchTime.extMidNiteOtwTime = newData.kscdtSchTime.extMidNiteOtwTime;
			dataUpdate.kscdtSchTime.extMidNiteHdwTimeLghd = newData.kscdtSchTime.extMidNiteHdwTimeLghd;
			dataUpdate.kscdtSchTime.extMidNiteHdwTimeIlghd = newData.kscdtSchTime.extMidNiteHdwTimeIlghd;
			dataUpdate.kscdtSchTime.extMidNiteHdwTimePubhd = newData.kscdtSchTime.extMidNiteHdwTimePubhd;
			dataUpdate.kscdtSchTime.extMidNiteTotal = newData.kscdtSchTime.extMidNiteTotal;
			dataUpdate.kscdtSchTime.extMidNiteTotalPreApp = newData.kscdtSchTime.extMidNiteTotalPreApp;
			dataUpdate.kscdtSchTime.intervalAtdClock = newData.kscdtSchTime.intervalAtdClock;
			dataUpdate.kscdtSchTime.intervalTime = newData.kscdtSchTime.intervalTime;
			dataUpdate.kscdtSchTime.brkTotalTime = newData.kscdtSchTime.brkTotalTime;
			dataUpdate.kscdtSchTime.hdPaidTime = newData.kscdtSchTime.hdPaidTime;
			dataUpdate.kscdtSchTime.hdPaidHourlyTime = newData.kscdtSchTime.hdPaidHourlyTime;
			dataUpdate.kscdtSchTime.hdComTime = newData.kscdtSchTime.hdComTime;
			dataUpdate.kscdtSchTime.hdComHourlyTime = newData.kscdtSchTime.hdComHourlyTime;
			dataUpdate.kscdtSchTime.hd60hTime = newData.kscdtSchTime.hd60hTime;
			dataUpdate.kscdtSchTime.hd60hHourlyTime = newData.kscdtSchTime.hd60hHourlyTime;
			dataUpdate.kscdtSchTime.hdspTime = newData.kscdtSchTime.hdspTime;
			dataUpdate.kscdtSchTime.hdspHourlyTime = newData.kscdtSchTime.hdspHourlyTime;
			dataUpdate.kscdtSchTime.hdstkTime = newData.kscdtSchTime.hdstkTime;
			dataUpdate.kscdtSchTime.hdHourlyTime = newData.kscdtSchTime.hdHourlyTime;
			dataUpdate.kscdtSchTime.hdHourlyShortageTime = newData.kscdtSchTime.hdHourlyShortageTime;
			dataUpdate.kscdtSchTime.absenceTime = newData.kscdtSchTime.absenceTime;
			dataUpdate.kscdtSchTime.vacationAddTime = newData.kscdtSchTime.vacationAddTime;
			dataUpdate.kscdtSchTime.staggeredWhTime = newData.kscdtSchTime.staggeredWhTime;
			// List<KscdtSchOvertimeWork> overtimeWorks
			dataUpdate.kscdtSchTime.overtimeWorks.stream().forEach(x -> {
				newData.kscdtSchTime.overtimeWorks.stream().forEach(y -> {
					x.cid = y.cid;
					x.overtimeWorkTime = y.overtimeWorkTime;
					x.overtimeWorkTimeTrans = y.overtimeWorkTimeTrans;
					x.overtimeWorkTimePreApp = y.getOvertimeWorkTimePreApp();
				});
			});
			// List<KscdtSchHolidayWork> holidayWorks
			dataUpdate.kscdtSchTime.holidayWorks.stream().forEach(x -> {
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
			dataUpdate.kscdtSchTime.bonusPays.stream().forEach(x ->{
				newData.kscdtSchTime.bonusPays.stream().forEach(y -> {
					x.cid = y.cid;
					x.premiumTime = y.premiumTime;
					x.premiumTimeWithIn = y.premiumTimeWithIn;
					x.premiumTimeWithOut = y.getPremiumTimeWithOut();
				});
			});
			//List<KscdtSchPremium> premiums
			dataUpdate.kscdtSchTime.premiums.stream().forEach(x ->{
				newData.kscdtSchTime.premiums.stream().forEach( y -> {
					x.cid = y.cid;
					x.premiumTime = y.premiumTime;
				});
			});
			//List<KscdtSchShortTime> shortTimes
			dataUpdate.kscdtSchTime.shortTimes.stream().forEach( x -> {
				newData.kscdtSchTime.shortTimes.stream().forEach( y -> {
					x.cid = y.cid;
					x.count = y.count;
					x.totalTime = y.totalTime;
					x.totalTimeWithIn = y.totalTimeWithIn;
					x.totalTimeWithOut = y.getTotalTimeWithOut();
				});
			});
			
			//List<KscdtSchEditState> editStates;
			dataUpdate.editStates.stream().forEach(x -> {
					newData.editStates.stream().forEach(y ->{
						x.cid = y.cid;
						x.sditState = y.sditState;
					});
			});
			//List<KscdtSchAtdLvwTime> atdLvwTimes;
			dataUpdate.atdLvwTimes.stream().forEach(x ->{
				newData.atdLvwTimes.stream().forEach(y ->{
					x.cid = y.cid;
					x.atdClock = y.atdClock;
					x.lwkClock = y.lwkClock;
				});
			});
			//List<KscdtSchShortTimeTs> schShortTimeTs
			dataUpdate.schShortTimeTs.stream().forEach( x ->{
				newData.schShortTimeTs.stream().forEach(y ->{
					x.cid = y.cid;
					x.shortTimeTsStart = y.shortTimeTsStart;
					x.shortTimeTsEnd = y.shortTimeTsEnd;
				});
			});
			//List<KscdtSchBreakTs> breakTs;
			dataUpdate.breakTs.stream().forEach(x -> {
				newData.breakTs.stream().forEach(y ->{
					x.cid = y.cid;
					x.breakTsStart = y.breakTsStart;
					x.breakTsEnd = y.breakTsEnd;
				});
			});

			this.commandProxy().update(KscdtSchBasicInfo.toEntity(workSchedule, cID));
		}
	}
}
