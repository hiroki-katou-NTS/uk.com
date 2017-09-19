/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.file.at.app.outsideot;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.layer.app.file.export.ExportService;
import nts.arc.layer.app.file.export.ExportServiceContext;
import nts.uk.ctx.at.record.dom.dailyattendanceitem.DailyAttendanceItem;
import nts.uk.ctx.at.record.dom.dailyattendanceitem.repository.DailyAttendanceItemRepository;
import nts.uk.ctx.at.shared.app.find.outsideot.OutsideOTSettingFinder;
import nts.uk.ctx.at.shared.app.find.outsideot.premium.extra.PremiumExtra60HRateFinder;
import nts.uk.ctx.at.shared.dom.outsideot.breakdown.BreakdownItemNo;
import nts.uk.ctx.at.shared.dom.outsideot.breakdown.language.OutsideOTBRDItemLangRepository;
import nts.uk.ctx.at.shared.dom.outsideot.overtime.OvertimeNo;
import nts.uk.ctx.at.shared.dom.outsideot.overtime.language.OvertimeNameLangRepository;
import nts.uk.file.at.app.outsideot.data.OutsideOTBRDItemNameLangData;
import nts.uk.file.at.app.outsideot.data.OutsideOTSettingData;
import nts.uk.file.at.app.outsideot.data.OvertimeNameLanguageData;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.context.LoginUserContext;

/**
 * The Class OutsideOTSettingExportService.
 */
@Stateless
public class OutsideOTSettingExportService extends ExportService<OutsideOTSettingQuery> {

    /** The generator. */
    @Inject
    private OutsideOTSettingExportGenerator generator;

    /** The finder. */
    @Inject
    private OutsideOTSettingFinder finder;
    
    /** The overtime name lang repository. */
    @Inject
    private OvertimeNameLangRepository overtimeNameLangRepository;
    
    /** The outside OTBRD item lang repository. */
    @Inject
    private OutsideOTBRDItemLangRepository outsideOTBRDItemLangRepository;
    

    /** The premium extra 60 H rate finder. */
    @Inject
    private PremiumExtra60HRateFinder premiumExtra60HRateFinder;
    
    /** The daily attendance item repository. */
    @Inject
    private DailyAttendanceItemRepository dailyAttendanceItemRepository;
    

    /** The Constant LANGUAGE_ID_JAPAN. */
    public static final String LANGUAGE_ID_JAPAN = "ja"; 
    
    /*
     * (non-Javadoc)
     * 
     * @see
     * nts.arc.layer.app.file.export.ExportService#handle(nts.arc.layer.app.file
     * .export.ExportServiceContext)
     */
    @Override
    protected void handle(ExportServiceContext<OutsideOTSettingQuery> context) {
    	
    	// get login user
    	LoginUserContext loginUserContext = AppContexts.user();
    	
    	// get company id
    	String companyId = loginUserContext.companyId();
    	
    	// get query
    	OutsideOTSettingQuery query = context.getQuery();
    	
    	OutsideOTSettingData data = new OutsideOTSettingData();
       
        data.setSetting(this.finder.findById());
        
		List<OvertimeNameLanguageData> overtimeNameLanguageData = new ArrayList<>();
		
		// for each all data overtime language
		for (int index = OvertimeNo.ONE.value; index <= OvertimeNo.FIVE.value; index++) {
			OvertimeNameLanguageData language = new OvertimeNameLanguageData("", index);
			overtimeNameLanguageData.add(language);
		}
		
		List<OutsideOTBRDItemNameLangData> breakdownNameLanguageData = new ArrayList<>();
		
		// for each all data overtime language
		for (int index = BreakdownItemNo.ONE.value; index <= BreakdownItemNo.TEN.value; index++) {
			OutsideOTBRDItemNameLangData language = new OutsideOTBRDItemNameLangData("", index);
			breakdownNameLanguageData.add(language);
		}

		if (!query.getLanguageId().equals(LANGUAGE_ID_JAPAN)) {
			this.overtimeNameLangRepository.findAll(companyId, query.getLanguageId())
					.forEach(languageRepository -> {
						overtimeNameLanguageData.forEach(language -> {
							if (languageRepository.getOvertimeNo().value == language
									.getOvertimeNo()) {
								language.setLanguage(languageRepository.getName().v());
							}
						});
					});
			this.outsideOTBRDItemLangRepository.findAll(companyId, query.getLanguageId())
			.forEach(languageRepository -> {
				breakdownNameLanguageData.forEach(language -> {
					if (languageRepository.getBreakdownItemNo().value == language
							.getBreakdownItemNo()) {
						language.setLanguage(languageRepository.getName().v());
					}
				});
			});
		}
        data.setOvertimeLanguageData(overtimeNameLanguageData);
        data.setBreakdownLanguageData(breakdownNameLanguageData);
        data.setPremiumExtraRates(this.premiumExtra60HRateFinder.findAll());
        
		Map<Integer, DailyAttendanceItem> mapAttendanceItem = this.dailyAttendanceItemRepository.getList(companyId).stream()
				.collect(Collectors.toMap((attendanceItem -> {
					return attendanceItem.getAttendanceItemId();
				}), Function.identity()));
		
		data.setMapAttendanceItem(mapAttendanceItem);
        // generate file
        this.generator.generate(context.getGeneratorContext(), data);
    }

}

