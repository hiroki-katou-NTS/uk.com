package nts.uk.ctx.sys.assist.app.find.datarestoration;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;

import nts.gul.text.StringUtil;
import nts.uk.ctx.sys.assist.dom.datarestoration.LoginPersonInCharge;
import nts.uk.ctx.sys.assist.dom.datarestoration.LoginPersonInChargeService;
import nts.uk.ctx.sys.assist.dom.role.RoleImportAdapter;
import nts.uk.ctx.sys.assist.dom.storage.SystemType;
import nts.uk.ctx.sys.assist.dom.tablelist.TableListRepository;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.context.loginuser.role.LoginUserRoles;

@Stateless
@TransactionAttribute(TransactionAttributeType.SUPPORTS)
public class SetScreenItemFinder {
	private static final int IS_CANNOT_BE_OLD = 1;
	
	@Inject
	private TableListRepository tableListRepository;
	
	@Inject
	private LoginPersonInChargeService picService;
	
	public List<ItemSetDto> findScreenItem(String dataStorageProcessId) {
		LoginUserRoles roles = AppContexts.user().roles();
		LoginPersonInCharge pic = picService.getPic(roles);
		
		List<TableListDto> categoryTableLists = tableListRepository.getByProcessingId(dataStorageProcessId)
																		.stream()
																		.map(TableListDto::fromDomain)
																		.collect(Collectors.toList());
		return categoryTableLists.stream()
				.map(t -> {
					if (checkSystemChargeStatus(pic, t.getSystemType())) {
						boolean isCannotBeOld = checkCannotBeOld(t);
						return new ItemSetDto(t, isCannotBeOld);
					}
					return new ItemSetDto(t, false);
				})
				.sorted(Comparator.comparing(item -> item.getCategoryTable().getCategoryId()))
				.collect(Collectors.toList());
	}
	
	private boolean checkCannotBeOld(TableListDto t) {
		return t.getCanNotBeOld().isPresent() && t.getCanNotBeOld().get() == IS_CANNOT_BE_OLD;
	}

	private boolean checkSystemChargeStatus(LoginPersonInCharge pic, int systemType) {
		return (systemType == SystemType.ATTENDANCE_SYSTEM.value && pic.isAttendance())
				|| (systemType == SystemType.OFFICE_HELPER.value && pic.isOfficeHelper())
				|| (systemType == SystemType.PAYROLL_SYSTEM.value && pic.isPayroll())
				|| (systemType == SystemType.PERSON_SYSTEM.value && pic.isPersonnel());
	}
}
