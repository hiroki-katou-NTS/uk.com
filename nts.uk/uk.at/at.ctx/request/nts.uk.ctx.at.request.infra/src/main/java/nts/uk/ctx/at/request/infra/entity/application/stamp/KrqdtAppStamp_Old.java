package nts.uk.ctx.at.request.infra.entity.application.stamp;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;
import javax.persistence.Table;
//import javax.persistence.Version;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import nts.uk.shr.infra.data.entity.UkJpaEntity;
/**
 * 
 * @author Doan Duy Hung
 *
 */
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="KRQDT_APP_STAMP")
@Builder
public class KrqdtAppStamp_Old extends UkJpaEntity {
	
	@EmbeddedId
	public KrqdpAppStamp krqdpAppStampPK;
	
	@Column(name="STAMP_REQUEST_MODE")
	public Integer stampRequestMode;
	
	@Column(name="COMBINATION_ATR")
	public Integer combinationAtr;

	@Column(name="APP_TIME")
	public Integer appTime;
	
	@OneToMany(targetEntity=KrqdtAppStampDetail.class, cascade = CascadeType.ALL, mappedBy = "krqdtAppStamp", orphanRemoval = true)
	@JoinTable(name = "KRQDT_APP_STAMP_DETAILS")
	public List<KrqdtAppStampDetail> krqdtAppStampDetails;
	
	@Override
	protected Object getKey() {
		return krqdpAppStampPK;
	}

}
