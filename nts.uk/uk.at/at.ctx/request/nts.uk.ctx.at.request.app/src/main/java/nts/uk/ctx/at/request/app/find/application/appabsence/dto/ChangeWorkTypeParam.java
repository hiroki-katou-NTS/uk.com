package nts.uk.ctx.at.request.app.find.application.appabsence.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ChangeWorkTypeParam {
    
    private List<String> appDates;
    
    private AppAbsenceStartInfoDto startInfo;
    
    private int holidayAppType;
    
    private String workTypeCd;
}
