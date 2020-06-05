package approve.employee.workplace.workplacegroup;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import lombok.AllArgsConstructor;
import nts.arc.error.BusinessException;
import nts.arc.layer.app.file.export.ExportService;
import nts.arc.layer.app.file.export.ExportServiceContext;
import nts.arc.time.GeneralDate;
import nts.arc.time.GeneralDateTime;
import nts.uk.ctx.bs.employee.dom.workplace.group.AffWorkplaceGroup;
import nts.uk.ctx.bs.employee.dom.workplace.group.AffWorkplaceGroupRespository;
import nts.uk.ctx.bs.employee.dom.workplace.group.domainservice.GetWorkplaceNotWorkgroupService;
import nts.uk.ctx.bs.employee.dom.workplace.master.WorkplaceInformation;
import nts.uk.ctx.bs.employee.dom.workplace.master.service.WorkplaceExportService;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.i18n.TextResource;

@Stateless
public class CreateUnsetWorkplaceReport extends ExportService<GeneralDate>  {
	
	@Inject
	private WorkplaceExportService workplaceExportService;
	
	@Inject
	private AffWorkplaceGroupRespository affWorkplaceGroupRespository;
	
	@Inject
	private CreateUnsetWorkplaceGenerator createUnsetWorkplaceGenerator;

	@Override
	protected void handle(ExportServiceContext<GeneralDate> context) {
		GeneralDate baseDate = context.getQuery();
		GetWorkplaceNotWorkgroupService.Require require = new GetWorkplaceNotWorkgroupRequireImpl(workplaceExportService, affWorkplaceGroupRespository);
		List<WorkplaceInformation> listWorkplaceInformation = GetWorkplaceNotWorkgroupService.getWorkplace(require, baseDate);
		if(listWorkplaceInformation.isEmpty()) {
			throw new BusinessException("Msg_672");
		}
		OutputExportKSM007  outputExportKSM007 = convertToData(baseDate,listWorkplaceInformation);
		this.createUnsetWorkplaceGenerator.generate(context.getGeneratorContext(), outputExportKSM007);
	}
	
	private OutputExportKSM007 convertToData(GeneralDate baseDate,List<WorkplaceInformation> listWorkplaceInformation) {
		CreateUnsetWorkplaceHeader header = new CreateUnsetWorkplaceHeader();
		header.setTitle(TextResource.localize("KSM007_16")); 
		header.setDatePeriodHead(baseDate+ " "+ GeneralDateTime.now().toString("HH:mm")); 
		
		List<WorkplaceInforDto> listWorkplaceInfor = new ArrayList<>();
		listWorkplaceInformation.forEach(value ->{
			listWorkplaceInfor.add(new WorkplaceInforDto(
					value.getWorkplaceCode().v(), value.getWorkplaceName().v(), value.getWorkplaceGeneric().v())
				);
		});
		return new OutputExportKSM007(header,listWorkplaceInfor);
	}
	
	@AllArgsConstructor
	private class GetWorkplaceNotWorkgroupRequireImpl implements GetWorkplaceNotWorkgroupService.Require {
		
		@Inject
		private WorkplaceExportService workplaceExportService;
		
		@Inject
		private AffWorkplaceGroupRespository affWorkplaceGroupRespository;

		@Override
		public List<WorkplaceInformation> getAllActiveWorkplace(GeneralDate baseDate) {
			String companyId = AppContexts.user().companyId();
			return workplaceExportService.getAllActiveWorkplace(companyId, baseDate);
		}

		@Override
		public List<AffWorkplaceGroup> getAll() {
			String companyId = AppContexts.user().companyId();
			return affWorkplaceGroupRespository.getAll(companyId);
		}
	
		
	}

}
