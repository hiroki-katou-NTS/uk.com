package nts.uk.ctx.at.shared.app.util.attendanceitem;

import java.util.HashMap;
import java.util.Map;

public class AttendanceItemIdContainer {

	private static Map<String, Integer> ITEM_ID_CONTAINER;
	static {
		ITEM_ID_CONTAINER = new HashMap<>();

		ITEM_ID_CONTAINER.put("日別実績の勤怠時間.医療時間.申送時間-日勤", 750);
		ITEM_ID_CONTAINER.put("日別実績の勤怠時間.医療時間.申送時間-夜勤", 751);
		ITEM_ID_CONTAINER.put("日別実績の勤怠時間.医療時間.控除時間-日勤", 752);
		ITEM_ID_CONTAINER.put("日別実績の勤怠時間.医療時間.控除時間-夜勤", 753);
		ITEM_ID_CONTAINER.put("日別実績の勤怠時間.医療時間.勤務時間-日勤", 754);
		ITEM_ID_CONTAINER.put("日別実績の勤怠時間.医療時間.勤務時間-夜勤", 755);

		ITEM_ID_CONTAINER.put("日別実績の勤怠時間.実績時間.総労働時間.所定外時間.休出時間.休出深夜.法定内休出.時間", 568);
		ITEM_ID_CONTAINER.put("日別実績の勤怠時間.実績時間.総労働時間.所定外時間.休出時間.休出深夜.法定内休出.計算時間", 569);
		ITEM_ID_CONTAINER.put("日別実績の勤怠時間.実績時間.総労働時間.所定外時間.休出時間.休出深夜.法定外休出.時間", 570);
		ITEM_ID_CONTAINER.put("日別実績の勤怠時間.実績時間.総労働時間.所定外時間.休出時間.休出深夜.法定外休出.計算時間", 571);
		ITEM_ID_CONTAINER.put("日別実績の勤怠時間.実績時間.総労働時間.所定外時間.休出時間.休出深夜.祝日休出.時間", 572);
		ITEM_ID_CONTAINER.put("日別実績の勤怠時間.実績時間.総労働時間.所定外時間.休出時間.休出深夜.祝日休出.計算時間", 573);

		ITEM_ID_CONTAINER.put("日別実績の勤怠時間.実績時間.総労働時間.所定外時間.休出時間.休出枠時間.休出時間.時間1", 266);
		ITEM_ID_CONTAINER.put("日別実績の勤怠時間.実績時間.総労働時間.所定外時間.休出時間.休出枠時間.休出時間.計算時間1", 268);
		ITEM_ID_CONTAINER.put("日別実績の勤怠時間.実績時間.総労働時間.所定外時間.休出時間.休出枠時間.休出時間.時間2", 271);
		ITEM_ID_CONTAINER.put("日別実績の勤怠時間.実績時間.総労働時間.所定外時間.休出時間.休出枠時間.休出時間.計算時間2", 273);
		ITEM_ID_CONTAINER.put("日別実績の勤怠時間.実績時間.総労働時間.所定外時間.休出時間.休出枠時間.休出時間.時間3", 276);
		ITEM_ID_CONTAINER.put("日別実績の勤怠時間.実績時間.総労働時間.所定外時間.休出時間.休出枠時間.休出時間.計算時間3", 278);
		ITEM_ID_CONTAINER.put("日別実績の勤怠時間.実績時間.総労働時間.所定外時間.休出時間.休出枠時間.休出時間.時間4", 281);
		ITEM_ID_CONTAINER.put("日別実績の勤怠時間.実績時間.総労働時間.所定外時間.休出時間.休出枠時間.休出時間.計算時間4", 283);
		ITEM_ID_CONTAINER.put("日別実績の勤怠時間.実績時間.総労働時間.所定外時間.休出時間.休出枠時間.休出時間.時間5", 286);
		ITEM_ID_CONTAINER.put("日別実績の勤怠時間.実績時間.総労働時間.所定外時間.休出時間.休出枠時間.休出時間.計算時間5", 288);
		ITEM_ID_CONTAINER.put("日別実績の勤怠時間.実績時間.総労働時間.所定外時間.休出時間.休出枠時間.休出時間.時間6", 291);
		ITEM_ID_CONTAINER.put("日別実績の勤怠時間.実績時間.総労働時間.所定外時間.休出時間.休出枠時間.休出時間.計算時間6", 293);
		ITEM_ID_CONTAINER.put("日別実績の勤怠時間.実績時間.総労働時間.所定外時間.休出時間.休出枠時間.休出時間.時間7", 296);
		ITEM_ID_CONTAINER.put("日別実績の勤怠時間.実績時間.総労働時間.所定外時間.休出時間.休出枠時間.休出時間.計算時間7", 298);
		ITEM_ID_CONTAINER.put("日別実績の勤怠時間.実績時間.総労働時間.所定外時間.休出時間.休出枠時間.休出時間.時間8", 301);
		ITEM_ID_CONTAINER.put("日別実績の勤怠時間.実績時間.総労働時間.所定外時間.休出時間.休出枠時間.休出時間.計算時間8", 303);
		ITEM_ID_CONTAINER.put("日別実績の勤怠時間.実績時間.総労働時間.所定外時間.休出時間.休出枠時間.休出時間.時間9", 306);
		ITEM_ID_CONTAINER.put("日別実績の勤怠時間.実績時間.総労働時間.所定外時間.休出時間.休出枠時間.休出時間.計算時間9", 308);
		ITEM_ID_CONTAINER.put("日別実績の勤怠時間.実績時間.総労働時間.所定外時間.休出時間.休出枠時間.休出時間.時間10", 311);
		ITEM_ID_CONTAINER.put("日別実績の勤怠時間.実績時間.総労働時間.所定外時間.休出時間.休出枠時間.休出時間.計算時間10", 313);
		ITEM_ID_CONTAINER.put("日別実績の勤怠時間.実績時間.総労働時間.所定外時間.休出時間.休出枠時間.振替時間.時間1", 267);
		ITEM_ID_CONTAINER.put("日別実績の勤怠時間.実績時間.総労働時間.所定外時間.休出時間.休出枠時間.振替時間.計算時間1", 269);
		ITEM_ID_CONTAINER.put("日別実績の勤怠時間.実績時間.総労働時間.所定外時間.休出時間.休出枠時間.振替時間.時間2", 272);
		ITEM_ID_CONTAINER.put("日別実績の勤怠時間.実績時間.総労働時間.所定外時間.休出時間.休出枠時間.振替時間.計算時間2", 274);
		ITEM_ID_CONTAINER.put("日別実績の勤怠時間.実績時間.総労働時間.所定外時間.休出時間.休出枠時間.振替時間.時間3", 277);
		ITEM_ID_CONTAINER.put("日別実績の勤怠時間.実績時間.総労働時間.所定外時間.休出時間.休出枠時間.振替時間.計算時間3", 279);
		ITEM_ID_CONTAINER.put("日別実績の勤怠時間.実績時間.総労働時間.所定外時間.休出時間.休出枠時間.振替時間.時間4", 282);
		ITEM_ID_CONTAINER.put("日別実績の勤怠時間.実績時間.総労働時間.所定外時間.休出時間.休出枠時間.振替時間.計算時間4", 284);
		ITEM_ID_CONTAINER.put("日別実績の勤怠時間.実績時間.総労働時間.所定外時間.休出時間.休出枠時間.振替時間.時間5", 287);
		ITEM_ID_CONTAINER.put("日別実績の勤怠時間.実績時間.総労働時間.所定外時間.休出時間.休出枠時間.振替時間.計算時間5", 289);
		ITEM_ID_CONTAINER.put("日別実績の勤怠時間.実績時間.総労働時間.所定外時間.休出時間.休出枠時間.振替時間.時間6", 292);
		ITEM_ID_CONTAINER.put("日別実績の勤怠時間.実績時間.総労働時間.所定外時間.休出時間.休出枠時間.振替時間.計算時間6", 294);
		ITEM_ID_CONTAINER.put("日別実績の勤怠時間.実績時間.総労働時間.所定外時間.休出時間.休出枠時間.振替時間.時間7", 297);
		ITEM_ID_CONTAINER.put("日別実績の勤怠時間.実績時間.総労働時間.所定外時間.休出時間.休出枠時間.振替時間.計算時間7", 299);
		ITEM_ID_CONTAINER.put("日別実績の勤怠時間.実績時間.総労働時間.所定外時間.休出時間.休出枠時間.振替時間.時間8", 302);
		ITEM_ID_CONTAINER.put("日別実績の勤怠時間.実績時間.総労働時間.所定外時間.休出時間.休出枠時間.振替時間.計算時間8", 304);
		ITEM_ID_CONTAINER.put("日別実績の勤怠時間.実績時間.総労働時間.所定外時間.休出時間.休出枠時間.振替時間.時間9", 307);
		ITEM_ID_CONTAINER.put("日別実績の勤怠時間.実績時間.総労働時間.所定外時間.休出時間.休出枠時間.振替時間.計算時間9", 309);
		ITEM_ID_CONTAINER.put("日別実績の勤怠時間.実績時間.総労働時間.所定外時間.休出時間.休出枠時間.振替時間.時間10", 312);
		ITEM_ID_CONTAINER.put("日別実績の勤怠時間.実績時間.総労働時間.所定外時間.休出時間.休出枠時間.振替時間.計算時間10", 314);

		ITEM_ID_CONTAINER.put("日別実績の勤怠時間.実績時間.総労働時間.所定外時間.残業時間.フレックス時間.フレックス時間.時間", 556);
		ITEM_ID_CONTAINER.put("日別実績の勤怠時間.実績時間.総労働時間.所定外時間.残業時間.フレックス時間.フレックス時間.計算時間", 557);

		ITEM_ID_CONTAINER.put("日別実績の勤怠時間.実績時間.総労働時間.所定外時間.残業時間.残業枠時間.振替時間.時間1", 217);
		ITEM_ID_CONTAINER.put("日別実績の勤怠時間.実績時間.総労働時間.所定外時間.残業時間.残業枠時間.振替時間.計算時間1", 219);
		ITEM_ID_CONTAINER.put("日別実績の勤怠時間.実績時間.総労働時間.所定外時間.残業時間.残業枠時間.振替時間.時間2", 222);
		ITEM_ID_CONTAINER.put("日別実績の勤怠時間.実績時間.総労働時間.所定外時間.残業時間.残業枠時間.振替時間.計算時間2", 224);
		ITEM_ID_CONTAINER.put("日別実績の勤怠時間.実績時間.総労働時間.所定外時間.残業時間.残業枠時間.振替時間.時間3", 227);
		ITEM_ID_CONTAINER.put("日別実績の勤怠時間.実績時間.総労働時間.所定外時間.残業時間.残業枠時間.振替時間.計算時間3", 229);
		ITEM_ID_CONTAINER.put("日別実績の勤怠時間.実績時間.総労働時間.所定外時間.残業時間.残業枠時間.振替時間.時間4", 232);
		ITEM_ID_CONTAINER.put("日別実績の勤怠時間.実績時間.総労働時間.所定外時間.残業時間.残業枠時間.振替時間.計算時間4", 234);
		ITEM_ID_CONTAINER.put("日別実績の勤怠時間.実績時間.総労働時間.所定外時間.残業時間.残業枠時間.振替時間.時間5", 237);
		ITEM_ID_CONTAINER.put("日別実績の勤怠時間.実績時間.総労働時間.所定外時間.残業時間.残業枠時間.振替時間.計算時間5", 239);
		ITEM_ID_CONTAINER.put("日別実績の勤怠時間.実績時間.総労働時間.所定外時間.残業時間.残業枠時間.振替時間.時間6", 242);
		ITEM_ID_CONTAINER.put("日別実績の勤怠時間.実績時間.総労働時間.所定外時間.残業時間.残業枠時間.振替時間.計算時間6", 244);
		ITEM_ID_CONTAINER.put("日別実績の勤怠時間.実績時間.総労働時間.所定外時間.残業時間.残業枠時間.振替時間.時間7", 247);
		ITEM_ID_CONTAINER.put("日別実績の勤怠時間.実績時間.総労働時間.所定外時間.残業時間.残業枠時間.振替時間.計算時間7", 249);
		ITEM_ID_CONTAINER.put("日別実績の勤怠時間.実績時間.総労働時間.所定外時間.残業時間.残業枠時間.振替時間.時間8", 252);
		ITEM_ID_CONTAINER.put("日別実績の勤怠時間.実績時間.総労働時間.所定外時間.残業時間.残業枠時間.振替時間.計算時間8", 254);
		ITEM_ID_CONTAINER.put("日別実績の勤怠時間.実績時間.総労働時間.所定外時間.残業時間.残業枠時間.振替時間.時間9", 257);
		ITEM_ID_CONTAINER.put("日別実績の勤怠時間.実績時間.総労働時間.所定外時間.残業時間.残業枠時間.振替時間.計算時間9", 259);
		ITEM_ID_CONTAINER.put("日別実績の勤怠時間.実績時間.総労働時間.所定外時間.残業時間.残業枠時間.振替時間.時間10", 262);
		ITEM_ID_CONTAINER.put("日別実績の勤怠時間.実績時間.総労働時間.所定外時間.残業時間.残業枠時間.振替時間.計算時間10", 264);
		ITEM_ID_CONTAINER.put("日別実績の勤怠時間.実績時間.総労働時間.所定外時間.残業時間.残業枠時間.残業時間.時間1", 216);
		ITEM_ID_CONTAINER.put("日別実績の勤怠時間.実績時間.総労働時間.所定外時間.残業時間.残業枠時間.残業時間.計算時間1", 218);
		ITEM_ID_CONTAINER.put("日別実績の勤怠時間.実績時間.総労働時間.所定外時間.残業時間.残業枠時間.残業時間.時間2", 221);
		ITEM_ID_CONTAINER.put("日別実績の勤怠時間.実績時間.総労働時間.所定外時間.残業時間.残業枠時間.残業時間.計算時間2", 223);
		ITEM_ID_CONTAINER.put("日別実績の勤怠時間.実績時間.総労働時間.所定外時間.残業時間.残業枠時間.残業時間.時間3", 226);
		ITEM_ID_CONTAINER.put("日別実績の勤怠時間.実績時間.総労働時間.所定外時間.残業時間.残業枠時間.残業時間.計算時間3", 228);
		ITEM_ID_CONTAINER.put("日別実績の勤怠時間.実績時間.総労働時間.所定外時間.残業時間.残業枠時間.残業時間.時間4", 231);
		ITEM_ID_CONTAINER.put("日別実績の勤怠時間.実績時間.総労働時間.所定外時間.残業時間.残業枠時間.残業時間.計算時間4", 233);
		ITEM_ID_CONTAINER.put("日別実績の勤怠時間.実績時間.総労働時間.所定外時間.残業時間.残業枠時間.残業時間.時間5", 236);
		ITEM_ID_CONTAINER.put("日別実績の勤怠時間.実績時間.総労働時間.所定外時間.残業時間.残業枠時間.残業時間.計算時間5", 238);
		ITEM_ID_CONTAINER.put("日別実績の勤怠時間.実績時間.総労働時間.所定外時間.残業時間.残業枠時間.残業時間.時間6", 241);
		ITEM_ID_CONTAINER.put("日別実績の勤怠時間.実績時間.総労働時間.所定外時間.残業時間.残業枠時間.残業時間.計算時間6", 243);
		ITEM_ID_CONTAINER.put("日別実績の勤怠時間.実績時間.総労働時間.所定外時間.残業時間.残業枠時間.残業時間.時間7", 246);
		ITEM_ID_CONTAINER.put("日別実績の勤怠時間.実績時間.総労働時間.所定外時間.残業時間.残業枠時間.残業時間.計算時間7", 248);
		ITEM_ID_CONTAINER.put("日別実績の勤怠時間.実績時間.総労働時間.所定外時間.残業時間.残業枠時間.残業時間.時間8", 251);
		ITEM_ID_CONTAINER.put("日別実績の勤怠時間.実績時間.総労働時間.所定外時間.残業時間.残業枠時間.残業時間.計算時間8", 253);
		ITEM_ID_CONTAINER.put("日別実績の勤怠時間.実績時間.総労働時間.所定外時間.残業時間.残業枠時間.残業時間.時間9", 256);
		ITEM_ID_CONTAINER.put("日別実績の勤怠時間.実績時間.総労働時間.所定外時間.残業時間.残業枠時間.残業時間.計算時間9", 258);
		ITEM_ID_CONTAINER.put("日別実績の勤怠時間.実績時間.総労働時間.所定外時間.残業時間.残業枠時間.残業時間.時間10", 261);
		ITEM_ID_CONTAINER.put("日別実績の勤怠時間.実績時間.総労働時間.所定外時間.残業時間.残業枠時間.残業時間.計算時間10", 263);

		ITEM_ID_CONTAINER.put("日別実績の勤怠時間.実績時間.総労働時間.所定外時間.残業時間.所定外深夜時間.時間.時間", 566);
		ITEM_ID_CONTAINER.put("日別実績の勤怠時間.実績時間.総労働時間.所定外時間.残業時間.所定外深夜時間.時間.計算時間", 567);

		ITEM_ID_CONTAINER.put("日別実績の勤怠時間.実績時間.総労働時間.所定外時間.所定外深夜時間.時間.時間", 563);
		ITEM_ID_CONTAINER.put("日別実績の勤怠時間.実績時間.総労働時間.所定外時間.所定外深夜時間.時間.計算時間", 564);

		ITEM_ID_CONTAINER.put("日別実績の勤怠時間.実績時間.総労働時間.所定内時間.所定内深夜時間.時間", 561);
		ITEM_ID_CONTAINER.put("日別実績の勤怠時間.実績時間.総労働時間.所定内時間.所定内深夜時間.計算時間", 562);

		ITEM_ID_CONTAINER.put("日別実績の勤怠時間.実績時間.総労働時間.早退時間.早退時間.時間1", 604);
		ITEM_ID_CONTAINER.put("日別実績の勤怠時間.実績時間.総労働時間.早退時間.早退時間.計算時間1", 605);
		ITEM_ID_CONTAINER.put("日別実績の勤怠時間.実績時間.総労働時間.早退時間.早退時間.時間2", 610);
		ITEM_ID_CONTAINER.put("日別実績の勤怠時間.実績時間.総労働時間.早退時間.早退時間.計算時間2", 611);
		
		ITEM_ID_CONTAINER.put("日別実績の勤怠時間.実績時間.総労働時間.早退時間.早退控除時間.時間1", 606);
		ITEM_ID_CONTAINER.put("日別実績の勤怠時間.実績時間.総労働時間.早退時間.早退控除時間.時間2", 612);

		ITEM_ID_CONTAINER.put("日別実績の勤怠時間.実績時間.総労働時間.遅刻時間.遅刻控除時間.時間1", 594);
		ITEM_ID_CONTAINER.put("日別実績の勤怠時間.実績時間.総労働時間.遅刻時間.遅刻控除時間.時間2", 600);
		ITEM_ID_CONTAINER.put("日別実績の勤怠時間.実績時間.総労働時間.遅刻時間.遅刻時間.時間1", 592);
		ITEM_ID_CONTAINER.put("日別実績の勤怠時間.実績時間.総労働時間.遅刻時間.遅刻時間.計算時間1", 593);
		ITEM_ID_CONTAINER.put("日別実績の勤怠時間.実績時間.総労働時間.遅刻時間.遅刻時間.時間2", 598);
		ITEM_ID_CONTAINER.put("日別実績の勤怠時間.実績時間.総労働時間.遅刻時間.遅刻時間.計算時間2", 599);

		ITEM_ID_CONTAINER.put("日別実績の勤怠時間.実績時間.総労働時間.外出時間帯.計上用合計時間.合計時間.時間-私用", 463);
		ITEM_ID_CONTAINER.put("日別実績の勤怠時間.実績時間.総労働時間.外出時間帯.計上用合計時間.合計時間.計算時間-私用", 467);
		ITEM_ID_CONTAINER.put("日別実績の勤怠時間.実績時間.総労働時間.外出時間帯.計上用合計時間.合計時間.時間-公用", 476);
		ITEM_ID_CONTAINER.put("日別実績の勤怠時間.実績時間.総労働時間.外出時間帯.計上用合計時間.合計時間.計算時間-公用", 479);
		ITEM_ID_CONTAINER.put("日別実績の勤怠時間.実績時間.総労働時間.外出時間帯.計上用合計時間.合計時間.時間-有償", 483);
		ITEM_ID_CONTAINER.put("日別実績の勤怠時間.実績時間.総労働時間.外出時間帯.計上用合計時間.合計時間.計算時間-有償", 486);
		ITEM_ID_CONTAINER.put("日別実績の勤怠時間.実績時間.総労働時間.外出時間帯.計上用合計時間.合計時間.時間-組合", 490);
		ITEM_ID_CONTAINER.put("日別実績の勤怠時間.実績時間.総労働時間.外出時間帯.計上用合計時間.合計時間.計算時間-組合", 494);
		ITEM_ID_CONTAINER.put("日別実績の勤怠時間.実績時間.総労働時間.外出時間帯.計上用合計時間.法定外合計時間.時間-私用", 465);
		ITEM_ID_CONTAINER.put("日別実績の勤怠時間.実績時間.総労働時間.外出時間帯.計上用合計時間.法定外合計時間.計算時間-私用", 469);
		ITEM_ID_CONTAINER.put("日別実績の勤怠時間.実績時間.総労働時間.外出時間帯.計上用合計時間.法定外合計時間.時間-公用", 478);
		ITEM_ID_CONTAINER.put("日別実績の勤怠時間.実績時間.総労働時間.外出時間帯.計上用合計時間.法定外合計時間.計算時間-公用", 481);
		ITEM_ID_CONTAINER.put("日別実績の勤怠時間.実績時間.総労働時間.外出時間帯.計上用合計時間.法定外合計時間.時間-有償", 485);
		ITEM_ID_CONTAINER.put("日別実績の勤怠時間.実績時間.総労働時間.外出時間帯.計上用合計時間.法定外合計時間.計算時間-有償", 488);
		ITEM_ID_CONTAINER.put("日別実績の勤怠時間.実績時間.総労働時間.外出時間帯.計上用合計時間.法定外合計時間.時間-組合", 492);
		ITEM_ID_CONTAINER.put("日別実績の勤怠時間.実績時間.総労働時間.外出時間帯.計上用合計時間.法定外合計時間.計算時間-組合", 496);
		ITEM_ID_CONTAINER.put("日別実績の勤怠時間.実績時間.総労働時間.外出時間帯.計上用合計時間.法定内合計時間.時間-私用", 464);
		ITEM_ID_CONTAINER.put("日別実績の勤怠時間.実績時間.総労働時間.外出時間帯.計上用合計時間.法定内合計時間.計算時間-私用", 468);
		ITEM_ID_CONTAINER.put("日別実績の勤怠時間.実績時間.総労働時間.外出時間帯.計上用合計時間.法定内合計時間.時間-公用", 477);
		ITEM_ID_CONTAINER.put("日別実績の勤怠時間.実績時間.総労働時間.外出時間帯.計上用合計時間.法定内合計時間.計算時間-公用", 480);
		ITEM_ID_CONTAINER.put("日別実績の勤怠時間.実績時間.総労働時間.外出時間帯.計上用合計時間.法定内合計時間.時間-有償", 484);
		ITEM_ID_CONTAINER.put("日別実績の勤怠時間.実績時間.総労働時間.外出時間帯.計上用合計時間.法定内合計時間.計算時間-有償", 487);
		ITEM_ID_CONTAINER.put("日別実績の勤怠時間.実績時間.総労働時間.外出時間帯.計上用合計時間.法定内合計時間.時間-組合", 491);
		ITEM_ID_CONTAINER.put("日別実績の勤怠時間.実績時間.総労働時間.外出時間帯.計上用合計時間.法定内合計時間.計算時間-組合", 495);
		ITEM_ID_CONTAINER.put("日別実績の勤怠時間.実績時間.総労働時間.加給時間.加給時間.加給時間.時間1", 316);
		ITEM_ID_CONTAINER.put("日別実績の勤怠時間.実績時間.総労働時間.加給時間.加給時間.加給時間.時間2", 317);
		ITEM_ID_CONTAINER.put("日別実績の勤怠時間.実績時間.総労働時間.加給時間.加給時間.加給時間..時間3", 318);
		ITEM_ID_CONTAINER.put("日別実績の勤怠時間.実績時間.総労働時間.加給時間.加給時間.加給時間..時間4", 319);
		ITEM_ID_CONTAINER.put("日別実績の勤怠時間.実績時間.総労働時間.加給時間.加給時間.加給時間..時間5", 320);
		ITEM_ID_CONTAINER.put("日別実績の勤怠時間.実績時間.総労働時間.加給時間.加給時間.加給時間..時間6", 321);
		ITEM_ID_CONTAINER.put("日別実績の勤怠時間.実績時間.総労働時間.加給時間.加給時間.加給時間..時間7", 322);
		ITEM_ID_CONTAINER.put("日別実績の勤怠時間.実績時間.総労働時間.加給時間.加給時間.加給時間..時間8", 323);
		ITEM_ID_CONTAINER.put("日別実績の勤怠時間.実績時間.総労働時間.加給時間.加給時間.加給時間..時間9", 324);
		ITEM_ID_CONTAINER.put("日別実績の勤怠時間.実績時間.総労働時間.加給時間.加給時間.加給時間..時間10", 325);
		ITEM_ID_CONTAINER.put("日別実績の勤怠時間.実績時間.総労働時間.加給時間.加給時間.加給時間..計算時間1", 326);
		ITEM_ID_CONTAINER.put("日別実績の勤怠時間.実績時間.総労働時間.加給時間.加給時間.加給時間.計算時間2", 327);
		ITEM_ID_CONTAINER.put("日別実績の勤怠時間.実績時間.総労働時間.加給時間.加給時間.加給時間.計算時間3", 328);
		ITEM_ID_CONTAINER.put("日別実績の勤怠時間.実績時間.総労働時間.加給時間.加給時間.加給時間.計算時間4", 329);
		ITEM_ID_CONTAINER.put("日別実績の勤怠時間.実績時間.総労働時間.加給時間.加給時間.加給時間.計算時間5", 330);
		ITEM_ID_CONTAINER.put("日別実績の勤怠時間.実績時間.総労働時間.加給時間.加給時間.加給時間.計算時間6", 331);
		ITEM_ID_CONTAINER.put("日別実績の勤怠時間.実績時間.総労働時間.加給時間.加給時間.加給時間.計算時間7", 332);
		ITEM_ID_CONTAINER.put("日別実績の勤怠時間.実績時間.総労働時間.加給時間.加給時間.加給時間.計算時間8", 333);
		ITEM_ID_CONTAINER.put("日別実績の勤怠時間.実績時間.総労働時間.加給時間.加給時間.加給時間.計算時間9", 334);
		ITEM_ID_CONTAINER.put("日別実績の勤怠時間.実績時間.総労働時間.加給時間.加給時間.加給時間.計算時間10", 335);
		ITEM_ID_CONTAINER.put("日別実績の勤怠時間.実績時間.総労働時間.加給時間.加給時間.法定外加給時間.時間1", 346);
		ITEM_ID_CONTAINER.put("日別実績の勤怠時間.実績時間.総労働時間.加給時間.加給時間.法定外加給時間.時間2", 347);
		ITEM_ID_CONTAINER.put("日別実績の勤怠時間.実績時間.総労働時間.加給時間.加給時間.法定外加給時間.時間3", 348);
		ITEM_ID_CONTAINER.put("日別実績の勤怠時間.実績時間.総労働時間.加給時間.加給時間.法定外加給時間.時間4", 349);
		ITEM_ID_CONTAINER.put("日別実績の勤怠時間.実績時間.総労働時間.加給時間.加給時間.法定外加給時間.時間5", 350);
		ITEM_ID_CONTAINER.put("日別実績の勤怠時間.実績時間.総労働時間.加給時間.加給時間.法定外加給時間.時間6", 351);
		ITEM_ID_CONTAINER.put("日別実績の勤怠時間.実績時間.総労働時間.加給時間.加給時間.法定外加給時間.時間7", 352);
		ITEM_ID_CONTAINER.put("日別実績の勤怠時間.実績時間.総労働時間.加給時間.加給時間.法定外加給時間.時間8", 353);
		ITEM_ID_CONTAINER.put("日別実績の勤怠時間.実績時間.総労働時間.加給時間.加給時間.法定外加給時間.時間9", 354);
		ITEM_ID_CONTAINER.put("日別実績の勤怠時間.実績時間.総労働時間.加給時間.加給時間.法定外加給時間.時間10", 355);
		ITEM_ID_CONTAINER.put("日別実績の勤怠時間.実績時間.総労働時間.加給時間.加給時間.法定外加給時間.計算時間1", 356);
		ITEM_ID_CONTAINER.put("日別実績の勤怠時間.実績時間.総労働時間.加給時間.加給時間.法定外加給時間.計算時間2", 357);
		ITEM_ID_CONTAINER.put("日別実績の勤怠時間.実績時間.総労働時間.加給時間.加給時間.法定外加給時間.計算時間3", 358);
		ITEM_ID_CONTAINER.put("日別実績の勤怠時間.実績時間.総労働時間.加給時間.加給時間.法定外加給時間.計算時間4", 359);
		ITEM_ID_CONTAINER.put("日別実績の勤怠時間.実績時間.総労働時間.加給時間.加給時間.法定外加給時間.計算時間5", 360);
		ITEM_ID_CONTAINER.put("日別実績の勤怠時間.実績時間.総労働時間.加給時間.加給時間.法定外加給時間.計算時間6", 361);
		ITEM_ID_CONTAINER.put("日別実績の勤怠時間.実績時間.総労働時間.加給時間.加給時間.法定外加給時間.計算時間7", 362);
		ITEM_ID_CONTAINER.put("日別実績の勤怠時間.実績時間.総労働時間.加給時間.加給時間.法定外加給時間.計算時間8", 363);
		ITEM_ID_CONTAINER.put("日別実績の勤怠時間.実績時間.総労働時間.加給時間.加給時間.法定外加給時間.計算時間9", 364);
		ITEM_ID_CONTAINER.put("日別実績の勤怠時間.実績時間.総労働時間.加給時間.加給時間.法定外加給時間.計算時間10", 365);
		ITEM_ID_CONTAINER.put("日別実績の勤怠時間.実績時間.総労働時間.加給時間.加給時間.法定内加給時間.時間1", 336);
		ITEM_ID_CONTAINER.put("日別実績の勤怠時間.実績時間.総労働時間.加給時間.加給時間.法定内加給時間.時間2", 337);
		ITEM_ID_CONTAINER.put("日別実績の勤怠時間.実績時間.総労働時間.加給時間.加給時間.法定内加給時間.時間3", 338);
		ITEM_ID_CONTAINER.put("日別実績の勤怠時間.実績時間.総労働時間.加給時間.加給時間.法定内加給時間.時間4", 339);
		ITEM_ID_CONTAINER.put("日別実績の勤怠時間.実績時間.総労働時間.加給時間.加給時間.法定内加給時間.時間5", 340);
		ITEM_ID_CONTAINER.put("日別実績の勤怠時間.実績時間.総労働時間.加給時間.加給時間.法定内加給時間.時間6", 341);
		ITEM_ID_CONTAINER.put("日別実績の勤怠時間.実績時間.総労働時間.加給時間.加給時間.法定内加給時間.時間7", 342);
		ITEM_ID_CONTAINER.put("日別実績の勤怠時間.実績時間.総労働時間.加給時間.加給時間.法定内加給時間.時間8", 343);
		ITEM_ID_CONTAINER.put("日別実績の勤怠時間.実績時間.総労働時間.加給時間.加給時間.法定内加給時間.時間9", 344);
		ITEM_ID_CONTAINER.put("日別実績の勤怠時間.実績時間.総労働時間.加給時間.加給時間.法定内加給時間.時間10", 345);

		ITEM_ID_CONTAINER.put("日別実績の勤怠時間.実績時間.総労働時間.加給時間.特定加給時間.加給時間.時間1", 366);
		ITEM_ID_CONTAINER.put("日別実績の勤怠時間.実績時間.総労働時間.加給時間.特定加給時間.加給時間.時間2", 367);
		ITEM_ID_CONTAINER.put("日別実績の勤怠時間.実績時間.総労働時間.加給時間.特定加給時間.加給時間.時間3", 368);
		ITEM_ID_CONTAINER.put("日別実績の勤怠時間.実績時間.総労働時間.加給時間.特定加給時間.加給時間.時間4", 369);
		ITEM_ID_CONTAINER.put("日別実績の勤怠時間.実績時間.総労働時間.加給時間.特定加給時間.加給時間.時間5", 370);
		ITEM_ID_CONTAINER.put("日別実績の勤怠時間.実績時間.総労働時間.加給時間.特定加給時間.加給時間.時間6", 371);
		ITEM_ID_CONTAINER.put("日別実績の勤怠時間.実績時間.総労働時間.加給時間.特定加給時間.加給時間.時間7", 372);
		ITEM_ID_CONTAINER.put("日別実績の勤怠時間.実績時間.総労働時間.加給時間.特定加給時間.加給時間.時間8", 373);
		ITEM_ID_CONTAINER.put("日別実績の勤怠時間.実績時間.総労働時間.加給時間.特定加給時間.加給時間.時間9", 374);
		ITEM_ID_CONTAINER.put("日別実績の勤怠時間.実績時間.総労働時間.加給時間.特定加給時間.加給時間.時間10", 375);
		ITEM_ID_CONTAINER.put("日別実績の勤怠時間.実績時間.総労働時間.加給時間.特定加給時間.加給時間.計算時間1", 376);
		ITEM_ID_CONTAINER.put("日別実績の勤怠時間.実績時間.総労働時間.加給時間.特定加給時間.加給時間.計算時間2", 377);
		ITEM_ID_CONTAINER.put("日別実績の勤怠時間.実績時間.総労働時間.加給時間.特定加給時間.加給時間.計算時間3", 378);
		ITEM_ID_CONTAINER.put("日別実績の勤怠時間.実績時間.総労働時間.加給時間.特定加給時間.加給時間.計算時間4", 379);
		ITEM_ID_CONTAINER.put("日別実績の勤怠時間.実績時間.総労働時間.加給時間.特定加給時間.加給時間.計算時間5", 380);
		ITEM_ID_CONTAINER.put("日別実績の勤怠時間.実績時間.総労働時間.加給時間.特定加給時間.加給時間.計算時間6", 381);
		ITEM_ID_CONTAINER.put("日別実績の勤怠時間.実績時間.総労働時間.加給時間.特定加給時間.加給時間.計算時間7", 382);
		ITEM_ID_CONTAINER.put("日別実績の勤怠時間.実績時間.総労働時間.加給時間.特定加給時間.加給時間.計算時間8", 383);
		ITEM_ID_CONTAINER.put("日別実績の勤怠時間.実績時間.総労働時間.加給時間.特定加給時間.加給時間.計算時間9", 384);
		ITEM_ID_CONTAINER.put("日別実績の勤怠時間.実績時間.総労働時間.加給時間.特定加給時間.加給時間.計算時間10", 385);
		ITEM_ID_CONTAINER.put("日別実績の勤怠時間.実績時間.総労働時間.加給時間.特定加給時間.法定外加給時間.時間1", 396);
		ITEM_ID_CONTAINER.put("日別実績の勤怠時間.実績時間.総労働時間.加給時間.特定加給時間.法定外加給時間.時間2", 397);
		ITEM_ID_CONTAINER.put("日別実績の勤怠時間.実績時間.総労働時間.加給時間.特定加給時間.法定外加給時間.時間3", 398);
		ITEM_ID_CONTAINER.put("日別実績の勤怠時間.実績時間.総労働時間.加給時間.特定加給時間.法定外加給時間.時間4", 399);
		ITEM_ID_CONTAINER.put("日別実績の勤怠時間.実績時間.総労働時間.加給時間.特定加給時間.法定外加給時間.時間5", 400);
		ITEM_ID_CONTAINER.put("日別実績の勤怠時間.実績時間.総労働時間.加給時間.特定加給時間.法定外加給時間.時間6", 401);
		ITEM_ID_CONTAINER.put("日別実績の勤怠時間.実績時間.総労働時間.加給時間.特定加給時間.法定外加給時間.時間7", 402);
		ITEM_ID_CONTAINER.put("日別実績の勤怠時間.実績時間.総労働時間.加給時間.特定加給時間.法定外加給時間.時間8", 403);
		ITEM_ID_CONTAINER.put("日別実績の勤怠時間.実績時間.総労働時間.加給時間.特定加給時間.法定外加給時間.時間9", 404);
		ITEM_ID_CONTAINER.put("日別実績の勤怠時間.実績時間.総労働時間.加給時間.特定加給時間.法定外加給時間.時間10", 405);
		ITEM_ID_CONTAINER.put("日別実績の勤怠時間.実績時間.総労働時間.加給時間.特定加給時間.法定外加給時間.計算時間1", 406);
		ITEM_ID_CONTAINER.put("日別実績の勤怠時間.実績時間.総労働時間.加給時間.特定加給時間.法定外加給時間.計算時間2", 407);
		ITEM_ID_CONTAINER.put("日別実績の勤怠時間.実績時間.総労働時間.加給時間.特定加給時間.法定外加給時間.計算時間3", 408);
		ITEM_ID_CONTAINER.put("日別実績の勤怠時間.実績時間.総労働時間.加給時間.特定加給時間.法定外加給時間.計算時間4", 409);
		ITEM_ID_CONTAINER.put("日別実績の勤怠時間.実績時間.総労働時間.加給時間.特定加給時間.法定外加給時間.計算時間5", 410);
		ITEM_ID_CONTAINER.put("日別実績の勤怠時間.実績時間.総労働時間.加給時間.特定加給時間.法定外加給時間.計算時間6", 411);
		ITEM_ID_CONTAINER.put("日別実績の勤怠時間.実績時間.総労働時間.加給時間.特定加給時間.法定外加給時間.計算時間7", 412);
		ITEM_ID_CONTAINER.put("日別実績の勤怠時間.実績時間.総労働時間.加給時間.特定加給時間.法定外加給時間.計算時間8", 413);
		ITEM_ID_CONTAINER.put("日別実績の勤怠時間.実績時間.総労働時間.加給時間.特定加給時間.法定外加給時間.計算時間9", 414);
		ITEM_ID_CONTAINER.put("日別実績の勤怠時間.実績時間.総労働時間.加給時間.特定加給時間.法定外加給時間.計算時間10", 415);
		ITEM_ID_CONTAINER.put("日別実績の勤怠時間.実績時間.総労働時間.加給時間.特定加給時間.法定内加給時間.時間1", 386);
		ITEM_ID_CONTAINER.put("日別実績の勤怠時間.実績時間.総労働時間.加給時間.特定加給時間.法定内加給時間.時間2", 387);
		ITEM_ID_CONTAINER.put("日別実績の勤怠時間.実績時間.総労働時間.加給時間.特定加給時間.法定内加給時間.時間3", 388);
		ITEM_ID_CONTAINER.put("日別実績の勤怠時間.実績時間.総労働時間.加給時間.特定加給時間.法定内加給時間.時間4", 389);
		ITEM_ID_CONTAINER.put("日別実績の勤怠時間.実績時間.総労働時間.加給時間.特定加給時間.法定内加給時間.時間5", 390);
		ITEM_ID_CONTAINER.put("日別実績の勤怠時間.実績時間.総労働時間.加給時間.特定加給時間.法定内加給時間.時間6", 391);
		ITEM_ID_CONTAINER.put("日別実績の勤怠時間.実績時間.総労働時間.加給時間.特定加給時間.法定内加給時間.時間7", 392);
		ITEM_ID_CONTAINER.put("日別実績の勤怠時間.実績時間.総労働時間.加給時間.特定加給時間.法定内加給時間.時間8", 393);
		ITEM_ID_CONTAINER.put("日別実績の勤怠時間.実績時間.総労働時間.加給時間.特定加給時間.法定内加給時間.時間9", 394);
		ITEM_ID_CONTAINER.put("日別実績の勤怠時間.実績時間.総労働時間.加給時間.特定加給時間.法定内加給時間.時間10", 395);

		ITEM_ID_CONTAINER.put("日別実績の勤務情報.勤務実績の勤務情報.勤務種類コード", 28);
		ITEM_ID_CONTAINER.put("日別実績の勤務情報.勤務実績の勤務情報.就業時間帯コード", 29);
		ITEM_ID_CONTAINER.put("日別実績の勤務情報.勤務予定の勤務情報.勤務種類コード", 1);
		ITEM_ID_CONTAINER.put("日別実績の勤務情報.勤務予定の勤務情報.就業時間帯コード", 2);

		ITEM_ID_CONTAINER.put("日別実績の入退門.入退門時刻.終了時間.場所コード1", 76);
		ITEM_ID_CONTAINER.put("日別実績の入退門.入退門時刻.終了時間.場所コード2", 80);
		ITEM_ID_CONTAINER.put("日別実績の入退門.入退門時刻.終了時間.場所コード3", 84);
		ITEM_ID_CONTAINER.put("日別実績の入退門.入退門時刻.終了時間.時刻1", 77);
		ITEM_ID_CONTAINER.put("日別実績の入退門.入退門時刻.終了時間.時刻2", 81);
		ITEM_ID_CONTAINER.put("日別実績の入退門.入退門時刻.終了時間.時刻3", 85);
		ITEM_ID_CONTAINER.put("日別実績の入退門.入退門時刻.開始時間.場所コード1", 74);
		ITEM_ID_CONTAINER.put("日別実績の入退門.入退門時刻.開始時間.場所コード2", 78);
		ITEM_ID_CONTAINER.put("日別実績の入退門.入退門時刻.開始時間.場所コード3", 82);
		ITEM_ID_CONTAINER.put("日別実績の入退門.入退門時刻.開始時間.時刻1", 75);
		ITEM_ID_CONTAINER.put("日別実績の入退門.入退門時刻.開始時間.時刻2", 79);
		ITEM_ID_CONTAINER.put("日別実績の入退門.入退門時刻.開始時間.時刻3", 83);

		ITEM_ID_CONTAINER.put("日別実績の出退勤.出退勤.出勤.打刻.場所コード1", 30);
		ITEM_ID_CONTAINER.put("日別実績の出退勤.出退勤.出勤.打刻.時刻1", 31);
		ITEM_ID_CONTAINER.put("日別実績の出退勤.出退勤.出勤.打刻.丸め後の時刻1", 32);
		ITEM_ID_CONTAINER.put("日別実績の出退勤.出退勤.出勤.実打刻.場所コード1", 36);
		ITEM_ID_CONTAINER.put("日別実績の出退勤.出退勤.出勤.実打刻.時刻1", 37);
		ITEM_ID_CONTAINER.put("日別実績の出退勤.出退勤.出勤.打刻.場所コード2", 40);
		ITEM_ID_CONTAINER.put("日別実績の出退勤.出退勤.出勤.打刻.時刻2", 41);
		ITEM_ID_CONTAINER.put("日別実績の出退勤.出退勤.出勤.打刻.丸め後の時刻2", 42);
		ITEM_ID_CONTAINER.put("日別実績の出退勤.出退勤.出勤.実打刻.場所コード2", 46);
		ITEM_ID_CONTAINER.put("日別実績の出退勤.出退勤.出勤.実打刻.時刻2", 47);
		ITEM_ID_CONTAINER.put("日別実績の出退勤.出退勤.退勤.打刻.場所コード1", 33);
		ITEM_ID_CONTAINER.put("日別実績の出退勤.出退勤.退勤.打刻.時刻1", 34);
		ITEM_ID_CONTAINER.put("日別実績の出退勤.出退勤.退勤.打刻.丸め後の時刻1", 35);
		ITEM_ID_CONTAINER.put("日別実績の出退勤.出退勤.退勤.実打刻.場所コード1", 38);
		ITEM_ID_CONTAINER.put("日別実績の出退勤.出退勤.退勤.実打刻.時刻1", 39);
		ITEM_ID_CONTAINER.put("日別実績の出退勤.出退勤.退勤.打刻.場所コード2", 43);
		ITEM_ID_CONTAINER.put("日別実績の出退勤.出退勤.退勤.打刻.時刻2", 44);
		ITEM_ID_CONTAINER.put("日別実績の出退勤.出退勤.退勤.打刻.丸め後の時刻2", 45);
		ITEM_ID_CONTAINER.put("日別実績の出退勤.出退勤.退勤.実打刻.場所コード2", 48);
		ITEM_ID_CONTAINER.put("日別実績の出退勤.出退勤.退勤.実打刻.時刻2", 49);

		ITEM_ID_CONTAINER.put("日別実績の臨時出退勤.出退勤.出勤.実打刻.場所コード1", 54);
		ITEM_ID_CONTAINER.put("日別実績の臨時出退勤.出退勤.出勤.実打刻.時刻1", 55);
		ITEM_ID_CONTAINER.put("日別実績の臨時出退勤.出退勤.出勤.実打刻.場所コード2", 62);
		ITEM_ID_CONTAINER.put("日別実績の臨時出退勤.出退勤.出勤.実打刻.時刻2", 63);
		ITEM_ID_CONTAINER.put("日別実績の臨時出退勤.出退勤.出勤.実打刻.場所コード3", 70);
		ITEM_ID_CONTAINER.put("日別実績の臨時出退勤.出退勤.出勤.実打刻.時刻3", 71);
		ITEM_ID_CONTAINER.put("日別実績の臨時出退勤.出退勤.出勤.打刻.場所コード1", 50);
		ITEM_ID_CONTAINER.put("日別実績の臨時出退勤.出退勤.出勤.打刻.時刻1", 51);
		ITEM_ID_CONTAINER.put("日別実績の臨時出退勤.出退勤.出勤.打刻.場所コード2", 58);
		ITEM_ID_CONTAINER.put("日別実績の臨時出退勤.出退勤.出勤.打刻.時刻2", 59);
		ITEM_ID_CONTAINER.put("日別実績の臨時出退勤.出退勤.出勤.打刻.場所コード3", 66);
		ITEM_ID_CONTAINER.put("日別実績の臨時出退勤.出退勤.出勤.打刻.時刻3", 67);
		ITEM_ID_CONTAINER.put("日別実績の臨時出退勤.出退勤.退勤.実打刻.場所コード1", 56);
		ITEM_ID_CONTAINER.put("日別実績の臨時出退勤.出退勤.退勤.実打刻.時刻1", 57);
		ITEM_ID_CONTAINER.put("日別実績の臨時出退勤.出退勤.退勤.実打刻.場所コード2", 64);
		ITEM_ID_CONTAINER.put("日別実績の臨時出退勤.出退勤.退勤.実打刻.時刻2", 65);
		ITEM_ID_CONTAINER.put("日別実績の臨時出退勤.出退勤.退勤.実打刻.場所コード3", 72);
		ITEM_ID_CONTAINER.put("日別実績の臨時出退勤.出退勤.退勤.実打刻.時刻3", 73);
		ITEM_ID_CONTAINER.put("日別実績の臨時出退勤.出退勤.退勤.打刻.場所コード1", 52);
		ITEM_ID_CONTAINER.put("日別実績の臨時出退勤.出退勤.退勤.打刻.時刻1", 53);
		ITEM_ID_CONTAINER.put("日別実績の臨時出退勤.出退勤.退勤.打刻.場所コード2", 60);
		ITEM_ID_CONTAINER.put("日別実績の臨時出退勤.出退勤.退勤.打刻.時刻2", 61);
		ITEM_ID_CONTAINER.put("日別実績の臨時出退勤.出退勤.退勤.打刻.場所コード3", 68);
		ITEM_ID_CONTAINER.put("日別実績の臨時出退勤.出退勤.退勤.打刻.時刻3", 69);

		ITEM_ID_CONTAINER.put("日別実績の休憩時間帯.時間帯.開始時間.打刻.場所コード-就業時間帯から参照1", 156);
		ITEM_ID_CONTAINER.put("日別実績の休憩時間帯.時間帯.開始時間.打刻.場所コード-就業時間帯から参照2", 162);
		ITEM_ID_CONTAINER.put("日別実績の休憩時間帯.時間帯.開始時間.打刻.場所コード-就業時間帯から参照3", 168);
		ITEM_ID_CONTAINER.put("日別実績の休憩時間帯.時間帯.開始時間.打刻.場所コード-就業時間帯から参照4", 174);
		ITEM_ID_CONTAINER.put("日別実績の休憩時間帯.時間帯.開始時間.打刻.場所コード-就業時間帯から参照5", 180);
		ITEM_ID_CONTAINER.put("日別実績の休憩時間帯.時間帯.開始時間.打刻.場所コード-就業時間帯から参照6", 186);
		ITEM_ID_CONTAINER.put("日別実績の休憩時間帯.時間帯.開始時間.打刻.場所コード-就業時間帯から参照7", 192);
		ITEM_ID_CONTAINER.put("日別実績の休憩時間帯.時間帯.開始時間.打刻.場所コード-就業時間帯から参照8", 198);
		ITEM_ID_CONTAINER.put("日別実績の休憩時間帯.時間帯.開始時間.打刻.場所コード-就業時間帯から参照9", 204);
		ITEM_ID_CONTAINER.put("日別実績の休憩時間帯.時間帯.開始時間.打刻.場所コード-就業時間帯から参照10", 210);
		ITEM_ID_CONTAINER.put("日別実績の休憩時間帯.時間帯.開始時間.時刻-就業時間帯から参照1", 157);
		ITEM_ID_CONTAINER.put("日別実績の休憩時間帯.時間帯.開始時間.時刻-就業時間帯から参照2", 163);
		ITEM_ID_CONTAINER.put("日別実績の休憩時間帯.時間帯.開始時間.時刻-就業時間帯から参照3", 169);
		ITEM_ID_CONTAINER.put("日別実績の休憩時間帯.時間帯.開始時間.時刻-就業時間帯から参照4", 175);
		ITEM_ID_CONTAINER.put("日別実績の休憩時間帯.時間帯.開始時間.時刻-就業時間帯から参照5", 181);
		ITEM_ID_CONTAINER.put("日別実績の休憩時間帯.時間帯.開始時間.時刻-就業時間帯から参照6", 187);
		ITEM_ID_CONTAINER.put("日別実績の休憩時間帯.時間帯.開始時間.時刻-就業時間帯から参照7", 193);
		ITEM_ID_CONTAINER.put("日別実績の休憩時間帯.時間帯.開始時間.時刻-就業時間帯から参照8", 199);
		ITEM_ID_CONTAINER.put("日別実績の休憩時間帯.時間帯.開始時間.時刻-就業時間帯から参照9", 205);
		ITEM_ID_CONTAINER.put("日別実績の休憩時間帯.時間帯.開始時間.時刻-就業時間帯から参照10", 211);
		ITEM_ID_CONTAINER.put("日別実績の休憩時間帯.時間帯.終了時間.場所コード-就業時間帯から参照1", 158);
		ITEM_ID_CONTAINER.put("日別実績の休憩時間帯.時間帯.終了時間.場所コード-就業時間帯から参照2", 164);
		ITEM_ID_CONTAINER.put("日別実績の休憩時間帯.時間帯.終了時間.場所コード-就業時間帯から参照3", 170);
		ITEM_ID_CONTAINER.put("日別実績の休憩時間帯.時間帯.終了時間.場所コード-就業時間帯から参照4", 176);
		ITEM_ID_CONTAINER.put("日別実績の休憩時間帯.時間帯.終了時間.場所コード-就業時間帯から参照5", 182);
		ITEM_ID_CONTAINER.put("日別実績の休憩時間帯.時間帯.終了時間.場所コード-就業時間帯から参照6", 188);
		ITEM_ID_CONTAINER.put("日別実績の休憩時間帯.時間帯.終了時間.場所コード-就業時間帯から参照7", 194);
		ITEM_ID_CONTAINER.put("日別実績の休憩時間帯.時間帯.終了時間.場所コード-就業時間帯から参照8", 200);
		ITEM_ID_CONTAINER.put("日別実績の休憩時間帯.時間帯.終了時間.場所コード-就業時間帯から参照9", 206);
		ITEM_ID_CONTAINER.put("日別実績の休憩時間帯.時間帯.終了時間.場所コード-就業時間帯から参照10", 212);
		ITEM_ID_CONTAINER.put("日別実績の休憩時間帯.時間帯.終了時間.時刻-就業時間帯から参照1", 159);
		ITEM_ID_CONTAINER.put("日別実績の休憩時間帯.時間帯.終了時間.時刻-就業時間帯から参照2", 165);
		ITEM_ID_CONTAINER.put("日別実績の休憩時間帯.時間帯.終了時間.時刻-就業時間帯から参照3", 171);
		ITEM_ID_CONTAINER.put("日別実績の休憩時間帯.時間帯.終了時間.時刻-就業時間帯から参照4", 177);
		ITEM_ID_CONTAINER.put("日別実績の休憩時間帯.時間帯.終了時間.時刻-就業時間帯から参照5", 183);
		ITEM_ID_CONTAINER.put("日別実績の休憩時間帯.時間帯.終了時間.時刻-就業時間帯から参照6", 189);
		ITEM_ID_CONTAINER.put("日別実績の休憩時間帯.時間帯.終了時間.時刻-就業時間帯から参照7", 195);
		ITEM_ID_CONTAINER.put("日別実績の休憩時間帯.時間帯.終了時間.時刻-就業時間帯から参照8", 201);
		ITEM_ID_CONTAINER.put("日別実績の休憩時間帯.時間帯.終了時間.時刻-就業時間帯から参照9", 207);
		ITEM_ID_CONTAINER.put("日別実績の休憩時間帯.時間帯.終了時間.時刻-就業時間帯から参照10", 213);
		ITEM_ID_CONTAINER.put("日別実績の休憩時間帯.時間帯.開始時間.時刻-スケジュールから参照1", 7);
		ITEM_ID_CONTAINER.put("日別実績の休憩時間帯.時間帯.終了時間.時刻-スケジュールから参照1", 8);
		ITEM_ID_CONTAINER.put("日別実績の休憩時間帯.時間帯.開始時間.時刻-スケジュールから参照2", 9);
		ITEM_ID_CONTAINER.put("日別実績の休憩時間帯.時間帯.終了時間.時刻-スケジュールから参照2", 10);
		ITEM_ID_CONTAINER.put("日別実績の休憩時間帯.時間帯.開始時間.時刻-スケジュールから参照3", 11);
		ITEM_ID_CONTAINER.put("日別実績の休憩時間帯.時間帯.終了時間.時刻-スケジュールから参照3", 12);
		ITEM_ID_CONTAINER.put("日別実績の休憩時間帯.時間帯.開始時間.時刻-スケジュールから参照4", 13);
		ITEM_ID_CONTAINER.put("日別実績の休憩時間帯.時間帯.終了時間.時刻-スケジュールから参照4", 14);
		ITEM_ID_CONTAINER.put("日別実績の休憩時間帯.時間帯.開始時間.時刻-スケジュールから参照5", 15);
		ITEM_ID_CONTAINER.put("日別実績の休憩時間帯.時間帯.終了時間.時刻-スケジュールから参照5", 16);
		ITEM_ID_CONTAINER.put("日別実績の休憩時間帯.時間帯.開始時間.時刻-スケジュールから参照6", 17);
		ITEM_ID_CONTAINER.put("日別実績の休憩時間帯.時間帯.終了時間.時刻-スケジュールから参照6", 18);
		ITEM_ID_CONTAINER.put("日別実績の休憩時間帯.時間帯.開始時間.時刻-スケジュールから参照7", 19);
		ITEM_ID_CONTAINER.put("日別実績の休憩時間帯.時間帯.終了時間.時刻-スケジュールから参照7", 20);
		ITEM_ID_CONTAINER.put("日別実績の休憩時間帯.時間帯.開始時間.時刻-スケジュールから参照8", 21);
		ITEM_ID_CONTAINER.put("日別実績の休憩時間帯.時間帯.終了時間.時刻-スケジュールから参照8", 22);
		ITEM_ID_CONTAINER.put("日別実績の休憩時間帯.時間帯.開始時間.時刻-スケジュールから参照9", 23);
		ITEM_ID_CONTAINER.put("日別実績の休憩時間帯.時間帯.終了時間.時刻-スケジュールから参照9", 24);
		ITEM_ID_CONTAINER.put("日別実績の休憩時間帯.時間帯.開始時間.時刻-スケジュールから参照10", 25);
		ITEM_ID_CONTAINER.put("日別実績の休憩時間帯.時間帯.終了時間.時刻-スケジュールから参照10", 26);

		ITEM_ID_CONTAINER.put("日別実績の外出時間帯.時間帯.外出.実打刻.場所コード1", 87);
		ITEM_ID_CONTAINER.put("日別実績の外出時間帯.時間帯.外出.実打刻.時刻1", 88);
		ITEM_ID_CONTAINER.put("日別実績の外出時間帯.時間帯.戻り.実打刻.場所コード1", 90);
		ITEM_ID_CONTAINER.put("日別実績の外出時間帯.時間帯.戻り.実打刻.時刻1", 91);
		ITEM_ID_CONTAINER.put("日別実績の外出時間帯.時間帯.外出.実打刻.場所コード2", 94);
		ITEM_ID_CONTAINER.put("日別実績の外出時間帯.時間帯.外出.実打刻.時刻2", 95);
		ITEM_ID_CONTAINER.put("日別実績の外出時間帯.時間帯.戻り.実打刻.場所コード2", 97);
		ITEM_ID_CONTAINER.put("日別実績の外出時間帯.時間帯.戻り.実打刻.時刻2", 98);
		ITEM_ID_CONTAINER.put("日別実績の外出時間帯.時間帯.外出.実打刻.場所コード3", 101);
		ITEM_ID_CONTAINER.put("日別実績の外出時間帯.時間帯.外出.実打刻.時刻3", 102);
		ITEM_ID_CONTAINER.put("日別実績の外出時間帯.時間帯.戻り.実打刻.場所コード3", 104);
		ITEM_ID_CONTAINER.put("日別実績の外出時間帯.時間帯.戻り.実打刻.時刻3", 105);
		ITEM_ID_CONTAINER.put("日別実績の外出時間帯.時間帯.外出.実打刻.場所コード4", 108);
		ITEM_ID_CONTAINER.put("日別実績の外出時間帯.時間帯.外出.実打刻.時刻4", 109);
		ITEM_ID_CONTAINER.put("日別実績の外出時間帯.時間帯.戻り.実打刻.場所コード4", 111);
		ITEM_ID_CONTAINER.put("日別実績の外出時間帯.時間帯.戻り.実打刻.時刻4", 112);
		ITEM_ID_CONTAINER.put("日別実績の外出時間帯.時間帯.外出.実打刻.場所コード5", 115);
		ITEM_ID_CONTAINER.put("日別実績の外出時間帯.時間帯.外出.実打刻.時刻5", 116);
		ITEM_ID_CONTAINER.put("日別実績の外出時間帯.時間帯.戻り.実打刻.場所コード5", 118);
		ITEM_ID_CONTAINER.put("日別実績の外出時間帯.時間帯.戻り.実打刻.時刻5", 119);
		ITEM_ID_CONTAINER.put("日別実績の外出時間帯.時間帯.外出.実打刻.場所コード6", 122);
		ITEM_ID_CONTAINER.put("日別実績の外出時間帯.時間帯.外出.実打刻.時刻6", 123);
		ITEM_ID_CONTAINER.put("日別実績の外出時間帯.時間帯.戻り.実打刻.場所コード6", 125);
		ITEM_ID_CONTAINER.put("日別実績の外出時間帯.時間帯.戻り.実打刻.時刻6", 126);
		ITEM_ID_CONTAINER.put("日別実績の外出時間帯.時間帯.外出.実打刻.場所コード7", 129);
		ITEM_ID_CONTAINER.put("日別実績の外出時間帯.時間帯.外出.実打刻.時刻7", 130);
		ITEM_ID_CONTAINER.put("日別実績の外出時間帯.時間帯.戻り.実打刻.場所コード7", 132);
		ITEM_ID_CONTAINER.put("日別実績の外出時間帯.時間帯.戻り.実打刻.時刻7", 133);
		ITEM_ID_CONTAINER.put("日別実績の外出時間帯.時間帯.外出.実打刻.場所コード8", 136);
		ITEM_ID_CONTAINER.put("日別実績の外出時間帯.時間帯.外出.実打刻.時刻8", 137);
		ITEM_ID_CONTAINER.put("日別実績の外出時間帯.時間帯.戻り.実打刻.場所コード8", 139);
		ITEM_ID_CONTAINER.put("日別実績の外出時間帯.時間帯.戻り.実打刻.時刻8", 140);
		ITEM_ID_CONTAINER.put("日別実績の外出時間帯.時間帯.外出.実打刻.場所コード9", 143);
		ITEM_ID_CONTAINER.put("日別実績の外出時間帯.時間帯.外出.実打刻.時刻9", 144);
		ITEM_ID_CONTAINER.put("日別実績の外出時間帯.時間帯.戻り.実打刻.場所コード9", 146);
		ITEM_ID_CONTAINER.put("日別実績の外出時間帯.時間帯.戻り.実打刻.時刻9", 147);
		ITEM_ID_CONTAINER.put("日別実績の外出時間帯.時間帯.外出.実打刻.場所コード10", 150);
		ITEM_ID_CONTAINER.put("日別実績の外出時間帯.時間帯.外出.実打刻.時刻10", 151);
		ITEM_ID_CONTAINER.put("日別実績の外出時間帯.時間帯.戻り.実打刻.場所コード10", 153);
		ITEM_ID_CONTAINER.put("日別実績の外出時間帯.時間帯.戻り.実打刻.時刻10", 154);
		
		ITEM_ID_CONTAINER.put("日別実績の計算区分.休出時間.休出時間.計算区分", 633);
		ITEM_ID_CONTAINER.put("日別実績の計算区分.休出時間.休出深夜時間.計算区分", 634);
		ITEM_ID_CONTAINER.put("日別実績の計算区分.残業時間.早出残業時間.計算区分", 627);
		ITEM_ID_CONTAINER.put("日別実績の計算区分.残業時間.早出深夜残業時間.計算区分", 628);
		ITEM_ID_CONTAINER.put("日別実績の計算区分.残業時間.普通残業時間.計算区分", 629);
		ITEM_ID_CONTAINER.put("日別実績の計算区分.残業時間.普通深夜残業時間.計算区分", 630);
		ITEM_ID_CONTAINER.put("日別実績の計算区分.残業時間.法定内残業時間.計算区分", 631);
		ITEM_ID_CONTAINER.put("日別実績の計算区分.残業時間.法定内深夜残業時間.計算区分", 632);
		ITEM_ID_CONTAINER.put("日別実績の計算区分.フレックス超過時間.計算区分", 635);
		
	}

	public static Integer getId(String key) {
		return ITEM_ID_CONTAINER.get(key);
	}
}
