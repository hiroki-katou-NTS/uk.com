package nts.uk.ctx.at.shared.app.workrule.workinghours;

import javax.ejb.Stateless;

import nts.arc.error.BusinessException;
import nts.uk.ctx.at.shared.dom.WorkInformation;
import nts.uk.shr.com.i18n.TextResource;

/**
 * TODO:«Query» 時刻が不正かチェックする
 * UKDesign.ドメインモデル.NittsuSystem.UniversalK.就業.shared.就業規則.就業時間帯.App.時刻が不正かチェックする
 * @author tutk
 *
 */
@Stateless
public class CheckTimeIsIncorrect {

	/**
	 * チェックする 
	 * @param workType 勤務種類: 勤務種類コード
	 * @param workTime  就業時間帯: 就業時間帯コード
	 * @param workTime1 , 勤務時間帯1: 時間帯(実装コードなし/使用不可)
	 * @param workTime2 勤務時間帯2: 時間帯(実装コードなし/使用不可)
	 */
	public boolean check(String workType,String workTime,TimeZoneDto workTime1,TimeZoneDto workTime2) {
		//1:Create()
		WorkInformation wi = new WorkInformation(workType, workTime);
		//TODO: 2: 変更可能な勤務時間帯のチェック(Require, 対象時刻区分, 勤務NO, 時刻(日区分付き))
		IsInculededTimeZone check1 = new IsInculededTimeZone();
		//3: 開始1の状態.含まれているか == false
		if(!check1.isInculedeCheck()) {
			throw new BusinessException("Msg_1772",TextResource.localize("KSU001_54",check1.getTimeZone().getStartTime().getTime().toString(),check1.getTimeZone().getEndTime().getTime().toString() ));
		}
		//4:変更可能な勤務時間帯のチェック(Require, 対象時刻区分, 勤務NO, 時刻(日区分付き))
		IsInculededTimeZone check2 = new IsInculededTimeZone();
		//5:終了1の状態.含まれているか == false
		if(!check2.isInculedeCheck()) {
			throw new BusinessException("Msg_1772",TextResource.localize("KSU001_55",check2.getTimeZone().getStartTime().getTime().toString(),check2.getTimeZone().getEndTime().getTime().toString() ));
		}
		
		//6:
		if(workTime2 != null) {
			//6.1
			IsInculededTimeZone check3 = new IsInculededTimeZone();
			if(!check3.isInculedeCheck()) {
				throw new BusinessException("Msg_1772",TextResource.localize("KSU001_56",check3.getTimeZone().getStartTime().getTime().toString(),check3.getTimeZone().getEndTime().getTime().toString() ));
			}
			
			//6.2
			IsInculededTimeZone check4 = new IsInculededTimeZone();
			if(!check3.isInculedeCheck()) {
				throw new BusinessException("Msg_1772",TextResource.localize("KSU001_57",check4.getTimeZone().getStartTime().getTime().toString(),check4.getTimeZone().getEndTime().getTime().toString() ));
			}
		}
		return false;
	}
}
