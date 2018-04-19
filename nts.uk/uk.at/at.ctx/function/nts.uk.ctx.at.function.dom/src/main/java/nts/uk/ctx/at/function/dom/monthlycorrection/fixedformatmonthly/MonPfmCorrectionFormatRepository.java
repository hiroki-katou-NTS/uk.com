package nts.uk.ctx.at.function.dom.monthlycorrection.fixedformatmonthly;

import java.util.List;
import java.util.Optional;

public interface MonPfmCorrectionFormatRepository {
	
	List<MonPfmCorrectionFormat> getAllMonPfm(String companyID);
	
	Optional<MonPfmCorrectionFormat> getMonPfmCorrectionFormat(String companyID, String monthlyPfmFormatCode);
	
	List<MonPfmCorrectionFormat> getMonPfmCorrectionFormat(String companyID, List<String> monthlyPfmFormatCodes);
	
	void addMonPfmCorrectionFormat (MonPfmCorrectionFormat monPfmCorrectionFormat);
	
	void updateMonPfmCorrectionFormat (MonPfmCorrectionFormat monPfmCorrectionFormat);
	
	void deleteMonPfmCorrectionFormat (String companyID, String monthlyPfmFormatCode );
	
}
