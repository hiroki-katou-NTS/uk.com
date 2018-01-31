package nts.uk.ctx.at.schedule.dom.schedule.schedulemaster;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.arc.enums.EnumAdaptor;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.schedule.dom.schedule.setting.displaycontrol.PersonSymbolQualify;
import nts.uk.ctx.at.schedule.dom.schedule.setting.displaycontrol.ScheDispControl;
import nts.uk.ctx.at.schedule.dom.schedule.setting.functioncontrol.UseAtr;

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
	private String employeeCd;
	
	/** 分類コード */
	private String classificationCd;
	
	/** 勤務種別コード */
	private String workTypeCd;
	
	/** 職位ID */
	private String jobId;
	
	/** 職場ID */
	private String workplaceId;

	public static ScheMasterInfo createFromJavaType(String sId, GeneralDate generalDate, String employeeCd,
			String classificationCd, String workTypeCd, String jobId, String workplaceId) {

		return new ScheMasterInfo(sId, generalDate, employeeCd, classificationCd, workTypeCd, jobId, workplaceId);
	}
}
