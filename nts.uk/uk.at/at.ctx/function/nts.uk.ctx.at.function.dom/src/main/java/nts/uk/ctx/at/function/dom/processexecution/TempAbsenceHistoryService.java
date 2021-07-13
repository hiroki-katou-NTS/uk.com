package nts.uk.ctx.at.function.dom.processexecution;

import java.util.List;

import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.shared.dom.adapter.temporaryabsence.TempAbsenceImport;

public interface TempAbsenceHistoryService {
	
	/**
     * 社員（List）と期間から休職休業を取得する
     * @param cid         会社ID
     * @param period      期間
     * @param employeeIds List<社員ID>
     * @return List<休職休業履歴, 休職休業履歴項目>
     */
    TempAbsenceImport getTempAbsence(String cid, DatePeriod period, List<String> employeeIds);

	/**
     * UKDesign.ドメインモデル.NittsuSystem.UniversalK.就業.contexts.勤務実績.勤務実績.日別実績.アルゴリズム.休職休業履歴変更期間を求める.休職休業履歴変更期間を求める
     * @param sid					社員ID
     * @param period				処理期間
     * @param tempAbsence			List<休職休業履歴，休職休業履歴項目>
     * @param isRecreateLeave		休職・休業者再作成(true，false)　
     * @return
     */
    List<DatePeriod> findChangingLeaveHistoryPeriod(String sid, DatePeriod period, TempAbsenceImport tempAbsence, boolean isRecreateLeave);
}
