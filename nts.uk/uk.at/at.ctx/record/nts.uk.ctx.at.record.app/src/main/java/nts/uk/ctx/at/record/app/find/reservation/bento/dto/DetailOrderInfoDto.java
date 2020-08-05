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

    /** 予約日 */
    private GeneralDate reservationDate;

    /** 明細注文情報 */
    private String detailOrderInfo;

    /** 職場又は場所情報 */
    private List<PlaceOfWorkInfoDto> placeOfWorkInfoDtos;

}
