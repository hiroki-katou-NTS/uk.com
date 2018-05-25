package nts.uk.ctx.at.record.dom.remainingnumber.specialleave.service;

import java.util.List;
import java.util.Optional;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.record.dom.remainingnumber.specialleave.empinfo.basicinfo.SpecialLeaveBasicInfo;
import nts.uk.ctx.at.record.dom.remainingnumber.specialleave.empinfo.grantremainingdata.SpecialLeaveGrantRemainingData;
import nts.uk.ctx.at.shared.dom.yearholidaygrant.service.Period;
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
	 * @param mngAtr ・翌月管理データ取得区分
	 * 月締め更新時に、集計終了日の翌日に付与があった場合、その月の管理データを作る
	 * @return
	 */
	InPeriodOfSpecialLeave complileInPeriodOfSpecialLeave(String cid, String sid, Period complileDate, boolean model, GeneralDate baseDate, int specialLeaveCode, NextMonthMngDataAtr mngAtr);
	/**
	 * 管理データを取得する
	 * @param cid
	 * @param sid 
	 * @param specialLeaveCode ・特別休暇コード
	 * @param complileDate ・集計開始日 ・集計終了日
	 * @return 特別休暇付与残数データ
	 */
	Optional<SpecialLeaveGrantRemainingData> getMngData(String cid, String sid, int specialLeaveCode, Period complileDate);
	/**
	 * RequestList373  社員の特別休暇情報を取得する
	 * @param cid
	 * @param sid
	 * @param specialLeaveCode
	 * @param complileDate
	 * @return
	 */
	InforSpecialLeaveOfEmployee getInforSpecialLeaveOfEmployee(String cid, String sid, int specialLeaveCode, Period complileDate);
	/**
	 * テーブルに基づいた付与日一覧を求める
	 * @param sid
	 * @param dateData
	 * @return
	 */
	List<GeneralDate> askGrantDayOnTable(String sid, Period dateData, SpecialLeaveBasicInfo specialInfo);
}
