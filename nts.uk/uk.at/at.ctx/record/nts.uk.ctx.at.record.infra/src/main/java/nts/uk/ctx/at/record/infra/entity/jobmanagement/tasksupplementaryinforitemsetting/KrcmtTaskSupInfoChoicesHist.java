package nts.uk.ctx.at.record.infra.entity.jobmanagement.tasksupplementaryinforitemsetting;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.arc.time.GeneralDate;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.record.dom.jobmanagement.tasksupplementaryinforitemsetting.TaskSupInfoChoicesHistory;
import nts.uk.shr.com.history.DateHistoryItem;
import nts.uk.shr.infra.data.entity.ContractCompanyUkJpaEntity;

/**
 * @author tutt
 * @name 作業補足情報の選択肢履歴 TaskSupInfoChoicesHistory
 *
 */
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "KRCMT_TAST_SUP_INFO_CHOICES_HIST")
public class KrcmtTaskSupInfoChoicesHist extends ContractCompanyUkJpaEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	@EmbeddedId
	public KrcmtTaskSupInfoChoicesHistPK pk;

	@Column(name = "START_DATE")
	public GeneralDate startDate;

	@Column(name = "END_DATE")
	public GeneralDate endDate;

	@Override
	protected Object getKey() {
		return this.pk;
	}
	
	public KrcmtTaskSupInfoChoicesHist(TaskSupInfoChoicesHistory domain) {
		
	}

	public static List<DateHistoryItem> toDomain(List<KrcmtTaskSupInfoChoicesHist> entities) {
		List<DateHistoryItem> dateHistoryItems = new ArrayList<>();

		for (KrcmtTaskSupInfoChoicesHist hist : entities) {
			dateHistoryItems.add(new DateHistoryItem(hist.pk.histId, new DatePeriod(hist.startDate, hist.endDate)));
		}

		return dateHistoryItems;
	}

}
