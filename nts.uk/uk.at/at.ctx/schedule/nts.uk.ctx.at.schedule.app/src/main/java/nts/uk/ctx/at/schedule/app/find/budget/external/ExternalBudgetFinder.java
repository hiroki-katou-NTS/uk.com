/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.schedule.app.find.budget.external;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.apache.commons.csv.CSVFormat;

import nts.arc.layer.infra.file.storage.StoredFileStreamService;
import nts.gul.csv.NtsCsvReader;
import nts.gul.csv.NtsCsvRecord;
import nts.uk.ctx.at.schedule.app.find.budget.external.actualresult.ExtBudgetDataPreviewDto;
import nts.uk.ctx.at.schedule.app.find.budget.external.actualresult.ExtBudgetExtractCondition;
import nts.uk.ctx.at.schedule.app.find.budget.external.actualresult.ExternalBudgetLogDto;
import nts.uk.ctx.at.schedule.app.find.budget.external.actualresult.ExternalBudgetQuery;
import nts.uk.ctx.at.schedule.app.find.budget.external.actualresult.ExternalBudgetValDto;
import nts.uk.ctx.at.schedule.dom.budget.external.ExternalBudget;
import nts.uk.ctx.at.schedule.dom.budget.external.ExternalBudgetRepository;
import nts.uk.ctx.at.schedule.dom.budget.external.UnitAtr;
import nts.uk.ctx.at.schedule.dom.budget.external.actualresult.Encoding;
import nts.uk.ctx.at.schedule.dom.budget.external.actualresult.ExternalBudgetLogRepository;
import nts.uk.ctx.at.schedule.dom.budget.external.actualresult.service.ExtBudgetFileCheckService;
import nts.uk.shr.com.context.AppContexts;

@Stateless
public class ExternalBudgetFinder {

	@Inject
	private ExternalBudgetRepository externalBudgetRepo;
	
	/** The ext budget log repo. */
	@Inject
    private ExternalBudgetLogRepository extBudgetLogRepo;
	
	/** The file stream service. */
	@Inject
    private StoredFileStreamService fileStreamService;
	
	/** The file check service. */
    @Inject
    private ExtBudgetFileCheckService fileCheckService;
	
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
	 * Validate file.
	 *
	 * @param fileId the file id
	 */
	public void validateFile(String fileId) {
	    // Check valid format file.
        this.fileCheckService.validFileFormat(fileId);
	}
	
	/**
	 * Find data preview.
	 *
	 * @param file the file
	 * @return the ext budget data preview dto
	 */
    public ExtBudgetDataPreviewDto findDataPreview(ExtBudgetExtractCondition extractCondition) {
        
        // Check valid format file.
        this.fileCheckService.validFileFormat(extractCondition.getFileId());
        
        List<ExternalBudgetValDto> lstExtBudgetVal = new ArrayList<>();
        int totalRecord = 0;
        int INDEX_CODE = 0;
        int INDEX_DATE = 1;
        int MAX_RECORD_DISP = 10;
        
        String companyId = AppContexts.user().companyId();
        Optional<ExternalBudget> extBudgetOptional = this.externalBudgetRepo.find(companyId,
                extractCondition.getExternalBudgetCode());
        if (!extBudgetOptional.isPresent()) {
            throw new RuntimeException("Not external budget setting.");
        }
        ExternalBudget externalBudget = extBudgetOptional.get();
        try {
            InputStream inputStream = this.fileStreamService.takeOutFromFileId(extractCondition.getFileId());
            Iterator<NtsCsvRecord> csvRecord = this.findRecordFile(inputStream, extractCondition.getEncoding(),
                    externalBudget.getUnitAtr());
            while(csvRecord.hasNext()) {
                if (totalRecord < MAX_RECORD_DISP) {
                    NtsCsvRecord record = csvRecord.next();
                    String[] lstHeader = this.fakeHeader(externalBudget.getUnitAtr());
                    List<String> result = new ArrayList<>();
                    for (String header : lstHeader) {
                        result.add(record.getColumnAsString(header));
                    }
                    lstExtBudgetVal.add(
                            ExternalBudgetValDto.newExternalBudgetVal(result.get(INDEX_CODE), result.get(INDEX_DATE),
                                    this.findListExtBudgetValue(result.subList(INDEX_DATE + 1, result.size()),
                                            externalBudget.getUnitAtr())));
                }
                totalRecord++;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
	    return ExtBudgetDataPreviewDto.builder()
	            .isDailyUnit(externalBudget.getUnitAtr() == UnitAtr.DAILY)
	            .data(lstExtBudgetVal)
	            .totalRecord(totalRecord)
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
	
	/**
	 * Find list ext budget value.
	 *
	 * @param lstRawVal the lst raw val
	 * @param extBudgetCode the ext budget code
	 * @return the list
	 */
	private List<String> findListExtBudgetValue(List<String> lstRawVal, UnitAtr unitAtr) {
        String valEmpty = "";
        int numberCol = 0;
        switch (unitAtr) {
            case DAILY:
                numberCol = 3;
                break;
            case BYTIMEZONE:
                numberCol = 47;
                break;
            default:
                throw new RuntimeException("Not unit atr suitable.");
        }
        if (lstRawVal.size() < numberCol) {
            do {
                lstRawVal.add(valEmpty);
            } while (lstRawVal.size() <= numberCol);
        } else if (lstRawVal.size() > numberCol){
            lstRawVal.subList(0, numberCol);
        }
        return lstRawVal;
	}
	
	/**
     * Find record file.
     *
     * @param inputStream the input stream
     * @param encoding the encoding
     * @return the iterator
     * @throws IOException Signals that an I/O exception has occurred.
     */
    private Iterator<NtsCsvRecord> findRecordFile(InputStream inputStream, Integer encoding, UnitAtr unitAtr) throws IOException {
        Charset charset = this.getCharset(encoding);
        String newLineCode = "\r\n"; // CR+LF
        NtsCsvReader csvReader = NtsCsvReader.newReader().withChartSet(charset)
                .withFormat(CSVFormat.EXCEL.withHeader(this.fakeHeader(unitAtr)).withRecordSeparator(newLineCode));
        return csvReader.parse(inputStream).iterator();
    }
    
    /**
     * Gets the charset.
     *
     * @param value the value
     * @return the charset
     */
    private Charset getCharset(Integer value) {
        Encoding encoding = Encoding.valueOf(value);
        switch (encoding) {
        case Shift_JIS:
            return Charset.forName("Shift_JIS");
        default:
            return StandardCharsets.UTF_8;
        }
    }
    
    private String[] fakeHeader(UnitAtr unitAtr) {
        switch (unitAtr) {
            case DAILY:
                return new String[] {"職場コード", "年月日", "値"};
            case BYTIMEZONE:
                List<String> lstHeader = new ArrayList<>();
                lstHeader.add("職場コード");
                lstHeader.add("年月日");
                for (int i = 0; i < 48; i++) {
                    lstHeader.add("値" + i);
                }
                return lstHeader.toArray(new String[lstHeader.size()]);
            default:
                throw new RuntimeException("Not unit atr suitable.");
        }
    }
}
