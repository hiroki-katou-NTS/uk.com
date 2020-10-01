package nts.uk.ctx.at.record.app.command.standardtime.company;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.arc.layer.app.command.CommandHandlerWithResult;
import nts.uk.ctx.at.record.dom.standardtime.repository.AgreementTimeCompanyRepository;
import nts.uk.ctx.at.record.dom.standardtime.repository.AgreementTimeOfCompanyDomainService;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.agreement.management.AgreementTimeOfCompany;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.agreement.management.enums.LaborSystemtAtr;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.context.LoginUserContext;

/**
 * 
 * @author nampt 全社 screen update
 *
 */
@Stateless
public class UpdateAgreementTimeOfCompanyCommandHandler
		extends CommandHandlerWithResult<UpdateAgreementTimeOfCompanyCommand, List<String>> {

	@Inject
	private AgreementTimeOfCompanyDomainService agreementTimeOfCompanyDomainService;

	@Inject
	private AgreementTimeCompanyRepository agreementTimeCompanyRepository;

	@Override
	protected List<String> handle(CommandHandlerContext<UpdateAgreementTimeOfCompanyCommand> context) {
		UpdateAgreementTimeOfCompanyCommand command = context.getCommand();
		LoginUserContext login = AppContexts.user();
		String companyId = login.companyId();

		Optional<AgreementTimeOfCompany> agreementTimeOfCompanyOpt = this.agreementTimeCompanyRepository.find(companyId,
				EnumAdaptor.valueOf(command.getLaborSystemAtr(), LaborSystemtAtr.class));

		if (agreementTimeOfCompanyOpt.isPresent()) {
			AgreementTimeOfCompany agreementTimeOfCompany = agreementTimeOfCompanyOpt.get();
			
			/** TODO: 36協定時間対応により、コメントアウトされた */
			return new ArrayList<>();
//			AgreementTimeOfCompany newAgreementTimeOfCompany = new AgreementTimeOfCompany(companyId, agreementTimeOfCompany.getBasicSettingId(), 
//					agreementTimeOfCompany.getLaborSystemAtr(), new UpperAgreementSetting(new AgreementOneMonthTime(command.getUpperMonth()),
//							new AgreementOneMonthTime(command.getUpperMonthAverage())));
//			
//			BasicAgreementSetting basicAgreementSetting = new BasicAgreementSetting(agreementTimeOfCompany.getBasicSettingId(),
//					new AlarmWeek(command.getAlarmWeek()), new ErrorWeek(command.getErrorWeek()), new LimitWeek(command.getLimitWeek()),
//					new AlarmTwoWeeks(command.getAlarmTwoWeeks()), new ErrorTwoWeeks(command.getErrorTwoWeeks()),new LimitTwoWeeks(command.getLimitTwoWeeks()),
//					new AlarmFourWeeks(command.getAlarmFourWeeks()),new ErrorFourWeeks(command.getErrorFourWeeks()), new LimitFourWeeks(command.getLimitFourWeeks()),
//					new AlarmOneMonth(command.getAlarmOneMonth()), new ErrorOneMonth(command.getErrorOneMonth()), new AgreementOneMonth(command.getLimitOneMonth()), 
//					new AlarmTwoMonths(command.getAlarmTwoMonths()), new ErrorTwoMonths(command.getErrorTwoMonths()), new LimitTwoMonths(command.getLimitTwoMonths()),
//					new AlarmThreeMonths(command.getAlarmThreeMonths()), new ErrorThreeMonths(command.getErrorThreeMonths()), new LimitThreeMonths(command.getLimitThreeMonths()), 
//					new AlarmOneYear(command.getAlarmOneYear()), new ErrorOneYear(command.getErrorOneYear()), new AgreementOneYearTime(command.getLimitOneYear()));
//
//			return this.agreementTimeOfCompanyDomainService.update(basicAgreementSetting, newAgreementTimeOfCompany);
		} else {
			return Collections.emptyList();
		}
	}
}
