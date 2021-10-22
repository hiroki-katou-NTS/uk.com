package nts.uk.ctx.at.record.infra.entity.reservation.reservationsetting;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.PrimaryKeyJoinColumns;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.arc.enums.EnumAdaptor;
import nts.uk.ctx.at.record.dom.reservation.bento.BentoReservationTime;
import nts.uk.ctx.at.record.dom.reservation.bento.rules.BentoReservationTimeName;
import nts.uk.ctx.at.record.dom.reservation.bentomenu.closingtime.ReservationClosingTimeFrame;
import nts.uk.ctx.at.record.dom.reservation.reservationsetting.ReservationRecTime;
import nts.uk.ctx.at.record.dom.reservation.reservationsetting.ReservationRecTimeZone;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.infra.data.entity.ContractUkJpaEntity;

@Entity
@Table(name = "KRCMT_PREPARATION_RECTIME")
@AllArgsConstructor
@NoArgsConstructor
public class KrcmtReservationRecTime extends ContractUkJpaEntity {
	
	@EmbeddedId
    public KrcmpReservationRecTimePK pk;
	
	@Column(name = "TIME＿NAME")
    public String timeName;
	
	@Column(name = "START_TIME")
    public int startTime;
	
	@Column(name = "END_TIME")
    public int endTime;
	
	@ManyToOne
    @PrimaryKeyJoinColumns({
    	@PrimaryKeyJoinColumn(name = "CID", referencedColumnName = "CID")
    })
	public KrcmtReservationSetting krcmtReservationSetting;
	
	@Override
	protected Object getKey() {
		return pk;
	}
	
	public ReservationRecTimeZone toDomain() {
		return new ReservationRecTimeZone(
				new ReservationRecTime(
						new BentoReservationTimeName(timeName), 
						new BentoReservationTime(startTime) , 
						new BentoReservationTime(endTime)), 
				EnumAdaptor.valueOf(pk.timeNo, ReservationClosingTimeFrame.class));
	}
	
	public static KrcmtReservationRecTime fromDomain(ReservationRecTimeZone reservationRecTimeZone) {
		return new KrcmtReservationRecTime(
				new KrcmpReservationRecTimePK(
						AppContexts.user().companyId(), 
						reservationRecTimeZone.getFrameNo().value), 
				reservationRecTimeZone.getReceptionHours().getReceptionName().v(), 
				reservationRecTimeZone.getReceptionHours().getStartTime().v(), 
				reservationRecTimeZone.getReceptionHours().getEndTime().v(), 
				null);
	}

}
