package nts.uk.ctx.at.request.app.find.application.appabsence.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import nts.uk.ctx.at.request.app.find.application.common.AppDispInfoWithDateDto;

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
    
    private AppDispInfoWithDateDto appWithDate;
}
