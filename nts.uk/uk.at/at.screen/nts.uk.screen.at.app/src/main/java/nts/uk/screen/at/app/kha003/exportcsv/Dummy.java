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
                            NotUseAtr.USE,
                            Arrays.asList(
                                    new SummaryItem(1, SummaryItemType.EMPLOYEE),
                                    new SummaryItem(1, SummaryItemType.TASK1),
                                    new SummaryItem(1, SummaryItemType.TASK3),
                                    new SummaryItem(1, SummaryItemType.TASK5)
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
                            new DisplayInformation("01", "LEVEL1_01"),
                            Arrays.asList(
                                    new SummaryItemDetail(
                                            "0101",
                                            new DisplayInformation("0101", "LEVEL2_01"),
                                            Arrays.asList(
                                                    new SummaryItemDetail(
                                                            "010101",
                                                            new DisplayInformation("A01010101", "LEVEL3_01"),
                                                            Arrays.asList(
                                                                    new SummaryItemDetail(
                                                                            "010101",
                                                                            new DisplayInformation("A01010101", "LEVEL4_01"),
                                                                            Collections.emptyList(),
                                                                            Arrays.asList(
                                                                                    new VerticalValueDaily(1, null, GeneralDate.fromString("2021/06/01", DATE_FORMAT)),
                                                                                    new VerticalValueDaily(1, null, GeneralDate.fromString("2021/06/02", DATE_FORMAT)),
                                                                                    new VerticalValueDaily(1, null, GeneralDate.fromString("2021/06/03", DATE_FORMAT))),
                                                                            Optional.of(3)
                                                                    ),
                                                                    new SummaryItemDetail(
                                                                            "010101",
                                                                            new DisplayInformation("A01010101", "LEVEL4_02"),
                                                                            Collections.emptyList(),
                                                                            Arrays.asList(
                                                                                    new VerticalValueDaily(2, null, GeneralDate.fromString("2021/06/01", DATE_FORMAT)),
                                                                                    new VerticalValueDaily(2, null, GeneralDate.fromString("2021/06/02", DATE_FORMAT)),
                                                                                    new VerticalValueDaily(2, null, GeneralDate.fromString("2021/06/03", DATE_FORMAT))),
                                                                            Optional.of(6)
                                                                    )
                                                            ),
                                                            Arrays.asList(
                                                                    new VerticalValueDaily(3, null, GeneralDate.fromString("2021/06/01", DATE_FORMAT)),
                                                                    new VerticalValueDaily(3, null, GeneralDate.fromString("2021/06/02", DATE_FORMAT)),
                                                                    new VerticalValueDaily(3, null, GeneralDate.fromString("2021/06/03", DATE_FORMAT))),
                                                            Optional.of(9)
                                                    ),
                                                    new SummaryItemDetail(
                                                            "010102",
                                                            new DisplayInformation("A01010101", "LEVEL3_02"),
                                                            Arrays.asList(
                                                                    new SummaryItemDetail(
                                                                            "010101",
                                                                            new DisplayInformation("A01010101", "LEVEL4_02"),
                                                                            Collections.emptyList(),
                                                                            Arrays.asList(
                                                                                    new VerticalValueDaily(1, null, GeneralDate.fromString("2021/06/01", DATE_FORMAT)),
                                                                                    new VerticalValueDaily(1, null, GeneralDate.fromString("2021/06/02", DATE_FORMAT)),
                                                                                    new VerticalValueDaily(1, null, GeneralDate.fromString("2021/06/03", DATE_FORMAT))),
                                                                            Optional.of(3)
                                                                    ),
                                                                    new SummaryItemDetail(
                                                                            "010102",
                                                                            new DisplayInformation("A01010102", "LEVEL4_02"),
                                                                            Collections.emptyList(),
                                                                            Arrays.asList(
                                                                                    new VerticalValueDaily(2, null, GeneralDate.fromString("2021/06/01", DATE_FORMAT)),
                                                                                    new VerticalValueDaily(2, null, GeneralDate.fromString("2021/06/02", DATE_FORMAT)),
                                                                                    new VerticalValueDaily(2, null, GeneralDate.fromString("2021/06/03", DATE_FORMAT))),
                                                                            Optional.of(6)
                                                                    )
                                                            ),
                                                            Arrays.asList(
                                                                    new VerticalValueDaily(3, null, GeneralDate.fromString("2021/06/01", DATE_FORMAT)),
                                                                    new VerticalValueDaily(3, null, GeneralDate.fromString("2021/06/02", DATE_FORMAT)),
                                                                    new VerticalValueDaily(3, null, GeneralDate.fromString("2021/06/03", DATE_FORMAT))),
                                                            Optional.of(9)
                                                    )
                                            ),
                                            Arrays.asList(
                                                    new VerticalValueDaily(9, null, GeneralDate.fromString("2021/06/01", DATE_FORMAT)),
                                                    new VerticalValueDaily(9, null, GeneralDate.fromString("2021/06/02", DATE_FORMAT)),
                                                    new VerticalValueDaily(9, null, GeneralDate.fromString("2021/06/03", DATE_FORMAT))),
                                            Optional.of(27)
                                    ),
                                    // LEVEL1_02
                                    new SummaryItemDetail(
                                            "0102",
                                            new DisplayInformation("0102", "LEVEL2_02"),
                                            Arrays.asList(
                                                    new SummaryItemDetail(
                                                            "010201",
                                                            new DisplayInformation("010201", "LEVEL3_02"),
                                                            Arrays.asList(
                                                                    new SummaryItemDetail(
                                                                            "010201",
                                                                            new DisplayInformation("010201", "LEVEL4_02"),
                                                                            Collections.emptyList(),
                                                                            Arrays.asList(
                                                                                    new VerticalValueDaily(1, null, GeneralDate.fromString("2021/06/01", DATE_FORMAT)),
                                                                                    new VerticalValueDaily(1, null, GeneralDate.fromString("2021/06/02", DATE_FORMAT)),
                                                                                    new VerticalValueDaily(1, null, GeneralDate.fromString("2021/06/03", DATE_FORMAT))),
                                                                            Optional.of(3)
                                                                    ),
                                                                    new SummaryItemDetail(
                                                                            "010202",
                                                                            new DisplayInformation("010202", "LEVEL4_02"),
                                                                            Collections.emptyList(),
                                                                            Arrays.asList(
                                                                                    new VerticalValueDaily(2, null, GeneralDate.fromString("2021/06/01", DATE_FORMAT)),
                                                                                    new VerticalValueDaily(2, null, GeneralDate.fromString("2021/06/02", DATE_FORMAT)),
                                                                                    new VerticalValueDaily(2, null, GeneralDate.fromString("2021/06/03", DATE_FORMAT))),
                                                                            Optional.of(6)
                                                                    )
                                                            ),
                                                            Arrays.asList(
                                                                    new VerticalValueDaily(3, null, GeneralDate.fromString("2021/06/01", DATE_FORMAT)),
                                                                    new VerticalValueDaily(3, null, GeneralDate.fromString("2021/06/02", DATE_FORMAT)),
                                                                    new VerticalValueDaily(3, null, GeneralDate.fromString("2021/06/03", DATE_FORMAT))),
                                                            Optional.of(9)
                                                    ),
                                                    new SummaryItemDetail(
                                                            "010202",
                                                            new DisplayInformation("010202", "LEVEL3_02"),
                                                            Arrays.asList(
                                                                    new SummaryItemDetail(
                                                                            "01020201",
                                                                            new DisplayInformation("01020201", "LEVEL4_02"),
                                                                            Collections.emptyList(),
                                                                            Arrays.asList(
                                                                                    new VerticalValueDaily(1, null, GeneralDate.fromString("2021/06/01", DATE_FORMAT)),
                                                                                    new VerticalValueDaily(1, null, GeneralDate.fromString("2021/06/02", DATE_FORMAT)),
                                                                                    new VerticalValueDaily(1, null, GeneralDate.fromString("2021/06/03", DATE_FORMAT))),
                                                                            Optional.of(3)
                                                                    ),
                                                                    new SummaryItemDetail(
                                                                            "01020202",
                                                                            new DisplayInformation("01020202", "LEVEL4_02"),
                                                                            Collections.emptyList(),
                                                                            Arrays.asList(
                                                                                    new VerticalValueDaily(2, null, GeneralDate.fromString("2021/06/01", DATE_FORMAT)),
                                                                                    new VerticalValueDaily(2, null, GeneralDate.fromString("2021/06/02", DATE_FORMAT)),
                                                                                    new VerticalValueDaily(2, null, GeneralDate.fromString("2021/06/03", DATE_FORMAT))),
                                                                            Optional.of(6)
                                                                    )
                                                            ),
                                                            Arrays.asList(
                                                                    new VerticalValueDaily(3, null, GeneralDate.fromString("2021/06/01", DATE_FORMAT)),
                                                                    new VerticalValueDaily(3, null, GeneralDate.fromString("2021/06/02", DATE_FORMAT)),
                                                                    new VerticalValueDaily(3, null, GeneralDate.fromString("2021/06/03", DATE_FORMAT))),
                                                            Optional.of(9)
                                                    )
                                            ),
                                            Arrays.asList(
                                                    new VerticalValueDaily(18, null, GeneralDate.fromString("2021/06/01", DATE_FORMAT)),
                                                    new VerticalValueDaily(18, null, GeneralDate.fromString("2021/06/02", DATE_FORMAT)),
                                                    new VerticalValueDaily(18, null, GeneralDate.fromString("2021/06/03", DATE_FORMAT))),
                                            Optional.of(54)
                                    )
                            ),
                            Arrays.asList(
                                    new VerticalValueDaily(3, null, GeneralDate.fromString("2021/06/01", DATE_FORMAT)),
                                    new VerticalValueDaily(6, null, GeneralDate.fromString("2021/06/02", DATE_FORMAT)),
                                    new VerticalValueDaily(9, null, GeneralDate.fromString("2021/06/03", DATE_FORMAT))),
                            Optional.of(18)
                    ),
                    new SummaryItemDetail(
                            "01",
                            new DisplayInformation("01", "LEVEL1_01"),
                            Arrays.asList(
                                    new SummaryItemDetail(
                                            "0101",
                                            new DisplayInformation("0101", "LEVEL2_01"),
                                            Arrays.asList(
                                                    new SummaryItemDetail(
                                                            "010101",
                                                            new DisplayInformation("A01010101", "LEVEL3_01"),
                                                            Arrays.asList(
                                                                    new SummaryItemDetail(
                                                                            "010101",
                                                                            new DisplayInformation("A01010101", "LEVEL4_01"),
                                                                            Collections.emptyList(),
                                                                            Arrays.asList(
                                                                                    new VerticalValueDaily(1, null, GeneralDate.fromString("2021/06/01", DATE_FORMAT)),
                                                                                    new VerticalValueDaily(1, null, GeneralDate.fromString("2021/06/02", DATE_FORMAT)),
                                                                                    new VerticalValueDaily(1, null, GeneralDate.fromString("2021/06/03", DATE_FORMAT))),
                                                                            Optional.of(3)
                                                                    ),
                                                                    new SummaryItemDetail(
                                                                            "010101",
                                                                            new DisplayInformation("A01010101", "LEVEL4_02"),
                                                                            Collections.emptyList(),
                                                                            Arrays.asList(
                                                                                    new VerticalValueDaily(2, null, GeneralDate.fromString("2021/06/01", DATE_FORMAT)),
                                                                                    new VerticalValueDaily(2, null, GeneralDate.fromString("2021/06/02", DATE_FORMAT)),
                                                                                    new VerticalValueDaily(2, null, GeneralDate.fromString("2021/06/03", DATE_FORMAT))),
                                                                            Optional.of(6)
                                                                    )
                                                            ),
                                                            Arrays.asList(
                                                                    new VerticalValueDaily(3, null, GeneralDate.fromString("2021/06/01", DATE_FORMAT)),
                                                                    new VerticalValueDaily(3, null, GeneralDate.fromString("2021/06/02", DATE_FORMAT)),
                                                                    new VerticalValueDaily(3, null, GeneralDate.fromString("2021/06/03", DATE_FORMAT))),
                                                            Optional.of(9)
                                                    ),
                                                    new SummaryItemDetail(
                                                            "010102",
                                                            new DisplayInformation("A01010101", "LEVEL3_02"),
                                                            Arrays.asList(
                                                                    new SummaryItemDetail(
                                                                            "010101",
                                                                            new DisplayInformation("A01010101", "LEVEL4_02"),
                                                                            Collections.emptyList(),
                                                                            Arrays.asList(
                                                                                    new VerticalValueDaily(1, null, GeneralDate.fromString("2021/06/01", DATE_FORMAT)),
                                                                                    new VerticalValueDaily(1, null, GeneralDate.fromString("2021/06/02", DATE_FORMAT)),
                                                                                    new VerticalValueDaily(1, null, GeneralDate.fromString("2021/06/03", DATE_FORMAT))),
                                                                            Optional.of(3)
                                                                    ),
                                                                    new SummaryItemDetail(
                                                                            "010102",
                                                                            new DisplayInformation("A01010102", "LEVEL4_02"),
                                                                            Collections.emptyList(),
                                                                            Arrays.asList(
                                                                                    new VerticalValueDaily(2, null, GeneralDate.fromString("2021/06/01", DATE_FORMAT)),
                                                                                    new VerticalValueDaily(2, null, GeneralDate.fromString("2021/06/02", DATE_FORMAT)),
                                                                                    new VerticalValueDaily(2, null, GeneralDate.fromString("2021/06/03", DATE_FORMAT))),
                                                                            Optional.of(6)
                                                                    )
                                                            ),
                                                            Arrays.asList(
                                                                    new VerticalValueDaily(3, null, GeneralDate.fromString("2021/06/01", DATE_FORMAT)),
                                                                    new VerticalValueDaily(3, null, GeneralDate.fromString("2021/06/02", DATE_FORMAT)),
                                                                    new VerticalValueDaily(3, null, GeneralDate.fromString("2021/06/03", DATE_FORMAT))),
                                                            Optional.of(9)
                                                    )
                                            ),
                                            Arrays.asList(
                                                    new VerticalValueDaily(9, null, GeneralDate.fromString("2021/06/01", DATE_FORMAT)),
                                                    new VerticalValueDaily(9, null, GeneralDate.fromString("2021/06/02", DATE_FORMAT)),
                                                    new VerticalValueDaily(9, null, GeneralDate.fromString("2021/06/03", DATE_FORMAT))),
                                            Optional.of(27)
                                    ),
                                    // LEVEL1_02
                                    new SummaryItemDetail(
                                            "0102",
                                            new DisplayInformation("0102", "LEVEL2_02"),
                                            Arrays.asList(
                                                    new SummaryItemDetail(
                                                            "010201",
                                                            new DisplayInformation("010201", "LEVEL3_02"),
                                                            Arrays.asList(
                                                                    new SummaryItemDetail(
                                                                            "010201",
                                                                            new DisplayInformation("010201", "LEVEL4_02"),
                                                                            Collections.emptyList(),
                                                                            Arrays.asList(
                                                                                    new VerticalValueDaily(1, null, GeneralDate.fromString("2021/06/01", DATE_FORMAT)),
                                                                                    new VerticalValueDaily(1, null, GeneralDate.fromString("2021/06/02", DATE_FORMAT)),
                                                                                    new VerticalValueDaily(1, null, GeneralDate.fromString("2021/06/03", DATE_FORMAT))),
                                                                            Optional.of(3)
                                                                    ),
                                                                    new SummaryItemDetail(
                                                                            "010202",
                                                                            new DisplayInformation("010202", "LEVEL4_02"),
                                                                            Collections.emptyList(),
                                                                            Arrays.asList(
                                                                                    new VerticalValueDaily(2, null, GeneralDate.fromString("2021/06/01", DATE_FORMAT)),
                                                                                    new VerticalValueDaily(2, null, GeneralDate.fromString("2021/06/02", DATE_FORMAT)),
                                                                                    new VerticalValueDaily(2, null, GeneralDate.fromString("2021/06/03", DATE_FORMAT))),
                                                                            Optional.of(6)
                                                                    )
                                                            ),
                                                            Arrays.asList(
                                                                    new VerticalValueDaily(3, null, GeneralDate.fromString("2021/06/01", DATE_FORMAT)),
                                                                    new VerticalValueDaily(3, null, GeneralDate.fromString("2021/06/02", DATE_FORMAT)),
                                                                    new VerticalValueDaily(3, null, GeneralDate.fromString("2021/06/03", DATE_FORMAT))),
                                                            Optional.of(9)
                                                    ),
                                                    new SummaryItemDetail(
                                                            "010202",
                                                            new DisplayInformation("010202", "LEVEL3_02"),
                                                            Arrays.asList(
                                                                    new SummaryItemDetail(
                                                                            "01020201",
                                                                            new DisplayInformation("01020201", "LEVEL4_02"),
                                                                            Collections.emptyList(),
                                                                            Arrays.asList(
                                                                                    new VerticalValueDaily(1, null, GeneralDate.fromString("2021/06/01", DATE_FORMAT)),
                                                                                    new VerticalValueDaily(1, null, GeneralDate.fromString("2021/06/02", DATE_FORMAT)),
                                                                                    new VerticalValueDaily(1, null, GeneralDate.fromString("2021/06/03", DATE_FORMAT))),
                                                                            Optional.of(3)
                                                                    ),
                                                                    new SummaryItemDetail(
                                                                            "01020202",
                                                                            new DisplayInformation("01020202", "LEVEL4_02"),
                                                                            Collections.emptyList(),
                                                                            Arrays.asList(
                                                                                    new VerticalValueDaily(2, null, GeneralDate.fromString("2021/06/01", DATE_FORMAT)),
                                                                                    new VerticalValueDaily(2, null, GeneralDate.fromString("2021/06/02", DATE_FORMAT)),
                                                                                    new VerticalValueDaily(2, null, GeneralDate.fromString("2021/06/03", DATE_FORMAT))),
                                                                            Optional.of(6)
                                                                    )
                                                            ),
                                                            Arrays.asList(
                                                                    new VerticalValueDaily(3, null, GeneralDate.fromString("2021/06/01", DATE_FORMAT)),
                                                                    new VerticalValueDaily(3, null, GeneralDate.fromString("2021/06/02", DATE_FORMAT)),
                                                                    new VerticalValueDaily(3, null, GeneralDate.fromString("2021/06/03", DATE_FORMAT))),
                                                            Optional.of(9)
                                                    )
                                            ),
                                            Arrays.asList(
                                                    new VerticalValueDaily(9, null, GeneralDate.fromString("2021/06/01", DATE_FORMAT)),
                                                    new VerticalValueDaily(9, null, GeneralDate.fromString("2021/06/02", DATE_FORMAT)),
                                                    new VerticalValueDaily(9, null, GeneralDate.fromString("2021/06/03", DATE_FORMAT))),
                                            Optional.of(27)
                                    )
                            ),
                            Arrays.asList(
                                    new VerticalValueDaily(18, null, GeneralDate.fromString("2021/06/01", DATE_FORMAT)),
                                    new VerticalValueDaily(18, null, GeneralDate.fromString("2021/06/02", DATE_FORMAT)),
                                    new VerticalValueDaily(18, null, GeneralDate.fromString("2021/06/03", DATE_FORMAT))),
                            Optional.of(54)
                    )
            );

            List<VerticalValueDaily> verticalTotalValues = Arrays.asList(
                    new VerticalValueDaily(36, null, GeneralDate.fromString("2021/06/01", DATE_FORMAT)),
                    new VerticalValueDaily(36, null, GeneralDate.fromString("2021/06/02", DATE_FORMAT)),
                    new VerticalValueDaily(36, null, GeneralDate.fromString("2021/06/03", DATE_FORMAT))
            );
            return new ManHourSummaryTableOutputContent(
                    itemDetails,
                    verticalTotalValues,
                    Optional.of(108)
            );
        }
    }
}
