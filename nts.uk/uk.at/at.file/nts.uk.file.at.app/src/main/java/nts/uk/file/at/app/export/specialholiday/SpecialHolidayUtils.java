package nts.uk.file.at.app.export.specialholiday;

import java.util.HashMap;
import java.util.Map;

import javax.ejb.Stateless;

import org.apache.commons.lang3.StringUtils;

import nts.uk.shr.com.i18n.TextResource;

@Stateless
public class SpecialHolidayUtils {
	public static final String KMF004_106 = "コード";
    public static final String KMF004_107 = "名称";
    public static final String KMF004_108 = "対象項目";
    public static final String KMF004_109 = "メモ";
    public static final String KMF004_110 = "付与基準日";
    public static final String KMF004_111 = "付与方法";
    public static final String KMF004_112 = "付与周期年";
    public static final String KMF004_113 = "付与周期日数";
    public static final String KMF004_114 = "付与コード";
    public static final String KMF004_115 = "付与名";
    public static final String KMF004_116 = "既定のテーブル";
    public static final String KMF004_117 = "付与の設定";
    public static final String KMF004_118 = "付与年数";
    public static final String KMF004_119 = "付与月数";
    public static final String KMF004_120 = "上限日数";
    public static final String KMF004_121 = "以降毎年固定日数を付与する";
    public static final String KMF004_122 = "固定付与日数";
    public static final String KMF004_123 = "有効期限";
    public static final String KMF004_124 = "蓄積上限日数";
    public static final String KMF004_125 = "有効期限年";
    public static final String KMF004_126 = "有効期限月";
    public static final String KMF004_127 = "使用可能期限開始月";
    public static final String KMF004_128 = "使用可能期限開始日";
    public static final String KMF004_129 = "使用可能期限終了月";
    public static final String KMF004_130 = "使用可能期限終了日";
    public static final String KMF004_131 = "利用条件性別";
    public static final String KMF004_132 = "性別";
    public static final String KMF004_133 = "雇用";
    public static final String KMF004_134 = "対象雇用";
    public static final String KMF004_135 = "分類";
    public static final String KMF004_136 = "対象分類";
    public static final String KMF004_137 = "年齢";
    public static final String KMF004_138 = "下限年齢";
    public static final String KMF004_139 = "上限年齢";
    public static final String KMF004_140 = "年齢基準";
    public static final String KMF004_141 = "年齢基準月";
    public static final String KMF004_142 = "年齢基準日";
    public static final String KMF004_143 = "枠番号";
    public static final String KMF004_144 = "特別休暇枠名称";
    public static final String KMF004_145 = "上限";
    public static final String KMF004_146 = "上限日数";
    public static final String KMF004_147 = "オプション 忌引とする";
    public static final String KMF004_148 = "オプション 休日をまたがって取得した場合には休日を含めて上限チェックをおこなう";
    public static final String KMF004_149 = "コード";
    public static final String KMF004_150 = "名称";
    public static final String KMF004_151 = "続柄上限日数";
    public static final String KMF004_152 = "喪主時加算日数";
    public static final String KMF004_161 = "メモ";
    public static final String KMF004_162 = "続柄入力を利用する";
    public static final String KMF004_187 = "自動で付与する";
    public static final String KMF004_188 = "指定日";
    public static final String KMF004_189 = "付与周期（年）";
    public static final String KMF004_190 = "付与周期（月）";
    public static final String KMF004_191 = "連続で取得する";
    public static final String MONTH = "MONTH";
    public static final String DAY = "DAY";
    
    /**
     * Get Text enum 付与基準日
     * @param value
     * @return
     */
    public static String getGrantDateOptions(int value){
    	switch (value){
    	case 0:
    		return TextResource.localize("KMF004_15");
    	case 1:
    		return TextResource.localize("KMF004_16");
    	case 2: 
    		return TextResource.localize("KMF004_17");
    	default: 
    		return null;
    	}
    }
    
    /**
     * Get Text enum 付与するタイミングの種類
     * @param value
     * @return
     */
    public static String getTimeType(int value){
    	switch (value){
    	case 0:
    		return TextResource.localize("KMF004_19");
    	case 1:
    		return TextResource.localize("KMF004_20");
    	default: 
    		return null;
    	}
    }
    
    /**
     * 期限指定方法
     * @param value
     * @return
     */
    public static String getTimeMethod(int value){
    	
    	switch (value){
    	case 0:
    		return TextResource.localize("KMF004_28");
    	case 1:
    		return TextResource.localize("KMF004_29");
    		
    	case 2:
    		return TextResource.localize("KMF004_30");
    	case 3:
    		return TextResource.localize("KMF004_31");
    	default: 
    		return null;
    	}
    }
    
    
    /**
     * Get Text enum
     * @param value
     * @return
     */
    public static String getGender(int value){
    	switch (value){
    	case 0:
    		return TextResource.localize("KMF004_55");
    	case 1:
    		return TextResource.localize("KMF004_56");
    	default: 
    		return null;
    	}
    }
    
    public static Map<String, String> convertMonthDay(String value){
    	Map<String, String> result = new HashMap<>();
    	result.put(MONTH, "");
    	result.put(DAY, "");
    	if (StringUtils.isNotEmpty(value)){
    		result.put(MONTH, String.valueOf(Integer.parseInt(value)/100) + "月");
        	result.put(DAY, String.valueOf(Integer.parseInt(value)%100)+"日");
    	}
    	return result;
    }
}
