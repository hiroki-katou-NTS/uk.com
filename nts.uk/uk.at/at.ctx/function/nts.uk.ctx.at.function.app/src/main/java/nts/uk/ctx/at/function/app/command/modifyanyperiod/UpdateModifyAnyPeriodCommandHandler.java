package nts.uk.ctx.at.function.app.command.modifyanyperiod;


import lombok.val;
import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.at.function.dom.anyperiodcorrection.formatsetting.AnyPeriodCorrectionDefaultFormat;
import nts.uk.ctx.at.function.dom.anyperiodcorrection.formatsetting.AnyPeriodCorrectionDefaultFormatRepository;
import nts.uk.ctx.at.function.dom.anyperiodcorrection.formatsetting.AnyPeriodCorrectionFormatSetting;
import nts.uk.ctx.at.function.dom.anyperiodcorrection.formatsetting.AnyPeriodCorrectionFormatSettingRepository;
import nts.uk.ctx.at.function.dom.dailyperformanceformat.primitivevalue.DailyPerformanceFormatName;
import nts.uk.ctx.at.function.dom.monthlycorrection.fixedformatmonthly.DisplayTimeItem;
import nts.uk.ctx.at.function.dom.monthlycorrection.fixedformatmonthly.SheetCorrectedMonthly;
import nts.uk.shr.com.context.AppContexts;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Stateless
@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
public class UpdateModifyAnyPeriodCommandHandler extends CommandHandler<UpdateModifyAnyPeriodCommand> {
    @Inject
    private AnyPeriodCorrectionDefaultFormatRepository anyPeriodCorrectionDefaultFormatRepository;

    @Inject
    private AnyPeriodCorrectionFormatSettingRepository formatSettingRepository;

    @Override
    protected void handle(CommandHandlerContext<UpdateModifyAnyPeriodCommand> commandHandlerContext) {
        UpdateModifyAnyPeriodCommand command = commandHandlerContext.getCommand();
        String cid = AppContexts.user().companyId();
        String code = command.getCode();
        String name = command.getName();
        Integer sheetNo = command.getSheetNo();
        //アルゴリズム「重複チェック」を実行する   (thực hiện thuật toán check duplicate)
        Optional<AnyPeriodCorrectionFormatSetting> oldDomainOpt = formatSettingRepository.get(cid, code);
        boolean exit = oldDomainOpt.isPresent();
        if (exit) {
            AnyPeriodCorrectionFormatSetting olldDomain = oldDomainOpt.get();
            //ドメインモデル「任意期間修正のフォーマット設定」を登録する
            List<SheetCorrectedMonthly> listSheet = olldDomain.getSheetSetting().getListSheetCorrectedMonthly();
            boolean checkExit = listSheet.stream().mapToInt(SheetCorrectedMonthly::getSheetNo)
                    .anyMatch(e->e == sheetNo);
            val sheetItem = new SheetCorrectedMonthly(
                   sheetNo,
                    new DailyPerformanceFormatName(command.getSheetName()),
                    command.getListItemDetail().stream().map(e -> new DisplayTimeItem(
                            e.getOrder(),
                            e.getAttendanceItemId(),
                            e.getColumnWidth()
                    )).collect(Collectors.toList())
            );
            if(checkExit){
                listSheet.removeIf(item -> item.getSheetNo() == sheetNo);
            }else {
                val sheetNoBefore = listSheet.stream().mapToInt(SheetCorrectedMonthly::getSheetNo).max();
                sheetItem.setSheetNo(sheetNoBefore.isPresent() ? (sheetNoBefore.getAsInt() + 1) : 1);
            }
            listSheet.add(sheetItem);
            AnyPeriodCorrectionFormatSetting domain = new AnyPeriodCorrectionFormatSetting(code, name, listSheet);
            formatSettingRepository.update(domain);
            //画面項目「A9_1：このフォーマットを初期設定にする」の状態をチェックする
            boolean initFormat = command.isInitFormat();
            if (initFormat) {
                //ドメインモデル「任意期間修正の規定フォーマット」を登録する
                anyPeriodCorrectionDefaultFormatRepository.delete(cid);
                AnyPeriodCorrectionDefaultFormat defaultFormat = new AnyPeriodCorrectionDefaultFormat(code);
                anyPeriodCorrectionDefaultFormatRepository.insert(defaultFormat);
            }
        }

    }
}
