package nts.uk.cnv.app.command;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Value;

@Value
@AllArgsConstructor
public class RegistConversionCategoryCommand {
	String category;
	List<String> tables;
}
