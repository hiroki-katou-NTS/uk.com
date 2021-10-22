package nts.uk.ctx.at.record.infra.entity.reservation.bentomenu;

import java.util.ArrayList;
import java.util.List;

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
import nts.uk.shr.com.history.DateHistoryItem;
import nts.uk.shr.infra.data.entity.ContractUkJpaEntity;

@Getter
@Setter
@Entity
@Table(name = "KRCMT_BENTO_MENU_HIST")
@AllArgsConstructor
@NoArgsConstructor
public class KrcmtBentoMenuHist extends ContractUkJpaEntity {
	
	@EmbeddedId
	public KrcmtBentoMenuHistPK pk;
	
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
					item.start(),item.end())));
		});
		return result;
	}

	public KrcmtBentoMenuHist update(DateHistoryItem domain){
		this.endDate = domain.end();
		this.startDate = domain.start();
		return this;
	}

}
