package nts.uk.cnv.app.command;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Value;
import nts.uk.cnv.dom.TableIdentity;

@Value
@AllArgsConstructor
public class RegistConversionCategoryCommand {
	String category;
	List<TableIdentity> tables;
}
