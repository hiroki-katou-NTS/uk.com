package nts.uk.ctx.at.shared.dom.remainingnumber.specialleave.service;

import java.util.List;
import java.util.Map;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.shared.dom.remainingnumber.interimremain.InterimRemain;
import nts.uk.ctx.at.shared.dom.remainingnumber.specialholidaymng.interim.InterimSpecialHolidayMng;
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
	ManagaData getMngData(String cid, String sid, int specialLeaveCode, DatePeriod complileDate);

	/**
	 * 使用数を管理データから引く
	 * @param specialLeaverData ・特別休暇付与残数データ一覧
	 * @param interimSpeHolidayData ・特別休暇暫定データ一覧
	 * @return
	 */
	InPeriodOfSpecialLeave subtractUseDaysFromMngData(List<SpecialLeaveGrantRemainingData> specialLeaverData, SpecialHolidayInterimMngData interimDataMng,
			OffsetDaysFromInterimDataMng offsetDays, InPeriodOfSpecialLeave inPeriodData, Map<GeneralDate, Double> limitDays);

	
	/**
	 * 特別休暇暫定データを取得する
	 * @param cid
	 * @param sid
	 * @param dateData
	 * @param mode
	 * @return
	 */
	SpecialHolidayInterimMngData specialHolidayData(String cid, String sid, DatePeriod dateData, boolean mode);
	/**
	 * 管理データと暫定データの相殺
	 * @param cid
	 * @param sid
	 * @param dateData ・INPUT．集計開始日・INPUT．集計終了日
	 * @param baseDate 基準日
	 * @param lstGrantData 取得した特別休暇付与残数データ一覧
	 * @param lstInterimData 取得した特別休暇暫定データ一覧
	 * @param accumulationMaxDays 蓄積上限日数
	 * @return
	 */
	InPeriodOfSpecialLeave getOffsetDay(String cid, String sid, DatePeriod dateData, GeneralDate baseDate,
			List<SpecialLeaveGrantRemainingData> lstGrantData, SpecialHolidayInterimMngData interimDataMng, double accumulationMaxDays);
	/**
	 * 残数情報をまとめる
	 * @param inPeriodData
	 * @return
	 */
	InPeriodOfSpecialLeave sumRemainData(InPeriodOfSpecialLeave inPeriodData);
}
