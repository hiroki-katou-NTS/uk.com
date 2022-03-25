package nts.uk.screen.at.app.query.kfp002;

import nts.uk.ctx.at.function.app.query.anyperiodcorrection.formatsetting.AnyPeriodCorrectionDefaultFormatSettingQuery;
import nts.uk.ctx.at.function.app.query.anyperiodcorrection.formatsetting.AnyPeriodCorrectionFormatDto;
import nts.uk.ctx.at.function.app.query.anyperiodcorrection.formatsetting.AnyPeriodCorrectionFormatSettingQuery;
import nts.uk.ctx.at.shared.app.find.scherec.monthlyattditem.ControlOfMonthlyDto;
import nts.uk.ctx.at.shared.app.find.scherec.monthlyattditem.ControlOfMonthlyFinder;
import nts.uk.ctx.at.shared.dom.scherec.dailyattendanceitem.adapter.attendanceitemname.AttItemName;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattendanceitem.service.CompanyMonthlyItemService;
import nts.uk.screen.at.app.monthlyperformance.correction.MonthlyPerformanceScreenRepo;
import nts.uk.screen.at.app.monthlyperformance.correction.dto.MonthlyAttendanceItemDto;
import nts.uk.shr.com.context.AppContexts;
import org.apache.commons.lang3.StringUtils;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * UKDesign.UniversalK.就業.KFP_任意期間集計.KFP002_任意期間の修正.A：任意期間の修正.メニュー別OCD
 * 表示フォーマットを取得する
 */
@Stateless
public class GetAnyPeriodDisplayFormatScreenQuery {
    @Inject
    private AnyPeriodCorrectionFormatSettingQuery formatSettingQuery;

    @Inject
    private AnyPeriodCorrectionDefaultFormatSettingQuery defaultFormatSettingQuery;

    @Inject
    private CompanyMonthlyItemService monthlyItemService;

    @Inject
    private MonthlyPerformanceScreenRepo monthlyItemScreenRepo;

    @Inject
    private ControlOfMonthlyFinder controlOfMonthlyFinder;

    public AnyPeriodCorrectionDisplayDto getDisplayFormat(String frameCode) {
        AnyPeriodCorrectionFormatDto formatSetting;
        if (StringUtils.isEmpty(frameCode) || frameCode.equals("null")) {
            formatSetting = defaultFormatSettingQuery.getDefaultFormatSetting();
        } else {
            formatSetting = formatSettingQuery.getSetting(frameCode);
        }

        if (formatSetting != null) {
            List<Integer> itemIds = new ArrayList<>();
            formatSetting.getSheetSettings().forEach(sheet -> {
                itemIds.addAll(sheet.getListDisplayTimeItem().stream().map(i -> i.getItemDaily()).collect(Collectors.toList()));
            });

            String companyId = AppContexts.user().companyId();
            List<AttItemName> items = monthlyItemService.getMonthlyItems(
                    companyId,
                    Optional.empty(),
                    itemIds,
                    Collections.emptyList()
            );

            List<MonthlyAttendanceItemDto> monthlyItems = monthlyItemScreenRepo.findByAttendanceItemId(companyId, itemIds);

            List<ControlOfMonthlyDto> monthlyItemControls = controlOfMonthlyFinder.getListControlOfAttendanceItem(itemIds);

            return new AnyPeriodCorrectionDisplayDto(
                    formatSetting,
                    items,
                    monthlyItems,
                    monthlyItemControls
            );
        }

        return new AnyPeriodCorrectionDisplayDto(
                formatSetting,
                Collections.emptyList(),
                Collections.emptyList(),
                Collections.emptyList()
        );
    }
}
