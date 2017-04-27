package nts.uk.ctx.pr.report.app.payment.comparing.confirm.find;

import java.math.BigDecimal;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.enums.EnumAdaptor;
import nts.uk.ctx.pr.report.dom.payment.comparing.confirm.ComfirmDifferentRepository;
import nts.uk.ctx.pr.report.dom.payment.comparing.confirm.ComparisonValue;
import nts.uk.ctx.pr.report.dom.payment.comparing.confirm.ConfirmedStatus;
import nts.uk.ctx.pr.report.dom.payment.comparing.confirm.DetailDifferential;
import nts.uk.ctx.pr.report.dom.payment.comparing.confirm.PaycompConfirm;
import nts.uk.ctx.pr.report.dom.payment.comparing.confirm.ReasonDifference;
import nts.uk.ctx.pr.report.dom.payment.comparing.confirm.RegistrationStatus;
import nts.uk.ctx.pr.report.dom.payment.comparing.confirm.ValueDifference;
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
			if (!detalDiff.isPresent()) {
				s.setComparisonValue2(new ComparisonValue(new BigDecimal(-1)));
				s.setRegistrationStatus2(EnumAdaptor.valueOf(2, RegistrationStatus.class));
				s.setValueDifference(new ValueDifference(s.getComparisonValue1().v().subtract(new BigDecimal(0))));
			} else {
				s.setComparisonValue2(detalDiff.get().getComparisonValue2());
				s.setRegistrationStatus2(detalDiff.get().getRegistrationStatus2());
				s.setValueDifference(new ValueDifference(
						s.getComparisonValue1().v().subtract(detalDiff.get().getComparisonValue2().v())));
				detailDifferential2.remove(detalDiff);
			}
			Optional<PaycompConfirm> payCompComfirmFilter = payCompComfirm.stream()
					.filter(c -> c.getCategoryAtr().value == s.getCategoryAtr().value
							&& s.getCompanyCode().equals(c.getItemCode().v()))
					.findFirst();
			if (payCompComfirmFilter.isPresent()) {
				s.setValueDifference(new ValueDifference(payCompComfirmFilter.get().getValueDifference().v()));
				s.setReasonDifference(new ReasonDifference(payCompComfirmFilter.get().getReasonDifference().v()));
				s.setConfirmedStatus(EnumAdaptor.valueOf(payCompComfirmFilter.get().getConfirmedStatus().value,
						ConfirmedStatus.class));
				return s;
			} else {
				s.setReasonDifference(new ReasonDifference(null));
				s.setConfirmedStatus(EnumAdaptor.valueOf(0, ConfirmedStatus.class));
				return s;
			}
		}).collect(Collectors.toList());
		/** start detailDifferential2 map detailDifferential1 */
		List<DetailDifferential> detailDifferential2Map = detailDifferential2.stream().map(s -> {
			Optional<PaycompConfirm> payCompComfirmFilter = payCompComfirm.stream()
					.filter(c -> c.getCategoryAtr().value == s.getCategoryAtr().value
							&& s.getCompanyCode().equals(c.getItemCode().v()))
					.findFirst();
			s.setComparisonValue1(new ComparisonValue(new BigDecimal(-1)));
			s.setRegistrationStatus1(EnumAdaptor.valueOf(2, RegistrationStatus.class));
			if (payCompComfirmFilter.isPresent()) {
				s.setValueDifference(new ValueDifference(payCompComfirmFilter.get().getValueDifference().v()));
				s.setReasonDifference(new ReasonDifference(payCompComfirmFilter.get().getReasonDifference().v()));
				s.setConfirmedStatus(EnumAdaptor.valueOf(payCompComfirmFilter.get().getConfirmedStatus().value,
						ConfirmedStatus.class));
				return s;
			} else {
				s.setValueDifference(new ValueDifference(new BigDecimal(0).subtract(s.getComparisonValue2().v())));
				s.setReasonDifference(new ReasonDifference(null));
				s.setConfirmedStatus(EnumAdaptor.valueOf(0, ConfirmedStatus.class));
				return s;
			}
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
