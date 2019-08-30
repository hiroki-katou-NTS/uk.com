package nts.uk.ctx.pr.shared.dom.socialinsurance.employeesociainsur.empbenepenpeninfor;

import lombok.Getter;
import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.dom.AggregateRoot;
import nts.uk.ctx.pr.shared.dom.socialinsurance.employeesociainsur.emphealinsurbeneinfo.RemarkForQuaCompany;

import java.util.Optional;


/**
* 厚生年金保険喪失時情報
*/
@Getter
public class WelfPenInsLossIf extends AggregateRoot {
    
    /**
    * 社員ID
    */
    private String empId;
    
    /**
    * その他
    */
    private int other;
    
    /**
    * その他理由
    */
    private Optional<RemarkForQuaCompany> otherReason;
    
    /**
    * 保険証回収添付枚数
    */
    private Optional<NumInsCards> caInsuarace;
    
    /**
    * 保険証回収返不能枚数
    */
    private Optional<NumInsCards> numRecoved;
    
    /**
    * 資格喪失原因
    */
    private Optional<ReasonsForLossPensionIns> cause;
    
    public WelfPenInsLossIf(String empId, int other, String otherReason, Integer caInsurance, Integer numRecoved, Integer cause) {
        this.empId = empId;
        this.cause = cause == null ? Optional.empty() : Optional.of(EnumAdaptor.valueOf(cause, ReasonsForLossPensionIns.class));
        this.caInsuarace = caInsurance == null ? Optional.empty() : Optional.of(new NumInsCards(caInsurance));
        this.numRecoved = numRecoved == null ? Optional.empty() : Optional.of(new NumInsCards(numRecoved));
        this.other = other;
        this.otherReason = otherReason == null ? Optional.empty() : Optional.of(new RemarkForQuaCompany(otherReason));
    }
}
