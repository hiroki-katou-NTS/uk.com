package nts.uk.screen.at.app.kmk.kmk008.employee;

import nts.uk.ctx.at.record.dom.standardtime.repository.AgreementMonthSettingRepository;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.agreement.management.exceptsetting.AgreementMonthSetting;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 社員の特例設定を取得する（年月）
 */
@Stateless
public class AgreeMonthSetScreenProcessor {

    @Inject
    private AgreementMonthSettingRepository monthSettingRepository;

    public List<AgreementMonthSettingDto> find(Request requestMonth) {

        List<AgreementMonthSetting> data = monthSettingRepository.find(requestMonth.getEmployeeId());

        return AgreementMonthSettingDto.setData(data);
    }

    public List<EmployeeMonthSettingDto> findAll(RequestEmployee requestMonth) {

        List<AgreementMonthSetting> data = monthSettingRepository.findByListEmployee(requestMonth.getEmployeeIds());

        Map<String, List<AgreementMonthSetting>> mapData = data.stream().collect(Collectors.groupingBy(AgreementMonthSetting::getEmployeeId));

        return EmployeeMonthSettingDto.setData(mapData,requestMonth.getEmployeeIds());
    }
}
