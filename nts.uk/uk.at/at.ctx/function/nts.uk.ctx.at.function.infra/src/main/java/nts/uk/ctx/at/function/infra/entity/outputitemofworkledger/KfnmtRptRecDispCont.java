package nts.uk.ctx.at.function.infra.entity.outputitemofworkledger;


import lombok.AllArgsConstructor;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * ENTITY: 	勤務台帳の表示内容
 */
@Entity
@Table(name = "KFNMT_RPT_REC_DISP_CONT")
@AllArgsConstructor
public class KfnmtRptRecDispCont extends UkJpaEntity implements Serializable {
    public static final long serialVersionUID = 1L;


    @Override
    protected Object getKey() {
        return null;
    }
}
