package nts.uk.screen.at.app.kmk.kmk008.employee;

import nts.arc.time.GeneralDate;
import nts.arc.time.YearMonth;
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

        YearMonth yearMonth = YearMonth.of(GeneralDate.today().addYears(-3).year(),GeneralDate.today().month());
        List<AgreementMonthSetting> data = monthSettingRepository.find(requestMonth.getEmployeeId());
        data = data.stream().filter(x ->  x.getYearMonthValue().v() >= yearMonth.v() ).collect(Collectors.toList());

        return AgreementMonthSettingDto.setData(data);
    }

    public List<EmployeeMonthSettingDto> findAll(RequestEmployee requestMonth) {

        YearMonth yearMonth = YearMonth.of(GeneralDate.today().addYears(-3).year(),GeneralDate.today().month());
        List<AgreementMonthSetting> data = monthSettingRepository.findByListEmployee(requestMonth.getEmployeeIds());
        data = data.stream().filter(x ->  x.getYearMonthValue().v() >= yearMonth.v() ).collect(Collectors.toList());
        Map<String, List<AgreementMonthSetting>> mapData = data.stream().collect(Collectors.groupingBy(AgreementMonthSetting::getEmployeeId));

        return EmployeeMonthSettingDto.setData(mapData,requestMonth.getEmployeeIds());
    }
}
