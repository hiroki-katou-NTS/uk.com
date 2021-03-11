package nts.uk.ctx.at.record.infra.repository.daily.ouen;

import java.sql.Date;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;

import lombok.SneakyThrows;
import lombok.val;
import nts.arc.layer.infra.data.DbConsts;
import nts.arc.layer.infra.data.JpaRepository;
import nts.arc.layer.infra.data.jdbc.NtsResultSet;
import nts.arc.layer.infra.data.jdbc.NtsStatement;
import nts.arc.layer.infra.data.jdbc.NtsResultSet.NtsResultRecord;
import nts.arc.time.GeneralDate;
import nts.arc.time.calendar.period.DatePeriod;
import nts.gul.collection.CollectionUtil;
import nts.uk.ctx.at.record.dom.daily.ouen.OuenWorkTimeOfDaily;
import nts.uk.ctx.at.record.dom.daily.ouen.OuenWorkTimeOfDailyRepo;
import nts.uk.ctx.at.record.infra.entity.daily.ouen.KrcdtDayOuenTime;
import nts.uk.ctx.at.shared.dom.common.amount.AttendanceAmountDaily;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTime;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.timesheet.ouen.MedicalCareTimeEachTimeSheet;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.timesheet.ouen.OuenAttendanceTimeEachTimeSheet;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.timesheet.ouen.SupportFrameNo;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.timesheet.ouen.OuenMovementTimeEachTimeSheet;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.timesheet.ouen.OuenWorkTimeOfDailyAttendance;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.timesheet.ouen.MedicalCareTimeEachTimeSheet.FullTimeNightShiftAttr;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.personcostcalc.premiumitem.WorkingHoursUnitPrice;

@Stateless
public class OuenWorkTimeOfDailyRepoImpl extends JpaRepository implements OuenWorkTimeOfDailyRepo {

	@Override
	public Optional<OuenWorkTimeOfDaily> find(String empId, GeneralDate ymd) {
		 List<OuenWorkTimeOfDailyAttendance> attd = queryProxy()
			.query("SELECT o FROM KrcdtDayOuenTime o WHERE o.pk.sid = :sid AND o.pk.ymd = :ymd", KrcdtDayOuenTime.class)
			.setParameter("sid", empId).setParameter("ymd", ymd)
			.getList(e -> e.domain());
		
		if(attd.isEmpty()) {
			 return Optional.empty();
		}
		return Optional.of(OuenWorkTimeOfDaily.create(empId, ymd, attd));
	}
	
	@Override
	public List<OuenWorkTimeOfDaily> find(List<String> employeeIds, DatePeriod datePeriod) {
		List<OuenWorkTimeOfDaily> ouens = new ArrayList<>();
		CollectionUtil.split(employeeIds, DbConsts.MAX_CONDITIONS_OF_IN_STATEMENT, empIds -> {
			ouens.addAll(internalQuery(datePeriod, empIds));
		});
		return ouens;
	}
	
	@SneakyThrows
	private List<OuenWorkTimeOfDaily> internalQuery(DatePeriod datePeriod, List<String> subList) {
		String subIn = NtsStatement.In.createParamsString(subList);

		Map<String, Map<GeneralDate, List<OuenWorkTimeOfDailyAttendance>>> scheTimes = new HashMap<>();
		try (val stmt = this.connection().prepareStatement("SELECT * FROM KRCDT_DAY_TIME_SUP WHERE YMD >= ? AND YMD <= ? AND SID IN (" + subIn + ")")){
			stmt.setDate(1, Date.valueOf(datePeriod.start().localDate()));
			stmt.setDate(2, Date.valueOf(datePeriod.end().localDate()));
			for (int i = 0; i < subList.size(); i++) {
				stmt.setString(i + 3, subList.get(i));
			}
			new NtsResultSet(stmt.executeQuery()).getList(c -> {
				String sid = c.getString("SID");
				GeneralDate ymd = c.getGeneralDate("YMD");
				if(!scheTimes.containsKey(sid)){
					scheTimes.put(sid, new HashMap<>());
				}
				if(!scheTimes.get(sid).containsKey(ymd)) {
					scheTimes.get(sid).put(ymd, new ArrayList<>());
				}
				getCurrent(scheTimes, sid, ymd).add(toDomain(c));
				return null;
			});
			return scheTimes.keySet().stream()
					.map(emp -> scheTimes.get(emp).entrySet().stream()
								.map(set -> OuenWorkTimeOfDaily.create(emp, set.getKey(), set.getValue()))
								.collect(Collectors.toList()))
					.flatMap(List::stream)
					.collect(Collectors.toList());
		}
	}
	
	private <T> List<T> getCurrent(Map<String, Map<GeneralDate, List<T>>> scheTimes,
			String sid, GeneralDate ymd) {
		if(scheTimes.containsKey(sid)){
			if(scheTimes.get(sid).containsKey(ymd)){
				return scheTimes.get(sid).get(ymd);
			}
		}
		return new ArrayList<>();
	}
	
	private OuenWorkTimeOfDailyAttendance toDomain(NtsResultRecord r) {
		
		List<MedicalCareTimeEachTimeSheet> medicalTimes = new ArrayList<>();
		medicalTimes.add(MedicalCareTimeEachTimeSheet.create(
				FullTimeNightShiftAttr.DAY_SHIFT,
				new AttendanceTime(r.getInt("NORMAL_WORK_TIME")), 
				new AttendanceTime(r.getInt("NORMAL_BREAK_TIME")), 
				new AttendanceTime(r.getInt("NORMAL_DEDUCTION_TIME"))));
		medicalTimes.add(MedicalCareTimeEachTimeSheet.create(
				FullTimeNightShiftAttr.NIGHT_SHIFT,
				new AttendanceTime(r.getInt("NIGHT_WORK_TIME")), 
				new AttendanceTime(r.getInt("NIGHT_BREAK_TIME")), 
				new AttendanceTime(r.getInt("NIGHT_DEDUCTION_TIME"))));
		
		return OuenWorkTimeOfDailyAttendance.create(new SupportFrameNo(r.getInt("SUP_NO")), 
				OuenAttendanceTimeEachTimeSheet.create(
						new AttendanceTime(r.getInt("TOTAL_TIME")), 
						new AttendanceTime(r.getInt("BREAK_TIME")), 
						new AttendanceTime(r.getInt("WITHIN_TIME")),
						medicalTimes, 
						KrcdtDayOuenTime.premiumTime(r.getInt("PREMIUM_TIME1"), r.getInt("PREMIUM_TIME2"), r.getInt("PREMIUM_TIME3"), r.getInt("PREMIUM_TIME4"), r.getInt("PREMIUM_TIME5"), 
								r.getInt("PREMIUM_TIME6"), r.getInt("PREMIUM_TIME7"), r.getInt("PREMIUM_TIME8"), r.getInt("PREMIUM_TIME9"), r.getInt("PREMIUM_TIME10"),
								r.getInt("PREMIUM_AMOUNT1"), r.getInt("PREMIUM_AMOUNT2"), r.getInt("PREMIUM_AMOUNT3"), r.getInt("PREMIUM_AMOUNT4"), r.getInt("PREMIUM_AMOUNT5"),
								r.getInt("PREMIUM_AMOUNT6"), r.getInt("PREMIUM_AMOUNT7"), r.getInt("PREMIUM_AMOUNT8"), r.getInt("PREMIUM_AMOUNT9"), r.getInt("PREMIUM_AMOUNT10"))),
				OuenMovementTimeEachTimeSheet.create(
						new AttendanceTime(r.getInt("MOVE_TOTAL_TIME")), 
						new AttendanceTime(r.getInt("MOVE_BREAK_TIME")), 
						new AttendanceTime(r.getInt("MOVE_WITHIN_TIME")), 
						KrcdtDayOuenTime.premiumTime(r.getInt("MOVE_PREMIUM_TIME1"), r.getInt("MOVE_PREMIUM_TIME2"), r.getInt("MOVE_PREMIUM_TIME3"), r.getInt("MOVE_PREMIUM_TIME4"), r.getInt("MOVE_PREMIUM_TIME5"), 
								r.getInt("MOVE_PREMIUM_TIME6"), r.getInt("MOVE_PREMIUM_TIME7"), r.getInt("MOVE_PREMIUM_TIME8"), r.getInt("MOVE_PREMIUM_TIME9"), r.getInt("MOVE_PREMIUM_TIME10"),
								0, 0, 0, 0, 0, 0, 0, 0, 0, 0)),
				new AttendanceAmountDaily(r.getInt("AMOUNT")), 
				new WorkingHoursUnitPrice(r.getInt("PRICE_UNIT")));
	}

	@Override
	public void update(OuenWorkTimeOfDaily domain) {
		KrcdtDayOuenTime.convert(domain).forEach(e -> {
			commandProxy().update(e);
		});
	}

	@Override
	public void insert(OuenWorkTimeOfDaily domain) {
		KrcdtDayOuenTime.convert(domain).forEach(e -> {
			commandProxy().insert(e);
		});
	}

	@Override
	public void delete(OuenWorkTimeOfDaily domain) {
		KrcdtDayOuenTime.convert(domain).forEach(e -> {
			commandProxy().remove(e);
		});
	}

}
