package nts.uk.ctx.pr.report.dom.payment.comparing.setting;

import java.util.Optional;

public interface ComparingPrintSetRepository {
	Optional<ComparingPrintSet> getComparingPrintSet(String companyCode);

	void insertComparingPrintSet(ComparingPrintSet comparingPrintSet);

	void updateComparingPrintSet(ComparingPrintSet comparingPrintSet);

	void deleteComparingPrintSet(String companyCode);
}
