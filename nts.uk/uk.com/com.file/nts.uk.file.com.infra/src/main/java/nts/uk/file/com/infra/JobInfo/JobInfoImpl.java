package nts.uk.file.com.infra.JobInfo;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import nts.uk.file.com.app.JobInfo.JobInfoColumn;
import nts.uk.file.com.app.JobInfo.JobInfoRepository;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.infra.file.report.masterlist.data.MasterData;

@Stateless
public class JobInfoImpl implements JobInfoRepository {
	
	@PersistenceContext
	private EntityManager entityManager;
	
	private static final String GET_EXPORT_EXCEL = 
			"  SELECT job.APPLY_CONCURRENT_PERSON, info.JOB_CD, info.JOB_NAME, d.ROLE_SET_CD +' '+ s.ROLE_SET_NAME AS ROLESET_NAME"
			+" FROM BSYMT_JOB_INFO info INNER JOIN BSYMT_JOB_HIST his ON info.CID = his.CID AND info.HIST_ID = his.HIST_ID AND info.JOB_ID = his.JOB_ID "
						+" INNER JOIN SACMT_ROLE_SET s ON info.CID = s.CID "
						+" INNER JOIN SACMT_ROLESET_JOB_DETAIL d ON s.ROLE_SET_CD = d.ROLESET_CD AND info.JOB_ID = d.JOB_ID"
						+" INNER JOIN SACMT_ROLESET_JOB job ON info.CID = job.CID"
			+" WHERE info.CID = ?cid AND his.START_DATE <= ?date AND his.END_DATE >= ?date"
			+" ORDER BY info.JOB_CD";
	
	@Override
	public List<MasterData> getDataExport(String date) {
		String cid = AppContexts.user().companyId();
		List<MasterData> datas = new ArrayList<>();
		Query query = entityManager.createNativeQuery(GET_EXPORT_EXCEL.toString()).setParameter("cid", cid).setParameter("date", date);
		List<Object[]> data = query.getResultList();
		for (Object[] objects : data) {
			datas.add(new MasterData(dataContent(objects), null, ""));
		}
		return datas;
	}
	
	private Map<String, Object> dataContent(Object[] object) {
		Map<String, Object> data = new HashMap<>();
		// R7_1
		data.put(JobInfoColumn.CAS014_41, ((BigDecimal) object[0]).intValue()  == 1 ? "○" : "ー");
		// R7_2
		data.put(JobInfoColumn.CAS014_42, object[1] != null ? (String) object[1] : null);
		// R7_3
		data.put(JobInfoColumn.CAS014_43, object[2] != null ? (String) object[2] : null);	
		// R7_4
		data.put(JobInfoColumn.CAS014_44, object[3] != null ? (String) object[3] : null);
		return data;
	}

}
