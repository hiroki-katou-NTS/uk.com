package nts.uk.ctx.at.record.app.find.reservation.bento.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * 社員の予約情報
 * @author Le Huu Dat
 */
@Setter
@Getter
@AllArgsConstructor
public class ReservationInfoForEmployeeDto {
    /** 予約明細 */
    private List<ReservationDetailDto> reservationDetails;
    /** 予約者のカード番号 */
    private int reservationCardNo;
    /** 予約者員ID */
    private String reservationMemberId;
    /** 予約者員コード */
    private String reservationMemberCode;
    /** 予約者員名 */
    private String reservationMemberName;
    /** 注文日 */
    private int orderDate;
    /** 注文時刻 */
    private int orderTime;
    /** 活性 */
    private boolean activity;
    /** 締め時刻枠 */
    private int closingTimeFrame;
}
