package nts.uk.ctx.workflow.app.export;

import nts.arc.enums.EnumAdaptor;
import nts.gul.collection.CollectionUtil;
import nts.uk.ctx.workflow.dom.adapter.bs.EmployeeAdapter;
import nts.uk.ctx.workflow.dom.agent.Agent;
import nts.uk.ctx.workflow.dom.agent.AgentAppType;
import nts.uk.ctx.workflow.dom.agent.AgentRepository;
import nts.uk.ctx.workflow.dom.export.agent.AgentExportData;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.i18n.TextResource;
import nts.uk.shr.infra.file.report.masterlist.annotation.DomainID;
import nts.uk.shr.infra.file.report.masterlist.data.ColumnTextAlign;
import nts.uk.shr.infra.file.report.masterlist.data.MasterData;
import nts.uk.shr.infra.file.report.masterlist.data.MasterHeaderColumn;
import nts.uk.shr.infra.file.report.masterlist.data.MasterListData;
import nts.uk.shr.infra.file.report.masterlist.webservice.MasterListExportQuery;
import sun.management.resources.agent;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.*;
import java.util.stream.Collectors;


@Stateless
@DomainID(value = "SubscribeRegis")
public class SubscribeRegisExportImpl implements MasterListData {

    @Inject
    private AgentRepository agentRepository;

    @Inject
    private EmployeeAdapter employeeAdapter;

    private static final String CMM044_42 = "社員コード";
    private static final String CMM044_43 = "社員名";
    private static final String CMM044_44 = "期間．開始日";
    private static final String CMM044_45 = "期間．終了日";
    private static final String CMM044_46 = "就業承認．代行承認種類";
    private static final String CMM044_47 = "就業承認．承認代行者";
    private static final String CMM044_48 = "人事承認．代行承認種類";
    private static final String CMM044_49 = "人事承認．承認代行者";
    private static final String CMM044_50 = "給与承認．代行承認種類";
    private static final String CMM044_51 = "給与承認．承認代行者";
    private static final String CMM044_52 = "経理承認．代行承認種類";
    private static final String CMM044_53 = "経理承認．承認代行者";

    @Override
    public List<MasterHeaderColumn> getHeaderColumns(MasterListExportQuery query) {
        List<MasterHeaderColumn> columns = new ArrayList<>();
        columns.add(new MasterHeaderColumn(CMM044_42, TextResource.localize("CMM044_42"),
                ColumnTextAlign.LEFT, "", true));
        columns.add(new MasterHeaderColumn(CMM044_43, TextResource.localize("CMM044_43"),
                ColumnTextAlign.LEFT, "", true));
        columns.add(new MasterHeaderColumn(CMM044_44, TextResource.localize("CMM044_44"),
                ColumnTextAlign.CENTER, "", true));
        columns.add(new MasterHeaderColumn(CMM044_45, TextResource.localize("CMM044_45"),
                ColumnTextAlign.CENTER, "", true));
        columns.add(new MasterHeaderColumn(CMM044_46, TextResource.localize("CMM044_46"),
                ColumnTextAlign.LEFT, "", true));
        columns.add(new MasterHeaderColumn(CMM044_47, TextResource.localize("CMM044_47"),
                ColumnTextAlign.LEFT, "", true));
        columns.add(new MasterHeaderColumn(CMM044_48, TextResource.localize("CMM044_48"),
                ColumnTextAlign.LEFT, "", true));
        columns.add(new MasterHeaderColumn(CMM044_49, TextResource.localize("CMM044_49"),
                ColumnTextAlign.LEFT, "", true));
        columns.add(new MasterHeaderColumn(CMM044_50, TextResource.localize("CMM044_50"),
                ColumnTextAlign.LEFT, "", true));
        columns.add(new MasterHeaderColumn(CMM044_51, TextResource.localize("CMM044_51"),
                ColumnTextAlign.LEFT, "", true));
        columns.add(new MasterHeaderColumn(CMM044_52, TextResource.localize("CMM044_52"),
                ColumnTextAlign.LEFT, "", true));
        columns.add(new MasterHeaderColumn(CMM044_53, TextResource.localize("CMM044_53"),
                ColumnTextAlign.LEFT, "", true));
        return columns;
    }


    @Override
    public List<MasterData> getMasterDatas(MasterListExportQuery query) {
        String companyId = AppContexts.user().companyId();
        List<MasterData> datas = new ArrayList<>();

        List<LinkedHashMap<String, String>> listEmployee = (List<LinkedHashMap<String, String>>) query.getData();
        if(CollectionUtil.isEmpty(listEmployee)){
            return new ArrayList<>();
        }else{
            List<String> employeeIds = new ArrayList<String>();

            listEmployee.forEach(x -> {
                employeeIds.add(x.get("employeeId"));
            });

            List<AgentExportData> listAgentExportData = agentRepository.getAgentByEmployeeID(companyId, employeeIds);
            if (!CollectionUtil.isEmpty(listAgentExportData)) {
                listAgentExportData.forEach(x -> {
                    this.createData(x,datas);
                });
            }
        }

        return datas;
    }

    private void createData(AgentExportData agent, List<MasterData> datas) {
        Map<String, Object> data = new HashMap<>();
        if (agent == null) {
            data.put(CMM044_42, agent.getEmployeeCode());
            data.put(CMM044_43, agent.getEmployeeName());
            data.put(CMM044_44, "");
            data.put(CMM044_45, "");
            data.put(CMM044_46, "");
            data.put(CMM044_47, "");
            data.put(CMM044_48, "");
            data.put(CMM044_49, "");
            data.put(CMM044_50, "");
            data.put(CMM044_51, "");
            data.put(CMM044_52, "");
            data.put(CMM044_53, "");
        } else {
            data.put(CMM044_42, agent.getEmployeeCode());
            data.put(CMM044_43, agent.getEmployeeName());
            data.put(CMM044_44, agent.getStartDate());
            data.put(CMM044_45, agent.getEndDate());
            data.put(CMM044_46, TextResource.localize("CMM044_16"));
            data.put(CMM044_47, agent.getPersonName());
            data.put(CMM044_48, this.getAgentAppType(EnumAdaptor.valueOf(agent.getAgentAppType2(),AgentAppType.class)));
            data.put(CMM044_49, this.getEmpNameAgentAppType(EnumAdaptor.valueOf(agent.getAgentAppType2(),AgentAppType.class)));
            data.put(CMM044_50, this.getAgentAppType(EnumAdaptor.valueOf(agent.getAgentAppType3(),AgentAppType.class)));
            data.put(CMM044_51, this.getEmpNameAgentAppType(EnumAdaptor.valueOf(agent.getAgentAppType3(),AgentAppType.class)));
            data.put(CMM044_52, this.getAgentAppType(EnumAdaptor.valueOf(agent.getAgentAppType4(),AgentAppType.class)));
            data.put(CMM044_53, this.getEmpNameAgentAppType(EnumAdaptor.valueOf(agent.getAgentAppType4(),AgentAppType.class)));
        }
        datas.add(new MasterData(data, null, ""));
    }

    private String getAgentAppType(AgentAppType agentAppType) {
        String result = "";
        switch (agentAppType) {
            case PATH:
                result = TextResource.localize("CMM044_17");
                break;
            case NO_SETTINGS:
                result = TextResource.localize("CMM044_18");
                break;
        }

        return result;
    }

    private String getEmpNameAgentAppType(AgentAppType agentAppType) {
        String result = "";
        switch (agentAppType) {
            case PATH:
                result = "ー";
                break;
            case NO_SETTINGS:
                result = "ー";
                break;
        }

        return result;
    }
}
