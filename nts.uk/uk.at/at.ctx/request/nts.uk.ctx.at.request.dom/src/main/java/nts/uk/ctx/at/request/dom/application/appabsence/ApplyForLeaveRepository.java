package nts.uk.ctx.at.request.dom.application.appabsence;

import java.util.Optional;

/**
 * @author anhnm
 *
 */
public interface ApplyForLeaveRepository {

    /**
     * @param domain
     * @param CID
     * @param appId
     */
    public void insert(ApplyForLeave domain, String CID, String appId);
    
    /**
     * @param CID
     * @param appId
     * @return ApplyForLeave domain
     */
    public Optional<ApplyForLeave> findApplyForLeave(String CID, String appId);
    
    /**
     * @param domain
     * @param CID
     * @param appId
     */
    public void update(ApplyForLeave domain, String CID, String appId);
    
    /**
     * @param CID
     * @param appId
     */
    public void delete(String CID, String appId);
}
