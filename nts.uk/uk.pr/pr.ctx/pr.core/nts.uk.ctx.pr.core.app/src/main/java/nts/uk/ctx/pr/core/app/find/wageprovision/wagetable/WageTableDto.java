package nts.uk.ctx.pr.core.app.find.wageprovision.wagetable;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.dom.AggregateRoot;
import nts.arc.primitive.PrimitiveValueBase;
import nts.uk.ctx.pr.core.dom.wageprovision.wagetable.*;

import java.util.Optional;

/**
 * 賃金テーブル
 */
@NoArgsConstructor
@Data
public class WageTableDto {

    /**
     * 会社ID
     */
    private String cid;

    /**
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

    public static WageTableDto fromDomainToDto(WageTable domain) {
        WageTableDto dto = new WageTableDto();
        dto.cid = domain.getCid();
        dto.wageTableCode = domain.getWageTableCode().v();
        dto.wageTableName = domain.getWageTableName().v();
        dto.elementInformation = ElementInformationDto.fromDomainToDto(domain.getElementInformation());
        dto.elementSetting = domain.getElementSetting().value;
        dto.remarkInformation = domain.getRemarkInformation().map(PrimitiveValueBase::v).orElse(null);
        return dto;
    }
}
