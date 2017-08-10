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
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.apache.commons.csv.CSVFormat;

import nts.arc.layer.infra.file.storage.StoredFileStreamService;
import nts.gul.csv.NtsCsvReader;
import nts.gul.csv.NtsCsvRecord;
import nts.uk.ctx.at.schedule.app.find.budget.external.actualresult.dto.ExtBudgetDataPreviewDto;
import nts.uk.ctx.at.schedule.app.find.budget.external.actualresult.dto.ExtBudgetExtractCondition;
import nts.uk.ctx.at.schedule.app.find.budget.external.actualresult.dto.ExternalBudgetValDto;
import nts.uk.ctx.at.schedule.dom.budget.external.ExternalBudget;
import nts.uk.ctx.at.schedule.dom.budget.external.ExternalBudgetRepository;
import nts.uk.ctx.at.schedule.dom.budget.external.UnitAtr;
import nts.uk.ctx.at.schedule.dom.budget.external.actualresult.Encoding;
import nts.uk.ctx.at.schedule.dom.budget.external.actualresult.service.ExtBudgetFileCheckService;
import nts.uk.shr.com.context.AppContexts;

@Stateless
public class ExternalBudgetFinder {

	@Inject
	private ExternalBudgetRepository externalBudgetRepo;
	
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
        int INDEX_CODE = 0;
        int INDEX_DATE = 1;
        int INDEX_VALUE = 2;
        int MAX_RECORD_DISP = 10;
        
        String companyId = AppContexts.user().companyId();
        Optional<ExternalBudget> extBudgetOptional = this.externalBudgetRepo.find(companyId,
                extractCondition.getExternalBudgetCode());
        if (!extBudgetOptional.isPresent()) {
            throw new RuntimeException("Not external budget setting.");
        }
        ExternalBudget externalBudget = extBudgetOptional.get();
        int MAX_COLUMN = 51;
        int totalRecord = 0;
        int indexLine = 0;
        int countLine = 1;
        try {
            InputStream inputStream = this.fileStreamService.takeOutFromFileId(extractCondition.getFileId());
            List<NtsCsvRecord> csvRecords = this.findRecordFile(inputStream, extractCondition.getEncoding(),
                    externalBudget.getUnitAtr());
            totalRecord = csvRecords.size() - extractCondition.getStartLine() + 1;
            Iterator<NtsCsvRecord> csvRecordIterator = csvRecords.iterator();
            while(csvRecordIterator.hasNext()) {
                NtsCsvRecord record = csvRecordIterator.next();
                indexLine++;
                if (indexLine < extractCondition.getStartLine()) {
                    continue;
                }
                if (countLine <= MAX_RECORD_DISP) {
                    List<String> result = new ArrayList<>();
                    for (int idxCol = 0; idxCol < MAX_COLUMN; idxCol++) {
                        Object value = record.getColumn(idxCol);
                        if (value == null) {
                            continue;
                        }
                        result.add(value.toString());
                    }
                    lstExtBudgetVal.add(
                            ExternalBudgetValDto.newExternalBudgetVal(result.get(INDEX_CODE), result.get(INDEX_DATE),
                                    this.findListExtBudgetValue(result.subList(INDEX_VALUE, result.size()),
                                            externalBudget.getUnitAtr())));
                    countLine++;
                }
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
     * Find record file.
     *
     * @param inputStream the input stream
     * @param encoding the encoding
     * @return the iterator
     * @throws IOException Signals that an I/O exception has occurred.
     */
    private List<NtsCsvRecord> findRecordFile(InputStream inputStream, Integer encoding, UnitAtr unitAtr) throws IOException {
        Charset charset = this.getCharset(encoding);
        String newLineCode = "\r\n"; // CR+LF
        NtsCsvReader csvReader = NtsCsvReader.newReader().withNoHeader().withChartSet(charset)
                .withFormat(CSVFormat.EXCEL.withRecordSeparator(newLineCode));
        return csvReader.parse(inputStream);
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
                numberCol = 1;
                break;
            case BYTIMEZONE:
                numberCol = 47;
                break;
            default:
                throw new RuntimeException("Not unit atr suitable.");
        }
        if (lstRawVal.size() > numberCol) {
            lstRawVal.subList(0, numberCol);
            return lstRawVal;
        }
        if (lstRawVal.size() == numberCol) {
            return lstRawVal;
        }
        do {
            lstRawVal.add(valEmpty);
        } while (lstRawVal.size() <= numberCol);
        return lstRawVal;
    }
}
