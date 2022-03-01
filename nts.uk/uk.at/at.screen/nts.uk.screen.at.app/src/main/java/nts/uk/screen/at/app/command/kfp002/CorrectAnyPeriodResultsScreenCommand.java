package nts.uk.screen.at.app.command.kfp002;

import lombok.Data;
import nts.uk.screen.at.app.kdw013.a.ItemValueCommand;

import java.util.List;
import java.util.Map;

@Data
public class CorrectAnyPeriodResultsScreenCommand {
    private String frameCode;
    private Map<String, List<ItemValueCommand>> items;
}
