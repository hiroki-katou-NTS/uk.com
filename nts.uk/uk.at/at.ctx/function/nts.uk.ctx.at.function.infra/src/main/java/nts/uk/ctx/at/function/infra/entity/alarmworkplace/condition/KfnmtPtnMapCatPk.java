package nts.uk.ctx.at.function.infra.entity.alarmworkplace.condition;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
@AllArgsConstructor
@NoArgsConstructor
public class KfnmtPtnMapCatPk implements Serializable {

    private static final long serialVersionUID = 1L;

    @Column(name = "CID")
    public String companyID;

    @Column(name = "ALARM_PATTERN_CD")
    public String alarmPatternCD;

    @Column(name = "CATEGORY")
    public int category;

    @Column(name = "CATEGORY_CD")
    public int categoryCode;

}
