package nts.uk.ctx.pr.core.infra.entity.wageprovision.wagetable;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.arc.time.YearMonth;
import nts.uk.ctx.pr.core.dom.wageprovision.wagetable.WageTableHistory;
import nts.uk.shr.com.history.YearMonthHistoryItem;
import nts.arc.time.calendar.period.YearMonthPeriod;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

/**
 * 
 * @author HungTT - entity 賃金テーブル履歴
 *
 */

@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "QPBMT_WAGE_TABLE_HIST")
public class QpbmtWageTableHistory extends UkJpaEntity {

	@EmbeddedId
	public QpbmtWageTableHistoryPk pk;

	@Column(name = "START_YM")
	public Integer startYm;

	@Column(name = "END_YM")
	public Integer endYm;

	@Override
	protected Object getKey() {
		return this.pk;
	}

	public static WageTableHistory toDomain(List<QpbmtWageTableHistory> histories) {
		if (histories == null || histories.isEmpty())
			return null;
		List<YearMonthHistoryItem> validityPeriods = new ArrayList<>();

		for (QpbmtWageTableHistory hist : histories) {
			validityPeriods.add(new YearMonthHistoryItem(hist.pk.historyId,
					new YearMonthPeriod(new YearMonth(hist.startYm), new YearMonth(hist.endYm))));
		}
		return new WageTableHistory(histories.get(0).pk.companyId, histories.get(0).pk.code, validityPeriods);
	}

	public static List<QpbmtWageTableHistory> fromDomain(WageTableHistory domain) {
		return domain.getValidityPeriods().stream()
				.map(p -> new QpbmtWageTableHistory(
						new QpbmtWageTableHistoryPk(domain.getCid(), domain.getWageTableCode().v(), p.identifier()),
						p.start().v(), p.end().v()))
				.collect(Collectors.toList());
	}

}
