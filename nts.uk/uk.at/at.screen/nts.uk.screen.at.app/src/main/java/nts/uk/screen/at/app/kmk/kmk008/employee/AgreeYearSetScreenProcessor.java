package nts.uk.screen.at.app.kmk.kmk008.employee;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.record.dom.standardtime.repository.AgreementYearSettingRepository;
import nts.uk.ctx.at.shared.dom.common.Year;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.agreement.management.exceptsetting.AgreementYearSetting;
import nts.uk.shr.com.context.AppContexts;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.List;
import java.util.Optional;
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
}
