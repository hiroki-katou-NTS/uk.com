package nts.uk.cnv.dom.td.alteration;

import java.util.List;
import java.util.Optional;

import lombok.val;
import nts.arc.error.BusinessException;
import nts.arc.error.RawErrorMessage;
import nts.arc.task.tran.AtomTask;
import nts.uk.cnv.dom.td.devstatus.DevelopmentProgress;
import nts.uk.cnv.dom.td.schema.prospect.definition.TableProspect;
import nts.uk.cnv.dom.td.schema.snapshot.SchemaSnapshot;
import nts.uk.cnv.dom.td.schema.snapshot.TableSnapshot;
import nts.uk.cnv.dom.td.schema.tabledesign.TableDesign;

/**
 * orutaを登録する
 */
public class SaveAlteration {

	/**
	 * 新しいテーブルを作成する
	 * @param require
	 * @param featureId
	 * @param meta
	 * @param newDesign
	 * @return
	 */
	public static AtomTask createTable(
			Require require,
			String featureId,
			AlterationMetaData meta,
			TableDesign newDesign) {

		val alter = Alteration.newTable(featureId, meta, newDesign);

		return AtomTask.of(() -> {
			require.save(alter);
		});
	}

	/**
	 * 既存のテーブルを変更する
	 * @param require
	 * @param featureId
	 * @param meta
	 * @param lastAlterId
	 * @param newDesign
	 * @return
	 */
	public static AtomTask alterTable(
			Require require,
			String featureId,
			AlterationMetaData meta,
			String lastAlterId,
			TableDesign newDesign) {

		TableSnapshot snapshot = require.getSchemaSnapsohtLatest()
				.flatMap(schema -> require.getTableSnapshot(schema.getSnapshotId(), newDesign.getId()))
				.orElseGet(() -> TableSnapshot.empty());

		// AlterationのIDによる排他制御が必要なので、この先は全てAtomTaskに入れる
		return AtomTask.of(() -> {

			List<Alteration> alters = require.getAlterationsOfTable(
					newDesign.getId(), DevelopmentProgress.notAccepted());

			// テーブル削除はありえないのでget
			TableProspect prospect = snapshot.apply(alters).get();

			// 排他制御
			if (!prospect.getLastAlterId().equals(lastAlterId)) {
				throw new BusinessException(new RawErrorMessage("排他エラーだよ"));
			}

			Alteration alter = Alteration.alter(featureId, meta, prospect, newDesign)
					.orElseThrow(() -> new BusinessException(new RawErrorMessage("変更が無いよ")));

			require.save(alter);
		});
	}

	public interface Require {

		Optional<SchemaSnapshot> getSchemaSnapsohtLatest();

		Optional<TableSnapshot> getTableSnapshot(String snapshotId, String tableId);

		List<Alteration> getAlterationsOfTable(String tableId, DevelopmentProgress progress);

		void save(Alteration alter);
	}
}
