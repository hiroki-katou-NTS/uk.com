package nts.uk.file.at.app.export.calculationsetting;

import java.util.Optional;


public interface OutsideOtSetRepository {
	Optional<OutsideOtSetDto> findById(String companyId);
		
	
}
