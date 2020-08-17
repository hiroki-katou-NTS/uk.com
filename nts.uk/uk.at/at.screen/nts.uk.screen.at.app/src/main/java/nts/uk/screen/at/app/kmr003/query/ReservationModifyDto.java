package nts.uk.screen.at.app.kmr003.query;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * 予約の修正起動情報
 * @author Le Huu Dat
 */
@Setter
@Getter
public class ReservationModifyDto {
    /** 弁当ヘッダー */
    private List<HeaderInfoDto> bentos;
    /** 弁当メニューの締め時刻 */
    private List<ClosingTimeDto> bentoClosingTimes;
    /** 月締め処理が済んでいる社員情報 */
    private List<EmployeeInfoMonthFinishDto> empFinishs;
    /** 社員の予約情報 */
    private List<ReservationModifyEmployeeDto> reservationModifyEmps;
}
