package nts.uk.ctx.at.shared.dom.scherec.service;

import java.util.Arrays;
import java.util.Optional;

public enum MonthlyItemList {
	OPTION_1(589, "任意項目1", 1),
	OPTION_2(590, "任意項目2", 2),
	OPTION_3(591, "任意項目3", 3),
	OPTION_4(592, "任意項目4", 4),
	OPTION_5(593, "任意項目5", 5),
	OPTION_6(594, "任意項目6", 6),
	OPTION_7(595, "任意項目7", 7),
	OPTION_8(596, "任意項目8", 8),
	OPTION_9(597, "任意項目9", 9),
	OPTION_10(598, "任意項目10", 10),
	OPTION_11(599, "任意項目11", 11),
	OPTION_12(600, "任意項目12", 12),
	OPTION_13(601, "任意項目13", 13),
	OPTION_14(602, "任意項目14", 14),
	OPTION_15(603, "任意項目15", 15),
	OPTION_16(604, "任意項目16", 16),
	OPTION_17(605, "任意項目17", 17),
	OPTION_18(606, "任意項目18", 18),
	OPTION_19(607, "任意項目19", 19),
	OPTION_20(608, "任意項目20", 20),
	OPTION_21(609, "任意項目21", 21),
	OPTION_22(610, "任意項目22", 22),
	OPTION_23(611, "任意項目23", 23),
	OPTION_24(612, "任意項目24", 24),
	OPTION_25(613, "任意項目25", 25),
	OPTION_26(614, "任意項目26", 26),
	OPTION_27(615, "任意項目27", 27),
	OPTION_28(616, "任意項目28", 28),
	OPTION_29(617, "任意項目29", 29),
	OPTION_30(618, "任意項目30", 30),
	OPTION_31(619, "任意項目31", 31),
	OPTION_32(620, "任意項目32", 32),
	OPTION_33(621, "任意項目33", 33),
	OPTION_34(622, "任意項目34", 34),
	OPTION_35(623, "任意項目35", 35),
	OPTION_36(624, "任意項目36", 36),
	OPTION_37(625, "任意項目37", 37),
	OPTION_38(626, "任意項目38", 38),
	OPTION_39(627, "任意項目39", 39),
	OPTION_40(628, "任意項目40", 40),
	OPTION_41(629, "任意項目41", 41),
	OPTION_42(630, "任意項目42", 42),
	OPTION_43(631, "任意項目43", 43),
	OPTION_44(632, "任意項目44", 44),
	OPTION_45(633, "任意項目45", 45),
	OPTION_46(634, "任意項目46", 46),
	OPTION_47(635, "任意項目47", 47),
	OPTION_48(636, "任意項目48", 48),
	OPTION_49(637, "任意項目49", 49),
	OPTION_50(638, "任意項目50", 50),
	OPTION_51(639, "任意項目51", 51),
	OPTION_52(640, "任意項目52", 52),
	OPTION_53(641, "任意項目53", 53),
	OPTION_54(642, "任意項目54", 54),
	OPTION_55(643, "任意項目55", 55),
	OPTION_56(644, "任意項目56", 56),
	OPTION_57(645, "任意項目57", 57),
	OPTION_58(646, "任意項目58", 58),
	OPTION_59(647, "任意項目59", 59),
	OPTION_60(648, "任意項目60", 60),
	OPTION_61(649, "任意項目61", 61),
	OPTION_62(650, "任意項目62", 62),
	OPTION_63(651, "任意項目63", 63),
	OPTION_64(652, "任意項目64", 64),
	OPTION_65(653, "任意項目65", 65),
	OPTION_66(654, "任意項目66", 66),
	OPTION_67(655, "任意項目67", 67),
	OPTION_68(656, "任意項目68", 68),
	OPTION_69(657, "任意項目69", 69),
	OPTION_70(658, "任意項目70", 70),
	OPTION_71(659, "任意項目71", 71),
	OPTION_72(660, "任意項目72", 72),
	OPTION_73(661, "任意項目73", 73),
	OPTION_74(662, "任意項目74", 74),
	OPTION_75(663, "任意項目75", 75),
	OPTION_76(664, "任意項目76", 76),
	OPTION_77(665, "任意項目77", 77),
	OPTION_78(666, "任意項目78", 78),
	OPTION_79(667, "任意項目79", 79),
	OPTION_80(668, "任意項目80", 80),
	OPTION_81(669, "任意項目81", 81),
	OPTION_82(670, "任意項目82", 82),
	OPTION_83(671, "任意項目83", 83),
	OPTION_84(672, "任意項目84", 84),
	OPTION_85(673, "任意項目85", 85),
	OPTION_86(674, "任意項目86", 86),
	OPTION_87(675, "任意項目87", 87),
	OPTION_88(676, "任意項目88", 88),
	OPTION_89(677, "任意項目89", 89),
	OPTION_90(678, "任意項目90", 90),
	OPTION_91(679, "任意項目91", 91),
	OPTION_92(680, "任意項目92", 92),
	OPTION_93(681, "任意項目93", 93),
	OPTION_94(682, "任意項目94", 94),
	OPTION_95(683, "任意項目95", 95),
	OPTION_96(684, "任意項目96", 96),
	OPTION_97(685, "任意項目97", 97),
	OPTION_98(686, "任意項目98", 98),
	OPTION_99(687, "任意項目99", 99),
	OPTION_100(688, "任意項目100", 100),
	OPTION_101(689, "任意項目101", 101),
	OPTION_102(690, "任意項目102", 102),
	OPTION_103(691, "任意項目103", 103),
	OPTION_104(692, "任意項目104", 104),
	OPTION_105(693, "任意項目105", 105),
	OPTION_106(694, "任意項目106", 106),
	OPTION_107(695, "任意項目107", 107),
	OPTION_108(696, "任意項目108", 108),
	OPTION_109(697, "任意項目109", 109),
	OPTION_110(698, "任意項目110", 110),
	OPTION_111(699, "任意項目111", 111),
	OPTION_112(700, "任意項目112", 112),
	OPTION_113(701, "任意項目113", 113),
	OPTION_114(702, "任意項目114", 114),
	OPTION_115(703, "任意項目115", 115),
	OPTION_116(704, "任意項目116", 116),
	OPTION_117(705, "任意項目117", 117),
	OPTION_118(706, "任意項目118", 118),
	OPTION_119(707, "任意項目119", 119),
	OPTION_120(708, "任意項目120", 120),
	OPTION_121(709, "任意項目121", 121),
	OPTION_122(710, "任意項目122", 122),
	OPTION_123(711, "任意項目123", 123),
	OPTION_124(712, "任意項目124", 124),
	OPTION_125(713, "任意項目125", 125),
	OPTION_126(714, "任意項目126", 126),
	OPTION_127(715, "任意項目127", 127),
	OPTION_128(716, "任意項目128", 128),
	OPTION_129(717, "任意項目129", 129),
	OPTION_130(718, "任意項目130", 130),
	OPTION_131(719, "任意項目131", 131),
	OPTION_132(720, "任意項目132", 132),
	OPTION_133(721, "任意項目133", 133),
	OPTION_134(722, "任意項目134", 134),
	OPTION_135(723, "任意項目135", 135),
	OPTION_136(724, "任意項目136", 136),
	OPTION_137(725, "任意項目137", 137),
	OPTION_138(726, "任意項目138", 138),
	OPTION_139(727, "任意項目139", 139),
	OPTION_140(728, "任意項目140", 140),
	OPTION_141(729, "任意項目141", 141),
	OPTION_142(730, "任意項目142", 142),
	OPTION_143(731, "任意項目143", 143),
	OPTION_144(732, "任意項目144", 144),
	OPTION_145(733, "任意項目145", 145),
	OPTION_146(734, "任意項目146", 146),
	OPTION_147(735, "任意項目147", 147),
	OPTION_148(736, "任意項目148", 148),
	OPTION_149(737, "任意項目149", 149),
	OPTION_150(738, "任意項目150", 150),
	OPTION_151(739, "任意項目151", 151),
	OPTION_152(740, "任意項目152", 152),
	OPTION_153(741, "任意項目153", 153),
	OPTION_154(742, "任意項目154", 154),
	OPTION_155(743, "任意項目155", 155),
	OPTION_156(744, "任意項目156", 156),
	OPTION_157(745, "任意項目157", 157),
	OPTION_158(746, "任意項目158", 158),
	OPTION_159(747, "任意項目159", 159),
	OPTION_160(748, "任意項目160", 160),
	OPTION_161(749, "任意項目161", 161),
	OPTION_162(750, "任意項目162", 162),
	OPTION_163(751, "任意項目163", 163),
	OPTION_164(752, "任意項目164", 164),
	OPTION_165(753, "任意項目165", 165),
	OPTION_166(754, "任意項目166", 166),
	OPTION_167(755, "任意項目167", 167),
	OPTION_168(756, "任意項目168", 168),
	OPTION_169(757, "任意項目169", 169),
	OPTION_170(758, "任意項目170", 170),
	OPTION_171(759, "任意項目171", 171),
	OPTION_172(760, "任意項目172", 172),
	OPTION_173(761, "任意項目173", 173),
	OPTION_174(762, "任意項目174", 174),
	OPTION_175(763, "任意項目175", 175),
	OPTION_176(764, "任意項目176", 176),
	OPTION_177(765, "任意項目177", 177),
	OPTION_178(766, "任意項目178", 178),
	OPTION_179(767, "任意項目179", 179),
	OPTION_180(768, "任意項目180", 180),
	OPTION_181(769, "任意項目181", 181),
	OPTION_182(770, "任意項目182", 182),
	OPTION_183(771, "任意項目183", 183),
	OPTION_184(772, "任意項目184", 184),
	OPTION_185(773, "任意項目185", 185),
	OPTION_186(774, "任意項目186", 186),
	OPTION_187(775, "任意項目187", 187),
	OPTION_188(776, "任意項目188", 188),
	OPTION_189(777, "任意項目189", 189),
	OPTION_190(778, "任意項目190", 190),
	OPTION_191(779, "任意項目191", 191),
	OPTION_192(780, "任意項目192", 192),
	OPTION_193(781, "任意項目193", 193),
	OPTION_194(782, "任意項目194", 194),
	OPTION_195(783, "任意項目195", 195),
	OPTION_196(784, "任意項目196", 196),
	OPTION_197(785, "任意項目197", 197),
	OPTION_198(786, "任意項目198", 198),
	OPTION_199(787, "任意項目199", 199),
	OPTION_200(788, "任意項目200", 200);
	
	public final int itemId;
	public final String itemName;
	public final Integer optionalItemNo;

	private MonthlyItemList(int itemId, String itemName, Integer optionalItemNo) {
		this.itemId = itemId;
		this.itemName = itemName;
		this.optionalItemNo = optionalItemNo;
	}

	public static Optional<MonthlyItemList> getOption(Integer optionalItemNo) {
		return Arrays.stream(MonthlyItemList.values()).filter(x -> x.optionalItemNo == optionalItemNo).findFirst();
	}
}
