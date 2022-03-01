package nts.uk.ctx.at.function.app.command.modifyanyperiod;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.at.function.dom.anyperiodcorrection.formatsetting.AnyPeriodCorrectionDefaultFormat;
import nts.uk.ctx.at.function.dom.anyperiodcorrection.formatsetting.AnyPeriodCorrectionDefaultFormatRepository;
import nts.uk.ctx.at.function.dom.anyperiodcorrection.formatsetting.AnyPeriodCorrectionFormatSetting;
import nts.uk.ctx.at.function.dom.anyperiodcorrection.formatsetting.AnyPeriodCorrectionFormatSettingRepository;
import nts.uk.ctx.at.function.dom.monthlycorrection.fixedformatmonthly.SheetCorrectedMonthly;
import nts.uk.shr.com.context.AppContexts;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;
import java.util.List;
import java.util.Optional;
@Stateless
@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
public class DuplicateModifyAnyPeriodCommandHandler extends CommandHandler<DuplicateModifyAnyPeriodCommand> {
    @Inject
    private AnyPeriodCorrectionDefaultFormatRepository anyPeriodCorrectionDefaultFormatRepository;

    @Inject
    private AnyPeriodCorrectionFormatSettingRepository formatSettingRepository;

    @Override
    protected void handle(CommandHandlerContext<DuplicateModifyAnyPeriodCommand> commandHandlerContext) {
        DuplicateModifyAnyPeriodCommand command = commandHandlerContext.getCommand();
        String cid = AppContexts.user().companyId();
        String code = command.getCode();
        String name = command.getName();
        String codeCurrent = command.getCodeCurrent();
        //アルゴリズム「重複チェック」を実行する   (thực hiện thuật toán check duplicate)
        Optional<AnyPeriodCorrectionFormatSetting> oldDomainOpt = formatSettingRepository.get(cid, codeCurrent);
        boolean exit = oldDomainOpt.isPresent();
        if (exit) {
            AnyPeriodCorrectionFormatSetting olldDomain = oldDomainOpt.get();
            //ドメインモデル「任意期間修正のフォーマット設定」を登録する
            List<SheetCorrectedMonthly> listSheet = olldDomain.getSheetSetting().getListSheetCorrectedMonthly();
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
}
