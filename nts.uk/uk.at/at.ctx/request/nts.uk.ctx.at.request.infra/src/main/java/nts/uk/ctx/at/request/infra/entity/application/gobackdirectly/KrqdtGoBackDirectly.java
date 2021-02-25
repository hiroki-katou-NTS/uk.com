package nts.uk.ctx.at.request.infra.entity.application.gobackdirectly;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.uk.shr.infra.data.entity.UkJpaEntity;



@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="KRQDT_APP_GOBACK_DIRECTLY")
public class KrqdtGoBackDirectly extends UkJpaEntity implements Serializable{
	
	private static final long serialVersionUID = 1L;
	@EmbeddedId
	public KrqdtGoBackDirectlyPK krqdtGoBackDirectlyPK;
	
	@Column(name="CONTRACT_CD")
	public String contractCd;
	
	@Column(name="WORK_TYPE_CD")
	public String workTypeCD;
	
	@Column(name="WORK_TIME_CD")
	public String workTimeCD;
	
	@Column(name="WORK_CHANGE_ATR")
	public Integer workChangeAtr;
	
	@Column(name="GO_WORK_ATR")
	public Integer goWorkAtr;

	@Column(name="BACK_HOME_ATR")
	public Integer backHomeAtr;

	@Override
	protected Object getKey() {
		return krqdtGoBackDirectlyPK;
	}
	
}
