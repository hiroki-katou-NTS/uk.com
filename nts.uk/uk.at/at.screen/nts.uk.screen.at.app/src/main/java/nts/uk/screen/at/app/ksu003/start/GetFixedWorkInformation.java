package nts.uk.screen.at.app.ksu003.start;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import lombok.Value;
import nts.uk.ctx.at.request.app.find.application.gobackdirectly.WorkInformationDto;
import nts.uk.ctx.at.shared.dom.WorkInformation;
import nts.uk.ctx.at.shared.dom.workrule.BreakTimeZone;
import nts.uk.ctx.at.shared.dom.worktime.flexset.CoreTimeSetting;
import nts.uk.ctx.at.shared.dom.worktime.service.WorkTimeForm;
import nts.uk.ctx.at.shared.dom.worktime.worktimeset.WorkTimeSetting;
import nts.uk.ctx.at.shared.dom.worktime.worktimeset.WorkTimeSettingRepository;
import nts.uk.ctx.at.shared.dom.worktype.WorkType;
import nts.uk.ctx.at.shared.dom.worktype.WorkTypeRepository;
import nts.uk.screen.at.app.ksu003.start.dto.FixedWorkInformationDto;
import nts.uk.shr.com.context.AppContexts;

/**
 * 勤務固定情報を取得する
 * UKDesign.UniversalK.就業.KSU_スケジュール.KSU003_個人スケジュール修正(日付別).A：個人スケジュール修正(日付別).メニュー別OCD.勤務固定情報を取得する
 * @author phongtq
 *
 */
@Stateless
public class GetFixedWorkInformation {
	
	@Inject
	private WorkTypeRepository workTypeRepository;
	
	@Inject
	private WorkTimeSettingRepository timeSettingRepository;
	
	public FixedWorkInformationDto getFixedWorkInfo(WorkInformationDto information) {
		FixedWorkInformationDto fixedWorkInforDto = null;
		String cid = AppContexts.user().companyId();
		
		// 1 変更可能な勤務時間帯を取得する(Require):List<勤務NOごとの変更可能な勤務時間帯>
		// 23 Nhật mới giao - TQP
		List<Integer> lstNo = new ArrayList<>();
		
		// 2
		Optional<WorkType> type = workTypeRepository.findByPK(cid, information.getWorkType());
		
		// 3
		if(!lstNo.isEmpty()) {
			// 3.1 休憩時間帯を取得する(Require):Optional<休憩時間>
			// Đợi bên nhật TQP - goi ham trong class WorkInformation
			Optional<BreakTimeZone> brkTime = Optional.empty();
			
			// 3.2 get(ログイン会社ID、取得した就業時間帯コード):就業時間帯の設定
			Optional<WorkTimeSetting> worktimeSet = timeSettingRepository.findByCode(cid, information.getWorkTime());
			
			// 3.3 cần QA
			WorkTimeForm timeForm = WorkTimeForm.FIXED;
			
			// 3.4 Cần QA
			CoreTimeSetting coreTimeSetting = null;
		}
		
		
		
		return fixedWorkInforDto;
	}

}
