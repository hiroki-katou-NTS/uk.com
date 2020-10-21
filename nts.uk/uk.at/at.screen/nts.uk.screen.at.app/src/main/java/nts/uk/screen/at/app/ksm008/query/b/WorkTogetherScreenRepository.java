package nts.uk.screen.at.app.ksm008.query.b;

import java.util.List;
import java.util.Map;

public interface WorkTogetherScreenRepository {

    Map<String, Boolean> employeeWorkTogetherStatus(List<String> sids);

}
