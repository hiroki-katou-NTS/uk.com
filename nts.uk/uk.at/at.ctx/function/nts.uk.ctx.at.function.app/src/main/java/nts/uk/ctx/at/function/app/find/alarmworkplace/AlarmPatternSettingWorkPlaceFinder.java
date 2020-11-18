package nts.uk.ctx.at.function.app.find.alarmworkplace;

import nts.arc.primitive.PrimitiveValueBase;
import nts.uk.ctx.at.function.dom.alarm.AlarmPatternCode;
import nts.uk.ctx.at.function.dom.alarmworkplace.AlarmPatternSettingWorkPlace;
import nts.uk.ctx.at.function.dom.alarmworkplace.AlarmPatternSettingWorkPlaceRepository;
import nts.uk.shr.com.context.AppContexts;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.ArrayList;
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
    public List<WkpCheckConditionDto> selectPatternSetting(RequestParam requestParam) {
        Optional<AlarmPatternSettingWorkPlace> settingByWkp = repository.getBy(AppContexts.user().companyId(),new AlarmPatternCode(requestParam.getPatternCode()));
        return settingByWkp.isPresent() ? settingByWkp.get().getCheckConList().stream().map(x -> {
            List<String> lstCode = x.getCheckConditionLis().stream().map(PrimitiveValueBase::v).collect(Collectors.toList());
            return WkpCheckConditionDto.setdata(x,lstCode);
        }).collect(Collectors.toList()) : new ArrayList<>();
    }
}
