package nts.uk.screen.at.app.ksm008.query.l;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MaxDaysOfWorkTimeDto implements Comparable<MaxDaysOfWorkTimeDto> {

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
    public int compareTo(MaxDaysOfWorkTimeDto o) {
        if (this.getCode() == null || o.getCode() == null) {
            return 0;
        }
        return this.getCode().compareTo(o.getCode());
    }
}