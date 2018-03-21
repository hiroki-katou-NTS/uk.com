package nts.uk.screen.at.app.monthlyperformance.correction;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.apache.logging.log4j.util.Strings;

import nts.arc.error.BusinessException;
import nts.uk.ctx.at.record.dom.monthlyperformanceformat.enums.SettingUnit;
import nts.uk.screen.at.app.dailyperformance.correction.dto.DailyPerformanceEmployeeDto;
import nts.uk.screen.at.app.monthlyperformance.correction.dto.CorrectionOfDailyPerformance;
import nts.uk.screen.at.app.monthlyperformance.correction.dto.DisplayItem;
import nts.uk.screen.at.app.monthlyperformance.correction.dto.FormatMPCorrectionDto;
import nts.uk.screen.at.app.monthlyperformance.correction.dto.MPSheetDto;
import nts.uk.screen.at.app.monthlyperformance.correction.dto.OperationOfMonthlyPerformanceDto;
import nts.uk.shr.com.context.AppContexts;

@Stateless
public class MonthlyPerformanceDisplay {
	
	@Inject
	//MonthlyInitDisplayFormatRepo initDisplay;
	
	private static final String SCREEN_KDW003 = "KDW/003/a";
	/**
	 * 表示フォーマットの取得
	 * @param lstEmployees: 社員一覧：List＜社員ID＞
	 * @param formatCodes: 使用するフォーマットコード：月別実績フォーマットコード
	 * 表示する項目一覧
	 */
	public void getDisplayFormat(List<String> lstEmployeeId, List<DailyPerformanceEmployeeDto> lstEmployees, List<String> formatCodes, CorrectionOfDailyPerformance correctionOfDaily, OperationOfMonthlyPerformanceDto operation){
		DisplayItem dispItem;
		//会社ID：ログイン会社に一致する
		String cId = AppContexts.user().companyId();
		//ロールID：ログイン社員の就業ロールに一致する
		String employmentRoleID = AppContexts.user().roles().forAttendance();
		//権限の場合 
		if(operation.getSettingUnit() == SettingUnit.AUTHORITY){
			//アルゴリズム「社員の権限に対応する表示項目を取得する」を実行する
			dispItem = getDisplayItemAuthority(cId, formatCodes, correctionOfDaily);
		}
		//勤務種別の場合
		else{
			//社員の勤務種別に対応する表示項目を取得する
			//(Lấy các item hiển thị ứng với loại đi làm của employee)
			dispItem = getDisplayItemBussiness(lstEmployeeId, formatCodes, correctionOfDaily);
		}
		//対応するドメインモデル「権限別月次項目制御」を取得する
		//TODO 権限別月次項目制御		
		//取得したドメインモデル「権限別月次項目制御」でパラメータ「表示する項目一覧」をしぼり込む
		//Filter param 「表示する項目一覧」 bởi domain 「権限別月次項目制御」
		DisplayItem lockItem = new DisplayItem();
	}

	/**
	 * アルゴリズム「社員の権限に対応する表示項目を取得する」を実行する
	 * (Lấy các item hiển thị ứng với quyền employee)
	 * @param cId
	 * @param formatCodes
	 * @param correctionOfDaily
	 * @return 表示する項目一覧
	 */
	private DisplayItem getDisplayItemAuthority(String cId, List<String> formatCodes, CorrectionOfDailyPerformance correctionOfDaily){		
		DisplayItem dispItem = new DisplayItem();
		List<FormatMPCorrectionDto> lstFormat = new ArrayList<FormatMPCorrectionDto>();
		List<MPSheetDto> lstSheet = new ArrayList<MPSheetDto>();
		List<Integer> lstAtdItem = new ArrayList<>();
		List<Integer> lstAtdItemUnique = new ArrayList<>();
		if(null == formatCodes){
			//ユーザー固有情報「月別実績のの修正」．前回の表示項目が取得できたかチェックする			
			if(null !=  correctionOfDaily && !Strings.isEmpty(correctionOfDaily.getPreviousDisplayItem())){
				//取得したユーザー固有情報「月別実績の修正．前回の表示項目」をパラメータ「使用するフォーマットコード」にセットする
				formatCodes = Arrays.asList(correctionOfDaily.getPreviousDisplayItem());
			}else{
				//対応するドメインモデル「月次の初期表示フォーマット」を取得する
				//TODO 月次の初期表示フォーマット
				//Optional<MonthlyInitDisplayFormat> initDisp = initDisplay.getMonthlyInitDisplayFormat(cId);
				//if(initDisp.isPresent()){
					//取得したドメインモデル「月次の初期表示フォーマット」をパラメータ「使用するフォーマットコード」にセットする
				//	formatCodes = Arrays.asList(initDisp.get().getCode());
				//}else{
					//アルゴリズム「表示項目の選択を起動する」を実行する
					//ダイアログで選択していたフォーマットコードをパラメータ「使用するフォーマットコード」にセットする
				//	throw new BusinessException(SCREEN_KDW003);
				//}
			}
		}		
		//TODO 会社の月別実績の修正フォーマット
		//対応するドメインモデル「会社の月別実績の修正フォーマット」を取得する
		//Contains list of sheet (月別実績の修正のシート) and list of display item in sheet (表示する項目)
		
		
		dispItem.setLstFormat(lstFormat);
		dispItem.setLstSheet(lstSheet);
		dispItem.setLstAtdItemUnique(lstAtdItemUnique);
		//dispItem.setBussiness(dailyPerformanceDto.getSettingUnit().value);
		return dispItem;
	}
	
	/**
	 * 社員の勤務種別に対応する表示項目を取得する
	 * (Lấy các item hiển thị ứng với loại đi làm của employee)
	 * @return
	 */
	private DisplayItem getDisplayItemBussiness(List<String> lstEmployeeId, List<String> formatCodes, CorrectionOfDailyPerformance correctionOfDaily){
		DisplayItem dispItem = new DisplayItem();
		List<FormatMPCorrectionDto> lstFormat = new ArrayList<FormatMPCorrectionDto>();
		List<MPSheetDto> lstSheet = new ArrayList<MPSheetDto>();
		List<Integer> lstAtdItem = new ArrayList<>();
		List<Integer> lstAtdItemUnique = new ArrayList<>();
		
		//TODO 特定の社員が所属する勤務種別をすべて取得する
		//Lấy tất cả các loại đi làm của employee chỉ định
		
		//取得したImported「（就業機能）勤務種別」の件数をチェックする
		//(Check số Imported「（就業機能）勤務種別」)		
		int importCnt = 0;
		if(importCnt == 0){
			//エラーメッセージ（#）を表示する
			//TODO missing message ID
			throw new BusinessException("エラーメッセージ（#）を表示する");
			
		}else if(importCnt == 1){
			//対応するドメインモデル「勤務種別の月別実績の修正のフォーマット」を取得する
			//Lấy domain 「勤務種別の月別実績の修正のフォーマット」
		}else{
			//対応するドメインモデル「勤務種別の月別実績の修正のフォーマット」を取得する
		}
		
		//取得した「勤務種別の月別実績の修正のフォーマット」に表示するすべての項目の列幅があるかチェックする
		//Check xem tất cả item có hiển thị trong 「勤務種別の月別実績の修正のフォーマット」
		
		dispItem.setLstFormat(lstFormat);
		dispItem.setLstSheet(lstSheet);
		dispItem.setLstAtdItemUnique(lstAtdItemUnique);
		//dispItem.setBussiness(dailyPerformanceDto.getSettingUnit().value);
		return dispItem;
	}
}
