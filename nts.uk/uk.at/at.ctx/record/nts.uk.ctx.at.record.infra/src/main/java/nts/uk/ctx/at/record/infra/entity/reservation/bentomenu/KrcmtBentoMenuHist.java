package nts.uk.ctx.at.record.infra.entity.reservation.bentomenu;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.record.dom.reservation.bento.BentoMenuHistory;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "KRCMT_BENTO_MENU_HIST")
@AllArgsConstructor
@NoArgsConstructor
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
	public static List<KrcmtBentoMenuHist> toEntity(BentoMenuHistory domain){
		List<KrcmtBentoMenuHist> result = new ArrayList<>();
		domain.getHistoryItems().forEach((item) -> {
			result.add((new KrcmtBentoMenuHist(new KrcmtBentoMenuHistPK(domain.companyId,item.identifier()),
					AppContexts.user().contractCode(),item.start(),item.end())));
		});
		return result;
	}
}
