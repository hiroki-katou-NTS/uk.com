package nts.uk.screen.at.app.dailyperformance.correction.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ErAlApplicationDto {
	
    public String cid;
    
    public String errorCd;
    
    public ApplicationType appType;
    
}
