package nts.uk.ctx.at.request.dom.mail;

import java.util.Optional;
import java.util.List;

/**
* 
*/
public interface UrlExecInfoRepository
{

    List<UrlExecInfo> getAllUrlExecInfo();

    Optional<UrlExecInfo> getUrlExecInfoById(String embeddedId, String cid);

    void add(UrlExecInfo domain);

    void update(UrlExecInfo domain);

    void remove(String embeddedId, String cid);

}
