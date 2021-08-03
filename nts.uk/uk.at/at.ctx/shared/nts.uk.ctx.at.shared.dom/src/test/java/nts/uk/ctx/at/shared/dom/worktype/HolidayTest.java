package nts.uk.ctx.at.shared.dom.worktype;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;
import org.junit.runner.RunWith;

import mockit.integration.junit4.JMockit;

/**
 * @author anhnm
 *
 */
@RunWith(JMockit.class)
public class HolidayTest {

    /**
     * Target: changeValue
     * Pattern:
     *      休暇種類区分 -> 年休
     *      休暇種類.変更する(年休) -> 休暇種類.年休 is true
     */
    @Test
    public void testChangeValue_isAnnualHoliday() {
        Holiday target = Helper.createHoliday(false, false, false, false, false, false, false);
        target.changeValue(HolidayTypeClassification.AnnualHoliday);
        
        assertThat( target ).isEqualToComparingFieldByField(Helper.createHoliday(true, false, false, false, false, false, false));
    }
    
    /**
     * Target: changeValue
     * Pattern:
     *      休暇種類区分 -> 積立年休
     *      休暇種類.変更する(積立年休) -> 休暇種類.積立年休 is true
     */
    @Test
    public void testChangeValue_isYearlyReserved() {
        Holiday target = Helper.createHoliday(false, false, false, false, false, false, false);
        target.changeValue(HolidayTypeClassification.YearlyReserved);
        
        assertThat( target ).isEqualToComparingFieldByField(Helper.createHoliday(false, true, false, false, false, false, false));
    }
    
    /**
     * Target: changeValue
     * Pattern:
     *      休暇種類区分 -> 代休
     *      休暇種類.変更する(代休) -> 休暇種類.代休 is true
     */
    @Test
    public void testChangeValue_isSubstituteHoliday() {
        Holiday target = Helper.createHoliday(false, false, false, false, false, false, false);
        target.changeValue(HolidayTypeClassification.SubstituteHoliday);
        
        assertThat( target ).isEqualToComparingFieldByField(Helper.createHoliday(false, false, true, false, false, false, false));
    }
    
    /**
     * Target: changeValue
     * Pattern:
     *      休暇種類区分 -> 振休
     *      休暇種類.変更する(振休) -> 休暇種類.振休 is true
     */
    @Test
    public void testChangeValue_isPause() {
        Holiday target = Helper.createHoliday(false, false, false, false, false, false, false);
        target.changeValue(HolidayTypeClassification.Pause);
        
        assertThat( target ).isEqualToComparingFieldByField(Helper.createHoliday(false, false, false, true, false, false, false));
    }
    
    /**
     * Target: changeValue
     * Pattern:
     *      休暇種類区分 -> 休日
     *      休暇種類.変更する(休日) -> 休暇種類.休日 is true
     */
    @Test
    public void testChangeValue_isHoliday() {
        Holiday target = Helper.createHoliday(false, false, false, false, false, false, false);
        target.changeValue(HolidayTypeClassification.Holiday);
        
        assertThat( target ).isEqualToComparingFieldByField(Helper.createHoliday(false, false, false, false, true, false, false));
    }
    
    /**
     * Target: changeValue
     * Pattern:
     *      休暇種類区分 -> 特別休暇
     *      休暇種類.変更する(特別休暇) -> 休暇種類.特別休暇 is true
     */
    @Test
    public void testChangeValue_isSpecialHoliday() {
        Holiday target = Helper.createHoliday(false, false, false, false, false, false, false);
        target.changeValue(HolidayTypeClassification.SpecialHoliday);
        
        assertThat( target ).isEqualToComparingFieldByField(Helper.createHoliday(false, false, false, false, false, true, false));
    }
    
    /**
     * Target: changeValue
     * Pattern:
     *      休暇種類区分 -> 時間消化休暇
     *      休暇種類.変更する(時間消化休暇) -> 休暇種類.時間消化休暇 is true
     */
    @Test
    public void testChangeValue_isTimeDigestVacation() {
        Holiday target = Helper.createHoliday(false, false, false, false, false, false, false);
        target.changeValue(HolidayTypeClassification.TimeDigestVacation);
        
        assertThat( target ).isEqualToComparingFieldByField(Helper.createHoliday(false, false, false, false, false, false, true));
    }
    
    /**
     * Target: changeValue
     * Pattern:
     *      休暇種類区分 -> 休暇なし
     *      休暇種類.変更する(休暇なし) -> 休暇種類変わらない
     */
    @Test
    public void testChangeValue_isNoVacation() {
        Holiday target = Helper.createHoliday(false, false, false, false, false, false, false);
        target.changeValue(HolidayTypeClassification.NoVacation);
        
        assertThat( target ).isEqualToComparingFieldByField(Helper.createHoliday(false, false, false, false, false, false, false));
    }

    public static class Helper {
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
    }
}
