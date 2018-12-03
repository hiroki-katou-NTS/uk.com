package nts.uk.ctx.workflow.dom.export.agent;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.workflow.dom.agent.AgentAppType;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AgentExportData {

    private String employeeId;

    /** 社員コード*/
    private String employeeCode;
    /** 社員名*/
    private String employeeName;

    /**開始日*/
    private String startDate;

    /**終了日*/
    private String endDate;

    /**就業承認: 社員ID*/
    private String agentSid1;

    /**就業承認: 代行承認種類1*/
    private Integer agentAppType1;

    /**人事承認: 社員ID*/
    private String agentSid2;

    /**人事承認: 代行承認種類2*/
    private Integer agentAppType2;

    /**給与承認: 社員ID*/
    private String agentSid3;

    /**給与承認: 代行承認種類3*/
    private Integer agentAppType3;

    /**経理承認: 社員ID*/
    private String agentSid4;

    /**経理承認: 代行承認種類4*/
    private Integer agentAppType4;

    private String personName;




}
