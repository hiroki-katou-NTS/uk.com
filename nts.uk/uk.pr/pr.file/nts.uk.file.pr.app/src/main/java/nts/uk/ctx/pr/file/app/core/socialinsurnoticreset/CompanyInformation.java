package nts.uk.ctx.pr.file.app.core.socialinsurnoticreset;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@Builder
@NoArgsConstructor

/**
 会社情報
 */
public class CompanyInformation {

    /** 会社ID*/
    private String companyId;

    /** 会社名 */
    private String companyName;

    /** 代表者名 */
    private String companyNameReference;

    /** 電話番号 */
    private String phoneNumber;

    private String add1;

    private String add2;


}
