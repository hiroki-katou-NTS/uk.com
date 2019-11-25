package nts.uk.ctx.hr.notice.app.command.report;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ClassificationCommand {
	private int reportLayoutId;
	private String reportLayoutCd;
	private String reportLayoutName;
	private int dispOrder;
	private String categoryCode;
	private String categoryName;
	private String personInfoCategoryID;
	private int layoutItemType;
	private boolean fixedAtr;
	private List<ClassificationItemHumanCommand> listItemClsDf;
}
