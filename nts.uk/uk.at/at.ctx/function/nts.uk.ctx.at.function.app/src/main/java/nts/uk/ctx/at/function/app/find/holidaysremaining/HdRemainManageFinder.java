package nts.uk.ctx.at.function.app.find.holidaysremaining;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.function.dom.holidaysremaining.repository.HolidaysRemainingManagementRepository;

@Stateless
/**
* 休暇残数管理表の出力項目設定
*/
public class HdRemainManageFinder
{

    @Inject
    private HolidaysRemainingManagementRepository hdRemainingManagementRepo;
    
    public List<HdRemainManageDto> findAll() {
    	return null;
	}
    


}
