package nts.uk.ctx.sys.assist.dom.saveProtection;

import java.util.Optional;
import java.util.List;

/**
* 保存保護
*/
public interface SaveProtectionRepository
{

    List<SaveProtection> getAllSaveProtection();

    Optional<SaveProtection> getSaveProtectionById();

    void add(SaveProtection domain);

    void update(SaveProtection domain);

    void remove();

}
