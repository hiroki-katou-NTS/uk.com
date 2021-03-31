package nts.uk.cnv.dom.td.alteration;

import java.util.Comparator;
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
			// 同名テーブルの衝突チェックしたほうが良さそう
			
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

			List<Alteration> alters = getAltersWithExclusionControl(
					require, newDesign.getId(), featureId, lastAlterId);

			// テーブル削除はありえないのでget
			TableProspect prospect = snapshot.apply(alters).get();

			Alteration alter = Alteration.alter(featureId, meta, prospect, newDesign)
					.orElseThrow(() -> new BusinessException(new RawErrorMessage("変更がありません")));

			require.save(alter);
		});
	}
	
	/**
	 * 既存テーブルを削除する
	 * @param require
	 * @param featureId
	 * @param meta
	 * @param lastAlterId
	 * @param targetTableId
	 * @return
	 */
	public static AtomTask dropTable(
			Require require,
			String featureId,
			AlterationMetaData meta,
			String lastAlterId,
			String targetTableId) {
		
		// AlterationのIDによる排他制御が必要なので、この先は全てAtomTaskに入れる
		return AtomTask.of(() -> {

			// 排他制御
			getAltersWithExclusionControl(require, targetTableId, featureId, lastAlterId);

			Alteration alter = Alteration.dropTable(featureId, meta, targetTableId);

			require.save(alter);
		});
	}

	/**
	 * 対象テーブルに対する既存の未検収orutaを取得しつつ排他チェックも実行する
	 * @param require
	 * @param tableId
	 * @param lastAlterId
	 * @return
	 */
	private static List<Alteration> getAltersWithExclusionControl(
			Require require,
			String targetTableId,
			String targetFeatureId,
			String lastAlterId) {

		List<Alteration> existingAlters = require.getAlterationsOfTable(
				targetTableId, DevelopmentProgress.notAccepted());
		
		val latestAlter = existingAlters.stream()
				.max(Comparator.comparing(a -> a.getCreatedAt()));
		
		// 単純な排他制御
		if (latestAlter.map(a -> !a.getAlterId().equals(lastAlterId)).orElse(false)) {
			throw new BusinessException(new RawErrorMessage("他の人がこのテーブルを変更したため、処理が失敗しました。画面をリロードしてやり直してください。"));
		}
		
		// 他Featureで変更中だったらブロック
		boolean existsAltersInOtherFeature = existingAlters.stream()
				.anyMatch(a -> !a.getFeatureId().equals(targetFeatureId));
		if (existsAltersInOtherFeature) {
			throw new BusinessException(new RawErrorMessage("他のFeatureに未検収のorutaがあるため、このテーブルを変更できません。"));
		}
		
		return existingAlters;
	}

	public interface Require {

		Optional<SchemaSnapshot> getSchemaSnapsohtLatest();

		Optional<TableSnapshot> getTableSnapshot(String snapshotId, String tableId);

		List<Alteration> getAlterationsOfTable(String tableId, DevelopmentProgress progress);

		void save(Alteration alter);
	}
}
