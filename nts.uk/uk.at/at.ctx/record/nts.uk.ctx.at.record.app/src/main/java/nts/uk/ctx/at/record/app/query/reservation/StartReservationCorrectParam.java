package nts.uk.ctx.at.record.app.query.reservation;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * @author anhnm
 *
 */
@Getter
@Setter
@AllArgsConstructor
public class StartReservationCorrectParam {

    /**
     * 予約日
     */
    public String reservationDate;
    
    /**
     * 注文日
     */
    public String correctionDate; 
    
    /**
     * 枠NO
     */
    public int frameNo;
    
    /**
     * 受付名称
     */
    public String frameName;
    
    /**
     * 抽出条件
     */
    public int extractCondition;
    
    /**
     * List＜社員ID＞
     */
    public List<String> employeeIds;
    
}
