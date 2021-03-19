/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.schedule.app.export.budget.external.actualresult;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.error.BusinessException;
import nts.arc.layer.app.file.export.ExportService;
import nts.arc.layer.app.file.export.ExportServiceContext;
import nts.gul.collection.CollectionUtil;
import nts.uk.ctx.at.schedule.app.find.budget.external.actualresult.error.dto.ExternalBudgetErrorDto;
import nts.uk.ctx.at.schedule.dom.budget.external.acceptance.error.ExternalBudgetError;
import nts.uk.ctx.at.schedule.dom.budget.external.acceptance.error.ExternalBudgetErrorRepository;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.i18n.TextResource;

/**
 * The Class ExtBudgetErrorExportService.
 */
@Stateless
public class ExtBudgetErrorExportService extends ExportService<String> {

    /** The generator. */
    @Inject
    private ExtBudgetErrorGenerator generator;

    /** The error repo. */
    @Inject
    private ExternalBudgetErrorRepository errorRepo;

    /** The Constant LST_NAME_ID. */
    private static final List<String> LST_NAME_ID_HEADER = Arrays.asList("KSU006_210", "KSU006_211", "KSU006_207",
            "KSU006_208", "KSU006_209", "KSU006_212");

    /*
     * (non-Javadoc)
     * 
     * @see
     * nts.arc.layer.app.file.export.ExportService#handle(nts.arc.layer.app.file
     * .export.ExportServiceContext)
     */
    @Override
    protected void handle(ExportServiceContext<String> context) {
        // get execute id
        String executeId = context.getQuery();
        
        // find list data error
        List<ExternalBudgetError> lstError = this.errorRepo.findByExecutionId(executeId);
        // check has data
        if (CollectionUtil.isEmpty(lstError)) {
            throw new BusinessException("Msg_160");
        }
        
        // convert to dto
        List<ExternalBudgetErrorDto> lstErrorDto = lstError.stream()
                .map(domain -> {
                    ExternalBudgetErrorDto dto = new ExternalBudgetErrorDto();
                    domain.saveToMemento(dto);
                    return dto;
                }).collect(Collectors.toList());
        
        // set data export
        ExportData exportData = ExportData.builder()
                .employeeId(AppContexts.user().employeeId())
                .lstHeader(this.finHeader())
                .lstError(lstErrorDto)
                .build();
        // generate file
        this.generator.generate(context.getGeneratorContext(), exportData);
    }

    /**
     * Fin header.
     *
     * @return the list
     */
    private List<String> finHeader() {
        List<String> lstHeader = new ArrayList<>();
        for (String nameId : LST_NAME_ID_HEADER) {
            lstHeader.add(TextResource.localize(nameId));
        }
        return lstHeader;
    }
}
