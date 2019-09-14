package nts.uk.screen.at.app.dailyperformance.correction.mobile;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class MonthlyPerData {
    private Integer itemId;
    private String name;
    private String value;
    private int type;
    private int order;
}
