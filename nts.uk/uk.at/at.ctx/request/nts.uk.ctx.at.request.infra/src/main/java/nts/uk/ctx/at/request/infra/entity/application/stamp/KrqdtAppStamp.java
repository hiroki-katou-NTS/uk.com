package nts.uk.ctx.at.request.infra.entity.application.stamp;




import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.uk.shr.infra.data.entity.ContractUkJpaEntity;

@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="KRQDT_APP_STAMP")
public class KrqdtAppStamp extends ContractUkJpaEntity{
	@EmbeddedId
	public KrqdtAppStampPK krqdtAppStampPK;
	
	@Column(name="START_TIME")
	public Integer startTime;
	
	@Column(name = "END_TIME")
	public Integer endTime;
	
	@Column(name = "START_CANCEL_ATR")
	public Integer startCancelAtr;
	
	@Column(name = "END_CANCEL_ATR")
	public Integer endCancelAtr;
	
	@Column(name = "GO_OUT_ATR")
	public Integer goOutAtr;

	/** 応援勤務NO */
	@Column(name = "SUPPORT_WORK_NO")
	public Integer supportWorkNo;

	/** 職場ID */
	@Column(name = "STAMP_WKP_ID")
	public String stampWkpId;

	/** 勤務場所コード */
	@Column(name = "STAMP_WK_LOCATION_CD")
	public String stampWkLocationCd;

	@Override
	protected Object getKey() {
		return krqdtAppStampPK;
	}

}
