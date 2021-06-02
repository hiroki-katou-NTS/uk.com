package nts.uk.file.at.app.schedule.export;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ItemError {
    
    private String employeeID;
    
    private int itemOrder;
    
    private String columnKey;
    
    private String message;

}
