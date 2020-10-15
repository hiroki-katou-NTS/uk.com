package nts.uk.ctx.at.record.app.command.standardtime.employment;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.layer.app.command.CommandHandlerContext;
import nts.arc.layer.app.command.CommandHandlerWithResult;
import nts.gul.text.IdentifierUtil;
import nts.uk.ctx.at.record.dom.standardtime.repository.AgreementTimeOfEmploymentDomainService;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.context.LoginUserContext;

/**
 * 
 * @author nampt 雇用 screen add
 *
 */
@Stateless
public class AddAgreementTimeOfEmploymentCommandHandler extends CommandHandlerWithResult<AddAgreementTimeOfEmploymentCommand, List<String>> {
	
	@Inject
	private AgreementTimeOfEmploymentDomainService agreementTimeDomainService;


	@Override
	protected List<String> handle(CommandHandlerContext<AddAgreementTimeOfEmploymentCommand> context) {
		AddAgreementTimeOfEmploymentCommand command = context.getCommand();
		LoginUserContext login = AppContexts.user();
		String companyId = login.companyId();
		String basicSettingId = IdentifierUtil.randomUniqueId();

		/** TODO: 36協定時間対応により、コメントアウトされた */
		return new ArrayList<>();
//		AgreementTimeOfEmployment agreementTimeOfEmployment =  AgreementTimeOfEmployment.createJavaType(companyId, basicSettingId,
//				command.getLaborSystemAtr(),
//				command.getEmploymentCategoryCode(), command.getUpperMonth(), command.getUpperMonthAverage());
//		
//
//		BasicAgreementSetting basicAgreementSetting = new BasicAgreementSetting(basicSettingId,
//				new AlarmWeek(command.getAlarmWeek()), new ErrorWeek(command.getErrorWeek()), new LimitWeek(command.getLimitWeek()), 
//				new AlarmTwoWeeks(command.getAlarmTwoWeeks()), new ErrorTwoWeeks(command.getErrorTwoWeeks()), new LimitTwoWeeks(command.getLimitTwoWeeks()),
//				new AlarmFourWeeks(command.getAlarmFourWeeks()), new ErrorFourWeeks(command.getErrorFourWeeks()), new LimitFourWeeks(command.getLimitFourWeeks()),
//				new AlarmOneMonth(command.getAlarmOneMonth()), new ErrorOneMonth(command.getErrorOneMonth()), new AgreementOneMonth(command.getLimitOneMonth()),
//				new AlarmTwoMonths(command.getAlarmTwoMonths()), new ErrorTwoMonths(command.getErrorTwoMonths()), new LimitTwoMonths(command.getLimitTwoMonths()),
//				new AlarmThreeMonths(command.getAlarmThreeMonths()), new ErrorThreeMonths(command.getErrorThreeMonths()), new LimitThreeMonths(command.getLimitThreeMonths()),
//				new AlarmOneYear(command.getAlarmOneYear()), new ErrorOneYear(command.getErrorOneYear()), new AgreementOneYearTime(command.getLimitOneYear()));
//		
//		return this.agreementTimeDomainService.add(basicAgreementSetting, agreementTimeOfEmployment);
	}

}
