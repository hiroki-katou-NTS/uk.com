package nts.uk.ctx.at.record.app.command.standardtime.classification;

import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.arc.layer.app.command.CommandHandlerWithResult;
import nts.uk.ctx.at.record.dom.standardtime.BasicAgreementSetting;
import nts.uk.ctx.at.record.dom.standardtime.enums.LaborSystemtAtr;
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

		Optional<String> basicSettingId = this.agreementTimeOfClassificationRepository.findEmploymentBasicSettingID(
				companyId, EnumAdaptor.valueOf(command.getLaborSystemAtr(), LaborSystemtAtr.class),
				command.getClassificationCode());

		if (basicSettingId.isPresent()) {
			BasicAgreementSetting basicAgreementSetting = new BasicAgreementSetting(basicSettingId.get(),
					new AlarmWeek(command.getAlarmWeek()), new ErrorWeek(command.getErrorWeek()),
					null, new AlarmTwoWeeks(command.getAlarmTwoWeeks()),
					new ErrorTwoWeeks(command.getErrorTwoWeeks()), null,
					new AlarmFourWeeks(command.getAlarmFourWeeks()), new ErrorFourWeeks(command.getErrorFourWeeks()),
					null, new AlarmOneMonth(command.getAlarmOneMonth()),
					new ErrorOneMonth(command.getErrorOneMonth()), null,
					new AlarmTwoMonths(command.getAlarmTwoMonths()), new ErrorTwoMonths(command.getErrorTwoMonths()),
					null,
					new AlarmThreeMonths(command.getAlarmThreeMonths()),
					new ErrorThreeMonths(command.getErrorThreeMonths()),
					null, new AlarmOneYear(command.getAlarmOneYear()),
					new ErrorOneYear(command.getErrorOneYear()), null);

			return this.agreementTimeOfClassificationDomainService.update(basicAgreementSetting);
		} else {
			return null;
		}
	}

}
