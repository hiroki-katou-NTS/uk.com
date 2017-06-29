package nts.uk.ctx.at.record.app.command.standardtime.workplace;

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
import nts.uk.ctx.at.record.dom.standardtime.repository.AgreementTimeOfWorkPlaceDomainService;
import nts.uk.ctx.at.record.dom.standardtime.repository.AgreementTimeOfWorkPlaceRepository;

/**
 * 
 * @author nampt 職場 screen update
 *
 */
@Stateless
public class UpdateAgreementTimeOfWorkPlaceCommandHandler
		extends CommandHandlerWithResult<UpdateAgreementTimeOfWorkPlaceCommand, List<String>> {
	
	@Inject
	private AgreementTimeOfWorkPlaceDomainService agreementTimeOfWorkPlaceDomainService;

	@Inject
	private AgreementTimeOfWorkPlaceRepository agreementTimeOfWorkPlaceRepository;

	@Override
	protected List<String> handle(CommandHandlerContext<UpdateAgreementTimeOfWorkPlaceCommand> context) {
		UpdateAgreementTimeOfWorkPlaceCommand command = context.getCommand();

		Optional<String> basicSettingId = this.agreementTimeOfWorkPlaceRepository.find(command.getWorkPlaceId(),
				EnumAdaptor.valueOf(command.getLaborSystemAtr(), LaborSystemtAtr.class));
		
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

			return this.agreementTimeOfWorkPlaceDomainService.update(basicAgreementSetting);
		} else {
			return null;
		}
	}

}
