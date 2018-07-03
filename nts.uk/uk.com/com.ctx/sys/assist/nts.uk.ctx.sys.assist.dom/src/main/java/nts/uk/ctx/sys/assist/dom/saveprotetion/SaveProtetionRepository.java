package nts.uk.ctx.sys.assist.dom.saveprotetion;

import java.util.Optional;
import java.util.List;

/**
* 保存保護
*/
public interface SaveProtetionRepository
{

    List<SaveProtetion> getAllSaveProtection();

    Optional<SaveProtetion> getSaveProtectionById();

    void add(SaveProtetion domain);

    void update(SaveProtetion domain);

    void remove();

}
