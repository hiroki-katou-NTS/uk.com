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
import nts.uk.cnv.dom.td.event.accept.AcceptEvent;
import nts.uk.cnv.dom.td.event.EventDetail;

/**
 * 納品イベント
 * @author ai_muto
 *
 */
@Getter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "NEM_TD_ACCEPT_EVENT")
public class NemTdAcceptEvent extends JpaEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "EVENT_ID")
	private String eventId;

	@Column(name = "ACCEPTED_TIME")
	private GeneralDateTime datetime;

	@Column(name = "NAME")
	private String name;

	@Column(name = "USER_NAME")
	private String userName;

	@OneToMany(targetEntity=NemTdAcceptEventAltaration.class, mappedBy="event", cascade=CascadeType.ALL, fetch = FetchType.LAZY)
	public List<NemTdAcceptEventAltaration> alterations;

	@Override
	protected Object getKey() {
		return eventId;
	}

	public AcceptEvent toDomain() {
		return new AcceptEvent(
				new EventId(this.eventId),
				new EventDetail(
					this.name,
					this.datetime,
					this.userName,
					alterations.stream()
						.map(entity -> entity.getPk().getAlterationId())
						.collect(Collectors.toList())
					)
			);
	}

	public static NemTdAcceptEvent toEntity(AcceptEvent acceptEvent) {
		return new NemTdAcceptEvent(
					acceptEvent.getEventId().getId(),
					acceptEvent.getDetail().getDatetime(),
					acceptEvent.getDetail().getName(),
					acceptEvent.getDetail().getUserName(),
					acceptEvent.getDetail().getAlterationIds().stream()
						.map(altrationId -> new NemTdAcceptEventAltaration(
								new NemTdAcceptEventAltarationPk(acceptEvent.getEventId().getId(), altrationId),
								null))
						.collect(Collectors.toList())
				);
	}
}
