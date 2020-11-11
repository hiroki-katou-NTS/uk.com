package nts.uk.ctx.at.function.infra.entity.alarmworkplace.alarmlstrole;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

@Embeddable
@AllArgsConstructor
@NoArgsConstructor
public class KfnmtALstWkpPmsPk implements Serializable {

    private static final long serialVersionUID = 1L;

    @Column(name = "CID")
    public String companyID;

    @Column(name = "ALARM_PATTERN_CD")
    public String alarmPatternCD;

    @Column(name = "ROLLID")
    public String roleId;

}
