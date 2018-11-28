package nts.uk.ctx.pr.core.app.find.wageprovision.wagetable;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.uk.ctx.pr.core.dom.wageprovision.wagetable.WageTable;

/**
 * 賃金テーブル
 */
@Getter
@Setter
@NoArgsConstructor
public class WageTableDto {

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

    public static WageTableDto fromDomain(WageTable domain) {
        WageTableDto dto = new WageTableDto();
        dto.setWageTableCode(domain.getWageTableCode().v());
        dto.setWageTableName(domain.getWageTableName().v());
        dto.setElementSetting(domain.getElementSetting().value);
        dto.setRemarkInformation(domain.getRemarkInformation().map(x -> x.v()).orElse(null));
        dto.setElementInformation(ElementInformationDto.fromDomain(domain.getElementInformation()));
        return dto;
    }
}
