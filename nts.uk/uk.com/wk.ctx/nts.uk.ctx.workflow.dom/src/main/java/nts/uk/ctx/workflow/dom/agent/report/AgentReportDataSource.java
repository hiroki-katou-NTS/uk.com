package nts.uk.ctx.workflow.dom.agent.report;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.LinkedHashMap;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class AgentReportDataSource {
    private String fileName;
    private List<LinkedHashMap<String, String>> data;
}
