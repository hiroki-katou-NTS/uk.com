package nts.uk.screen.at.app.kwr003;


import lombok.val;
import nts.uk.ctx.at.function.dom.outputitemsofworkstatustable.enums.CommonAttributesOfForms;
import nts.uk.ctx.at.function.dom.outputitemsofworkstatustable.enums.DailyMonthlyClassification;
import nts.uk.ctx.at.shared.dom.monthlyattditem.MonthlyAttendanceItemAtr;
import nts.uk.ctx.at.shared.dom.scherec.dailyattendanceitem.enums.DailyAttendanceAtr;
import nts.uk.ctx.at.shared.dom.scherec.dailyattendanceitem.enums.TypesMasterRelatedDailyAttendanceItem;
import nts.uk.ctx.at.shared.dom.scherec.dailyattendanceitem.service.CompanyDailyItemService;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattendanceitem.service.CompanyMonthlyItemService;
import nts.uk.shr.com.context.AppContexts;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.ArrayList;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * ScreenQuery : 勤怠項目情報を取得する
 *
 * @author chinh.hm
 */
@Stateless
public class GetAttendanceItemInfoScreenQuery {

    @Inject
    private CompanyMonthlyItemService monthlyItemService;
    @Inject
    private CompanyDailyItemService dailyItemService;


    public AttendanceItemInfoDto getAttendanceItemInfo(DailyMonthlyClassification classification, int formNumberDisplay) {
        // ①　=　List<勤怠項目ID>// TODO   QA.
        val listAttendanceItemId = new ArrayList<Integer>();
        //  （2:月次）
        val listAttdanceIdOfMonthly = new ArrayList<Integer>();
        //  （1:日次）
        val listAttdanceIdOfDaily = new ArrayList<Integer>();

        val cid = AppContexts.user().companyId();
        val roleId = Optional.of(AppContexts.user().roles().forAttendance()); // TODO QA
        // 勤怠項目の種類　（2:月次）
        val itemMonthlys = monthlyItemService.getMonthlyItems(cid, roleId, listAttdanceIdOfMonthly, null);
        //勤怠項目の種類　（1:日次）
        val itemDailys = dailyItemService.getDailyItems(cid, roleId, listAttdanceIdOfDaily, null);

        val listDailyAttributes = itemDailys.stream().map(e -> new AttItemDto(
                e.getAttendanceItemId(),
                e.getAttendanceItemName(),
                e.getAttendanceItemDisplayNumber(),
                e.getTypeOfAttendanceItem(),
                convertDailyToAttForms(e.getTypeOfAttendanceItem(), 0)))
                .collect(Collectors.toCollection(ArrayList::new));
        val listMonthlyAttributes = itemMonthlys.stream().map(e -> new AttItemDto(
                e.getAttendanceItemId(),
                e.getAttendanceItemName(),
                e.getAttendanceItemDisplayNumber(),
                e.getTypeOfAttendanceItem(),
                convertMonthlyToAttForms(e.getTypeOfAttendanceItem())))
                .collect(Collectors.toCollection(ArrayList::new));

        return new AttendanceItemInfoDto(listDailyAttributes, listMonthlyAttributes);

    }

    private int convertDailyToAttForms(Integer typeOfAttendanceItem, int masterType) {
        if (typeOfAttendanceItem == DailyAttendanceAtr.Code.value &&
                masterType == TypesMasterRelatedDailyAttendanceItem.WORK_TYPE.value) {
            return CommonAttributesOfForms.WORK_TYPE.value;
        }
        if (typeOfAttendanceItem == DailyAttendanceAtr.Code.value &&
                masterType == TypesMasterRelatedDailyAttendanceItem.WORKING_HOURS.value) {
            return CommonAttributesOfForms.WORKING_HOURS.value;
        }
        if (typeOfAttendanceItem == DailyAttendanceAtr.NumberOfTime.value) {
            return CommonAttributesOfForms.NUMBER_OF_TIMES.value;
        }
        if (typeOfAttendanceItem == DailyAttendanceAtr.AmountOfMoney.value) {
            return CommonAttributesOfForms.AMOUNT_OF_MONEY.value;
        }
        if (typeOfAttendanceItem == DailyAttendanceAtr.Time.value) {
            return CommonAttributesOfForms.TIME.value;
        }
        return 0;
    }

    private int convertMonthlyToAttForms(Integer typeOfAttendanceItem) {
        if (typeOfAttendanceItem == MonthlyAttendanceItemAtr.TIME.value) {

            return CommonAttributesOfForms.TIME.value;
        }
        if (typeOfAttendanceItem == MonthlyAttendanceItemAtr.NUMBER.value) {
            return CommonAttributesOfForms.NUMBER_OF_TIMES.value;
        }
        if (typeOfAttendanceItem == MonthlyAttendanceItemAtr.DAYS.value) {
            return CommonAttributesOfForms.DAYS.value;
        }
        if (typeOfAttendanceItem == MonthlyAttendanceItemAtr.AMOUNT.value) {
            return CommonAttributesOfForms.AMOUNT_OF_MONEY.value;
        }

        return 0;
    }

}
