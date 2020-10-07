package nts.uk.ctx.at.schedule.infra.repository.workschedules;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.arc.time.GeneralDate;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.schedule.dom.schedule.workschedule.WorkSchedule;
import nts.uk.ctx.at.schedule.dom.schedule.workschedule.WorkScheduleRepository;
import nts.uk.ctx.at.schedule.infra.entity.schedule.workschedule.KscdtSchAtdLvwTime;
import nts.uk.ctx.at.schedule.infra.entity.schedule.workschedule.KscdtSchAtdLvwTimePK;
import nts.uk.ctx.at.schedule.infra.entity.schedule.workschedule.KscdtSchBasicInfo;
import nts.uk.ctx.at.schedule.infra.entity.schedule.workschedule.KscdtSchBasicInfoPK;
import nts.uk.ctx.at.schedule.infra.entity.schedule.workschedule.KscdtSchBonusPay;
import nts.uk.ctx.at.schedule.infra.entity.schedule.workschedule.KscdtSchBreakTs;
import nts.uk.ctx.at.schedule.infra.entity.schedule.workschedule.KscdtSchEditState;
import nts.uk.ctx.at.schedule.infra.entity.schedule.workschedule.KscdtSchHolidayWork;
import nts.uk.ctx.at.schedule.infra.entity.schedule.workschedule.KscdtSchOvertimeWork;
import nts.uk.ctx.at.schedule.infra.entity.schedule.workschedule.KscdtSchPremium;
import nts.uk.ctx.at.schedule.infra.entity.schedule.workschedule.KscdtSchShortTime;
import nts.uk.ctx.at.schedule.infra.entity.schedule.workschedule.KscdtSchShortTimeTs;
import nts.uk.ctx.at.schedule.infra.entity.schedule.workschedule.KscdtSchShortTimeTsPK;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.attendancetime.TimeLeavingWork;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.shortworktime.ShortTimeOfDailyAttd;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.shortworktime.ShortWorkingTimeSheet;
import nts.uk.shr.com.context.AppContexts;

@Stateless
public class JpaWorkScheduleRepository extends JpaRepository implements WorkScheduleRepository {

	private static final String SELECT_BY_KEY = "SELECT c FROM KscdtSchBasicInfo c WHERE c.pk.sid = :employeeID AND c.pk.ymd = :ymd";

	private static final String SELECT_BY_LIST = "SELECT c FROM KscdtSchBasicInfo c WHERE c.pk.sid IN :sids AND (c.pk.ymd >= :startDate AND c.pk.ymd <= :endDate) ";

	private static final String SELECT_CHECK_UPDATE = "SELECT count (c) FROM KscdtSchBasicInfo c WHERE c.pk.sid = :employeeID AND c.pk.ymd = :ymd";

	@Override
	public Optional<WorkSchedule> get(String employeeID, GeneralDate ymd) {
		Optional<WorkSchedule> workSchedule = this.queryProxy().query(SELECT_BY_KEY, KscdtSchBasicInfo.class)
				.setParameter("employeeID", employeeID).setParameter("ymd", ymd)
				.getSingle(c -> c.toDomain(employeeID, ymd));
		return workSchedule;
	}

	@Override
	public List<WorkSchedule> getList(List<String> sids, DatePeriod period) {
		if (sids.isEmpty())
			return new ArrayList<>();

		List<WorkSchedule> result = this.queryProxy().query(SELECT_BY_LIST, KscdtSchBasicInfo.class)
				.setParameter("sids", sids).setParameter("startDate", period.start())
				.setParameter("endDate", period.end()).getList(c -> c.toDomain(c.pk.sid, c.pk.ymd));
		return result;
	}

	@Override
	public boolean checkExits(String employeeID, GeneralDate ymd) {
		return this.queryProxy().query(SELECT_CHECK_UPDATE, Long.class).setParameter("employeeID", employeeID)
				.setParameter("ymd", ymd).getSingle().get() > 0;
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
				.setParameter("employeeID", workSchedule.getEmployeeID()).setParameter("ymd", workSchedule.getYmd())
				.getSingle(c -> c);

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
			if (oldData.get().kscdtSchTime != null) {
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
			}

			if (oldData.get().kscdtSchTime != null) {
				// List<KscdtSchOvertimeWork> overtimeWorks
				if (!oldData.get().kscdtSchTime.overtimeWorks.isEmpty()) {
					for (KscdtSchOvertimeWork y : newData.kscdtSchTime.overtimeWorks) {
						oldData.get().kscdtSchTime.overtimeWorks.forEach(x -> {
							if (y.pk.frameNo == x.pk.frameNo) {
								x.cid = y.cid;
								x.overtimeWorkTime = y.overtimeWorkTime;
								x.overtimeWorkTimeTrans = y.overtimeWorkTimeTrans;
								x.overtimeWorkTimePreApp = y.getOvertimeWorkTimePreApp();
							}
						});
					}
				}

				// List<KscdtSchHolidayWork> holidayWorks
				if (!oldData.get().kscdtSchTime.holidayWorks.isEmpty()) {
					for (KscdtSchHolidayWork y : newData.kscdtSchTime.holidayWorks) {
						oldData.get().kscdtSchTime.holidayWorks.forEach(x -> {
							if (y.pk.frameNo == x.pk.frameNo) {
								x.cid = y.cid;
								x.holidayWorkTsStart = y.holidayWorkTsStart;
								x.holidayWorkTsEnd = y.holidayWorkTsEnd;
								x.holidayWorkTime = y.holidayWorkTime;
								x.holidayWorkTimeTrans = y.holidayWorkTimeTrans;
								x.holidayWorkTimePreApp = y.holidayWorkTimePreApp;
							}
						});
					}
				}

				// List<KscdtSchBonusPay> bonusPays
				if (!oldData.get().kscdtSchTime.bonusPays.isEmpty()) {
					for (KscdtSchBonusPay y : newData.kscdtSchTime.bonusPays) {
						oldData.get().kscdtSchTime.bonusPays.forEach(x -> {
							if (y.pK.frameNo == x.pK.frameNo && y.pK.bonuspayType == x.pK.bonuspayType) {
								x.cid = y.cid;
								x.premiumTime = y.premiumTime;
								x.premiumTimeWithIn = y.premiumTimeWithIn;
								x.premiumTimeWithOut = y.getPremiumTimeWithOut();
							}
						});
					}
				}

				// List<KscdtSchPremium> premiums
				if (!oldData.get().kscdtSchTime.premiums.isEmpty()) {
					for (KscdtSchPremium y : newData.kscdtSchTime.premiums) {
						oldData.get().kscdtSchTime.premiums.forEach(x -> {
							if (y.pk.frameNo == x.pk.frameNo) {
								x.cid = y.cid;
								x.premiumTime = y.premiumTime;
							}
						});
					}
				}

				// List<KscdtSchShortTime> shortTimes
				if (!oldData.get().kscdtSchTime.shortTimes.isEmpty()) {
					for (KscdtSchShortTime y : newData.kscdtSchTime.shortTimes) {
						oldData.get().kscdtSchTime.shortTimes.forEach(x -> {
							if (y.pk.childCareAtr == x.pk.childCareAtr) {
								x.cid = y.cid;
								x.count = y.count;
								x.totalTime = y.totalTime;
								x.totalTimeWithIn = y.totalTimeWithIn;
								x.totalTimeWithOut = y.getTotalTimeWithOut();
							}
						});
					}
				}
			}
			// List<KscdtSchEditState> editStates;
			if (!oldData.get().editStates.isEmpty()) {
				for (KscdtSchEditState y : newData.editStates) {
					oldData.get().editStates.forEach(x -> {
						if (y.pk.atdItemId == x.pk.atdItemId) {
							x.cid = y.cid;
							x.sditState = y.sditState;
						}
					});
				}
			}

			// List<KscdtSchAtdLvwTime> atdLvwTimes;
			if (!oldData.get().atdLvwTimes.isEmpty()) {
				for (KscdtSchAtdLvwTime y : newData.atdLvwTimes) {
					oldData.get().atdLvwTimes.forEach(x -> {
						// Update data
						if (y.pk.workNo == x.pk.workNo) {
							x.cid = y.cid;
							x.atdClock = y.atdClock;
							x.lwkClock = y.lwkClock;
						}
						// Insert work no 2 when old data just have work no 1
						if(y.pk.workNo == 2 && oldData.get().atdLvwTimes.size() < 2) {
							TimeLeavingWork leavingWork = workSchedule.getOptTimeLeaving().get()
									.getTimeLeavingWorks().stream().filter(z -> z.getWorkNo().v() == 2)
									.findFirst().get();
							this.insertAtdLvwTimes(leavingWork, workSchedule.getEmployeeID(), workSchedule.getYmd(), cID);
						}
						// Delete work no 2 when new data just have work no 1
						if(workSchedule.getOptTimeLeaving().get().getTimeLeavingWorks().size() < 2 && x.pk.workNo == 2) {
							String delete = "delete from KscdtSchAtdLvwTime o " + " where o.pk.sid = :sid "
									+ " and o.pk.ymd = :ymd " + " and o.pk.workNo = :workNo";
							this.getEntityManager().createQuery(delete).setParameter("sid", x.pk.sid)
									.setParameter("ymd", x.pk.ymd)
									.setParameter("workNo", x.pk.workNo).executeUpdate();
						}
					});
				}
			} else {
				// If old data is empty
				for (KscdtSchAtdLvwTime y : newData.atdLvwTimes) {
							TimeLeavingWork leavingWork = workSchedule.getOptTimeLeaving().get()
									.getTimeLeavingWorks().stream().filter(z -> z.getWorkNo().v() == y.getPk().getWorkNo())
									.findFirst().get();
							this.insertAtdLvwTimes(leavingWork, workSchedule.getEmployeeID(), workSchedule.getYmd(), cID);
					}
			}

			Optional<WorkSchedule> oldDatas = this.queryProxy().query(SELECT_BY_KEY, KscdtSchBasicInfo.class)
					.setParameter("employeeID", workSchedule.getEmployeeID()).setParameter("ymd", workSchedule.getYmd())
					.getSingle(c -> c.toDomain(workSchedule.getEmployeeID(), workSchedule.getYmd()));

			if (newData.schShortTimeTs.isEmpty()) {
				// if have not ShortWorkingTimeSheet delete all old data
				this.deleteAllShortTime(workSchedule.getEmployeeID(), workSchedule.getYmd());
				oldData.get().setSchShortTimeTs(newData.schShortTimeTs);
			} else {

			if (oldDatas.get().getOptSortTimeWork().get().getShortWorkingTimeSheets().isEmpty()
					&& workSchedule.getOptSortTimeWork().isPresent()) {
				if (!workSchedule.getOptSortTimeWork().get().getShortWorkingTimeSheets().isEmpty()) {
					for (ShortWorkingTimeSheet ts : workSchedule.getOptSortTimeWork().get()
							.getShortWorkingTimeSheets()) {
						this.insert(ts, workSchedule.getEmployeeID(), workSchedule.getYmd(), cID);
					}
				}
			} else {
				// List<KscdtSchShortTimeTs> schShortTimeTs
				for (KscdtSchShortTimeTs ts : newData.schShortTimeTs) {
					oldData.get().schShortTimeTs.forEach(x -> {
						if (x.pk.frameNo == ts.pk.frameNo && x.pk.childCareAtr == ts.pk.childCareAtr) {
							x.cid = ts.cid;
							x.shortTimeTsStart = ts.shortTimeTsStart;
							x.shortTimeTsEnd = ts.shortTimeTsEnd;
						}

						if (x.pk.frameNo == 2 && workSchedule.getOptSortTimeWork().get().getShortWorkingTimeSheets().size() < 2) {
							String delete = "delete from KscdtSchShortTimeTs o " + " where o.pk.sid = :sid "
									+ " and o.pk.ymd = :ymd " + " and o.pk.childCareAtr = :childCareAtr "
									+ "  and o.pk.frameNo = :frameNo";
							this.getEntityManager().createQuery(delete).setParameter("sid", x.pk.sid)
									.setParameter("ymd", x.pk.ymd).setParameter("childCareAtr", x.pk.childCareAtr)
									.setParameter("frameNo", x.pk.frameNo).executeUpdate();

						}
					});
					if (ts.pk.frameNo == 2 && oldData.get().schShortTimeTs.size() < 2) {
						ShortWorkingTimeSheet schShortTimeTs = workSchedule.getOptSortTimeWork().get()
								.getShortWorkingTimeSheets().stream().filter(x -> x.getShortWorkTimeFrameNo().v() == 2)
								.findFirst().get();
						this.insert(schShortTimeTs, workSchedule.getEmployeeID(), workSchedule.getYmd(), cID);
					}
				}
			}
		}
			// List<KscdtSchBreakTs> breakTs;
			if (!oldData.get().breakTs.isEmpty()) {
				for (KscdtSchBreakTs y : newData.breakTs) {
					oldData.get().breakTs.stream().forEach(x -> {
						if (y.pk.frameNo == x.pk.frameNo) {
							x.cid = y.cid;
							x.breakTsStart = y.breakTsStart;
							x.breakTsEnd = y.breakTsEnd;
						}
					});
				}
			}
			this.commandProxy().update(oldData.get());

		}
	}

	@Override
	public void delete(String sid, GeneralDate ymd) {
		Optional<WorkSchedule> optWorkSchedule = this.get(sid, ymd);
		if (optWorkSchedule.isPresent()) {
			KscdtSchBasicInfoPK pk = new KscdtSchBasicInfoPK(optWorkSchedule.get().getEmployeeID(),
					optWorkSchedule.get().getYmd());
			this.commandProxy().remove(KscdtSchBasicInfo.class, pk);
		}
	}

	@Override
	public void delete(String sid, DatePeriod datePeriod) {
		String delete = "delete from KscdtSchShortTimeTs o " + " where o.pk.sid = :sid "
				+ " and o.pk.ymd >= :ymdStart " + " and o.pk.ymd <= :ymdEnd ";
		this.getEntityManager().createQuery(delete).setParameter("sid", sid)
				.setParameter("ymdStart",datePeriod.start()).setParameter("ymdEnd", datePeriod.end()).executeUpdate();
	}

	@Override
	public void deleteAllShortTime(String sid, GeneralDate ymd) {
		Boolean optWorkShortTime = this.checkExitsShortTime(sid, ymd);
		if (optWorkShortTime) {
			KscdtSchShortTimeTsPK pk = new KscdtSchShortTimeTsPK(sid, ymd);
			this.commandProxy().remove(KscdtSchShortTimeTs.class, pk);
		}
	}
	
	@Override
	public void deleteSchAtdLvwTime(String sid, GeneralDate ymd, int workNo) {
			KscdtSchAtdLvwTimePK pk = new KscdtSchAtdLvwTimePK(sid, ymd, workNo);
			this.commandProxy().remove(KscdtSchAtdLvwTime.class, pk);
	}

	@Override
	public void insert(ShortWorkingTimeSheet shortWorkingTimeSheets, String sID, GeneralDate yMD, String cID) {
		this.commandProxy().insert(KscdtSchShortTimeTs.toEntity(shortWorkingTimeSheets, sID, yMD, cID));
	}
	
	@Override
	public void insertAtdLvwTimes(TimeLeavingWork leavingWork, String sID, GeneralDate yMD, String cID) {
		this.commandProxy().insert(KscdtSchAtdLvwTime.toEntity(leavingWork, sID, yMD, cID));
	}

	private static final String SELECT_BY_SHORTTIME_TS = "SELECT c FROM KscdtSchShortTimeTs c WHERE c.pk.sid = :employeeID AND c.pk.ymd = :ymd AND c.pk.childCareAtr = :childCareAtr AND c.pk.frameNo = :frameNo";

	@Override
	public Optional<ShortTimeOfDailyAttd> getShortTime(String sid, GeneralDate ymd, int childCareAtr, int frameNo) {
		Optional<ShortTimeOfDailyAttd> workSchedule = this.queryProxy()
				.query(SELECT_BY_SHORTTIME_TS, KscdtSchShortTimeTs.class).setParameter("employeeID", sid)
				.setParameter("ymd", ymd).setParameter("childCareAtr", childCareAtr).setParameter("frameNo", frameNo)
				.getSingle(c -> c.toDomain(sid, ymd, childCareAtr, frameNo));
		return workSchedule;
	}

	private static final String SELECT_ALL_SHORTTIME_TS = "SELECT count (c) FROM KscdtSchShortTimeTs c WHERE c.pk.sid = :employeeID AND c.pk.ymd = :ymd";

	@Override
	public boolean checkExitsShortTime(String employeeID, GeneralDate ymd) {
		return this.queryProxy().query(SELECT_ALL_SHORTTIME_TS, Long.class).setParameter("employeeID", employeeID)
				.setParameter("ymd", ymd).getSingle().get() > 0;
	}
}
