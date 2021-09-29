package nts.uk.ctx.at.shared.dom.alarmList.persistenceextractresult;

import nts.arc.time.GeneralDate;
import nts.arc.time.GeneralDateTime;
import nts.uk.ctx.at.shared.dom.alarmList.AlarmCategory;
import nts.uk.ctx.at.shared.dom.alarmList.AlarmPatternCode;
import nts.uk.ctx.at.shared.dom.alarmList.AlarmPatternName;
import nts.uk.ctx.at.shared.dom.alarmList.extractionResult.AlarmListCheckType;
import nts.uk.ctx.at.shared.dom.alarmList.extractionResult.ExtractionAlarmPeriodDate;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class DumData {
    public static final List<AlarmEmployeeList> alarmListExtractResults =
            Arrays.asList(
                    new AlarmEmployeeList(
                            Arrays.asList(
                                    new AlarmExtractInfoResult(
                                            "ConditionNo1",
                                            new AlarmCheckConditionCode("C01"),
                                            AlarmCategory.DAILY,
                                            AlarmListCheckType.FixCheck,
                                            Arrays.asList(
                                                    new ExtractResultDetail(
                                                            new ExtractionAlarmPeriodDate(Optional.of(GeneralDate.today()), Optional.of(GeneralDate.today().addDays(2))),
                                                            "alarmName1",
                                                            "alarmContent1",
                                                            GeneralDateTime.now(),
                                                            Optional.of("S001"),
                                                            Optional.of("comment1"),
                                                            Optional.of("checkValue1")
                                                    ),
                                                    new ExtractResultDetail(
                                                            new ExtractionAlarmPeriodDate(Optional.of(GeneralDate.today()), Optional.of(GeneralDate.today().addDays(1))),
                                                            "alarmName2",
                                                            "alarmContent2",
                                                            GeneralDateTime.now(),
                                                            Optional.of("S002"),
                                                            Optional.of("comment2"),
                                                            Optional.of("checkValue2")
                                                    )
                                            )
                                    ),
                                    new AlarmExtractInfoResult(
                                            "ConditionNo2",
                                            new AlarmCheckConditionCode("C02"),
                                            AlarmCategory.SCHEDULE_DAILY,
                                            AlarmListCheckType.FreeCheck,
                                            Arrays.asList(
                                                    new ExtractResultDetail(
                                                            new ExtractionAlarmPeriodDate(Optional.of(GeneralDate.today()), Optional.of(GeneralDate.today().addDays(2))),
                                                            "alarmName3",
                                                            "alarmContent3",
                                                            GeneralDateTime.now(),
                                                            Optional.of("S003"),
                                                            Optional.of("comment"),
                                                            Optional.of("checkValue3")
                                                    ),
                                                    new ExtractResultDetail(
                                                            new ExtractionAlarmPeriodDate(Optional.of(GeneralDate.today()), Optional.of(GeneralDate.today().addDays(1))),
                                                            "alarmName4",
                                                            "alarmContent4",
                                                            GeneralDateTime.now(),
                                                            Optional.of("S004"),
                                                            Optional.of("comment4"),
                                                            Optional.of("checkValue4")
                                                    )
                                            )
                                    )
                            ),
                            "00001"
                    ),
                    new AlarmEmployeeList(
                            Arrays.asList(
                                    new AlarmExtractInfoResult(
                                            "ConditionNo1",
                                            new AlarmCheckConditionCode("C03"),
                                            AlarmCategory.DAILY,
                                            AlarmListCheckType.FixCheck,
                                            Arrays.asList(
                                                    new ExtractResultDetail(
                                                            new ExtractionAlarmPeriodDate(Optional.of(GeneralDate.today()), Optional.of(GeneralDate.today().addDays(2))),
                                                            "alarmName1",
                                                            "alarmContent1",
                                                            GeneralDateTime.now(),
                                                            Optional.of("workplaceId1"),
                                                            Optional.of("comment1"),
                                                            Optional.of("checkValue1")
                                                    ),
                                                    new ExtractResultDetail(
                                                            new ExtractionAlarmPeriodDate(Optional.of(GeneralDate.today()), Optional.of(GeneralDate.today().addDays(1))),
                                                            "alarmName2",
                                                            "alarmContent2",
                                                            GeneralDateTime.now(),
                                                            Optional.of("workplaceId2"),
                                                            Optional.of("comment2"),
                                                            Optional.of("checkValue2")
                                                    )
                                            )
                                    ),
                                    new AlarmExtractInfoResult(
                                            "ConditionNo2",
                                            new AlarmCheckConditionCode("C02"),
                                            AlarmCategory.SCHEDULE_DAILY,
                                            AlarmListCheckType.FreeCheck,
                                            Arrays.asList(
                                                    new ExtractResultDetail(
                                                            new ExtractionAlarmPeriodDate(Optional.of(GeneralDate.today()), Optional.of(GeneralDate.today().addDays(1))),
                                                            "alarmName3",
                                                            "alarmContent3",
                                                            GeneralDateTime.now(),
                                                            Optional.of("workplaceId3"),
                                                            Optional.of("comment"),
                                                            Optional.of("checkValue3")
                                                    ),
                                                    new ExtractResultDetail(
                                                            new ExtractionAlarmPeriodDate(Optional.of(GeneralDate.today()), Optional.of(GeneralDate.today().addDays(2))),
                                                            "alarmName4",
                                                            "alarmContent4",
                                                            GeneralDateTime.now(),
                                                            Optional.of("workplaceId4"),
                                                            Optional.of("comment4"),
                                                            Optional.of("checkValue4")
                                                    )
                                            )
                                    )
                            ),
                            "00002"
                    )
            );

    public static final PersistenceAlarmListExtractResult dumDomain =
            new PersistenceAlarmListExtractResult(
                    new AlarmPatternCode("01"),
                    new AlarmPatternName("日々の勤怠チェック"),
                    alarmListExtractResults,
                    "000000000001",
                    "Z"
            );
}