package nts.uk.ctx.at.record.dom.workrecord.workmanagement.manhoursummarytable;

import lombok.val;
import mockit.integration.junit4.JMockit;
import nts.arc.testing.assertion.NtsAssert;
import nts.arc.time.GeneralDate;
import nts.arc.time.YearMonth;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;

@RunWith(JMockit.class)
public class ManHourSummaryTableOutputContentTest {
    private final List<YearMonth> yearMonthList = Collections.singletonList(YearMonth.of(6));

    /**
     * Getter
     */
    @Test
    public void getters() {
        val itemDetails = Helper.summaryItemDetail.getList(3);
//        val verticalTotalValues = Helper.verticalValueDaily.getList(1);
        val instance = new ManHourSummaryTableOutputContent(itemDetails);
        NtsAssert.invokeGetters(instance);
    }

    /**
     * TotalUnit = Date
     */
    @Test
    public void calculateTotal_UnitDate() {
        List<GeneralDate> dateList = Helper.dateList;
        val itemDetails = Helper.summaryItemDetail.getList(2);
        val instance = new ManHourSummaryTableOutputContent(itemDetails);

        // Execute
        instance.calculateTotal(TotalUnit.DATE, dateList, yearMonthList);

        // Assertion
        assertThat(instance.getTotalPeriod().get()).isEqualTo(18);
        val targetDispInfo1 = new DisplayInformation("01", "NAME1");
        val targetDispInfo2 = new DisplayInformation("01", "NAME1");
//        assertThat(instance.getItemDetails())
//                .extracting(
//                        SummaryItemDetail::getCode,
//                        SummaryItemDetail::getDisplayInfo,
//                        SummaryItemDetail::getChildHierarchyList,
//                        SummaryItemDetail::getVerticalTotalList,
//                        SummaryItemDetail::getTotalPeriod)
//                .containsExactly(
//                        tuple("01, targetDispInfo1
//                ); TODO TODO
        assertThat(instance.getVerticalTotalValues())
                .extracting(
                        VerticalValueDaily::getWorkingHours,
                        VerticalValueDaily::getYearMonth,
                        VerticalValueDaily::getDate)
                .containsExactly(
                        tuple(3, null, GeneralDate.fromString("2021/06/01", "yyyy/MM/dd")),
                        tuple(6, null, GeneralDate.fromString("2021/06/02", "yyyy/MM/dd")),
                        tuple(9, null, GeneralDate.fromString("2021/06/03", "yyyy/MM/dd"))
                );
    }
}
