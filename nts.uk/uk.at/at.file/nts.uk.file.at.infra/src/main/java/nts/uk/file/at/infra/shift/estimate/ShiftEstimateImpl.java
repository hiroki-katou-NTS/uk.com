package nts.uk.file.at.infra.shift.estimate;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.file.at.app.export.shift.estimate.ShiftEstimateRepository;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.infra.file.report.masterlist.data.MasterData;

@Stateless
public class ShiftEstimateImpl extends JpaRepository implements ShiftEstimateRepository {
	
	@PersistenceContext
	private EntityManager entityManager;
	
	private static final String GET_EXPORT_EXCEL = 
			"  SELECT job.APPLY_CONCURRENT_PERSON, info.JOB_CD, info.JOB_NAME, d.ROLESET_CD +' '+ s.ROLE_SET_NAME AS ROLESET_NAME"
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

		return data;
	}

}
