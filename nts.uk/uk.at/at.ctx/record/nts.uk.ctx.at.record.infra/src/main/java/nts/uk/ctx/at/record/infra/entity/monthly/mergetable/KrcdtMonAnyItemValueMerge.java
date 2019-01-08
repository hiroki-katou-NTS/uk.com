package nts.uk.ctx.at.record.infra.entity.monthly.mergetable;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.arc.enums.EnumAdaptor;
import nts.arc.time.YearMonth;
import nts.uk.ctx.at.record.dom.monthly.anyitem.AnyItemOfMonthly;
import nts.uk.ctx.at.record.dom.monthly.mergetable.MonthMergeKey;
import nts.uk.ctx.at.shared.dom.common.anyitem.AnyAmountMonth;
import nts.uk.ctx.at.shared.dom.common.anyitem.AnyTimeMonth;
import nts.uk.ctx.at.shared.dom.common.anyitem.AnyTimesMonth;
import nts.uk.ctx.at.shared.dom.workrule.closure.ClosureId;
import nts.uk.shr.com.time.calendar.date.ClosureDate;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

/**
 * 任意項目
 * 
 * @author lanlt
 *
 */
@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "KRCDT_MON_ANYITEMVALUE_MERGE")
public class KrcdtMonAnyItemValueMerge extends UkJpaEntity implements Serializable {

	/** serialVersionUID */
	private static final long serialVersionUID = 1L;

	/** プライマリキー */
	@EmbeddedId
	public KrcdtMonMergePk krcdtMonAnyItemValuePk;

	/** 時間 */
	/** 振替時間 - TIME_VALUE_1 */
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
		return krcdtMonAnyItemValuePk;
	}
	
	
	public void toEntityAnyItemOfMonthly(AnyItemOfMonthly domain) {
		switch(domain.getAnyItemId()) {
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
	
	/**
	 * ドメインに変換
	 * @return 集計任意項目
	 */
	 
	public 	MonthMergeKey toDomainKey() {
		MonthMergeKey key = new MonthMergeKey();
		key.setEmployeeId(this.krcdtMonAnyItemValuePk.getEmployeeId());
		key.setYearMonth(new YearMonth(this.krcdtMonAnyItemValuePk.getYearMonth()));
		key.setClosureId(EnumAdaptor.valueOf(this.krcdtMonAnyItemValuePk.getClosureId(), ClosureId.class));
		key.setClosureDate(new ClosureDate(this.krcdtMonAnyItemValuePk.getClosureDay(),
			(this.krcdtMonAnyItemValuePk.getIsLastDay() == 1)));
		return key;
		
	}
	
	
	public  AnyItemOfMonthly toDomainAnyItemOfMonthly(Integer timeValue, Double countValue, Integer moneyValue, int anyItemId) {
		// get value anyItemId = 0 temporary because table have not anyitemId
		return AnyItemOfMonthly.of(this.krcdtMonAnyItemValuePk.getEmployeeId(),
				new YearMonth(this.krcdtMonAnyItemValuePk.getYearMonth()),
				EnumAdaptor.valueOf(this.krcdtMonAnyItemValuePk.getClosureId(), ClosureId.class),
				new ClosureDate(this.krcdtMonAnyItemValuePk.getClosureDay(),
					(this.krcdtMonAnyItemValuePk.getIsLastDay() == 1)),
				anyItemId, (timeValue == null ? Optional.empty() : Optional.of(new AnyTimeMonth(timeValue))),
			(countValue == null ? Optional.empty() : Optional.of(new AnyTimesMonth(countValue))),
			(moneyValue == null ? Optional.empty() : Optional.of(new AnyAmountMonth(moneyValue))));
	}
	
	public List<AnyItemOfMonthly> toDomainAnyItemOfMonthly() {
		List<AnyItemOfMonthly> anyItemLst = new ArrayList<>();
		anyItemLst.add(toDomainAnyItemOfMonthly(this.timeValue1, this.countValue1, this.moneyValue1, 1));
		anyItemLst.add(toDomainAnyItemOfMonthly(this.timeValue2, this.countValue2, this.moneyValue2, 2));
		anyItemLst.add(toDomainAnyItemOfMonthly(this.timeValue3, this.countValue3, this.moneyValue3, 3));
		anyItemLst.add(toDomainAnyItemOfMonthly(this.timeValue4, this.countValue4, this.moneyValue4, 4));
		anyItemLst.add(toDomainAnyItemOfMonthly(this.timeValue5, this.countValue5, this.moneyValue5, 5));
		anyItemLst.add(toDomainAnyItemOfMonthly(this.timeValue6, this.countValue6, this.moneyValue6, 6));
		anyItemLst.add(toDomainAnyItemOfMonthly(this.timeValue7, this.countValue7, this.moneyValue7, 7));
		anyItemLst.add(toDomainAnyItemOfMonthly(this.timeValue8, this.countValue8, this.moneyValue8, 8));
		anyItemLst.add(toDomainAnyItemOfMonthly(this.timeValue9, this.countValue9, this.moneyValue9, 9));
		anyItemLst.add(toDomainAnyItemOfMonthly(this.timeValue10, this.countValue10, this.moneyValue10, 10));
		anyItemLst.add(toDomainAnyItemOfMonthly(this.timeValue11, this.countValue11, this.moneyValue11, 11));
		anyItemLst.add(toDomainAnyItemOfMonthly(this.timeValue12, this.countValue12, this.moneyValue12, 12));
		anyItemLst.add(toDomainAnyItemOfMonthly(this.timeValue13, this.countValue13, this.moneyValue13, 13));
		anyItemLst.add(toDomainAnyItemOfMonthly(this.timeValue14, this.countValue14, this.moneyValue14, 14));
		anyItemLst.add(toDomainAnyItemOfMonthly(this.timeValue15, this.countValue15, this.moneyValue15, 15));
		anyItemLst.add(toDomainAnyItemOfMonthly(this.timeValue16, this.countValue16, this.moneyValue16, 16));
		anyItemLst.add(toDomainAnyItemOfMonthly(this.timeValue17, this.countValue17, this.moneyValue17, 17));
		anyItemLst.add(toDomainAnyItemOfMonthly(this.timeValue18, this.countValue18, this.moneyValue18, 18));
		anyItemLst.add(toDomainAnyItemOfMonthly(this.timeValue19, this.countValue19, this.moneyValue19, 19));
		anyItemLst.add(toDomainAnyItemOfMonthly(this.timeValue20, this.countValue20, this.moneyValue20, 20));
		anyItemLst.add(toDomainAnyItemOfMonthly(this.timeValue21, this.countValue21, this.moneyValue21, 21));
		anyItemLst.add(toDomainAnyItemOfMonthly(this.timeValue22, this.countValue22, this.moneyValue22, 22));
		anyItemLst.add(toDomainAnyItemOfMonthly(this.timeValue23, this.countValue23, this.moneyValue23, 23));
		anyItemLst.add(toDomainAnyItemOfMonthly(this.timeValue24, this.countValue24, this.moneyValue24, 24));
		anyItemLst.add(toDomainAnyItemOfMonthly(this.timeValue25, this.countValue25, this.moneyValue25, 25));
		anyItemLst.add(toDomainAnyItemOfMonthly(this.timeValue26, this.countValue26, this.moneyValue26, 26));
		anyItemLst.add(toDomainAnyItemOfMonthly(this.timeValue27, this.countValue27, this.moneyValue27, 27));
		anyItemLst.add(toDomainAnyItemOfMonthly(this.timeValue28, this.countValue28, this.moneyValue28, 28));
		anyItemLst.add(toDomainAnyItemOfMonthly(this.timeValue29, this.countValue29, this.moneyValue29, 29));
		anyItemLst.add(toDomainAnyItemOfMonthly(this.timeValue30, this.countValue30, this.moneyValue30, 30));
		anyItemLst.add(toDomainAnyItemOfMonthly(this.timeValue31, this.countValue31, this.moneyValue31, 31));
		anyItemLst.add(toDomainAnyItemOfMonthly(this.timeValue32, this.countValue32, this.moneyValue32, 32));
		anyItemLst.add(toDomainAnyItemOfMonthly(this.timeValue33, this.countValue33, this.moneyValue33, 33));
		anyItemLst.add(toDomainAnyItemOfMonthly(this.timeValue34, this.countValue34, this.moneyValue34, 34));
		anyItemLst.add(toDomainAnyItemOfMonthly(this.timeValue35, this.countValue35, this.moneyValue35, 35));
		anyItemLst.add(toDomainAnyItemOfMonthly(this.timeValue36, this.countValue36, this.moneyValue36, 36));
		anyItemLst.add(toDomainAnyItemOfMonthly(this.timeValue37, this.countValue37, this.moneyValue37, 37));
		anyItemLst.add(toDomainAnyItemOfMonthly(this.timeValue38, this.countValue38, this.moneyValue38, 38));
		anyItemLst.add(toDomainAnyItemOfMonthly(this.timeValue39, this.countValue39, this.moneyValue39, 39));
		anyItemLst.add(toDomainAnyItemOfMonthly(this.timeValue40, this.countValue40, this.moneyValue40, 40));
		anyItemLst.add(toDomainAnyItemOfMonthly(this.timeValue41, this.countValue41, this.moneyValue41, 41));
		anyItemLst.add(toDomainAnyItemOfMonthly(this.timeValue42, this.countValue42, this.moneyValue42, 42));
		anyItemLst.add(toDomainAnyItemOfMonthly(this.timeValue43, this.countValue43, this.moneyValue43, 43));
		anyItemLst.add(toDomainAnyItemOfMonthly(this.timeValue44, this.countValue44, this.moneyValue44, 44));
		anyItemLst.add(toDomainAnyItemOfMonthly(this.timeValue45, this.countValue45, this.moneyValue45, 45));
		anyItemLst.add(toDomainAnyItemOfMonthly(this.timeValue46, this.countValue46, this.moneyValue46, 46));
		anyItemLst.add(toDomainAnyItemOfMonthly(this.timeValue47, this.countValue47, this.moneyValue47, 47));
		anyItemLst.add(toDomainAnyItemOfMonthly(this.timeValue48, this.countValue48, this.moneyValue48, 48));
		anyItemLst.add(toDomainAnyItemOfMonthly(this.timeValue49, this.countValue49, this.moneyValue49, 49));
		anyItemLst.add(toDomainAnyItemOfMonthly(this.timeValue50, this.countValue50, this.moneyValue50, 50));
		anyItemLst.add(toDomainAnyItemOfMonthly(this.timeValue51, this.countValue51, this.moneyValue51, 51));
		anyItemLst.add(toDomainAnyItemOfMonthly(this.timeValue52, this.countValue52, this.moneyValue52, 52));
		anyItemLst.add(toDomainAnyItemOfMonthly(this.timeValue53, this.countValue53, this.moneyValue53, 53));
		anyItemLst.add(toDomainAnyItemOfMonthly(this.timeValue54, this.countValue54, this.moneyValue54, 54));
		anyItemLst.add(toDomainAnyItemOfMonthly(this.timeValue55, this.countValue55, this.moneyValue55, 55));
		anyItemLst.add(toDomainAnyItemOfMonthly(this.timeValue56, this.countValue56, this.moneyValue56, 56));
		anyItemLst.add(toDomainAnyItemOfMonthly(this.timeValue57, this.countValue57, this.moneyValue57, 57));
		anyItemLst.add(toDomainAnyItemOfMonthly(this.timeValue58, this.countValue58, this.moneyValue58, 58));
		anyItemLst.add(toDomainAnyItemOfMonthly(this.timeValue59, this.countValue59, this.moneyValue59, 59));
		anyItemLst.add(toDomainAnyItemOfMonthly(this.timeValue60, this.countValue60, this.moneyValue60, 60));
		anyItemLst.add(toDomainAnyItemOfMonthly(this.timeValue61, this.countValue61, this.moneyValue61, 61));
		anyItemLst.add(toDomainAnyItemOfMonthly(this.timeValue62, this.countValue62, this.moneyValue62, 62));
		anyItemLst.add(toDomainAnyItemOfMonthly(this.timeValue63, this.countValue63, this.moneyValue63, 63));
		anyItemLst.add(toDomainAnyItemOfMonthly(this.timeValue64, this.countValue64, this.moneyValue64, 64));
		anyItemLst.add(toDomainAnyItemOfMonthly(this.timeValue65, this.countValue65, this.moneyValue65, 65));
		anyItemLst.add(toDomainAnyItemOfMonthly(this.timeValue66, this.countValue66, this.moneyValue66, 66));
		anyItemLst.add(toDomainAnyItemOfMonthly(this.timeValue67, this.countValue67, this.moneyValue67, 67));
		anyItemLst.add(toDomainAnyItemOfMonthly(this.timeValue68, this.countValue68, this.moneyValue68, 68));
		anyItemLst.add(toDomainAnyItemOfMonthly(this.timeValue69, this.countValue69, this.moneyValue69, 69));
		anyItemLst.add(toDomainAnyItemOfMonthly(this.timeValue70, this.countValue70, this.moneyValue70, 70));
		anyItemLst.add(toDomainAnyItemOfMonthly(this.timeValue71, this.countValue71, this.moneyValue71, 71));
		anyItemLst.add(toDomainAnyItemOfMonthly(this.timeValue72, this.countValue72, this.moneyValue72, 72));
		anyItemLst.add(toDomainAnyItemOfMonthly(this.timeValue73, this.countValue73, this.moneyValue73, 73));
		anyItemLst.add(toDomainAnyItemOfMonthly(this.timeValue74, this.countValue74, this.moneyValue74, 74));
		anyItemLst.add(toDomainAnyItemOfMonthly(this.timeValue75, this.countValue75, this.moneyValue75, 75));
		anyItemLst.add(toDomainAnyItemOfMonthly(this.timeValue76, this.countValue76, this.moneyValue76, 76));
		anyItemLst.add(toDomainAnyItemOfMonthly(this.timeValue77, this.countValue77, this.moneyValue77, 77));
		anyItemLst.add(toDomainAnyItemOfMonthly(this.timeValue78, this.countValue78, this.moneyValue78, 78));
		anyItemLst.add(toDomainAnyItemOfMonthly(this.timeValue79, this.countValue79, this.moneyValue79, 79));
		anyItemLst.add(toDomainAnyItemOfMonthly(this.timeValue80, this.countValue80, this.moneyValue80, 80));
		anyItemLst.add(toDomainAnyItemOfMonthly(this.timeValue81, this.countValue81, this.moneyValue81, 81));
		anyItemLst.add(toDomainAnyItemOfMonthly(this.timeValue82, this.countValue82, this.moneyValue82, 82));
		anyItemLst.add(toDomainAnyItemOfMonthly(this.timeValue83, this.countValue83, this.moneyValue83, 83));
		anyItemLst.add(toDomainAnyItemOfMonthly(this.timeValue84, this.countValue84, this.moneyValue84, 84));
		anyItemLst.add(toDomainAnyItemOfMonthly(this.timeValue85, this.countValue85, this.moneyValue85, 85));
		anyItemLst.add(toDomainAnyItemOfMonthly(this.timeValue86, this.countValue86, this.moneyValue86, 86));
		anyItemLst.add(toDomainAnyItemOfMonthly(this.timeValue87, this.countValue87, this.moneyValue87, 87));
		anyItemLst.add(toDomainAnyItemOfMonthly(this.timeValue88, this.countValue88, this.moneyValue88, 88));
		anyItemLst.add(toDomainAnyItemOfMonthly(this.timeValue89, this.countValue89, this.moneyValue89, 89));
		anyItemLst.add(toDomainAnyItemOfMonthly(this.timeValue90, this.countValue90, this.moneyValue90, 90));
		anyItemLst.add(toDomainAnyItemOfMonthly(this.timeValue91, this.countValue91, this.moneyValue91, 91));
		anyItemLst.add(toDomainAnyItemOfMonthly(this.timeValue92, this.countValue92, this.moneyValue92, 92));
		anyItemLst.add(toDomainAnyItemOfMonthly(this.timeValue93, this.countValue93, this.moneyValue93, 93));
		anyItemLst.add(toDomainAnyItemOfMonthly(this.timeValue94, this.countValue94, this.moneyValue94, 94));
		anyItemLst.add(toDomainAnyItemOfMonthly(this.timeValue95, this.countValue95, this.moneyValue95, 95));
		anyItemLst.add(toDomainAnyItemOfMonthly(this.timeValue96, this.countValue96, this.moneyValue96, 96));
		anyItemLst.add(toDomainAnyItemOfMonthly(this.timeValue97, this.countValue97, this.moneyValue97, 97));
		anyItemLst.add(toDomainAnyItemOfMonthly(this.timeValue98, this.countValue98, this.moneyValue98, 98));
		anyItemLst.add(toDomainAnyItemOfMonthly(this.timeValue99, this.countValue99, this.moneyValue99, 99));
		anyItemLst.add(toDomainAnyItemOfMonthly(this.timeValue100, this.countValue100, this.moneyValue100, 100));
		anyItemLst.add(toDomainAnyItemOfMonthly(this.timeValue101, this.countValue101, this.moneyValue101, 101));
		anyItemLst.add(toDomainAnyItemOfMonthly(this.timeValue102, this.countValue102, this.moneyValue102, 102));
		anyItemLst.add(toDomainAnyItemOfMonthly(this.timeValue103, this.countValue103, this.moneyValue103, 103));
		anyItemLst.add(toDomainAnyItemOfMonthly(this.timeValue104, this.countValue104, this.moneyValue104, 104));
		anyItemLst.add(toDomainAnyItemOfMonthly(this.timeValue105, this.countValue105, this.moneyValue105, 105));
		anyItemLst.add(toDomainAnyItemOfMonthly(this.timeValue106, this.countValue106, this.moneyValue106, 106));
		anyItemLst.add(toDomainAnyItemOfMonthly(this.timeValue107, this.countValue107, this.moneyValue107, 107));
		anyItemLst.add(toDomainAnyItemOfMonthly(this.timeValue108, this.countValue108, this.moneyValue108, 108));
		anyItemLst.add(toDomainAnyItemOfMonthly(this.timeValue109, this.countValue109, this.moneyValue109, 109));
		anyItemLst.add(toDomainAnyItemOfMonthly(this.timeValue110, this.countValue110, this.moneyValue110, 110));
		anyItemLst.add(toDomainAnyItemOfMonthly(this.timeValue111, this.countValue111, this.moneyValue111, 111));
		anyItemLst.add(toDomainAnyItemOfMonthly(this.timeValue112, this.countValue112, this.moneyValue112, 112));
		anyItemLst.add(toDomainAnyItemOfMonthly(this.timeValue113, this.countValue113, this.moneyValue113, 113));
		anyItemLst.add(toDomainAnyItemOfMonthly(this.timeValue114, this.countValue114, this.moneyValue114, 114));
		anyItemLst.add(toDomainAnyItemOfMonthly(this.timeValue115, this.countValue115, this.moneyValue115, 115));
		anyItemLst.add(toDomainAnyItemOfMonthly(this.timeValue116, this.countValue116, this.moneyValue116, 116));
		anyItemLst.add(toDomainAnyItemOfMonthly(this.timeValue117, this.countValue117, this.moneyValue117, 117));
		anyItemLst.add(toDomainAnyItemOfMonthly(this.timeValue118, this.countValue118, this.moneyValue118, 118));
		anyItemLst.add(toDomainAnyItemOfMonthly(this.timeValue119, this.countValue119, this.moneyValue119, 119));
		anyItemLst.add(toDomainAnyItemOfMonthly(this.timeValue120, this.countValue120, this.moneyValue120, 120));
		anyItemLst.add(toDomainAnyItemOfMonthly(this.timeValue121, this.countValue121, this.moneyValue121, 121));
		anyItemLst.add(toDomainAnyItemOfMonthly(this.timeValue122, this.countValue122, this.moneyValue122, 122));
		anyItemLst.add(toDomainAnyItemOfMonthly(this.timeValue123, this.countValue123, this.moneyValue123, 123));
		anyItemLst.add(toDomainAnyItemOfMonthly(this.timeValue124, this.countValue124, this.moneyValue124, 124));
		anyItemLst.add(toDomainAnyItemOfMonthly(this.timeValue125, this.countValue125, this.moneyValue125, 125));
		anyItemLst.add(toDomainAnyItemOfMonthly(this.timeValue126, this.countValue126, this.moneyValue126, 126));
		anyItemLst.add(toDomainAnyItemOfMonthly(this.timeValue127, this.countValue127, this.moneyValue127, 127));
		anyItemLst.add(toDomainAnyItemOfMonthly(this.timeValue128, this.countValue128, this.moneyValue128, 128));
		anyItemLst.add(toDomainAnyItemOfMonthly(this.timeValue129, this.countValue129, this.moneyValue129, 129));
		anyItemLst.add(toDomainAnyItemOfMonthly(this.timeValue130, this.countValue130, this.moneyValue130, 130));
		anyItemLst.add(toDomainAnyItemOfMonthly(this.timeValue131, this.countValue131, this.moneyValue131, 131));
		anyItemLst.add(toDomainAnyItemOfMonthly(this.timeValue132, this.countValue132, this.moneyValue132, 132));
		anyItemLst.add(toDomainAnyItemOfMonthly(this.timeValue133, this.countValue133, this.moneyValue133, 133));
		anyItemLst.add(toDomainAnyItemOfMonthly(this.timeValue134, this.countValue134, this.moneyValue134, 134));
		anyItemLst.add(toDomainAnyItemOfMonthly(this.timeValue135, this.countValue135, this.moneyValue135, 135));
		anyItemLst.add(toDomainAnyItemOfMonthly(this.timeValue136, this.countValue136, this.moneyValue136, 136));
		anyItemLst.add(toDomainAnyItemOfMonthly(this.timeValue137, this.countValue137, this.moneyValue137, 137));
		anyItemLst.add(toDomainAnyItemOfMonthly(this.timeValue138, this.countValue138, this.moneyValue138, 138));
		anyItemLst.add(toDomainAnyItemOfMonthly(this.timeValue139, this.countValue139, this.moneyValue139, 139));
		anyItemLst.add(toDomainAnyItemOfMonthly(this.timeValue140, this.countValue140, this.moneyValue140, 140));
		anyItemLst.add(toDomainAnyItemOfMonthly(this.timeValue141, this.countValue141, this.moneyValue141, 141));
		anyItemLst.add(toDomainAnyItemOfMonthly(this.timeValue142, this.countValue142, this.moneyValue142, 142));
		anyItemLst.add(toDomainAnyItemOfMonthly(this.timeValue143, this.countValue143, this.moneyValue143, 143));
		anyItemLst.add(toDomainAnyItemOfMonthly(this.timeValue144, this.countValue144, this.moneyValue144, 144));
		anyItemLst.add(toDomainAnyItemOfMonthly(this.timeValue145, this.countValue145, this.moneyValue145, 145));
		anyItemLst.add(toDomainAnyItemOfMonthly(this.timeValue146, this.countValue146, this.moneyValue146, 146));
		anyItemLst.add(toDomainAnyItemOfMonthly(this.timeValue147, this.countValue147, this.moneyValue147, 147));
		anyItemLst.add(toDomainAnyItemOfMonthly(this.timeValue148, this.countValue148, this.moneyValue148, 148));
		anyItemLst.add(toDomainAnyItemOfMonthly(this.timeValue149, this.countValue149, this.moneyValue149, 149));
		anyItemLst.add(toDomainAnyItemOfMonthly(this.timeValue150, this.countValue150, this.moneyValue150, 150));
		anyItemLst.add(toDomainAnyItemOfMonthly(this.timeValue151, this.countValue151, this.moneyValue151, 151));
		anyItemLst.add(toDomainAnyItemOfMonthly(this.timeValue152, this.countValue152, this.moneyValue152, 152));
		anyItemLst.add(toDomainAnyItemOfMonthly(this.timeValue153, this.countValue153, this.moneyValue153, 153));
		anyItemLst.add(toDomainAnyItemOfMonthly(this.timeValue154, this.countValue154, this.moneyValue154, 154));
		anyItemLst.add(toDomainAnyItemOfMonthly(this.timeValue155, this.countValue155, this.moneyValue155, 155));
		anyItemLst.add(toDomainAnyItemOfMonthly(this.timeValue156, this.countValue156, this.moneyValue156, 156));
		anyItemLst.add(toDomainAnyItemOfMonthly(this.timeValue157, this.countValue157, this.moneyValue157, 157));
		anyItemLst.add(toDomainAnyItemOfMonthly(this.timeValue158, this.countValue158, this.moneyValue158, 158));
		anyItemLst.add(toDomainAnyItemOfMonthly(this.timeValue159, this.countValue159, this.moneyValue159, 159));
		anyItemLst.add(toDomainAnyItemOfMonthly(this.timeValue160, this.countValue160, this.moneyValue160, 160));
		anyItemLst.add(toDomainAnyItemOfMonthly(this.timeValue161, this.countValue161, this.moneyValue161, 161));
		anyItemLst.add(toDomainAnyItemOfMonthly(this.timeValue162, this.countValue162, this.moneyValue162, 162));
		anyItemLst.add(toDomainAnyItemOfMonthly(this.timeValue163, this.countValue163, this.moneyValue163, 163));
		anyItemLst.add(toDomainAnyItemOfMonthly(this.timeValue164, this.countValue164, this.moneyValue164, 164));
		anyItemLst.add(toDomainAnyItemOfMonthly(this.timeValue165, this.countValue165, this.moneyValue165, 165));
		anyItemLst.add(toDomainAnyItemOfMonthly(this.timeValue166, this.countValue166, this.moneyValue166, 166));
		anyItemLst.add(toDomainAnyItemOfMonthly(this.timeValue167, this.countValue167, this.moneyValue167, 167));
		anyItemLst.add(toDomainAnyItemOfMonthly(this.timeValue168, this.countValue168, this.moneyValue168, 168));
		anyItemLst.add(toDomainAnyItemOfMonthly(this.timeValue169, this.countValue169, this.moneyValue169, 169));
		anyItemLst.add(toDomainAnyItemOfMonthly(this.timeValue170, this.countValue170, this.moneyValue170, 170));
		anyItemLst.add(toDomainAnyItemOfMonthly(this.timeValue171, this.countValue171, this.moneyValue171, 171));
		anyItemLst.add(toDomainAnyItemOfMonthly(this.timeValue172, this.countValue172, this.moneyValue172, 172));
		anyItemLst.add(toDomainAnyItemOfMonthly(this.timeValue173, this.countValue173, this.moneyValue173, 173));
		anyItemLst.add(toDomainAnyItemOfMonthly(this.timeValue174, this.countValue174, this.moneyValue174, 174));
		anyItemLst.add(toDomainAnyItemOfMonthly(this.timeValue175, this.countValue175, this.moneyValue175, 175));
		anyItemLst.add(toDomainAnyItemOfMonthly(this.timeValue176, this.countValue176, this.moneyValue176, 176));
		anyItemLst.add(toDomainAnyItemOfMonthly(this.timeValue177, this.countValue177, this.moneyValue177, 177));
		anyItemLst.add(toDomainAnyItemOfMonthly(this.timeValue178, this.countValue178, this.moneyValue178, 178));
		anyItemLst.add(toDomainAnyItemOfMonthly(this.timeValue179, this.countValue179, this.moneyValue179, 179));
		anyItemLst.add(toDomainAnyItemOfMonthly(this.timeValue180, this.countValue180, this.moneyValue180, 180));
		anyItemLst.add(toDomainAnyItemOfMonthly(this.timeValue181, this.countValue181, this.moneyValue181, 181));
		anyItemLst.add(toDomainAnyItemOfMonthly(this.timeValue182, this.countValue182, this.moneyValue182, 182));
		anyItemLst.add(toDomainAnyItemOfMonthly(this.timeValue183, this.countValue183, this.moneyValue183, 183));
		anyItemLst.add(toDomainAnyItemOfMonthly(this.timeValue184, this.countValue184, this.moneyValue184, 184));
		anyItemLst.add(toDomainAnyItemOfMonthly(this.timeValue185, this.countValue185, this.moneyValue185, 185));
		anyItemLst.add(toDomainAnyItemOfMonthly(this.timeValue186, this.countValue186, this.moneyValue186, 186));
		anyItemLst.add(toDomainAnyItemOfMonthly(this.timeValue187, this.countValue187, this.moneyValue187, 187));
		anyItemLst.add(toDomainAnyItemOfMonthly(this.timeValue188, this.countValue188, this.moneyValue188, 188));
		anyItemLst.add(toDomainAnyItemOfMonthly(this.timeValue189, this.countValue189, this.moneyValue189, 189));
		anyItemLst.add(toDomainAnyItemOfMonthly(this.timeValue190, this.countValue190, this.moneyValue190, 190));
		anyItemLst.add(toDomainAnyItemOfMonthly(this.timeValue191, this.countValue191, this.moneyValue191, 191));
		anyItemLst.add(toDomainAnyItemOfMonthly(this.timeValue192, this.countValue192, this.moneyValue192, 192));
		anyItemLst.add(toDomainAnyItemOfMonthly(this.timeValue193, this.countValue193, this.moneyValue193, 193));
		anyItemLst.add(toDomainAnyItemOfMonthly(this.timeValue194, this.countValue194, this.moneyValue194, 194));
		anyItemLst.add(toDomainAnyItemOfMonthly(this.timeValue195, this.countValue195, this.moneyValue195, 195));
		anyItemLst.add(toDomainAnyItemOfMonthly(this.timeValue196, this.countValue196, this.moneyValue196, 196));
		anyItemLst.add(toDomainAnyItemOfMonthly(this.timeValue197, this.countValue197, this.moneyValue197, 197));
		anyItemLst.add(toDomainAnyItemOfMonthly(this.timeValue198, this.countValue198, this.moneyValue198, 198));
		anyItemLst.add(toDomainAnyItemOfMonthly(this.timeValue199, this.countValue199, this.moneyValue199, 199));
		anyItemLst.add(toDomainAnyItemOfMonthly(this.timeValue200, this.countValue200, this.moneyValue200, 200));

		return anyItemLst;
	}

}
