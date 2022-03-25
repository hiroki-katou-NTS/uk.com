package nts.uk.screen.at.app.query.kdp.kdp002.a;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.shared.app.query.task.GetTaskOperationSettingQuery;
import nts.uk.ctx.at.shared.dom.entranceexit.ManageEntryExit;
import nts.uk.ctx.at.shared.dom.entranceexit.ManageEntryExitRepository;
import nts.uk.ctx.at.shared.dom.scherec.taskmanagement.operationsettings.TaskOperationSetting;
import nts.uk.ctx.at.shared.dom.supportmanagement.supportoperationsetting.SupportOperationSettingRepository;
import nts.uk.ctx.at.shared.dom.workrule.workuse.TemporaryWorkUseManage;
import nts.uk.ctx.at.shared.dom.workrule.workuse.TemporaryWorkUseManageRepository;
import nts.uk.shr.com.context.AppContexts;

/**
 * 打刻入力の共通設定を取得する
 * UKDesign.UniversalK.就業.KDP_打刻.KDP002_打刻入力(個人打刻).A:打刻入力(個人).メニュー別OCD.打刻入力の共通設定を取得する.打刻入力の共通設定を取得する
 * @author chungnt
 *
 */

@Stateless
public class SettingsStampCommon {
	
	@Inject
	private TemporaryWorkUseManageRepository temporaryWorkUseManage;
	
	@Inject
	private GetTaskOperationSettingQuery gettask;
	
	@Inject
	private ManageEntryExitRepository manageEntryExitRepo;
	
	@Inject
	private SupportOperationSettingRepository operationSetting;
	
	
	public SettingsStampCommonDto getSettingCommonStamp() {
		
		SettingsStampCommonDto result = new SettingsStampCommonDto();
		
		String cid = AppContexts.user().companyId();
		
		Optional<TemporaryWorkUseManage> temporaryWorkUseManage = this.temporaryWorkUseManage.findByCid(cid);
		
		if (temporaryWorkUseManage.isPresent()) {
			result.setTemporaryUse(temporaryWorkUseManage.get().getUseClassification().value == 1 ? true : false);
		}
		
		Optional<TaskOperationSetting> taskOperationSetting = gettask.getTasksOperationSetting(cid);
		
		result.setWorkUse(
				taskOperationSetting.map(m -> m.getTaskOperationMethod().value == 1 ? true : false).orElse(false));
		
		result.setSupportUse(true);
		operationSetting.getSupportOperationSetting(cid).ifPresent(os -> {
			result.setSupportUse(os.isUsed());
		});
		
		Optional<ManageEntryExit> manageEntryExitOpt = manageEntryExitRepo.findByID(cid);
		
		result.setEntranceExitUse(manageEntryExitOpt.map(m -> m.getUseClassification().value == 1 ? true : false).orElse(false));
		
		return result;
	}
}
