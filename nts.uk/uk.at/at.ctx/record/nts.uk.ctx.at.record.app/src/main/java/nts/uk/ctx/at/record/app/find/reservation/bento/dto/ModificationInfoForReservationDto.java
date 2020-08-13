package nts.uk.ctx.at.record.app.find.reservation.bento.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * 予約の修正起動情報
 * @author Le Huu Dat
 */
@Setter
@Getter
public class ModificationInfoForReservationDto {
    /** 弁当ヘッダー */
    private List<HeaderInfoDto> bentoHeaderInfos;
    /** 弁当メニューの締め時刻 */
    private List<ClosingTimeDto> bentoClosingTimes;
    /** 月締め処理が済んでいる社員情報 */
    private List<EmployeeInfoMonthFinishDto> employeeInfoMonthFinishs;
    /** 社員の予約情報 */
    private List<ReservationInfoForEmployeeDto> reservationInfoForEmployees;
}
