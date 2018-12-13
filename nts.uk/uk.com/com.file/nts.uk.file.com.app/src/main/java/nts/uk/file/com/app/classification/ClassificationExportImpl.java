package nts.uk.file.com.app.classification;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.error.BusinessException;
import nts.gul.collection.CollectionUtil;
import nts.uk.ctx.bs.employee.dom.classification.Classification;
import nts.uk.ctx.bs.employee.dom.classification.ClassificationRepository;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.i18n.TextResource;
import nts.uk.shr.infra.file.report.masterlist.annotation.DomainID;
import nts.uk.shr.infra.file.report.masterlist.data.ColumnTextAlign;
import nts.uk.shr.infra.file.report.masterlist.data.MasterData;
import nts.uk.shr.infra.file.report.masterlist.data.MasterHeaderColumn;
import nts.uk.shr.infra.file.report.masterlist.data.MasterListData;
import nts.uk.shr.infra.file.report.masterlist.webservice.MasterListExportQuery;

/**
 * 
 * @author minhvv
 *
 */

@Stateless
@DomainID(value = "Classification")
public class ClassificationExportImpl implements MasterListData{

	@Inject
	private ClassificationRepository classificationRepository;
	
	
	@Override
	public List<MasterData> getMasterDatas(MasterListExportQuery query) {
		// TODO Auto-generated method stub
		
		String companyId = AppContexts.user().companyId();

		List<MasterData> datas = new ArrayList<>();
		
		List<Classification> listEmployeeExport = classificationRepository.getAllManagementCategory(companyId);
		
		if (CollectionUtil.isEmpty(listEmployeeExport)) {
			throw new BusinessException("Msg_393");
		} else {
			listEmployeeExport.stream().forEach(c -> {
				Map<String, Object> data = new HashMap<>();
				data.put("コード", c.getClassificationCode());
				data.put("名称", c.getClassificationName());
				data.put("メモ", c.getMemo());
				datas.add(new MasterData(data, null, ""));
				
			});
		}
		return datas;
	}

	@Override
	public List<MasterHeaderColumn> getHeaderColumns(MasterListExportQuery query) {
		List<MasterHeaderColumn> columns = new ArrayList<>();
		columns.add(new MasterHeaderColumn("コード", TextResource.localize("CMM014_6"),
				ColumnTextAlign.CENTER, "", true));
		columns.add(new MasterHeaderColumn("名称", TextResource.localize("CMM014_7"),
				ColumnTextAlign.CENTER, "", true));
		columns.add(new MasterHeaderColumn("メモ", TextResource.localize("CMM014_8"),
				ColumnTextAlign.CENTER, "", true));
		return columns;
	}

}
