package nts.uk.ctx.pr.transfer.infra.entity.emppaymentinfo;

import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.NoArgsConstructor;
import nts.arc.time.YearMonth;
import nts.uk.ctx.pr.transfer.dom.emppaymentinfo.EmployeeSalaryPaymentInfor;
import nts.uk.shr.com.history.YearMonthHistoryItem;
import nts.arc.time.calendar.period.YearMonthPeriod;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

/**
 * 
 * @author HungTT - 社員給与支払情報
 *
 */

@NoArgsConstructor
@Entity
@Table(name = "QBTMT_EMP_SAL_PAY_INFO")
public class QbtmtEmpSalPayInfo extends UkJpaEntity {

	@EmbeddedId
	public QbtmtEmpPayInfoPk pk;

	@Column(name = "YM_START")
	public Integer startYm;

	@Column(name = "YM_END")
	public Integer endYm;

	@Override
	protected Object getKey() {
		return this.pk;
	}

	public QbtmtEmpSalPayInfo(String employeeId, String historyId, YearMonth start, YearMonth end) {
		super();
		this.pk = new QbtmtEmpPayInfoPk(employeeId, historyId);
		this.startYm = start.v();
		this.endYm = end.v();
	}

	public static List<QbtmtEmpSalPayInfo> fromDomain(EmployeeSalaryPaymentInfor domain) {
		List<YearMonthHistoryItem> periods = domain.items();
		return periods.stream()
				.map(p -> new QbtmtEmpSalPayInfo(domain.getEmployeeId(), p.identifier(), p.start(), p.end()))
				.collect(Collectors.toList());
	}

	public static EmployeeSalaryPaymentInfor toDomain(List<QbtmtEmpSalPayInfo> listEntity) {
		if (listEntity.isEmpty()) return null;
		List<YearMonthHistoryItem> periods = listEntity.stream()
				.map(e -> new YearMonthHistoryItem(e.pk.historyId,
						new YearMonthPeriod(new YearMonth(e.startYm), new YearMonth(e.endYm))))
				.collect(Collectors.toList());
		return new EmployeeSalaryPaymentInfor(listEntity.get(0).pk.employeeId, periods);
	}

}
