package nts.uk.ctx.hr.develop.dom.sysoperationset.businessrecognition;

import java.util.Optional;

import lombok.Getter;
import nts.arc.layer.dom.AggregateRoot;

/**
 * @author thanhpv
 * メニュー承認設定
 */
@Getter
public class MenuApprovalSettings extends AggregateRoot {

	/** 会社ID */
	private String cId;

	/** 業務ID */
	private int workId;

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

	/** 個別届出種類ID */
	private Integer rptLayoutId;

	/** 承認者1社員ID */
	private Optional<String> apr1Sid;

	/** 承認者1社員CD */
	private Optional<String> apr1Scd;

	/** 承認者1社員名 */
	private Optional<String> apr1BusinessName;

	/** 承認者1部門CD */
	private Optional<String> app1Devcd;

	/** 承認者1部門名 */
	private Optional<String> app1DevName;

	/** 承認者1職位CD */
	private Optional<String> app1Poscd;

	/** 承認者1職位名 */
	private Optional<String> app1PosName;

	/** 承認者2社員ID */
	private Optional<String> apr2Sid;

	/** 承認者2社員CD */
	private Optional<String> apr2Scd;

	/** 承認者2社員名 */
	private Optional<String> apr2BusinessName;

	/** 承認者2部門CD */
	private Optional<String> app2Devcd;

	/** 承認者2部門名 */
	private Optional<String> app2DevName;

	/** 承認者2職位CD */
	private Optional<String> app2Poscd;

	/** 承認者2職位名 */
	private Optional<String> app2PosName;

	public MenuApprovalSettings(String cId, int workId, String programId, String screenId, boolean useApproval,
			boolean availableAprRoot, boolean availableAprWork1, boolean availableAprWork2, Integer rptLayoutId,
			String apr1Sid, String apr1Scd, String apr1BusinessName, String app1Devcd, String app1DevName,
			String app1Poscd, String app1PosName, String apr2Sid, String apr2Scd, String apr2BusinessName,
			String app2Devcd, String app2DevName, String app2Poscd, String app2PosName) {
		super();
		this.cId = cId;
		this.workId = workId;
		this.programId = programId;
		this.screenId = screenId;
		this.useApproval = useApproval;
		this.availableAprRoot = availableAprRoot;
		this.availableAprWork1 = availableAprWork1;
		this.availableAprWork2 = availableAprWork2;
		this.rptLayoutId = rptLayoutId;
		this.apr1Sid = Optional.ofNullable(apr1Sid);
		this.apr1Scd = Optional.ofNullable(apr1Scd);
		this.apr1BusinessName = Optional.ofNullable(apr1BusinessName);
		this.app1Devcd = Optional.ofNullable(app1Devcd);
		this.app1DevName = Optional.ofNullable(app1DevName);
		this.app1Poscd = Optional.ofNullable(app1Poscd);
		this.app1PosName = Optional.ofNullable(app1PosName);
		this.apr2Sid = Optional.ofNullable(apr2Sid);
		this.apr2Scd = Optional.ofNullable(apr2Scd);
		this.apr2BusinessName = Optional.ofNullable(apr2BusinessName);
		this.app2Devcd = Optional.ofNullable(app2Devcd);
		this.app2DevName = Optional.ofNullable(app2DevName);
		this.app2Poscd = Optional.ofNullable(app2Poscd);
		this.app2PosName = Optional.ofNullable(app2PosName);
	}

}
