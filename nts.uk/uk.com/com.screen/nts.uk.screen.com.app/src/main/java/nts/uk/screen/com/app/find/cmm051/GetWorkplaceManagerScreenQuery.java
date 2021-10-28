package nts.uk.screen.com.app.find.cmm051;


import nts.uk.ctx.sys.auth.dom.wkpmanager.WorkplaceManager;
import nts.uk.ctx.sys.auth.dom.wkpmanager.WorkplaceManagerRepository;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.List;

/**
 * Screen query: UKDesign.UniversalK.共通.CMM_マスタメンテナンス.CMM051_職場管理者の登録.A:職場管理者の登録.メニュー別OCD.職場管理者を取得する
 * @author chinh.hm
 */

@Stateless
public class GetWorkplaceManagerScreenQuery {
    @Inject
    private WorkplaceManagerRepository workplaceManagerRepository;

    public List<WorkplaceManager> getListWplInfo(String workplaceId, String sid) {
        return workplaceManagerRepository
                .getWkpManagerByWorkplaceIdAndSid(workplaceId, sid);
    }
}
