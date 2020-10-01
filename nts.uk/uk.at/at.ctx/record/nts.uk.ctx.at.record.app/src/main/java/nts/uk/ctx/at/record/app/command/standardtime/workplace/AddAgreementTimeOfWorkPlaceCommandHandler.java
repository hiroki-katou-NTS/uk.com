package nts.uk.ctx.at.record.app.command.standardtime.workplace;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.layer.app.command.CommandHandlerContext;
import nts.arc.layer.app.command.CommandHandlerWithResult;
import nts.gul.text.IdentifierUtil;
import nts.uk.ctx.at.record.dom.standardtime.repository.AgreementTimeOfWorkPlaceDomainService;

/**
 * 
 * @author nampt 職場 screen add
 *
 */
@Stateless
public class AddAgreementTimeOfWorkPlaceCommandHandler extends CommandHandlerWithResult<AddAgreementTimeOfWorkPlaceCommand, List<String>> {

	@Inject
	private AgreementTimeOfWorkPlaceDomainService agreementTimeOfWorkPlaceDomainService;

	@Override
	protected List<String> handle(CommandHandlerContext<AddAgreementTimeOfWorkPlaceCommand> context) {
		AddAgreementTimeOfWorkPlaceCommand command = context.getCommand();
		String basicSettingId = IdentifierUtil.randomUniqueId();

		/** TODO: 36協定時間対応により、コメントアウトされた */
		return new ArrayList<>();
//		AgreementTimeOfWorkPlace agreementTimeOfWorkPlace = AgreementTimeOfWorkPlace.createJavaType(command.getWorkPlaceId(),
//				basicSettingId, command.getLaborSystemAtr(), command.getUpperMonth(), command.getUpperMonthAverage());
//
//		BasicAgreementSetting basicAgreementSetting = new BasicAgreementSetting(basicSettingId,
//				new AlarmWeek(command.getAlarmWeek()), new ErrorWeek(command.getErrorWeek()), new LimitWeek(command.getLimitWeek()), 
//				new AlarmTwoWeeks(command.getAlarmTwoWeeks()), new ErrorTwoWeeks(command.getErrorTwoWeeks()), new LimitTwoWeeks(command.getLimitTwoWeeks()),
//				new AlarmFourWeeks(command.getAlarmFourWeeks()), new ErrorFourWeeks(command.getErrorFourWeeks()), new LimitFourWeeks(command.getLimitFourWeeks()),
//				new AlarmOneMonth(command.getAlarmOneMonth()), new ErrorOneMonth(command.getErrorOneMonth()), new AgreementOneMonth(command.getLimitOneMonth()),
//				new AlarmTwoMonths(command.getAlarmTwoMonths()), new ErrorTwoMonths(command.getErrorTwoMonths()), new LimitTwoMonths(command.getLimitTwoMonths()),
//				new AlarmThreeMonths(command.getAlarmThreeMonths()), new ErrorThreeMonths(command.getErrorThreeMonths()), new LimitThreeMonths(command.getLimitThreeMonths()),
//				new AlarmOneYear(command.getAlarmOneYear()), new ErrorOneYear(command.getErrorOneYear()), new AgreementOneYearTime(command.getLimitOneYear()));
		
//		return this.agreementTimeOfWorkPlaceDomainService.add(agreementTimeOfWorkPlace, basicAgreementSetting);
	}

}
