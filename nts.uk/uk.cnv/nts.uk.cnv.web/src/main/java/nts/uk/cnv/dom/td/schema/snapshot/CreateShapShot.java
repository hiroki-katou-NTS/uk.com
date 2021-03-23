package nts.uk.cnv.dom.td.schema.snapshot;

import java.util.List;

import nts.arc.task.tran.AtomTask;
import nts.uk.cnv.dom.td.schema.snapshot.CreateShapShotImpl.Require;

public interface CreateShapShot {
	AtomTask create(Require require,String acceptedEventId ,List<String> alterationId);
}
