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
        this.companyName = "株式会社 健保産業";
        this.companyNameReference = "健保 良一";
        this.phoneNumber = "354326789";
        this.add1 = "杉並区";
        this.add2 = "高井戸３－２－１";
    }
}
