package nts.uk.ctx.at.function.app.query.workledgeroutputitem;


/**
 * Query: 勤務台帳の出力設定の詳細を取得する
 */

import lombok.val;
import nts.uk.ctx.at.function.dom.workledgeroutputitem.WorkLedgerOutputItem;
import nts.uk.ctx.at.function.dom.workledgeroutputitem.WorkLedgerOutputItemRepo;
import nts.uk.shr.com.context.AppContexts;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;

@Stateless
@TransactionAttribute(TransactionAttributeType.SUPPORTS)
public class GetSettingDetailWorkLedger {

    @Inject
    private WorkLedgerOutputItemRepo workLedgerOutputItemRepo;
    public WorkLedgerOutputItem getDetail(String settingId) {
        val cid = AppContexts.user().companyId();
        return workLedgerOutputItemRepo.getWorkStatusOutputSettings(cid, settingId);
    }
}
