package nts.uk.ctx.exio.dom.input.canonicalize.domains.employee;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;

import lombok.RequiredArgsConstructor;
import lombok.val;
import nts.arc.task.tran.AtomTask;
import nts.arc.time.GeneralDate;
import nts.arc.time.GeneralDateTime;
import nts.gul.security.hash.password.PasswordHash;
import nts.gul.text.IdentifierUtil;
import nts.uk.ctx.bs.employee.dom.employee.mgndata.EmployeeDataMngInfo;
import nts.uk.ctx.exio.dom.input.ExecutionContext;
import nts.uk.ctx.exio.dom.input.canonicalize.CanonicalItem;
import nts.uk.ctx.exio.dom.input.canonicalize.CanonicalItemList;
import nts.uk.ctx.exio.dom.input.canonicalize.ImportingMode;
import nts.uk.ctx.exio.dom.input.canonicalize.domains.DomainCanonicalization;
import nts.uk.ctx.exio.dom.input.canonicalize.existing.AnyRecordToChange;
import nts.uk.ctx.exio.dom.input.canonicalize.existing.AnyRecordToDelete;
import nts.uk.ctx.exio.dom.input.canonicalize.existing.StringifiedValue;
import nts.uk.ctx.exio.dom.input.canonicalize.methods.IntermediateResult;
import nts.uk.ctx.exio.dom.input.errors.ExternalImportError;
import nts.uk.ctx.exio.dom.input.meta.ImportingDataMeta;
import nts.uk.ctx.sys.shared.dom.user.User;

/**
 * 個人基本情報の正準化
 */
public class EmployeeBasicCanonicalization implements DomainCanonicalization {
	
	@Override
	public void canonicalize(DomainCanonicalization.RequireCanonicalize require, ExecutionContext context) {
		
		List<String> employeeCodes = require.getStringsOfRevisedData(context, Items.SCD);
		
		for (String employeeCode : employeeCodes) {
			
			canonicalizeEmployee(require, context, employeeCode);
		}
	}

	private void canonicalizeEmployee(
			DomainCanonicalization.RequireCanonicalize require,
			ExecutionContext context,
			String employeeCode) {
		
		val revisedRecords = require.getRevisedDataRecordWhere(context, Items.SCD, employeeCode);
		
		// 個人基本情報は1社員につき1レコード
		// 2レコード目以降はエラーとする
		revisedRecords.stream().skip(1).forEach(record -> {
			require.add(context, ExternalImportError.record(
					record.getRowNo(), "受入データに同じ社員のレコードが存在しています。1社員につき1レコードのみにしてください。"));
		});
		
		// ここまで来たら必ず1レコードあるはず
		val revised = revisedRecords.get(0);
		
		// 一旦中間オブジェクトに変換
		IntermediateResult interm = IntermediateResult.noChange(revised);
		
		if (context.getMode() == ImportingMode.INSERT_ONLY) {
			
			// 既存データがあれば受け入れない
			if (require.getEmployeeDataMngInfoByEmployeeCode(employeeCode).isPresent()) {
				return;
			}
			
			interm = Ids.newIds().fill(interm);
			interm = Items.fillNewData(context, interm);
			
		} else if (context.getMode() == ImportingMode.UPDATE_ONLY) {
			
			// 既存データが無ければ受け入れない
			val idsOpt = Ids.get(require, employeeCode);
			if (!idsOpt.isPresent()) {
				return;
			}
			
			val ids = idsOpt.get();
			interm = ids.fill(interm);

			// パスワードは既存データを削除して受け入れる
			if (interm.isImporting(Items.Password.PASSWORD)) {
				interm = Items.Password.fillNewData(context, interm);
				require.save(context, ids.toDeletePassword(context));
			}
			
		} else {
			
			val idsOpt = Ids.get(require, employeeCode);
			
			if (context.getMode() == ImportingMode.DELETE_RECORD_BEFOREHAND) {
				// 既存データを削除
				idsOpt.ifPresent(ids -> ids.toDelete(require, context));
			}
			
			interm = idsOpt.orElseGet(() -> Ids.newIds()).fill(interm);
			interm = Items.fillNewData(context, interm);
		}
		
		// ログインIDの重複チェック
		if (Items.User.isDuplicatedLoginId(require, interm)) {
			require.add(context, ExternalImportError.record(interm.getRowNo(), "ログインIDが重複しています。"));
			
			return;
		}
		
		require.save(context, interm.complete());
	}
	
	/**
	 * 社員ID・個人ID・ユーザIDの取り扱い担当クラス
	 */
	@RequiredArgsConstructor
	private static class Ids {
		
		final String employeeId;
		final String personId;
		final String userId;
		
		/**
		 * 社員コードから既存データを取得する
		 * @param require
		 * @param employeeCode
		 * @return
		 */
		static Optional<Ids> get(
				DomainCanonicalization.RequireCanonicalize require,
				String employeeCode) {
			
			return require.getEmployeeDataMngInfoByEmployeeCode(employeeCode).map(emp ->  new Ids(
					emp.getEmployeeId(),
					emp.getPersonId(),
					require.getUserByPersonId(emp.getPersonId()).get().getUserID()));
		}
		
		/**
		 * 新しいIDを生成する
		 * @return
		 */
		static Ids newIds() {
			return new Ids(
					IdentifierUtil.randomUniqueId(),
					IdentifierUtil.randomUniqueId(),
					IdentifierUtil.randomUniqueId());
		}
		
		/**
		 * 中間データに値を埋める
		 * @param source
		 * @return
		 */
		IntermediateResult fill(IntermediateResult source) {
			return source
					.addCanonicalized(CanonicalItem.of(Items.SID, employeeId))
					.addCanonicalized(CanonicalItem.of(Items.PID, personId))
					.addCanonicalized(CanonicalItem.of(Items.USER_ID, userId));
		}
		
		/**
		 * 補正削除のデータを保存する
		 * @param require
		 * @param context
		 */
		void toDelete(DomainCanonicalization.RequireCanonicalize require, ExecutionContext context) {
			require.save(context, toDeleteEmployee(context));
			require.save(context, toDeletePerson(context));
			require.save(context, toDeleteUser(context));
			require.save(context, toDeletePassword(context));
		}
		
		AnyRecordToDelete toDeleteEmployee(ExecutionContext context) {
			return AnyRecordToDelete.create(context, Items.Employee.TARGET_NAME)
					.addKey(Items.SID, StringifiedValue.of(employeeId))
					.addKey(Items.PID, StringifiedValue.of(personId));
		}
		
		AnyRecordToDelete toDeletePerson(ExecutionContext context) {
			return AnyRecordToDelete.create(context, Items.Person.TARGET_NAME)
					.addKey(Items.SID, StringifiedValue.of(employeeId))
					.addKey(Items.PID, StringifiedValue.of(personId));
		}
		
		AnyRecordToDelete toDeleteUser(ExecutionContext context) {
			return AnyRecordToDelete.create(context, Items.User.TARGET_NAME)
					.addKey(Items.SID, StringifiedValue.of(employeeId))
					.addKey(Items.USER_ID, StringifiedValue.of(userId));
		}
		
		AnyRecordToDelete toDeletePassword(ExecutionContext context) {
			return AnyRecordToDelete.create(context, Items.Password.TARGET_NAME)
					.addKey(Items.SID, StringifiedValue.of(employeeId))
					.addKey(Items.USER_ID, StringifiedValue.of(userId));
		}
	}

	private static class Items {

		/** 社員コード */
		private static final int SCD = 1;
		
		/** 社員ID */
		private static final int SID = 101;
		
		/** 個人ID */
		private static final int PID = 102;
		
		/** ユーザID */
		private static final int USER_ID = 103;

		/**
		 * 新規作成として固定値データを埋める
		 * @param context
		 * @param interm
		 * @return
		 */
		private static IntermediateResult fillNewData(ExecutionContext context, IntermediateResult interm) {
			
			interm = Items.Employee.fillNewData(context, interm);
			interm = Items.Person.fillNewData(context, interm);
			interm = Items.User.fillNewData(context, interm);
			interm = Items.Password.fillNewData(context, interm);
			
			return interm;
		}
		
		/** 社員データ管理情報 */
		private static class Employee {
			
			private static final String TARGET_NAME = "Employee";
			
			/** 削除状況 */
			private static final int DELETE_STATUS = 110;
			
			
			/**
			 * 新規作成用に固定値を埋める
			 * @param record
			 * @return
			 */
			static IntermediateResult fillNewData(ExecutionContext context, IntermediateResult source) {
				return source.addCanonicalized(CanonicalItem.of(DELETE_STATUS, 0));
			}
		}
		
		/** 個人 */
		private static class Person {

			private static final String TARGET_NAME = "Person";

			/**
			 * 新規作成用に固定値を埋める
			 * @param record
			 * @return
			 */
			static IntermediateResult fillNewData(ExecutionContext context, IntermediateResult source) {
				// 今のところ固定値無し
				return source;
			}
		}
		
		/** ユーザ */
		private static class User {
			
			private static final String TARGET_NAME = "User";
			
			/** ログインID */
			private static final int LOGIN_ID = 4;
			
			/** 紐付け先個人ID */
			private static final int ASSO_PID = 123;

			/** 有効期限 */
			private static final int EXPIRE_DATE = 124;

			/** 特別利用者 */
			private static final int SPECIAL_USER = 125;

			/** 複数会社を兼務する */
			private static final int MULTI_COMPANY = 126;

			/** デフォルトユーザ */
			private static final int DEFAULT_USER = 127;
			
			/**
			 * 新規作成用に固定値を埋める
			 * @param record
			 * @return
			 */
			static IntermediateResult fillNewData(ExecutionContext context, IntermediateResult source) {
				return source
						.addCanonicalized(CanonicalItem.of(ASSO_PID, source.getItemByNo(PID).get().getString()))
						.addCanonicalized(CanonicalItem.of(EXPIRE_DATE, GeneralDate.max()))
						.addCanonicalized(CanonicalItem.of(SPECIAL_USER, 0))
						.addCanonicalized(CanonicalItem.of(MULTI_COMPANY, 0))
						.addCanonicalized(CanonicalItem.of(DEFAULT_USER, 0));
			}
			
			static boolean isDuplicatedLoginId(RequireCanonicalize require, IntermediateResult interm) {
				
				String userId = interm.getItemByNo(USER_ID).get().getString();
				String loginId = interm.getItemByNo(LOGIN_ID).get().getString();

				// 異なるユーザIDであるにも関わらず同じログインIDの既存データがあるか
				return require.getUserByLoginId(loginId)
						.filter(u -> !u.getUserID().equals(userId))
						.isPresent();
			}

		}
		
		/** ユーザのログインパスワード */
		private static class Password {
			
			private static final String TARGET_NAME = "Password";
			
			/** パスワード */
			private static final int PASSWORD = 5;
			
			/** パスワード状態 */
			private static final int STATE = 130;
			
			/** ハッシュ化パスワード */
			private static final int HASHED = 131;
			
			/** 変更日時 */
			private static final int CHANGED_AT = 132;

			private static IntermediateResult fillNewData(ExecutionContext context, IntermediateResult source) {
				return canonicalizePassword(source)
						.addCanonicalized(CanonicalItem.of(STATE, 0))
						.addCanonicalized(CanonicalItem.of(CHANGED_AT, GeneralDateTime.now()));
			}
			
			/**
			 * パスワードを正準化（ハッシュ化）する
			 * @param record
			 * @return パスワードを受け入れなければ空リストを返す
			 */
			private static IntermediateResult canonicalizePassword(IntermediateResult source) {
				
				Function<String, String> hash = passwordPlainText -> {
					String salt = source.getItemByNo(Items.USER_ID).get().getString();
					return PasswordHash.generate(passwordPlainText, salt);
				};
				
				val list = new CanonicalItemList();
				
				source.getItemByNo(Items.Password.PASSWORD)
						.map(item -> hash.apply(item.getString()))
						.map(hashed -> CanonicalItem.of(Items.Password.HASHED, hashed))
						.ifPresent(item -> list.addItem(item));
				
				return source.addCanonicalized(list);
				
			}
		}
	}
	
	public static interface RequireCanonicalize { 
		
		Optional<EmployeeDataMngInfo> getEmployeeDataMngInfoByEmployeeCode(String employeeCode);
		
		Optional<User> getUserByPersonId(String personId);
		
		Optional<User> getUserByLoginId(String loginId);
	}

	@Override
	public ImportingDataMeta appendMeta(ImportingDataMeta source) {
		return source;
	}

	@Override
	public AtomTask adjust(
			DomainCanonicalization.RequireAdjsut require,
			List<AnyRecordToChange> recordsToChange,
			List<AnyRecordToDelete> recordsToDelete) {
		
		if (recordsToChange.size() > 0) {
			throw new RuntimeException("既存データの変更は無いはず");
		}
		
		return AtomTask.of(() -> {
			for (val record : recordsToDelete) {
				
				val key = new RecordKey(record);
				
				switch (record.getTargetName()) {
				case Items.Employee.TARGET_NAME:
					require.deleteEmployeeDataMngInfo(key.employeeId(), key.personId());
					break;
				case Items.Person.TARGET_NAME:
					require.deletePerson(key.personId());
					break;
				case Items.User.TARGET_NAME:
					require.deleteUser(key.userId());
					break;
				case Items.Password.TARGET_NAME:
					require.deleteLoginPasswordOfUser(key.userId());
				}
			}
		});
	}
	
	@RequiredArgsConstructor
	private static class RecordKey {
		private final AnyRecordToDelete record;
		
		private String employeeId() {
			return record.getKey(Items.SID).asString();
		}
		private String personId() {
			return record.getKey(Items.PID).asString();
		}
		private String userId() {
			return record.getKey(Items.USER_ID).asString();
		}
	}
	
	public static interface RequireAdjust {
		
		void deleteEmployeeDataMngInfo(String employeeId, String personId);
		
		void deletePerson(String personId);
		
		void deleteUser(String userId);
		
		void deleteLoginPasswordOfUser(String userId);
	}

	@Override
	public int getItemNoOfEmployeeId() {
		return Items.SID;
	}

}
