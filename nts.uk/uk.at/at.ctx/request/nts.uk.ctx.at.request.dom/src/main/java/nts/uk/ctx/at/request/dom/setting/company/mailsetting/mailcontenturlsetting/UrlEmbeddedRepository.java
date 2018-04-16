package nts.uk.ctx.at.request.dom.setting.company.mailsetting.mailcontenturlsetting;

import java.util.List;
import java.util.Optional;

/**
* 
*/
public interface UrlEmbeddedRepository
{

    List<UrlEmbedded> getAllUrlEmbedded();

    Optional<UrlEmbedded> getUrlEmbeddedById(String cid);

    void add(UrlEmbedded domain);

    void update(UrlEmbedded domain);

    void remove(String cid);

}
