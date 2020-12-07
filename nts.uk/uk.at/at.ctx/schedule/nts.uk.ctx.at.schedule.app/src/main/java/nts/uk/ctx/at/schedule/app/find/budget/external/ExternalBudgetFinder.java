/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.schedule.app.find.budget.external;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.apache.logging.log4j.util.Strings;

import nts.arc.layer.infra.file.storage.StoredFileStreamService;
import nts.gul.collection.CollectionUtil;
import nts.uk.ctx.at.schedule.app.find.budget.external.actualresult.dto.ExtBudgetDataPreviewDto;
import nts.uk.ctx.at.schedule.app.find.budget.external.actualresult.dto.ExtBudgetExtractCondition;
import nts.uk.ctx.at.schedule.app.find.budget.external.actualresult.dto.ExternalBudgetValDto;
import nts.uk.ctx.at.schedule.dom.budget.external.ExternalBudget;
import nts.uk.ctx.at.schedule.dom.budget.external.ExternalBudgetRepository;
import nts.uk.ctx.at.schedule.dom.budget.external.UnitAtr;
import nts.uk.ctx.at.schedule.dom.budget.external.actualresult.service.ExtBudgetFileCheckService;
import nts.uk.ctx.at.schedule.dom.budget.external.actualresult.service.FileUtil;
import nts.uk.shr.com.context.AppContexts;

/**
 * The Class ExternalBudgetFinder.
 */
@Stateless
public class ExternalBudgetFinder {

    /** The external budget repo. */
    @Inject
    private ExternalBudgetRepository externalBudgetRepo;

    /** The file stream service. */
    @Inject
    private StoredFileStreamService fileStreamService;

    /** The file check service. */
    @Inject
    private ExtBudgetFileCheckService fileCheckService;

    /** The default value. */
    private static final int DEFAULT_VALUE = 0;

    /** The index code. */
    private static final int INDEX_CODE = 0;

    /** The index date. */
    private static final int INDEX_DATE = 1;

    /** The index value. */
    private static final int INDEX_VALUE = 2;

    /** The max record disp. */
    private static final int MAX_RECORD_DISP = 10;

    /*
     * get All List iTem of external Budget
     */
    public List<ExternalBudgetDto> findAll() {
        String companyId = AppContexts.user().companyId();
        return this.externalBudgetRepo.findAll(companyId).stream()
                .map(ExternalBudgetDto::fromDomain).collect(Collectors.toList());
    }

    /**
     * find External Budget by BudgetAtr and UnitAtr
     * @param param
     * @return
     * author: Hoang Yen
     */
    public List<ExternalBudgetDto> findByAtr(ParamExternalBudget param){
		String companyId = AppContexts.user().companyId();
		return this.externalBudgetRepo.findByAtr(companyId, param.getBudgetAtr(), param.getUnitAtr())
				.stream()
				.map(x -> {
					return new ExternalBudgetDto(x.getExternalBudgetCd().toString(), x.getExternalBudgetName().toString(), param.getBudgetAtr(), param.getUnitAtr());
				}).collect(Collectors.toList());
    }
    
    
    /**
     * Checks if is daily unit.
     *
     * @param externalBudgetCd
     *            the external budget cd
     * @return true, if is daily unit
     */
    public boolean isDailyUnit(String externalBudgetCd) {
        String companyId = AppContexts.user().companyId();
        return this.externalBudgetRepo.findAll(companyId).stream()
                .anyMatch(p -> p.getExternalBudgetCd().v().equals(externalBudgetCd) && p.getUnitAtr() == UnitAtr.DAILY);
    }

    /**
     * Validate file.
     *
     * @param extractCondition
     *            the extract condition
     */
    public void validateFile(ExtBudgetExtractCondition extractCondition) {
        // Check valid format file.
        this.fileCheckService.validFileFormat(extractCondition.getFileId(), extractCondition.getEncoding(),
                FileUtil.getStandardColumn(this.findSetting(extractCondition.getExternalBudgetCode()).getUnitAtr()));
    }

    /**
     * Find data preview.
     *
     * @param extractCondition
     *            the extract condition
     * @return the ext budget data preview dto
     */
    public ExtBudgetDataPreviewDto findDataPreview(ExtBudgetExtractCondition extractCondition) {
        // find external budget setting
        ExternalBudget externalBudget = this.findSetting(extractCondition.getExternalBudgetCode());

        // get standard column
        Integer standardColumn = FileUtil.getStandardColumn(externalBudget.getUnitAtr());

        // Check valid format file.
        this.fileCheckService.validFileFormat(extractCondition.getFileId(), extractCondition.getEncoding(),
                standardColumn);

        int totalRecord = 0;
        int indexLine = 0;
        List<ExternalBudgetValDto> lstExtBudgetVal = new ArrayList<>();
        try {
            // get input stream by fileId
            InputStream inputStream = this.fileStreamService.takeOutFromFileId(extractCondition.getFileId());

            // get list record
            List<List<String>> lstRecord = FileUtil.findContentFile(inputStream, extractCondition.getEncoding(),
                        standardColumn).entrySet().stream().map(entry -> entry.getValue()).collect(Collectors.toList());
            
            Integer lineStart = extractCondition.getStartLine().v();

            // check file empty data?
            if (!CollectionUtil.isEmpty(lstRecord)) {
                // calculate total record file
                int calTotal = lstRecord.size() - lineStart + 1;
                if (calTotal > totalRecord) {
                    totalRecord = calTotal;
                }
                Iterator<List<String>> recordIterator = lstRecord.iterator();
                while (recordIterator.hasNext()) {
                    List<String> record = recordIterator.next();
                    indexLine++;
                    if (indexLine < lineStart) {
                        continue;
                    }
                    // check max record show client.
                    if (lstExtBudgetVal.size() < MAX_RECORD_DISP) {
                        // fill or split column space if number column is incorrect.
                        this.findListExtBudgetValue(record, externalBudget.getUnitAtr());

                        lstExtBudgetVal.add(ExternalBudgetValDto.newExternalBudgetVal(record.get(INDEX_CODE),
                                record.get(INDEX_DATE), record.subList(INDEX_VALUE, record.size())));
                    }
                }
            }
            // close input stream
            inputStream.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return ExtBudgetDataPreviewDto.builder()
                .isDailyUnit(externalBudget.getUnitAtr() == UnitAtr.DAILY)
                .data(lstExtBudgetVal)
                .totalRecord(totalRecord)
                .build();
    }

    /**
     * Find setting.
     *
     * @param externalBudgetCode the external budget code
     * @return the external budget
     */
    private ExternalBudget findSetting(String externalBudgetCode) {
        String companyId = AppContexts.user().companyId();
        Optional<ExternalBudget> extBudgetOptional = this.externalBudgetRepo.find(companyId, externalBudgetCode);
        if (!extBudgetOptional.isPresent()) {
            throw new RuntimeException("Not external budget setting.");
        }
        return extBudgetOptional.get();
    }

    /**
     * Find list ext budget value.
     *
     * @param lstRawVal
     *            the lst raw val
     * @param unitAtr
     *            the unit atr
     * @return the list
     */
    private List<String> findListExtBudgetValue(List<String> lstRawVal, UnitAtr unitAtr) {
        String valEmpty = Strings.EMPTY;
        
        // get standard column
        int numberCol = FileUtil.getStandardColumn(unitAtr);

        if (lstRawVal.size() > numberCol) {
            lstRawVal.subList(DEFAULT_VALUE, numberCol);
            return lstRawVal;
        }
        if (lstRawVal.size() == numberCol) {
            return lstRawVal;
        }
        while (lstRawVal.size() < numberCol) {
            lstRawVal.add(valEmpty);
        }
        return lstRawVal;
    }
}
