package nts.uk.ctx.pr.core.dom.wageprovision.statementlayout.service;

import nts.uk.ctx.pr.core.dom.wageprovision.statementitem.*;
import nts.uk.shr.com.context.AppContexts;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
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
    public List<StatementItemCustom> getStatementItem(int categoryAtr) {
        String cid = AppContexts.user().companyId();
        // ドメインモデル「固定項目区分一覧」を取得する
        List<String> itemNameCds = fixedItemClassificationListRepo
                .getFixedItemClassificationListBySpecOutPutCls(SpecificationOutputCls.DO_NOT_OUTPUT.value)
                .stream().map(x -> x.getItemNameCd().v()).collect(Collectors.toList());

        // TODO #125441
        // ドメインモデル「明細書項目」を取得する
        // ドメインモデル「明細書項目名称」を取得する
        return statementItemRepo.getItemCustomByCtgAndExcludeCodes(cid, categoryAtr, itemNameCds);
    }

    /**
     * Screen F: 起動時処理
     */
    public List<StatementItemCustom> getAttendanceItem(int categoryAtr, List<String> itemCdFromScreen, String itemSelected) {
        String cid = AppContexts.user().companyId();
        // ドメインモデル「固定項目区分一覧」を取得する
        List<String> itemNameCds = fixedItemClassificationListRepo
                .getFixedItemClassificationListBySpecOutPutCls(SpecificationOutputCls.DO_NOT_OUTPUT.value)
                .stream().map(x -> x.getItemNameCd().v()).collect(Collectors.toList());

        // Add to set to remove duplicate
        Set<String> targetSet = new HashSet<>(itemNameCds);
        targetSet.addAll(itemCdFromScreen);
        targetSet.remove(itemSelected);

        // Convert back to list
        itemNameCds = new ArrayList<>(targetSet);

        // TODO #125441
        // ドメインモデル「明細書項目」を取得する
        // ドメインモデル「明細書項目名称」を取得する
        return statementItemRepo.getItemCustomByCtgAndExcludeCodes(cid, categoryAtr, itemNameCds);
    }
}
