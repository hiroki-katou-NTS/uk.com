package nts.uk.ctx.pr.file.app.core.socialinsurnoticreset;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 家族現同居住所
 * */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FamilyBeforeAddress {

    private String familyId;

    /**住所1 */
    private String address1;

    /**住所2 */
    private String address2;
}
