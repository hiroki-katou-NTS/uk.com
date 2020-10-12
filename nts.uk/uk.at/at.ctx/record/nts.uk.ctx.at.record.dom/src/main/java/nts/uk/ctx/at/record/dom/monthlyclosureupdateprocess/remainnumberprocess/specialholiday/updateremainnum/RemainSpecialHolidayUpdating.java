package nts.uk.ctx.at.record.dom.monthlyclosureupdateprocess.remainnumberprocess.specialholiday.updateremainnum;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import nts.arc.task.tran.AtomTask;
import nts.arc.time.GeneralDate;
import nts.arc.time.calendar.period.DatePeriod;
import nts.gul.text.IdentifierUtil;
import nts.uk.ctx.at.shared.dom.remainingnumber.base.GrantRemainRegisterType;
import nts.uk.ctx.at.shared.dom.remainingnumber.common.empinfo.grantremainingdata.daynumber.LeaveNumberInfo;
import nts.uk.ctx.at.shared.dom.remainingnumber.specialleave.empinfo.grantremainingdata.SpecialLeaveGrantRemainingData;
import nts.uk.ctx.at.shared.dom.remainingnumber.specialleave.service.LimitTimeAndDays;
import nts.uk.ctx.at.shared.dom.remainingnumber.specialleave.service.SpecialLeaveGrantDetails;
import nts.uk.ctx.at.shared.dom.remainingnumber.specialleave.service.SpecialLeaveNumberInfoService;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.ctx.at.record.dom.remainingnumber.specialleave.empinfo.grantremainingdata.InPeriodOfSpecialLeaveResultInfor;

/**
 * 特別休暇残数更新
 * @author shuichi_ishida
 */
public class RemainSpecialHolidayUpdating {
	
	/**
	 * 特別休暇残数更新
	 * @param output 特別休暇の集計結果
	 * @param empId 社員ID
	 * @param period 期間
	 * @param specialLeaveCode 特別休暇コード
	 * @param autoGrant 自動付与区分
	 */
	public static AtomTask updateRemainSpecialHoliday(RequireM1 require, InPeriodOfSpecialLeaveResultInfor output, 
			String empId, DatePeriod period, int specialLeaveCode, int autoGrant) {
		
		List<AtomTask> atomTask = new ArrayList<>();
		String companyId = AppContexts.user().companyId();
	
		Map<GeneralDate, String> existDataMap = new HashMap<>();
		
		// 当月以降の特別休暇付与残数データを削除
		List<SpecialLeaveGrantRemainingData> datas = require.specialLeaveGrantRemainingData(empId, specialLeaveCode);
		for (SpecialLeaveGrantRemainingData data : datas){
			if (data.getGrantDate().after(period.start()) && autoGrant == 1) {
				atomTask.add(AtomTask.of(() -> require.deleteSpecialLeaveGrantRemainingData(data.getLeaveID())));
			}
			else {
				existDataMap.putIfAbsent(data.getGrantDate(), data.getLeaveID());
			}
		}
		
		// 特別休暇付与残数データの更新
		// 特別休暇付与残数データ更新処理
		List<SpecialLeaveGrantRemainingData> details = output.getAsOfPeriodEnd().getGrantRemainingList();
		for (SpecialLeaveGrantRemainingData detail : details){
			
			String specialId = IdentifierUtil.randomUniqueId();
			if (existDataMap.containsKey(detail.getGrantDate())){
				specialId = existDataMap.get(detail.getGrantDate());
			}
			
			LeaveNumberInfo info = detail.getDetails();
//			double numberOverdays = 0.0;	// 上限超過消滅日数
//			Integer timeOver = null;		// 上限超過消滅時間
//			if (info.getLimitDays().isPresent()){
//				LimitTimeAndDays limitDays = info.getLimitDays().get();
//				numberOverdays = limitDays.getDays();
//				timeOver = (limitDays.getTimes().isPresent() ? limitDays.getTimes().get() : null);
//			}
			//残数がマイナスの場合の補正処理
			SpecialLeaveGrantRemainingData updateData = SpecialLeaveGrantRemainingData.createFromJavaType(
					specialId,
					companyId,
					empId,
					specialLeaveCode,
					detail.getGrantDate(),
					detail.getDeadline(),
					detail.getExpirationStatus().value,
					GrantRemainRegisterType.MONTH_CLOSE.value,
					new BigDecimal(info.getGrantNumber().getDays().v()),
					(info.getGrantNumber().getMinutes().isPresent() ? info.getGrantNumber().getMinutes().get().v() : null),
					new BigDecimal(info.getUsedNumber().getDays().v() < 0? null :info.getUsedNumber().getDays().v()),
					(info.getUsedNumber().getMinutes().isPresent() ? info.getUsedNumber().getMinutes().get().v() : null),
					null,				// 積み崩し日数
					new BigDecimal(0),
					0,
					new BigDecimal(info.getRemainingNumber().getDays().v() < 0?0:info.getRemainingNumber().getDays().v()),
					(info.getRemainingNumber().getMinutes().isPresent() ? info.getRemainingNumber().getMinutes().get().v() : null),
					"", ""
					);
			
			if (existDataMap.containsKey(detail.getGrantDate())){
				
				// 「特別休暇付与残数データ」を更新する
				atomTask.add(AtomTask.of(() -> require.updateSpecialLeaveGrantRemainingData(updateData)));
			}
			else {
				
				// 「特別休暇付与残数データ」を追加する
				atomTask.add(AtomTask.of(() -> require.addSpecialLeaveGrantRemainingData(updateData)));
			}
		}
		
		return AtomTask.bundle(atomTask);
	}
	
	public static interface RequireM1 {
		
		List<SpecialLeaveGrantRemainingData> specialLeaveGrantRemainingData(String employeeId, int specialCode);
		
		void deleteSpecialLeaveGrantRemainingData(String specialid);
		
		void updateSpecialLeaveGrantRemainingData(SpecialLeaveGrantRemainingData data);
		
		void addSpecialLeaveGrantRemainingData(SpecialLeaveGrantRemainingData data);
	}
}
