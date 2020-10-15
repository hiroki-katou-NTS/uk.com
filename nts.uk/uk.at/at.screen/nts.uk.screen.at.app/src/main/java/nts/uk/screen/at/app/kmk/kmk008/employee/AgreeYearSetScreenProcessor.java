package nts.uk.screen.at.app.kmk.kmk008.employee;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.record.dom.standardtime.repository.AgreementYearSettingRepository;
import nts.uk.ctx.at.shared.dom.common.Year;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.agreement.management.exceptsetting.AgreementYearSetting;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 社員の特例設定を取得する（年度）
 */
@Stateless
public class AgreeYearSetScreenProcessor {

    @Inject
    private AgreementYearSettingRepository yearSettingRepository;

    public List<AgreementYearSettingDto> find(Request request) {

        Year year = new Year(GeneralDate.today().addYears(-3).year());
        List<AgreementYearSetting> data = yearSettingRepository.find(request.getEmployeeId());
        data = data.stream().filter(x -> x.getYearValue().v() >= year.v() ).collect(Collectors.toList());

        return AgreementYearSettingDto.setData(data);
    }

    public List<EmployeeYearSettingDto> findAll(RequestEmployee requestMonth) {

        Year year = new Year(GeneralDate.today().addYears(-3).year());
        List<AgreementYearSetting> data = yearSettingRepository.findByListEmployee(requestMonth.getEmployeeIds());
        data = data.stream().filter(x -> x.getYearValue().v() >= year.v() ).collect(Collectors.toList());
        Map<String, List<AgreementYearSetting>> mapData = data.stream().collect(Collectors.groupingBy(AgreementYearSetting::getEmployeeId));

        return EmployeeYearSettingDto.setData(mapData);
    }
}
