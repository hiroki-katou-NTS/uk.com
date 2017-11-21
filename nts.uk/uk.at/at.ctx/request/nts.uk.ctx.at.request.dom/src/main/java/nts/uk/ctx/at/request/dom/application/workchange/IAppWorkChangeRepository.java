package nts.uk.ctx.at.request.dom.application.workchange;

import java.util.List;

/**
* 勤務変更申請
*/
public interface IAppWorkChangeRepository
{

    List<AppWorkChange> getAllAppWorkChange();

    void add(AppWorkChange domain);

    void update(AppWorkChange domain);

    void remove(AppWorkChange domain);
    
    void remove(String cid, String appId);
}
