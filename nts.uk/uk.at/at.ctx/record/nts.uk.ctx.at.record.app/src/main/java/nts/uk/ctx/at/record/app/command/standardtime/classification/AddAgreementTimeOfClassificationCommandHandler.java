package nts.uk.ctx.at.record.app.command.standardtime.classification;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.arc.layer.app.command.CommandHandlerWithResult;
import nts.gul.text.IdentifierUtil;
import nts.uk.ctx.at.record.dom.standardtime.repository.AgreementTimeOfClassificationDomainService;
import nts.uk.ctx.at.shared.dom.monthly.agreement.management.AgreementTimeOfClassification;
import nts.uk.ctx.at.shared.dom.monthly.agreement.management.UpperAgreementSetting;
import nts.uk.ctx.at.shared.dom.monthly.agreement.management.enums.LaborSystemtAtr;
import nts.uk.ctx.at.shared.dom.monthly.agreement.management.onemonth.AgreementOneMonth;
import nts.uk.ctx.at.shared.dom.monthly.agreement.management.oneyear.AgreementOneYearTime;
import nts.uk.ctx.at.shared.dom.monthly.agreement.management.primitivevalue.AgreementOneMonthTime;
import nts.uk.ctx.at.shared.dom.monthly.agreement.management.primitivevalue.AlarmFourWeeks;
import nts.uk.ctx.at.shared.dom.monthly.agreement.management.primitivevalue.AlarmOneMonth;
import nts.uk.ctx.at.shared.dom.monthly.agreement.management.primitivevalue.AlarmOneYear;
import nts.uk.ctx.at.shared.dom.monthly.agreement.management.primitivevalue.AlarmThreeMonths;
import nts.uk.ctx.at.shared.dom.monthly.agreement.management.primitivevalue.AlarmTwoMonths;
import nts.uk.ctx.at.shared.dom.monthly.agreement.management.primitivevalue.AlarmTwoWeeks;
import nts.uk.ctx.at.shared.dom.monthly.agreement.management.primitivevalue.AlarmWeek;
import nts.uk.ctx.at.shared.dom.monthly.agreement.management.primitivevalue.ErrorFourWeeks;
import nts.uk.ctx.at.shared.dom.monthly.agreement.management.primitivevalue.ErrorOneMonth;
import nts.uk.ctx.at.shared.dom.monthly.agreement.management.primitivevalue.ErrorOneYear;
import nts.uk.ctx.at.shared.dom.monthly.agreement.management.primitivevalue.ErrorThreeMonths;
import nts.uk.ctx.at.shared.dom.monthly.agreement.management.primitivevalue.ErrorTwoMonths;
import nts.uk.ctx.at.shared.dom.monthly.agreement.management.primitivevalue.ErrorTwoWeeks;
import nts.uk.ctx.at.shared.dom.monthly.agreement.management.primitivevalue.ErrorWeek;
import nts.uk.ctx.at.shared.dom.monthly.agreement.management.primitivevalue.LimitFourWeeks;
import nts.uk.ctx.at.shared.dom.monthly.agreement.management.primitivevalue.LimitThreeMonths;
import nts.uk.ctx.at.shared.dom.monthly.agreement.management.primitivevalue.LimitTwoMonths;
import nts.uk.ctx.at.shared.dom.monthly.agreement.management.primitivevalue.LimitTwoWeeks;
import nts.uk.ctx.at.shared.dom.monthly.agreement.management.primitivevalue.LimitWeek;
import nts.uk.ctx.at.shared.dom.monthly.agreement.management.timesetting.BasicAgreementSetting;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.context.LoginUserContext;

/**
 * 
 * @author nampt 分類 screen add
 *
 */
@Stateless
public class AddAgreementTimeOfClassificationCommandHandler
		extends CommandHandlerWithResult<AddAgreementTimeOfClassificationCommand, List<String>> {
	
	@Inject
	private AgreementTimeOfClassificationDomainService agreementTimeOfClassificationDomainService;


	@Override
	protected List<String> handle(CommandHandlerContext<AddAgreementTimeOfClassificationCommand> context) {
		AddAgreementTimeOfClassificationCommand command = context.getCommand();
		LoginUserContext login = AppContexts.user();
		String companyId = login.companyId();
		String basicSettingId = IdentifierUtil.randomUniqueId();

		AgreementTimeOfClassification agreementTimeOfClassification = new AgreementTimeOfClassification(companyId,
				basicSettingId, EnumAdaptor.valueOf(command.getLaborSystemAtr(), LaborSystemtAtr.class),
				command.getClassificationCode(),
				new UpperAgreementSetting(new AgreementOneMonthTime(command.getUpperMonth()),
						new AgreementOneMonthTime(command.getUpperMonthAverage())));
		
		BasicAgreementSetting basicAgreementSetting = new BasicAgreementSetting(basicSettingId,
				new AlarmWeek(command.getAlarmWeek()), new ErrorWeek(command.getErrorWeek()), new LimitWeek(command.getLimitWeek()), 
				new AlarmTwoWeeks(command.getAlarmTwoWeeks()), new ErrorTwoWeeks(command.getErrorTwoWeeks()), new LimitTwoWeeks(command.getLimitTwoWeeks()),
				new AlarmFourWeeks(command.getAlarmFourWeeks()), new ErrorFourWeeks(command.getErrorFourWeeks()), new LimitFourWeeks(command.getLimitFourWeeks()),
				new AlarmOneMonth(command.getAlarmOneMonth()), new ErrorOneMonth(command.getErrorOneMonth()), new AgreementOneMonth(command.getLimitOneMonth()),
				new AlarmTwoMonths(command.getAlarmTwoMonths()), new ErrorTwoMonths(command.getErrorTwoMonths()), new LimitTwoMonths(command.getLimitTwoMonths()),
				new AlarmThreeMonths(command.getAlarmThreeMonths()), new ErrorThreeMonths(command.getErrorThreeMonths()), new LimitThreeMonths(command.getLimitThreeMonths()),
				new AlarmOneYear(command.getAlarmOneYear()), new ErrorOneYear(command.getErrorOneYear()), new AgreementOneYearTime(command.getLimitOneYear()));
		
		return this.agreementTimeOfClassificationDomainService.add(agreementTimeOfClassification, basicAgreementSetting);
	}

}
