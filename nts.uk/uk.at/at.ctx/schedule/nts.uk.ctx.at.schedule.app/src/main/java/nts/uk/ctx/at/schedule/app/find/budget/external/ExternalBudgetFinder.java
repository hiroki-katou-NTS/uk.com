/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.schedule.app.find.budget.external;

import java.io.File;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.layer.infra.file.storage.StoredFileInfoRepository;
import nts.arc.layer.infra.file.storage.StoredFileStreamService;
import nts.arc.system.ServerSystemProperties;
import nts.uk.ctx.at.schedule.app.find.budget.external.actualresult.ExtBudgetDataPreviewDto;
import nts.uk.ctx.at.schedule.app.find.budget.external.actualresult.ExtBudgetExtractCondition;
import nts.uk.ctx.at.schedule.app.find.budget.external.actualresult.ExtBudgetFileReaderServiceImpl;
import nts.uk.ctx.at.schedule.app.find.budget.external.actualresult.ExternalBudgetLogDto;
import nts.uk.ctx.at.schedule.app.find.budget.external.actualresult.ExternalBudgetQuery;
import nts.uk.ctx.at.schedule.app.find.budget.external.actualresult.ExternalBudgetValDto;
import nts.uk.ctx.at.schedule.dom.budget.external.ExternalBudget;
import nts.uk.ctx.at.schedule.dom.budget.external.ExternalBudgetRepository;
import nts.uk.ctx.at.schedule.dom.budget.external.actualresult.ExternalBudgetLogRepository;
import nts.uk.shr.com.context.AppContexts;

@Stateless
public class ExternalBudgetFinder {

	@Inject
	private ExternalBudgetRepository externalBudgetRepo;
	
	/** The ext budget log repo. */
	@Inject
    private ExternalBudgetLogRepository extBudgetLogRepo;
	
	@Inject
	private StoredFileInfoRepository fileInfoRepository;
	
	@Inject
    private StoredFileStreamService fileStreamService;
	
	/*
	 * get All List iTem of external Budget
	 */
	public List<ExternalBudgetDto> findAll() {
		String companyId = AppContexts.user().companyId();
		List<ExternalBudgetDto> lstBudget = this.externalBudgetRepo.findAll(companyId).stream()
				.map(item -> ExternalBudgetDto.fromDomain(item))
				.collect(Collectors.toList());
		return lstBudget;
	}
	
	/**
	 * Find data preview.
	 *
	 * @param file the file
	 * @return the ext budget data preview dto
	 */
	@SuppressWarnings("unchecked")
    public ExtBudgetDataPreviewDto findDataPreview(ExtBudgetExtractCondition extractCondition) {
	    System.out.println(new File(ServerSystemProperties.fileStoragePath()).toPath().resolve(extractCondition.getFileId()).toString());
//	    InputStream inputStream = null;
//	    try {
//            inputStream = this.fileStreamService.takeOutFromFileId(extractCondition.getFileId());
//        } catch (BusinessException businessException) {
//            throw new BusinessException("Msg_158");
//        }
//	    
//	    Optional<StoredFileInfo> optional = this.fileInfoRepository.find(extractCondition.getFileId());
//        if(!optional.isPresent()){
//            new RuntimeException("stored file info is not found.");
//        }
//        StoredFileInfo storagedFileInfor = optional.get();
//        
//        // check file extension
//        List<String> FILE_EXTENSION_ARR = Arrays.asList("text, csv");
//        if (!FILE_EXTENSION_ARR.contains(storagedFileInfor.getFileType().toLowerCase())) {
//            throw new BusinessException("Msg_159"); 
//        }
//	    
        
        String companyId = AppContexts.user().companyId();
        Optional<ExternalBudget> extBudgetOptional = this.externalBudgetRepo.find(companyId,
                extractCondition.getExternalBudgetCode());
        if (!extBudgetOptional.isPresent()) {
            throw new RuntimeException("Not external budget setting.");
        }
        
        ExternalBudget externalBudget = extBudgetOptional.get();
        ExtBudgetFileReaderServiceImpl fileReader = new ExtBudgetFileReaderServiceImpl(extractCondition, externalBudget,
                this.fileInfoRepository, this.fileStreamService);
	    Map<String, Object> mapData = fileReader.findDataPreview();
	    return ExtBudgetDataPreviewDto.builder()
	            .data((List<ExternalBudgetValDto>) mapData.get(ExtBudgetFileReaderServiceImpl.DATA_PREVIEW))
	            .totalRecord((Integer) mapData.get(ExtBudgetFileReaderServiceImpl.TOTAL_RECORD))
	            .build();
	}
	
	/**
	 * Find external budget log.
	 *
	 * @param startDate the start date
	 * @return the list
	 */
	public List<ExternalBudgetLogDto> findExternalBudgetLog(ExternalBudgetQuery query) {
	    String companyId = AppContexts.user().companyId();
	    String employeeIdLogin = AppContexts.user().employeeId();
	    Map<String, String> mapBudget = this.externalBudgetRepo.findAll(companyId).stream()
	            .collect(Collectors.toMap(item -> item.getExternalBudgetCd().v(),
	                    item -> item.getExternalBudgetName().v()));
	    return this.extBudgetLogRepo.findExternalBudgetLog(employeeIdLogin, query.getStartDate(), query.getEndDate(),
	            query.getListState()).stream()
	            .map(domain -> {
	                ExternalBudgetLogDto dto = new ExternalBudgetLogDto();
	                domain.saveToMemento(dto);
	                
	                // set external budget name
	                dto.extBudgetName = mapBudget.get(domain.getExtBudgetCode().v());
	                return dto;
	            })
	            .collect(Collectors.toList());
	}
	
}
