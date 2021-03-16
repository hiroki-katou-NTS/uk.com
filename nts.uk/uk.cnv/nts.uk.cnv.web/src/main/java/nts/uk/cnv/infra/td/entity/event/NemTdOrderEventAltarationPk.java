package nts.uk.cnv.infra.td.entity.event;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Embeddable
@NoArgsConstructor
@AllArgsConstructor
@NoArgsConstructor
public class NemTdOrderEventAltarationPk implements Serializable {
	private static final long serialVersionUID = 1L;

	@Column(name = "EVENT_ID")
	private String eventId;

	@Column(name = "ALTERATION_ID")
	private String alterationId;
}
