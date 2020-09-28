package nts.uk.screen.at.app.ksm008.query;

import lombok.val;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Stateless
@TransactionAttribute(TransactionAttributeType.SUPPORTS)
public class AlarmCheckConditionsFinder {

//    @Inject
//    private SampleRepository repository;

    public List<AlarmCheckDto> getItems() {
//        val data = repository.getSampleData();
        // Convert data to SampleDto
        // Return converted DTO list.
        val result = new ArrayList<AlarmCheckDto>();
        result.add(AlarmCheckDto.startScreen(new WaitingDomain("001", "first", Arrays.asList(
                new SubConditionDto("s001", "description1", "message1"),
                new SubConditionDto("s002", "description2", "message2")

        ))));
        result.add(AlarmCheckDto.startScreen(new WaitingDomain("002", "second", Arrays.asList())));
        result.add(AlarmCheckDto.startScreen(new WaitingDomain("003", "third", Arrays.asList())));
        return result;
    }
}