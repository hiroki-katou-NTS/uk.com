package nts.uk.ctx.pr.shared.dom.socialinsurance.employeesociainsur.emphealinsurbeneinfo;

import lombok.Getter;
import nts.arc.layer.dom.AggregateRoot;

import java.util.Optional;

/**
* 社員基礎年金番号情報
*/
@Getter
public class EmpBasicPenNumInfor extends AggregateRoot {
    
    /**
    * 社員ID
    */
    private String employeeId;
    
    /**
    * 基礎年金番号
    */
    private Optional<BasicPenNumber> basicPenNumber;
    
    public EmpBasicPenNumInfor(String employeeId, String basicPenNumber) {
        this.employeeId = employeeId;
        this.basicPenNumber = basicPenNumber == null ? Optional.empty() : Optional.of(new BasicPenNumber(basicPenNumber));
    }
    
}
