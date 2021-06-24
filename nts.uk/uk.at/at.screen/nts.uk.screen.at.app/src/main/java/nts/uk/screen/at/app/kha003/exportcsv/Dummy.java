package nts.uk.screen.at.app.kha003.exportcsv;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.record.dom.workrecord.workmanagement.manhoursummarytable.*;
import nts.uk.shr.com.enumcommon.NotUseAtr;

import java.util.*;

public class Dummy {
    private static final String DATE_FORMAT = "yyyy/MM/dd";

    public static class SummaryTableFormat {
        public static ManHourSummaryTableFormat create() {
            return new ManHourSummaryTableFormat(
                    new ManHourSummaryTableCode("01"),
                    new ManHourSummaryTableName("test"),
                    new DetailFormatSetting(
                            DisplayFormat.MINUTE,
                            TotalUnit.DATE,
                            NotUseAtr.NOT_USE,
                            Arrays.asList(
                                    new SummaryItem(1, SummaryItemType.EMPLOYEE),
                                    new SummaryItem(1, SummaryItemType.TASK1)
                            )
                    )
            );
        }
    }

    public static class SummaryTableOutputContent {
        public static ManHourSummaryTableOutputContent create() {
            List<SummaryItemDetail> itemDetails = Arrays.asList(
                    new SummaryItemDetail(
                            "01",
                            new DisplayInformation("A01", "AA"),
                            Arrays.asList(
                                    new SummaryItemDetail(
                                            "0101",
                                            new DisplayInformation("A0101", "AABB1"),
                                            Collections.emptyList(),
                                            Arrays.asList(
                                                    new VerticalValueDaily(1, null, GeneralDate.fromString("2021/06/01", DATE_FORMAT)),
                                                    new VerticalValueDaily(2, null, GeneralDate.fromString("2021/06/02", DATE_FORMAT)),
                                                    new VerticalValueDaily(3, null, GeneralDate.fromString("2021/06/03", DATE_FORMAT))),
                                            Optional.of(2)
                                    ),
                                    new SummaryItemDetail(
                                            "0102",
                                            new DisplayInformation("A0102", "AABB2"),
                                            Arrays.asList(

                                            ),
                                            Arrays.asList(
                                                    new VerticalValueDaily(2, null, GeneralDate.fromString("2021/06/01", DATE_FORMAT)),
                                                    new VerticalValueDaily(4, null, GeneralDate.fromString("2021/06/02", DATE_FORMAT)),
                                                    new VerticalValueDaily(6, null, GeneralDate.fromString("2021/06/03", DATE_FORMAT))),
                                            Optional.of(4)
                                    )
                            ),
                            Arrays.asList(
                                    new VerticalValueDaily(1, null, GeneralDate.fromString("2021/06/01", DATE_FORMAT)),
                                    new VerticalValueDaily(2, null, GeneralDate.fromString("2021/06/02", DATE_FORMAT)),
                                    new VerticalValueDaily(3, null, GeneralDate.fromString("2021/06/03", DATE_FORMAT))),
                            Optional.of(2)
                    ),
                    new SummaryItemDetail(
                            "02",
                            new DisplayInformation("B01", "BB"),
                            Arrays.asList(
                                    new SummaryItemDetail(
                                            "0201",
                                            new DisplayInformation("B0101", "BBCC1"),
                                            Collections.emptyList(),
                                            Arrays.asList(
                                                    new VerticalValueDaily(1, null, GeneralDate.fromString("2021/06/01", DATE_FORMAT)),
                                                    new VerticalValueDaily(2, null, GeneralDate.fromString("2021/06/02", DATE_FORMAT)),
                                                    new VerticalValueDaily(3, null, GeneralDate.fromString("2021/06/03", DATE_FORMAT))),
                                            Optional.of(2)
                                    ),
                                    new SummaryItemDetail(
                                            "0202",
                                            new DisplayInformation("B0102", "BBCC2"),
                                            Collections.emptyList(),
                                            Arrays.asList(
                                                    new VerticalValueDaily(2, null, GeneralDate.fromString("2021/06/01", DATE_FORMAT)),
                                                    new VerticalValueDaily(4, null, GeneralDate.fromString("2021/06/02", DATE_FORMAT)),
                                                    new VerticalValueDaily(6, null, GeneralDate.fromString("2021/06/03", DATE_FORMAT))),
                                            Optional.of(4)
                                    )
                            ),
                            Arrays.asList(
                                    new VerticalValueDaily(2, null, GeneralDate.fromString("2021/06/01", DATE_FORMAT)),
                                    new VerticalValueDaily(4, null, GeneralDate.fromString("2021/06/02", DATE_FORMAT)),
                                    new VerticalValueDaily(6, null, GeneralDate.fromString("2021/06/03", DATE_FORMAT))),
                            Optional.of(4)
                    )
            );

            List<VerticalValueDaily> verticalTotalValues = Arrays.asList(
                    new VerticalValueDaily(3, null, GeneralDate.fromString("2021/06/01", DATE_FORMAT)),
                    new VerticalValueDaily(6, null, GeneralDate.fromString("2021/06/02", DATE_FORMAT)),
                    new VerticalValueDaily(9, null, GeneralDate.fromString("2021/06/03", DATE_FORMAT))
            );
            return new ManHourSummaryTableOutputContent(
                    itemDetails,
                    verticalTotalValues,
                    Optional.of(18)
            );
        }
    }
}
