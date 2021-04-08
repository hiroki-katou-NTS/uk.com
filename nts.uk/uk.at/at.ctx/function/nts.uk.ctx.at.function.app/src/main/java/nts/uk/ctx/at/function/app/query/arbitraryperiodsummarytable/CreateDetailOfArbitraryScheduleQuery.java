package nts.uk.ctx.at.function.app.query.arbitraryperiodsummarytable;


import lombok.val;
import nts.arc.error.BusinessException;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.function.dom.adapter.outputitemsofworkstatustable.AffComHistAdapter;
import nts.uk.ctx.at.function.dom.arbitraryperiodsummarytable.OutputSettingOfArbitrary;
import nts.uk.ctx.at.function.dom.commonform.AttendanceItemToPrint;
import nts.uk.ctx.at.function.dom.outputitemsofworkstatustable.dto.EmployeeInfor;
import nts.uk.ctx.at.function.dom.outputitemsofworkstatustable.dto.StatusOfEmployee;
import nts.uk.ctx.at.shared.dom.adapter.workplace.config.info.WorkplaceInfor;
import nts.uk.ctx.at.shared.dom.monthlyattditem.MonthlyAttendanceItem;
import nts.uk.ctx.at.shared.dom.monthlyattditem.MonthlyAttendanceItemRepository;
import nts.uk.ctx.at.shared.dom.monthlyattditem.aggregate.MonthlyAttItemCanAggregateRepository;
import nts.uk.ctx.at.shared.dom.scherec.byperiod.AttendanceTimeOfAnyPeriod;
import nts.uk.ctx.at.shared.dom.scherec.dailyattendanceitem.adapter.attendanceitemname.AttItemName;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattendanceitem.service.CompanyMonthlyItemService;
import nts.uk.shr.com.context.AppContexts;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * 任意期間集計表の表示内容を作成する
 */
@Stateless
public class CreateDetailOfArbitraryScheduleQuery {

    @Inject
    private AffComHistAdapter affComHistAdapter;
    @Inject
    private MonthlyAttItemCanAggregateRepository monthlyAttItemCanAggregateRepo;
    @Inject
    private CompanyMonthlyItemService companyMonthlyItemService;
    @Inject
    private MonthlyAttendanceItemRepository monthlyAttendanceItemRepository;

    public DetailOfArbitrarySchedule getContentOfArbitrarySchedule(DatePeriod period,
                                               String aggrFrameCode,
                                               List<EmployeeInfor> employeeBasicInfoImportList,
                                               List<WorkplaceInfor> workplaceInforList,
                                               OutputSettingOfArbitrary ofArbitrary,
                                               boolean isDetail,
                                               boolean isWorkplaceTotal,
                                               boolean isTotal,
                                               boolean isCumulativeWorkplace,
                                               List<Integer> workplacePrintTargetList) {
        val cid = AppContexts.user().companyId();

        val listWplDistinct = workplaceInforList.stream().filter(distinctByKey(WorkplaceInfor::getWorkplaceId))
                .collect(Collectors.toList());
        Map<String, WorkplaceInfor> mapWorkplaceInfor =
                listWplDistinct.stream()
                        .collect(Collectors.toMap(WorkplaceInfor::getWorkplaceId, i -> i));

        Map<String, EmployeeInfor> mapEmployeeInfor =
                employeeBasicInfoImportList.stream().filter(distinctByKey(EmployeeInfor::getEmployeeId))
                        .collect(Collectors.toMap(EmployeeInfor::getEmployeeId, i -> i));

        List<String> lisSids = new ArrayList<>(mapEmployeeInfor.keySet());
        //1.  ⓪: <call> 社員の指定期間中の所属期間を取得する

        List<StatusOfEmployee> employees = affComHistAdapter.getListAffComHist(lisSids, period);
        //2.  ①: <call> 任意期間別実績を取得する
        List<AttendanceTimeOfAnyPeriod> listActualAttendances = new ArrayList<>(); // TODO QA: 40272

        //3. [①.isEmpty()]
        if (ofArbitrary == null || listActualAttendances.isEmpty()) {
            throw new BusinessException("Msg_1894");
        }
        // SUM THEO SID, ATTID
        Map<String, List<DisplayContent>> mapEplIdAndDisplayContent = new HashMap<>();
        // SUM THEO WPLID, ATTID
        Map<String, List<DisplayContent>> mapWplIdAndDisplayContent = new HashMap<>();

        //4.  ② 集計可能勤怠項目ID
        List<Integer> getAggregableMonthlyAttId = monthlyAttItemCanAggregateRepo.getMonthlyAtdItemCanAggregate(cid)
                .stream()
                .map(t -> t.v().intValue())
                .collect(Collectors.toList());
        //5.  ③: <call> 月次の勤怠項目の名称を取得する
        //会社ID = ログイン会社ID
        //ロールID = ログイン社員の就業ロールID
        //印刷する勤怠項目．勤怠項目ID>
        val roleId = Optional.ofNullable(AppContexts.user().roles().forAttendance());
        val outputItemList = ofArbitrary.getOutputItemList();
        val listAttId = outputItemList.stream().map(AttendanceItemToPrint::getAttendanceId).collect(Collectors.toList());

        val nameAttendanceItems = companyMonthlyItemService.getMonthlyItems(cid, roleId, listAttId, null);
        //6. ④: <call> 月次の勤怠項目を取得する
        List<MonthlyAttendanceItem> monthlyAttendanceItemList = this.monthlyAttendanceItemRepository.findByAttendanceItemId(cid, listAttId);


        Map<Integer, Integer> mapAttendanceItemToPrint =
                ofArbitrary.getOutputItemList().stream().filter(distinctByKey(AttendanceItemToPrint::getAttendanceId))
                        .collect(Collectors.toMap(AttendanceItemToPrint::getAttendanceId, AttendanceItemToPrint::getRanking));
        //7.
        List<AttendanceItemDisplayContents> contentsList = new ArrayList<>();
        monthlyAttendanceItemList.forEach(
                e -> {
                    Optional<AttItemName> attendance = nameAttendanceItems.stream()
                            .filter(attd -> attd.getAttendanceItemId() == e.getAttendanceItemId())
                            .findFirst();
                    attendance.ifPresent(attItemName -> contentsList.add(new AttendanceItemDisplayContents(
                            e.getPrimitiveValue(),
                            e.getAttendanceItemId(),
                            attItemName.getAttendanceItemName(),
                            e.getMonthlyAttendanceAtr(),
                            mapAttendanceItemToPrint.getOrDefault(e.getAttendanceItemId(), null)
                    )));
                });

        List<AttendanceDetailDisplayContents> detailDisplayContents = new ArrayList<>();

        List<WorkplaceTotalDisplayContent> totalDisplayContents = new ArrayList<>();

        List<DisplayContent> totalAll = new ArrayList<>();

        List<CumulativeWorkplaceDisplayContent> cumulativeWorkplaceDisplayContents = new ArrayList<>();


        if (isDetail || isWorkplaceTotal) {

            listWplDistinct.forEach(e -> {
                // 7.1 [4] == TRUE
                if (isDetail) {
                    val epls = employeeBasicInfoImportList.stream()
                            .filter(j -> j.getWorkPlaceId().equals(e.getWorkplaceId())).collect(Collectors.toList());
                    detailDisplayContents.add(new AttendanceDetailDisplayContents(
                            e.getWorkplaceId(),
                            e.getWorkplaceCode(),
                            e.getWorkplaceName(),
                            e.getHierarchyCode(),
                            epls.stream().map(i -> new DisplayedEmployee(
                                    getListContent(mapEplIdAndDisplayContent.getOrDefault(i, Collections.emptyList()),
                                            outputItemList, getAggregableMonthlyAttId),
                                    i.getEmployeeId(),
                                    i.getEmployeeCode(),
                                    i.getEmployeeName()
                            )).collect(Collectors.toList())
                    ));
                }
                // 7.2 [5] == TRUE
                if (isWorkplaceTotal) {
                    totalDisplayContents.add(new WorkplaceTotalDisplayContent(
                                    e.getWorkplaceId(),
                                    e.getWorkplaceCode(),
                                    e.getWorkplaceName(),
                                    e.getHierarchyCode(),
                                    mapWplIdAndDisplayContent.getOrDefault(e.getWorkplaceId(), Collections.emptyList())
                            )
                    );
                }
            });
            //7.3 「６」 == TRUE
            if (isTotal) {
                // SUM THEO ATTID
            }
            // 7.4 「７」 == TRUE
            if (isCumulativeWorkplace) {
                workplacePrintTargetList.forEach(i -> {
                            val item = totalDisplayContents.stream().filter(e -> e.getHierarchyCode()
                                    .equals(i)).findFirst();

                            val subItem = totalDisplayContents.stream()
                                    .filter(e -> (Integer.parseInt(e.getHierarchyCode())) <= i)

                                    .collect(Collectors.toList());
                            List<DisplayContent> listOfWorkplaces = new ArrayList<>();
                            subItem.forEach(
                                    k -> listOfWorkplaces.addAll(k.getListOfWorkplaces())
                            );
                            if (item.isPresent()) {
                                val m = item.get();
                                cumulativeWorkplaceDisplayContents.add(new CumulativeWorkplaceDisplayContent(
                                        m.getWorkplaceId(),
                                        m.getWorkplaceCode(),
                                        m.getWorkplaceName(),
                                        m.getHierarchyCode(),
                                        outputItemList.stream().map(e ->
                                                new DisplayContent(
                                                        listOfWorkplaces.stream()
                                                                .filter(k -> k.getAttendanceItemId() == e.getAttendanceId())
                                                                .mapToDouble(DisplayContent::getValue).sum(),
                                                        e.getAttendanceId(),
                                                        e.getRanking()
                                                )
                                        ).collect(Collectors.toList()),
                                        Integer.parseInt(m.getHierarchyCode())
                                ));
                            }

                        }

                );
            }

        }

        return new DetailOfArbitrarySchedule(
                contentsList,
                detailDisplayContents,
                totalDisplayContents,
                totalAll,
                cumulativeWorkplaceDisplayContents
        );

    }

    private List<DisplayContent> getListContent(List<DisplayContent> contentList, List<AttendanceItemToPrint> outputItemList,
                                                List<Integer> getAggregableMonthlyAttId) {
        val rs = new ArrayList<DisplayContent>();
        outputItemList.forEach(
                e -> {
                    val item0pt = contentList.stream().filter(j -> j.getAttendanceItemId() == e.getAttendanceId()).findFirst();
                    if (item0pt.isPresent()) {
                        val item = item0pt.get();
                        if (!checkAttId(getAggregableMonthlyAttId, item.getAttendanceItemId())) {
                            item.setValue(null);
                            rs.add(item);
                        }
                    } else {
                        rs.add(new DisplayContent(
                                null,
                                e.getAttendanceId(),
                                e.getRanking()
                        ));
                    }
                }
        );
        return rs;

    }

    private static boolean checkAttId(List<Integer> attIds, int attId) {
        return attIds.stream().anyMatch(e -> e == attId);
    }

    public static <T> Predicate<T> distinctByKey(Function<? super T, ?> keyExtractor) {
        Map<Object, Boolean> seen = new ConcurrentHashMap<>();
        return t -> seen.putIfAbsent(keyExtractor.apply(t), Boolean.TRUE) == null;
    }
}
