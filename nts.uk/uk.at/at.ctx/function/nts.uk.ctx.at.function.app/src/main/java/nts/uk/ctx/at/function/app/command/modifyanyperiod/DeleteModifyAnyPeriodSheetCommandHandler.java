package nts.uk.ctx.at.function.app.command.modifyanyperiod;


import lombok.val;
import nts.arc.error.BusinessException;
import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.at.function.dom.anyperiodcorrection.formatsetting.AnyPeriodCorrectionDefaultFormatRepository;
import nts.uk.ctx.at.function.dom.anyperiodcorrection.formatsetting.AnyPeriodCorrectionFormatSetting;
import nts.uk.ctx.at.function.dom.anyperiodcorrection.formatsetting.AnyPeriodCorrectionFormatSettingRepository;
import nts.uk.ctx.at.function.dom.monthlycorrection.fixedformatmonthly.SheetCorrectedMonthly;
import nts.uk.shr.com.context.AppContexts;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 任意期間修正のフォーマット設定のSheetを削除する
 * UKDesign.UniversalK.就業.KDW_日別実績.KDW008_表示フォーマットの登録
 * .表示フォーマットの登録.アルゴリズム.任意期間修正.任意期間修正のフォーマット設定のSheetを削除する.任意期間修正のフォーマット設定のSheetを削除する
 */
@Stateless
@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
public class DeleteModifyAnyPeriodSheetCommandHandler extends CommandHandler<DeleteModifyAnyPeriodSheetCommand> {
    @Inject
    private AnyPeriodCorrectionDefaultFormatRepository anyPeriodCorrectionDefaultFormatRepository;

    @Inject
    private AnyPeriodCorrectionFormatSettingRepository formatSettingRepository;

    @Override
    protected void handle(CommandHandlerContext<DeleteModifyAnyPeriodSheetCommand> commandHandlerContext) {
        DeleteModifyAnyPeriodSheetCommand command = commandHandlerContext.getCommand();
        String cid = AppContexts.user().companyId();
        String code = command.getCode();
        Integer sheetNo = command.getSheetNo();
        //事前条件をチェックする
        if (command.getSheetNo().equals(1)) {
            throw new BusinessException("Msg_992");
        } else {
            //選択されているドメインモデル「任意期間修正のフォーマット設定のシート」を削除する
            val domainOpt = formatSettingRepository.get(cid, code);
            if (domainOpt.isPresent()) {
                AnyPeriodCorrectionFormatSetting domain = domainOpt.get();
                List<SheetCorrectedMonthly> sheetSetting = domain
                        .getSheetSetting()
                        .getListSheetCorrectedMonthly();
                sheetSetting = sheetSetting.stream()
                        .sorted(Comparator.comparing(SheetCorrectedMonthly::getSheetNo))
                        .collect(Collectors.toList());
                sheetSetting.removeIf(i->i.getSheetNo() == sheetNo);
                for (int i = 0; i < sheetSetting.size() ; i++) {
                    val sheet = sheetSetting.get(i);
                    sheet.setSheetNo(i + 1);
                }
                domain.getSheetSetting().setListSheetCorrectedMonthly(sheetSetting);
                formatSettingRepository.update(domain);
                val  defaultFormatOpt = anyPeriodCorrectionDefaultFormatRepository.get(cid);
                if(defaultFormatOpt.isPresent() && defaultFormatOpt.get().getCode().v().equals(domain.getCode().v()))
                    anyPeriodCorrectionDefaultFormatRepository.delete(cid);
            }
        }

    }
}
