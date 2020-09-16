package nts.uk.cnv.app.command;

import java.util.List;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class RegistConversionCategoryCommand {
	String category;
	List<String> tables;
}
