package nts.uk.ctx.pr.report.app.find.printconfig.socialinsurnoticreset;


import lombok.*;
import nts.uk.ctx.pr.report.dom.printconfig.socinsurnoticreset.RomajiNameNotiCreSetting;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class RomajiNameNotiCreSetDto {

    /**
     * 会社ID
     */
    private String cid;

    /**
     * ユーザID
     */
    private String userId;

    /**
     * 住所出力区分
     */
    private Integer addressOutputClass;

    public  static RomajiNameNotiCreSetDto fromDomain(RomajiNameNotiCreSetting domain){
        return new RomajiNameNotiCreSetDto(
                domain.getUserId(),
                domain.getCid(),
                domain.getAddressOutputClass().value);
    }
}
