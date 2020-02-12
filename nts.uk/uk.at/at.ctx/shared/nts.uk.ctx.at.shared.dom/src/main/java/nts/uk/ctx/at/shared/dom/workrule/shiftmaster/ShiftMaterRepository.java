package nts.uk.ctx.at.shared.dom.workrule.shiftmaster;

import java.util.List;
import java.util.Optional;

public interface ShiftMaterRepository {

	public List<ShiftMater> getAllByCid(String companyId);

	public Optional<ShiftMater> getByShiftMaterCd(String companyId, String shiftMaterCode);

	public Optional<ShiftMater> getByWorkTypeAndWorkTime(String companyId, String workTypeCd, String workTimeCd);

	public boolean checkExists(String companyId, String workTypeCd, String workTimeCd);

	public void insert(ShiftMater shiftMater);

	public void update(ShiftMater shiftMater);

	public void delete(String companyId, String shiftMaterCode);
	
	public List<ShiftMater> getByListShiftMaterCd(String companyId, List<String> listShiftMaterCode);

}
