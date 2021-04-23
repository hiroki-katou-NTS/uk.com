package nts.uk.ctx.at.record.dom.stamp.card.stampcard.service;

import nts.uk.ctx.at.record.dom.stamp.card.stampcard.StampCard;
import nts.uk.ctx.at.record.dom.stamp.card.stampcard.StampCardRepository;
import nts.uk.ctx.at.record.dom.stamp.card.stampcard.StampNumber;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Stateless
public class GetStampCardQuery {

    @Inject
    private StampCardRepository stampCardRepo;

    /**
     * List<社員ID＞から打刻カードを全て取得する
     *
     * @param empIds List<社員ID＞
     * @return return Map＜社員ID、打刻カード番号＞
     * @author Le Huu Dat
     */
    public Map<String, StampNumber> getStampNumberBy(List<String> empIds) {
        List<StampCard> stampCardAll = stampCardRepo.getLstStampCardByLstSid(empIds);
        Map<String, StampNumber> stampCards = new HashMap<>();
        Map<String, List<StampCard>> stampCardMap = stampCardAll.stream().collect(
                Collectors.groupingBy(StampCard::getEmployeeId, Collectors.toList()));
        for (Map.Entry<String, List<StampCard>> entry : stampCardMap.entrySet()) {
            StampCard item = entry.getValue().stream()
                    .max(Comparator.comparing(StampCard::getRegisterDate, Comparator.naturalOrder())).get();
            stampCards.put(entry.getKey(), item.getStampNumber());
        }
        return stampCards;
    }
}
