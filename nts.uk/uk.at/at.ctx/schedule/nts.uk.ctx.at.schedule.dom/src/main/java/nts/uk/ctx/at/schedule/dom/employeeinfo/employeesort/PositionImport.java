package nts.uk.ctx.at.schedule.dom.employeeinfo.employeesort;

import lombok.Getter;
import lombok.Setter;

/**
 * 職位 Imported
 * @author HieuLt
 *
 */
@Getter
@Setter
public class PositionImport {
	
	/**職位ID **/
	private String jobId;
	/**職位コード **/
	private String jobCd;
	/**職位名称 **/
	private String jobName;
	/**序列 **/ 
	private int rank;
	public PositionImport(String jobId, String jobCd, String jobName, int rank) {
		super();
		this.jobId = jobId;
		this.jobCd = jobCd;
		this.jobName = jobName;
		this.rank = rank;
	}
	
}
