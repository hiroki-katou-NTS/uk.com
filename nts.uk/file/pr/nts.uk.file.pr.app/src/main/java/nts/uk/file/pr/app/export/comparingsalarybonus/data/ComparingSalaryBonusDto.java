package nts.uk.file.pr.app.export.comparingsalarybonus.data;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class ComparingSalaryBonusDto {
    /** the month 1 */
    private String month1;  
    
    /** the month 2 */
    private String month2;
}
