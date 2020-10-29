package nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.overtimerestappcommon;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import nts.arc.enums.EnumAdaptor;
import nts.uk.ctx.at.request.dom.application.ApplicationType;
import nts.uk.ctx.at.request.dom.application.overtime.OvertimeAppAtr;
import nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.AppDateContradictionAtr;
import nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.hdworkapplicationsetting.OverrideSet;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.deviationtime.deviationtimeframe.DivergenceTimeRoot;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.deviationtime.deviationtimeframe.DivergenceTimeRootRepository;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.deviationtime.deviationtimeframe.DivergenceTimeUseSet;
import nts.uk.shr.com.enumcommon.NotUseAtr;

/**
 * refactor 4
 * UKDesign.ドメインモデル."NittsuSystem.UniversalK".就業.contexts.申請承認.設定.会社別.申請承認設定.残業休出申請共通設定.残業休出申請共通設定
 * @author Doan Duy Hung
 *
 */
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class OvertimeLeaveAppCommonSet {

	/**
	 * 事前超過表示設定
	 */
	private NotUseAtr preExcessDisplaySetting;

	/**
	 * 時間外超過区分
	 */
	private Time36AgreeCheckRegister extratimeExcessAtr;

	/**
	 * 時間外表示区分
	 */
	private NotUseAtr extratimeDisplayAtr;

	/**
	 * 実績超過区分
	 */
	private AppDateContradictionAtr performanceExcessAtr;

	/**
	 * 登録時の指示時間超過チェック
	 */
	private NotUseAtr checkOvertimeInstructionRegister;

	/**
	 * 登録時の乖離時間チェック
	 */
	private NotUseAtr checkDeviationRegister;

	/**
	 * 実績超過打刻優先設定
	 */
	private OverrideSet overrideSet;

	public static OvertimeLeaveAppCommonSet create(int preExcessDisplaySetting, int extratimeExcessAtr, int extratimeDisplayAtr, int performanceExcessAtr, int checkOvertimeInstructionRegister, int checkDeviationRegister, int overrideSet) {
		return new OvertimeLeaveAppCommonSet(
				EnumAdaptor.valueOf(preExcessDisplaySetting, NotUseAtr.class),
				EnumAdaptor.valueOf(extratimeExcessAtr, Time36AgreeCheckRegister.class),
				EnumAdaptor.valueOf(extratimeDisplayAtr, NotUseAtr.class),
				EnumAdaptor.valueOf(performanceExcessAtr, AppDateContradictionAtr.class),
				EnumAdaptor.valueOf(checkOvertimeInstructionRegister, NotUseAtr.class),
				EnumAdaptor.valueOf(checkDeviationRegister, NotUseAtr.class),
				EnumAdaptor.valueOf(overrideSet, OverrideSet.class)
		);
	}
	/**
	 * Refactor5
	 * UKDesign.ドメインモデル."NittsuSystem.UniversalK".就業.contexts.申請承認.設定.会社別.申請承認設定.残業休出申請共通設定.アルゴリズム.利用する乖離理由のを取得する
	 * @param companyId 会社ID
	 * @param appType 申請種類
	 * @param ovetTimeAtr 残業申請区分<Optional>
	 */
	// QA get 乖離理由の入力方法
	public void getInfoNoBaseDate(String companyId, ApplicationType appType, Optional<OvertimeAppAtr> ovetTimeAtr) {
		// @「登録時の乖離時間チェック」を確認する
		if (this.checkDeviationRegister == NotUseAtr.NOT_USE) // しない
			 return;  // emptyを返す
		// する
		// INPUT．「申請種類」」をチェックする
		if (appType == ApplicationType.OVER_TIME_APPLICATION || appType == ApplicationType.HOLIDAY_WORK_APPLICATION) {// 残業申請　OR　休出時間申請
			DivergenceTimeRootRepository divergenceTimeRoots =  null;
			/*
			 * 
				INPUT．「申請種類」= 残業申請　AND　INPUT．「残業申請区分」= 早出残業 ⇒ List<乖離時間NO> = 1　
				INPUT．「申請種類」= 残業申請　AND　INPUT．「残業申請区分」= 通常残業 ⇒ List<乖離時間NO> = 2
				INPUT．「申請種類」= 残業申請　AND　INPUT．「残業申請区分」= 早出残業・通常残業 ⇒ List<乖離時間NO> = 1,2
			 * */
			List<Integer> frames = new ArrayList<Integer>();
			if (appType == ApplicationType.HOLIDAY_WORK_APPLICATION) { // INPUT．「申請種類」= 休出時間申請 ⇒ List<乖離時間NO> = 3
				frames.add(3);
			} else if(appType == ApplicationType.HOLIDAY_WORK_APPLICATION) {
				if (ovetTimeAtr.isPresent()) {
					OvertimeAppAtr value = ovetTimeAtr.get();
					// 早出残業
					if (value == OvertimeAppAtr.EARLY_OVERTIME) {
						frames.add(1);
					} else if (value == OvertimeAppAtr.NORMAL_OVERTIME) { // 通常残業
						frames.add(2);
					} else { // 早出残業・通常残業
						frames.add(1);
						frames.add(2);
					}
				}
			}
			// 乖離時間Listを取得する
			List<DivergenceTimeRoot> divergenceTimeRootList = divergenceTimeRoots.getList(frames);
			// 取得した「乖離時間．使用区分」を確認する
			List<DivergenceTimeRoot> divergenceTimeRootListFilter = divergenceTimeRootList.stream().filter(x -> x.getDivTimeUseSet() == DivergenceTimeUseSet.USE).collect(Collectors.toList());
			if (divergenceTimeRootListFilter.isEmpty()) {
				return;  // emptyを返す
			}
			
			//divergenceTimeRoots.getList()
		} else {
			return;  // emptyを返す
		}
		
		
	}
	
	

}
