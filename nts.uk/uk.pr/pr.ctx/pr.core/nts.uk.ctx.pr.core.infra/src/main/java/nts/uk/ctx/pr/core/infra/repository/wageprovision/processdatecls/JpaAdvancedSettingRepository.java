package nts.uk.ctx.pr.core.infra.repository.wageprovision.processdatecls;


import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.pr.core.dom.wageprovision.processdatecls.AdvancedSetting;
import nts.uk.ctx.pr.core.dom.wageprovision.processdatecls.AdvancedSettingRepository;

import javax.ejb.Stateless;
import java.util.List;

@Stateless
public class JpaAdvancedSettingRepository extends JpaRepository implements AdvancedSettingRepository {


    private static final String SELECT_ALL_QUERY_STRING = "SELECT f FROM QpbmtCloseDate f";

    @Override
    public List<AdvancedSetting> getAllAdvancedSetting() {
        return null;
    }
}
