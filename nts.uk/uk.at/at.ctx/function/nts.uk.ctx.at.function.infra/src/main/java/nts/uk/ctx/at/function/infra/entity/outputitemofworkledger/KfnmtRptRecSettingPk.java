package nts.uk.ctx.at.function.infra.entity.outputitemofworkledger;


import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
public class KfnmtRptRecSettingPk implements Serializable {

    public static final long serialVersionUID = 1L;

    // ID->勤務台帳の出力項目.ID
    @Column(name = "ID")
    public String iD;


}
