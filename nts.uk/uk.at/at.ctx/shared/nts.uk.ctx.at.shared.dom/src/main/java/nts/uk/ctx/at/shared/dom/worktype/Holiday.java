package nts.uk.ctx.at.shared.dom.worktype;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author anhnm
 * UKDesign.ドメインモデル.NittsuSystem.UniversalK.就業.shared.就業規則.勤務種類.休暇種類
 * 休暇種類
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Holiday {
    
    /**
     * 年休
     */
    private boolean isAnnualHoliday;
    
    /**
     * 積立年休
     */
    private boolean isYearlyReserved;
    
    /**
     * 代休
     */
    private boolean isSubstituteHoliday;
    
    /**
     * 振休
     */
    private boolean isPause;
    
    /**
     * 休日
     */
    private boolean isHoliday;
    
    /**
     * 特別休暇
     */
    private boolean isSpecialHoliday;
    
    /**
     * 時間消化休暇
     */
    private boolean isTimeDigestVacation;
    
    /**
     * [1] 変更する
     * @param holidayType
     */
    public void changeValue(HolidayTypeClassification holidayType) {
        switch (holidayType) {
        case AnnualHoliday:
            isAnnualHoliday = true;
            break;
            
        case YearlyReserved:
            isYearlyReserved = true;
            break;
            
        case SubstituteHoliday:
            isSubstituteHoliday = true;
            break;
        
        case Pause:
            isPause = true;
            break;
            
        case Holiday:
            isHoliday = true;
            break;
            
        case SpecialHoliday:
            isSpecialHoliday = true;
            break;
            
        case TimeDigestVacation:
            isTimeDigestVacation = true;
            break;
        
        case NoVacation:
        default:
            break;
        }
    }
}
