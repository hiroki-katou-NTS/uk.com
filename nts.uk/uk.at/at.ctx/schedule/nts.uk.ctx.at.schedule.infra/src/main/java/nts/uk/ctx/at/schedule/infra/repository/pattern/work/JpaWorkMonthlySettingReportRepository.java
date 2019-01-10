package nts.uk.ctx.at.schedule.infra.repository.pattern.work;

import java.sql.Timestamp;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.schedule.app.export.shift.pattern.work.PersionalWorkMonthlySettingReportData;
import nts.uk.ctx.at.schedule.app.export.shift.pattern.work.WorkMonthlySettingReportData;
import nts.uk.ctx.at.schedule.app.export.shift.pattern.work.WorkMonthlySettingReportRepository;

@Stateless
public class JpaWorkMonthlySettingReportRepository extends JpaRepository implements WorkMonthlySettingReportRepository {
	private static final String GET_WORK_MONTHLY_SET = (new StringBuffer()
			.append("SELECT a.M_PATTERN_CD as CODE, d.M_PATTERN_NAME as NAME, a.YMD_K as DATE")
			.append(", a.WORK_TYPE_CD, a.WORKING_CD, c.NAME as WORK_TYPE_NAME, b.NAME as WORK_TIME_NAME")
			.append(" FROM KSCMT_WORK_MONTH_SET a")
			.append(" LEFT OUTER JOIN KSHMT_WORK_TIME_SET b ON (a.WORKING_CD IS NOT NULL) AND b.CID = a.CID AND b.WORKTIME_CD =  a.WORKING_CD")
			.append(" INNER JOIN KSHMT_WORKTYPE c ON c.CID = a.CID AND c.CD = a.WORK_TYPE_CD")
			.append(" INNER JOIN KSCMT_MONTH_PATTERN d ON d.CID = a.CID AND d.M_PATTERN_CD = a.M_PATTERN_CD")
			.append(" WHERE a.CID = ?companyId AND a.YMD_K >= ?startYm AND a.YMD_K <= ?endYm")
			.append(" ORDER BY a.M_PATTERN_CD, a.YMD_K"))
			.toString();
	
	private static final String GET_PERSION_WORK_MONTH_SET = (new StringBuffer()
			.append("SELECT a.SCD, b.BUSINESS_NAME, c.START_DATE, c.END_DATE, ISNULL(d.MONTHLY_PATTERN, '') as MONTHLY_PATTERN, ISNULL(e.M_PATTERN_NAME, 'マスタ未登録') as M_PATTERN_NAME")
			.append(" FROM BSYMT_EMP_DTA_MNG_INFO a")
			.append(" INNER JOIN BPSMT_PERSON b on a.PID = b.PID")
			.append(" INNER JOIN KSHMT_WORKING_COND c on c.SID = a.SID and c.START_DATE <= ?baseDate and c.END_DATE >= ?baseDate")
			.append(" INNER JOIN KSHMT_WORKING_COND_ITEM d on c.HIST_ID = d.HIST_ID")
			.append(" LEFT OUTER JOIN KSCMT_MONTH_PATTERN e on e.M_PATTERN_CD = d.MONTHLY_PATTERN and a.CID = e.CID")
			.append(" WHERE a.CID = ?companyId ")
			.append(" ORDER BY a.SCD")).toString();

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
		//a.WORK_TYPE_CD, a.WORKING_CD, c.NAME as WORK_TYPE_NAME, b.NAME as WORK_TIME_NAME
		String pattenCode = (String) object[0];
		String patternName = (String) object[1];
		String timeStamp = ((Timestamp)object[2]).toString();
		GeneralDate date = GeneralDate.fromString(timeStamp, "yyyy-MM-dd hh:mm:ss.s");
		StringBuffer workSetName = new StringBuffer();
		
		String workTypeCD = (String) object[3];
		String workingCD = (String) object[4];
		String workingTypeName = (String) object[5];
		String workTimeName = (String) object[6];
		if (workTypeCD != null) {
			workSetName.append(workTypeCD);
			workSetName.append(workingTypeName);
			if (workingCD != null) {
				workSetName.append("," + workingCD);
				if (workTimeName != null) {
					workSetName.append(workTimeName);
				}
				else {
					workSetName.append("マスタ未登録");
				}
			}
		}
		
		WorkMonthlySettingReportData domain = WorkMonthlySettingReportData.createFromJavaType(
				pattenCode, patternName, date, workSetName.toString());
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
		String startTimeStamp = ((Timestamp)object[2]).toString();
		GeneralDate startDate = GeneralDate.fromString(startTimeStamp, "yyyy-MM-dd hh:mm:ss.s");
		String endTimeStamp = ((Timestamp)object[3]).toString();
		GeneralDate endDate = GeneralDate.fromString(endTimeStamp, "yyyy-MM-dd hh:mm:ss.s");
		String patternCode = (String) object[4];
		String patternName = (String) object[5];
		
		PersionalWorkMonthlySettingReportData domain = PersionalWorkMonthlySettingReportData.createFromJavaType(
				scd, name, startDate, endDate, patternCode, patternName);
		return domain;
	}
}
