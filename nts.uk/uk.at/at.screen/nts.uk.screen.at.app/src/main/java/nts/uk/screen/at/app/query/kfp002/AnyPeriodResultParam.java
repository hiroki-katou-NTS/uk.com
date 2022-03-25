package nts.uk.screen.at.app.query.kfp002;

import lombok.Data;

import java.util.List;

@Data
public class AnyPeriodResultParam {
    private String frameCode;
    private List<String> employeeIds;
    private List<Integer> itemIds;
}
