package nts.uk.ctx.at.request.dom.application.holidayshipment;

import java.util.List;
import java.util.Optional;

import lombok.AllArgsConstructor;
import lombok.Data;
import nts.uk.ctx.at.request.dom.application.WorkInformationForApplication;
import nts.uk.ctx.at.shared.dom.remainingnumber.paymana.PayoutSubofHDManagement;
import nts.uk.ctx.at.shared.dom.remainingnumber.subhdmana.LeaveComDayOffManagement;
import nts.uk.ctx.at.shared.dom.worktype.WorkType;

@Data
@AllArgsConstructor
public class DisplayInformationApplicationRoot {
    /** 勤務種類リスト */
    private List<WorkType> workTypeList;
    /** 休出代休紐付け管理 */
    private List<LeaveComDayOffManagement> leaveComDayOffMana;
    /** 初期選択勤務種類 */
    private Optional<String> workType;
    /** 初期選択就業時間帯 */
    private Optional<String> workTime;
    /** 開始時刻 */
    private Optional<Integer> startTime;
    /** 終了時刻 */
    private Optional<Integer> endTime;
    /** 開始時刻2 */
    private Optional<Integer> startTime2;
    /** 終了時刻2 */
    private Optional<Integer> endTime2;
    
    /** 申請中の勤務情報 */
    private Optional<WorkInformationForApplication> workInformationForApplication;
    
    /** 振出振休紐付け管理 --> only AbsApplication <=> 振出申請起動時の表示情報*/ 
    private List<PayoutSubofHDManagement> payoutSubofHDManagements;
    
}
