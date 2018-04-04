package nts.uk.ctx.at.shared.infra.entity.workrecord.monthlyresults.roleopenperiod;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

/* (non-Javadoc)
 * @see java.lang.Object#hashCode()
 */
@Getter
@Setter
@EqualsAndHashCode
@Embeddable
public class KrcstRoleOfOpenPeriodPK implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -2891128043962831805L;
	
	/** The cid. */
    @Column(name = "CID")
    private String cid;
    
    /** The workday off fr no. */
	@Column(name="BREAKOUT_FR_NO")
	private BigDecimal breakoutOffFrNo;
    
    /**
     * Instantiates a new krcst role open period PK.
     */
    public KrcstRoleOfOpenPeriodPK() {
    	super();
    }
    
    /**
     * Instantiates a new krcst role open period PK.
     *
     * @param cid the cid
     */
    public KrcstRoleOfOpenPeriodPK(String cid, BigDecimal breakoutOffFrNo) {
        this.cid = cid;
        this.breakoutOffFrNo = breakoutOffFrNo;
    }
}
