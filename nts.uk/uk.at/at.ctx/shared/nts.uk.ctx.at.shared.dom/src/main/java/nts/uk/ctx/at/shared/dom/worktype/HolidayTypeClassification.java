package nts.uk.ctx.at.shared.dom.worktype;

import lombok.AllArgsConstructor;

/**
 * @author anhnm
 * UKDesign.ドメインモデル.NittsuSystem.UniversalK.就業.shared.就業規則.勤務種類.休暇種類区分
 * 休暇種類区分
 */
@AllArgsConstructor
public enum HolidayTypeClassification {

    /**
     * 休日
     */
    Holiday(1, "休日"),
    
    /**
     * 年休
     */
    AnnualHoliday(2, "年休"),
    
    /**
     * 積立年休
     */
    YearlyReserved(3, "積立年休"),
    
    /**
     * 特別休暇
     */
    SpecialHoliday(4, "特別休暇"), 
    
    /**
     * 代休
     */
    SubstituteHoliday(5, "代休"), 
    
    /**
     * 振休
     */
    Pause(6, "振休"),
    
    /**
     * 時間消化休暇
     */
    TimeDigestVacation(7, "時間消化休暇"),
    
    /**
     * 休暇なし
     */
    NoVacation(8, "休暇なし");
    
    public final int value;
    
    public final String name;
}
