package nts.uk.ctx.at.record.infra.repository.workinformation;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.record.dom.workinformation.WorkInfoOfDailyPerformance;
import nts.uk.ctx.at.record.dom.workinformation.WorkInformationRepository;
import nts.uk.ctx.at.record.infra.entity.workinformation.KrcdtDaiPerWorkInfo;

/**
 * 
 * @author lamdt
 *
 */
@Stateless
public class JpaWorkInformationRepository extends JpaRepository implements WorkInformationRepository {

	private static final String DEL_BY_KEY;

	private static final String FIND_BY_LIST_SID;

	private static final String DEL_BY_LIST_KEY;

	private static final String UPDATE_WORK_INFO;

	private static final String UPDATE_SCHEDULE_TIME;

	private static final String UPDATE_DIRECT_LINE;

	private static final String UPDATE_DAY_OF_WEEK;

	private static final String FIND_BY_ID = "SELECT a FROM KrcmtDaiPerWorkInfo"
			+ " WHERE a.krcmtDaiPerWorkInfoPK.employeeId = :employeeId " + " AND a.krcmtDaiPerWorkInfoPK.ymd = :ymd ";

	static {
		StringBuilder builderString = new StringBuilder();
		builderString.append("DELETE ");
		builderString.append("FROM KrcmtDaiPerWorkInfo a ");
		builderString.append("WHERE WHERE a.krcmtDaiPerWorkInfoPK.employeeId = :employeeId ");
		builderString.append("AND a.krcmtDaiPerWorkInfoPK.ymd = :ymd ");
		DEL_BY_KEY = builderString.toString();

		builderString = new StringBuilder();
		builderString.append("SELECT a ");
		builderString.append("FROM KrcmtDaiPerWorkInfo a ");
		builderString.append("WHERE a.krcmtDaiPerWorkInfoPK.employeeId IN :employeeIds ");
		builderString.append("AND a.krcmtDaiPerWorkInfoPK.ymd IN :ymds ");
		FIND_BY_LIST_SID = builderString.toString();

		builderString = new StringBuilder();
		builderString.append("DELETE ");
		builderString.append("FROM KrcmtDaiPerWorkInfo a ");
		builderString.append("WHERE WHERE a.krcmtDaiPerWorkInfoPK.employeeId IN :employeeIds ");
		builderString.append("AND a.krcmtDaiPerWorkInfoPK.ymd IN :ymds ");
		DEL_BY_LIST_KEY = builderString.toString();

		builderString = new StringBuilder();
		builderString.append("UPDATE KrcmtDaiPerWorkInfo a ");
		builderString.append("SET a.scheduleWorkWorktypeCode = :scheduleWorkWorkTypeCode ");
		builderString.append("AND a.scheduleWorkWorktimeCode = :scheduleWorkWorkTimeCode ");
		builderString.append("WHERE a.krcmtDaiPerWorkInfoPK.employeeId = :employeeId ");
		builderString.append("AND a.krcmtDaiPerWorkInfoPK.ymd = :ymd ");
		UPDATE_WORK_INFO = builderString.toString();

		builderString = new StringBuilder();
		builderString.append("UPDATE KrcdtWorkScheduleTime a ");
		builderString.append("SET a.attendance = :attendance ");
		builderString.append("AND a.leaveWork = :leaveWork ");
		builderString.append("AND a.krcdtWorkScheduleTimePK.workNo = :workNo ");
		builderString.append("WHERE a.krcdtWorkScheduleTimePK.employeeId = :employeeId ");
		builderString.append("AND a.krcdtWorkScheduleTimePK.ymd = :ymd ");
		UPDATE_SCHEDULE_TIME = builderString.toString();

		builderString = new StringBuilder();
		builderString.append("UPDATE KrcmtDaiPerWorkInfo a ");
		builderString.append("SET a.goStraightAttribute = :goStraightAttribute ");
		builderString.append("AND a.backStraightAttribute = :backStraightAttribute ");
		builderString.append("WHERE a.krcmtDaiPerWorkInfoPK.employeeId = :employeeId ");
		builderString.append("AND a.krcmtDaiPerWorkInfoPK.ymd = :ymd ");
		UPDATE_DIRECT_LINE = builderString.toString();

		builderString = new StringBuilder();
		builderString.append("UPDATE KrcmtDaiPerWorkInfo a ");
		builderString.append("SET a.recordWorkWorktypeCode = :recordWorkWorktypeCode ");
		builderString.append("AND a.recordWorkWorktimeCode = :recordWorkWorktimeCode ");
		builderString.append("WHERE a.krcmtDaiPerWorkInfoPK.employeeId = :employeeId ");
		builderString.append("AND a.krcmtDaiPerWorkInfoPK.ymd = :ymd ");
		UPDATE_DAY_OF_WEEK = builderString.toString();
	}

	@Override
	public Optional<WorkInfoOfDailyPerformance> find(String employeeId, GeneralDate ymd) {
		return this.queryProxy().query(FIND_BY_ID, KrcdtDaiPerWorkInfo.class).setParameter("employeeId", employeeId)
				.setParameter("ymd", ymd).getSingle(c -> c.toDomain());
	}

	@Override
	public void delete(String employeeId, GeneralDate ymd) {
		this.getEntityManager().createQuery(DEL_BY_KEY).setParameter("employeeId", employeeId).setParameter("ymd", ymd)
				.executeUpdate();
	}

	@Override
	public List<WorkInfoOfDailyPerformance> findByListEmployeeId(List<String> employeeIds, List<GeneralDate> ymds) {
		return this.queryProxy().query(FIND_BY_LIST_SID, KrcdtDaiPerWorkInfo.class)
				.setParameter("employeeIds", employeeIds).setParameter("ymds", ymds).getList(f -> f.toDomain());
	}

	@Override
	public void deleteByListEmployeeId(List<String> employeeIds, List<GeneralDate> ymds) {
		this.getEntityManager().createQuery(DEL_BY_LIST_KEY).setParameter("employeeIds", employeeIds)
				.setParameter("ymds", ymds).executeUpdate();
	}

	@Override
	public void updateWorkInfo(String employeeId, GeneralDate ymd, String scheduleWorkTypeCode,
			String scheduleWorkTimeCode) {
		this.getEntityManager().createQuery(UPDATE_WORK_INFO).setParameter("employeeId", employeeId)
				.setParameter("ymd", ymd).setParameter("scheduleWorkWorkTypeCode", scheduleWorkTypeCode)
				.setParameter("scheduleWorkWorkTimeCode", scheduleWorkTimeCode).executeUpdate();
	}

	@Override
	public void updateScheduleTime(String employeeId, GeneralDate ymd, BigDecimal workNo, int attendance,
			int leaveWork) {
		this.getEntityManager().createQuery(UPDATE_SCHEDULE_TIME).setParameter("employeeId", employeeId)
				.setParameter("ymd", ymd).setParameter("attendance", attendance).setParameter("leaveWork", leaveWork)
				.setParameter("workNo", workNo).executeUpdate();
	}

	@Override
	public void updateDirectLine(String employeeId, GeneralDate ymd, int goStraight, int backStraight) {
		this.getEntityManager().createQuery(UPDATE_DIRECT_LINE).setParameter("employeeId", employeeId)
				.setParameter("ymd", ymd).setParameter("goStraightAttribute", goStraight)
				.setParameter("backStraightAttribute", backStraight).executeUpdate();
	}

	@Override
	public void updateDayOfWeek(String employeeId, GeneralDate ymd, String workTime, String workType) {
		this.getEntityManager().createQuery(UPDATE_DAY_OF_WEEK).setParameter("employeeId", employeeId)
				.setParameter("ymd", ymd).setParameter("recordWorkWorktypeCode", workType)
				.setParameter("recordWorkWorktimeCode", workTime).executeUpdate();
	}

}