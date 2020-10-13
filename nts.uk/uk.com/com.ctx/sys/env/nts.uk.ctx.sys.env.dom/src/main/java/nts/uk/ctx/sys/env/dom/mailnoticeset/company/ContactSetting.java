package nts.uk.ctx.sys.env.dom.mailnoticeset.company;

import java.util.Optional;

import lombok.Getter;
import nts.uk.shr.com.enumcommon.NotUseAtr;

@Getter
//連絡先の設定
public class ContactSetting {

    /**
     * 連絡先利用設定
     */
    private ContactUsageSetting contactUsageSetting;

    /**
     * 更新可能
     */
    private Optional<NotUseAtr> updatable;
}
