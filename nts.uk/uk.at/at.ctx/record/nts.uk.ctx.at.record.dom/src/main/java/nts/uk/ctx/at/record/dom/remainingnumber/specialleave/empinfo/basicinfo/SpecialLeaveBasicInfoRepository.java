package nts.uk.ctx.at.record.dom.remainingnumber.specialleave.empinfo.basicinfo;

import java.util.List;

public interface SpecialLeaveBasicInfoRepository {
	List<SpecialLeaveBasicInfo> listSPLeav(String sid);
	
	void add(SpecialLeaveBasicInfo domain);
	
	void update(SpecialLeaveBasicInfo domain);
	
	void delete(String infoId);
}
