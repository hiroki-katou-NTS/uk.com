package nts.uk.ctx.at.record.dom.remainingnumber.specialleave.empinfo.basicinfo;

import java.util.List;
import java.util.Optional;

public interface SpecialLeaveBasicInfoRepository {
	List<SpecialLeaveBasicInfo> listSPLeav(String sid);
	
	Optional<SpecialLeaveBasicInfo> getBySidLeaveCd(String sid, int spLeaveCD);
	
	void add(SpecialLeaveBasicInfo domain);
	
	void update(SpecialLeaveBasicInfo domain);
	
	void delete(String sID, int spLeavCD);
}
