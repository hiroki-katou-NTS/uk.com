package nts.uk.ctx.at.function.app.find.alarmworkplace;

import nts.uk.ctx.at.function.dom.alarm.AlarmPatternCode;
import nts.uk.ctx.at.function.dom.alarmworkplace.AlarmPatternSettingWorkPlace;
import nts.uk.ctx.at.function.dom.alarmworkplace.AlarmPatternSettingWorkPlaceRepository;
import nts.uk.shr.com.context.AppContexts;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Stateless
public class AlarmPatternSettingWorkPlaceFinder {

    @Inject
    private AlarmPatternSettingWorkPlaceRepository repository;

    /**
     * 起動する
     */
    public List<WkpAlarmPatternSettingDto> getListPatternSettings() {
        List<AlarmPatternSettingWorkPlace> settingByWkp = repository.findByCompanyId(AppContexts.user().companyId());
        return settingByWkp.stream().map(WkpAlarmPatternSettingDto::setData).collect(Collectors.toList());
    }

    /**
     * パータンを選択する
     */
    public WkpAlarmPatternSettingDto selectPatternSetting(RequestParam requestParam) {
        Optional<AlarmPatternSettingWorkPlace> settingByWkp = repository.getBy(AppContexts.user().companyId(),new AlarmPatternCode(requestParam.getPatternCode()));
        if (!settingByWkp.isPresent()){
            return null;
        }
        return WkpAlarmPatternSettingDto.setData(settingByWkp.get());
    }
}
