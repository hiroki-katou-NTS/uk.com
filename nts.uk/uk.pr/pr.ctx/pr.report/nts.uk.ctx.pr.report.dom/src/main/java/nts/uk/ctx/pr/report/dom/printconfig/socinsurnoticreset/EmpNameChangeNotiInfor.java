package nts.uk.ctx.pr.report.dom.printconfig.socinsurnoticreset;

import lombok.Getter;
import nts.arc.layer.dom.AggregateRoot;

import java.util.Optional;


/**
* 社員氏名変更届情報
*/
@Getter
public class EmpNameChangeNotiInfor extends AggregateRoot {
    
    /**
    * 社員ID
    */
    private String employeeId;
    
    /**
    * 会社ID
    */
    private String companyId;
    
    /**
    * 健康保険被保険者証不要
    */
    private int healInsurPersonNoNeed;
    
    /**
    * その他
    */
    private int other;
    
    /**
    * その他備考
    */
    private Optional<NameChangeNotifi> otherRemarks;
    
    public EmpNameChangeNotiInfor(String employeeId, String cid, int healInsurPerNoneed, int other, String otherRemarks) {
        this.companyId = cid;
        this.employeeId = employeeId;
        this.other = other;
        this.otherRemarks = otherRemarks == null ? Optional.empty() : Optional.of(new NameChangeNotifi(otherRemarks));
        this.healInsurPersonNoNeed = healInsurPerNoneed;
    }
    
}
