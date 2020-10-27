package nts.uk.ctx.at.request.infra.entity.setting.company.divergencereason;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.uk.shr.infra.data.entity.ContractUkJpaEntity;

/**
 *
 * @author loivt
 */
@Entity
@Table(name = "KRQMT_APP_DIVERGEN_REASON")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class KrqmtAppDivergenReason extends ContractUkJpaEntity implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected KrqmtAppDivergenReasonPK krqmtAppDivergenReasonPK;

    @Column(name = "DISPORDER")
    private int disporder;
    
    @Column(name = "REASON_TEMP")
    private String reasonTemp;
    
    @Column(name = "DEFAULT_FLG")
    private int defaultFlg;
	@Override
	protected Object getKey() {
		return krqmtAppDivergenReasonPK;
	}
}

