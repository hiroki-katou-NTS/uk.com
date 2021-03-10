package nts.uk.ctx.at.record.infra.repository.breakorgoout;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import lombok.val;
import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.infra.data.DbConsts;
import nts.arc.layer.infra.data.JpaRepository;
import nts.arc.layer.infra.data.query.TypedQueryWrapper;
import nts.arc.time.GeneralDate;
import nts.gul.collection.CollectionUtil;
import nts.uk.ctx.at.record.dom.breakorgoout.OutingTimeOfDailyPerformance;
import nts.uk.ctx.at.record.dom.breakorgoout.repository.OutingTimeOfDailyPerformanceRepository;
import nts.uk.ctx.at.record.infra.entity.breakorgoout.KrcdtDaiOutingTime;
import nts.uk.ctx.at.record.infra.entity.breakorgoout.KrcdtDaiOutingTimePK;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTime;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.breakouting.OutingFrameNo;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.breakouting.OutingTimeSheet;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.common.TimeActualStamp;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.common.timestamp.TimeChangeMeans;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.common.timestamp.WorkLocationCD;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.common.timestamp.WorkStamp;
import nts.uk.ctx.at.shared.dom.workrule.goingout.GoingOutReason;
import nts.uk.shr.com.time.TimeWithDayAttr;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.shr.infra.data.jdbc.JDBCUtil;

@TransactionAttribute(TransactionAttributeType.SUPPORTS)
@Stateless
public class JpaOutingTimeOfDailyPerformanceRepository extends JpaRepository
		implements OutingTimeOfDailyPerformanceRepository {


	private static final String SELECT_BY_EMPLOYEE_AND_DATE;

	private static final String SELECT_BY_KEY;

	private static final String CHECK_EXIST_DATA;
	
	static {
		StringBuilder builderString = new StringBuilder();
//		builderString.append("DELETE ");
//		builderString.append("FROM KrcdtDaiOutingTime a ");
//		builderString.append("WHERE a.krcdtDaiOutingTimePK.employeeId = :employeeId ");
//		builderString.append("AND a.krcdtDaiOutingTimePK.ymd = :ymd ");
//		REMOVE_BY_EMPLOYEE = builderString.toString();

		builderString = new StringBuilder();
		builderString.append("SELECT a ");
		builderString.append("FROM KrcdtDaiOutingTime a ");
		builderString.append("WHERE a.krcdtDaiOutingTimePK.employeeId = :employeeId ");
		builderString.append("AND a.krcdtDaiOutingTimePK.ymd = :ymd ");
		SELECT_BY_EMPLOYEE_AND_DATE = builderString.toString();

		builderString = new StringBuilder();
		builderString.append("SELECT a ");
		builderString.append("FROM KrcdtDaiOutingTime a ");
		builderString.append("WHERE a.krcdtDaiOutingTimePK.employeeId = :employeeId ");
		builderString.append("AND a.krcdtDaiOutingTimePK.ymd = :ymd ");
		builderString.append("AND a.krcdtDaiOutingTimePK.outingFrameNo IN :outingFrameNos ");
		SELECT_BY_KEY = builderString.toString();

		builderString = new StringBuilder();
		builderString.append("SELECT COUNT(a) ");
		builderString.append("FROM KrcdtDaiOutingTime a ");
		builderString.append("WHERE a.krcdtDaiOutingTimePK.employeeId = :employeeId ");
		builderString.append("AND a.krcdtDaiOutingTimePK.ymd = :ymd ");
		builderString.append("AND a.krcdtDaiOutingTimePK.outingFrameNo = :outingFrameNo ");
		CHECK_EXIST_DATA = builderString.toString();
	}

	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	@Override
	public void delete(String employeeId, GeneralDate ymd) {
		
		Connection con = this.getEntityManager().unwrap(Connection.class);
		String sqlQuery = "Delete From KRCDT_DAY_TS_GOOUT Where SID = " + "'" + employeeId + "'" + " and YMD = " + "'" + ymd + "'" ;
		try {
			con.createStatement().executeUpdate(sqlQuery);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
		
//		this.getEntityManager().createQuery(REMOVE_BY_EMPLOYEE).setParameter("employeeId", employeeId)
//				.setParameter("ymd", ymd).executeUpdate();

	}

    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
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
			lstOutingTimeSheet.add(toDtomain(x));
		});
		return Optional.ofNullable(new OutingTimeOfDailyPerformance(employeeId, ymd, lstOutingTimeSheet));
	}

	@Override
	public void add(OutingTimeOfDailyPerformance outing) {
		// commandProxy().insertAll(outing.getOutingTimeSheets().stream()
		// .map(c -> KrcdtDaiOutingTime.toEntity(outing.getEmployeeId(),
		// outing.getYmd(), c))
		// .collect(Collectors.toList()));
		// this.getEntityManager().flush();

		try {
			Connection con = this.getEntityManager().unwrap(Connection.class);
			Statement statementI = con.createStatement();
			for (OutingTimeSheet outingTimeSheet : outing.getOutingTime().getOutingTimeSheets()) {

				// OutingTimeSheet - goOut - stamp
				Integer outStampTime = (outingTimeSheet.getGoOut().isPresent() && outingTimeSheet.getGoOut().get().getTimeDay().getTimeWithDay().isPresent())
								? outingTimeSheet.getGoOut().get().getTimeDay().getTimeWithDay().get().valueAsMinutes()
								: null;
				String outStampLocationCode = (outingTimeSheet.getGoOut().isPresent()
						&& outingTimeSheet.getGoOut().get().getLocationCode().isPresent())
								? "'" + outingTimeSheet.getGoOut().get().getLocationCode().get().v() + "'"
								: null;
				Integer outStampSource = (outingTimeSheet.getGoOut().isPresent())
								? outingTimeSheet.getGoOut().get().getTimeDay().getReasonTimeChange().getTimeChangeMeans().value 
								: null;



				// OutingTimeSheet - comeBack - stamp
				Integer backStampTime = (outingTimeSheet.getComeBack().isPresent()
						&& outingTimeSheet.getComeBack().get().getTimeDay().getTimeWithDay().isPresent())
								? outingTimeSheet.getComeBack().get().getTimeDay().getTimeWithDay().get().valueAsMinutes()
								: null;
				Integer backStampSource = (outingTimeSheet.getComeBack().isPresent())
								? outingTimeSheet.getComeBack().get().getTimeDay().getReasonTimeChange().getTimeChangeMeans().value
								: null;
				String backStampLocationCode = (outingTimeSheet.getComeBack().isPresent()
						&& outingTimeSheet.getComeBack().get().getLocationCode().isPresent())
								? "'" + outingTimeSheet.getComeBack().get().getLocationCode().get().v()	+ "'"
								: null;


				String insertTableSQL = "INSERT INTO KRCDT_DAY_TS_GOOUT ( SID , YMD , OUTING_FRAME_NO , OUT_STAMP_TIME  , OUT_STAMP_PLACE_CODE , OUT_STAMP_SOURCE_INFO , "
						+ " BACK_STAMP_TIME  , BACK_STAMP_PLACE_CODE , "
						+ " BACK_STAMP_SOURCE_INFO ,"
						+ " OUTING_REASON ) " + "VALUES( '" + outing.getEmployeeId() + "' , '"
						+ outing.getYmd() + "' , " + outingTimeSheet.getOutingFrameNo().v() + " , " 
						+ outStampTime + " , "  + outStampLocationCode + " , " + outStampSource + ", "
						+ backStampTime + " , " + backStampLocationCode + " , " + backStampSource + ", "
						+ outingTimeSheet.getReasonForGoOut().value + " )";
				statementI.executeUpdate(JDBCUtil.toInsertWithCommonField(insertTableSQL));
			}
		} catch (Exception e) {

		}
	}

    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
	@Override
	public boolean checkExistData(String employeeId, GeneralDate ymd, OutingFrameNo outingFrameNo) {
		return this.queryProxy().query(CHECK_EXIST_DATA, long.class).setParameter("employeeId", employeeId)
				.setParameter("ymd", ymd).setParameter("outingFrameNo", outingFrameNo.v()).getSingle().get() > 0;
	}

	@Override
	public void update(OutingTimeOfDailyPerformance domain) {
		List<Integer> outingFrameNos = domain.getOutingTime().getOutingTimeSheets().stream().map(item -> {
			return item.getOutingFrameNo().v();
		}).collect(Collectors.toList());
		
		List<KrcdtDaiOutingTime> krcdtDaiOutingTimeLists = new ArrayList<>();
		CollectionUtil.split(outingFrameNos, DbConsts.MAX_CONDITIONS_OF_IN_STATEMENT, subList -> {
			krcdtDaiOutingTimeLists.addAll(this.queryProxy()
				.query(SELECT_BY_KEY, KrcdtDaiOutingTime.class)
					.setParameter("employeeId", domain.getEmployeeId())
					.setParameter("ymd", domain.getYmd())
					.setParameter("outingFrameNos", subList)
				.getList());
		});

		List<OutingTimeSheet> outingTimeSheets = domain.getOutingTime().getOutingTimeSheets();

		outingTimeSheets.stream().forEach(outingTimeSheet -> {
			KrcdtDaiOutingTime krcdtDaiOutingTime = krcdtDaiOutingTimeLists.stream()
					.filter(c -> c.krcdtDaiOutingTimePK.employeeId == domain.getEmployeeId()
							&& c.krcdtDaiOutingTimePK.ymd == domain.getYmd()
							&& c.krcdtDaiOutingTimePK.outingFrameNo == outingTimeSheet.getOutingFrameNo().v())
					.findFirst().orElse(null);
			if (krcdtDaiOutingTime != null) {
				setEntityValue(outingTimeSheet, krcdtDaiOutingTime);
			} else {
				krcdtDaiOutingTime = new KrcdtDaiOutingTime();
				krcdtDaiOutingTime.krcdtDaiOutingTimePK = new KrcdtDaiOutingTimePK(domain.getEmployeeId(),
						domain.getYmd(), outingTimeSheet.getOutingFrameNo().v());
				setEntityValue(outingTimeSheet, krcdtDaiOutingTime);
				krcdtDaiOutingTimeLists.add(krcdtDaiOutingTime);
			}
		});
		this.commandProxy().updateAll(krcdtDaiOutingTimeLists);
		this.getEntityManager().flush();
	}

	private void setEntityValue(OutingTimeSheet domain, KrcdtDaiOutingTime krcdtDaiOutingTime) {
		setBackMainStamp(domain.getComeBack().orElse(null), krcdtDaiOutingTime);
		krcdtDaiOutingTime.outingReason = domain.getReasonForGoOut().value;
		setGoOutMainStamp(domain.getGoOut().orElse(null), krcdtDaiOutingTime);
	}

	private void setBackMainStamp(WorkStamp stamp, KrcdtDaiOutingTime krcdtDaiOutingTime) {
		if (stamp != null) {
			setBackStamp(stamp, krcdtDaiOutingTime);
		} else {
			setGoOutStampNull(krcdtDaiOutingTime);
		}
	}



	private void setBackStamp(WorkStamp stamp, KrcdtDaiOutingTime krcdtDaiOutingTime) {
		if (stamp != null) {
			krcdtDaiOutingTime.backStampPlaceCode = !stamp.getLocationCode().isPresent() ? null
					: stamp.getLocationCode().get().v();
			krcdtDaiOutingTime.backStampSourceInfo = stamp.getTimeDay().getReasonTimeChange().getTimeChangeMeans() == null ? null
					: stamp.getTimeDay().getReasonTimeChange().getTimeChangeMeans().value;
			krcdtDaiOutingTime.backStampTime = !stamp.getTimeDay().getTimeWithDay().isPresent() ? null
					: stamp.getTimeDay().getTimeWithDay().get().valueAsMinutes();
		} else {
			setBackStampNull(krcdtDaiOutingTime);
		}
	}



	private void setBackStampNull(KrcdtDaiOutingTime krcdtDaiOutingTime) {
		krcdtDaiOutingTime.backStampPlaceCode = null;
		krcdtDaiOutingTime.backStampSourceInfo = null;
		krcdtDaiOutingTime.backStampTime = null;
	}

	private void setGoOutMainStamp(WorkStamp stamp, KrcdtDaiOutingTime krcdtDaiOutingTime) {
		if (stamp != null) {
			setGoOutStamp(stamp, krcdtDaiOutingTime);
		} else {
			setGoOutStampNull(krcdtDaiOutingTime);
		}
	}


	private void setGoOutStamp(WorkStamp stamp, KrcdtDaiOutingTime krcdtDaiOutingTime) {
		if (stamp != null) {
			krcdtDaiOutingTime.outStampPlaceCode = !stamp.getLocationCode().isPresent() ? null
					: stamp.getLocationCode().get().v();
			krcdtDaiOutingTime.outStampSourceInfo = stamp.getTimeDay().getReasonTimeChange().getTimeChangeMeans() == null ? null
					: stamp.getTimeDay().getReasonTimeChange().getTimeChangeMeans().value;
			krcdtDaiOutingTime.outStampTime = !stamp.getTimeDay().getTimeWithDay().isPresent() ? null
					: stamp.getTimeDay().getTimeWithDay().get().valueAsMinutes();
		} else {
			setGoOutStampNull(krcdtDaiOutingTime);
		}
	}

	private void setGoOutStampNull(KrcdtDaiOutingTime krcdtDaiOutingTime) {
		krcdtDaiOutingTime.outStampPlaceCode = null;
		krcdtDaiOutingTime.outStampSourceInfo = null;
		krcdtDaiOutingTime.outStampTime = null;
	}

    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
	@Override
	public List<OutingTimeOfDailyPerformance> finds(List<String> employeeId, DatePeriod ymd) {
		List<OutingTimeOfDailyPerformance> result = new ArrayList<>();
		StringBuilder query = new StringBuilder();
		query.append("SELECT a FROM KrcdtDaiOutingTime a ");
		query.append("WHERE a.krcdtDaiOutingTimePK.employeeId IN :employeeId ");
		query.append("AND a.krcdtDaiOutingTimePK.ymd <= :end AND a.krcdtDaiOutingTimePK.ymd >= :start");
		TypedQueryWrapper<KrcdtDaiOutingTime> tQuery = queryProxy().query(query.toString(), KrcdtDaiOutingTime.class);
		CollectionUtil.split(employeeId, DbConsts.MAX_CONDITIONS_OF_IN_STATEMENT, empIds -> {
			result.addAll(tQuery.setParameter("employeeId", empIds).setParameter("start", ymd.start())
					.setParameter("end", ymd.end()).getList().stream()
					.collect(Collectors
							.groupingBy(c -> c.krcdtDaiOutingTimePK.employeeId + c.krcdtDaiOutingTimePK.ymd.toString()))
					.entrySet().stream()
					.map(c -> new OutingTimeOfDailyPerformance(c.getValue().get(0).krcdtDaiOutingTimePK.employeeId,
							c.getValue().get(0).krcdtDaiOutingTimePK.ymd,
							c.getValue().stream().map(x -> toDtomain(x)).collect(Collectors.toList())))
					.collect(Collectors.toList()));
		});
		return result;
	}

	private OutingTimeSheet toDtomain(KrcdtDaiOutingTime x) {
		WorkStamp outStamp = x.outStampTime == null ? null :  new WorkStamp(
				x.outStampTime != null ? new TimeWithDayAttr(x.outStampTime) : null,
				x.outStampPlaceCode != null ? new WorkLocationCD(x.outStampPlaceCode) : null,
				x.outStampSourceInfo != null ? EnumAdaptor.valueOf(x.outStampSourceInfo, TimeChangeMeans.class) : null);

		WorkStamp backStamp = 	x.backStampTime == null ? null : new WorkStamp(
				x.backStampTime != null ? new TimeWithDayAttr(x.backStampTime) : null,
				x.backStampPlaceCode != null ? new WorkLocationCD(x.backStampPlaceCode) : null,
				x.backStampSourceInfo != null ? EnumAdaptor.valueOf(x.backStampSourceInfo, TimeChangeMeans.class)
						: null);

		GoingOutReason reasonForGoOut = EnumAdaptor.valueOf(x.outingReason, GoingOutReason.class);
		OutingTimeSheet outingTimeSheet = new OutingTimeSheet(new OutingFrameNo(x.krcdtDaiOutingTimePK.outingFrameNo),
				Optional.ofNullable(outStamp), reasonForGoOut, Optional.ofNullable(backStamp));
		return outingTimeSheet;
	}

    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
	@Override
	public List<OutingTimeOfDailyPerformance> finds(Map<String, List<GeneralDate>> param) {
		List<OutingTimeOfDailyPerformance> result = new ArrayList<>();
		StringBuilder query = new StringBuilder();
		query.append("SELECT a FROM KrcdtDaiOutingTime a ");
		query.append("WHERE a.krcdtDaiOutingTimePK.employeeId IN :employeeId ");
		query.append("AND a.krcdtDaiOutingTimePK.ymd IN :date");
		TypedQueryWrapper<KrcdtDaiOutingTime> tQuery = queryProxy().query(query.toString(), KrcdtDaiOutingTime.class);
		CollectionUtil.split(param, DbConsts.MAX_CONDITIONS_OF_IN_STATEMENT, p -> {
			result.addAll(tQuery.setParameter("employeeId", p.keySet())
					.setParameter("date", p.values().stream().flatMap(List::stream).collect(Collectors.toSet()))
					.getList().stream()
					.filter(c -> p.get(c.krcdtDaiOutingTimePK.employeeId).contains(c.krcdtDaiOutingTimePK.ymd))
					.collect(Collectors
							.groupingBy(c -> c.krcdtDaiOutingTimePK.employeeId + c.krcdtDaiOutingTimePK.ymd.toString()))
					.entrySet().stream()
					.map(c -> new OutingTimeOfDailyPerformance(c.getValue().get(0).krcdtDaiOutingTimePK.employeeId,
							c.getValue().get(0).krcdtDaiOutingTimePK.ymd,
							c.getValue().stream().map(x -> toDtomain(x)).collect(Collectors.toList())))
					.collect(Collectors.toList()));
		});
		return result;
	}
}
