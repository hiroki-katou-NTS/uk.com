/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.app.find.outsideot.export;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.i18n.custom.IInternationalization;
import nts.arc.layer.app.file.export.ExportService;
import nts.arc.layer.app.file.export.ExportServiceContext;
import nts.uk.ctx.at.shared.dom.outsideot.OutsideOTSettingRepository;

/**
 * The Class OutsideOTSettingExportService.
 */
@Stateless
public class OutsideOTSettingExportService extends ExportService<String> {

    /** The generator. */
    @Inject
    private OutsideOTSettingExportGenerator generator;

    /** The repository. */
    @Inject
    private OutsideOTSettingRepository repository;

    /** The internationalization. */
    @Inject
    private IInternationalization internationalization;

    /** The Constant LST_NAME_ID_HEADER. */
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
        
        OutsideOTSettingData data = new OutsideOTSettingData();
       
        // generate file
        this.generator.generate(context.getGeneratorContext(), data);
    }

    /**
     * Fin header.
     *
     * @return the list
     */
    private List<String> finHeader() {
        List<String> lstHeader = new ArrayList<>();
        for (String nameId : LST_NAME_ID_HEADER) {
            Optional<String> optional = this.internationalization.getItemName(nameId);
            if (!optional.isPresent()) {
                throw new RuntimeException("NameId of header not found.");
            }
            lstHeader.add(optional.get());
        }
        return lstHeader;
    }
}

