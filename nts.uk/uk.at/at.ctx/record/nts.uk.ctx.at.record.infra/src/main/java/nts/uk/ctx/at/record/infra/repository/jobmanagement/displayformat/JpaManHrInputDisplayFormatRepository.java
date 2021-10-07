package nts.uk.ctx.at.record.infra.repository.jobmanagement.displayformat;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.record.dom.jobmanagement.displayformat.DisplayAttItem;
import nts.uk.ctx.at.record.dom.jobmanagement.displayformat.DisplayManHrRecordItem;
import nts.uk.ctx.at.record.dom.jobmanagement.displayformat.ManHrInputDisplayFormat;
import nts.uk.ctx.at.record.dom.jobmanagement.displayformat.ManHrInputDisplayFormatRepository;
import nts.uk.ctx.at.record.dom.jobmanagement.displayformat.RecordColumnDisplayItem;
import nts.uk.ctx.at.record.infra.entity.jobmanagement.manhourinputdisplayformat.KrcmtManHrFormatDialog;
import nts.uk.ctx.at.record.infra.entity.jobmanagement.manhourinputdisplayformat.KrcmtManHrFormatDialogTask;
import nts.uk.ctx.at.record.infra.entity.jobmanagement.manhourinputdisplayformat.KrcmtManHrFormatMain;

/**
 * 
 * @author tutt
 *
 */
@Stateless
public class JpaManHrInputDisplayFormatRepository extends JpaRepository implements ManHrInputDisplayFormatRepository {

	private static final String SELECT_MAIN_BY_CID_STRING = "SELECT s FROM KrcmtManHrFormatMain m WHERE m.pk.cId = :cId";
	private static final String SELECT_DIALOG_BY_CID_STRING = "SELECT s FROM KrcmtManHrFormatDialog m WHERE m.pk.cId = :cId";
	private static final String SELECT_TASK_BY_CID_STRING = "SELECT s FROM KrcmtManHrFormatDialogTask m WHERE m.pk.cId = :cId";

	@Override
	public void insert(ManHrInputDisplayFormat format) {

		List<RecordColumnDisplayItem> recordColumnDisplayItems = format.getRecordColumnDisplayItems();

		for (RecordColumnDisplayItem i : recordColumnDisplayItems) {
			this.commandProxy().insert(new KrcmtManHrFormatMain(i));
		}

		List<DisplayAttItem> displayAttItems = format.getDisplayAttItems();

		for (DisplayAttItem i : displayAttItems) {
			this.commandProxy().insert(new KrcmtManHrFormatDialog(i));
		}

		List<DisplayManHrRecordItem> displayManHrRecordItems = format.getDisplayManHrRecordItems();

		for (DisplayManHrRecordItem i : displayManHrRecordItems) {
			this.commandProxy().insert(new KrcmtManHrFormatDialogTask(i));
		}

	}

	@Override
	public void update(ManHrInputDisplayFormat format) {
		List<RecordColumnDisplayItem> recordColumnDisplayItems = format.getRecordColumnDisplayItems();

		for (RecordColumnDisplayItem i : recordColumnDisplayItems) {
			this.commandProxy().update(new KrcmtManHrFormatMain(i));
		}

		List<DisplayAttItem> displayAttItems = format.getDisplayAttItems();

		for (DisplayAttItem i : displayAttItems) {
			this.commandProxy().update(new KrcmtManHrFormatDialog(i));
		}

		List<DisplayManHrRecordItem> displayManHrRecordItems = format.getDisplayManHrRecordItems();

		for (DisplayManHrRecordItem i : displayManHrRecordItems) {
			this.commandProxy().update(new KrcmtManHrFormatDialogTask(i));
		}

	}

	@Override
	public void delete(String cId) {
		List<KrcmtManHrFormatMain> recordColumnDisplayItems = this.queryProxy()
				.query(SELECT_MAIN_BY_CID_STRING, KrcmtManHrFormatMain.class).setParameter("cId", cId).getList()
				.stream().collect(Collectors.toList());
		
		for (KrcmtManHrFormatMain m : recordColumnDisplayItems) {
			this.commandProxy().remove(m);
		}
		
		List<KrcmtManHrFormatDialog> displayAttItems = this.queryProxy()
				.query(SELECT_DIALOG_BY_CID_STRING, KrcmtManHrFormatDialog.class).setParameter("cId", cId).getList()
				.stream().collect(Collectors.toList());
		
		for (KrcmtManHrFormatDialog d : displayAttItems) {
			this.commandProxy().remove(d);
		}
		
		List<KrcmtManHrFormatDialogTask> displayManHrRecordItems = this.queryProxy()
				.query(SELECT_TASK_BY_CID_STRING, KrcmtManHrFormatDialogTask.class).setParameter("cId", cId).getList()
				.stream().collect(Collectors.toList());
		
		for (KrcmtManHrFormatDialogTask d : displayManHrRecordItems) {
			this.commandProxy().remove(d);
		}
	}

	@Override
	public Optional<ManHrInputDisplayFormat> get(String cId) {
		List<RecordColumnDisplayItem> recordColumnDisplayItems = this.queryProxy()
				.query(SELECT_MAIN_BY_CID_STRING, KrcmtManHrFormatMain.class).setParameter("cId", cId).getList()
				.stream().map(m -> m.toDomain()).collect(Collectors.toList());

		List<DisplayAttItem> displayAttItems = this.queryProxy()
				.query(SELECT_DIALOG_BY_CID_STRING, KrcmtManHrFormatDialog.class).setParameter("cId", cId).getList()
				.stream().map(m -> m.toDomain()).collect(Collectors.toList());

		List<DisplayManHrRecordItem> displayManHrRecordItems = this.queryProxy()
				.query(SELECT_TASK_BY_CID_STRING, KrcmtManHrFormatDialogTask.class).setParameter("cId", cId).getList()
				.stream().map(m -> m.toDomain()).collect(Collectors.toList());

		return Optional
				.of(new ManHrInputDisplayFormat(recordColumnDisplayItems, displayAttItems, displayManHrRecordItems));
	}

}
