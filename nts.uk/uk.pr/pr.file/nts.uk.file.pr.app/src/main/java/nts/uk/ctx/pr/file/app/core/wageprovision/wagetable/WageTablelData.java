package nts.uk.ctx.pr.file.app.core.wageprovision.wagetable;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class WageTablelData {

    //R1
    private String wageTableCode;
    //R2
    private String wageTableName;
    //R3
    private String WageHisStartYm;
    //R4
    private String WageHisEndYm;

    private String fixElement1;

    private String optAddElement1;

    private String fixElement2;

    private String optAddElement2;

    private String fixElement3;

    private String optAddElement3;

    private String stepIncrement1;

    private String upperLimit1;

    private String lowerLimit1;

    private String qualifiGroupCd;

    private String qualifiGroupName;

    private String stepIncrement2;

    private String upperLimit2;

    private String lowerLimit2;

    private String stepIncrement3;

    private String upperLimit3;

    private String lowerLimit3;

    private BigDecimal payAmount;

    private String payMethod;

    private int elementSet;

    private int masterNumAtr1;

    private int masterNumAtr2;

    private int masterNumAtr3;

    private String masterCd1;

    private String masterCd2;

    private String masterCd3;

    private String qualificationName;

    private String frameNumber1;

    private String frameNumber2;

    private String frameNumber3;

}
