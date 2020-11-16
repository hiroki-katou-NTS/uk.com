package nts.uk.ctx.at.function.infra.entity.alarmworkplace.alarmpatternworkplace;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
@AllArgsConstructor
public class KfnmtALstWkpPtnPk implements Serializable {

    private static final long serialVersionUID = 1L;

    @Column(name = "CID")
    public String companyID;

    @Column(name = "ALARM_PATTERN_CD")
    public String alarmPatternCD;

}
