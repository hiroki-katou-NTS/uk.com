package nts.uk.screen.at.app.reservation;

import lombok.val;
import nts.uk.ctx.at.record.dom.reservation.bento.IBentoMenuHistoryRepository;
import nts.uk.shr.com.context.AppContexts;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.ArrayList;

/**
 * ScreenQuery :初期起動
 */
@Stateless
public class BentoMenuHistScreenQuery {

    @Inject
    private IBentoMenuHistoryRepository bentoMenuHistoryRepository;

    public BentoMenuHistDto getListBentoMenuHist() {
        val result = new BentoMenuHistDto();
        // Get companyId
        val cid = AppContexts.user().companyId();
        // Get list bentomenuhist by companyId
        val bentoMenuHistory = bentoMenuHistoryRepository.findByCompanyId(cid);
        val dateItem = new ArrayList<DateHistoryItemDto>();
        if (bentoMenuHistory.isPresent()) {

            bentoMenuHistory.get().getHistoryItems().stream().forEach(e ->
                    dateItem.add(new DateHistoryItemDto(e.identifier(), e.start().toString(), e.end().toString()))
            );
            result.setCompanyId(cid);
            result.setHistoryItems(dateItem);
        }
        return result;

    }

}

