package nts.uk.ctx.at.record.dom.remainingnumber.annualleave.export.testdata;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import lombok.val;
import nts.arc.time.GeneralDate;
import nts.gul.serialize.binary.ObjectBinaryFile;
import nts.uk.ctx.at.record.dom.monthlyprocess.aggr.work.MonAggrCompanySettings;
import nts.uk.ctx.at.shared.dom.remainingnumber.annualleave.interim.TmpAnnualLeaveMngWork;
import nts.uk.ctx.at.shared.dom.remainingnumber.interimremain.primitive.CreateAtr;
import nts.uk.ctx.at.shared.dom.remainingnumber.interimremain.primitive.RemainAtr;
import nts.uk.ctx.at.shared.dom.remainingnumber.interimremain.primitive.RemainType;
import nts.uk.ctx.at.shared.dom.remainingnumber.interimremain.primitive.UseDay;

/**
 * 月別集計で必要な会社別設定
 * @author masaaki_jinno
 *
 */
public class TestMonAggrCompanySettings {

	private static String fileDir = TestBinaryFile.fileDir;
	private static String fileName = "TestMonAggrCompanySettings.csv";
	
	private static Path getFilePath(){
		val filePath = Paths.get(fileDir + fileName);
		return filePath;
	}
	
	/**
	 * テストデータを作成してバイナリファイルに保存
	 */
	public void SaveBinary(){
		TestAnnualPaidLeaveSetting aTestAnnualPaidLeaveSetting
			= new TestAnnualPaidLeaveSetting();
		Map<Integer, MonAggrCompanySettings> map 
			= createTestDataList(aTestAnnualPaidLeaveSetting);
		ObjectBinaryFile.write(map, getFilePath());
	}
	
	/**
	 * バイナリファイルからオブジェクトを作成
	 */
	public static Map<Integer, MonAggrCompanySettings> build(){
		return ObjectBinaryFile.read(getFilePath());
	}
	
	/**
	 * テストデータ（リスト）作成
	 * @return
	 */
	public Map<Integer, MonAggrCompanySettings> createTestDataList(
			TestAnnualPaidLeaveSetting aTestAnnualPaidLeaveSetting){
		
		val map = new HashMap<Integer, MonAggrCompanySettings>();
		map.put(1, createTestData1(aTestAnnualPaidLeaveSetting));
		map.put(2, createTestData2(aTestAnnualPaidLeaveSetting));
		
		return map;
	}

	/**
	 * パターン１
	 * @return
	 */
	private MonAggrCompanySettings createTestData1(
			TestAnnualPaidLeaveSetting aTestAnnualPaidLeaveSetting){
		
		val mon = new MonAggrCompanySettings();
		mon.setAnnualLeaveSet(aTestAnnualPaidLeaveSetting.getData(1));
		return mon;
	}
	
	/**
	 * パターン２
	 * @return
	 */
	private MonAggrCompanySettings createTestData2(
			TestAnnualPaidLeaveSetting aTestAnnualPaidLeaveSetting){
		
		val mon = new MonAggrCompanySettings();
		mon.setAnnualLeaveSet(aTestAnnualPaidLeaveSetting.getData(1));
		return mon;
	}
	
}

