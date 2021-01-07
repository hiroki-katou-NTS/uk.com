package nts.uk.ctx.at.schedule.infra.repository.workschedules;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;

import lombok.val;
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

	private static final String WHERE_PK = "WHERE a.pk.sid = :sid AND a.pk.ymd >= :ymdStart AND a.pk.ymd <= :ymdEnd";

	private static final List<String> DELETE_TABLES = Arrays.asList(
			"DELETE FROM KscdtSchTime a ",
			"DELETE FROM KscdtSchOvertimeWork a ",
			"DELETE FROM KscdtSchHolidayWork a ",
			"DELETE FROM KscdtSchBonusPay a ",
			"DELETE FROM KscdtSchPremium a ",
			"DELETE FROM KscdtSchShortTime a ",
			"DELETE FROM KscdtSchBasicInfo a ",
			"DELETE FROM KscdtSchEditState a ",
			"DELETE FROM KscdtSchAtdLvwTime a ",
			"DELETE FROM KscdtSchShortTimeTs a ",
			"DELETE FROM KscdtSchBreakTs a ");

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
	
	@Override
	public void insertAll(String cID, List<WorkSchedule> workSchedules) {
		this.commandProxy().insertAll(workSchedules.stream().map(s -> this.toEntity(s, cID)).collect(Collectors.toList()));
	}

	public KscdtSchBasicInfo toEntity(WorkSchedule workSchedule, String cID) {
		return KscdtSchBasicInfo.toEntity(workSchedule, cID);
	}

	@Override
	public void delete(String sid, GeneralDate ymd) {
		Optional<WorkSchedule> optWorkSchedule = this.get(sid, ymd);
		if (optWorkSchedule.isPresent()) {
			KscdtSchBasicInfoPK pk = new KscdtSchBasicInfoPK(optWorkSchedule.get().getEmployeeID(),
					optWorkSchedule.get().getYmd());
			this.commandProxy().remove(KscdtSchBasicInfo.class, pk);
			this.getEntityManager().flush();
		}
	}

	@Override
	public void delete(String sid, DatePeriod datePeriod) {
		for (val deleteTable : DELETE_TABLES){
			this.getEntityManager().createQuery(deleteTable + WHERE_PK)
					.setParameter("sid", sid)
					.setParameter("ymdStart",datePeriod.start())
					.setParameter("ymdEnd", datePeriod.end())
					.executeUpdate();
		}
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
