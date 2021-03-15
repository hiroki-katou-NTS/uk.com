package nts.uk.cnv.dom.td.alteration;

import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.error.BusinessException;
import nts.arc.error.RawErrorMessage;
import nts.arc.task.tran.AtomTask;
import nts.uk.cnv.dom.td.schema.prospect.definition.TableProspect;
import nts.uk.cnv.dom.td.schema.snapshot.TableSnapshot;
import nts.uk.cnv.dom.td.schema.tabledesign.TableDesign;

@Stateless
public class TableDesignService {

	@Inject
	AlterationFactory factory;

	public AtomTask alter(
			Require require,
			String featureId,
			String tableId,
			AlterationMetaData meta,
			Optional<TableDesign> altered) {

		TableSnapshot ss = require.getNewestSnapshot(tableId);
		List<Alteration> alterationList = require.getUnaccepted(tableId);

		Optional<TableProspect> tableProspect = ss.apply(alterationList);

		Alteration alt = factory.create(featureId, tableId, meta, tableProspect, altered)
				.orElseThrow(() -> new BusinessException(new RawErrorMessage("")));

		return AtomTask.of(() ->{
			require.add(alt);
		});
	}

	public interface Require {
		TableSnapshot getNewestSnapshot(String tableId);
		List<Alteration> getUnaccepted(String tableId);
		void add(Alteration alt);
	}
}
