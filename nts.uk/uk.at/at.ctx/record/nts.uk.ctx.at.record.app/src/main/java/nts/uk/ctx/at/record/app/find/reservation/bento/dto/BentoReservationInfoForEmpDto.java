package nts.uk.ctx.at.record.app.find.reservation.bento.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * 社員ごとの弁当予約情報
 * @author Hoang Anh Tuan
 */
@Setter
@Getter
@AllArgsConstructor
public class BentoReservationInfoForEmpDto {

    /** 打刻カード番号 */
    private String stampCardNo;

    /** 数量 */
    private int quantity;

    /** 社員ID */
    private String empId;

    /** 社員コード */
    private String empCode;

    /** 社員名 */
    private String empName;

}
