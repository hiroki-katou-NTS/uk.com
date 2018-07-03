package nts.uk.ctx.exio.dom.exo.executionlog;

import java.util.Optional;
import java.util.List;

/**
* 外部出力動作管理
*/
public interface ExOutOpMngRepository
{

    List<ExOutOpMng> getAllExOutOpMng();

    Optional<ExOutOpMng> getExOutOpMngById(String exOutProId);

    void add(ExOutOpMng domain);

    void update(ExOutOpMng domain);

    void remove(String exOutProId);

}
