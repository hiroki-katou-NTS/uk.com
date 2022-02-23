package nts.uk.ctx.at.record.app.find.kdw008;


import nts.uk.ctx.at.record.app.query.kdw008.InitialDisplayFormatArbitraryPeriodQuery;
import nts.uk.ctx.at.shared.app.find.scherec.monthlyattditem.MonthlyAttdItemSharedDto;
import nts.uk.ctx.at.shared.dom.scherec.dailyattendanceitem.adapter.attendanceitemname.AttItemName;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattendanceitem.service.CompanyMonthlyItemService;
import nts.uk.shr.com.context.AppContexts;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * 月次の勤怠項目（任意期間利用項目）の取得
 * Ea: UKDesign.UniversalK.就業.KDW_日別実績.KDW008_表示フォーマットの登録
 * .表示フォーマットの登録.アルゴリズム.任意期間修正.月次の勤怠項目（任意期間利用項目）の取得.月次の勤怠項目（任意期間利用項目）の取得
 */
@Stateless
public class AcquisitionOfMonthlyAttendanceItemFinder {
    @Inject
    private InitialDisplayFormatArbitraryPeriodQuery monthlyItemQuery;
    @Inject
    private CompanyMonthlyItemService companyMonthlyItemService;
    public List<AttItemName> getMonthlyAttendaceItem(){
        String cid = AppContexts.user().companyId();
        //ドメインモデル「月次の勤怠項目」を取得する(lấy dữ liệu domain「月次の勤怠項目」)
        List<MonthlyAttdItemSharedDto> monthlyAttdItemList = monthlyItemQuery.getAll(cid);
        List<Integer> attIds = monthlyAttdItemList.stream()
                .map(MonthlyAttdItemSharedDto::getAttendanceItemId)
                .collect(Collectors.toList());
        //会社の月次項目を取得する
        return companyMonthlyItemService.getMonthlyItems(cid, Optional.empty(),attIds, Collections.emptyList());

    }
}
