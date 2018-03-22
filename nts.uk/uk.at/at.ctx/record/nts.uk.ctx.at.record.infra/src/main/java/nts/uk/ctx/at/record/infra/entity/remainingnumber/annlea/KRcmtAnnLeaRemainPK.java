package nts.uk.ctx.at.record.infra.entity.remainingnumber.annlea;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Embeddable
@AllArgsConstructor
@NoArgsConstructor
public class KRcmtAnnLeaRemainPK {
	@Column(name = "ANNLEAV_ID")
    public String annLeavID;
}
