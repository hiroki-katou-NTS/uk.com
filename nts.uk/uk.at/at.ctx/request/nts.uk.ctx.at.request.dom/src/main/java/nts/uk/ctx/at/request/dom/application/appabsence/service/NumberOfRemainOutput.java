package nts.uk.ctx.at.request.dom.application.appabsence.service;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class NumberOfRemainOutput {
	//年休残数
	private int yearRemain;

	// 年休残時間
	private int yearHourRemain;
	
	//代休残数
	private int subHdRemain;
	
	//振休残数
	private int subVacaRemain;
	
//	//ストック休暇残数
//	private int stockRemain;
	
	// 代休残時間
	private int subVacaHourRemain;
	
	// 代休残時間
	private int subHdHourRemain;
	
	// 60H超休残時間
	private int over60HHourRemain;
	
	// 子看護残数
	private int childNursingRemain;
	
	// 子看護残時間
	private int childNursingHourRemain;
	
	// 介護残数
	private int nursingRemain;
	
	// 介護残時間
	private int nursingHourRemain;
	
//    public static NumberOfRemainOutput init(int yearRemain, int subHdRemain, int subVacaRemain, int stockRemain,
//            boolean yearFlg, boolean subHdFlg, boolean subVacaFlg, boolean  retentionFlg){
//        return new NumberOfRemainOutput(yearRemain == null && yearFlg ? new int(0L) : yearRemain,
//                        subHdRemain == null && subHdFlg? new int(0L) : subHdRemain,
//                        subVacaRemain == null && subVacaFlg? new int(0L) : subVacaRemain,
//                        stockRemain == null && retentionFlg? new int(0L) : stockRemain);
//
//	}
}
