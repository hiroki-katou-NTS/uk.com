package nts.uk.screen.at.app.kwr003;


import lombok.val;
import nts.uk.ctx.at.function.dom.outputitemsofworkstatustable.enums.CommonAttributesOfForms;
import nts.uk.ctx.at.function.dom.outputitemsofworkstatustable.enums.DailyMonthlyClassification;
import nts.uk.ctx.at.shared.dom.scherec.dailyattendanceitem.service.CompanyDailyItemService;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattendanceitem.service.CompanyMonthlyItemService;
import nts.uk.screen.at.app.stamp.personalengraving.query.GetRoleIDQuery;
import nts.uk.shr.com.context.AppContexts;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * ScreenQuery : 勤怠項目情報を取得する
 * @author chinh.hm
 */
public class GetAttendanceItemInfoScreenQuery {

    @Inject
    private CompanyMonthlyItemService monthlyItemService;
    @Inject
    private CompanyDailyItemService dailyItemService;


    public List<Object>getAttendanceItemInfo(DailyMonthlyClassification classification,int formNumberDisplay){
        // ①　=　List<勤怠項目ID>// TODO   QA.
        val listAttendanceItemId = new ArrayList<Integer>();
        //  （2:月次）
        val listAttdanceIdOfMonthly = new ArrayList<Integer>();
        //  （1:日次）
        val listAttdanceIdOfDaily = new ArrayList<Integer>();

        val cid = AppContexts.user().companyId();
        val roleId = Optional.of(AppContexts.user().roles().forAttendance()); // TODO QA
        // 勤怠項目の種類　（2:月次）
        val itemMonthlys = monthlyItemService.getMonthlyItems(cid,roleId,listAttdanceIdOfMonthly,null);
        //勤怠項目の種類　（1:日次）
        val itemDailys = dailyItemService.getDailyItems(cid,roleId,listAttdanceIdOfDaily,null);

        val listMonthlyAttributes = new ArrayList<>();
        val listDailyAttributes = new ArrayList<>();

        return null;
    }
    private List<CommonAttributesOfForms> convertToAttForms(){
        return  null;
    }

}
