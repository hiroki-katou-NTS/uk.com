package nts.uk.ctx.basic.dom.organization.position;


import lombok.Getter;
import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.dom.AggregateRoot;
import nts.arc.time.GeneralDate;
import nts.gul.text.IdentifierUtil;
import nts.uk.shr.com.primitive.Memo;

@Getter
public class Position extends AggregateRoot {

	private GeneralDate endDate;

	private JobName jobName;

	private JobCode jobCode;

	private JobCode jobOutCode;

	private GeneralDate startDate;

	private String historyID;

	private String companyCode;

	private Memo memo;

	private HiterarchyOrderCode hiterarchyOrderCode;
	
	private PresenceCheckScopeSet presenceCheckScopeSet;
	
	public Position( JobName jobName,GeneralDate endDate, JobCode jobCode, JobCode jobOutCode,
			GeneralDate startDate, String historyID, String companyCode, Memo memo ,HiterarchyOrderCode hiterarchyOrderCode ,PresenceCheckScopeSet presenceCheckScopeSet) {

		super();
		this.endDate = endDate;
		this.jobName = jobName;
		this.jobCode = jobCode;
		this.jobOutCode = jobOutCode;
		this.startDate = startDate;
		this.companyCode = companyCode;
		this.historyID = historyID;
		this.memo = memo;
		this.hiterarchyOrderCode = hiterarchyOrderCode;
		this.presenceCheckScopeSet = presenceCheckScopeSet;
	}

	
	public Position( JobName jobName,GeneralDate endDate, JobCode jobCode, JobCode jobOutCode,
			GeneralDate startDate, String companyCode, Memo memo,HiterarchyOrderCode hiterarchyOrderCode ,PresenceCheckScopeSet presenceCheckScopeSet) {

		super();
		this.endDate = endDate;
		this.jobName = jobName;
		this.jobCode = jobCode;
		this.jobOutCode = jobOutCode;
		this.startDate = startDate;
		this.companyCode = companyCode;
		this.historyID = IdentifierUtil.randomUniqueId();
		this.memo = memo;
		this.hiterarchyOrderCode = hiterarchyOrderCode;
		this.presenceCheckScopeSet = presenceCheckScopeSet;
	}

	public static Position createFromJavaType( String jobName, GeneralDate endDate,String jobCode, String jobOutCode,
			GeneralDate startDate, String historyID, String companyCode, String memo ,String hiterarchyOrderCode ,int presenceCheckScopeSet) {
		return new Position(new JobName(jobName),endDate,  new JobCode(jobCode), new JobCode(jobOutCode), startDate,
				historyID, companyCode, new Memo(memo),new HiterarchyOrderCode(hiterarchyOrderCode) ,EnumAdaptor.valueOf(presenceCheckScopeSet, PresenceCheckScopeSet.class));

	}

}