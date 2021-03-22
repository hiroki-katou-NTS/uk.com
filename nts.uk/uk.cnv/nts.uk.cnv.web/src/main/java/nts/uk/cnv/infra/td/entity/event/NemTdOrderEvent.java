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
import nts.uk.cnv.dom.td.event.EventId;
import nts.uk.cnv.dom.td.event.EventDetail;
import nts.uk.cnv.dom.td.event.OrderEvent;

/**
 * 発注イベント
 * @author ai_muto
 *
 */
@Getter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "NEM_TD_ORDER_EVENT")
public class NemTdOrderEvent extends JpaEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "EVENT_ID")
	private String eventId;

	@Column(name = "ORDERED_TIME")
	private GeneralDateTime datetime;

	@Column(name = "NAME")
	private String name;

	@Column(name = "USER_NAME")
	private String userName;

	@OneToMany(targetEntity=NemTdOrderEventAltaration.class, mappedBy="event", cascade=CascadeType.ALL, fetch = FetchType.LAZY)
	public List<NemTdOrderEventAltaration> alterations;

	@Override
	protected Object getKey() {
		return eventId;
	}

	public OrderEvent toDomain() {
		return new OrderEvent(
				new EventId(this.eventId),
				new EventDetail(
					this.name,
					this.datetime,
					this.userName,
					alterations.stream()
						.map(entity -> entity.getPk().getAlterationId())
						.collect(Collectors.toList()))
			);
	}

	public static NemTdOrderEvent toEntity(OrderEvent domain) {
		return new NemTdOrderEvent(
					domain.getEventId().getId(),
					domain.getDetail().getDatetime(),
					domain.getDetail().getName(),
					domain.getDetail().getUserName(),
					domain.getDetail().getAlterationIds().stream()
						.map(altrationId -> new NemTdOrderEventAltaration(
								new NemTdOrderEventAltarationPk(domain.getEventId().getId(), altrationId),
								null))
						.collect(Collectors.toList())
				);
	}
}
