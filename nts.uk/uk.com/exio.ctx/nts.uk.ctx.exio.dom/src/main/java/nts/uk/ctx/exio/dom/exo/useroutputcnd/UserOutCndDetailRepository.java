package nts.uk.ctx.exio.dom.exo.useroutputcnd;

import java.util.Optional;
import java.util.List;

/**
* 出力条件詳細
*/
public interface UserOutCndDetailRepository
{

    List<UserOutCndDetail> getAllUserOutCndDetail();

    Optional<UserOutCndDetail> getUserOutCndDetailById();

    void add(UserOutCndDetail domain);

    void update(UserOutCndDetail domain);

    void remove();

}
