package nts.uk.ctx.at.request.dom.setting.employment.appemploymentsetting;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import nts.arc.layer.dom.AggregateRoot;
import nts.uk.ctx.at.request.dom.application.ApplicationType;

/**
 * @author loivt
 * 申請別対象勤務種類
 */
@Getter
@Setter
@AllArgsConstructor
public class AppEmploymentSetting extends AggregateRoot{
	
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
	private ApplicationType appType;
	/**
	 * 休暇申請種類, 振休振出区分
	 */
	private int holidayOrPauseType;
	/**
	 * 休暇種類を利用しない
	 */
	private Boolean holidayTypeUseFlg;
	/**
	 * 表示する勤務種類を設定する
	 */
	private boolean displayFlag;
	/**
	 * 申請別対象勤務種類-勤務種類リスト
	 */
	List<AppEmployWorkType> lstWorkType;
}
