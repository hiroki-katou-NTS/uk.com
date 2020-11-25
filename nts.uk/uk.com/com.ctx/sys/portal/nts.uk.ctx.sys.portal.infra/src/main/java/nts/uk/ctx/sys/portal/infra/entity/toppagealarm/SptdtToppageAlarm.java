package nts.uk.ctx.sys.portal.infra.entity.toppagealarm;

import java.io.Serializable;
import java.util.Optional;

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
import nts.uk.ctx.sys.portal.dom.toppagealarm.DisplayMessage;
import nts.uk.ctx.sys.portal.dom.toppagealarm.IdentificationKey;
import nts.uk.ctx.sys.portal.dom.toppagealarm.LinkURL;
import nts.uk.ctx.sys.portal.dom.toppagealarm.ToppageAlarmData;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "SPTDT_TOPPAGE_ALARM")
public class SptdtToppageAlarm extends UkJpaEntity implements Serializable {
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
	@Column(name = "CRT_DATETIME")
	private GeneralDateTime crtDatetime;
	
	@Basic(optional = false)
	@Column(name = "MESSEGE")
	private String messege;
	
	@Column(name = "LINK_URL")
	private String linkUrl;
	
	@Override
	protected Object getKey() {
		return this.pk;
	}
	
	public ToppageAlarmData toDomain() {
		return ToppageAlarmData.builder()
				.cid(this.pk.cId)
				.alarmClassification(AlarmClassification.valueOf(Integer.parseInt(this.pk.alarmCls)))
				.identificationKey(new IdentificationKey(this.pk.idenKey))
				.displaySId(this.pk.dispSid)
				.displayAtr(DisplayAtr.valueOf(Integer.parseInt(this.pk.dispAtr)))
				.occurrenceDateTime(this.crtDatetime)
				.displayMessage(new DisplayMessage(this.messege))
				.linkUrl(Optional.ofNullable(this.linkUrl).map(LinkURL::new))
				.build();
	}
	
	public static SptdtToppageAlarm toEntity(String contractCd, ToppageAlarmData domain) {
		return SptdtToppageAlarm.builder()
				.pk(SptdtToppageAlarmPK.builder()
						.cId(domain.getCid())
						.alarmCls(String.valueOf(domain.getAlarmClassification().value))
						.idenKey(domain.getIdentificationKey().v())
						.dispSid(domain.getDisplaySId())
						.dispAtr(String.valueOf(domain.getDisplayAtr().value))
						.build())
				.contractCd(contractCd)
				.crtDatetime(domain.getOccurrenceDateTime())
				.messege(domain.getDisplayMessage().v())
				.linkUrl(domain.getLinkUrl().map(LinkURL::v).orElse(null))
				.build();
	}

}
