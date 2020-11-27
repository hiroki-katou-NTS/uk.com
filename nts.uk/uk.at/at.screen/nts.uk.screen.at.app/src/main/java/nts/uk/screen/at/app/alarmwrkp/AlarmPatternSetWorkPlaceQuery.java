package nts.uk.screen.at.app.alarmwrkp;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.List;

@Stateless
public class AlarmPatternSetWorkPlaceQuery {
    @Inject
    private  AlarmPatternSetWorkPlaceRepo alarmPatternSetWorkPlaceRepo;

    public List<AlarmPatternSetDto> getAll(){
        return alarmPatternSetWorkPlaceRepo.getAll();
    }
}
