package nts.uk.file.com.app.Sequence;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.error.BusinessException;
import nts.arc.time.GeneralDate;
import nts.gul.collection.CollectionUtil;
import nts.uk.ctx.bs.employee.dom.jobtitle.JobTitle;
import nts.uk.ctx.bs.employee.dom.jobtitle.JobTitleRepository;
import nts.uk.ctx.bs.employee.dom.jobtitle.history.JobTitleHistory;
import nts.uk.ctx.bs.employee.dom.jobtitle.info.JobTitleInfo;
import nts.uk.ctx.bs.employee.dom.jobtitle.info.JobTitleInfoRepository;
import nts.uk.ctx.bs.employee.dom.jobtitle.info.SequenceMasterExportRepository;
import nts.uk.ctx.bs.employee.dom.jobtitle.sequence.SequenceMaster;
import nts.uk.ctx.bs.employee.dom.jobtitle.sequence.SequenceMasterRepository;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.context.LoginUserContext;
import nts.uk.shr.infra.file.report.masterlist.annotation.DomainID;
import nts.uk.shr.infra.file.report.masterlist.data.ColumnTextAlign;
import nts.uk.shr.infra.file.report.masterlist.data.MasterData;
import nts.uk.shr.infra.file.report.masterlist.data.MasterHeaderColumn;
import nts.uk.shr.infra.file.report.masterlist.data.MasterListData;
import nts.uk.shr.infra.file.report.masterlist.data.SheetData;
import nts.uk.shr.infra.file.report.masterlist.webservice.MasterListExportQuery;

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
	private JobTitleRepository jobTitleRepository;
	
	@Inject
	private SequenceMasterExportRepository sequenceMasterExportRepository;
	
	@Inject
	private JobTitleInfoRepository jobTitleInfoRepository;
	
	
	// One
	@Override
	public List<MasterHeaderColumn> getHeaderColumns(MasterListExportQuery query) {
		List<MasterHeaderColumn> columns = new ArrayList<>();
		
		columns.add(new MasterHeaderColumn("コード", "コード", ColumnTextAlign.LEFT,
				"", true));
		columns.add(new MasterHeaderColumn("職歴開始日","職歴開始日", ColumnTextAlign.LEFT,
				"", true));
		columns.add(new MasterHeaderColumn("職歴終了", "職歴終了", ColumnTextAlign.LEFT,
				"", true));
		columns.add(new MasterHeaderColumn("名称", "名称", ColumnTextAlign.LEFT,
				"", true));
		columns.add(new MasterHeaderColumn("36協定対象外","36協定対象外", ColumnTextAlign.LEFT,
				"", true));
		columns.add(new MasterHeaderColumn("序列コード", "序列コード", ColumnTextAlign.LEFT,
				"", true));
		columns.add(new MasterHeaderColumn("序列名", "序列名", ColumnTextAlign.LEFT,
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
			throw new BusinessException("Msg_393");
		}else{
			listSequenceMaster.stream().forEach(c ->{
				Map<String, Object> data = new HashMap<>();
				putEmptyDataTwo(data);
				data.put("コード", c.getSequenceCode());
				data.put("名称", c.getSequenceName());
				data.put("順位", c.getOrder());
				datas.add(new MasterData(data, null, ""));
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
		SheetData sheetDataTwo = new SheetData(getMasterDataTwo(query), getHeaderColumnTwos(query), null, null, "シーケンス設定");
		listSheetData.add(sheetDataTwo);
		return listSheetData;
	}
	
	@Override
	public List<MasterData> getMasterDatas(MasterListExportQuery query) {
		
		LoginUserContext loginUserContext = AppContexts.user();
		String companyId = loginUserContext.companyId();
		
		List<MasterData> datas = new ArrayList<>();

		List<JobTitleInfo> listFindAll = sequenceMasterExportRepository.findAll(companyId,query.getStartDate(), query.getEndDate())
				.stream()
				.sorted(Comparator.comparing(JobTitleInfo::getJobTitleCode)).collect(Collectors.toList());
		
//		List<String> listJobTitleItemDto =  listFindAll.stream()
//				.map(job -> job.getJobTitleId())
//				.collect(Collectors.toList());

		if(CollectionUtil.isEmpty(listFindAll)){
			throw new BusinessException("Msg_393");
		}else{
			listFindAll.stream().forEach(c ->{
				Map<String, Object> data = new HashMap<>();
				putEmptyDataOne(data);
				
				
				
				Optional<JobTitle> findByJobTitleId = jobTitleRepository.findByJobTitleId(companyId, c.getJobTitleId());
				
				
				List<JobTitleHistory> jobTitleHistories = findByJobTitleId.get().getJobTitleHistories();
				
				if(CollectionUtil.isEmpty(jobTitleHistories)){
					datas.add(new MasterData(data, null, ""));
				}else{
					
					for(int i=0; i<jobTitleHistories.size();i++){
						
						if(i==0){
							data.put("コード", c.getJobTitleCode());
							data.put("職歴開始日", jobTitleHistories.get(i).span().start());
							data.put("職歴終了", jobTitleHistories.get(i).end());
							
							Optional<JobTitleInfo> findJobTitleInfo  = jobTitleInfoRepository.find(companyId, c.getJobTitleId(),jobTitleHistories.get(i).identifier());
							data.put("名称", findJobTitleInfo.get().getJobTitleName());
							if(findJobTitleInfo.get().isManager()==false){
								data.put("36協定対象外","-");
							}else{
								data.put("36協定対象外","〇");
							}
							data.put("序列コード", findJobTitleInfo.get().getSequenceCode());

							if(findJobTitleInfo.get().getSequenceCode() == null){
								data.put("序列名", "");
							}else{
								Optional<SequenceMaster> opSequenceMaster = sequenceMasterRepository.findBySequenceCode(companyId,findJobTitleInfo.get().getSequenceCode().toString());
								data.put("序列名", opSequenceMaster.get().getSequenceName());
							}
							
							
						}else{
							data.put("コード", "");
							data.put("職歴開始日", jobTitleHistories.get(i).span().start());
							data.put("職歴終了", jobTitleHistories.get(i).end());
							Optional<JobTitleInfo> findJobTitleInfo  = jobTitleInfoRepository.find(companyId, c.getJobTitleId(),jobTitleHistories.get(i).identifier());
							data.put("名称", findJobTitleInfo.get().getJobTitleName());
							if(findJobTitleInfo.get().isManager()==false){
								data.put("36協定対象外","-");
							}else{
								data.put("36協定対象外","〇");
							}
							data.put("序列コード", findJobTitleInfo.get().getSequenceCode());
							if(findJobTitleInfo.get().getSequenceCode() == null){
								data.put("序列名", "");
							}else{
								Optional<SequenceMaster> opSequenceMaster = sequenceMasterRepository.findBySequenceCode(companyId,findJobTitleInfo.get().getSequenceCode().toString());
								data.put("序列名", opSequenceMaster.get().getSequenceName());
							}
							
						}
						datas.add(new MasterData(data, null, ""));
					}
					
				}

				
			});
		}
		return datas;
	}





	@Override
	public String mainSheetName() {
		// TODO Auto-generated method stub
		return "職位情報の登録";
	}
	

	
}
