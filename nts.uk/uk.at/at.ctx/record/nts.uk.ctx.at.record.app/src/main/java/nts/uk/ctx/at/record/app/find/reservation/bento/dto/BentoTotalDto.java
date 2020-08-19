package nts.uk.ctx.at.record.app.find.reservation.bento.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * 弁当合計
 * @author Hoang Anh Tuan
 */
@Setter
@Getter
@AllArgsConstructor
public class BentoTotalDto {

    /** 単位 */
    private String unit;

    /** 名称 */
    private String name;

    /** 数量 */
    private int quantity;

    /** 枠番 */
    private int frameNo;

    /** 金額 */
    private int amount;

}
