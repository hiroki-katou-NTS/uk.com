package nts.uk.ctx.at.function.app.find.alarmworkplace;

import nts.uk.ctx.at.function.dom.alarmworkplace.AlarmPatternSettingWorkPlace;
import nts.uk.ctx.at.function.dom.alarmworkplace.AlarmPatternSettingWorkPlaceRepository;
import nts.uk.shr.com.context.AppContexts;
import sun.awt.AppContext;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.List;

@Stateless
public class AlarmPatternSettingWorkPlaceFinder {

    @Inject
    private AlarmPatternSettingWorkPlaceRepository repository;

    /**
     * 起動する
     */
    public List<AlarmPatternSettingWorkPlace> getListPatternSettings() {
        List<AlarmPatternSettingWorkPlace> settingByWkp = repository.findByCompanyId(AppContexts.user().companyId());
        if (settingByWkp.size() > 0){

        }
        return null;
    }

    /**
     * パータンを選択する
     */
//    public AlarmPatternSettingWorkPlace selectPattern() {
//
//    }

    /**
     * パータン設定を取得する
     */
//    public AlarmPatternSettingWorkPlace et pattern settings() {
//
//    }
}
