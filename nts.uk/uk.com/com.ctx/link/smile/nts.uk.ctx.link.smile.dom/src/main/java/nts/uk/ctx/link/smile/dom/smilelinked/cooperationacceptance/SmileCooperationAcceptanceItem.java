package nts.uk.ctx.link.smile.dom.smilelinked.cooperationacceptance;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.val;
import nts.arc.enums.EnumAdaptor;
import nts.arc.task.tran.AtomTask;
import nts.uk.ctx.exio.dom.input.setting.ExternalImportCode;
import nts.uk.ctx.exio.dom.input.setting.copy.ExternalImportDefaultDataItem;
import nts.uk.ctx.exio.dom.input.setting.copy.GenerateExternalImportDefaultData;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static nts.uk.ctx.exio.dom.input.setting.copy.ExternalImportDefaultDataItem.*;

/**
 * Smile連携受入項目
 * 
 */
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public enum SmileCooperationAcceptanceItem {
	
	/**
	 * SMILE_組織情報（職場）
	 */
	ORGANIZATION_INFORMATION(1, "組織情報", SMILE_SOSHIKI),

	/**
	 * SMILE_人事基本情報
	 */
	BASIC_PERSONNEL_INFORMATION(2, "人事基本情報", SMILE_JINJI_KIHON),

	/**
	 * SMILE_職制情報
	 */
	JOB_STRUCTURE_INFORMATION(3, "職制情報", SMILE_SHOKUSEI),

	/**
	 * SMILE_住所情報
	 */
	ADDRESS_INFORMATION(4, "住所情報", null),

	/**
	 * SMILE_休職情報
	 */
	LEAVE_INFORMATION(5, "休職情報", SMILE_KYUSHOKU),

	/**
	 * SMILE_所属マスター
	 */
	AFFILIATED_MASTER(6, "所属マスター", null),

	/**
	 * SMILE_社員マスター
	 */
	EMPLOYEE_MASTER(7, "社員マスター", null);

	public final int value;
	public final String nameId;

	/** 外部受入の初期データ項目 */
	private final ExternalImportDefaultDataItem defaultDataItem;

	public static SmileCooperationAcceptanceItem valueOf(int value) {
		return EnumAdaptor.valueOf(value, SmileCooperationAcceptanceItem.class);
	}

    public int getValue() {
    	return this.value;
    }
	
    public static final List<SmileCooperationAcceptanceItem> lookup = new ArrayList<SmileCooperationAcceptanceItem>();
    static {
        for (SmileCooperationAcceptanceItem e : SmileCooperationAcceptanceItem.values()) {
            lookup.add(e);
        }
    }

	/**
	 * 初期データを生成する
	 * @param settingCode
	 * @return
	 */
	public AtomTask generateDefaultData(
			RequireGenerateDefaultData require,
			String companyId,
			ExternalImportCode settingCode) {

		if (defaultDataItem == null) {
			throw new RuntimeException("no implementation: " + this);
		}

		AtomTask externalImportTask = GenerateExternalImportDefaultData.generate(
				require, companyId, settingCode, defaultDataItem);

		AtomTask smileSettingTask = autoSetSmileSetting(require, companyId, settingCode);

		return externalImportTask.then(smileSettingTask);
	}

	/**
	 * Smileの連携設定に外部受入設定コードがまだ設定されていなければ、今回生成する設定コードを自動設定しておく
	 * @param require
	 * @param companyId
	 * @param settingCode
	 * @return
	 */
	private AtomTask autoSetSmileSetting(RequireGenerateDefaultData require, String companyId, ExternalImportCode settingCode) {

		val smileSetting = require.getSmileCooperationAcceptanceSetting(companyId, this);
		if (smileSetting.getCooperationAcceptanceConditions().isPresent()) {
			return AtomTask.none();
		}

		smileSetting.setCooperationAcceptanceConditions(Optional.of(settingCode));

		return AtomTask.of(() -> {
			require.save(smileSetting);
		});
	}

	public interface RequireGenerateDefaultData extends GenerateExternalImportDefaultData.Require {

		SmileCooperationAcceptanceSetting getSmileCooperationAcceptanceSetting(String companyId, SmileCooperationAcceptanceItem item);

		void save(SmileCooperationAcceptanceSetting smileSetting);
	}
}
