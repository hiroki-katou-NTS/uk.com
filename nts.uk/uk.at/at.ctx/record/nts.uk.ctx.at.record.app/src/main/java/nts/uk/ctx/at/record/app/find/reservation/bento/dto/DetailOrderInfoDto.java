package nts.uk.ctx.at.record.app.find.reservation.bento.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import nts.arc.time.GeneralDate;

import java.util.List;

/**
 * 明細注文情報
 * @author Hoang Anh Tuan
 */
@Setter
@Getter
@AllArgsConstructor
public class DetailOrderInfoDto {

    /** 予約弁当情報 */
    private List<BentoReservedInfoDto> bentoReservedInfoDtos;

    /** 予約日 */
    private GeneralDate reservationDate;

    /** 予約登録情報 */
    private String reservationRegisInfo;

    /** 締め時刻名 */
    private String closingTimeName;

    /** 職場又は場所情報 */
    private List<PlaceOfWorkInfoDto> placeOfWorkInfoDtos;

}
