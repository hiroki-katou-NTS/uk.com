package nts.uk.ctx.at.shared.app.find.workrule.shiftmaster;

import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.i18n.I18NText;
import nts.uk.ctx.at.shared.dom.workrule.shiftmaster.ShiftMasterRepository;
import nts.uk.ctx.at.shared.dom.workrule.shiftmaster.dto.ShiftMasterDto;
import nts.uk.ctx.at.shared.dom.worktime.worktimeset.WorkTimeSetting;
import nts.uk.ctx.at.shared.dom.worktime.worktimeset.WorkTimeSettingRepository;
import nts.uk.ctx.at.shared.dom.worktype.WorkType;
import nts.uk.ctx.at.shared.dom.worktype.WorkTypeRepository;
import nts.uk.shr.com.context.AppContexts;

/**
 * シフトマスタの一覧を取得する
 * 
 * @author anhdt
 *
 */
@Stateless
public class ShiftMasterFinder {

	@Inject
	private ShiftMasterRepository shiftMasterRepo;
	
	@Inject
	private WorkTypeRepository workTypeRepo;
	
	@Inject
	private WorkTimeSettingRepository workTimeRepo;

	// シフトマスタの一覧を取得する
	public Ksm015StartPageDto startScreen() {
		String companyId = AppContexts.user().companyId();
		Ksm015StartPageDto startPage = new Ksm015StartPageDto();

		List<ShiftMasterDto> shiftMasters = shiftMasterRepo.getAllDtoByCid(companyId);
		startPage.setShiftMasters(shiftMasters);
		
		return startPage;

	}
	
	public List<ShiftMasterDto> getShiftMasters() {
		String companyId = AppContexts.user().companyId();
		List<ShiftMasterDto> shiftMasters = shiftMasterRepo.getAllDtoByCid(companyId);
		return shiftMasters;

	}
	// 使用できるシフトマスタの勤務情報と補正済み所定時間帯を取得する
	public List<ShiftMasterDto> getShiftMastersByWorkPlace(String workplaceId) {
		String companyId = AppContexts.user().companyId();
		List<ShiftMasterDto> shiftMasters = shiftMasterRepo.getAllDtoByCid(companyId);
		return shiftMasters;

	}
	
	public WorkInfoDto getWorkInfo(String workTypeCd, String workTimeCd) {
		String companyId = AppContexts.user().companyId();
		Optional<WorkType> worktype = workTypeRepo.findByPK(companyId, workTypeCd);
		Optional<WorkTimeSetting> worktime = workTimeRepo.findByCode(companyId, workTimeCd);
		
		WorkInfoDto result = new WorkInfoDto();
		
		if(worktype.isPresent()) {
			result.setWorkType(new WorkTypeDto(worktype.get().getWorkTypeCode().v(), worktype.get().getAbbreviationName().v()));
		} else {
			result.setWorkType(new WorkTypeDto(workTypeCd, I18NText.getText("KSM015_28", workTypeCd, I18NText.getText("KSM015_29"))));
		}
		
		if(worktime.isPresent()) {
			result.setWorkTime(new WorkTimeSettingDto(worktime.get()));
		} else {
			result.setWorkTime(new WorkTimeSettingDto(workTimeCd, I18NText.getText("KSM015_28", workTimeCd, I18NText.getText("KSM015_29"))));
		}
		
		return result;
	}

}
