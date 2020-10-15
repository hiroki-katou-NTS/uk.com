package nts.uk.ctx.at.record.app.command.standardtime.employment;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.arc.layer.app.command.CommandHandlerWithResult;
import nts.uk.ctx.at.record.dom.standardtime.repository.AgreementTimeOfEmploymentDomainService;
import nts.uk.ctx.at.record.dom.standardtime.repository.AgreementTimeOfEmploymentRepostitory;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.agreement.management.AgreementTimeOfEmployment;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.agreement.management.enums.LaborSystemtAtr;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.context.LoginUserContext;

/**
 * 
 * @author nampt 雇用 screen update
 *
 */
@Stateless
public class UpdateAgreementTimeOfEmploymentCommandHandler
		extends CommandHandlerWithResult<UpdateAgreementTimeOfEmploymentCommand, List<String>> {

	@Inject
	private AgreementTimeOfEmploymentDomainService agreementTimeOfEmploymentDomainService;

	@Inject
	private AgreementTimeOfEmploymentRepostitory agreementTimeOfEmploymentRepostitory;

	@Override
	protected List<String> handle(CommandHandlerContext<UpdateAgreementTimeOfEmploymentCommand> context) {
		UpdateAgreementTimeOfEmploymentCommand command = context.getCommand();
		LoginUserContext login = AppContexts.user();
		String companyId = login.companyId();

		 Optional<AgreementTimeOfEmployment> agreementTimeOfEmploymentOpt = this.agreementTimeOfEmploymentRepostitory.find(
				companyId, command.getEmploymentCategoryCode(),
				EnumAdaptor.valueOf(command.getLaborSystemAtr(), LaborSystemtAtr.class));

		if (agreementTimeOfEmploymentOpt.isPresent()) {
			AgreementTimeOfEmployment agreementTimeOfEmployment = agreementTimeOfEmploymentOpt.get();
			
			/** TODO: 36協定時間対応により、コメントアウトされた */
			return new ArrayList<>();
//			AgreementTimeOfEmployment newAgreementTimeOfEmployment = new AgreementTimeOfEmployment(companyId, agreementTimeOfEmployment.getBasicSettingId(), 
//					agreementTimeOfEmployment.getLaborSystemAtr(), agreementTimeOfEmployment.getEmploymentCategoryCode(), 
//					new UpperAgreementSetting(new AgreementOneMonthTime(command.getUpperMonth()),
//							new AgreementOneMonthTime(command.getUpperMonthAverage())));
//			
//			BasicAgreementSetting basicAgreementSetting = new BasicAgreementSetting(agreementTimeOfEmployment.getBasicSettingId(),
//					new AlarmWeek(command.getAlarmWeek()), new ErrorWeek(command.getErrorWeek()), new LimitWeek(command.getLimitWeek()),
//					new AlarmTwoWeeks(command.getAlarmTwoWeeks()),new ErrorTwoWeeks(command.getErrorTwoWeeks()), new LimitTwoWeeks(command.getLimitTwoWeeks()),
//					new AlarmFourWeeks(command.getAlarmFourWeeks()), new ErrorFourWeeks(command.getErrorFourWeeks()), new LimitFourWeeks(command.getLimitFourWeeks()),
//					new AlarmOneMonth(command.getAlarmOneMonth()), new ErrorOneMonth(command.getErrorOneMonth()), new AgreementOneMonth(command.getLimitOneMonth()),
//					new AlarmTwoMonths(command.getAlarmTwoMonths()), new ErrorTwoMonths(command.getErrorTwoMonths()), new LimitTwoMonths(command.getLimitTwoMonths()),
//					new AlarmThreeMonths(command.getAlarmThreeMonths()), new ErrorThreeMonths(command.getErrorThreeMonths()), new LimitThreeMonths(command.getLimitThreeMonths()),
//					new AlarmOneYear(command.getAlarmOneYear()), new ErrorOneYear(command.getErrorOneYear()), new AgreementOneYearTime(command.getLimitOneYear()));
//
//			return this.agreementTimeOfEmploymentDomainService.update(basicAgreementSetting, newAgreementTimeOfEmployment);
		} else {
			return Collections.emptyList();
		}
	}

}
