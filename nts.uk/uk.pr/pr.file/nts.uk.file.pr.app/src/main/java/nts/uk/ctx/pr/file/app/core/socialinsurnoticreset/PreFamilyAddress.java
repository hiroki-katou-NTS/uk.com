package nts.uk.ctx.pr.file.app.core.socialinsurnoticreset;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
/**
 家族前住所
 */
public class PreFamilyAddress {

    private String familyId;
    /**住所1 */
    private String address1;

    /**住所2 */
    private String address2;

    private boolean livingTogether;
}
