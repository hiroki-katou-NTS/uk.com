package nts.uk.file.com.app.sequence;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;
import nts.gul.collection.CollectionUtil;
import nts.uk.ctx.bs.employee.dom.jobtitle.JobTitleRepository;
import nts.uk.ctx.bs.employee.dom.jobtitle.info.JobTitleInfoRepository;
import nts.uk.ctx.bs.employee.dom.jobtitle.sequence.SequenceMaster;
import nts.uk.ctx.bs.employee.dom.jobtitle.sequence.SequenceMasterRepository;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.context.LoginUserContext;
import nts.uk.shr.com.i18n.TextResource;
import nts.uk.shr.infra.file.report.masterlist.annotation.DomainID;
import nts.uk.shr.infra.file.report.masterlist.data.ColumnTextAlign;
import nts.uk.shr.infra.file.report.masterlist.data.MasterCellStyle;
import nts.uk.shr.infra.file.report.masterlist.data.MasterData;
import nts.uk.shr.infra.file.report.masterlist.data.MasterHeaderColumn;
import nts.uk.shr.infra.file.report.masterlist.data.MasterListData;
import nts.uk.shr.infra.file.report.masterlist.data.SheetData;
import nts.uk.shr.infra.file.report.masterlist.webservice.MasterListExportQuery;
import nts.uk.shr.infra.file.report.masterlist.webservice.MasterListMode;

/**
 * 
 * @author minhvv
 *
 */

@Stateless
@DomainID(value = "Sequence")
public class SequenceMasterExportImpl implements MasterListData{

	@Inject
	private SequenceMasterRepository sequenceMasterRepository;
	
	@Inject
	private SequenceMasterExportRepository sequenceMasterExportRepository;

	// One
	@Override
	public List<MasterHeaderColumn> getHeaderColumns(MasterListExportQuery query) {
		List<MasterHeaderColumn> columns = new ArrayList<>();
		
		columns.add(new MasterHeaderColumn("コード", TextResource.localize("CMM013_10"), ColumnTextAlign.LEFT,
				"", true));
		columns.add(new MasterHeaderColumn("職歴開始日", TextResource.localize("CMM013_63"), ColumnTextAlign.LEFT,
				"", true));
		columns.add(new MasterHeaderColumn("職歴終了", TextResource.localize("CMM013_64"), ColumnTextAlign.LEFT,
				"", true));
		columns.add(new MasterHeaderColumn("名称", TextResource.localize("CMM013_12"), ColumnTextAlign.LEFT,
				"", true));
		columns.add(new MasterHeaderColumn("36協定対象外", TextResource.localize("CMM013_52"), ColumnTextAlign.LEFT,
				"", true));
		columns.add(new MasterHeaderColumn("序列コード", TextResource.localize("CMM013_14")+" "+TextResource.localize("CMM013_23"), ColumnTextAlign.LEFT,
				"", true));
		columns.add(new MasterHeaderColumn("序列名", TextResource.localize("CMM013_14")+" "+TextResource.localize("CMM013_24"), ColumnTextAlign.LEFT,
				"", true));
		
		return columns;
	}
	
	
	
	

	//Two
	private List<MasterData> getMasterDataTwo(MasterListExportQuery query) {
		LoginUserContext loginUserContext = AppContexts.user();

		String companyId = loginUserContext.companyId();
		
		List<MasterData> datas = new ArrayList<>();
		
		List<SequenceMaster> listSequenceMaster = sequenceMasterRepository.findByCompanyId(companyId);
		
		if(CollectionUtil.isEmpty(listSequenceMaster)){
			return null;
		}else{
			listSequenceMaster.stream().sorted(Comparator.comparing(SequenceMaster::getOrder)).forEach(c ->{
				Map<String, Object> data = new HashMap<>();
				putEmptyDataTwo(data);
				data.put("コード", c.getSequenceCode());
				data.put("名称", c.getSequenceName());
				data.put("順位", c.getOrder());
				
				MasterData masterData = new MasterData(data, null, "");
				masterData.cellAt("コード").setStyle(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT));
				masterData.cellAt("名称").setStyle(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT));
				masterData.cellAt("順位").setStyle(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.RIGHT));
				
				datas.add(masterData);
			});
		}
		
		return datas;
	}
	
	public List<MasterHeaderColumn> getHeaderColumnTwos(MasterListExportQuery query) {
		List<MasterHeaderColumn> columns = new ArrayList<>();
		
		columns.add(new MasterHeaderColumn("コード", "コード", ColumnTextAlign.LEFT,
				"", true));
		columns.add(new MasterHeaderColumn("名称", "名称", ColumnTextAlign.LEFT,
				"", true));
		columns.add(new MasterHeaderColumn("順位", "順位", ColumnTextAlign.RIGHT,
				"", true));
		return columns;
	}
	
	private void putEmptyDataOne (Map<String, Object> data){
		data.put("コード", "");
		data.put("職歴開始日", "");
		data.put("職歴終了", "");
		data.put("名称", "");
		data.put("36協定対象外", "");
		data.put("序列コード", "");
		data.put("序列名", "");
	}
	
	private void putEmptyDataTwo(Map<String, Object> data){
		data.put("コード", "");
		data.put("名称", "");
		data.put("順位", "");
	}
	
	@Override
	public List<SheetData> extraSheets(MasterListExportQuery query){
		List<SheetData> listSheetData = new ArrayList<>();
		SheetData sheetDataTwo = new SheetData(getMasterDataTwo(query), getHeaderColumnTwos(query), null, null, TextResource.localize("CMM013_61"), MasterListMode.NONE);
		listSheetData.add(sheetDataTwo);
		return listSheetData;
	}
	
	@Override
	public List<MasterData> getMasterDatas(MasterListExportQuery query) {
		
		LoginUserContext loginUserContext = AppContexts.user();
		String companyId = loginUserContext.companyId();
		
		List<MasterData> datas = new ArrayList<>();

		List<SequenceMasterExportData>  listFindAll = sequenceMasterExportRepository.findAll(companyId , query.getBaseDate());
		if(CollectionUtil.isEmpty(listFindAll)){
			return null;
		}else{
			listFindAll.stream().sorted(Comparator.comparing(SequenceMasterExportData::getJobCd)).forEach(c ->{
				Map<String, Object> data = new HashMap<>();
				putEmptyDataOne(data);
				
				data.put("コード", c.getJobCd());
				data.put("職歴開始日", c.getStartDate());
				data.put("職歴終了", c.getEndDate());
				data.put("名称", c.getJobName());
				if(c.getIsManager() ==0){
					data.put("36協定対象外","-");
				}else{
					data.put("36協定対象外","○");
				}
				
				if(c.getSequenceCode() == null ){
					data.put("序列コード", "");
					data.put("序列名", "");
				}else{
					data.put("序列コード", c.getSequenceCode());
					Optional<SequenceMaster> opfindBySequenceCode = sequenceMasterRepository.findBySequenceCode(companyId, c.getSequenceCode());
					
					if(opfindBySequenceCode.isPresent()){
						data.put("序列名", opfindBySequenceCode.get().getSequenceName());
					}else{
						data.put("序列名","");
					}
				}
				

				MasterData masterData = new MasterData(data, null, "");
				masterData.cellAt("コード").setStyle(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT));
				masterData.cellAt("職歴開始日").setStyle(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.RIGHT));
				masterData.cellAt("職歴終了").setStyle(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.RIGHT));
				masterData.cellAt("名称").setStyle(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT));
				masterData.cellAt("36協定対象外").setStyle(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT));
				masterData.cellAt("序列コード").setStyle(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT));
				masterData.cellAt("序列名").setStyle(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT));
				
				datas.add(masterData);
			});
			
		}

		return datas;
	}


	@Override
	public String mainSheetName() {
		// TODO Auto-generated method stub
		return TextResource.localize("CMM013_60");
	}
	
	@Override
	 public MasterListMode mainSheetMode() {
	  return MasterListMode.BASE_DATE;
	 }

	
}
