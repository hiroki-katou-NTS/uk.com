package nts.uk.ctx.at.request.dom.setting.employment;

import java.util.List;

import lombok.Getter;
import nts.uk.ctx.at.request.dom.application.ApplicationType_Old;
/**
 * 
 * @author Doan Duy Hung
 *
 */
@Getter
public class WorkTypeObjAppHoliday {
	
	/**
	 * 休暇申請種類
	 */
	private int holidayAppType;
	
	/**
	 * 休暇種類を利用しない
	 */
	private Boolean holidayTypeUseFlg;
	
	/**
	 * 勤務種類リスト
	 */
	private List<String> lstWorkType;
	
	/**
	 * 振休振出区分
	 */
	private int shipmentCategory;
	
	/**
	 * 申請種類
	 */
	private ApplicationType_Old appType;
	
	/**
	 * 表示する勤務種類を設定する
	 */
	private Boolean displayFlg;
	
}
