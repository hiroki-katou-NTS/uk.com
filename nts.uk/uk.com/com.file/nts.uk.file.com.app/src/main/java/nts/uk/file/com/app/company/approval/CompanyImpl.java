package nts.uk.file.com.app.company.approval;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.gul.collection.CollectionUtil;
import nts.uk.ctx.bs.company.dom.company.AddInfor;
import nts.uk.ctx.bs.company.dom.company.Company;
import nts.uk.ctx.bs.company.dom.company.CompanyRepository;
import nts.uk.ctx.bs.company.dom.company.MonthStr;
import nts.uk.ctx.bs.employee.dom.operationrule.DepartmentWorkplaceSynchronizationAtr;
import nts.uk.ctx.bs.employee.dom.operationrule.OperationRule;
import nts.uk.ctx.bs.employee.dom.operationrule.OperationRuleRepository;
import nts.uk.shr.com.i18n.TextResource;
import nts.uk.shr.infra.file.report.masterlist.annotation.DomainID;
import nts.uk.shr.infra.file.report.masterlist.data.ColumnTextAlign;
import nts.uk.shr.infra.file.report.masterlist.data.MasterCellStyle;
import nts.uk.shr.infra.file.report.masterlist.data.MasterData;
import nts.uk.shr.infra.file.report.masterlist.data.MasterHeaderColumn;
import nts.uk.shr.infra.file.report.masterlist.data.MasterListData;
import nts.uk.shr.infra.file.report.masterlist.webservice.MasterListExportQuery;
import nts.uk.shr.infra.file.report.masterlist.webservice.MasterListMode;
/**
 * 
 * @author minhvv
 *
 */

@Stateless
@DomainID(value = "Company")
public class CompanyImpl implements MasterListData{
	
	@Inject
	private CompanyExportRepository companyExportRepository;
	
	@Inject
	private CompanyRepository companyRepository;

	@Inject
	private OperationRuleRepository operationRuleRep;
	
	@Inject
	private SysUsageExportRepository sysRep;
	
	@Override
	public List<MasterData> getMasterDatas(MasterListExportQuery query) {
		List<MasterData> datas = new ArrayList<>();
		
		List<CompanyData> listCompany = companyExportRepository.findAll();
		
		listCompany = listCompany.stream().sorted(
				Comparator.comparing(CompanyData::getCompanyCode, Comparator.nullsLast(String::compareTo)))
				.collect(Collectors.toList());
		
		if(CollectionUtil.isEmpty(listCompany)){
			return null;
		}else{
			listCompany.stream().forEach(c->{
				Map<String, Object> data = new HashMap<>();
				
				putEmptyData(data);
				
				Optional<Company> listFind = companyRepository.find(c.getCompanyId());
				
				Optional<OperationRule> findOperationRule = operationRuleRep.findOperationRule(c.getCompanyId());
				
				Optional<SysUsageSetData> findUsageSets = sysRep.findUsageSet(c.getCompanyId());
				

				data.put("会社コード", c.getCompanyCode());
				data.put("会社名", c.getCompanyName());
				if(c.getIsAbolition() == 1){
					data.put("この会社を廃止する", "○");
				}else{
					data.put("この会社を廃止する", "-");
				}
				
				Company company = listFind.get();
				if(company != null ){
					data.put("会社名（カナ）", company.getComNameKana());
					
					data.put("会社名（略名）", company.getShortComName());
					
					data.put("法人番号", company.getTaxNo());
					
					data.put("会社代表者名", company.getRepname());
					
					data.put("会社代表者職位", company.getRepjob());
					
					company.getAddInfor().ifPresent(value ->{
						data.put("郵便番号", ""+value.getPostCd());
						
						data.put("住所 市区町村・番地", ""+value.getAdd_1());
						
						data.put("住所 建物名など", value.getAdd_2());
						
						data.put("住所カナ 市区町村・番地", value.getAddKana_1());
						
						data.put("住所カナ 建物名など", value.getAddKana_2());
						
						data.put("電話番号", ""+value.getPhoneNum());
						
						data.put("FAX", ""+value.getFaxNum());
					});
				}
				
				if(findUsageSets.get().getJinji()== 1){
					data.put("人事システム", "利用する");
				}else{ 
					data.put("人事システム", "利用しない");
				}
				
				if(findUsageSets.get().getShugyo()  == 1){
					data.put("就業システム", "利用する");
				}else{ 
					data.put("就業システム", "利用しない");
				}
				
				if(findUsageSets.get().getKyuyo() == 1){
					data.put("給与システム", "利用する");
				}else{ 
					data.put("給与システム", "利用しない");
				}
				
				if(findOperationRule.get().getDepWkpSynchAtr() == DepartmentWorkplaceSynchronizationAtr.NOT_SYNCHRONIZED){
					data.put("職場と部門", "区別しない");
				}else{
					data.put("職場と部門", "区別する ");
				}
				//Month
				if(listFind.get().getStartMonth() == MonthStr.ONE){
					data.put("期首月", MonthStr.ONE.month);
				}else if(listFind.get().getStartMonth() == MonthStr.TWO){
					data.put("期首月", MonthStr.TWO.month);
				}else if(listFind.get().getStartMonth() == MonthStr.THREE){
					data.put("期首月", MonthStr.THREE.month);
				}else if(listFind.get().getStartMonth() == MonthStr.FOUR){
					data.put("期首月", MonthStr.FOUR.month);
				}else if(listFind.get().getStartMonth() == MonthStr.FIVE){
					data.put("期首月", MonthStr.FIVE.month);
				}else if(listFind.get().getStartMonth() == MonthStr.SIX){
					data.put("期首月", MonthStr.SIX.month);
				}else if(listFind.get().getStartMonth() == MonthStr.SEVEN){
					data.put("期首月", MonthStr.SEVEN.month);
				}else if(listFind.get().getStartMonth() == MonthStr.EIGHT){
					data.put("期首月", MonthStr.EIGHT.month);
				}else if(listFind.get().getStartMonth() == MonthStr.NINE){
					data.put("期首月", MonthStr.NINE.month);
				}else if(listFind.get().getStartMonth() == MonthStr.TEN){
					data.put("期首月", MonthStr.TEN.month);
				}else if(listFind.get().getStartMonth() == MonthStr.ELEVEN){
					data.put("期首月", MonthStr.ELEVEN.month);
				}else{
					data.put("期首月", MonthStr.TWELVE.month);
				}

				MasterData masterData = new MasterData(data, null, "");
				masterData.cellAt("会社コード").setStyle(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT));
				masterData.cellAt("この会社を廃止する").setStyle(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT));
				masterData.cellAt("会社名").setStyle(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT));
				masterData.cellAt("会社名（カナ）").setStyle(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT));
				masterData.cellAt("会社名（略名）").setStyle(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT));
				masterData.cellAt("法人番号").setStyle(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT));
				masterData.cellAt("会社代表者名").setStyle(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT));
				masterData.cellAt("会社代表者職位").setStyle(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT));
				masterData.cellAt("郵便番号").setStyle(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT));
				masterData.cellAt("住所 市区町村・番地").setStyle(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT));
				masterData.cellAt("住所 建物名など").setStyle(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT));
				masterData.cellAt("住所カナ 市区町村・番地").setStyle(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT));
				masterData.cellAt("住所カナ 建物名など").setStyle(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT));
				masterData.cellAt("電話番号").setStyle(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT));
				masterData.cellAt("FAX").setStyle(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT));
				masterData.cellAt("人事システム").setStyle(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT));
				masterData.cellAt("就業システム").setStyle(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT));
				masterData.cellAt("給与システム").setStyle(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT));
				masterData.cellAt("職場と部門").setStyle(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT));
				masterData.cellAt("期首月").setStyle(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.RIGHT));
				
				

				datas.add(masterData);
			});
		}
		return datas;
	}

	@Override
	public List<MasterHeaderColumn> getHeaderColumns(MasterListExportQuery query) {
		// TODO Auto-generated method stub
		List<MasterHeaderColumn> columns = new ArrayList<>();
		
		columns.add(new MasterHeaderColumn("会社コード", TextResource.localize("CMM001_7"),
				ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn("この会社を廃止する", TextResource.localize("CMM001_10"),
				ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn("会社名", TextResource.localize("CMM001_11"),
				ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn("会社名（カナ）", TextResource.localize("CMM001_12"),
				ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn("会社名（略名）", TextResource.localize("CMM001_14"),
				ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn("法人番号", TextResource.localize("CMM001_19"),
				ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn("会社代表者名", TextResource.localize("CMM001_20"),
				ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn("会社代表者職位", TextResource.localize("CMM001_21"),
				ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn("郵便番号", TextResource.localize("CMM001_22"),
				ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn("住所 市区町村・番地",TextResource.localize("CMM001_24")+" "+TextResource.localize("CMM001_25"),
				ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn("住所 建物名など", TextResource.localize("CMM001_24")+" "+TextResource.localize("CMM001_26"),
				ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn("住所カナ 市区町村・番地",TextResource.localize("CMM001_27")+" "+TextResource.localize("CMM001_25"),
				ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn("住所カナ 建物名など", TextResource.localize("CMM001_27")+" "+TextResource.localize("CMM001_26"),
				ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn("電話番号", TextResource.localize("CMM001_28"),
				ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn("FAX", TextResource.localize("CMM001_29"),
				ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn("人事システム", TextResource.localize("CMM001_30"),
				ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn("就業システム", TextResource.localize("CMM001_33"),
				ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn("給与システム", TextResource.localize("CMM001_34"),
				ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn("職場と部門", TextResource.localize("CMM001_35"),
				ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn("期首月", TextResource.localize("CMM001_38"),
				ColumnTextAlign.LEFT, "", true));
		
		return columns;
	}
	
	private void putEmptyData (Map<String, Object> data){
		data.put("会社コード", "");
		data.put("この会社を廃止する",""); 
		data.put("会社名","");
		data.put("会社名（カナ）", "");
		data.put("会社名（略名）", "");
		data.put("法人番号", "");
		data.put("会社代表者名",""); 
		data.put("会社代表者職位", "");
		data.put("郵便番号", "");
		data.put("住所 市区町村・番地","");
		data.put("住所 建物名など", "");
		data.put("住所カナ 市区町村・番地","");
		data.put("住所カナ 建物名など", "");
		data.put("電話番号", "");
		data.put("FAX", "");
		data.put("人事システム", "");
		data.put("就業システム", "");
		data.put("給与システム", "");
		data.put("職場と部門", "");
		data.put("期首月","");
	}

	@Override
	public String mainSheetName() {
		// TODO Auto-generated method stub
		return TextResource.localize("CMM001_76");
	}

	@Override
	public MasterListMode mainSheetMode(){
		return MasterListMode.NONE;
	}
	

}

