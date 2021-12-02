package nts.uk.ctx.at.record.dom.remainingnumber.annualleave.export;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.junit.Test;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;

import nts.arc.layer.ws.json.serializer.EnumDeserializer;
import nts.arc.layer.ws.json.serializer.EnumSerializer;
import nts.arc.layer.ws.json.serializer.GeneralDateDeserializer;
import nts.arc.layer.ws.json.serializer.GeneralDateSerializer;
import nts.arc.layer.ws.json.serializer.GeneralDateTimeDeserializer;
import nts.arc.layer.ws.json.serializer.GeneralDateTimeSerializer;
import nts.arc.time.GeneralDate;
import nts.arc.time.GeneralDateTime;
import nts.arc.time.YearMonth;
import nts.gul.serialize.binary.ObjectBinaryFile;

//import com.fasterxml.jackson.core.JsonProcessingException;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.fasterxml.jackson.annotation.*;

import nts.gul.serialize.json.JsonMapping;

import lombok.val;
import lombok.experimental.var;
import nts.uk.ctx.at.shared.dom.common.days.YearlyDays;
import nts.uk.ctx.at.shared.dom.remainingnumber.annualleave.empinfo.grantremainingdata.AnnualLeaveConditionInfo;
import nts.uk.ctx.at.shared.dom.remainingnumber.annualleave.empinfo.grantremainingdata.AnnualLeaveRemainingHistoryTest;
import nts.uk.ctx.at.shared.dom.remainingnumber.base.YearDayNumber;


public class JsonTest2 {
	
	//@Test
	public void testToJson() throws IOException{
		
		try{
			
			Map<String,ArrayList<AnnualLeaveRemainingHistoryTest>> toBinaryMap 
				= new HashMap<String,ArrayList<AnnualLeaveRemainingHistoryTest>>();
			
		//	// 「年休付与残数履歴データ」を取得
		//	AnnualLeaveRemainHistRepository annualLeaveRemainHistRepo
		//		= new JpaAnnualLeaveRemainHistRepository();	
			
			ArrayList<AnnualLeaveRemainingHistoryTest> remainHistList 
				= new ArrayList<AnnualLeaveRemainingHistoryTest>();
			
			AnnualLeaveRemainingHistoryTest aAnnualLeaveRemainingHistoryTest
				= new AnnualLeaveRemainingHistoryTest(
						"cID", "employeeId", 201909, 1, 20, true,
						GeneralDate.ymd(2019, 3, 30),
						GeneralDate.ymd(2019, 3, 30),
						1, 0, 1.5, 10, 1.0, 30, 20.5, 4.5, 30,
						4.5, 4.5, 4.5, 4.5);
			
			AnnualLeaveConditionInfo aAnnualLeaveConditionInfo = new AnnualLeaveConditionInfo();
			aAnnualLeaveConditionInfo.setPrescribedDays(new YearlyDays(1.1));
			aAnnualLeaveRemainingHistoryTest.setAnnualLeaveConditionInfo(
					Optional.of(aAnnualLeaveConditionInfo));
					
			remainHistList.add(aAnnualLeaveRemainingHistoryTest);
			remainHistList.add(aAnnualLeaveRemainingHistoryTest);
			
			
			
			ArrayList<AnnualLeaveRemainingHistoryTest> remainHistList2 
			= new ArrayList<AnnualLeaveRemainingHistoryTest>();
			
			toBinaryMap.put("2", remainHistList);
			toBinaryMap.put("3", remainHistList2);
			toBinaryMap.put("4", remainHistList);
			
			
			
			SimpleModule module = new SimpleModule();
	        module.addSerializer(Enum.class, new EnumSerializer());
	        module.addDeserializer(Enum.class, new EnumDeserializer());
	        module.addSerializer(GeneralDate.class, new GeneralDateSerializer());
	        module.addDeserializer(GeneralDate.class, new GeneralDateDeserializer());
	        module.addSerializer(GeneralDateTime.class, new GeneralDateTimeSerializer());
	        module.addDeserializer(GeneralDateTime.class, new GeneralDateTimeDeserializer());
//	        module.addSerializer(YearMonth.class, new YearMonthSerializer());
//	        module.addDeserializer(YearMonth.class, new YearMonthDeserializer());
	        JsonMapping.MAPPER.registerModule(module);
	        
	        
	        //JSON文字列に変換
	        String json = JsonMapping.toJson(toBinaryMap);
	            
	            //System.out.println(json);
	            
	//		// JSON変換用のクラス
	//        ObjectMapper mapper = new ObjectMapper();
	// 
	        System.out.println(json);
			
	        
	        //ファイル書き出し
	//        val file = Paths.get("c:\\jinno\\binarytest.csv");
	//		ObjectBinaryFile.write(json, file);
			
	        String fileName = "c:\\jinno\\jsontest.csv";
			try {
	            FileWriter fw = new FileWriter(fileName);
	            fw.write(json);
	            fw.close();
	        } catch (IOException ex) {
	            ex.printStackTrace();
	        }
	        
			// ファイル読み出し
			//FileReader fileReader = new FileReader(fileName);
			String text = readAll(fileName);
			
			
			Map<String,ArrayList<AnnualLeaveRemainingHistoryTest>> map9 = null;
			
			// jsonをオブジェクトへ変換
			System.out.println("1");
			try{
				map9
					= JsonMapping.parse(text, toBinaryMap.getClass());
				
			}catch(Exception e2){
				e2.printStackTrace();
			}
			
			System.out.println("2");
			
			
			ArrayList<AnnualLeaveRemainingHistoryTest> remainHistList9
				= map9.get("4");
			
			System.out.println("3");
			System.out.println("リストサイズ＝" + String.valueOf(remainHistList9.size()));
			
			Object val9 = remainHistList9.get(0);
			System.out.println(val9.getClass().toString());
			
			
			// エラー　↓
			AnnualLeaveRemainingHistoryTest aAnnualLeaveRemainingHistoryTest9
				= remainHistList9.get(0);
			
			
			
			System.out.println("4");
			
			if ( aAnnualLeaveRemainingHistoryTest9.getAnnualLeaveConditionInfo().isPresent()){
				Double d = aAnnualLeaveRemainingHistoryTest9.getAnnualLeaveConditionInfo().get().getPrescribedDays().v();
				System.out.println( "getPrescribedDays().v() = " + d.toString() );
			} else {
				System.out.println( "aAnnualLeaveRemainingHistoryTest9.getAnnualLeaveConditionInfo() = Empty" );
			}
			
			System.out.println("5");
			
	
			// 変換したオブジェクトをファイルに保存する
			String json2 = JsonMapping.toJson(map9);
			System.out.println(json2);
			
			String fileName2 = "c:\\jinno\\jsontest2.csv";
			try {
	            FileWriter fw2 = new FileWriter(fileName2);
	            fw2.write(json2);
	            fw2.close();
	        } catch (IOException ex) {
	            ex.printStackTrace();
	        }
		} catch (Exception ex9) {
            ex9.printStackTrace();
        }
	}
	
	// ファイル読み出し メソッド定義
	public static String readAll(String path) throws IOException {
	    StringBuilder builder = new StringBuilder();

	    try (BufferedReader reader = new BufferedReader(new FileReader(path))) {
	        String string = reader.readLine();
	        while (string != null){
	            builder.append(string + System.getProperty("line.separator"));
	            string = reader.readLine();
	        }
	    }

	    return builder.toString();
	}
	
}


