package nts.uk.cnv.infra.td.entity.event;

import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import nts.arc.layer.infra.data.entity.JpaEntity;
import nts.arc.time.GeneralDateTime;
import nts.uk.cnv.dom.td.event.DeliveryEvent;
import nts.uk.cnv.dom.td.event.EventId;
import nts.uk.cnv.dom.td.event.EventMetaData;

/**
 * 納品イベント
 * @author ai_muto
 *
 */
@Getter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "NEM_TD_DELIVERY_EVENT")
public class NemTdDeliveryEvent extends JpaEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "EVENT_ID")
	private String eventId;

	@Column(name = "DELIVERED_TIME")
	private GeneralDateTime datetime;

	@Column(name = "NAME")
	private String name;

	@Column(name = "USER_NAME")
	private String userName;

	@OneToMany(targetEntity=NemTdDeliveryEventAltaration.class, mappedBy="event", cascade=CascadeType.ALL, fetch = FetchType.LAZY)
	public List<NemTdDeliveryEventAltaration> alterations;

	@Override
	protected Object getKey() {
		return eventId;
	}

	public DeliveryEvent toDomain() {
		return new DeliveryEvent(
				new EventId(this.eventId),
				this.datetime,
				new EventMetaData(
					this.name,
					this.userName),
				alterations.stream()
					.map(entity -> entity.getPk().getAlterationId())
					.collect(Collectors.toList())
			);
	}

	public static NemTdDeliveryEvent toEntity(DeliveryEvent deliveryEvent) {
		return new NemTdDeliveryEvent(
					deliveryEvent.getEventId().getId(),
					deliveryEvent.getDatetime(),
					deliveryEvent.getMeta().getName(),
					deliveryEvent.getMeta().getUserName(),
					deliveryEvent.getAlterationIds().stream()
						.map(altrationId -> new NemTdDeliveryEventAltaration(
								new NemTdDeliveryEventAltarationPk(deliveryEvent.getEventId().getId(), altrationId),
								null))
						.collect(Collectors.toList())
				);
	}
}
