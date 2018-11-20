package nts.uk.ctx.pr.yearend.dom.yearendadjustment.insurancecompany.insuranceType;

import lombok.Getter;
import nts.arc.layer.dom.AggregateRoot;
import nts.arc.enums.EnumAdaptor;
import nts.uk.ctx.pr.yearend.dom.yearendadjustment.insurancecompany.lifeInsurance.LifeInsuranceCode;

/**
* 保険種類情報
*/
@Getter
public class InsuranceType extends AggregateRoot {
    
    /**
    * 生命保険コード
    */
    private LifeInsuranceCode lifeInsuranceCode;
    
    /**
    * コード
    */
    private InsuranceTypeCode insuranceTypeCode;
    
    /**
    * 名称
    */
    private InsuranceTypeName insuranceTypeName;
    
    /**
    * 区分
    */
    private AtrOfInsuranceType atrOfInsuranceType;
    
    /**
    * 会社ＩＤ
    */
    private String cId;
    
    public InsuranceType(String cid, String lifeInsuranceCode, String insuranceTypeCode, String insuranceTypeName, int atrOfInsuranceType) {
        this.cId = cid;
        this.lifeInsuranceCode = new LifeInsuranceCode(lifeInsuranceCode);
        this.insuranceTypeCode = new InsuranceTypeCode(insuranceTypeCode);
        this.insuranceTypeName = new InsuranceTypeName(insuranceTypeName);
        this.atrOfInsuranceType = EnumAdaptor.valueOf(atrOfInsuranceType, AtrOfInsuranceType.class);
    }
    
}
