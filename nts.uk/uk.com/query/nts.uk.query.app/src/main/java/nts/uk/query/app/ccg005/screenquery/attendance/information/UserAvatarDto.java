package nts.uk.query.app.ccg005.screenquery.attendance.information;

import lombok.Builder;
import lombok.Data;
import nts.uk.ctx.bs.person.dom.person.personal.avatar.UserAvatar;

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
