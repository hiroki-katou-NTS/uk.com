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
		Map<Integer, List<TmpAnnualLeaveMngWork>> map = createTestDataList();
		ObjectBinaryFile.write(map, getFilePath());
	}
	
	/**
	 * バイナリファイルからオブジェクトを作成
	 */
	public static Map<Integer, List<TmpAnnualLeaveMngWork>> build(){
		return ObjectBinaryFile.read(getFilePath());
	}
	
	/**
	 * テストデータ（リスト）作成
	 * @return
	 */
	private Map<Integer, List<TmpAnnualLeaveMngWork>> createTestDataList(){
		
		val map = new HashMap<Integer, List<TmpAnnualLeaveMngWork>>();
		map.put(1, createTestData1());
		map.put(2, createTestData2());
		
		return map;
	}
	
	/**
	 * リスト
	 */
	private Map<Integer, List<TmpAnnualLeaveMngWork>> map = null;
	
	/**
	 * テスト番号をキーにテストデータを取得
	 * @param testCode
	 * @return
	 */
	public List<TmpAnnualLeaveMngWork> getData(int testCode){
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
		
		val list = new ArrayList<TmpAnnualLeaveMngWork>();
		
		// ----------------------------------------
		{
			/** 残数管理データID */
			String manageId = "manageid";
			/** 対象日 */
			GeneralDate ymd = GeneralDate.ymd(2020, 4, 1);
			/** 勤務種類コード */
			String workTypeCode = RemainType.ANNUAL.toString(); // 年休
			/** 使用日数 */
			UseDay useDays = new UseDay(1.0);
			/** 作成元区分 */
			CreateAtr creatorAtr = CreateAtr.RECORD; // 実績
			/** 残数分類 */
			RemainAtr remainAtr = RemainAtr.SINGLE; // 単一
			
			TmpAnnualLeaveMngWork tmpAnnualLeaveMngWork
				= TmpAnnualLeaveMngWork.of(manageId, ymd, workTypeCode, useDays, creatorAtr, remainAtr);
			
			list.add(tmpAnnualLeaveMngWork);
		}
		
		// ----------------------------------------
		{
			/** 残数管理データID */
			String manageId = "manageid";
			/** 対象日 */
			GeneralDate ymd = GeneralDate.ymd(2020, 4, 3);
			/** 勤務種類コード */
			String workTypeCode = RemainType.ANNUAL.toString(); // 年休
			/** 使用日数 */
			UseDay useDays = new UseDay(1.0);
			/** 作成元区分 */
			CreateAtr creatorAtr = CreateAtr.RECORD; // 実績
			/** 残数分類 */
			RemainAtr remainAtr = RemainAtr.SINGLE; // 単一
			
			TmpAnnualLeaveMngWork tmpAnnualLeaveMngWork
				= TmpAnnualLeaveMngWork.of(manageId, ymd, workTypeCode, useDays, creatorAtr, remainAtr);
			
			list.add(tmpAnnualLeaveMngWork);
		}
		
		return list;
	}
	
	/**
	 * パターン２
	 * @return
	 */
	private List<TmpAnnualLeaveMngWork> createTestData2(){
		
		val list = new ArrayList<TmpAnnualLeaveMngWork>();
		
		// ----------------------------------------
		{
			/** 残数管理データID */
			String manageId = "manageid";
			/** 対象日 */
			GeneralDate ymd = GeneralDate.ymd(2020, 4, 5);
			/** 勤務種類コード */
			String workTypeCode = RemainType.ANNUAL.toString(); // 年休
			/** 使用日数 */
			UseDay useDays = new UseDay(1.0);
			/** 作成元区分 */
			CreateAtr creatorAtr = CreateAtr.RECORD; // 実績
			/** 残数分類 */
			RemainAtr remainAtr = RemainAtr.SINGLE; // 単一
			
			TmpAnnualLeaveMngWork tmpAnnualLeaveMngWork
				= TmpAnnualLeaveMngWork.of(manageId, ymd, workTypeCode, useDays, creatorAtr, remainAtr);
			
			list.add(tmpAnnualLeaveMngWork);
		}
		
		// ----------------------------------------
		{
			/** 残数管理データID */
			String manageId = "manageid";
			/** 対象日 */
			GeneralDate ymd = GeneralDate.ymd(2020, 4, 6);
			/** 勤務種類コード */
			String workTypeCode = RemainType.ANNUAL.toString(); // 年休
			/** 使用日数 */
			UseDay useDays = new UseDay(1.0);
			/** 作成元区分 */
			CreateAtr creatorAtr = CreateAtr.RECORD; // 実績
			/** 残数分類 */
			RemainAtr remainAtr = RemainAtr.SINGLE; // 単一
			
			TmpAnnualLeaveMngWork tmpAnnualLeaveMngWork
				= TmpAnnualLeaveMngWork.of(manageId, ymd, workTypeCode, useDays, creatorAtr, remainAtr);
			
			list.add(tmpAnnualLeaveMngWork);
		}
		
		return list;
	}
	
}
