package nts.uk.ctx.pr.report.app.payment.comparing.confirm.find;

import java.math.BigDecimal;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.pr.report.dom.payment.comparing.confirm.ComfirmDifferentRepository;
import nts.uk.ctx.pr.report.dom.payment.comparing.confirm.DetailDifferential;
import nts.uk.ctx.pr.report.dom.payment.comparing.confirm.PaycompConfirm;
import nts.uk.shr.com.context.AppContexts;

@Stateless
public class DetailDifferentialFinder {

	@Inject
	private ComfirmDifferentRepository comfirmDiffRepository;

	public List<DetailDifferentialDto> getDetailDifferential(int processingYMEarlier, int processingYMLater,
			List<String> personIDs) {
		String companyCode = AppContexts.user().companyCode();
		List<DetailDifferential> detailDifferential1 = this.comfirmDiffRepository
				.getDetailDifferentialWithEarlyYM(companyCode, processingYMEarlier, personIDs);
		List<DetailDifferential> detailDifferential2 = this.comfirmDiffRepository
				.getDetailDifferentialWithLaterYM(companyCode, processingYMLater, personIDs);
		List<PaycompConfirm> payCompComfirm = this.comfirmDiffRepository.getPayCompComfirm(companyCode, personIDs,
				processingYMEarlier, processingYMLater);

		List<DetailDifferential> detailDifferential = detailDifferential1.stream().map(s -> {
			Optional<DetailDifferential> detalDiff = detailDifferential2.stream()
					.filter(f -> s.getEmployeeCode().v().equals(f.getEmployeeCode().v())
							&& s.getItemCode().v().equals(f.getItemCode().v())
							&& s.getCategoryAtr().value == f.getCategoryAtr().value)
					.findFirst();
			BigDecimal comparisonValue2 = new BigDecimal(-1);
			int registrationStatus2 = 2;
			BigDecimal valueDifference = s.getComparisonValue1().v().subtract(new BigDecimal(0));
			String reasonDifference = "";
			int confirmedStatus = 0;
			if (detalDiff.isPresent()) {
				comparisonValue2 = detalDiff.get().getComparisonValue2().v();
				registrationStatus2 = detalDiff.get().getRegistrationStatus2().value;
				valueDifference = s.getComparisonValue1().v().subtract(detalDiff.get().getComparisonValue2().v());
				detailDifferential2.remove(detalDiff.get());
			}

			Optional<PaycompConfirm> payCompComfirmFilter = payCompComfirm.stream()
					.filter(c -> s.getEmployeeCode().v().equals(c.getEmployeeCode().v())
							&& s.getCategoryAtr().value == c.getCategoryAtr().value
							&& s.getItemCode().v().equals(c.getItemCode().v()))
					.findFirst();

			if (payCompComfirmFilter.isPresent()) {
				valueDifference = payCompComfirmFilter.get().getValueDifference().v();
				reasonDifference = payCompComfirmFilter.get().getReasonDifference().v();
				confirmedStatus = payCompComfirmFilter.get().getConfirmedStatus().value;
			}
			return DetailDifferential.createFromJavaType(companyCode, s.getEmployeeCode().v(), s.getEmployeeName().v(),
					s.getItemCode().v(), s.getItemName().v(), s.getCategoryAtr().value, s.getComparisonValue1().v(),
					comparisonValue2, valueDifference, reasonDifference, s.getRegistrationStatus1().value,
					registrationStatus2, confirmedStatus);
		}).collect(Collectors.toList());
		/** start detailDifferential2 map detailDifferential1 */
		List<DetailDifferential> detailDifferential2Map = detailDifferential2.stream().map(s -> {
			Optional<PaycompConfirm> payCompComfirmFilter = payCompComfirm.stream()
					.filter(c -> s.getEmployeeCode().v().equals(c.getEmployeeCode().v())
							&& c.getCategoryAtr().value == s.getCategoryAtr().value
							&& s.getItemCode().v().equals(c.getItemCode().v()))
					.findFirst();
			BigDecimal comparisonValue1 = new BigDecimal(-1);
			int registrationStatus1 = 2;
			BigDecimal valueDifference = new BigDecimal(0).subtract(s.getComparisonValue2().v());
			String reasonDifference = "";
			int confirmedStatus = 0;
			if (payCompComfirmFilter.isPresent()) {
				valueDifference = payCompComfirmFilter.get().getValueDifference().v();
				reasonDifference = payCompComfirmFilter.get().getReasonDifference().v();
				confirmedStatus = payCompComfirmFilter.get().getConfirmedStatus().value;
			}
			return DetailDifferential.createFromJavaType(companyCode, s.getEmployeeCode().v(), s.getEmployeeName().v(),
					s.getItemCode().v(), s.getItemName().v(), s.getCategoryAtr().value, comparisonValue1,
					s.getComparisonValue2().v(), valueDifference, reasonDifference, registrationStatus1,
					s.getRegistrationStatus2().value, confirmedStatus);

		}).collect(Collectors.toList());
		detailDifferential.addAll(detailDifferential2Map);
		/** end detailDifferential2 map detailDifferential1 */
		Comparator<DetailDifferential> c = (p, o) -> p.getEmployeeCode().v().compareTo(o.getEmployeeCode().v());
		c = c.thenComparing((p, o) -> p.getItemCode().v().compareTo(o.getItemCode().v()));
		detailDifferential.sort(c);
		return detailDifferential.stream().map(s -> DetailDifferentialDto.createFromJavaType(s))
				.collect(Collectors.toList());
	}

}
