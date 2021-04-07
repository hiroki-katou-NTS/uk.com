package nts.uk.ctx.at.function.app.query.arbitraryperiodsummarytable;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 表示内容
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class DisplayContent {

    private Double value;
    // 勤怠項目ID
    public int attendanceItemId;
    // 順位
    private Integer rank;
}
