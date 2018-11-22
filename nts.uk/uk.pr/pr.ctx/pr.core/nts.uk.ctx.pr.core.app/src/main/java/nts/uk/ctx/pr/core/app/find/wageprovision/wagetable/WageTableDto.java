package nts.uk.ctx.pr.core.app.find.wageprovision.wagetable;

<<<<<<< HEAD
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.dom.AggregateRoot;
import nts.arc.primitive.PrimitiveValueBase;
import nts.uk.ctx.pr.core.dom.wageprovision.wagetable.*;

import java.util.Optional;
=======
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.uk.ctx.pr.core.dom.wageprovision.wagetable.WageTable;
>>>>>>> pj/pr/team_G/QMM019

/**
 * 賃金テーブル
 */
<<<<<<< HEAD
@NoArgsConstructor
@Data
public class WageTableDto {

    /**
     * 会社ID
     */
    private String cid;

    /**
=======
@Getter
@Setter
@NoArgsConstructor
public class WageTableDto {

    /**
>>>>>>> pj/pr/team_G/QMM019
     * 賃金テーブルコード
     */
    private String wageTableCode;

    /**
     * 賃金テーブル名
     */
    private String wageTableName;

    /**
     * 要素設定
     */
    private int elementSetting;

    /**
     * 備考情報
     */
    private String remarkInformation;

    /**
     * 要素情報
     */
    private ElementInformationDto elementInformation;

<<<<<<< HEAD
    public static WageTableDto fromDomainToDto(WageTable domain) {
        WageTableDto dto = new WageTableDto();
        dto.cid = domain.getCid();
        dto.wageTableCode = domain.getWageTableCode().v();
        dto.wageTableName = domain.getWageTableName().v();
        dto.elementInformation = ElementInformationDto.fromDomainToDto(domain.getElementInformation());
        dto.elementSetting = domain.getElementSetting().value;
        dto.remarkInformation = domain.getRemarkInformation().map(PrimitiveValueBase::v).orElse(null);
=======
    public static WageTableDto fromDomain(WageTable domain) {
        WageTableDto dto = new WageTableDto();
        dto.setWageTableCode(domain.getWageTableCode().v());
        dto.setWageTableName(domain.getWageTableName().v());
        dto.setElementSetting(domain.getElementSetting().value);
        dto.setRemarkInformation(domain.getRemarkInformation().map(x -> x.v()).orElse(null));
        dto.setElementInformation(ElementInformationDto.fromDomain(domain.getElementInformation()));
>>>>>>> pj/pr/team_G/QMM019
        return dto;
    }
}
