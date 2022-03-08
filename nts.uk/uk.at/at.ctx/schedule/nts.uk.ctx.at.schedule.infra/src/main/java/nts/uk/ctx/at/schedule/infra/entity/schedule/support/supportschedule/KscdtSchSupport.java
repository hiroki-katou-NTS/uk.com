/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.schedule.infra.entity.schedule.support.supportschedule;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.PrimaryKeyJoinColumns;
import javax.persistence.Table;

import lombok.NoArgsConstructor;
import nts.arc.layer.infra.data.jdbc.map.JpaEntityMapper;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.schedule.dom.schedule.support.supportschedule.SupportScheduleDetail;
import nts.uk.ctx.at.schedule.infra.entity.schedule.workschedule.KscdtSchBasicInfo;
import nts.uk.shr.infra.data.entity.ContractUkJpaEntity;

/**
 * @author laitv
 *
 */
@NoArgsConstructor
@Entity
@Table(name = "KSCDT_SCH_SUPPORT")
public class KscdtSchSupport extends ContractUkJpaEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	@EmbeddedId
	public KscdtSchSupportPK pk;
	
	@Column(name="CID")
	private String cid;

	@Column(name = "START_TS")
	public Integer start;
	
	@Column(name = "END_TS")
	public Integer end;
	
	@Column(name = "RECIPIENT_TARGET_ID")
	public String recipientTargerId;
	
	@Column(name = "RECIPIENT_TARGET_UNIT")
	public int recipientTargerUnit;
	
	@Column(name = "SUPPORT_TYPE")
	public int supportType;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@PrimaryKeyJoinColumns({ @PrimaryKeyJoinColumn(name = "SID", referencedColumnName = "SID"),
			@PrimaryKeyJoinColumn(name = "YMD", referencedColumnName = "YMD") })
	public KscdtSchBasicInfo kscdtSchBasicInfo;
	
	public static KscdtSchSupport toEntity(String cid, String sid, GeneralDate date, int serialNo, SupportScheduleDetail supportScheduleDetail) {
		KscdtSchSupport entity = new KscdtSchSupport(
				new KscdtSchSupportPK(sid, date, serialNo), 
				cid, 
				supportScheduleDetail.getTimeSpan().isPresent() ? supportScheduleDetail.getTimeSpan().get().start() : null, 
				supportScheduleDetail.getTimeSpan().isPresent() ? supportScheduleDetail.getTimeSpan().get().end() : null, 
				supportScheduleDetail.getSupportDestination().getTargetId(), 
				supportScheduleDetail.getSupportDestination().getUnit().value, 
				supportScheduleDetail.getSupportType().getValue());
		return entity;
	}
	
	
	@Override
	protected Object getKey() {
		return this.pk;
	}


	public KscdtSchSupport(KscdtSchSupportPK pk, String cid, Integer start, Integer end, String recipientTargerId,
			int recipientTargerUnit, int supportType ) {
		super();
		this.pk = pk;
		this.cid = cid;
		this.start = start;
		this.end = end;
		this.recipientTargerId = recipientTargerId;
		this.recipientTargerUnit = recipientTargerUnit;
		this.supportType = supportType;
	}
}
