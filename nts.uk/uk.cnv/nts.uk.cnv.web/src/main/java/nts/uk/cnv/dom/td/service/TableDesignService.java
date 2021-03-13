package nts.uk.cnv.dom.td.service;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.task.tran.AtomTask;
import nts.uk.cnv.dom.td.alteration.Alteration;
import nts.uk.cnv.dom.td.alteration.AlterationMetaData;
import nts.uk.cnv.dom.td.tabledesign.TableDesign;

@Stateless
public class TableDesignService {

	@Inject
	AlterationFactory factory;

	public AtomTask alter(
			Require require,
			String tableId,
			AlterationMetaData meta,
			Optional<TableDesign> altered) {

		Optional<TableDesign> newest = require.getNewest(tableId);
		Alteration alt = factory.create(tableId, meta, newest, altered);

		return AtomTask.of(() ->{
			require.add(alt);
		});
	}

	public interface Require {
		/** 最新のスナップショットを取得 */
		Optional<TableDesign> getNewest(String tableId);
		void add(Alteration alt);
	}
}
