package nts.uk.ctx.at.record.app.find.reservation.bento.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * 職場又は場所情報
 * @author Hoang Anh Tuan
 */
@Setter
@Getter
@AllArgsConstructor
public class PlaceOfWorkInfoDto {

    /** 職場又は場所コード */
    private String placeCode;

    /** 職場又は場所名称 */
    private String placeName;
}
