package nts.uk.ctx.at.record.app.query.kdw008;


import nts.uk.ctx.at.shared.app.find.scherec.monthlyattditem.MonthlyAttdItemSharedDto;
import nts.uk.ctx.at.shared.dom.monthlyattditem.MonthlyAttendanceItem;
import nts.uk.ctx.at.shared.dom.monthlyattditem.MonthlyAttendanceItemRepository;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.context.LoginUserContext;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Query: 任意期間で利用する月次の勤怠項目を取得する
 * EA:UKDesign.ドメインモデル.NittsuSystem.UniversalK.就業.contexts.勤務実績.勤怠項目.月次の勤怠項目.App.任意期間で利用する月次の勤怠項目を取得する.任意期間で利用する月次の勤怠項目を取得する
 */
@Stateless
public class InitialDisplayFormatArbitraryPeriodQuery {

    @Inject
    public MonthlyAttendanceItemRepository monthlyRepo;
    /**
     * 全ての任意期間項目を取得する
     * @param companyId : 会社ID
     * @return: 月次の勤怠項目
     */
    public List<MonthlyAttdItemSharedDto> getAll(String companyId){
        return monthlyRepo.findAllAnyPeriod(companyId).stream().map(this::toDto)
                .collect(Collectors.toList());
    }
    private MonthlyAttdItemSharedDto toDto(MonthlyAttendanceItem dom) {
        MonthlyAttdItemSharedDto attdItemDto = new MonthlyAttdItemSharedDto();
        attdItemDto.setAttendanceItemDisplayNumber(dom.getDisplayNumber());
        attdItemDto.setNameLineFeedPosition(dom.getNameLineFeedPosition());
        attdItemDto.setAttendanceItemId(dom.getAttendanceItemId());
        attdItemDto.setAttendanceItemName(dom.getAttendanceName().v());
        attdItemDto.setDailyAttendanceAtr(dom.getMonthlyAttendanceAtr().value);
        attdItemDto.setDisplayNumber(dom.getDisplayNumber());
        return attdItemDto;

    }
}
