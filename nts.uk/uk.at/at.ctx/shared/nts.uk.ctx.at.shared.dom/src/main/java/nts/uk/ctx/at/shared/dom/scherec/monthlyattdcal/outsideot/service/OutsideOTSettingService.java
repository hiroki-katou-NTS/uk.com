package nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.outsideot.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import lombok.val;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.outsideot.OutsideOTSetting;
import nts.uk.ctx.at.shared.dom.workdayoff.frame.WorkdayoffFrame;
import nts.uk.ctx.at.shared.dom.workdayoff.frame.WorkdayoffFrameRole;

public class OutsideOTSettingService {

	/**
	 * 36協定対象項目一覧を取得
	 * 
	 * @param companyId
	 * @return 36協定時間対象項目
	 */
	public static Time36AgreementTargetItem getTime36AgreementTargetItem(RequireM2 require, String companyId) {
		List<Integer> overtimeFrNo = new ArrayList<>();
		List<Integer> breakFrNo = new ArrayList<>();
		List<MonthlyItems> overTimeItems = MonthlyItems.findOverTime();
		List<MonthlyItems> breakTimeItems = MonthlyItems.findBreakTime();
		boolean targetFlex = false;

		// ドメインモデル「時間外超過設定」を取得する
		Optional<OutsideOTSetting> outsideOTSettingOtp = require.outsideOTSetting(companyId);
		if (!outsideOTSettingOtp.isPresent()) {
			return new Time36AgreementTargetItem(overtimeFrNo, breakFrNo, targetFlex);
		}
		// 内訳項目に設定されている勤怠項目IDを全て取得
		List<Integer> itemIdList = outsideOTSettingOtp.get().getAllAttendanceItemIds();
		// 取得した勤怠項目IDから36協定時間対象と判断
		for (Integer itemId : itemIdList) {
			Optional<MonthlyItems> itemOverTimeOtp = overTimeItems
					.stream().filter(x -> x.itemId == itemId)
					.findFirst();
			if (itemOverTimeOtp.isPresent()) {
				if (!overtimeFrNo.contains(itemOverTimeOtp.get().frameNo)) {
					overtimeFrNo.add(itemOverTimeOtp.get().frameNo);
				}
			}
			Optional<MonthlyItems> itemBreakTimeOtp = breakTimeItems
					.stream().filter(x -> x.itemId == itemId)
					.findFirst();
			if (itemBreakTimeOtp.isPresent()) {
				if (!breakFrNo.contains(itemBreakTimeOtp.get().frameNo)) {
					breakFrNo.add(itemBreakTimeOtp.get().frameNo);
				}
			}
		}
		Optional<Integer> flexExessOtp = itemIdList.stream()
			.filter(x -> {
				boolean result = (x == MonthlyItems.FLEX_EXCESS_TIME.itemId);
				result = result || (x == MonthlyItems.LEGAL_FLEX_TIME.itemId);
				result = result || (x == MonthlyItems.ILLEGAL_FLEX_TIME.itemId);
				return result;
			})
			.findFirst();
		if(flexExessOtp.isPresent()){
			targetFlex = true;
		}
		// 36協定時間対象項目を返す		
		return new Time36AgreementTargetItem(overtimeFrNo, breakFrNo, targetFlex);
	}
	
	/**
	 * 法定内休出の勤怠項目IDを全て取得
	 * @param companyId 会社ID
	 * @return 勤怠項目IDリスト
	 */
	// 2019.2.13 ADD shuichi_ishida
	public static List<Integer> getAllAttendanceItemIdsForLegalBreak(RequireM1 require, String companyId) {
		return getAllAttendanceItemIdsForLegalBreak(require, companyId, Optional.empty(), Optional.empty());
	}
	
	/**
	 * 法定内休出の勤怠項目IDを全て取得
	 * @param companyId 会社ID
	 * @param outsideOtSettingOpt 時間外超過設定
	 * @param workdayoffFramesOpt 休出枠リスト
	 * @return 勤怠項目IDリスト
	 */
	// 2019.2.13 ADD shuichi_ishida
	public static List<Integer> getAllAttendanceItemIdsForLegalBreak(RequireM1 require, String companyId,
			Optional<OutsideOTSetting> outsideOtSettingOpt,
			Optional<List<WorkdayoffFrame>> workdayoffFramesOpt){

		List<Integer> result = new ArrayList<>();
		
		// 「時間外超過設定」を取得
		OutsideOTSetting outsideOTSetting = null;
		if (outsideOtSettingOpt.isPresent()) {
			outsideOTSetting = outsideOtSettingOpt.get();
		}
		else {
			val outsideOTSettingData = require.outsideOTSetting(companyId);
			if (outsideOTSettingData.isPresent()) {
				outsideOTSetting = outsideOTSettingData.get();
			}
		}
		if (outsideOTSetting == null) return result;
		
		// 「休出枠の役割」を取得
		List<WorkdayoffFrame> workdayoffFrames = new ArrayList<>();
		if (workdayoffFramesOpt.isPresent()) {
			workdayoffFrames.addAll(workdayoffFramesOpt.get());
		}
		else {
			workdayoffFrames.addAll(require.workdayoffFrames(companyId));
		}
		
		// 内訳項目一覧に設定されている勤怠項目IDを確認する
		List<Integer> itemIds = outsideOTSetting.getAllAttendanceItemIds();
		
		// 「休出枠．役割」から法定内休出のみを取り出す
		for (val workdayoffFrame : workdayoffFrames) {
			if (workdayoffFrame.getRole() != WorkdayoffFrameRole.STATUTORY_HOLIDAYS) continue;
			
			// 取得した枠NOに該当する勤怠項目IDを取得　（内訳項目一覧に存在していない勤怠項目ID）
			val frNo = workdayoffFrame.getWorkdayoffFrNo().v().intValue();
			Integer breakTimeItemId = MonthlyItems.BREAKTIME_1.itemId + frNo - 1;
			if (!itemIds.contains(breakTimeItemId)) result.add(breakTimeItemId);
			Integer transTimeItemId = MonthlyItems.TRANSTIME_1.itemId + frNo - 1;
			if (!itemIds.contains(transTimeItemId)) result.add(transTimeItemId);
		}
		
		// 取得した勤怠項目IDリストを返す
		return result;
	}
	
	public static interface RequireM2 {
		
		Optional<OutsideOTSetting> outsideOTSetting(String companyId);
		
	}
	
	public static interface RequireM1 extends RequireM2 {
		
		List<WorkdayoffFrame> workdayoffFrames(String companyId);
		
	}
}
