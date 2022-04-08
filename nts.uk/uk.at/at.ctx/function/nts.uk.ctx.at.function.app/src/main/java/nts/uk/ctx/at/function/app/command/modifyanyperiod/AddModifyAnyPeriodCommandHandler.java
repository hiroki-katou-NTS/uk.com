package nts.uk.ctx.at.function.app.command.modifyanyperiod;


import nts.arc.error.BusinessException;
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
import java.util.List;
import java.util.stream.Collectors;

/**
 * 任意期間修正のフォーマットを登録する
 * UKDesign.UniversalK.就業.KDW_日別実績.KDW008_表示フォーマットの登録
 * .表示フォーマットの登録.アルゴリズム.任意期間修正.任意期間修正のフォーマットを登録する
 */
@Stateless
@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
public class AddModifyAnyPeriodCommandHandler extends CommandHandler<AddModifyAnyPeriodCommand> {

    @Inject
    private AnyPeriodCorrectionDefaultFormatRepository anyPeriodCorrectionDefaultFormatRepository;

    @Inject
    private AnyPeriodCorrectionFormatSettingRepository formatSettingRepository;

    @Override
    protected void handle(CommandHandlerContext<AddModifyAnyPeriodCommand> commandHandlerContext) {
        AddModifyAnyPeriodCommand command = commandHandlerContext.getCommand();
        String cid = AppContexts.user().companyId();
        String code = command.getCode();
        String name = command.getName();
        //アルゴリズム「重複チェック」を実行する   (thực hiện thuật toán check duplicate)
        boolean exit = formatSettingRepository.get(cid, code).isPresent();
        if (exit) {
            throw new BusinessException("Msg_3");
        }
        //ドメインモデル「任意期間修正のフォーマット設定」を登録する
        List<SheetCorrectedMonthly> listSheet = new ArrayList<>();
        listSheet.add(new SheetCorrectedMonthly(
                1,
                new DailyPerformanceFormatName(command.getSheetName()),
                command.getListItemDetail().stream().map(e -> new DisplayTimeItem(
                        e.getOrder(),
                        e.getAttendanceItemId(),
                        e.getColumnWidth()
                )).collect(Collectors.toList())
        ));

        AnyPeriodCorrectionFormatSetting domain = new AnyPeriodCorrectionFormatSetting(code, name, listSheet);
        formatSettingRepository.insert(domain);
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
