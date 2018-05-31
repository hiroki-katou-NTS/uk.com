package nts.uk.ctx.at.request.dom.setting.company.mailsetting.remandsetting;

import java.util.Optional;
import java.util.List;

/**
* 
*/
public interface ContentOfRemandMailRepository
{

    List<ContentOfRemandMail> getAllRemandMail();

    Optional<ContentOfRemandMail> getRemandMailById(String cid);

    void add(ContentOfRemandMail domain);

    void update(ContentOfRemandMail domain);

    void remove(String cid);

}
