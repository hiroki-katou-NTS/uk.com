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
public class GridDto {
    
    private int updateMode;

    private List<ItemError> errorItems;
    
    private List<GridEmpHead> headDatas;
    
    private List<EmployeeRowDto> employeeRow;
}
