package nts.uk.ctx.at.function.app.find.holidaysremaining;
import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.function.dom.holidaysremaining.HolidaysRemainingManagement;

@Stateless
/**
* 休暇残数管理表の出力項目設定
*/
public class HdRemainManageFinder
{

    @Inject
    private HolidaysRemainingManagement holidaysRemainingManagementFinder;


}
