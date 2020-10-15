package nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.applicationsetting.applicationtypesetting.service;

import javax.ejb.Stateless;

/**
 * 対象日が申請可能かを判定する
 *
 */
@Stateless
public class ApplyPossibleCheckImpl implements ApplyPossibleCheck {

//	@Override
//	public boolean check(ApplicationType appType, GeneralDate startDate, OverTimeAtr overTimeAtr, AppTypeDiscreteSetting appTypeDiscreteSetting, 
//			int i, List<ReceptionRestrictionSetting> receptionRestrictionSetting) {
//		GeneralDate loopDay = startDate.addDays(i);
//		GeneralDateTime systemDateTime = GeneralDateTime.now();
//		GeneralDate systemDate = GeneralDate.today();
//		// ドメインモデル「事前の受付制限」．チェック方法をチェックする
//		if(appTypeDiscreteSetting.getRetrictPreMethodFlg().equals(CheckMethod.DAYCHECK)){
//			// ループする日と受付制限日と比較する
//			GeneralDate limitDay = systemDate.addDays(appTypeDiscreteSetting.getRetrictPreDay().value);
//			if(loopDay.before(limitDay)) {
//				return true;
//			}
//		} else {
//			if(appType.equals(ApplicationType.OVER_TIME_APPLICATION)){
//				// ループする日とシステム日付を比較する
//				if(loopDay.before(systemDate)){
//					return true;
//				} else if(loopDay.equals(systemDate)){
//					Integer systemTime = systemDateTime.hours() * 60 + systemDateTime.minutes();
//					int resultCompare = 0;
//					if(overTimeAtr == OverTimeAtr.PREOVERTIME && receptionRestrictionSetting.get(0).getBeforehandRestriction().getPreOtTime() != null){
//						resultCompare = systemTime.compareTo(receptionRestrictionSetting.get(0).getBeforehandRestriction().getPreOtTime().v());
//					}else if(overTimeAtr == OverTimeAtr.REGULAROVERTIME && receptionRestrictionSetting.get(0).getBeforehandRestriction().getNormalOtTime() !=  null){
//						resultCompare = systemTime.compareTo(receptionRestrictionSetting.get(0).getBeforehandRestriction().getNormalOtTime().v());
//					}else if(overTimeAtr == OverTimeAtr.ALL && receptionRestrictionSetting.get(0).getBeforehandRestriction().getTimeBeforehandRestriction() !=  null){
//						resultCompare = systemTime.compareTo(receptionRestrictionSetting.get(0).getBeforehandRestriction().getTimeBeforehandRestriction().v());
//					}
//					// システム日時と受付制限日時と比較する
//					if(resultCompare == 1) {
//						return true;
//					}
//				}
//			}
//		}
//		return false;
//	}
}
