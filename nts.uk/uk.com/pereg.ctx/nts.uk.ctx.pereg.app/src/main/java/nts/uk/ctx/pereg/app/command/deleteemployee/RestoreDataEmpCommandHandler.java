package nts.uk.ctx.pereg.app.command.deleteemployee;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;

import lombok.val;
import nts.arc.error.BusinessException;
import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.bs.employee.dom.employee.mgndata.EmployeeDataMngInfo;
import nts.uk.ctx.bs.employee.dom.employee.mgndata.EmployeeDataMngInfoRepository;
import nts.uk.ctx.bs.employee.dom.employee.mgndata.EmployeeDeletionAttr;
import nts.uk.ctx.bs.employee.dom.employeeinfo.EmployeeCode;
import nts.uk.ctx.bs.person.dom.person.info.Person;
import nts.uk.ctx.bs.person.dom.person.info.PersonRepository;
import nts.uk.ctx.bs.person.dom.person.info.personnamegroup.BusinessName;
import nts.uk.ctx.bs.person.dom.person.info.personnamegroup.PersonNameGroup;
import nts.uk.ctx.pereg.dom.person.info.category.PerInfoCategoryRepositoty;
import nts.uk.ctx.pereg.dom.person.info.category.PersonInfoCategory;
import nts.uk.ctx.pereg.dom.person.info.item.PerInfoItemDefRepositoty;
import nts.uk.ctx.pereg.dom.person.info.item.PersonInfoItemDefinition;
import nts.uk.ctx.sys.log.app.command.pereg.KeySetCorrectionLog;
import nts.uk.ctx.sys.log.app.command.pereg.PersonCategoryCorrectionLogParameter;
import nts.uk.ctx.sys.log.app.command.pereg.PersonCorrectionLogParameter;
import nts.uk.ctx.sys.log.app.command.pereg.PersonCategoryCorrectionLogParameter.CategoryCorrectionTarget;
import nts.uk.ctx.sys.log.app.command.pereg.PersonCategoryCorrectionLogParameter.PersonCorrectionItemInfo;
import nts.uk.ctx.sys.log.app.command.pereg.PersonCorrectionLogParameter.PersonCorrectionTarget;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.security.audittrail.correction.DataCorrectionContext;
import nts.uk.shr.com.security.audittrail.correction.content.DataValueAttribute;
import nts.uk.shr.com.security.audittrail.correction.content.TargetDataKey;
import nts.uk.shr.com.security.audittrail.correction.content.TargetDataKey.CalendarKeyType;
import nts.uk.shr.com.security.audittrail.correction.content.pereg.InfoOperateAttr;
import nts.uk.shr.com.security.audittrail.correction.content.pereg.PersonInfoProcessAttr;
import nts.uk.shr.com.security.audittrail.correction.processor.CorrectionProcessorId;

@Stateless
@Transactional
public class RestoreDataEmpCommandHandler extends CommandHandler<EmployeeDeleteToRestoreCommand> {

	@Inject
	PersonRepository personRepo;

	@Inject
	private EmployeeDataMngInfoRepository empDataMngRepo;
	
	@Inject
	PerInfoCategoryRepositoty personCtgRepo;
	
	@Inject
	PerInfoItemDefRepositoty perItemDf;

	@Override
	protected void handle(CommandHandlerContext<EmployeeDeleteToRestoreCommand> context) {

		EmployeeDeleteToRestoreCommand command = context.getCommand();

		if (command != null) {
			
			//check Exit by SCD
			Optional<EmployeeDataMngInfo> checkEmpByScd = empDataMngRepo.findByEmployeCD(command.getCode(), AppContexts.user().companyId());
			
			if(checkEmpByScd.isPresent()) {
				
				throw new BusinessException("Msg_345");
			}
			
			
			List<EmployeeDataMngInfo> listEmpData = empDataMngRepo.findByEmployeeId(command.getId());

			if (!listEmpData.isEmpty()) {
				
				DataCorrectionContext.transactionBegun(CorrectionProcessorId.PEREG_REGISTER);
				
				
				EmployeeDataMngInfo empInfo = listEmpData.get(0);
				
				String  scdBefore = empInfo.getEmployeeCode().toString();
				
				empInfo.setEmployeeCode(new EmployeeCode(command.getCode().toString()));
				empInfo.setDeletedStatus(EmployeeDeletionAttr.NOTDELETED);
				empInfo.setDeleteDateTemporary(null);
				empInfo.setRemoveReason(null);

				empDataMngRepo.updateRemoveReason(empInfo);

				// get Person
				Person person = personRepo.getByPersonId(empInfo.getPersonId()).get();
				String nameBefore = person.getPersonNameGroup().getBusinessName().v();
				PersonNameGroup nameGroup = person.getPersonNameGroup();
				nameGroup.setBusinessName(new BusinessName(command.getName()));
				person.setPersonNameGroup(nameGroup);
				personRepo.update(person);
				
				
				setDataLogPersonCorrection();
				
				List<CategoryCorrectionTarget> ctgTargets = setDataLogCategory(command, scdBefore, nameBefore);
				
				PersonCategoryCorrectionLogParameter personCtg = new PersonCategoryCorrectionLogParameter(ctgTargets.stream().filter( c -> c != null).collect(Collectors.toList()));
				DataCorrectionContext.setParameter(String.valueOf(KeySetCorrectionLog.CATEGORY_CORRECTION_LOG.value), personCtg);
				DataCorrectionContext.transactionFinishing();
			}
		}
	}
	
	private void setDataLogPersonCorrection() {
		// set PeregCorrectionLogParameter
		PersonCorrectionTarget target = new PersonCorrectionTarget(
				"userId",
				"employeeId", 
				"userName",
			    PersonInfoProcessAttr.RESTORE_LOGICAL_DELETE, null);

		// set correction log
		PersonCorrectionLogParameter correction = new PersonCorrectionLogParameter(Arrays.asList(target));
		DataCorrectionContext.setParameter(String.valueOf(KeySetCorrectionLog.PERSON_CORRECTION_LOG.value), correction);
	}
	

	/**
	 * @param command
	 * @param scdBefore
	 * @param nameBefore
	 * @return
	 */
	private List<CategoryCorrectionTarget> setDataLogCategory(EmployeeDeleteToRestoreCommand command, String scdBefore,
			String nameBefore) {
		List<CategoryCorrectionTarget> ctgTargets = new ArrayList<>();

		// Log cho category CS00001
		Optional<PersonInfoCategory>  perInfoCtgCS00001 = personCtgRepo.getPerInfoCategoryByCtgCD("CS00001", AppContexts.user().companyId());
		List<PersonCorrectionItemInfo> lstItemInfoCS00001 = new ArrayList<>();
		
		if (perInfoCtgCS00001.isPresent()) {
			List<PersonInfoItemDefinition> lstItemDf = perItemDf.getAllPerInfoItemDefByCategoryId(
					perInfoCtgCS00001.get().getPersonInfoCategoryId(), AppContexts.user().contractCode());

			Optional<PersonInfoItemDefinition> itCS00001 = lstItemDf.stream()
					.filter(c -> c.getItemCode().compareTo("IS00001") > 0).findFirst();

			if (itCS00001.isPresent()) {
				lstItemInfoCS00001.add(new PersonCorrectionItemInfo(itCS00001.get().getPerInfoItemDefId(),
						itCS00001.get().getItemName().toString(), scdBefore,
						command.getCode().toString(), DataValueAttribute.STRING.value));
			}
			
			CategoryCorrectionTarget ctgTargetCS00001 = new CategoryCorrectionTarget(
					perInfoCtgCS00001.get().getCategoryName().toString(), 
					InfoOperateAttr.UPDATE, 
					lstItemInfoCS00001.isEmpty() ? null : lstItemInfoCS00001,
					new TargetDataKey(CalendarKeyType.NONE, null, command.getCode().toString()), null);
			
			ctgTargets.add(ctgTargetCS00001);
		}
		
		// Log cho category CS00002
		Optional<PersonInfoCategory>  perInfoCtgCS00002 = personCtgRepo.getPerInfoCategoryByCtgCD("CS00002", AppContexts.user().companyId());
		List<PersonCorrectionItemInfo> lstItemInfoCS00002 = new ArrayList<>();
		
		if (perInfoCtgCS00002.isPresent()) {
			List<PersonInfoItemDefinition> lstItemDf = perItemDf.getAllPerInfoItemDefByCategoryId(
					perInfoCtgCS00002.get().getPersonInfoCategoryId(), AppContexts.user().contractCode());

			Optional<PersonInfoItemDefinition> itCS00003 = lstItemDf.stream()
					.filter(c -> c.getItemCode().compareTo("IS00003") > 0).findFirst();

			if (itCS00003.isPresent()) {
				lstItemInfoCS00002.add(new PersonCorrectionItemInfo(itCS00003.get().getPerInfoItemDefId(),
						itCS00003.get().getItemName().toString(), nameBefore ,
						command.getName().toString(), DataValueAttribute.STRING.value));
			}
			
			CategoryCorrectionTarget ctgTargetCS00002 = new CategoryCorrectionTarget(
					perInfoCtgCS00001.get().getCategoryName().toString(), 
					InfoOperateAttr.UPDATE, 
					lstItemInfoCS00002.isEmpty() ? null : lstItemInfoCS00002,
					new TargetDataKey(CalendarKeyType.NONE, null,null), null);
			
			ctgTargets.add(ctgTargetCS00002);
		}
		return ctgTargets;
	}

}
