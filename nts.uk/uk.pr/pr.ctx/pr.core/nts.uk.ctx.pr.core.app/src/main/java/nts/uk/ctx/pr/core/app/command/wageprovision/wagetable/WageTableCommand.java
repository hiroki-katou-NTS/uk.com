package nts.uk.ctx.pr.core.app.command.wageprovision.wagetable;

import lombok.Data;
import lombok.NoArgsConstructor;
import nts.arc.primitive.PrimitiveValueBase;
import nts.uk.ctx.pr.core.app.find.wageprovision.wagetable.ElementInformationDto;
import nts.uk.ctx.pr.core.dom.wageprovision.wagetable.WageTable;

/**
 * 賃金テーブル
 */
@NoArgsConstructor
@Data
public class WageTableCommand {

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
    private ElementInformationCommand elementInformation;

    public WageTable fromCommandToDomain() {
        return new WageTable(cid, wageTableCode, wageTableName, elementSetting, remarkInformation, elementInformation.fromCommandToDomain());
    }
}
