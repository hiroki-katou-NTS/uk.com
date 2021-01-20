package nts.uk.ctx.at.function.app.query.arbitraryperiodsummarytable;


/**
 * Query: 任意期間集計表の出力設定の詳細を取得する
 */

import lombok.val;
import nts.uk.ctx.at.function.dom.arbitraryperiodsummarytable.OutputSettingOfArbitrary;
import nts.uk.ctx.at.function.dom.arbitraryperiodsummarytable.OutputSettingOfArbitraryRepo;
import nts.uk.ctx.at.function.dom.workledgeroutputitem.WorkLedgerOutputItem;
import nts.uk.ctx.at.function.dom.workledgeroutputitem.WorkLedgerOutputItemRepo;
import nts.uk.shr.com.context.AppContexts;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;

@Stateless
@TransactionAttribute(TransactionAttributeType.SUPPORTS)
public class GetOutputSettingDetailArbitraryQuery {
    @Inject
    private OutputSettingOfArbitraryRepo ofArbitraryRepo;
    public OutputSettingOfArbitrary getDetail(String settingId) {
        val cid = AppContexts.user().companyId();
        return ofArbitraryRepo.getOutputSettingOfArbitrary(cid, settingId);
    }
}
