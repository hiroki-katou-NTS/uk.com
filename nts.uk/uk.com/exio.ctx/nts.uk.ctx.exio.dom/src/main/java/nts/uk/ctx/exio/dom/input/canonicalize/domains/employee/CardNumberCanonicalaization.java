package nts.uk.ctx.exio.dom.input.canonicalize.domains.employee;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import lombok.val;
import nts.arc.task.tran.AtomTask;
import nts.arc.time.GeneralDateTime;
import nts.gul.text.IdentifierUtil;
import nts.uk.ctx.at.record.dom.stamp.card.stampcard.StampCard;
import nts.uk.ctx.exio.dom.input.ExecutionContext;
import nts.uk.ctx.exio.dom.input.canonicalize.CanonicalizeUtil;
import nts.uk.ctx.exio.dom.input.canonicalize.ImportingMode;
import nts.uk.ctx.exio.dom.input.canonicalize.domaindata.KeyValues;
import nts.uk.ctx.exio.dom.input.canonicalize.domains.DomainCanonicalization;
import nts.uk.ctx.exio.dom.input.canonicalize.domains.ItemNoMap;
import nts.uk.ctx.exio.dom.input.canonicalize.existing.AnyRecordToChange;
import nts.uk.ctx.exio.dom.input.canonicalize.existing.AnyRecordToDelete;
import nts.uk.ctx.exio.dom.input.canonicalize.existing.StringifiedValue;
import nts.uk.ctx.exio.dom.input.canonicalize.methods.EmployeeCodeCanonicalization;
import nts.uk.ctx.exio.dom.input.canonicalize.result.CanonicalItemList;
import nts.uk.ctx.exio.dom.input.canonicalize.result.IntermediateResult;
import nts.uk.ctx.exio.dom.input.errors.ExternalImportError;
import nts.uk.ctx.exio.dom.input.meta.ImportingDataMeta;
import nts.uk.shr.com.context.AppContexts;

public class CardNumberCanonicalaization implements DomainCanonicalization{

	private final EmployeeCodeCanonicalization employeeCodeCanonicalization;
	
	public CardNumberCanonicalaization() {
		this.employeeCodeCanonicalization = new EmployeeCodeCanonicalization(getItemNoMap());
	}

	@Override
	public ItemNoMap getItemNoMap() {
		return ItemNoMap.reflection(Items.class);
	}
	
	public static class Items {
		public static final int 社員コード = 1;
		public static final int カードNO = 2;
		public static final int CARD_ID = 101;
		public static final int SID = 102;
		public static final int 登録日付 = 103;
	}

	@Override
	public void canonicalize(DomainCanonicalization.RequireCanonicalize require, ExecutionContext context) {
		// 受入データ内の重複チェック用
		Set<KeyValues> importingKeys = new HashSet<>();
		CanonicalizeUtil.forEachEmployee(require, context, employeeCodeCanonicalization, interms -> {

			// 全DELETEの場合、この社員に紐づくカードNoを全て削除
			if (context.getMode() == ImportingMode.DELETE_DOMAIN_BEFOREHAND
				|| context.getMode() == ImportingMode.DELETE_RECORD_BEFOREHAND) {
				String employeeId = interms.get(0).getItemByNo(Items.SID).get().getString();
				require.save(context, toDelete(context, employeeId));
			}

			for(val interm : interms) {

				// 重複チェック
				val keyValues = getPrimaryKeys(interm);
				if (importingKeys.contains(keyValues)) {
					require.add(ExternalImportError.record(interm.getRowNo(), context.getDomainId(), "受入データの中にキーの重複があります。"));
					continue; // 次のレコードへ
				}
				importingKeys.add(keyValues);
				val addedInterm = interm.addCanonicalized(getFixedItems());
				
				if(context.getMode() == ImportingMode.INSERT_ONLY) {
					
					// 既存データがなければ受け入れる
					if(!require.getStampCardByCardNumber(keyValues.getString(1)).isPresent()) {
						require.save(context, addedInterm.complete());
					}
				}
				else if (context.getMode() == ImportingMode.UPDATE_ONLY) {
					
					// 既存データがあれば受け入れる
					if(require.getStampCardByCardNumber(keyValues.getString(1)).isPresent()) {
						require.save(context, addedInterm.complete());
					}
				}
				else {
					if (context.getMode() == ImportingMode.DELETE_RECORD_BEFOREHAND) {
						// 既存データがあれば削除する（削除時はIDで削除するため、有無をチェックする）
						val optStampCard = require.getStampCardByCardNumber(keyValues.getString(1));
						if(require.getStampCardByCardNumber(keyValues.getString(1)).isPresent()) {
							require.save(context, toDelete(optStampCard.get().getStampCardId(), context));
						}
					}
					require.save(context, addedInterm.complete());
				}
			}
		});
	}
	
	private static CanonicalItemList getFixedItems() {
		return new CanonicalItemList()
			.add(Items.CARD_ID, IdentifierUtil.randomUniqueId().toString())
			.add(Items.登録日付, GeneralDateTime.now());
	}
	
	private KeyValues getPrimaryKeys(IntermediateResult interm) {
		return new KeyValues(Arrays.asList(
				AppContexts.user().contractCode(), 
				interm.getItemByNo(Items.カードNO).get().getString()));
	}
	
	private AnyRecordToDelete toDelete(String stampCardId, ExecutionContext context) {
		
		return AnyRecordToDelete.create(context)
		.addKey(Items.CARD_ID, StringifiedValue.of(stampCardId));
	}
	
	private AnyRecordToDelete toDelete(ExecutionContext context, String employeeId) {
		
		return AnyRecordToDelete.create(context)
				.addKey(Items.CARD_ID, StringifiedValue.of("SID"))
				.addKey(Items.SID, StringifiedValue.of(employeeId));
	}

	@Override
	public AtomTask adjust(
			RequireAdjsut require,
			ExecutionContext context,
			List<AnyRecordToChange> recordsToChange,
			List<AnyRecordToDelete> recordsToDelete) {
		
		return AtomTask.of(() -> {
			for (val record : recordsToDelete) {

				val delKey = record.getKey(Items.CARD_ID).asString();

				if(delKey.equals("SID")) {
					require.deleteStampCardByEmployeeId(record.getKey(Items.SID).asString());
				} else {
					require.deleteStampCardById(record.getKey(Items.CARD_ID).asString());
				}
			}
		});

	}

	@Override
	public ImportingDataMeta appendMeta(ImportingDataMeta source) {
		return employeeCodeCanonicalization.appendMeta(source);
	}
	
	public static interface RequireCanonicalize { 
		
		Optional<StampCard> getStampCardByCardNumber(String cardNumber);
	}
	
	public static interface RequireAdjust {
		
		void deleteStampCardById(String stampCardId);
		
		void deleteStampCardByEmployeeId(String employeeId);
	}
}
