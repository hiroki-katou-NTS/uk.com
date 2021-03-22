package nts.uk.cnv.dom.td.alteration.schema;

import java.util.List;

import nts.uk.cnv.dom.td.devstatus.DevelopmentProgress;

public interface SchemaAlterationRepository {

	List<SchemaAlteration> get(DevelopmentProgress progress);
}
