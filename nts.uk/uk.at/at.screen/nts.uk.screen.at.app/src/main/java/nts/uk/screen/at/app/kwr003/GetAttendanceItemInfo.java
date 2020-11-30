package nts.uk.screen.at.app.kwr003;


import lombok.val;
import nts.uk.ctx.at.function.app.query.outputworkstatustable.GetAttendanceIdByFormNumberQuery;
import nts.uk.ctx.at.function.dom.attendanceitemframelinking.enums.TypeOfItem;
import nts.uk.ctx.at.function.dom.attendanceitemname.service.AttendanceItemNameService;
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
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * ScreenQuery : 勤怠項目情報を取得する
 *
 * @author chinh.hm
 */
@Stateless
public class GetAttendanceItemInfo {

    @Inject
    private CompanyMonthlyItemService monthlyItemService;
    @Inject
    private CompanyDailyItemService dailyItemService;
    @Inject
    private AttendanceItemNameService attendanceItemNameService;
    @Inject
    private GetAttendanceIdByFormNumberQuery getAttendanceIdByFormNumberQuery;

    public  List<AttItemDto> getAttendanceItemInfo(DailyMonthlyClassification classification, int formNumberDisplay) {
        val cid = AppContexts.user().companyId();
        val roleId = Optional.ofNullable(AppContexts.user().roles().forAttendance());
        List<AttItemDto> rs = new ArrayList<AttItemDto>();
        if (classification == DailyMonthlyClassification.DAILY ) {
            //  （1:日次）
            val listAttdanceIdOfDaily = getAttendanceIdByFormNumberQuery.getAttendanceId(DailyMonthlyClassification.DAILY, formNumberDisplay);
            //  「使用不可の勤怠項目を除く」
            val listAttId = this.attendanceItemNameService.getAvaiableAttendanceItem(cid, TypeOfItem.Daily, listAttdanceIdOfDaily);
            //勤怠項目の種類　（1:日次）

            val itemDailys = dailyItemService.getDailyItems(cid, roleId, listAttId, null);
            rs.addAll(itemDailys.stream().map(e -> new AttItemDto(
                    e.getAttendanceItemId(),
                    e.getAttendanceItemName(),
                    e.getAttendanceItemDisplayNumber(),
                    e.getTypeOfAttendanceItem(),
                    convertDailyToAttForms(e.getTypeOfAttendanceItem(), 0)))
                    .collect(Collectors.toCollection(ArrayList::new)));
            return rs;
        }

        if (classification == DailyMonthlyClassification.MONTHLY) {
            //  （2:月次）
            val listAttdanceIdOfMonthly = getAttendanceIdByFormNumberQuery.getAttendanceId(DailyMonthlyClassification.MONTHLY, formNumberDisplay);
            //  「使用不可の勤怠項目を除く」
            val listAttId = this.attendanceItemNameService.getAvaiableAttendanceItem(cid, TypeOfItem.Monthly, listAttdanceIdOfMonthly);
            // 勤怠項目の種類　（2:月次）
            val itemMonthlys = monthlyItemService.getMonthlyItems(cid, roleId, listAttId, null);
            rs.addAll(itemMonthlys.stream().map(e -> new AttItemDto(
                    e.getAttendanceItemId(),
                    e.getAttendanceItemName(),
                    e.getAttendanceItemDisplayNumber(),
                    e.getTypeOfAttendanceItem(),
                    convertMonthlyToAttForms(e.getTypeOfAttendanceItem())))
                    .collect(Collectors.toCollection(ArrayList::new)));
            return rs;
        }
        return rs;
    }

    private Integer convertDailyToAttForms(Integer typeOfAttendanceItem, int masterType) {
        if (typeOfAttendanceItem == DailyAttendanceAtr.Code.value &&
                masterType == TypesMasterRelatedDailyAttendanceItem.WORK_TYPE.value) {
            return CommonAttributesOfForms.WORK_TYPE.value;
        } else if (typeOfAttendanceItem == DailyAttendanceAtr.Code.value &&
                masterType == TypesMasterRelatedDailyAttendanceItem.WORKING_HOURS.value) {
            return CommonAttributesOfForms.WORKING_HOURS.value;
        } else if (typeOfAttendanceItem == DailyAttendanceAtr.NumberOfTime.value) {
            return CommonAttributesOfForms.NUMBER_OF_TIMES.value;
        } else if (typeOfAttendanceItem == DailyAttendanceAtr.AmountOfMoney.value) {
            return CommonAttributesOfForms.AMOUNT_OF_MONEY.value;
        } else if (typeOfAttendanceItem == DailyAttendanceAtr.Time.value) {
            return CommonAttributesOfForms.TIME.value;
        } else
            return null;
    }

    private Integer convertMonthlyToAttForms(Integer typeOfAttendanceItem) {
        if (typeOfAttendanceItem == MonthlyAttendanceItemAtr.TIME.value) {
            return CommonAttributesOfForms.TIME.value;
        } else if (typeOfAttendanceItem == MonthlyAttendanceItemAtr.NUMBER.value) {
            return CommonAttributesOfForms.NUMBER_OF_TIMES.value;
        } else if (typeOfAttendanceItem == MonthlyAttendanceItemAtr.DAYS.value) {
            return CommonAttributesOfForms.DAYS.value;
        } else if (typeOfAttendanceItem == MonthlyAttendanceItemAtr.AMOUNT.value) {
            return CommonAttributesOfForms.AMOUNT_OF_MONEY.value;
        } else

            return null;
    }

}
