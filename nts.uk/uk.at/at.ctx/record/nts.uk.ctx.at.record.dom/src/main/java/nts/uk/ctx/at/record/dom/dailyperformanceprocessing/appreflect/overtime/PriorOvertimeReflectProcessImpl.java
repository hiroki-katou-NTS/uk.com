package nts.uk.ctx.at.record.dom.dailyperformanceprocessing.appreflect.overtime;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.record.dom.dailyperformanceprocessing.appreflect.WorkTimeForm;
import nts.uk.ctx.at.record.dom.editstate.EditStateOfDailyPerformance;
import nts.uk.ctx.at.record.dom.editstate.enums.EditStateSetting;
import nts.uk.ctx.at.record.dom.editstate.repository.EditStateOfDailyPerformanceRepository;
import nts.uk.ctx.at.record.dom.workinformation.WorkInfoOfDailyPerformance;
import nts.uk.ctx.at.record.dom.workinformation.repository.WorkInformationRepository;
import nts.uk.ctx.at.shared.dom.WorkInformation;
import nts.uk.ctx.at.shared.dom.worktime.worktimeset.WorkTimeDailyAtr;
import nts.uk.ctx.at.shared.dom.worktime.worktimeset.WorkTimeMethodSet;
import nts.uk.ctx.at.shared.dom.worktime.worktimeset.WorkTimeSetting;
import nts.uk.ctx.at.shared.dom.worktime.worktimeset.WorkTimeSettingRepository;
import nts.uk.shr.com.context.AppContexts;
@Stateless
public class PriorOvertimeReflectProcessImpl implements PriorOvertimeReflectProcess{
	@Inject
	private WorkInformationRepository workRepository;
	@Inject
	private EditStateOfDailyPerformanceRepository dailyReposiroty;
	@Inject
	private WorkTimeSettingRepository workTimeSettingRepository;
	@Override
	public void workTimeWorkTimeUpdate(PreOvertimeParameter para) {
		//INPUT．勤種反映フラグ(予定)をチェックする
		if(!para.isScheReflectFlg()) {
			return;
		}
		this.updateDaily(this.lstItemSche(), para);		
	}
	/**
	 * 予定勤務種類の項目ID
	 * @return
	 */
	private List<Integer> lstItemSche(){
		List<Integer> lstItem = new ArrayList<>();
		lstItem.add(3);
		lstItem.add(4);
		lstItem.add(5);
		lstItem.add(6);
		return lstItem;
	}
	/**
	 * 勤務種類の項目ID
	 * 就業時間帯の項目ID
	 * @return
	 */
	private List<Integer> lstItemRecord(){
		List<Integer> lstItem = new ArrayList<>();
		lstItem.add(1);
		lstItem.add(2);
		return lstItem;
	}
	/**
	 * update 日別実績の勤務情報, 日別実績の編集状態
	 * @param lstItem
	 * @param para
	 */
	private void updateDaily(List<Integer> lstItem, PreOvertimeParameter para) {
		Optional<WorkInfoOfDailyPerformance> optDailyPerfor = workRepository.find(para.getEmployeeId(), para.getDateInfo());
		if(!optDailyPerfor.isPresent()) {
			return;
		}
		//日別実績の勤務情報
		WorkInfoOfDailyPerformance dailyPerfor = optDailyPerfor.get();
		WorkInformation scheInfor = new WorkInformation(para.getOvertimePara().getWorkTimeCode(), para.getOvertimePara().getWorkTypeCode());
		dailyPerfor.setScheduleWorkInformation(scheInfor);
		workRepository.updateByKey(dailyPerfor);
		//日別実績の編集状態
		List<EditStateOfDailyPerformance> lstDaily = new ArrayList<>();
		this.lstItemSche().stream().forEach(z -> {
			Optional<EditStateOfDailyPerformance> optItemData = dailyReposiroty.findByKeyId(para.getEmployeeId(), para.getDateInfo(), z);
			if(optItemData.isPresent()) {
				EditStateOfDailyPerformance itemData = optItemData.get();
				EditStateOfDailyPerformance data = new EditStateOfDailyPerformance(itemData.getEmployeeId(), 
						itemData.getAttendanceItemId(), itemData.getYmd(), 
						EditStateSetting.REFLECT_APPLICATION);
				lstDaily.add(data);
			}
		});
		
		if(!lstDaily.isEmpty()) {
			dailyReposiroty.updateByKey(lstDaily);
		}
	}
	@Override
	public boolean changeFlg(PreOvertimeParameter para) {
		//INPUT．勤種反映フラグ(実績)をチェックする
		if(!para.isActualReflectFlg()) {
			return false;
		}
		//ドメインモデル「日別実績の勤務情報」を取得する
		Optional<WorkInfoOfDailyPerformance> optDailyPerfor = workRepository.find(para.getEmployeeId(), para.getDateInfo());
		if(!optDailyPerfor.isPresent()) {
			return false;
		}
		//勤種・就時の反映
		this.updateDaily(lstItemRecord(), para);
		WorkInfoOfDailyPerformance dailyPerfor = optDailyPerfor.get();
		//反映前後勤就に変更があるかチェックする
		//取得した勤務種類コード ≠ INPUT．勤務種類コード OR
		//取得した就業時間帯コード ≠ INPUT．就業時間帯コード
		if(dailyPerfor.getRecordWorkInformation().getWorkTimeCode().v().equals(para.getOvertimePara().getWorkTimeCode())
				||dailyPerfor.getRecordWorkInformation().getWorkTypeCode().v().equals(para.getOvertimePara().getWorkTypeCode())){
			 return true;
		}
		
		return false;
		
	}
	@Override
	public boolean startAndEndTimeReflectSche(PreOvertimeParameter para, boolean changeFlg,
			WorkInfoOfDailyPerformance dailyData) {
		//設定による予定開始終了時刻を反映できるかチェックする
		if(!this.timeReflectCheck(para, changeFlg, dailyData)) {
			return false;
		}

		//予定開始終了時刻の反映(事前事後共通部分)
		//@@
		
		
		
		
		
		
		return false;
	}
	@Override
	public boolean timeReflectCheck(PreOvertimeParameter para, boolean changeFlg,
			WorkInfoOfDailyPerformance dailyData) {
		//INPUT．勤種反映フラグ(予定)をチェックする
		if(para.isScheReflectFlg()) {
			return true;
		}
		//実績に反映するかチェックする
		//INPUT．勤種反映フラグ(実績) == する AND
		//INPUT．予定出退勤反映フラグ == する AND
		//反映前後勤就の変更フラグ == true
		if(!para.isActualReflectFlg()
				|| !para.isScheTimeOutFlg()
				|| !changeFlg) {
			return false;
		}
		//INPUT．予定と実績を同じに変更する区分をチェックする
		if(para.getScheAndRecordSameChangeFlg() == ScheAndRecordSameChangeFlg.ALWAY) {
			return true;
		}
		//流動勤務かどうかの判断処理
		WorkTimeForm workTimeFormCheck = this.workTimeFormCheck(para.getOvertimePara().getWorkTimeCode());
		if(workTimeFormCheck == WorkTimeForm.FLOW) {
			return true;
		} else {
			return false;
		}
	}
	/**
	 * 流動勤務かどうかの判断処理
	 * @return
	 */
	private WorkTimeForm workTimeFormCheck(String workTimeCode) {
		String companyId = AppContexts.user().companyId();
		//就業時間帯の設定
		Optional<WorkTimeSetting> findByCode = workTimeSettingRepository.findByCode(companyId, workTimeCode);
		if(!findByCode.isPresent()) {
			return null;
		}
		WorkTimeSetting workTimeData = findByCode.get();
		if(workTimeData.getWorkTimeDivision().getWorkTimeDailyAtr() == WorkTimeDailyAtr.FLEX_WORK) {
			return WorkTimeForm.FLEX;
		} else if (workTimeData.getWorkTimeDivision().getWorkTimeDailyAtr() == WorkTimeDailyAtr.REGULAR_WORK) {
			if(workTimeData.getWorkTimeDivision().getWorkTimeMethodSet() == WorkTimeMethodSet.FIXED_WORK) {
				return WorkTimeForm.FIXED;
			} else if (workTimeData.getWorkTimeDivision().getWorkTimeMethodSet() == WorkTimeMethodSet.DIFFTIME_WORK) {
				return WorkTimeForm.TIMEDIFFERENCE;
			} else {
				return WorkTimeForm.FLOW;
			}
		}
		return null;
	}

}
