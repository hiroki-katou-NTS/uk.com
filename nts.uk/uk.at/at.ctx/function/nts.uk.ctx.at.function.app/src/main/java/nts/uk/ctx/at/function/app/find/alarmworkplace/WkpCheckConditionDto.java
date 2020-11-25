package nts.uk.ctx.at.function.app.find.alarmworkplace;

import lombok.AllArgsConstructor;
import lombok.Data;
import nts.uk.ctx.at.function.app.find.alarmworkplace.extractionrange.WkpExtractionPeriodDailyDto;
import nts.uk.ctx.at.function.app.find.alarmworkplace.extractionrange.WkpExtractionPeriodMonthlyDto;
import nts.uk.ctx.at.function.app.find.alarmworkplace.extractionrange.WkpSingleMonthDto;
import nts.uk.ctx.at.function.dom.alarm.extractionrange.month.singlemonth.SingleMonth;
import nts.uk.ctx.at.function.dom.alarmworkplace.CheckCondition;
import nts.uk.ctx.at.function.dom.alarmworkplace.ExtractionPeriodDaily;
import nts.uk.ctx.at.function.dom.alarmworkplace.ExtractionPeriodMonthly;
import nts.uk.ctx.at.function.dom.alarmworkplace.checkcondition.WorkplaceCategory;

import java.util.List;

@Data
@AllArgsConstructor
public class WkpCheckConditionDto {
    private int alarmCategory;
    private List<String> checkConditionCodes;
    private WkpExtractionPeriodDailyDto extractionDaily;
    private WkpExtractionPeriodMonthlyDto listExtractionMonthly;
    private WkpSingleMonthDto singleMonth;

    public WkpCheckConditionDto(int alarmCategory, List<String> checkConditionCodes, WkpExtractionPeriodDailyDto extractionDaily) {
        this.alarmCategory = alarmCategory;
        this.checkConditionCodes = checkConditionCodes;
        this.extractionDaily = extractionDaily;
    }

    public WkpCheckConditionDto(int alarmCategory, List<String> checkConditionCodes, WkpExtractionPeriodMonthlyDto listExtractionMonthly) {
        this.alarmCategory = alarmCategory;
        this.checkConditionCodes = checkConditionCodes;
        this.listExtractionMonthly = listExtractionMonthly;
    }

    public WkpCheckConditionDto(int alarmCategory, List<String> checkConditionCodes, WkpSingleMonthDto singleMonth) {
        this.alarmCategory = alarmCategory;
        this.checkConditionCodes = checkConditionCodes;
        this.singleMonth = singleMonth;
    }

    public WkpCheckConditionDto(int alarmCategory, List<String> checkConditionCodes) {
        this.alarmCategory = alarmCategory;
        this.checkConditionCodes = checkConditionCodes;
    }

    public static WkpCheckConditionDto setdata(CheckCondition domain, List<String> lstCode) {
        if (domain.getWorkplaceCategory().value == WorkplaceCategory.MONTHLY.value) {
            SingleMonth singleMonth = ((SingleMonth) domain.getRangeToExtract());
            return new WkpCheckConditionDto(
                domain.getWorkplaceCategory().value,
                lstCode,
                new WkpSingleMonthDto(singleMonth.getMonthPrevious().value, singleMonth.getMonthNo(), singleMonth.isCurentMonth())
            );
        } else if (domain.getWorkplaceCategory().value == WorkplaceCategory.MASTER_CHECK_BASIC.value || domain.getWorkplaceCategory().value == WorkplaceCategory.MASTER_CHECK_WORKPLACE.value) {
            ExtractionPeriodMonthly periodMonthly = ((ExtractionPeriodMonthly) domain.getRangeToExtract());
            return new WkpCheckConditionDto(
                domain.getWorkplaceCategory().value,
                lstCode,
                WkpExtractionPeriodMonthlyDto.setdata(periodMonthly)
            );
        } else if (domain.getWorkplaceCategory().value == WorkplaceCategory.MASTER_CHECK_DAILY.value ||
            domain.getWorkplaceCategory().value == WorkplaceCategory.SCHEDULE_DAILY.value ||
            domain.getWorkplaceCategory().value == WorkplaceCategory.APPLICATION_APPROVAL.value) {
            ExtractionPeriodDaily periodDaily = ((ExtractionPeriodDaily) domain.getRangeToExtract());
            return new WkpCheckConditionDto(
                domain.getWorkplaceCategory().value,
                lstCode,
                WkpExtractionPeriodDailyDto.setdata(periodDaily)
            );
        } else {
            return new WkpCheckConditionDto(domain.getWorkplaceCategory().value, lstCode);
        }
    }
}
