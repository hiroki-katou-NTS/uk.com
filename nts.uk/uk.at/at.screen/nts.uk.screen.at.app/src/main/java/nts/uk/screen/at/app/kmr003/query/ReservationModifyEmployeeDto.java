package nts.uk.screen.at.app.kmr003.query;

import lombok.Getter;
import lombok.Setter;
import nts.arc.time.GeneralDate;
import nts.arc.time.GeneralDateTime;

import java.util.List;

/**
 * 社員の予約情報
 * @author Le Huu Dat
 */
@Setter
@Getter
public class ReservationModifyEmployeeDto {
    /** 予約明細 */
    private List<ReservationModifyDetailDto> reservationDetails;
    /** 予約者のカード番号 */
    private String reservationCardNo;
    /** 予約者員ID */
    private String reservationMemberId;
    /** 予約者員コード */
    private String reservationMemberCode;
    /** 予約者員名 */
    private String reservationMemberName;
    /** 注文日 */
    private GeneralDate reservationDate;
    /** 注文時刻 */
    private String reservationTime;
    /** 活性 */
    private boolean activity = true;
    /** 注文済み */
    private boolean ordered;
    /** 締め時刻枠 */
    private int closingTimeFrame;
}
