package nts.uk.ctx.bs.person.dom.person.setting.init.category;

import java.util.List;

public interface PerInfoInitValSetCtgRepository {
	
	List<PerInfoInitValSetCtg> getAllInitValSetCtg(String initValueSettingId);
	
	PerInfoInitValSetCtg getDetailInitValSetCtg(String initValueSettingId, String initValueSettingCtgId);

}
