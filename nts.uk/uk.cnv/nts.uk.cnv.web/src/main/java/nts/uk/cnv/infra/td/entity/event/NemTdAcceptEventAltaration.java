package nts.uk.cnv.infra.td.entity.event;

import java.io.Serializable;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.PrimaryKeyJoinColumns;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import nts.arc.layer.infra.data.entity.JpaEntity;

@Getter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "NEM_TD_ACCEPT_EVENT_ALT")
public class NemTdAcceptEventAltaration extends JpaEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private NemTdAcceptEventAltarationPk pk;

	@ManyToOne
    @PrimaryKeyJoinColumns({
    	@PrimaryKeyJoinColumn(name = "EVENT_ID", referencedColumnName = "EVENT_ID")
    })
	public NemTdAcceptEvent event;

	protected Object getKey() {
		return pk;
	}

}
