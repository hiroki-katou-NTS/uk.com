package nts.uk.ctx.at.record.infra.entity.reservation.bentomenu;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import nts.arc.time.GeneralDate;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

@Entity
@Table(name = "KRCMT_BENTO_MENU_HIST")
@AllArgsConstructor
public class KrcmtBentoMenuHist extends UkJpaEntity {
	
	@EmbeddedId
	public KrcmtBentoMenuHistPK pk;
	
	@Column(name = "CONTRACT_CD")
	public String contractCD;
	
	@Column(name = "START_YMD")
	public GeneralDate startDate;
	
	@Column(name = "END_YMD")
	public GeneralDate endDate;

	@Override
	protected Object getKey() {
		return pk;
	}
	
}
