package nts.uk.ctx.pr.core.app.find.wageprovision.statementbindingsetting;

import nts.uk.ctx.pr.core.dom.wageprovision.statementbindingsetting.StateCorrelationHisClassificationRepository;
import nts.uk.shr.com.context.AppContexts;

import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

@Stateless
/**
* 明細書紐付け履歴（分類）: Finder
*/
public class StateCorrelationHisClassificationFinder {

    @Inject
    private StateCorrelationHisClassificationRepository finder;

    public List<StateCorrelationHisClassificationDto> getStateCorrelationHisClassificationByCid(){
        String cId = AppContexts.user().companyId();
        return StateCorrelationHisClassificationDto.fromDomain(finder.getStateCorrelationHisClassificationByCid(cId).get());
    }


}
