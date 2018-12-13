package nts.uk.file.com.infra.JobInfo;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.file.com.app.JobInfo.JobInfoColumn;
import nts.uk.file.com.app.JobInfo.JobInfoRepository;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.infra.file.report.masterlist.data.MasterData;

@Stateless
public class JobInfoImpl extends JpaRepository implements JobInfoRepository {
	
	@PersistenceContext
	private EntityManager entityManager;
	
	private static final String GET_EXPORT_EXCEL = 
			"SELECT CASE WHEN TBL.ROW_NUMBER = 1 THEN TBL.APPLY_CONCURRENT_PERSON"
			+" ELSE NULL END APPLY_CONCURRENT_PERSON,"
			+" TBL.JOB_CD, TBL.JOB_NAME, TBL.ROLESET_NAME"
			+" FROM (SELECT job.APPLY_CONCURRENT_PERSON, info.JOB_CD, info.JOB_NAME, d.ROLESET_CD +' '+ s.ROLE_SET_NAME AS ROLESET_NAME,"
			+" ROW_NUMBER() OVER (ORDER BY info.JOB_CD ASC) AS ROW_NUMBER"
			+" FROM "
			+" BSYMT_JOB_INFO info"
			+" INNER JOIN BSYMT_JOB_HIST his ON info.CID = his.CID AND info.HIST_ID = his.HIST_ID AND info.JOB_ID = his.JOB_ID"
			+" INNER JOIN SACMT_ROLE_SET s ON info.CID = s.CID"
			+" INNER JOIN SACMT_ROLESET_JOB_DETAIL d ON s.ROLE_SET_CD = d.ROLESET_CD AND info.JOB_ID = d.JOB_ID"
			+" INNER JOIN SACMT_ROLESET_JOB job ON info.CID = job.CID"
			+" WHERE info.CID = ?cid"
			+" AND his.START_DATE <= CONVERT(DATETIME, ?date, 127) "
			+" AND his.END_DATE >= CONVERT(DATETIME, ?date, 127)) TBL ";
	private static final String GET_EXPORT_DATA = 
			" SELECT CASE WHEN TBL.ROW_NUMBER = 1 THEN TBL.ROLESET_CD ELSE NULL END ROLESET_CD, "
			+" CASE WHEN TBL.ROW_NUMBER = 1 THEN TBL.ROLE_SET_NAME ELSE NULL END ROLE_SET_NAME, "
			+" TBL.SCD, TBL.BUSINESS_NAME, TBL.START_DATE, TBL.END_DATE FROM "
			+ "(SELECT per.ROLESET_CD, rs.ROLE_SET_NAME, em.SCD, p.BUSINESS_NAME, per.START_DATE, per.END_DATE,"
				+" ROW_NUMBER() OVER (PARTITION BY per.ROLESET_CD "
				+" ORDER BY  rs.ROLE_SET_CD ASC, em.SCD ASC) AS ROW_NUMBER "
				+" FROM SACMT_ROLESET_PERSON per"
				+" INNER JOIN SACMT_ROLE_SET rs ON rs.CID = per.CID AND rs.ROLE_SET_CD = per.ROLESET_CD" 
				+" INNER JOIN BSYMT_EMP_DTA_MNG_INFO em ON per.SID = em.SID"
				+" INNER JOIN BSYMT_AFF_COM_HIST aff ON per.SID = aff.SID" 
				+" INNER JOIN BPSMT_PERSON p ON aff.PID = p.PID "
				+" WHERE rs.CID = ?cid) TBL";
	
	@Override
	public List<MasterData> getDataRoleSetPosExport(String date) {
		String cid = AppContexts.user().companyId();
		List<MasterData> datas = new ArrayList<>();
		Query query = entityManager.createNativeQuery(GET_EXPORT_EXCEL.toString()).setParameter("cid", cid).setParameter("date", date);
		@SuppressWarnings("unchecked")
		List<Object[]> data = query.getResultList();
		for (Object[] objects : data) {
			datas.add(new MasterData(buildRoleSetPosRow(objects), null, ""));
		}
		return datas;
	}
	
	private Map<String, Object> buildRoleSetPosRow(Object[] object) {
		Map<String, Object> data = new HashMap<>();
		// R7_1
		data.put(JobInfoColumn.CAS014_41, object[0] != null ? ((BigDecimal) object[0]).intValue()  == 1 ? "○" : "ー" : null);
		// R7_2
		data.put(JobInfoColumn.CAS014_42, object[1] != null ? (String) object[1] : null);
		// R7_3
		data.put(JobInfoColumn.CAS014_43, object[2] != null ? (String) object[2] : null);	
		// R7_4
		data.put(JobInfoColumn.CAS014_44, object[3] != null ? (String) object[3] : null);
		return data;
	}
	
	@Override
	public List<MasterData> getDataRoleSetEmpExport(){
		String cid = AppContexts.user().companyId();
		List<MasterData> datas = new ArrayList<>();
		Query querydata = entityManager.createNativeQuery(GET_EXPORT_DATA.toString()).setParameter("cid", cid);
		@SuppressWarnings("unchecked")
		List<Object[]> data = querydata.getResultList();
		DateFormat df = new SimpleDateFormat("yyyy/MM/dd");
		for (Object[] objects : data) {
			datas.add(new MasterData(buildRoleSetEmpRow(objects, df), null, ""));
		}
		return datas;
	}
	
	private Map<String, Object> buildRoleSetEmpRow(Object[] object, DateFormat df) {
		Map<String, Object> data = new HashMap<>();
		// R9_1
		data.put(JobInfoColumn.CAS014_45, object[0] != null ? (String) object[0] : null);
		// R9_2
		data.put(JobInfoColumn.CAS014_46, object[1] != null ? (String) object[1] : null);
		// R9_3
		data.put(JobInfoColumn.CAS014_47, object[2] != null ? (String) object[2] : null);	
		// R9_4
		data.put(JobInfoColumn.CAS014_48, object[3] != null ? (String) object[3] : null);
		// R9_5
		data.put(JobInfoColumn.CAS014_49, object[4] != null ? df.format(object[4]) : null);
		// R9_6
		data.put(JobInfoColumn.CAS014_50, object[5] != null ? df.format(object[5]) : null);
		return data;
	}
}
