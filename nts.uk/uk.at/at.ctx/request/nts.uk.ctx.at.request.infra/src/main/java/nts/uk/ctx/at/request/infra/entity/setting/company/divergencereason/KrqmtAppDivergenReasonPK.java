package nts.uk.ctx.at.request.infra.entity.setting.company.divergencereason;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 *
 * @author loivt
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Embeddable
public class KrqmtAppDivergenReasonPK implements Serializable {

	private static final long serialVersionUID = 1L;
	
    @Column(name = "CID")
    private String cid;
    
    @Column(name = "APP_TYPE")
    private int appType;
    
    @Column(name = "REASON_ID")
    private String reasonId;

    
}

