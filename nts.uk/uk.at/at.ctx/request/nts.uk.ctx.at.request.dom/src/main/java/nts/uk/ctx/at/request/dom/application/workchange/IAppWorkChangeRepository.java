package nts.uk.ctx.at.request.dom.application.workchange;

import java.util.List;
import java.util.Optional;

/**
* 古い勤務変更申請 「古いクラス → 削除予定 → 使わないでください (Old Class → Delete plan → Please don't use it)
*/
public interface IAppWorkChangeRepository
{

    List<AppWorkChange_Old> getAllAppWorkChange();
    
    Optional<AppWorkChange_Old> getAppworkChangeById(String cid, String appId);
    
    void add(AppWorkChange_Old domain);

    void update(AppWorkChange_Old domain);
    
    void delete(String cid, String appId);
    /**
     * @author hoatt
     * get list application work change by list appID
     * @param companyID
     * @param lstAppId
     * @return
     */
    List<AppWorkChange_Old> getListAppWorkChangeByID(String companyID, List<String> lstAppId);
}
