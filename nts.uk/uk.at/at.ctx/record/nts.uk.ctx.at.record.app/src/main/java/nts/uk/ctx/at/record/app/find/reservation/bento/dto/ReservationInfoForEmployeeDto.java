package nts.uk.ctx.at.record.app.find.reservation.bento.dto;

import lombok.Getter;
import lombok.Setter;
import nts.arc.time.GeneralDate;

import java.util.List;

/**
 * 社員の予約情報
 * @author Le Huu Dat
 */
@Setter
@Getter
public class ReservationInfoForEmployeeDto {
    /** 予約明細 */
    private List<ReservationDetailDto> reservationDetails;
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
    private int reservationTime;
    /** 活性 */
    private boolean ordered = true;
    /** 締め時刻枠 */
    private int closingTimeFrame;
}
