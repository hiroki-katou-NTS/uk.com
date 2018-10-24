package nts.uk.ctx.pereg.app.command.deleteemployee;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;

import lombok.val;
import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.arc.time.GeneralDateTime;
import nts.uk.ctx.at.record.dom.stamp.card.stampcard.StampCard;
import nts.uk.ctx.at.record.dom.stamp.card.stampcard.StampCardRepository;
import nts.uk.ctx.bs.employee.dom.employee.mgndata.EmployeeDataMngInfo;
import nts.uk.ctx.bs.employee.dom.employee.mgndata.EmployeeDataMngInfoRepository;
import nts.uk.ctx.bs.employee.dom.employee.mgndata.EmployeeDeletionAttr;
import nts.uk.ctx.bs.employee.dom.employee.mgndata.RemoveReason;
import nts.uk.ctx.pereg.dom.person.info.category.PerInfoCategoryRepositoty;
import nts.uk.ctx.pereg.dom.person.info.category.PersonInfoCategory;
import nts.uk.ctx.pereg.dom.person.info.item.PerInfoItemDefRepositoty;
import nts.uk.ctx.pereg.dom.person.info.item.PersonInfoItemDefinition;
import nts.uk.ctx.sys.auth.app.find.user.GetUserByEmpFinder;
import nts.uk.ctx.sys.auth.app.find.user.UserAuthDto;
import nts.uk.ctx.sys.log.app.command.pereg.KeySetCorrectionLog;
import nts.uk.ctx.sys.log.app.command.pereg.PersonCategoryCorrectionLogParameter;
import nts.uk.ctx.sys.log.app.command.pereg.PersonCorrectionLogParameter;
import nts.uk.ctx.sys.log.app.command.pereg.PersonCategoryCorrectionLogParameter.PersonCorrectionItemInfo;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.security.audittrail.correction.DataCorrectionContext;
import nts.uk.shr.com.security.audittrail.correction.content.pereg.InfoOperateAttr;
import nts.uk.shr.com.security.audittrail.correction.content.pereg.PersonInfoProcessAttr;
import nts.uk.shr.com.security.audittrail.correction.content.pereg.TargetDataKey;
import nts.uk.shr.com.security.audittrail.correction.content.pereg.TargetDataKey.CalendarKeyType;
import nts.uk.shr.com.security.audittrail.correction.processor.CorrectionProcessorId;

@Stateless
@Transactional
public class EmployeeDeleteCommandHandler extends CommandHandler<EmployeeDeleteCommand> {

	/** The repository. */
	@Inject
	private EmployeeDataMngInfoRepository EmpDataMngRepo;
	
	@Inject
	private StampCardRepository stampCardRepo;
	
	@Inject
	private GetUserByEmpFinder userFinder;
	
	@Inject
	PerInfoCategoryRepositoty personCtgRepo;
	
	@Inject
	PerInfoItemDefRepositoty perItemDf;

	@Override
	protected void handle(CommandHandlerContext<EmployeeDeleteCommand> context) {

		if (context != null) {
			// get command
			EmployeeDeleteCommand command = context.getCommand();
			
			// get EmployeeDataMngInfo
			List<EmployeeDataMngInfo> listEmpData = EmpDataMngRepo.findByEmployeeId(command.getSId());
			if (!listEmpData.isEmpty()) {
				
				// begin process write log
				DataCorrectionContext.transactional(CorrectionProcessorId.PEREG_REGISTER, () -> {
					
					EmployeeDataMngInfo empInfo =  EmpDataMngRepo.findByEmployeeId(command.getSId()).get(0);
					GeneralDateTime currentDatetime = GeneralDateTime.legacyDateTime(new Date());
					empInfo.setDeleteDateTemporary(currentDatetime);
					empInfo.setRemoveReason(new RemoveReason(command.getReason()));
					empInfo.setDeletedStatus(EmployeeDeletionAttr.TEMPDELETED);
					EmpDataMngRepo.updateRemoveReason(empInfo);
					
					List<StampCard> stampCards = stampCardRepo.getListStampCard(command.getSId());
					List<String> cardNos = new ArrayList<>();
					if(!stampCards.isEmpty()) {
						cardNos = stampCards.stream().map(i -> i.getStampNumber().toString()).collect(Collectors.toList());
					}
					
					stampCardRepo.deleteBySid(command.getSId());
					
					setDataLogPersonCorrection(command);
					List<PersonCategoryCorrectionLogParameter> ctgs = setDataLogCategory(command,cardNos);
					if (!ctgs.isEmpty()) {
						ctgs.forEach(cat -> {
							DataCorrectionContext.setParameter(cat.getHashID(), cat);
						});
					}
				});
			} 
		}
	}
	
	private List<PersonCategoryCorrectionLogParameter> setDataLogCategory(EmployeeDeleteCommand command,List<String> cardNos) {
		List<PersonCategoryCorrectionLogParameter> ctgTargets = new ArrayList<>();
		Optional<PersonInfoCategory>  perInfoCtgCS00069 = personCtgRepo.getPerInfoCategoryByCtgCD("CS00069", AppContexts.user().companyId());
		
		
		if (cardNos.isEmpty() || !perInfoCtgCS00069.isPresent())
			return new ArrayList<>();
		List<PersonInfoItemDefinition> lstItemDf = perItemDf.getAllPerInfoItemDefByCategoryId(
				perInfoCtgCS00069.get().getPersonInfoCategoryId(), AppContexts.user().contractCode());
		
		Optional<PersonInfoItemDefinition> itIS00779 = lstItemDf.stream().filter(c -> c.getItemCode().equals("IS00779")).findFirst();
		if(!itIS00779.isPresent())
			return new ArrayList<>();
		
		cardNos.forEach(st -> {
			
			List<PersonCorrectionItemInfo> lstItemInfo = new ArrayList<>();

			PersonCorrectionItemInfo itemInfo = new PersonCorrectionItemInfo(
					itIS00779.get().getPerInfoItemDefId(),
					itIS00779.get().getItemName().toString(),
					st,st,null,null,1);
			lstItemInfo.add(itemInfo);
			
			PersonCategoryCorrectionLogParameter ctgTargetCS00069 = new PersonCategoryCorrectionLogParameter(
					perInfoCtgCS00069.get().getPersonInfoCategoryId(),
					perInfoCtgCS00069.get().getCategoryName().toString(), 
					InfoOperateAttr.DELETE, 
					lstItemInfo.isEmpty() ? null : lstItemInfo,
					new TargetDataKey(CalendarKeyType.NONE, null, st), Optional.empty());
			ctgTargets.add(ctgTargetCS00069);
		});

		return ctgTargets;

	}

	private void setDataLogPersonCorrection(EmployeeDeleteCommand command) {
		//get User From RequestList486 Doctor Hieu
		List<UserAuthDto> userAuth = this.userFinder.getByListEmp(Arrays.asList(command.getSId()));
		UserAuthDto user = new UserAuthDto("", "", "", command.getSId(), "", "");
		if(userAuth.size() > 0) {
			 user = userAuth.get(0);
		}
		// set PeregCorrectionLogParameter
		PersonCorrectionLogParameter target = new PersonCorrectionLogParameter(
				user != null ? user.getUserID() : "",
				user != null ? user.getEmpID() : "", 
				user != null ?user.getUserName(): "",
			    PersonInfoProcessAttr.LOGICAL_DELETE,
			    command.getReason());
		DataCorrectionContext.setParameter(target.getHashID(), target);
	}
}
