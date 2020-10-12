package nts.uk.ctx.sys.auth.app.find.avatar;

import lombok.Data;
import nts.uk.ctx.sys.auth.dom.avatar.UserAvatar;

/**
 * Dto 個人の顔写真
 */
@Data
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
