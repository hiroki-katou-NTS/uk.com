package nts.uk.file.com.app.sequence;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import nts.arc.time.GeneralDate;

@Getter
@Setter
@AllArgsConstructor
public class SequenceMasterExportData {
	
	private String cid;
	private String jobCd;
	private String jobName;
	private GeneralDate startDate;
	private GeneralDate endDate;
	private int isManager;
	private String sequenceCode;	
	public static SequenceMasterExportData createFromJavaType(String cid,
			String jobCd, String jobName, GeneralDate startDate, GeneralDate endDate, int isManager, String sequenceCode) {
		return new SequenceMasterExportData(
				cid, 
				jobCd,
				jobName,
				startDate,
				endDate,
				isManager,
				sequenceCode);
	}
}
