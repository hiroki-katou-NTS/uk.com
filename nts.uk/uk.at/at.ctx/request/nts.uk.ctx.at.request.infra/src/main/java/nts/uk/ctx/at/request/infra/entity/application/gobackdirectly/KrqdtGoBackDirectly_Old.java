package nts.uk.ctx.at.request.infra.entity.application.gobackdirectly;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Version;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.uk.shr.infra.data.entity.UkJpaEntity;
/**
 * 
 * @author ducpm
 *
 */
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Entity
@Table(name="KRQDT_GO_BACK_DIRECTLY")
public class KrqdtGoBackDirectly_Old extends UkJpaEntity implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	public KrqdtGoBackDirectlyPK_Old krqdtGoBackDirectlyPK;
	
	@Version
	@Column(name="EXCLUS_VER")
	public Long version;
	
	@Column(name="WORK_TYPE_CD")
	public String workTypeCD;
	
	@Column(name="SIFT_CD")
	public String siftCD;
	
	@Column(name="WORK_CHANGE_ATR")
	public Integer workChangeAtr;
	
	@Column(name="WORK_TIME_START1")
	public Integer workTimeStart1;
	
	@Column(name="WORK_TIME_END1")
	public Integer workTimeEnd1;
	
	@Column(name="WORK_LOCATION_CD1")
	public String workLocationCd1;
	
	@Column(name="GO_WORK_ATR1")
	public Integer goWorkAtr1;

	@Column(name="BACK_HOME_ATR1")
	public Integer backHomeAtr1;
	
	@Column(name="WORK_TIME_START2")
	public Integer workTimeStart2;
	
	@Column(name="WORK_TIME_END2")
	public Integer workTimeEnd2;

	@Column(name="WORK_LOCATION_CD2")
	public String workLocationCd2;
	
	@Column(name="GO_WORK_ATR2")
	public Integer goWorkAtr2;

	@Column(name="BACK_HOME_ATR2")
	public Integer backHomeAtr2;
	
	@Override
	protected Object getKey() {
		return krqdtGoBackDirectlyPK;
	}

}
