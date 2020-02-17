package nts.uk.ctx.workflow.app.find.approvermanagement.workroot;

import java.util.List;

import lombok.Getter;

@Getter
public class ParamDto {
	/**システム区分*/
    private int systemAtr;
	/**就業ルート区分: 会社(0)　－　職場(1)　－　社員(2)*/
	private int rootType;
	/**	職場ID*/
	private String workplaceId;
	/**社員ID*/
	private String employeeId;
	/**申請種類*/
	private List<Integer> lstAppType;
	/**届出種類ID*/
	private List<Integer> lstNoticeID;
	/**プログラムID(インベント)*/
	private List<String> lstEventID;
}
