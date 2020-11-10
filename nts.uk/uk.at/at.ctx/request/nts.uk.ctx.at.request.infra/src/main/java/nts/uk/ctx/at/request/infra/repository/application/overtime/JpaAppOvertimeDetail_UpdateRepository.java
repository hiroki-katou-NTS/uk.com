package nts.uk.ctx.at.request.infra.repository.application.overtime;

import java.util.Optional;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.arc.layer.infra.data.jdbc.NtsResultSet.NtsResultRecord;
import nts.arc.layer.infra.data.jdbc.NtsStatement;
import nts.arc.time.YearMonth;
import nts.uk.ctx.at.request.dom.application.overtime.AppOvertimeDetail_Update;
import nts.uk.ctx.at.request.dom.application.overtime.AppOvertimeDetail_UpdateRepository;
import nts.uk.ctx.at.request.dom.application.overtime.time36.Time36Agree;
import nts.uk.ctx.at.request.dom.application.overtime.time36.Time36AgreeMonth;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTimeMonth;

@Stateless
public class JpaAppOvertimeDetail_UpdateRepository extends JpaRepository implements AppOvertimeDetail_UpdateRepository{
	public static final String FIND_BY_ID = "SELECT *  "
			+ "FROM KRQDT_APP_OVERTIME as a"
			+ " WHERE a.APP_ID = @appID AND a.CID = @companyId";
	
	@Override
	public Optional<AppOvertimeDetail_Update> getAppOvertimeDetailById(String cid, String appId) {
		return new NtsStatement(FIND_BY_ID, this.jdbcProxy())
				.paramString("appID", appId)
				.paramString("companyId", cid)
				.getSingle(res -> toDomain(res));
	}

	private AppOvertimeDetail_Update toDomain(NtsResultRecord res) {
		AppOvertimeDetail_Update appOvertimeDetail_Update = new AppOvertimeDetail_Update();
		Integer applicationTime = res.getInt("APPLICATION_TIME");
		
		Integer yearMonth = res.getInt("YEAR_MONTH");
		
		Integer actualTime = res.getInt("ACTUAL_TIME");
		
		Integer limitErrorTime = res.getInt("LIMIT_ERROR_TIME");
		
		Integer limitAlarmTime = res.getInt("LIMIT_ALARM_TIME");
		
		Integer extLimitErrorTime = res.getInt("EXCEPTION_LIMIT_ERROR_TIME");

		Integer extLimitAlarmTime = res.getInt("EXCEPTION_LIMIT_ALARM_TIME");
		
		Integer numOfYear36Over = res.getInt("NUM_OF_YEAR36_OVER");
		
		Integer actualTimeYear = res.getInt("ACTUAL_TIME_YEAR");

		Integer limitTimeYear = res.getInt("LIMIT_TIME_YEAR");

		Integer regApplicationTime = res.getInt("REG_APPLICATION_TIME");

		Integer regActualTime = res.getInt("REG_ACTUAL_TIME");

		Integer regLimitTime = res.getInt("REG_LIMIT_TIME");
		
		Integer regLimitTimeMulti = res.getInt("REG_LIMIT_TIME_MULTI");
		
		Time36Agree time36Agree = new Time36Agree();
		AttendanceTimeMonth applicationTimeMonth = new AttendanceTimeMonth(applicationTime);
		time36Agree.setApplicationTime(applicationTimeMonth);
		appOvertimeDetail_Update.setYearMonth(new YearMonth(yearMonth));
		Time36AgreeMonth time36AgreeMonth = new Time36AgreeMonth();
		time36AgreeMonth.setActualTime(actualTime);
		
		
		
		
		return appOvertimeDetail_Update;
	}

}
