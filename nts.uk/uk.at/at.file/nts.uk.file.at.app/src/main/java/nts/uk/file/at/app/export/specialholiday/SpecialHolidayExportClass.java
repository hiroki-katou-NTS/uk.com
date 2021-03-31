package nts.uk.file.at.app.export.specialholiday;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.shared.dom.common.CompanyId;
import nts.uk.ctx.at.shared.dom.specialholiday.SpecialHoliday;
import nts.uk.ctx.at.shared.dom.specialholiday.SpecialHolidayRepository;
import nts.uk.ctx.at.shared.dom.specialholiday.grantinformation.ElapseYear;
import nts.uk.ctx.at.shared.dom.specialholiday.grantinformation.ElapseYearRepository;
import nts.uk.ctx.at.shared.dom.specialholiday.grantinformation.GrantDateTbl;
import nts.uk.ctx.at.shared.dom.specialholiday.grantinformation.GrantDateTblRepository;
import nts.uk.ctx.bs.employee.dom.classification.Classification;
import nts.uk.ctx.bs.employee.dom.classification.ClassificationRepository;
import nts.uk.ctx.bs.employee.dom.employment.Employment;
import nts.uk.ctx.bs.employee.dom.employment.EmploymentRepository;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.i18n.TextResource;
import nts.uk.shr.infra.file.report.masterlist.data.ColumnTextAlign;
import nts.uk.shr.infra.file.report.masterlist.data.MasterCellData;
import nts.uk.shr.infra.file.report.masterlist.data.MasterCellStyle;
import nts.uk.shr.infra.file.report.masterlist.data.MasterData;
import nts.uk.shr.infra.file.report.masterlist.data.MasterHeaderColumn;
import nts.uk.shr.infra.file.report.masterlist.data.SheetData;
import nts.uk.shr.infra.file.report.masterlist.webservice.MasterListExportQuery;
import nts.uk.shr.infra.file.report.masterlist.webservice.MasterListMode;

@Stateless
public class SpecialHolidayExportClass {
	
    @Inject
    private SpecialHolidayExRepository specialHolidayExRepository;
    
    @Inject
    private SpecialHolidayRepository specialHolidayRepository;
    
    @Inject
    private GrantDateTblRepository grantDateTblRepository;
    
    @Inject
    private EmploymentRepository employmentRepository;
    
    @Inject
    private ClassificationRepository classificationRepository;
    
    @Inject
    private ElapseYearRepository elapseYearRepository;
	
    public List<MasterHeaderColumn> getHeaderColumns(MasterListExportQuery arg0)
    {
        List <MasterHeaderColumn> columns = new ArrayList<>();
        columns.add(new MasterHeaderColumn(SpecialHolidayUtils.KMF004_106, TextResource.localize("KMF004_106"),
        ColumnTextAlign.LEFT, "", true));
        columns.add(new MasterHeaderColumn(SpecialHolidayUtils.KMF004_107, TextResource.localize("KMF004_107"),
        ColumnTextAlign.LEFT, "", true));
        columns.add(new MasterHeaderColumn(SpecialHolidayUtils.KMF004_108, TextResource.localize("KMF004_108"),
        ColumnTextAlign.LEFT, "", true));
        columns.add(new MasterHeaderColumn(SpecialHolidayUtils.KMF004_187, TextResource.localize("KMF004_187"),
        ColumnTextAlign.LEFT, "", true));
        columns.add(new MasterHeaderColumn(SpecialHolidayUtils.KMF004_109, TextResource.localize("KMF004_109"),
        ColumnTextAlign.LEFT, "", true));
        columns.add(new MasterHeaderColumn(SpecialHolidayUtils.KMF004_111, TextResource.localize("KMF004_111"),
        ColumnTextAlign.LEFT, "", true));
        columns.add(new MasterHeaderColumn(SpecialHolidayUtils.KMF004_110, TextResource.localize("KMF004_110"),
        ColumnTextAlign.LEFT, "", true));
        columns.add(new MasterHeaderColumn(SpecialHolidayUtils.KMF004_188, TextResource.localize("KMF004_188"),
        ColumnTextAlign.LEFT, "", true));
        columns.add(new MasterHeaderColumn(SpecialHolidayUtils.KMF004_113, TextResource.localize("KMF004_113"),
        ColumnTextAlign.LEFT, "", true));
        columns.add(new MasterHeaderColumn(SpecialHolidayUtils.KMF004_127, TextResource.localize("KMF004_127"),
        ColumnTextAlign.LEFT, "", true));
        columns.add(new MasterHeaderColumn(SpecialHolidayUtils.KMF004_128, TextResource.localize("KMF004_128"),
        ColumnTextAlign.LEFT, "", true));
        columns.add(new MasterHeaderColumn(SpecialHolidayUtils.KMF004_129, TextResource.localize("KMF004_129"),
        ColumnTextAlign.LEFT, "", true));
        columns.add(new MasterHeaderColumn(SpecialHolidayUtils.KMF004_130, TextResource.localize("KMF004_130"),
        ColumnTextAlign.LEFT, "", true));
        columns.add(new MasterHeaderColumn(SpecialHolidayUtils.KMF004_191, TextResource.localize("KMF004_191"),
        ColumnTextAlign.LEFT, "", true));
        columns.add(new MasterHeaderColumn(SpecialHolidayUtils.KMF004_114, TextResource.localize("KMF004_114"),
        ColumnTextAlign.LEFT, "", true));
        columns.add(new MasterHeaderColumn(SpecialHolidayUtils.KMF004_115, TextResource.localize("KMF004_115"),
        ColumnTextAlign.LEFT, "", true));
        columns.add(new MasterHeaderColumn(SpecialHolidayUtils.KMF004_116, TextResource.localize("KMF004_116"),
        ColumnTextAlign.LEFT, "", true));
        columns.add(new MasterHeaderColumn(SpecialHolidayUtils.KMF004_117, TextResource.localize("KMF004_117"),
        ColumnTextAlign.LEFT, "", true));
        columns.add(new MasterHeaderColumn(SpecialHolidayUtils.KMF004_118, TextResource.localize("KMF004_118"),
        ColumnTextAlign.LEFT, "", true));
        columns.add(new MasterHeaderColumn(SpecialHolidayUtils.KMF004_119, TextResource.localize("KMF004_119"),
        ColumnTextAlign.LEFT, "", true));
        columns.add(new MasterHeaderColumn(SpecialHolidayUtils.KMF004_120, TextResource.localize("KMF004_120"),
        ColumnTextAlign.LEFT, "", true));
        columns.add(new MasterHeaderColumn(SpecialHolidayUtils.KMF004_121, TextResource.localize("KMF004_121"),
        ColumnTextAlign.LEFT, "", true));
        columns.add(new MasterHeaderColumn(SpecialHolidayUtils.KMF004_189, TextResource.localize("KMF004_189"),
        ColumnTextAlign.LEFT, "", true));
        columns.add(new MasterHeaderColumn(SpecialHolidayUtils.KMF004_190, TextResource.localize("KMF004_190"),
        ColumnTextAlign.LEFT, "", true));
        columns.add(new MasterHeaderColumn(SpecialHolidayUtils.KMF004_122, TextResource.localize("KMF004_122"),
        ColumnTextAlign.LEFT, "", true));
        columns.add(new MasterHeaderColumn(SpecialHolidayUtils.KMF004_123, TextResource.localize("KMF004_123"),
        ColumnTextAlign.LEFT, "", true));
        columns.add(new MasterHeaderColumn(SpecialHolidayUtils.KMF004_125, TextResource.localize("KMF004_125"),
        ColumnTextAlign.LEFT, "", true));
        columns.add(new MasterHeaderColumn(SpecialHolidayUtils.KMF004_126, TextResource.localize("KMF004_126"),
        ColumnTextAlign.LEFT, "", true));
        columns.add(new MasterHeaderColumn(SpecialHolidayUtils.KMF004_124, TextResource.localize("KMF004_124"),
        ColumnTextAlign.LEFT, "", true));
        columns.add(new MasterHeaderColumn(SpecialHolidayUtils.KMF004_131, TextResource.localize("KMF004_131"),
        ColumnTextAlign.LEFT, "", true));
        columns.add(new MasterHeaderColumn(SpecialHolidayUtils.KMF004_132, TextResource.localize("KMF004_132"),
        ColumnTextAlign.LEFT, "", true));
        columns.add(new MasterHeaderColumn(SpecialHolidayUtils.KMF004_133, TextResource.localize("KMF004_133"),
        ColumnTextAlign.LEFT, "", true));
        columns.add(new MasterHeaderColumn(SpecialHolidayUtils.KMF004_134, TextResource.localize("KMF004_134"),
        ColumnTextAlign.LEFT, "", true));
        columns.add(new MasterHeaderColumn(SpecialHolidayUtils.KMF004_135, TextResource.localize("KMF004_135"),
        ColumnTextAlign.LEFT, "", true));
        columns.add(new MasterHeaderColumn(SpecialHolidayUtils.KMF004_136, TextResource.localize("KMF004_136"),
        ColumnTextAlign.LEFT, "", true));
        columns.add(new MasterHeaderColumn(SpecialHolidayUtils.KMF004_137, TextResource.localize("KMF004_137"),
        ColumnTextAlign.LEFT, "", true));
        columns.add(new MasterHeaderColumn(SpecialHolidayUtils.KMF004_138, TextResource.localize("KMF004_138"),
        ColumnTextAlign.LEFT, "", true));
        columns.add(new MasterHeaderColumn(SpecialHolidayUtils.KMF004_139, TextResource.localize("KMF004_139"),
        ColumnTextAlign.LEFT, "", true));
        columns.add(new MasterHeaderColumn(SpecialHolidayUtils.KMF004_140, TextResource.localize("KMF004_140"),
        ColumnTextAlign.LEFT, "", true));
        columns.add(new MasterHeaderColumn(SpecialHolidayUtils.KMF004_141, TextResource.localize("KMF004_141"),
        ColumnTextAlign.LEFT, "", true));
        columns.add(new MasterHeaderColumn(SpecialHolidayUtils.KMF004_142, TextResource.localize("KMF004_142"),
        ColumnTextAlign.LEFT, "", true));
        return columns;
    }

    public List<MasterData> getMasterDatas(MasterListExportQuery query)
    {
        String companyId = AppContexts.user().companyId();
        
        List<SpecialHoliday> specialHolidayList = specialHolidayRepository.findByCompanyIdWithTargetItem(companyId);
        List<SpecialHolidayExportDataSource> dataSource = new ArrayList<SpecialHolidayExportDataSource>();

        specialHolidayList.stream().forEach(specialHoliday -> {
        	List<GrantDateTbl> grantDateTblList = 
        			grantDateTblRepository.findBySphdCd(companyId, specialHoliday.getSpecialHolidayCode().v());
        	
        	Optional<ElapseYear> elapseYearOp = 
        			elapseYearRepository.findByCode(new CompanyId(companyId), specialHoliday.getSpecialHolidayCode());
        	
        	ElapseYear elapseYear = null;
        	
        	if(elapseYearOp.isPresent()) {
        		elapseYear = elapseYearOp.get();
        		int numOfElapsedYears = elapseYear.getElapseYearMonthTblList().size();
        		
        		grantDateTblList.stream().forEach(grantDateTbl -> grantDateTbl.addLessTableThanElapsedYearsTable(numOfElapsedYears));
        	}
        	
        	List<Employment> empList = new ArrayList<Employment>();
        	if(specialHoliday.getSpecialLeaveRestriction().getListEmp().size() > 0) {
        		empList = employmentRepository
            			.findByEmpCodes(companyId, specialHoliday.getSpecialLeaveRestriction().getListEmp());
        	}
        	
        	List<Classification> clsList = new ArrayList<Classification>();
			if(specialHoliday.getSpecialLeaveRestriction().getListCls().size() > 0) {
				clsList = classificationRepository
            			.getClassificationByCodes(companyId, specialHoliday.getSpecialLeaveRestriction().getListCls());
        	}
        	
        	List<SpecialHolidayExportDataSource> dataList = SpecialHolidayExportDataSource.convertToDatasource(specialHoliday, 
        			grantDateTblList, 
        			elapseYear,
        			empList, 
        			clsList);
        	dataSource.addAll(dataList);
        });
        
        
        List<MasterData> masterDataList = new ArrayList<MasterData>();
        
        dataSource.stream().forEach(el -> {
        	Map<String, MasterCellData> data = new HashMap<>();
        	
        	data.put(SpecialHolidayUtils.KMF004_106, MasterCellData.builder()
					.columnId(SpecialHolidayUtils.KMF004_106)
					.value(el.getCode())
					.style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT))
					.build());
        	data.put(SpecialHolidayUtils.KMF004_107, MasterCellData.builder()
					.columnId(SpecialHolidayUtils.KMF004_107)
					.value(el.getName())
					.style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT))
					.build());
        	data.put(SpecialHolidayUtils.KMF004_108, MasterCellData.builder()
					.columnId(SpecialHolidayUtils.KMF004_108)
					.value(el.getTargetItem())
					.style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT))
					.build());
        	data.put(SpecialHolidayUtils.KMF004_187, MasterCellData.builder()
					.columnId(SpecialHolidayUtils.KMF004_187)
					.value(el.getGrantAuto())
					.style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT))
					.build());
        	data.put(SpecialHolidayUtils.KMF004_109, MasterCellData.builder()
					.columnId(SpecialHolidayUtils.KMF004_109)
					.value(el.getMemo())
					.style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT))
					.build());
        	data.put(SpecialHolidayUtils.KMF004_111, MasterCellData.builder()
					.columnId(SpecialHolidayUtils.KMF004_111)
					.value(el.getGrantMethod())
					.style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT))
					.build());
        	data.put(SpecialHolidayUtils.KMF004_110, MasterCellData.builder()
					.columnId(SpecialHolidayUtils.KMF004_110)
					.value(el.getGrantDate())
					.style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT))
					.build());
        	data.put(SpecialHolidayUtils.KMF004_188, MasterCellData.builder()
					.columnId(SpecialHolidayUtils.KMF004_188)
					.value(el.getDesignatedDate())
					.style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.RIGHT))
					.build());
        	data.put(SpecialHolidayUtils.KMF004_113, MasterCellData.builder()
					.columnId(SpecialHolidayUtils.KMF004_113)
					.value(el.getGrantDays())
					.style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.RIGHT))
					.build());
        	data.put(SpecialHolidayUtils.KMF004_127, MasterCellData.builder()
					.columnId(SpecialHolidayUtils.KMF004_127)
					.value(el.getExpirationStartMonth())
					.style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.RIGHT))
					.build());
        	data.put(SpecialHolidayUtils.KMF004_128, MasterCellData.builder()
					.columnId(SpecialHolidayUtils.KMF004_128)
					.value(el.getExpirationStartDay())
					.style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.RIGHT))
					.build());
        	data.put(SpecialHolidayUtils.KMF004_129, MasterCellData.builder()
					.columnId(SpecialHolidayUtils.KMF004_129)
					.value(el.getExpirationEndMonth())
					.style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.RIGHT))
					.build());
        	data.put(SpecialHolidayUtils.KMF004_130, MasterCellData.builder()
					.columnId(SpecialHolidayUtils.KMF004_130)
					.value(el.getExpirationEndDay())
					.style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.RIGHT))
					.build());
        	data.put(SpecialHolidayUtils.KMF004_191, MasterCellData.builder()
					.columnId(SpecialHolidayUtils.KMF004_191)
					.value(el.getContinuousAtr())
					.style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT))
					.build());
        	data.put(SpecialHolidayUtils.KMF004_114, MasterCellData.builder()
					.columnId(SpecialHolidayUtils.KMF004_114)
					.value(el.getGrantCode())
					.style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT))
					.build());
        	data.put(SpecialHolidayUtils.KMF004_115, MasterCellData.builder()
					.columnId(SpecialHolidayUtils.KMF004_115)
					.value(el.getGrantName())
					.style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT))
					.build());
        	data.put(SpecialHolidayUtils.KMF004_116, MasterCellData.builder()
					.columnId(SpecialHolidayUtils.KMF004_116)
					.value(el.getDefaultTable())
					.style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT))
					.build());
        	data.put(SpecialHolidayUtils.KMF004_117, MasterCellData.builder()
					.columnId(SpecialHolidayUtils.KMF004_117)
					.value(el.getGrantSetting())
					.style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.RIGHT))
					.build());
        	data.put(SpecialHolidayUtils.KMF004_118, MasterCellData.builder()
					.columnId(SpecialHolidayUtils.KMF004_118)
					.value(el.getGrantYears())
					.style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.RIGHT))
					.build());
        	data.put(SpecialHolidayUtils.KMF004_119, MasterCellData.builder()
					.columnId(SpecialHolidayUtils.KMF004_119)
					.value(el.getGrantMonths())
					.style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.RIGHT))
					.build());
        	data.put(SpecialHolidayUtils.KMF004_120, MasterCellData.builder()
					.columnId(SpecialHolidayUtils.KMF004_120)
					.value(el.getMaxDays())
					.style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.RIGHT))
					.build());
        	data.put(SpecialHolidayUtils.KMF004_121, MasterCellData.builder()
					.columnId(SpecialHolidayUtils.KMF004_121)
					.value(el.getGrantFixedDaysEachYear())
					.style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT))
					.build());
        	data.put(SpecialHolidayUtils.KMF004_189, MasterCellData.builder()
					.columnId(SpecialHolidayUtils.KMF004_189)
					.value(el.getGrantCycleYear())
					.style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.RIGHT))
					.build());
        	data.put(SpecialHolidayUtils.KMF004_190, MasterCellData.builder()
					.columnId(SpecialHolidayUtils.KMF004_190)
					.value(el.getGrantCycleMonth())
					.style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.RIGHT))
					.build());
        	data.put(SpecialHolidayUtils.KMF004_122, MasterCellData.builder()
					.columnId(SpecialHolidayUtils.KMF004_122)
					.value(el.getFixedGrantDays())
					.style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.RIGHT))
					.build());
        	data.put(SpecialHolidayUtils.KMF004_123, MasterCellData.builder()
					.columnId(SpecialHolidayUtils.KMF004_123)
					.value(el.getExpirationDate())
					.style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT))
					.build());
        	data.put(SpecialHolidayUtils.KMF004_125, MasterCellData.builder()
					.columnId(SpecialHolidayUtils.KMF004_125)
					.value(el.getExpirationYear())
					.style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.RIGHT))
					.build());
        	data.put(SpecialHolidayUtils.KMF004_126, MasterCellData.builder()
					.columnId(SpecialHolidayUtils.KMF004_126)
					.value(el.getExpirationMonth())
					.style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.RIGHT))
					.build());
        	data.put(SpecialHolidayUtils.KMF004_124, MasterCellData.builder()
					.columnId(SpecialHolidayUtils.KMF004_124)
					.value(el.getMaxAccumulationDays())
					.style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.RIGHT))
					.build());
        	data.put(SpecialHolidayUtils.KMF004_131, MasterCellData.builder()
					.columnId(SpecialHolidayUtils.KMF004_131)
					.value(el.getGenderRestUseAtr())
					.style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT))
					.build());
        	data.put(SpecialHolidayUtils.KMF004_132, MasterCellData.builder()
					.columnId(SpecialHolidayUtils.KMF004_132)
					.value(el.getGender())
					.style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT))
					.build());
        	data.put(SpecialHolidayUtils.KMF004_133, MasterCellData.builder()
					.columnId(SpecialHolidayUtils.KMF004_133)
					.value(el.getEmployment())
					.style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT))
					.build());
        	data.put(SpecialHolidayUtils.KMF004_134, MasterCellData.builder()
					.columnId(SpecialHolidayUtils.KMF004_134)
					.value(el.getTargetEmployment())
					.style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT))
					.build());
        	data.put(SpecialHolidayUtils.KMF004_135, MasterCellData.builder()
					.columnId(SpecialHolidayUtils.KMF004_135)
					.value(el.getType())
					.style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT))
					.build());
        	data.put(SpecialHolidayUtils.KMF004_136, MasterCellData.builder()
					.columnId(SpecialHolidayUtils.KMF004_136)
					.value(el.getTargetType())
					.style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT))
					.build());
        	data.put(SpecialHolidayUtils.KMF004_137, MasterCellData.builder()
					.columnId(SpecialHolidayUtils.KMF004_137)
					.value(el.getAge())
					.style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT))
					.build());
        	data.put(SpecialHolidayUtils.KMF004_138, MasterCellData.builder()
					.columnId(SpecialHolidayUtils.KMF004_138)
					.value(el.getMinAge())
					.style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.RIGHT))
					.build());
        	data.put(SpecialHolidayUtils.KMF004_139, MasterCellData.builder()
					.columnId(SpecialHolidayUtils.KMF004_139)
					.value(el.getMaxAge())
					.style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.RIGHT))
					.build());
        	data.put(SpecialHolidayUtils.KMF004_140, MasterCellData.builder()
					.columnId(SpecialHolidayUtils.KMF004_140)
					.value(el.getAgeStandard())
					.style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT))
					.build());
        	data.put(SpecialHolidayUtils.KMF004_141, MasterCellData.builder()
					.columnId(SpecialHolidayUtils.KMF004_141)
					.value(el.getAgeBaseMonth())
					.style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.RIGHT))
					.build());
        	data.put(SpecialHolidayUtils.KMF004_142, MasterCellData.builder()
					.columnId(SpecialHolidayUtils.KMF004_142)
					.value(el.getAgeBaseDate())
					.style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.RIGHT))
					.build());
        	
        	MasterData masterData = MasterData.builder().rowData(data).build();
        	masterDataList.add(masterData);
        });
        
        return masterDataList;
    }
    
    public String mainSheetName(){
		return TextResource.localize("KMF004_153");
	} 
    
    public List<MasterHeaderColumn> getHeaderColumnSPHDEvent(){
    	List<MasterHeaderColumn> columns = new ArrayList<>();
		columns.add(new MasterHeaderColumn(SpecialHolidayUtils.KMF004_143, TextResource.localize("KMF004_143"),
				ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn(SpecialHolidayUtils.KMF004_144, TextResource.localize("KMF004_144"),
				ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn(SpecialHolidayUtils.KMF004_161, TextResource.localize("KMF004_161"),
				ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn(SpecialHolidayUtils.KMF004_145, TextResource.localize("KMF004_145"),
				ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn(SpecialHolidayUtils.KMF004_146, TextResource.localize("KMF004_146"),
				ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn(SpecialHolidayUtils.KMF004_147, TextResource.localize("KMF004_147"),
				ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn(SpecialHolidayUtils.KMF004_148, TextResource.localize("KMF004_148"),
				ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn(SpecialHolidayUtils.KMF004_149, TextResource.localize("KMF004_149"),
				ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn(SpecialHolidayUtils.KMF004_150, TextResource.localize("KMF004_150"),
				ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn(SpecialHolidayUtils.KMF004_162, TextResource.localize("KMF004_162"),
				ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn(SpecialHolidayUtils.KMF004_151, TextResource.localize("KMF004_151"),
				ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn(SpecialHolidayUtils.KMF004_152, TextResource.localize("KMF004_152"),
				ColumnTextAlign.LEFT, "", true));
    	return columns;
    }

	public MasterListMode mainSheetMode(){
		return MasterListMode.NONE;
	}
    
    public List<MasterData> getSPHDEventMasterData(){
    	String cid = AppContexts.user().companyId();
    	return specialHolidayExRepository.getSPHDEventExportData(cid);
    }
    
    public List<SheetData> extraSheets(MasterListExportQuery query){
    	
    	List<SheetData> sheets = new ArrayList<>();
    	
    	SheetData sheetData = SheetData.builder()
						    			.mainData(getSPHDEventMasterData())
						    			.mainDataColumns(getHeaderColumnSPHDEvent())
						    			.sheetName(TextResource.localize("KMF004_154"))
						    			.mode(MasterListMode.NONE)
						    			.build();
    	sheets.add(sheetData);
		return sheets;
	}
}
