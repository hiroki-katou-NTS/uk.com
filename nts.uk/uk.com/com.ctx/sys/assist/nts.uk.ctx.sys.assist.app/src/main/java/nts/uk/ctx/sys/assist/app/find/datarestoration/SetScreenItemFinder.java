package nts.uk.ctx.sys.assist.app.find.datarestoration;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;

import nts.uk.ctx.sys.assist.dom.datarestoration.LoginPersonInCharge;
import nts.uk.ctx.sys.assist.dom.datarestoration.LoginPersonInChargeService;
import nts.uk.ctx.sys.assist.dom.storage.SystemType;
import nts.uk.ctx.sys.assist.dom.tablelist.TableListRepository;

/**
 * アルゴリズム「画面項目セット」
 */
@Stateless
@TransactionAttribute(TransactionAttributeType.SUPPORTS)
public class SetScreenItemFinder {
	private static final int IS_CANNOT_BE_OLD = 1;
	
	@Inject
	private TableListRepository tableListRepository;
	
	@Inject
	private LoginPersonInChargeService picService;
	
	public List<ItemSetDto> findScreenItem(String dataStorageProcessId) {
		// 「ログイン者が担当者か判断する」を取得する。
		LoginPersonInCharge pic = picService.getPic();
		
		//1. ドメインオブジェクト「テーブル一覧」を取得する。
		//2. 取得したList<テーブル一覧>から「カテゴリのテーブル」をマッピングする。
		List<TableListDto> categoryTableLists = tableListRepository.getByProcessingId(dataStorageProcessId)
																		.stream()
																		.map(TableListDto::fromDomain)
																		.collect(Collectors.toList());
		//List<項目セット>を返す。
		return categoryTableLists.stream()
				.distinct()
				.map(t -> {
					////システム担当区分状態をチェックする。
					if (checkSystemChargeStatus(pic, t.getSystemType())) {
						//ループ中の「カテゴリのテーブル．復旧対象可不可」をチェックする。
						boolean isCannotBeOld = checkCannotBeOld(t);
						return new ItemSetDto(t, isCannotBeOld);
					}
					return new ItemSetDto(t, false);
				})
				.sorted(Comparator.comparing(item -> item.getCategoryTable().getCategoryId()))
				.collect(Collectors.toList());
	}
	
	private boolean checkCannotBeOld(TableListDto t) {
		return t.getCanNotBeOld() == IS_CANNOT_BE_OLD;
	}

	private boolean checkSystemChargeStatus(LoginPersonInCharge pic, int systemType) {
		return (systemType == SystemType.ATTENDANCE_SYSTEM.value && pic.isAttendance())
				|| (systemType == SystemType.OFFICE_HELPER.value && pic.isOfficeHelper())
				|| (systemType == SystemType.PAYROLL_SYSTEM.value && pic.isPayroll())
				|| (systemType == SystemType.PERSON_SYSTEM.value && pic.isPersonnel());
	}
}
