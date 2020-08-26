package nts.uk.ctx.at.record.app.query.stamp;

import nts.uk.ctx.at.record.dom.stamp.card.stampcard.StampCard;
import nts.uk.ctx.at.record.dom.stamp.card.stampcard.StampCardRepository;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.ArrayList;
import java.util.Comparator;
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
     * @author Le Huu Dat
     */
    public List<StampCard> getStampCardBy(List<String> empIds) {
        List<StampCard> stampCardAll = stampCardRepo.getLstStampCardByLstSid(empIds);
        List<StampCard> stampCards = new ArrayList<>();
        Map<String, List<StampCard>> stampCardMap = stampCardAll.stream().collect(
                Collectors.groupingBy(StampCard::getEmployeeId, Collectors.toList()));
        for (Map.Entry<String, List<StampCard>> entry : stampCardMap.entrySet()) {
            StampCard item = entry.getValue().stream()
                    .sorted(Comparator.comparing(StampCard::getRegisterDate, Comparator.naturalOrder()).reversed())
                    .findFirst().get();
            stampCards.add(item);
        }
        return stampCards;
    }
}
