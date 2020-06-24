package nts.uk.ctx.at.request.dom.setting.employment.appemploymentsetting;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.arc.enums.EnumAdaptor;
import nts.uk.ctx.at.request.dom.application.ApplicationType_Old;

/**
 * @author loivt
 * 申請別対象勤務種類-勤務種類リスト
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AppEmployWorkType {
	/**
	 * 会社ID
	 * companyID
	 */
	private String companyID;
	
	/**
	 * 雇用コード
	 */
	private String employmentCode;
	/**
	 * 申請種類
	 */
	private ApplicationType_Old appType;
	/**
	 * 休暇申請種類, 振休振出区分
	 */
	private int holidayOrPauseType;
	/**
	 * 表示する勤務種類を設定する
	 */
	private String workTypeCode;
	
	public static AppEmployWorkType createSimpleFromJavaType(String companyID, String employmentCode, int appType, int holidayOrPauseType, String workTypeCode){
		return new AppEmployWorkType(companyID,
				employmentCode,
				EnumAdaptor.valueOf(appType,ApplicationType_Old.class),
				holidayOrPauseType,
				workTypeCode);
	}
}
