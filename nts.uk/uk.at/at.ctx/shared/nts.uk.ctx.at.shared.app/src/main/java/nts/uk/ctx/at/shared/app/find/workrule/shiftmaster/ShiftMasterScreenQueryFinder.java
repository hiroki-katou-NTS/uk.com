package nts.uk.ctx.at.shared.app.find.workrule.shiftmaster;

import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.shared.dom.workrule.shiftmaster.ShiftMasterRepository;
import nts.uk.ctx.at.shared.dom.worktime.worktimeset.WorkTimeSettingRepository;
import nts.uk.ctx.at.shared.dom.worktype.WorkTypeRepository;
import nts.uk.shr.com.context.AppContexts;

/**
 * シフトマスタの一覧を取得する
 * 
 * @author anhdt
 *
 */
@Stateless
public class ShiftMasterScreenQueryFinder {

	@Inject
	private ShiftMasterRepository shiftMasterRepo;

	@Inject
	private WorkTypeRepository workTypeRepo;
	
	@Inject
	private WorkTimeSettingRepository workTimeSetRepo;
	
	// シフトマスタの一覧を取得する
	public Ksm015bStartPageDto startBScreen() {
		String companyId = AppContexts.user().companyId();
		Ksm015bStartPageDto startPage = new Ksm015bStartPageDto();

		List<ShiftMasterDto> shiftMasters = shiftMasterRepo.getAllByCid(companyId).stream()
				.map(s -> new ShiftMasterDto(s)).collect(Collectors.toList());
		startPage.setShiftMasters(shiftMasters);

		List<WorkTypeDto> workTypes = workTypeRepo.findByCompanyId(companyId).stream()
				.map(w -> new WorkTypeDto(w)).collect(Collectors.toList());
		startPage.setWorkTypes(workTypes);
		
		List<WorkTimeSettingDto> workTimeSettings = workTimeSetRepo.findByCId(companyId).stream()
				.map(w -> new WorkTimeSettingDto(w)).collect(Collectors.toList());
		startPage.setWorkTimeSettings(workTimeSettings);
		
		return startPage;

	}

}
