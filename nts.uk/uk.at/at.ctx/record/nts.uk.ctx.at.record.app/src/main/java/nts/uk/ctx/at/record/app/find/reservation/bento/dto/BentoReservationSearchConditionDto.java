package nts.uk.ctx.at.record.app.find.reservation.bento.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 検索条件
 * @author Hoang Anh Tuan
 */
@Getter
@AllArgsConstructor
public enum BentoReservationSearchConditionDto {

    MORE_THAN_1_PRODUCT(0),
    ORDERED(1),
    UN_ORDERED(2),
    NEW_ORDER(3),
    ALL(4);

    public int value;

}
