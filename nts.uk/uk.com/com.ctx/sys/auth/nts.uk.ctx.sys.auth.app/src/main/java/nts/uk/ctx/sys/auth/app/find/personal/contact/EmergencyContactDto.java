package nts.uk.ctx.sys.auth.app.find.personal.contact;

import lombok.Builder;
import nts.uk.ctx.sys.auth.dom.personal.contact.ContactName;
import nts.uk.ctx.sys.auth.dom.personal.contact.PhoneNumber;
import nts.uk.ctx.sys.auth.dom.personal.contact.Remark;

/**
 * Dto 個人連絡先
 */
@Builder
public class EmergencyContactDto {
    /**
     * メモ
     */
    private String remark;

    /**
     * 連絡先名
     */
    private String contactName;

    /**
     * 電話番号
     */
    private String phoneNumber;
}
