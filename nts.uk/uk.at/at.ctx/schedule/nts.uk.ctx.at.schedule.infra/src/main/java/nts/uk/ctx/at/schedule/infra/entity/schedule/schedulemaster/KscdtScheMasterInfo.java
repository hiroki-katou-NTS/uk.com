package nts.uk.ctx.at.schedule.infra.entity.schedule.schedulemaster;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

/**
 * 勤務予定マスタ情報
 * 
 * @author TanLV
 *
 */
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "KSCDT_SCHE_MASTER")
public class KscdtScheMasterInfo extends UkJpaEntity implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@EmbeddedId
	public KscdtScheMasterInfoPK kscdtScheMasterInfoPk;
	
	@Column(name = "EMP_CD")
	public String employmentCd;

	@Column(name = "CLS_CD")
	public String classificationCd;
	
	@Column(name = "WORKTYPE_CD")
	public String workTypeCd;
	
	@Column(name = "JOB_ID")
	public String jobId;
	
	@Column(name = "WKP_ID")
	public String workplaceId;
	
	@Override
	protected Object getKey() {
		// TODO Auto-generated method stub
		return kscdtScheMasterInfoPk;
	}
}
