package nts.uk.ctx.at.function.dom.adapter.employmentinfoterminal.infoterminal;

import java.util.Optional;

import lombok.Getter;

/**
 * @author ThanhNX
 *
 *         就業情報端末
 */
public class FuncEmpInfoTerminalImport {

	/**
	 * IPアドレス
	 */
	@Getter
	private Optional<String> ipAddress;

	/**
	 * MACアドレス
	 */
	@Getter
	private String macAddress;

	/**
	 * コード
	 */
	@Getter
	private final String empInfoTerCode;

	/**
	 * シリアルNO
	 */
	@Getter
	private Optional<String> terSerialNo;

	/**
	 * 名称
	 */
	@Getter
	private String empInfoTerName;

	/**
	 * 契約コード
	 */
	@Getter
	private final String contractCode;

	/**
	 * 打刻情報の作成
	 */
//	@Getter
//	private final CreateStampInfo createStampInfo;

	/**
	 * 機種
	 */
	@Getter
	private Integer modelEmpInfoTer;

	/**
	 * 監視間隔時間
	 */
	@Getter
	private Integer intervalTime;

	/**
	 * 就業情報端末のメモ
	 */
	@Getter
	private Optional<String> empInfoTerMemo;

	public FuncEmpInfoTerminalImport(FuncEmpInfoTerminalBuilder builder) {
		super();
		this.ipAddress = builder.ipAddress;
		this.macAddress = builder.macAddress;
		this.empInfoTerCode = builder.empInfoTerCode;
		this.terSerialNo = builder.terSerialNo;
		this.empInfoTerName = builder.empInfoTerName;
		this.contractCode = builder.contractCode;
		this.modelEmpInfoTer = builder.modelEmpInfoTer;
		this.intervalTime = builder.intervalTime;
		this.empInfoTerMemo = builder.empInfoTerMemo;
	}

	public static class FuncEmpInfoTerminalBuilder {
		/**
		 * IPアドレス
		 */
		private Optional<String> ipAddress;

		/**
		 * MACアドレス
		 */
		private String macAddress;

		/**
		 * コード
		 */
		private String empInfoTerCode;

		/**
		 * シリアルNO
		 */
		private Optional<String> terSerialNo;

		/**
		 * 名称
		 */
		private String empInfoTerName;

		/**
		 * 契約コード
		 */
		private String contractCode;

		/**
		 * 機種
		 */
		private Integer modelEmpInfoTer;

		/**
		 * 監視間隔時間
		 */
		private Integer intervalTime;

		/**
		 * 就業情報端末のメモ
		 */
		@Getter
		private Optional<String> empInfoTerMemo;

		public FuncEmpInfoTerminalBuilder(Optional<String> ipAddress, String macAddress, String empInfoTerCode,
				Optional<String> terSerialNo, String empInfoTerName, String contractCode) {
			this.ipAddress = ipAddress;
			this.macAddress = macAddress;
			this.empInfoTerCode = empInfoTerCode;
			this.terSerialNo = terSerialNo;
			this.empInfoTerName = empInfoTerName;
			this.contractCode = contractCode;
		}

		public FuncEmpInfoTerminalBuilder modelEmpInfoTer(Integer modelEmpInfoTer) {
			this.modelEmpInfoTer = modelEmpInfoTer;
			return this;
		}

		public FuncEmpInfoTerminalBuilder intervalTime(Integer intervalTime) {
			this.intervalTime = intervalTime;
			return this;
		}

		public FuncEmpInfoTerminalBuilder empInfoTerMemo(Optional<String> empInfoTerMemo) {
			this.empInfoTerMemo = empInfoTerMemo;
			return this;
		}

		public FuncEmpInfoTerminalImport build() {
			return new FuncEmpInfoTerminalImport(this);
		}
	}
}
