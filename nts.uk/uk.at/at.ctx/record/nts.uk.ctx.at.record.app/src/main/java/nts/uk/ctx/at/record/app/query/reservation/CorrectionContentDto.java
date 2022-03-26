package nts.uk.ctx.at.record.app.query.reservation;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import nts.uk.ctx.at.record.dom.reservation.reservationsetting.CorrectionContent;

/**
 * @author anhnm
 *
 */
@Getter
@Setter
@AllArgsConstructor
public class CorrectionContentDto {
    /**
     * 予約済みの内容変更期限
     */
    // Content change deadline.
    private int contentChangeDeadline;

    /**
     * 予約済みの内容変更期限日数
     */
    // content change deadline days.
    private Integer contentChangeDeadlineDay;

    /**
     * 発注機能管理区分
     */
    // Ordered data.
    private int orderMngAtr;
    
    /**
     * 修正不可の際でも修正可能な権限一覧
     */
    private List<String> canModifiLst;
    
    public static CorrectionContentDto fromDomain(CorrectionContent domain) {
        return new CorrectionContentDto(
                domain.getContentChangeDeadline().value, 
                domain.getContentChangeDeadlineDay()==null?null:domain.getContentChangeDeadlineDay().value, 
                domain.getOrderMngAtr().value, 
                domain.getCanModifiLst());
    }
}
