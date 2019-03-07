package nts.uk.file.com.app.subscriberegis;

import nts.gul.collection.CollectionUtil;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.i18n.TextResource;
import nts.uk.shr.infra.file.report.masterlist.annotation.DomainID;
import nts.uk.shr.infra.file.report.masterlist.data.*;
import nts.uk.shr.infra.file.report.masterlist.webservice.MasterListExportQuery;
import nts.uk.shr.infra.file.report.masterlist.webservice.MasterListMode;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.stream.Collectors;

@Stateless
@DomainID("SubscribeRegis")
public class SubscribeRegisExportImpl implements MasterListData {

    @Inject
    private SubscribeRegisRepository subscribeRegisRepository;

    @Override
    public List<MasterData> getMasterDatas(MasterListExportQuery query) {
        String companyId = AppContexts.user().companyId();
        List<MasterData> datas = new ArrayList<>();
        List<LinkedHashMap<String, String>> listEmployee = (List<LinkedHashMap<String, String>>) query.getData();

        if(CollectionUtil.isEmpty(listEmployee)){
            return datas;
        }else{
            List<String> employeeIds = listEmployee.stream().map(x -> x.get("employeeId")).collect(Collectors.toList());
            datas = subscribeRegisRepository.getDataExport(companyId,  employeeIds);
        }

        return datas;
    }

    @Override
    public List<MasterHeaderColumn> getHeaderColumns(MasterListExportQuery query) {
        List<MasterHeaderColumn> columns = new ArrayList<>();
        columns.add(new MasterHeaderColumn(SubscribeRegisColumn.CMM044_42, TextResource.localize("CMM044_42"),
                ColumnTextAlign.LEFT, "", true));
        columns.add(new MasterHeaderColumn(SubscribeRegisColumn.CMM044_43, TextResource.localize("CMM044_43"),
                ColumnTextAlign.LEFT, "", true));
        columns.add(new MasterHeaderColumn(SubscribeRegisColumn.CMM044_44, TextResource.localize("CMM044_44"),
                ColumnTextAlign.CENTER, "", true));
        columns.add(new MasterHeaderColumn(SubscribeRegisColumn.CMM044_45, TextResource.localize("CMM044_45"),
                ColumnTextAlign.CENTER, "", true));
        columns.add(new MasterHeaderColumn(SubscribeRegisColumn.CMM044_46, TextResource.localize("CMM044_46"),
                ColumnTextAlign.LEFT, "", true));
        columns.add(new MasterHeaderColumn(SubscribeRegisColumn.CMM044_47, TextResource.localize("CMM044_47"),
                ColumnTextAlign.LEFT, "", true));
        return columns;
    }


	@Override
	public MasterListMode mainSheetMode(){
		return MasterListMode.NONE;
	}
}
