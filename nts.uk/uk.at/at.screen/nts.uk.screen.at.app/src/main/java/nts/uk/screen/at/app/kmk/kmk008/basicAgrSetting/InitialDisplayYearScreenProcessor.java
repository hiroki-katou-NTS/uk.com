package nts.uk.screen.at.app.kmk.kmk008.basicAgrSetting;

import nts.arc.time.GeneralDate;
import nts.arc.time.YearMonth;
import nts.arc.time.calendar.Year;
import nts.uk.ctx.at.record.dom.require.RecordDomRequireService;
import nts.uk.ctx.at.record.dom.standardtime.repository.AgreementDomainService;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.agreement.management.timesetting.BasicAgreementSetting;
import nts.uk.shr.com.context.AppContexts;

import javax.ejb.Stateless;
import javax.inject.Inject;

/**
 * 初期表示を行う（年度）
 */
@Stateless
public class InitialDisplayYearScreenProcessor {

    @Inject
    private RecordDomRequireService requireService;

    public BasicAgrYearSettingDto findYear(RequestParam request) {

        BasicAgreementSetting data = AgreementDomainService.getBasicSet(requireService.createRequire(),AppContexts.user().companyId(),request.getEmployeeId(),GeneralDate.today(), new Year(GeneralDate.today().year()));

        return BasicAgrYearSettingDto.setData(data);
    }

    public BasicAgrYearMonthSettingDto findYearMonth(RequestParam request) {

        BasicAgreementSetting data = AgreementDomainService.getBasicSet(requireService.createRequire(),AppContexts.user().companyId(),request.getEmployeeId(), GeneralDate.today(), GeneralDate.today().yearMonth());

        return BasicAgrYearMonthSettingDto.setData(data);
    }
}
