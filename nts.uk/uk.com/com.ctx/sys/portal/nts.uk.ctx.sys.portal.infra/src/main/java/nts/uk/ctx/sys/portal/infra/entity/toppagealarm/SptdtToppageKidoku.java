package nts.uk.ctx.sys.portal.infra.entity.toppagealarm;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Version;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import nts.arc.time.GeneralDateTime;
import nts.uk.ctx.sys.portal.dom.toppagealarm.AlarmClassification;
import nts.uk.ctx.sys.portal.dom.toppagealarm.DisplayAtr;
import nts.uk.ctx.sys.portal.dom.toppagealarm.ToppageAlarmLog;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "SPTDT_TOPPAGE_KIDOKU")
public class SptdtToppageKidoku extends UkJpaEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Version
	@Column(name = "EXCLUS_VER")
	private long version;
	
	@EmbeddedId
	private SptdtToppageAlarmPK pk;
	
	// column 契約コード
	@Basic(optional = false)
	@Column(name = "CONTRACT_CD")
	private String contractCd;
	
	@Basic(optional = false)
	@Column(name = "ALREADY_DATETIME")
	private GeneralDateTime alreadyDatetime;
	
	@Override
	protected Object getKey() {
		return this.pk;
	}
	
	public ToppageAlarmLog toDomain() {
		return ToppageAlarmLog.builder()
				.cid(this.pk.cId)
				.alarmClassification(AlarmClassification.valueOf(Integer.parseInt(this.pk.alarmCls)))
				// TODO
				.identificationKey(this.pk.idenKey)
				.displaySId(this.pk.dispSid)
				.displayAtr(DisplayAtr.valueOf(Integer.parseInt(this.pk.dispAtr)))
				.alreadyDatetime(this.alreadyDatetime)
				.build();
	}
	
	public static SptdtToppageKidoku toEntity(String contractCd, ToppageAlarmLog domain) {
		return SptdtToppageKidoku.builder()
				.pk(SptdtToppageAlarmPK.builder()
						.cId(domain.getCid())
						.alarmCls(String.valueOf(domain.getAlarmClassification().value))
						.idenKey(domain.getIdentificationKey())
						.dispSid(domain.getDisplaySId())
						.dispAtr(String.valueOf(domain.getDisplayAtr().value))
						.build())
				.contractCd(contractCd)
				.alreadyDatetime(domain.getAlreadyDatetime())
				.build();
	}

}
