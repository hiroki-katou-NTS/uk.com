package nts.uk.ctx.at.request.dom.application.workchange;

import java.util.List;
import java.util.Optional;

/**
* 勤務変更申請
*/
public interface IAppWorkChangeRepository
{

    List<AppWorkChange> getAllAppWorkChange();
    
    Optional<AppWorkChange> getAppworkChangeById(String cid, String appId);
    
    void add(AppWorkChange domain);

    void update(AppWorkChange domain);
    
    void delete(String cid, String appId);
    /**
     * @author hoatt
     * get list application work change by list appID
     * @param companyID
     * @param lstAppId
     * @return
     */
    List<AppWorkChange> getListAppWorkChangeByID(String companyID, List<String> lstAppId);
}
