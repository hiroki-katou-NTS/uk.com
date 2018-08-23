package nts.uk.ctx.at.request.dom.setting.employment.appemploymentsetting;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import nts.arc.layer.dom.AggregateRoot;
import nts.uk.ctx.at.request.dom.application.ApplicationType;

/**
 * @author loivt
 * 申請別対象勤務種類,雇用別申請承認設定, 休暇申請対象勤務種類
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
	 * 休暇申請の種類
		 0: 年次有休
		 1: 代休
		 2: 欠勤
		 3: 特別休暇
		 4: 積立年休
		 5: 休日
		 6: 時間消化
		 7: 振休
		振休振出申請の種類振休振出区分
		 0: 振休
		 1: 振出
		その他：
		 9: その他
	 */
	private int holidayOrPauseType;
	/**
	 * 休暇種類を利用しない
	 * 申請種類は1:休暇申請しか使わない。
		利用:
		 0: 利用する
		 1: 利用しない
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
