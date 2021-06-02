package nts.uk.file.at.app.schedule.export;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeRowDto {
    
    private String employeeID;
    
    private String employeeCode;
    
    private String businessName;
    
    private List<ItemRowDto> itemRows;
}
