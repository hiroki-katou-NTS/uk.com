package nts.uk.ctx.at.record.dom.remainingnumber.annualleave.export.testdata;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import lombok.val;
import nts.gul.serialize.binary.ObjectBinaryFile;
import nts.uk.ctx.at.shared.dom.remainingnumber.annualleave.interim.TmpAnnualLeaveMngWork;
import nts.uk.ctx.at.shared.dom.vacation.setting.ManageDistinct;
import nts.uk.ctx.at.shared.dom.vacation.setting.annualpaidleave.AnnualPaidLeaveSetting;

/**
 * 年休設定
 * @author masaaki_jinno
 *
 */
public class TestAnnualPaidLeaveSetting {
	
	private static String fileDir = TestBinaryFile.fileDir;
	private static String fileName = "TestAnnualPaidLeaveSetting.csv";
	
	private static Path getFilePath(){
		val filePath = Paths.get(fileDir + fileName);
		return filePath;
	}
	
	/**
	 * テストデータを作成してバイナリファイルに保存
	 */
	public void SaveBinary(){
		Map<Integer, AnnualPaidLeaveSetting> map = createTestDataList();
		ObjectBinaryFile.write(map, getFilePath());
	}
	
	/**
	 * バイナリファイルからオブジェクトを作成
	 */
	private static Map<Integer, List<TmpAnnualLeaveMngWork>> build(){
		return ObjectBinaryFile.read(getFilePath());
	}
	
	/**
	 * テストデータ（リスト）作成
	 * @return
	 */
	private Map<Integer, AnnualPaidLeaveSetting> createTestDataList(){
		
		val map = new HashMap<Integer, AnnualPaidLeaveSetting>();
		map.put(1, createTestData1());
		map.put(2, createTestData2());
		
		return map;
	}
	
	/**
	 * リスト
	 */
	private Map<Integer, AnnualPaidLeaveSetting> map = null;
	
	/**
	 * テスト番号をキーにテストデータを取得
	 * @param testCode
	 * @return
	 */
	public AnnualPaidLeaveSetting getData(int testCode){
		if ( map == null ){
			map = createTestDataList();
		}
		return map.get(testCode);
	}

	/**
	 * パターン１
	 * @return
	 */
	private AnnualPaidLeaveSetting createTestData1(){
		
		val mon = new AnnualPaidLeaveSetting(null, null, ManageDistinct.YES, null, null);
		
		mon.getManageAnnualSetting();
		
		return mon;
	}
	
	/**
	 * パターン２
	 * @return
	 */
	private AnnualPaidLeaveSetting createTestData2(){
			
			val mon = new AnnualPaidLeaveSetting(null, null, ManageDistinct.NO, null, null);
			
			mon.getManageAnnualSetting();
			
			return mon;
		}
	
}

