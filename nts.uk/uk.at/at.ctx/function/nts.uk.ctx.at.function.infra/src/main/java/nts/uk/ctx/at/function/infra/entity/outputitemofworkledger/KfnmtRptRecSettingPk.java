package nts.uk.ctx.at.function.infra.entity.outputitemofworkledger;


import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
public class KfnmtRptRecSettingPk implements Serializable {

    public static final long serialVersionUID = 1L;

    @Column(name = "ID")
    public String iD;


}
