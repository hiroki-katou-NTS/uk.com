package nts.uk.query.app.user.information.avatar;

import lombok.Builder;
import lombok.Data;
import nts.uk.ctx.sys.auth.dom.avatar.UserAvatar;

/**
 * Dto 個人の顔写真
 */
@Data
@Builder
public class UserAvatarDto implements UserAvatar.MementoSetter {

    /**
     * 個人ID
     */
    private String personalId;

    /**
     * 顔写真ファイルID
     */
    private String fileId;
}
