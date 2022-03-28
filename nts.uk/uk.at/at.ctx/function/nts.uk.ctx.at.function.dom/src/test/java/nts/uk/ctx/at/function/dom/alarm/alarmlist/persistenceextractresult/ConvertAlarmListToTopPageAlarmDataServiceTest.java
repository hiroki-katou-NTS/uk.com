package nts.uk.ctx.at.function.dom.alarm.alarmlist.persistenceextractresult;

import lombok.val;
import mockit.Expectations;
import mockit.Injectable;
import mockit.integration.junit4.JMockit;
import nts.uk.ctx.at.function.dom.alarm.AlarmPatternCode;
import nts.uk.ctx.at.function.dom.processexecution.ExecutionCode;
import nts.uk.ctx.at.shared.dom.alarmList.persistenceextractresult.PersistenceAlarmListExtractResult;
import nts.uk.shr.com.context.AppContexts;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(JMockit.class)
public class ConvertAlarmListToTopPageAlarmDataServiceTest {
    @Injectable
    private ConvertAlarmListToTopPageAlarmDataService.Require require;

    /**
     * isDisplayByAdmin = false && isDisplayByUser = false
     */
    @Test
    public void testConvert_returnEmpty(){
        String companyId = "companyId";
        List<String> employeeIds = Arrays.asList("sya001", "sya002");
        AlarmPatternCode patternCode = new AlarmPatternCode("patternCode1");
        ExecutionCode executionCode = new ExecutionCode("Z");
        boolean isDisplayByAdmin = false;
        boolean isDisplayByUser = false;

        val actual = ConvertAlarmListToTopPageAlarmDataService.convert(require, companyId,
                employeeIds, patternCode, executionCode, isDisplayByAdmin, isDisplayByUser);
        assertThat(actual.isEmpty()).isTrue();
    }

    /**
     * isDisplayByAdmin = true && isDisplayByUser = true
     */
    @Test
    public void testConvert_displayByAdminAndDisplayByUser(){
        String companyId = "companyId";
        List<String> employeeIds = Arrays.asList("sya001", "sya002", "sya003", "sya004");
        AlarmPatternCode patternCode = new AlarmPatternCode("patternCode1");
        ExecutionCode executionCode = new ExecutionCode("Z");
        boolean isDisplayByAdmin = true;
        boolean isDisplayByUser = true;

        PersistenceAlarmListExtractResult extractResult = DumData.dumDomain;

        new Expectations(AppContexts.class){
            {
                require.getAlarmListExtractionResult(companyId, patternCode.v(), executionCode.v(), employeeIds);
                result = Optional.of(extractResult);
            }
        };

        val actual = ConvertAlarmListToTopPageAlarmDataService.convert(require, companyId,
                employeeIds, patternCode, executionCode, isDisplayByAdmin, isDisplayByUser);
        assertThat(actual.size()).isEqualTo(2);
    }

    /**
     * isDisplayByAdmin = true && isDisplayByUser = false
     */
    @Test
    public void testConvert_displayByAdmin(){
        String companyId = "companyId";
        List<String> employeeIds = Arrays.asList("sya001", "sya002");
        AlarmPatternCode patternCode = new AlarmPatternCode("patternCode1");
        ExecutionCode executionCode = new ExecutionCode("Z");
        boolean isDisplayByAdmin = true;
        boolean isDisplayByUser = false;
        PersistenceAlarmListExtractResult extractResult = DumData.dumDomain;

        new Expectations(AppContexts.class){
            {
                require.getAlarmListExtractionResult(companyId, patternCode.v(), executionCode.v(), employeeIds);
                result = Optional.of(extractResult);
            }
        };

        val actual = ConvertAlarmListToTopPageAlarmDataService.convert(require, companyId,
                employeeIds, patternCode, executionCode, isDisplayByAdmin, isDisplayByUser);
        assertThat(actual.size()).isEqualTo(1);

    }

    /**
     * isDisplayByAdmin = false & isDisplayByUser = true
     */
    @Test
    public void testConvert_displayByUser(){
        String companyId = "companyId";
        List<String> employeeIds = Arrays.asList("sya001", "sya002", "sya003", "sya004");
        AlarmPatternCode patternCode = new AlarmPatternCode("patternCode1");
        ExecutionCode executionCode = new ExecutionCode("Z");
        boolean isDisplayByAdmin = false;
        boolean isDisplayByUser = true;

        val actual = ConvertAlarmListToTopPageAlarmDataService.convert(require, companyId,
                employeeIds, patternCode, executionCode, isDisplayByAdmin, isDisplayByUser);
        assertThat(actual.size()).isEqualTo(1);
    }

    /**
     * extractResult NOT isPresent
     * isDisplayByAdmin = true
     * boolean isDisplayByUser = false;
     */
    @Test
    public void testConvert_AlarmAListExtrectionResults_Not_isPresent_DispByAdmin(){
        String companyId = "companyId";
        List<String> employeeIds = Arrays.asList("sya001", "sya002");
        AlarmPatternCode patternCode = new AlarmPatternCode("patternCode1");
        ExecutionCode executionCode = new ExecutionCode("Z");
        boolean isDisplayByAdmin = true;
        boolean isDisplayByUser = false;

        new Expectations(AppContexts.class){
            {
                require.getAlarmListExtractionResult(companyId, patternCode.v(), executionCode.v(), employeeIds);
                result = Optional.empty();
            }
        };

        val actual = ConvertAlarmListToTopPageAlarmDataService.convert(require, companyId,
                employeeIds, patternCode, executionCode, isDisplayByAdmin, isDisplayByUser);
        assertThat(actual.size()).isEqualTo(1);
    }

    /**
     * extractResult NOT isPresent
     * isDisplayByAdmin = false
     * boolean isDisplayByUser = true;
     */
    @Test
    public void testConvert_AlarmAListExtrectionResults_Not_isPresent_DispByUser(){
        String companyId = "companyId";
        List<String> employeeIds = Arrays.asList("sya001", "sya002");
        AlarmPatternCode patternCode = new AlarmPatternCode("patternCode1");
        ExecutionCode executionCode = new ExecutionCode("Z");
        boolean isDisplayByAdmin = false;
        boolean isDisplayByUser = true;

        new Expectations(AppContexts.class){
            {
                require.getAlarmListExtractionResult(companyId, patternCode.v(), executionCode.v(), employeeIds);
                result = Optional.empty();
            }
        };

        val actual = ConvertAlarmListToTopPageAlarmDataService.convert(require, companyId,
                employeeIds, patternCode, executionCode, isDisplayByAdmin, isDisplayByUser);
        assertThat(actual.size()).isEqualTo(1);
    }

    /**
     * extractResult NOT isPresent
     * isDisplayByAdmin = true
     * boolean isDisplayByUser = true;
     */
    @Test
    public void testConvert_AlarmAListExtrectionResults_Not_isPresent_DispAdmin_DispUser(){
        String companyId = "companyId";
        List<String> employeeIds = Arrays.asList("sya001", "sya002");
        AlarmPatternCode patternCode = new AlarmPatternCode("patternCode1");
        ExecutionCode executionCode = new ExecutionCode("Z");
        boolean isDisplayByAdmin = true;
        boolean isDisplayByUser = true;

        new Expectations(AppContexts.class){
            {
                require.getAlarmListExtractionResult(companyId, patternCode.v(), executionCode.v(), employeeIds);
                result = Optional.empty();
            }
        };

        val actual = ConvertAlarmListToTopPageAlarmDataService.convert(require, companyId,
                employeeIds, patternCode, executionCode, isDisplayByAdmin, isDisplayByUser);
        assertThat(actual.size()).isEqualTo(2);
    }

    /**
     * empIdListNoErrors Not empty
     * isDisplayByAdmin = true
     * boolean isDisplayByUser = true;
     */
    @Test
    public void testConvert_empIdListNoErrors_NotEmpty(){
        String companyId = "companyId";
        List<String> employeeIds = Arrays.asList("sya005", "sya006");
        AlarmPatternCode patternCode = new AlarmPatternCode("patternCode1");
        ExecutionCode executionCode = new ExecutionCode("Z");
        boolean isDisplayByAdmin = true;
        boolean isDisplayByUser = true;
        PersistenceAlarmListExtractResult extractResult = DumData.dumDomain;

        new Expectations(AppContexts.class){
            {
                require.getAlarmListExtractionResult(companyId, patternCode.v(), executionCode.v(), employeeIds);
                result = Optional.of(extractResult);
            }
        };

        val actual = ConvertAlarmListToTopPageAlarmDataService.convert(require, companyId,
                employeeIds, patternCode, executionCode, isDisplayByAdmin, isDisplayByUser);
        assertThat(actual.size()).isEqualTo(2);
    }

    /**
     * empIdListNoErrors NOT empty
     * isDisplayByAdmin = true
     * boolean isDisplayByUser = false;
     */
    @Test
    public void testConvert_empIdListNoErrors_NotEmpty_DispAdmin(){
        String companyId = "companyId";
        List<String> employeeIds = Arrays.asList("sya005", "sya006");
        AlarmPatternCode patternCode = new AlarmPatternCode("patternCode1");
        ExecutionCode executionCode = new ExecutionCode("Z");
        boolean isDisplayByAdmin = true;
        boolean isDisplayByUser = false;
        PersistenceAlarmListExtractResult extractResult = DumData.dumDomain;

        new Expectations(){
            {
                require.getAlarmListExtractionResult(companyId, patternCode.v(), executionCode.v(), employeeIds);
                result = Optional.of(extractResult);
            }
        };

        val actual = ConvertAlarmListToTopPageAlarmDataService.convert(require, companyId,
                employeeIds, patternCode, executionCode, isDisplayByAdmin, isDisplayByUser);
        assertThat(actual.size()).isEqualTo(1);
    }

    /**
     * empIdListNoErrors NOT empty
     * isDisplayByAdmin = false
     * boolean isDisplayByUser = true;
     */
    @Test
        public void testConvert_empIdListNoErrors_NotEmpty_DispUser(){
        String companyId = "companyId";
        List<String> employeeIds = Arrays.asList("sya005", "sya006");
        AlarmPatternCode patternCode = new AlarmPatternCode("patternCode1");
        ExecutionCode executionCode = new ExecutionCode("Z");
        boolean isDisplayByAdmin = false;
        boolean isDisplayByUser = true;
        PersistenceAlarmListExtractResult extractResult = DumData.dumDomain;

        new Expectations(AppContexts.class){
            {
                require.getAlarmListExtractionResult(companyId, patternCode.v(), executionCode.v(), employeeIds);
                result = Optional.of(extractResult);
            }
        };

        val actual = ConvertAlarmListToTopPageAlarmDataService.convert(require, companyId,
                employeeIds, patternCode, executionCode, isDisplayByAdmin, isDisplayByUser);
        assertThat(actual.size()).isEqualTo(1);
    }

    /**
     * empIdListNoErrors empty
     * isDisplayByAdmin = true
     * boolean isDisplayByUser = true;
     */
    @Test
    public void testConvert_empIdListNoErrors_Empty(){
        String companyId = "companyId";
        List<String> employeeIds = Arrays.asList("00001", "00002");
        AlarmPatternCode patternCode = new AlarmPatternCode("patternCode1");
        ExecutionCode executionCode = new ExecutionCode("Z");
        boolean isDisplayByAdmin = true;
        boolean isDisplayByUser = true;
        PersistenceAlarmListExtractResult extractResult = DumData.dumDomain;

        new Expectations(AppContexts.class){
            {
                require.getAlarmListExtractionResult(companyId, patternCode.v(), executionCode.v(), employeeIds);
                result = Optional.of(extractResult);
            }
        };

        val actual = ConvertAlarmListToTopPageAlarmDataService.convert(require, companyId,
                employeeIds, patternCode, executionCode, isDisplayByAdmin, isDisplayByUser);
        assertThat(actual.size()).isEqualTo(2);
    }

    /**
     * empIdListNoErrors empty
     * isDisplayByAdmin = true
     * boolean isDisplayByUser = false;
     */
    @Test
    public void testConvert_empIdListNoErrors_Empty_DispAdmin(){
        String companyId = "companyId";
        List<String> employeeIds = Arrays.asList("00001", "00002");
        AlarmPatternCode patternCode = new AlarmPatternCode("patternCode1");
        ExecutionCode executionCode = new ExecutionCode("Z");
        boolean isDisplayByAdmin = true;
        boolean isDisplayByUser = false;
        PersistenceAlarmListExtractResult extractResult = DumData.dumDomain;

        new Expectations(AppContexts.class){
            {
                require.getAlarmListExtractionResult(companyId, patternCode.v(), executionCode.v(), employeeIds);
                result = Optional.of(extractResult);
            }
        };

        val actual = ConvertAlarmListToTopPageAlarmDataService.convert(require, companyId,
                employeeIds, patternCode, executionCode, isDisplayByAdmin, isDisplayByUser);
        assertThat(actual.size()).isEqualTo(1);
    }

    /**
     * empIdListNoErrors empty
     * isDisplayByAdmin = false
     * boolean isDisplayByUser = true;
     */
    @Test
    public void testConvert_empIdListNoErrors_Empty_DispUser(){
        String companyId = "companyId";
        List<String> employeeIds = Arrays.asList("00001", "00002");
        AlarmPatternCode patternCode = new AlarmPatternCode("patternCode1");
        ExecutionCode executionCode = new ExecutionCode("Z");
        boolean isDisplayByAdmin = false;
        boolean isDisplayByUser = true;
        PersistenceAlarmListExtractResult extractResult = DumData.dumDomain;

        new Expectations(AppContexts.class){
            {
                require.getAlarmListExtractionResult(companyId, patternCode.v(), executionCode.v(), employeeIds);
                result = Optional.of(extractResult);
            }
        };

        val actual = ConvertAlarmListToTopPageAlarmDataService.convert(require, companyId,
                employeeIds, patternCode, executionCode, isDisplayByAdmin, isDisplayByUser);
        assertThat(actual.size()).isEqualTo(1);
    }
}
