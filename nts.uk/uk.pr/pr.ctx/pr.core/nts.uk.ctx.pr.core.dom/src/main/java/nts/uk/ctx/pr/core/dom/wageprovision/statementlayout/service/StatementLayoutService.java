package nts.uk.ctx.pr.core.dom.wageprovision.statementlayout.service;

import nts.uk.ctx.pr.core.dom.wageprovision.statementitem.*;
import nts.uk.shr.com.context.AppContexts;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.List;
import java.util.stream.Collectors;

@Stateless
public class StatementLayoutService {

    @Inject
    private FixedItemClassificationListRepository fixedItemClassificationListRepo;

    @Inject
    private StatementItemRepository statementItemRepo;
    /**
     * 支給項目一覧を更新する
     */
    public List<StatementItemCustom> getStatementItem() {
        String cid = AppContexts.user().companyId();
        // ドメインモデル「固定項目区分一覧」を取得する
        List<String> itemNameCds = fixedItemClassificationListRepo
                .getFixedItemClassificationListBySpecOutPutCls(SpecificationOutputCls.DO_NOT_OUTPUT.value)
                .stream().map(x -> x.getItemNameCd().v()).collect(Collectors.toList());

        // TODO #125441
        // ドメインモデル「明細書項目」を取得する
        // ドメインモデル「明細書項目名称」を取得する
        return statementItemRepo.getItemCustomByCtgAndExcludeCodes(cid, CategoryAtr.PAYMENT_ITEM.value, itemNameCds);
    }
}
