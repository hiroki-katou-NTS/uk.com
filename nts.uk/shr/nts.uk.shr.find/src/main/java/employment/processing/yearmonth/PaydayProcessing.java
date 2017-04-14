package employment.processing.yearmonth;

import java.util.List;

public interface PaydayProcessing {
	List<PaydayProcessingDto> getPaydayProcessing(String companyCode); 
}
