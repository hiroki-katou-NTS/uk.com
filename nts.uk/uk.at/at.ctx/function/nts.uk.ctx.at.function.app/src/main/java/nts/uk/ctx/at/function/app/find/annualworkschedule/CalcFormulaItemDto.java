package nts.uk.ctx.at.function.app.find.annualworkschedule;

import lombok.AllArgsConstructor;
import lombok.Value;
import nts.arc.time.GeneralDate;
import nts.arc.time.GeneralDateTime;
import nts.uk.ctx.at.function.dom.annualworkschedule.CalcFormulaItem;

/**
* 項目の算出式
*/
@AllArgsConstructor
@Value
public class CalcFormulaItemDto
{
    
    /**
    * 会社ID
    */
    private String cid;
    
    /**
    * コード
    */
    private int setOutCd;
    
    /**
    * コード
    */
    private int itemOutCd;
    
    /**
    * 勤怠項目ID
    */
    private int attendanceItemId;
    
    /**
    * 加, 減
    */
    private int operation;
    
    
    public static CalcFormulaItemDto fromDomain(CalcFormulaItem domain)
    {
        return new CalcFormulaItemDto(domain.getCid(), domain.getSetOutCd(), domain.getItemOutCd(), domain.getAttendanceItemId(), domain.getOperation());
    }
    
}
