package nts.uk.ctx.exio.dom.input.canonicalize.domains.recode.stamp;

import lombok.val;
import nts.arc.task.tran.AtomTask;
import nts.arc.time.GeneralDateTime;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.stamp.Stamp;
import nts.uk.ctx.exio.dom.input.ExecutionContext;
import nts.uk.ctx.exio.dom.input.canonicalize.CanonicalizeUtil;
import nts.uk.ctx.exio.dom.input.canonicalize.domaindata.KeyValues;
import nts.uk.ctx.exio.dom.input.canonicalize.domains.DomainCanonicalization;
import nts.uk.ctx.exio.dom.input.canonicalize.domains.ItemNoMap;
import nts.uk.ctx.exio.dom.input.canonicalize.existing.AnyRecordToChange;
import nts.uk.ctx.exio.dom.input.canonicalize.existing.AnyRecordToDelete;
import nts.uk.ctx.exio.dom.input.canonicalize.methods.DateTimeCanonicalization;
import nts.uk.ctx.exio.dom.input.canonicalize.methods.WorkplaceCodeCanonicalization;
import nts.uk.ctx.exio.dom.input.canonicalize.result.CanonicalItem;
import nts.uk.ctx.exio.dom.input.canonicalize.result.CanonicalItemList;
import nts.uk.ctx.exio.dom.input.canonicalize.result.IntermediateResult;
import nts.uk.ctx.exio.dom.input.errors.ExternalImportError;
import nts.uk.ctx.exio.dom.input.errors.RecordError;
import nts.uk.ctx.exio.dom.input.meta.ImportingDataMeta;
import nts.uk.ctx.exio.dom.input.setting.assembly.RevisedDataRecord;
import nts.uk.ctx.exio.dom.input.workspace.domain.DomainWorkspace;

import java.util.*;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

public class StampCanonicalization implements DomainCanonicalization {

	private final WorkplaceCodeCanonicalization workplaceCodeCanonicalization;

	private final DateTimeCanonicalization dateTimeCanonicalization;

	public StampCanonicalization() {
		workplaceCodeCanonicalization = new WorkplaceCodeCanonicalization(Items.年月日, Items.職場コード, Items.職場ID);
		dateTimeCanonicalization = new DateTimeCanonicalization(Items.年月日, Items.時分, Items.秒, Items.打刻日時);
	}

	@Override
	public ItemNoMap getItemNoMap() {
		return ItemNoMap.reflection(Items.class);
	}

	public static class Items {
		public static final int カードNO = 1;
		public static final int 年月日 = 2;
		public static final int 時分 = 3;
		public static final int 秒 = 4;
		public static final int 認証方法 = 5;
		public static final int 打刻手段 = 6;
		public static final int 時刻変更区分 = 7;
		public static final int 計算区分変更対象 = 8;
		public static final int 所定時刻セット区分 = 9;
		public static final int 勤務種類を半休に変更する = 10;
		public static final int 外出区分 = 11;
		public static final int 就業時間帯コード = 12;
		public static final int 時間外時間 = 13;
		public static final int 時間外深夜時間 = 14;
		public static final int 職場コード = 15;
		public static final int 勤務場所コード = 16;
		public static final int 応援カードNO = 17;
		public static final int 就業情報端末コード = 18;
		public static final int 打刻日時 = 101;
		public static final int 職場ID = 102;
		public static final int 作業コード1 = 103;
		public static final int 作業コード2 = 104;
		public static final int 作業コード3 = 105;
		public static final int 作業コード4 = 106;
		public static final int 作業コード5 = 107;
		public static final int 緯度 = 108;
		public static final int 経度 = 109;
		public static final int 反映済み区分 = 110;
		public static final int 反映された年月日 = 111;
	}

	@Override
	public void canonicalize(DomainCanonicalization.RequireCanonicalize require, ExecutionContext context) {

		List<IntermediateResult> revisedRecords = require.getAllRevisedDataRecords(context).stream()
				.map(r -> IntermediateResult.create(r))
				.collect(toList());

		if (revisedRecords.isEmpty()) {
			return;
		}

		val workspace = require.getDomainWorkspace(context.getDomainId());

		// 受入データ内の重複チェック
		Set<KeyValues> importingKeys = new HashSet<>();

		CanonicalizeUtil.forEachRow(require, context, revisedData -> {

			IntermediateResult interm = IntermediateResult.create(revisedData);

			interm = preCanonicalize(interm);

			// 職場コードの正準化
			if(interm.isImporting(Items.職場コード)) {
				val wkpCanoItems = new CanonicalItemList();
				workplaceCodeCanonicalization.canonicalize(require, interm, interm.getRowNo())
						.ifRight(canonicalized -> wkpCanoItems.addItem(canonicalized.getItemByNo(Items.職場ID).get()))
						.ifLeft(error -> require.add(ExternalImportError.of(context.getDomainId(), error)));
				interm = interm.addCanonicalized(wkpCanoItems);
			}

			// 打刻日時の正準化(年月日時分秒→日時)
			val dateTimeCanoItem = dateTimeCanonicalization.canonicalize(require, interm).getItemByNo(Items.打刻日時).get();
			interm = interm.addCanonicalized(dateTimeCanoItem);

			// 既定値の追加
			interm = setDefaultItems(interm);

			// 固定値の追加
			interm = setFixedItems(interm);

			// キー重複チェック
			KeyValues key = getPrimaryKeys(interm, workspace);
			if (importingKeys.contains(key)) {
				require.add(ExternalImportError.record(interm.getRowNo(), context.getDomainId(), "受入データの中にキーの重複があります。"));
				return;
			}
			importingKeys.add(key);

			// 	制約のチェック
			val optError = checkConstraints(interm);
			if(optError.isPresent()){
				require.add(ExternalImportError.record(revisedData.getRowNo(), context.getDomainId(), optError.get().getMessage()));
				return;
			}

			// 既存データが存在する場合受け入れない
			if(existsStamp(require, interm)){
				return;
			}

			// 永続化
			require.save(context, interm.complete());
		});
	}

	/**
	 * E版での処理拡張用
	 * @param interm
	 * @return
	 */
	protected IntermediateResult preCanonicalize(IntermediateResult interm) {
		return interm;
	}

	/**
	 * 既定値のセット
	 * @param interm
	 * @return
	 */
	private static IntermediateResult setDefaultItems(IntermediateResult interm) {
		return interm
				.optionalItem(CanonicalItem.of(Items.認証方法, 3))
				.optionalItem(CanonicalItem.of(Items.打刻手段, 7))
				.optionalItem(CanonicalItem.of(Items.計算区分変更対象, 0))
				.optionalItem(CanonicalItem.of(Items.所定時刻セット区分, 0))
				.optionalItem(CanonicalItem.of(Items.勤務種類を半休に変更する, 0));
	}

	/**
	 * 固定値のセット
	 * @param interm
	 * @return
	 */
	private static IntermediateResult setFixedItems(IntermediateResult interm) {
		val fixedItems = new CanonicalItemList()
				.add(Items.反映済み区分, 0);
		return interm.addCanonicalized(fixedItems);
	}

	/**
	 * 制約のチェック
	 * @param interm
	 * @return
	 */
	private static Optional<RecordError> checkConstraints(IntermediateResult interm){
		// 「時刻変更区分」が「4：外出」の場合、「外出区分」はEmptyでないこと
		if(interm.getItemByNo(Items.時刻変更区分).get().getInt() == 4){
			if(interm.getItemByNo(Items.外出区分).get().isNull()) {
				return Optional.of(RecordError.record(interm.getRowNo(), "「時刻変更区分」が「4：外出」の場合、「外出区分」を受け入れる必要があります。"));
			}
		}
		return Optional.empty();
	}

	/**
	 * 既存データが存在するかチェックする
	 * @param require
	 * @param interm
	 * @return
	 */
	private boolean existsStamp(RequireCanonicalize require, IntermediateResult interm){
		return require.existsStamp(
				interm.getItemByNo(Items.カードNO).get().toString(),
				interm.getItemByNo(Items.打刻日時).get().getDateTime(),
				interm.getItemByNo(Items.時刻変更区分).get().getJavaInt());
	}

	/**
	 * 主キー項目の値の取得
	 * @param interm
	 * @param workspace
	 * @return
	 */
	private static KeyValues getPrimaryKeys(IntermediateResult interm, DomainWorkspace workspace) {
		return workspace.getItemsPk().stream()
				.map(k -> k.getItemNo())
				.collect(toList()).stream()
				.map(itemNo -> interm.getItemByNo(itemNo).get())
				.map(item -> item.getValue())
				.collect(Collectors.collectingAndThen(toList(), KeyValues::new));
	}

	public interface RequireCanonicalize  {
		boolean existsStamp(String cardNumber, GeneralDateTime stampDateTime, int changeClockArt);
	}

	@Override
	public ImportingDataMeta appendMeta(ImportingDataMeta source) {
		return source;
	}

	@Override
	public AtomTask adjust(RequireAdjsut require, ExecutionContext context, List<AnyRecordToChange> recordsToChange, List<AnyRecordToDelete> recordsToDelete) {
		if (!recordsToChange.isEmpty()) {
			throw new RuntimeException("既存データの変更はありえない");
		}
		// INSERTのみのため補正処理は不要
		return AtomTask.none();
	}
}
