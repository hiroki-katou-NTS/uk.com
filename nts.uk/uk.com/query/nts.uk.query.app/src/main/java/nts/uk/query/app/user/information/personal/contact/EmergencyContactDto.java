package nts.uk.query.app.user.information.personal.contact;

import lombok.Builder;
import lombok.Data;

/**
 * Dto  緊急連絡先
 */
@Data
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
