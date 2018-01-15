package nts.uk.ctx.at.request.dom.setting.request.application.workchange;

import java.util.List;
import java.util.Optional;

/**
* 勤務変更申請設定
*/
public interface IAppWorkChangeSetRepository
{

    List<AppWorkChangeSet> getAllAppWorkChangeSet();
    
    Optional<AppWorkChangeSet> findWorkChangeSetByID(String cid);
    
    void add(AppWorkChangeSet domain);

    void update(AppWorkChangeSet domain);

    void remove(AppWorkChangeSet domain);
    
    void remove(String key);
}
