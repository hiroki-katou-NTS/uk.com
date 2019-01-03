package nts.uk.ctx.at.record.infra.entity.daily.anyitem;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.NamedStoredProcedureQuery;
import javax.persistence.ParameterMode;
import javax.persistence.StoredProcedureParameter;
import javax.persistence.Table;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.uk.ctx.at.record.dom.daily.optionalitemtime.AnyItemAmount;
import nts.uk.ctx.at.record.dom.daily.optionalitemtime.AnyItemNo;
import nts.uk.ctx.at.record.dom.daily.optionalitemtime.AnyItemTime;
import nts.uk.ctx.at.record.dom.daily.optionalitemtime.AnyItemTimes;
import nts.uk.ctx.at.record.dom.daily.optionalitemtime.AnyItemValue;
import nts.uk.ctx.at.record.dom.daily.optionalitemtime.AnyItemValueOfDaily;
import nts.uk.ctx.at.record.infra.entity.daily.time.KrcdtDayTimePK;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

/**
 * 任意項目
 * @author lanlt
 */
@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "KRCDT_DAY_ANYITEMVALUE_MERGE")
@NamedStoredProcedureQuery(name = "SSPR_DAIKYUZAN_PRC", procedureName = "SSPR_DAIKYUZAN_PRC", parameters = {
		@StoredProcedureParameter(name = "CID", mode = ParameterMode.IN, type = String.class),
		@StoredProcedureParameter(name = "SID", mode = ParameterMode.IN, type = String.class),
		@StoredProcedureParameter(name = "YMD", mode = ParameterMode.IN, type = Date.class),
		@StoredProcedureParameter(name = "WorkTypeCode", mode = ParameterMode.IN, type = String.class),
		@StoredProcedureParameter(name = "WorkTimeCode", mode = ParameterMode.IN, type = String.class),
		@StoredProcedureParameter(name = "HoliWorkTimes", mode = ParameterMode.IN, type = Integer.class) },
		resultClasses = Integer.class)
public class KrcdtDayAnyItemValueMerge extends UkJpaEntity implements Serializable {

	/** serialVersionUID */
	private static final long serialVersionUID = 1L;

	/** プライマリキー */
	@EmbeddedId
	public KrcdtDayTimePK krcdtDayTimePk;

	/** 時間 */
	@Column(name = "TIME_VALUE_1")
	public Integer timeValue1;

	@Column(name = "TIME_VALUE_2")
	public Integer timeValue2;

	@Column(name = "TIME_VALUE_3")
	public Integer timeValue3;

	@Column(name = "TIME_VALUE_4")
	public Integer timeValue4;

	@Column(name = "TIME_VALUE_5")
	public Integer timeValue5;

	@Column(name = "TIME_VALUE_6")
	public Integer timeValue6;

	@Column(name = "TIME_VALUE_7")
	public Integer timeValue7;

	@Column(name = "TIME_VALUE_8")
	public Integer timeValue8;

	@Column(name = "TIME_VALUE_9")
	public Integer timeValue9;

	@Column(name = "TIME_VALUE_10")
	public Integer timeValue10;

	@Column(name = "TIME_VALUE_11")
	public Integer timeValue11;

	@Column(name = "TIME_VALUE_12")
	public Integer timeValue12;

	@Column(name = "TIME_VALUE_13")
	public Integer timeValue13;

	@Column(name = "TIME_VALUE_14")
	public Integer timeValue14;

	@Column(name = "TIME_VALUE_15")
	public Integer timeValue15;

	@Column(name = "TIME_VALUE_16")
	public Integer timeValue16;

	@Column(name = "TIME_VALUE_17")
	public Integer timeValue17;

	@Column(name = "TIME_VALUE_18")
	public Integer timeValue18;

	@Column(name = "TIME_VALUE_19")
	public Integer timeValue19;

	@Column(name = "TIME_VALUE_20")
	public Integer timeValue20;

	@Column(name = "TIME_VALUE_21")
	public Integer timeValue21;

	@Column(name = "TIME_VALUE_22")
	public Integer timeValue22;

	@Column(name = "TIME_VALUE_23")
	public Integer timeValue23;

	@Column(name = "TIME_VALUE_24")
	public Integer timeValue24;

	@Column(name = "TIME_VALUE_25")
	public Integer timeValue25;

	@Column(name = "TIME_VALUE_26")
	public Integer timeValue26;

	@Column(name = "TIME_VALUE_27")
	public Integer timeValue27;

	@Column(name = "TIME_VALUE_28")
	public Integer timeValue28;

	@Column(name = "TIME_VALUE_29")
	public Integer timeValue29;

	@Column(name = "TIME_VALUE_30")
	public Integer timeValue30;

	@Column(name = "TIME_VALUE_31")
	public Integer timeValue31;

	@Column(name = "TIME_VALUE_32")
	public Integer timeValue32;

	@Column(name = "TIME_VALUE_33")
	public Integer timeValue33;

	@Column(name = "TIME_VALUE_34")
	public Integer timeValue34;

	@Column(name = "TIME_VALUE_35")
	public Integer timeValue35;

	@Column(name = "TIME_VALUE_36")
	public Integer timeValue36;

	@Column(name = "TIME_VALUE_37")
	public Integer timeValue37;

	@Column(name = "TIME_VALUE_38")
	public Integer timeValue38;

	@Column(name = "TIME_VALUE_39")
	public Integer timeValue39;

	@Column(name = "TIME_VALUE_40")
	public Integer timeValue40;

	@Column(name = "TIME_VALUE_41")
	public Integer timeValue41;

	@Column(name = "TIME_VALUE_42")
	public Integer timeValue42;

	@Column(name = "TIME_VALUE_43")
	public Integer timeValue43;

	@Column(name = "TIME_VALUE_44")
	public Integer timeValue44;

	@Column(name = "TIME_VALUE_45")
	public Integer timeValue45;

	@Column(name = "TIME_VALUE_46")
	public Integer timeValue46;

	@Column(name = "TIME_VALUE_47")
	public Integer timeValue47;

	@Column(name = "TIME_VALUE_48")
	public Integer timeValue48;

	@Column(name = "TIME_VALUE_49")
	public Integer timeValue49;

	@Column(name = "TIME_VALUE_50")
	public Integer timeValue50;

	/** 振替時間 - TIME_VALUE_1 */
	@Column(name = "TIME_VALUE_51")
	public Integer timeValue51;

	@Column(name = "TIME_VALUE_52")
	public Integer timeValue52;

	@Column(name = "TIME_VALUE_53")
	public Integer timeValue53;

	@Column(name = "TIME_VALUE_54")
	public Integer timeValue54;

	@Column(name = "TIME_VALUE_55")
	public Integer timeValue55;

	@Column(name = "TIME_VALUE_56")
	public Integer timeValue56;

	@Column(name = "TIME_VALUE_57")
	public Integer timeValue57;

	@Column(name = "TIME_VALUE_58")
	public Integer timeValue58;

	@Column(name = "TIME_VALUE_59")
	public Integer timeValue59;

	@Column(name = "TIME_VALUE_60")
	public Integer timeValue60;

	@Column(name = "TIME_VALUE_61")
	public Integer timeValue61;

	@Column(name = "TIME_VALUE_62")
	public Integer timeValue62;

	@Column(name = "TIME_VALUE_63")
	public Integer timeValue63;

	@Column(name = "TIME_VALUE_64")
	public Integer timeValue64;

	@Column(name = "TIME_VALUE_65")
	public Integer timeValue65;

	@Column(name = "TIME_VALUE_66")
	public Integer timeValue66;

	@Column(name = "TIME_VALUE_67")
	public Integer timeValue67;

	@Column(name = "TIME_VALUE_68")
	public Integer timeValue68;

	@Column(name = "TIME_VALUE_69")
	public Integer timeValue69;

	@Column(name = "TIME_VALUE_70")
	public Integer timeValue70;

	@Column(name = "TIME_VALUE_71")
	public Integer timeValue71;

	@Column(name = "TIME_VALUE_72")
	public Integer timeValue72;

	@Column(name = "TIME_VALUE_73")
	public Integer timeValue73;

	@Column(name = "TIME_VALUE_74")
	public Integer timeValue74;

	@Column(name = "TIME_VALUE_75")
	public Integer timeValue75;

	@Column(name = "TIME_VALUE_76")
	public Integer timeValue76;

	@Column(name = "TIME_VALUE_77")
	public Integer timeValue77;

	@Column(name = "TIME_VALUE_78")
	public Integer timeValue78;

	@Column(name = "TIME_VALUE_79")
	public Integer timeValue79;

	@Column(name = "TIME_VALUE_80")
	public Integer timeValue80;

	@Column(name = "TIME_VALUE_81")
	public Integer timeValue81;

	@Column(name = "TIME_VALUE_82")
	public Integer timeValue82;

	@Column(name = "TIME_VALUE_83")
	public Integer timeValue83;

	@Column(name = "TIME_VALUE_84")
	public Integer timeValue84;

	@Column(name = "TIME_VALUE_85")
	public Integer timeValue85;

	@Column(name = "TIME_VALUE_86")
	public Integer timeValue86;

	@Column(name = "TIME_VALUE_87")
	public Integer timeValue87;

	@Column(name = "TIME_VALUE_88")
	public Integer timeValue88;

	@Column(name = "TIME_VALUE_89")
	public Integer timeValue89;

	@Column(name = "TIME_VALUE_90")
	public Integer timeValue90;

	@Column(name = "TIME_VALUE_91")
	public Integer timeValue91;

	@Column(name = "TIME_VALUE_92")
	public Integer timeValue92;

	@Column(name = "TIME_VALUE_93")
	public Integer timeValue93;

	@Column(name = "TIME_VALUE_94")
	public Integer timeValue94;

	@Column(name = "TIME_VALUE_95")
	public Integer timeValue95;

	@Column(name = "TIME_VALUE_96")
	public Integer timeValue96;

	@Column(name = "TIME_VALUE_97")
	public Integer timeValue97;

	@Column(name = "TIME_VALUE_98")
	public Integer timeValue98;

	@Column(name = "TIME_VALUE_99")
	public Integer timeValue99;

	@Column(name = "TIME_VALUE_100")
	public Integer timeValue100;

	@Column(name = "TIME_VALUE_101")
	public Integer timeValue101;

	@Column(name = "TIME_VALUE_102")
	public Integer timeValue102;

	@Column(name = "TIME_VALUE_103")
	public Integer timeValue103;

	@Column(name = "TIME_VALUE_104")
	public Integer timeValue104;

	@Column(name = "TIME_VALUE_105")
	public Integer timeValue105;

	@Column(name = "TIME_VALUE_106")
	public Integer timeValue106;

	@Column(name = "TIME_VALUE_107")
	public Integer timeValue107;

	@Column(name = "TIME_VALUE_108")
	public Integer timeValue108;

	@Column(name = "TIME_VALUE_109")
	public Integer timeValue109;

	@Column(name = "TIME_VALUE_110")
	public Integer timeValue110;

	@Column(name = "TIME_VALUE_111")
	public Integer timeValue111;

	@Column(name = "TIME_VALUE_112")
	public Integer timeValue112;

	@Column(name = "TIME_VALUE_113")
	public Integer timeValue113;

	@Column(name = "TIME_VALUE_114")
	public Integer timeValue114;

	@Column(name = "TIME_VALUE_115")
	public Integer timeValue115;

	@Column(name = "TIME_VALUE_116")
	public Integer timeValue116;

	@Column(name = "TIME_VALUE_117")
	public Integer timeValue117;

	@Column(name = "TIME_VALUE_118")
	public Integer timeValue118;

	@Column(name = "TIME_VALUE_119")
	public Integer timeValue119;

	@Column(name = "TIME_VALUE_120")
	public Integer timeValue120;

	@Column(name = "TIME_VALUE_121")
	public Integer timeValue121;

	@Column(name = "TIME_VALUE_122")
	public Integer timeValue122;

	@Column(name = "TIME_VALUE_123")
	public Integer timeValue123;

	@Column(name = "TIME_VALUE_124")
	public Integer timeValue124;

	@Column(name = "TIME_VALUE_125")
	public Integer timeValue125;

	@Column(name = "TIME_VALUE_126")
	public Integer timeValue126;

	@Column(name = "TIME_VALUE_127")
	public Integer timeValue127;

	@Column(name = "TIME_VALUE_128")
	public Integer timeValue128;

	@Column(name = "TIME_VALUE_129")
	public Integer timeValue129;

	@Column(name = "TIME_VALUE_130")
	public Integer timeValue130;

	@Column(name = "TIME_VALUE_131")
	public Integer timeValue131;

	@Column(name = "TIME_VALUE_132")
	public Integer timeValue132;

	@Column(name = "TIME_VALUE_133")
	public Integer timeValue133;

	@Column(name = "TIME_VALUE_134")
	public Integer timeValue134;

	@Column(name = "TIME_VALUE_135")
	public Integer timeValue135;

	@Column(name = "TIME_VALUE_136")
	public Integer timeValue136;

	@Column(name = "TIME_VALUE_137")
	public Integer timeValue137;

	@Column(name = "TIME_VALUE_138")
	public Integer timeValue138;

	@Column(name = "TIME_VALUE_139")
	public Integer timeValue139;

	@Column(name = "TIME_VALUE_140")
	public Integer timeValue140;

	@Column(name = "TIME_VALUE_141")
	public Integer timeValue141;

	@Column(name = "TIME_VALUE_142")
	public Integer timeValue142;

	@Column(name = "TIME_VALUE_143")
	public Integer timeValue143;

	@Column(name = "TIME_VALUE_144")
	public Integer timeValue144;

	@Column(name = "TIME_VALUE_145")
	public Integer timeValue145;

	@Column(name = "TIME_VALUE_146")
	public Integer timeValue146;

	@Column(name = "TIME_VALUE_147")
	public Integer timeValue147;

	@Column(name = "TIME_VALUE_148")
	public Integer timeValue148;

	@Column(name = "TIME_VALUE_149")
	public Integer timeValue149;

	@Column(name = "TIME_VALUE_150")
	public Integer timeValue150;

	/** 振替時間 - TIME_VALUE_11 */
	@Column(name = "TIME_VALUE_151")
	public Integer timeValue151;

	@Column(name = "TIME_VALUE_152")
	public Integer timeValue152;

	@Column(name = "TIME_VALUE_153")
	public Integer timeValue153;

	@Column(name = "TIME_VALUE_154")
	public Integer timeValue154;

	@Column(name = "TIME_VALUE_155")
	public Integer timeValue155;

	@Column(name = "TIME_VALUE_156")
	public Integer timeValue156;

	@Column(name = "TIME_VALUE_157")
	public Integer timeValue157;

	@Column(name = "TIME_VALUE_158")
	public Integer timeValue158;

	@Column(name = "TIME_VALUE_159")
	public Integer timeValue159;

	@Column(name = "TIME_VALUE_160")
	public Integer timeValue160;

	@Column(name = "TIME_VALUE_161")
	public Integer timeValue161;

	@Column(name = "TIME_VALUE_162")
	public Integer timeValue162;

	@Column(name = "TIME_VALUE_163")
	public Integer timeValue163;

	@Column(name = "TIME_VALUE_164")
	public Integer timeValue164;

	@Column(name = "TIME_VALUE_165")
	public Integer timeValue165;

	@Column(name = "TIME_VALUE_166")
	public Integer timeValue166;

	@Column(name = "TIME_VALUE_167")
	public Integer timeValue167;

	@Column(name = "TIME_VALUE_168")
	public Integer timeValue168;

	@Column(name = "TIME_VALUE_169")
	public Integer timeValue169;

	@Column(name = "TIME_VALUE_170")
	public Integer timeValue170;

	@Column(name = "TIME_VALUE_171")
	public Integer timeValue171;

	@Column(name = "TIME_VALUE_172")
	public Integer timeValue172;

	@Column(name = "TIME_VALUE_173")
	public Integer timeValue173;

	@Column(name = "TIME_VALUE_174")
	public Integer timeValue174;

	@Column(name = "TIME_VALUE_175")
	public Integer timeValue175;

	@Column(name = "TIME_VALUE_176")
	public Integer timeValue176;

	@Column(name = "TIME_VALUE_177")
	public Integer timeValue177;

	@Column(name = "TIME_VALUE_178")
	public Integer timeValue178;

	@Column(name = "TIME_VALUE_179")
	public Integer timeValue179;

	@Column(name = "TIME_VALUE_180")
	public Integer timeValue180;

	@Column(name = "TIME_VALUE_181")
	public Integer timeValue181;

	@Column(name = "TIME_VALUE_182")
	public Integer timeValue182;

	@Column(name = "TIME_VALUE_183")
	public Integer timeValue183;

	@Column(name = "TIME_VALUE_184")
	public Integer timeValue184;

	@Column(name = "TIME_VALUE_185")
	public Integer timeValue185;

	@Column(name = "TIME_VALUE_186")
	public Integer timeValue186;

	@Column(name = "TIME_VALUE_187")
	public Integer timeValue187;

	@Column(name = "TIME_VALUE_188")
	public Integer timeValue188;

	@Column(name = "TIME_VALUE_189")
	public Integer timeValue189;

	@Column(name = "TIME_VALUE_190")
	public Integer timeValue190;

	@Column(name = "TIME_VALUE_191")
	public Integer timeValue191;

	@Column(name = "TIME_VALUE_192")
	public Integer timeValue192;

	@Column(name = "TIME_VALUE_193")
	public Integer timeValue193;

	@Column(name = "TIME_VALUE_194")
	public Integer timeValue194;

	@Column(name = "TIME_VALUE_195")
	public Integer timeValue195;

	@Column(name = "TIME_VALUE_196")
	public Integer timeValue196;

	@Column(name = "TIME_VALUE_197")
	public Integer timeValue197;

	@Column(name = "TIME_VALUE_198")
	public Integer timeValue198;

	@Column(name = "TIME_VALUE_199")
	public Integer timeValue199;

	@Column(name = "TIME_VALUE_200")
	public Integer timeValue200;

	/** 回数 */
	@Column(name = "COUNT_VALUE_1")
	public Double countValue1;

	@Column(name = "COUNT_VALUE_2")
	public Double countValue2;

	@Column(name = "COUNT_VALUE_3")
	public Double countValue3;

	@Column(name = "COUNT_VALUE_4")
	public Double countValue4;

	@Column(name = "COUNT_VALUE_5")
	public Double countValue5;

	@Column(name = "COUNT_VALUE_6")
	public Double countValue6;

	@Column(name = "COUNT_VALUE_7")
	public Double countValue7;

	@Column(name = "COUNT_VALUE_8")
	public Double countValue8;

	@Column(name = "COUNT_VALUE_9")
	public Double countValue9;

	@Column(name = "COUNT_VALUE_10")
	public Double countValue10;

	@Column(name = "COUNT_VALUE_11")
	public Double countValue11;

	@Column(name = "COUNT_VALUE_12")
	public Double countValue12;

	@Column(name = "COUNT_VALUE_13")
	public Double countValue13;

	@Column(name = "COUNT_VALUE_14")
	public Double countValue14;

	@Column(name = "COUNT_VALUE_15")
	public Double countValue15;

	@Column(name = "COUNT_VALUE_16")
	public Double countValue16;

	@Column(name = "COUNT_VALUE_17")
	public Double countValue17;

	@Column(name = "COUNT_VALUE_18")
	public Double countValue18;

	@Column(name = "COUNT_VALUE_19")
	public Double countValue19;

	@Column(name = "COUNT_VALUE_20")
	public Double countValue20;

	@Column(name = "COUNT_VALUE_21")
	public Double countValue21;

	@Column(name = "COUNT_VALUE_22")
	public Double countValue22;

	@Column(name = "COUNT_VALUE_23")
	public Double countValue23;

	@Column(name = "COUNT_VALUE_24")
	public Double countValue24;

	@Column(name = "COUNT_VALUE_25")
	public Double countValue25;

	@Column(name = "COUNT_VALUE_26")
	public Double countValue26;

	@Column(name = "COUNT_VALUE_27")
	public Double countValue27;

	@Column(name = "COUNT_VALUE_28")
	public Double countValue28;

	@Column(name = "COUNT_VALUE_29")
	public Double countValue29;

	@Column(name = "COUNT_VALUE_30")
	public Double countValue30;

	@Column(name = "COUNT_VALUE_31")
	public Double countValue31;

	@Column(name = "COUNT_VALUE_32")
	public Double countValue32;

	@Column(name = "COUNT_VALUE_33")
	public Double countValue33;

	@Column(name = "COUNT_VALUE_34")
	public Double countValue34;

	@Column(name = "COUNT_VALUE_35")
	public Double countValue35;

	@Column(name = "COUNT_VALUE_36")
	public Double countValue36;

	@Column(name = "COUNT_VALUE_37")
	public Double countValue37;

	@Column(name = "COUNT_VALUE_38")
	public Double countValue38;

	@Column(name = "COUNT_VALUE_39")
	public Double countValue39;

	@Column(name = "COUNT_VALUE_40")
	public Double countValue40;

	@Column(name = "COUNT_VALUE_41")
	public Double countValue41;

	@Column(name = "COUNT_VALUE_42")
	public Double countValue42;

	@Column(name = "COUNT_VALUE_43")
	public Double countValue43;

	@Column(name = "COUNT_VALUE_44")
	public Double countValue44;

	@Column(name = "COUNT_VALUE_45")
	public Double countValue45;

	@Column(name = "COUNT_VALUE_46")
	public Double countValue46;

	@Column(name = "COUNT_VALUE_47")
	public Double countValue47;

	@Column(name = "COUNT_VALUE_48")
	public Double countValue48;

	@Column(name = "COUNT_VALUE_49")
	public Double countValue49;

	@Column(name = "COUNT_VALUE_50")
	public Double countValue50;

	/** 振替時間 - COUNT_VALUE_1 */
	@Column(name = "COUNT_VALUE_51")
	public Double countValue51;

	@Column(name = "COUNT_VALUE_52")
	public Double countValue52;

	@Column(name = "COUNT_VALUE_53")
	public Double countValue53;

	@Column(name = "COUNT_VALUE_54")
	public Double countValue54;

	@Column(name = "COUNT_VALUE_55")
	public Double countValue55;

	@Column(name = "COUNT_VALUE_56")
	public Double countValue56;

	@Column(name = "COUNT_VALUE_57")
	public Double countValue57;

	@Column(name = "COUNT_VALUE_58")
	public Double countValue58;

	@Column(name = "COUNT_VALUE_59")
	public Double countValue59;

	@Column(name = "COUNT_VALUE_60")
	public Double countValue60;

	@Column(name = "COUNT_VALUE_61")
	public Double countValue61;

	@Column(name = "COUNT_VALUE_62")
	public Double countValue62;

	@Column(name = "COUNT_VALUE_63")
	public Double countValue63;

	@Column(name = "COUNT_VALUE_64")
	public Double countValue64;

	@Column(name = "COUNT_VALUE_65")
	public Double countValue65;

	@Column(name = "COUNT_VALUE_66")
	public Double countValue66;

	@Column(name = "COUNT_VALUE_67")
	public Double countValue67;

	@Column(name = "COUNT_VALUE_68")
	public Double countValue68;

	@Column(name = "COUNT_VALUE_69")
	public Double countValue69;

	@Column(name = "COUNT_VALUE_70")
	public Double countValue70;

	@Column(name = "COUNT_VALUE_71")
	public Double countValue71;

	@Column(name = "COUNT_VALUE_72")
	public Double countValue72;

	@Column(name = "COUNT_VALUE_73")
	public Double countValue73;

	@Column(name = "COUNT_VALUE_74")
	public Double countValue74;

	@Column(name = "COUNT_VALUE_75")
	public Double countValue75;

	@Column(name = "COUNT_VALUE_76")
	public Double countValue76;

	@Column(name = "COUNT_VALUE_77")
	public Double countValue77;

	@Column(name = "COUNT_VALUE_78")
	public Double countValue78;

	@Column(name = "COUNT_VALUE_79")
	public Double countValue79;

	@Column(name = "COUNT_VALUE_80")
	public Double countValue80;

	@Column(name = "COUNT_VALUE_81")
	public Double countValue81;

	@Column(name = "COUNT_VALUE_82")
	public Double countValue82;

	@Column(name = "COUNT_VALUE_83")
	public Double countValue83;

	@Column(name = "COUNT_VALUE_84")
	public Double countValue84;

	@Column(name = "COUNT_VALUE_85")
	public Double countValue85;

	@Column(name = "COUNT_VALUE_86")
	public Double countValue86;

	@Column(name = "COUNT_VALUE_87")
	public Double countValue87;

	@Column(name = "COUNT_VALUE_88")
	public Double countValue88;

	@Column(name = "COUNT_VALUE_89")
	public Double countValue89;

	@Column(name = "COUNT_VALUE_90")
	public Double countValue90;

	@Column(name = "COUNT_VALUE_91")
	public Double countValue91;

	@Column(name = "COUNT_VALUE_92")
	public Double countValue92;

	@Column(name = "COUNT_VALUE_93")
	public Double countValue93;

	@Column(name = "COUNT_VALUE_94")
	public Double countValue94;

	@Column(name = "COUNT_VALUE_95")
	public Double countValue95;

	@Column(name = "COUNT_VALUE_96")
	public Double countValue96;

	@Column(name = "COUNT_VALUE_97")
	public Double countValue97;

	@Column(name = "COUNT_VALUE_98")
	public Double countValue98;

	@Column(name = "COUNT_VALUE_99")
	public Double countValue99;

	@Column(name = "COUNT_VALUE_100")
	public Double countValue100;

	@Column(name = "COUNT_VALUE_101")
	public Double countValue101;

	@Column(name = "COUNT_VALUE_102")
	public Double countValue102;

	@Column(name = "COUNT_VALUE_103")
	public Double countValue103;

	@Column(name = "COUNT_VALUE_104")
	public Double countValue104;

	@Column(name = "COUNT_VALUE_105")
	public Double countValue105;

	@Column(name = "COUNT_VALUE_106")
	public Double countValue106;

	@Column(name = "COUNT_VALUE_107")
	public Double countValue107;

	@Column(name = "COUNT_VALUE_108")
	public Double countValue108;

	@Column(name = "COUNT_VALUE_109")
	public Double countValue109;

	@Column(name = "COUNT_VALUE_110")
	public Double countValue110;

	@Column(name = "COUNT_VALUE_111")
	public Double countValue111;

	@Column(name = "COUNT_VALUE_112")
	public Double countValue112;

	@Column(name = "COUNT_VALUE_113")
	public Double countValue113;

	@Column(name = "COUNT_VALUE_114")
	public Double countValue114;

	@Column(name = "COUNT_VALUE_115")
	public Double countValue115;

	@Column(name = "COUNT_VALUE_116")
	public Double countValue116;

	@Column(name = "COUNT_VALUE_117")
	public Double countValue117;

	@Column(name = "COUNT_VALUE_118")
	public Double countValue118;

	@Column(name = "COUNT_VALUE_119")
	public Double countValue119;

	@Column(name = "COUNT_VALUE_120")
	public Double countValue120;

	@Column(name = "COUNT_VALUE_121")
	public Double countValue121;

	@Column(name = "COUNT_VALUE_122")
	public Double countValue122;

	@Column(name = "COUNT_VALUE_123")
	public Double countValue123;

	@Column(name = "COUNT_VALUE_124")
	public Double countValue124;

	@Column(name = "COUNT_VALUE_125")
	public Double countValue125;

	@Column(name = "COUNT_VALUE_126")
	public Double countValue126;

	@Column(name = "COUNT_VALUE_127")
	public Double countValue127;

	@Column(name = "COUNT_VALUE_128")
	public Double countValue128;

	@Column(name = "COUNT_VALUE_129")
	public Double countValue129;

	@Column(name = "COUNT_VALUE_130")
	public Double countValue130;

	@Column(name = "COUNT_VALUE_131")
	public Double countValue131;

	@Column(name = "COUNT_VALUE_132")
	public Double countValue132;

	@Column(name = "COUNT_VALUE_133")
	public Double countValue133;

	@Column(name = "COUNT_VALUE_134")
	public Double countValue134;

	@Column(name = "COUNT_VALUE_135")
	public Double countValue135;

	@Column(name = "COUNT_VALUE_136")
	public Double countValue136;

	@Column(name = "COUNT_VALUE_137")
	public Double countValue137;

	@Column(name = "COUNT_VALUE_138")
	public Double countValue138;

	@Column(name = "COUNT_VALUE_139")
	public Double countValue139;

	@Column(name = "COUNT_VALUE_140")
	public Double countValue140;

	@Column(name = "COUNT_VALUE_141")
	public Double countValue141;

	@Column(name = "COUNT_VALUE_142")
	public Double countValue142;

	@Column(name = "COUNT_VALUE_143")
	public Double countValue143;

	@Column(name = "COUNT_VALUE_144")
	public Double countValue144;

	@Column(name = "COUNT_VALUE_145")
	public Double countValue145;

	@Column(name = "COUNT_VALUE_146")
	public Double countValue146;

	@Column(name = "COUNT_VALUE_147")
	public Double countValue147;

	@Column(name = "COUNT_VALUE_148")
	public Double countValue148;

	@Column(name = "COUNT_VALUE_149")
	public Double countValue149;

	@Column(name = "COUNT_VALUE_150")
	public Double countValue150;

	/** 振替時間 - COUNT_VALUE_11 */
	@Column(name = "COUNT_VALUE_151")
	public Double countValue151;

	@Column(name = "COUNT_VALUE_152")
	public Double countValue152;

	@Column(name = "COUNT_VALUE_153")
	public Double countValue153;

	@Column(name = "COUNT_VALUE_154")
	public Double countValue154;

	@Column(name = "COUNT_VALUE_155")
	public Double countValue155;

	@Column(name = "COUNT_VALUE_156")
	public Double countValue156;

	@Column(name = "COUNT_VALUE_157")
	public Double countValue157;

	@Column(name = "COUNT_VALUE_158")
	public Double countValue158;

	@Column(name = "COUNT_VALUE_159")
	public Double countValue159;

	@Column(name = "COUNT_VALUE_160")
	public Double countValue160;

	@Column(name = "COUNT_VALUE_161")
	public Double countValue161;

	@Column(name = "COUNT_VALUE_162")
	public Double countValue162;

	@Column(name = "COUNT_VALUE_163")
	public Double countValue163;

	@Column(name = "COUNT_VALUE_164")
	public Double countValue164;

	@Column(name = "COUNT_VALUE_165")
	public Double countValue165;

	@Column(name = "COUNT_VALUE_166")
	public Double countValue166;

	@Column(name = "COUNT_VALUE_167")
	public Double countValue167;

	@Column(name = "COUNT_VALUE_168")
	public Double countValue168;

	@Column(name = "COUNT_VALUE_169")
	public Double countValue169;

	@Column(name = "COUNT_VALUE_170")
	public Double countValue170;

	@Column(name = "COUNT_VALUE_171")
	public Double countValue171;

	@Column(name = "COUNT_VALUE_172")
	public Double countValue172;

	@Column(name = "COUNT_VALUE_173")
	public Double countValue173;

	@Column(name = "COUNT_VALUE_174")
	public Double countValue174;

	@Column(name = "COUNT_VALUE_175")
	public Double countValue175;

	@Column(name = "COUNT_VALUE_176")
	public Double countValue176;

	@Column(name = "COUNT_VALUE_177")
	public Double countValue177;

	@Column(name = "COUNT_VALUE_178")
	public Double countValue178;

	@Column(name = "COUNT_VALUE_179")
	public Double countValue179;

	@Column(name = "COUNT_VALUE_180")
	public Double countValue180;

	@Column(name = "COUNT_VALUE_181")
	public Double countValue181;

	@Column(name = "COUNT_VALUE_182")
	public Double countValue182;

	@Column(name = "COUNT_VALUE_183")
	public Double countValue183;

	@Column(name = "COUNT_VALUE_184")
	public Double countValue184;

	@Column(name = "COUNT_VALUE_185")
	public Double countValue185;

	@Column(name = "COUNT_VALUE_186")
	public Double countValue186;

	@Column(name = "COUNT_VALUE_187")
	public Double countValue187;

	@Column(name = "COUNT_VALUE_188")
	public Double countValue188;

	@Column(name = "COUNT_VALUE_189")
	public Double countValue189;

	@Column(name = "COUNT_VALUE_190")
	public Double countValue190;

	@Column(name = "COUNT_VALUE_191")
	public Double countValue191;

	@Column(name = "COUNT_VALUE_192")
	public Double countValue192;

	@Column(name = "COUNT_VALUE_193")
	public Double countValue193;

	@Column(name = "COUNT_VALUE_194")
	public Double countValue194;

	@Column(name = "COUNT_VALUE_195")
	public Double countValue195;

	@Column(name = "COUNT_VALUE_196")
	public Double countValue196;

	@Column(name = "COUNT_VALUE_197")
	public Double countValue197;

	@Column(name = "COUNT_VALUE_198")
	public Double countValue198;

	@Column(name = "COUNT_VALUE_199")
	public Double countValue199;

	@Column(name = "COUNT_VALUE_200")
	public Double countValue200;

	/** 金額 */
	@Column(name = "MONEY_VALUE_1")
	public Integer moneyValue1;

	@Column(name = "MONEY_VALUE_2")
	public Integer moneyValue2;

	@Column(name = "MONEY_VALUE_3")
	public Integer moneyValue3;

	@Column(name = "MONEY_VALUE_4")
	public Integer moneyValue4;

	@Column(name = "MONEY_VALUE_5")
	public Integer moneyValue5;

	@Column(name = "MONEY_VALUE_6")
	public Integer moneyValue6;

	@Column(name = "MONEY_VALUE_7")
	public Integer moneyValue7;

	@Column(name = "MONEY_VALUE_8")
	public Integer moneyValue8;

	@Column(name = "MONEY_VALUE_9")
	public Integer moneyValue9;

	@Column(name = "MONEY_VALUE_10")
	public Integer moneyValue10;

	@Column(name = "MONEY_VALUE_11")
	public Integer moneyValue11;

	@Column(name = "MONEY_VALUE_12")
	public Integer moneyValue12;

	@Column(name = "MONEY_VALUE_13")
	public Integer moneyValue13;

	@Column(name = "MONEY_VALUE_14")
	public Integer moneyValue14;

	@Column(name = "MONEY_VALUE_15")
	public Integer moneyValue15;

	@Column(name = "MONEY_VALUE_16")
	public Integer moneyValue16;

	@Column(name = "MONEY_VALUE_17")
	public Integer moneyValue17;

	@Column(name = "MONEY_VALUE_18")
	public Integer moneyValue18;

	@Column(name = "MONEY_VALUE_19")
	public Integer moneyValue19;

	@Column(name = "MONEY_VALUE_20")
	public Integer moneyValue20;

	@Column(name = "MONEY_VALUE_21")
	public Integer moneyValue21;

	@Column(name = "MONEY_VALUE_22")
	public Integer moneyValue22;

	@Column(name = "MONEY_VALUE_23")
	public Integer moneyValue23;

	@Column(name = "MONEY_VALUE_24")
	public Integer moneyValue24;

	@Column(name = "MONEY_VALUE_25")
	public Integer moneyValue25;

	@Column(name = "MONEY_VALUE_26")
	public Integer moneyValue26;

	@Column(name = "MONEY_VALUE_27")
	public Integer moneyValue27;

	@Column(name = "MONEY_VALUE_28")
	public Integer moneyValue28;

	@Column(name = "MONEY_VALUE_29")
	public Integer moneyValue29;

	@Column(name = "MONEY_VALUE_30")
	public Integer moneyValue30;

	@Column(name = "MONEY_VALUE_31")
	public Integer moneyValue31;

	@Column(name = "MONEY_VALUE_32")
	public Integer moneyValue32;

	@Column(name = "MONEY_VALUE_33")
	public Integer moneyValue33;

	@Column(name = "MONEY_VALUE_34")
	public Integer moneyValue34;

	@Column(name = "MONEY_VALUE_35")
	public Integer moneyValue35;

	@Column(name = "MONEY_VALUE_36")
	public Integer moneyValue36;

	@Column(name = "MONEY_VALUE_37")
	public Integer moneyValue37;

	@Column(name = "MONEY_VALUE_38")
	public Integer moneyValue38;

	@Column(name = "MONEY_VALUE_39")
	public Integer moneyValue39;

	@Column(name = "MONEY_VALUE_40")
	public Integer moneyValue40;

	@Column(name = "MONEY_VALUE_41")
	public Integer moneyValue41;

	@Column(name = "MONEY_VALUE_42")
	public Integer moneyValue42;

	@Column(name = "MONEY_VALUE_43")
	public Integer moneyValue43;

	@Column(name = "MONEY_VALUE_44")
	public Integer moneyValue44;

	@Column(name = "MONEY_VALUE_45")
	public Integer moneyValue45;

	@Column(name = "MONEY_VALUE_46")
	public Integer moneyValue46;

	@Column(name = "MONEY_VALUE_47")
	public Integer moneyValue47;

	@Column(name = "MONEY_VALUE_48")
	public Integer moneyValue48;

	@Column(name = "MONEY_VALUE_49")
	public Integer moneyValue49;

	@Column(name = "MONEY_VALUE_50")
	public Integer moneyValue50;

	/** 振替時間 - MONEY_VALUE_1 */
	@Column(name = "MONEY_VALUE_51")
	public Integer moneyValue51;

	@Column(name = "MONEY_VALUE_52")
	public Integer moneyValue52;

	@Column(name = "MONEY_VALUE_53")
	public Integer moneyValue53;

	@Column(name = "MONEY_VALUE_54")
	public Integer moneyValue54;

	@Column(name = "MONEY_VALUE_55")
	public Integer moneyValue55;

	@Column(name = "MONEY_VALUE_56")
	public Integer moneyValue56;

	@Column(name = "MONEY_VALUE_57")
	public Integer moneyValue57;

	@Column(name = "MONEY_VALUE_58")
	public Integer moneyValue58;

	@Column(name = "MONEY_VALUE_59")
	public Integer moneyValue59;

	@Column(name = "MONEY_VALUE_60")
	public Integer moneyValue60;

	@Column(name = "MONEY_VALUE_61")
	public Integer moneyValue61;

	@Column(name = "MONEY_VALUE_62")
	public Integer moneyValue62;

	@Column(name = "MONEY_VALUE_63")
	public Integer moneyValue63;

	@Column(name = "MONEY_VALUE_64")
	public Integer moneyValue64;

	@Column(name = "MONEY_VALUE_65")
	public Integer moneyValue65;

	@Column(name = "MONEY_VALUE_66")
	public Integer moneyValue66;

	@Column(name = "MONEY_VALUE_67")
	public Integer moneyValue67;

	@Column(name = "MONEY_VALUE_68")
	public Integer moneyValue68;

	@Column(name = "MONEY_VALUE_69")
	public Integer moneyValue69;

	@Column(name = "MONEY_VALUE_70")
	public Integer moneyValue70;

	@Column(name = "MONEY_VALUE_71")
	public Integer moneyValue71;

	@Column(name = "MONEY_VALUE_72")
	public Integer moneyValue72;

	@Column(name = "MONEY_VALUE_73")
	public Integer moneyValue73;

	@Column(name = "MONEY_VALUE_74")
	public Integer moneyValue74;

	@Column(name = "MONEY_VALUE_75")
	public Integer moneyValue75;

	@Column(name = "MONEY_VALUE_76")
	public Integer moneyValue76;

	@Column(name = "MONEY_VALUE_77")
	public Integer moneyValue77;

	@Column(name = "MONEY_VALUE_78")
	public Integer moneyValue78;

	@Column(name = "MONEY_VALUE_79")
	public Integer moneyValue79;

	@Column(name = "MONEY_VALUE_80")
	public Integer moneyValue80;

	@Column(name = "MONEY_VALUE_81")
	public Integer moneyValue81;

	@Column(name = "MONEY_VALUE_82")
	public Integer moneyValue82;

	@Column(name = "MONEY_VALUE_83")
	public Integer moneyValue83;

	@Column(name = "MONEY_VALUE_84")
	public Integer moneyValue84;

	@Column(name = "MONEY_VALUE_85")
	public Integer moneyValue85;

	@Column(name = "MONEY_VALUE_86")
	public Integer moneyValue86;

	@Column(name = "MONEY_VALUE_87")
	public Integer moneyValue87;

	@Column(name = "MONEY_VALUE_88")
	public Integer moneyValue88;

	@Column(name = "MONEY_VALUE_89")
	public Integer moneyValue89;

	@Column(name = "MONEY_VALUE_90")
	public Integer moneyValue90;

	@Column(name = "MONEY_VALUE_91")
	public Integer moneyValue91;

	@Column(name = "MONEY_VALUE_92")
	public Integer moneyValue92;

	@Column(name = "MONEY_VALUE_93")
	public Integer moneyValue93;

	@Column(name = "MONEY_VALUE_94")
	public Integer moneyValue94;

	@Column(name = "MONEY_VALUE_95")
	public Integer moneyValue95;

	@Column(name = "MONEY_VALUE_96")
	public Integer moneyValue96;

	@Column(name = "MONEY_VALUE_97")
	public Integer moneyValue97;

	@Column(name = "MONEY_VALUE_98")
	public Integer moneyValue98;

	@Column(name = "MONEY_VALUE_99")
	public Integer moneyValue99;

	@Column(name = "MONEY_VALUE_100")
	public Integer moneyValue100;

	@Column(name = "MONEY_VALUE_101")
	public Integer moneyValue101;

	@Column(name = "MONEY_VALUE_102")
	public Integer moneyValue102;

	@Column(name = "MONEY_VALUE_103")
	public Integer moneyValue103;

	@Column(name = "MONEY_VALUE_104")
	public Integer moneyValue104;

	@Column(name = "MONEY_VALUE_105")
	public Integer moneyValue105;

	@Column(name = "MONEY_VALUE_106")
	public Integer moneyValue106;

	@Column(name = "MONEY_VALUE_107")
	public Integer moneyValue107;

	@Column(name = "MONEY_VALUE_108")
	public Integer moneyValue108;

	@Column(name = "MONEY_VALUE_109")
	public Integer moneyValue109;

	@Column(name = "MONEY_VALUE_110")
	public Integer moneyValue110;

	@Column(name = "MONEY_VALUE_111")
	public Integer moneyValue111;

	@Column(name = "MONEY_VALUE_112")
	public Integer moneyValue112;

	@Column(name = "MONEY_VALUE_113")
	public Integer moneyValue113;

	@Column(name = "MONEY_VALUE_114")
	public Integer moneyValue114;

	@Column(name = "MONEY_VALUE_115")
	public Integer moneyValue115;

	@Column(name = "MONEY_VALUE_116")
	public Integer moneyValue116;

	@Column(name = "MONEY_VALUE_117")
	public Integer moneyValue117;

	@Column(name = "MONEY_VALUE_118")
	public Integer moneyValue118;

	@Column(name = "MONEY_VALUE_119")
	public Integer moneyValue119;

	@Column(name = "MONEY_VALUE_120")
	public Integer moneyValue120;

	@Column(name = "MONEY_VALUE_121")
	public Integer moneyValue121;

	@Column(name = "MONEY_VALUE_122")
	public Integer moneyValue122;

	@Column(name = "MONEY_VALUE_123")
	public Integer moneyValue123;

	@Column(name = "MONEY_VALUE_124")
	public Integer moneyValue124;

	@Column(name = "MONEY_VALUE_125")
	public Integer moneyValue125;

	@Column(name = "MONEY_VALUE_126")
	public Integer moneyValue126;

	@Column(name = "MONEY_VALUE_127")
	public Integer moneyValue127;

	@Column(name = "MONEY_VALUE_128")
	public Integer moneyValue128;

	@Column(name = "MONEY_VALUE_129")
	public Integer moneyValue129;

	@Column(name = "MONEY_VALUE_130")
	public Integer moneyValue130;

	@Column(name = "MONEY_VALUE_131")
	public Integer moneyValue131;

	@Column(name = "MONEY_VALUE_132")
	public Integer moneyValue132;

	@Column(name = "MONEY_VALUE_133")
	public Integer moneyValue133;

	@Column(name = "MONEY_VALUE_134")
	public Integer moneyValue134;

	@Column(name = "MONEY_VALUE_135")
	public Integer moneyValue135;

	@Column(name = "MONEY_VALUE_136")
	public Integer moneyValue136;

	@Column(name = "MONEY_VALUE_137")
	public Integer moneyValue137;

	@Column(name = "MONEY_VALUE_138")
	public Integer moneyValue138;

	@Column(name = "MONEY_VALUE_139")
	public Integer moneyValue139;

	@Column(name = "MONEY_VALUE_140")
	public Integer moneyValue140;

	@Column(name = "MONEY_VALUE_141")
	public Integer moneyValue141;

	@Column(name = "MONEY_VALUE_142")
	public Integer moneyValue142;

	@Column(name = "MONEY_VALUE_143")
	public Integer moneyValue143;

	@Column(name = "MONEY_VALUE_144")
	public Integer moneyValue144;

	@Column(name = "MONEY_VALUE_145")
	public Integer moneyValue145;

	@Column(name = "MONEY_VALUE_146")
	public Integer moneyValue146;

	@Column(name = "MONEY_VALUE_147")
	public Integer moneyValue147;

	@Column(name = "MONEY_VALUE_148")
	public Integer moneyValue148;

	@Column(name = "MONEY_VALUE_149")
	public Integer moneyValue149;

	@Column(name = "MONEY_VALUE_150")
	public Integer moneyValue150;

	/** 振替時間 - MONEY_VALUE_11 */
	@Column(name = "MONEY_VALUE_151")
	public Integer moneyValue151;

	@Column(name = "MONEY_VALUE_152")
	public Integer moneyValue152;

	@Column(name = "MONEY_VALUE_153")
	public Integer moneyValue153;

	@Column(name = "MONEY_VALUE_154")
	public Integer moneyValue154;

	@Column(name = "MONEY_VALUE_155")
	public Integer moneyValue155;

	@Column(name = "MONEY_VALUE_156")
	public Integer moneyValue156;

	@Column(name = "MONEY_VALUE_157")
	public Integer moneyValue157;

	@Column(name = "MONEY_VALUE_158")
	public Integer moneyValue158;

	@Column(name = "MONEY_VALUE_159")
	public Integer moneyValue159;

	@Column(name = "MONEY_VALUE_160")
	public Integer moneyValue160;

	@Column(name = "MONEY_VALUE_161")
	public Integer moneyValue161;

	@Column(name = "MONEY_VALUE_162")
	public Integer moneyValue162;

	@Column(name = "MONEY_VALUE_163")
	public Integer moneyValue163;

	@Column(name = "MONEY_VALUE_164")
	public Integer moneyValue164;

	@Column(name = "MONEY_VALUE_165")
	public Integer moneyValue165;

	@Column(name = "MONEY_VALUE_166")
	public Integer moneyValue166;

	@Column(name = "MONEY_VALUE_167")
	public Integer moneyValue167;

	@Column(name = "MONEY_VALUE_168")
	public Integer moneyValue168;

	@Column(name = "MONEY_VALUE_169")
	public Integer moneyValue169;

	@Column(name = "MONEY_VALUE_170")
	public Integer moneyValue170;

	@Column(name = "MONEY_VALUE_171")
	public Integer moneyValue171;

	@Column(name = "MONEY_VALUE_172")
	public Integer moneyValue172;

	@Column(name = "MONEY_VALUE_173")
	public Integer moneyValue173;

	@Column(name = "MONEY_VALUE_174")
	public Integer moneyValue174;

	@Column(name = "MONEY_VALUE_175")
	public Integer moneyValue175;

	@Column(name = "MONEY_VALUE_176")
	public Integer moneyValue176;

	@Column(name = "MONEY_VALUE_177")
	public Integer moneyValue177;

	@Column(name = "MONEY_VALUE_178")
	public Integer moneyValue178;

	@Column(name = "MONEY_VALUE_179")
	public Integer moneyValue179;

	@Column(name = "MONEY_VALUE_180")
	public Integer moneyValue180;

	@Column(name = "MONEY_VALUE_181")
	public Integer moneyValue181;

	@Column(name = "MONEY_VALUE_182")
	public Integer moneyValue182;

	@Column(name = "MONEY_VALUE_183")
	public Integer moneyValue183;

	@Column(name = "MONEY_VALUE_184")
	public Integer moneyValue184;

	@Column(name = "MONEY_VALUE_185")
	public Integer moneyValue185;

	@Column(name = "MONEY_VALUE_186")
	public Integer moneyValue186;

	@Column(name = "MONEY_VALUE_187")
	public Integer moneyValue187;

	@Column(name = "MONEY_VALUE_188")
	public Integer moneyValue188;

	@Column(name = "MONEY_VALUE_189")
	public Integer moneyValue189;

	@Column(name = "MONEY_VALUE_190")
	public Integer moneyValue190;

	@Column(name = "MONEY_VALUE_191")
	public Integer moneyValue191;

	@Column(name = "MONEY_VALUE_192")
	public Integer moneyValue192;

	@Column(name = "MONEY_VALUE_193")
	public Integer moneyValue193;

	@Column(name = "MONEY_VALUE_194")
	public Integer moneyValue194;

	@Column(name = "MONEY_VALUE_195")
	public Integer moneyValue195;

	@Column(name = "MONEY_VALUE_196")
	public Integer moneyValue196;

	@Column(name = "MONEY_VALUE_197")
	public Integer moneyValue197;

	@Column(name = "MONEY_VALUE_198")
	public Integer moneyValue198;

	@Column(name = "MONEY_VALUE_199")
	public Integer moneyValue199;

	@Column(name = "MONEY_VALUE_200")
	public Integer moneyValue200;

	@Override
	protected Object getKey() {
		return krcdtDayTimePk;
	}
	
	public void clearAllValues(){
		for (int i = 1; i <= 200; i++){
			AnyItemValue emptyValue = new AnyItemValue(
					new AnyItemNo(i), Optional.empty(), Optional.empty(), Optional.empty());
			this.toEntityAnyItemValue(emptyValue);
		}
	}
	
	public void toEntityAnyItemValueOfDaily(AnyItemValueOfDaily domain){
		for (AnyItemValue item : domain.getItems()){
			this.toEntityAnyItemValue(item);
		}
	}
	
	public void toEntityAnyItemValue(AnyItemValue domain) {
		switch(domain.getItemNo().v()) {
		case 1:
			this.timeValue1 = (domain.getTime().isPresent() ? domain.getTime().get().v() : null);
			this.countValue1 = (domain.getTimes().isPresent() ? domain.getTimes().get().v().doubleValue() : null);
			this.moneyValue1 = (domain.getAmount().isPresent() ? domain.getAmount().get().v() : null);
			break;
		case 2:
			this.timeValue2 = (domain.getTime().isPresent() ? domain.getTime().get().v() : null);
			this.countValue2 = (domain.getTimes().isPresent() ? domain.getTimes().get().v().doubleValue() : null);
			this.moneyValue2 = (domain.getAmount().isPresent() ? domain.getAmount().get().v() : null);
			break;
		case 3:
			this.timeValue3 = (domain.getTime().isPresent() ? domain.getTime().get().v() : null);
			this.countValue3 = (domain.getTimes().isPresent() ? domain.getTimes().get().v().doubleValue() : null);
			this.moneyValue3 = (domain.getAmount().isPresent() ? domain.getAmount().get().v() : null);
			break;
		case 4:
			this.timeValue4 = (domain.getTime().isPresent() ? domain.getTime().get().v() : null);
			this.countValue4 = (domain.getTimes().isPresent() ? domain.getTimes().get().v().doubleValue() : null);
			this.moneyValue4 = (domain.getAmount().isPresent() ? domain.getAmount().get().v() : null);
			break;
		case 5:
			this.timeValue5 = (domain.getTime().isPresent() ? domain.getTime().get().v() : null);
			this.countValue5 = (domain.getTimes().isPresent() ? domain.getTimes().get().v().doubleValue() : null);
			this.moneyValue5 = (domain.getAmount().isPresent() ? domain.getAmount().get().v() : null);
			break;
		case 6:
			this.timeValue6 = (domain.getTime().isPresent() ? domain.getTime().get().v() : null);
			this.countValue6 = (domain.getTimes().isPresent() ? domain.getTimes().get().v().doubleValue() : null);
			this.moneyValue6 = (domain.getAmount().isPresent() ? domain.getAmount().get().v() : null);
			break;
		case 7:
			this.timeValue7 = (domain.getTime().isPresent() ? domain.getTime().get().v() : null);
			this.countValue7 = (domain.getTimes().isPresent() ? domain.getTimes().get().v().doubleValue() : null);
			this.moneyValue7 = (domain.getAmount().isPresent() ? domain.getAmount().get().v() : null);
			break;
		case 8:
			this.timeValue8 = (domain.getTime().isPresent() ? domain.getTime().get().v() : null);
			this.countValue8 = (domain.getTimes().isPresent() ? domain.getTimes().get().v().doubleValue() : null);
			this.moneyValue8 = (domain.getAmount().isPresent() ? domain.getAmount().get().v() : null);
			break;
		case 9:
			this.timeValue9 = (domain.getTime().isPresent() ? domain.getTime().get().v() : null);
			this.countValue9 = (domain.getTimes().isPresent() ? domain.getTimes().get().v().doubleValue() : null);
			this.moneyValue9 = (domain.getAmount().isPresent() ? domain.getAmount().get().v() : null);
			break;
		case 10:
			this.timeValue10 = (domain.getTime().isPresent() ? domain.getTime().get().v() : null);
			this.countValue10 = (domain.getTimes().isPresent() ? domain.getTimes().get().v().doubleValue() : null);
			this.moneyValue10 = (domain.getAmount().isPresent() ? domain.getAmount().get().v() : null);
			break;
		case 11:
			this.timeValue11 = (domain.getTime().isPresent() ? domain.getTime().get().v() : null);
			this.countValue11 = (domain.getTimes().isPresent() ? domain.getTimes().get().v().doubleValue() : null);
			this.moneyValue11 = (domain.getAmount().isPresent() ? domain.getAmount().get().v() : null);
			break;
		case 12:
			this.timeValue12 = (domain.getTime().isPresent() ? domain.getTime().get().v() : null);
			this.countValue12 = (domain.getTimes().isPresent() ? domain.getTimes().get().v().doubleValue() : null);
			this.moneyValue12 = (domain.getAmount().isPresent() ? domain.getAmount().get().v() : null);
			break;
		case 13:
			this.timeValue13 = (domain.getTime().isPresent() ? domain.getTime().get().v() : null);
			this.countValue13 = (domain.getTimes().isPresent() ? domain.getTimes().get().v().doubleValue() : null);
			this.moneyValue13 = (domain.getAmount().isPresent() ? domain.getAmount().get().v() : null);
			break;
		case 14:
			this.timeValue14 = (domain.getTime().isPresent() ? domain.getTime().get().v() : null);
			this.countValue14 = (domain.getTimes().isPresent() ? domain.getTimes().get().v().doubleValue() : null);
			this.moneyValue14 = (domain.getAmount().isPresent() ? domain.getAmount().get().v() : null);
			break;
		case 15:
			this.timeValue15 = (domain.getTime().isPresent() ? domain.getTime().get().v() : null);
			this.countValue15 = (domain.getTimes().isPresent() ? domain.getTimes().get().v().doubleValue() : null);
			this.moneyValue15 = (domain.getAmount().isPresent() ? domain.getAmount().get().v() : null);
			break;
		case 16:
			this.timeValue16 = (domain.getTime().isPresent() ? domain.getTime().get().v() : null);
			this.countValue16 = (domain.getTimes().isPresent() ? domain.getTimes().get().v().doubleValue() : null);
			this.moneyValue16 = (domain.getAmount().isPresent() ? domain.getAmount().get().v() : null);
			break;
		case 17:
			this.timeValue17 = (domain.getTime().isPresent() ? domain.getTime().get().v() : null);
			this.countValue17 = (domain.getTimes().isPresent() ? domain.getTimes().get().v().doubleValue() : null);
			this.moneyValue17 = (domain.getAmount().isPresent() ? domain.getAmount().get().v() : null);
			break;
		case 18:
			this.timeValue18 = (domain.getTime().isPresent() ? domain.getTime().get().v() : null);
			this.countValue18 = (domain.getTimes().isPresent() ? domain.getTimes().get().v().doubleValue() : null);
			this.moneyValue18 = (domain.getAmount().isPresent() ? domain.getAmount().get().v() : null);
			break;
		case 19:
			this.timeValue19 = (domain.getTime().isPresent() ? domain.getTime().get().v() : null);
			this.countValue19 = (domain.getTimes().isPresent() ? domain.getTimes().get().v().doubleValue() : null);
			this.moneyValue19 = (domain.getAmount().isPresent() ? domain.getAmount().get().v() : null);
			break;
		case 20:
			this.timeValue20 = (domain.getTime().isPresent() ? domain.getTime().get().v() : null);
			this.countValue20 = (domain.getTimes().isPresent() ? domain.getTimes().get().v().doubleValue() : null);
			this.moneyValue20 = (domain.getAmount().isPresent() ? domain.getAmount().get().v() : null);
			break;
		case 21:
			this.timeValue21 = (domain.getTime().isPresent() ? domain.getTime().get().v() : null);
			this.countValue21 = (domain.getTimes().isPresent() ? domain.getTimes().get().v().doubleValue() : null);
			this.moneyValue21 = (domain.getAmount().isPresent() ? domain.getAmount().get().v() : null);
			break;
		case 22:
			this.timeValue22 = (domain.getTime().isPresent() ? domain.getTime().get().v() : null);
			this.countValue22 = (domain.getTimes().isPresent() ? domain.getTimes().get().v().doubleValue() : null);
			this.moneyValue22 = (domain.getAmount().isPresent() ? domain.getAmount().get().v() : null);
			break;
		case 23:
			this.timeValue23 = (domain.getTime().isPresent() ? domain.getTime().get().v() : null);
			this.countValue23 = (domain.getTimes().isPresent() ? domain.getTimes().get().v().doubleValue() : null);
			this.moneyValue23 = (domain.getAmount().isPresent() ? domain.getAmount().get().v() : null);
			break;
		case 24:
			this.timeValue24 = (domain.getTime().isPresent() ? domain.getTime().get().v() : null);
			this.countValue24 = (domain.getTimes().isPresent() ? domain.getTimes().get().v().doubleValue() : null);
			this.moneyValue24 = (domain.getAmount().isPresent() ? domain.getAmount().get().v() : null);
			break;
		case 25:
			this.timeValue25 = (domain.getTime().isPresent() ? domain.getTime().get().v() : null);
			this.countValue25 = (domain.getTimes().isPresent() ? domain.getTimes().get().v().doubleValue() : null);
			this.moneyValue25 = (domain.getAmount().isPresent() ? domain.getAmount().get().v() : null);
			break;
		case 26:
			this.timeValue26 = (domain.getTime().isPresent() ? domain.getTime().get().v() : null);
			this.countValue26 = (domain.getTimes().isPresent() ? domain.getTimes().get().v().doubleValue() : null);
			this.moneyValue26 = (domain.getAmount().isPresent() ? domain.getAmount().get().v() : null);
			break;
		case 27:
			this.timeValue27 = (domain.getTime().isPresent() ? domain.getTime().get().v() : null);
			this.countValue27 = (domain.getTimes().isPresent() ? domain.getTimes().get().v().doubleValue() : null);
			this.moneyValue27 = (domain.getAmount().isPresent() ? domain.getAmount().get().v() : null);
			break;
		case 28:
			this.timeValue28 = (domain.getTime().isPresent() ? domain.getTime().get().v() : null);
			this.countValue28 = (domain.getTimes().isPresent() ? domain.getTimes().get().v().doubleValue() : null);
			this.moneyValue28 = (domain.getAmount().isPresent() ? domain.getAmount().get().v() : null);
			break;
		case 29:
			this.timeValue29 = (domain.getTime().isPresent() ? domain.getTime().get().v() : null);
			this.countValue29 = (domain.getTimes().isPresent() ? domain.getTimes().get().v().doubleValue() : null);
			this.moneyValue29 = (domain.getAmount().isPresent() ? domain.getAmount().get().v() : null);
			break;
		case 30:
			this.timeValue30 = (domain.getTime().isPresent() ? domain.getTime().get().v() : null);
			this.countValue30 = (domain.getTimes().isPresent() ? domain.getTimes().get().v().doubleValue() : null);
			this.moneyValue30 = (domain.getAmount().isPresent() ? domain.getAmount().get().v() : null);
			break;
		case 31:
			this.timeValue31 = (domain.getTime().isPresent() ? domain.getTime().get().v() : null);
			this.countValue31 = (domain.getTimes().isPresent() ? domain.getTimes().get().v().doubleValue() : null);
			this.moneyValue31 = (domain.getAmount().isPresent() ? domain.getAmount().get().v() : null);
			break;
		case 32:
			this.timeValue32 = (domain.getTime().isPresent() ? domain.getTime().get().v() : null);
			this.countValue32 = (domain.getTimes().isPresent() ? domain.getTimes().get().v().doubleValue() : null);
			this.moneyValue32 = (domain.getAmount().isPresent() ? domain.getAmount().get().v() : null);
			break;
		case 33:
			this.timeValue33 = (domain.getTime().isPresent() ? domain.getTime().get().v() : null);
			this.countValue33 = (domain.getTimes().isPresent() ? domain.getTimes().get().v().doubleValue() : null);
			this.moneyValue33 = (domain.getAmount().isPresent() ? domain.getAmount().get().v() : null);
			break;
		case 34:
			this.timeValue34 = (domain.getTime().isPresent() ? domain.getTime().get().v() : null);
			this.countValue34 = (domain.getTimes().isPresent() ? domain.getTimes().get().v().doubleValue() : null);
			this.moneyValue34 = (domain.getAmount().isPresent() ? domain.getAmount().get().v() : null);
			break;
		case 35:
			this.timeValue35 = (domain.getTime().isPresent() ? domain.getTime().get().v() : null);
			this.countValue35 = (domain.getTimes().isPresent() ? domain.getTimes().get().v().doubleValue() : null);
			this.moneyValue35 = (domain.getAmount().isPresent() ? domain.getAmount().get().v() : null);
			break;
		case 36:
			this.timeValue36 = (domain.getTime().isPresent() ? domain.getTime().get().v() : null);
			this.countValue36 = (domain.getTimes().isPresent() ? domain.getTimes().get().v().doubleValue() : null);
			this.moneyValue36 = (domain.getAmount().isPresent() ? domain.getAmount().get().v() : null);
			break;
		case 37:
			this.timeValue37 = (domain.getTime().isPresent() ? domain.getTime().get().v() : null);
			this.countValue37 = (domain.getTimes().isPresent() ? domain.getTimes().get().v().doubleValue() : null);
			this.moneyValue37 = (domain.getAmount().isPresent() ? domain.getAmount().get().v() : null);
			break;
		case 38:
			this.timeValue38 = (domain.getTime().isPresent() ? domain.getTime().get().v() : null);
			this.countValue38 = (domain.getTimes().isPresent() ? domain.getTimes().get().v().doubleValue() : null);
			this.moneyValue38 = (domain.getAmount().isPresent() ? domain.getAmount().get().v() : null);
			break;
		case 39:
			this.timeValue39 = (domain.getTime().isPresent() ? domain.getTime().get().v() : null);
			this.countValue39 = (domain.getTimes().isPresent() ? domain.getTimes().get().v().doubleValue() : null);
			this.moneyValue39 = (domain.getAmount().isPresent() ? domain.getAmount().get().v() : null);
			break;
		case 40:
			this.timeValue40 = (domain.getTime().isPresent() ? domain.getTime().get().v() : null);
			this.countValue40 = (domain.getTimes().isPresent() ? domain.getTimes().get().v().doubleValue() : null);
			this.moneyValue40 = (domain.getAmount().isPresent() ? domain.getAmount().get().v() : null);
			break;
		case 41:
			this.timeValue41 = (domain.getTime().isPresent() ? domain.getTime().get().v() : null);
			this.countValue41 = (domain.getTimes().isPresent() ? domain.getTimes().get().v().doubleValue() : null);
			this.moneyValue41 = (domain.getAmount().isPresent() ? domain.getAmount().get().v() : null);
			break;
		case 42:
			this.timeValue42 = (domain.getTime().isPresent() ? domain.getTime().get().v() : null);
			this.countValue42 = (domain.getTimes().isPresent() ? domain.getTimes().get().v().doubleValue() : null);
			this.moneyValue42 = (domain.getAmount().isPresent() ? domain.getAmount().get().v() : null);
			break;
		case 43:
			this.timeValue43 = (domain.getTime().isPresent() ? domain.getTime().get().v() : null);
			this.countValue43 = (domain.getTimes().isPresent() ? domain.getTimes().get().v().doubleValue() : null);
			this.moneyValue43 = (domain.getAmount().isPresent() ? domain.getAmount().get().v() : null);
			break;
		case 44:
			this.timeValue44 = (domain.getTime().isPresent() ? domain.getTime().get().v() : null);
			this.countValue44 = (domain.getTimes().isPresent() ? domain.getTimes().get().v().doubleValue() : null);
			this.moneyValue44 = (domain.getAmount().isPresent() ? domain.getAmount().get().v() : null);
			break;
		case 45:
			this.timeValue45 = (domain.getTime().isPresent() ? domain.getTime().get().v() : null);
			this.countValue45 = (domain.getTimes().isPresent() ? domain.getTimes().get().v().doubleValue() : null);
			this.moneyValue45 = (domain.getAmount().isPresent() ? domain.getAmount().get().v() : null);
			break;
		case 46:
			this.timeValue46 = (domain.getTime().isPresent() ? domain.getTime().get().v() : null);
			this.countValue46 = (domain.getTimes().isPresent() ? domain.getTimes().get().v().doubleValue() : null);
			this.moneyValue46 = (domain.getAmount().isPresent() ? domain.getAmount().get().v() : null);
			break;
		case 47:
			this.timeValue47 = (domain.getTime().isPresent() ? domain.getTime().get().v() : null);
			this.countValue47 = (domain.getTimes().isPresent() ? domain.getTimes().get().v().doubleValue() : null);
			this.moneyValue47 = (domain.getAmount().isPresent() ? domain.getAmount().get().v() : null);
			break;
		case 48:
			this.timeValue48 = (domain.getTime().isPresent() ? domain.getTime().get().v() : null);
			this.countValue48 = (domain.getTimes().isPresent() ? domain.getTimes().get().v().doubleValue() : null);
			this.moneyValue48 = (domain.getAmount().isPresent() ? domain.getAmount().get().v() : null);
			break;
		case 49:
			this.timeValue49 = (domain.getTime().isPresent() ? domain.getTime().get().v() : null);
			this.countValue49 = (domain.getTimes().isPresent() ? domain.getTimes().get().v().doubleValue() : null);
			this.moneyValue49 = (domain.getAmount().isPresent() ? domain.getAmount().get().v() : null);
			break;
		case 50:
			this.timeValue50 = (domain.getTime().isPresent() ? domain.getTime().get().v() : null);
			this.countValue50 = (domain.getTimes().isPresent() ? domain.getTimes().get().v().doubleValue() : null);
			this.moneyValue50 = (domain.getAmount().isPresent() ? domain.getAmount().get().v() : null);
			break;
		case 51:
			this.timeValue51 = (domain.getTime().isPresent() ? domain.getTime().get().v() : null);
			this.countValue51 = (domain.getTimes().isPresent() ? domain.getTimes().get().v().doubleValue() : null);
			this.moneyValue51 = (domain.getAmount().isPresent() ? domain.getAmount().get().v() : null);
			break;
		case 52:
			this.timeValue52 = (domain.getTime().isPresent() ? domain.getTime().get().v() : null);
			this.countValue52 = (domain.getTimes().isPresent() ? domain.getTimes().get().v().doubleValue() : null);
			this.moneyValue52 = (domain.getAmount().isPresent() ? domain.getAmount().get().v() : null);
			break;
		case 53:
			this.timeValue53 = (domain.getTime().isPresent() ? domain.getTime().get().v() : null);
			this.countValue53 = (domain.getTimes().isPresent() ? domain.getTimes().get().v().doubleValue() : null);
			this.moneyValue53 = (domain.getAmount().isPresent() ? domain.getAmount().get().v() : null);
			break;
		case 54:
			this.timeValue54 = (domain.getTime().isPresent() ? domain.getTime().get().v() : null);
			this.countValue54 = (domain.getTimes().isPresent() ? domain.getTimes().get().v().doubleValue() : null);
			this.moneyValue54 = (domain.getAmount().isPresent() ? domain.getAmount().get().v() : null);
			break;
		case 55:
			this.timeValue55 = (domain.getTime().isPresent() ? domain.getTime().get().v() : null);
			this.countValue55 = (domain.getTimes().isPresent() ? domain.getTimes().get().v().doubleValue() : null);
			this.moneyValue55 = (domain.getAmount().isPresent() ? domain.getAmount().get().v() : null);
			break;
		case 56:
			this.timeValue56 = (domain.getTime().isPresent() ? domain.getTime().get().v() : null);
			this.countValue56 = (domain.getTimes().isPresent() ? domain.getTimes().get().v().doubleValue() : null);
			this.moneyValue56 = (domain.getAmount().isPresent() ? domain.getAmount().get().v() : null);
			break;
		case 57:
			this.timeValue57 = (domain.getTime().isPresent() ? domain.getTime().get().v() : null);
			this.countValue57 = (domain.getTimes().isPresent() ? domain.getTimes().get().v().doubleValue() : null);
			this.moneyValue57 = (domain.getAmount().isPresent() ? domain.getAmount().get().v() : null);
			break;
		case 58:
			this.timeValue58 = (domain.getTime().isPresent() ? domain.getTime().get().v() : null);
			this.countValue58 = (domain.getTimes().isPresent() ? domain.getTimes().get().v().doubleValue() : null);
			this.moneyValue58 = (domain.getAmount().isPresent() ? domain.getAmount().get().v() : null);
			break;
		case 59:
			this.timeValue59 = (domain.getTime().isPresent() ? domain.getTime().get().v() : null);
			this.countValue59 = (domain.getTimes().isPresent() ? domain.getTimes().get().v().doubleValue() : null);
			this.moneyValue59 = (domain.getAmount().isPresent() ? domain.getAmount().get().v() : null);
			break;
		case 60:
			this.timeValue60 = (domain.getTime().isPresent() ? domain.getTime().get().v() : null);
			this.countValue60 = (domain.getTimes().isPresent() ? domain.getTimes().get().v().doubleValue() : null);
			this.moneyValue60 = (domain.getAmount().isPresent() ? domain.getAmount().get().v() : null);
			break;
		case 61:
			this.timeValue61 = (domain.getTime().isPresent() ? domain.getTime().get().v() : null);
			this.countValue61 = (domain.getTimes().isPresent() ? domain.getTimes().get().v().doubleValue() : null);
			this.moneyValue61 = (domain.getAmount().isPresent() ? domain.getAmount().get().v() : null);
			break;
		case 62:
			this.timeValue62 = (domain.getTime().isPresent() ? domain.getTime().get().v() : null);
			this.countValue62 = (domain.getTimes().isPresent() ? domain.getTimes().get().v().doubleValue() : null);
			this.moneyValue62 = (domain.getAmount().isPresent() ? domain.getAmount().get().v() : null);
			break;
		case 63:
			this.timeValue63 = (domain.getTime().isPresent() ? domain.getTime().get().v() : null);
			this.countValue63 = (domain.getTimes().isPresent() ? domain.getTimes().get().v().doubleValue() : null);
			this.moneyValue63 = (domain.getAmount().isPresent() ? domain.getAmount().get().v() : null);
			break;
		case 64:
			this.timeValue64 = (domain.getTime().isPresent() ? domain.getTime().get().v() : null);
			this.countValue64 = (domain.getTimes().isPresent() ? domain.getTimes().get().v().doubleValue() : null);
			this.moneyValue64 = (domain.getAmount().isPresent() ? domain.getAmount().get().v() : null);
			break;
		case 65:
			this.timeValue65 = (domain.getTime().isPresent() ? domain.getTime().get().v() : null);
			this.countValue65 = (domain.getTimes().isPresent() ? domain.getTimes().get().v().doubleValue() : null);
			this.moneyValue65 = (domain.getAmount().isPresent() ? domain.getAmount().get().v() : null);
			break;
		case 66:
			this.timeValue66 = (domain.getTime().isPresent() ? domain.getTime().get().v() : null);
			this.countValue66 = (domain.getTimes().isPresent() ? domain.getTimes().get().v().doubleValue() : null);
			this.moneyValue66 = (domain.getAmount().isPresent() ? domain.getAmount().get().v() : null);
			break;
		case 67:
			this.timeValue67 = (domain.getTime().isPresent() ? domain.getTime().get().v() : null);
			this.countValue67 = (domain.getTimes().isPresent() ? domain.getTimes().get().v().doubleValue() : null);
			this.moneyValue67 = (domain.getAmount().isPresent() ? domain.getAmount().get().v() : null);
			break;
		case 68:
			this.timeValue68 = (domain.getTime().isPresent() ? domain.getTime().get().v() : null);
			this.countValue68 = (domain.getTimes().isPresent() ? domain.getTimes().get().v().doubleValue() : null);
			this.moneyValue68 = (domain.getAmount().isPresent() ? domain.getAmount().get().v() : null);
			break;
		case 69:
			this.timeValue69 = (domain.getTime().isPresent() ? domain.getTime().get().v() : null);
			this.countValue69 = (domain.getTimes().isPresent() ? domain.getTimes().get().v().doubleValue() : null);
			this.moneyValue69 = (domain.getAmount().isPresent() ? domain.getAmount().get().v() : null);
			break;
		case 70:
			this.timeValue70 = (domain.getTime().isPresent() ? domain.getTime().get().v() : null);
			this.countValue70 = (domain.getTimes().isPresent() ? domain.getTimes().get().v().doubleValue() : null);
			this.moneyValue70 = (domain.getAmount().isPresent() ? domain.getAmount().get().v() : null);
			break;
		case 71:
			this.timeValue71 = (domain.getTime().isPresent() ? domain.getTime().get().v() : null);
			this.countValue71 = (domain.getTimes().isPresent() ? domain.getTimes().get().v().doubleValue() : null);
			this.moneyValue71 = (domain.getAmount().isPresent() ? domain.getAmount().get().v() : null);
			break;
		case 72:
			this.timeValue72 = (domain.getTime().isPresent() ? domain.getTime().get().v() : null);
			this.countValue72 = (domain.getTimes().isPresent() ? domain.getTimes().get().v().doubleValue() : null);
			this.moneyValue72 = (domain.getAmount().isPresent() ? domain.getAmount().get().v() : null);
			break;
		case 73:
			this.timeValue73 = (domain.getTime().isPresent() ? domain.getTime().get().v() : null);
			this.countValue73 = (domain.getTimes().isPresent() ? domain.getTimes().get().v().doubleValue() : null);
			this.moneyValue73 = (domain.getAmount().isPresent() ? domain.getAmount().get().v() : null);
			break;
		case 74:
			this.timeValue74 = (domain.getTime().isPresent() ? domain.getTime().get().v() : null);
			this.countValue74 = (domain.getTimes().isPresent() ? domain.getTimes().get().v().doubleValue() : null);
			this.moneyValue74 = (domain.getAmount().isPresent() ? domain.getAmount().get().v() : null);
			break;
		case 75:
			this.timeValue75 = (domain.getTime().isPresent() ? domain.getTime().get().v() : null);
			this.countValue75 = (domain.getTimes().isPresent() ? domain.getTimes().get().v().doubleValue() : null);
			this.moneyValue75 = (domain.getAmount().isPresent() ? domain.getAmount().get().v() : null);
			break;
		case 76:
			this.timeValue76 = (domain.getTime().isPresent() ? domain.getTime().get().v() : null);
			this.countValue76 = (domain.getTimes().isPresent() ? domain.getTimes().get().v().doubleValue() : null);
			this.moneyValue76 = (domain.getAmount().isPresent() ? domain.getAmount().get().v() : null);
			break;
		case 77:
			this.timeValue77 = (domain.getTime().isPresent() ? domain.getTime().get().v() : null);
			this.countValue77 = (domain.getTimes().isPresent() ? domain.getTimes().get().v().doubleValue() : null);
			this.moneyValue77 = (domain.getAmount().isPresent() ? domain.getAmount().get().v() : null);
			break;
		case 78:
			this.timeValue78 = (domain.getTime().isPresent() ? domain.getTime().get().v() : null);
			this.countValue78 = (domain.getTimes().isPresent() ? domain.getTimes().get().v().doubleValue() : null);
			this.moneyValue78 = (domain.getAmount().isPresent() ? domain.getAmount().get().v() : null);
			break;
		case 79:
			this.timeValue79 = (domain.getTime().isPresent() ? domain.getTime().get().v() : null);
			this.countValue79 = (domain.getTimes().isPresent() ? domain.getTimes().get().v().doubleValue() : null);
			this.moneyValue79 = (domain.getAmount().isPresent() ? domain.getAmount().get().v() : null);
			break;
		case 80:
			this.timeValue80 = (domain.getTime().isPresent() ? domain.getTime().get().v() : null);
			this.countValue80 = (domain.getTimes().isPresent() ? domain.getTimes().get().v().doubleValue() : null);
			this.moneyValue80 = (domain.getAmount().isPresent() ? domain.getAmount().get().v() : null);
			break;
		case 81:
			this.timeValue81 = (domain.getTime().isPresent() ? domain.getTime().get().v() : null);
			this.countValue81 = (domain.getTimes().isPresent() ? domain.getTimes().get().v().doubleValue() : null);
			this.moneyValue81 = (domain.getAmount().isPresent() ? domain.getAmount().get().v() : null);
			break;
		case 82:
			this.timeValue82 = (domain.getTime().isPresent() ? domain.getTime().get().v() : null);
			this.countValue82 = (domain.getTimes().isPresent() ? domain.getTimes().get().v().doubleValue() : null);
			this.moneyValue82 = (domain.getAmount().isPresent() ? domain.getAmount().get().v() : null);
			break;
		case 83:
			this.timeValue83 = (domain.getTime().isPresent() ? domain.getTime().get().v() : null);
			this.countValue83 = (domain.getTimes().isPresent() ? domain.getTimes().get().v().doubleValue() : null);
			this.moneyValue83 = (domain.getAmount().isPresent() ? domain.getAmount().get().v() : null);
			break;
		case 84:
			this.timeValue84 = (domain.getTime().isPresent() ? domain.getTime().get().v() : null);
			this.countValue84 = (domain.getTimes().isPresent() ? domain.getTimes().get().v().doubleValue() : null);
			this.moneyValue84 = (domain.getAmount().isPresent() ? domain.getAmount().get().v() : null);
			break;
		case 85:
			this.timeValue85 = (domain.getTime().isPresent() ? domain.getTime().get().v() : null);
			this.countValue85 = (domain.getTimes().isPresent() ? domain.getTimes().get().v().doubleValue() : null);
			this.moneyValue85 = (domain.getAmount().isPresent() ? domain.getAmount().get().v() : null);
			break;
		case 86:
			this.timeValue86 = (domain.getTime().isPresent() ? domain.getTime().get().v() : null);
			this.countValue86 = (domain.getTimes().isPresent() ? domain.getTimes().get().v().doubleValue() : null);
			this.moneyValue86 = (domain.getAmount().isPresent() ? domain.getAmount().get().v() : null);
			break;
		case 87:
			this.timeValue87 = (domain.getTime().isPresent() ? domain.getTime().get().v() : null);
			this.countValue87 = (domain.getTimes().isPresent() ? domain.getTimes().get().v().doubleValue() : null);
			this.moneyValue87 = (domain.getAmount().isPresent() ? domain.getAmount().get().v() : null);
			break;
		case 88:
			this.timeValue88 = (domain.getTime().isPresent() ? domain.getTime().get().v() : null);
			this.countValue88 = (domain.getTimes().isPresent() ? domain.getTimes().get().v().doubleValue() : null);
			this.moneyValue88 = (domain.getAmount().isPresent() ? domain.getAmount().get().v() : null);
			break;
		case 89:
			this.timeValue89 = (domain.getTime().isPresent() ? domain.getTime().get().v() : null);
			this.countValue89 = (domain.getTimes().isPresent() ? domain.getTimes().get().v().doubleValue() : null);
			this.moneyValue89 = (domain.getAmount().isPresent() ? domain.getAmount().get().v() : null);
			break;
		case 90:
			this.timeValue90 = (domain.getTime().isPresent() ? domain.getTime().get().v() : null);
			this.countValue90 = (domain.getTimes().isPresent() ? domain.getTimes().get().v().doubleValue() : null);
			this.moneyValue90 = (domain.getAmount().isPresent() ? domain.getAmount().get().v() : null);
			break;
		case 91:
			this.timeValue91 = (domain.getTime().isPresent() ? domain.getTime().get().v() : null);
			this.countValue91 = (domain.getTimes().isPresent() ? domain.getTimes().get().v().doubleValue() : null);
			this.moneyValue91 = (domain.getAmount().isPresent() ? domain.getAmount().get().v() : null);
			break;
		case 92:
			this.timeValue92 = (domain.getTime().isPresent() ? domain.getTime().get().v() : null);
			this.countValue92 = (domain.getTimes().isPresent() ? domain.getTimes().get().v().doubleValue() : null);
			this.moneyValue92 = (domain.getAmount().isPresent() ? domain.getAmount().get().v() : null);
			break;
		case 93:
			this.timeValue93 = (domain.getTime().isPresent() ? domain.getTime().get().v() : null);
			this.countValue93 = (domain.getTimes().isPresent() ? domain.getTimes().get().v().doubleValue() : null);
			this.moneyValue93 = (domain.getAmount().isPresent() ? domain.getAmount().get().v() : null);
			break;
		case 94:
			this.timeValue94 = (domain.getTime().isPresent() ? domain.getTime().get().v() : null);
			this.countValue94 = (domain.getTimes().isPresent() ? domain.getTimes().get().v().doubleValue() : null);
			this.moneyValue94 = (domain.getAmount().isPresent() ? domain.getAmount().get().v() : null);
			break;
		case 95:
			this.timeValue95 = (domain.getTime().isPresent() ? domain.getTime().get().v() : null);
			this.countValue95 = (domain.getTimes().isPresent() ? domain.getTimes().get().v().doubleValue() : null);
			this.moneyValue95 = (domain.getAmount().isPresent() ? domain.getAmount().get().v() : null);
			break;
		case 96:
			this.timeValue96 = (domain.getTime().isPresent() ? domain.getTime().get().v() : null);
			this.countValue96 = (domain.getTimes().isPresent() ? domain.getTimes().get().v().doubleValue() : null);
			this.moneyValue96 = (domain.getAmount().isPresent() ? domain.getAmount().get().v() : null);
			break;
		case 97:
			this.timeValue97 = (domain.getTime().isPresent() ? domain.getTime().get().v() : null);
			this.countValue97 = (domain.getTimes().isPresent() ? domain.getTimes().get().v().doubleValue() : null);
			this.moneyValue97 = (domain.getAmount().isPresent() ? domain.getAmount().get().v() : null);
			break;
		case 98:
			this.timeValue98 = (domain.getTime().isPresent() ? domain.getTime().get().v() : null);
			this.countValue98 = (domain.getTimes().isPresent() ? domain.getTimes().get().v().doubleValue() : null);
			this.moneyValue98 = (domain.getAmount().isPresent() ? domain.getAmount().get().v() : null);
			break;
		case 99:
			this.timeValue99 = (domain.getTime().isPresent() ? domain.getTime().get().v() : null);
			this.countValue99 = (domain.getTimes().isPresent() ? domain.getTimes().get().v().doubleValue() : null);
			this.moneyValue99 = (domain.getAmount().isPresent() ? domain.getAmount().get().v() : null);
			break;
		case 100:
			this.timeValue100 = (domain.getTime().isPresent() ? domain.getTime().get().v() : null);
			this.countValue100 = (domain.getTimes().isPresent() ? domain.getTimes().get().v().doubleValue() : null);
			this.moneyValue100 = (domain.getAmount().isPresent() ? domain.getAmount().get().v() : null);
			break;
		case 101:
			this.timeValue101 = (domain.getTime().isPresent() ? domain.getTime().get().v() : null);
			this.countValue101 = (domain.getTimes().isPresent() ? domain.getTimes().get().v().doubleValue() : null);
			this.moneyValue101 = (domain.getAmount().isPresent() ? domain.getAmount().get().v() : null);
			break;
		case 102:
			this.timeValue102 = (domain.getTime().isPresent() ? domain.getTime().get().v() : null);
			this.countValue102 = (domain.getTimes().isPresent() ? domain.getTimes().get().v().doubleValue() : null);
			this.moneyValue102 = (domain.getAmount().isPresent() ? domain.getAmount().get().v() : null);
			break;
		case 103:
			this.timeValue103 = (domain.getTime().isPresent() ? domain.getTime().get().v() : null);
			this.countValue103 = (domain.getTimes().isPresent() ? domain.getTimes().get().v().doubleValue() : null);
			this.moneyValue103 = (domain.getAmount().isPresent() ? domain.getAmount().get().v() : null);
			break;
		case 104:
			this.timeValue104 = (domain.getTime().isPresent() ? domain.getTime().get().v() : null);
			this.countValue104 = (domain.getTimes().isPresent() ? domain.getTimes().get().v().doubleValue() : null);
			this.moneyValue104 = (domain.getAmount().isPresent() ? domain.getAmount().get().v() : null);
			break;
		case 105:
			this.timeValue105 = (domain.getTime().isPresent() ? domain.getTime().get().v() : null);
			this.countValue105 = (domain.getTimes().isPresent() ? domain.getTimes().get().v().doubleValue() : null);
			this.moneyValue105 = (domain.getAmount().isPresent() ? domain.getAmount().get().v() : null);
			break;
		case 106:
			this.timeValue106 = (domain.getTime().isPresent() ? domain.getTime().get().v() : null);
			this.countValue106 = (domain.getTimes().isPresent() ? domain.getTimes().get().v().doubleValue() : null);
			this.moneyValue106 = (domain.getAmount().isPresent() ? domain.getAmount().get().v() : null);
			break;
		case 107:
			this.timeValue107 = (domain.getTime().isPresent() ? domain.getTime().get().v() : null);
			this.countValue107 = (domain.getTimes().isPresent() ? domain.getTimes().get().v().doubleValue() : null);
			this.moneyValue107 = (domain.getAmount().isPresent() ? domain.getAmount().get().v() : null);
			break;
		case 108:
			this.timeValue108 = (domain.getTime().isPresent() ? domain.getTime().get().v() : null);
			this.countValue108 = (domain.getTimes().isPresent() ? domain.getTimes().get().v().doubleValue() : null);
			this.moneyValue108 = (domain.getAmount().isPresent() ? domain.getAmount().get().v() : null);
			break;
		case 109:
			this.timeValue109 = (domain.getTime().isPresent() ? domain.getTime().get().v() : null);
			this.countValue109 = (domain.getTimes().isPresent() ? domain.getTimes().get().v().doubleValue() : null);
			this.moneyValue109 = (domain.getAmount().isPresent() ? domain.getAmount().get().v() : null);
			break;
		case 110:
			this.timeValue110 = (domain.getTime().isPresent() ? domain.getTime().get().v() : null);
			this.countValue110 = (domain.getTimes().isPresent() ? domain.getTimes().get().v().doubleValue() : null);
			this.moneyValue110 = (domain.getAmount().isPresent() ? domain.getAmount().get().v() : null);
			break;
		case 111:
			this.timeValue111 = (domain.getTime().isPresent() ? domain.getTime().get().v() : null);
			this.countValue111 = (domain.getTimes().isPresent() ? domain.getTimes().get().v().doubleValue() : null);
			this.moneyValue111 = (domain.getAmount().isPresent() ? domain.getAmount().get().v() : null);
			break;
		case 112:
			this.timeValue112 = (domain.getTime().isPresent() ? domain.getTime().get().v() : null);
			this.countValue112 = (domain.getTimes().isPresent() ? domain.getTimes().get().v().doubleValue() : null);
			this.moneyValue112 = (domain.getAmount().isPresent() ? domain.getAmount().get().v() : null);
			break;
		case 113:
			this.timeValue113 = (domain.getTime().isPresent() ? domain.getTime().get().v() : null);
			this.countValue113 = (domain.getTimes().isPresent() ? domain.getTimes().get().v().doubleValue() : null);
			this.moneyValue113 = (domain.getAmount().isPresent() ? domain.getAmount().get().v() : null);
			break;
		case 114:
			this.timeValue114 = (domain.getTime().isPresent() ? domain.getTime().get().v() : null);
			this.countValue114 = (domain.getTimes().isPresent() ? domain.getTimes().get().v().doubleValue() : null);
			this.moneyValue114 = (domain.getAmount().isPresent() ? domain.getAmount().get().v() : null);
			break;
		case 115:
			this.timeValue115 = (domain.getTime().isPresent() ? domain.getTime().get().v() : null);
			this.countValue115 = (domain.getTimes().isPresent() ? domain.getTimes().get().v().doubleValue() : null);
			this.moneyValue115 = (domain.getAmount().isPresent() ? domain.getAmount().get().v() : null);
			break;
		case 116:
			this.timeValue116 = (domain.getTime().isPresent() ? domain.getTime().get().v() : null);
			this.countValue116 = (domain.getTimes().isPresent() ? domain.getTimes().get().v().doubleValue() : null);
			this.moneyValue116 = (domain.getAmount().isPresent() ? domain.getAmount().get().v() : null);
			break;
		case 117:
			this.timeValue117 = (domain.getTime().isPresent() ? domain.getTime().get().v() : null);
			this.countValue117 = (domain.getTimes().isPresent() ? domain.getTimes().get().v().doubleValue() : null);
			this.moneyValue117 = (domain.getAmount().isPresent() ? domain.getAmount().get().v() : null);
			break;
		case 118:
			this.timeValue118 = (domain.getTime().isPresent() ? domain.getTime().get().v() : null);
			this.countValue118 = (domain.getTimes().isPresent() ? domain.getTimes().get().v().doubleValue() : null);
			this.moneyValue118 = (domain.getAmount().isPresent() ? domain.getAmount().get().v() : null);
			break;
		case 119:
			this.timeValue119 = (domain.getTime().isPresent() ? domain.getTime().get().v() : null);
			this.countValue119 = (domain.getTimes().isPresent() ? domain.getTimes().get().v().doubleValue() : null);
			this.moneyValue119 = (domain.getAmount().isPresent() ? domain.getAmount().get().v() : null);
			break;
		case 120:
			this.timeValue120 = (domain.getTime().isPresent() ? domain.getTime().get().v() : null);
			this.countValue120 = (domain.getTimes().isPresent() ? domain.getTimes().get().v().doubleValue() : null);
			this.moneyValue120 = (domain.getAmount().isPresent() ? domain.getAmount().get().v() : null);
			break;
		case 121:
			this.timeValue121 = (domain.getTime().isPresent() ? domain.getTime().get().v() : null);
			this.countValue121 = (domain.getTimes().isPresent() ? domain.getTimes().get().v().doubleValue() : null);
			this.moneyValue121 = (domain.getAmount().isPresent() ? domain.getAmount().get().v() : null);
			break;
		case 122:
			this.timeValue122 = (domain.getTime().isPresent() ? domain.getTime().get().v() : null);
			this.countValue122 = (domain.getTimes().isPresent() ? domain.getTimes().get().v().doubleValue() : null);
			this.moneyValue122 = (domain.getAmount().isPresent() ? domain.getAmount().get().v() : null);
			break;
		case 123:
			this.timeValue123 = (domain.getTime().isPresent() ? domain.getTime().get().v() : null);
			this.countValue123 = (domain.getTimes().isPresent() ? domain.getTimes().get().v().doubleValue() : null);
			this.moneyValue123 = (domain.getAmount().isPresent() ? domain.getAmount().get().v() : null);
			break;
		case 124:
			this.timeValue124 = (domain.getTime().isPresent() ? domain.getTime().get().v() : null);
			this.countValue124 = (domain.getTimes().isPresent() ? domain.getTimes().get().v().doubleValue() : null);
			this.moneyValue124 = (domain.getAmount().isPresent() ? domain.getAmount().get().v() : null);
			break;
		case 125:
			this.timeValue125 = (domain.getTime().isPresent() ? domain.getTime().get().v() : null);
			this.countValue125 = (domain.getTimes().isPresent() ? domain.getTimes().get().v().doubleValue() : null);
			this.moneyValue125 = (domain.getAmount().isPresent() ? domain.getAmount().get().v() : null);
			break;
		case 126:
			this.timeValue126 = (domain.getTime().isPresent() ? domain.getTime().get().v() : null);
			this.countValue126 = (domain.getTimes().isPresent() ? domain.getTimes().get().v().doubleValue() : null);
			this.moneyValue126 = (domain.getAmount().isPresent() ? domain.getAmount().get().v() : null);
			break;
		case 127:
			this.timeValue127 = (domain.getTime().isPresent() ? domain.getTime().get().v() : null);
			this.countValue127 = (domain.getTimes().isPresent() ? domain.getTimes().get().v().doubleValue() : null);
			this.moneyValue127 = (domain.getAmount().isPresent() ? domain.getAmount().get().v() : null);
			break;
		case 128:
			this.timeValue128 = (domain.getTime().isPresent() ? domain.getTime().get().v() : null);
			this.countValue128 = (domain.getTimes().isPresent() ? domain.getTimes().get().v().doubleValue() : null);
			this.moneyValue128 = (domain.getAmount().isPresent() ? domain.getAmount().get().v() : null);
			break;
		case 129:
			this.timeValue129 = (domain.getTime().isPresent() ? domain.getTime().get().v() : null);
			this.countValue129 = (domain.getTimes().isPresent() ? domain.getTimes().get().v().doubleValue() : null);
			this.moneyValue129 = (domain.getAmount().isPresent() ? domain.getAmount().get().v() : null);
			break;
		case 130:
			this.timeValue130 = (domain.getTime().isPresent() ? domain.getTime().get().v() : null);
			this.countValue130 = (domain.getTimes().isPresent() ? domain.getTimes().get().v().doubleValue() : null);
			this.moneyValue130 = (domain.getAmount().isPresent() ? domain.getAmount().get().v() : null);
			break;
		case 131:
			this.timeValue131 = (domain.getTime().isPresent() ? domain.getTime().get().v() : null);
			this.countValue131 = (domain.getTimes().isPresent() ? domain.getTimes().get().v().doubleValue() : null);
			this.moneyValue131 = (domain.getAmount().isPresent() ? domain.getAmount().get().v() : null);
			break;
		case 132:
			this.timeValue132 = (domain.getTime().isPresent() ? domain.getTime().get().v() : null);
			this.countValue132 = (domain.getTimes().isPresent() ? domain.getTimes().get().v().doubleValue() : null);
			this.moneyValue132 = (domain.getAmount().isPresent() ? domain.getAmount().get().v() : null);
			break;
		case 133:
			this.timeValue133 = (domain.getTime().isPresent() ? domain.getTime().get().v() : null);
			this.countValue133 = (domain.getTimes().isPresent() ? domain.getTimes().get().v().doubleValue() : null);
			this.moneyValue133 = (domain.getAmount().isPresent() ? domain.getAmount().get().v() : null);
			break;
		case 134:
			this.timeValue134 = (domain.getTime().isPresent() ? domain.getTime().get().v() : null);
			this.countValue134 = (domain.getTimes().isPresent() ? domain.getTimes().get().v().doubleValue() : null);
			this.moneyValue134 = (domain.getAmount().isPresent() ? domain.getAmount().get().v() : null);
			break;
		case 135:
			this.timeValue135 = (domain.getTime().isPresent() ? domain.getTime().get().v() : null);
			this.countValue135 = (domain.getTimes().isPresent() ? domain.getTimes().get().v().doubleValue() : null);
			this.moneyValue135 = (domain.getAmount().isPresent() ? domain.getAmount().get().v() : null);
			break;
		case 136:
			this.timeValue136 = (domain.getTime().isPresent() ? domain.getTime().get().v() : null);
			this.countValue136 = (domain.getTimes().isPresent() ? domain.getTimes().get().v().doubleValue() : null);
			this.moneyValue136 = (domain.getAmount().isPresent() ? domain.getAmount().get().v() : null);
			break;
		case 137:
			this.timeValue137 = (domain.getTime().isPresent() ? domain.getTime().get().v() : null);
			this.countValue137 = (domain.getTimes().isPresent() ? domain.getTimes().get().v().doubleValue() : null);
			this.moneyValue137 = (domain.getAmount().isPresent() ? domain.getAmount().get().v() : null);
			break;
		case 138:
			this.timeValue138 = (domain.getTime().isPresent() ? domain.getTime().get().v() : null);
			this.countValue138 = (domain.getTimes().isPresent() ? domain.getTimes().get().v().doubleValue() : null);
			this.moneyValue138 = (domain.getAmount().isPresent() ? domain.getAmount().get().v() : null);
			break;
		case 139:
			this.timeValue139 = (domain.getTime().isPresent() ? domain.getTime().get().v() : null);
			this.countValue139 = (domain.getTimes().isPresent() ? domain.getTimes().get().v().doubleValue() : null);
			this.moneyValue139 = (domain.getAmount().isPresent() ? domain.getAmount().get().v() : null);
			break;
		case 140:
			this.timeValue140 = (domain.getTime().isPresent() ? domain.getTime().get().v() : null);
			this.countValue140 = (domain.getTimes().isPresent() ? domain.getTimes().get().v().doubleValue() : null);
			this.moneyValue140 = (domain.getAmount().isPresent() ? domain.getAmount().get().v() : null);
			break;
		case 141:
			this.timeValue141 = (domain.getTime().isPresent() ? domain.getTime().get().v() : null);
			this.countValue141 = (domain.getTimes().isPresent() ? domain.getTimes().get().v().doubleValue() : null);
			this.moneyValue141 = (domain.getAmount().isPresent() ? domain.getAmount().get().v() : null);
			break;
		case 142:
			this.timeValue142 = (domain.getTime().isPresent() ? domain.getTime().get().v() : null);
			this.countValue142 = (domain.getTimes().isPresent() ? domain.getTimes().get().v().doubleValue() : null);
			this.moneyValue142 = (domain.getAmount().isPresent() ? domain.getAmount().get().v() : null);
			break;
		case 143:
			this.timeValue143 = (domain.getTime().isPresent() ? domain.getTime().get().v() : null);
			this.countValue143 = (domain.getTimes().isPresent() ? domain.getTimes().get().v().doubleValue() : null);
			this.moneyValue143 = (domain.getAmount().isPresent() ? domain.getAmount().get().v() : null);
			break;
		case 144:
			this.timeValue144 = (domain.getTime().isPresent() ? domain.getTime().get().v() : null);
			this.countValue144 = (domain.getTimes().isPresent() ? domain.getTimes().get().v().doubleValue() : null);
			this.moneyValue144 = (domain.getAmount().isPresent() ? domain.getAmount().get().v() : null);
			break;
		case 145:
			this.timeValue145 = (domain.getTime().isPresent() ? domain.getTime().get().v() : null);
			this.countValue145 = (domain.getTimes().isPresent() ? domain.getTimes().get().v().doubleValue() : null);
			this.moneyValue145 = (domain.getAmount().isPresent() ? domain.getAmount().get().v() : null);
			break;
		case 146:
			this.timeValue146 = (domain.getTime().isPresent() ? domain.getTime().get().v() : null);
			this.countValue146 = (domain.getTimes().isPresent() ? domain.getTimes().get().v().doubleValue() : null);
			this.moneyValue146 = (domain.getAmount().isPresent() ? domain.getAmount().get().v() : null);
			break;
		case 147:
			this.timeValue147 = (domain.getTime().isPresent() ? domain.getTime().get().v() : null);
			this.countValue147 = (domain.getTimes().isPresent() ? domain.getTimes().get().v().doubleValue() : null);
			this.moneyValue147 = (domain.getAmount().isPresent() ? domain.getAmount().get().v() : null);
			break;
		case 148:
			this.timeValue148 = (domain.getTime().isPresent() ? domain.getTime().get().v() : null);
			this.countValue148 = (domain.getTimes().isPresent() ? domain.getTimes().get().v().doubleValue() : null);
			this.moneyValue148 = (domain.getAmount().isPresent() ? domain.getAmount().get().v() : null);
			break;
		case 149:
			this.timeValue149 = (domain.getTime().isPresent() ? domain.getTime().get().v() : null);
			this.countValue149 = (domain.getTimes().isPresent() ? domain.getTimes().get().v().doubleValue() : null);
			this.moneyValue149 = (domain.getAmount().isPresent() ? domain.getAmount().get().v() : null);
			break;
		case 150:
			this.timeValue150 = (domain.getTime().isPresent() ? domain.getTime().get().v() : null);
			this.countValue150 = (domain.getTimes().isPresent() ? domain.getTimes().get().v().doubleValue() : null);
			this.moneyValue150 = (domain.getAmount().isPresent() ? domain.getAmount().get().v() : null);
			break;
		case 151:
			this.timeValue151 = (domain.getTime().isPresent() ? domain.getTime().get().v() : null);
			this.countValue151 = (domain.getTimes().isPresent() ? domain.getTimes().get().v().doubleValue() : null);
			this.moneyValue151 = (domain.getAmount().isPresent() ? domain.getAmount().get().v() : null);
			break;
		case 152:
			this.timeValue152 = (domain.getTime().isPresent() ? domain.getTime().get().v() : null);
			this.countValue152 = (domain.getTimes().isPresent() ? domain.getTimes().get().v().doubleValue() : null);
			this.moneyValue152 = (domain.getAmount().isPresent() ? domain.getAmount().get().v() : null);
			break;
		case 153:
			this.timeValue153 = (domain.getTime().isPresent() ? domain.getTime().get().v() : null);
			this.countValue153 = (domain.getTimes().isPresent() ? domain.getTimes().get().v().doubleValue() : null);
			this.moneyValue153 = (domain.getAmount().isPresent() ? domain.getAmount().get().v() : null);
			break;
		case 154:
			this.timeValue154 = (domain.getTime().isPresent() ? domain.getTime().get().v() : null);
			this.countValue154 = (domain.getTimes().isPresent() ? domain.getTimes().get().v().doubleValue() : null);
			this.moneyValue154 = (domain.getAmount().isPresent() ? domain.getAmount().get().v() : null);
			break;
		case 155:
			this.timeValue155 = (domain.getTime().isPresent() ? domain.getTime().get().v() : null);
			this.countValue155 = (domain.getTimes().isPresent() ? domain.getTimes().get().v().doubleValue() : null);
			this.moneyValue155 = (domain.getAmount().isPresent() ? domain.getAmount().get().v() : null);
			break;
		case 156:
			this.timeValue156 = (domain.getTime().isPresent() ? domain.getTime().get().v() : null);
			this.countValue156 = (domain.getTimes().isPresent() ? domain.getTimes().get().v().doubleValue() : null);
			this.moneyValue156 = (domain.getAmount().isPresent() ? domain.getAmount().get().v() : null);
			break;
		case 157:
			this.timeValue157 = (domain.getTime().isPresent() ? domain.getTime().get().v() : null);
			this.countValue157 = (domain.getTimes().isPresent() ? domain.getTimes().get().v().doubleValue() : null);
			this.moneyValue157 = (domain.getAmount().isPresent() ? domain.getAmount().get().v() : null);
			break;
		case 158:
			this.timeValue158 = (domain.getTime().isPresent() ? domain.getTime().get().v() : null);
			this.countValue158 = (domain.getTimes().isPresent() ? domain.getTimes().get().v().doubleValue() : null);
			this.moneyValue158 = (domain.getAmount().isPresent() ? domain.getAmount().get().v() : null);
			break;
		case 159:
			this.timeValue159 = (domain.getTime().isPresent() ? domain.getTime().get().v() : null);
			this.countValue159 = (domain.getTimes().isPresent() ? domain.getTimes().get().v().doubleValue() : null);
			this.moneyValue159 = (domain.getAmount().isPresent() ? domain.getAmount().get().v() : null);
			break;
		case 160:
			this.timeValue160 = (domain.getTime().isPresent() ? domain.getTime().get().v() : null);
			this.countValue160 = (domain.getTimes().isPresent() ? domain.getTimes().get().v().doubleValue() : null);
			this.moneyValue160 = (domain.getAmount().isPresent() ? domain.getAmount().get().v() : null);
			break;
		case 161:
			this.timeValue161 = (domain.getTime().isPresent() ? domain.getTime().get().v() : null);
			this.countValue161 = (domain.getTimes().isPresent() ? domain.getTimes().get().v().doubleValue() : null);
			this.moneyValue161 = (domain.getAmount().isPresent() ? domain.getAmount().get().v() : null);
			break;
		case 162:
			this.timeValue162 = (domain.getTime().isPresent() ? domain.getTime().get().v() : null);
			this.countValue162 = (domain.getTimes().isPresent() ? domain.getTimes().get().v().doubleValue() : null);
			this.moneyValue162 = (domain.getAmount().isPresent() ? domain.getAmount().get().v() : null);
			break;
		case 163:
			this.timeValue163 = (domain.getTime().isPresent() ? domain.getTime().get().v() : null);
			this.countValue163 = (domain.getTimes().isPresent() ? domain.getTimes().get().v().doubleValue() : null);
			this.moneyValue163 = (domain.getAmount().isPresent() ? domain.getAmount().get().v() : null);
			break;
		case 164:
			this.timeValue164 = (domain.getTime().isPresent() ? domain.getTime().get().v() : null);
			this.countValue164 = (domain.getTimes().isPresent() ? domain.getTimes().get().v().doubleValue() : null);
			this.moneyValue164 = (domain.getAmount().isPresent() ? domain.getAmount().get().v() : null);
			break;
		case 165:
			this.timeValue165 = (domain.getTime().isPresent() ? domain.getTime().get().v() : null);
			this.countValue165 = (domain.getTimes().isPresent() ? domain.getTimes().get().v().doubleValue() : null);
			this.moneyValue165 = (domain.getAmount().isPresent() ? domain.getAmount().get().v() : null);
			break;
		case 166:
			this.timeValue166 = (domain.getTime().isPresent() ? domain.getTime().get().v() : null);
			this.countValue166 = (domain.getTimes().isPresent() ? domain.getTimes().get().v().doubleValue() : null);
			this.moneyValue166 = (domain.getAmount().isPresent() ? domain.getAmount().get().v() : null);
			break;
		case 167:
			this.timeValue167 = (domain.getTime().isPresent() ? domain.getTime().get().v() : null);
			this.countValue167 = (domain.getTimes().isPresent() ? domain.getTimes().get().v().doubleValue() : null);
			this.moneyValue167 = (domain.getAmount().isPresent() ? domain.getAmount().get().v() : null);
			break;
		case 168:
			this.timeValue168 = (domain.getTime().isPresent() ? domain.getTime().get().v() : null);
			this.countValue168 = (domain.getTimes().isPresent() ? domain.getTimes().get().v().doubleValue() : null);
			this.moneyValue168 = (domain.getAmount().isPresent() ? domain.getAmount().get().v() : null);
			break;
		case 169:
			this.timeValue169 = (domain.getTime().isPresent() ? domain.getTime().get().v() : null);
			this.countValue169 = (domain.getTimes().isPresent() ? domain.getTimes().get().v().doubleValue() : null);
			this.moneyValue169 = (domain.getAmount().isPresent() ? domain.getAmount().get().v() : null);
			break;
		case 170:
			this.timeValue170 = (domain.getTime().isPresent() ? domain.getTime().get().v() : null);
			this.countValue170 = (domain.getTimes().isPresent() ? domain.getTimes().get().v().doubleValue() : null);
			this.moneyValue170 = (domain.getAmount().isPresent() ? domain.getAmount().get().v() : null);
			break;
		case 171:
			this.timeValue171 = (domain.getTime().isPresent() ? domain.getTime().get().v() : null);
			this.countValue171 = (domain.getTimes().isPresent() ? domain.getTimes().get().v().doubleValue() : null);
			this.moneyValue171 = (domain.getAmount().isPresent() ? domain.getAmount().get().v() : null);
			break;
		case 172:
			this.timeValue172 = (domain.getTime().isPresent() ? domain.getTime().get().v() : null);
			this.countValue172 = (domain.getTimes().isPresent() ? domain.getTimes().get().v().doubleValue() : null);
			this.moneyValue172 = (domain.getAmount().isPresent() ? domain.getAmount().get().v() : null);
			break;
		case 173:
			this.timeValue173 = (domain.getTime().isPresent() ? domain.getTime().get().v() : null);
			this.countValue173 = (domain.getTimes().isPresent() ? domain.getTimes().get().v().doubleValue() : null);
			this.moneyValue173 = (domain.getAmount().isPresent() ? domain.getAmount().get().v() : null);
			break;
		case 174:
			this.timeValue174 = (domain.getTime().isPresent() ? domain.getTime().get().v() : null);
			this.countValue174 = (domain.getTimes().isPresent() ? domain.getTimes().get().v().doubleValue() : null);
			this.moneyValue174 = (domain.getAmount().isPresent() ? domain.getAmount().get().v() : null);
			break;
		case 175:
			this.timeValue175 = (domain.getTime().isPresent() ? domain.getTime().get().v() : null);
			this.countValue175 = (domain.getTimes().isPresent() ? domain.getTimes().get().v().doubleValue() : null);
			this.moneyValue175 = (domain.getAmount().isPresent() ? domain.getAmount().get().v() : null);
			break;
		case 176:
			this.timeValue176 = (domain.getTime().isPresent() ? domain.getTime().get().v() : null);
			this.countValue176 = (domain.getTimes().isPresent() ? domain.getTimes().get().v().doubleValue() : null);
			this.moneyValue176 = (domain.getAmount().isPresent() ? domain.getAmount().get().v() : null);
			break;
		case 177:
			this.timeValue177 = (domain.getTime().isPresent() ? domain.getTime().get().v() : null);
			this.countValue177 = (domain.getTimes().isPresent() ? domain.getTimes().get().v().doubleValue() : null);
			this.moneyValue177 = (domain.getAmount().isPresent() ? domain.getAmount().get().v() : null);
			break;
		case 178:
			this.timeValue178 = (domain.getTime().isPresent() ? domain.getTime().get().v() : null);
			this.countValue178 = (domain.getTimes().isPresent() ? domain.getTimes().get().v().doubleValue() : null);
			this.moneyValue178 = (domain.getAmount().isPresent() ? domain.getAmount().get().v() : null);
			break;
		case 179:
			this.timeValue179 = (domain.getTime().isPresent() ? domain.getTime().get().v() : null);
			this.countValue179 = (domain.getTimes().isPresent() ? domain.getTimes().get().v().doubleValue() : null);
			this.moneyValue179 = (domain.getAmount().isPresent() ? domain.getAmount().get().v() : null);
			break;
		case 180:
			this.timeValue180 = (domain.getTime().isPresent() ? domain.getTime().get().v() : null);
			this.countValue180 = (domain.getTimes().isPresent() ? domain.getTimes().get().v().doubleValue() : null);
			this.moneyValue180 = (domain.getAmount().isPresent() ? domain.getAmount().get().v() : null);
			break;
		case 181:
			this.timeValue181 = (domain.getTime().isPresent() ? domain.getTime().get().v() : null);
			this.countValue181 = (domain.getTimes().isPresent() ? domain.getTimes().get().v().doubleValue() : null);
			this.moneyValue181 = (domain.getAmount().isPresent() ? domain.getAmount().get().v() : null);
			break;
		case 182:
			this.timeValue182 = (domain.getTime().isPresent() ? domain.getTime().get().v() : null);
			this.countValue182 = (domain.getTimes().isPresent() ? domain.getTimes().get().v().doubleValue() : null);
			this.moneyValue182 = (domain.getAmount().isPresent() ? domain.getAmount().get().v() : null);
			break;
		case 183:
			this.timeValue183 = (domain.getTime().isPresent() ? domain.getTime().get().v() : null);
			this.countValue183 = (domain.getTimes().isPresent() ? domain.getTimes().get().v().doubleValue() : null);
			this.moneyValue183 = (domain.getAmount().isPresent() ? domain.getAmount().get().v() : null);
			break;
		case 184:
			this.timeValue184 = (domain.getTime().isPresent() ? domain.getTime().get().v() : null);
			this.countValue184 = (domain.getTimes().isPresent() ? domain.getTimes().get().v().doubleValue() : null);
			this.moneyValue184 = (domain.getAmount().isPresent() ? domain.getAmount().get().v() : null);
			break;
		case 185:
			this.timeValue185 = (domain.getTime().isPresent() ? domain.getTime().get().v() : null);
			this.countValue185 = (domain.getTimes().isPresent() ? domain.getTimes().get().v().doubleValue() : null);
			this.moneyValue185 = (domain.getAmount().isPresent() ? domain.getAmount().get().v() : null);
			break;
		case 186:
			this.timeValue186 = (domain.getTime().isPresent() ? domain.getTime().get().v() : null);
			this.countValue186 = (domain.getTimes().isPresent() ? domain.getTimes().get().v().doubleValue() : null);
			this.moneyValue186 = (domain.getAmount().isPresent() ? domain.getAmount().get().v() : null);
			break;
		case 187:
			this.timeValue187 = (domain.getTime().isPresent() ? domain.getTime().get().v() : null);
			this.countValue187 = (domain.getTimes().isPresent() ? domain.getTimes().get().v().doubleValue() : null);
			this.moneyValue187 = (domain.getAmount().isPresent() ? domain.getAmount().get().v() : null);
			break;
		case 188:
			this.timeValue188 = (domain.getTime().isPresent() ? domain.getTime().get().v() : null);
			this.countValue188 = (domain.getTimes().isPresent() ? domain.getTimes().get().v().doubleValue() : null);
			this.moneyValue188 = (domain.getAmount().isPresent() ? domain.getAmount().get().v() : null);
			break;
		case 189:
			this.timeValue189 = (domain.getTime().isPresent() ? domain.getTime().get().v() : null);
			this.countValue189 = (domain.getTimes().isPresent() ? domain.getTimes().get().v().doubleValue() : null);
			this.moneyValue189 = (domain.getAmount().isPresent() ? domain.getAmount().get().v() : null);
			break;
		case 190:
			this.timeValue190 = (domain.getTime().isPresent() ? domain.getTime().get().v() : null);
			this.countValue190 = (domain.getTimes().isPresent() ? domain.getTimes().get().v().doubleValue() : null);
			this.moneyValue190 = (domain.getAmount().isPresent() ? domain.getAmount().get().v() : null);
			break;
		case 191:
			this.timeValue191 = (domain.getTime().isPresent() ? domain.getTime().get().v() : null);
			this.countValue191 = (domain.getTimes().isPresent() ? domain.getTimes().get().v().doubleValue() : null);
			this.moneyValue191 = (domain.getAmount().isPresent() ? domain.getAmount().get().v() : null);
			break;
		case 192:
			this.timeValue192 = (domain.getTime().isPresent() ? domain.getTime().get().v() : null);
			this.countValue192 = (domain.getTimes().isPresent() ? domain.getTimes().get().v().doubleValue() : null);
			this.moneyValue192 = (domain.getAmount().isPresent() ? domain.getAmount().get().v() : null);
			break;
		case 193:
			this.timeValue193 = (domain.getTime().isPresent() ? domain.getTime().get().v() : null);
			this.countValue193 = (domain.getTimes().isPresent() ? domain.getTimes().get().v().doubleValue() : null);
			this.moneyValue193 = (domain.getAmount().isPresent() ? domain.getAmount().get().v() : null);
			break;
		case 194:
			this.timeValue194 = (domain.getTime().isPresent() ? domain.getTime().get().v() : null);
			this.countValue194 = (domain.getTimes().isPresent() ? domain.getTimes().get().v().doubleValue() : null);
			this.moneyValue194 = (domain.getAmount().isPresent() ? domain.getAmount().get().v() : null);
			break;
		case 195:
			this.timeValue195 = (domain.getTime().isPresent() ? domain.getTime().get().v() : null);
			this.countValue195 = (domain.getTimes().isPresent() ? domain.getTimes().get().v().doubleValue() : null);
			this.moneyValue195 = (domain.getAmount().isPresent() ? domain.getAmount().get().v() : null);
			break;
		case 196:
			this.timeValue196 = (domain.getTime().isPresent() ? domain.getTime().get().v() : null);
			this.countValue196 = (domain.getTimes().isPresent() ? domain.getTimes().get().v().doubleValue() : null);
			this.moneyValue196 = (domain.getAmount().isPresent() ? domain.getAmount().get().v() : null);
			break;
		case 197:
			this.timeValue197 = (domain.getTime().isPresent() ? domain.getTime().get().v() : null);
			this.countValue197 = (domain.getTimes().isPresent() ? domain.getTimes().get().v().doubleValue() : null);
			this.moneyValue197 = (domain.getAmount().isPresent() ? domain.getAmount().get().v() : null);
			break;
		case 198:
			this.timeValue198 = (domain.getTime().isPresent() ? domain.getTime().get().v() : null);
			this.countValue198 = (domain.getTimes().isPresent() ? domain.getTimes().get().v().doubleValue() : null);
			this.moneyValue198 = (domain.getAmount().isPresent() ? domain.getAmount().get().v() : null);
			break;
		case 199:
			this.timeValue199 = (domain.getTime().isPresent() ? domain.getTime().get().v() : null);
			this.countValue199 = (domain.getTimes().isPresent() ? domain.getTimes().get().v().doubleValue() : null);
			this.moneyValue199 = (domain.getAmount().isPresent() ? domain.getAmount().get().v() : null);
			break;
		case 200:
			this.timeValue200 = (domain.getTime().isPresent() ? domain.getTime().get().v() : null);
			this.countValue200 = (domain.getTimes().isPresent() ? domain.getTimes().get().v().doubleValue() : null);
			this.moneyValue200 = (domain.getAmount().isPresent() ? domain.getAmount().get().v() : null);
			break;
		}
	}
	
	private AnyItemValue toDomainAnyItemValue(Integer timeValue, Double countValue, Integer moneyValue, int anyItemId) {
		// get value anyItemId = 0 temporary because table have not anyitemId
		return new AnyItemValue(
				new AnyItemNo(anyItemId),
				(countValue == null ? Optional.empty() : Optional.of(new AnyItemTimes(BigDecimal.valueOf(countValue)))),
				(moneyValue == null ? Optional.empty() : Optional.of(new AnyItemAmount(moneyValue))),
				(timeValue == null ? Optional.empty() : Optional.of(new AnyItemTime(timeValue))));
	}
	
	/**
	 * ドメインに変換
	 * @return AnyItemValue
	 */
	public AnyItemValue toDomainAnyItemValue(int anyItemId){
		switch (anyItemId){
		case 1:
			return this.toDomainAnyItemValue(timeValue1, countValue1, moneyValue1, anyItemId);
		case 2:
			return this.toDomainAnyItemValue(timeValue2, countValue2, moneyValue2, anyItemId);
		case 3:
			return this.toDomainAnyItemValue(timeValue3, countValue3, moneyValue3, anyItemId);
		case 4:
			return this.toDomainAnyItemValue(timeValue4, countValue4, moneyValue4, anyItemId);
		case 5:
			return this.toDomainAnyItemValue(timeValue5, countValue5, moneyValue5, anyItemId);
		case 6:
			return this.toDomainAnyItemValue(timeValue6, countValue6, moneyValue6, anyItemId);
		case 7:
			return this.toDomainAnyItemValue(timeValue7, countValue7, moneyValue7, anyItemId);
		case 8:
			return this.toDomainAnyItemValue(timeValue8, countValue8, moneyValue8, anyItemId);
		case 9:
			return this.toDomainAnyItemValue(timeValue9, countValue9, moneyValue9, anyItemId);
		case 10:
			return this.toDomainAnyItemValue(timeValue10, countValue10, moneyValue10, anyItemId);
		case 11:
			return this.toDomainAnyItemValue(timeValue11, countValue11, moneyValue11, anyItemId);
		case 12:
			return this.toDomainAnyItemValue(timeValue12, countValue12, moneyValue12, anyItemId);
		case 13:
			return this.toDomainAnyItemValue(timeValue13, countValue13, moneyValue13, anyItemId);
		case 14:
			return this.toDomainAnyItemValue(timeValue14, countValue14, moneyValue14, anyItemId);
		case 15:
			return this.toDomainAnyItemValue(timeValue15, countValue15, moneyValue15, anyItemId);
		case 16:
			return this.toDomainAnyItemValue(timeValue16, countValue16, moneyValue16, anyItemId);
		case 17:
			return this.toDomainAnyItemValue(timeValue17, countValue17, moneyValue17, anyItemId);
		case 18:
			return this.toDomainAnyItemValue(timeValue18, countValue18, moneyValue18, anyItemId);
		case 19:
			return this.toDomainAnyItemValue(timeValue19, countValue19, moneyValue19, anyItemId);
		case 20:
			return this.toDomainAnyItemValue(timeValue20, countValue20, moneyValue20, anyItemId);
		case 21:
			return this.toDomainAnyItemValue(timeValue21, countValue21, moneyValue21, anyItemId);
		case 22:
			return this.toDomainAnyItemValue(timeValue22, countValue22, moneyValue22, anyItemId);
		case 23:
			return this.toDomainAnyItemValue(timeValue23, countValue23, moneyValue23, anyItemId);
		case 24:
			return this.toDomainAnyItemValue(timeValue24, countValue24, moneyValue24, anyItemId);
		case 25:
			return this.toDomainAnyItemValue(timeValue25, countValue25, moneyValue25, anyItemId);
		case 26:
			return this.toDomainAnyItemValue(timeValue26, countValue26, moneyValue26, anyItemId);
		case 27:
			return this.toDomainAnyItemValue(timeValue27, countValue27, moneyValue27, anyItemId);
		case 28:
			return this.toDomainAnyItemValue(timeValue28, countValue28, moneyValue28, anyItemId);
		case 29:
			return this.toDomainAnyItemValue(timeValue29, countValue29, moneyValue29, anyItemId);
		case 30:
			return this.toDomainAnyItemValue(timeValue30, countValue30, moneyValue30, anyItemId);
		case 31:
			return this.toDomainAnyItemValue(timeValue31, countValue31, moneyValue31, anyItemId);
		case 32:
			return this.toDomainAnyItemValue(timeValue32, countValue32, moneyValue32, anyItemId);
		case 33:
			return this.toDomainAnyItemValue(timeValue33, countValue33, moneyValue33, anyItemId);
		case 34:
			return this.toDomainAnyItemValue(timeValue34, countValue34, moneyValue34, anyItemId);
		case 35:
			return this.toDomainAnyItemValue(timeValue35, countValue35, moneyValue35, anyItemId);
		case 36:
			return this.toDomainAnyItemValue(timeValue36, countValue36, moneyValue36, anyItemId);
		case 37:
			return this.toDomainAnyItemValue(timeValue37, countValue37, moneyValue37, anyItemId);
		case 38:
			return this.toDomainAnyItemValue(timeValue38, countValue38, moneyValue38, anyItemId);
		case 39:
			return this.toDomainAnyItemValue(timeValue39, countValue39, moneyValue39, anyItemId);
		case 40:
			return this.toDomainAnyItemValue(timeValue40, countValue40, moneyValue40, anyItemId);
		case 41:
			return this.toDomainAnyItemValue(timeValue41, countValue41, moneyValue41, anyItemId);
		case 42:
			return this.toDomainAnyItemValue(timeValue42, countValue42, moneyValue42, anyItemId);
		case 43:
			return this.toDomainAnyItemValue(timeValue43, countValue43, moneyValue43, anyItemId);
		case 44:
			return this.toDomainAnyItemValue(timeValue44, countValue44, moneyValue44, anyItemId);
		case 45:
			return this.toDomainAnyItemValue(timeValue45, countValue45, moneyValue45, anyItemId);
		case 46:
			return this.toDomainAnyItemValue(timeValue46, countValue46, moneyValue46, anyItemId);
		case 47:
			return this.toDomainAnyItemValue(timeValue47, countValue47, moneyValue47, anyItemId);
		case 48:
			return this.toDomainAnyItemValue(timeValue48, countValue48, moneyValue48, anyItemId);
		case 49:
			return this.toDomainAnyItemValue(timeValue49, countValue49, moneyValue49, anyItemId);
		case 50:
			return this.toDomainAnyItemValue(timeValue50, countValue50, moneyValue50, anyItemId);
		case 51:
			return this.toDomainAnyItemValue(timeValue51, countValue51, moneyValue51, anyItemId);
		case 52:
			return this.toDomainAnyItemValue(timeValue52, countValue52, moneyValue52, anyItemId);
		case 53:
			return this.toDomainAnyItemValue(timeValue53, countValue53, moneyValue53, anyItemId);
		case 54:
			return this.toDomainAnyItemValue(timeValue54, countValue54, moneyValue54, anyItemId);
		case 55:
			return this.toDomainAnyItemValue(timeValue55, countValue55, moneyValue55, anyItemId);
		case 56:
			return this.toDomainAnyItemValue(timeValue56, countValue56, moneyValue56, anyItemId);
		case 57:
			return this.toDomainAnyItemValue(timeValue57, countValue57, moneyValue57, anyItemId);
		case 58:
			return this.toDomainAnyItemValue(timeValue58, countValue58, moneyValue58, anyItemId);
		case 59:
			return this.toDomainAnyItemValue(timeValue59, countValue59, moneyValue59, anyItemId);
		case 60:
			return this.toDomainAnyItemValue(timeValue60, countValue60, moneyValue60, anyItemId);
		case 61:
			return this.toDomainAnyItemValue(timeValue61, countValue61, moneyValue61, anyItemId);
		case 62:
			return this.toDomainAnyItemValue(timeValue62, countValue62, moneyValue62, anyItemId);
		case 63:
			return this.toDomainAnyItemValue(timeValue63, countValue63, moneyValue63, anyItemId);
		case 64:
			return this.toDomainAnyItemValue(timeValue64, countValue64, moneyValue64, anyItemId);
		case 65:
			return this.toDomainAnyItemValue(timeValue65, countValue65, moneyValue65, anyItemId);
		case 66:
			return this.toDomainAnyItemValue(timeValue66, countValue66, moneyValue66, anyItemId);
		case 67:
			return this.toDomainAnyItemValue(timeValue67, countValue67, moneyValue67, anyItemId);
		case 68:
			return this.toDomainAnyItemValue(timeValue68, countValue68, moneyValue68, anyItemId);
		case 69:
			return this.toDomainAnyItemValue(timeValue69, countValue69, moneyValue69, anyItemId);
		case 70:
			return this.toDomainAnyItemValue(timeValue70, countValue70, moneyValue70, anyItemId);
		case 71:
			return this.toDomainAnyItemValue(timeValue71, countValue71, moneyValue71, anyItemId);
		case 72:
			return this.toDomainAnyItemValue(timeValue72, countValue72, moneyValue72, anyItemId);
		case 73:
			return this.toDomainAnyItemValue(timeValue73, countValue73, moneyValue73, anyItemId);
		case 74:
			return this.toDomainAnyItemValue(timeValue74, countValue74, moneyValue74, anyItemId);
		case 75:
			return this.toDomainAnyItemValue(timeValue75, countValue75, moneyValue75, anyItemId);
		case 76:
			return this.toDomainAnyItemValue(timeValue76, countValue76, moneyValue76, anyItemId);
		case 77:
			return this.toDomainAnyItemValue(timeValue77, countValue77, moneyValue77, anyItemId);
		case 78:
			return this.toDomainAnyItemValue(timeValue78, countValue78, moneyValue78, anyItemId);
		case 79:
			return this.toDomainAnyItemValue(timeValue79, countValue79, moneyValue79, anyItemId);
		case 80:
			return this.toDomainAnyItemValue(timeValue80, countValue80, moneyValue80, anyItemId);
		case 81:
			return this.toDomainAnyItemValue(timeValue81, countValue81, moneyValue81, anyItemId);
		case 82:
			return this.toDomainAnyItemValue(timeValue82, countValue82, moneyValue82, anyItemId);
		case 83:
			return this.toDomainAnyItemValue(timeValue83, countValue83, moneyValue83, anyItemId);
		case 84:
			return this.toDomainAnyItemValue(timeValue84, countValue84, moneyValue84, anyItemId);
		case 85:
			return this.toDomainAnyItemValue(timeValue85, countValue85, moneyValue85, anyItemId);
		case 86:
			return this.toDomainAnyItemValue(timeValue86, countValue86, moneyValue86, anyItemId);
		case 87:
			return this.toDomainAnyItemValue(timeValue87, countValue87, moneyValue87, anyItemId);
		case 88:
			return this.toDomainAnyItemValue(timeValue88, countValue88, moneyValue88, anyItemId);
		case 89:
			return this.toDomainAnyItemValue(timeValue89, countValue89, moneyValue89, anyItemId);
		case 90:
			return this.toDomainAnyItemValue(timeValue90, countValue90, moneyValue90, anyItemId);
		case 91:
			return this.toDomainAnyItemValue(timeValue91, countValue91, moneyValue91, anyItemId);
		case 92:
			return this.toDomainAnyItemValue(timeValue92, countValue92, moneyValue92, anyItemId);
		case 93:
			return this.toDomainAnyItemValue(timeValue93, countValue93, moneyValue93, anyItemId);
		case 94:
			return this.toDomainAnyItemValue(timeValue94, countValue94, moneyValue94, anyItemId);
		case 95:
			return this.toDomainAnyItemValue(timeValue95, countValue95, moneyValue95, anyItemId);
		case 96:
			return this.toDomainAnyItemValue(timeValue96, countValue96, moneyValue96, anyItemId);
		case 97:
			return this.toDomainAnyItemValue(timeValue97, countValue97, moneyValue97, anyItemId);
		case 98:
			return this.toDomainAnyItemValue(timeValue98, countValue98, moneyValue98, anyItemId);
		case 99:
			return this.toDomainAnyItemValue(timeValue99, countValue99, moneyValue99, anyItemId);
		case 100:
			return this.toDomainAnyItemValue(timeValue100, countValue100, moneyValue100, anyItemId);
		case 101:
			return this.toDomainAnyItemValue(timeValue101, countValue101, moneyValue101, anyItemId);
		case 102:
			return this.toDomainAnyItemValue(timeValue102, countValue102, moneyValue102, anyItemId);
		case 103:
			return this.toDomainAnyItemValue(timeValue103, countValue103, moneyValue103, anyItemId);
		case 104:
			return this.toDomainAnyItemValue(timeValue104, countValue104, moneyValue104, anyItemId);
		case 105:
			return this.toDomainAnyItemValue(timeValue105, countValue105, moneyValue105, anyItemId);
		case 106:
			return this.toDomainAnyItemValue(timeValue106, countValue106, moneyValue106, anyItemId);
		case 107:
			return this.toDomainAnyItemValue(timeValue107, countValue107, moneyValue107, anyItemId);
		case 108:
			return this.toDomainAnyItemValue(timeValue108, countValue108, moneyValue108, anyItemId);
		case 109:
			return this.toDomainAnyItemValue(timeValue109, countValue109, moneyValue109, anyItemId);
		case 110:
			return this.toDomainAnyItemValue(timeValue110, countValue110, moneyValue110, anyItemId);
		case 111:
			return this.toDomainAnyItemValue(timeValue111, countValue111, moneyValue111, anyItemId);
		case 112:
			return this.toDomainAnyItemValue(timeValue112, countValue112, moneyValue112, anyItemId);
		case 113:
			return this.toDomainAnyItemValue(timeValue113, countValue113, moneyValue113, anyItemId);
		case 114:
			return this.toDomainAnyItemValue(timeValue114, countValue114, moneyValue114, anyItemId);
		case 115:
			return this.toDomainAnyItemValue(timeValue115, countValue115, moneyValue115, anyItemId);
		case 116:
			return this.toDomainAnyItemValue(timeValue116, countValue116, moneyValue116, anyItemId);
		case 117:
			return this.toDomainAnyItemValue(timeValue117, countValue117, moneyValue117, anyItemId);
		case 118:
			return this.toDomainAnyItemValue(timeValue118, countValue118, moneyValue118, anyItemId);
		case 119:
			return this.toDomainAnyItemValue(timeValue119, countValue119, moneyValue119, anyItemId);
		case 120:
			return this.toDomainAnyItemValue(timeValue120, countValue120, moneyValue120, anyItemId);
		case 121:
			return this.toDomainAnyItemValue(timeValue121, countValue121, moneyValue121, anyItemId);
		case 122:
			return this.toDomainAnyItemValue(timeValue122, countValue122, moneyValue122, anyItemId);
		case 123:
			return this.toDomainAnyItemValue(timeValue123, countValue123, moneyValue123, anyItemId);
		case 124:
			return this.toDomainAnyItemValue(timeValue124, countValue124, moneyValue124, anyItemId);
		case 125:
			return this.toDomainAnyItemValue(timeValue125, countValue125, moneyValue125, anyItemId);
		case 126:
			return this.toDomainAnyItemValue(timeValue126, countValue126, moneyValue126, anyItemId);
		case 127:
			return this.toDomainAnyItemValue(timeValue127, countValue127, moneyValue127, anyItemId);
		case 128:
			return this.toDomainAnyItemValue(timeValue128, countValue128, moneyValue128, anyItemId);
		case 129:
			return this.toDomainAnyItemValue(timeValue129, countValue129, moneyValue129, anyItemId);
		case 130:
			return this.toDomainAnyItemValue(timeValue130, countValue130, moneyValue130, anyItemId);
		case 131:
			return this.toDomainAnyItemValue(timeValue131, countValue131, moneyValue131, anyItemId);
		case 132:
			return this.toDomainAnyItemValue(timeValue132, countValue132, moneyValue132, anyItemId);
		case 133:
			return this.toDomainAnyItemValue(timeValue133, countValue133, moneyValue133, anyItemId);
		case 134:
			return this.toDomainAnyItemValue(timeValue134, countValue134, moneyValue134, anyItemId);
		case 135:
			return this.toDomainAnyItemValue(timeValue135, countValue135, moneyValue135, anyItemId);
		case 136:
			return this.toDomainAnyItemValue(timeValue136, countValue136, moneyValue136, anyItemId);
		case 137:
			return this.toDomainAnyItemValue(timeValue137, countValue137, moneyValue137, anyItemId);
		case 138:
			return this.toDomainAnyItemValue(timeValue138, countValue138, moneyValue138, anyItemId);
		case 139:
			return this.toDomainAnyItemValue(timeValue139, countValue139, moneyValue139, anyItemId);
		case 140:
			return this.toDomainAnyItemValue(timeValue140, countValue140, moneyValue140, anyItemId);
		case 141:
			return this.toDomainAnyItemValue(timeValue141, countValue141, moneyValue141, anyItemId);
		case 142:
			return this.toDomainAnyItemValue(timeValue142, countValue142, moneyValue142, anyItemId);
		case 143:
			return this.toDomainAnyItemValue(timeValue143, countValue143, moneyValue143, anyItemId);
		case 144:
			return this.toDomainAnyItemValue(timeValue144, countValue144, moneyValue144, anyItemId);
		case 145:
			return this.toDomainAnyItemValue(timeValue145, countValue145, moneyValue145, anyItemId);
		case 146:
			return this.toDomainAnyItemValue(timeValue146, countValue146, moneyValue146, anyItemId);
		case 147:
			return this.toDomainAnyItemValue(timeValue147, countValue147, moneyValue147, anyItemId);
		case 148:
			return this.toDomainAnyItemValue(timeValue148, countValue148, moneyValue148, anyItemId);
		case 149:
			return this.toDomainAnyItemValue(timeValue149, countValue149, moneyValue149, anyItemId);
		case 150:
			return this.toDomainAnyItemValue(timeValue150, countValue150, moneyValue150, anyItemId);
		case 151:
			return this.toDomainAnyItemValue(timeValue151, countValue151, moneyValue151, anyItemId);
		case 152:
			return this.toDomainAnyItemValue(timeValue152, countValue152, moneyValue152, anyItemId);
		case 153:
			return this.toDomainAnyItemValue(timeValue153, countValue153, moneyValue153, anyItemId);
		case 154:
			return this.toDomainAnyItemValue(timeValue154, countValue154, moneyValue154, anyItemId);
		case 155:
			return this.toDomainAnyItemValue(timeValue155, countValue155, moneyValue155, anyItemId);
		case 156:
			return this.toDomainAnyItemValue(timeValue156, countValue156, moneyValue156, anyItemId);
		case 157:
			return this.toDomainAnyItemValue(timeValue157, countValue157, moneyValue157, anyItemId);
		case 158:
			return this.toDomainAnyItemValue(timeValue158, countValue158, moneyValue158, anyItemId);
		case 159:
			return this.toDomainAnyItemValue(timeValue159, countValue159, moneyValue159, anyItemId);
		case 160:
			return this.toDomainAnyItemValue(timeValue160, countValue160, moneyValue160, anyItemId);
		case 161:
			return this.toDomainAnyItemValue(timeValue161, countValue161, moneyValue161, anyItemId);
		case 162:
			return this.toDomainAnyItemValue(timeValue162, countValue162, moneyValue162, anyItemId);
		case 163:
			return this.toDomainAnyItemValue(timeValue163, countValue163, moneyValue163, anyItemId);
		case 164:
			return this.toDomainAnyItemValue(timeValue164, countValue164, moneyValue164, anyItemId);
		case 165:
			return this.toDomainAnyItemValue(timeValue165, countValue165, moneyValue165, anyItemId);
		case 166:
			return this.toDomainAnyItemValue(timeValue166, countValue166, moneyValue166, anyItemId);
		case 167:
			return this.toDomainAnyItemValue(timeValue167, countValue167, moneyValue167, anyItemId);
		case 168:
			return this.toDomainAnyItemValue(timeValue168, countValue168, moneyValue168, anyItemId);
		case 169:
			return this.toDomainAnyItemValue(timeValue169, countValue169, moneyValue169, anyItemId);
		case 170:
			return this.toDomainAnyItemValue(timeValue170, countValue170, moneyValue170, anyItemId);
		case 171:
			return this.toDomainAnyItemValue(timeValue171, countValue171, moneyValue171, anyItemId);
		case 172:
			return this.toDomainAnyItemValue(timeValue172, countValue172, moneyValue172, anyItemId);
		case 173:
			return this.toDomainAnyItemValue(timeValue173, countValue173, moneyValue173, anyItemId);
		case 174:
			return this.toDomainAnyItemValue(timeValue174, countValue174, moneyValue174, anyItemId);
		case 175:
			return this.toDomainAnyItemValue(timeValue175, countValue175, moneyValue175, anyItemId);
		case 176:
			return this.toDomainAnyItemValue(timeValue176, countValue176, moneyValue176, anyItemId);
		case 177:
			return this.toDomainAnyItemValue(timeValue177, countValue177, moneyValue177, anyItemId);
		case 178:
			return this.toDomainAnyItemValue(timeValue178, countValue178, moneyValue178, anyItemId);
		case 179:
			return this.toDomainAnyItemValue(timeValue179, countValue179, moneyValue179, anyItemId);
		case 180:
			return this.toDomainAnyItemValue(timeValue180, countValue180, moneyValue180, anyItemId);
		case 181:
			return this.toDomainAnyItemValue(timeValue181, countValue181, moneyValue181, anyItemId);
		case 182:
			return this.toDomainAnyItemValue(timeValue182, countValue182, moneyValue182, anyItemId);
		case 183:
			return this.toDomainAnyItemValue(timeValue183, countValue183, moneyValue183, anyItemId);
		case 184:
			return this.toDomainAnyItemValue(timeValue184, countValue184, moneyValue184, anyItemId);
		case 185:
			return this.toDomainAnyItemValue(timeValue185, countValue185, moneyValue185, anyItemId);
		case 186:
			return this.toDomainAnyItemValue(timeValue186, countValue186, moneyValue186, anyItemId);
		case 187:
			return this.toDomainAnyItemValue(timeValue187, countValue187, moneyValue187, anyItemId);
		case 188:
			return this.toDomainAnyItemValue(timeValue188, countValue188, moneyValue188, anyItemId);
		case 189:
			return this.toDomainAnyItemValue(timeValue189, countValue189, moneyValue189, anyItemId);
		case 190:
			return this.toDomainAnyItemValue(timeValue190, countValue190, moneyValue190, anyItemId);
		case 191:
			return this.toDomainAnyItemValue(timeValue191, countValue191, moneyValue191, anyItemId);
		case 192:
			return this.toDomainAnyItemValue(timeValue192, countValue192, moneyValue192, anyItemId);
		case 193:
			return this.toDomainAnyItemValue(timeValue193, countValue193, moneyValue193, anyItemId);
		case 194:
			return this.toDomainAnyItemValue(timeValue194, countValue194, moneyValue194, anyItemId);
		case 195:
			return this.toDomainAnyItemValue(timeValue195, countValue195, moneyValue195, anyItemId);
		case 196:
			return this.toDomainAnyItemValue(timeValue196, countValue196, moneyValue196, anyItemId);
		case 197:
			return this.toDomainAnyItemValue(timeValue197, countValue197, moneyValue197, anyItemId);
		case 198:
			return this.toDomainAnyItemValue(timeValue198, countValue198, moneyValue198, anyItemId);
		case 199:
			return this.toDomainAnyItemValue(timeValue199, countValue199, moneyValue199, anyItemId);
		case 200:
			return this.toDomainAnyItemValue(timeValue200, countValue200, moneyValue200, anyItemId);
		}
		return null;
	}
	
	/**
	 * ドメインに変換
	 * @return AnyItemValueOfDaily
	 */
	public AnyItemValueOfDaily toDomainAnyItemValueOfDaily() {
		List<AnyItemValue> anyItemLst = new ArrayList<>();
		for (int i = 1; i <= 200; i++){
			anyItemLst.add(this.toDomainAnyItemValue(i));
		}
		return new AnyItemValueOfDaily(
				this.krcdtDayTimePk.employeeID,
				this.krcdtDayTimePk.generalDate,
				anyItemLst);
	}
}
