package nts.uk.ctx.at.schedule.infra.repository.pattern.work;

import java.sql.Timestamp;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.arc.time.GeneralDate;
import nts.gul.text.StringUtil;
import nts.uk.ctx.at.schedule.app.export.shift.pattern.work.PersionalWorkMonthlySettingReportData;
import nts.uk.ctx.at.schedule.app.export.shift.pattern.work.WorkMonthlySettingReportData;
import nts.uk.ctx.at.schedule.app.export.shift.pattern.work.WorkMonthlySettingReportRepository;
import nts.uk.shr.com.i18n.TextResource;

@Stateless
public class JpaWorkMonthlySettingReportRepository extends JpaRepository implements WorkMonthlySettingReportRepository {
	private static final String GET_WORK_MONTHLY_SET_UPDATE = (new StringBuffer()
			.append("SELECT d.M_PATTERN_CD, d.M_PATTERN_NAME AS NAME, a.YMD_K AS DATE ,")
			.append("a.WORK_TYPE_CD, c.NAME AS WORK_TYPE_NAME, a.WORKING_CD, b.NAME AS WORK_TIME_NAME, a1.EVENT_NAME")
			.append(" FROM KSCMT_MONTH_PATTERN d")
			.append(" LEFT JOIN KSCMT_WORK_MONTH_SET a ON d.CID = a.CID AND d.M_PATTERN_CD = a.M_PATTERN_CD ")
			.append(" AND a.YMD_K >= ?startYm AND a.YMD_K <= ?endYm ")
			.append(" LEFT JOIN KSHMT_WKTP c ON c.CID = a.CID AND c.CD = a.WORK_TYPE_CD")
			.append(" LEFT JOIN KSHMT_WT b ON b.CID = a.CID AND b.WORKTIME_CD =  a.WORKING_CD")
			.append(" LEFT JOIN KSCMT_EVENT_COM a1 ON a1.CID = a.CID AND a1.YMD_K =  a.YMD_K")
			.append(" WHERE d.CID = ?companyId ")
			.append(" ORDER BY d.M_PATTERN_CD ASC, a.YMD_K ASC"))
			.toString();
	
	private static final String GET_PERSION_WORK_MONTH_SET = (new StringBuffer()
			.append("SELECT DISTINCT a.SCD, a.BUSINESS_NAME,")
			.append(" c.START_DATE, c.END_DATE, ISNULL(d.MONTHLY_PATTERN, '') as MONTHLY_PATTERN, ISNULL(e.M_PATTERN_NAME, '') as M_PATTERN_NAME")
			.append(", a.SID")
			.append(" FROM KSHMT_WORKCOND_HIST c ")
//			.append(" FROM EMPLOYEE_DATA_VIEW a")
			.append(" LEFT JOIN EMPLOYEE_DATA_VIEW a ON c.SID = a.SID AND c.CID = a.CID ")
//			.append(" AND c.START_DATE <= ?baseDate AND c.END_DATE >= ?baseDate ")
			.append(" LEFT JOIN KSHMT_WORKCOND_HIST_ITEM d ON c.HIST_ID = d.HIST_ID AND c.SID = d.SID")
			.append(" LEFT JOIN KSCMT_MONTH_PATTERN e ON e.M_PATTERN_CD = d.MONTHLY_PATTERN AND e.CID = c.CID")
//			.append(" LEFT JOIN KSHMT_WORKCOND_SCHE_METH f ON f.HIST_ID = c.HIST_ID AND c.SID = f.SID")
			.append(" WHERE c.CID = ?companyId ")
			.append(" AND c.START_DATE <= ?baseDate AND c.END_DATE >= ?baseDate AND a.DEL_STATUS_ATR = 0 ")
//			.append(" AND a.EMPLOYMENT_STR_DATE <= ?baseDate AND a.EMPLOYMENT_END_DATE >= ?baseDate")
//			.append(" AND a.WPL_STR_DATE <= ?baseDate AND a.WPL_END_DATE >= ?baseDate")
//			.append(" AND a.WPL_INFO_STR_DATE <= ?baseDate AND a.WPL_INFO_END_DATE >= ?baseDate")
//			.append(" AND a.WKP_CONF_STR_DT <= ?baseDate AND a.WKP_CONF_END_DT >= ?baseDate")
//			.append(" AND a.CLASS_STR_DATE <= ?baseDate AND a.CLASS_END_DATE >= ?baseDate")
//			.append(" AND a.JOB_STR_DATE <= ?baseDate AND a.JOB_END_DATE >= ?baseDate")
//			.append(" AND a.JOB_INFO_STR_DATE <= ?baseDate AND a.JOB_INFO_END_DATE >= ?baseDate")
//			.append(" AND (")
//			//includeIncumbents
//			.append(" (")
//			//isWorking
//			.append(" ((a.TEMP_ABS_FRAME_NO IS NULL AND a.ABS_STR_DATE IS NULL) OR (a.ABS_STR_DATE > ?baseDate) ")
//			.append(" OR (a.ABS_END_DATE < ?baseDate))")
//			//isInCompany")
//			.append(" AND NOT(a.COM_STR_DATE >?baseDate OR a.COM_END_DATE < ?baseDate ))")
//			.append(" OR ")
//			//workerOnLeave
//			.append(" ((NOT (a.COM_STR_DATE >?baseDate OR a.COM_END_DATE < ?baseDate )")
//			.append(" AND NOT ((a.TEMP_ABS_FRAME_NO IS NULL AND a.ABS_STR_DATE IS NULL) OR (a.ABS_STR_DATE > ?baseDate) OR (a.ABS_END_DATE < ?baseDate))")
//			.append(" AND a.TEMP_ABS_FRAME_NO = 1))")
//			.append(" OR ")
//			//occupancy
//			.append(" (NOT(a.COM_STR_DATE >?baseDate OR a.COM_END_DATE < ?baseDate ) ")
//			.append(" AND NOT ((a.TEMP_ABS_FRAME_NO IS NULL AND a.ABS_STR_DATE IS NULL) OR (a.ABS_STR_DATE > ?baseDate) OR (a.ABS_END_DATE < ?baseDate))")
//			.append(" AND a.TEMP_ABS_FRAME_NO <> 1)")
//			.append(" OR ")
//			//retire
//			.append(" (a.COM_END_DATE >= '1900-01-01 00:00:00' AND a.COM_END_DATE <= '9999-12-31 00:00:00' AND a.COM_END_DATE != '9999-12-31 00:00:00'))")
//			.append(" AND (f.BASIC_CREATE_METHOD IS NULL OR f.BASIC_CREATE_METHOD = 1)")
			.append(" ORDER BY a.SCD")).toString();

	@Override
	public Optional<Map<String, List<WorkMonthlySettingReportData>>> findAllWorkMonthlySet(String companyId,
			GeneralDate startDate, GeneralDate endDate) {
		List<?> data = this.getEntityManager().createNativeQuery(GET_WORK_MONTHLY_SET_UPDATE)
				.setParameter("companyId", companyId)
				.setParameter("startYm", startDate.date())
				.setParameter("endYm", endDate.date()).getResultList();

		List<WorkMonthlySettingReportData> workMonthlySettingReportDatas = data.stream().map(x -> toDomainWorkMonthlySet((Object[])x)).collect(Collectors.toList()); 
		return Optional.ofNullable(workMonthlySettingReportDatas.stream().collect(Collectors.groupingBy(WorkMonthlySettingReportData::getPattenCode)));
	}
	
	private static WorkMonthlySettingReportData toDomainWorkMonthlySet(Object[] object) {
		// d.M_PATTERN_CD, d.M_PATTERN_NAME as NAME, a.YMD_K as DATE, 
		// c.CD, c.NAME as WORK_TYPE_NAME, b.WORKTIME_CD, b.NAME as WORK_TIME_NAME
		String pattenCode = (String) object[0];
		String patternName = (String) object[1];
		
		Optional<GeneralDate> date = Optional.empty();
		if (object[2] != null) {
		String timeStamp = ((Timestamp)object[2]).toString();
			date = Optional.ofNullable(GeneralDate.fromString(timeStamp, "yyyy-MM-dd hh:mm:ss.s"));
		}
		StringBuffer workSetNameBuf = new StringBuffer();
		
		String workTypeCD = (String) object[3];
		String workingTypeName = (String) object[4];
		String workingCD = (String) object[5];
		String workTimeName = (String) object[6];
		String eventName = (String) object[7];
		if (workTypeCD != null) {
			workSetNameBuf.append(workTypeCD);
			if (workingTypeName != null) {
				workSetNameBuf.append(workingTypeName);
			}
			else {
				workSetNameBuf.append(TextResource.localize("KSM005_84"));
			}
			
			if (workingCD != null) {
				workSetNameBuf.append("," + workingCD);
				if (workTimeName != null) {
					workSetNameBuf.append(workTimeName);
				}
				else {
					workSetNameBuf.append(TextResource.localize("KSM005_84"));
				}
			}
		}
		
		String workSetName = workSetNameBuf.toString();
		WorkMonthlySettingReportData domain = WorkMonthlySettingReportData.createFromJavaType(
				pattenCode, patternName, date, 
				!StringUtil.isNullOrEmpty(workSetName, true) 
				? Optional.ofNullable(workSetName) : Optional.empty(),eventName);
		return domain;
	}
	
	
	@Override
	public Optional<List<PersionalWorkMonthlySettingReportData>> findAllPersionWorkMonthlySet(String companyId,
			GeneralDate baseDate) {
		List<?> data = this.getEntityManager().createNativeQuery(GET_PERSION_WORK_MONTH_SET)
				.setParameter("companyId", companyId)
				.setParameter("baseDate", baseDate.date()).getResultList();

		return Optional.ofNullable(data.stream().map(x -> toDomainPersionWorkMonthlySet((Object[])x)).collect(Collectors.toList())); 
//		return Optional.ofNullable(workMonthlySettingReportDatas.stream().collect(Collectors.toMap(PersionalWorkMonthlySettingReportData::getScd, Function.identity())));
	}
	
	private static PersionalWorkMonthlySettingReportData toDomainPersionWorkMonthlySet(Object[] object) {
		//a.SCD, b.BUSINESS_NAME, c.START_DATE, c.END_DATE, d.MONTHLY_PATTERN, e.M_PATTERN_NAME
		String scd = (String) object[0];
		String name = (String) object[1];
		
		GeneralDate startDate = null;
		if (object[2] != null) {
			String startTimeStamp = ((Timestamp)object[2]).toString();
			startDate = GeneralDate.fromString(startTimeStamp, "yyyy-MM-dd hh:mm:ss.s");
		}
		
		GeneralDate endDate = null;
		if (object[3] != null) {
			String endTimeStamp = ((Timestamp)object[3]).toString();
			endDate = GeneralDate.fromString(endTimeStamp, "yyyy-MM-dd hh:mm:ss.s");
		}
		
		String patternCode = (String) object[4];
		String patternName = (String) object[5];
		if (!StringUtil.isNullOrEmpty(patternCode, true) && StringUtil.isNullOrEmpty(patternName, true)) {
			patternName = TextResource.localize("KSM005_84");
		}
		String sid = (String) object[6];
		PersionalWorkMonthlySettingReportData domain = PersionalWorkMonthlySettingReportData.createFromJavaType(sid, 
				scd, name, startDate, endDate, patternCode, patternName);
		return domain;
	}
}
