package nts.uk.cnv.screen.app.query.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import nts.uk.cnv.core.dom.TableIdentity;

@AllArgsConstructor
@Data
public class Cnv001BLoadDataDto {

	List<TableIdentity> selectedTables;

//	List<String> unselectedTables;
}
