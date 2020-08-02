package nts.uk.ctx.at.record.app.find.reservation.bento.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * 予約弁当情報
 * @author Hoang Anh Tuan
 */
@Setter
@Getter
@AllArgsConstructor
public class BentoReservedInfoDto {

    /** 弁当名 */
    private String bentoName;

    /** 枠番 */
    private int frameNo;

    /** 社員ごとの弁当予約情報 */
    private List<BentoReservationInfoForEmpDto> bentoReservationInfoForEmpList;
}
