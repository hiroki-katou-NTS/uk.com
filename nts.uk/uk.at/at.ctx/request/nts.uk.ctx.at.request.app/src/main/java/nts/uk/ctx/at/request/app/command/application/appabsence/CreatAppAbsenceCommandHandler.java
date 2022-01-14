package nts.uk.ctx.at.request.app.command.application.appabsence;

import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.arc.layer.app.command.CommandHandlerWithResult;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.request.dom.application.AppReason;
import nts.uk.ctx.at.request.dom.application.Application;
import nts.uk.ctx.at.request.dom.application.ApplicationDate;
import nts.uk.ctx.at.request.dom.application.ApplicationType;
import nts.uk.ctx.at.request.dom.application.IFactoryApplication;
import nts.uk.ctx.at.request.dom.application.PrePostAtr;
import nts.uk.ctx.at.request.dom.application.ReasonForReversion;
import nts.uk.ctx.at.request.dom.application.appabsence.ApplyForLeave;
import nts.uk.ctx.at.request.dom.application.appabsence.service.AbsenceServiceProcess;
import nts.uk.ctx.at.request.dom.application.common.service.newscreen.RegisterAtApproveReflectionInfoService;
import nts.uk.ctx.at.request.dom.application.common.service.newscreen.after.NewAfterRegister;
import nts.uk.ctx.at.request.dom.application.common.service.other.OtherCommonAlgorithm;
import nts.uk.ctx.at.request.dom.application.common.service.other.output.ProcessResult;
import nts.uk.ctx.at.request.dom.application.stamp.StampRequestMode;
import nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.applicationsetting.DisplayReasonRepository;
import nts.uk.ctx.at.request.dom.setting.company.appreasonstandard.AppStandardReasonCode;
import nts.uk.ctx.at.shared.dom.remainingnumber.algorithm.InterimRemainDataMngRegisterDateChange;

@Stateless
public class CreatAppAbsenceCommandHandler extends CommandHandlerWithResult<RegisterAppAbsenceCommand, ProcessResult>{
	
	final static String DATE_FORMAT = "yyyy/MM/dd";
	@Inject
	private IFactoryApplication iFactoryApplication;
	@Inject 
	private AbsenceServiceProcess absenceServiceProcess;
	@Inject
	private NewAfterRegister newAfterRegister;
	@Inject
	private RegisterAtApproveReflectionInfoService registerService;
	@Inject
	private InterimRemainDataMngRegisterDateChange interimRemainDataMngRegisterDateChange;
	@Inject
	private DisplayReasonRepository displayRep;
	@Inject
	private OtherCommonAlgorithm otherCommonAlg;	
	
	/**
	 * 休暇申請（新規）登録処理
	 */
	@Override
	protected ProcessResult handle(CommandHandlerContext<RegisterAppAbsenceCommand> context) {
	    RegisterAppAbsenceCommand command = context.getCommand();
	    ApplyForLeave applyForLeave = command.getApplyForLeave().toDomain();
//	    Application application = command.getApplication()
	    Application application = Application.createFromNew(
                EnumAdaptor.valueOf(command.getApplication().getPrePostAtr(), PrePostAtr.class),
                command.getApplication().getEmployeeID(), EnumAdaptor.valueOf(command.getApplication().getAppType(), ApplicationType.class),
                new ApplicationDate(GeneralDate.fromString(command.getApplication().getAppDate(), "yyyy/MM/dd")),
                command.getApplication().getEnteredPerson(),
                command.getApplication().getOpStampRequestMode() == null ? Optional.empty()
                        : Optional.of(EnumAdaptor.valueOf(command.getApplication().getOpStampRequestMode(),
                                StampRequestMode.class)),
                Optional.of(new ReasonForReversion(command.getApplication().getOpReversionReason())),
                command.getApplication().getOpAppStartDate() == null ? Optional.empty()
                        : Optional.of(new ApplicationDate(
                                GeneralDate.fromString(command.getApplication().getOpAppStartDate(), "yyyy/MM/dd"))),
                        command.getApplication().getOpAppEndDate() == null ? Optional.empty()
                        : Optional.of(new ApplicationDate(
                                GeneralDate.fromString(command.getApplication().getOpAppEndDate(), "yyyy/MM/dd"))),
                Optional.of(new AppReason(command.getApplication().getOpAppReason())),
                command.getApplication().getOpAppStandardReasonCD() == null ? 
                        Optional.empty() : Optional.of(new AppStandardReasonCode(command.getApplication().getOpAppStandardReasonCD())));
	    
	    applyForLeave.setApplication(application);
	    
		return absenceServiceProcess.registerAppAbsence(
		        applyForLeave, 
		        command.getAppDates(), 
		        command.getLeaveComDayOffMana().stream().map(x -> x.toDomain()).collect(Collectors.toList()), 
		        command.getPayoutSubofHDManagements().stream().map(x -> x.toDomain()).collect(Collectors.toList()), 
		        command.isMailServerSet(),
		        command.getApprovalRoot().stream().map(x -> x.toDomain()).collect(Collectors.toList()),
		        command.getApptypeSetting().toDomain(), 
		        command.isHolidayFlg());
	}
}
