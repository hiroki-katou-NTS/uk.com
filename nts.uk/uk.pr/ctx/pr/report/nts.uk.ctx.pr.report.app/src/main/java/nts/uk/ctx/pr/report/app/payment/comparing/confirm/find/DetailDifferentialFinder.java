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
import nts.uk.ctx.pr.report.dom.payment.comparing.setting.ComparingPrintSet;
import nts.uk.ctx.pr.report.dom.payment.comparing.setting.ComparingPrintSetRepository;
import nts.uk.shr.com.context.AppContexts;

@Stateless
public class DetailDifferentialFinder {

	@Inject
	private ComfirmDifferentRepository comfirmDiffRepository;

	@Inject
	private ComparingPrintSetRepository printSetRepository;
	
	private int showItemIfCfWithNull = 0;
	private int showItemIfSameValue = 0;
	
	public List<DetailDifferentialDto> getDetailDifferential(int processingYMEarlier, int processingYMLater,
			List<String> personIDs) {
		String companyCode = AppContexts.user().companyCode();
		List<DetailDifferential> detailDifferential1 = this.comfirmDiffRepository
				.getDetailDifferentialWithEarlyYM(companyCode, processingYMEarlier, personIDs);
		List<DetailDifferential> detailDifferential2 = this.comfirmDiffRepository
				.getDetailDifferentialWithLaterYM(companyCode, processingYMLater, personIDs);
		List<PaycompConfirm> payCompComfirm = this.comfirmDiffRepository.getPayCompComfirm(companyCode, personIDs,
				processingYMEarlier, processingYMLater);
		ComparingPrintSet comparingPrintSet = this.printSetRepository.getComparingPrintSet(companyCode).orElse(null);		
		if(comparingPrintSet != null){
			this.showItemIfCfWithNull = comparingPrintSet.getShowItemIfCfWithNull().value;
			this.showItemIfSameValue = comparingPrintSet.getShowItemIfSameValue().value;
		}

		List<DetailDifferential> detailDifferential = detailDifferential1.stream().map(s -> {
			Optional<DetailDifferential> detalDiff = detailDifferential2.stream()
					.filter(f -> s.getPersonID().v().equals(f.getPersonID().v())
							&& s.getItemCode().v().equals(f.getItemCode().v())
							&& s.getCategoryAtr().value == f.getCategoryAtr().value)
					.findFirst();
			BigDecimal comparisonValue2 = new BigDecimal(-1);
			int registrationStatus2 = 2;
			BigDecimal valueDifference = new BigDecimal(0).subtract(s.getComparisonValue1().v());
			String reasonDifference = "";
			int confirmedStatus = 0;
			if (detalDiff.isPresent()) {
				comparisonValue2 = detalDiff.get().getComparisonValue2().v();
				registrationStatus2 = detalDiff.get().getRegistrationStatus2().value;
				valueDifference = detalDiff.get().getComparisonValue2().v().subtract(s.getComparisonValue1().v());
				detailDifferential2.remove(detalDiff.get());
			}
			Optional<PaycompConfirm> payCompComfirmFilter = payCompComfirm.stream()
					.filter(c -> s.getPersonID().v().equals(c.getPersonID().v())
							&& s.getCategoryAtr().value == c.getCategoryAtr().value
							&& s.getItemCode().v().equals(c.getItemCode().v()))
					.findFirst();

			if (payCompComfirmFilter.isPresent()) {
				valueDifference = payCompComfirmFilter.get().getValueDifference().v();
				reasonDifference = payCompComfirmFilter.get().getReasonDifference().v();
				confirmedStatus = payCompComfirmFilter.get().getConfirmedStatus().value;
			}
			return DetailDifferential.createFromJavaType(companyCode, s.getPersonID().v(), s.getEmployeeCode().v(),
					s.getEmployeeName().v(), s.getItemCode().v(), s.getItemName().v(), s.getCategoryAtr().value,
					s.getComparisonValue1().v(), comparisonValue2, valueDifference, reasonDifference,
					s.getRegistrationStatus1().value, registrationStatus2, confirmedStatus);
		}).collect(Collectors.toList());
		/** start detailDifferential2 map detailDifferential1 */
		List<DetailDifferential> detailDifferential2Map = detailDifferential2.stream().map(s -> {
			Optional<PaycompConfirm> payCompComfirmFilter = payCompComfirm.stream()
					.filter(c -> s.getEmployeeCode().v().equals(c.getPersonID().v())
							&& c.getCategoryAtr().value == s.getCategoryAtr().value
							&& s.getItemCode().v().equals(c.getItemCode().v()))
					.findFirst();
			BigDecimal comparisonValue1 = new BigDecimal(-1);
			int registrationStatus1 = 2;
			BigDecimal valueDifference = s.getComparisonValue2().v().subtract(new BigDecimal(0));
			String reasonDifference = "";
			int confirmedStatus = 0;
			if (payCompComfirmFilter.isPresent()) {
				valueDifference = payCompComfirmFilter.get().getValueDifference().v();
				reasonDifference = payCompComfirmFilter.get().getReasonDifference().v();
				confirmedStatus = payCompComfirmFilter.get().getConfirmedStatus().value;
			}
			return DetailDifferential.createFromJavaType(companyCode, s.getPersonID().v(), s.getEmployeeCode().v(),
					s.getEmployeeName().v(), s.getItemCode().v(), s.getItemName().v(), s.getCategoryAtr().value,
					comparisonValue1, s.getComparisonValue2().v(), valueDifference, reasonDifference,
					registrationStatus1, s.getRegistrationStatus2().value, confirmedStatus);

		}).collect(Collectors.toList());
		detailDifferential.addAll(detailDifferential2Map);
		//filter by setting print SEL_010 and SEL_011
		
		detailDifferential = detailDifferential.stream().filter(d -> {
			if (this.showItemIfCfWithNull == 0
					&& d.getComparisonValue1().v().compareTo(new BigDecimal(-1)) == 0
					&& d.getComparisonValue2().v().compareTo(new BigDecimal(-1)) == 0) {
				return false;
			}
			if (this.showItemIfSameValue == 0
					&& d.getValueDifference().v().compareTo(new BigDecimal(0)) == 0) {
				return false;
			}
			return true;
		}).collect(Collectors.toList());
		
		/** end detailDifferential2 map detailDifferential1 */
		Comparator<DetailDifferential> c = (p, o) -> p.getEmployeeCode().v().compareTo(o.getEmployeeCode().v());
		c = c.thenComparing((p, o) -> p.getItemCode().v().compareTo(o.getItemCode().v()));
		detailDifferential.sort(c);

		return detailDifferential.stream()
				.map(s -> new DetailDifferentialDto(s.getPersonID().v(), s.getEmployeeCode().v(),
						s.getEmployeeName().v(), s.getItemCode().v(), s.getItemName().v(), s.getCategoryAtr().value,
						s.getCategoryAtr().name, s.getComparisonValue1().v(), s.getComparisonValue2().v(),
						s.getValueDifference().v(), s.getReasonDifference().v(), s.getRegistrationStatus1().name,
						s.getRegistrationStatus2().name, s.getConfirmedStatus().value))
				.collect(Collectors.toList());
	}

}
