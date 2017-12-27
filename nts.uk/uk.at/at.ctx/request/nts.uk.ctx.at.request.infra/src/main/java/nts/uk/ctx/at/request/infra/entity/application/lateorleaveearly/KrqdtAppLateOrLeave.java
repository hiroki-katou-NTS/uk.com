package nts.uk.ctx.at.request.infra.entity.application.lateorleaveearly;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.PrimaryKeyJoinColumns;
import javax.persistence.Table;
import javax.persistence.Version;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.request.infra.entity.application.common.KrqdtApplication_New;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

/**
 * author hieult
 */
@Entity
@Table(name = "KRQDT_APP_LATE_OR_LEAVE")
@AllArgsConstructor
@NoArgsConstructor
public class KrqdtAppLateOrLeave  extends UkJpaEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	@EmbeddedId
	public KrqdtAppLateOrLeavePK krqdtAppLateOrLeavePK;
	
	@Version
	@Column(name="EXCLUS_VER")
	public Long version;
	
	@Column(name = "ACTUAL_CANCEL_ATR")
	public int actualCancelAtr;
	
	@Column(name = "EARLY1")
	public int early1;
	
	@Column(name = "EARLY_TIME1")
	public int earlyTime1;
	
	@Column(name = "LATE1")
	public int late1;
	
	@Column(name = "LATE_TIME1")
	public int lateTime1;
	
	@Column(name = "EARLY2")
	public int early2;
	
	@Column(name = "EARLY_TIME2")
	public int earlyTime2;
	
	@Column(name = "LATE2")
	public int late2;
	
	@Column(name = "LATE_TIME2")
	public int lateTime2;

	@OneToOne(targetEntity=KrqdtApplication_New.class, cascade = CascadeType.ALL, orphanRemoval = true)
	@PrimaryKeyJoinColumns({
		@PrimaryKeyJoinColumn(name="CID",referencedColumnName="CID"),
		@PrimaryKeyJoinColumn(name="APP_ID",referencedColumnName="APP_ID")
	})
	public KrqdtApplication_New kafdtApplication;
	
	@Override
	protected Object getKey() {
		return krqdtAppLateOrLeavePK;
	}

}
