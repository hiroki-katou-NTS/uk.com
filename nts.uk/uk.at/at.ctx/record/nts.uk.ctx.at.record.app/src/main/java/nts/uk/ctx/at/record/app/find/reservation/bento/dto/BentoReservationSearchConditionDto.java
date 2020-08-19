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

    /** １商品２件以上 */
    MORE_THAN_1_PRODUCT(0),
    /** 注文済み */
    ORDERED(1),
    /** 未注文 */
    UN_ORDERED(2),
    /** 新規注文 */
    NEW_ORDER(3),
    /** 全部 */
    ALL(4);

    public int value;

}
