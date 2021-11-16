package nts.uk.ctx.at.shared.dom.worktype;

/**
 * @author anhnm
 *
 */
public class HolidayTestHelper {
    
    /**
     * 休暇種類を新規作成する
     * @param isAnnualHoliday 年休
     * @param isYearlyReserved 積立年休
     * @param isSubstituteHoliday 代休
     * @param isPause 振休
     * @param isHoliday 休日
     * @param isSpecialHoliday 特別休暇
     * @param isTimeDigestVacation 時間消化休暇
     * @return 休暇種類 Holiday
     */
    public static Holiday createHoliday(boolean isAnnualHoliday, boolean isYearlyReserved, boolean isSubstituteHoliday, 
            boolean isPause, boolean isHoliday, boolean isSpecialHoliday, boolean isTimeDigestVacation) {
        
        return new Holiday(isAnnualHoliday, isYearlyReserved, isSubstituteHoliday, isPause, isHoliday, isSpecialHoliday, isTimeDigestVacation);
    }
    
    /**
     * 休暇種類を新規作成する
     * @return 休暇種類 Holiday
     */
    public static Holiday createDefaultHoliday() {
        return new Holiday();
    }
    
    /**
     * 休暇種類を新規作成する (年休 = true)
     */
    public static Holiday createAnnualHoliday() {
        Holiday holiday = new Holiday();
        holiday.setAnnualHoliday(true);
        
        return holiday;
    }
    
    /**
     * 休暇種類を新規作成する (積立年休 = true)
     */
    public static Holiday createYearlyReserved() {
        Holiday holiday = new Holiday();
        holiday.setYearlyReserved(true);
        
        return holiday;
    }
    
    /**
     * 休暇種類を新規作成する (代休 = true)
     */
    public static Holiday createSubstituteHoliday() {
        Holiday holiday = new Holiday();
        holiday.setSubstituteHoliday(true);
        
        return holiday;
    }
    
    /**
     * 休暇種類を新規作成する (振休 = true)
     */
    public static Holiday createPause() {
        Holiday holiday = new Holiday();
        holiday.setPause(true);
        
        return holiday;
    }
    
    /**
     * 休暇種類を新規作成する (休日 = true)
     */
    public static Holiday createHoliday() {
        Holiday holiday = new Holiday();
        holiday.setHoliday(true);
        
        return holiday;
    }
    
    /**
     * 休暇種類を新規作成する (特別休暇 = true)
     */
    public static Holiday createSpecialHoliday() {
        Holiday holiday = new Holiday();
        holiday.setSpecialHoliday(true);
        
        return holiday;
    }
    
    /**
     * 休暇種類を新規作成する (時間消化休暇 = true)
     */
    public static Holiday createTimeDigestVacation() {
        Holiday holiday = new Holiday();
        holiday.setTimeDigestVacation(true);
        
        return holiday;
    }
    
    /**
     * 休暇種類を新規作成する (休日 = true, 年休 = true)
     */
    public static Holiday createHolidayAnnual() {
        Holiday holiday = new Holiday();
        holiday.setHoliday(true);
        holiday.setAnnualHoliday(true);
        
        return holiday;
    }
}
