package nts.uk.screen.at.app.alarmwrkp;

import java.util.List;

public interface AlarmPatternSetWorkPlaceRepo {
    List<AlarmPatternSetDto> getAll();
    List<AlarmCheckCategoryList> getAllCtg();

}
