package nts.uk.ctx.at.record.dom.remainingnumber.annualleave.export;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

import lombok.val;

public class TestAnnualLeave {

//	private Map<int, inputObject1> inputList1;
//	private Map<int, inputObject2> inputList2;
//	private Map<int, expectedValue> expectedValueList;
	
	private List<AnnualleaveTestCase> caseList;	// テストケース
	
	
	/*検証用のドメイン取得処理*/
	@Before
	public void initialize(){	
//		this.inputList = inputObject1.build();
//		this.inputList = inputObject2.build();
//		this.expectedValueList = expectedVaue.build();
		
		this.caseList = AnnualleaveTestCase.build();
	}

	/*検証method*/
	@Test
	public void assert1(){
		try{
			
			for(AnnualleaveTestCase ca : this.caseList){
	//			val exp = expectedVaueList.get(ca.no);			
	//			val input1 = this.inputList1.get(ca.input1No)			
	//			val input2 = this.inputList1.get(ca.input2No)			
							
	//			// テストしたい処理を実行			
	//			val result = testProcedure(input1, input2);
				
	//			// 検証			
	//			assertProcedure(result, exp, case);
				
				String ss = "";
			}
		}
		catch(Exception e1)
		{
			e1.printStackTrace();
		}
	}					

//	private assertProcedure(result, exp, ca){				
//		assertThat(result.attr1.isEqualTo(exp.attr1).as(case.asStr("no").String() + "attr1");			
//		assertThat(result.attr2.isEqualTo(exp.attr2).as(case.asStr("no").String() + "attr2");			
//		assertThat(result.attr3.isEqualTo(exp.attr3).as(case.asStr("no").String() + "attr3");			
//	}				

	
}
