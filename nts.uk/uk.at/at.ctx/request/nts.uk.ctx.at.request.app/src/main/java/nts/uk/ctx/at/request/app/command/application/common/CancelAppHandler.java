package nts.uk.ctx.at.request.app.command.application.common;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;

import nts.arc.layer.app.command.CommandHandlerContext;
import nts.arc.layer.app.command.CommandHandlerWithResult;
import nts.uk.ctx.at.request.app.find.application.common.AppDispInfoStartupDto;
import nts.uk.ctx.at.request.dom.application.Application;
import nts.uk.ctx.at.request.dom.application.ApplicationRepository;
import nts.uk.ctx.at.request.dom.application.ApplicationType;
import nts.uk.ctx.at.request.dom.application.common.service.detailscreen.ProcessCancel;
import nts.uk.ctx.at.request.dom.application.common.service.setting.output.AppDispInfoStartupOutput;
import nts.uk.ctx.at.request.dom.application.holidayshipment.compltleavesimmng.AppHdsubRec;
import nts.uk.ctx.at.request.dom.application.holidayshipment.compltleavesimmng.AppHdsubRecRepository;
@Stateless
@Transactional
public class CancelAppHandler extends CommandHandlerWithResult<AppDispInfoStartupDto, Boolean> {
	
	@Inject
	private ProcessCancel processCancelRepo;
	
	@Inject
	private AppHdsubRecRepository appHdsubRecRepository;
	
	@Inject
	private ApplicationRepository applicationRepository;

	@Override
	protected Boolean handle(CommandHandlerContext<AppDispInfoStartupDto> context) {
		AppDispInfoStartupOutput appDispInfoStartupOutput = context.getCommand().toDomain();
		Application application = appDispInfoStartupOutput.getAppDetailScreenInfo().get().getApplication();
		List<Application> appLst = new ArrayList<>();
        appLst.add(application);
        if(application.getAppType()==ApplicationType.COMPLEMENT_LEAVE_APPLICATION) {
        	Optional<AppHdsubRec> appHdsubRec = appHdsubRecRepository.findByAppId(application.getAppID());
			if(appHdsubRec.isPresent()) {
				if(appHdsubRec.get().getRecAppID().equals(application.getAppID())) {
					applicationRepository.findByID(appHdsubRec.get().getAbsenceLeaveAppID()).ifPresent(x -> appLst.add(x));
				} else {
					applicationRepository.findByID(appHdsubRec.get().getRecAppID()).ifPresent(x -> appLst.add(x));
				}
			}
        }
		//1 : 排他チェック,
		// detailBeforeUpdate.exclusiveCheck(companyID, application.getAppID(), application.getVersion());
		// 共通アルゴリズム「詳細画面取消の処理」を実行する(thực hiện xử lý 「詳細画面取消の処理」)
		return processCancelRepo.detailScreenCancelProcess(appLst);
	}

}
