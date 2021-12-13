package nts.uk.ctx.at.record.infra.entity.reservation.bentomenu;

import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.arc.time.GeneralDate;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.record.dom.reservation.bentomenu.BentoMenuHistory;
import nts.uk.shr.com.context.AppContexts;
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
	
	@OneToMany(targetEntity = KrcmtBento.class, mappedBy = "krcmtBentoMenuHist", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
	@JoinTable(name = "KRCMT_BENTO")
	private List<KrcmtBento> krcmtBentoLst;

	@Override
	protected Object getKey() {
		return pk;
	}
	
	public static KrcmtBentoMenuHist fromDomain(BentoMenuHistory domain) {
		return new KrcmtBentoMenuHist(
				new KrcmtBentoMenuHistPK(AppContexts.user().companyId(), domain.getHistoryID()), 
				domain.getHistoryItem().span().start(), 
				domain.getHistoryItem().span().end(), 
				domain.getMenu().stream().map(x -> KrcmtBento.fromDomain(x, domain.getHistoryID())).collect(Collectors.toList()));
	}
	
	public BentoMenuHistory toDomain() {
		return new BentoMenuHistory(
				pk.histID, 
				new DateHistoryItem(pk.histID, new DatePeriod(startDate, endDate)), 
				krcmtBentoLst.stream().map(x -> x.toDomain()).collect(Collectors.toList()));
	}
}
