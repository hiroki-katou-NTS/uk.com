package nts.uk.ctx.at.request.dom.setting.request.application.workchange;

import java.util.List;
import java.util.Optional;

/**
* 勤務変更申請設定
*/
public interface IAppWorkChangeSetRepository
{

    List<AppWorkChangeSet_Old> getAllAppWorkChangeSet();
    
    Optional<AppWorkChangeSet_Old> findWorkChangeSetByID(String cid);
    
    void add(AppWorkChangeSet_Old domain);

    void update(AppWorkChangeSet_Old domain);

    void remove(AppWorkChangeSet_Old domain);
    
    void remove(String key);
}
