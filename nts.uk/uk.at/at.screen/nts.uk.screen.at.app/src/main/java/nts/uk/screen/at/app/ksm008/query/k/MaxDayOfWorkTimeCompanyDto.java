package nts.uk.screen.at.app.ksm008.query.k;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MaxDayOfWorkTimeCompanyDto implements Comparable<MaxDayOfWorkTimeCompanyDto> {

    /**
     * コード
     */
    private String code;

    /**
     * 名称
     */
    private String name;

    /**
     * 日数
     */
    private Integer maxNumbeOfWorkingDays;

    @Override
    public int compareTo(MaxDayOfWorkTimeCompanyDto o) {
        if (this.getCode() == null || o.getCode() == null) {
            return 0;
        }
        return this.getCode().compareTo(o.getCode());
    }
}