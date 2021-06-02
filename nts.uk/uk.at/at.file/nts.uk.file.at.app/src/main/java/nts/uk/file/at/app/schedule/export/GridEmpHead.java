package nts.uk.file.at.app.schedule.export;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class GridEmpHead {
    private String itemId;
    
    private String itemName;
    
    private int itemOrder;
    
    private boolean isDate;
    
    private String value;
    
    private String date;
    
    private String dayInWeek;
    
    private String backgroundColor;
    
    private String color;
} 
