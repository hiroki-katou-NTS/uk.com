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
			String tableName,
			Optional<TableDesign> altered) {

		AlterationMetaData meta = require.getMetaData();
		Optional<TableDesign> newest = require.getNewest(meta.getFeatureId());
		Alteration alt = factory.create(tableName, meta, newest, altered);

		return AtomTask.of(() ->{
			require.add(alt);
		});
	}

	public interface Require {
		/** 最新のスナップショットを取得 */
		Optional<TableDesign> getNewest(String featureId);
		AlterationMetaData getMetaData();
		void add(Alteration alt);
	}
}
