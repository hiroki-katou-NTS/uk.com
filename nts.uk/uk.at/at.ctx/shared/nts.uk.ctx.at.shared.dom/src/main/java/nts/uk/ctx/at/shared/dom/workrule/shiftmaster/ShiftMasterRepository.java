package nts.uk.ctx.at.shared.dom.workrule.shiftmaster;

import java.util.List;
import java.util.Optional;

import nts.uk.ctx.at.shared.dom.workrule.shiftmaster.dto.ShiftMasterDto;

public interface ShiftMasterRepository {

	public List<ShiftMasterDto> getAllByCid(String companyId);

	public Optional<ShiftMaster> getByShiftMaterCd(String companyId, String shiftMaterCode);

	public Optional<ShiftMaster> getByWorkTypeAndWorkTime(String companyId, String workTypeCd, String workTimeCd);

	public boolean checkExists(String companyId, String workTypeCd, String workTimeCd);

	public void insert(ShiftMaster shiftMater);

	public void update(ShiftMaster shiftMater);

	public void delete(String companyId, String shiftMaterCode);
	
	public List<ShiftMasterDto> getByListShiftMaterCd(String companyId, List<String> listShiftMaterCode);

	List<ShiftMasterDto> getAllDtoByCid(String companyId);


}
