package nts.uk.ctx.pr.yearend.app.command.yearendadjustment.insurancecompany.insurancetype;

import lombok.Value;

@Value
public class InsuranceTypeCommand
{
    /**
    * 生命保険コード
    */
    private String lifeInsuranceCode;
    
    /**
    * コード
    */
    private String insuranceTypeCode;
    
    /**
    * 名称
    */
    private String insuranceTypeName;
    
    /**
    * 区分
    */
    private int atrOfInsuranceType;
    

}
