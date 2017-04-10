package nts.uk.ctx.basic.app.command.organization.workplace;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.error.BusinessException;
import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.arc.time.GeneralDate;
import nts.gul.text.IdentifierUtil;
import nts.uk.ctx.basic.dom.organization.department.Department;
import nts.uk.ctx.basic.dom.organization.department.DepartmentCode;
import nts.uk.ctx.basic.dom.organization.department.DepartmentGenericName;
import nts.uk.ctx.basic.dom.organization.department.DepartmentMemo;
import nts.uk.ctx.basic.dom.organization.department.DepartmentName;
import nts.uk.ctx.basic.dom.organization.shr.HierarchyCode;
import nts.uk.ctx.basic.dom.organization.workplace.ParentChildAttribute;
import nts.uk.ctx.basic.dom.organization.workplace.WorkPlace;
import nts.uk.ctx.basic.dom.organization.workplace.WorkPlaceCode;
import nts.uk.ctx.basic.dom.organization.workplace.WorkPlaceGenericName;
import nts.uk.ctx.basic.dom.organization.workplace.WorkPlaceMemo;
import nts.uk.ctx.basic.dom.organization.workplace.WorkPlaceName;
import nts.uk.ctx.basic.dom.organization.workplace.WorkPlaceRepository;
import nts.uk.ctx.basic.dom.organization.workplace.WorkPlaceShortName;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.primitive.Memo;

@Stateless
public class AddWorkPlaceCommandHandler extends CommandHandler<List<AddWorkPlaceCommand>> {

	@Inject
	private WorkPlaceRepository workPlaceRepository;

	@Override
	protected void handle(CommandHandlerContext<List<AddWorkPlaceCommand>> context) {
		Date startDate1 = new Date();
		Date endDate1 = new Date();
		String companyCode = AppContexts.user().companyCode();
		List<WorkPlace> listDep = new ArrayList<WorkPlace>();
		String newhistoryId = IdentifierUtil.randomUniqueId();
		if (context.getCommand().size() == 1) {

			SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd");
			try {
				startDate1 = formatter.parse(context.getCommand().get(0).getStartDate());
				endDate1 = formatter.parse(context.getCommand().get(0).getEndDate());
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			GeneralDate startDate = GeneralDate.legacyDate(startDate1);
			GeneralDate endDate = GeneralDate.legacyDate(endDate1);

			WorkPlace workPlace = null;
			if (context.getCommand().get(0).getHistoryId() == null) {
				workPlace = new WorkPlace(companyCode,
						new WorkPlaceCode(context.getCommand().get(0).getWorkPlaceCode()), endDate,
						new WorkPlaceCode(context.getCommand().get(0).getExternalCode() != null
								? context.getCommand().get(0).getExternalCode() : ""),
						new WorkPlaceGenericName(context.getCommand().get(0).getFullName() != null
								? context.getCommand().get(0).getFullName() : ""),
						new HierarchyCode(context.getCommand().get(0).getHierarchyCode()),
						new WorkPlaceName(context.getCommand().get(0).getName() != null
								? context.getCommand().get(0).getName() : ""),
						new ParentChildAttribute(
								new BigDecimal(context.getCommand().get(0).getParentChildAttribute1())),
						new ParentChildAttribute(
								new BigDecimal(context.getCommand().get(0).getParentChildAttribute2())),
						new WorkPlaceCode(context.getCommand().get(0).getParentWorkCode1()),
						new WorkPlaceCode(context.getCommand().get(0).getParentWorkCode2()),
						new WorkPlaceShortName(context.getCommand().get(0).getShortName() != null
								? context.getCommand().get(0).getShortName() : ""),
						startDate);

				String historyId = workPlace.getHistoryId();
				addMemo(companyCode, historyId, context.getCommand().get(0).getMemo());
			} else {

				if (workPlaceRepository.isDuplicateWorkPlaceCode(companyCode,
						new WorkPlaceCode(context.getCommand().get(0).getWorkPlaceCode()),
						context.getCommand().get(0).getHistoryId())) {
					throw new BusinessException("ER026");
				}

				workPlace = new WorkPlace(companyCode,
						new WorkPlaceCode(context.getCommand().get(0).getWorkPlaceCode()),
						context.getCommand().get(0).getHistoryId(), endDate,
						new WorkPlaceCode(context.getCommand().get(0).getExternalCode() != null
								? context.getCommand().get(0).getExternalCode() : ""),
						new WorkPlaceGenericName(context.getCommand().get(0).getFullName() != null
								? context.getCommand().get(0).getFullName() : ""),
						new HierarchyCode(context.getCommand().get(0).getHierarchyCode()),
						new WorkPlaceName(context.getCommand().get(0).getName() != null
								? context.getCommand().get(0).getName() : ""),
						new ParentChildAttribute(
								new BigDecimal(context.getCommand().get(0).getParentChildAttribute1())),
						new ParentChildAttribute(
								new BigDecimal(context.getCommand().get(0).getParentChildAttribute2())),
						new WorkPlaceCode(context.getCommand().get(0).getParentWorkCode1()),
						new WorkPlaceCode(context.getCommand().get(0).getParentWorkCode2()),
						new WorkPlaceShortName(context.getCommand().get(0).getShortName() != null
								? context.getCommand().get(0).getShortName() : ""),
						startDate);
				updateMemo(companyCode, context.getCommand().get(0).getHistoryId(),
						context.getCommand().get(0).getMemo());
			}
			workPlaceRepository.add(workPlace);

		}
	}

	void addMemo(String companyCode, String historyId, String memo) {
		workPlaceRepository.registerMemo(companyCode, historyId, new Memo(memo));
	}
	
	void updateMemo(String companyCode, String historyId, String memo) {
		workPlaceRepository.updateMemo(new WorkPlaceMemo(companyCode, historyId, new Memo(memo)));
	}

}
