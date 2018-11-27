package nts.uk.ctx.pr.core.app.export.wageprovision.statementlayout;

import nts.arc.layer.app.file.export.ExportService;
import nts.arc.layer.app.file.export.ExportServiceContext;
import nts.arc.time.YearMonth;
import nts.uk.ctx.pr.core.dom.wageprovision.formula.FormulaRepository;
import nts.uk.ctx.pr.core.dom.wageprovision.salaryindividualamountname.SalIndAmountNameRepository;
import nts.uk.ctx.pr.core.dom.wageprovision.statementitem.deductionitemset.DeductionItemSetRepository;
import nts.uk.ctx.pr.core.dom.wageprovision.statementitem.paymentitemset.PaymentItemSetRepository;
import nts.uk.ctx.pr.core.dom.wageprovision.statementlayout.*;
import nts.uk.ctx.pr.core.dom.wageprovision.statementlayout.export.StatementLayoutExportData;
import nts.uk.ctx.pr.core.dom.wageprovision.statementlayout.export.StatementLayoutFileGenerator;
import nts.uk.ctx.pr.core.dom.wageprovision.wagetable.WageTableRepository;
import nts.uk.shr.com.context.AppContexts;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.*;
import java.util.stream.Collectors;

@Stateless
public class StatementLayoutExportService extends ExportService<StatementLayoutExportQuery> {

    @Inject
    private StatementLayoutFileGenerator dtatementLayoutFileGenerator;

    @Inject
    private WageTableRepository wageTableRepo;

    @Inject
    private SalIndAmountNameRepository salIndAmountNameRepo;

    @Inject
    private FormulaRepository formulaRepo;

    @Inject
    private StatementLayoutRepository statementLayoutRepo;

    @Inject
    private StatementLayoutHistRepository statementLayoutHistRepo;

    @Inject
    private StatementLayoutSetRepository statementLayoutSetRepo;

    @Inject
    private PaymentItemDetailSetRepository paymentItemDetailSetRepo;

    @Inject
    private DeductionItemDetailSetRepository deductionItemDetailSetRepo;

    @Override
    protected void handle(ExportServiceContext<StatementLayoutExportQuery> exportServiceContext) {
        String cid = AppContexts.user().companyId();
        StatementLayoutExportQuery cmd = exportServiceContext.getQuery();
        List<String> sttCodes = exportServiceContext.getQuery().getStatementCodes();
        int processingDate = cmd.getProcessingDate();
        // ドメインモデル「賃金テーブル」を取得する
        wageTableRepo.getAllWageTable(cid);
        // ドメインモデル「給与個人別金額名称」を取得する
        salIndAmountNameRepo.getSalIndAmountName(cid);
        // ドメインモデル「計算式」を取得する
        formulaRepo.getAllFormula();

        // ドメインモデル「明細書レイアウト」を取得する
        Map<String, StatementLayout> statementLayoutMap = statementLayoutRepo.getAllStatementLayoutByCidAndCodes(cid, sttCodes)
                .stream().collect(Collectors.toMap(x -> x.getStatementCode().v(), x -> x));

        // ドメインモデル「明細書レイアウト履歴」を取得する
        // 取得した明細書レイアウト履歴から基準年月に準ずる履歴IDを取得する
        Map<String, StatementLayoutHist> statementLayoutHistMap = statementLayoutHistRepo.getLayoutHistByCidAndCodesAndYM(cid, sttCodes, processingDate)
                .stream().collect(Collectors.toMap(x -> x.getStatementCode().v(), x -> x));

        // 選択された明細書ごとに処理を行う
        for (String sttCode : sttCodes) {
            StatementLayout sttLayout = null;
            StatementLayoutHist sttLayoutHist = null;
            String histId = null;
            // ドメインモデル「明細書レイアウト」を取得する
            if (statementLayoutMap.containsKey(sttCode)) {
                sttLayout = statementLayoutMap.get(sttCode);
            }
            // ドメインモデル「明細書レイアウト履歴」を取得する
            if (statementLayoutHistMap.containsKey(sttCode)) {
                sttLayoutHist = statementLayoutHistMap.get(sttCode);
                if (!sttLayoutHist.items().isEmpty()) {
                    // 取得した明細書レイアウト履歴から基準年月に準ずる履歴IDを取得する
                    histId = sttLayoutHist.items().get(0).identifier();
                }
            }
            if (histId == null) continue;
            // ドメインモデル「明細書レイアウト設定」を取得する
            Optional<StatementLayoutSet> sttLayoutSetOtp = statementLayoutSetRepo.getStatementLayoutSetById(histId);
            // 取得した給与項目IDごとに処理を実施する
            if (!sttLayoutSetOtp.isPresent()) continue;
            for (SettingByCtg set : sttLayoutSetOtp.get().getListSettingByCtg()) {
                // ドメインモデル「明細書項目名称」を取得する
                // ドメインモデル「明細書項目」を取得する
                switch (set.getCtgAtr()) {
                    case PAYMENT_ITEM:
                        // ドメインモデル「支給項目明細設定」を取得する
                        paymentItemDetailSetRepo.getPaymentItemDetailSetById(histId);
                        break;
                    case DEDUCTION_ITEM:
                        // ドメインモデル「控除項目明細設定」を取得する
                        deductionItemDetailSetRepo.getDeductionItemDetailSetById(histId);
                        break;
                }
                // ドメインモデル「明細書項目範囲設定」を取得する

            }

            StatementLayoutExportData data = new StatementLayoutExportData();
            data.setStatementCode(sttLayout.getStatementCode().v());
            data.setStatementName(sttLayout.getStatementName().v());
            data.setProcessingDate(new YearMonth(processingDate));
            data.setListSettingByCtg(sttLayoutSetOtp.get().getListSettingByCtg());
        }
        dtatementLayoutFileGenerator.generate();
    }

    List<String> getItemIds(SettingByCtg settingByCtg) {
        List<String> itemIds = new ArrayList();
        for (LineByLineSetting line : settingByCtg.getListLineByLineSet()) {
            for (SettingByItem item : line.getListSetByItem()) {
                itemIds.add(item.getItemId());
            }
        }
        return itemIds;
    }
}
