package nts.uk.ctx.at.shared.dom.remainingnumber.specialleave.service;

import java.util.List;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.shared.dom.remainingnumber.specialholidaymng.interim.InterimSpecialHolidayMng;
import nts.uk.ctx.at.shared.dom.remainingnumber.specialleave.empinfo.basicinfo.SpecialLeaveBasicInfo;
import nts.uk.ctx.at.shared.dom.remainingnumber.specialleave.empinfo.grantremainingdata.SpecialLeaveGrantRemainingData;
import nts.uk.shr.com.time.calendar.period.DatePeriod;
/**
 * UKDesign.ドメインモデル.NittsuSystem.UniversalK.就業.contexts.勤務実績.残数管理.残数管理.特別休暇管理.Export
 * @author do_dt
 *
 */
public interface SpecialLeaveManagementService {
	/**
	 * RequestList273 期間内の特別休暇残を集計する
	 * @param cid
	 * @param sid
	 * @param complileDate ・集計開始日 ・集計終了日
	 * @param model ・モード（月次か、その他か） TRUE: 月次, FALSE: その他  
	 * 月次モード：当月以降は日次のみ見るが、申請とスケは見ない
	 * その他モード：当月以降は申請日次スケを見る
	 * @param baseDate ・基準日
	 * @param specialLeaveCode ・特別休暇コード
	 * @param mngAtr true: 翌月管理データ取得区分がする, false: 翌月管理データ取得区分がしない。 
	 * @return
	 */
	InPeriodOfSpecialLeave complileInPeriodOfSpecialLeave(String cid, String sid, DatePeriod complileDate, boolean mode, GeneralDate baseDate, int specialLeaveCode, boolean mngAtr);
	/**
	 * 管理データを取得する
	 * @param cid
	 * @param sid 
	 * @param specialLeaveCode ・特別休暇コード
	 * @param complileDate ・集計開始日 ・集計終了日
	 * @return 特別休暇付与残数データ
	 */
	List<SpecialLeaveGrantRemainingData> getMngData(String cid, String sid, int specialLeaveCode, DatePeriod complileDate);
	/**
	 * RequestList373  社員の特別休暇情報を取得する
	 * @param cid
	 * @param sid
	 * @param specialLeaveCode
	 * @param complileDate
	 * @return
	 */
	InforSpecialLeaveOfEmployee getInforSpecialLeaveOfEmployee(String cid, String sid, int specialLeaveCode, DatePeriod complileDate);
	/**
	 * テーブルに基づいた付与日一覧を求める
	 * @param sid
	 * @param dateData
	 * @return
	 */
	List<GeneralDate> askGrantDayOnTable(String sid, DatePeriod dateData, SpecialLeaveBasicInfo specialInfo);
	/**
	 * 繰越上限日数まで調整する
	 * @param specialLeaverData ・特別休暇付与残数データ一覧
	 * @param specialHolidayData ・特別休暇暫定データ一覧
	 * @param upLimiDays ・蓄積上限日数
	 * @return
	 */
	List<SpecialLeaveGrantRemainingData> adjustCarryoverDays(List<SpecialLeaveGrantRemainingData> specialLeaverData, List<InterimSpecialHolidayMng> specialHolidayData, Integer upLimiDays);
	/**
	 * 指定日までの使用数を求める
	 * @param specialHolidayData
	 * @param baseDate
	 * @return
	 */
	double getInterimSpeUseDays(List<InterimSpecialHolidayMng> specialHolidayData, GeneralDate baseDate);
	/**
	 * 使用数を管理データから引く
	 * @param specialLeaverData ・特別休暇付与残数データ一覧
	 * @param specialHolidayData ・特別休暇暫定データ一覧
	 * @return
	 */
	List<SpecialLeaveGrantRemainingData> subtractUseDaysFromMngData(List<SpecialLeaveGrantRemainingData> specialLeaverData, List<InterimSpecialHolidayMng> specialHolidayData);
	
	/**
	 * 使用数を求める
	 * @param specialHolidayData
	 * @return
	 */
	double askUseDays(List<InterimSpecialHolidayMng> specialHolidayData);
	/**
	 * 残数情報をまとめる
	 * @param lstSpecialLeaverData 特別休暇付与残数データ一覧
	 * @param useDays ・使用数
	 * @param baseDate ・基準日
	 * @return
	 */
	InPeriodOfSpecialLeave sumRemainData(SpecialLeaveGrantDetails lstSpecialLeaverData, double useDays, GeneralDate baseDate);
		
}
