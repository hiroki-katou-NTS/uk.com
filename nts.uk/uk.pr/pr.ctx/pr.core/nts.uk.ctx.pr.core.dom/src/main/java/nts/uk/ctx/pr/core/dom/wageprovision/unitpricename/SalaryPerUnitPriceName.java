package nts.uk.ctx.pr.core.dom.wageprovision.unitpricename;

import java.util.Optional;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.arc.layer.dom.AggregateRoot;
import nts.arc.time.GeneralDate;
import nts.arc.time.GeneralDateTime;
import nts.arc.enums.EnumAdaptor;
import nts.uk.ctx.pr.core.dom.wageprovision.statementitem.IntegrationItemCode;
import nts.uk.shr.com.enumcommon.Abolition;
import nts.uk.shr.com.primitive.Memo;

/**
* 給与個人単価名称
*/
@Getter
public class SalaryPerUnitPriceName extends AggregateRoot {
    
    /**
    * 会社ID
    */
    private String cid;
    
    /**
    * コード
    */
    private PerUnitPriceCode code;
    
    /**
    * 名称
    */
    private PerUnitPriceName name;
    
    /**
    * 廃止区分
    */
    private Abolition abolition;
    
    /**
    * 略名
    */
    private PerUnitPriceShortName shortName;
    
    /**
    * 統合コード
    */
    private IntegrationItemCode integrationCode;
    
    /**
    * メモ
    */
    private Optional<Memo> note;
    
    public SalaryPerUnitPriceName(String cid, String code, String name, int abolition, String shortName, String integrationCode, String note) {
        this.cid = cid;
        this.code = new PerUnitPriceCode(code);
        this.name = new PerUnitPriceName(name);
        this.shortName = new PerUnitPriceShortName(shortName);
        this.integrationCode = new IntegrationItemCode(integrationCode);
        this.abolition = EnumAdaptor.valueOf(abolition, Abolition.class);
        this.note = note == null ? Optional.empty() : Optional.of(new Memo(note));
    }
    
}
