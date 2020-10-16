package nts.uk.ctx.sys.env.dom.mailnoticeset.company;

import java.util.Optional;

/**
 * Repository ユーザー情報の使用方法
 */
public interface UserInfoUseMethod_Repository {

    /**
     * Add new UserInfoUseMethod_
     *
     * @param domain
     */
    void insert(UserInfoUseMethod_ domain);

    /**
     * Update UserInfoUseMethod_
     *
     * @param domain
     */
    void update(UserInfoUseMethod_ domain);

    /**
     * Find UserInfoUseMethod_ by company ID
     *
     * @param cid
     * @return
     */
    Optional<UserInfoUseMethod_> findByCId(String cid);
}
