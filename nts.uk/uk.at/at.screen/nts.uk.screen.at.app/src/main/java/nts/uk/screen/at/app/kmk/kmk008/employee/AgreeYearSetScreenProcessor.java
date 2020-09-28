package nts.uk.screen.at.app.kmk.kmk008.employee;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.record.dom.standardtime.repository.AgreementYearSettingRepository;
import nts.uk.ctx.at.shared.dom.common.Year;
import nts.uk.ctx.at.shared.dom.standardtime.AgreementYearSetting;
import nts.uk.shr.com.context.AppContexts;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.List;
import java.util.Optional;

/**
 * 社員の特例設定を取得する（年度）
 */
@Stateless
public class AgreeYearSetScreenProcessor {

    @Inject
    private AgreementYearSettingRepository yearSettingRepository;

    public AgreementYearSettingDto find(Request request) {

        Year year = new Year(GeneralDate.today().addYears(-3).year());
        List<AgreementYearSetting> data = yearSettingRepository.find(AppContexts.user().employeeId());
        Optional<AgreementYearSetting> agreementMonthSetting = data.stream().filter(x -> request.getEmployeeIds().contains(x.getEmployeeId())  && x.getYearValue().v() >= year.v() ).findFirst();

        return AgreementYearSettingDto.setData(agreementMonthSetting);
    }
}
