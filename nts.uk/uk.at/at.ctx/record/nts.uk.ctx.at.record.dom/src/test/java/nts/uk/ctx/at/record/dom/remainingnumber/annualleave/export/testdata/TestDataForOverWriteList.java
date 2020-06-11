package nts.uk.ctx.at.record.dom.remainingnumber.annualleave.export.testdata;

import java.util.List;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
import lombok.val;

import nts.arc.time.GeneralDate;
import nts.gul.serialize.binary.ObjectBinaryFile;
import nts.uk.ctx.at.shared.dom.remainingnumber.annualleave.interim.TmpAnnualLeaveMngWork;
import nts.uk.ctx.at.shared.dom.remainingnumber.interimremain.primitive.CreateAtr;
import nts.uk.ctx.at.shared.dom.remainingnumber.interimremain.primitive.RemainAtr;
import nts.uk.ctx.at.shared.dom.remainingnumber.interimremain.primitive.RemainType;
import nts.uk.ctx.at.shared.dom.remainingnumber.interimremain.primitive.UseDay;
import nts.uk.ctx.at.shared.dom.vacation.setting.annualpaidleave.AnnualPaidLeaveSetting;

/**
 * 上書き用の暫定年休管理データ
 * @author masaaki_jinno
 *
 */
public class TestDataForOverWriteList {
	
	private static String fileDir = TestBinaryFile.fileDir;
	private static String fileName = "TestDataForOverWriteListBinary.csv";
	
	private static Path getFilePath(){
		val filePath = Paths.get(fileDir + fileName);
		return filePath;
	}
	
	/**
	 * テストデータを作成してバイナリファイルに保存
	 */
	public void SaveBinary(){
		Map<String, List<TmpAnnualLeaveMngWork>> map = createTestDataList();
		ObjectBinaryFile.write(map, getFilePath());
	}
	
	/**
	 * バイナリファイルからオブジェクトを作成
	 */
	public static Map<String, List<TmpAnnualLeaveMngWork>> build(){
		//return ObjectBinaryFile.read(getFilePath());
		
		TestDataForOverWriteList t = new TestDataForOverWriteList();
		return t.createTestDataList();
	}
	
	/**
	 * テストデータ（リスト）作成
	 * @return
	 */
	private Map<String, List<TmpAnnualLeaveMngWork>> createTestDataList(){
		
		val map = new HashMap<String, List<TmpAnnualLeaveMngWork>>();
		map.put("1", createTestData1());
		map.put("2", createTestData2());
		map.put("3", createTestData3());
		map.put("4", createTestData4());
		map.put("5", createTestData5());
		map.put("6", createTestData6());
		map.put("7", createTestData7());
		map.put("8", createTestData8());
		map.put("9", createTestData9());
		map.put("10", createTestData10());
		map.put("11", createTestData11());
		map.put("12", createTestData12());
		map.put("13", createTestData13());

		return map;
	}
	
	/**
	 * リスト
	 */
	private Map<String, List<TmpAnnualLeaveMngWork>> map = null;
	
	/**
	 * テスト番号をキーにテストデータを取得
	 * @param testCode
	 * @return
	 */
	public List<TmpAnnualLeaveMngWork> getData(String testCode){
		if ( map == null ){
			map = createTestDataList();
		}
		return map.get(testCode);
	}

	/**
	 * パターン１
	 * @return
	 */
	private List<TmpAnnualLeaveMngWork> createTestData1(){
		// 使用数０
		val list = new ArrayList<TmpAnnualLeaveMngWork>();
		return list;
	}
	
	/**
	 * パターン２
	 * @return
	 */
	private List<TmpAnnualLeaveMngWork> createTestData2(){
			/** 開始日 */
			GeneralDate start_date = GeneralDate.ymd(2020, 9, 16);
			// データ作成
			List<TmpAnnualLeaveMngWork> list = createTestData(start_date, 3);
		return list;
	}
	
	/**
	 * パターン３
	 * @return
	 */
	private List<TmpAnnualLeaveMngWork> createTestData3(){
			/** 開始日 */
			GeneralDate start_date = GeneralDate.ymd(2020, 9, 15);
			// データ作成
			List<TmpAnnualLeaveMngWork> list = createTestData(start_date, 3);
		return list;
	}
	
	/**
	 * パターン４
	 * @return
	 */
	private List<TmpAnnualLeaveMngWork> createTestData4(){
			/** 開始日 */
			GeneralDate start_date = GeneralDate.ymd(2020, 9, 1);
			// データ作成
			List<TmpAnnualLeaveMngWork> list = createTestData(start_date, 3);
		return list;
	}
	
	/**
	 * パターン５
	 * @return
	 */
	private List<TmpAnnualLeaveMngWork> createTestData5(){
			/** 開始日 */
			GeneralDate start_date = GeneralDate.ymd(2020, 9, 16);
			// データ作成
			List<TmpAnnualLeaveMngWork> list = createTestData(start_date, 6);
		return list;
	}
	
	/**
	 * パターン６
	 * @return
	 */
	private List<TmpAnnualLeaveMngWork> createTestData6(){
			/** 開始日 */
			GeneralDate start_date = GeneralDate.ymd(2020, 9, 15);
			// データ作成
			List<TmpAnnualLeaveMngWork> list = createTestData(start_date, 6);
		return list;
	}
	
	/**
	 * パターン７
	 * @return
	 */
	private List<TmpAnnualLeaveMngWork> createTestData7(){
			/** 開始日 */
			GeneralDate start_date = GeneralDate.ymd(2020, 9, 1);
			// データ作成
			List<TmpAnnualLeaveMngWork> list = createTestData(start_date, 6);
		return list;
	}
	
	/**
	 * パターン８
	 * @return
	 */
	private List<TmpAnnualLeaveMngWork> createTestData8(){
			/** 開始日 */
			GeneralDate start_date = GeneralDate.ymd(2020, 10, 16);
			// データ作成
			List<TmpAnnualLeaveMngWork> list = createTestData(start_date, 3);
		return list;
	}
	
	/**
	 * パターン９
	 * @return
	 */
	private List<TmpAnnualLeaveMngWork> createTestData9(){
			/** 開始日 */
			GeneralDate start_date = GeneralDate.ymd(2020, 10, 15);
			// データ作成
			List<TmpAnnualLeaveMngWork> list = createTestData(start_date, 3);
		return list;
	}
	
	/**
	 * パターン１０
	 * @return
	 */
	private List<TmpAnnualLeaveMngWork> createTestData10(){
			/** 開始日 */
			GeneralDate start_date = GeneralDate.ymd(2020, 10, 1);
			// データ作成
			List<TmpAnnualLeaveMngWork> list = createTestData(start_date, 3);
		return list;
	}
	
	/**
	 * パターン１１
	 * @return
	 */
	private List<TmpAnnualLeaveMngWork> createTestData11(){
			/** 開始日 */
			GeneralDate start_date = GeneralDate.ymd(2020, 10, 16);
			// データ作成
			List<TmpAnnualLeaveMngWork> list = createTestData(start_date, 6);
		return list;
	}
	
	/**
	 * パターン１２
	 * @return
	 */
	private List<TmpAnnualLeaveMngWork> createTestData12(){
			/** 開始日 */
			GeneralDate start_date = GeneralDate.ymd(2020, 10, 15);
			// データ作成
			List<TmpAnnualLeaveMngWork> list = createTestData(start_date, 6);
		return list;
	}
	
	/**
	 * パターン１３
	 * @return
	 */
	private List<TmpAnnualLeaveMngWork> createTestData13(){
			/** 開始日 */
			GeneralDate start_date = GeneralDate.ymd(2020, 10, 1);
			// データ作成
			List<TmpAnnualLeaveMngWork> list = createTestData(start_date, 6);
		return list;
	}
	
	
	/**
	 * 年休暫定データのテストデータを作成
	 * 　　引数で渡される件数分作成する。開始日から1日ずつシフトしながら作成する。
	 * @param start_date 開始日
	 * @param day_number
	 * @return
	 */
	private List<TmpAnnualLeaveMngWork> createTestData(GeneralDate start_date, int day_number ){
		List<TmpAnnualLeaveMngWork> list = new ArrayList<TmpAnnualLeaveMngWork>();
		
		GeneralDate tmpDate = GeneralDate.ymd(start_date.year(), start_date.month(), start_date.day());
		for(int i=0; i<day_number; i++){
			
			/** 残数管理データID */
			String manageId = "1";
			/** 対象日 */
			GeneralDate ymd = GeneralDate.ymd(2020, 9, 18);
			/** 勤務種類コード */
			String workTypeCode = RemainType.ANNUAL.toString(); // 年休
			/** 使用日数 */
			UseDay useDays = new UseDay(1.0);
			/** 作成元区分 */
			CreateAtr creatorAtr = CreateAtr.RECORD; // 実績
			/** 残数分類 */
			RemainAtr remainAtr = RemainAtr.SINGLE; // 単一
			
			TmpAnnualLeaveMngWork t = TmpAnnualLeaveMngWork.of(
					manageId, ymd, workTypeCode, useDays, creatorAtr, remainAtr);
			
			list.add(t); // リストへ追加
			
			// 1日後
			tmpDate = tmpDate.addDays(1);
		}
		
		return list;		
	}
	
}
