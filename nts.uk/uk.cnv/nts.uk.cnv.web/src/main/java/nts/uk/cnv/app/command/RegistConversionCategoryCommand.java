package nts.uk.cnv.app.command;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Value;
import nts.uk.cnv.core.dom.TableIdentity;

@Value
@AllArgsConstructor
public class RegistConversionCategoryCommand {
	String category;
	List<TableIdentity> tables;
}
