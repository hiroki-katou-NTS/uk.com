package nts.uk.ctx.pr.file.app.core.socialinsurnoticreset;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@Builder

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

    public CompanyInformation(){
        this.companyId = "11111";
        this.companyName = "companyName";
        this.companyNameReference = "companyNameReference";
        this.phoneNumber = "09827344";
        this.add1 = "add1";
        this.add2 = "add2";
    }
}
