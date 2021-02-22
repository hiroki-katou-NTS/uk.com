package nts.uk.ctx.at.request.infra.entity.application.overtime;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.PrimaryKeyJoinColumns;
import javax.persistence.Table;
import javax.persistence.Version;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.request.dom.application.overtime.time36.Time36AgreeUpperLimitPerMonth;
import nts.uk.shr.infra.data.entity.ContractUkJpaEntity;
/**
 * Refactor5
 * @author hoangnd
 *
 */
@Entity
@Table(name = "KRQDT_APP_OVERTIME_DET_M")
@AllArgsConstructor
@NoArgsConstructor
public class KrqdtAppOverTimeDetM extends ContractUkJpaEntity implements Serializable{
	
	public static final long serialVersionUID = 1L;
	
	@EmbeddedId
	public KrqdtAppOverTimeDetMPK krqdtAppOverTimeDetMPK;
	
	@Version
	@Column(name = "EXCLUS_VER")
	public Long version;
	
	@Column(name = "AVE_TIME")
	public Integer avTime;
	
	@Column(name = "TOTAL_TIME")
	public Integer totalTime;
	
	@ManyToOne
    @PrimaryKeyJoinColumns({
    	@PrimaryKeyJoinColumn(name="CID", referencedColumnName="CID"),
    	@PrimaryKeyJoinColumn(name="APP_ID", referencedColumnName="APP_ID")
    })
	public KrqdtAppOvertimeDetail appOvertimeDetail;
	@Override
	protected Object getKey() {
		return null;
	}
	
	
	public Time36AgreeUpperLimitPerMonth toDomain() {
		if (getKey() == null) return null;
		Time36AgreeUpperLimitPerMonth time36AgreeUpperLimitPerMonth = new Time36AgreeUpperLimitPerMonth(
				krqdtAppOverTimeDetMPK.startYm,
				krqdtAppOverTimeDetMPK.endYm,
				avTime,
				totalTime);
		return time36AgreeUpperLimitPerMonth;
	}

}
