package nts.uk.ctx.at.request.app.command.application.stamp;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.apache.logging.log4j.util.Strings;

import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.arc.layer.app.command.CommandHandlerWithResult;
import nts.uk.ctx.at.request.app.command.application.stamp.command.AppStampCmd;
import nts.uk.ctx.at.request.dom.application.AppReason;
import nts.uk.ctx.at.request.dom.application.ApplicationRepository_New;
import nts.uk.ctx.at.request.dom.application.Application_New;
import nts.uk.ctx.at.request.dom.application.stamp.AppStamp;
import nts.uk.ctx.at.request.dom.application.stamp.AppStampAtr;
import nts.uk.ctx.at.request.dom.application.stamp.AppStampCancel;
import nts.uk.ctx.at.request.dom.application.stamp.AppStampCombinationAtr;
import nts.uk.ctx.at.request.dom.application.stamp.AppStampDetailDomainService;
import nts.uk.ctx.at.request.dom.application.stamp.AppStampGoOutAtr;
import nts.uk.ctx.at.request.dom.application.stamp.AppStampGoOutPermit;
import nts.uk.ctx.at.request.dom.application.stamp.AppStampOnlineRecord;
import nts.uk.ctx.at.request.dom.application.stamp.AppStampWork;
import nts.uk.ctx.at.request.dom.application.stamp.StampRequestMode;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.time.TimeWithDayAttr;
/**
 * 
 * @author Doan Duy Hung
 *
 */
@Stateless
public class UpdateAppStampCommandHandler extends CommandHandlerWithResult<AppStampCmd, List<String>>{
	
	private static final String DATE_FORMAT = "yyyy/MM/dd";
	
	@Inject
	private AppStampDetailDomainService applicationStampDetailDomainService;
	
	@Inject
	private ApplicationRepository_New applicationRepository;
	
	@Override
	protected List<String> handle(CommandHandlerContext<AppStampCmd> context) {
		String companyID = AppContexts.user().companyId();
		AppStampCmd appStampCmd = context.getCommand();
		String applicationReason = "";
		if(!appStampCmd.getTitleReason().isEmpty() || !appStampCmd.getDetailReason().isEmpty()) {
			applicationReason = !appStampCmd.getTitleReason().isEmpty()? appStampCmd.getTitleReason() + System.lineSeparator() + appStampCmd.getDetailReason() : appStampCmd.getDetailReason();
		}
		StampRequestMode stampRequestMode = EnumAdaptor.valueOf(appStampCmd.getStampRequestMode(), StampRequestMode.class);
		Application_New application = applicationRepository.findByID(companyID, appStampCmd.getAppID()).get();
		application.setAppReason(new AppReason(applicationReason));
		List<AppStampGoOutPermit> appStampGoOutPermits = Collections.emptyList();
		List<AppStampWork> appStampWorks = Collections.emptyList();
		List<AppStampCancel> appStampCancels = Collections.emptyList();
		Optional<AppStampOnlineRecord> appStampOnlineRecord = Optional.empty();
		switch (stampRequestMode) {
		case STAMP_GO_OUT_PERMIT:
			appStampGoOutPermits = appStampCmd.getAppStampGoOutPermitCmds()
			.stream()
			.filter(x -> x.getStartTime()!=null||x.getEndTime()!=null||Strings.isNotBlank(x.getStartLocation()))
			.map(x -> new AppStampGoOutPermit(
							EnumAdaptor.valueOf(x.getStampAtr(), AppStampAtr.class), 
							x.getStampFrameNo(), 
							EnumAdaptor.valueOf(x.getStampGoOutAtr(), AppStampGoOutAtr.class), 
							Optional.ofNullable(x.getStartTime()).map(p -> new TimeWithDayAttr(p)), 
							Optional.ofNullable(x.getStartLocation()), 
							Optional.ofNullable(x.getEndTime()).map(p -> new TimeWithDayAttr(p)), 
							Optional.ofNullable(x.getEndLocation())))
			.collect(Collectors.toList());
			break;
		case STAMP_WORK:
			appStampWorks = appStampCmd.getAppStampWorkCmds()
			.stream()
			.filter(x -> x.getStartTime()!=null||x.getEndTime()!=null||Strings.isNotBlank(x.getStartLocation()))
			.map(
					x -> new AppStampWork(
							EnumAdaptor.valueOf(x.getStampAtr(), AppStampAtr.class), 
							x.getStampFrameNo(), 
							EnumAdaptor.valueOf(x.getStampGoOutAtr(), AppStampGoOutAtr.class), 
							Optional.ofNullable(x.getSupportCard()), 
							Optional.ofNullable(x.getSupportLocationCD()), 
							Optional.ofNullable(x.getStartTime()).map(p -> new TimeWithDayAttr(p)), 
							Optional.ofNullable(x.getStartLocation()), 
							Optional.ofNullable(x.getEndTime()).map(p -> new TimeWithDayAttr(p)), 
							Optional.ofNullable(x.getEndLocation())))
			.collect(Collectors.toList());
			break;
		case STAMP_CANCEL:
			appStampCancels = appStampCmd.getAppStampCancelCmds().stream().map(
					x -> new AppStampCancel(
							EnumAdaptor.valueOf(x.getStampAtr(), AppStampAtr.class), 
							x.getStampFrameNo(), 
							x.getCancelAtr())	
				).collect(Collectors.toList());
			break;
		case STAMP_ONLINE_RECORD:
			appStampOnlineRecord = Optional.of(new AppStampOnlineRecord(
					EnumAdaptor.valueOf(appStampCmd.getAppStampOnlineRecordCmd().getStampCombinationAtr(), AppStampCombinationAtr.class),
					appStampCmd.getAppStampOnlineRecordCmd().getAppTime()));
			break;
		case OTHER:
			appStampWorks = appStampCmd.getAppStampWorkCmds()
			.stream()
			.filter(x -> x.getStartTime()!=null||x.getEndTime()!=null||Strings.isNotBlank(x.getStartLocation()))
			.map(
					x -> new AppStampWork(
							EnumAdaptor.valueOf(x.getStampAtr(), AppStampAtr.class), 
							x.getStampFrameNo(), 
							EnumAdaptor.valueOf(x.getStampGoOutAtr(), AppStampGoOutAtr.class), 
							Optional.ofNullable(x.getSupportCard()), 
							Optional.ofNullable(x.getSupportLocationCD()), 
							Optional.ofNullable(x.getStartTime()).map(p -> new TimeWithDayAttr(p)), 
							Optional.ofNullable(x.getStartLocation()), 
							Optional.ofNullable(x.getEndTime()).map(p -> new TimeWithDayAttr(p)), 
							Optional.ofNullable(x.getEndLocation())))
			.collect(Collectors.toList());
			break;
		default:
			break;
		}
		AppStamp appStamp = new AppStamp(
				stampRequestMode, 
				application, 
				appStampGoOutPermits, 
				appStampWorks, 
				appStampCancels, 
				appStampOnlineRecord);
		appStamp.setVersion(appStampCmd.getVersion());
		return applicationStampDetailDomainService.appStampUpdate(applicationReason, appStamp);
	}
}
