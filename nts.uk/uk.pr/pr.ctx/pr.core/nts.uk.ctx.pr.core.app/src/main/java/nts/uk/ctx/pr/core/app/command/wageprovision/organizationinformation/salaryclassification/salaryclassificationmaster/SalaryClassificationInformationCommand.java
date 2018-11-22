package nts.uk.ctx.pr.core.app.command.wageprovision.organizationinformation.salaryclassification.salaryclassificationmaster;

import lombok.Value;
import nts.arc.time.GeneralDate;
import nts.arc.time.GeneralDateTime;

@Value
public class SalaryClassificationInformationCommand
{
    
    /**
    * 会社ID
    */
    private String companyId;
    
    /**
    * 給与分類コード
    */
    private String salaryClassificationCode;
    
    /**
    * 給与分類名称
    */
    private String salaryClassificationName;
    
    /**
    * メモ
    */
    private String memo;
    

}
