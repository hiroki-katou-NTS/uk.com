package nts.uk.ctx.at.record.infra.repository.breakorgoout;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;

import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.infra.data.JpaRepository;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.record.dom.breakorgoout.OutingTimeOfDailyPerformance;
import nts.uk.ctx.at.record.dom.breakorgoout.OutingTimeSheet;
import nts.uk.ctx.at.record.dom.breakorgoout.enums.GoingOutReason;
import nts.uk.ctx.at.record.dom.breakorgoout.primitivevalue.OutingFrameNo;
import nts.uk.ctx.at.record.dom.breakorgoout.repository.OutingTimeOfDailyPerformanceRepository;
import nts.uk.ctx.at.record.dom.worklocation.WorkLocationCD;
import nts.uk.ctx.at.record.dom.worktime.TimeActualStamp;
import nts.uk.ctx.at.record.dom.worktime.WorkStamp;
import nts.uk.ctx.at.record.dom.worktime.enums.StampSourceInfo;
import nts.uk.ctx.at.record.infra.entity.breakorgoout.KrcdtDaiOutingTime;
import nts.uk.ctx.at.record.infra.entity.worktime.KrcdtDaiTemporaryTime;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTime;
import nts.uk.shr.com.time.TimeWithDayAttr;

@Stateless
public class JpaOutingTimeOfDailyPerformanceRepository extends JpaRepository
		implements OutingTimeOfDailyPerformanceRepository {

	private static final String REMOVE_BY_EMPLOYEE;

	private static final String DEL_BY_LIST_KEY;

	private static final String SELECT_BY_EMPLOYEE_AND_DATE;
	
	private static final String SELECT_BY_KEY;
	
	private static final String CHECK_EXIST_DATA;

	static {
		StringBuilder builderString = new StringBuilder();
		builderString.append("DELETE ");
		builderString.append("FROM KrcdtDaiOutingTime a ");
		builderString.append("WHERE a.krcdtDaiOutingTimePK.employeeId = :employeeId ");
		builderString.append("AND a.krcdtDaiOutingTimePK.ymd = :ymd ");
		REMOVE_BY_EMPLOYEE = builderString.toString();
		
		builderString = new StringBuilder();
		builderString.append("SELECT a ");
		builderString.append("FROM KrcdtDaiOutingTime a ");
		builderString.append("WHERE a.krcdtDaiOutingTimePK.employeeId = :employeeId ");
		builderString.append("AND a.krcdtDaiOutingTimePK.ymd = :ymd ");
		SELECT_BY_EMPLOYEE_AND_DATE = builderString.toString();

		builderString = new StringBuilder();
		builderString.append("DELETE ");
		builderString.append("FROM KrcdtDaiOutingTime a ");
		builderString.append("WHERE a.krcdtDaiOutingTimePK.employeeId IN :employeeIds ");
		builderString.append("AND a.krcdtDaiOutingTimePK.ymd IN :ymds ");
		DEL_BY_LIST_KEY = builderString.toString();
		
		builderString = new StringBuilder();
		builderString.append("DELETE ");
		builderString.append("FROM KrcdtDaiOutingTime a ");
		builderString.append("WHERE a.krcdtDaiOutingTimePK.employeeId = :employeeId ");
		builderString.append("AND a.krcdtDaiOutingTimePK.ymd = :ymd ");
		builderString.append("AND a.krcdtDaiOutingTimePK.outingFrameNo IN :outingFrameNos ");
		SELECT_BY_KEY = builderString.toString();
		
		builderString = new StringBuilder();
		builderString.append("DELETE ");
		builderString.append("FROM KrcdtDaiOutingTime a ");
		builderString.append("WHERE a.krcdtDaiOutingTimePK.employeeId = :employeeId ");
		builderString.append("AND a.krcdtDaiOutingTimePK.ymd = :ymd ");
		builderString.append("AND a.krcdtDaiOutingTimePK.outingFrameNo = :outingFrameNo ");
		CHECK_EXIST_DATA = builderString.toString();
	}

	@Override
	public void delete(String employeeId, GeneralDate ymd) {
		this.getEntityManager().createQuery(REMOVE_BY_EMPLOYEE).setParameter("employeeId", employeeId)
				.setParameter("ymd", ymd).executeUpdate();
	}

	@Override
	public void deleteByListEmployeeId(List<String> employeeIds, List<GeneralDate> ymds) {
		this.getEntityManager().createQuery(DEL_BY_LIST_KEY).setParameter("employeeIds", employeeIds)
				.setParameter("ymds", ymds).executeUpdate();
	}

	@Override
	public Optional<OutingTimeOfDailyPerformance> findByEmployeeIdAndDate(String employeeId, GeneralDate ymd) {
		List<KrcdtDaiOutingTime> lstKrcdtDaiOutingTime = this.queryProxy()
				.query(SELECT_BY_EMPLOYEE_AND_DATE, KrcdtDaiOutingTime.class).setParameter("employeeId", employeeId)
				.setParameter("ymd", ymd).getList();
		if (lstKrcdtDaiOutingTime == null || lstKrcdtDaiOutingTime.isEmpty()) {
			return Optional.empty();
		}
		List<OutingTimeSheet> lstOutingTimeSheet = new ArrayList<OutingTimeSheet>();

		lstKrcdtDaiOutingTime.forEach(x -> {
			WorkStamp outStamp = new WorkStamp(
					x.outStampRoundingTimeDay != null ? new TimeWithDayAttr(x.outStampRoundingTimeDay) : null,
					x.outStampTime != null ? new TimeWithDayAttr(x.outStampTime) : null,
					x.outStampPlaceCode != null ? new WorkLocationCD(x.outStampPlaceCode) : null,
					x.outStampSourceInfo != null ? EnumAdaptor.valueOf(x.outStampSourceInfo, StampSourceInfo.class)
							: null);
			WorkStamp outActualStamp = new WorkStamp(
					x.outActualRoundingTimeDay != null ? new TimeWithDayAttr(x.outActualRoundingTimeDay) : null,
					x.outActualTime != null ? new TimeWithDayAttr(x.outActualTime) : null,
					x.outActualPlaceCode != null ? new WorkLocationCD(x.outActualPlaceCode) : null,
					x.outActualSourceInfo != null ? EnumAdaptor.valueOf(x.outActualSourceInfo, StampSourceInfo.class)
							: null);
			TimeActualStamp goOut = new TimeActualStamp(outActualStamp, outStamp, x.outNumberStamp);
			WorkStamp backStamp = new WorkStamp(
					x.backStampRoundingTimeDay != null ? new TimeWithDayAttr(x.backStampRoundingTimeDay) : null,
					x.backStampTime != null ? new TimeWithDayAttr(x.backStampTime) : null,
					x.backStampPlaceCode != null ? new WorkLocationCD(x.backStampPlaceCode) : null,
					x.backStampSourceInfo != null ? EnumAdaptor.valueOf(x.backStampSourceInfo, StampSourceInfo.class)
							: null);
			WorkStamp backActualStamp = new WorkStamp(
					x.backActualRoundingTimeDay != null ? new TimeWithDayAttr(x.backActualRoundingTimeDay) : null,
					x.backActualTime != null ? new TimeWithDayAttr(x.backActualTime) : null,
					x.backActualPlaceCode != null ? new WorkLocationCD(x.backActualPlaceCode) : null,
					x.backActualSourceInfo != null ? EnumAdaptor.valueOf(x.backActualSourceInfo, StampSourceInfo.class)
							: null);
			TimeActualStamp comeBack = new TimeActualStamp(backActualStamp, backStamp, x.backNumberStamp);
			GoingOutReason reasonForGoOut = EnumAdaptor.valueOf(x.outingReason, GoingOutReason.class);
			AttendanceTime outingTimeCalculation = new AttendanceTime(x.outingTimeCalculation);
			AttendanceTime outingTime = new AttendanceTime(x.outingTime);
			OutingTimeSheet outingTimeSheet = new OutingTimeSheet(
					new OutingFrameNo(x.krcdtDaiOutingTimePK.outingFrameNo), Optional.of(goOut), outingTimeCalculation, outingTime,
					reasonForGoOut, Optional.of(comeBack));
			lstOutingTimeSheet.add(outingTimeSheet);
		});
		return Optional.ofNullable(new OutingTimeOfDailyPerformance(employeeId, ymd, lstOutingTimeSheet));
	}

	@Override
	public void add(OutingTimeOfDailyPerformance outing) {
		commandProxy().updateAll(outing.getOutingTimeSheets().stream()
				.map(c -> KrcdtDaiOutingTime.toEntity(outing.getEmployeeId(), outing.getYmd(), c))
				.collect(Collectors.toList()));
	}

//	@Override
//	public void update(OutingTimeOfDailyPerformance outing) {
//		commandProxy().insertAll(outing.getOutingTimeSheets().stream()
//				.map(c -> KrcdtDaiOutingTime.toEntity(outing.getEmployeeId(), outing.getYmd(), c))
//				.collect(Collectors.toList()));
//	}

	@Override
	public void insert(OutingTimeOfDailyPerformance outingTimeOfDailyPerformance) {
//		KrcdtDaiOutingTime.toEntity(outingTimeOfDailyPerformance).stream().forEach(item -> {
//			this.commandProxy().insert(item);
//		});
//		this.getEntityManager().flush();
	}

	@Override
	public void update(OutingTimeOfDailyPerformance outingTimeOfDailyPerformance) {
		List<Integer> outingFrameNos = outingTimeOfDailyPerformance.getOutingTimeSheets().stream().map(item -> {
			return item.getOutingFrameNo().v();
		}).collect(Collectors.toList());
		List<KrcdtDaiOutingTime> krcdtDaiOutingTimeLists = this.queryProxy().query(SELECT_BY_KEY, KrcdtDaiOutingTime.class)
				.setParameter("employeeId", outingTimeOfDailyPerformance.getEmployeeId())
				.setParameter("ymd", outingTimeOfDailyPerformance.getEmployeeId())
				.setParameter("outingFrameNos", outingFrameNos).getList();
		
		List<OutingTimeSheet> outingTimeSheets = outingTimeOfDailyPerformance.getOutingTimeSheets();
		
		for(KrcdtDaiOutingTime krcdtDaiOutingTime : krcdtDaiOutingTimeLists) {
			Optional<OutingTimeSheet> outingTimeSheet = outingTimeSheets.stream().filter(item ->
					krcdtDaiOutingTime.krcdtDaiOutingTimePK.employeeId == outingTimeOfDailyPerformance.getEmployeeId() &&
					krcdtDaiOutingTime.krcdtDaiOutingTimePK.ymd == outingTimeOfDailyPerformance.getYmd() &&
					krcdtDaiOutingTime.krcdtDaiOutingTimePK.outingFrameNo == item.getOutingFrameNo().v()).findFirst();
			if (outingTimeSheet.isPresent()) {
				krcdtDaiOutingTime.backActualPlaceCode = outingTimeSheet.get().getComeBack().get().getActualStamp().getLocationCode().v();
				krcdtDaiOutingTime.backActualRoundingTimeDay = outingTimeSheet.get().getComeBack().get().getActualStamp().getAfterRoundingTime().v();
				krcdtDaiOutingTime.backActualSourceInfo = outingTimeSheet.get().getComeBack().get().getActualStamp().getStampSourceInfo().value;
				krcdtDaiOutingTime.backActualTime = outingTimeSheet.get().getComeBack().get().getActualStamp().getTimeWithDay().v();
				krcdtDaiOutingTime.backNumberStamp = outingTimeSheet.get().getComeBack().get().getNumberOfReflectionStamp();
				krcdtDaiOutingTime.backStampPlaceCode = outingTimeSheet.get().getComeBack().get().getStamp().isPresent() ? outingTimeSheet.get().getComeBack().get().getStamp().get().getLocationCode().v() : null;
				krcdtDaiOutingTime.backStampRoundingTimeDay = outingTimeSheet.get().getComeBack().get().getStamp().isPresent() ? outingTimeSheet.get().getComeBack().get().getStamp().get().getAfterRoundingTime().v() : null;
				krcdtDaiOutingTime.backStampSourceInfo = outingTimeSheet.get().getComeBack().get().getStamp().isPresent() ? outingTimeSheet.get().getComeBack().get().getStamp().get().getStampSourceInfo().value : null;
				krcdtDaiOutingTime.backStampTime = outingTimeSheet.get().getComeBack().get().getStamp().isPresent() ? outingTimeSheet.get().getComeBack().get().getStamp().get().getTimeWithDay().v() : null;
				krcdtDaiOutingTime.outActualPlaceCode = outingTimeSheet.get().getGoOut().get().getActualStamp().getLocationCode().v();
				krcdtDaiOutingTime.outActualRoundingTimeDay = outingTimeSheet.get().getGoOut().get().getActualStamp().getAfterRoundingTime().v();
				krcdtDaiOutingTime.outActualSourceInfo = outingTimeSheet.get().getGoOut().get().getActualStamp().getStampSourceInfo().value;
				krcdtDaiOutingTime.outActualTime = outingTimeSheet.get().getGoOut().get().getActualStamp().getTimeWithDay().v();
				krcdtDaiOutingTime.outingReason = outingTimeSheet.get().getReasonForGoOut().value;
				krcdtDaiOutingTime.outingTime = outingTimeSheet.get().getOutingTime().v();
				krcdtDaiOutingTime.outingTimeCalculation = outingTimeSheet.get().getOutingTimeCalculation().v();
				krcdtDaiOutingTime.outNumberStamp = outingTimeSheet.get().getGoOut().get().getNumberOfReflectionStamp();
				krcdtDaiOutingTime.outStampPlaceCode = outingTimeSheet.get().getGoOut().get().getStamp().isPresent() ? outingTimeSheet.get().getGoOut().get().getStamp().get().getLocationCode().v() : null;
				krcdtDaiOutingTime.outStampRoundingTimeDay = outingTimeSheet.get().getGoOut().get().getStamp().isPresent() ? outingTimeSheet.get().getGoOut().get().getStamp().get().getAfterRoundingTime().v() : null;
				krcdtDaiOutingTime.outStampSourceInfo = outingTimeSheet.get().getGoOut().get().getStamp().isPresent() ? outingTimeSheet.get().getGoOut().get().getStamp().get().getStampSourceInfo().value : null;
				krcdtDaiOutingTime.outStampTime = outingTimeSheet.get().getGoOut().get().getStamp().isPresent() ? outingTimeSheet.get().getGoOut().get().getStamp().get().getTimeWithDay().v() : null;	
			}
		};
		krcdtDaiOutingTimeLists.forEach(item -> {
			this.commandProxy().update(item);
		});
		this.getEntityManager().flush();
	}

	@Override
	public boolean checkExistData(String employeeId, GeneralDate ymd, OutingFrameNo outingFrameNo) {
		return this.queryProxy().query(CHECK_EXIST_DATA, long.class).setParameter("employeeId", employeeId)
				.setParameter("ymd", ymd)
				.setParameter("outingFrameNo", outingFrameNo.v()).getSingle().get() > 0;
	}
}
