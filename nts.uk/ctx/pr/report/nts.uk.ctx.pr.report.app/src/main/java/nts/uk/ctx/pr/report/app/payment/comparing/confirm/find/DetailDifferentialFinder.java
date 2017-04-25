package nts.uk.ctx.pr.report.app.payment.comparing.confirm.find;

import java.math.BigDecimal;
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

	public List<DetailDifferentialDto> getDetailDifferential(int processingYMEarlier, int processingYMLater) {
		String companyCode = AppContexts.user().companyCode();
		List<DetailDifferential> detailDifferential1 = this.comfirmDiffRepository
				.getDetailDifferentialWithEarlyYM(companyCode, processingYMEarlier);
		List<DetailDifferential> detailDifferential2 = this.comfirmDiffRepository
				.getDetailDifferentialWithLaterYM(companyCode, processingYMLater);

		List<DetailDifferential> detailDifferential = detailDifferential1.stream().map(s -> {
			Optional<DetailDifferential> detalDiff = detailDifferential2.stream()
					.filter(f -> s.getEmployeeCode().equals(f.getEmployeeCode())
							&& s.getItemCode().equals(f.getItemCode()) && s.getCategoryAtr().equals(f.getCategoryAtr()))
					.findFirst();
			if (!detalDiff.isPresent()) {
				s.setComparisonValue2(new ComparisonValue(new BigDecimal(0)));
				s.setRegistrationStatus2(EnumAdaptor.valueOf(0, RegistrationStatus.class));
				s.setValueDifference(new ValueDifference(new BigDecimal(0)));
				s.setReasonDifference(new ReasonDifference(null));
				s.setConfirmedStatus(EnumAdaptor.valueOf(0, ConfirmedStatus.class));
				return s;
			}
			s.setComparisonValue2(detalDiff.get().getComparisonValue2());
			s.setRegistrationStatus2(detalDiff.get().getRegistrationStatus2());
			detailDifferential2.remove(detalDiff);
			PaycompConfirm payCompComfirm = this.comfirmDiffRepository.getPayCompComfirm(companyCode,
					s.getEmployeeCode().v(), processingYMEarlier, processingYMLater, s.getCategoryAtr().value,
					s.getItemCode().v());
			if (payCompComfirm != null) {
				s.setValueDifference(new ValueDifference(payCompComfirm.getValueDifference().v()));
				s.setReasonDifference(new ReasonDifference(payCompComfirm.getReasonDifference().v()));
				s.setConfirmedStatus(EnumAdaptor.valueOf(payCompComfirm.getConfirmedStatus().value, ConfirmedStatus.class));
				return s;
			} else {
				s.setValueDifference(new ValueDifference(s.getComparisonValue1().v().subtract(detalDiff.get().getComparisonValue2().v())));
				s.setReasonDifference(new ReasonDifference(null));
				s.setConfirmedStatus(EnumAdaptor.valueOf(0, ConfirmedStatus.class));
				return s;
			}
		}).collect(Collectors.toList());
		/** start detailDifferential2 map detailDifferential1 */

		/** end detailDifferential2 map detailDifferential1 */
		return detailDifferential.stream().map(s -> DetailDifferentialDto.createFromJavaType(s)).collect(Collectors.toList());
	}
}
