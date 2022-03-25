package nts.uk.ctx.at.record.dom.reservation.bento;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 検索条件
 * @author Hoang Anh Tuan
 */
@Getter
@AllArgsConstructor
public enum BentoReservationSearchCondition {

    /** 全部 */
    ALL(4, "Enum_OrderSearchCondition_ALL"),
    /** １商品２件以上 */
    MORE_THAN_1_PRODUCT(0, "Enum_OrderSearchCondition_DUPLICATION"),
    /** 注文済み */
    ORDERED(1, "Enum_OrderSearchCondition_ORDERED"),
    /** 未注文 */
    UN_ORDERED(2, "Enum_OrderSearchCondition_NOTORDERED"),
    /** 新規注文 */
    NEW_ORDER(3, "Enum_OrderSearchCondition_NEW");

    public int value;
    public String nameId;

}
