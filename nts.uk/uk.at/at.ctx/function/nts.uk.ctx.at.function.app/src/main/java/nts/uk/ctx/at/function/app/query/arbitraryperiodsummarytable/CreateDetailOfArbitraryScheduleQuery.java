package nts.uk.ctx.at.function.app.query.arbitraryperiodsummarytable;


import lombok.val;
import nts.arc.error.BusinessException;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.function.dom.adapter.outputitemsofworkstatustable.AffComHistAdapter;
import nts.uk.ctx.at.function.dom.arbitraryperiodsummarytable.OutputSettingOfArbitrary;
import nts.uk.ctx.at.function.dom.commonform.AttendanceItemToPrint;
import nts.uk.ctx.at.function.dom.outputitemsofworkstatustable.dto.StatusOfEmployee;
import nts.uk.ctx.at.shared.dom.adapter.employee.EmployeeBasicInfoImport;
import nts.uk.ctx.at.shared.dom.adapter.workplace.config.info.WorkplaceInfor;
import nts.uk.ctx.at.shared.dom.monthlyattditem.MonthlyAttendanceItem;
import nts.uk.ctx.at.shared.dom.monthlyattditem.MonthlyAttendanceItemRepository;
import nts.uk.ctx.at.shared.dom.monthlyattditem.aggregate.MonthlyAttItemCanAggregateRepository;
import nts.uk.ctx.at.shared.dom.scherec.dailyattendanceitem.adapter.attendanceitemname.AttItemName;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattendanceitem.service.CompanyMonthlyItemService;
import nts.uk.shr.com.context.AppContexts;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
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

    public Object getDetail(DatePeriod period,
                            String aggrFrameCode,
                            List<EmployeeBasicInfoImport> employeeBasicInfoImportList,
                            List<WorkplaceInfor> workplaceInforList,
                            OutputSettingOfArbitrary ofArbitrary,
                            boolean isDetail,
                            boolean isWorkplaceTotal,
                            boolean isTotal,
                            boolean isCumulativeWorkplace,
                            List<Integer> workplacePrintTargetList) {
        val cid = AppContexts.user().companyId();
        List<String> lisSids = employeeBasicInfoImportList.stream().map(EmployeeBasicInfoImport::getSid).collect(Collectors.toList());
        //1.  ⓪: <call> 社員の指定期間中の所属期間を取得する
        List<StatusOfEmployee> employees = affComHistAdapter.getListAffComHist(lisSids, period);
        //2.  ①: <call> 任意期間別実績を取得する
        val listActualAttendances = new ArrayList<>(); // TODO QA: 40272
        //3. [①.isEmpty()]
        if (ofArbitrary == null || listActualAttendances.isEmpty()) {
            throw new BusinessException("Msg_1894");
        }
        //4.  ② 集計可能勤怠項目ID
        List<Integer> getAggregableMonthlyAttId = monthlyAttItemCanAggregateRepo.getMonthlyAtdItemCanAggregate(cid).stream()
                .map(t -> t.v().intValue())
                .collect(Collectors.toList());
        //5.  ③: <call> 月次の勤怠項目の名称を取得する
        //会社ID = ログイン会社ID
        //ロールID = ログイン社員の就業ロールID
        //印刷する勤怠項目．勤怠項目ID>
        val roleId = Optional.ofNullable(AppContexts.user().roles().forAttendance());
        val listAttId = ofArbitrary.getOutputItemList().stream().map(AttendanceItemToPrint::getAttendanceId).collect(Collectors.toList());
        val nameAttendanceItems = companyMonthlyItemService.getMonthlyItems(cid, roleId, listAttId, null);
        //6. ④: <call> 月次の勤怠項目を取得する
        List<MonthlyAttendanceItem> monthlyAttendanceItemList = this.monthlyAttendanceItemRepository.findByAttendanceItemId(cid, listAttId);
        List<AttendanceItemDisplayContents> contentsList = new ArrayList<>();

        Map<Integer, Integer> mapAttendanceItemToPrint =
                ofArbitrary.getOutputItemList().stream().filter(distinctByKey(AttendanceItemToPrint::getAttendanceId))
                        .collect(Collectors.toMap(AttendanceItemToPrint::getAttendanceId, AttendanceItemToPrint::getRanking));
        //7.
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


        return null;

    }

    public static <T> Predicate<T> distinctByKey(
            Function<? super T, ?> keyExtractor) {

        Map<Object, Boolean> seen = new ConcurrentHashMap<>();
        return t -> seen.putIfAbsent(keyExtractor.apply(t), Boolean.TRUE) == null;
    }
}
