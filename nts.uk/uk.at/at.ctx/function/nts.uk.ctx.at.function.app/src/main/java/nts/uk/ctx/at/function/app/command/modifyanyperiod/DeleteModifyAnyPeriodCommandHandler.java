package nts.uk.ctx.at.function.app.command.modifyanyperiod;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.at.function.dom.anyperiodcorrection.formatsetting.AnyPeriodCorrectionDefaultFormat;
import nts.uk.ctx.at.function.dom.anyperiodcorrection.formatsetting.AnyPeriodCorrectionDefaultFormatRepository;
import nts.uk.ctx.at.function.dom.anyperiodcorrection.formatsetting.AnyPeriodCorrectionFormatSetting;
import nts.uk.ctx.at.function.dom.anyperiodcorrection.formatsetting.AnyPeriodCorrectionFormatSettingRepository;
import nts.uk.shr.com.context.AppContexts;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.Optional;


/**
 * 任意期間修正のフォーマットを削除する
 * UKDesign.UniversalK.就業.KDW_日別実績.KDW008_表示フォーマットの登録
 *.表示フォーマットの登録.アルゴリズム.任意期間修正.任意期間修正のフォーマットを削除する.任意修正のフォーマットを削除する
 */
@Stateless
public class DeleteModifyAnyPeriodCommandHandler extends CommandHandler<DeleteModifyAnyPeriodCommand> {

    @Inject
    private AnyPeriodCorrectionDefaultFormatRepository anyPeriodCorrectionDefaultFormatRepository;

    @Inject
    private AnyPeriodCorrectionFormatSettingRepository formatSettingRepository;
    @Override
    protected void handle(CommandHandlerContext<DeleteModifyAnyPeriodCommand> commandHandlerContext) {
        //ドメインモデル「任意期間修正のフォーマット設定」を削除する
        DeleteModifyAnyPeriodCommand command = commandHandlerContext.getCommand();
        String cid = AppContexts.user().companyId();
        Optional<AnyPeriodCorrectionFormatSetting> optionalFormatSetting = formatSettingRepository.get(cid,command.getCode());
        if(optionalFormatSetting.isPresent()){
            formatSettingRepository.delete(cid,command.getCode());
            Optional<AnyPeriodCorrectionDefaultFormat> defaultFormat = anyPeriodCorrectionDefaultFormatRepository.get(cid);
            if(defaultFormat.isPresent()){
                anyPeriodCorrectionDefaultFormatRepository.delete(cid);
            }
        }

    }
}
