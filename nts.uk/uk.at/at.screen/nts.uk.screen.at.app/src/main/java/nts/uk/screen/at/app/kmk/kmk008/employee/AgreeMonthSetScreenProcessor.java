package nts.uk.screen.at.app.kmk.kmk008.employee;

import nts.arc.time.GeneralDate;
import nts.arc.time.YearMonth;
import nts.uk.ctx.at.record.dom.standardtime.repository.AgreementMonthSettingRepository;
import nts.uk.ctx.at.shared.dom.standardtime.AgreementMonthSetting;
import nts.uk.shr.com.context.AppContexts;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.List;
import java.util.Optional;

/**
 * 社員の特例設定を取得する（年月）
 */
@Stateless
public class AgreeMonthSetScreenProcessor {

    @Inject
    private AgreementMonthSettingRepository monthSettingRepository;

    public AgreementMonthSettingDto find(Request requestMonth) {

        YearMonth yearMonth = YearMonth.of(GeneralDate.today().addYears(-3).year(),GeneralDate.today().month());
        List<AgreementMonthSetting> data = monthSettingRepository.find(AppContexts.user().employeeId());
        Optional<AgreementMonthSetting> agreementMonthSetting = data.stream().filter(x -> requestMonth.getEmployeeIds().contains(x.getEmployeeId())  && x.getYearMonthValue().v() >= yearMonth.v() ).findFirst();

        return AgreementMonthSettingDto.setData(agreementMonthSetting);
    }
}
