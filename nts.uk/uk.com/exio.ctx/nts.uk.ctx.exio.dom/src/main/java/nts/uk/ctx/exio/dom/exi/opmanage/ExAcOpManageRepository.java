package nts.uk.ctx.exio.dom.exi.opmanage;

import java.util.Optional;
import java.util.List;

/**
* 外部受入動作管理
*/
public interface ExAcOpManageRepository
{

    List<ExAcOpManage> getAllExAcOpManage();

    Optional<ExAcOpManage> getExAcOpManageById(String cid, String processId);

    void add(ExAcOpManage domain);

    void update(ExAcOpManage domain);

    void remove(String cid, String processId);

}
