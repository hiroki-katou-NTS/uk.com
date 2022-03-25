package nts.uk.screen.at.app.reservation;

import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import lombok.val;
import nts.gul.collection.CollectionUtil;
import nts.uk.ctx.at.record.dom.reservation.bentomenu.BentoMenuHistRepository;
import nts.uk.ctx.at.record.dom.reservation.bentomenu.BentoMenuHistory;
import nts.uk.shr.com.context.AppContexts;

/**
 * ScreenQuery :初期起動
 */
@Stateless
public class BentoMenuHistScreenQuery {

    @Inject
    private BentoMenuHistRepository bentoMenuHistRepository;

    public BentoMenuHistDto getListBentoMenuHist() {
        val result = new BentoMenuHistDto();
        // Get companyId
        val cid = AppContexts.user().companyId();
        // Get list bentomenuhist by companyId
        List<BentoMenuHistory> bentoMenuHistoryLst = bentoMenuHistRepository.findByCompany(cid);
        if(!CollectionUtil.isEmpty(bentoMenuHistoryLst)) {
        	result.setCompanyId(cid);
        	result.setHistoryItems(bentoMenuHistoryLst.stream().map(x -> new DateHistoryItemDto(
        			x.getHistoryItem().identifier(), 
        			x.getHistoryItem().start().toString(), 
        			x.getHistoryItem().end().toString())).collect(Collectors.toList()));
        }
        return result;

    }

}

