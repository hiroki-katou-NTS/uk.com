package nts.uk.ctx.at.record.app.command.standardtime.classification;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.arc.layer.app.command.CommandHandlerWithResult;
import nts.uk.ctx.at.record.dom.standardtime.repository.AgreementTimeOfClassificationDomainService;
import nts.uk.ctx.at.record.dom.standardtime.repository.AgreementTimeOfClassificationRepository;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.agreement.management.AgreementTimeOfClassification;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.agreement.management.enums.LaborSystemtAtr;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.context.LoginUserContext;

/**
 * 
 * @author nampt 分類 screen update
 *
 */
@Stateless
public class UpdateAgreementTimeOfClassificationCommandHandler
		extends CommandHandlerWithResult<UpdateAgreementTimeOfClassificationCommand, List<String>> {

	@Inject
	private AgreementTimeOfClassificationDomainService agreementTimeOfClassificationDomainService;

	@Inject
	private AgreementTimeOfClassificationRepository agreementTimeOfClassificationRepository;

	@Override
	protected List<String> handle(CommandHandlerContext<UpdateAgreementTimeOfClassificationCommand> context) {
		UpdateAgreementTimeOfClassificationCommand command = context.getCommand();
		LoginUserContext login = AppContexts.user();
		String companyId = login.companyId();

		 Optional<AgreementTimeOfClassification> agreementTimeOfClassificationOpt = this.agreementTimeOfClassificationRepository.find(companyId, 
				EnumAdaptor.valueOf(command.getLaborSystemAtr(), LaborSystemtAtr.class),
				command.getClassificationCode());

		if (agreementTimeOfClassificationOpt.isPresent()) {
			AgreementTimeOfClassification agreementTimeOfClassification = agreementTimeOfClassificationOpt.get();
			
			/** TODO: 36協定時間対応により、コメントアウトされた */
			return new ArrayList<>();
//			AgreementTimeOfClassification newAgreementTimeOfClassification = new AgreementTimeOfClassification(companyId, 
//					agreementTimeOfClassification.getBasicSettingId(), agreementTimeOfClassification.getLaborSystemAtr(), 
//					agreementTimeOfClassification.getClassificationCode(), 
//					new UpperAgreementSetting(new AgreementOneMonthTime(command.getUpperMonth()),
//							new AgreementOneMonthTime(command.getUpperMonthAverage())));
//			
//			BasicAgreementSetting basicAgreementSetting = new BasicAgreementSetting(agreementTimeOfClassification.getBasicSettingId(),
//					new AlarmWeek(command.getAlarmWeek()), new ErrorWeek(command.getErrorWeek()), new LimitWeek(command.getLimitWeek()),
//					new AlarmTwoWeeks(command.getAlarmTwoWeeks()),new ErrorTwoWeeks(command.getErrorTwoWeeks()), new LimitTwoWeeks(command.getLimitTwoWeeks()),
//					new AlarmFourWeeks(command.getAlarmFourWeeks()), new ErrorFourWeeks(command.getErrorFourWeeks()), new LimitFourWeeks(command.getLimitFourWeeks()),
//					new AlarmOneMonth(command.getAlarmOneMonth()), new ErrorOneMonth(command.getErrorOneMonth()), new AgreementOneMonth(command.getLimitOneMonth()),
//					new AlarmTwoMonths(command.getAlarmTwoMonths()), new ErrorTwoMonths(command.getErrorTwoMonths()), new LimitTwoMonths(command.getLimitTwoMonths()),
//					new AlarmThreeMonths(command.getAlarmThreeMonths()), new ErrorThreeMonths(command.getErrorThreeMonths()), new LimitThreeMonths(command.getLimitThreeMonths()),
//					new AlarmOneYear(command.getAlarmOneYear()), new ErrorOneYear(command.getErrorOneYear()), new AgreementOneYearTime(command.getLimitOneYear()));
//
//			return this.agreementTimeOfClassificationDomainService.update(basicAgreementSetting, newAgreementTimeOfClassification);
		} else {
			return Collections.emptyList();
		}
	}

}
