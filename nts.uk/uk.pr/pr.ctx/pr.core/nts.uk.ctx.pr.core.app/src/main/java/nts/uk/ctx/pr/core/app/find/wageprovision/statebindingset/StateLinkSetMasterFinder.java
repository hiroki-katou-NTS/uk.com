package nts.uk.ctx.pr.core.app.find.wageprovision.statebindingset;

import nts.uk.ctx.pr.core.dom.wageprovision.statebindingset.StateLinkSetMaster;
import nts.uk.ctx.pr.core.dom.wageprovision.statementlayout.StatementLayout;
import nts.uk.ctx.pr.core.dom.wageprovision.statementlayout.StatementLayoutRepository;
import nts.uk.shr.com.context.AppContexts;

import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

@Stateless
/**
* 明細書紐付け設定（マスタ）: Finder
*/
public class StateLinkSetMasterFinder {


    @Inject
    private StatementLayoutRepository statementLayoutFinder;

    public List<StateLinkSetMasterDto> getStateLinkSettingMaster(int startYearMonth, List<StateLinkSetMaster> listStateLinkSetMaster){
        String cId = AppContexts.user().companyId();
        List<StatementLayout> statementLayout = statementLayoutFinder.getStatement(cId, startYearMonth);
        List<StateLinkSetMasterDto> listStateLinkSetMasterDto = listStateLinkSetMaster.stream()
                .map(i -> StateLinkSetMasterDto.fromDomain(i, statementLayout)).collect(Collectors.toList());
        return listStateLinkSetMasterDto;
    }

}
