package nts.uk.ctx.at.request.dom.mail;

import java.util.Optional;
import java.util.List;

/**
* 
*/
public interface UrlTaskIncreRepository
{

    List<UrlTaskIncre> getAllUrlTaskIncre();

    Optional<UrlTaskIncre> getUrlTaskIncreById(String embeddedId, String cid, String taskIncreId);

    void add(UrlTaskIncre domain);

    void update(UrlTaskIncre domain);

    void remove(String embeddedId, String cid, String taskIncreId);

}
