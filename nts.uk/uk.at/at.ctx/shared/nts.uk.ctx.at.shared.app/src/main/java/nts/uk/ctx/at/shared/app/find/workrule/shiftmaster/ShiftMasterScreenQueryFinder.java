package nts.uk.ctx.at.shared.app.find.workrule.shiftmaster;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.shared.dom.workrule.shiftmaster.ShiftMasterRepository;
import nts.uk.ctx.at.shared.dom.workrule.shiftmaster.dto.ShiftMasterDto;
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

	// シフトマスタの一覧を取得する
	public Ksm015bStartPageDto startBScreen() {
		String companyId = AppContexts.user().companyId();
		Ksm015bStartPageDto startPage = new Ksm015bStartPageDto();

		List<ShiftMasterDto> shiftMasters = shiftMasterRepo.getAllDtoByCid(companyId);
		startPage.setShiftMasters(shiftMasters);

		return startPage;

	}
	
	public List<ShiftMasterDto> getShiftMasters() {
		String companyId = AppContexts.user().companyId();
		List<ShiftMasterDto> shiftMasters = shiftMasterRepo.getAllDtoByCid(companyId);
		return shiftMasters;

	}

}
