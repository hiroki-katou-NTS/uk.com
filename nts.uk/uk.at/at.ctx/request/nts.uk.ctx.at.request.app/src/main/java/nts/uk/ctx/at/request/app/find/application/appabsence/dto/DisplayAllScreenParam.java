package nts.uk.ctx.at.request.app.find.application.appabsence.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author anhnm
 *
 */
@Data
@AllArgsConstructor
public class DisplayAllScreenParam {
    private String companyID;
    
    private List<String> appDates;
    
    private AppAbsenceStartInfoDto startInfo;
    
    private int holidayAppType;
}
