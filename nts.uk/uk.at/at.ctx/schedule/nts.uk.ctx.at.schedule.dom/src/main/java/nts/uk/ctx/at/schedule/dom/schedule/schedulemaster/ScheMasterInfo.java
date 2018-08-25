package nts.uk.ctx.at.schedule.dom.schedule.schedulemaster;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import nts.arc.time.GeneralDate;

/**
 * 勤務予定マスタ情報
 * 
 * @author TanLV
 *
 */
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ScheMasterInfo {
	/** 社員ID */
	private String sId;
	
	/** 年月日 */
	private GeneralDate generalDate;
	
	/** 雇用コード */
	private String employmentCd;
	
	/** 分類コード */
	private String classificationCd;
	
	/** 勤務種別コード */
	private String businessTypeCd;
	
	/** 職位ID */
	private String jobId;
	
	/** 職場ID */
	private String workplaceId;

	public static ScheMasterInfo createFromJavaType(String sId, GeneralDate generalDate, String employmentCd,
			String classificationCd, String workTypeCd, String jobId, String workplaceId) {

		return new ScheMasterInfo(sId, generalDate, employmentCd, classificationCd, workTypeCd, jobId, workplaceId);
	}

	public ScheMasterInfo(String workplaceId) {
		super();
		this.workplaceId = workplaceId;
	}
	
	public ScheMasterInfo(String businessTypeCd, String workplaceId) {
		super();
		this.workplaceId = workplaceId;
		this.businessTypeCd = businessTypeCd;
	}
	
	public boolean diffEmploymentCd(String empCd){
		return !employmentCd.equals(empCd);
	}
	
	public boolean diffClassificationCd(String claCd){
		if(classificationCd == null && claCd == null){
			return false;
		}
		if(classificationCd == null && claCd != null){
			return true;
		}
		
		return !classificationCd.equals(claCd);
	}
	
	public boolean diffWorkplaceId(String workpId){
		return !workplaceId.equals(workpId);
	}
	
	public boolean diffJobId(String jobbId){
		return !jobId.equals(jobbId);
	}
}
