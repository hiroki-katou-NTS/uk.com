package nts.uk.ctx.at.record.app.query.reservation;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import nts.uk.ctx.at.record.dom.reservation.bento.WorkLocationCode;
import nts.uk.ctx.at.record.dom.reservation.bentomenu.Bento;

/**
 * @author anhnm
 *
 */
@Getter
@Setter
@AllArgsConstructor
public class BentoDto {

    /**
     * 枠番
     */
    public int frameNo;
    
    /**
     * 弁当名
     */
    public String name;
    
    /**
     * 金額１
     */
    public int amount1;
    
    /**
     * 金額２
     */
    public int amount2;
    
    /**
     * 単位
     */
    public String unit;
    
    /**
     * 受付時間帯NO
     */
    public int receptionTimezoneNo;
    
    /**
     * 勤務場所コード
     */
    public String workLocationCode;
    
    public static BentoDto fromDomain(Bento domain) {
        return new BentoDto(
                domain.getFrameNo(), 
                domain.getName().v(), 
                domain.getAmount1().v(), 
                domain.getAmount2().v(), 
                domain.getUnit().v(), 
                domain.getReceptionTimezoneNo().value, 
                domain.getWorkLocationCode().isPresent() ? domain.getWorkLocationCode().map(WorkLocationCode::v).get() : null);
    }
}
