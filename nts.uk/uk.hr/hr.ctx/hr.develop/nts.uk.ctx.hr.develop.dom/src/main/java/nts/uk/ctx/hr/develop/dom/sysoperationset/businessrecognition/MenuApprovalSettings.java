package nts.uk.ctx.hr.develop.dom.sysoperationset.businessrecognition;

import lombok.Getter;
import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.dom.AggregateRoot;
import nts.uk.ctx.hr.shared.dom.databeforereflecting.WorkId;

/**
 * @author thanhpv
 * メニュー承認設定
 */
@Getter
public class MenuApprovalSettings extends AggregateRoot {

	/** 会社ID */
	private String cId;

	/** 業務ID */
	private WorkId workId;

	/** 個別届出種類ID */
	private int rptLayoutId;

	/** プログラムID */
	private String programId;

	/** 画面ID */
	private String screenId;

	/** 承認機能を使用する */
	private boolean useApproval;

	/** 承認ルート使用 */
	private boolean availableAprRoot;

	/** 業務承認1使用 */
	private boolean availableAprWork1;

	/** 業務承認2使用 */
	private boolean availableAprWork2;

	/** 承認者1社員ID */
	private String apr1Sid;

	/** 承認者1社員CD */
	private String apr1Scd;

	/** 承認者1社員名 */
	private String apr1BusinessName;

	/** 承認者1部門CD */
	private String app1Devcd;

	/** 承認者1部門名 */
	private String app1DevName;

	/** 承認者1職位CD */
	private String app1Poscd;

	/** 承認者1職位名 */
	private String app1PosName;

	/** 承認者2社員ID */
	private String apr2Sid;

	/** 承認者2社員CD */
	private String apr2Scd;

	/** 承認者2社員名 */
	private String apr2BusinessName;

	/** 承認者2部門CD */
	private String app2Devcd;

	/** 承認者2部門名 */
	private String app2DevName;

	/** 承認者2職位CD */
	private String app2Poscd;

	/** 承認者2職位名 */
	private String app2PosName;

	public MenuApprovalSettings(String cId, int workId, int rptLayoutId, String programId, String screenId,
			boolean useApproval, boolean availableAprRoot, boolean availableAprWork1, boolean availableAprWork2,
			String apr1Sid, String apr1Scd, String apr1BusinessName, String app1Devcd, String app1DevName,
			String app1Poscd, String app1PosName, String apr2Sid, String apr2Scd, String apr2BusinessName,
			String app2Devcd, String app2DevName, String app2Poscd, String app2PosName) {
		super();
		this.cId = cId;
		this.workId = EnumAdaptor.valueOf(workId, WorkId.class);
		this.rptLayoutId = rptLayoutId;
		this.programId = programId;
		this.screenId = screenId;
		this.useApproval = useApproval;
		this.availableAprRoot = availableAprRoot;
		this.availableAprWork1 = availableAprWork1;
		this.availableAprWork2 = availableAprWork2;
		this.apr1Sid = apr1Sid;
		this.apr1Scd = apr1Scd;
		this.apr1BusinessName = apr1BusinessName;
		this.app1Devcd = app1Devcd;
		this.app1DevName = app1DevName;
		this.app1Poscd = app1Poscd;
		this.app1PosName = app1PosName;
		this.apr2Sid = apr2Sid;
		this.apr2Scd = apr2Scd;
		this.apr2BusinessName = apr2BusinessName;
		this.app2Devcd = app2Devcd;
		this.app2DevName = app2DevName;
		this.app2Poscd = app2Poscd;
		this.app2PosName = app2PosName;
	}

}
