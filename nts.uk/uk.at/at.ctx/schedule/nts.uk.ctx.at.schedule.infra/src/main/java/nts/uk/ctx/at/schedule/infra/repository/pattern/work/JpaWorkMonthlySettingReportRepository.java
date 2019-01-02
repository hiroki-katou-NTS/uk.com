package nts.uk.ctx.at.schedule.infra.repository.pattern.work;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.schedule.app.export.shift.pattern.work.WorkMonthlySettingReportData;
import nts.uk.ctx.at.schedule.app.export.shift.pattern.work.WorkMonthlySettingReportRepository;

@Stateless
public class JpaWorkMonthlySettingReportRepository extends JpaRepository implements WorkMonthlySettingReportRepository {
	private static final String GET_WORK_MONTHLY_SET = (new StringBuffer()
			.append("SELECT a.M_PATTERN_CD as CODE, d.M_PATTERN_NAME as NAME, a.YMD_K as DATE")
			.append(",CONCAT(a.WORK_TYPE_CD , (CASE WHEN a.WORK_TYPE_CD IS NULL THEN  '' ELSE c.NAME END),")
			.append("(CASE WHEN a.WORKING_CD IS NULL OR a.WORKING_CD = '' THEN  '' ELSE CONCAT(',', a.WORKING_CD) END),")
			.append("(CASE WHEN a.WORKING_CD IS NULL THEN  '' ELSE b.NAME END)) AS WORK_SET_NAME")
			.append(" FROM KSCMT_WORK_MONTH_SET a")
			.append(" LEFT OUTER JOIN KSHMT_WORK_TIME_SET b ON (a.WORKING_CD IS NOT NULL) AND b.CID = a.CID AND b.WORKTIME_CD =  a.WORKING_CD")
			.append(" INNER JOIN KSHMT_WORKTYPE c ON c.CID = a.CID AND c.CD = a.WORK_TYPE_CD")
			.append(" INNER JOIN KSCMT_MONTH_PATTERN d ON d.CID = a.CID AND d.M_PATTERN_CD = a.M_PATTERN_CD")
			.append(" WHERE a.CID = ?companyId AND a.YMD_K >= ?startYm AND a.YMD_K <= ?endYm")
			.append(" ORDER BY a.M_PATTERN_CD, a.YMD_K"))
			.toString();

	@Override
	public Optional<Map<String, List<WorkMonthlySettingReportData>>> findAllWorkMonthlySet(String companyId,
			GeneralDate startDate, GeneralDate endDate) {
		List<?> data = this.getEntityManager().createNativeQuery(GET_WORK_MONTHLY_SET)
				.setParameter("companyId", companyId)
				.setParameter("startYm", startDate.date())
				.setParameter("endYm", endDate.date()).getResultList();

		List<WorkMonthlySettingReportData> workMonthlySettingReportDatas = data.stream().map(x -> toDomainWorkMonthlySet((Object[])x)).collect(Collectors.toList()); 
		return Optional.ofNullable(workMonthlySettingReportDatas.stream().collect(Collectors.groupingBy(WorkMonthlySettingReportData::getPattenCode)));
	}
	
	private static WorkMonthlySettingReportData toDomainWorkMonthlySet(Object[] object) {
		String pattenCode = (String) object[0];
		String patternName = (String) object[1];
		Timestamp timeStamp = (Timestamp)object[2];
		GeneralDate date = GeneralDate.legacyDate(new Date(timeStamp.getTime()));
		String workSetName = (String) object[3];
		
		WorkMonthlySettingReportData domain = WorkMonthlySettingReportData.createFromJavaType(
				pattenCode, patternName, date, workSetName);
		return domain;
	}
}
