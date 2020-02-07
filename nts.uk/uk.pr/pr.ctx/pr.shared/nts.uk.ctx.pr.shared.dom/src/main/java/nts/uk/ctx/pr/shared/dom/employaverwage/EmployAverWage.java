package nts.uk.ctx.pr.shared.dom.employaverwage;

import lombok.Getter;
import nts.arc.layer.dom.AggregateRoot;

import java.math.BigDecimal;

/**
* 社員平均賃金
*/
@Getter
public class EmployAverWage extends AggregateRoot {

    /**
    * 社員ID
    */
    private String employeeId;

    /**
    * 平均賃金
    */
    private AverageWage averageWage;

    /**
    * 対象年月
    */
    private Integer targetDate;

    public EmployAverWage(String employeeId, int targetDate, BigDecimal averageWage) {
        this.employeeId = employeeId;
        this.targetDate = targetDate;
        this.averageWage = new AverageWage(averageWage.longValue());
    }

}
