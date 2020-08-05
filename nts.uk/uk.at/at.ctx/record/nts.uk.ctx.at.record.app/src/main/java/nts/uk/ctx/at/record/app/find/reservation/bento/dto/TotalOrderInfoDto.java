package nts.uk.ctx.at.record.app.find.reservation.bento.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import nts.arc.time.GeneralDate;

/**
 * 合計注文情報
 * @author Hoang Anh Tuan
 */
@Setter
@Getter
@AllArgsConstructor
public class TotalOrderInfoDto {

    /** 予約日 */
    private GeneralDate reservationDate;

    /** 予約登録情報 */
    private String reservationRegisInfo;

    /** 合計金額 */
    private int totalFee;

    /** 弁当合計 */
    private BentoTotalDto bentoTotalDto;

    /** 締め時刻名 */
    private String closedName;

    /** 職場又は場所情報 */
    private PlaceOfWorkInfoDto placeOfWorkInfoDto;

}
