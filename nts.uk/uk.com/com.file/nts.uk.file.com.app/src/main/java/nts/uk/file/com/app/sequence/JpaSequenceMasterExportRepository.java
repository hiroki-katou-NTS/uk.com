package nts.uk.file.com.app.sequence;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.bs.employee.dom.jobtitle.sequence.SequenceCode;

@Stateless
public class JpaSequenceMasterExportRepository extends JpaRepository implements SequenceMasterExportRepository {

	
	private static final String GET_BSYMT_JOB_HIST = (new StringBuffer()
			.append("SELECT c.CID,c.JOB_CD, c.JOB_NAME, b.START_DATE, b.END_DATE, c.IS_MANAGER, c.SEQUENCE_CD")
			.append(" FROM BSYMT_JOB_INFO c INNER JOIN BSYMT_JOB_HIST b ")
			.append(" ON c.CID = ?companyId  and c.CID = b.CID and c.HIST_ID = b.HIST_ID ")
			.append(" AND c.JOB_ID = b.JOB_ID and b.START_DATE <= ?baseDate")
			.append(" AND b.END_DATE >= ?baseDate"))
			.toString();

	@Override
	public List<SequenceMasterExportData> findAll(String companyId, GeneralDate baseDate) {
		List<?> data = this.getEntityManager().createNativeQuery(GET_BSYMT_JOB_HIST)
				.setParameter("companyId", companyId)
				.setParameter("baseDate", baseDate.date()).getResultList();
		
		
		return  data.stream().map(x -> toDomainWorkMonthlySet((Object[])x)).collect(Collectors.toList()); 
		
	}
	
	private static SequenceMasterExportData toDomainWorkMonthlySet(Object[] object) {
		String cid = (String) object[0];
		String jobCd = (String) object[1];
		String  jobName = (String)object[2];
		
		String timeStampStart = ((Timestamp)object[3]).toString();
		GeneralDate startDate = GeneralDate.fromString(timeStampStart, "yyyy-MM-dd hh:mm:ss.s");//1900-01-01 00:00:00.0
		String timeStampEnd = ((Timestamp)object[4]).toString();
		GeneralDate endDate = GeneralDate.fromString(timeStampEnd, "yyyy-MM-dd hh:mm:ss.s");
		int isManager = ((BigDecimal)object[5]).intValue();
		String sequenceCode = (String)object[6];
		
		
		SequenceMasterExportData data =  SequenceMasterExportData.createFromJavaType(
				cid, 
				jobCd,
				jobName,
				startDate,
				endDate,
				isManager, 
				sequenceCode);

		return data;
	}

}
