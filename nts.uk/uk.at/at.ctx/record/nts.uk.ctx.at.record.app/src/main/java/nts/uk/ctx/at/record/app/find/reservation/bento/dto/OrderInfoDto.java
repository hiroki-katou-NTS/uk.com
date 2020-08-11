package nts.uk.ctx.at.record.app.find.reservation.bento.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * 注文情報
 * @author Hoang Anh Tuan
 */
@Setter
@Getter
@AllArgsConstructor
public class OrderInfoDto {

    /** 会社名 */
    private String companyName;

    /** 合計タイトル */
    private String totalTittle;

    /** 合計注文情報 */
    private List<TotalOrderInfoDto> totalOrderInfoDtoList;

    /** 明細タイトル*/
    private int detailTittle;

    /** 明細注文情報 */
    private List<DetailOrderInfoDto> detailOrderInfoDtoList;

}
