package nts.uk.ctx.at.shared.infra.entity.workrecord.monthlyresults.roleofovertimework;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

/**
 * The Class KrcstRoleOTWorkPK.
 */
@Setter
@Getter
@EqualsAndHashCode
@Embeddable
public class KrcstRoleOTWorkPK implements Serializable{
/** The Constant serialVersionUID. */
	private static final long serialVersionUID = -2657027089670913624L;
	
	/** The cid. */
	@Column(name = "CID")
    private String cid;
	
	/** The overtime fr no. */
	@Column(name="OVERTIME_FR_NO")
	private BigDecimal overtimeFrNo;

	/**
	 * Instantiates a new krcst role ot work PK.
	 */
	public KrcstRoleOTWorkPK() {
    	super();
    }
	
	/**
	 * Instantiates a new krcst role ot work PK.
	 *
	 * @param cid the cid
	 */
	public KrcstRoleOTWorkPK(String cid, BigDecimal overtimeFrNo) {
        this.cid = cid;
        this.overtimeFrNo = overtimeFrNo;
    }
}
