package nts.uk.ctx.pereg.app.command.deleteemployee;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;

import nts.arc.error.BusinessException;
import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
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
import nts.uk.ctx.sys.auth.app.find.user.GetUserByEmpFinder;
import nts.uk.ctx.sys.auth.app.find.user.UserAuthDto;
import nts.uk.ctx.sys.log.app.command.pereg.PersonCategoryCorrectionLogParameter;
import nts.uk.ctx.sys.log.app.command.pereg.PersonCategoryCorrectionLogParameter.PersonCorrectionItemInfo;
import nts.uk.ctx.sys.log.app.command.pereg.PersonCorrectionLogParameter;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.security.audittrail.correction.DataCorrectionContext;
import nts.uk.shr.com.security.audittrail.correction.content.pereg.TargetDataKey;
import nts.uk.shr.com.security.audittrail.correction.content.pereg.TargetDataKey.CalendarKeyType;
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
	
	@Inject
	private GetUserByEmpFinder userFinder;

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
				
				DataCorrectionContext.transactional(CorrectionProcessorId.PEREG_REGISTER, () -> {
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
					person.getPersonNameGroup().setBusinessName(new BusinessName(command.getName()));
					personRepo.update(person);
					
					setDataLogPersonCorrection(command);
					List<PersonCategoryCorrectionLogParameter> ctgs = setDataLogCategory(command, scdBefore, nameBefore);
					if (!ctgs.isEmpty()) {
						ctgs.forEach(cat -> {
							DataCorrectionContext.setParameter(cat.getHashID(), cat);
						});
					}
				});
			}
		}
	}
	
	private void setDataLogPersonCorrection(EmployeeDeleteToRestoreCommand command) {
		//get User From RequestList486 Doctor Hieu
		List<UserAuthDto> userAuth = this.userFinder.getByListEmp(Arrays.asList(command.getId()));
		UserAuthDto user = new UserAuthDto("", "", "", command.getId() , "", "");
		if(userAuth.size() > 0) {
			 user = userAuth.get(0);
		}
		// set PeregCorrectionLogParameter
		PersonCorrectionLogParameter target = new PersonCorrectionLogParameter(
				user != null ? user.getUserID() : "",
				user != null ? user.getEmpID() : "", 
				user != null ?user.getEmpName(): "",
			    PersonInfoProcessAttr.RESTORE_LOGICAL_DELETE, null);
		DataCorrectionContext.setParameter(target.getHashID(), target);
	}
	

	/**
	 * @param command
	 * @param scdBefore
	 * @param nameBefore
	 * @return
	 */
	private List<PersonCategoryCorrectionLogParameter> setDataLogCategory(EmployeeDeleteToRestoreCommand command, String scdBefore,
			String nameBefore) {
		List<PersonCategoryCorrectionLogParameter> ctgTargets = new ArrayList<>();
		// Log cho category CS00001
		Optional<PersonInfoCategory>  perInfoCtgCS00001 = personCtgRepo.getPerInfoCategoryByCtgCD("CS00001", AppContexts.user().companyId());
		List<PersonCorrectionItemInfo> lstItemInfoCS00001 = new ArrayList<>();
		
		if(!command.getCode().equals(scdBefore)) {
			if (perInfoCtgCS00001.isPresent()) {
				List<PersonInfoItemDefinition> lstItemDf = perItemDf.getAllPerInfoItemDefByCategoryId(
						perInfoCtgCS00001.get().getPersonInfoCategoryId(), AppContexts.user().contractCode());
	
				Optional<PersonInfoItemDefinition> itCS00001 = lstItemDf.stream().filter(c -> c.getItemCode().equals("IS00001")).findFirst();
	
				if (itCS00001.isPresent()) {
					lstItemInfoCS00001.add(new PersonCorrectionItemInfo(
							itCS00001.get().getPerInfoItemDefId(),
							itCS00001.get().getItemName().toString(),
							scdBefore,
							scdBefore,
							command.getCode().toString(),
							command.getCode().toString(),
							1));
				}
				
				PersonCategoryCorrectionLogParameter ctgTargetCS00001 = new PersonCategoryCorrectionLogParameter(
						perInfoCtgCS00001.get().getPersonInfoCategoryId(),
						perInfoCtgCS00001.get().getCategoryName().toString(), 
						InfoOperateAttr.UPDATE, 
						lstItemInfoCS00001.isEmpty() ? null : lstItemInfoCS00001,
						new TargetDataKey(CalendarKeyType.NONE, null, command.getCode().toString()), Optional.empty());
				ctgTargets.add(ctgTargetCS00001);
			}
		}
		
		// Log cho category CS00002
		Optional<PersonInfoCategory>  perInfoCtgCS00002 = personCtgRepo.getPerInfoCategoryByCtgCD("CS00002", AppContexts.user().companyId());
		List<PersonCorrectionItemInfo> lstItemInfoCS00002 = new ArrayList<>();
		
		if(!command.getName().equals(nameBefore)) {
			if (perInfoCtgCS00002.isPresent()) {
				List<PersonInfoItemDefinition> lstItemDf = perItemDf.getAllPerInfoItemDefByCategoryId(
						perInfoCtgCS00002.get().getPersonInfoCategoryId(), AppContexts.user().contractCode());
	
				Optional<PersonInfoItemDefinition> itCS00003 = lstItemDf.stream().filter(c -> c.getItemCode().equals("IS00009")).findFirst();
	
				if (itCS00003.isPresent()) {
					
					lstItemInfoCS00002.add(new PersonCorrectionItemInfo(
							itCS00003.get().getPerInfoItemDefId(),
							itCS00003.get().getItemName().toString(), 
							nameBefore ,
							nameBefore ,
							command.getName().toString(), 
							command.getName().toString(), 
							1)); 
				}
				
				PersonCategoryCorrectionLogParameter ctgTargetCS00002 = new PersonCategoryCorrectionLogParameter(
						perInfoCtgCS00002.get().getPersonInfoCategoryId(),
						perInfoCtgCS00002.get().getCategoryName().toString(), 
						InfoOperateAttr.UPDATE, 
						lstItemInfoCS00002.isEmpty() ? null : lstItemInfoCS00002,
						new TargetDataKey(CalendarKeyType.NONE, null,null), Optional.empty());
				ctgTargets.add(ctgTargetCS00002);
			}
		}
		return ctgTargets;
	}

}
