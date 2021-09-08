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
        Holiday target = new Holiday();
        Holiday result = HolidayTestHelper.createAnnualHoliday();
        target.changeValue(HolidayTypeClassification.AnnualHoliday);
        
        assertThat( target ).isEqualToComparingFieldByField(result);
    }
    
    /**
     * Target: changeValue
     * Pattern:
     *      休暇種類区分 -> 積立年休
     *      休暇種類.変更する(積立年休) -> 休暇種類.積立年休 is true
     */
    @Test
    public void testChangeValue_isYearlyReserved() {
        Holiday target = new Holiday();
        Holiday result = HolidayTestHelper.createYearlyReserved();
        target.changeValue(HolidayTypeClassification.YearlyReserved);
        
        assertThat( target ).isEqualToComparingFieldByField(result);
    }
    
    /**
     * Target: changeValue
     * Pattern:
     *      休暇種類区分 -> 代休
     *      休暇種類.変更する(代休) -> 休暇種類.代休 is true
     */
    @Test
    public void testChangeValue_isSubstituteHoliday() {
        Holiday target = new Holiday();
        Holiday result = HolidayTestHelper.createSubstituteHoliday();
        target.changeValue(HolidayTypeClassification.SubstituteHoliday);
        
        assertThat( target ).isEqualToComparingFieldByField(result);
    }
    
    /**
     * Target: changeValue
     * Pattern:
     *      休暇種類区分 -> 振休
     *      休暇種類.変更する(振休) -> 休暇種類.振休 is true
     */
    @Test
    public void testChangeValue_isPause() {
        Holiday target = new Holiday();
        Holiday result = HolidayTestHelper.createPause();
        target.changeValue(HolidayTypeClassification.Pause);
        
        assertThat( target ).isEqualToComparingFieldByField(result);
    }
    
    /**
     * Target: changeValue
     * Pattern:
     *      休暇種類区分 -> 休日
     *      休暇種類.変更する(休日) -> 休暇種類.休日 is true
     */
    @Test
    public void testChangeValue_isHoliday() {
        Holiday target = new Holiday();
        Holiday result = HolidayTestHelper.createHoliday();
        target.changeValue(HolidayTypeClassification.Holiday);
        
        assertThat( target ).isEqualToComparingFieldByField(result);
    }
    
    /**
     * Target: changeValue
     * Pattern:
     *      休暇種類区分 -> 特別休暇
     *      休暇種類.変更する(特別休暇) -> 休暇種類.特別休暇 is true
     */
    @Test
    public void testChangeValue_isSpecialHoliday() {
        Holiday target = new Holiday();
        Holiday result = HolidayTestHelper.createSpecialHoliday();
        target.changeValue(HolidayTypeClassification.SpecialHoliday);
        
        assertThat( target ).isEqualToComparingFieldByField(result);
    }
    
    /**
     * Target: changeValue
     * Pattern:
     *      休暇種類区分 -> 時間消化休暇
     *      休暇種類.変更する(時間消化休暇) -> 休暇種類.時間消化休暇 is true
     */
    @Test
    public void testChangeValue_isTimeDigestVacation() {
        Holiday target = new Holiday();
        Holiday result = HolidayTestHelper.createTimeDigestVacation();
        target.changeValue(HolidayTypeClassification.TimeDigestVacation);
        
        assertThat( target ).isEqualToComparingFieldByField(result);
    }
    
    /**
     * Target: changeValue
     * Pattern:
     *      休暇種類区分 -> 休暇なし
     *      休暇種類.変更する(休暇なし) -> 休暇種類変わらない
     */
    @Test
    public void testChangeValue_isNoVacation() {
        Holiday target = new Holiday();
        target.changeValue(HolidayTypeClassification.NoVacation);
        
        assertThat( target ).isEqualToComparingFieldByField(new Holiday());
    }
}
