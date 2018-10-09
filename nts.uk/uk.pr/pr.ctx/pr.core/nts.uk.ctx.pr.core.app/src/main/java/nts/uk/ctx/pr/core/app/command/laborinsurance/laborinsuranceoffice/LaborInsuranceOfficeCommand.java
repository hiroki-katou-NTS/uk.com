package nts.uk.ctx.pr.core.app.command.労働保険.労働保険事業所;

import lombok.Value;
import nts.arc.time.GeneralDate;
import nts.arc.time.GeneralDateTime;

@Value
public class LaborInsuranceOfficeCommand
{
    
    /**
    * 会社ID
    */
    private String companyId;
    
    /**
    * コード
    */
    private String officeCode;
    
    /**
    * 名称
    */
    private String officeName;
    
    /**
    * メモ
    */
    private String notes;
    
    /**
    * 代表者職位
    */
    private String representativePosition;
    
    /**
    * 労働保険事業所代表者名
    */
    private String ;
    
    /**
    * 住所１
    */
    private String ;
    
    /**
    * 住所２
    */
    private String ;
    
    /**
    * 住所カナ１
    */
    private String ;
    
    /**
    * 住所カナ２
    */
    private String ;
    
    /**
    * 電話番号
    */
    private String phoneNumber;
    
    /**
    * 郵便番号
    */
    private String postalCode;
    
    /**
    * 事業所記号
    */
    private String ;
    
    /**
    * 事業所番号1
    */
    private Integer ;
    
    /**
    * 事業所番号2
    */
    private Integer ;
    
    /**
    * 事業所番号3
    */
    private Integer ;
    
    /**
    * 都市区符号
    */
    private String ;
    

}
