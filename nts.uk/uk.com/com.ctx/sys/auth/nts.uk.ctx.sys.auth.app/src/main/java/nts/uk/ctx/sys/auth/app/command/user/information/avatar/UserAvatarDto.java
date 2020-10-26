package nts.uk.ctx.sys.auth.app.command.user.information.avatar;

import lombok.Builder;
import lombok.Data;
import nts.uk.ctx.sys.auth.dom.avatar.UserAvatar;

/**
 * Command dto 個人の顔写真
 */
@Data
@Builder
public class UserAvatarDto implements UserAvatar.MementoSetter, UserAvatar.MementoGetter {

    /**
     * 個人ID
     */
    private String personalId;

    /**
     * 顔写真ファイルID
     */
    private String fileId;
}
