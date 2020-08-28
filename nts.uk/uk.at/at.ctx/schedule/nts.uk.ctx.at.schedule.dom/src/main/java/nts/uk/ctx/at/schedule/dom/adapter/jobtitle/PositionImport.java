package nts.uk.ctx.at.schedule.dom.adapter.jobtitle;

import lombok.Getter;
import lombok.Setter;

/**
 * 職位 Imported
 * path: UKDesign.ドメインモデル.NittsuSystem.UniversalK.就業.shared.就業規則.組織管理.職位.職位
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
	/**序列コード **/
	private String sequenceCode;

	public PositionImport(String jobId, String jobCd, String jobName,String sequenceCode) {
		super();
		this.jobId = jobId;
		this.jobCd = jobCd;
		this.jobName = jobName;
		this.sequenceCode = sequenceCode;
	}
	
}
