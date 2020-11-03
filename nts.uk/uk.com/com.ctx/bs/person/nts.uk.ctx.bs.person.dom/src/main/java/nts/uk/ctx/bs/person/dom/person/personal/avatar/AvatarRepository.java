package nts.uk.ctx.bs.person.dom.person.personal.avatar;

import java.util.Optional;

/**
 * Repository 個人の顔写真
 */
public interface AvatarRepository {
    /**
     * Add new UserAvatar
     *
     * @param userAvatar
     */
    void insert(UserAvatar userAvatar);

    /**
     * Update UserAvatar
     *
     * @param userAvatar
     */
    void update(UserAvatar userAvatar);

    /**
     * Delete UserAvatar
     *
     * @param userAvatar
     */
    void delete(UserAvatar userAvatar);

    /**
     * Find UserAvatar by personalId
     *
     * @param personalId
     */
    Optional<UserAvatar> getAvatarByPersonalId(String personalId);
}
