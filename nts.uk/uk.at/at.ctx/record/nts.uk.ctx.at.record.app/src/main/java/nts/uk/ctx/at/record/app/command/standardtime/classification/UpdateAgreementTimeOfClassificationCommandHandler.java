package nts.uk.ctx.at.record.app.command.standardtime.classification;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.arc.layer.app.command.CommandHandlerWithResult;
import nts.uk.ctx.at.record.dom.standardtime.AgreementTimeOfClassification;
import nts.uk.ctx.at.record.dom.standardtime.BasicAgreementSetting;
import nts.uk.ctx.at.record.dom.standardtime.UpperAgreementSetting;
import nts.uk.ctx.at.record.dom.standardtime.enums.LaborSystemtAtr;
import nts.uk.ctx.at.record.dom.standardtime.primitivevalue.AgreementOneMonthTime;
import nts.uk.ctx.at.record.dom.standardtime.primitivevalue.AlarmFourWeeks;
import nts.uk.ctx.at.record.dom.standardtime.primitivevalue.AlarmOneMonth;
import nts.uk.ctx.at.record.dom.standardtime.primitivevalue.AlarmOneYear;
import nts.uk.ctx.at.record.dom.standardtime.primitivevalue.AlarmThreeMonths;
import nts.uk.ctx.at.record.dom.standardtime.primitivevalue.AlarmTwoMonths;
import nts.uk.ctx.at.record.dom.standardtime.primitivevalue.AlarmTwoWeeks;
import nts.uk.ctx.at.record.dom.standardtime.primitivevalue.AlarmWeek;
import nts.uk.ctx.at.record.dom.standardtime.primitivevalue.ErrorFourWeeks;
import nts.uk.ctx.at.record.dom.standardtime.primitivevalue.ErrorOneMonth;
import nts.uk.ctx.at.record.dom.standardtime.primitivevalue.ErrorOneYear;
import nts.uk.ctx.at.record.dom.standardtime.primitivevalue.ErrorThreeMonths;
import nts.uk.ctx.at.record.dom.standardtime.primitivevalue.ErrorTwoMonths;
import nts.uk.ctx.at.record.dom.standardtime.primitivevalue.ErrorTwoWeeks;
import nts.uk.ctx.at.record.dom.standardtime.primitivevalue.ErrorWeek;
import nts.uk.ctx.at.record.dom.standardtime.primitivevalue.LimitFourWeeks;
import nts.uk.ctx.at.record.dom.standardtime.primitivevalue.LimitOneMonth;
import nts.uk.ctx.at.record.dom.standardtime.primitivevalue.LimitOneYear;
import nts.uk.ctx.at.record.dom.standardtime.primitivevalue.LimitThreeMonths;
import nts.uk.ctx.at.record.dom.standardtime.primitivevalue.LimitTwoMonths;
import nts.uk.ctx.at.record.dom.standardtime.primitivevalue.LimitTwoWeeks;
import nts.uk.ctx.at.record.dom.standardtime.primitivevalue.LimitWeek;
import nts.uk.ctx.at.record.dom.standardtime.repository.AgreementTimeOfClassificationDomainService;
import nts.uk.ctx.at.record.dom.standardtime.repository.AgreementTimeOfClassificationRepository;
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
			
			AgreementTimeOfClassification newAgreementTimeOfClassification = new AgreementTimeOfClassification(companyId, 
					agreementTimeOfClassification.getBasicSettingId(), agreementTimeOfClassification.getLaborSystemAtr(), 
					agreementTimeOfClassification.getClassificationCode(), 
					new UpperAgreementSetting(new AgreementOneMonthTime(command.getUpperMonth()),
							new AgreementOneMonthTime(command.getUpperMonthAverage())));
			
			BasicAgreementSetting basicAgreementSetting = new BasicAgreementSetting(agreementTimeOfClassification.getBasicSettingId(),
					new AlarmWeek(command.getAlarmWeek()), new ErrorWeek(command.getErrorWeek()), new LimitWeek(command.getLimitWeek()),
					new AlarmTwoWeeks(command.getAlarmTwoWeeks()),new ErrorTwoWeeks(command.getErrorTwoWeeks()), new LimitTwoWeeks(command.getLimitTwoWeeks()),
					new AlarmFourWeeks(command.getAlarmFourWeeks()), new ErrorFourWeeks(command.getErrorFourWeeks()), new LimitFourWeeks(command.getLimitFourWeeks()),
					new AlarmOneMonth(command.getAlarmOneMonth()), new ErrorOneMonth(command.getErrorOneMonth()), new LimitOneMonth(command.getLimitOneMonth()),
					new AlarmTwoMonths(command.getAlarmTwoMonths()), new ErrorTwoMonths(command.getErrorTwoMonths()), new LimitTwoMonths(command.getLimitTwoMonths()),
					new AlarmThreeMonths(command.getAlarmThreeMonths()), new ErrorThreeMonths(command.getErrorThreeMonths()), new LimitThreeMonths(command.getLimitThreeMonths()),
					new AlarmOneYear(command.getAlarmOneYear()), new ErrorOneYear(command.getErrorOneYear()), new LimitOneYear(command.getLimitOneYear()));

			return this.agreementTimeOfClassificationDomainService.update(basicAgreementSetting, newAgreementTimeOfClassification);
		} else {
			return Collections.emptyList();
		}
	}

}
