package nts.uk.ctx.at.record.infra.entity.employmentinfoterminal.remotel;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.arc.time.GeneralDateTime;
import nts.uk.shr.infra.data.entity.UkJpaEntity;
/**
 * 
 * @author dungbn
 *
 */
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Setter
@Table(name = "KRCDT_TR_REMOTE_BACKUP")
public class KrcdtTrRemoteBackup extends UkJpaEntity implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@EmbeddedId
	public KrcdtTrRemoteBackupPK pk;
	
	/**
	 * 機種名
	 */
	@NotNull
	@Column(name = "TR_NAME")
	public String modelName;
	
	/**
	 * ROMバージョン
	 */
	@NotNull
	@Column(name = "ROM_VERSION")
	public String romVersion;
	
	/**
	 * 機種区分
	 */
	@NotNull
	@Column(name = "RECORD_TYPE")
	public int modelType;
	
	/**
	 * バックアップ年月日
	 */
	@NotNull
	@Column(name = "BACKUP_DATE")
	public GeneralDateTime backupDate;
	
	/**
	 * 大項目NO
	 */
	@Column(name = "MAJOR_NO")
	public Integer majorNo;
	
	/**
	 * 大項目名称
	 */
	@Column(name = "MAJOR_NAME")
	public String majorName;
	
	/**
	 * 小項目NO
	 */
	@Column(name = "SMALL_NO")
	public Integer smallNo;
	
	/**
	 * 小項目名称
	 */
	@Column(name = "SMALL_NAME")
	public String smallName;
	
	/**
	 * 入力タイプ
	 */
	@Column(name = "INPUT_TYPE")
	public int inputType;
	
	/**
	 * 入力桁数
	 */
	@Column(name = "NUMBER_DIGIST")
	public Integer numberDigist;
	
	/**
	 * 設定値
	 */
	@Column(name = "SET_VALUE")
	public String settingValue;
	
	/**
	 * 入力範囲
	 */
	@Column(name = "INPUT_RANGE")
	public String inputRange;
	
	/**
	 * 再起動フラグ
	 */
	@Column(name = "REBOOT")
	public Integer reboot;
	
	/**
	 * 現在の値
	 */
	@Column(name = "CURRENT_VALUE")
	public String currentValue;

	@Override
	protected Object getKey() {
		// TODO Auto-generated method stub
		return pk;
	}

}
