package command.person.personal.contact;

import lombok.Builder;
import lombok.Data;

/**
 * Command dto 個人連絡先
 */
@Data
@Builder
public class OtherContactDto  {
    /**
     * NO
     */
    private Integer otherContactNo;

    /**
     * 連絡先のアドレス
     */
    private String address;

}
