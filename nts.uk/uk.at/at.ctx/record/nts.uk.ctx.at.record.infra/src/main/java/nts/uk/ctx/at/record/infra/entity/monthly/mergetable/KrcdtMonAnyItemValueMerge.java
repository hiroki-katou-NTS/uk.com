package nts.uk.ctx.at.record.infra.entity.monthly.mergetable;

import java.io.Serializable;
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
import nts.uk.ctx.at.record.dom.monthly.mergetable.AnyItemMerge;
import nts.uk.ctx.at.record.dom.monthly.mergetable.AnyItemOfMonthlyMerge;
import nts.uk.ctx.at.record.dom.monthly.mergetable.MonthMergeKey;
import nts.uk.ctx.at.shared.dom.common.anyitem.AnyAmountMonth;
import nts.uk.ctx.at.shared.dom.common.anyitem.AnyTimeMonth;
import nts.uk.ctx.at.shared.dom.common.anyitem.AnyTimesMonth;
import nts.uk.ctx.at.shared.dom.workrule.closure.ClosureDate;
import nts.uk.ctx.at.shared.dom.workrule.closure.ClosureId;
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

	@Column(name = "TIME_VALUE_4")
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

	@Column(name = "TIME_VALUE_14")
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

	@Column(name = "COUNT_VALUE_4")
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

	@Column(name = "COUNT_VALUE_14")
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

	@Column(name = "MONEY_VALUE_4")
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

	@Column(name = "MONEY_VALUE_14")
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
	
	public void toEntityAnyItemOfMonthly1(AnyItemOfMonthly domain) {
		this.timeValue1 = (domain.getTime().isPresent() ? domain.getTime().get().v() : null);
		this.countValue1 = (domain.getTimes().isPresent() ? domain.getTimes().get().v().doubleValue() : null);
		this.moneyValue1 = (domain.getAmount().isPresent() ? domain.getAmount().get().v() : null);
	}

	public void toEntityAnyItemOfMonthly2(AnyItemOfMonthly domain) {
		this.timeValue2 = (domain.getTime().isPresent() ? domain.getTime().get().v() : null);
		this.countValue2 = (domain.getTimes().isPresent() ? domain.getTimes().get().v().doubleValue() : null);
		this.moneyValue2 = (domain.getAmount().isPresent() ? domain.getAmount().get().v() : null);
	}

	public void toEntityAnyItemOfMonthly3(AnyItemOfMonthly domain) {
		this.timeValue3 = (domain.getTime().isPresent() ? domain.getTime().get().v() : null);
		this.countValue3 = (domain.getTimes().isPresent() ? domain.getTimes().get().v().doubleValue() : null);
		this.moneyValue3 = (domain.getAmount().isPresent() ? domain.getAmount().get().v() : null);
	}

	public void toEntityAnyItemOfMonthly4(AnyItemOfMonthly domain) {
		this.timeValue4 = (domain.getTime().isPresent() ? domain.getTime().get().v() : null);
		this.countValue4 = (domain.getTimes().isPresent() ? domain.getTimes().get().v().doubleValue() : null);
		this.moneyValue4 = (domain.getAmount().isPresent() ? domain.getAmount().get().v() : null);
	}

	public void toEntityAnyItemOfMonthly5(AnyItemOfMonthly domain) {
		this.timeValue5 = (domain.getTime().isPresent() ? domain.getTime().get().v() : null);
		this.countValue5 = (domain.getTimes().isPresent() ? domain.getTimes().get().v().doubleValue() : null);
		this.moneyValue5 = (domain.getAmount().isPresent() ? domain.getAmount().get().v() : null);
	}

	public void toEntityAnyItemOfMonthly6(AnyItemOfMonthly domain) {
		this.timeValue6 = (domain.getTime().isPresent() ? domain.getTime().get().v() : null);
		this.countValue6 = (domain.getTimes().isPresent() ? domain.getTimes().get().v().doubleValue() : null);
		this.moneyValue6 = (domain.getAmount().isPresent() ? domain.getAmount().get().v() : null);
	}

	public void toEntityAnyItemOfMonthly7(AnyItemOfMonthly domain) {
		this.timeValue7 = (domain.getTime().isPresent() ? domain.getTime().get().v() : null);
		this.countValue7 = (domain.getTimes().isPresent() ? domain.getTimes().get().v().doubleValue() : null);
		this.moneyValue7 = (domain.getAmount().isPresent() ? domain.getAmount().get().v() : null);
	}

	public void toEntityAnyItemOfMonthly8(AnyItemOfMonthly domain) {
		this.timeValue8 = (domain.getTime().isPresent() ? domain.getTime().get().v() : null);
		this.countValue8 = (domain.getTimes().isPresent() ? domain.getTimes().get().v().doubleValue() : null);
		this.moneyValue8 = (domain.getAmount().isPresent() ? domain.getAmount().get().v() : null);
	}

	public void toEntityAnyItemOfMonthly9(AnyItemOfMonthly domain) {
		this.timeValue9 = (domain.getTime().isPresent() ? domain.getTime().get().v() : null);
		this.countValue9 = (domain.getTimes().isPresent() ? domain.getTimes().get().v().doubleValue() : null);
		this.moneyValue9 = (domain.getAmount().isPresent() ? domain.getAmount().get().v() : null);
	}

	public void toEntityAnyItemOfMonthly10(AnyItemOfMonthly domain) {
		this.timeValue10 = (domain.getTime().isPresent() ? domain.getTime().get().v() : null);
		this.countValue10 = (domain.getTimes().isPresent() ? domain.getTimes().get().v().doubleValue() : null);
		this.moneyValue10 = (domain.getAmount().isPresent() ? domain.getAmount().get().v() : null);
	}

	public void toEntityAnyItemOfMonthly11(AnyItemOfMonthly domain) {
		this.timeValue11 = (domain.getTime().isPresent() ? domain.getTime().get().v() : null);
		this.countValue11 = (domain.getTimes().isPresent() ? domain.getTimes().get().v().doubleValue() : null);
		this.moneyValue11 = (domain.getAmount().isPresent() ? domain.getAmount().get().v() : null);
	}

	public void toEntityAnyItemOfMonthly12(AnyItemOfMonthly domain) {
		this.timeValue12 = (domain.getTime().isPresent() ? domain.getTime().get().v() : null);
		this.countValue12 = (domain.getTimes().isPresent() ? domain.getTimes().get().v().doubleValue() : null);
		this.moneyValue12 = (domain.getAmount().isPresent() ? domain.getAmount().get().v() : null);
	}

	public void toEntityAnyItemOfMonthly13(AnyItemOfMonthly domain) {
		this.timeValue13 = (domain.getTime().isPresent() ? domain.getTime().get().v() : null);
		this.countValue13 = (domain.getTimes().isPresent() ? domain.getTimes().get().v().doubleValue() : null);
		this.moneyValue13 = (domain.getAmount().isPresent() ? domain.getAmount().get().v() : null);
	}

	public void toEntityAnyItemOfMonthly14(AnyItemOfMonthly domain) {
		this.timeValue14 = (domain.getTime().isPresent() ? domain.getTime().get().v() : null);
		this.countValue14 = (domain.getTimes().isPresent() ? domain.getTimes().get().v().doubleValue() : null);
		this.moneyValue14 = (domain.getAmount().isPresent() ? domain.getAmount().get().v() : null);
	}

	public void toEntityAnyItemOfMonthly15(AnyItemOfMonthly domain) {
		this.timeValue15 = (domain.getTime().isPresent() ? domain.getTime().get().v() : null);
		this.countValue15 = (domain.getTimes().isPresent() ? domain.getTimes().get().v().doubleValue() : null);
		this.moneyValue15 = (domain.getAmount().isPresent() ? domain.getAmount().get().v() : null);
	}

	public void toEntityAnyItemOfMonthly16(AnyItemOfMonthly domain) {
		this.timeValue16 = (domain.getTime().isPresent() ? domain.getTime().get().v() : null);
		this.countValue16 = (domain.getTimes().isPresent() ? domain.getTimes().get().v().doubleValue() : null);
		this.moneyValue16 = (domain.getAmount().isPresent() ? domain.getAmount().get().v() : null);
	}

	public void toEntityAnyItemOfMonthly17(AnyItemOfMonthly domain) {
		this.timeValue17 = (domain.getTime().isPresent() ? domain.getTime().get().v() : null);
		this.countValue17 = (domain.getTimes().isPresent() ? domain.getTimes().get().v().doubleValue() : null);
		this.moneyValue17 = (domain.getAmount().isPresent() ? domain.getAmount().get().v() : null);
	}

	public void toEntityAnyItemOfMonthly18(AnyItemOfMonthly domain) {
		this.timeValue18 = (domain.getTime().isPresent() ? domain.getTime().get().v() : null);
		this.countValue18 = (domain.getTimes().isPresent() ? domain.getTimes().get().v().doubleValue() : null);
		this.moneyValue18 = (domain.getAmount().isPresent() ? domain.getAmount().get().v() : null);
	}

	public void toEntityAnyItemOfMonthly19(AnyItemOfMonthly domain) {
		this.timeValue19 = (domain.getTime().isPresent() ? domain.getTime().get().v() : null);
		this.countValue19 = (domain.getTimes().isPresent() ? domain.getTimes().get().v().doubleValue() : null);
		this.moneyValue19 = (domain.getAmount().isPresent() ? domain.getAmount().get().v() : null);
	}

	public void toEntityAnyItemOfMonthly20(AnyItemOfMonthly domain) {
		this.timeValue20 = (domain.getTime().isPresent() ? domain.getTime().get().v() : null);
		this.countValue20 = (domain.getTimes().isPresent() ? domain.getTimes().get().v().doubleValue() : null);
		this.moneyValue20 = (domain.getAmount().isPresent() ? domain.getAmount().get().v() : null);
	}

	public void toEntityAnyItemOfMonthly21(AnyItemOfMonthly domain) {
		this.timeValue21 = (domain.getTime().isPresent() ? domain.getTime().get().v() : null);
		this.countValue21 = (domain.getTimes().isPresent() ? domain.getTimes().get().v().doubleValue() : null);
		this.moneyValue21 = (domain.getAmount().isPresent() ? domain.getAmount().get().v() : null);
	}

	public void toEntityAnyItemOfMonthly22(AnyItemOfMonthly domain) {
		this.timeValue22 = (domain.getTime().isPresent() ? domain.getTime().get().v() : null);
		this.countValue22 = (domain.getTimes().isPresent() ? domain.getTimes().get().v().doubleValue() : null);
		this.moneyValue22 = (domain.getAmount().isPresent() ? domain.getAmount().get().v() : null);
	}

	public void toEntityAnyItemOfMonthly23(AnyItemOfMonthly domain) {
		this.timeValue23 = (domain.getTime().isPresent() ? domain.getTime().get().v() : null);
		this.countValue23 = (domain.getTimes().isPresent() ? domain.getTimes().get().v().doubleValue() : null);
		this.moneyValue23 = (domain.getAmount().isPresent() ? domain.getAmount().get().v() : null);
	}

	public void toEntityAnyItemOfMonthly24(AnyItemOfMonthly domain) {
		this.timeValue24 = (domain.getTime().isPresent() ? domain.getTime().get().v() : null);
		this.countValue24 = (domain.getTimes().isPresent() ? domain.getTimes().get().v().doubleValue() : null);
		this.moneyValue24 = (domain.getAmount().isPresent() ? domain.getAmount().get().v() : null);
	}

	public void toEntityAnyItemOfMonthly25(AnyItemOfMonthly domain) {
		this.timeValue25 = (domain.getTime().isPresent() ? domain.getTime().get().v() : null);
		this.countValue25 = (domain.getTimes().isPresent() ? domain.getTimes().get().v().doubleValue() : null);
		this.moneyValue25 = (domain.getAmount().isPresent() ? domain.getAmount().get().v() : null);
	}

	public void toEntityAnyItemOfMonthly26(AnyItemOfMonthly domain) {
		this.timeValue26 = (domain.getTime().isPresent() ? domain.getTime().get().v() : null);
		this.countValue26 = (domain.getTimes().isPresent() ? domain.getTimes().get().v().doubleValue() : null);
		this.moneyValue26 = (domain.getAmount().isPresent() ? domain.getAmount().get().v() : null);
	}

	public void toEntityAnyItemOfMonthly27(AnyItemOfMonthly domain) {
		this.timeValue27 = (domain.getTime().isPresent() ? domain.getTime().get().v() : null);
		this.countValue27 = (domain.getTimes().isPresent() ? domain.getTimes().get().v().doubleValue() : null);
		this.moneyValue27 = (domain.getAmount().isPresent() ? domain.getAmount().get().v() : null);
	}

	public void toEntityAnyItemOfMonthly28(AnyItemOfMonthly domain) {
		this.timeValue28 = (domain.getTime().isPresent() ? domain.getTime().get().v() : null);
		this.countValue28 = (domain.getTimes().isPresent() ? domain.getTimes().get().v().doubleValue() : null);
		this.moneyValue28 = (domain.getAmount().isPresent() ? domain.getAmount().get().v() : null);
	}

	public void toEntityAnyItemOfMonthly29(AnyItemOfMonthly domain) {
		this.timeValue29 = (domain.getTime().isPresent() ? domain.getTime().get().v() : null);
		this.countValue29 = (domain.getTimes().isPresent() ? domain.getTimes().get().v().doubleValue() : null);
		this.moneyValue29 = (domain.getAmount().isPresent() ? domain.getAmount().get().v() : null);
	}

	public void toEntityAnyItemOfMonthly30(AnyItemOfMonthly domain) {
		this.timeValue30 = (domain.getTime().isPresent() ? domain.getTime().get().v() : null);
		this.countValue30 = (domain.getTimes().isPresent() ? domain.getTimes().get().v().doubleValue() : null);
		this.moneyValue30 = (domain.getAmount().isPresent() ? domain.getAmount().get().v() : null);
	}

	public void toEntityAnyItemOfMonthly31(AnyItemOfMonthly domain) {
		this.timeValue31 = (domain.getTime().isPresent() ? domain.getTime().get().v() : null);
		this.countValue31 = (domain.getTimes().isPresent() ? domain.getTimes().get().v().doubleValue() : null);
		this.moneyValue31 = (domain.getAmount().isPresent() ? domain.getAmount().get().v() : null);
	}

	public void toEntityAnyItemOfMonthly32(AnyItemOfMonthly domain) {
		this.timeValue32 = (domain.getTime().isPresent() ? domain.getTime().get().v() : null);
		this.countValue32 = (domain.getTimes().isPresent() ? domain.getTimes().get().v().doubleValue() : null);
		this.moneyValue32 = (domain.getAmount().isPresent() ? domain.getAmount().get().v() : null);
	}

	public void toEntityAnyItemOfMonthly33(AnyItemOfMonthly domain) {
		this.timeValue33 = (domain.getTime().isPresent() ? domain.getTime().get().v() : null);
		this.countValue33 = (domain.getTimes().isPresent() ? domain.getTimes().get().v().doubleValue() : null);
		this.moneyValue33 = (domain.getAmount().isPresent() ? domain.getAmount().get().v() : null);
	}

	public void toEntityAnyItemOfMonthly34(AnyItemOfMonthly domain) {
		this.timeValue34 = (domain.getTime().isPresent() ? domain.getTime().get().v() : null);
		this.countValue34 = (domain.getTimes().isPresent() ? domain.getTimes().get().v().doubleValue() : null);
		this.moneyValue34 = (domain.getAmount().isPresent() ? domain.getAmount().get().v() : null);
	}

	public void toEntityAnyItemOfMonthly35(AnyItemOfMonthly domain) {
		this.timeValue35 = (domain.getTime().isPresent() ? domain.getTime().get().v() : null);
		this.countValue35 = (domain.getTimes().isPresent() ? domain.getTimes().get().v().doubleValue() : null);
		this.moneyValue35 = (domain.getAmount().isPresent() ? domain.getAmount().get().v() : null);
	}

	public void toEntityAnyItemOfMonthly36(AnyItemOfMonthly domain) {
		this.timeValue36 = (domain.getTime().isPresent() ? domain.getTime().get().v() : null);
		this.countValue36 = (domain.getTimes().isPresent() ? domain.getTimes().get().v().doubleValue() : null);
		this.moneyValue36 = (domain.getAmount().isPresent() ? domain.getAmount().get().v() : null);
	}

	public void toEntityAnyItemOfMonthly37(AnyItemOfMonthly domain) {
		this.timeValue37 = (domain.getTime().isPresent() ? domain.getTime().get().v() : null);
		this.countValue37 = (domain.getTimes().isPresent() ? domain.getTimes().get().v().doubleValue() : null);
		this.moneyValue37 = (domain.getAmount().isPresent() ? domain.getAmount().get().v() : null);
	}

	public void toEntityAnyItemOfMonthly38(AnyItemOfMonthly domain) {
		this.timeValue38 = (domain.getTime().isPresent() ? domain.getTime().get().v() : null);
		this.countValue38 = (domain.getTimes().isPresent() ? domain.getTimes().get().v().doubleValue() : null);
		this.moneyValue38 = (domain.getAmount().isPresent() ? domain.getAmount().get().v() : null);
	}

	public void toEntityAnyItemOfMonthly39(AnyItemOfMonthly domain) {
		this.timeValue39 = (domain.getTime().isPresent() ? domain.getTime().get().v() : null);
		this.countValue39 = (domain.getTimes().isPresent() ? domain.getTimes().get().v().doubleValue() : null);
		this.moneyValue39 = (domain.getAmount().isPresent() ? domain.getAmount().get().v() : null);
	}

	public void toEntityAnyItemOfMonthly40(AnyItemOfMonthly domain) {
		this.timeValue40 = (domain.getTime().isPresent() ? domain.getTime().get().v() : null);
		this.countValue40 = (domain.getTimes().isPresent() ? domain.getTimes().get().v().doubleValue() : null);
		this.moneyValue40 = (domain.getAmount().isPresent() ? domain.getAmount().get().v() : null);
	}

	public void toEntityAnyItemOfMonthly41(AnyItemOfMonthly domain) {
		this.timeValue41 = (domain.getTime().isPresent() ? domain.getTime().get().v() : null);
		this.countValue41 = (domain.getTimes().isPresent() ? domain.getTimes().get().v().doubleValue() : null);
		this.moneyValue41 = (domain.getAmount().isPresent() ? domain.getAmount().get().v() : null);
	}

	public void toEntityAnyItemOfMonthly42(AnyItemOfMonthly domain) {
		this.timeValue42 = (domain.getTime().isPresent() ? domain.getTime().get().v() : null);
		this.countValue42 = (domain.getTimes().isPresent() ? domain.getTimes().get().v().doubleValue() : null);
		this.moneyValue42 = (domain.getAmount().isPresent() ? domain.getAmount().get().v() : null);
	}

	public void toEntityAnyItemOfMonthly43(AnyItemOfMonthly domain) {
		this.timeValue43 = (domain.getTime().isPresent() ? domain.getTime().get().v() : null);
		this.countValue43 = (domain.getTimes().isPresent() ? domain.getTimes().get().v().doubleValue() : null);
		this.moneyValue43 = (domain.getAmount().isPresent() ? domain.getAmount().get().v() : null);
	}

	public void toEntityAnyItemOfMonthly44(AnyItemOfMonthly domain) {
		this.timeValue44 = (domain.getTime().isPresent() ? domain.getTime().get().v() : null);
		this.countValue44 = (domain.getTimes().isPresent() ? domain.getTimes().get().v().doubleValue() : null);
		this.moneyValue44 = (domain.getAmount().isPresent() ? domain.getAmount().get().v() : null);
	}

	public void toEntityAnyItemOfMonthly45(AnyItemOfMonthly domain) {
		this.timeValue45 = (domain.getTime().isPresent() ? domain.getTime().get().v() : null);
		this.countValue45 = (domain.getTimes().isPresent() ? domain.getTimes().get().v().doubleValue() : null);
		this.moneyValue45 = (domain.getAmount().isPresent() ? domain.getAmount().get().v() : null);
	}

	public void toEntityAnyItemOfMonthly46(AnyItemOfMonthly domain) {
		this.timeValue46 = (domain.getTime().isPresent() ? domain.getTime().get().v() : null);
		this.countValue46 = (domain.getTimes().isPresent() ? domain.getTimes().get().v().doubleValue() : null);
		this.moneyValue46 = (domain.getAmount().isPresent() ? domain.getAmount().get().v() : null);
	}

	public void toEntityAnyItemOfMonthly47(AnyItemOfMonthly domain) {
		this.timeValue47 = (domain.getTime().isPresent() ? domain.getTime().get().v() : null);
		this.countValue47 = (domain.getTimes().isPresent() ? domain.getTimes().get().v().doubleValue() : null);
		this.moneyValue47 = (domain.getAmount().isPresent() ? domain.getAmount().get().v() : null);
	}

	public void toEntityAnyItemOfMonthly48(AnyItemOfMonthly domain) {
		this.timeValue48 = (domain.getTime().isPresent() ? domain.getTime().get().v() : null);
		this.countValue48 = (domain.getTimes().isPresent() ? domain.getTimes().get().v().doubleValue() : null);
		this.moneyValue48 = (domain.getAmount().isPresent() ? domain.getAmount().get().v() : null);
	}

	public void toEntityAnyItemOfMonthly49(AnyItemOfMonthly domain) {
		this.timeValue49 = (domain.getTime().isPresent() ? domain.getTime().get().v() : null);
		this.countValue49 = (domain.getTimes().isPresent() ? domain.getTimes().get().v().doubleValue() : null);
		this.moneyValue49 = (domain.getAmount().isPresent() ? domain.getAmount().get().v() : null);
	}

	public void toEntityAnyItemOfMonthly50(AnyItemOfMonthly domain) {
		this.timeValue50 = (domain.getTime().isPresent() ? domain.getTime().get().v() : null);
		this.countValue50 = (domain.getTimes().isPresent() ? domain.getTimes().get().v().doubleValue() : null);
		this.moneyValue50 = (domain.getAmount().isPresent() ? domain.getAmount().get().v() : null);
	}

	public void toEntityAnyItemOfMonthly51(AnyItemOfMonthly domain) {
		this.timeValue51 = (domain.getTime().isPresent() ? domain.getTime().get().v() : null);
		this.countValue51 = (domain.getTimes().isPresent() ? domain.getTimes().get().v().doubleValue() : null);
		this.moneyValue51 = (domain.getAmount().isPresent() ? domain.getAmount().get().v() : null);
	}

	public void toEntityAnyItemOfMonthly52(AnyItemOfMonthly domain) {
		this.timeValue52 = (domain.getTime().isPresent() ? domain.getTime().get().v() : null);
		this.countValue52 = (domain.getTimes().isPresent() ? domain.getTimes().get().v().doubleValue() : null);
		this.moneyValue52 = (domain.getAmount().isPresent() ? domain.getAmount().get().v() : null);
	}

	public void toEntityAnyItemOfMonthly53(AnyItemOfMonthly domain) {
		this.timeValue53 = (domain.getTime().isPresent() ? domain.getTime().get().v() : null);
		this.countValue53 = (domain.getTimes().isPresent() ? domain.getTimes().get().v().doubleValue() : null);
		this.moneyValue53 = (domain.getAmount().isPresent() ? domain.getAmount().get().v() : null);
	}

	public void toEntityAnyItemOfMonthly54(AnyItemOfMonthly domain) {
		this.timeValue54 = (domain.getTime().isPresent() ? domain.getTime().get().v() : null);
		this.countValue54 = (domain.getTimes().isPresent() ? domain.getTimes().get().v().doubleValue() : null);
		this.moneyValue54 = (domain.getAmount().isPresent() ? domain.getAmount().get().v() : null);
	}

	public void toEntityAnyItemOfMonthly55(AnyItemOfMonthly domain) {
		this.timeValue55 = (domain.getTime().isPresent() ? domain.getTime().get().v() : null);
		this.countValue55 = (domain.getTimes().isPresent() ? domain.getTimes().get().v().doubleValue() : null);
		this.moneyValue55 = (domain.getAmount().isPresent() ? domain.getAmount().get().v() : null);
	}

	public void toEntityAnyItemOfMonthly56(AnyItemOfMonthly domain) {
		this.timeValue56 = (domain.getTime().isPresent() ? domain.getTime().get().v() : null);
		this.countValue56 = (domain.getTimes().isPresent() ? domain.getTimes().get().v().doubleValue() : null);
		this.moneyValue56 = (domain.getAmount().isPresent() ? domain.getAmount().get().v() : null);
	}

	public void toEntityAnyItemOfMonthly57(AnyItemOfMonthly domain) {
		this.timeValue57 = (domain.getTime().isPresent() ? domain.getTime().get().v() : null);
		this.countValue57 = (domain.getTimes().isPresent() ? domain.getTimes().get().v().doubleValue() : null);
		this.moneyValue57 = (domain.getAmount().isPresent() ? domain.getAmount().get().v() : null);
	}

	public void toEntityAnyItemOfMonthly58(AnyItemOfMonthly domain) {
		this.timeValue58 = (domain.getTime().isPresent() ? domain.getTime().get().v() : null);
		this.countValue58 = (domain.getTimes().isPresent() ? domain.getTimes().get().v().doubleValue() : null);
		this.moneyValue58 = (domain.getAmount().isPresent() ? domain.getAmount().get().v() : null);
	}

	public void toEntityAnyItemOfMonthly59(AnyItemOfMonthly domain) {
		this.timeValue59 = (domain.getTime().isPresent() ? domain.getTime().get().v() : null);
		this.countValue59 = (domain.getTimes().isPresent() ? domain.getTimes().get().v().doubleValue() : null);
		this.moneyValue59 = (domain.getAmount().isPresent() ? domain.getAmount().get().v() : null);
	}

	public void toEntityAnyItemOfMonthly60(AnyItemOfMonthly domain) {
		this.timeValue60 = (domain.getTime().isPresent() ? domain.getTime().get().v() : null);
		this.countValue60 = (domain.getTimes().isPresent() ? domain.getTimes().get().v().doubleValue() : null);
		this.moneyValue60 = (domain.getAmount().isPresent() ? domain.getAmount().get().v() : null);
	}

	public void toEntityAnyItemOfMonthly61(AnyItemOfMonthly domain) {
		this.timeValue61 = (domain.getTime().isPresent() ? domain.getTime().get().v() : null);
		this.countValue61 = (domain.getTimes().isPresent() ? domain.getTimes().get().v().doubleValue() : null);
		this.moneyValue61 = (domain.getAmount().isPresent() ? domain.getAmount().get().v() : null);
	}

	public void toEntityAnyItemOfMonthly62(AnyItemOfMonthly domain) {
		this.timeValue62 = (domain.getTime().isPresent() ? domain.getTime().get().v() : null);
		this.countValue62 = (domain.getTimes().isPresent() ? domain.getTimes().get().v().doubleValue() : null);
		this.moneyValue62 = (domain.getAmount().isPresent() ? domain.getAmount().get().v() : null);
	}

	public void toEntityAnyItemOfMonthly63(AnyItemOfMonthly domain) {
		this.timeValue63 = (domain.getTime().isPresent() ? domain.getTime().get().v() : null);
		this.countValue63 = (domain.getTimes().isPresent() ? domain.getTimes().get().v().doubleValue() : null);
		this.moneyValue63 = (domain.getAmount().isPresent() ? domain.getAmount().get().v() : null);
	}

	public void toEntityAnyItemOfMonthly64(AnyItemOfMonthly domain) {
		this.timeValue64 = (domain.getTime().isPresent() ? domain.getTime().get().v() : null);
		this.countValue64 = (domain.getTimes().isPresent() ? domain.getTimes().get().v().doubleValue() : null);
		this.moneyValue64 = (domain.getAmount().isPresent() ? domain.getAmount().get().v() : null);
	}

	public void toEntityAnyItemOfMonthly65(AnyItemOfMonthly domain) {
		this.timeValue65 = (domain.getTime().isPresent() ? domain.getTime().get().v() : null);
		this.countValue65 = (domain.getTimes().isPresent() ? domain.getTimes().get().v().doubleValue() : null);
		this.moneyValue65 = (domain.getAmount().isPresent() ? domain.getAmount().get().v() : null);
	}

	public void toEntityAnyItemOfMonthly66(AnyItemOfMonthly domain) {
		this.timeValue66 = (domain.getTime().isPresent() ? domain.getTime().get().v() : null);
		this.countValue66 = (domain.getTimes().isPresent() ? domain.getTimes().get().v().doubleValue() : null);
		this.moneyValue66 = (domain.getAmount().isPresent() ? domain.getAmount().get().v() : null);
	}

	public void toEntityAnyItemOfMonthly67(AnyItemOfMonthly domain) {
		this.timeValue67 = (domain.getTime().isPresent() ? domain.getTime().get().v() : null);
		this.countValue67 = (domain.getTimes().isPresent() ? domain.getTimes().get().v().doubleValue() : null);
		this.moneyValue67 = (domain.getAmount().isPresent() ? domain.getAmount().get().v() : null);
	}

	public void toEntityAnyItemOfMonthly68(AnyItemOfMonthly domain) {
		this.timeValue68 = (domain.getTime().isPresent() ? domain.getTime().get().v() : null);
		this.countValue68 = (domain.getTimes().isPresent() ? domain.getTimes().get().v().doubleValue() : null);
		this.moneyValue68 = (domain.getAmount().isPresent() ? domain.getAmount().get().v() : null);
	}

	public void toEntityAnyItemOfMonthly69(AnyItemOfMonthly domain) {
		this.timeValue69 = (domain.getTime().isPresent() ? domain.getTime().get().v() : null);
		this.countValue69 = (domain.getTimes().isPresent() ? domain.getTimes().get().v().doubleValue() : null);
		this.moneyValue69 = (domain.getAmount().isPresent() ? domain.getAmount().get().v() : null);
	}

	public void toEntityAnyItemOfMonthly70(AnyItemOfMonthly domain) {
		this.timeValue70 = (domain.getTime().isPresent() ? domain.getTime().get().v() : null);
		this.countValue70 = (domain.getTimes().isPresent() ? domain.getTimes().get().v().doubleValue() : null);
		this.moneyValue70 = (domain.getAmount().isPresent() ? domain.getAmount().get().v() : null);
	}

	public void toEntityAnyItemOfMonthly71(AnyItemOfMonthly domain) {
		this.timeValue71 = (domain.getTime().isPresent() ? domain.getTime().get().v() : null);
		this.countValue71 = (domain.getTimes().isPresent() ? domain.getTimes().get().v().doubleValue() : null);
		this.moneyValue71 = (domain.getAmount().isPresent() ? domain.getAmount().get().v() : null);
	}

	public void toEntityAnyItemOfMonthly72(AnyItemOfMonthly domain) {
		this.timeValue72 = (domain.getTime().isPresent() ? domain.getTime().get().v() : null);
		this.countValue72 = (domain.getTimes().isPresent() ? domain.getTimes().get().v().doubleValue() : null);
		this.moneyValue72 = (domain.getAmount().isPresent() ? domain.getAmount().get().v() : null);
	}

	public void toEntityAnyItemOfMonthly73(AnyItemOfMonthly domain) {
		this.timeValue73 = (domain.getTime().isPresent() ? domain.getTime().get().v() : null);
		this.countValue73 = (domain.getTimes().isPresent() ? domain.getTimes().get().v().doubleValue() : null);
		this.moneyValue73 = (domain.getAmount().isPresent() ? domain.getAmount().get().v() : null);
	}

	public void toEntityAnyItemOfMonthly74(AnyItemOfMonthly domain) {
		this.timeValue74 = (domain.getTime().isPresent() ? domain.getTime().get().v() : null);
		this.countValue74 = (domain.getTimes().isPresent() ? domain.getTimes().get().v().doubleValue() : null);
		this.moneyValue74 = (domain.getAmount().isPresent() ? domain.getAmount().get().v() : null);
	}

	public void toEntityAnyItemOfMonthly75(AnyItemOfMonthly domain) {
		this.timeValue75 = (domain.getTime().isPresent() ? domain.getTime().get().v() : null);
		this.countValue75 = (domain.getTimes().isPresent() ? domain.getTimes().get().v().doubleValue() : null);
		this.moneyValue75 = (domain.getAmount().isPresent() ? domain.getAmount().get().v() : null);
	}

	public void toEntityAnyItemOfMonthly76(AnyItemOfMonthly domain) {
		this.timeValue76 = (domain.getTime().isPresent() ? domain.getTime().get().v() : null);
		this.countValue76 = (domain.getTimes().isPresent() ? domain.getTimes().get().v().doubleValue() : null);
		this.moneyValue76 = (domain.getAmount().isPresent() ? domain.getAmount().get().v() : null);
	}

	public void toEntityAnyItemOfMonthly77(AnyItemOfMonthly domain) {
		this.timeValue77 = (domain.getTime().isPresent() ? domain.getTime().get().v() : null);
		this.countValue77 = (domain.getTimes().isPresent() ? domain.getTimes().get().v().doubleValue() : null);
		this.moneyValue77 = (domain.getAmount().isPresent() ? domain.getAmount().get().v() : null);
	}

	public void toEntityAnyItemOfMonthly78(AnyItemOfMonthly domain) {
		this.timeValue78 = (domain.getTime().isPresent() ? domain.getTime().get().v() : null);
		this.countValue78 = (domain.getTimes().isPresent() ? domain.getTimes().get().v().doubleValue() : null);
		this.moneyValue78 = (domain.getAmount().isPresent() ? domain.getAmount().get().v() : null);
	}

	public void toEntityAnyItemOfMonthly79(AnyItemOfMonthly domain) {
		this.timeValue79 = (domain.getTime().isPresent() ? domain.getTime().get().v() : null);
		this.countValue79 = (domain.getTimes().isPresent() ? domain.getTimes().get().v().doubleValue() : null);
		this.moneyValue79 = (domain.getAmount().isPresent() ? domain.getAmount().get().v() : null);
	}

	public void toEntityAnyItemOfMonthly80(AnyItemOfMonthly domain) {
		this.timeValue80 = (domain.getTime().isPresent() ? domain.getTime().get().v() : null);
		this.countValue80 = (domain.getTimes().isPresent() ? domain.getTimes().get().v().doubleValue() : null);
		this.moneyValue80 = (domain.getAmount().isPresent() ? domain.getAmount().get().v() : null);
	}

	public void toEntityAnyItemOfMonthly81(AnyItemOfMonthly domain) {
		this.timeValue81 = (domain.getTime().isPresent() ? domain.getTime().get().v() : null);
		this.countValue81 = (domain.getTimes().isPresent() ? domain.getTimes().get().v().doubleValue() : null);
		this.moneyValue81 = (domain.getAmount().isPresent() ? domain.getAmount().get().v() : null);
	}

	public void toEntityAnyItemOfMonthly82(AnyItemOfMonthly domain) {
		this.timeValue82 = (domain.getTime().isPresent() ? domain.getTime().get().v() : null);
		this.countValue82 = (domain.getTimes().isPresent() ? domain.getTimes().get().v().doubleValue() : null);
		this.moneyValue82 = (domain.getAmount().isPresent() ? domain.getAmount().get().v() : null);
	}

	public void toEntityAnyItemOfMonthly83(AnyItemOfMonthly domain) {
		this.timeValue83 = (domain.getTime().isPresent() ? domain.getTime().get().v() : null);
		this.countValue83 = (domain.getTimes().isPresent() ? domain.getTimes().get().v().doubleValue() : null);
		this.moneyValue83 = (domain.getAmount().isPresent() ? domain.getAmount().get().v() : null);
	}

	public void toEntityAnyItemOfMonthly84(AnyItemOfMonthly domain) {
		this.timeValue84 = (domain.getTime().isPresent() ? domain.getTime().get().v() : null);
		this.countValue84 = (domain.getTimes().isPresent() ? domain.getTimes().get().v().doubleValue() : null);
		this.moneyValue84 = (domain.getAmount().isPresent() ? domain.getAmount().get().v() : null);
	}

	public void toEntityAnyItemOfMonthly85(AnyItemOfMonthly domain) {
		this.timeValue85 = (domain.getTime().isPresent() ? domain.getTime().get().v() : null);
		this.countValue85 = (domain.getTimes().isPresent() ? domain.getTimes().get().v().doubleValue() : null);
		this.moneyValue85 = (domain.getAmount().isPresent() ? domain.getAmount().get().v() : null);
	}

	public void toEntityAnyItemOfMonthly86(AnyItemOfMonthly domain) {
		this.timeValue86 = (domain.getTime().isPresent() ? domain.getTime().get().v() : null);
		this.countValue86 = (domain.getTimes().isPresent() ? domain.getTimes().get().v().doubleValue() : null);
		this.moneyValue86 = (domain.getAmount().isPresent() ? domain.getAmount().get().v() : null);
	}

	public void toEntityAnyItemOfMonthly87(AnyItemOfMonthly domain) {
		this.timeValue87 = (domain.getTime().isPresent() ? domain.getTime().get().v() : null);
		this.countValue87 = (domain.getTimes().isPresent() ? domain.getTimes().get().v().doubleValue() : null);
		this.moneyValue87 = (domain.getAmount().isPresent() ? domain.getAmount().get().v() : null);
	}

	public void toEntityAnyItemOfMonthly88(AnyItemOfMonthly domain) {
		this.timeValue88 = (domain.getTime().isPresent() ? domain.getTime().get().v() : null);
		this.countValue88 = (domain.getTimes().isPresent() ? domain.getTimes().get().v().doubleValue() : null);
		this.moneyValue88 = (domain.getAmount().isPresent() ? domain.getAmount().get().v() : null);
	}

	public void toEntityAnyItemOfMonthly89(AnyItemOfMonthly domain) {
		this.timeValue89 = (domain.getTime().isPresent() ? domain.getTime().get().v() : null);
		this.countValue89 = (domain.getTimes().isPresent() ? domain.getTimes().get().v().doubleValue() : null);
		this.moneyValue89 = (domain.getAmount().isPresent() ? domain.getAmount().get().v() : null);
	}

	public void toEntityAnyItemOfMonthly90(AnyItemOfMonthly domain) {
		this.timeValue90 = (domain.getTime().isPresent() ? domain.getTime().get().v() : null);
		this.countValue90 = (domain.getTimes().isPresent() ? domain.getTimes().get().v().doubleValue() : null);
		this.moneyValue90 = (domain.getAmount().isPresent() ? domain.getAmount().get().v() : null);
	}

	public void toEntityAnyItemOfMonthly91(AnyItemOfMonthly domain) {
		this.timeValue91 = (domain.getTime().isPresent() ? domain.getTime().get().v() : null);
		this.countValue91 = (domain.getTimes().isPresent() ? domain.getTimes().get().v().doubleValue() : null);
		this.moneyValue91 = (domain.getAmount().isPresent() ? domain.getAmount().get().v() : null);
	}

	public void toEntityAnyItemOfMonthly92(AnyItemOfMonthly domain) {
		this.timeValue92 = (domain.getTime().isPresent() ? domain.getTime().get().v() : null);
		this.countValue92 = (domain.getTimes().isPresent() ? domain.getTimes().get().v().doubleValue() : null);
		this.moneyValue92 = (domain.getAmount().isPresent() ? domain.getAmount().get().v() : null);
	}

	public void toEntityAnyItemOfMonthly93(AnyItemOfMonthly domain) {
		this.timeValue93 = (domain.getTime().isPresent() ? domain.getTime().get().v() : null);
		this.countValue93 = (domain.getTimes().isPresent() ? domain.getTimes().get().v().doubleValue() : null);
		this.moneyValue93 = (domain.getAmount().isPresent() ? domain.getAmount().get().v() : null);
	}

	public void toEntityAnyItemOfMonthly94(AnyItemOfMonthly domain) {
		this.timeValue94 = (domain.getTime().isPresent() ? domain.getTime().get().v() : null);
		this.countValue94 = (domain.getTimes().isPresent() ? domain.getTimes().get().v().doubleValue() : null);
		this.moneyValue94 = (domain.getAmount().isPresent() ? domain.getAmount().get().v() : null);
	}

	public void toEntityAnyItemOfMonthly95(AnyItemOfMonthly domain) {
		this.timeValue95 = (domain.getTime().isPresent() ? domain.getTime().get().v() : null);
		this.countValue95 = (domain.getTimes().isPresent() ? domain.getTimes().get().v().doubleValue() : null);
		this.moneyValue95 = (domain.getAmount().isPresent() ? domain.getAmount().get().v() : null);
	}

	public void toEntityAnyItemOfMonthly96(AnyItemOfMonthly domain) {
		this.timeValue96 = (domain.getTime().isPresent() ? domain.getTime().get().v() : null);
		this.countValue96 = (domain.getTimes().isPresent() ? domain.getTimes().get().v().doubleValue() : null);
		this.moneyValue96 = (domain.getAmount().isPresent() ? domain.getAmount().get().v() : null);
	}

	public void toEntityAnyItemOfMonthly97(AnyItemOfMonthly domain) {
		this.timeValue97 = (domain.getTime().isPresent() ? domain.getTime().get().v() : null);
		this.countValue97 = (domain.getTimes().isPresent() ? domain.getTimes().get().v().doubleValue() : null);
		this.moneyValue97 = (domain.getAmount().isPresent() ? domain.getAmount().get().v() : null);
	}

	public void toEntityAnyItemOfMonthly98(AnyItemOfMonthly domain) {
		this.timeValue98 = (domain.getTime().isPresent() ? domain.getTime().get().v() : null);
		this.countValue98 = (domain.getTimes().isPresent() ? domain.getTimes().get().v().doubleValue() : null);
		this.moneyValue98 = (domain.getAmount().isPresent() ? domain.getAmount().get().v() : null);
	}

	public void toEntityAnyItemOfMonthly99(AnyItemOfMonthly domain) {
		this.timeValue99 = (domain.getTime().isPresent() ? domain.getTime().get().v() : null);
		this.countValue99 = (domain.getTimes().isPresent() ? domain.getTimes().get().v().doubleValue() : null);
		this.moneyValue99 = (domain.getAmount().isPresent() ? domain.getAmount().get().v() : null);
	}

	public void toEntityAnyItemOfMonthly100(AnyItemOfMonthly domain) {
		this.timeValue100 = (domain.getTime().isPresent() ? domain.getTime().get().v() : null);
		this.countValue100 = (domain.getTimes().isPresent() ? domain.getTimes().get().v().doubleValue() : null);
		this.moneyValue100 = (domain.getAmount().isPresent() ? domain.getAmount().get().v() : null);
	}

	public void toEntityAnyItemOfMonthly101(AnyItemOfMonthly domain) {
		this.timeValue101 = (domain.getTime().isPresent() ? domain.getTime().get().v() : null);
		this.countValue101 = (domain.getTimes().isPresent() ? domain.getTimes().get().v().doubleValue() : null);
		this.moneyValue101 = (domain.getAmount().isPresent() ? domain.getAmount().get().v() : null);
	}

	public void toEntityAnyItemOfMonthly102(AnyItemOfMonthly domain) {
		this.timeValue102 = (domain.getTime().isPresent() ? domain.getTime().get().v() : null);
		this.countValue102 = (domain.getTimes().isPresent() ? domain.getTimes().get().v().doubleValue() : null);
		this.moneyValue102 = (domain.getAmount().isPresent() ? domain.getAmount().get().v() : null);
	}

	public void toEntityAnyItemOfMonthly103(AnyItemOfMonthly domain) {
		this.timeValue103 = (domain.getTime().isPresent() ? domain.getTime().get().v() : null);
		this.countValue103 = (domain.getTimes().isPresent() ? domain.getTimes().get().v().doubleValue() : null);
		this.moneyValue103 = (domain.getAmount().isPresent() ? domain.getAmount().get().v() : null);
	}

	public void toEntityAnyItemOfMonthly104(AnyItemOfMonthly domain) {
		this.timeValue104 = (domain.getTime().isPresent() ? domain.getTime().get().v() : null);
		this.countValue104 = (domain.getTimes().isPresent() ? domain.getTimes().get().v().doubleValue() : null);
		this.moneyValue104 = (domain.getAmount().isPresent() ? domain.getAmount().get().v() : null);
	}

	public void toEntityAnyItemOfMonthly105(AnyItemOfMonthly domain) {
		this.timeValue105 = (domain.getTime().isPresent() ? domain.getTime().get().v() : null);
		this.countValue105 = (domain.getTimes().isPresent() ? domain.getTimes().get().v().doubleValue() : null);
		this.moneyValue105 = (domain.getAmount().isPresent() ? domain.getAmount().get().v() : null);
	}

	public void toEntityAnyItemOfMonthly106(AnyItemOfMonthly domain) {
		this.timeValue106 = (domain.getTime().isPresent() ? domain.getTime().get().v() : null);
		this.countValue106 = (domain.getTimes().isPresent() ? domain.getTimes().get().v().doubleValue() : null);
		this.moneyValue106 = (domain.getAmount().isPresent() ? domain.getAmount().get().v() : null);
	}

	public void toEntityAnyItemOfMonthly107(AnyItemOfMonthly domain) {
		this.timeValue107 = (domain.getTime().isPresent() ? domain.getTime().get().v() : null);
		this.countValue107 = (domain.getTimes().isPresent() ? domain.getTimes().get().v().doubleValue() : null);
		this.moneyValue107 = (domain.getAmount().isPresent() ? domain.getAmount().get().v() : null);
	}

	public void toEntityAnyItemOfMonthly108(AnyItemOfMonthly domain) {
		this.timeValue108 = (domain.getTime().isPresent() ? domain.getTime().get().v() : null);
		this.countValue108 = (domain.getTimes().isPresent() ? domain.getTimes().get().v().doubleValue() : null);
		this.moneyValue108 = (domain.getAmount().isPresent() ? domain.getAmount().get().v() : null);
	}

	public void toEntityAnyItemOfMonthly109(AnyItemOfMonthly domain) {
		this.timeValue109 = (domain.getTime().isPresent() ? domain.getTime().get().v() : null);
		this.countValue109 = (domain.getTimes().isPresent() ? domain.getTimes().get().v().doubleValue() : null);
		this.moneyValue109 = (domain.getAmount().isPresent() ? domain.getAmount().get().v() : null);
	}

	public void toEntityAnyItemOfMonthly110(AnyItemOfMonthly domain) {
		this.timeValue110 = (domain.getTime().isPresent() ? domain.getTime().get().v() : null);
		this.countValue110 = (domain.getTimes().isPresent() ? domain.getTimes().get().v().doubleValue() : null);
		this.moneyValue110 = (domain.getAmount().isPresent() ? domain.getAmount().get().v() : null);
	}

	public void toEntityAnyItemOfMonthly111(AnyItemOfMonthly domain) {
		this.timeValue111 = (domain.getTime().isPresent() ? domain.getTime().get().v() : null);
		this.countValue111 = (domain.getTimes().isPresent() ? domain.getTimes().get().v().doubleValue() : null);
		this.moneyValue111 = (domain.getAmount().isPresent() ? domain.getAmount().get().v() : null);
	}

	public void toEntityAnyItemOfMonthly112(AnyItemOfMonthly domain) {
		this.timeValue112 = (domain.getTime().isPresent() ? domain.getTime().get().v() : null);
		this.countValue112 = (domain.getTimes().isPresent() ? domain.getTimes().get().v().doubleValue() : null);
		this.moneyValue112 = (domain.getAmount().isPresent() ? domain.getAmount().get().v() : null);
	}

	public void toEntityAnyItemOfMonthly113(AnyItemOfMonthly domain) {
		this.timeValue113 = (domain.getTime().isPresent() ? domain.getTime().get().v() : null);
		this.countValue113 = (domain.getTimes().isPresent() ? domain.getTimes().get().v().doubleValue() : null);
		this.moneyValue113 = (domain.getAmount().isPresent() ? domain.getAmount().get().v() : null);
	}

	public void toEntityAnyItemOfMonthly114(AnyItemOfMonthly domain) {
		this.timeValue114 = (domain.getTime().isPresent() ? domain.getTime().get().v() : null);
		this.countValue114 = (domain.getTimes().isPresent() ? domain.getTimes().get().v().doubleValue() : null);
		this.moneyValue114 = (domain.getAmount().isPresent() ? domain.getAmount().get().v() : null);
	}

	public void toEntityAnyItemOfMonthly115(AnyItemOfMonthly domain) {
		this.timeValue115 = (domain.getTime().isPresent() ? domain.getTime().get().v() : null);
		this.countValue115 = (domain.getTimes().isPresent() ? domain.getTimes().get().v().doubleValue() : null);
		this.moneyValue115 = (domain.getAmount().isPresent() ? domain.getAmount().get().v() : null);
	}

	public void toEntityAnyItemOfMonthly116(AnyItemOfMonthly domain) {
		this.timeValue116 = (domain.getTime().isPresent() ? domain.getTime().get().v() : null);
		this.countValue116 = (domain.getTimes().isPresent() ? domain.getTimes().get().v().doubleValue() : null);
		this.moneyValue116 = (domain.getAmount().isPresent() ? domain.getAmount().get().v() : null);
	}

	public void toEntityAnyItemOfMonthly117(AnyItemOfMonthly domain) {
		this.timeValue117 = (domain.getTime().isPresent() ? domain.getTime().get().v() : null);
		this.countValue117 = (domain.getTimes().isPresent() ? domain.getTimes().get().v().doubleValue() : null);
		this.moneyValue117 = (domain.getAmount().isPresent() ? domain.getAmount().get().v() : null);
	}

	public void toEntityAnyItemOfMonthly118(AnyItemOfMonthly domain) {
		this.timeValue118 = (domain.getTime().isPresent() ? domain.getTime().get().v() : null);
		this.countValue118 = (domain.getTimes().isPresent() ? domain.getTimes().get().v().doubleValue() : null);
		this.moneyValue118 = (domain.getAmount().isPresent() ? domain.getAmount().get().v() : null);
	}

	public void toEntityAnyItemOfMonthly119(AnyItemOfMonthly domain) {
		this.timeValue119 = (domain.getTime().isPresent() ? domain.getTime().get().v() : null);
		this.countValue119 = (domain.getTimes().isPresent() ? domain.getTimes().get().v().doubleValue() : null);
		this.moneyValue119 = (domain.getAmount().isPresent() ? domain.getAmount().get().v() : null);
	}

	public void toEntityAnyItemOfMonthly120(AnyItemOfMonthly domain) {
		this.timeValue120 = (domain.getTime().isPresent() ? domain.getTime().get().v() : null);
		this.countValue120 = (domain.getTimes().isPresent() ? domain.getTimes().get().v().doubleValue() : null);
		this.moneyValue120 = (domain.getAmount().isPresent() ? domain.getAmount().get().v() : null);
	}

	public void toEntityAnyItemOfMonthly121(AnyItemOfMonthly domain) {
		this.timeValue121 = (domain.getTime().isPresent() ? domain.getTime().get().v() : null);
		this.countValue121 = (domain.getTimes().isPresent() ? domain.getTimes().get().v().doubleValue() : null);
		this.moneyValue121 = (domain.getAmount().isPresent() ? domain.getAmount().get().v() : null);
	}

	public void toEntityAnyItemOfMonthly122(AnyItemOfMonthly domain) {
		this.timeValue122 = (domain.getTime().isPresent() ? domain.getTime().get().v() : null);
		this.countValue122 = (domain.getTimes().isPresent() ? domain.getTimes().get().v().doubleValue() : null);
		this.moneyValue122 = (domain.getAmount().isPresent() ? domain.getAmount().get().v() : null);
	}

	public void toEntityAnyItemOfMonthly123(AnyItemOfMonthly domain) {
		this.timeValue123 = (domain.getTime().isPresent() ? domain.getTime().get().v() : null);
		this.countValue123 = (domain.getTimes().isPresent() ? domain.getTimes().get().v().doubleValue() : null);
		this.moneyValue123 = (domain.getAmount().isPresent() ? domain.getAmount().get().v() : null);
	}

	public void toEntityAnyItemOfMonthly124(AnyItemOfMonthly domain) {
		this.timeValue124 = (domain.getTime().isPresent() ? domain.getTime().get().v() : null);
		this.countValue124 = (domain.getTimes().isPresent() ? domain.getTimes().get().v().doubleValue() : null);
		this.moneyValue124 = (domain.getAmount().isPresent() ? domain.getAmount().get().v() : null);
	}

	public void toEntityAnyItemOfMonthly125(AnyItemOfMonthly domain) {
		this.timeValue125 = (domain.getTime().isPresent() ? domain.getTime().get().v() : null);
		this.countValue125 = (domain.getTimes().isPresent() ? domain.getTimes().get().v().doubleValue() : null);
		this.moneyValue125 = (domain.getAmount().isPresent() ? domain.getAmount().get().v() : null);
	}

	public void toEntityAnyItemOfMonthly126(AnyItemOfMonthly domain) {
		this.timeValue126 = (domain.getTime().isPresent() ? domain.getTime().get().v() : null);
		this.countValue126 = (domain.getTimes().isPresent() ? domain.getTimes().get().v().doubleValue() : null);
		this.moneyValue126 = (domain.getAmount().isPresent() ? domain.getAmount().get().v() : null);
	}

	public void toEntityAnyItemOfMonthly127(AnyItemOfMonthly domain) {
		this.timeValue127 = (domain.getTime().isPresent() ? domain.getTime().get().v() : null);
		this.countValue127 = (domain.getTimes().isPresent() ? domain.getTimes().get().v().doubleValue() : null);
		this.moneyValue127 = (domain.getAmount().isPresent() ? domain.getAmount().get().v() : null);
	}

	public void toEntityAnyItemOfMonthly128(AnyItemOfMonthly domain) {
		this.timeValue128 = (domain.getTime().isPresent() ? domain.getTime().get().v() : null);
		this.countValue128 = (domain.getTimes().isPresent() ? domain.getTimes().get().v().doubleValue() : null);
		this.moneyValue128 = (domain.getAmount().isPresent() ? domain.getAmount().get().v() : null);
	}

	public void toEntityAnyItemOfMonthly129(AnyItemOfMonthly domain) {
		this.timeValue129 = (domain.getTime().isPresent() ? domain.getTime().get().v() : null);
		this.countValue129 = (domain.getTimes().isPresent() ? domain.getTimes().get().v().doubleValue() : null);
		this.moneyValue129 = (domain.getAmount().isPresent() ? domain.getAmount().get().v() : null);
	}

	public void toEntityAnyItemOfMonthly130(AnyItemOfMonthly domain) {
		this.timeValue130 = (domain.getTime().isPresent() ? domain.getTime().get().v() : null);
		this.countValue130 = (domain.getTimes().isPresent() ? domain.getTimes().get().v().doubleValue() : null);
		this.moneyValue130 = (domain.getAmount().isPresent() ? domain.getAmount().get().v() : null);
	}

	public void toEntityAnyItemOfMonthly131(AnyItemOfMonthly domain) {
		this.timeValue131 = (domain.getTime().isPresent() ? domain.getTime().get().v() : null);
		this.countValue131 = (domain.getTimes().isPresent() ? domain.getTimes().get().v().doubleValue() : null);
		this.moneyValue131 = (domain.getAmount().isPresent() ? domain.getAmount().get().v() : null);
	}

	public void toEntityAnyItemOfMonthly132(AnyItemOfMonthly domain) {
		this.timeValue132 = (domain.getTime().isPresent() ? domain.getTime().get().v() : null);
		this.countValue132 = (domain.getTimes().isPresent() ? domain.getTimes().get().v().doubleValue() : null);
		this.moneyValue132 = (domain.getAmount().isPresent() ? domain.getAmount().get().v() : null);
	}

	public void toEntityAnyItemOfMonthly133(AnyItemOfMonthly domain) {
		this.timeValue133 = (domain.getTime().isPresent() ? domain.getTime().get().v() : null);
		this.countValue133 = (domain.getTimes().isPresent() ? domain.getTimes().get().v().doubleValue() : null);
		this.moneyValue133 = (domain.getAmount().isPresent() ? domain.getAmount().get().v() : null);
	}

	public void toEntityAnyItemOfMonthly134(AnyItemOfMonthly domain) {
		this.timeValue134 = (domain.getTime().isPresent() ? domain.getTime().get().v() : null);
		this.countValue134 = (domain.getTimes().isPresent() ? domain.getTimes().get().v().doubleValue() : null);
		this.moneyValue134 = (domain.getAmount().isPresent() ? domain.getAmount().get().v() : null);
	}

	public void toEntityAnyItemOfMonthly135(AnyItemOfMonthly domain) {
		this.timeValue135 = (domain.getTime().isPresent() ? domain.getTime().get().v() : null);
		this.countValue135 = (domain.getTimes().isPresent() ? domain.getTimes().get().v().doubleValue() : null);
		this.moneyValue135 = (domain.getAmount().isPresent() ? domain.getAmount().get().v() : null);
	}

	public void toEntityAnyItemOfMonthly136(AnyItemOfMonthly domain) {
		this.timeValue136 = (domain.getTime().isPresent() ? domain.getTime().get().v() : null);
		this.countValue136 = (domain.getTimes().isPresent() ? domain.getTimes().get().v().doubleValue() : null);
		this.moneyValue136 = (domain.getAmount().isPresent() ? domain.getAmount().get().v() : null);
	}

	public void toEntityAnyItemOfMonthly137(AnyItemOfMonthly domain) {
		this.timeValue137 = (domain.getTime().isPresent() ? domain.getTime().get().v() : null);
		this.countValue137 = (domain.getTimes().isPresent() ? domain.getTimes().get().v().doubleValue() : null);
		this.moneyValue137 = (domain.getAmount().isPresent() ? domain.getAmount().get().v() : null);
	}

	public void toEntityAnyItemOfMonthly138(AnyItemOfMonthly domain) {
		this.timeValue138 = (domain.getTime().isPresent() ? domain.getTime().get().v() : null);
		this.countValue138 = (domain.getTimes().isPresent() ? domain.getTimes().get().v().doubleValue() : null);
		this.moneyValue138 = (domain.getAmount().isPresent() ? domain.getAmount().get().v() : null);
	}

	public void toEntityAnyItemOfMonthly139(AnyItemOfMonthly domain) {
		this.timeValue139 = (domain.getTime().isPresent() ? domain.getTime().get().v() : null);
		this.countValue139 = (domain.getTimes().isPresent() ? domain.getTimes().get().v().doubleValue() : null);
		this.moneyValue139 = (domain.getAmount().isPresent() ? domain.getAmount().get().v() : null);
	}

	public void toEntityAnyItemOfMonthly140(AnyItemOfMonthly domain) {
		this.timeValue140 = (domain.getTime().isPresent() ? domain.getTime().get().v() : null);
		this.countValue140 = (domain.getTimes().isPresent() ? domain.getTimes().get().v().doubleValue() : null);
		this.moneyValue140 = (domain.getAmount().isPresent() ? domain.getAmount().get().v() : null);
	}

	public void toEntityAnyItemOfMonthly141(AnyItemOfMonthly domain) {
		this.timeValue141 = (domain.getTime().isPresent() ? domain.getTime().get().v() : null);
		this.countValue141 = (domain.getTimes().isPresent() ? domain.getTimes().get().v().doubleValue() : null);
		this.moneyValue141 = (domain.getAmount().isPresent() ? domain.getAmount().get().v() : null);
	}

	public void toEntityAnyItemOfMonthly142(AnyItemOfMonthly domain) {
		this.timeValue142 = (domain.getTime().isPresent() ? domain.getTime().get().v() : null);
		this.countValue142 = (domain.getTimes().isPresent() ? domain.getTimes().get().v().doubleValue() : null);
		this.moneyValue142 = (domain.getAmount().isPresent() ? domain.getAmount().get().v() : null);
	}

	public void toEntityAnyItemOfMonthly143(AnyItemOfMonthly domain) {
		this.timeValue143 = (domain.getTime().isPresent() ? domain.getTime().get().v() : null);
		this.countValue143 = (domain.getTimes().isPresent() ? domain.getTimes().get().v().doubleValue() : null);
		this.moneyValue143 = (domain.getAmount().isPresent() ? domain.getAmount().get().v() : null);
	}

	public void toEntityAnyItemOfMonthly144(AnyItemOfMonthly domain) {
		this.timeValue144 = (domain.getTime().isPresent() ? domain.getTime().get().v() : null);
		this.countValue144 = (domain.getTimes().isPresent() ? domain.getTimes().get().v().doubleValue() : null);
		this.moneyValue144 = (domain.getAmount().isPresent() ? domain.getAmount().get().v() : null);
	}

	public void toEntityAnyItemOfMonthly145(AnyItemOfMonthly domain) {
		this.timeValue145 = (domain.getTime().isPresent() ? domain.getTime().get().v() : null);
		this.countValue145 = (domain.getTimes().isPresent() ? domain.getTimes().get().v().doubleValue() : null);
		this.moneyValue145 = (domain.getAmount().isPresent() ? domain.getAmount().get().v() : null);
	}

	public void toEntityAnyItemOfMonthly146(AnyItemOfMonthly domain) {
		this.timeValue146 = (domain.getTime().isPresent() ? domain.getTime().get().v() : null);
		this.countValue146 = (domain.getTimes().isPresent() ? domain.getTimes().get().v().doubleValue() : null);
		this.moneyValue146 = (domain.getAmount().isPresent() ? domain.getAmount().get().v() : null);
	}

	public void toEntityAnyItemOfMonthly147(AnyItemOfMonthly domain) {
		this.timeValue147 = (domain.getTime().isPresent() ? domain.getTime().get().v() : null);
		this.countValue147 = (domain.getTimes().isPresent() ? domain.getTimes().get().v().doubleValue() : null);
		this.moneyValue147 = (domain.getAmount().isPresent() ? domain.getAmount().get().v() : null);
	}

	public void toEntityAnyItemOfMonthly148(AnyItemOfMonthly domain) {
		this.timeValue148 = (domain.getTime().isPresent() ? domain.getTime().get().v() : null);
		this.countValue148 = (domain.getTimes().isPresent() ? domain.getTimes().get().v().doubleValue() : null);
		this.moneyValue148 = (domain.getAmount().isPresent() ? domain.getAmount().get().v() : null);
	}

	public void toEntityAnyItemOfMonthly149(AnyItemOfMonthly domain) {
		this.timeValue149 = (domain.getTime().isPresent() ? domain.getTime().get().v() : null);
		this.countValue149 = (domain.getTimes().isPresent() ? domain.getTimes().get().v().doubleValue() : null);
		this.moneyValue149 = (domain.getAmount().isPresent() ? domain.getAmount().get().v() : null);
	}

	public void toEntityAnyItemOfMonthly150(AnyItemOfMonthly domain) {
		this.timeValue150 = (domain.getTime().isPresent() ? domain.getTime().get().v() : null);
		this.countValue150 = (domain.getTimes().isPresent() ? domain.getTimes().get().v().doubleValue() : null);
		this.moneyValue150 = (domain.getAmount().isPresent() ? domain.getAmount().get().v() : null);
	}

	public void toEntityAnyItemOfMonthly151(AnyItemOfMonthly domain) {
		this.timeValue151 = (domain.getTime().isPresent() ? domain.getTime().get().v() : null);
		this.countValue151 = (domain.getTimes().isPresent() ? domain.getTimes().get().v().doubleValue() : null);
		this.moneyValue151 = (domain.getAmount().isPresent() ? domain.getAmount().get().v() : null);
	}

	public void toEntityAnyItemOfMonthly152(AnyItemOfMonthly domain) {
		this.timeValue152 = (domain.getTime().isPresent() ? domain.getTime().get().v() : null);
		this.countValue152 = (domain.getTimes().isPresent() ? domain.getTimes().get().v().doubleValue() : null);
		this.moneyValue152 = (domain.getAmount().isPresent() ? domain.getAmount().get().v() : null);
	}

	public void toEntityAnyItemOfMonthly153(AnyItemOfMonthly domain) {
		this.timeValue153 = (domain.getTime().isPresent() ? domain.getTime().get().v() : null);
		this.countValue153 = (domain.getTimes().isPresent() ? domain.getTimes().get().v().doubleValue() : null);
		this.moneyValue153 = (domain.getAmount().isPresent() ? domain.getAmount().get().v() : null);
	}

	public void toEntityAnyItemOfMonthly154(AnyItemOfMonthly domain) {
		this.timeValue154 = (domain.getTime().isPresent() ? domain.getTime().get().v() : null);
		this.countValue154 = (domain.getTimes().isPresent() ? domain.getTimes().get().v().doubleValue() : null);
		this.moneyValue154 = (domain.getAmount().isPresent() ? domain.getAmount().get().v() : null);
	}

	public void toEntityAnyItemOfMonthly155(AnyItemOfMonthly domain) {
		this.timeValue155 = (domain.getTime().isPresent() ? domain.getTime().get().v() : null);
		this.countValue155 = (domain.getTimes().isPresent() ? domain.getTimes().get().v().doubleValue() : null);
		this.moneyValue155 = (domain.getAmount().isPresent() ? domain.getAmount().get().v() : null);
	}

	public void toEntityAnyItemOfMonthly156(AnyItemOfMonthly domain) {
		this.timeValue156 = (domain.getTime().isPresent() ? domain.getTime().get().v() : null);
		this.countValue156 = (domain.getTimes().isPresent() ? domain.getTimes().get().v().doubleValue() : null);
		this.moneyValue156 = (domain.getAmount().isPresent() ? domain.getAmount().get().v() : null);
	}

	public void toEntityAnyItemOfMonthly157(AnyItemOfMonthly domain) {
		this.timeValue157 = (domain.getTime().isPresent() ? domain.getTime().get().v() : null);
		this.countValue157 = (domain.getTimes().isPresent() ? domain.getTimes().get().v().doubleValue() : null);
		this.moneyValue157 = (domain.getAmount().isPresent() ? domain.getAmount().get().v() : null);
	}

	public void toEntityAnyItemOfMonthly158(AnyItemOfMonthly domain) {
		this.timeValue158 = (domain.getTime().isPresent() ? domain.getTime().get().v() : null);
		this.countValue158 = (domain.getTimes().isPresent() ? domain.getTimes().get().v().doubleValue() : null);
		this.moneyValue158 = (domain.getAmount().isPresent() ? domain.getAmount().get().v() : null);
	}

	public void toEntityAnyItemOfMonthly159(AnyItemOfMonthly domain) {
		this.timeValue159 = (domain.getTime().isPresent() ? domain.getTime().get().v() : null);
		this.countValue159 = (domain.getTimes().isPresent() ? domain.getTimes().get().v().doubleValue() : null);
		this.moneyValue159 = (domain.getAmount().isPresent() ? domain.getAmount().get().v() : null);
	}

	public void toEntityAnyItemOfMonthly160(AnyItemOfMonthly domain) {
		this.timeValue160 = (domain.getTime().isPresent() ? domain.getTime().get().v() : null);
		this.countValue160 = (domain.getTimes().isPresent() ? domain.getTimes().get().v().doubleValue() : null);
		this.moneyValue160 = (domain.getAmount().isPresent() ? domain.getAmount().get().v() : null);
	}

	public void toEntityAnyItemOfMonthly161(AnyItemOfMonthly domain) {
		this.timeValue161 = (domain.getTime().isPresent() ? domain.getTime().get().v() : null);
		this.countValue161 = (domain.getTimes().isPresent() ? domain.getTimes().get().v().doubleValue() : null);
		this.moneyValue161 = (domain.getAmount().isPresent() ? domain.getAmount().get().v() : null);
	}

	public void toEntityAnyItemOfMonthly162(AnyItemOfMonthly domain) {
		this.timeValue162 = (domain.getTime().isPresent() ? domain.getTime().get().v() : null);
		this.countValue162 = (domain.getTimes().isPresent() ? domain.getTimes().get().v().doubleValue() : null);
		this.moneyValue162 = (domain.getAmount().isPresent() ? domain.getAmount().get().v() : null);
	}

	public void toEntityAnyItemOfMonthly163(AnyItemOfMonthly domain) {
		this.timeValue163 = (domain.getTime().isPresent() ? domain.getTime().get().v() : null);
		this.countValue163 = (domain.getTimes().isPresent() ? domain.getTimes().get().v().doubleValue() : null);
		this.moneyValue163 = (domain.getAmount().isPresent() ? domain.getAmount().get().v() : null);
	}

	public void toEntityAnyItemOfMonthly164(AnyItemOfMonthly domain) {
		this.timeValue164 = (domain.getTime().isPresent() ? domain.getTime().get().v() : null);
		this.countValue164 = (domain.getTimes().isPresent() ? domain.getTimes().get().v().doubleValue() : null);
		this.moneyValue164 = (domain.getAmount().isPresent() ? domain.getAmount().get().v() : null);
	}

	public void toEntityAnyItemOfMonthly165(AnyItemOfMonthly domain) {
		this.timeValue165 = (domain.getTime().isPresent() ? domain.getTime().get().v() : null);
		this.countValue165 = (domain.getTimes().isPresent() ? domain.getTimes().get().v().doubleValue() : null);
		this.moneyValue165 = (domain.getAmount().isPresent() ? domain.getAmount().get().v() : null);
	}

	public void toEntityAnyItemOfMonthly166(AnyItemOfMonthly domain) {
		this.timeValue166 = (domain.getTime().isPresent() ? domain.getTime().get().v() : null);
		this.countValue166 = (domain.getTimes().isPresent() ? domain.getTimes().get().v().doubleValue() : null);
		this.moneyValue166 = (domain.getAmount().isPresent() ? domain.getAmount().get().v() : null);
	}

	public void toEntityAnyItemOfMonthly167(AnyItemOfMonthly domain) {
		this.timeValue167 = (domain.getTime().isPresent() ? domain.getTime().get().v() : null);
		this.countValue167 = (domain.getTimes().isPresent() ? domain.getTimes().get().v().doubleValue() : null);
		this.moneyValue167 = (domain.getAmount().isPresent() ? domain.getAmount().get().v() : null);
	}

	public void toEntityAnyItemOfMonthly168(AnyItemOfMonthly domain) {
		this.timeValue168 = (domain.getTime().isPresent() ? domain.getTime().get().v() : null);
		this.countValue168 = (domain.getTimes().isPresent() ? domain.getTimes().get().v().doubleValue() : null);
		this.moneyValue168 = (domain.getAmount().isPresent() ? domain.getAmount().get().v() : null);
	}

	public void toEntityAnyItemOfMonthly169(AnyItemOfMonthly domain) {
		this.timeValue169 = (domain.getTime().isPresent() ? domain.getTime().get().v() : null);
		this.countValue169 = (domain.getTimes().isPresent() ? domain.getTimes().get().v().doubleValue() : null);
		this.moneyValue169 = (domain.getAmount().isPresent() ? domain.getAmount().get().v() : null);
	}

	public void toEntityAnyItemOfMonthly170(AnyItemOfMonthly domain) {
		this.timeValue170 = (domain.getTime().isPresent() ? domain.getTime().get().v() : null);
		this.countValue170 = (domain.getTimes().isPresent() ? domain.getTimes().get().v().doubleValue() : null);
		this.moneyValue170 = (domain.getAmount().isPresent() ? domain.getAmount().get().v() : null);
	}

	public void toEntityAnyItemOfMonthly171(AnyItemOfMonthly domain) {
		this.timeValue171 = (domain.getTime().isPresent() ? domain.getTime().get().v() : null);
		this.countValue171 = (domain.getTimes().isPresent() ? domain.getTimes().get().v().doubleValue() : null);
		this.moneyValue171 = (domain.getAmount().isPresent() ? domain.getAmount().get().v() : null);
	}

	public void toEntityAnyItemOfMonthly172(AnyItemOfMonthly domain) {
		this.timeValue172 = (domain.getTime().isPresent() ? domain.getTime().get().v() : null);
		this.countValue172 = (domain.getTimes().isPresent() ? domain.getTimes().get().v().doubleValue() : null);
		this.moneyValue172 = (domain.getAmount().isPresent() ? domain.getAmount().get().v() : null);
	}

	public void toEntityAnyItemOfMonthly173(AnyItemOfMonthly domain) {
		this.timeValue173 = (domain.getTime().isPresent() ? domain.getTime().get().v() : null);
		this.countValue173 = (domain.getTimes().isPresent() ? domain.getTimes().get().v().doubleValue() : null);
		this.moneyValue173 = (domain.getAmount().isPresent() ? domain.getAmount().get().v() : null);
	}

	public void toEntityAnyItemOfMonthly174(AnyItemOfMonthly domain) {
		this.timeValue174 = (domain.getTime().isPresent() ? domain.getTime().get().v() : null);
		this.countValue174 = (domain.getTimes().isPresent() ? domain.getTimes().get().v().doubleValue() : null);
		this.moneyValue174 = (domain.getAmount().isPresent() ? domain.getAmount().get().v() : null);
	}

	public void toEntityAnyItemOfMonthly175(AnyItemOfMonthly domain) {
		this.timeValue175 = (domain.getTime().isPresent() ? domain.getTime().get().v() : null);
		this.countValue175 = (domain.getTimes().isPresent() ? domain.getTimes().get().v().doubleValue() : null);
		this.moneyValue175 = (domain.getAmount().isPresent() ? domain.getAmount().get().v() : null);
	}

	public void toEntityAnyItemOfMonthly176(AnyItemOfMonthly domain) {
		this.timeValue176 = (domain.getTime().isPresent() ? domain.getTime().get().v() : null);
		this.countValue176 = (domain.getTimes().isPresent() ? domain.getTimes().get().v().doubleValue() : null);
		this.moneyValue176 = (domain.getAmount().isPresent() ? domain.getAmount().get().v() : null);
	}

	public void toEntityAnyItemOfMonthly177(AnyItemOfMonthly domain) {
		this.timeValue177 = (domain.getTime().isPresent() ? domain.getTime().get().v() : null);
		this.countValue177 = (domain.getTimes().isPresent() ? domain.getTimes().get().v().doubleValue() : null);
		this.moneyValue177 = (domain.getAmount().isPresent() ? domain.getAmount().get().v() : null);
	}

	public void toEntityAnyItemOfMonthly178(AnyItemOfMonthly domain) {
		this.timeValue178 = (domain.getTime().isPresent() ? domain.getTime().get().v() : null);
		this.countValue178 = (domain.getTimes().isPresent() ? domain.getTimes().get().v().doubleValue() : null);
		this.moneyValue178 = (domain.getAmount().isPresent() ? domain.getAmount().get().v() : null);
	}

	public void toEntityAnyItemOfMonthly179(AnyItemOfMonthly domain) {
		this.timeValue179 = (domain.getTime().isPresent() ? domain.getTime().get().v() : null);
		this.countValue179 = (domain.getTimes().isPresent() ? domain.getTimes().get().v().doubleValue() : null);
		this.moneyValue179 = (domain.getAmount().isPresent() ? domain.getAmount().get().v() : null);
	}

	public void toEntityAnyItemOfMonthly180(AnyItemOfMonthly domain) {
		this.timeValue180 = (domain.getTime().isPresent() ? domain.getTime().get().v() : null);
		this.countValue180 = (domain.getTimes().isPresent() ? domain.getTimes().get().v().doubleValue() : null);
		this.moneyValue180 = (domain.getAmount().isPresent() ? domain.getAmount().get().v() : null);
	}

	public void toEntityAnyItemOfMonthly181(AnyItemOfMonthly domain) {
		this.timeValue181 = (domain.getTime().isPresent() ? domain.getTime().get().v() : null);
		this.countValue181 = (domain.getTimes().isPresent() ? domain.getTimes().get().v().doubleValue() : null);
		this.moneyValue181 = (domain.getAmount().isPresent() ? domain.getAmount().get().v() : null);
	}

	public void toEntityAnyItemOfMonthly182(AnyItemOfMonthly domain) {
		this.timeValue182 = (domain.getTime().isPresent() ? domain.getTime().get().v() : null);
		this.countValue182 = (domain.getTimes().isPresent() ? domain.getTimes().get().v().doubleValue() : null);
		this.moneyValue182 = (domain.getAmount().isPresent() ? domain.getAmount().get().v() : null);
	}

	public void toEntityAnyItemOfMonthly183(AnyItemOfMonthly domain) {
		this.timeValue183 = (domain.getTime().isPresent() ? domain.getTime().get().v() : null);
		this.countValue183 = (domain.getTimes().isPresent() ? domain.getTimes().get().v().doubleValue() : null);
		this.moneyValue183 = (domain.getAmount().isPresent() ? domain.getAmount().get().v() : null);
	}

	public void toEntityAnyItemOfMonthly184(AnyItemOfMonthly domain) {
		this.timeValue184 = (domain.getTime().isPresent() ? domain.getTime().get().v() : null);
		this.countValue184 = (domain.getTimes().isPresent() ? domain.getTimes().get().v().doubleValue() : null);
		this.moneyValue184 = (domain.getAmount().isPresent() ? domain.getAmount().get().v() : null);
	}

	public void toEntityAnyItemOfMonthly185(AnyItemOfMonthly domain) {
		this.timeValue185 = (domain.getTime().isPresent() ? domain.getTime().get().v() : null);
		this.countValue185 = (domain.getTimes().isPresent() ? domain.getTimes().get().v().doubleValue() : null);
		this.moneyValue185 = (domain.getAmount().isPresent() ? domain.getAmount().get().v() : null);
	}

	public void toEntityAnyItemOfMonthly186(AnyItemOfMonthly domain) {
		this.timeValue186 = (domain.getTime().isPresent() ? domain.getTime().get().v() : null);
		this.countValue186 = (domain.getTimes().isPresent() ? domain.getTimes().get().v().doubleValue() : null);
		this.moneyValue186 = (domain.getAmount().isPresent() ? domain.getAmount().get().v() : null);
	}

	public void toEntityAnyItemOfMonthly187(AnyItemOfMonthly domain) {
		this.timeValue187 = (domain.getTime().isPresent() ? domain.getTime().get().v() : null);
		this.countValue187 = (domain.getTimes().isPresent() ? domain.getTimes().get().v().doubleValue() : null);
		this.moneyValue187 = (domain.getAmount().isPresent() ? domain.getAmount().get().v() : null);
	}

	public void toEntityAnyItemOfMonthly188(AnyItemOfMonthly domain) {
		this.timeValue188 = (domain.getTime().isPresent() ? domain.getTime().get().v() : null);
		this.countValue188 = (domain.getTimes().isPresent() ? domain.getTimes().get().v().doubleValue() : null);
		this.moneyValue188 = (domain.getAmount().isPresent() ? domain.getAmount().get().v() : null);
	}

	public void toEntityAnyItemOfMonthly189(AnyItemOfMonthly domain) {
		this.timeValue189 = (domain.getTime().isPresent() ? domain.getTime().get().v() : null);
		this.countValue189 = (domain.getTimes().isPresent() ? domain.getTimes().get().v().doubleValue() : null);
		this.moneyValue189 = (domain.getAmount().isPresent() ? domain.getAmount().get().v() : null);
	}

	public void toEntityAnyItemOfMonthly190(AnyItemOfMonthly domain) {
		this.timeValue190 = (domain.getTime().isPresent() ? domain.getTime().get().v() : null);
		this.countValue190 = (domain.getTimes().isPresent() ? domain.getTimes().get().v().doubleValue() : null);
		this.moneyValue190 = (domain.getAmount().isPresent() ? domain.getAmount().get().v() : null);
	}

	public void toEntityAnyItemOfMonthly191(AnyItemOfMonthly domain) {
		this.timeValue191 = (domain.getTime().isPresent() ? domain.getTime().get().v() : null);
		this.countValue191 = (domain.getTimes().isPresent() ? domain.getTimes().get().v().doubleValue() : null);
		this.moneyValue191 = (domain.getAmount().isPresent() ? domain.getAmount().get().v() : null);
	}

	public void toEntityAnyItemOfMonthly192(AnyItemOfMonthly domain) {
		this.timeValue192 = (domain.getTime().isPresent() ? domain.getTime().get().v() : null);
		this.countValue192 = (domain.getTimes().isPresent() ? domain.getTimes().get().v().doubleValue() : null);
		this.moneyValue192 = (domain.getAmount().isPresent() ? domain.getAmount().get().v() : null);
	}

	public void toEntityAnyItemOfMonthly193(AnyItemOfMonthly domain) {
		this.timeValue193 = (domain.getTime().isPresent() ? domain.getTime().get().v() : null);
		this.countValue193 = (domain.getTimes().isPresent() ? domain.getTimes().get().v().doubleValue() : null);
		this.moneyValue193 = (domain.getAmount().isPresent() ? domain.getAmount().get().v() : null);
	}

	public void toEntityAnyItemOfMonthly194(AnyItemOfMonthly domain) {
		this.timeValue194 = (domain.getTime().isPresent() ? domain.getTime().get().v() : null);
		this.countValue194 = (domain.getTimes().isPresent() ? domain.getTimes().get().v().doubleValue() : null);
		this.moneyValue194 = (domain.getAmount().isPresent() ? domain.getAmount().get().v() : null);
	}

	public void toEntityAnyItemOfMonthly195(AnyItemOfMonthly domain) {
		this.timeValue195 = (domain.getTime().isPresent() ? domain.getTime().get().v() : null);
		this.countValue195 = (domain.getTimes().isPresent() ? domain.getTimes().get().v().doubleValue() : null);
		this.moneyValue195 = (domain.getAmount().isPresent() ? domain.getAmount().get().v() : null);
	}

	public void toEntityAnyItemOfMonthly196(AnyItemOfMonthly domain) {
		this.timeValue196 = (domain.getTime().isPresent() ? domain.getTime().get().v() : null);
		this.countValue196 = (domain.getTimes().isPresent() ? domain.getTimes().get().v().doubleValue() : null);
		this.moneyValue196 = (domain.getAmount().isPresent() ? domain.getAmount().get().v() : null);
	}

	public void toEntityAnyItemOfMonthly197(AnyItemOfMonthly domain) {
		this.timeValue197 = (domain.getTime().isPresent() ? domain.getTime().get().v() : null);
		this.countValue197 = (domain.getTimes().isPresent() ? domain.getTimes().get().v().doubleValue() : null);
		this.moneyValue197 = (domain.getAmount().isPresent() ? domain.getAmount().get().v() : null);
	}

	public void toEntityAnyItemOfMonthly198(AnyItemOfMonthly domain) {
		this.timeValue198 = (domain.getTime().isPresent() ? domain.getTime().get().v() : null);
		this.countValue198 = (domain.getTimes().isPresent() ? domain.getTimes().get().v().doubleValue() : null);
		this.moneyValue198 = (domain.getAmount().isPresent() ? domain.getAmount().get().v() : null);
	}

	public void toEntityAnyItemOfMonthly199(AnyItemOfMonthly domain) {
		this.timeValue199 = (domain.getTime().isPresent() ? domain.getTime().get().v() : null);
		this.countValue199 = (domain.getTimes().isPresent() ? domain.getTimes().get().v().doubleValue() : null);
		this.moneyValue199 = (domain.getAmount().isPresent() ? domain.getAmount().get().v() : null);
	}

	public void toEntityAnyItemOfMonthly200(AnyItemOfMonthly domain) {
		this.timeValue200 = (domain.getTime().isPresent() ? domain.getTime().get().v() : null);
		this.countValue200 = (domain.getTimes().isPresent() ? domain.getTimes().get().v().doubleValue() : null);
		this.moneyValue200 = (domain.getAmount().isPresent() ? domain.getAmount().get().v() : null);
	}
	
	
	public void toEntityAnyItemOfMonthly(AnyItemOfMonthlyMerge domain) {
		this.toEntityAnyItemOfMonthly1(domain.getAnyItemOfMonthly1());
		this.toEntityAnyItemOfMonthly2(domain.getAnyItemOfMonthly2());
		this.toEntityAnyItemOfMonthly3(domain.getAnyItemOfMonthly3());
		this.toEntityAnyItemOfMonthly4(domain.getAnyItemOfMonthly4());
		this.toEntityAnyItemOfMonthly5(domain.getAnyItemOfMonthly5());
		this.toEntityAnyItemOfMonthly6(domain.getAnyItemOfMonthly6());
		this.toEntityAnyItemOfMonthly7(domain.getAnyItemOfMonthly7());
		this.toEntityAnyItemOfMonthly8(domain.getAnyItemOfMonthly8());
		this.toEntityAnyItemOfMonthly9(domain.getAnyItemOfMonthly9());
		this.toEntityAnyItemOfMonthly10(domain.getAnyItemOfMonthly10());
		this.toEntityAnyItemOfMonthly11(domain.getAnyItemOfMonthly11());
		this.toEntityAnyItemOfMonthly12(domain.getAnyItemOfMonthly12());
		this.toEntityAnyItemOfMonthly13(domain.getAnyItemOfMonthly13());
		this.toEntityAnyItemOfMonthly14(domain.getAnyItemOfMonthly14());
		this.toEntityAnyItemOfMonthly15(domain.getAnyItemOfMonthly15());
		this.toEntityAnyItemOfMonthly16(domain.getAnyItemOfMonthly16());
		this.toEntityAnyItemOfMonthly17(domain.getAnyItemOfMonthly17());
		this.toEntityAnyItemOfMonthly18(domain.getAnyItemOfMonthly18());
		this.toEntityAnyItemOfMonthly19(domain.getAnyItemOfMonthly19());
		this.toEntityAnyItemOfMonthly20(domain.getAnyItemOfMonthly20());
		this.toEntityAnyItemOfMonthly21(domain.getAnyItemOfMonthly21());
		this.toEntityAnyItemOfMonthly22(domain.getAnyItemOfMonthly22());
		this.toEntityAnyItemOfMonthly23(domain.getAnyItemOfMonthly23());
		this.toEntityAnyItemOfMonthly24(domain.getAnyItemOfMonthly24());
		this.toEntityAnyItemOfMonthly25(domain.getAnyItemOfMonthly25());
		this.toEntityAnyItemOfMonthly26(domain.getAnyItemOfMonthly26());
		this.toEntityAnyItemOfMonthly27(domain.getAnyItemOfMonthly27());
		this.toEntityAnyItemOfMonthly28(domain.getAnyItemOfMonthly28());
		this.toEntityAnyItemOfMonthly29(domain.getAnyItemOfMonthly29());
		this.toEntityAnyItemOfMonthly30(domain.getAnyItemOfMonthly30());
		this.toEntityAnyItemOfMonthly31(domain.getAnyItemOfMonthly31());
		this.toEntityAnyItemOfMonthly32(domain.getAnyItemOfMonthly32());
		this.toEntityAnyItemOfMonthly33(domain.getAnyItemOfMonthly33());
		this.toEntityAnyItemOfMonthly34(domain.getAnyItemOfMonthly34());
		this.toEntityAnyItemOfMonthly35(domain.getAnyItemOfMonthly35());
		this.toEntityAnyItemOfMonthly36(domain.getAnyItemOfMonthly36());
		this.toEntityAnyItemOfMonthly37(domain.getAnyItemOfMonthly37());
		this.toEntityAnyItemOfMonthly38(domain.getAnyItemOfMonthly38());
		this.toEntityAnyItemOfMonthly39(domain.getAnyItemOfMonthly39());
		this.toEntityAnyItemOfMonthly40(domain.getAnyItemOfMonthly40());
		this.toEntityAnyItemOfMonthly41(domain.getAnyItemOfMonthly41());
		this.toEntityAnyItemOfMonthly42(domain.getAnyItemOfMonthly42());
		this.toEntityAnyItemOfMonthly43(domain.getAnyItemOfMonthly43());
		this.toEntityAnyItemOfMonthly44(domain.getAnyItemOfMonthly44());
		this.toEntityAnyItemOfMonthly45(domain.getAnyItemOfMonthly45());
		this.toEntityAnyItemOfMonthly46(domain.getAnyItemOfMonthly46());
		this.toEntityAnyItemOfMonthly47(domain.getAnyItemOfMonthly47());
		this.toEntityAnyItemOfMonthly48(domain.getAnyItemOfMonthly48());
		this.toEntityAnyItemOfMonthly49(domain.getAnyItemOfMonthly49());
		this.toEntityAnyItemOfMonthly50(domain.getAnyItemOfMonthly50());
		this.toEntityAnyItemOfMonthly51(domain.getAnyItemOfMonthly51());
		this.toEntityAnyItemOfMonthly52(domain.getAnyItemOfMonthly52());
		this.toEntityAnyItemOfMonthly53(domain.getAnyItemOfMonthly53());
		this.toEntityAnyItemOfMonthly54(domain.getAnyItemOfMonthly54());
		this.toEntityAnyItemOfMonthly55(domain.getAnyItemOfMonthly55());
		this.toEntityAnyItemOfMonthly56(domain.getAnyItemOfMonthly56());
		this.toEntityAnyItemOfMonthly57(domain.getAnyItemOfMonthly57());
		this.toEntityAnyItemOfMonthly58(domain.getAnyItemOfMonthly58());
		this.toEntityAnyItemOfMonthly59(domain.getAnyItemOfMonthly59());
		this.toEntityAnyItemOfMonthly60(domain.getAnyItemOfMonthly60());
		this.toEntityAnyItemOfMonthly61(domain.getAnyItemOfMonthly61());
		this.toEntityAnyItemOfMonthly62(domain.getAnyItemOfMonthly62());
		this.toEntityAnyItemOfMonthly63(domain.getAnyItemOfMonthly63());
		this.toEntityAnyItemOfMonthly64(domain.getAnyItemOfMonthly64());
		this.toEntityAnyItemOfMonthly65(domain.getAnyItemOfMonthly65());
		this.toEntityAnyItemOfMonthly66(domain.getAnyItemOfMonthly66());
		this.toEntityAnyItemOfMonthly67(domain.getAnyItemOfMonthly67());
		this.toEntityAnyItemOfMonthly68(domain.getAnyItemOfMonthly68());
		this.toEntityAnyItemOfMonthly69(domain.getAnyItemOfMonthly69());
		this.toEntityAnyItemOfMonthly70(domain.getAnyItemOfMonthly70());
		this.toEntityAnyItemOfMonthly71(domain.getAnyItemOfMonthly71());
		this.toEntityAnyItemOfMonthly72(domain.getAnyItemOfMonthly72());
		this.toEntityAnyItemOfMonthly73(domain.getAnyItemOfMonthly73());
		this.toEntityAnyItemOfMonthly74(domain.getAnyItemOfMonthly74());
		this.toEntityAnyItemOfMonthly75(domain.getAnyItemOfMonthly75());
		this.toEntityAnyItemOfMonthly76(domain.getAnyItemOfMonthly76());
		this.toEntityAnyItemOfMonthly77(domain.getAnyItemOfMonthly77());
		this.toEntityAnyItemOfMonthly78(domain.getAnyItemOfMonthly78());
		this.toEntityAnyItemOfMonthly79(domain.getAnyItemOfMonthly79());
		this.toEntityAnyItemOfMonthly80(domain.getAnyItemOfMonthly80());
		this.toEntityAnyItemOfMonthly81(domain.getAnyItemOfMonthly81());
		this.toEntityAnyItemOfMonthly82(domain.getAnyItemOfMonthly82());
		this.toEntityAnyItemOfMonthly83(domain.getAnyItemOfMonthly83());
		this.toEntityAnyItemOfMonthly84(domain.getAnyItemOfMonthly84());
		this.toEntityAnyItemOfMonthly85(domain.getAnyItemOfMonthly85());
		this.toEntityAnyItemOfMonthly86(domain.getAnyItemOfMonthly86());
		this.toEntityAnyItemOfMonthly87(domain.getAnyItemOfMonthly87());
		this.toEntityAnyItemOfMonthly88(domain.getAnyItemOfMonthly88());
		this.toEntityAnyItemOfMonthly89(domain.getAnyItemOfMonthly89());
		this.toEntityAnyItemOfMonthly90(domain.getAnyItemOfMonthly90());
		this.toEntityAnyItemOfMonthly91(domain.getAnyItemOfMonthly91());
		this.toEntityAnyItemOfMonthly92(domain.getAnyItemOfMonthly92());
		this.toEntityAnyItemOfMonthly93(domain.getAnyItemOfMonthly93());
		this.toEntityAnyItemOfMonthly94(domain.getAnyItemOfMonthly94());
		this.toEntityAnyItemOfMonthly95(domain.getAnyItemOfMonthly95());
		this.toEntityAnyItemOfMonthly96(domain.getAnyItemOfMonthly96());
		this.toEntityAnyItemOfMonthly97(domain.getAnyItemOfMonthly97());
		this.toEntityAnyItemOfMonthly98(domain.getAnyItemOfMonthly98());
		this.toEntityAnyItemOfMonthly99(domain.getAnyItemOfMonthly99());
		this.toEntityAnyItemOfMonthly100(domain.getAnyItemOfMonthly100());
		this.toEntityAnyItemOfMonthly101(domain.getAnyItemOfMonthly101());
		this.toEntityAnyItemOfMonthly102(domain.getAnyItemOfMonthly102());
		this.toEntityAnyItemOfMonthly103(domain.getAnyItemOfMonthly103());
		this.toEntityAnyItemOfMonthly104(domain.getAnyItemOfMonthly104());
		this.toEntityAnyItemOfMonthly105(domain.getAnyItemOfMonthly105());
		this.toEntityAnyItemOfMonthly106(domain.getAnyItemOfMonthly106());
		this.toEntityAnyItemOfMonthly107(domain.getAnyItemOfMonthly107());
		this.toEntityAnyItemOfMonthly108(domain.getAnyItemOfMonthly108());
		this.toEntityAnyItemOfMonthly109(domain.getAnyItemOfMonthly109());
		this.toEntityAnyItemOfMonthly110(domain.getAnyItemOfMonthly110());
		this.toEntityAnyItemOfMonthly111(domain.getAnyItemOfMonthly111());
		this.toEntityAnyItemOfMonthly112(domain.getAnyItemOfMonthly112());
		this.toEntityAnyItemOfMonthly113(domain.getAnyItemOfMonthly113());
		this.toEntityAnyItemOfMonthly114(domain.getAnyItemOfMonthly114());
		this.toEntityAnyItemOfMonthly115(domain.getAnyItemOfMonthly115());
		this.toEntityAnyItemOfMonthly116(domain.getAnyItemOfMonthly116());
		this.toEntityAnyItemOfMonthly117(domain.getAnyItemOfMonthly117());
		this.toEntityAnyItemOfMonthly118(domain.getAnyItemOfMonthly118());
		this.toEntityAnyItemOfMonthly119(domain.getAnyItemOfMonthly119());
		this.toEntityAnyItemOfMonthly120(domain.getAnyItemOfMonthly120());
		this.toEntityAnyItemOfMonthly121(domain.getAnyItemOfMonthly121());
		this.toEntityAnyItemOfMonthly122(domain.getAnyItemOfMonthly122());
		this.toEntityAnyItemOfMonthly123(domain.getAnyItemOfMonthly123());
		this.toEntityAnyItemOfMonthly124(domain.getAnyItemOfMonthly124());
		this.toEntityAnyItemOfMonthly125(domain.getAnyItemOfMonthly125());
		this.toEntityAnyItemOfMonthly126(domain.getAnyItemOfMonthly126());
		this.toEntityAnyItemOfMonthly127(domain.getAnyItemOfMonthly127());
		this.toEntityAnyItemOfMonthly128(domain.getAnyItemOfMonthly128());
		this.toEntityAnyItemOfMonthly129(domain.getAnyItemOfMonthly129());
		this.toEntityAnyItemOfMonthly130(domain.getAnyItemOfMonthly130());
		this.toEntityAnyItemOfMonthly131(domain.getAnyItemOfMonthly131());
		this.toEntityAnyItemOfMonthly132(domain.getAnyItemOfMonthly132());
		this.toEntityAnyItemOfMonthly133(domain.getAnyItemOfMonthly133());
		this.toEntityAnyItemOfMonthly134(domain.getAnyItemOfMonthly134());
		this.toEntityAnyItemOfMonthly135(domain.getAnyItemOfMonthly135());
		this.toEntityAnyItemOfMonthly136(domain.getAnyItemOfMonthly136());
		this.toEntityAnyItemOfMonthly137(domain.getAnyItemOfMonthly137());
		this.toEntityAnyItemOfMonthly138(domain.getAnyItemOfMonthly138());
		this.toEntityAnyItemOfMonthly139(domain.getAnyItemOfMonthly139());
		this.toEntityAnyItemOfMonthly140(domain.getAnyItemOfMonthly140());
		this.toEntityAnyItemOfMonthly141(domain.getAnyItemOfMonthly141());
		this.toEntityAnyItemOfMonthly142(domain.getAnyItemOfMonthly142());
		this.toEntityAnyItemOfMonthly143(domain.getAnyItemOfMonthly143());
		this.toEntityAnyItemOfMonthly144(domain.getAnyItemOfMonthly144());
		this.toEntityAnyItemOfMonthly145(domain.getAnyItemOfMonthly145());
		this.toEntityAnyItemOfMonthly146(domain.getAnyItemOfMonthly146());
		this.toEntityAnyItemOfMonthly147(domain.getAnyItemOfMonthly147());
		this.toEntityAnyItemOfMonthly148(domain.getAnyItemOfMonthly148());
		this.toEntityAnyItemOfMonthly149(domain.getAnyItemOfMonthly149());
		this.toEntityAnyItemOfMonthly150(domain.getAnyItemOfMonthly150());
		this.toEntityAnyItemOfMonthly151(domain.getAnyItemOfMonthly151());
		this.toEntityAnyItemOfMonthly152(domain.getAnyItemOfMonthly152());
		this.toEntityAnyItemOfMonthly153(domain.getAnyItemOfMonthly153());
		this.toEntityAnyItemOfMonthly154(domain.getAnyItemOfMonthly154());
		this.toEntityAnyItemOfMonthly155(domain.getAnyItemOfMonthly155());
		this.toEntityAnyItemOfMonthly156(domain.getAnyItemOfMonthly156());
		this.toEntityAnyItemOfMonthly157(domain.getAnyItemOfMonthly157());
		this.toEntityAnyItemOfMonthly158(domain.getAnyItemOfMonthly158());
		this.toEntityAnyItemOfMonthly159(domain.getAnyItemOfMonthly159());
		this.toEntityAnyItemOfMonthly160(domain.getAnyItemOfMonthly160());
		this.toEntityAnyItemOfMonthly161(domain.getAnyItemOfMonthly161());
		this.toEntityAnyItemOfMonthly162(domain.getAnyItemOfMonthly162());
		this.toEntityAnyItemOfMonthly163(domain.getAnyItemOfMonthly163());
		this.toEntityAnyItemOfMonthly164(domain.getAnyItemOfMonthly164());
		this.toEntityAnyItemOfMonthly165(domain.getAnyItemOfMonthly165());
		this.toEntityAnyItemOfMonthly166(domain.getAnyItemOfMonthly166());
		this.toEntityAnyItemOfMonthly167(domain.getAnyItemOfMonthly167());
		this.toEntityAnyItemOfMonthly168(domain.getAnyItemOfMonthly168());
		this.toEntityAnyItemOfMonthly169(domain.getAnyItemOfMonthly169());
		this.toEntityAnyItemOfMonthly170(domain.getAnyItemOfMonthly170());
		this.toEntityAnyItemOfMonthly171(domain.getAnyItemOfMonthly171());
		this.toEntityAnyItemOfMonthly172(domain.getAnyItemOfMonthly172());
		this.toEntityAnyItemOfMonthly173(domain.getAnyItemOfMonthly173());
		this.toEntityAnyItemOfMonthly174(domain.getAnyItemOfMonthly174());
		this.toEntityAnyItemOfMonthly175(domain.getAnyItemOfMonthly175());
		this.toEntityAnyItemOfMonthly176(domain.getAnyItemOfMonthly176());
		this.toEntityAnyItemOfMonthly177(domain.getAnyItemOfMonthly177());
		this.toEntityAnyItemOfMonthly178(domain.getAnyItemOfMonthly178());
		this.toEntityAnyItemOfMonthly179(domain.getAnyItemOfMonthly179());
		this.toEntityAnyItemOfMonthly180(domain.getAnyItemOfMonthly180());
		this.toEntityAnyItemOfMonthly181(domain.getAnyItemOfMonthly181());
		this.toEntityAnyItemOfMonthly182(domain.getAnyItemOfMonthly182());
		this.toEntityAnyItemOfMonthly183(domain.getAnyItemOfMonthly183());
		this.toEntityAnyItemOfMonthly184(domain.getAnyItemOfMonthly184());
		this.toEntityAnyItemOfMonthly185(domain.getAnyItemOfMonthly185());
		this.toEntityAnyItemOfMonthly186(domain.getAnyItemOfMonthly186());
		this.toEntityAnyItemOfMonthly187(domain.getAnyItemOfMonthly187());
		this.toEntityAnyItemOfMonthly188(domain.getAnyItemOfMonthly188());
		this.toEntityAnyItemOfMonthly189(domain.getAnyItemOfMonthly189());
		this.toEntityAnyItemOfMonthly190(domain.getAnyItemOfMonthly190());
		this.toEntityAnyItemOfMonthly191(domain.getAnyItemOfMonthly191());
		this.toEntityAnyItemOfMonthly192(domain.getAnyItemOfMonthly192());
		this.toEntityAnyItemOfMonthly193(domain.getAnyItemOfMonthly193());
		this.toEntityAnyItemOfMonthly194(domain.getAnyItemOfMonthly194());
		this.toEntityAnyItemOfMonthly195(domain.getAnyItemOfMonthly195());
		this.toEntityAnyItemOfMonthly196(domain.getAnyItemOfMonthly196());
		this.toEntityAnyItemOfMonthly197(domain.getAnyItemOfMonthly197());
		this.toEntityAnyItemOfMonthly198(domain.getAnyItemOfMonthly198());
		this.toEntityAnyItemOfMonthly199(domain.getAnyItemOfMonthly199());
		this.toEntityAnyItemOfMonthly200(domain.getAnyItemOfMonthly200());

	}
	
	
	/**
	 * ドメインに変換
	 * @return 集計任意項目
	 */
	public AnyItemOfMonthly toDomainAnyItemOfMonthly1(){
		//get  value anyItemId  = 0 temporary because table have not anyitemId
		return AnyItemOfMonthly.of(
				this.krcdtMonAnyItemValuePk.getEmployeeId(),
				new YearMonth(this.krcdtMonAnyItemValuePk.getYearMonth()),
				EnumAdaptor.valueOf(this.krcdtMonAnyItemValuePk.getClosureId(), ClosureId.class),
				new ClosureDate(this.krcdtMonAnyItemValuePk.getClosureDay(), (this.krcdtMonAnyItemValuePk.getIsLastDay() == 1)),
				0,
			(this.timeValue1 == null ? Optional.empty() : Optional.of(new AnyTimeMonth(this.timeValue1))),
			(this.countValue1 == null ? Optional.empty() : Optional.of(new AnyTimesMonth(this.countValue1))),
			(this.moneyValue1 == null ? Optional.empty() : Optional.of(new AnyAmountMonth(this.moneyValue1))));
	}
	
	public AnyItemOfMonthly toDomainAnyItemOfMonthly2() {
		// get value anyItemId = 0 temporary because table have not anyitemId
		return AnyItemOfMonthly.of(this.krcdtMonAnyItemValuePk.getEmployeeId(),
				new YearMonth(this.krcdtMonAnyItemValuePk.getYearMonth()),
				EnumAdaptor.valueOf(this.krcdtMonAnyItemValuePk.getClosureId(), ClosureId.class),
				new ClosureDate(this.krcdtMonAnyItemValuePk.getClosureDay(),
					(this.krcdtMonAnyItemValuePk.getIsLastDay() == 1)),
				0, (this.timeValue2 == null ? Optional.empty() : Optional.of(new AnyTimeMonth(this.timeValue2))),
			(this.countValue2 == null ? Optional.empty() : Optional.of(new AnyTimesMonth(this.countValue2))),
			(this.moneyValue2 == null ? Optional.empty() : Optional.of(new AnyAmountMonth(this.moneyValue2))));
	}

	public AnyItemOfMonthly toDomainAnyItemOfMonthly3() {
		// get value anyItemId = 0 temporary because table have not anyitemId
		return AnyItemOfMonthly.of(this.krcdtMonAnyItemValuePk.getEmployeeId(),
				new YearMonth(this.krcdtMonAnyItemValuePk.getYearMonth()),
				EnumAdaptor.valueOf(this.krcdtMonAnyItemValuePk.getClosureId(), ClosureId.class),
				new ClosureDate(this.krcdtMonAnyItemValuePk.getClosureDay(),
					(this.krcdtMonAnyItemValuePk.getIsLastDay() == 1)),
				0, (this.timeValue3 == null ? Optional.empty() : Optional.of(new AnyTimeMonth(this.timeValue3))),
			(this.countValue3 == null ? Optional.empty() : Optional.of(new AnyTimesMonth(this.countValue3))),
			(this.moneyValue3 == null ? Optional.empty() : Optional.of(new AnyAmountMonth(this.moneyValue3))));
	}

	public AnyItemOfMonthly toDomainAnyItemOfMonthly4() {
		// get value anyItemId = 0 temporary because table have not anyitemId
		return AnyItemOfMonthly.of(this.krcdtMonAnyItemValuePk.getEmployeeId(),
				new YearMonth(this.krcdtMonAnyItemValuePk.getYearMonth()),
				EnumAdaptor.valueOf(this.krcdtMonAnyItemValuePk.getClosureId(), ClosureId.class),
				new ClosureDate(this.krcdtMonAnyItemValuePk.getClosureDay(),
					(this.krcdtMonAnyItemValuePk.getIsLastDay() == 1)),
				0, (this.timeValue4 == null ? Optional.empty() : Optional.of(new AnyTimeMonth(this.timeValue4))),
			(this.countValue4 == null ? Optional.empty() : Optional.of(new AnyTimesMonth(this.countValue4))),
			(this.moneyValue4 == null ? Optional.empty() : Optional.of(new AnyAmountMonth(this.moneyValue4))));
	}

	public AnyItemOfMonthly toDomainAnyItemOfMonthly5() {
		// get value anyItemId = 0 temporary because table have not anyitemId
		return AnyItemOfMonthly.of(this.krcdtMonAnyItemValuePk.getEmployeeId(),
				new YearMonth(this.krcdtMonAnyItemValuePk.getYearMonth()),
				EnumAdaptor.valueOf(this.krcdtMonAnyItemValuePk.getClosureId(), ClosureId.class),
				new ClosureDate(this.krcdtMonAnyItemValuePk.getClosureDay(),
					(this.krcdtMonAnyItemValuePk.getIsLastDay() == 1)),
				0, (this.timeValue5 == null ? Optional.empty() : Optional.of(new AnyTimeMonth(this.timeValue5))),
			(this.countValue5 == null ? Optional.empty() : Optional.of(new AnyTimesMonth(this.countValue5))),
			(this.moneyValue5 == null ? Optional.empty() : Optional.of(new AnyAmountMonth(this.moneyValue5))));
	}

	public AnyItemOfMonthly toDomainAnyItemOfMonthly6() {
		// get value anyItemId = 0 temporary because table have not anyitemId
		return AnyItemOfMonthly.of(this.krcdtMonAnyItemValuePk.getEmployeeId(),
				new YearMonth(this.krcdtMonAnyItemValuePk.getYearMonth()),
				EnumAdaptor.valueOf(this.krcdtMonAnyItemValuePk.getClosureId(), ClosureId.class),
				new ClosureDate(this.krcdtMonAnyItemValuePk.getClosureDay(),
					(this.krcdtMonAnyItemValuePk.getIsLastDay() == 1)),
				0, (this.timeValue6 == null ? Optional.empty() : Optional.of(new AnyTimeMonth(this.timeValue6))),
			(this.countValue6 == null ? Optional.empty() : Optional.of(new AnyTimesMonth(this.countValue6))),
			(this.moneyValue6 == null ? Optional.empty() : Optional.of(new AnyAmountMonth(this.moneyValue6))));
	}

	public AnyItemOfMonthly toDomainAnyItemOfMonthly7() {
		// get value anyItemId = 0 temporary because table have not anyitemId
		return AnyItemOfMonthly.of(this.krcdtMonAnyItemValuePk.getEmployeeId(),
				new YearMonth(this.krcdtMonAnyItemValuePk.getYearMonth()),
				EnumAdaptor.valueOf(this.krcdtMonAnyItemValuePk.getClosureId(), ClosureId.class),
				new ClosureDate(this.krcdtMonAnyItemValuePk.getClosureDay(),
					(this.krcdtMonAnyItemValuePk.getIsLastDay() == 1)),
				0, (this.timeValue7 == null ? Optional.empty() : Optional.of(new AnyTimeMonth(this.timeValue7))),
			(this.countValue7 == null ? Optional.empty() : Optional.of(new AnyTimesMonth(this.countValue7))),
			(this.moneyValue7 == null ? Optional.empty() : Optional.of(new AnyAmountMonth(this.moneyValue7))));
	}

	public AnyItemOfMonthly toDomainAnyItemOfMonthly8() {
		// get value anyItemId = 0 temporary because table have not anyitemId
		return AnyItemOfMonthly.of(this.krcdtMonAnyItemValuePk.getEmployeeId(),
				new YearMonth(this.krcdtMonAnyItemValuePk.getYearMonth()),
				EnumAdaptor.valueOf(this.krcdtMonAnyItemValuePk.getClosureId(), ClosureId.class),
				new ClosureDate(this.krcdtMonAnyItemValuePk.getClosureDay(),
					(this.krcdtMonAnyItemValuePk.getIsLastDay() == 1)),
				0, (this.timeValue8 == null ? Optional.empty() : Optional.of(new AnyTimeMonth(this.timeValue8))),
			(this.countValue8 == null ? Optional.empty() : Optional.of(new AnyTimesMonth(this.countValue8))),
			(this.moneyValue8 == null ? Optional.empty() : Optional.of(new AnyAmountMonth(this.moneyValue8))));
	}

	public AnyItemOfMonthly toDomainAnyItemOfMonthly9() {
		// get value anyItemId = 0 temporary because table have not anyitemId
		return AnyItemOfMonthly.of(this.krcdtMonAnyItemValuePk.getEmployeeId(),
				new YearMonth(this.krcdtMonAnyItemValuePk.getYearMonth()),
				EnumAdaptor.valueOf(this.krcdtMonAnyItemValuePk.getClosureId(), ClosureId.class),
				new ClosureDate(this.krcdtMonAnyItemValuePk.getClosureDay(),
					(this.krcdtMonAnyItemValuePk.getIsLastDay() == 1)),
				0, (this.timeValue9 == null ? Optional.empty() : Optional.of(new AnyTimeMonth(this.timeValue9))),
			(this.countValue9 == null ? Optional.empty() : Optional.of(new AnyTimesMonth(this.countValue9))),
			(this.moneyValue9 == null ? Optional.empty() : Optional.of(new AnyAmountMonth(this.moneyValue9))));
	}

	public AnyItemOfMonthly toDomainAnyItemOfMonthly10() {
		// get value anyItemId = 0 temporary because table have not anyitemId
		return AnyItemOfMonthly.of(this.krcdtMonAnyItemValuePk.getEmployeeId(),
				new YearMonth(this.krcdtMonAnyItemValuePk.getYearMonth()),
				EnumAdaptor.valueOf(this.krcdtMonAnyItemValuePk.getClosureId(), ClosureId.class),
				new ClosureDate(this.krcdtMonAnyItemValuePk.getClosureDay(),
					(this.krcdtMonAnyItemValuePk.getIsLastDay() == 1)),
				0, (this.timeValue10 == null ? Optional.empty() : Optional.of(new AnyTimeMonth(this.timeValue10))),
			(this.countValue10 == null ? Optional.empty() : Optional.of(new AnyTimesMonth(this.countValue10))),
			(this.moneyValue10 == null ? Optional.empty() : Optional.of(new AnyAmountMonth(this.moneyValue10))));
	}

	public AnyItemOfMonthly toDomainAnyItemOfMonthly11() {
		// get value anyItemId = 0 temporary because table have not anyitemId
		return AnyItemOfMonthly.of(this.krcdtMonAnyItemValuePk.getEmployeeId(),
				new YearMonth(this.krcdtMonAnyItemValuePk.getYearMonth()),
				EnumAdaptor.valueOf(this.krcdtMonAnyItemValuePk.getClosureId(), ClosureId.class),
				new ClosureDate(this.krcdtMonAnyItemValuePk.getClosureDay(),
					(this.krcdtMonAnyItemValuePk.getIsLastDay() == 1)),
				0, (this.timeValue11 == null ? Optional.empty() : Optional.of(new AnyTimeMonth(this.timeValue11))),
			(this.countValue11 == null ? Optional.empty() : Optional.of(new AnyTimesMonth(this.countValue11))),
			(this.moneyValue11 == null ? Optional.empty() : Optional.of(new AnyAmountMonth(this.moneyValue11))));
	}

	public AnyItemOfMonthly toDomainAnyItemOfMonthly12() {
		// get value anyItemId = 0 temporary because table have not anyitemId
		return AnyItemOfMonthly.of(this.krcdtMonAnyItemValuePk.getEmployeeId(),
				new YearMonth(this.krcdtMonAnyItemValuePk.getYearMonth()),
				EnumAdaptor.valueOf(this.krcdtMonAnyItemValuePk.getClosureId(), ClosureId.class),
				new ClosureDate(this.krcdtMonAnyItemValuePk.getClosureDay(),
					(this.krcdtMonAnyItemValuePk.getIsLastDay() == 1)),
				0, (this.timeValue12 == null ? Optional.empty() : Optional.of(new AnyTimeMonth(this.timeValue12))),
			(this.countValue12 == null ? Optional.empty() : Optional.of(new AnyTimesMonth(this.countValue12))),
			(this.moneyValue12 == null ? Optional.empty() : Optional.of(new AnyAmountMonth(this.moneyValue12))));
	}

	public AnyItemOfMonthly toDomainAnyItemOfMonthly13() {
		// get value anyItemId = 0 temporary because table have not anyitemId
		return AnyItemOfMonthly.of(this.krcdtMonAnyItemValuePk.getEmployeeId(),
				new YearMonth(this.krcdtMonAnyItemValuePk.getYearMonth()),
				EnumAdaptor.valueOf(this.krcdtMonAnyItemValuePk.getClosureId(), ClosureId.class),
				new ClosureDate(this.krcdtMonAnyItemValuePk.getClosureDay(),
					(this.krcdtMonAnyItemValuePk.getIsLastDay() == 1)),
				0, (this.timeValue13 == null ? Optional.empty() : Optional.of(new AnyTimeMonth(this.timeValue13))),
			(this.countValue13 == null ? Optional.empty() : Optional.of(new AnyTimesMonth(this.countValue13))),
			(this.moneyValue13 == null ? Optional.empty() : Optional.of(new AnyAmountMonth(this.moneyValue13))));
	}

	public AnyItemOfMonthly toDomainAnyItemOfMonthly14() {
		// get value anyItemId = 0 temporary because table have not anyitemId
		return AnyItemOfMonthly.of(this.krcdtMonAnyItemValuePk.getEmployeeId(),
				new YearMonth(this.krcdtMonAnyItemValuePk.getYearMonth()),
				EnumAdaptor.valueOf(this.krcdtMonAnyItemValuePk.getClosureId(), ClosureId.class),
				new ClosureDate(this.krcdtMonAnyItemValuePk.getClosureDay(),
					(this.krcdtMonAnyItemValuePk.getIsLastDay() == 1)),
				0, (this.timeValue14 == null ? Optional.empty() : Optional.of(new AnyTimeMonth(this.timeValue14))),
			(this.countValue14 == null ? Optional.empty() : Optional.of(new AnyTimesMonth(this.countValue14))),
			(this.moneyValue14 == null ? Optional.empty() : Optional.of(new AnyAmountMonth(this.moneyValue14))));
	}

	public AnyItemOfMonthly toDomainAnyItemOfMonthly15() {
		// get value anyItemId = 0 temporary because table have not anyitemId
		return AnyItemOfMonthly.of(this.krcdtMonAnyItemValuePk.getEmployeeId(),
				new YearMonth(this.krcdtMonAnyItemValuePk.getYearMonth()),
				EnumAdaptor.valueOf(this.krcdtMonAnyItemValuePk.getClosureId(), ClosureId.class),
				new ClosureDate(this.krcdtMonAnyItemValuePk.getClosureDay(),
					(this.krcdtMonAnyItemValuePk.getIsLastDay() == 1)),
				0, (this.timeValue15 == null ? Optional.empty() : Optional.of(new AnyTimeMonth(this.timeValue15))),
			(this.countValue15 == null ? Optional.empty() : Optional.of(new AnyTimesMonth(this.countValue15))),
			(this.moneyValue15 == null ? Optional.empty() : Optional.of(new AnyAmountMonth(this.moneyValue15))));
	}

	public AnyItemOfMonthly toDomainAnyItemOfMonthly16() {
		// get value anyItemId = 0 temporary because table have not anyitemId
		return AnyItemOfMonthly.of(this.krcdtMonAnyItemValuePk.getEmployeeId(),
				new YearMonth(this.krcdtMonAnyItemValuePk.getYearMonth()),
				EnumAdaptor.valueOf(this.krcdtMonAnyItemValuePk.getClosureId(), ClosureId.class),
				new ClosureDate(this.krcdtMonAnyItemValuePk.getClosureDay(),
					(this.krcdtMonAnyItemValuePk.getIsLastDay() == 1)),
				0, (this.timeValue16 == null ? Optional.empty() : Optional.of(new AnyTimeMonth(this.timeValue16))),
			(this.countValue16 == null ? Optional.empty() : Optional.of(new AnyTimesMonth(this.countValue16))),
			(this.moneyValue16 == null ? Optional.empty() : Optional.of(new AnyAmountMonth(this.moneyValue16))));
	}

	public AnyItemOfMonthly toDomainAnyItemOfMonthly17() {
		// get value anyItemId = 0 temporary because table have not anyitemId
		return AnyItemOfMonthly.of(this.krcdtMonAnyItemValuePk.getEmployeeId(),
				new YearMonth(this.krcdtMonAnyItemValuePk.getYearMonth()),
				EnumAdaptor.valueOf(this.krcdtMonAnyItemValuePk.getClosureId(), ClosureId.class),
				new ClosureDate(this.krcdtMonAnyItemValuePk.getClosureDay(),
					(this.krcdtMonAnyItemValuePk.getIsLastDay() == 1)),
				0, (this.timeValue17 == null ? Optional.empty() : Optional.of(new AnyTimeMonth(this.timeValue17))),
			(this.countValue17 == null ? Optional.empty() : Optional.of(new AnyTimesMonth(this.countValue17))),
			(this.moneyValue17 == null ? Optional.empty() : Optional.of(new AnyAmountMonth(this.moneyValue17))));
	}

	public AnyItemOfMonthly toDomainAnyItemOfMonthly18() {
		// get value anyItemId = 0 temporary because table have not anyitemId
		return AnyItemOfMonthly.of(this.krcdtMonAnyItemValuePk.getEmployeeId(),
				new YearMonth(this.krcdtMonAnyItemValuePk.getYearMonth()),
				EnumAdaptor.valueOf(this.krcdtMonAnyItemValuePk.getClosureId(), ClosureId.class),
				new ClosureDate(this.krcdtMonAnyItemValuePk.getClosureDay(),
					(this.krcdtMonAnyItemValuePk.getIsLastDay() == 1)),
				0, (this.timeValue18 == null ? Optional.empty() : Optional.of(new AnyTimeMonth(this.timeValue18))),
			(this.countValue18 == null ? Optional.empty() : Optional.of(new AnyTimesMonth(this.countValue18))),
			(this.moneyValue18 == null ? Optional.empty() : Optional.of(new AnyAmountMonth(this.moneyValue18))));
	}

	public AnyItemOfMonthly toDomainAnyItemOfMonthly19() {
		// get value anyItemId = 0 temporary because table have not anyitemId
		return AnyItemOfMonthly.of(this.krcdtMonAnyItemValuePk.getEmployeeId(),
				new YearMonth(this.krcdtMonAnyItemValuePk.getYearMonth()),
				EnumAdaptor.valueOf(this.krcdtMonAnyItemValuePk.getClosureId(), ClosureId.class),
				new ClosureDate(this.krcdtMonAnyItemValuePk.getClosureDay(),
					(this.krcdtMonAnyItemValuePk.getIsLastDay() == 1)),
				0, (this.timeValue19 == null ? Optional.empty() : Optional.of(new AnyTimeMonth(this.timeValue19))),
			(this.countValue19 == null ? Optional.empty() : Optional.of(new AnyTimesMonth(this.countValue19))),
			(this.moneyValue19 == null ? Optional.empty() : Optional.of(new AnyAmountMonth(this.moneyValue19))));
	}

	public AnyItemOfMonthly toDomainAnyItemOfMonthly20() {
		// get value anyItemId = 0 temporary because table have not anyitemId
		return AnyItemOfMonthly.of(this.krcdtMonAnyItemValuePk.getEmployeeId(),
				new YearMonth(this.krcdtMonAnyItemValuePk.getYearMonth()),
				EnumAdaptor.valueOf(this.krcdtMonAnyItemValuePk.getClosureId(), ClosureId.class),
				new ClosureDate(this.krcdtMonAnyItemValuePk.getClosureDay(),
					(this.krcdtMonAnyItemValuePk.getIsLastDay() == 1)),
				0, (this.timeValue20 == null ? Optional.empty() : Optional.of(new AnyTimeMonth(this.timeValue20))),
			(this.countValue20 == null ? Optional.empty() : Optional.of(new AnyTimesMonth(this.countValue20))),
			(this.moneyValue20 == null ? Optional.empty() : Optional.of(new AnyAmountMonth(this.moneyValue20))));
	}

	public AnyItemOfMonthly toDomainAnyItemOfMonthly21() {
		// get value anyItemId = 0 temporary because table have not anyitemId
		return AnyItemOfMonthly.of(this.krcdtMonAnyItemValuePk.getEmployeeId(),
				new YearMonth(this.krcdtMonAnyItemValuePk.getYearMonth()),
				EnumAdaptor.valueOf(this.krcdtMonAnyItemValuePk.getClosureId(), ClosureId.class),
				new ClosureDate(this.krcdtMonAnyItemValuePk.getClosureDay(),
					(this.krcdtMonAnyItemValuePk.getIsLastDay() == 1)),
				0, (this.timeValue21 == null ? Optional.empty() : Optional.of(new AnyTimeMonth(this.timeValue21))),
			(this.countValue21 == null ? Optional.empty() : Optional.of(new AnyTimesMonth(this.countValue21))),
			(this.moneyValue21 == null ? Optional.empty() : Optional.of(new AnyAmountMonth(this.moneyValue21))));
	}

	public AnyItemOfMonthly toDomainAnyItemOfMonthly22() {
		// get value anyItemId = 0 temporary because table have not anyitemId
		return AnyItemOfMonthly.of(this.krcdtMonAnyItemValuePk.getEmployeeId(),
				new YearMonth(this.krcdtMonAnyItemValuePk.getYearMonth()),
				EnumAdaptor.valueOf(this.krcdtMonAnyItemValuePk.getClosureId(), ClosureId.class),
				new ClosureDate(this.krcdtMonAnyItemValuePk.getClosureDay(),
					(this.krcdtMonAnyItemValuePk.getIsLastDay() == 1)),
				0, (this.timeValue22 == null ? Optional.empty() : Optional.of(new AnyTimeMonth(this.timeValue22))),
			(this.countValue22 == null ? Optional.empty() : Optional.of(new AnyTimesMonth(this.countValue22))),
			(this.moneyValue22 == null ? Optional.empty() : Optional.of(new AnyAmountMonth(this.moneyValue22))));
	}

	public AnyItemOfMonthly toDomainAnyItemOfMonthly23() {
		// get value anyItemId = 0 temporary because table have not anyitemId
		return AnyItemOfMonthly.of(this.krcdtMonAnyItemValuePk.getEmployeeId(),
				new YearMonth(this.krcdtMonAnyItemValuePk.getYearMonth()),
				EnumAdaptor.valueOf(this.krcdtMonAnyItemValuePk.getClosureId(), ClosureId.class),
				new ClosureDate(this.krcdtMonAnyItemValuePk.getClosureDay(),
					(this.krcdtMonAnyItemValuePk.getIsLastDay() == 1)),
				0, (this.timeValue23 == null ? Optional.empty() : Optional.of(new AnyTimeMonth(this.timeValue23))),
			(this.countValue23 == null ? Optional.empty() : Optional.of(new AnyTimesMonth(this.countValue23))),
			(this.moneyValue23 == null ? Optional.empty() : Optional.of(new AnyAmountMonth(this.moneyValue23))));
	}

	public AnyItemOfMonthly toDomainAnyItemOfMonthly24() {
		// get value anyItemId = 0 temporary because table have not anyitemId
		return AnyItemOfMonthly.of(this.krcdtMonAnyItemValuePk.getEmployeeId(),
				new YearMonth(this.krcdtMonAnyItemValuePk.getYearMonth()),
				EnumAdaptor.valueOf(this.krcdtMonAnyItemValuePk.getClosureId(), ClosureId.class),
				new ClosureDate(this.krcdtMonAnyItemValuePk.getClosureDay(),
					(this.krcdtMonAnyItemValuePk.getIsLastDay() == 1)),
				0, (this.timeValue24 == null ? Optional.empty() : Optional.of(new AnyTimeMonth(this.timeValue24))),
			(this.countValue24 == null ? Optional.empty() : Optional.of(new AnyTimesMonth(this.countValue24))),
			(this.moneyValue24 == null ? Optional.empty() : Optional.of(new AnyAmountMonth(this.moneyValue24))));
	}

	public AnyItemOfMonthly toDomainAnyItemOfMonthly25() {
		// get value anyItemId = 0 temporary because table have not anyitemId
		return AnyItemOfMonthly.of(this.krcdtMonAnyItemValuePk.getEmployeeId(),
				new YearMonth(this.krcdtMonAnyItemValuePk.getYearMonth()),
				EnumAdaptor.valueOf(this.krcdtMonAnyItemValuePk.getClosureId(), ClosureId.class),
				new ClosureDate(this.krcdtMonAnyItemValuePk.getClosureDay(),
					(this.krcdtMonAnyItemValuePk.getIsLastDay() == 1)),
				0, (this.timeValue25 == null ? Optional.empty() : Optional.of(new AnyTimeMonth(this.timeValue25))),
			(this.countValue25 == null ? Optional.empty() : Optional.of(new AnyTimesMonth(this.countValue25))),
			(this.moneyValue25 == null ? Optional.empty() : Optional.of(new AnyAmountMonth(this.moneyValue25))));
	}

	public AnyItemOfMonthly toDomainAnyItemOfMonthly26() {
		// get value anyItemId = 0 temporary because table have not anyitemId
		return AnyItemOfMonthly.of(this.krcdtMonAnyItemValuePk.getEmployeeId(),
				new YearMonth(this.krcdtMonAnyItemValuePk.getYearMonth()),
				EnumAdaptor.valueOf(this.krcdtMonAnyItemValuePk.getClosureId(), ClosureId.class),
				new ClosureDate(this.krcdtMonAnyItemValuePk.getClosureDay(),
					(this.krcdtMonAnyItemValuePk.getIsLastDay() == 1)),
				0, (this.timeValue26 == null ? Optional.empty() : Optional.of(new AnyTimeMonth(this.timeValue26))),
			(this.countValue26 == null ? Optional.empty() : Optional.of(new AnyTimesMonth(this.countValue26))),
			(this.moneyValue26 == null ? Optional.empty() : Optional.of(new AnyAmountMonth(this.moneyValue26))));
	}

	public AnyItemOfMonthly toDomainAnyItemOfMonthly27() {
		// get value anyItemId = 0 temporary because table have not anyitemId
		return AnyItemOfMonthly.of(this.krcdtMonAnyItemValuePk.getEmployeeId(),
				new YearMonth(this.krcdtMonAnyItemValuePk.getYearMonth()),
				EnumAdaptor.valueOf(this.krcdtMonAnyItemValuePk.getClosureId(), ClosureId.class),
				new ClosureDate(this.krcdtMonAnyItemValuePk.getClosureDay(),
					(this.krcdtMonAnyItemValuePk.getIsLastDay() == 1)),
				0, (this.timeValue27 == null ? Optional.empty() : Optional.of(new AnyTimeMonth(this.timeValue27))),
			(this.countValue27 == null ? Optional.empty() : Optional.of(new AnyTimesMonth(this.countValue27))),
			(this.moneyValue27 == null ? Optional.empty() : Optional.of(new AnyAmountMonth(this.moneyValue27))));
	}

	public AnyItemOfMonthly toDomainAnyItemOfMonthly28() {
		// get value anyItemId = 0 temporary because table have not anyitemId
		return AnyItemOfMonthly.of(this.krcdtMonAnyItemValuePk.getEmployeeId(),
				new YearMonth(this.krcdtMonAnyItemValuePk.getYearMonth()),
				EnumAdaptor.valueOf(this.krcdtMonAnyItemValuePk.getClosureId(), ClosureId.class),
				new ClosureDate(this.krcdtMonAnyItemValuePk.getClosureDay(),
					(this.krcdtMonAnyItemValuePk.getIsLastDay() == 1)),
				0, (this.timeValue28 == null ? Optional.empty() : Optional.of(new AnyTimeMonth(this.timeValue28))),
			(this.countValue28 == null ? Optional.empty() : Optional.of(new AnyTimesMonth(this.countValue28))),
			(this.moneyValue28 == null ? Optional.empty() : Optional.of(new AnyAmountMonth(this.moneyValue28))));
	}

	public AnyItemOfMonthly toDomainAnyItemOfMonthly29() {
		// get value anyItemId = 0 temporary because table have not anyitemId
		return AnyItemOfMonthly.of(this.krcdtMonAnyItemValuePk.getEmployeeId(),
				new YearMonth(this.krcdtMonAnyItemValuePk.getYearMonth()),
				EnumAdaptor.valueOf(this.krcdtMonAnyItemValuePk.getClosureId(), ClosureId.class),
				new ClosureDate(this.krcdtMonAnyItemValuePk.getClosureDay(),
					(this.krcdtMonAnyItemValuePk.getIsLastDay() == 1)),
				0, (this.timeValue29 == null ? Optional.empty() : Optional.of(new AnyTimeMonth(this.timeValue29))),
			(this.countValue29 == null ? Optional.empty() : Optional.of(new AnyTimesMonth(this.countValue29))),
			(this.moneyValue29 == null ? Optional.empty() : Optional.of(new AnyAmountMonth(this.moneyValue29))));
	}

	public AnyItemOfMonthly toDomainAnyItemOfMonthly30() {
		// get value anyItemId = 0 temporary because table have not anyitemId
		return AnyItemOfMonthly.of(this.krcdtMonAnyItemValuePk.getEmployeeId(),
				new YearMonth(this.krcdtMonAnyItemValuePk.getYearMonth()),
				EnumAdaptor.valueOf(this.krcdtMonAnyItemValuePk.getClosureId(), ClosureId.class),
				new ClosureDate(this.krcdtMonAnyItemValuePk.getClosureDay(),
					(this.krcdtMonAnyItemValuePk.getIsLastDay() == 1)),
				0, (this.timeValue30 == null ? Optional.empty() : Optional.of(new AnyTimeMonth(this.timeValue30))),
			(this.countValue30 == null ? Optional.empty() : Optional.of(new AnyTimesMonth(this.countValue30))),
			(this.moneyValue30 == null ? Optional.empty() : Optional.of(new AnyAmountMonth(this.moneyValue30))));
	}

	public AnyItemOfMonthly toDomainAnyItemOfMonthly31() {
		// get value anyItemId = 0 temporary because table have not anyitemId
		return AnyItemOfMonthly.of(this.krcdtMonAnyItemValuePk.getEmployeeId(),
				new YearMonth(this.krcdtMonAnyItemValuePk.getYearMonth()),
				EnumAdaptor.valueOf(this.krcdtMonAnyItemValuePk.getClosureId(), ClosureId.class),
				new ClosureDate(this.krcdtMonAnyItemValuePk.getClosureDay(),
					(this.krcdtMonAnyItemValuePk.getIsLastDay() == 1)),
				0, (this.timeValue31 == null ? Optional.empty() : Optional.of(new AnyTimeMonth(this.timeValue31))),
			(this.countValue31 == null ? Optional.empty() : Optional.of(new AnyTimesMonth(this.countValue31))),
			(this.moneyValue31 == null ? Optional.empty() : Optional.of(new AnyAmountMonth(this.moneyValue31))));
	}

	public AnyItemOfMonthly toDomainAnyItemOfMonthly32() {
		// get value anyItemId = 0 temporary because table have not anyitemId
		return AnyItemOfMonthly.of(this.krcdtMonAnyItemValuePk.getEmployeeId(),
				new YearMonth(this.krcdtMonAnyItemValuePk.getYearMonth()),
				EnumAdaptor.valueOf(this.krcdtMonAnyItemValuePk.getClosureId(), ClosureId.class),
				new ClosureDate(this.krcdtMonAnyItemValuePk.getClosureDay(),
					(this.krcdtMonAnyItemValuePk.getIsLastDay() == 1)),
				0, (this.timeValue32 == null ? Optional.empty() : Optional.of(new AnyTimeMonth(this.timeValue32))),
			(this.countValue32 == null ? Optional.empty() : Optional.of(new AnyTimesMonth(this.countValue32))),
			(this.moneyValue32 == null ? Optional.empty() : Optional.of(new AnyAmountMonth(this.moneyValue32))));
	}

	public AnyItemOfMonthly toDomainAnyItemOfMonthly33() {
		// get value anyItemId = 0 temporary because table have not anyitemId
		return AnyItemOfMonthly.of(this.krcdtMonAnyItemValuePk.getEmployeeId(),
				new YearMonth(this.krcdtMonAnyItemValuePk.getYearMonth()),
				EnumAdaptor.valueOf(this.krcdtMonAnyItemValuePk.getClosureId(), ClosureId.class),
				new ClosureDate(this.krcdtMonAnyItemValuePk.getClosureDay(),
					(this.krcdtMonAnyItemValuePk.getIsLastDay() == 1)),
				0, (this.timeValue33 == null ? Optional.empty() : Optional.of(new AnyTimeMonth(this.timeValue33))),
			(this.countValue33 == null ? Optional.empty() : Optional.of(new AnyTimesMonth(this.countValue33))),
			(this.moneyValue33 == null ? Optional.empty() : Optional.of(new AnyAmountMonth(this.moneyValue33))));
	}

	public AnyItemOfMonthly toDomainAnyItemOfMonthly34() {
		// get value anyItemId = 0 temporary because table have not anyitemId
		return AnyItemOfMonthly.of(this.krcdtMonAnyItemValuePk.getEmployeeId(),
				new YearMonth(this.krcdtMonAnyItemValuePk.getYearMonth()),
				EnumAdaptor.valueOf(this.krcdtMonAnyItemValuePk.getClosureId(), ClosureId.class),
				new ClosureDate(this.krcdtMonAnyItemValuePk.getClosureDay(),
					(this.krcdtMonAnyItemValuePk.getIsLastDay() == 1)),
				0, (this.timeValue34 == null ? Optional.empty() : Optional.of(new AnyTimeMonth(this.timeValue34))),
			(this.countValue34 == null ? Optional.empty() : Optional.of(new AnyTimesMonth(this.countValue34))),
			(this.moneyValue34 == null ? Optional.empty() : Optional.of(new AnyAmountMonth(this.moneyValue34))));
	}

	public AnyItemOfMonthly toDomainAnyItemOfMonthly35() {
		// get value anyItemId = 0 temporary because table have not anyitemId
		return AnyItemOfMonthly.of(this.krcdtMonAnyItemValuePk.getEmployeeId(),
				new YearMonth(this.krcdtMonAnyItemValuePk.getYearMonth()),
				EnumAdaptor.valueOf(this.krcdtMonAnyItemValuePk.getClosureId(), ClosureId.class),
				new ClosureDate(this.krcdtMonAnyItemValuePk.getClosureDay(),
					(this.krcdtMonAnyItemValuePk.getIsLastDay() == 1)),
				0, (this.timeValue35 == null ? Optional.empty() : Optional.of(new AnyTimeMonth(this.timeValue35))),
			(this.countValue35 == null ? Optional.empty() : Optional.of(new AnyTimesMonth(this.countValue35))),
			(this.moneyValue35 == null ? Optional.empty() : Optional.of(new AnyAmountMonth(this.moneyValue35))));
	}

	public AnyItemOfMonthly toDomainAnyItemOfMonthly36() {
		// get value anyItemId = 0 temporary because table have not anyitemId
		return AnyItemOfMonthly.of(this.krcdtMonAnyItemValuePk.getEmployeeId(),
				new YearMonth(this.krcdtMonAnyItemValuePk.getYearMonth()),
				EnumAdaptor.valueOf(this.krcdtMonAnyItemValuePk.getClosureId(), ClosureId.class),
				new ClosureDate(this.krcdtMonAnyItemValuePk.getClosureDay(),
					(this.krcdtMonAnyItemValuePk.getIsLastDay() == 1)),
				0, (this.timeValue36 == null ? Optional.empty() : Optional.of(new AnyTimeMonth(this.timeValue36))),
			(this.countValue36 == null ? Optional.empty() : Optional.of(new AnyTimesMonth(this.countValue36))),
			(this.moneyValue36 == null ? Optional.empty() : Optional.of(new AnyAmountMonth(this.moneyValue36))));
	}

	public AnyItemOfMonthly toDomainAnyItemOfMonthly37() {
		// get value anyItemId = 0 temporary because table have not anyitemId
		return AnyItemOfMonthly.of(this.krcdtMonAnyItemValuePk.getEmployeeId(),
				new YearMonth(this.krcdtMonAnyItemValuePk.getYearMonth()),
				EnumAdaptor.valueOf(this.krcdtMonAnyItemValuePk.getClosureId(), ClosureId.class),
				new ClosureDate(this.krcdtMonAnyItemValuePk.getClosureDay(),
					(this.krcdtMonAnyItemValuePk.getIsLastDay() == 1)),
				0, (this.timeValue37 == null ? Optional.empty() : Optional.of(new AnyTimeMonth(this.timeValue37))),
			(this.countValue37 == null ? Optional.empty() : Optional.of(new AnyTimesMonth(this.countValue37))),
			(this.moneyValue37 == null ? Optional.empty() : Optional.of(new AnyAmountMonth(this.moneyValue37))));
	}

	public AnyItemOfMonthly toDomainAnyItemOfMonthly38() {
		// get value anyItemId = 0 temporary because table have not anyitemId
		return AnyItemOfMonthly.of(this.krcdtMonAnyItemValuePk.getEmployeeId(),
				new YearMonth(this.krcdtMonAnyItemValuePk.getYearMonth()),
				EnumAdaptor.valueOf(this.krcdtMonAnyItemValuePk.getClosureId(), ClosureId.class),
				new ClosureDate(this.krcdtMonAnyItemValuePk.getClosureDay(),
					(this.krcdtMonAnyItemValuePk.getIsLastDay() == 1)),
				0, (this.timeValue38 == null ? Optional.empty() : Optional.of(new AnyTimeMonth(this.timeValue38))),
			(this.countValue38 == null ? Optional.empty() : Optional.of(new AnyTimesMonth(this.countValue38))),
			(this.moneyValue38 == null ? Optional.empty() : Optional.of(new AnyAmountMonth(this.moneyValue38))));
	}

	public AnyItemOfMonthly toDomainAnyItemOfMonthly39() {
		// get value anyItemId = 0 temporary because table have not anyitemId
		return AnyItemOfMonthly.of(this.krcdtMonAnyItemValuePk.getEmployeeId(),
				new YearMonth(this.krcdtMonAnyItemValuePk.getYearMonth()),
				EnumAdaptor.valueOf(this.krcdtMonAnyItemValuePk.getClosureId(), ClosureId.class),
				new ClosureDate(this.krcdtMonAnyItemValuePk.getClosureDay(),
					(this.krcdtMonAnyItemValuePk.getIsLastDay() == 1)),
				0, (this.timeValue39 == null ? Optional.empty() : Optional.of(new AnyTimeMonth(this.timeValue39))),
			(this.countValue39 == null ? Optional.empty() : Optional.of(new AnyTimesMonth(this.countValue39))),
			(this.moneyValue39 == null ? Optional.empty() : Optional.of(new AnyAmountMonth(this.moneyValue39))));
	}

	public AnyItemOfMonthly toDomainAnyItemOfMonthly40() {
		// get value anyItemId = 0 temporary because table have not anyitemId
		return AnyItemOfMonthly.of(this.krcdtMonAnyItemValuePk.getEmployeeId(),
				new YearMonth(this.krcdtMonAnyItemValuePk.getYearMonth()),
				EnumAdaptor.valueOf(this.krcdtMonAnyItemValuePk.getClosureId(), ClosureId.class),
				new ClosureDate(this.krcdtMonAnyItemValuePk.getClosureDay(),
					(this.krcdtMonAnyItemValuePk.getIsLastDay() == 1)),
				0, (this.timeValue40 == null ? Optional.empty() : Optional.of(new AnyTimeMonth(this.timeValue40))),
			(this.countValue40 == null ? Optional.empty() : Optional.of(new AnyTimesMonth(this.countValue40))),
			(this.moneyValue40 == null ? Optional.empty() : Optional.of(new AnyAmountMonth(this.moneyValue40))));
	}

	public AnyItemOfMonthly toDomainAnyItemOfMonthly41() {
		// get value anyItemId = 0 temporary because table have not anyitemId
		return AnyItemOfMonthly.of(this.krcdtMonAnyItemValuePk.getEmployeeId(),
				new YearMonth(this.krcdtMonAnyItemValuePk.getYearMonth()),
				EnumAdaptor.valueOf(this.krcdtMonAnyItemValuePk.getClosureId(), ClosureId.class),
				new ClosureDate(this.krcdtMonAnyItemValuePk.getClosureDay(),
					(this.krcdtMonAnyItemValuePk.getIsLastDay() == 1)),
				0, (this.timeValue41 == null ? Optional.empty() : Optional.of(new AnyTimeMonth(this.timeValue41))),
			(this.countValue41 == null ? Optional.empty() : Optional.of(new AnyTimesMonth(this.countValue41))),
			(this.moneyValue41 == null ? Optional.empty() : Optional.of(new AnyAmountMonth(this.moneyValue41))));
	}

	public AnyItemOfMonthly toDomainAnyItemOfMonthly42() {
		// get value anyItemId = 0 temporary because table have not anyitemId
		return AnyItemOfMonthly.of(this.krcdtMonAnyItemValuePk.getEmployeeId(),
				new YearMonth(this.krcdtMonAnyItemValuePk.getYearMonth()),
				EnumAdaptor.valueOf(this.krcdtMonAnyItemValuePk.getClosureId(), ClosureId.class),
				new ClosureDate(this.krcdtMonAnyItemValuePk.getClosureDay(),
					(this.krcdtMonAnyItemValuePk.getIsLastDay() == 1)),
				0, (this.timeValue42 == null ? Optional.empty() : Optional.of(new AnyTimeMonth(this.timeValue42))),
			(this.countValue42 == null ? Optional.empty() : Optional.of(new AnyTimesMonth(this.countValue42))),
			(this.moneyValue42 == null ? Optional.empty() : Optional.of(new AnyAmountMonth(this.moneyValue42))));
	}

	public AnyItemOfMonthly toDomainAnyItemOfMonthly43() {
		// get value anyItemId = 0 temporary because table have not anyitemId
		return AnyItemOfMonthly.of(this.krcdtMonAnyItemValuePk.getEmployeeId(),
				new YearMonth(this.krcdtMonAnyItemValuePk.getYearMonth()),
				EnumAdaptor.valueOf(this.krcdtMonAnyItemValuePk.getClosureId(), ClosureId.class),
				new ClosureDate(this.krcdtMonAnyItemValuePk.getClosureDay(),
					(this.krcdtMonAnyItemValuePk.getIsLastDay() == 1)),
				0, (this.timeValue43 == null ? Optional.empty() : Optional.of(new AnyTimeMonth(this.timeValue43))),
			(this.countValue43 == null ? Optional.empty() : Optional.of(new AnyTimesMonth(this.countValue43))),
			(this.moneyValue43 == null ? Optional.empty() : Optional.of(new AnyAmountMonth(this.moneyValue43))));
	}

	public AnyItemOfMonthly toDomainAnyItemOfMonthly44() {
		// get value anyItemId = 0 temporary because table have not anyitemId
		return AnyItemOfMonthly.of(this.krcdtMonAnyItemValuePk.getEmployeeId(),
				new YearMonth(this.krcdtMonAnyItemValuePk.getYearMonth()),
				EnumAdaptor.valueOf(this.krcdtMonAnyItemValuePk.getClosureId(), ClosureId.class),
				new ClosureDate(this.krcdtMonAnyItemValuePk.getClosureDay(),
					(this.krcdtMonAnyItemValuePk.getIsLastDay() == 1)),
				0, (this.timeValue44 == null ? Optional.empty() : Optional.of(new AnyTimeMonth(this.timeValue44))),
			(this.countValue44 == null ? Optional.empty() : Optional.of(new AnyTimesMonth(this.countValue44))),
			(this.moneyValue44 == null ? Optional.empty() : Optional.of(new AnyAmountMonth(this.moneyValue44))));
	}

	public AnyItemOfMonthly toDomainAnyItemOfMonthly45() {
		// get value anyItemId = 0 temporary because table have not anyitemId
		return AnyItemOfMonthly.of(this.krcdtMonAnyItemValuePk.getEmployeeId(),
				new YearMonth(this.krcdtMonAnyItemValuePk.getYearMonth()),
				EnumAdaptor.valueOf(this.krcdtMonAnyItemValuePk.getClosureId(), ClosureId.class),
				new ClosureDate(this.krcdtMonAnyItemValuePk.getClosureDay(),
					(this.krcdtMonAnyItemValuePk.getIsLastDay() == 1)),
				0, (this.timeValue45 == null ? Optional.empty() : Optional.of(new AnyTimeMonth(this.timeValue45))),
			(this.countValue45 == null ? Optional.empty() : Optional.of(new AnyTimesMonth(this.countValue45))),
			(this.moneyValue45 == null ? Optional.empty() : Optional.of(new AnyAmountMonth(this.moneyValue45))));
	}

	public AnyItemOfMonthly toDomainAnyItemOfMonthly46() {
		// get value anyItemId = 0 temporary because table have not anyitemId
		return AnyItemOfMonthly.of(this.krcdtMonAnyItemValuePk.getEmployeeId(),
				new YearMonth(this.krcdtMonAnyItemValuePk.getYearMonth()),
				EnumAdaptor.valueOf(this.krcdtMonAnyItemValuePk.getClosureId(), ClosureId.class),
				new ClosureDate(this.krcdtMonAnyItemValuePk.getClosureDay(),
					(this.krcdtMonAnyItemValuePk.getIsLastDay() == 1)),
				0, (this.timeValue46 == null ? Optional.empty() : Optional.of(new AnyTimeMonth(this.timeValue46))),
			(this.countValue46 == null ? Optional.empty() : Optional.of(new AnyTimesMonth(this.countValue46))),
			(this.moneyValue46 == null ? Optional.empty() : Optional.of(new AnyAmountMonth(this.moneyValue46))));
	}

	public AnyItemOfMonthly toDomainAnyItemOfMonthly47() {
		// get value anyItemId = 0 temporary because table have not anyitemId
		return AnyItemOfMonthly.of(this.krcdtMonAnyItemValuePk.getEmployeeId(),
				new YearMonth(this.krcdtMonAnyItemValuePk.getYearMonth()),
				EnumAdaptor.valueOf(this.krcdtMonAnyItemValuePk.getClosureId(), ClosureId.class),
				new ClosureDate(this.krcdtMonAnyItemValuePk.getClosureDay(),
					(this.krcdtMonAnyItemValuePk.getIsLastDay() == 1)),
				0, (this.timeValue47 == null ? Optional.empty() : Optional.of(new AnyTimeMonth(this.timeValue47))),
			(this.countValue47 == null ? Optional.empty() : Optional.of(new AnyTimesMonth(this.countValue47))),
			(this.moneyValue47 == null ? Optional.empty() : Optional.of(new AnyAmountMonth(this.moneyValue47))));
	}

	public AnyItemOfMonthly toDomainAnyItemOfMonthly48() {
		// get value anyItemId = 0 temporary because table have not anyitemId
		return AnyItemOfMonthly.of(this.krcdtMonAnyItemValuePk.getEmployeeId(),
				new YearMonth(this.krcdtMonAnyItemValuePk.getYearMonth()),
				EnumAdaptor.valueOf(this.krcdtMonAnyItemValuePk.getClosureId(), ClosureId.class),
				new ClosureDate(this.krcdtMonAnyItemValuePk.getClosureDay(),
					(this.krcdtMonAnyItemValuePk.getIsLastDay() == 1)),
				0, (this.timeValue48 == null ? Optional.empty() : Optional.of(new AnyTimeMonth(this.timeValue48))),
			(this.countValue48 == null ? Optional.empty() : Optional.of(new AnyTimesMonth(this.countValue48))),
			(this.moneyValue48 == null ? Optional.empty() : Optional.of(new AnyAmountMonth(this.moneyValue48))));
	}

	public AnyItemOfMonthly toDomainAnyItemOfMonthly49() {
		// get value anyItemId = 0 temporary because table have not anyitemId
		return AnyItemOfMonthly.of(this.krcdtMonAnyItemValuePk.getEmployeeId(),
				new YearMonth(this.krcdtMonAnyItemValuePk.getYearMonth()),
				EnumAdaptor.valueOf(this.krcdtMonAnyItemValuePk.getClosureId(), ClosureId.class),
				new ClosureDate(this.krcdtMonAnyItemValuePk.getClosureDay(),
					(this.krcdtMonAnyItemValuePk.getIsLastDay() == 1)),
				0, (this.timeValue49 == null ? Optional.empty() : Optional.of(new AnyTimeMonth(this.timeValue49))),
			(this.countValue49 == null ? Optional.empty() : Optional.of(new AnyTimesMonth(this.countValue49))),
			(this.moneyValue49 == null ? Optional.empty() : Optional.of(new AnyAmountMonth(this.moneyValue49))));
	}

	public AnyItemOfMonthly toDomainAnyItemOfMonthly50() {
		// get value anyItemId = 0 temporary because table have not anyitemId
		return AnyItemOfMonthly.of(this.krcdtMonAnyItemValuePk.getEmployeeId(),
				new YearMonth(this.krcdtMonAnyItemValuePk.getYearMonth()),
				EnumAdaptor.valueOf(this.krcdtMonAnyItemValuePk.getClosureId(), ClosureId.class),
				new ClosureDate(this.krcdtMonAnyItemValuePk.getClosureDay(),
					(this.krcdtMonAnyItemValuePk.getIsLastDay() == 1)),
				0, (this.timeValue50 == null ? Optional.empty() : Optional.of(new AnyTimeMonth(this.timeValue50))),
			(this.countValue50 == null ? Optional.empty() : Optional.of(new AnyTimesMonth(this.countValue50))),
			(this.moneyValue50 == null ? Optional.empty() : Optional.of(new AnyAmountMonth(this.moneyValue50))));
	}

	public AnyItemOfMonthly toDomainAnyItemOfMonthly51() {
		// get value anyItemId = 0 temporary because table have not anyitemId
		return AnyItemOfMonthly.of(this.krcdtMonAnyItemValuePk.getEmployeeId(),
				new YearMonth(this.krcdtMonAnyItemValuePk.getYearMonth()),
				EnumAdaptor.valueOf(this.krcdtMonAnyItemValuePk.getClosureId(), ClosureId.class),
				new ClosureDate(this.krcdtMonAnyItemValuePk.getClosureDay(),
					(this.krcdtMonAnyItemValuePk.getIsLastDay() == 1)),
				0, (this.timeValue51 == null ? Optional.empty() : Optional.of(new AnyTimeMonth(this.timeValue51))),
			(this.countValue51 == null ? Optional.empty() : Optional.of(new AnyTimesMonth(this.countValue51))),
			(this.moneyValue51 == null ? Optional.empty() : Optional.of(new AnyAmountMonth(this.moneyValue51))));
	}

	public AnyItemOfMonthly toDomainAnyItemOfMonthly52() {
		// get value anyItemId = 0 temporary because table have not anyitemId
		return AnyItemOfMonthly.of(this.krcdtMonAnyItemValuePk.getEmployeeId(),
				new YearMonth(this.krcdtMonAnyItemValuePk.getYearMonth()),
				EnumAdaptor.valueOf(this.krcdtMonAnyItemValuePk.getClosureId(), ClosureId.class),
				new ClosureDate(this.krcdtMonAnyItemValuePk.getClosureDay(),
					(this.krcdtMonAnyItemValuePk.getIsLastDay() == 1)),
				0, (this.timeValue52 == null ? Optional.empty() : Optional.of(new AnyTimeMonth(this.timeValue52))),
			(this.countValue52 == null ? Optional.empty() : Optional.of(new AnyTimesMonth(this.countValue52))),
			(this.moneyValue52 == null ? Optional.empty() : Optional.of(new AnyAmountMonth(this.moneyValue52))));
	}

	public AnyItemOfMonthly toDomainAnyItemOfMonthly53() {
		// get value anyItemId = 0 temporary because table have not anyitemId
		return AnyItemOfMonthly.of(this.krcdtMonAnyItemValuePk.getEmployeeId(),
				new YearMonth(this.krcdtMonAnyItemValuePk.getYearMonth()),
				EnumAdaptor.valueOf(this.krcdtMonAnyItemValuePk.getClosureId(), ClosureId.class),
				new ClosureDate(this.krcdtMonAnyItemValuePk.getClosureDay(),
					(this.krcdtMonAnyItemValuePk.getIsLastDay() == 1)),
				0, (this.timeValue53 == null ? Optional.empty() : Optional.of(new AnyTimeMonth(this.timeValue53))),
			(this.countValue53 == null ? Optional.empty() : Optional.of(new AnyTimesMonth(this.countValue53))),
			(this.moneyValue53 == null ? Optional.empty() : Optional.of(new AnyAmountMonth(this.moneyValue53))));
	}

	public AnyItemOfMonthly toDomainAnyItemOfMonthly54() {
		// get value anyItemId = 0 temporary because table have not anyitemId
		return AnyItemOfMonthly.of(this.krcdtMonAnyItemValuePk.getEmployeeId(),
				new YearMonth(this.krcdtMonAnyItemValuePk.getYearMonth()),
				EnumAdaptor.valueOf(this.krcdtMonAnyItemValuePk.getClosureId(), ClosureId.class),
				new ClosureDate(this.krcdtMonAnyItemValuePk.getClosureDay(),
					(this.krcdtMonAnyItemValuePk.getIsLastDay() == 1)),
				0, (this.timeValue54 == null ? Optional.empty() : Optional.of(new AnyTimeMonth(this.timeValue54))),
			(this.countValue54 == null ? Optional.empty() : Optional.of(new AnyTimesMonth(this.countValue54))),
			(this.moneyValue54 == null ? Optional.empty() : Optional.of(new AnyAmountMonth(this.moneyValue54))));
	}

	public AnyItemOfMonthly toDomainAnyItemOfMonthly55() {
		// get value anyItemId = 0 temporary because table have not anyitemId
		return AnyItemOfMonthly.of(this.krcdtMonAnyItemValuePk.getEmployeeId(),
				new YearMonth(this.krcdtMonAnyItemValuePk.getYearMonth()),
				EnumAdaptor.valueOf(this.krcdtMonAnyItemValuePk.getClosureId(), ClosureId.class),
				new ClosureDate(this.krcdtMonAnyItemValuePk.getClosureDay(),
					(this.krcdtMonAnyItemValuePk.getIsLastDay() == 1)),
				0, (this.timeValue55 == null ? Optional.empty() : Optional.of(new AnyTimeMonth(this.timeValue55))),
			(this.countValue55 == null ? Optional.empty() : Optional.of(new AnyTimesMonth(this.countValue55))),
			(this.moneyValue55 == null ? Optional.empty() : Optional.of(new AnyAmountMonth(this.moneyValue55))));
	}

	public AnyItemOfMonthly toDomainAnyItemOfMonthly56() {
		// get value anyItemId = 0 temporary because table have not anyitemId
		return AnyItemOfMonthly.of(this.krcdtMonAnyItemValuePk.getEmployeeId(),
				new YearMonth(this.krcdtMonAnyItemValuePk.getYearMonth()),
				EnumAdaptor.valueOf(this.krcdtMonAnyItemValuePk.getClosureId(), ClosureId.class),
				new ClosureDate(this.krcdtMonAnyItemValuePk.getClosureDay(),
					(this.krcdtMonAnyItemValuePk.getIsLastDay() == 1)),
				0, (this.timeValue56 == null ? Optional.empty() : Optional.of(new AnyTimeMonth(this.timeValue56))),
			(this.countValue56 == null ? Optional.empty() : Optional.of(new AnyTimesMonth(this.countValue56))),
			(this.moneyValue56 == null ? Optional.empty() : Optional.of(new AnyAmountMonth(this.moneyValue56))));
	}

	public AnyItemOfMonthly toDomainAnyItemOfMonthly57() {
		// get value anyItemId = 0 temporary because table have not anyitemId
		return AnyItemOfMonthly.of(this.krcdtMonAnyItemValuePk.getEmployeeId(),
				new YearMonth(this.krcdtMonAnyItemValuePk.getYearMonth()),
				EnumAdaptor.valueOf(this.krcdtMonAnyItemValuePk.getClosureId(), ClosureId.class),
				new ClosureDate(this.krcdtMonAnyItemValuePk.getClosureDay(),
					(this.krcdtMonAnyItemValuePk.getIsLastDay() == 1)),
				0, (this.timeValue57 == null ? Optional.empty() : Optional.of(new AnyTimeMonth(this.timeValue57))),
			(this.countValue57 == null ? Optional.empty() : Optional.of(new AnyTimesMonth(this.countValue57))),
			(this.moneyValue57 == null ? Optional.empty() : Optional.of(new AnyAmountMonth(this.moneyValue57))));
	}

	public AnyItemOfMonthly toDomainAnyItemOfMonthly58() {
		// get value anyItemId = 0 temporary because table have not anyitemId
		return AnyItemOfMonthly.of(this.krcdtMonAnyItemValuePk.getEmployeeId(),
				new YearMonth(this.krcdtMonAnyItemValuePk.getYearMonth()),
				EnumAdaptor.valueOf(this.krcdtMonAnyItemValuePk.getClosureId(), ClosureId.class),
				new ClosureDate(this.krcdtMonAnyItemValuePk.getClosureDay(),
					(this.krcdtMonAnyItemValuePk.getIsLastDay() == 1)),
				0, (this.timeValue58 == null ? Optional.empty() : Optional.of(new AnyTimeMonth(this.timeValue58))),
			(this.countValue58 == null ? Optional.empty() : Optional.of(new AnyTimesMonth(this.countValue58))),
			(this.moneyValue58 == null ? Optional.empty() : Optional.of(new AnyAmountMonth(this.moneyValue58))));
	}

	public AnyItemOfMonthly toDomainAnyItemOfMonthly59() {
		// get value anyItemId = 0 temporary because table have not anyitemId
		return AnyItemOfMonthly.of(this.krcdtMonAnyItemValuePk.getEmployeeId(),
				new YearMonth(this.krcdtMonAnyItemValuePk.getYearMonth()),
				EnumAdaptor.valueOf(this.krcdtMonAnyItemValuePk.getClosureId(), ClosureId.class),
				new ClosureDate(this.krcdtMonAnyItemValuePk.getClosureDay(),
					(this.krcdtMonAnyItemValuePk.getIsLastDay() == 1)),
				0, (this.timeValue59 == null ? Optional.empty() : Optional.of(new AnyTimeMonth(this.timeValue59))),
			(this.countValue59 == null ? Optional.empty() : Optional.of(new AnyTimesMonth(this.countValue59))),
			(this.moneyValue59 == null ? Optional.empty() : Optional.of(new AnyAmountMonth(this.moneyValue59))));
	}

	public AnyItemOfMonthly toDomainAnyItemOfMonthly60() {
		// get value anyItemId = 0 temporary because table have not anyitemId
		return AnyItemOfMonthly.of(this.krcdtMonAnyItemValuePk.getEmployeeId(),
				new YearMonth(this.krcdtMonAnyItemValuePk.getYearMonth()),
				EnumAdaptor.valueOf(this.krcdtMonAnyItemValuePk.getClosureId(), ClosureId.class),
				new ClosureDate(this.krcdtMonAnyItemValuePk.getClosureDay(),
					(this.krcdtMonAnyItemValuePk.getIsLastDay() == 1)),
				0, (this.timeValue60 == null ? Optional.empty() : Optional.of(new AnyTimeMonth(this.timeValue60))),
			(this.countValue60 == null ? Optional.empty() : Optional.of(new AnyTimesMonth(this.countValue60))),
			(this.moneyValue60 == null ? Optional.empty() : Optional.of(new AnyAmountMonth(this.moneyValue60))));
	}

	public AnyItemOfMonthly toDomainAnyItemOfMonthly61() {
		// get value anyItemId = 0 temporary because table have not anyitemId
		return AnyItemOfMonthly.of(this.krcdtMonAnyItemValuePk.getEmployeeId(),
				new YearMonth(this.krcdtMonAnyItemValuePk.getYearMonth()),
				EnumAdaptor.valueOf(this.krcdtMonAnyItemValuePk.getClosureId(), ClosureId.class),
				new ClosureDate(this.krcdtMonAnyItemValuePk.getClosureDay(),
					(this.krcdtMonAnyItemValuePk.getIsLastDay() == 1)),
				0, (this.timeValue61 == null ? Optional.empty() : Optional.of(new AnyTimeMonth(this.timeValue61))),
			(this.countValue61 == null ? Optional.empty() : Optional.of(new AnyTimesMonth(this.countValue61))),
			(this.moneyValue61 == null ? Optional.empty() : Optional.of(new AnyAmountMonth(this.moneyValue61))));
	}

	public AnyItemOfMonthly toDomainAnyItemOfMonthly62() {
		// get value anyItemId = 0 temporary because table have not anyitemId
		return AnyItemOfMonthly.of(this.krcdtMonAnyItemValuePk.getEmployeeId(),
				new YearMonth(this.krcdtMonAnyItemValuePk.getYearMonth()),
				EnumAdaptor.valueOf(this.krcdtMonAnyItemValuePk.getClosureId(), ClosureId.class),
				new ClosureDate(this.krcdtMonAnyItemValuePk.getClosureDay(),
					(this.krcdtMonAnyItemValuePk.getIsLastDay() == 1)),
				0, (this.timeValue62 == null ? Optional.empty() : Optional.of(new AnyTimeMonth(this.timeValue62))),
			(this.countValue62 == null ? Optional.empty() : Optional.of(new AnyTimesMonth(this.countValue62))),
			(this.moneyValue62 == null ? Optional.empty() : Optional.of(new AnyAmountMonth(this.moneyValue62))));
	}

	public AnyItemOfMonthly toDomainAnyItemOfMonthly63() {
		// get value anyItemId = 0 temporary because table have not anyitemId
		return AnyItemOfMonthly.of(this.krcdtMonAnyItemValuePk.getEmployeeId(),
				new YearMonth(this.krcdtMonAnyItemValuePk.getYearMonth()),
				EnumAdaptor.valueOf(this.krcdtMonAnyItemValuePk.getClosureId(), ClosureId.class),
				new ClosureDate(this.krcdtMonAnyItemValuePk.getClosureDay(),
					(this.krcdtMonAnyItemValuePk.getIsLastDay() == 1)),
				0, (this.timeValue63 == null ? Optional.empty() : Optional.of(new AnyTimeMonth(this.timeValue63))),
			(this.countValue63 == null ? Optional.empty() : Optional.of(new AnyTimesMonth(this.countValue63))),
			(this.moneyValue63 == null ? Optional.empty() : Optional.of(new AnyAmountMonth(this.moneyValue63))));
	}

	public AnyItemOfMonthly toDomainAnyItemOfMonthly64() {
		// get value anyItemId = 0 temporary because table have not anyitemId
		return AnyItemOfMonthly.of(this.krcdtMonAnyItemValuePk.getEmployeeId(),
				new YearMonth(this.krcdtMonAnyItemValuePk.getYearMonth()),
				EnumAdaptor.valueOf(this.krcdtMonAnyItemValuePk.getClosureId(), ClosureId.class),
				new ClosureDate(this.krcdtMonAnyItemValuePk.getClosureDay(),
					(this.krcdtMonAnyItemValuePk.getIsLastDay() == 1)),
				0, (this.timeValue64 == null ? Optional.empty() : Optional.of(new AnyTimeMonth(this.timeValue64))),
			(this.countValue64 == null ? Optional.empty() : Optional.of(new AnyTimesMonth(this.countValue64))),
			(this.moneyValue64 == null ? Optional.empty() : Optional.of(new AnyAmountMonth(this.moneyValue64))));
	}

	public AnyItemOfMonthly toDomainAnyItemOfMonthly65() {
		// get value anyItemId = 0 temporary because table have not anyitemId
		return AnyItemOfMonthly.of(this.krcdtMonAnyItemValuePk.getEmployeeId(),
				new YearMonth(this.krcdtMonAnyItemValuePk.getYearMonth()),
				EnumAdaptor.valueOf(this.krcdtMonAnyItemValuePk.getClosureId(), ClosureId.class),
				new ClosureDate(this.krcdtMonAnyItemValuePk.getClosureDay(),
					(this.krcdtMonAnyItemValuePk.getIsLastDay() == 1)),
				0, (this.timeValue65 == null ? Optional.empty() : Optional.of(new AnyTimeMonth(this.timeValue65))),
			(this.countValue65 == null ? Optional.empty() : Optional.of(new AnyTimesMonth(this.countValue65))),
			(this.moneyValue65 == null ? Optional.empty() : Optional.of(new AnyAmountMonth(this.moneyValue65))));
	}

	public AnyItemOfMonthly toDomainAnyItemOfMonthly66() {
		// get value anyItemId = 0 temporary because table have not anyitemId
		return AnyItemOfMonthly.of(this.krcdtMonAnyItemValuePk.getEmployeeId(),
				new YearMonth(this.krcdtMonAnyItemValuePk.getYearMonth()),
				EnumAdaptor.valueOf(this.krcdtMonAnyItemValuePk.getClosureId(), ClosureId.class),
				new ClosureDate(this.krcdtMonAnyItemValuePk.getClosureDay(),
					(this.krcdtMonAnyItemValuePk.getIsLastDay() == 1)),
				0, (this.timeValue66 == null ? Optional.empty() : Optional.of(new AnyTimeMonth(this.timeValue66))),
			(this.countValue66 == null ? Optional.empty() : Optional.of(new AnyTimesMonth(this.countValue66))),
			(this.moneyValue66 == null ? Optional.empty() : Optional.of(new AnyAmountMonth(this.moneyValue66))));
	}

	public AnyItemOfMonthly toDomainAnyItemOfMonthly67() {
		// get value anyItemId = 0 temporary because table have not anyitemId
		return AnyItemOfMonthly.of(this.krcdtMonAnyItemValuePk.getEmployeeId(),
				new YearMonth(this.krcdtMonAnyItemValuePk.getYearMonth()),
				EnumAdaptor.valueOf(this.krcdtMonAnyItemValuePk.getClosureId(), ClosureId.class),
				new ClosureDate(this.krcdtMonAnyItemValuePk.getClosureDay(),
					(this.krcdtMonAnyItemValuePk.getIsLastDay() == 1)),
				0, (this.timeValue67 == null ? Optional.empty() : Optional.of(new AnyTimeMonth(this.timeValue67))),
			(this.countValue67 == null ? Optional.empty() : Optional.of(new AnyTimesMonth(this.countValue67))),
			(this.moneyValue67 == null ? Optional.empty() : Optional.of(new AnyAmountMonth(this.moneyValue67))));
	}

	public AnyItemOfMonthly toDomainAnyItemOfMonthly68() {
		// get value anyItemId = 0 temporary because table have not anyitemId
		return AnyItemOfMonthly.of(this.krcdtMonAnyItemValuePk.getEmployeeId(),
				new YearMonth(this.krcdtMonAnyItemValuePk.getYearMonth()),
				EnumAdaptor.valueOf(this.krcdtMonAnyItemValuePk.getClosureId(), ClosureId.class),
				new ClosureDate(this.krcdtMonAnyItemValuePk.getClosureDay(),
					(this.krcdtMonAnyItemValuePk.getIsLastDay() == 1)),
				0, (this.timeValue68 == null ? Optional.empty() : Optional.of(new AnyTimeMonth(this.timeValue68))),
			(this.countValue68 == null ? Optional.empty() : Optional.of(new AnyTimesMonth(this.countValue68))),
			(this.moneyValue68 == null ? Optional.empty() : Optional.of(new AnyAmountMonth(this.moneyValue68))));
	}

	public AnyItemOfMonthly toDomainAnyItemOfMonthly69() {
		// get value anyItemId = 0 temporary because table have not anyitemId
		return AnyItemOfMonthly.of(this.krcdtMonAnyItemValuePk.getEmployeeId(),
				new YearMonth(this.krcdtMonAnyItemValuePk.getYearMonth()),
				EnumAdaptor.valueOf(this.krcdtMonAnyItemValuePk.getClosureId(), ClosureId.class),
				new ClosureDate(this.krcdtMonAnyItemValuePk.getClosureDay(),
					(this.krcdtMonAnyItemValuePk.getIsLastDay() == 1)),
				0, (this.timeValue69 == null ? Optional.empty() : Optional.of(new AnyTimeMonth(this.timeValue69))),
			(this.countValue69 == null ? Optional.empty() : Optional.of(new AnyTimesMonth(this.countValue69))),
			(this.moneyValue69 == null ? Optional.empty() : Optional.of(new AnyAmountMonth(this.moneyValue69))));
	}

	public AnyItemOfMonthly toDomainAnyItemOfMonthly70() {
		// get value anyItemId = 0 temporary because table have not anyitemId
		return AnyItemOfMonthly.of(this.krcdtMonAnyItemValuePk.getEmployeeId(),
				new YearMonth(this.krcdtMonAnyItemValuePk.getYearMonth()),
				EnumAdaptor.valueOf(this.krcdtMonAnyItemValuePk.getClosureId(), ClosureId.class),
				new ClosureDate(this.krcdtMonAnyItemValuePk.getClosureDay(),
					(this.krcdtMonAnyItemValuePk.getIsLastDay() == 1)),
				0, (this.timeValue70 == null ? Optional.empty() : Optional.of(new AnyTimeMonth(this.timeValue70))),
			(this.countValue70 == null ? Optional.empty() : Optional.of(new AnyTimesMonth(this.countValue70))),
			(this.moneyValue70 == null ? Optional.empty() : Optional.of(new AnyAmountMonth(this.moneyValue70))));
	}

	public AnyItemOfMonthly toDomainAnyItemOfMonthly71() {
		// get value anyItemId = 0 temporary because table have not anyitemId
		return AnyItemOfMonthly.of(this.krcdtMonAnyItemValuePk.getEmployeeId(),
				new YearMonth(this.krcdtMonAnyItemValuePk.getYearMonth()),
				EnumAdaptor.valueOf(this.krcdtMonAnyItemValuePk.getClosureId(), ClosureId.class),
				new ClosureDate(this.krcdtMonAnyItemValuePk.getClosureDay(),
					(this.krcdtMonAnyItemValuePk.getIsLastDay() == 1)),
				0, (this.timeValue71 == null ? Optional.empty() : Optional.of(new AnyTimeMonth(this.timeValue71))),
			(this.countValue71 == null ? Optional.empty() : Optional.of(new AnyTimesMonth(this.countValue71))),
			(this.moneyValue71 == null ? Optional.empty() : Optional.of(new AnyAmountMonth(this.moneyValue71))));
	}

	public AnyItemOfMonthly toDomainAnyItemOfMonthly72() {
		// get value anyItemId = 0 temporary because table have not anyitemId
		return AnyItemOfMonthly.of(this.krcdtMonAnyItemValuePk.getEmployeeId(),
				new YearMonth(this.krcdtMonAnyItemValuePk.getYearMonth()),
				EnumAdaptor.valueOf(this.krcdtMonAnyItemValuePk.getClosureId(), ClosureId.class),
				new ClosureDate(this.krcdtMonAnyItemValuePk.getClosureDay(),
					(this.krcdtMonAnyItemValuePk.getIsLastDay() == 1)),
				0, (this.timeValue72 == null ? Optional.empty() : Optional.of(new AnyTimeMonth(this.timeValue72))),
			(this.countValue72 == null ? Optional.empty() : Optional.of(new AnyTimesMonth(this.countValue72))),
			(this.moneyValue72 == null ? Optional.empty() : Optional.of(new AnyAmountMonth(this.moneyValue72))));
	}

	public AnyItemOfMonthly toDomainAnyItemOfMonthly73() {
		// get value anyItemId = 0 temporary because table have not anyitemId
		return AnyItemOfMonthly.of(this.krcdtMonAnyItemValuePk.getEmployeeId(),
				new YearMonth(this.krcdtMonAnyItemValuePk.getYearMonth()),
				EnumAdaptor.valueOf(this.krcdtMonAnyItemValuePk.getClosureId(), ClosureId.class),
				new ClosureDate(this.krcdtMonAnyItemValuePk.getClosureDay(),
					(this.krcdtMonAnyItemValuePk.getIsLastDay() == 1)),
				0, (this.timeValue73 == null ? Optional.empty() : Optional.of(new AnyTimeMonth(this.timeValue73))),
			(this.countValue73 == null ? Optional.empty() : Optional.of(new AnyTimesMonth(this.countValue73))),
			(this.moneyValue73 == null ? Optional.empty() : Optional.of(new AnyAmountMonth(this.moneyValue73))));
	}

	public AnyItemOfMonthly toDomainAnyItemOfMonthly74() {
		// get value anyItemId = 0 temporary because table have not anyitemId
		return AnyItemOfMonthly.of(this.krcdtMonAnyItemValuePk.getEmployeeId(),
				new YearMonth(this.krcdtMonAnyItemValuePk.getYearMonth()),
				EnumAdaptor.valueOf(this.krcdtMonAnyItemValuePk.getClosureId(), ClosureId.class),
				new ClosureDate(this.krcdtMonAnyItemValuePk.getClosureDay(),
					(this.krcdtMonAnyItemValuePk.getIsLastDay() == 1)),
				0, (this.timeValue74 == null ? Optional.empty() : Optional.of(new AnyTimeMonth(this.timeValue74))),
			(this.countValue74 == null ? Optional.empty() : Optional.of(new AnyTimesMonth(this.countValue74))),
			(this.moneyValue74 == null ? Optional.empty() : Optional.of(new AnyAmountMonth(this.moneyValue74))));
	}

	public AnyItemOfMonthly toDomainAnyItemOfMonthly75() {
		// get value anyItemId = 0 temporary because table have not anyitemId
		return AnyItemOfMonthly.of(this.krcdtMonAnyItemValuePk.getEmployeeId(),
				new YearMonth(this.krcdtMonAnyItemValuePk.getYearMonth()),
				EnumAdaptor.valueOf(this.krcdtMonAnyItemValuePk.getClosureId(), ClosureId.class),
				new ClosureDate(this.krcdtMonAnyItemValuePk.getClosureDay(),
					(this.krcdtMonAnyItemValuePk.getIsLastDay() == 1)),
				0, (this.timeValue75 == null ? Optional.empty() : Optional.of(new AnyTimeMonth(this.timeValue75))),
			(this.countValue75 == null ? Optional.empty() : Optional.of(new AnyTimesMonth(this.countValue75))),
			(this.moneyValue75 == null ? Optional.empty() : Optional.of(new AnyAmountMonth(this.moneyValue75))));
	}

	public AnyItemOfMonthly toDomainAnyItemOfMonthly76() {
		// get value anyItemId = 0 temporary because table have not anyitemId
		return AnyItemOfMonthly.of(this.krcdtMonAnyItemValuePk.getEmployeeId(),
				new YearMonth(this.krcdtMonAnyItemValuePk.getYearMonth()),
				EnumAdaptor.valueOf(this.krcdtMonAnyItemValuePk.getClosureId(), ClosureId.class),
				new ClosureDate(this.krcdtMonAnyItemValuePk.getClosureDay(),
					(this.krcdtMonAnyItemValuePk.getIsLastDay() == 1)),
				0, (this.timeValue76 == null ? Optional.empty() : Optional.of(new AnyTimeMonth(this.timeValue76))),
			(this.countValue76 == null ? Optional.empty() : Optional.of(new AnyTimesMonth(this.countValue76))),
			(this.moneyValue76 == null ? Optional.empty() : Optional.of(new AnyAmountMonth(this.moneyValue76))));
	}

	public AnyItemOfMonthly toDomainAnyItemOfMonthly77() {
		// get value anyItemId = 0 temporary because table have not anyitemId
		return AnyItemOfMonthly.of(this.krcdtMonAnyItemValuePk.getEmployeeId(),
				new YearMonth(this.krcdtMonAnyItemValuePk.getYearMonth()),
				EnumAdaptor.valueOf(this.krcdtMonAnyItemValuePk.getClosureId(), ClosureId.class),
				new ClosureDate(this.krcdtMonAnyItemValuePk.getClosureDay(),
					(this.krcdtMonAnyItemValuePk.getIsLastDay() == 1)),
				0, (this.timeValue77 == null ? Optional.empty() : Optional.of(new AnyTimeMonth(this.timeValue77))),
			(this.countValue77 == null ? Optional.empty() : Optional.of(new AnyTimesMonth(this.countValue77))),
			(this.moneyValue77 == null ? Optional.empty() : Optional.of(new AnyAmountMonth(this.moneyValue77))));
	}

	public AnyItemOfMonthly toDomainAnyItemOfMonthly78() {
		// get value anyItemId = 0 temporary because table have not anyitemId
		return AnyItemOfMonthly.of(this.krcdtMonAnyItemValuePk.getEmployeeId(),
				new YearMonth(this.krcdtMonAnyItemValuePk.getYearMonth()),
				EnumAdaptor.valueOf(this.krcdtMonAnyItemValuePk.getClosureId(), ClosureId.class),
				new ClosureDate(this.krcdtMonAnyItemValuePk.getClosureDay(),
					(this.krcdtMonAnyItemValuePk.getIsLastDay() == 1)),
				0, (this.timeValue78 == null ? Optional.empty() : Optional.of(new AnyTimeMonth(this.timeValue78))),
			(this.countValue78 == null ? Optional.empty() : Optional.of(new AnyTimesMonth(this.countValue78))),
			(this.moneyValue78 == null ? Optional.empty() : Optional.of(new AnyAmountMonth(this.moneyValue78))));
	}

	public AnyItemOfMonthly toDomainAnyItemOfMonthly79() {
		// get value anyItemId = 0 temporary because table have not anyitemId
		return AnyItemOfMonthly.of(this.krcdtMonAnyItemValuePk.getEmployeeId(),
				new YearMonth(this.krcdtMonAnyItemValuePk.getYearMonth()),
				EnumAdaptor.valueOf(this.krcdtMonAnyItemValuePk.getClosureId(), ClosureId.class),
				new ClosureDate(this.krcdtMonAnyItemValuePk.getClosureDay(),
					(this.krcdtMonAnyItemValuePk.getIsLastDay() == 1)),
				0, (this.timeValue79 == null ? Optional.empty() : Optional.of(new AnyTimeMonth(this.timeValue79))),
			(this.countValue79 == null ? Optional.empty() : Optional.of(new AnyTimesMonth(this.countValue79))),
			(this.moneyValue79 == null ? Optional.empty() : Optional.of(new AnyAmountMonth(this.moneyValue79))));
	}

	public AnyItemOfMonthly toDomainAnyItemOfMonthly80() {
		// get value anyItemId = 0 temporary because table have not anyitemId
		return AnyItemOfMonthly.of(this.krcdtMonAnyItemValuePk.getEmployeeId(),
				new YearMonth(this.krcdtMonAnyItemValuePk.getYearMonth()),
				EnumAdaptor.valueOf(this.krcdtMonAnyItemValuePk.getClosureId(), ClosureId.class),
				new ClosureDate(this.krcdtMonAnyItemValuePk.getClosureDay(),
					(this.krcdtMonAnyItemValuePk.getIsLastDay() == 1)),
				0, (this.timeValue80 == null ? Optional.empty() : Optional.of(new AnyTimeMonth(this.timeValue80))),
			(this.countValue80 == null ? Optional.empty() : Optional.of(new AnyTimesMonth(this.countValue80))),
			(this.moneyValue80 == null ? Optional.empty() : Optional.of(new AnyAmountMonth(this.moneyValue80))));
	}

	public AnyItemOfMonthly toDomainAnyItemOfMonthly81() {
		// get value anyItemId = 0 temporary because table have not anyitemId
		return AnyItemOfMonthly.of(this.krcdtMonAnyItemValuePk.getEmployeeId(),
				new YearMonth(this.krcdtMonAnyItemValuePk.getYearMonth()),
				EnumAdaptor.valueOf(this.krcdtMonAnyItemValuePk.getClosureId(), ClosureId.class),
				new ClosureDate(this.krcdtMonAnyItemValuePk.getClosureDay(),
					(this.krcdtMonAnyItemValuePk.getIsLastDay() == 1)),
				0, (this.timeValue81 == null ? Optional.empty() : Optional.of(new AnyTimeMonth(this.timeValue81))),
			(this.countValue81 == null ? Optional.empty() : Optional.of(new AnyTimesMonth(this.countValue81))),
			(this.moneyValue81 == null ? Optional.empty() : Optional.of(new AnyAmountMonth(this.moneyValue81))));
	}

	public AnyItemOfMonthly toDomainAnyItemOfMonthly82() {
		// get value anyItemId = 0 temporary because table have not anyitemId
		return AnyItemOfMonthly.of(this.krcdtMonAnyItemValuePk.getEmployeeId(),
				new YearMonth(this.krcdtMonAnyItemValuePk.getYearMonth()),
				EnumAdaptor.valueOf(this.krcdtMonAnyItemValuePk.getClosureId(), ClosureId.class),
				new ClosureDate(this.krcdtMonAnyItemValuePk.getClosureDay(),
					(this.krcdtMonAnyItemValuePk.getIsLastDay() == 1)),
				0, (this.timeValue82 == null ? Optional.empty() : Optional.of(new AnyTimeMonth(this.timeValue82))),
			(this.countValue82 == null ? Optional.empty() : Optional.of(new AnyTimesMonth(this.countValue82))),
			(this.moneyValue82 == null ? Optional.empty() : Optional.of(new AnyAmountMonth(this.moneyValue82))));
	}

	public AnyItemOfMonthly toDomainAnyItemOfMonthly83() {
		// get value anyItemId = 0 temporary because table have not anyitemId
		return AnyItemOfMonthly.of(this.krcdtMonAnyItemValuePk.getEmployeeId(),
				new YearMonth(this.krcdtMonAnyItemValuePk.getYearMonth()),
				EnumAdaptor.valueOf(this.krcdtMonAnyItemValuePk.getClosureId(), ClosureId.class),
				new ClosureDate(this.krcdtMonAnyItemValuePk.getClosureDay(),
					(this.krcdtMonAnyItemValuePk.getIsLastDay() == 1)),
				0, (this.timeValue83 == null ? Optional.empty() : Optional.of(new AnyTimeMonth(this.timeValue83))),
			(this.countValue83 == null ? Optional.empty() : Optional.of(new AnyTimesMonth(this.countValue83))),
			(this.moneyValue83 == null ? Optional.empty() : Optional.of(new AnyAmountMonth(this.moneyValue83))));
	}

	public AnyItemOfMonthly toDomainAnyItemOfMonthly84() {
		// get value anyItemId = 0 temporary because table have not anyitemId
		return AnyItemOfMonthly.of(this.krcdtMonAnyItemValuePk.getEmployeeId(),
				new YearMonth(this.krcdtMonAnyItemValuePk.getYearMonth()),
				EnumAdaptor.valueOf(this.krcdtMonAnyItemValuePk.getClosureId(), ClosureId.class),
				new ClosureDate(this.krcdtMonAnyItemValuePk.getClosureDay(),
					(this.krcdtMonAnyItemValuePk.getIsLastDay() == 1)),
				0, (this.timeValue84 == null ? Optional.empty() : Optional.of(new AnyTimeMonth(this.timeValue84))),
			(this.countValue84 == null ? Optional.empty() : Optional.of(new AnyTimesMonth(this.countValue84))),
			(this.moneyValue84 == null ? Optional.empty() : Optional.of(new AnyAmountMonth(this.moneyValue84))));
	}

	public AnyItemOfMonthly toDomainAnyItemOfMonthly85() {
		// get value anyItemId = 0 temporary because table have not anyitemId
		return AnyItemOfMonthly.of(this.krcdtMonAnyItemValuePk.getEmployeeId(),
				new YearMonth(this.krcdtMonAnyItemValuePk.getYearMonth()),
				EnumAdaptor.valueOf(this.krcdtMonAnyItemValuePk.getClosureId(), ClosureId.class),
				new ClosureDate(this.krcdtMonAnyItemValuePk.getClosureDay(),
					(this.krcdtMonAnyItemValuePk.getIsLastDay() == 1)),
				0, (this.timeValue85 == null ? Optional.empty() : Optional.of(new AnyTimeMonth(this.timeValue85))),
			(this.countValue85 == null ? Optional.empty() : Optional.of(new AnyTimesMonth(this.countValue85))),
			(this.moneyValue85 == null ? Optional.empty() : Optional.of(new AnyAmountMonth(this.moneyValue85))));
	}

	public AnyItemOfMonthly toDomainAnyItemOfMonthly86() {
		// get value anyItemId = 0 temporary because table have not anyitemId
		return AnyItemOfMonthly.of(this.krcdtMonAnyItemValuePk.getEmployeeId(),
				new YearMonth(this.krcdtMonAnyItemValuePk.getYearMonth()),
				EnumAdaptor.valueOf(this.krcdtMonAnyItemValuePk.getClosureId(), ClosureId.class),
				new ClosureDate(this.krcdtMonAnyItemValuePk.getClosureDay(),
					(this.krcdtMonAnyItemValuePk.getIsLastDay() == 1)),
				0, (this.timeValue86 == null ? Optional.empty() : Optional.of(new AnyTimeMonth(this.timeValue86))),
			(this.countValue86 == null ? Optional.empty() : Optional.of(new AnyTimesMonth(this.countValue86))),
			(this.moneyValue86 == null ? Optional.empty() : Optional.of(new AnyAmountMonth(this.moneyValue86))));
	}

	public AnyItemOfMonthly toDomainAnyItemOfMonthly87() {
		// get value anyItemId = 0 temporary because table have not anyitemId
		return AnyItemOfMonthly.of(this.krcdtMonAnyItemValuePk.getEmployeeId(),
				new YearMonth(this.krcdtMonAnyItemValuePk.getYearMonth()),
				EnumAdaptor.valueOf(this.krcdtMonAnyItemValuePk.getClosureId(), ClosureId.class),
				new ClosureDate(this.krcdtMonAnyItemValuePk.getClosureDay(),
					(this.krcdtMonAnyItemValuePk.getIsLastDay() == 1)),
				0, (this.timeValue87 == null ? Optional.empty() : Optional.of(new AnyTimeMonth(this.timeValue87))),
			(this.countValue87 == null ? Optional.empty() : Optional.of(new AnyTimesMonth(this.countValue87))),
			(this.moneyValue87 == null ? Optional.empty() : Optional.of(new AnyAmountMonth(this.moneyValue87))));
	}

	public AnyItemOfMonthly toDomainAnyItemOfMonthly88() {
		// get value anyItemId = 0 temporary because table have not anyitemId
		return AnyItemOfMonthly.of(this.krcdtMonAnyItemValuePk.getEmployeeId(),
				new YearMonth(this.krcdtMonAnyItemValuePk.getYearMonth()),
				EnumAdaptor.valueOf(this.krcdtMonAnyItemValuePk.getClosureId(), ClosureId.class),
				new ClosureDate(this.krcdtMonAnyItemValuePk.getClosureDay(),
					(this.krcdtMonAnyItemValuePk.getIsLastDay() == 1)),
				0, (this.timeValue88 == null ? Optional.empty() : Optional.of(new AnyTimeMonth(this.timeValue88))),
			(this.countValue88 == null ? Optional.empty() : Optional.of(new AnyTimesMonth(this.countValue88))),
			(this.moneyValue88 == null ? Optional.empty() : Optional.of(new AnyAmountMonth(this.moneyValue88))));
	}

	public AnyItemOfMonthly toDomainAnyItemOfMonthly89() {
		// get value anyItemId = 0 temporary because table have not anyitemId
		return AnyItemOfMonthly.of(this.krcdtMonAnyItemValuePk.getEmployeeId(),
				new YearMonth(this.krcdtMonAnyItemValuePk.getYearMonth()),
				EnumAdaptor.valueOf(this.krcdtMonAnyItemValuePk.getClosureId(), ClosureId.class),
				new ClosureDate(this.krcdtMonAnyItemValuePk.getClosureDay(),
					(this.krcdtMonAnyItemValuePk.getIsLastDay() == 1)),
				0, (this.timeValue89 == null ? Optional.empty() : Optional.of(new AnyTimeMonth(this.timeValue89))),
			(this.countValue89 == null ? Optional.empty() : Optional.of(new AnyTimesMonth(this.countValue89))),
			(this.moneyValue89 == null ? Optional.empty() : Optional.of(new AnyAmountMonth(this.moneyValue89))));
	}

	public AnyItemOfMonthly toDomainAnyItemOfMonthly90() {
		// get value anyItemId = 0 temporary because table have not anyitemId
		return AnyItemOfMonthly.of(this.krcdtMonAnyItemValuePk.getEmployeeId(),
				new YearMonth(this.krcdtMonAnyItemValuePk.getYearMonth()),
				EnumAdaptor.valueOf(this.krcdtMonAnyItemValuePk.getClosureId(), ClosureId.class),
				new ClosureDate(this.krcdtMonAnyItemValuePk.getClosureDay(),
					(this.krcdtMonAnyItemValuePk.getIsLastDay() == 1)),
				0, (this.timeValue90 == null ? Optional.empty() : Optional.of(new AnyTimeMonth(this.timeValue90))),
			(this.countValue90 == null ? Optional.empty() : Optional.of(new AnyTimesMonth(this.countValue90))),
			(this.moneyValue90 == null ? Optional.empty() : Optional.of(new AnyAmountMonth(this.moneyValue90))));
	}

	public AnyItemOfMonthly toDomainAnyItemOfMonthly91() {
		// get value anyItemId = 0 temporary because table have not anyitemId
		return AnyItemOfMonthly.of(this.krcdtMonAnyItemValuePk.getEmployeeId(),
				new YearMonth(this.krcdtMonAnyItemValuePk.getYearMonth()),
				EnumAdaptor.valueOf(this.krcdtMonAnyItemValuePk.getClosureId(), ClosureId.class),
				new ClosureDate(this.krcdtMonAnyItemValuePk.getClosureDay(),
					(this.krcdtMonAnyItemValuePk.getIsLastDay() == 1)),
				0, (this.timeValue91 == null ? Optional.empty() : Optional.of(new AnyTimeMonth(this.timeValue91))),
			(this.countValue91 == null ? Optional.empty() : Optional.of(new AnyTimesMonth(this.countValue91))),
			(this.moneyValue91 == null ? Optional.empty() : Optional.of(new AnyAmountMonth(this.moneyValue91))));
	}

	public AnyItemOfMonthly toDomainAnyItemOfMonthly92() {
		// get value anyItemId = 0 temporary because table have not anyitemId
		return AnyItemOfMonthly.of(this.krcdtMonAnyItemValuePk.getEmployeeId(),
				new YearMonth(this.krcdtMonAnyItemValuePk.getYearMonth()),
				EnumAdaptor.valueOf(this.krcdtMonAnyItemValuePk.getClosureId(), ClosureId.class),
				new ClosureDate(this.krcdtMonAnyItemValuePk.getClosureDay(),
					(this.krcdtMonAnyItemValuePk.getIsLastDay() == 1)),
				0, (this.timeValue92 == null ? Optional.empty() : Optional.of(new AnyTimeMonth(this.timeValue92))),
			(this.countValue92 == null ? Optional.empty() : Optional.of(new AnyTimesMonth(this.countValue92))),
			(this.moneyValue92 == null ? Optional.empty() : Optional.of(new AnyAmountMonth(this.moneyValue92))));
	}

	public AnyItemOfMonthly toDomainAnyItemOfMonthly93() {
		// get value anyItemId = 0 temporary because table have not anyitemId
		return AnyItemOfMonthly.of(this.krcdtMonAnyItemValuePk.getEmployeeId(),
				new YearMonth(this.krcdtMonAnyItemValuePk.getYearMonth()),
				EnumAdaptor.valueOf(this.krcdtMonAnyItemValuePk.getClosureId(), ClosureId.class),
				new ClosureDate(this.krcdtMonAnyItemValuePk.getClosureDay(),
					(this.krcdtMonAnyItemValuePk.getIsLastDay() == 1)),
				0, (this.timeValue93 == null ? Optional.empty() : Optional.of(new AnyTimeMonth(this.timeValue93))),
			(this.countValue93 == null ? Optional.empty() : Optional.of(new AnyTimesMonth(this.countValue93))),
			(this.moneyValue93 == null ? Optional.empty() : Optional.of(new AnyAmountMonth(this.moneyValue93))));
	}

	public AnyItemOfMonthly toDomainAnyItemOfMonthly94() {
		// get value anyItemId = 0 temporary because table have not anyitemId
		return AnyItemOfMonthly.of(this.krcdtMonAnyItemValuePk.getEmployeeId(),
				new YearMonth(this.krcdtMonAnyItemValuePk.getYearMonth()),
				EnumAdaptor.valueOf(this.krcdtMonAnyItemValuePk.getClosureId(), ClosureId.class),
				new ClosureDate(this.krcdtMonAnyItemValuePk.getClosureDay(),
					(this.krcdtMonAnyItemValuePk.getIsLastDay() == 1)),
				0, (this.timeValue94 == null ? Optional.empty() : Optional.of(new AnyTimeMonth(this.timeValue94))),
			(this.countValue94 == null ? Optional.empty() : Optional.of(new AnyTimesMonth(this.countValue94))),
			(this.moneyValue94 == null ? Optional.empty() : Optional.of(new AnyAmountMonth(this.moneyValue94))));
	}

	public AnyItemOfMonthly toDomainAnyItemOfMonthly95() {
		// get value anyItemId = 0 temporary because table have not anyitemId
		return AnyItemOfMonthly.of(this.krcdtMonAnyItemValuePk.getEmployeeId(),
				new YearMonth(this.krcdtMonAnyItemValuePk.getYearMonth()),
				EnumAdaptor.valueOf(this.krcdtMonAnyItemValuePk.getClosureId(), ClosureId.class),
				new ClosureDate(this.krcdtMonAnyItemValuePk.getClosureDay(),
					(this.krcdtMonAnyItemValuePk.getIsLastDay() == 1)),
				0, (this.timeValue95 == null ? Optional.empty() : Optional.of(new AnyTimeMonth(this.timeValue95))),
			(this.countValue95 == null ? Optional.empty() : Optional.of(new AnyTimesMonth(this.countValue95))),
			(this.moneyValue95 == null ? Optional.empty() : Optional.of(new AnyAmountMonth(this.moneyValue95))));
	}

	public AnyItemOfMonthly toDomainAnyItemOfMonthly96() {
		// get value anyItemId = 0 temporary because table have not anyitemId
		return AnyItemOfMonthly.of(this.krcdtMonAnyItemValuePk.getEmployeeId(),
				new YearMonth(this.krcdtMonAnyItemValuePk.getYearMonth()),
				EnumAdaptor.valueOf(this.krcdtMonAnyItemValuePk.getClosureId(), ClosureId.class),
				new ClosureDate(this.krcdtMonAnyItemValuePk.getClosureDay(),
					(this.krcdtMonAnyItemValuePk.getIsLastDay() == 1)),
				0, (this.timeValue96 == null ? Optional.empty() : Optional.of(new AnyTimeMonth(this.timeValue96))),
			(this.countValue96 == null ? Optional.empty() : Optional.of(new AnyTimesMonth(this.countValue96))),
			(this.moneyValue96 == null ? Optional.empty() : Optional.of(new AnyAmountMonth(this.moneyValue96))));
	}

	public AnyItemOfMonthly toDomainAnyItemOfMonthly97() {
		// get value anyItemId = 0 temporary because table have not anyitemId
		return AnyItemOfMonthly.of(this.krcdtMonAnyItemValuePk.getEmployeeId(),
				new YearMonth(this.krcdtMonAnyItemValuePk.getYearMonth()),
				EnumAdaptor.valueOf(this.krcdtMonAnyItemValuePk.getClosureId(), ClosureId.class),
				new ClosureDate(this.krcdtMonAnyItemValuePk.getClosureDay(),
					(this.krcdtMonAnyItemValuePk.getIsLastDay() == 1)),
				0, (this.timeValue97 == null ? Optional.empty() : Optional.of(new AnyTimeMonth(this.timeValue97))),
			(this.countValue97 == null ? Optional.empty() : Optional.of(new AnyTimesMonth(this.countValue97))),
			(this.moneyValue97 == null ? Optional.empty() : Optional.of(new AnyAmountMonth(this.moneyValue97))));
	}

	public AnyItemOfMonthly toDomainAnyItemOfMonthly98() {
		// get value anyItemId = 0 temporary because table have not anyitemId
		return AnyItemOfMonthly.of(this.krcdtMonAnyItemValuePk.getEmployeeId(),
				new YearMonth(this.krcdtMonAnyItemValuePk.getYearMonth()),
				EnumAdaptor.valueOf(this.krcdtMonAnyItemValuePk.getClosureId(), ClosureId.class),
				new ClosureDate(this.krcdtMonAnyItemValuePk.getClosureDay(),
					(this.krcdtMonAnyItemValuePk.getIsLastDay() == 1)),
				0, (this.timeValue98 == null ? Optional.empty() : Optional.of(new AnyTimeMonth(this.timeValue98))),
			(this.countValue98 == null ? Optional.empty() : Optional.of(new AnyTimesMonth(this.countValue98))),
			(this.moneyValue98 == null ? Optional.empty() : Optional.of(new AnyAmountMonth(this.moneyValue98))));
	}

	public AnyItemOfMonthly toDomainAnyItemOfMonthly99() {
		// get value anyItemId = 0 temporary because table have not anyitemId
		return AnyItemOfMonthly.of(this.krcdtMonAnyItemValuePk.getEmployeeId(),
				new YearMonth(this.krcdtMonAnyItemValuePk.getYearMonth()),
				EnumAdaptor.valueOf(this.krcdtMonAnyItemValuePk.getClosureId(), ClosureId.class),
				new ClosureDate(this.krcdtMonAnyItemValuePk.getClosureDay(),
					(this.krcdtMonAnyItemValuePk.getIsLastDay() == 1)),
				0, (this.timeValue99 == null ? Optional.empty() : Optional.of(new AnyTimeMonth(this.timeValue99))),
			(this.countValue99 == null ? Optional.empty() : Optional.of(new AnyTimesMonth(this.countValue99))),
			(this.moneyValue99 == null ? Optional.empty() : Optional.of(new AnyAmountMonth(this.moneyValue99))));
	}

	public AnyItemOfMonthly toDomainAnyItemOfMonthly100() { 
	//get  value anyItemId  = 0 temporary because table have not anyitemId
	return AnyItemOfMonthly.of(
	this.krcdtMonAnyItemValuePk.getEmployeeId(),
					new YearMonth(this.krcdtMonAnyItemValuePk.getYearMonth()),
					EnumAdaptor.valueOf(this.krcdtMonAnyItemValuePk.getClosureId(), ClosureId.class),
					new ClosureDate(this.krcdtMonAnyItemValuePk.getClosureDay(), (this.krcdtMonAnyItemValuePk.getIsLastDay() == 1)),
					0,
(this.timeValue100 == null ? Optional.empty() : Optional.of(new AnyTimeMonth(this.timeValue100))),
(this.countValue100 == null ? Optional.empty() : Optional.of(new AnyTimesMonth(this.countValue100))),
(this.moneyValue100 == null ? Optional.empty() : Optional.of(new AnyAmountMonth(this.moneyValue100))));
	}	
	
	public AnyItemOfMonthly toDomainAnyItemOfMonthly101() {
		// get value anyItemId = 0 temporary because table have not anyitemId
		return AnyItemOfMonthly.of(this.krcdtMonAnyItemValuePk.getEmployeeId(),
				new YearMonth(this.krcdtMonAnyItemValuePk.getYearMonth()),
				EnumAdaptor.valueOf(this.krcdtMonAnyItemValuePk.getClosureId(), ClosureId.class),
				new ClosureDate(this.krcdtMonAnyItemValuePk.getClosureDay(),
					(this.krcdtMonAnyItemValuePk.getIsLastDay() == 1)),
				0, (this.timeValue101 == null ? Optional.empty() : Optional.of(new AnyTimeMonth(this.timeValue101))),
			(this.countValue101 == null ? Optional.empty() : Optional.of(new AnyTimesMonth(this.countValue101))),
			(this.moneyValue101 == null ? Optional.empty() : Optional.of(new AnyAmountMonth(this.moneyValue101))));
	}

	public AnyItemOfMonthly toDomainAnyItemOfMonthly102() {
		// get value anyItemId = 0 temporary because table have not anyitemId
		return AnyItemOfMonthly.of(this.krcdtMonAnyItemValuePk.getEmployeeId(),
				new YearMonth(this.krcdtMonAnyItemValuePk.getYearMonth()),
				EnumAdaptor.valueOf(this.krcdtMonAnyItemValuePk.getClosureId(), ClosureId.class),
				new ClosureDate(this.krcdtMonAnyItemValuePk.getClosureDay(),
					(this.krcdtMonAnyItemValuePk.getIsLastDay() == 1)),
				0, (this.timeValue102 == null ? Optional.empty() : Optional.of(new AnyTimeMonth(this.timeValue102))),
			(this.countValue102 == null ? Optional.empty() : Optional.of(new AnyTimesMonth(this.countValue102))),
			(this.moneyValue102 == null ? Optional.empty() : Optional.of(new AnyAmountMonth(this.moneyValue102))));
	}

	public AnyItemOfMonthly toDomainAnyItemOfMonthly103() {
		// get value anyItemId = 0 temporary because table have not anyitemId
		return AnyItemOfMonthly.of(this.krcdtMonAnyItemValuePk.getEmployeeId(),
				new YearMonth(this.krcdtMonAnyItemValuePk.getYearMonth()),
				EnumAdaptor.valueOf(this.krcdtMonAnyItemValuePk.getClosureId(), ClosureId.class),
				new ClosureDate(this.krcdtMonAnyItemValuePk.getClosureDay(),
					(this.krcdtMonAnyItemValuePk.getIsLastDay() == 1)),
				0, (this.timeValue103 == null ? Optional.empty() : Optional.of(new AnyTimeMonth(this.timeValue103))),
			(this.countValue103 == null ? Optional.empty() : Optional.of(new AnyTimesMonth(this.countValue103))),
			(this.moneyValue103 == null ? Optional.empty() : Optional.of(new AnyAmountMonth(this.moneyValue103))));
	}

	public AnyItemOfMonthly toDomainAnyItemOfMonthly104() {
		// get value anyItemId = 0 temporary because table have not anyitemId
		return AnyItemOfMonthly.of(this.krcdtMonAnyItemValuePk.getEmployeeId(),
				new YearMonth(this.krcdtMonAnyItemValuePk.getYearMonth()),
				EnumAdaptor.valueOf(this.krcdtMonAnyItemValuePk.getClosureId(), ClosureId.class),
				new ClosureDate(this.krcdtMonAnyItemValuePk.getClosureDay(),
					(this.krcdtMonAnyItemValuePk.getIsLastDay() == 1)),
				0, (this.timeValue104 == null ? Optional.empty() : Optional.of(new AnyTimeMonth(this.timeValue104))),
			(this.countValue104 == null ? Optional.empty() : Optional.of(new AnyTimesMonth(this.countValue104))),
			(this.moneyValue104 == null ? Optional.empty() : Optional.of(new AnyAmountMonth(this.moneyValue104))));
	}

	public AnyItemOfMonthly toDomainAnyItemOfMonthly105() {
		// get value anyItemId = 0 temporary because table have not anyitemId
		return AnyItemOfMonthly.of(this.krcdtMonAnyItemValuePk.getEmployeeId(),
				new YearMonth(this.krcdtMonAnyItemValuePk.getYearMonth()),
				EnumAdaptor.valueOf(this.krcdtMonAnyItemValuePk.getClosureId(), ClosureId.class),
				new ClosureDate(this.krcdtMonAnyItemValuePk.getClosureDay(),
					(this.krcdtMonAnyItemValuePk.getIsLastDay() == 1)),
				0, (this.timeValue105 == null ? Optional.empty() : Optional.of(new AnyTimeMonth(this.timeValue105))),
			(this.countValue105 == null ? Optional.empty() : Optional.of(new AnyTimesMonth(this.countValue105))),
			(this.moneyValue105 == null ? Optional.empty() : Optional.of(new AnyAmountMonth(this.moneyValue105))));
	}

	public AnyItemOfMonthly toDomainAnyItemOfMonthly106() {
		// get value anyItemId = 0 temporary because table have not anyitemId
		return AnyItemOfMonthly.of(this.krcdtMonAnyItemValuePk.getEmployeeId(),
				new YearMonth(this.krcdtMonAnyItemValuePk.getYearMonth()),
				EnumAdaptor.valueOf(this.krcdtMonAnyItemValuePk.getClosureId(), ClosureId.class),
				new ClosureDate(this.krcdtMonAnyItemValuePk.getClosureDay(),
					(this.krcdtMonAnyItemValuePk.getIsLastDay() == 1)),
				0, (this.timeValue106 == null ? Optional.empty() : Optional.of(new AnyTimeMonth(this.timeValue106))),
			(this.countValue106 == null ? Optional.empty() : Optional.of(new AnyTimesMonth(this.countValue106))),
			(this.moneyValue106 == null ? Optional.empty() : Optional.of(new AnyAmountMonth(this.moneyValue106))));
	}

	public AnyItemOfMonthly toDomainAnyItemOfMonthly107() {
		// get value anyItemId = 0 temporary because table have not anyitemId
		return AnyItemOfMonthly.of(this.krcdtMonAnyItemValuePk.getEmployeeId(),
				new YearMonth(this.krcdtMonAnyItemValuePk.getYearMonth()),
				EnumAdaptor.valueOf(this.krcdtMonAnyItemValuePk.getClosureId(), ClosureId.class),
				new ClosureDate(this.krcdtMonAnyItemValuePk.getClosureDay(),
					(this.krcdtMonAnyItemValuePk.getIsLastDay() == 1)),
				0, (this.timeValue107 == null ? Optional.empty() : Optional.of(new AnyTimeMonth(this.timeValue107))),
			(this.countValue107 == null ? Optional.empty() : Optional.of(new AnyTimesMonth(this.countValue107))),
			(this.moneyValue107 == null ? Optional.empty() : Optional.of(new AnyAmountMonth(this.moneyValue107))));
	}

	public AnyItemOfMonthly toDomainAnyItemOfMonthly108() {
		// get value anyItemId = 0 temporary because table have not anyitemId
		return AnyItemOfMonthly.of(this.krcdtMonAnyItemValuePk.getEmployeeId(),
				new YearMonth(this.krcdtMonAnyItemValuePk.getYearMonth()),
				EnumAdaptor.valueOf(this.krcdtMonAnyItemValuePk.getClosureId(), ClosureId.class),
				new ClosureDate(this.krcdtMonAnyItemValuePk.getClosureDay(),
					(this.krcdtMonAnyItemValuePk.getIsLastDay() == 1)),
				0, (this.timeValue108 == null ? Optional.empty() : Optional.of(new AnyTimeMonth(this.timeValue108))),
			(this.countValue108 == null ? Optional.empty() : Optional.of(new AnyTimesMonth(this.countValue108))),
			(this.moneyValue108 == null ? Optional.empty() : Optional.of(new AnyAmountMonth(this.moneyValue108))));
	}

	public AnyItemOfMonthly toDomainAnyItemOfMonthly109() {
		// get value anyItemId = 0 temporary because table have not anyitemId
		return AnyItemOfMonthly.of(this.krcdtMonAnyItemValuePk.getEmployeeId(),
				new YearMonth(this.krcdtMonAnyItemValuePk.getYearMonth()),
				EnumAdaptor.valueOf(this.krcdtMonAnyItemValuePk.getClosureId(), ClosureId.class),
				new ClosureDate(this.krcdtMonAnyItemValuePk.getClosureDay(),
					(this.krcdtMonAnyItemValuePk.getIsLastDay() == 1)),
				0, (this.timeValue109 == null ? Optional.empty() : Optional.of(new AnyTimeMonth(this.timeValue109))),
			(this.countValue109 == null ? Optional.empty() : Optional.of(new AnyTimesMonth(this.countValue109))),
			(this.moneyValue109 == null ? Optional.empty() : Optional.of(new AnyAmountMonth(this.moneyValue109))));
	}

	public AnyItemOfMonthly toDomainAnyItemOfMonthly110() {
		// get value anyItemId = 0 temporary because table have not anyitemId
		return AnyItemOfMonthly.of(this.krcdtMonAnyItemValuePk.getEmployeeId(),
				new YearMonth(this.krcdtMonAnyItemValuePk.getYearMonth()),
				EnumAdaptor.valueOf(this.krcdtMonAnyItemValuePk.getClosureId(), ClosureId.class),
				new ClosureDate(this.krcdtMonAnyItemValuePk.getClosureDay(),
					(this.krcdtMonAnyItemValuePk.getIsLastDay() == 1)),
				0, (this.timeValue110 == null ? Optional.empty() : Optional.of(new AnyTimeMonth(this.timeValue110))),
			(this.countValue110 == null ? Optional.empty() : Optional.of(new AnyTimesMonth(this.countValue110))),
			(this.moneyValue110 == null ? Optional.empty() : Optional.of(new AnyAmountMonth(this.moneyValue110))));
	}

	public AnyItemOfMonthly toDomainAnyItemOfMonthly111() {
		// get value anyItemId = 0 temporary because table have not anyitemId
		return AnyItemOfMonthly.of(this.krcdtMonAnyItemValuePk.getEmployeeId(),
				new YearMonth(this.krcdtMonAnyItemValuePk.getYearMonth()),
				EnumAdaptor.valueOf(this.krcdtMonAnyItemValuePk.getClosureId(), ClosureId.class),
				new ClosureDate(this.krcdtMonAnyItemValuePk.getClosureDay(),
					(this.krcdtMonAnyItemValuePk.getIsLastDay() == 1)),
				0, (this.timeValue111 == null ? Optional.empty() : Optional.of(new AnyTimeMonth(this.timeValue111))),
			(this.countValue111 == null ? Optional.empty() : Optional.of(new AnyTimesMonth(this.countValue111))),
			(this.moneyValue111 == null ? Optional.empty() : Optional.of(new AnyAmountMonth(this.moneyValue111))));
	}

	public AnyItemOfMonthly toDomainAnyItemOfMonthly112() {
		// get value anyItemId = 0 temporary because table have not anyitemId
		return AnyItemOfMonthly.of(this.krcdtMonAnyItemValuePk.getEmployeeId(),
				new YearMonth(this.krcdtMonAnyItemValuePk.getYearMonth()),
				EnumAdaptor.valueOf(this.krcdtMonAnyItemValuePk.getClosureId(), ClosureId.class),
				new ClosureDate(this.krcdtMonAnyItemValuePk.getClosureDay(),
					(this.krcdtMonAnyItemValuePk.getIsLastDay() == 1)),
				0, (this.timeValue112 == null ? Optional.empty() : Optional.of(new AnyTimeMonth(this.timeValue112))),
			(this.countValue112 == null ? Optional.empty() : Optional.of(new AnyTimesMonth(this.countValue112))),
			(this.moneyValue112 == null ? Optional.empty() : Optional.of(new AnyAmountMonth(this.moneyValue112))));
	}

	public AnyItemOfMonthly toDomainAnyItemOfMonthly113() {
		// get value anyItemId = 0 temporary because table have not anyitemId
		return AnyItemOfMonthly.of(this.krcdtMonAnyItemValuePk.getEmployeeId(),
				new YearMonth(this.krcdtMonAnyItemValuePk.getYearMonth()),
				EnumAdaptor.valueOf(this.krcdtMonAnyItemValuePk.getClosureId(), ClosureId.class),
				new ClosureDate(this.krcdtMonAnyItemValuePk.getClosureDay(),
					(this.krcdtMonAnyItemValuePk.getIsLastDay() == 1)),
				0, (this.timeValue113 == null ? Optional.empty() : Optional.of(new AnyTimeMonth(this.timeValue113))),
			(this.countValue113 == null ? Optional.empty() : Optional.of(new AnyTimesMonth(this.countValue113))),
			(this.moneyValue113 == null ? Optional.empty() : Optional.of(new AnyAmountMonth(this.moneyValue113))));
	}

	public AnyItemOfMonthly toDomainAnyItemOfMonthly114() {
		// get value anyItemId = 0 temporary because table have not anyitemId
		return AnyItemOfMonthly.of(this.krcdtMonAnyItemValuePk.getEmployeeId(),
				new YearMonth(this.krcdtMonAnyItemValuePk.getYearMonth()),
				EnumAdaptor.valueOf(this.krcdtMonAnyItemValuePk.getClosureId(), ClosureId.class),
				new ClosureDate(this.krcdtMonAnyItemValuePk.getClosureDay(),
					(this.krcdtMonAnyItemValuePk.getIsLastDay() == 1)),
				0, (this.timeValue114 == null ? Optional.empty() : Optional.of(new AnyTimeMonth(this.timeValue114))),
			(this.countValue114 == null ? Optional.empty() : Optional.of(new AnyTimesMonth(this.countValue114))),
			(this.moneyValue114 == null ? Optional.empty() : Optional.of(new AnyAmountMonth(this.moneyValue114))));
	}

	public AnyItemOfMonthly toDomainAnyItemOfMonthly115() {
		// get value anyItemId = 0 temporary because table have not anyitemId
		return AnyItemOfMonthly.of(this.krcdtMonAnyItemValuePk.getEmployeeId(),
				new YearMonth(this.krcdtMonAnyItemValuePk.getYearMonth()),
				EnumAdaptor.valueOf(this.krcdtMonAnyItemValuePk.getClosureId(), ClosureId.class),
				new ClosureDate(this.krcdtMonAnyItemValuePk.getClosureDay(),
					(this.krcdtMonAnyItemValuePk.getIsLastDay() == 1)),
				0, (this.timeValue115 == null ? Optional.empty() : Optional.of(new AnyTimeMonth(this.timeValue115))),
			(this.countValue115 == null ? Optional.empty() : Optional.of(new AnyTimesMonth(this.countValue115))),
			(this.moneyValue115 == null ? Optional.empty() : Optional.of(new AnyAmountMonth(this.moneyValue115))));
	}

	public AnyItemOfMonthly toDomainAnyItemOfMonthly116() {
		// get value anyItemId = 0 temporary because table have not anyitemId
		return AnyItemOfMonthly.of(this.krcdtMonAnyItemValuePk.getEmployeeId(),
				new YearMonth(this.krcdtMonAnyItemValuePk.getYearMonth()),
				EnumAdaptor.valueOf(this.krcdtMonAnyItemValuePk.getClosureId(), ClosureId.class),
				new ClosureDate(this.krcdtMonAnyItemValuePk.getClosureDay(),
					(this.krcdtMonAnyItemValuePk.getIsLastDay() == 1)),
				0, (this.timeValue116 == null ? Optional.empty() : Optional.of(new AnyTimeMonth(this.timeValue116))),
			(this.countValue116 == null ? Optional.empty() : Optional.of(new AnyTimesMonth(this.countValue116))),
			(this.moneyValue116 == null ? Optional.empty() : Optional.of(new AnyAmountMonth(this.moneyValue116))));
	}

	public AnyItemOfMonthly toDomainAnyItemOfMonthly117() {
		// get value anyItemId = 0 temporary because table have not anyitemId
		return AnyItemOfMonthly.of(this.krcdtMonAnyItemValuePk.getEmployeeId(),
				new YearMonth(this.krcdtMonAnyItemValuePk.getYearMonth()),
				EnumAdaptor.valueOf(this.krcdtMonAnyItemValuePk.getClosureId(), ClosureId.class),
				new ClosureDate(this.krcdtMonAnyItemValuePk.getClosureDay(),
					(this.krcdtMonAnyItemValuePk.getIsLastDay() == 1)),
				0, (this.timeValue117 == null ? Optional.empty() : Optional.of(new AnyTimeMonth(this.timeValue117))),
			(this.countValue117 == null ? Optional.empty() : Optional.of(new AnyTimesMonth(this.countValue117))),
			(this.moneyValue117 == null ? Optional.empty() : Optional.of(new AnyAmountMonth(this.moneyValue117))));
	}

	public AnyItemOfMonthly toDomainAnyItemOfMonthly118() {
		// get value anyItemId = 0 temporary because table have not anyitemId
		return AnyItemOfMonthly.of(this.krcdtMonAnyItemValuePk.getEmployeeId(),
				new YearMonth(this.krcdtMonAnyItemValuePk.getYearMonth()),
				EnumAdaptor.valueOf(this.krcdtMonAnyItemValuePk.getClosureId(), ClosureId.class),
				new ClosureDate(this.krcdtMonAnyItemValuePk.getClosureDay(),
					(this.krcdtMonAnyItemValuePk.getIsLastDay() == 1)),
				0, (this.timeValue118 == null ? Optional.empty() : Optional.of(new AnyTimeMonth(this.timeValue118))),
			(this.countValue118 == null ? Optional.empty() : Optional.of(new AnyTimesMonth(this.countValue118))),
			(this.moneyValue118 == null ? Optional.empty() : Optional.of(new AnyAmountMonth(this.moneyValue118))));
	}

	public AnyItemOfMonthly toDomainAnyItemOfMonthly119() {
		// get value anyItemId = 0 temporary because table have not anyitemId
		return AnyItemOfMonthly.of(this.krcdtMonAnyItemValuePk.getEmployeeId(),
				new YearMonth(this.krcdtMonAnyItemValuePk.getYearMonth()),
				EnumAdaptor.valueOf(this.krcdtMonAnyItemValuePk.getClosureId(), ClosureId.class),
				new ClosureDate(this.krcdtMonAnyItemValuePk.getClosureDay(),
					(this.krcdtMonAnyItemValuePk.getIsLastDay() == 1)),
				0, (this.timeValue119 == null ? Optional.empty() : Optional.of(new AnyTimeMonth(this.timeValue119))),
			(this.countValue119 == null ? Optional.empty() : Optional.of(new AnyTimesMonth(this.countValue119))),
			(this.moneyValue119 == null ? Optional.empty() : Optional.of(new AnyAmountMonth(this.moneyValue119))));
	}

	public AnyItemOfMonthly toDomainAnyItemOfMonthly120() {
		// get value anyItemId = 0 temporary because table have not anyitemId
		return AnyItemOfMonthly.of(this.krcdtMonAnyItemValuePk.getEmployeeId(),
				new YearMonth(this.krcdtMonAnyItemValuePk.getYearMonth()),
				EnumAdaptor.valueOf(this.krcdtMonAnyItemValuePk.getClosureId(), ClosureId.class),
				new ClosureDate(this.krcdtMonAnyItemValuePk.getClosureDay(),
					(this.krcdtMonAnyItemValuePk.getIsLastDay() == 1)),
				0, (this.timeValue120 == null ? Optional.empty() : Optional.of(new AnyTimeMonth(this.timeValue120))),
			(this.countValue120 == null ? Optional.empty() : Optional.of(new AnyTimesMonth(this.countValue120))),
			(this.moneyValue120 == null ? Optional.empty() : Optional.of(new AnyAmountMonth(this.moneyValue120))));
	}

	public AnyItemOfMonthly toDomainAnyItemOfMonthly121() {
		// get value anyItemId = 0 temporary because table have not anyitemId
		return AnyItemOfMonthly.of(this.krcdtMonAnyItemValuePk.getEmployeeId(),
				new YearMonth(this.krcdtMonAnyItemValuePk.getYearMonth()),
				EnumAdaptor.valueOf(this.krcdtMonAnyItemValuePk.getClosureId(), ClosureId.class),
				new ClosureDate(this.krcdtMonAnyItemValuePk.getClosureDay(),
					(this.krcdtMonAnyItemValuePk.getIsLastDay() == 1)),
				0, (this.timeValue121 == null ? Optional.empty() : Optional.of(new AnyTimeMonth(this.timeValue121))),
			(this.countValue121 == null ? Optional.empty() : Optional.of(new AnyTimesMonth(this.countValue121))),
			(this.moneyValue121 == null ? Optional.empty() : Optional.of(new AnyAmountMonth(this.moneyValue121))));
	}

	public AnyItemOfMonthly toDomainAnyItemOfMonthly122() {
		// get value anyItemId = 0 temporary because table have not anyitemId
		return AnyItemOfMonthly.of(this.krcdtMonAnyItemValuePk.getEmployeeId(),
				new YearMonth(this.krcdtMonAnyItemValuePk.getYearMonth()),
				EnumAdaptor.valueOf(this.krcdtMonAnyItemValuePk.getClosureId(), ClosureId.class),
				new ClosureDate(this.krcdtMonAnyItemValuePk.getClosureDay(),
					(this.krcdtMonAnyItemValuePk.getIsLastDay() == 1)),
				0, (this.timeValue122 == null ? Optional.empty() : Optional.of(new AnyTimeMonth(this.timeValue122))),
			(this.countValue122 == null ? Optional.empty() : Optional.of(new AnyTimesMonth(this.countValue122))),
			(this.moneyValue122 == null ? Optional.empty() : Optional.of(new AnyAmountMonth(this.moneyValue122))));
	}

	public AnyItemOfMonthly toDomainAnyItemOfMonthly123() {
		// get value anyItemId = 0 temporary because table have not anyitemId
		return AnyItemOfMonthly.of(this.krcdtMonAnyItemValuePk.getEmployeeId(),
				new YearMonth(this.krcdtMonAnyItemValuePk.getYearMonth()),
				EnumAdaptor.valueOf(this.krcdtMonAnyItemValuePk.getClosureId(), ClosureId.class),
				new ClosureDate(this.krcdtMonAnyItemValuePk.getClosureDay(),
					(this.krcdtMonAnyItemValuePk.getIsLastDay() == 1)),
				0, (this.timeValue123 == null ? Optional.empty() : Optional.of(new AnyTimeMonth(this.timeValue123))),
			(this.countValue123 == null ? Optional.empty() : Optional.of(new AnyTimesMonth(this.countValue123))),
			(this.moneyValue123 == null ? Optional.empty() : Optional.of(new AnyAmountMonth(this.moneyValue123))));
	}

	public AnyItemOfMonthly toDomainAnyItemOfMonthly124() {
		// get value anyItemId = 0 temporary because table have not anyitemId
		return AnyItemOfMonthly.of(this.krcdtMonAnyItemValuePk.getEmployeeId(),
				new YearMonth(this.krcdtMonAnyItemValuePk.getYearMonth()),
				EnumAdaptor.valueOf(this.krcdtMonAnyItemValuePk.getClosureId(), ClosureId.class),
				new ClosureDate(this.krcdtMonAnyItemValuePk.getClosureDay(),
					(this.krcdtMonAnyItemValuePk.getIsLastDay() == 1)),
				0, (this.timeValue124 == null ? Optional.empty() : Optional.of(new AnyTimeMonth(this.timeValue124))),
			(this.countValue124 == null ? Optional.empty() : Optional.of(new AnyTimesMonth(this.countValue124))),
			(this.moneyValue124 == null ? Optional.empty() : Optional.of(new AnyAmountMonth(this.moneyValue124))));
	}

	public AnyItemOfMonthly toDomainAnyItemOfMonthly125() {
		// get value anyItemId = 0 temporary because table have not anyitemId
		return AnyItemOfMonthly.of(this.krcdtMonAnyItemValuePk.getEmployeeId(),
				new YearMonth(this.krcdtMonAnyItemValuePk.getYearMonth()),
				EnumAdaptor.valueOf(this.krcdtMonAnyItemValuePk.getClosureId(), ClosureId.class),
				new ClosureDate(this.krcdtMonAnyItemValuePk.getClosureDay(),
					(this.krcdtMonAnyItemValuePk.getIsLastDay() == 1)),
				0, (this.timeValue125 == null ? Optional.empty() : Optional.of(new AnyTimeMonth(this.timeValue125))),
			(this.countValue125 == null ? Optional.empty() : Optional.of(new AnyTimesMonth(this.countValue125))),
			(this.moneyValue125 == null ? Optional.empty() : Optional.of(new AnyAmountMonth(this.moneyValue125))));
	}

	public AnyItemOfMonthly toDomainAnyItemOfMonthly126() {
		// get value anyItemId = 0 temporary because table have not anyitemId
		return AnyItemOfMonthly.of(this.krcdtMonAnyItemValuePk.getEmployeeId(),
				new YearMonth(this.krcdtMonAnyItemValuePk.getYearMonth()),
				EnumAdaptor.valueOf(this.krcdtMonAnyItemValuePk.getClosureId(), ClosureId.class),
				new ClosureDate(this.krcdtMonAnyItemValuePk.getClosureDay(),
					(this.krcdtMonAnyItemValuePk.getIsLastDay() == 1)),
				0, (this.timeValue126 == null ? Optional.empty() : Optional.of(new AnyTimeMonth(this.timeValue126))),
			(this.countValue126 == null ? Optional.empty() : Optional.of(new AnyTimesMonth(this.countValue126))),
			(this.moneyValue126 == null ? Optional.empty() : Optional.of(new AnyAmountMonth(this.moneyValue126))));
	}

	public AnyItemOfMonthly toDomainAnyItemOfMonthly127() {
		// get value anyItemId = 0 temporary because table have not anyitemId
		return AnyItemOfMonthly.of(this.krcdtMonAnyItemValuePk.getEmployeeId(),
				new YearMonth(this.krcdtMonAnyItemValuePk.getYearMonth()),
				EnumAdaptor.valueOf(this.krcdtMonAnyItemValuePk.getClosureId(), ClosureId.class),
				new ClosureDate(this.krcdtMonAnyItemValuePk.getClosureDay(),
					(this.krcdtMonAnyItemValuePk.getIsLastDay() == 1)),
				0, (this.timeValue127 == null ? Optional.empty() : Optional.of(new AnyTimeMonth(this.timeValue127))),
			(this.countValue127 == null ? Optional.empty() : Optional.of(new AnyTimesMonth(this.countValue127))),
			(this.moneyValue127 == null ? Optional.empty() : Optional.of(new AnyAmountMonth(this.moneyValue127))));
	}

	public AnyItemOfMonthly toDomainAnyItemOfMonthly128() {
		// get value anyItemId = 0 temporary because table have not anyitemId
		return AnyItemOfMonthly.of(this.krcdtMonAnyItemValuePk.getEmployeeId(),
				new YearMonth(this.krcdtMonAnyItemValuePk.getYearMonth()),
				EnumAdaptor.valueOf(this.krcdtMonAnyItemValuePk.getClosureId(), ClosureId.class),
				new ClosureDate(this.krcdtMonAnyItemValuePk.getClosureDay(),
					(this.krcdtMonAnyItemValuePk.getIsLastDay() == 1)),
				0, (this.timeValue128 == null ? Optional.empty() : Optional.of(new AnyTimeMonth(this.timeValue128))),
			(this.countValue128 == null ? Optional.empty() : Optional.of(new AnyTimesMonth(this.countValue128))),
			(this.moneyValue128 == null ? Optional.empty() : Optional.of(new AnyAmountMonth(this.moneyValue128))));
	}

	public AnyItemOfMonthly toDomainAnyItemOfMonthly129() {
		// get value anyItemId = 0 temporary because table have not anyitemId
		return AnyItemOfMonthly.of(this.krcdtMonAnyItemValuePk.getEmployeeId(),
				new YearMonth(this.krcdtMonAnyItemValuePk.getYearMonth()),
				EnumAdaptor.valueOf(this.krcdtMonAnyItemValuePk.getClosureId(), ClosureId.class),
				new ClosureDate(this.krcdtMonAnyItemValuePk.getClosureDay(),
					(this.krcdtMonAnyItemValuePk.getIsLastDay() == 1)),
				0, (this.timeValue129 == null ? Optional.empty() : Optional.of(new AnyTimeMonth(this.timeValue129))),
			(this.countValue129 == null ? Optional.empty() : Optional.of(new AnyTimesMonth(this.countValue129))),
			(this.moneyValue129 == null ? Optional.empty() : Optional.of(new AnyAmountMonth(this.moneyValue129))));
	}

	public AnyItemOfMonthly toDomainAnyItemOfMonthly130() {
		// get value anyItemId = 0 temporary because table have not anyitemId
		return AnyItemOfMonthly.of(this.krcdtMonAnyItemValuePk.getEmployeeId(),
				new YearMonth(this.krcdtMonAnyItemValuePk.getYearMonth()),
				EnumAdaptor.valueOf(this.krcdtMonAnyItemValuePk.getClosureId(), ClosureId.class),
				new ClosureDate(this.krcdtMonAnyItemValuePk.getClosureDay(),
					(this.krcdtMonAnyItemValuePk.getIsLastDay() == 1)),
				0, (this.timeValue130 == null ? Optional.empty() : Optional.of(new AnyTimeMonth(this.timeValue130))),
			(this.countValue130 == null ? Optional.empty() : Optional.of(new AnyTimesMonth(this.countValue130))),
			(this.moneyValue130 == null ? Optional.empty() : Optional.of(new AnyAmountMonth(this.moneyValue130))));
	}

	public AnyItemOfMonthly toDomainAnyItemOfMonthly131() {
		// get value anyItemId = 0 temporary because table have not anyitemId
		return AnyItemOfMonthly.of(this.krcdtMonAnyItemValuePk.getEmployeeId(),
				new YearMonth(this.krcdtMonAnyItemValuePk.getYearMonth()),
				EnumAdaptor.valueOf(this.krcdtMonAnyItemValuePk.getClosureId(), ClosureId.class),
				new ClosureDate(this.krcdtMonAnyItemValuePk.getClosureDay(),
					(this.krcdtMonAnyItemValuePk.getIsLastDay() == 1)),
				0, (this.timeValue131 == null ? Optional.empty() : Optional.of(new AnyTimeMonth(this.timeValue131))),
			(this.countValue131 == null ? Optional.empty() : Optional.of(new AnyTimesMonth(this.countValue131))),
			(this.moneyValue131 == null ? Optional.empty() : Optional.of(new AnyAmountMonth(this.moneyValue131))));
	}

	public AnyItemOfMonthly toDomainAnyItemOfMonthly132() {
		// get value anyItemId = 0 temporary because table have not anyitemId
		return AnyItemOfMonthly.of(this.krcdtMonAnyItemValuePk.getEmployeeId(),
				new YearMonth(this.krcdtMonAnyItemValuePk.getYearMonth()),
				EnumAdaptor.valueOf(this.krcdtMonAnyItemValuePk.getClosureId(), ClosureId.class),
				new ClosureDate(this.krcdtMonAnyItemValuePk.getClosureDay(),
					(this.krcdtMonAnyItemValuePk.getIsLastDay() == 1)),
				0, (this.timeValue132 == null ? Optional.empty() : Optional.of(new AnyTimeMonth(this.timeValue132))),
			(this.countValue132 == null ? Optional.empty() : Optional.of(new AnyTimesMonth(this.countValue132))),
			(this.moneyValue132 == null ? Optional.empty() : Optional.of(new AnyAmountMonth(this.moneyValue132))));
	}

	public AnyItemOfMonthly toDomainAnyItemOfMonthly133() {
		// get value anyItemId = 0 temporary because table have not anyitemId
		return AnyItemOfMonthly.of(this.krcdtMonAnyItemValuePk.getEmployeeId(),
				new YearMonth(this.krcdtMonAnyItemValuePk.getYearMonth()),
				EnumAdaptor.valueOf(this.krcdtMonAnyItemValuePk.getClosureId(), ClosureId.class),
				new ClosureDate(this.krcdtMonAnyItemValuePk.getClosureDay(),
					(this.krcdtMonAnyItemValuePk.getIsLastDay() == 1)),
				0, (this.timeValue133 == null ? Optional.empty() : Optional.of(new AnyTimeMonth(this.timeValue133))),
			(this.countValue133 == null ? Optional.empty() : Optional.of(new AnyTimesMonth(this.countValue133))),
			(this.moneyValue133 == null ? Optional.empty() : Optional.of(new AnyAmountMonth(this.moneyValue133))));
	}

	public AnyItemOfMonthly toDomainAnyItemOfMonthly134() {
		// get value anyItemId = 0 temporary because table have not anyitemId
		return AnyItemOfMonthly.of(this.krcdtMonAnyItemValuePk.getEmployeeId(),
				new YearMonth(this.krcdtMonAnyItemValuePk.getYearMonth()),
				EnumAdaptor.valueOf(this.krcdtMonAnyItemValuePk.getClosureId(), ClosureId.class),
				new ClosureDate(this.krcdtMonAnyItemValuePk.getClosureDay(),
					(this.krcdtMonAnyItemValuePk.getIsLastDay() == 1)),
				0, (this.timeValue134 == null ? Optional.empty() : Optional.of(new AnyTimeMonth(this.timeValue134))),
			(this.countValue134 == null ? Optional.empty() : Optional.of(new AnyTimesMonth(this.countValue134))),
			(this.moneyValue134 == null ? Optional.empty() : Optional.of(new AnyAmountMonth(this.moneyValue134))));
	}

	public AnyItemOfMonthly toDomainAnyItemOfMonthly135() {
		// get value anyItemId = 0 temporary because table have not anyitemId
		return AnyItemOfMonthly.of(this.krcdtMonAnyItemValuePk.getEmployeeId(),
				new YearMonth(this.krcdtMonAnyItemValuePk.getYearMonth()),
				EnumAdaptor.valueOf(this.krcdtMonAnyItemValuePk.getClosureId(), ClosureId.class),
				new ClosureDate(this.krcdtMonAnyItemValuePk.getClosureDay(),
					(this.krcdtMonAnyItemValuePk.getIsLastDay() == 1)),
				0, (this.timeValue135 == null ? Optional.empty() : Optional.of(new AnyTimeMonth(this.timeValue135))),
			(this.countValue135 == null ? Optional.empty() : Optional.of(new AnyTimesMonth(this.countValue135))),
			(this.moneyValue135 == null ? Optional.empty() : Optional.of(new AnyAmountMonth(this.moneyValue135))));
	}

	public AnyItemOfMonthly toDomainAnyItemOfMonthly136() {
		// get value anyItemId = 0 temporary because table have not anyitemId
		return AnyItemOfMonthly.of(this.krcdtMonAnyItemValuePk.getEmployeeId(),
				new YearMonth(this.krcdtMonAnyItemValuePk.getYearMonth()),
				EnumAdaptor.valueOf(this.krcdtMonAnyItemValuePk.getClosureId(), ClosureId.class),
				new ClosureDate(this.krcdtMonAnyItemValuePk.getClosureDay(),
					(this.krcdtMonAnyItemValuePk.getIsLastDay() == 1)),
				0, (this.timeValue136 == null ? Optional.empty() : Optional.of(new AnyTimeMonth(this.timeValue136))),
			(this.countValue136 == null ? Optional.empty() : Optional.of(new AnyTimesMonth(this.countValue136))),
			(this.moneyValue136 == null ? Optional.empty() : Optional.of(new AnyAmountMonth(this.moneyValue136))));
	}

	public AnyItemOfMonthly toDomainAnyItemOfMonthly137() {
		// get value anyItemId = 0 temporary because table have not anyitemId
		return AnyItemOfMonthly.of(this.krcdtMonAnyItemValuePk.getEmployeeId(),
				new YearMonth(this.krcdtMonAnyItemValuePk.getYearMonth()),
				EnumAdaptor.valueOf(this.krcdtMonAnyItemValuePk.getClosureId(), ClosureId.class),
				new ClosureDate(this.krcdtMonAnyItemValuePk.getClosureDay(),
					(this.krcdtMonAnyItemValuePk.getIsLastDay() == 1)),
				0, (this.timeValue137 == null ? Optional.empty() : Optional.of(new AnyTimeMonth(this.timeValue137))),
			(this.countValue137 == null ? Optional.empty() : Optional.of(new AnyTimesMonth(this.countValue137))),
			(this.moneyValue137 == null ? Optional.empty() : Optional.of(new AnyAmountMonth(this.moneyValue137))));
	}

	public AnyItemOfMonthly toDomainAnyItemOfMonthly138() {
		// get value anyItemId = 0 temporary because table have not anyitemId
		return AnyItemOfMonthly.of(this.krcdtMonAnyItemValuePk.getEmployeeId(),
				new YearMonth(this.krcdtMonAnyItemValuePk.getYearMonth()),
				EnumAdaptor.valueOf(this.krcdtMonAnyItemValuePk.getClosureId(), ClosureId.class),
				new ClosureDate(this.krcdtMonAnyItemValuePk.getClosureDay(),
					(this.krcdtMonAnyItemValuePk.getIsLastDay() == 1)),
				0, (this.timeValue138 == null ? Optional.empty() : Optional.of(new AnyTimeMonth(this.timeValue138))),
			(this.countValue138 == null ? Optional.empty() : Optional.of(new AnyTimesMonth(this.countValue138))),
			(this.moneyValue138 == null ? Optional.empty() : Optional.of(new AnyAmountMonth(this.moneyValue138))));
	}

	public AnyItemOfMonthly toDomainAnyItemOfMonthly139() {
		// get value anyItemId = 0 temporary because table have not anyitemId
		return AnyItemOfMonthly.of(this.krcdtMonAnyItemValuePk.getEmployeeId(),
				new YearMonth(this.krcdtMonAnyItemValuePk.getYearMonth()),
				EnumAdaptor.valueOf(this.krcdtMonAnyItemValuePk.getClosureId(), ClosureId.class),
				new ClosureDate(this.krcdtMonAnyItemValuePk.getClosureDay(),
					(this.krcdtMonAnyItemValuePk.getIsLastDay() == 1)),
				0, (this.timeValue139 == null ? Optional.empty() : Optional.of(new AnyTimeMonth(this.timeValue139))),
			(this.countValue139 == null ? Optional.empty() : Optional.of(new AnyTimesMonth(this.countValue139))),
			(this.moneyValue139 == null ? Optional.empty() : Optional.of(new AnyAmountMonth(this.moneyValue139))));
	}

	public AnyItemOfMonthly toDomainAnyItemOfMonthly140() {
		// get value anyItemId = 0 temporary because table have not anyitemId
		return AnyItemOfMonthly.of(this.krcdtMonAnyItemValuePk.getEmployeeId(),
				new YearMonth(this.krcdtMonAnyItemValuePk.getYearMonth()),
				EnumAdaptor.valueOf(this.krcdtMonAnyItemValuePk.getClosureId(), ClosureId.class),
				new ClosureDate(this.krcdtMonAnyItemValuePk.getClosureDay(),
					(this.krcdtMonAnyItemValuePk.getIsLastDay() == 1)),
				0, (this.timeValue140 == null ? Optional.empty() : Optional.of(new AnyTimeMonth(this.timeValue140))),
			(this.countValue140 == null ? Optional.empty() : Optional.of(new AnyTimesMonth(this.countValue140))),
			(this.moneyValue140 == null ? Optional.empty() : Optional.of(new AnyAmountMonth(this.moneyValue140))));
	}

	public AnyItemOfMonthly toDomainAnyItemOfMonthly141() {
		// get value anyItemId = 0 temporary because table have not anyitemId
		return AnyItemOfMonthly.of(this.krcdtMonAnyItemValuePk.getEmployeeId(),
				new YearMonth(this.krcdtMonAnyItemValuePk.getYearMonth()),
				EnumAdaptor.valueOf(this.krcdtMonAnyItemValuePk.getClosureId(), ClosureId.class),
				new ClosureDate(this.krcdtMonAnyItemValuePk.getClosureDay(),
					(this.krcdtMonAnyItemValuePk.getIsLastDay() == 1)),
				0, (this.timeValue141 == null ? Optional.empty() : Optional.of(new AnyTimeMonth(this.timeValue141))),
			(this.countValue141 == null ? Optional.empty() : Optional.of(new AnyTimesMonth(this.countValue141))),
			(this.moneyValue141 == null ? Optional.empty() : Optional.of(new AnyAmountMonth(this.moneyValue141))));
	}

	public AnyItemOfMonthly toDomainAnyItemOfMonthly142() {
		// get value anyItemId = 0 temporary because table have not anyitemId
		return AnyItemOfMonthly.of(this.krcdtMonAnyItemValuePk.getEmployeeId(),
				new YearMonth(this.krcdtMonAnyItemValuePk.getYearMonth()),
				EnumAdaptor.valueOf(this.krcdtMonAnyItemValuePk.getClosureId(), ClosureId.class),
				new ClosureDate(this.krcdtMonAnyItemValuePk.getClosureDay(),
					(this.krcdtMonAnyItemValuePk.getIsLastDay() == 1)),
				0, (this.timeValue142 == null ? Optional.empty() : Optional.of(new AnyTimeMonth(this.timeValue142))),
			(this.countValue142 == null ? Optional.empty() : Optional.of(new AnyTimesMonth(this.countValue142))),
			(this.moneyValue142 == null ? Optional.empty() : Optional.of(new AnyAmountMonth(this.moneyValue142))));
	}

	public AnyItemOfMonthly toDomainAnyItemOfMonthly143() {
		// get value anyItemId = 0 temporary because table have not anyitemId
		return AnyItemOfMonthly.of(this.krcdtMonAnyItemValuePk.getEmployeeId(),
				new YearMonth(this.krcdtMonAnyItemValuePk.getYearMonth()),
				EnumAdaptor.valueOf(this.krcdtMonAnyItemValuePk.getClosureId(), ClosureId.class),
				new ClosureDate(this.krcdtMonAnyItemValuePk.getClosureDay(),
					(this.krcdtMonAnyItemValuePk.getIsLastDay() == 1)),
				0, (this.timeValue143 == null ? Optional.empty() : Optional.of(new AnyTimeMonth(this.timeValue143))),
			(this.countValue143 == null ? Optional.empty() : Optional.of(new AnyTimesMonth(this.countValue143))),
			(this.moneyValue143 == null ? Optional.empty() : Optional.of(new AnyAmountMonth(this.moneyValue143))));
	}

	public AnyItemOfMonthly toDomainAnyItemOfMonthly144() {
		// get value anyItemId = 0 temporary because table have not anyitemId
		return AnyItemOfMonthly.of(this.krcdtMonAnyItemValuePk.getEmployeeId(),
				new YearMonth(this.krcdtMonAnyItemValuePk.getYearMonth()),
				EnumAdaptor.valueOf(this.krcdtMonAnyItemValuePk.getClosureId(), ClosureId.class),
				new ClosureDate(this.krcdtMonAnyItemValuePk.getClosureDay(),
					(this.krcdtMonAnyItemValuePk.getIsLastDay() == 1)),
				0, (this.timeValue144 == null ? Optional.empty() : Optional.of(new AnyTimeMonth(this.timeValue144))),
			(this.countValue144 == null ? Optional.empty() : Optional.of(new AnyTimesMonth(this.countValue144))),
			(this.moneyValue144 == null ? Optional.empty() : Optional.of(new AnyAmountMonth(this.moneyValue144))));
	}

	public AnyItemOfMonthly toDomainAnyItemOfMonthly145() {
		// get value anyItemId = 0 temporary because table have not anyitemId
		return AnyItemOfMonthly.of(this.krcdtMonAnyItemValuePk.getEmployeeId(),
				new YearMonth(this.krcdtMonAnyItemValuePk.getYearMonth()),
				EnumAdaptor.valueOf(this.krcdtMonAnyItemValuePk.getClosureId(), ClosureId.class),
				new ClosureDate(this.krcdtMonAnyItemValuePk.getClosureDay(),
					(this.krcdtMonAnyItemValuePk.getIsLastDay() == 1)),
				0, (this.timeValue145 == null ? Optional.empty() : Optional.of(new AnyTimeMonth(this.timeValue145))),
			(this.countValue145 == null ? Optional.empty() : Optional.of(new AnyTimesMonth(this.countValue145))),
			(this.moneyValue145 == null ? Optional.empty() : Optional.of(new AnyAmountMonth(this.moneyValue145))));
	}

	public AnyItemOfMonthly toDomainAnyItemOfMonthly146() {
		// get value anyItemId = 0 temporary because table have not anyitemId
		return AnyItemOfMonthly.of(this.krcdtMonAnyItemValuePk.getEmployeeId(),
				new YearMonth(this.krcdtMonAnyItemValuePk.getYearMonth()),
				EnumAdaptor.valueOf(this.krcdtMonAnyItemValuePk.getClosureId(), ClosureId.class),
				new ClosureDate(this.krcdtMonAnyItemValuePk.getClosureDay(),
					(this.krcdtMonAnyItemValuePk.getIsLastDay() == 1)),
				0, (this.timeValue146 == null ? Optional.empty() : Optional.of(new AnyTimeMonth(this.timeValue146))),
			(this.countValue146 == null ? Optional.empty() : Optional.of(new AnyTimesMonth(this.countValue146))),
			(this.moneyValue146 == null ? Optional.empty() : Optional.of(new AnyAmountMonth(this.moneyValue146))));
	}

	public AnyItemOfMonthly toDomainAnyItemOfMonthly147() {
		// get value anyItemId = 0 temporary because table have not anyitemId
		return AnyItemOfMonthly.of(this.krcdtMonAnyItemValuePk.getEmployeeId(),
				new YearMonth(this.krcdtMonAnyItemValuePk.getYearMonth()),
				EnumAdaptor.valueOf(this.krcdtMonAnyItemValuePk.getClosureId(), ClosureId.class),
				new ClosureDate(this.krcdtMonAnyItemValuePk.getClosureDay(),
					(this.krcdtMonAnyItemValuePk.getIsLastDay() == 1)),
				0, (this.timeValue147 == null ? Optional.empty() : Optional.of(new AnyTimeMonth(this.timeValue147))),
			(this.countValue147 == null ? Optional.empty() : Optional.of(new AnyTimesMonth(this.countValue147))),
			(this.moneyValue147 == null ? Optional.empty() : Optional.of(new AnyAmountMonth(this.moneyValue147))));
	}

	public AnyItemOfMonthly toDomainAnyItemOfMonthly148() {
		// get value anyItemId = 0 temporary because table have not anyitemId
		return AnyItemOfMonthly.of(this.krcdtMonAnyItemValuePk.getEmployeeId(),
				new YearMonth(this.krcdtMonAnyItemValuePk.getYearMonth()),
				EnumAdaptor.valueOf(this.krcdtMonAnyItemValuePk.getClosureId(), ClosureId.class),
				new ClosureDate(this.krcdtMonAnyItemValuePk.getClosureDay(),
					(this.krcdtMonAnyItemValuePk.getIsLastDay() == 1)),
				0, (this.timeValue148 == null ? Optional.empty() : Optional.of(new AnyTimeMonth(this.timeValue148))),
			(this.countValue148 == null ? Optional.empty() : Optional.of(new AnyTimesMonth(this.countValue148))),
			(this.moneyValue148 == null ? Optional.empty() : Optional.of(new AnyAmountMonth(this.moneyValue148))));
	}

	public AnyItemOfMonthly toDomainAnyItemOfMonthly149() {
		// get value anyItemId = 0 temporary because table have not anyitemId
		return AnyItemOfMonthly.of(this.krcdtMonAnyItemValuePk.getEmployeeId(),
				new YearMonth(this.krcdtMonAnyItemValuePk.getYearMonth()),
				EnumAdaptor.valueOf(this.krcdtMonAnyItemValuePk.getClosureId(), ClosureId.class),
				new ClosureDate(this.krcdtMonAnyItemValuePk.getClosureDay(),
					(this.krcdtMonAnyItemValuePk.getIsLastDay() == 1)),
				0, (this.timeValue149 == null ? Optional.empty() : Optional.of(new AnyTimeMonth(this.timeValue149))),
			(this.countValue149 == null ? Optional.empty() : Optional.of(new AnyTimesMonth(this.countValue149))),
			(this.moneyValue149 == null ? Optional.empty() : Optional.of(new AnyAmountMonth(this.moneyValue149))));
	}

	public AnyItemOfMonthly toDomainAnyItemOfMonthly150() {
		// get value anyItemId = 0 temporary because table have not anyitemId
		return AnyItemOfMonthly.of(this.krcdtMonAnyItemValuePk.getEmployeeId(),
				new YearMonth(this.krcdtMonAnyItemValuePk.getYearMonth()),
				EnumAdaptor.valueOf(this.krcdtMonAnyItemValuePk.getClosureId(), ClosureId.class),
				new ClosureDate(this.krcdtMonAnyItemValuePk.getClosureDay(),
					(this.krcdtMonAnyItemValuePk.getIsLastDay() == 1)),
				0, (this.timeValue150 == null ? Optional.empty() : Optional.of(new AnyTimeMonth(this.timeValue150))),
			(this.countValue150 == null ? Optional.empty() : Optional.of(new AnyTimesMonth(this.countValue150))),
			(this.moneyValue150 == null ? Optional.empty() : Optional.of(new AnyAmountMonth(this.moneyValue150))));
	}

	public AnyItemOfMonthly toDomainAnyItemOfMonthly151() {
		// get value anyItemId = 0 temporary because table have not anyitemId
		return AnyItemOfMonthly.of(this.krcdtMonAnyItemValuePk.getEmployeeId(),
				new YearMonth(this.krcdtMonAnyItemValuePk.getYearMonth()),
				EnumAdaptor.valueOf(this.krcdtMonAnyItemValuePk.getClosureId(), ClosureId.class),
				new ClosureDate(this.krcdtMonAnyItemValuePk.getClosureDay(),
					(this.krcdtMonAnyItemValuePk.getIsLastDay() == 1)),
				0, (this.timeValue151 == null ? Optional.empty() : Optional.of(new AnyTimeMonth(this.timeValue151))),
			(this.countValue151 == null ? Optional.empty() : Optional.of(new AnyTimesMonth(this.countValue151))),
			(this.moneyValue151 == null ? Optional.empty() : Optional.of(new AnyAmountMonth(this.moneyValue151))));
	}

	public AnyItemOfMonthly toDomainAnyItemOfMonthly152() {
		// get value anyItemId = 0 temporary because table have not anyitemId
		return AnyItemOfMonthly.of(this.krcdtMonAnyItemValuePk.getEmployeeId(),
				new YearMonth(this.krcdtMonAnyItemValuePk.getYearMonth()),
				EnumAdaptor.valueOf(this.krcdtMonAnyItemValuePk.getClosureId(), ClosureId.class),
				new ClosureDate(this.krcdtMonAnyItemValuePk.getClosureDay(),
					(this.krcdtMonAnyItemValuePk.getIsLastDay() == 1)),
				0, (this.timeValue152 == null ? Optional.empty() : Optional.of(new AnyTimeMonth(this.timeValue152))),
			(this.countValue152 == null ? Optional.empty() : Optional.of(new AnyTimesMonth(this.countValue152))),
			(this.moneyValue152 == null ? Optional.empty() : Optional.of(new AnyAmountMonth(this.moneyValue152))));
	}

	public AnyItemOfMonthly toDomainAnyItemOfMonthly153() {
		// get value anyItemId = 0 temporary because table have not anyitemId
		return AnyItemOfMonthly.of(this.krcdtMonAnyItemValuePk.getEmployeeId(),
				new YearMonth(this.krcdtMonAnyItemValuePk.getYearMonth()),
				EnumAdaptor.valueOf(this.krcdtMonAnyItemValuePk.getClosureId(), ClosureId.class),
				new ClosureDate(this.krcdtMonAnyItemValuePk.getClosureDay(),
					(this.krcdtMonAnyItemValuePk.getIsLastDay() == 1)),
				0, (this.timeValue153 == null ? Optional.empty() : Optional.of(new AnyTimeMonth(this.timeValue153))),
			(this.countValue153 == null ? Optional.empty() : Optional.of(new AnyTimesMonth(this.countValue153))),
			(this.moneyValue153 == null ? Optional.empty() : Optional.of(new AnyAmountMonth(this.moneyValue153))));
	}

	public AnyItemOfMonthly toDomainAnyItemOfMonthly154() {
		// get value anyItemId = 0 temporary because table have not anyitemId
		return AnyItemOfMonthly.of(this.krcdtMonAnyItemValuePk.getEmployeeId(),
				new YearMonth(this.krcdtMonAnyItemValuePk.getYearMonth()),
				EnumAdaptor.valueOf(this.krcdtMonAnyItemValuePk.getClosureId(), ClosureId.class),
				new ClosureDate(this.krcdtMonAnyItemValuePk.getClosureDay(),
					(this.krcdtMonAnyItemValuePk.getIsLastDay() == 1)),
				0, (this.timeValue154 == null ? Optional.empty() : Optional.of(new AnyTimeMonth(this.timeValue154))),
			(this.countValue154 == null ? Optional.empty() : Optional.of(new AnyTimesMonth(this.countValue154))),
			(this.moneyValue154 == null ? Optional.empty() : Optional.of(new AnyAmountMonth(this.moneyValue154))));
	}

	public AnyItemOfMonthly toDomainAnyItemOfMonthly155() {
		// get value anyItemId = 0 temporary because table have not anyitemId
		return AnyItemOfMonthly.of(this.krcdtMonAnyItemValuePk.getEmployeeId(),
				new YearMonth(this.krcdtMonAnyItemValuePk.getYearMonth()),
				EnumAdaptor.valueOf(this.krcdtMonAnyItemValuePk.getClosureId(), ClosureId.class),
				new ClosureDate(this.krcdtMonAnyItemValuePk.getClosureDay(),
					(this.krcdtMonAnyItemValuePk.getIsLastDay() == 1)),
				0, (this.timeValue155 == null ? Optional.empty() : Optional.of(new AnyTimeMonth(this.timeValue155))),
			(this.countValue155 == null ? Optional.empty() : Optional.of(new AnyTimesMonth(this.countValue155))),
			(this.moneyValue155 == null ? Optional.empty() : Optional.of(new AnyAmountMonth(this.moneyValue155))));
	}

	public AnyItemOfMonthly toDomainAnyItemOfMonthly156() {
		// get value anyItemId = 0 temporary because table have not anyitemId
		return AnyItemOfMonthly.of(this.krcdtMonAnyItemValuePk.getEmployeeId(),
				new YearMonth(this.krcdtMonAnyItemValuePk.getYearMonth()),
				EnumAdaptor.valueOf(this.krcdtMonAnyItemValuePk.getClosureId(), ClosureId.class),
				new ClosureDate(this.krcdtMonAnyItemValuePk.getClosureDay(),
					(this.krcdtMonAnyItemValuePk.getIsLastDay() == 1)),
				0, (this.timeValue156 == null ? Optional.empty() : Optional.of(new AnyTimeMonth(this.timeValue156))),
			(this.countValue156 == null ? Optional.empty() : Optional.of(new AnyTimesMonth(this.countValue156))),
			(this.moneyValue156 == null ? Optional.empty() : Optional.of(new AnyAmountMonth(this.moneyValue156))));
	}

	public AnyItemOfMonthly toDomainAnyItemOfMonthly157() {
		// get value anyItemId = 0 temporary because table have not anyitemId
		return AnyItemOfMonthly.of(this.krcdtMonAnyItemValuePk.getEmployeeId(),
				new YearMonth(this.krcdtMonAnyItemValuePk.getYearMonth()),
				EnumAdaptor.valueOf(this.krcdtMonAnyItemValuePk.getClosureId(), ClosureId.class),
				new ClosureDate(this.krcdtMonAnyItemValuePk.getClosureDay(),
					(this.krcdtMonAnyItemValuePk.getIsLastDay() == 1)),
				0, (this.timeValue157 == null ? Optional.empty() : Optional.of(new AnyTimeMonth(this.timeValue157))),
			(this.countValue157 == null ? Optional.empty() : Optional.of(new AnyTimesMonth(this.countValue157))),
			(this.moneyValue157 == null ? Optional.empty() : Optional.of(new AnyAmountMonth(this.moneyValue157))));
	}

	public AnyItemOfMonthly toDomainAnyItemOfMonthly158() {
		// get value anyItemId = 0 temporary because table have not anyitemId
		return AnyItemOfMonthly.of(this.krcdtMonAnyItemValuePk.getEmployeeId(),
				new YearMonth(this.krcdtMonAnyItemValuePk.getYearMonth()),
				EnumAdaptor.valueOf(this.krcdtMonAnyItemValuePk.getClosureId(), ClosureId.class),
				new ClosureDate(this.krcdtMonAnyItemValuePk.getClosureDay(),
					(this.krcdtMonAnyItemValuePk.getIsLastDay() == 1)),
				0, (this.timeValue158 == null ? Optional.empty() : Optional.of(new AnyTimeMonth(this.timeValue158))),
			(this.countValue158 == null ? Optional.empty() : Optional.of(new AnyTimesMonth(this.countValue158))),
			(this.moneyValue158 == null ? Optional.empty() : Optional.of(new AnyAmountMonth(this.moneyValue158))));
	}

	public AnyItemOfMonthly toDomainAnyItemOfMonthly159() {
		// get value anyItemId = 0 temporary because table have not anyitemId
		return AnyItemOfMonthly.of(this.krcdtMonAnyItemValuePk.getEmployeeId(),
				new YearMonth(this.krcdtMonAnyItemValuePk.getYearMonth()),
				EnumAdaptor.valueOf(this.krcdtMonAnyItemValuePk.getClosureId(), ClosureId.class),
				new ClosureDate(this.krcdtMonAnyItemValuePk.getClosureDay(),
					(this.krcdtMonAnyItemValuePk.getIsLastDay() == 1)),
				0, (this.timeValue159 == null ? Optional.empty() : Optional.of(new AnyTimeMonth(this.timeValue159))),
			(this.countValue159 == null ? Optional.empty() : Optional.of(new AnyTimesMonth(this.countValue159))),
			(this.moneyValue159 == null ? Optional.empty() : Optional.of(new AnyAmountMonth(this.moneyValue159))));
	}

	public AnyItemOfMonthly toDomainAnyItemOfMonthly160() {
		// get value anyItemId = 0 temporary because table have not anyitemId
		return AnyItemOfMonthly.of(this.krcdtMonAnyItemValuePk.getEmployeeId(),
				new YearMonth(this.krcdtMonAnyItemValuePk.getYearMonth()),
				EnumAdaptor.valueOf(this.krcdtMonAnyItemValuePk.getClosureId(), ClosureId.class),
				new ClosureDate(this.krcdtMonAnyItemValuePk.getClosureDay(),
					(this.krcdtMonAnyItemValuePk.getIsLastDay() == 1)),
				0, (this.timeValue160 == null ? Optional.empty() : Optional.of(new AnyTimeMonth(this.timeValue160))),
			(this.countValue160 == null ? Optional.empty() : Optional.of(new AnyTimesMonth(this.countValue160))),
			(this.moneyValue160 == null ? Optional.empty() : Optional.of(new AnyAmountMonth(this.moneyValue160))));
	}

	public AnyItemOfMonthly toDomainAnyItemOfMonthly161() {
		// get value anyItemId = 0 temporary because table have not anyitemId
		return AnyItemOfMonthly.of(this.krcdtMonAnyItemValuePk.getEmployeeId(),
				new YearMonth(this.krcdtMonAnyItemValuePk.getYearMonth()),
				EnumAdaptor.valueOf(this.krcdtMonAnyItemValuePk.getClosureId(), ClosureId.class),
				new ClosureDate(this.krcdtMonAnyItemValuePk.getClosureDay(),
					(this.krcdtMonAnyItemValuePk.getIsLastDay() == 1)),
				0, (this.timeValue161 == null ? Optional.empty() : Optional.of(new AnyTimeMonth(this.timeValue161))),
			(this.countValue161 == null ? Optional.empty() : Optional.of(new AnyTimesMonth(this.countValue161))),
			(this.moneyValue161 == null ? Optional.empty() : Optional.of(new AnyAmountMonth(this.moneyValue161))));
	}

	public AnyItemOfMonthly toDomainAnyItemOfMonthly162() {
		// get value anyItemId = 0 temporary because table have not anyitemId
		return AnyItemOfMonthly.of(this.krcdtMonAnyItemValuePk.getEmployeeId(),
				new YearMonth(this.krcdtMonAnyItemValuePk.getYearMonth()),
				EnumAdaptor.valueOf(this.krcdtMonAnyItemValuePk.getClosureId(), ClosureId.class),
				new ClosureDate(this.krcdtMonAnyItemValuePk.getClosureDay(),
					(this.krcdtMonAnyItemValuePk.getIsLastDay() == 1)),
				0, (this.timeValue162 == null ? Optional.empty() : Optional.of(new AnyTimeMonth(this.timeValue162))),
			(this.countValue162 == null ? Optional.empty() : Optional.of(new AnyTimesMonth(this.countValue162))),
			(this.moneyValue162 == null ? Optional.empty() : Optional.of(new AnyAmountMonth(this.moneyValue162))));
	}

	public AnyItemOfMonthly toDomainAnyItemOfMonthly163() {
		// get value anyItemId = 0 temporary because table have not anyitemId
		return AnyItemOfMonthly.of(this.krcdtMonAnyItemValuePk.getEmployeeId(),
				new YearMonth(this.krcdtMonAnyItemValuePk.getYearMonth()),
				EnumAdaptor.valueOf(this.krcdtMonAnyItemValuePk.getClosureId(), ClosureId.class),
				new ClosureDate(this.krcdtMonAnyItemValuePk.getClosureDay(),
					(this.krcdtMonAnyItemValuePk.getIsLastDay() == 1)),
				0, (this.timeValue163 == null ? Optional.empty() : Optional.of(new AnyTimeMonth(this.timeValue163))),
			(this.countValue163 == null ? Optional.empty() : Optional.of(new AnyTimesMonth(this.countValue163))),
			(this.moneyValue163 == null ? Optional.empty() : Optional.of(new AnyAmountMonth(this.moneyValue163))));
	}

	public AnyItemOfMonthly toDomainAnyItemOfMonthly164() {
		// get value anyItemId = 0 temporary because table have not anyitemId
		return AnyItemOfMonthly.of(this.krcdtMonAnyItemValuePk.getEmployeeId(),
				new YearMonth(this.krcdtMonAnyItemValuePk.getYearMonth()),
				EnumAdaptor.valueOf(this.krcdtMonAnyItemValuePk.getClosureId(), ClosureId.class),
				new ClosureDate(this.krcdtMonAnyItemValuePk.getClosureDay(),
					(this.krcdtMonAnyItemValuePk.getIsLastDay() == 1)),
				0, (this.timeValue164 == null ? Optional.empty() : Optional.of(new AnyTimeMonth(this.timeValue164))),
			(this.countValue164 == null ? Optional.empty() : Optional.of(new AnyTimesMonth(this.countValue164))),
			(this.moneyValue164 == null ? Optional.empty() : Optional.of(new AnyAmountMonth(this.moneyValue164))));
	}

	public AnyItemOfMonthly toDomainAnyItemOfMonthly165() {
		// get value anyItemId = 0 temporary because table have not anyitemId
		return AnyItemOfMonthly.of(this.krcdtMonAnyItemValuePk.getEmployeeId(),
				new YearMonth(this.krcdtMonAnyItemValuePk.getYearMonth()),
				EnumAdaptor.valueOf(this.krcdtMonAnyItemValuePk.getClosureId(), ClosureId.class),
				new ClosureDate(this.krcdtMonAnyItemValuePk.getClosureDay(),
					(this.krcdtMonAnyItemValuePk.getIsLastDay() == 1)),
				0, (this.timeValue165 == null ? Optional.empty() : Optional.of(new AnyTimeMonth(this.timeValue165))),
			(this.countValue165 == null ? Optional.empty() : Optional.of(new AnyTimesMonth(this.countValue165))),
			(this.moneyValue165 == null ? Optional.empty() : Optional.of(new AnyAmountMonth(this.moneyValue165))));
	}

	public AnyItemOfMonthly toDomainAnyItemOfMonthly166() {
		// get value anyItemId = 0 temporary because table have not anyitemId
		return AnyItemOfMonthly.of(this.krcdtMonAnyItemValuePk.getEmployeeId(),
				new YearMonth(this.krcdtMonAnyItemValuePk.getYearMonth()),
				EnumAdaptor.valueOf(this.krcdtMonAnyItemValuePk.getClosureId(), ClosureId.class),
				new ClosureDate(this.krcdtMonAnyItemValuePk.getClosureDay(),
					(this.krcdtMonAnyItemValuePk.getIsLastDay() == 1)),
				0, (this.timeValue166 == null ? Optional.empty() : Optional.of(new AnyTimeMonth(this.timeValue166))),
			(this.countValue166 == null ? Optional.empty() : Optional.of(new AnyTimesMonth(this.countValue166))),
			(this.moneyValue166 == null ? Optional.empty() : Optional.of(new AnyAmountMonth(this.moneyValue166))));
	}

	public AnyItemOfMonthly toDomainAnyItemOfMonthly167() {
		// get value anyItemId = 0 temporary because table have not anyitemId
		return AnyItemOfMonthly.of(this.krcdtMonAnyItemValuePk.getEmployeeId(),
				new YearMonth(this.krcdtMonAnyItemValuePk.getYearMonth()),
				EnumAdaptor.valueOf(this.krcdtMonAnyItemValuePk.getClosureId(), ClosureId.class),
				new ClosureDate(this.krcdtMonAnyItemValuePk.getClosureDay(),
					(this.krcdtMonAnyItemValuePk.getIsLastDay() == 1)),
				0, (this.timeValue167 == null ? Optional.empty() : Optional.of(new AnyTimeMonth(this.timeValue167))),
			(this.countValue167 == null ? Optional.empty() : Optional.of(new AnyTimesMonth(this.countValue167))),
			(this.moneyValue167 == null ? Optional.empty() : Optional.of(new AnyAmountMonth(this.moneyValue167))));
	}

	public AnyItemOfMonthly toDomainAnyItemOfMonthly168() {
		// get value anyItemId = 0 temporary because table have not anyitemId
		return AnyItemOfMonthly.of(this.krcdtMonAnyItemValuePk.getEmployeeId(),
				new YearMonth(this.krcdtMonAnyItemValuePk.getYearMonth()),
				EnumAdaptor.valueOf(this.krcdtMonAnyItemValuePk.getClosureId(), ClosureId.class),
				new ClosureDate(this.krcdtMonAnyItemValuePk.getClosureDay(),
					(this.krcdtMonAnyItemValuePk.getIsLastDay() == 1)),
				0, (this.timeValue168 == null ? Optional.empty() : Optional.of(new AnyTimeMonth(this.timeValue168))),
			(this.countValue168 == null ? Optional.empty() : Optional.of(new AnyTimesMonth(this.countValue168))),
			(this.moneyValue168 == null ? Optional.empty() : Optional.of(new AnyAmountMonth(this.moneyValue168))));
	}

	public AnyItemOfMonthly toDomainAnyItemOfMonthly169() {
		// get value anyItemId = 0 temporary because table have not anyitemId
		return AnyItemOfMonthly.of(this.krcdtMonAnyItemValuePk.getEmployeeId(),
				new YearMonth(this.krcdtMonAnyItemValuePk.getYearMonth()),
				EnumAdaptor.valueOf(this.krcdtMonAnyItemValuePk.getClosureId(), ClosureId.class),
				new ClosureDate(this.krcdtMonAnyItemValuePk.getClosureDay(),
					(this.krcdtMonAnyItemValuePk.getIsLastDay() == 1)),
				0, (this.timeValue169 == null ? Optional.empty() : Optional.of(new AnyTimeMonth(this.timeValue169))),
			(this.countValue169 == null ? Optional.empty() : Optional.of(new AnyTimesMonth(this.countValue169))),
			(this.moneyValue169 == null ? Optional.empty() : Optional.of(new AnyAmountMonth(this.moneyValue169))));
	}

	public AnyItemOfMonthly toDomainAnyItemOfMonthly170() {
		// get value anyItemId = 0 temporary because table have not anyitemId
		return AnyItemOfMonthly.of(this.krcdtMonAnyItemValuePk.getEmployeeId(),
				new YearMonth(this.krcdtMonAnyItemValuePk.getYearMonth()),
				EnumAdaptor.valueOf(this.krcdtMonAnyItemValuePk.getClosureId(), ClosureId.class),
				new ClosureDate(this.krcdtMonAnyItemValuePk.getClosureDay(),
					(this.krcdtMonAnyItemValuePk.getIsLastDay() == 1)),
				0, (this.timeValue170 == null ? Optional.empty() : Optional.of(new AnyTimeMonth(this.timeValue170))),
			(this.countValue170 == null ? Optional.empty() : Optional.of(new AnyTimesMonth(this.countValue170))),
			(this.moneyValue170 == null ? Optional.empty() : Optional.of(new AnyAmountMonth(this.moneyValue170))));
	}

	public AnyItemOfMonthly toDomainAnyItemOfMonthly171() {
		// get value anyItemId = 0 temporary because table have not anyitemId
		return AnyItemOfMonthly.of(this.krcdtMonAnyItemValuePk.getEmployeeId(),
				new YearMonth(this.krcdtMonAnyItemValuePk.getYearMonth()),
				EnumAdaptor.valueOf(this.krcdtMonAnyItemValuePk.getClosureId(), ClosureId.class),
				new ClosureDate(this.krcdtMonAnyItemValuePk.getClosureDay(),
					(this.krcdtMonAnyItemValuePk.getIsLastDay() == 1)),
				0, (this.timeValue171 == null ? Optional.empty() : Optional.of(new AnyTimeMonth(this.timeValue171))),
			(this.countValue171 == null ? Optional.empty() : Optional.of(new AnyTimesMonth(this.countValue171))),
			(this.moneyValue171 == null ? Optional.empty() : Optional.of(new AnyAmountMonth(this.moneyValue171))));
	}

	public AnyItemOfMonthly toDomainAnyItemOfMonthly172() {
		// get value anyItemId = 0 temporary because table have not anyitemId
		return AnyItemOfMonthly.of(this.krcdtMonAnyItemValuePk.getEmployeeId(),
				new YearMonth(this.krcdtMonAnyItemValuePk.getYearMonth()),
				EnumAdaptor.valueOf(this.krcdtMonAnyItemValuePk.getClosureId(), ClosureId.class),
				new ClosureDate(this.krcdtMonAnyItemValuePk.getClosureDay(),
					(this.krcdtMonAnyItemValuePk.getIsLastDay() == 1)),
				0, (this.timeValue172 == null ? Optional.empty() : Optional.of(new AnyTimeMonth(this.timeValue172))),
			(this.countValue172 == null ? Optional.empty() : Optional.of(new AnyTimesMonth(this.countValue172))),
			(this.moneyValue172 == null ? Optional.empty() : Optional.of(new AnyAmountMonth(this.moneyValue172))));
	}

	public AnyItemOfMonthly toDomainAnyItemOfMonthly173() {
		// get value anyItemId = 0 temporary because table have not anyitemId
		return AnyItemOfMonthly.of(this.krcdtMonAnyItemValuePk.getEmployeeId(),
				new YearMonth(this.krcdtMonAnyItemValuePk.getYearMonth()),
				EnumAdaptor.valueOf(this.krcdtMonAnyItemValuePk.getClosureId(), ClosureId.class),
				new ClosureDate(this.krcdtMonAnyItemValuePk.getClosureDay(),
					(this.krcdtMonAnyItemValuePk.getIsLastDay() == 1)),
				0, (this.timeValue173 == null ? Optional.empty() : Optional.of(new AnyTimeMonth(this.timeValue173))),
			(this.countValue173 == null ? Optional.empty() : Optional.of(new AnyTimesMonth(this.countValue173))),
			(this.moneyValue173 == null ? Optional.empty() : Optional.of(new AnyAmountMonth(this.moneyValue173))));
	}

	public AnyItemOfMonthly toDomainAnyItemOfMonthly174() {
		// get value anyItemId = 0 temporary because table have not anyitemId
		return AnyItemOfMonthly.of(this.krcdtMonAnyItemValuePk.getEmployeeId(),
				new YearMonth(this.krcdtMonAnyItemValuePk.getYearMonth()),
				EnumAdaptor.valueOf(this.krcdtMonAnyItemValuePk.getClosureId(), ClosureId.class),
				new ClosureDate(this.krcdtMonAnyItemValuePk.getClosureDay(),
					(this.krcdtMonAnyItemValuePk.getIsLastDay() == 1)),
				0, (this.timeValue174 == null ? Optional.empty() : Optional.of(new AnyTimeMonth(this.timeValue174))),
			(this.countValue174 == null ? Optional.empty() : Optional.of(new AnyTimesMonth(this.countValue174))),
			(this.moneyValue174 == null ? Optional.empty() : Optional.of(new AnyAmountMonth(this.moneyValue174))));
	}

	public AnyItemOfMonthly toDomainAnyItemOfMonthly175() {
		// get value anyItemId = 0 temporary because table have not anyitemId
		return AnyItemOfMonthly.of(this.krcdtMonAnyItemValuePk.getEmployeeId(),
				new YearMonth(this.krcdtMonAnyItemValuePk.getYearMonth()),
				EnumAdaptor.valueOf(this.krcdtMonAnyItemValuePk.getClosureId(), ClosureId.class),
				new ClosureDate(this.krcdtMonAnyItemValuePk.getClosureDay(),
					(this.krcdtMonAnyItemValuePk.getIsLastDay() == 1)),
				0, (this.timeValue175 == null ? Optional.empty() : Optional.of(new AnyTimeMonth(this.timeValue175))),
			(this.countValue175 == null ? Optional.empty() : Optional.of(new AnyTimesMonth(this.countValue175))),
			(this.moneyValue175 == null ? Optional.empty() : Optional.of(new AnyAmountMonth(this.moneyValue175))));
	}

	public AnyItemOfMonthly toDomainAnyItemOfMonthly176() {
		// get value anyItemId = 0 temporary because table have not anyitemId
		return AnyItemOfMonthly.of(this.krcdtMonAnyItemValuePk.getEmployeeId(),
				new YearMonth(this.krcdtMonAnyItemValuePk.getYearMonth()),
				EnumAdaptor.valueOf(this.krcdtMonAnyItemValuePk.getClosureId(), ClosureId.class),
				new ClosureDate(this.krcdtMonAnyItemValuePk.getClosureDay(),
					(this.krcdtMonAnyItemValuePk.getIsLastDay() == 1)),
				0, (this.timeValue176 == null ? Optional.empty() : Optional.of(new AnyTimeMonth(this.timeValue176))),
			(this.countValue176 == null ? Optional.empty() : Optional.of(new AnyTimesMonth(this.countValue176))),
			(this.moneyValue176 == null ? Optional.empty() : Optional.of(new AnyAmountMonth(this.moneyValue176))));
	}

	public AnyItemOfMonthly toDomainAnyItemOfMonthly177() {
		// get value anyItemId = 0 temporary because table have not anyitemId
		return AnyItemOfMonthly.of(this.krcdtMonAnyItemValuePk.getEmployeeId(),
				new YearMonth(this.krcdtMonAnyItemValuePk.getYearMonth()),
				EnumAdaptor.valueOf(this.krcdtMonAnyItemValuePk.getClosureId(), ClosureId.class),
				new ClosureDate(this.krcdtMonAnyItemValuePk.getClosureDay(),
					(this.krcdtMonAnyItemValuePk.getIsLastDay() == 1)),
				0, (this.timeValue177 == null ? Optional.empty() : Optional.of(new AnyTimeMonth(this.timeValue177))),
			(this.countValue177 == null ? Optional.empty() : Optional.of(new AnyTimesMonth(this.countValue177))),
			(this.moneyValue177 == null ? Optional.empty() : Optional.of(new AnyAmountMonth(this.moneyValue177))));
	}

	public AnyItemOfMonthly toDomainAnyItemOfMonthly178() {
		// get value anyItemId = 0 temporary because table have not anyitemId
		return AnyItemOfMonthly.of(this.krcdtMonAnyItemValuePk.getEmployeeId(),
				new YearMonth(this.krcdtMonAnyItemValuePk.getYearMonth()),
				EnumAdaptor.valueOf(this.krcdtMonAnyItemValuePk.getClosureId(), ClosureId.class),
				new ClosureDate(this.krcdtMonAnyItemValuePk.getClosureDay(),
					(this.krcdtMonAnyItemValuePk.getIsLastDay() == 1)),
				0, (this.timeValue178 == null ? Optional.empty() : Optional.of(new AnyTimeMonth(this.timeValue178))),
			(this.countValue178 == null ? Optional.empty() : Optional.of(new AnyTimesMonth(this.countValue178))),
			(this.moneyValue178 == null ? Optional.empty() : Optional.of(new AnyAmountMonth(this.moneyValue178))));
	}

	public AnyItemOfMonthly toDomainAnyItemOfMonthly179() {
		// get value anyItemId = 0 temporary because table have not anyitemId
		return AnyItemOfMonthly.of(this.krcdtMonAnyItemValuePk.getEmployeeId(),
				new YearMonth(this.krcdtMonAnyItemValuePk.getYearMonth()),
				EnumAdaptor.valueOf(this.krcdtMonAnyItemValuePk.getClosureId(), ClosureId.class),
				new ClosureDate(this.krcdtMonAnyItemValuePk.getClosureDay(),
					(this.krcdtMonAnyItemValuePk.getIsLastDay() == 1)),
				0, (this.timeValue179 == null ? Optional.empty() : Optional.of(new AnyTimeMonth(this.timeValue179))),
			(this.countValue179 == null ? Optional.empty() : Optional.of(new AnyTimesMonth(this.countValue179))),
			(this.moneyValue179 == null ? Optional.empty() : Optional.of(new AnyAmountMonth(this.moneyValue179))));
	}

	public AnyItemOfMonthly toDomainAnyItemOfMonthly180() {
		// get value anyItemId = 0 temporary because table have not anyitemId
		return AnyItemOfMonthly.of(this.krcdtMonAnyItemValuePk.getEmployeeId(),
				new YearMonth(this.krcdtMonAnyItemValuePk.getYearMonth()),
				EnumAdaptor.valueOf(this.krcdtMonAnyItemValuePk.getClosureId(), ClosureId.class),
				new ClosureDate(this.krcdtMonAnyItemValuePk.getClosureDay(),
					(this.krcdtMonAnyItemValuePk.getIsLastDay() == 1)),
				0, (this.timeValue180 == null ? Optional.empty() : Optional.of(new AnyTimeMonth(this.timeValue180))),
			(this.countValue180 == null ? Optional.empty() : Optional.of(new AnyTimesMonth(this.countValue180))),
			(this.moneyValue180 == null ? Optional.empty() : Optional.of(new AnyAmountMonth(this.moneyValue180))));
	}

	public AnyItemOfMonthly toDomainAnyItemOfMonthly181() {
		// get value anyItemId = 0 temporary because table have not anyitemId
		return AnyItemOfMonthly.of(this.krcdtMonAnyItemValuePk.getEmployeeId(),
				new YearMonth(this.krcdtMonAnyItemValuePk.getYearMonth()),
				EnumAdaptor.valueOf(this.krcdtMonAnyItemValuePk.getClosureId(), ClosureId.class),
				new ClosureDate(this.krcdtMonAnyItemValuePk.getClosureDay(),
					(this.krcdtMonAnyItemValuePk.getIsLastDay() == 1)),
				0, (this.timeValue181 == null ? Optional.empty() : Optional.of(new AnyTimeMonth(this.timeValue181))),
			(this.countValue181 == null ? Optional.empty() : Optional.of(new AnyTimesMonth(this.countValue181))),
			(this.moneyValue181 == null ? Optional.empty() : Optional.of(new AnyAmountMonth(this.moneyValue181))));
	}

	public AnyItemOfMonthly toDomainAnyItemOfMonthly182() {
		// get value anyItemId = 0 temporary because table have not anyitemId
		return AnyItemOfMonthly.of(this.krcdtMonAnyItemValuePk.getEmployeeId(),
				new YearMonth(this.krcdtMonAnyItemValuePk.getYearMonth()),
				EnumAdaptor.valueOf(this.krcdtMonAnyItemValuePk.getClosureId(), ClosureId.class),
				new ClosureDate(this.krcdtMonAnyItemValuePk.getClosureDay(),
					(this.krcdtMonAnyItemValuePk.getIsLastDay() == 1)),
				0, (this.timeValue182 == null ? Optional.empty() : Optional.of(new AnyTimeMonth(this.timeValue182))),
			(this.countValue182 == null ? Optional.empty() : Optional.of(new AnyTimesMonth(this.countValue182))),
			(this.moneyValue182 == null ? Optional.empty() : Optional.of(new AnyAmountMonth(this.moneyValue182))));
	}

	public AnyItemOfMonthly toDomainAnyItemOfMonthly183() {
		// get value anyItemId = 0 temporary because table have not anyitemId
		return AnyItemOfMonthly.of(this.krcdtMonAnyItemValuePk.getEmployeeId(),
				new YearMonth(this.krcdtMonAnyItemValuePk.getYearMonth()),
				EnumAdaptor.valueOf(this.krcdtMonAnyItemValuePk.getClosureId(), ClosureId.class),
				new ClosureDate(this.krcdtMonAnyItemValuePk.getClosureDay(),
					(this.krcdtMonAnyItemValuePk.getIsLastDay() == 1)),
				0, (this.timeValue183 == null ? Optional.empty() : Optional.of(new AnyTimeMonth(this.timeValue183))),
			(this.countValue183 == null ? Optional.empty() : Optional.of(new AnyTimesMonth(this.countValue183))),
			(this.moneyValue183 == null ? Optional.empty() : Optional.of(new AnyAmountMonth(this.moneyValue183))));
	}

	public AnyItemOfMonthly toDomainAnyItemOfMonthly184() {
		// get value anyItemId = 0 temporary because table have not anyitemId
		return AnyItemOfMonthly.of(this.krcdtMonAnyItemValuePk.getEmployeeId(),
				new YearMonth(this.krcdtMonAnyItemValuePk.getYearMonth()),
				EnumAdaptor.valueOf(this.krcdtMonAnyItemValuePk.getClosureId(), ClosureId.class),
				new ClosureDate(this.krcdtMonAnyItemValuePk.getClosureDay(),
					(this.krcdtMonAnyItemValuePk.getIsLastDay() == 1)),
				0, (this.timeValue184 == null ? Optional.empty() : Optional.of(new AnyTimeMonth(this.timeValue184))),
			(this.countValue184 == null ? Optional.empty() : Optional.of(new AnyTimesMonth(this.countValue184))),
			(this.moneyValue184 == null ? Optional.empty() : Optional.of(new AnyAmountMonth(this.moneyValue184))));
	}

	public AnyItemOfMonthly toDomainAnyItemOfMonthly185() {
		// get value anyItemId = 0 temporary because table have not anyitemId
		return AnyItemOfMonthly.of(this.krcdtMonAnyItemValuePk.getEmployeeId(),
				new YearMonth(this.krcdtMonAnyItemValuePk.getYearMonth()),
				EnumAdaptor.valueOf(this.krcdtMonAnyItemValuePk.getClosureId(), ClosureId.class),
				new ClosureDate(this.krcdtMonAnyItemValuePk.getClosureDay(),
					(this.krcdtMonAnyItemValuePk.getIsLastDay() == 1)),
				0, (this.timeValue185 == null ? Optional.empty() : Optional.of(new AnyTimeMonth(this.timeValue185))),
			(this.countValue185 == null ? Optional.empty() : Optional.of(new AnyTimesMonth(this.countValue185))),
			(this.moneyValue185 == null ? Optional.empty() : Optional.of(new AnyAmountMonth(this.moneyValue185))));
	}

	public AnyItemOfMonthly toDomainAnyItemOfMonthly186() {
		// get value anyItemId = 0 temporary because table have not anyitemId
		return AnyItemOfMonthly.of(this.krcdtMonAnyItemValuePk.getEmployeeId(),
				new YearMonth(this.krcdtMonAnyItemValuePk.getYearMonth()),
				EnumAdaptor.valueOf(this.krcdtMonAnyItemValuePk.getClosureId(), ClosureId.class),
				new ClosureDate(this.krcdtMonAnyItemValuePk.getClosureDay(),
					(this.krcdtMonAnyItemValuePk.getIsLastDay() == 1)),
				0, (this.timeValue186 == null ? Optional.empty() : Optional.of(new AnyTimeMonth(this.timeValue186))),
			(this.countValue186 == null ? Optional.empty() : Optional.of(new AnyTimesMonth(this.countValue186))),
			(this.moneyValue186 == null ? Optional.empty() : Optional.of(new AnyAmountMonth(this.moneyValue186))));
	}

	public AnyItemOfMonthly toDomainAnyItemOfMonthly187() {
		// get value anyItemId = 0 temporary because table have not anyitemId
		return AnyItemOfMonthly.of(this.krcdtMonAnyItemValuePk.getEmployeeId(),
				new YearMonth(this.krcdtMonAnyItemValuePk.getYearMonth()),
				EnumAdaptor.valueOf(this.krcdtMonAnyItemValuePk.getClosureId(), ClosureId.class),
				new ClosureDate(this.krcdtMonAnyItemValuePk.getClosureDay(),
					(this.krcdtMonAnyItemValuePk.getIsLastDay() == 1)),
				0, (this.timeValue187 == null ? Optional.empty() : Optional.of(new AnyTimeMonth(this.timeValue187))),
			(this.countValue187 == null ? Optional.empty() : Optional.of(new AnyTimesMonth(this.countValue187))),
			(this.moneyValue187 == null ? Optional.empty() : Optional.of(new AnyAmountMonth(this.moneyValue187))));
	}

	public AnyItemOfMonthly toDomainAnyItemOfMonthly188() {
		// get value anyItemId = 0 temporary because table have not anyitemId
		return AnyItemOfMonthly.of(this.krcdtMonAnyItemValuePk.getEmployeeId(),
				new YearMonth(this.krcdtMonAnyItemValuePk.getYearMonth()),
				EnumAdaptor.valueOf(this.krcdtMonAnyItemValuePk.getClosureId(), ClosureId.class),
				new ClosureDate(this.krcdtMonAnyItemValuePk.getClosureDay(),
					(this.krcdtMonAnyItemValuePk.getIsLastDay() == 1)),
				0, (this.timeValue188 == null ? Optional.empty() : Optional.of(new AnyTimeMonth(this.timeValue188))),
			(this.countValue188 == null ? Optional.empty() : Optional.of(new AnyTimesMonth(this.countValue188))),
			(this.moneyValue188 == null ? Optional.empty() : Optional.of(new AnyAmountMonth(this.moneyValue188))));
	}

	public AnyItemOfMonthly toDomainAnyItemOfMonthly189() {
		// get value anyItemId = 0 temporary because table have not anyitemId
		return AnyItemOfMonthly.of(this.krcdtMonAnyItemValuePk.getEmployeeId(),
				new YearMonth(this.krcdtMonAnyItemValuePk.getYearMonth()),
				EnumAdaptor.valueOf(this.krcdtMonAnyItemValuePk.getClosureId(), ClosureId.class),
				new ClosureDate(this.krcdtMonAnyItemValuePk.getClosureDay(),
					(this.krcdtMonAnyItemValuePk.getIsLastDay() == 1)),
				0, (this.timeValue189 == null ? Optional.empty() : Optional.of(new AnyTimeMonth(this.timeValue189))),
			(this.countValue189 == null ? Optional.empty() : Optional.of(new AnyTimesMonth(this.countValue189))),
			(this.moneyValue189 == null ? Optional.empty() : Optional.of(new AnyAmountMonth(this.moneyValue189))));
	}

	public AnyItemOfMonthly toDomainAnyItemOfMonthly190() {
		// get value anyItemId = 0 temporary because table have not anyitemId
		return AnyItemOfMonthly.of(this.krcdtMonAnyItemValuePk.getEmployeeId(),
				new YearMonth(this.krcdtMonAnyItemValuePk.getYearMonth()),
				EnumAdaptor.valueOf(this.krcdtMonAnyItemValuePk.getClosureId(), ClosureId.class),
				new ClosureDate(this.krcdtMonAnyItemValuePk.getClosureDay(),
					(this.krcdtMonAnyItemValuePk.getIsLastDay() == 1)),
				0, (this.timeValue190 == null ? Optional.empty() : Optional.of(new AnyTimeMonth(this.timeValue190))),
			(this.countValue190 == null ? Optional.empty() : Optional.of(new AnyTimesMonth(this.countValue190))),
			(this.moneyValue190 == null ? Optional.empty() : Optional.of(new AnyAmountMonth(this.moneyValue190))));
	}

	public AnyItemOfMonthly toDomainAnyItemOfMonthly191() {
		// get value anyItemId = 0 temporary because table have not anyitemId
		return AnyItemOfMonthly.of(this.krcdtMonAnyItemValuePk.getEmployeeId(),
				new YearMonth(this.krcdtMonAnyItemValuePk.getYearMonth()),
				EnumAdaptor.valueOf(this.krcdtMonAnyItemValuePk.getClosureId(), ClosureId.class),
				new ClosureDate(this.krcdtMonAnyItemValuePk.getClosureDay(),
					(this.krcdtMonAnyItemValuePk.getIsLastDay() == 1)),
				0, (this.timeValue191 == null ? Optional.empty() : Optional.of(new AnyTimeMonth(this.timeValue191))),
			(this.countValue191 == null ? Optional.empty() : Optional.of(new AnyTimesMonth(this.countValue191))),
			(this.moneyValue191 == null ? Optional.empty() : Optional.of(new AnyAmountMonth(this.moneyValue191))));
	}

	public AnyItemOfMonthly toDomainAnyItemOfMonthly192() {
		// get value anyItemId = 0 temporary because table have not anyitemId
		return AnyItemOfMonthly.of(this.krcdtMonAnyItemValuePk.getEmployeeId(),
				new YearMonth(this.krcdtMonAnyItemValuePk.getYearMonth()),
				EnumAdaptor.valueOf(this.krcdtMonAnyItemValuePk.getClosureId(), ClosureId.class),
				new ClosureDate(this.krcdtMonAnyItemValuePk.getClosureDay(),
					(this.krcdtMonAnyItemValuePk.getIsLastDay() == 1)),
				0, (this.timeValue192 == null ? Optional.empty() : Optional.of(new AnyTimeMonth(this.timeValue192))),
			(this.countValue192 == null ? Optional.empty() : Optional.of(new AnyTimesMonth(this.countValue192))),
			(this.moneyValue192 == null ? Optional.empty() : Optional.of(new AnyAmountMonth(this.moneyValue192))));
	}

	public AnyItemOfMonthly toDomainAnyItemOfMonthly193() {
		// get value anyItemId = 0 temporary because table have not anyitemId
		return AnyItemOfMonthly.of(this.krcdtMonAnyItemValuePk.getEmployeeId(),
				new YearMonth(this.krcdtMonAnyItemValuePk.getYearMonth()),
				EnumAdaptor.valueOf(this.krcdtMonAnyItemValuePk.getClosureId(), ClosureId.class),
				new ClosureDate(this.krcdtMonAnyItemValuePk.getClosureDay(),
					(this.krcdtMonAnyItemValuePk.getIsLastDay() == 1)),
				0, (this.timeValue193 == null ? Optional.empty() : Optional.of(new AnyTimeMonth(this.timeValue193))),
			(this.countValue193 == null ? Optional.empty() : Optional.of(new AnyTimesMonth(this.countValue193))),
			(this.moneyValue193 == null ? Optional.empty() : Optional.of(new AnyAmountMonth(this.moneyValue193))));
	}

	public AnyItemOfMonthly toDomainAnyItemOfMonthly194() {
		// get value anyItemId = 0 temporary because table have not anyitemId
		return AnyItemOfMonthly.of(this.krcdtMonAnyItemValuePk.getEmployeeId(),
				new YearMonth(this.krcdtMonAnyItemValuePk.getYearMonth()),
				EnumAdaptor.valueOf(this.krcdtMonAnyItemValuePk.getClosureId(), ClosureId.class),
				new ClosureDate(this.krcdtMonAnyItemValuePk.getClosureDay(),
					(this.krcdtMonAnyItemValuePk.getIsLastDay() == 1)),
				0, (this.timeValue194 == null ? Optional.empty() : Optional.of(new AnyTimeMonth(this.timeValue194))),
			(this.countValue194 == null ? Optional.empty() : Optional.of(new AnyTimesMonth(this.countValue194))),
			(this.moneyValue194 == null ? Optional.empty() : Optional.of(new AnyAmountMonth(this.moneyValue194))));
	}

	public AnyItemOfMonthly toDomainAnyItemOfMonthly195() {
		// get value anyItemId = 0 temporary because table have not anyitemId
		return AnyItemOfMonthly.of(this.krcdtMonAnyItemValuePk.getEmployeeId(),
				new YearMonth(this.krcdtMonAnyItemValuePk.getYearMonth()),
				EnumAdaptor.valueOf(this.krcdtMonAnyItemValuePk.getClosureId(), ClosureId.class),
				new ClosureDate(this.krcdtMonAnyItemValuePk.getClosureDay(),
					(this.krcdtMonAnyItemValuePk.getIsLastDay() == 1)),
				0, (this.timeValue195 == null ? Optional.empty() : Optional.of(new AnyTimeMonth(this.timeValue195))),
			(this.countValue195 == null ? Optional.empty() : Optional.of(new AnyTimesMonth(this.countValue195))),
			(this.moneyValue195 == null ? Optional.empty() : Optional.of(new AnyAmountMonth(this.moneyValue195))));
	}

	public AnyItemOfMonthly toDomainAnyItemOfMonthly196() {
		// get value anyItemId = 0 temporary because table have not anyitemId
		return AnyItemOfMonthly.of(this.krcdtMonAnyItemValuePk.getEmployeeId(),
				new YearMonth(this.krcdtMonAnyItemValuePk.getYearMonth()),
				EnumAdaptor.valueOf(this.krcdtMonAnyItemValuePk.getClosureId(), ClosureId.class),
				new ClosureDate(this.krcdtMonAnyItemValuePk.getClosureDay(),
					(this.krcdtMonAnyItemValuePk.getIsLastDay() == 1)),
				0, (this.timeValue196 == null ? Optional.empty() : Optional.of(new AnyTimeMonth(this.timeValue196))),
			(this.countValue196 == null ? Optional.empty() : Optional.of(new AnyTimesMonth(this.countValue196))),
			(this.moneyValue196 == null ? Optional.empty() : Optional.of(new AnyAmountMonth(this.moneyValue196))));
	}

	public AnyItemOfMonthly toDomainAnyItemOfMonthly197() {
		// get value anyItemId = 0 temporary because table have not anyitemId
		return AnyItemOfMonthly.of(this.krcdtMonAnyItemValuePk.getEmployeeId(),
				new YearMonth(this.krcdtMonAnyItemValuePk.getYearMonth()),
				EnumAdaptor.valueOf(this.krcdtMonAnyItemValuePk.getClosureId(), ClosureId.class),
				new ClosureDate(this.krcdtMonAnyItemValuePk.getClosureDay(),
					(this.krcdtMonAnyItemValuePk.getIsLastDay() == 1)),
				0, (this.timeValue197 == null ? Optional.empty() : Optional.of(new AnyTimeMonth(this.timeValue197))),
			(this.countValue197 == null ? Optional.empty() : Optional.of(new AnyTimesMonth(this.countValue197))),
			(this.moneyValue197 == null ? Optional.empty() : Optional.of(new AnyAmountMonth(this.moneyValue197))));
	}

	public AnyItemOfMonthly toDomainAnyItemOfMonthly198() {
		// get value anyItemId = 0 temporary because table have not anyitemId
		return AnyItemOfMonthly.of(this.krcdtMonAnyItemValuePk.getEmployeeId(),
				new YearMonth(this.krcdtMonAnyItemValuePk.getYearMonth()),
				EnumAdaptor.valueOf(this.krcdtMonAnyItemValuePk.getClosureId(), ClosureId.class),
				new ClosureDate(this.krcdtMonAnyItemValuePk.getClosureDay(),
					(this.krcdtMonAnyItemValuePk.getIsLastDay() == 1)),
				0, (this.timeValue198 == null ? Optional.empty() : Optional.of(new AnyTimeMonth(this.timeValue198))),
			(this.countValue198 == null ? Optional.empty() : Optional.of(new AnyTimesMonth(this.countValue198))),
			(this.moneyValue198 == null ? Optional.empty() : Optional.of(new AnyAmountMonth(this.moneyValue198))));
	}

	public AnyItemOfMonthly toDomainAnyItemOfMonthly199() {
		// get value anyItemId = 0 temporary because table have not anyitemId
		return AnyItemOfMonthly.of(this.krcdtMonAnyItemValuePk.getEmployeeId(),
				new YearMonth(this.krcdtMonAnyItemValuePk.getYearMonth()),
				EnumAdaptor.valueOf(this.krcdtMonAnyItemValuePk.getClosureId(), ClosureId.class),
				new ClosureDate(this.krcdtMonAnyItemValuePk.getClosureDay(),
					(this.krcdtMonAnyItemValuePk.getIsLastDay() == 1)),
				0, (this.timeValue199 == null ? Optional.empty() : Optional.of(new AnyTimeMonth(this.timeValue199))),
			(this.countValue199 == null ? Optional.empty() : Optional.of(new AnyTimesMonth(this.countValue199))),
			(this.moneyValue199 == null ? Optional.empty() : Optional.of(new AnyAmountMonth(this.moneyValue199))));
	}

	public AnyItemOfMonthly toDomainAnyItemOfMonthly200() { 
	//get  value anyItemId  = 0 temporary because table have not anyitemId
	return AnyItemOfMonthly.of(
	this.krcdtMonAnyItemValuePk.getEmployeeId(),
					new YearMonth(this.krcdtMonAnyItemValuePk.getYearMonth()),
					EnumAdaptor.valueOf(this.krcdtMonAnyItemValuePk.getClosureId(), ClosureId.class),
					new ClosureDate(this.krcdtMonAnyItemValuePk.getClosureDay(), (this.krcdtMonAnyItemValuePk.getIsLastDay() == 1)),
					0,
(this.timeValue200 == null ? Optional.empty() : Optional.of(new AnyTimeMonth(this.timeValue200))),
(this.countValue200 == null ? Optional.empty() : Optional.of(new AnyTimesMonth(this.countValue200))),
(this.moneyValue200 == null ? Optional.empty() : Optional.of(new AnyAmountMonth(this.moneyValue200))));
	
	}
	 
	public 	MonthMergeKey toDomainKey() {
		MonthMergeKey key = new MonthMergeKey();
		key.setEmployeeId(this.krcdtMonAnyItemValuePk.getEmployeeId());
		key.setYearMonth(new YearMonth(this.krcdtMonAnyItemValuePk.getYearMonth()));
		key.setClosureId(EnumAdaptor.valueOf(this.krcdtMonAnyItemValuePk.getClosureId(), ClosureId.class));
		key.setClosureDate(new ClosureDate(this.krcdtMonAnyItemValuePk.getClosureDay(),
			(this.krcdtMonAnyItemValuePk.getIsLastDay() == 1)));
		return key;
		
	}
	public AnyItemOfMonthlyMerge toDomainAnyItemOfMonthly() {
		AnyItemOfMonthlyMerge merge = new AnyItemOfMonthlyMerge();
		merge.setAnyItemOfMonthly1(this.toDomainAnyItemOfMonthly1());
		merge.setAnyItemOfMonthly2(this.toDomainAnyItemOfMonthly2());
		merge.setAnyItemOfMonthly3(this.toDomainAnyItemOfMonthly3());
		merge.setAnyItemOfMonthly4(this.toDomainAnyItemOfMonthly4());
		merge.setAnyItemOfMonthly5(this.toDomainAnyItemOfMonthly5());
		merge.setAnyItemOfMonthly6(this.toDomainAnyItemOfMonthly6());
		merge.setAnyItemOfMonthly7(this.toDomainAnyItemOfMonthly7());
		merge.setAnyItemOfMonthly8(this.toDomainAnyItemOfMonthly8());
		merge.setAnyItemOfMonthly9(this.toDomainAnyItemOfMonthly9());
		merge.setAnyItemOfMonthly10(this.toDomainAnyItemOfMonthly10());
		merge.setAnyItemOfMonthly11(this.toDomainAnyItemOfMonthly11());
		merge.setAnyItemOfMonthly12(this.toDomainAnyItemOfMonthly12());
		merge.setAnyItemOfMonthly13(this.toDomainAnyItemOfMonthly13());
		merge.setAnyItemOfMonthly14(this.toDomainAnyItemOfMonthly14());
		merge.setAnyItemOfMonthly15(this.toDomainAnyItemOfMonthly15());
		merge.setAnyItemOfMonthly16(this.toDomainAnyItemOfMonthly16());
		merge.setAnyItemOfMonthly17(this.toDomainAnyItemOfMonthly17());
		merge.setAnyItemOfMonthly18(this.toDomainAnyItemOfMonthly18());
		merge.setAnyItemOfMonthly19(this.toDomainAnyItemOfMonthly19());
		merge.setAnyItemOfMonthly20(this.toDomainAnyItemOfMonthly20());
		merge.setAnyItemOfMonthly21(this.toDomainAnyItemOfMonthly21());
		merge.setAnyItemOfMonthly22(this.toDomainAnyItemOfMonthly22());
		merge.setAnyItemOfMonthly23(this.toDomainAnyItemOfMonthly23());
		merge.setAnyItemOfMonthly24(this.toDomainAnyItemOfMonthly24());
		merge.setAnyItemOfMonthly25(this.toDomainAnyItemOfMonthly25());
		merge.setAnyItemOfMonthly26(this.toDomainAnyItemOfMonthly26());
		merge.setAnyItemOfMonthly27(this.toDomainAnyItemOfMonthly27());
		merge.setAnyItemOfMonthly28(this.toDomainAnyItemOfMonthly28());
		merge.setAnyItemOfMonthly29(this.toDomainAnyItemOfMonthly29());
		merge.setAnyItemOfMonthly30(this.toDomainAnyItemOfMonthly30());
		merge.setAnyItemOfMonthly31(this.toDomainAnyItemOfMonthly31());
		merge.setAnyItemOfMonthly32(this.toDomainAnyItemOfMonthly32());
		merge.setAnyItemOfMonthly33(this.toDomainAnyItemOfMonthly33());
		merge.setAnyItemOfMonthly34(this.toDomainAnyItemOfMonthly34());
		merge.setAnyItemOfMonthly35(this.toDomainAnyItemOfMonthly35());
		merge.setAnyItemOfMonthly36(this.toDomainAnyItemOfMonthly36());
		merge.setAnyItemOfMonthly37(this.toDomainAnyItemOfMonthly37());
		merge.setAnyItemOfMonthly38(this.toDomainAnyItemOfMonthly38());
		merge.setAnyItemOfMonthly39(this.toDomainAnyItemOfMonthly39());
		merge.setAnyItemOfMonthly40(this.toDomainAnyItemOfMonthly40());
		merge.setAnyItemOfMonthly41(this.toDomainAnyItemOfMonthly41());
		merge.setAnyItemOfMonthly42(this.toDomainAnyItemOfMonthly42());
		merge.setAnyItemOfMonthly43(this.toDomainAnyItemOfMonthly43());
		merge.setAnyItemOfMonthly44(this.toDomainAnyItemOfMonthly44());
		merge.setAnyItemOfMonthly45(this.toDomainAnyItemOfMonthly45());
		merge.setAnyItemOfMonthly46(this.toDomainAnyItemOfMonthly46());
		merge.setAnyItemOfMonthly47(this.toDomainAnyItemOfMonthly47());
		merge.setAnyItemOfMonthly48(this.toDomainAnyItemOfMonthly48());
		merge.setAnyItemOfMonthly49(this.toDomainAnyItemOfMonthly49());
		merge.setAnyItemOfMonthly50(this.toDomainAnyItemOfMonthly50());
		merge.setAnyItemOfMonthly51(this.toDomainAnyItemOfMonthly51());
		merge.setAnyItemOfMonthly52(this.toDomainAnyItemOfMonthly52());
		merge.setAnyItemOfMonthly53(this.toDomainAnyItemOfMonthly53());
		merge.setAnyItemOfMonthly54(this.toDomainAnyItemOfMonthly54());
		merge.setAnyItemOfMonthly55(this.toDomainAnyItemOfMonthly55());
		merge.setAnyItemOfMonthly56(this.toDomainAnyItemOfMonthly56());
		merge.setAnyItemOfMonthly57(this.toDomainAnyItemOfMonthly57());
		merge.setAnyItemOfMonthly58(this.toDomainAnyItemOfMonthly58());
		merge.setAnyItemOfMonthly59(this.toDomainAnyItemOfMonthly59());
		merge.setAnyItemOfMonthly60(this.toDomainAnyItemOfMonthly60());
		merge.setAnyItemOfMonthly61(this.toDomainAnyItemOfMonthly61());
		merge.setAnyItemOfMonthly62(this.toDomainAnyItemOfMonthly62());
		merge.setAnyItemOfMonthly63(this.toDomainAnyItemOfMonthly63());
		merge.setAnyItemOfMonthly64(this.toDomainAnyItemOfMonthly64());
		merge.setAnyItemOfMonthly65(this.toDomainAnyItemOfMonthly65());
		merge.setAnyItemOfMonthly66(this.toDomainAnyItemOfMonthly66());
		merge.setAnyItemOfMonthly67(this.toDomainAnyItemOfMonthly67());
		merge.setAnyItemOfMonthly68(this.toDomainAnyItemOfMonthly68());
		merge.setAnyItemOfMonthly69(this.toDomainAnyItemOfMonthly69());
		merge.setAnyItemOfMonthly70(this.toDomainAnyItemOfMonthly70());
		merge.setAnyItemOfMonthly71(this.toDomainAnyItemOfMonthly71());
		merge.setAnyItemOfMonthly72(this.toDomainAnyItemOfMonthly72());
		merge.setAnyItemOfMonthly73(this.toDomainAnyItemOfMonthly73());
		merge.setAnyItemOfMonthly74(this.toDomainAnyItemOfMonthly74());
		merge.setAnyItemOfMonthly75(this.toDomainAnyItemOfMonthly75());
		merge.setAnyItemOfMonthly76(this.toDomainAnyItemOfMonthly76());
		merge.setAnyItemOfMonthly77(this.toDomainAnyItemOfMonthly77());
		merge.setAnyItemOfMonthly78(this.toDomainAnyItemOfMonthly78());
		merge.setAnyItemOfMonthly79(this.toDomainAnyItemOfMonthly79());
		merge.setAnyItemOfMonthly80(this.toDomainAnyItemOfMonthly80());
		merge.setAnyItemOfMonthly81(this.toDomainAnyItemOfMonthly81());
		merge.setAnyItemOfMonthly82(this.toDomainAnyItemOfMonthly82());
		merge.setAnyItemOfMonthly83(this.toDomainAnyItemOfMonthly83());
		merge.setAnyItemOfMonthly84(this.toDomainAnyItemOfMonthly84());
		merge.setAnyItemOfMonthly85(this.toDomainAnyItemOfMonthly85());
		merge.setAnyItemOfMonthly86(this.toDomainAnyItemOfMonthly86());
		merge.setAnyItemOfMonthly87(this.toDomainAnyItemOfMonthly87());
		merge.setAnyItemOfMonthly88(this.toDomainAnyItemOfMonthly88());
		merge.setAnyItemOfMonthly89(this.toDomainAnyItemOfMonthly89());
		merge.setAnyItemOfMonthly90(this.toDomainAnyItemOfMonthly90());
		merge.setAnyItemOfMonthly91(this.toDomainAnyItemOfMonthly91());
		merge.setAnyItemOfMonthly92(this.toDomainAnyItemOfMonthly92());
		merge.setAnyItemOfMonthly93(this.toDomainAnyItemOfMonthly93());
		merge.setAnyItemOfMonthly94(this.toDomainAnyItemOfMonthly94());
		merge.setAnyItemOfMonthly95(this.toDomainAnyItemOfMonthly95());
		merge.setAnyItemOfMonthly96(this.toDomainAnyItemOfMonthly96());
		merge.setAnyItemOfMonthly97(this.toDomainAnyItemOfMonthly97());
		merge.setAnyItemOfMonthly98(this.toDomainAnyItemOfMonthly98());
		merge.setAnyItemOfMonthly99(this.toDomainAnyItemOfMonthly99());
		merge.setAnyItemOfMonthly100(this.toDomainAnyItemOfMonthly100());
		merge.setAnyItemOfMonthly101(this.toDomainAnyItemOfMonthly101());
		merge.setAnyItemOfMonthly102(this.toDomainAnyItemOfMonthly102());
		merge.setAnyItemOfMonthly103(this.toDomainAnyItemOfMonthly103());
		merge.setAnyItemOfMonthly104(this.toDomainAnyItemOfMonthly104());
		merge.setAnyItemOfMonthly105(this.toDomainAnyItemOfMonthly105());
		merge.setAnyItemOfMonthly106(this.toDomainAnyItemOfMonthly106());
		merge.setAnyItemOfMonthly107(this.toDomainAnyItemOfMonthly107());
		merge.setAnyItemOfMonthly108(this.toDomainAnyItemOfMonthly108());
		merge.setAnyItemOfMonthly109(this.toDomainAnyItemOfMonthly109());
		merge.setAnyItemOfMonthly110(this.toDomainAnyItemOfMonthly110());
		merge.setAnyItemOfMonthly111(this.toDomainAnyItemOfMonthly111());
		merge.setAnyItemOfMonthly112(this.toDomainAnyItemOfMonthly112());
		merge.setAnyItemOfMonthly113(this.toDomainAnyItemOfMonthly113());
		merge.setAnyItemOfMonthly114(this.toDomainAnyItemOfMonthly114());
		merge.setAnyItemOfMonthly115(this.toDomainAnyItemOfMonthly115());
		merge.setAnyItemOfMonthly116(this.toDomainAnyItemOfMonthly116());
		merge.setAnyItemOfMonthly117(this.toDomainAnyItemOfMonthly117());
		merge.setAnyItemOfMonthly118(this.toDomainAnyItemOfMonthly118());
		merge.setAnyItemOfMonthly119(this.toDomainAnyItemOfMonthly119());
		merge.setAnyItemOfMonthly120(this.toDomainAnyItemOfMonthly120());
		merge.setAnyItemOfMonthly121(this.toDomainAnyItemOfMonthly121());
		merge.setAnyItemOfMonthly122(this.toDomainAnyItemOfMonthly122());
		merge.setAnyItemOfMonthly123(this.toDomainAnyItemOfMonthly123());
		merge.setAnyItemOfMonthly124(this.toDomainAnyItemOfMonthly124());
		merge.setAnyItemOfMonthly125(this.toDomainAnyItemOfMonthly125());
		merge.setAnyItemOfMonthly126(this.toDomainAnyItemOfMonthly126());
		merge.setAnyItemOfMonthly127(this.toDomainAnyItemOfMonthly127());
		merge.setAnyItemOfMonthly128(this.toDomainAnyItemOfMonthly128());
		merge.setAnyItemOfMonthly129(this.toDomainAnyItemOfMonthly129());
		merge.setAnyItemOfMonthly130(this.toDomainAnyItemOfMonthly130());
		merge.setAnyItemOfMonthly131(this.toDomainAnyItemOfMonthly131());
		merge.setAnyItemOfMonthly132(this.toDomainAnyItemOfMonthly132());
		merge.setAnyItemOfMonthly133(this.toDomainAnyItemOfMonthly133());
		merge.setAnyItemOfMonthly134(this.toDomainAnyItemOfMonthly134());
		merge.setAnyItemOfMonthly135(this.toDomainAnyItemOfMonthly135());
		merge.setAnyItemOfMonthly136(this.toDomainAnyItemOfMonthly136());
		merge.setAnyItemOfMonthly137(this.toDomainAnyItemOfMonthly137());
		merge.setAnyItemOfMonthly138(this.toDomainAnyItemOfMonthly138());
		merge.setAnyItemOfMonthly139(this.toDomainAnyItemOfMonthly139());
		merge.setAnyItemOfMonthly140(this.toDomainAnyItemOfMonthly140());
		merge.setAnyItemOfMonthly141(this.toDomainAnyItemOfMonthly141());
		merge.setAnyItemOfMonthly142(this.toDomainAnyItemOfMonthly142());
		merge.setAnyItemOfMonthly143(this.toDomainAnyItemOfMonthly143());
		merge.setAnyItemOfMonthly144(this.toDomainAnyItemOfMonthly144());
		merge.setAnyItemOfMonthly145(this.toDomainAnyItemOfMonthly145());
		merge.setAnyItemOfMonthly146(this.toDomainAnyItemOfMonthly146());
		merge.setAnyItemOfMonthly147(this.toDomainAnyItemOfMonthly147());
		merge.setAnyItemOfMonthly148(this.toDomainAnyItemOfMonthly148());
		merge.setAnyItemOfMonthly149(this.toDomainAnyItemOfMonthly149());
		merge.setAnyItemOfMonthly150(this.toDomainAnyItemOfMonthly150());
		merge.setAnyItemOfMonthly151(this.toDomainAnyItemOfMonthly151());
		merge.setAnyItemOfMonthly152(this.toDomainAnyItemOfMonthly152());
		merge.setAnyItemOfMonthly153(this.toDomainAnyItemOfMonthly153());
		merge.setAnyItemOfMonthly154(this.toDomainAnyItemOfMonthly154());
		merge.setAnyItemOfMonthly155(this.toDomainAnyItemOfMonthly155());
		merge.setAnyItemOfMonthly156(this.toDomainAnyItemOfMonthly156());
		merge.setAnyItemOfMonthly157(this.toDomainAnyItemOfMonthly157());
		merge.setAnyItemOfMonthly158(this.toDomainAnyItemOfMonthly158());
		merge.setAnyItemOfMonthly159(this.toDomainAnyItemOfMonthly159());
		merge.setAnyItemOfMonthly160(this.toDomainAnyItemOfMonthly160());
		merge.setAnyItemOfMonthly161(this.toDomainAnyItemOfMonthly161());
		merge.setAnyItemOfMonthly162(this.toDomainAnyItemOfMonthly162());
		merge.setAnyItemOfMonthly163(this.toDomainAnyItemOfMonthly163());
		merge.setAnyItemOfMonthly164(this.toDomainAnyItemOfMonthly164());
		merge.setAnyItemOfMonthly165(this.toDomainAnyItemOfMonthly165());
		merge.setAnyItemOfMonthly166(this.toDomainAnyItemOfMonthly166());
		merge.setAnyItemOfMonthly167(this.toDomainAnyItemOfMonthly167());
		merge.setAnyItemOfMonthly168(this.toDomainAnyItemOfMonthly168());
		merge.setAnyItemOfMonthly169(this.toDomainAnyItemOfMonthly169());
		merge.setAnyItemOfMonthly170(this.toDomainAnyItemOfMonthly170());
		merge.setAnyItemOfMonthly171(this.toDomainAnyItemOfMonthly171());
		merge.setAnyItemOfMonthly172(this.toDomainAnyItemOfMonthly172());
		merge.setAnyItemOfMonthly173(this.toDomainAnyItemOfMonthly173());
		merge.setAnyItemOfMonthly174(this.toDomainAnyItemOfMonthly174());
		merge.setAnyItemOfMonthly175(this.toDomainAnyItemOfMonthly175());
		merge.setAnyItemOfMonthly176(this.toDomainAnyItemOfMonthly176());
		merge.setAnyItemOfMonthly177(this.toDomainAnyItemOfMonthly177());
		merge.setAnyItemOfMonthly178(this.toDomainAnyItemOfMonthly178());
		merge.setAnyItemOfMonthly179(this.toDomainAnyItemOfMonthly179());
		merge.setAnyItemOfMonthly180(this.toDomainAnyItemOfMonthly180());
		merge.setAnyItemOfMonthly181(this.toDomainAnyItemOfMonthly181());
		merge.setAnyItemOfMonthly182(this.toDomainAnyItemOfMonthly182());
		merge.setAnyItemOfMonthly183(this.toDomainAnyItemOfMonthly183());
		merge.setAnyItemOfMonthly184(this.toDomainAnyItemOfMonthly184());
		merge.setAnyItemOfMonthly185(this.toDomainAnyItemOfMonthly185());
		merge.setAnyItemOfMonthly186(this.toDomainAnyItemOfMonthly186());
		merge.setAnyItemOfMonthly187(this.toDomainAnyItemOfMonthly187());
		merge.setAnyItemOfMonthly188(this.toDomainAnyItemOfMonthly188());
		merge.setAnyItemOfMonthly189(this.toDomainAnyItemOfMonthly189());
		merge.setAnyItemOfMonthly190(this.toDomainAnyItemOfMonthly190());
		merge.setAnyItemOfMonthly191(this.toDomainAnyItemOfMonthly191());
		merge.setAnyItemOfMonthly192(this.toDomainAnyItemOfMonthly192());
		merge.setAnyItemOfMonthly193(this.toDomainAnyItemOfMonthly193());
		merge.setAnyItemOfMonthly194(this.toDomainAnyItemOfMonthly194());
		merge.setAnyItemOfMonthly195(this.toDomainAnyItemOfMonthly195());
		merge.setAnyItemOfMonthly196(this.toDomainAnyItemOfMonthly196());
		merge.setAnyItemOfMonthly197(this.toDomainAnyItemOfMonthly197());
		merge.setAnyItemOfMonthly198(this.toDomainAnyItemOfMonthly198());
		merge.setAnyItemOfMonthly199(this.toDomainAnyItemOfMonthly199());
		merge.setAnyItemOfMonthly200(this.toDomainAnyItemOfMonthly200());
		return merge;
	}

}
