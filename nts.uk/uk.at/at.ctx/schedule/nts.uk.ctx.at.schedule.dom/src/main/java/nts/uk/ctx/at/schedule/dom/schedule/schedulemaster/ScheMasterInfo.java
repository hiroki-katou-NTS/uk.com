package nts.uk.ctx.at.schedule.dom.schedule.schedulemaster;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.arc.time.GeneralDate;

/**
 * 勤務予定マスタ情報
 * 
 * @author TanLV
 *
 */
@Getter
@AllArgsConstructor
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
}
