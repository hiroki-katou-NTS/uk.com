package nts.uk.ctx.at.function.dom.workledgeroutputitem;


import lombok.val;
import mockit.Expectations;
import mockit.Injectable;
import mockit.integration.junit4.JMockit;
import nts.arc.testing.assertion.NtsAssert;
import nts.arc.time.GeneralDate;
import nts.arc.time.YearMonth;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.function.dom.adapter.actualmultiplemonth.MonthlyRecordValueImport;
import nts.uk.ctx.at.function.dom.commonform.AttendanceItemToPrint;
import nts.uk.ctx.at.function.dom.commonform.GetSuitableDateByClosureDateUtility;
import nts.uk.ctx.at.function.dom.dailyworkschedule.OutputItemSettingCode;
import nts.uk.ctx.at.function.dom.dailyworkschedule.OutputItemSettingName;
import nts.uk.ctx.at.function.dom.outputitemsofannualworkledger.DumDataTest;
import nts.uk.ctx.at.function.dom.outputitemsofworkstatustable.DumData;
import nts.uk.ctx.at.function.dom.outputitemsofworkstatustable.dto.EmployeeInfor;
import nts.uk.ctx.at.function.dom.outputitemsofworkstatustable.dto.StatusOfEmployee;
import nts.uk.ctx.at.function.dom.outputitemsofworkstatustable.dto.WorkPlaceInfo;
import nts.uk.ctx.at.function.dom.outputitemsofworkstatustable.enums.SettingClassificationCommon;
import nts.uk.ctx.at.shared.dom.adapter.employment.BsEmploymentHistoryImport;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.converter.util.item.ItemValue;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.converter.util.item.ValueType;
import nts.uk.ctx.at.shared.dom.scherec.dailyattendanceitem.adapter.attendanceitemname.AttItemName;
import nts.uk.shr.com.context.AppContexts;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.*;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.groups.Tuple.tuple;

@RunWith(JMockit.class)
public class CreateWorkLedgerDisplayContentDomainServiceTest {
    @Injectable
    CreateWorkLedgerDisplayContentDomainService.Require require;

    private final DatePeriod datePeriod = new DatePeriod(GeneralDate.today(), GeneralDate.today().addDays(1));
    private static final List<EmployeeInfor> employeeInfors = DumData.employeeInfors;
    private static final List<EmployeeInfor> employeeInforFail = DumData.employeeInforFail;
    private static final List<WorkPlaceInfo> workPlaceInfo = DumData.workPlaceInfo;
    private static final List<WorkPlaceInfo> workPlaceInfoFail = DumData.workPlaceInfoFail;
    private final OutputItemSettingCode code = new OutputItemSettingCode("ABC");
    private final OutputItemSettingName name = new OutputItemSettingName("CBA");
    private final String iD = "iD";
    private final String empId = "employeeId";
    private final SettingClassificationCommon settingCategoryStandard = SettingClassificationCommon.STANDARD_SELECTION;
    private final WorkLedgerOutputItem domainsStandard = WorkLedgerOutputItem.create(iD, empId, code, name, settingCategoryStandard);


    /**
     * TEST CASE :Throw exception
     * - List ouputItem = empty
     */
    @Test
    public void test_01() {
        val listSid = employeeInfors.stream().map(EmployeeInfor::getEmployeeId).collect(Collectors.toList());
        val listEmployeeStatus = Arrays.asList(
                new StatusOfEmployee("eplId05",
                        Arrays.asList(
                                new DatePeriod(GeneralDate.today(), GeneralDate.today().addDays(1))
                        )));
        new Expectations(AppContexts.class) {{
            AppContexts.user().companyId();
            result = "cid";
        }};

        new Expectations() {
            {
                require.getAffiliateEmpListDuringPeriod(datePeriod, listSid);
                result = listEmployeeStatus;

            }
        };

        NtsAssert.businessException("Msg_1926", () -> {
            CreateWorkLedgerDisplayContentDomainService.createWorkLedgerDisplayContent(require, datePeriod, employeeInfors,
                    domainsStandard, workPlaceInfo);
        });
    }
    /**
     * TEST CASE :Throw exception
     * - List Monthly AttendanceItem : size = 0
     */
    @Test
    public void test_02() {
        val listSid = employeeInfors.stream().map(EmployeeInfor::getEmployeeId).collect(Collectors.toList());
        new Expectations(AppContexts.class) {{
            AppContexts.user().companyId();
            result = "cid";
        }};
        val outPutItems = Arrays.asList(new AttendanceItemToPrint(1, 1)
                , new AttendanceItemToPrint(1, 2)
                , new AttendanceItemToPrint(2, 2)
                , new AttendanceItemToPrint(3, 2)
                , new AttendanceItemToPrint(4, 2)
                , new AttendanceItemToPrint(5, 2)
                , new AttendanceItemToPrint(6, 2)

        );
        val listEmployeeStatus = Arrays.asList(
                new StatusOfEmployee("eplId05",
                        Arrays.asList(
                                new DatePeriod(GeneralDate.today(), GeneralDate.today().addDays(1))
                        )));
        val domain = new WorkLedgerOutputItem(iD, code, outPutItems, name, settingCategoryStandard, empId);

        new Expectations() {
            {
                require.getAffiliateEmpListDuringPeriod(datePeriod, listSid);
                result = listEmployeeStatus;

                require.getAggregableMonthlyAttId("cid");
                result = Arrays.asList(1, 2, 3, 4, 5);

            }
        };

        NtsAssert.businessException("Msg_1926", () -> {
            CreateWorkLedgerDisplayContentDomainService.createWorkLedgerDisplayContent(require, datePeriod, employeeInfors,
                    domain, workPlaceInfo);
        });
    }
    /**
     * TEST CASE :Throw exception
     * - Fail  employee info
     */
    @Test
    public void test_03() {
        val listSid = employeeInfors.stream().map(EmployeeInfor::getEmployeeId).collect(Collectors.toList());
        val outPutItems = Arrays.asList(new AttendanceItemToPrint(1, 1)
                , new AttendanceItemToPrint(1, 2)
                , new AttendanceItemToPrint(2, 2)
                , new AttendanceItemToPrint(3, 2)
                , new AttendanceItemToPrint(4, 2)
                , new AttendanceItemToPrint(5, 2)
                , new AttendanceItemToPrint(6, 2)

        );
        new Expectations(AppContexts.class) {{
            AppContexts.user().companyId();
            result = "cid";
        }};

        val listAttIds = outPutItems.parallelStream().map(AttendanceItemToPrint::getAttendanceId)
                .distinct().collect(Collectors.toCollection(ArrayList::new));
        val listEmployeeStatus = Arrays.asList(
                new StatusOfEmployee("eplId05",
                        Arrays.asList(
                                new DatePeriod(GeneralDate.today(), GeneralDate.today().addDays(1))
                        )));

        val domain = new WorkLedgerOutputItem(iD, code, outPutItems, name, settingCategoryStandard, empId);
        val listAggregable = Arrays.asList(1, 2, 3, 4, 5);
        val attName = new AttItemName();
        attName.setAttendanceItemDisplayNumber(1);
        attName.setAttendanceItemId(1);
        attName.setAttendanceItemName("attendanceItemName");
        attName.setNameLineFeedPosition(1);
        new Expectations() {
            {
                require.getAffiliateEmpListDuringPeriod(datePeriod, listSid);
                result = listEmployeeStatus;

                require.getAggregableMonthlyAttId("cid");
                result = listAggregable;

                require.getMonthlyItems("cid", null, listAttIds, null);
                result = Arrays.asList(attName);
            }
        };

        NtsAssert.businessException("Msg_1926", () -> {
            CreateWorkLedgerDisplayContentDomainService.createWorkLedgerDisplayContent(require, datePeriod, employeeInfors,
                    domain, workPlaceInfo);
        });
    }
    /**
     * TEST CASE :Throw exception
     * - RQ 588 : return empty;
     */
    @Test
    public void test_04() {
        val listSid = employeeInfors.stream().map(EmployeeInfor::getEmployeeId).collect(Collectors.toList());
        new Expectations(AppContexts.class) {{
            AppContexts.user().companyId();
            result = "cid";
        }};
        val outPutItems = Arrays.asList(new AttendanceItemToPrint(1, 1)
                , new AttendanceItemToPrint(1, 2)
                , new AttendanceItemToPrint(2, 2)
                , new AttendanceItemToPrint(3, 2)
                , new AttendanceItemToPrint(4, 2)
                , new AttendanceItemToPrint(5, 2)
                , new AttendanceItemToPrint(6, 2)

        );

        val listAttIds = outPutItems.parallelStream().map(AttendanceItemToPrint::getAttendanceId)
                .distinct().collect(Collectors.toCollection(ArrayList::new));
        val domain = new WorkLedgerOutputItem(iD, code, outPutItems, name, settingCategoryStandard, empId);
        val listAggregable = Arrays.asList(1, 2, 3, 4, 5);
        val attName = new AttItemName();
        attName.setAttendanceItemDisplayNumber(1);
        attName.setAttendanceItemId(1);
        attName.setAttendanceItemName("attendanceItemName");
        attName.setNameLineFeedPosition(1);
        new Expectations() {
            {
                require.getAffiliateEmpListDuringPeriod(datePeriod, listSid);
                result = Collections.emptyList();

                require.getAggregableMonthlyAttId("cid");
                result = listAggregable;

                require.getMonthlyItems("cid", null, listAttIds, null);
                result = Arrays.asList(attName);
            }
        };

        NtsAssert.businessException("Msg_1926", () -> {
            CreateWorkLedgerDisplayContentDomainService.createWorkLedgerDisplayContent(require, datePeriod, employeeInfors,
                    domain, workPlaceInfo);
        });
    }
    /**
     * TEST CASE :Throw exception
     * - Fail  employee info
     */
    @Test
    public void test_05() {
        val listSid = employeeInforFail.stream().map(EmployeeInfor::getEmployeeId).collect(Collectors.toList());
        new Expectations(AppContexts.class) {{
            AppContexts.user().companyId();
            result = "cid";
        }};
        val outPutItems = Arrays.asList(new AttendanceItemToPrint(1, 1)
                , new AttendanceItemToPrint(1, 2)
                , new AttendanceItemToPrint(2, 2)
                , new AttendanceItemToPrint(3, 2)
                , new AttendanceItemToPrint(4, 2)
                , new AttendanceItemToPrint(5, 2)
                , new AttendanceItemToPrint(6, 2)

        );

        val listAttIds = outPutItems.parallelStream().map(AttendanceItemToPrint::getAttendanceId)
                .distinct().collect(Collectors.toCollection(ArrayList::new));
        val listEmployeeStatus = Arrays.asList(
                new StatusOfEmployee("eplId05",
                        Arrays.asList(
                                new DatePeriod(GeneralDate.today(), GeneralDate.today().addDays(1))
                        )));

        val domain = new WorkLedgerOutputItem(iD, code, outPutItems, name, settingCategoryStandard, empId);
        val listAggregable = Arrays.asList(1, 2, 3, 4, 5);
        val attName = new AttItemName();
        attName.setAttendanceItemDisplayNumber(1);
        attName.setAttendanceItemId(1);
        attName.setAttendanceItemName("attendanceItemName");
        attName.setNameLineFeedPosition(1);
        new Expectations() {
            {
                require.getAffiliateEmpListDuringPeriod(datePeriod, listSid);
                result = listEmployeeStatus;

                require.getAggregableMonthlyAttId("cid");
                result = listAggregable;

                require.getMonthlyItems("cid", null, listAttIds, null);
                result = Arrays.asList(attName);
            }
        };

        NtsAssert.businessException("Msg_1926", () -> {
            CreateWorkLedgerDisplayContentDomainService.createWorkLedgerDisplayContent(require, datePeriod, employeeInforFail,
                    domain, workPlaceInfo);
        });
    }
    /**
     * TEST CASE :Throw exception
     * - Fail  workplace info
     */
    @Test
    public void test_06() {
        val listSid = employeeInfors.stream().map(EmployeeInfor::getEmployeeId).collect(Collectors.toList());
        new Expectations(AppContexts.class) {{
            AppContexts.user().companyId();
            result = "cid";
        }};
        val outPutItems = Arrays.asList(new AttendanceItemToPrint(1, 1)
                , new AttendanceItemToPrint(1, 2)
                , new AttendanceItemToPrint(2, 2)
                , new AttendanceItemToPrint(3, 2)
                , new AttendanceItemToPrint(4, 2)
                , new AttendanceItemToPrint(5, 2)
                , new AttendanceItemToPrint(6, 2)

        );

        val listAttIds = outPutItems.parallelStream().map(AttendanceItemToPrint::getAttendanceId)
                .distinct().collect(Collectors.toCollection(ArrayList::new));
        val listEmployeeStatus = Arrays.asList(
                new StatusOfEmployee("eplId01",
                        Arrays.asList(
                                new DatePeriod(GeneralDate.today(), GeneralDate.today().addDays(1))
                        )));

        val domain = new WorkLedgerOutputItem(iD, code, outPutItems, name, settingCategoryStandard, empId);
        val listAggregable = Arrays.asList(1, 2, 3, 4, 5);
        val attName = new AttItemName();
        attName.setAttendanceItemDisplayNumber(1);
        attName.setAttendanceItemId(1);
        attName.setAttendanceItemName("attendanceItemName");
        attName.setNameLineFeedPosition(1);

        new Expectations() {
            {
                require.getAffiliateEmpListDuringPeriod(datePeriod, listSid);
                result = listEmployeeStatus;

                require.getAggregableMonthlyAttId("cid");
                result = listAggregable;

                require.getMonthlyItems("cid", null, listAttIds, null);
                result = Arrays.asList(attName );
            }
        };

        NtsAssert.businessException("Msg_1926", () -> {
            CreateWorkLedgerDisplayContentDomainService.createWorkLedgerDisplayContent(require, datePeriod, employeeInfors,
                    domain, workPlaceInfoFail);
        });
    }
    /**
     */
    @Test
    public void test_07() {
        val listSid = employeeInfors.stream().map(EmployeeInfor::getEmployeeId).collect(Collectors.toList());
        new Expectations(AppContexts.class) {{
            AppContexts.user().companyId();
            result = "cid";
        }};
        val outPutItems = Arrays.asList(
                new AttendanceItemToPrint(1, 1)
                , new AttendanceItemToPrint(2, 2)
                , new AttendanceItemToPrint(3, 3)
                , new AttendanceItemToPrint(4, 4)
                , new AttendanceItemToPrint(5, 5)
                , new AttendanceItemToPrint(6, 6)

        );

        val listAttIds = outPutItems.parallelStream().map(AttendanceItemToPrint::getAttendanceId)
                .distinct().collect(Collectors.toCollection(ArrayList::new));
        val listEmployeeStatus = Arrays.asList(
                new StatusOfEmployee("eplId01",
                        Arrays.asList(
                                new DatePeriod(GeneralDate.today(), GeneralDate.today().addDays(1))
                        )));

        val baseDate = datePeriod.end();
        val closure = DumDataTest.createClosure();
        val domain = new WorkLedgerOutputItem(iD, code, outPutItems, name, settingCategoryStandard, empId);
        val listAggregable = Arrays.asList(1, 2, 3, 4, 5);
        val attName = new AttItemName();
        attName.setAttendanceItemDisplayNumber(1);
        attName.setAttendanceItemId(1);
        attName.setAttendanceItemName("attendanceItemName");
        attName.setNameLineFeedPosition(1);
        attName.setTypeOfAttendanceItem(1);
        val mapEmploymentInfor = new HashMap<String, BsEmploymentHistoryImport>();
        mapEmploymentInfor.put("eplId01", new BsEmploymentHistoryImport("eplId01", "code",
                "name", new DatePeriod(GeneralDate.today(), GeneralDate.today().addDays(2))));
        mapEmploymentInfor.put("eplId02", new BsEmploymentHistoryImport());
        val ymPeriod = GetSuitableDateByClosureDateUtility.convertPeriod(datePeriod, 1);
        Map<String, List<MonthlyRecordValueImport>> listMap = new HashMap<>();
        val listValueM = Arrays.asList(
                MonthlyRecordValueImport.of(
                        new YearMonth(202012),
                        Arrays.asList(new ItemValue(
                                        20,
                                        ValueType.TIME,
                                        "123",
                                        1
                                ),
                                new ItemValue(
                                        "ABCFD",
                                        ValueType.DAYS,
                                        "123",
                                        5
                                ))
                )
        );
        listMap.put("eplId01", listValueM);
        new Expectations() {
            {
                require.getAffiliateEmpListDuringPeriod(datePeriod, listSid);
                result = listEmployeeStatus;

                require.getAggregableMonthlyAttId("cid");
                result = listAggregable;

                require.getMonthlyItems("cid", null, listAttIds, null);
                result = Arrays.asList(attName);

                require.getEmploymentInfor("cid", listSid, baseDate);
                result = mapEmploymentInfor;

                require.getClosureDataByEmployee("eplId01", baseDate);
                result = closure;

                require.getActualMultipleMonth(Collections.singletonList("eplId01"), ymPeriod, listAttIds);
                result = listMap;

            }
        };
        val actual = CreateWorkLedgerDisplayContentDomainService.createWorkLedgerDisplayContent(require, datePeriod, employeeInfors,
                domain, workPlaceInfo);
        val expected = DumDataLedger.expected;

        assertThat(actual)
                .extracting(
                        WorkLedgerDisplayContent::getEmployeeName,
                        WorkLedgerDisplayContent::getEmployeeCode,
                        WorkLedgerDisplayContent::getWorkplaceCode,
                        WorkLedgerDisplayContent::getWorkplaceName)
                .containsExactly(
                        tuple(expected.get(0).getEmployeeName(),
                                expected.get(0).getEmployeeCode(),
                                expected.get(0).getWorkplaceCode(),
                                expected.get(0).getWorkplaceName()));

        assertThat(actual.get(0).getMonthlyDataList())
                .extracting(
                        d -> d.getValueList().size())
                .containsExactly(
                        expected.get(0).getMonthlyDataList().get(0).getValueList().size()
                );

        assertThat(actual.get(0).getMonthlyDataList())
                .extracting(
                        MonthlyOutputLine::getAttendanceItemName,
                        MonthlyOutputLine::getAttribute,
                        MonthlyOutputLine::getPrintOrder,
                        MonthlyOutputLine::getTotal)
                .containsExactly(
                        tuple(expected.get(0).getMonthlyDataList().get(0).getAttendanceItemName(),
                                expected.get(0).getMonthlyDataList().get(0).getAttribute(),
                                expected.get(0).getMonthlyDataList().get(0).getPrintOrder(),
                                expected.get(0).getMonthlyDataList().get(0).getTotal())
                );

        assertThat(actual.get(0).getMonthlyDataList().get(0).getValueList())
                .extracting(
                        MonthlyValue::getActualValue,
                        MonthlyValue::getAttId,
                        MonthlyValue::getCharacterValue,
                        MonthlyValue::getDate
                )
                .containsExactly(
                        tuple(expected.get(0).getMonthlyDataList().get(0).getValueList().get(0).getActualValue(),
                                expected.get(0).getMonthlyDataList().get(0).getValueList().get(0).getAttId(),
                                expected.get(0).getMonthlyDataList().get(0).getValueList().get(0).getCharacterValue(),
                                expected.get(0).getMonthlyDataList().get(0).getValueList().get(0).getDate()
                        )
                );
    }

}
